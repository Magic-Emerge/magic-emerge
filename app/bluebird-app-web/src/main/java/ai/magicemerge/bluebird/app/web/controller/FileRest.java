package ai.magicemerge.bluebird.app.web.controller;


import ai.magicemerge.bluebird.app.service.common.ApiResponse;
import ai.magicemerge.bluebird.app.service.common.annotions.JwtScope;
import ai.magicemerge.bluebird.app.service.common.config.CommonProperties;
import ai.magicemerge.bluebird.app.service.common.utils.FileUploadUtils;
import ai.magicemerge.bluebird.app.service.config.StorageConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

import static ai.magicemerge.bluebird.app.service.common.constant.Const.BASE_API;

@Slf4j
@Api(tags = "文件上传")
@RestController
@RequestMapping(value = BASE_API + "/file")
public class FileRest {

	@Autowired
	private CommonProperties commonProperties;

	@Autowired
	private FileUploadUtils fileUploadUtils;

	@Autowired
	private StorageConfiguration storageConfiguration;


	@ApiOperation("上传应用头像图片")
	@PostMapping(value = "/image/upload")
	@JwtScope
	public ApiResponse<String> uploadLogo(@RequestParam(value = "image") MultipartFile file) {
		if (file.isEmpty()) {
			return ApiResponse.error("不支持上传空的对象");
		}
		String fileType = StringUtils.isNotBlank(file.getOriginalFilename()) ? file.getOriginalFilename().split("\\.")[1] : "";
		if (!Arrays.asList("jpeg","png", "jpg").contains(fileType)) {
			return ApiResponse.error("不支持上传该文件类型的图片或文件");
		}
		String imagePath = fileUploadUtils.uploadFile(file, storageConfiguration.getUploadFilePath(), "/profile/images");
		String suffixImagePath = File.separator + imagePath.replace(storageConfiguration.getUploadFilePath(), "");
		String imgHost = commonProperties.getImgRemoteUrl();

		log.info("该图片:{}, 浏览地址为: {}", file.getOriginalFilename(), imgHost + suffixImagePath);
		return ApiResponse.ok(imgHost + suffixImagePath);
	}


}
