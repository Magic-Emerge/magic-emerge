package ai.magicemerge.bluebird.app.web.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AppNewsRequest {

	@Schema(description = "新闻标题", name = "新闻标题")
	private String title;

}
