package ai.magicemerge.bluebird.app.service.impl;


import ai.magicemerge.bluebird.app.dal.mapper.RecordMapper;
import ai.magicemerge.bluebird.app.dal.model.Record;
import ai.magicemerge.bluebird.app.service.RecordService;
import ai.magicemerge.bluebird.app.service.common.exceptions.BusinessException;
import ai.magicemerge.bluebird.app.service.common.utils.AuthUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 充值记录 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

	@Override
	public Record getMsgCountByUserId(String userId) {
		LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<Record>()
				.eq(Record::getUserId, userId);
		Record record = baseMapper.selectOne(queryWrapper);
		return record;
	}

	@Override
	public Boolean checkLimit(String userId) {
		LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<Record>()
				.eq(Record::getUserId, userId);
		Record record = baseMapper.selectOne(queryWrapper);
		if (AuthUtils.isAdmin() || AuthUtils.isSuperAdmin()) {
			return false;
		} else {
			if (Objects.nonNull(record)) {
				return 0 == record.getMessageCount();
			}
		}
		return false;
	}

	@Override
	public Boolean subMessageCount(String userId) {
		Record msgCountByUserId = this.getMsgCountByUserId(userId);
		if (Objects.nonNull(msgCountByUserId)) {
			Integer messageCount = msgCountByUserId.getMessageCount();
			if (0 == messageCount) {
				throw new BusinessException("surpassed the number of trial uses");
			}
			msgCountByUserId.setMessageCount(messageCount - 1);
			int update = baseMapper.updateById(msgCountByUserId);
			return update > 0;
		}
		return false;
	}
}
