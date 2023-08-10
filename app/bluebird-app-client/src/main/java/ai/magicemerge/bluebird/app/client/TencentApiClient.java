package ai.magicemerge.bluebird.app.client;

import ai.magicemerge.bluebird.app.service.common.LocalCache;
import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class TencentApiClient {

    @Autowired
    private SmsClient smsClient;

    @Value(value = "${tencent.sms.sdk-app-id}")
    private String sdkAppId;

    @Value(value = "${tencent.sms.sign-name}")
    private String signName;

    @Value(value = "${tencent.sms.template-id}")
    private String templateId;


    public boolean SendSMS(String phoneNumber, String code) throws TencentCloudSDKException {
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(sdkAppId);
        req.setSignName(signName);
        req.setTemplateId(templateId);

        req.setPhoneNumberSet(new String[]{phoneNumber});
        String[] templateParamSet = {code, String.valueOf(LocalCache.TIMEOUT_MINUTE)};
        req.setTemplateParamSet(templateParamSet);

        SendSmsResponse response = smsClient.SendSms(req);
        SendStatus[] sendStatusSet = response.getSendStatusSet();
        log.info("发送sms状态信息: {}", JSON.toJSONString(sendStatusSet));
        if (sendStatusSet.length == 0) {
            return false;
        }
        return Objects.equals(sendStatusSet[0].getCode(), "Ok");
    }

}
