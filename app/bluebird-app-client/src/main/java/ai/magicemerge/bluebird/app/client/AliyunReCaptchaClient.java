package ai.magicemerge.bluebird.app.client;

import ai.magicemerge.bluebird.app.service.common.bean.AuthSigRequestDto;
import ai.magicemerge.bluebird.app.service.common.config.CommonProperties;
import ai.magicemerge.bluebird.app.service.common.utils.SpringContextHolder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AliyunReCaptchaClient {

	private static final CommonProperties commonProperties = SpringContextHolder.getBean(CommonProperties.class);

	private static final String regionid = commonProperties.getProperty("regionid");
	private static final String accessKeyId = commonProperties.getProperty("accessKeyId");
	private static final String accessKeySecret = commonProperties.getProperty("accessKeySecret");
	public static final String appKey = commonProperties.getProperty("appKey");

	private static final IClientProfile profile = DefaultProfile.getProfile(regionid, accessKeyId, accessKeySecret);

	private static final IAcsClient client;

	static {
		client = new DefaultAcsClient(profile);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "afs", "afs.aliyuncs.com");
		} catch (ClientException e) {
			log.error("初始化阿里云配置失败");
		}
	}

	public Boolean verifyTheCaptcha(AuthSigRequestDto authSigRequestDto) {
		AuthenticateSigRequest request = new AuthenticateSigRequest();
		request.setSessionId(authSigRequestDto.getSessionId());// 会话ID。必填参数，从前端sucess回调中获取，不可更改。
		request.setSig(authSigRequestDto.getSig());// 签名串。必填参数，从前端sucess回调中获取，不可更改。
		request.setToken(authSigRequestDto.getToken());// 请求唯一标识。必填参数，从前端sucess回调中获取，不可更改。
		request.setScene(authSigRequestDto.getScene());// 场景标识。必填参数，与前端页面填写数据一致，不可更改。
		request.setAppKey(appKey);// 应用类型标识。必填参数，后端填写。
		request.setRemoteIp(authSigRequestDto.getRemoteIp());// 客户端IP。必填参数，后端填写。
		try {
			//response的code枚举：100验签通过，900验签失败。
			AuthenticateSigResponse response = client.getAcsResponse(request);
			return response.getCode() == 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}






}
