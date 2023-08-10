package ai.magicemerge.bluebird.app.service.common.utils;

import ai.magicemerge.bluebird.app.service.common.TreeNode;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 构建树结构集合工具类
 */
public class TreeBuildUtils {

	/**
	 * 构建树结构
	 *
	 * @param treeNodes
	 * @param parentId
	 * @param <T>
	 * @return
	 */
	public  static <T extends TreeNode<T>>List<T> build(List<T> treeNodes, Integer parentId){
		if (parentId == null){
			return null;
		}
		List<T> rootNodes = Lists.newArrayList();
		for (T t : treeNodes){
			if (parentId.equals(t.getParentId())){
				//得到根节点
				rootNodes.add(t);
			}
		}
		for (T t : rootNodes){
			t.setChildren(getChildNodes(t,treeNodes));
		}
		return rootNodes;
	}

	/**
	 * 构建子节点
	 *
	 * @param t
	 * @param treeNodes
	 * @param <T>
	 * @return
	 */
	private static <T extends TreeNode<T>>List<TreeNode<T>> getChildNodes(TreeNode<T> t,List<T> treeNodes){
		List<TreeNode<T>> childNodes = Lists.newArrayList();
		for (TreeNode<T> t1 : treeNodes){
			if (t1.getParentId().equals(t.getId())){
				childNodes.add(t1);
			}
		}
		if (childNodes.size() == 0){
			return null;
		}
		for (TreeNode<T> t1 : childNodes){
			t1.setChildren(getChildNodes(t1,treeNodes));
		}
		return childNodes;
	}

}
