package ai.magicemerge.bluebird.app.dal.utils;

import ai.magicemerge.bluebird.app.dal.mapper.RecordMapper;
import ai.magicemerge.bluebird.app.dal.model.Record;
import ai.magicemerge.bluebird.app.service.common.utils.SpringContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Objects;

public class ChargeRecordUtils {

	private static final RecordMapper recordMapper = SpringContextHolder.getBean(RecordMapper.class);


	public static Boolean subMessageCount(String userId) {
		LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<Record>()
				.eq(Record::getUserId, userId);
		Record record = recordMapper.selectOne(queryWrapper);
		if (Objects.nonNull(record)) {
			Integer messageCount = record.getMessageCount();
			record.setMessageCount(messageCount - 1);
			int update = recordMapper.updateById(record);
			return update > 0;
		}
		return false;
	}

}
