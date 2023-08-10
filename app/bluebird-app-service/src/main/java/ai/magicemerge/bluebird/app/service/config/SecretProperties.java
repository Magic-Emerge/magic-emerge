package ai.magicemerge.bluebird.app.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server.secret")
public class SecretProperties {
	/**
	 * 是否开启
	 */
	private boolean enabled;

	/**
	 * 3des 密钥长度不得小于24
	 */
	private String desSecretKey;
	/**
	 * 3des IV向量必须为8位
	 */
	private String desIv;

}
