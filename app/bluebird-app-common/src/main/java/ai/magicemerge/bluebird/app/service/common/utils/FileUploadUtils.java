package ai.magicemerge.bluebird.app.service.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Component
public class FileUploadUtils {

	public String uploadFile(MultipartFile file, String rootPath, String secondPath) {
		String serverFileNamePath = "";
		if (!file.isEmpty()) {
			String name = file.getOriginalFilename();
			String md5Name = "";
			if (StringUtils.isNotBlank(name)) {
				String filetype = "." + name.split("\\.")[1];
				md5Name = UUID.randomUUID().toString().replace("-","") + filetype;
			}
			try {
				File dir = new File(rootPath + File.separator + secondPath);
				if (!dir.exists()) {
					boolean mkdirs = dir.mkdirs();
					if (!mkdirs) {
						log.error("failed to create dir: {}", dir.getPath());
					}
				}
				File serverFile = new File(dir.getAbsolutePath() + File.separator + md5Name);
				file.transferTo(serverFile);
				log.info("Server File Location= {} ", serverFile.getAbsolutePath());
				serverFileNamePath = serverFile.getPath();
			} catch (Exception e) {
				log.error("You failed to upload " + name + " => {} " + e);
			}
		}
		return serverFileNamePath;
	}
}
