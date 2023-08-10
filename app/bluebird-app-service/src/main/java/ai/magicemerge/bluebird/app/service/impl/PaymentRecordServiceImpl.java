package ai.magicemerge.bluebird.app.service.impl;

import ai.magicemerge.bluebird.app.dal.model.PaymentRecord;
import ai.magicemerge.bluebird.app.dal.mapper.PaymentRecordMapper;
import ai.magicemerge.bluebird.app.service.PaymentrecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用付费记录 服务实现类
 * </p>
 *
 * @author GygesM
 * @since 2023-05-29
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements PaymentrecordService {

}
