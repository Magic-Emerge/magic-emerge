package ai.magicemerge.bluebird.app.web.params;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConversationsRequest implements Serializable {
	private static final long serialVersionUID = -9183711241733201928L;

	private String user;

	private String lastId;

	private Long limit;

	private String appKey;

}
