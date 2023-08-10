package ai.magicemerge.bluebird.app.dal.utils;

import ai.magicemerge.bluebird.app.dal.mapper.MessagesMapper;
import ai.magicemerge.bluebird.app.dal.model.Messages;
import ai.magicemerge.bluebird.app.service.common.utils.SpringContextHolder;

public class MessagesUtils {


	private static MessagesMapper messagesMapper = SpringContextHolder.getBean(MessagesMapper.class);

	/**
	 * 保存信息
	 *
	 * @param messages
	 * @return
	 */
	public static Boolean saveMessages(Messages messages) {
		int insert = messagesMapper.insert(messages);
		return insert > 0;
	}


}
