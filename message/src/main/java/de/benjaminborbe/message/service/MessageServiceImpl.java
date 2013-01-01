package de.benjaminborbe.message.service;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.benjaminborbe.message.api.MessageIdentifier;
import de.benjaminborbe.message.api.MessageService;
import de.benjaminborbe.message.api.MessageServiceException;
import de.benjaminborbe.message.dao.MessageBean;
import de.benjaminborbe.message.dao.MessageDao;
import de.benjaminborbe.message.util.MessageUnlock;
import de.benjaminborbe.storage.api.StorageException;
import de.benjaminborbe.tools.util.IdGeneratorUUID;

@Singleton
public class MessageServiceImpl implements MessageService {

	private final Logger logger;

	private final MessageDao messageDao;

	private final IdGeneratorUUID idGeneratorUUID;

	private final MessageUnlock messageUnlock;

	@Inject
	public MessageServiceImpl(final Logger logger, final MessageDao messageDao, final IdGeneratorUUID idGeneratorUUID, final MessageUnlock messageUnlock) {
		this.logger = logger;
		this.messageDao = messageDao;
		this.idGeneratorUUID = idGeneratorUUID;
		this.messageUnlock = messageUnlock;
	}

	@Override
	public void sendMessage(final String type, final String id, final String content) throws MessageServiceException {
		try {
			logger.debug("sendMessage");
			final MessageBean bean = messageDao.create();
			bean.setId(new MessageIdentifier(type + "_" + id));
			bean.setType(type);
			bean.setContent(content);
			bean.setRetryCounter(0l);
			messageDao.save(bean);
		}
		catch (final StorageException e) {
			throw new MessageServiceException(e);
		}
	}

	@Override
	public void sendMessage(final String type, final String content) throws MessageServiceException {
		sendMessage(type, idGeneratorUUID.nextId(), content);
	}

	@Override
	public boolean unlockExpiredMessages() {
		return messageUnlock.execute();
	}
}