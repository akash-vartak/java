// Copyright (C) 2010, 2011, 2012 GlavSoft LLC.
// All rights reserved.
//
//-------------------------------------------------------------------------
// This file is part of the TightVNC software.  Please visit our Web site:
//
//                       http://www.tightvnc.com/
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along
// with this program; if not, write to the Free Software Foundation, Inc.,
// 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//-------------------------------------------------------------------------
//

package com.glavsoft.rfb.protocol;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;

import com.glavsoft.drawing.Renderer;
import com.glavsoft.exceptions.CommonException;
import com.glavsoft.exceptions.ProtocolException;
import com.glavsoft.exceptions.TransportException;
import com.glavsoft.rfb.ClipboardController;
import com.glavsoft.rfb.IRepaintController;
import com.glavsoft.rfb.client.FramebufferUpdateRequestMessage;
import com.glavsoft.rfb.client.SetPixelFormatMessage;
import com.glavsoft.rfb.encoding.EncodingType;
import com.glavsoft.rfb.encoding.PixelFormat;
import com.glavsoft.rfb.encoding.decoder.Decoder;
import com.glavsoft.rfb.encoding.decoder.DecodersContainer;
import com.glavsoft.rfb.encoding.decoder.FramebufferUpdateRectangle;
import com.glavsoft.rfb.encoding.decoder.RichCursorDecoder;
import com.glavsoft.transport.Reader;

public class ReceiverTask implements Runnable {

	private static final byte FRAMEBUFFER_UPDATE = 0;

	private static final byte SET_COLOR_MAP_ENTRIES = 1;

	private static final byte BELL = 2;

	private static final byte SERVER_CUT_TEXT = 3;

	private final Reader reader;

	private volatile boolean isRunning = false;

	private Renderer renderer;

	private final IRepaintController repaintController;

	private final ClipboardController clipboardController;

	private final DecodersContainer decoders;

	private FramebufferUpdateRequestMessage fullscreenFbUpdateIncrementalRequest;

	private final ProtocolContext context;

	private PixelFormat pixelFormat;

	private boolean needSendPixelFormat;

	private final Logger logger;

	public ReceiverTask(
			final Logger logger,
			final Reader reader,
			final IRepaintController repaintController,
			final ClipboardController clipboardController,
			final DecodersContainer decoders,
			final ProtocolContext context) {
		this.logger = logger;
		this.reader = reader;
		this.repaintController = repaintController;
		this.clipboardController = clipboardController;
		this.context = context;
		this.decoders = decoders;
		renderer = repaintController.createRenderer(reader, context.getFbWidth(), context.getFbHeight(), context.getPixelFormat());
		fullscreenFbUpdateIncrementalRequest = new FramebufferUpdateRequestMessage(0, 0, context.getFbWidth(), context.getFbHeight(), true);
	}

	@Override
	public void run() {
		isRunning = true;
		while (isRunning) {
			try {
				final byte messageId = reader.readByte();
				switch (messageId) {
				case FRAMEBUFFER_UPDATE:
					// logger.trace("Server message: FramebufferUpdate (0)");
					framebufferUpdateMessage();
					break;
				case SET_COLOR_MAP_ENTRIES:
					logger.debug("Server message SetColorMapEntries is not implemented. Skip.");
					setColorMapEntries();
					break;
				case BELL:
					logger.trace("Server message: Bell");
					System.out.print("\0007");
					System.out.flush();
					break;
				case SERVER_CUT_TEXT:
					logger.trace("Server message: CutText (3)");
					serverCutText();
					break;
				default:
					logger.debug("Unsupported server message. Id = " + messageId);
				}
			}
			catch (final TransportException e) {
				logger.debug("Close session: " + e.getMessage());
				if (isRunning) {
					context.cleanUpSession("Connection closed.");
				}
				stopTask();
			}
			catch (final ProtocolException e) {
				logger.debug(e.getMessage());
				if (isRunning) {
					context.cleanUpSession(e.getMessage() + "\nConnection closed.");
				}
				stopTask();
			}
			catch (final CommonException e) {
				logger.debug(e.getMessage());
				if (isRunning) {
					context.cleanUpSession("Connection closed.");
				}
				stopTask();
			}
			catch (final Throwable te) {
				final StringWriter sw = new StringWriter();
				final PrintWriter pw = new PrintWriter(sw);
				te.printStackTrace(pw);
				if (isRunning) {
					context.cleanUpSession(te.getMessage() + "\n" + sw.toString());
				}
				stopTask();
			}
		}
	}

	private void setColorMapEntries() throws TransportException {
		reader.readByte(); // padding
		reader.readUInt16(); // first color index
		int length = reader.readUInt16();
		while (length-- > 0) {
			reader.readUInt16(); // R
			reader.readUInt16(); // G
			reader.readUInt16(); // B
		}
	}

	private void serverCutText() throws TransportException {
		reader.readByte(); // padding
		reader.readInt16(); // padding
		final int length = reader.readInt32() & Integer.MAX_VALUE;
		clipboardController.updateSystemClipboard(reader.readBytes(length));
	}

	public void framebufferUpdateMessage() throws CommonException {
		reader.readByte(); // padding
		int numberOfRectangles = reader.readUInt16();
		while (numberOfRectangles-- > 0) {
			final FramebufferUpdateRectangle rect = new FramebufferUpdateRectangle();
			rect.fill(reader);

			final Decoder decoder = decoders.getDecoderByType(rect.getEncodingType());
			logger.trace(rect.toString() + (0 == numberOfRectangles ? "\n---" : ""));
			if (decoder != null) {
				decoder.decode(reader, renderer, rect);
				repaintController.repaintBitmap(rect);
			}
			else if (rect.getEncodingType() == EncodingType.RICH_CURSOR) {
				RichCursorDecoder.getInstance().decode(reader, renderer, rect);
				repaintController.repaintCursor();
			}
			else if (rect.getEncodingType() == EncodingType.CURSOR_POS) {
				renderer.decodeCursorPosition(rect);
				repaintController.repaintCursor();
			}
			else if (rect.getEncodingType() == EncodingType.DESKTOP_SIZE) {
				fullscreenFbUpdateIncrementalRequest = new FramebufferUpdateRequestMessage(0, 0, rect.width, rect.height, true);
				synchronized (renderer) {
					renderer = repaintController.createRenderer(reader, rect.width, rect.height, context.getPixelFormat());
				}
				context.sendMessage(new FramebufferUpdateRequestMessage(0, 0, rect.width, rect.height, false));
				// repaintController.repaintCursor();
			}
			else
				throw new CommonException("Unprocessed encoding: " + rect.toString());
		}
		synchronized (this) {
			if (needSendPixelFormat) {
				needSendPixelFormat = false;
				context.setPixelFormat(pixelFormat);
				context.sendMessage(new SetPixelFormatMessage(pixelFormat));
				logger.trace("sent: " + pixelFormat);
				context.sendRefreshMessage();
				logger.trace("sent: nonincremental fb update");
			}
			else {
				context.sendMessage(fullscreenFbUpdateIncrementalRequest);
			}
		}
	}

	public synchronized void queueUpdatePixelFormat(final PixelFormat pf) {
		pixelFormat = pf;
		needSendPixelFormat = true;
		// context.sendMessage(new FramebufferUpdateRequestMessage(0, 0, 1, 1, false));
	}

	public void stopTask() {
		isRunning = false;
	}

}
