package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.model.Notify;
import ai.magicemerge.bluebird.app.dal.mapper.NotifyMapper;
import ai.magicemerge.bluebird.app.service.NotifyService;
import ai.magicemerge.bluebird.app.service.dto.NotifyDto;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 应用通知 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-07-09
 */
@Service
public class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify> implements NotifyService {


	@Override
	public List<Notify> getNotifyList(NotifyDto notifyDto) {

		LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();

		if (Objects.nonNull(notifyDto.getIsHandle())) {
			queryWrapper.eq(Notify::getIsHandle, notifyDto.getIsHandle());
		}

		if (StringUtils.isNotBlank(notifyDto.getOpenUserId())) {
			queryWrapper.eq(Notify::getOpenUserId, notifyDto.getOpenUserId());
		}

		if (StringUtils.isNotBlank(notifyDto.getAlertType())) {
			queryWrapper.eq(Notify::getAlertType, notifyDto.getAlertType());
		}

		List<Notify> notifies = baseMapper.selectList(queryWrapper);

		return notifies;
	}
}
