package ai.magicemerge.bluebird.app.service.common;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeNode<C extends TreeNode<?>>  implements Serializable {
	private static final long serialVersionUID = 5752716732980318532L;


	private Integer id;

	/**
	 * 父节点ID
	 */
	private Integer parentId;

	/**
	 * 子节点集合
	 */

	private List<TreeNode<C>> children = Lists.newArrayList();

}
