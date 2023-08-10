package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.dto.ActiveUserDto;
import ai.magicemerge.bluebird.app.dal.dto.AnalysisQueryDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageCountDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageTokenDto;

import java.util.List;

public interface AnalysisService {

	List<MessageCountDto> queryMessageCountList(AnalysisQueryDto analysisQueryDto);

	List<MessageTokenDto> queryMessageTokenList(AnalysisQueryDto analysisQueryDto);

	List<ActiveUserDto> queryActiveUserList(AnalysisQueryDto analysisQueryDto);

}
