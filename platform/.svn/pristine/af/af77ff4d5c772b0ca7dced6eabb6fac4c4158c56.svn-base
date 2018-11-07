package com.fable.insightview.platform.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.entity.Entity;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * @Description:   单位信息Service
 *
 */
public interface IOrganizationService {
	List<OrganizationBean> getOrganizationBeanByConditions(String paramName,
			String paramValue);

	/* 更新单位组织 */
	boolean updateOrganizationBean(OrganizationBean organizationBean);

	/* 数据总记录数 */
	int getTotalCount(OrganizationBean organizationBean);

	/* 新增单位组织 */
	boolean addOrganization(OrganizationBean organizationBean);

	/* 删除单位组织 */
	String delOrganizationById(OrganizationBean organizationBean);

	/* 查询单位组织 */
	List<OrganizationBean> getOrganizationByConditions(OrganizationBean organizationBean,FlexiGridPageInfo flexiGridPageInfo);

	/*根据单位Id查询用户 */
	List<SysUserInfoBean> findUserByOrganizationId(int organizationId);

	/*根据单位Id查询单位组织 */
	OrganizationBean findOrganizationById(int organizationId);

	/* 根据上级组织查询单位组织 */
	List<OrganizationBean> findOrganizationsByParentId(int parentId);
	
	/* 条件查询单位组织 */
	List<OrganizationBean> getOrganizationByConditions(String paramName,String paramValue);

	/* 获得组织树 */
	List<OrganizationBean> getOrganizationTreeVal();
	
	/* 获得本组织树 */
	List<OrganizationBean> getOrganizationTree(String deptId);
	
	/* 验证单位名称的唯一性 */
	public boolean checkOrganizationName(OrganizationBean organizationBean);
	
	/* 验证单位编码的唯一性 */
	public boolean checkOrganizationCode(OrganizationBean organizationBean);
	
	/* 根据单位名称查询单位组织 */
	public OrganizationBean getOrganizationBeanByOrgName(String organizationName);
	
	/* 删除前验证单位组织 */
	public boolean checkBeforeDel(OrganizationBean organizationBean);

	/*获取单位组织下拉列表*/
	List<OrganizationBean> listOrganization();

	/*获取改单位下的用户Account集合*/
	List<Integer> getUserAccountsByOrgId(Integer branchId);

	/*获取供应商用户所在供应商服务的单位列表*/
	List<OrganizationBean> listOrganizationByUserId(int currentUserId);
	
	/**
	 * 根据用户Id查询所属单位信息
	 * @param userId
	 * @return
	 */
	List<OrganizationBean> getOrgByUserId(int userId);
	
	public List<OrganizationBean> getOrgByParentId(int parentOrgId);
	
	public List<OrganizationBean> queryOrgConditionList(Integer orgID);
	
	public List<OrganizationBean> queryOrgProviderTree(Integer orgID);
	
	public List<OrganizationBean> queryOrgAll();
}
