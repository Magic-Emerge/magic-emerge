package ai.magicemerge.bluebird.app.dal.mapper;

import ai.magicemerge.bluebird.app.dal.dto.ActiveUserDto;
import ai.magicemerge.bluebird.app.dal.dto.AnalysisQueryDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageCountDto;
import ai.magicemerge.bluebird.app.dal.dto.MessageTokenDto;
import ai.magicemerge.bluebird.app.dal.model.Messages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * AI聊天记录 Mapper 接口
 * </p>
 *
 * @author GygesM
 * @since 2023-06-04
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {

	List<MessageCountDto> countMessagesByDate(AnalysisQueryDto queryDto);

	List<MessageTokenDto> sumTokenByDate(AnalysisQueryDto queryDto);

	List<ActiveUserDto> countActiveUserByDate(AnalysisQueryDto queryDto);


}
