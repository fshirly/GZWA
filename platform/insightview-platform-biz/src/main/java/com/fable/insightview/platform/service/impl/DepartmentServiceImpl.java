package com.fable.insightview.platform.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.dao.IDepartmentDao;
import com.fable.insightview.platform.dao.IDepartmentProvDao;
import com.fable.insightview.platform.dao.IOrganizationDao;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.OrgDeptProviderTreeBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.ipmanager.entity.IPManSubNetRDeptBean;
import com.fable.insightview.platform.ipmanager.mapper.IPManSubNetRDeptMapper;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.log.entity.SysLog;
import com.fable.insightview.platform.log.service.ISysLogService;
import com.fable.insightview.platform.mapper.DepartmentMapper;
import com.fable.insightview.platform.service.IDepartmentService;

/**
 * 部门组织Service
 * 
 * @author 武林
 * 
 */
@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService,MessageListener {//update zheng zhen

	private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
	
	@Autowired
	protected IDepartmentDao departmentDao;
//	@Autowired
//	private IDepartmentProvDao departmentProvDao;

	@Autowired
	protected ISysUserDao sysUserDao;

	@Autowired
	protected IOrganizationDao organizationDao;
	
	@Autowired
	protected IPManSubNetRDeptMapper ipManSubNetRDeptMapper;
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private ISysLogService sysLogService;

	@Override
	public int getTotalCount(DepartmentBean departmentBean) {
		return departmentDao.getTotalCount(departmentBean);
	}

	/*
	 * 根据条件查询部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentBeanByConditions(String paramName,
			String paramValue) {
		List<DepartmentBean> deptLst = departmentDao
				.getDepartmentBeanByConditions(paramName, paramValue);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			DepartmentBean dept = deptLst.get(i);
			String parentDeptName = "";
			DepartmentBean deptTemp = departmentDao.getById(dept
					.getParentDeptID());

			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				parentDeptName = deptTemp.getDeptName();
			}
			dept.setParentDeptName(parentDeptName);
			deptLstTemp.add(dept);
		}
		for (int i = 0; i < deptLstTemp.size(); i++) {
			String headName = "";
			if (deptLstTemp.get(i).getHeadOfDept() != null
					&& deptLstTemp.get(i).getHeadOfDept() != -1) {
				headName = departmentDao.getHeadNameByHeadID(deptLstTemp.get(i)
						.getHeadOfDept());
			}
			deptLstTemp.get(i).setHeadName(headName);
		}
		return deptLstTemp;
	}

	/*
	 * 菜单总记录数
	 * 
	 * @author 武林
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateSysUser(DepartmentBean departmentBean) {
		return departmentDao.updateDepartmentBean(departmentBean);
	}

	/*
	 * 获取部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public DepartmentBean getDepartmentById(Integer id) {
		return departmentDao.getById(id);
	}

	/*
	 * 新增部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean addDepartment(DepartmentBean departmentBean) {
		return departmentDao.addDepartment(departmentBean);
	}

	/*
	 * 删除部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public boolean delDepartmentById(DepartmentBean departmentBean) {
		return departmentDao.delDepartmentById(departmentBean);
	}

	/*
	 * 查询部门组织
	 * 
	 * @author 武林
	 */
	@Override
	public List<DepartmentBean> getDepartmentByConditions(
			DepartmentBean departmentBean, FlexiGridPageInfo flexiGridPageInfo) {
		if (departmentBean.getTreeType() != null
				&& !"".equals(departmentBean.getTreeType())
				&& !"-1".equals(departmentBean.getTreeType())) {
			String treeType = departmentBean.getTreeType();
			if (treeType.startsWith("O") == true) {
				treeType = treeType.substring(1);
				int orgID = Integer.parseInt(treeType);
				departmentBean.setOrganizationID(orgID);
			} else if (treeType.startsWith("D") == true) {
				treeType = treeType.substring(1);
				int deptID = Integer.parseInt(treeType);
				departmentBean.setDeptId(deptID);
			}
		}
		// System.out.println("部门ID："+departmentBean.getDeptId()+"======上级部门ID："+departmentBean.getParentDeptID()+"=========所属单位："+departmentBean.getOrganizationBean().getOrganizationID()+"/"+departmentBean.getOrganizationBean().getOrganizationName());
		List<DepartmentBean> deptLst = departmentDao.getDepartmentByConditions(
				departmentBean, flexiGridPageInfo);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			String parentDeptName = "";
			DepartmentBean dept = deptLst.get(i);
			DepartmentBean deptTemp = departmentDao.getById(dept
					.getParentDeptID());
			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				parentDeptName = deptTemp.getDeptName();
			}
			dept.setParentDeptName(parentDeptName);
			deptLstTemp.add(dept);
		}
		return deptLstTemp;
	}
	
	@Override
	public List<DepartmentBean> getDepartmentByConditions(DepartmentBean departmentBean) {
		if (departmentBean.getTreeType() != null
				&& !"".equals(departmentBean.getTreeType())
				&& !"-1".equals(departmentBean.getTreeType())) {
			String treeType = departmentBean.getTreeType();
			if (treeType.startsWith("O") == true) {
				treeType = treeType.substring(1);
				int orgID = Integer.parseInt(treeType);
				departmentBean.setOrganizationID(orgID);
			} else if (treeType.startsWith("D") == true) {
				treeType = treeType.substring(1);
				int deptID = Integer.parseInt(treeType);
				departmentBean.setDeptId(deptID);
			}
		}
		// System.out.println("部门ID："+departmentBean.getDeptId()+"======上级部门ID："+departmentBean.getParentDeptID()+"=========所属单位："+departmentBean.getOrganizationBean().getOrganizationID()+"/"+departmentBean.getOrganizationBean().getOrganizationName());
		List<DepartmentBean> deptLst = departmentDao.getDepartmentByConditions(departmentBean);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			String parentDeptName = "";
			DepartmentBean dept = deptLst.get(i);
			DepartmentBean deptTemp = departmentDao.getById(dept.getParentDeptID());
			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				parentDeptName = deptTemp.getDeptName();
			}
			dept.setParentDeptName(parentDeptName);
			deptLstTemp.add(dept);
		}
		return deptLstTemp;
	}

	/*
	 * 根据用户Id获得部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentByUserId(Integer id) {
		return departmentDao.getDepartmentByUserID(id);
	}

	/*
	 * 根据部门获得用户信息
	 */
	@Override
	public List<SysUserInfoBean> findSysUserInfoByDeptId(int deptId) {
		return departmentDao.findSysUserInfoByDeptId(deptId);
	}
	
	/*
	 * 根据部门id获取部门名称
	 * @see com.fable.insightview.platform.service.IDepartmentService#findDeptNameByDeptId(int)
	 */
	@Override
	public DepartmentBean findDeptNameByDeptId(int deptId) {
		return departmentDao.findDeptNameByDeptId(deptId);
	}

	/*
	 * 根君条件查询部门
	 */
	@Override
	public List<DepartmentBean> getDepartmentByConditions(String paramName,
			String paramValue) {
		return departmentDao.getDepartmentByConditions(paramName, paramValue);
	}

	/*
	 * 获得部门树数据信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentTreeVal() {
		return departmentDao.getDepartmentTreeVal();
	}
	
	/*
	 * 根据条件获得部门树数据信息
	 */
	@Override
	public List<DepartmentBean> getDepartmentTreeSelf(String deptId) {
		return departmentDao.getDepartmentTreeSelf(deptId);
	}

	/*
	 * 验证上级部门与所属组织是否匹配
	 */
	@Override
	public boolean getDeptByOrgIDAndParentDeptID(DepartmentBean departmentBean) {
		int count = departmentDao.getDeptBYOrgIDAndParentDeptID(departmentBean);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 验证部门名称唯一性
	 */
	@Override
	public boolean checkDeptName(DepartmentBean departmentBean) {
		return departmentDao.checkDeptName(departmentBean);
	}

	/*
	 * 获得单位的部门id
	 */
	@Override
	public List<Integer> getDeptIdsByOrdId(int orgId) {
		return departmentDao.getDeptIdsByOrdId(orgId);
	}

	/*
	 * 根据单位Id获得部门信息
	 */
	@Override
	public List<SysEmploymentBean> getEmploymentListByOrgID(
			DepartmentBean departmentBean) {
		List<SysEmploymentBean> empList = departmentDao
				.getEmploymentListByOrgID(departmentBean);
		for (int i = 0; i < empList.size(); i++) {
			String userName = sysUserDao.getUserNameByUserId(empList.get(i)
					.getUserId());
			empList.get(i).setUserName(userName);
		}
		return empList;
	}

	/*
	 * 根据部门名称获得部门信息
	 */
	@Override
	public DepartmentBean getDepartmentByDeptName(String deptName) {
		return departmentDao.getDepartmentByDeptName(deptName);
	}

	/*
	 * 验证部门是否可删除
	 */
	@Override
	public boolean checkBeforeDel(DepartmentBean departmentBean) {
		List<DepartmentBean> childsDepts = departmentDao
				.getDepartmentByParendID(departmentBean);
		List<SysEmploymentBean> employmentList = departmentDao
				.getSysEmploymentByDeptID(departmentBean);
		List<IPManSubNetRDeptBean> subnetRDeptLst = ipManSubNetRDeptMapper.getByDeptID(departmentBean.getDeptId());
		boolean flag = false;
		if ((childsDepts != null && childsDepts.size() > 0)
				|| (employmentList != null && employmentList.size() > 0)
				|| (subnetRDeptLst != null && subnetRDeptLst.size() > 0)) {
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

	@Override
	public List<DepartmentBean> getDepartmentTreeList() {
		return departmentDao.getDepartmentTreeList();
	}

	@Override
	public List<DepartmentBean> querySysDept(DepartmentBean departmentBean) {

		if (departmentBean.getTreeType() != null
				&& !"".equals(departmentBean.getTreeType())
				&& !"-1".equals(departmentBean.getTreeType())) {
			String treeType = departmentBean.getTreeType();
			if (treeType.startsWith("O") == true) {
				treeType = treeType.substring(1);
				int orgID = Integer.parseInt(treeType);
				departmentBean.setOrganizationID(orgID);
			} else if (treeType.startsWith("D") == true) {
				treeType = treeType.substring(1);
				int deptID = Integer.parseInt(treeType);
				departmentBean.setDeptId(deptID);
			}
		}
		List<DepartmentBean> deptLst = departmentDao
				.querySysDept(departmentBean);
		List<DepartmentBean> deptLstTemp = new ArrayList<DepartmentBean>();
		for (int i = 0; i < deptLst.size(); i++) {
			DepartmentBean dept = deptLst.get(i);
			DepartmentBean deptTemp = departmentDao.getById(dept
					.getParentDeptID());

			if (null == deptTemp) {
				dept.setParentDept(new DepartmentBean());
			} else {
				dept.setParentDept(deptTemp);
				dept.setParentDeptName(deptTemp.getDeptName());
			}
			deptLstTemp.add(dept);
		}
		return deptLstTemp;

	}

	@Override
	public boolean checkDeptCode(String flag, DepartmentBean departmentBean) {
		return departmentDao.checkDeptCode(flag, departmentBean);
	}

	@Override
	public List<DepartmentBean> getDepartmentByOrgID(int organizationId) {
		// 根据organizationId获得其所有的子单位组织
		List<Integer> allOrgIDList = organizationDao
				.getAllOrgId(organizationId);
		String organizationIds = organizationId + ",";
		for (int i = 0; i < allOrgIDList.size(); i++) {
			organizationIds += allOrgIDList.get(i) + ",";
		}
		if (organizationIds.length() > 0) {
			organizationIds = organizationIds.substring(0, organizationIds
					.length() - 1);
		}
		// genju8单位组织获得所有的部门
		List<DepartmentBean> deptLst = departmentDao
				.getDepartmentByOrgIDs(organizationIds);
		return deptLst;
	}

	@Override
	public List<DepartmentBean> getDepartmentByOrgId(Integer organizationId) {
		// TODO Auto-generated method stub
		return departmentDao.getDepartmentByOrgId(organizationId);
	}

	@Override
	public List<Integer> getDepartmentByUserId(String userId) {
		return departmentDao.getDepartmentByUserId(userId);
	}

	/************************* update zheng zhen start *************************/
	@Override
	public void onMessage(Message message) {
		try {
			if (null != message && message instanceof TextMessage){
				TextMessage textMessage = (TextMessage)message;
				if (null != textMessage){
					Map<String,Object> req = (Map<String,Object>)JsonUtil.jsonToBean(textMessage.getText(),Map.class);
					if (null == req || null == req.get("data")){
						insertSysLog(textMessage.getText(),"格式转换错误！");
						return ;
					}
					
					int op = (int)req.get("op");
					switch(op){
						case 1 :
							insertDeptSyn(req,textMessage.getText());
							break;
						case 2 :
							updateDeptSyn(req,textMessage.getText());
							break;
						case 3 :
							deleteDeptSyn(req,textMessage.getText());
						    break;
						default :
						   break;
					}
				}
			}
				
		} catch (Exception e) {
			logger.error("部门同步异常:" + e.getMessage());
		}
		
	}
	
	
	private void insertDeptSyn(Map<String,Object> req,String message){
		
		Map<String,Object> deptMap= (Map<String,Object>)req.get("data");
		Map<String,Object> parentDeptMap= (Map<String,Object>)deptMap.get("parent");
		Map<String,Object> orgMap= (Map<String,Object>)deptMap.get("org");
		String parentDeptCode = (String)parentDeptMap.get("code");
		Integer parentDeptId = 0;
		
		//监测当前部门是否存在
		OrganizationBean dept = organizationDao.getOrganizationByCode((String)deptMap.get("code"));
		if (null != dept){
			insertSysLog(message,"该部门已存在！");
			return ;
		}
		
		if (null != parentDeptCode && !"".equals(parentDeptCode)){
			DepartmentBean parentDept = departmentDao.getDepartmentByDeptCode(parentDeptCode);
			if (null == parentDept){
				insertSysLog(message,"上级部门【" + (String)parentDeptMap.get("name")+ "】不存在！");
				return ;
			}
			parentDeptId = parentDept.getDeptId();
		}
		
		//查询所属机构
		OrganizationBean org = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		//转换实体bean
		DepartmentBean departmentBean = new DepartmentBean();
		departmentBean.setDeptName((String)deptMap.get("name"));
		departmentBean.setDeptCode((String)deptMap.get("code"));
		departmentBean.setParentDeptID(parentDeptId);
		departmentBean.setDescr((String)deptMap.get("description"));
		departmentBean.setOrganizationBean(org);
		departmentBean.setHeadOfDept(0);
	    boolean bool = departmentDao.addDepartment(departmentBean);
	    if (!bool){
		    insertSysLog(message,"插入数据失败！");
	    }else{
	    	 insertSysLog(message,"插入数据成功！");
	    }
	}
	
	
	private void updateDeptSyn(Map<String,Object> req,String message){
		
		Map<String,Object> deptMap= (Map<String,Object>)req.get("data");
		Map<String,Object> parentMap= (Map<String,Object>)deptMap.get("parent");
		Map<String,Object> orgMap= (Map<String,Object>)deptMap.get("org");
		String parentDeptCode = (String)parentMap.get("code");
		Integer parentDeptId = 0;
		if (null != parentDeptCode && !"".equals(parentDeptCode)){
			DepartmentBean parentDept = departmentDao.getDepartmentByDeptCode(parentDeptCode);
			if (null == parentDept){
				insertSysLog(message,"上级部门【" + (String)parentMap.get("name") + "】不存在！");
				return ;
			}
			parentDeptId = parentDept.getDeptId();
		}
		
		//查询所属机构
		OrganizationBean org = organizationDao.getOrganizationByCode((String)orgMap.get("code"));
		
		DepartmentBean updateDeptBean = departmentDao.getDepartmentByDeptCode((String)deptMap.get("originalCode"));
		if (null == updateDeptBean){
			insertSysLog(message,"没有找到修改的数据！");
			return ;
		}
		updateDeptBean.setDeptName((String)deptMap.get("name"));
		updateDeptBean.setDeptCode((String)deptMap.get("code"));
		updateDeptBean.setParentDeptID(parentDeptId);
		updateDeptBean.setDescr((String)deptMap.get("description"));
		updateDeptBean.setOrganizationBean(org);
		updateDeptBean.setHeadOfDept(0);
		boolean bool = departmentDao.updateDepartmentBean(updateDeptBean);
		if (!bool){
		  insertSysLog(message,"更新数据失败！");
		}else{
		  insertSysLog(message,"更新数据成功！");
		}
	}
	
	private void deleteDeptSyn(Map<String,Object> req,String message){
		List<Map<String,Object>> orgList = (List<Map<String,Object>>)req.get("data");
		for (Map<String,Object> map : orgList){
			DepartmentBean departmentBean = departmentDao.getDepartmentByDeptCode((String)map.get("code"));
			if (null == departmentBean){
				insertSysLog(message,"没有找到删除的数据！");
				continue;
			}
			boolean bool = departmentDao.delDepartmentById(departmentBean);
			if (!bool){
				insertSysLog(message,"删除失败！");
			}else{
				insertSysLog(message,"删除成功！");
			}
		}
	}
	
	
	private void insertSysLog(String context,String result){
		SysLog sysLog = new SysLog();
		sysLog.setModule("部门管理");
		sysLog.setActor("admin");
		sysLog.setContent(context);
		sysLog.setResult(result);
		sysLog.setOperation("数据同步");
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sysLog.setOptionTime(sdf.format(new Date())); //操作时间
		sysLogService.insertSysLog(sysLog);
	}
	
	
	/************************* update zheng zhen end *************************/

	@Override
	public int getOrgByUserId(Integer userId) {
		
		return departmentDao.getOrgByUserId(userId);
	}

	@Override
	public List<OrgDeptProviderTreeBean> findOrgAndDept() {
		List<OrgDeptProviderTreeBean> TreeMenuList = new ArrayList<OrgDeptProviderTreeBean>();
		List<OrganizationBean> orgList = organizationDao.getOrganizationTreeVal();
		for (int i = 0; i < orgList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("O"+orgList.get(i).getOrganizationID());
			odp.setParentId("0");
			odp.setName(orgList.get(i).getOrganizationName());
			TreeMenuList.add(odp);
		}
		List<DepartmentBean> deptList = this.getDepartmentTreeVal();
		for (int i = 0; i < deptList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("D"+deptList.get(i).getDeptId());
			odp.setName(deptList.get(i).getDeptName());
			if(deptList.get(i).getParentDeptID() == 0){
				odp.setParentId("O"+deptList.get(i).getOrganizationBean().getOrganizationID());
			}else{
				odp.setParentId("D"+deptList.get(i).getParentDeptID());
			}
			TreeMenuList.add(odp);
		}
		return TreeMenuList;
	}
	
	@Override
	public List<Integer> getExistIds(boolean isOrg) {
		return departmentDao.getExistIds(isOrg);
	}
	
	/*
	 * 根据条件查询部门
	 */
	@Override
	public List<DepartmentBean> getDepartments(String paramName,
			String paramValue) {
		return departmentDao.getDepartments(paramName, paramValue);
	}

//	public void batchSyncDepts(List<DepartmentProvBean> depts) {
//		departmentProvDao.batchSave(depts);
//	}
	
	@Override
	public List<DepartmentBean> queryDepartTree(Map<String, Object> hashmap) {
		return departmentMapper.queryDepartTree(hashmap);
	}

	@Override
	public List<DepartmentBean> queryDepartByOrgID(Map<String, Object> hashmap) {
		return departmentMapper.queryDepartByOrgID(hashmap);
	}

	@Override
	public DepartmentBean queryDepartByDepName(String name) {
		return departmentMapper.queryDepartByDepName(name);
	}

}
