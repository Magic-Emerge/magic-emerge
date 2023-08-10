package ai.magicemerge.bluebird.app.service;

import ai.magicemerge.bluebird.app.dal.model.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 充值记录 服务类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
public interface RecordService extends IService<Record> {

	Record getMsgCountByUserId(String userId);

	Boolean checkLimit(String userId);

	Boolean subMessageCount(String userId);

}
