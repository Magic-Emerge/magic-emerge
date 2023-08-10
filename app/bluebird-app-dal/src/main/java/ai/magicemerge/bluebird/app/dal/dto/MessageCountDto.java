package ai.magicemerge.bluebird.app.dal.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MessageCountDto implements Serializable {
	private static final long serialVersionUID = 3741384843857580550L;


	private Date datetime;

	private Integer messagescount;
}
