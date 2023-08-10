package ai.magicemerge.bluebird.app.service.common.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@Scope("singleton")
public class CommonProperties {

	public static final Logger logger = LoggerFactory.getLogger(CommonProperties.class);

	private static final int RELOAD_DELAY = 5;
	private PropertiesConfiguration configuration;


	private String imgRemoteUrl;

	private Integer maxModShopInfoTimes;

	private Boolean accessLogOpen;

	private List<String> queryDaysParams;


	@PostConstruct
	private void init() throws ConfigurationException {
		configuration = new PropertiesConfiguration("common.properties");
		FileChangedReloadingStrategy fileChangedReloadingStrategy = new FileChangedReloadingStrategy();
		fileChangedReloadingStrategy.setRefreshDelay(RELOAD_DELAY * 1000);
		configuration.setReloadingStrategy(fileChangedReloadingStrategy);
		logger.info("Loaded common properties");
	}

	public String getProperty(String key) {
		return (String) configuration.getProperty(key);
	}

	public void setProperty(String key, Object value) {
		configuration.setProperty(key, value);
	}

	public void save() {
		try {
			configuration.save();
		} catch (ConfigurationException e) {
			logger.error("Unable to save properties", e);
		}
	}


	public String getImgRemoteUrl() {
		return configuration.getString("img.remote.root.url");
	}


	public Integer getMaxModShopInfoTimes() {
		return configuration.getInt("max.mod.shop.count");
	}


	public Boolean getAccessLogOpen() {
		return configuration.getBoolean("access.log.isOpen");
	}


	public List<Object> getQueryDaysParam() {
		return configuration.getList("query.analysis.days");
	}
}
