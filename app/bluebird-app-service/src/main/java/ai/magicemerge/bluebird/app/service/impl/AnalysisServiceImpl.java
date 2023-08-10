package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.dto.ActiveUserDto;
import ai.magicemerge.bluebird.app.dal.dto.AnalysisQueryDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageCountDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageTokenDto;
import ai.magicemerge.bluebird.app.dal.mapper.MessagesMapper;
import ai.magicemerge.bluebird.app.service.AnalysisService;
import ai.magicemerge.bluebird.app.service.common.config.CommonProperties;
import ai.magicemerge.bluebird.app.service.common.constant.ResCode;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AnalysisServiceImpl implements AnalysisService {

	@Autowired
	private MessagesMapper messagesMapper;

	@Autowired
	private CommonProperties commonProperties;


	@Override
	public List<MessageCountDto> queryMessageCountList(AnalysisQueryDto analysisQueryDto) {
		validateParam(analysisQueryDto.getParams());
		List<MessageCountDto> messageCountDtos = messagesMapper.countMessagesByDate(analysisQueryDto);
		if (CollectionUtil.isEmpty(messageCountDtos)) {
			MessageCountDto messageCountDto = new MessageCountDto();
			messageCountDto.setDatetime(DateUtil.parse(DateUtil.formatLocalDateTime(LocalDateTime.now()), "yyyy-MM-dd"));
			messageCountDto.setMessagescount(0);
			messageCountDtos.add(messageCountDto);
		}
		return messageCountDtos;
	}

	@Override
	public List<MessageTokenDto> queryMessageTokenList(AnalysisQueryDto analysisQueryDto) {
		validateParam(analysisQueryDto.getParams());
		List<MessageTokenDto> messageTokenDtos = messagesMapper.sumTokenByDate(analysisQueryDto);
		if (CollectionUtil.isEmpty(messageTokenDtos)) {
			MessageTokenDto messageTokenDto = new MessageTokenDto();
			messageTokenDto.setDatetime(DateUtil.parse(DateUtil.formatLocalDateTime(LocalDateTime.now()), "yyyy-MM-dd"));
			messageTokenDto.setTokenscount(0);
			messageTokenDtos.add(messageTokenDto);
		}
		return messageTokenDtos;
	}

	@Override
	public List<ActiveUserDto> queryActiveUserList(AnalysisQueryDto analysisQueryDto) {
		validateParam(analysisQueryDto.getParams());
		List<ActiveUserDto> activeUserDtos = messagesMapper.countActiveUserByDate(analysisQueryDto);
		if (CollectionUtil.isEmpty(activeUserDtos)) {
			ActiveUserDto activeUserDto = new ActiveUserDto();
			activeUserDto.setDatetime(DateUtil.parse(DateUtil.formatLocalDateTime(LocalDateTime.now()), "yyyy-MM-dd"));
			activeUserDto.setUserscount(0);
			activeUserDtos.add(activeUserDto);
		}
		return activeUserDtos;
	}


	private void validateParam(String newParams) {
		List<Object> queryDaysParams = commonProperties.getQueryDaysParam();
		if (!queryDaysParams.contains(newParams)) {
			throw new BusinessException(ResCode.FAILED.getCode(), "Invalid params value: " + newParams);
		}





	}

}
