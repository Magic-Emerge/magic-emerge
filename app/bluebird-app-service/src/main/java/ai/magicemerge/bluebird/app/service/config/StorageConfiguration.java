package ai.magicemerge.bluebird.app.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("storage.local")
public class StorageConfiguration {


	private String uploadFilePath;

	private Integer maxSize;

	private Boolean allowEmpty;

	private List<String> allowTypes;

	private Integer qrcodeWidth;

	private Integer qrcodeHeight;

	private String qrcodeImgFormat;


	private Integer ean13Width;

	private Integer ean13Height;

	private String ean13ImgFormat;

}
