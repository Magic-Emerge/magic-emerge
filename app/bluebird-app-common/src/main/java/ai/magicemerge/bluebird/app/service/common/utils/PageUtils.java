package ai.magicemerge.bluebird.app.service.common.utils;


import ai.magicemerge.bluebird.app.service.common.PageEntity;

import java.util.List;

public class PageUtils {



	public static<T> PageEntity<T> listToPage(List<T> entityList, long pageNum, long pageSize, long total) {
		PageEntity<T> entity = new PageEntity<>();
		entity.setRecords(entityList);
		entity.setPageSize(pageSize);
		entity.setPage(pageNum);
		entity.setTotal(total);
		return entity;
	}


}
