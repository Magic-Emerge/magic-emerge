package ai.magicemerge.bluebird.app.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppStoreStarDto extends AppStoreDto implements Serializable {
	private static final long serialVersionUID = -3318259393062300458L;

	@Schema(name = "是否喜欢")
	private Long appPersonalCollectionId;


	@Schema(name = "是否喜欢")
	private Boolean isFavorite;


	@Schema(name = "应用作者")
	private String author;

}
