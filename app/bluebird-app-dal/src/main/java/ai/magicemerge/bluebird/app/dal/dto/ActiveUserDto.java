package ai.magicemerge.bluebird.app.dal.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ActiveUserDto implements Serializable {
	private static final long serialVersionUID = -8359362791957873408L;

	private Date datetime;

	private Integer userscount;

}
