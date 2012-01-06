package de.benjaminborbe.gwt.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public abstract class ResourceServlet extends HttpServlet {

	private static final long serialVersionUID = -5530978651192430456L;

	private final Logger logger;

	@Inject
	public ResourceServlet(final Logger logger) {
		this.logger = logger;
	}

	@Override
	public void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		logger.debug("service");
		response.setContentType(contentType());
		final InputStream input = getClass().getClassLoader().getResourceAsStream(getPath());
		final ServletOutputStream output = response.getOutputStream();
		copy(input, output);
	}

	public abstract String getPath();

	public abstract String contentType();

	protected void copy(final InputStream input, final OutputStream output) throws IOException {
		// get an channel from the stream
		final ReadableByteChannel inputChannel = Channels.newChannel(input);
		final WritableByteChannel outputChannel = Channels.newChannel(output);
		// copy the channels
		ChannelTools.fastChannelCopy(inputChannel, outputChannel);
		// closing the channels
		inputChannel.close();
		outputChannel.close();
	}
}

class ChannelTools {

	public static void fastChannelCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while (src.read(buffer) != -1) {
			// prepare the buffer to be drained
			buffer.flip();
			// write to the channel, may block
			dest.write(buffer);
			// If partial transfer, shift remainder down
			// If buffer is empty, same as doing clear()
			buffer.compact();
		}
		// EOF will leave buffer in fill state
		buffer.flip();
		// make sure the buffer is fully drained.
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
}
