package ai.magicemerge.bluebird.app.service.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PageEntity <T> implements Serializable {

	private static final long serialVersionUID = -3537330544860565284L;

	private Long page;

	private Long pageSize;

	private Long total;

	private List<T> records;

}
