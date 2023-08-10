package ai.magicemerge.bluebird.app.dal.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MessageTokenDto implements Serializable {
	private static final long serialVersionUID = 3210968728335424840L;


	private Date datetime;

	private Integer tokenscount;

	private BigDecimal totalprice;

}
