package ai.magicemerge.bluebird.app.service.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentApiConfig {

    @Value(value = "${tencent.sms.secret-id}")
    private String secretId;

    @Value(value = "${tencent.sms.secret-key}")
    private String secretKey;

    @Value(value = "${tencent.sms.endpoint}")
    private String endpoint;

    @Value(value = "${tencent.sms.timeout}")
    private Integer timeout;

    @Bean
    public SmsClient initSmsClient() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(timeout);
        httpProfile.setEndpoint(endpoint);

        Credential credential = new Credential(secretId, secretKey);

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);

        return new SmsClient(credential, "ap-guangzhou",clientProfile);
    }

}
