package com.fable.insightview.platform.mapper;

import java.util.List;
import java.util.Map;

import com.fable.insightview.platform.entity.DepartmentBean;

public interface DepartmentMapper {
     
	List<DepartmentBean> queryDepartTree(Map<String,Object> hashmap);
	
	List<DepartmentBean> queryDepartByOrgID(Map<String,Object> hashmap);

	/**
	 * 根据部门名称查询部门id
	 */
	DepartmentBean queryDepartByDepName(String name);
}
