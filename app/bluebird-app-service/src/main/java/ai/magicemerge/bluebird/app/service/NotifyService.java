package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Notify;
import ai.magicemerge.bluebird.app.service.dto.NotifyDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 应用通知 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-07-09
 */
public interface NotifyService extends IService<Notify> {

	List<Notify> getNotifyList(NotifyDto notifyDto);
}
