package com.fable.insightview.permission.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.permission.form.SysUserForm;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.service.AdapterService;
import com.fable.insightview.platform.common.util.CommonUtil;
import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.common.util.KeyValPair;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.dao.ISysUserDao;
import com.fable.insightview.platform.entity.DepartmentBean;
import com.fable.insightview.platform.entity.EmploymentGradeBean;
import com.fable.insightview.platform.entity.OrgDeptProviderTreeBean;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.entity.SysEmploymentBean;
import com.fable.insightview.platform.entity.SysMailServerConfigBean;
import com.fable.insightview.platform.entity.SysProviderUserBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.mailtools.MailUtil;
import com.fable.insightview.platform.mybatis.entity.STPostManage;
import com.fable.insightview.platform.provider.entity.ProviderInfoBean;
import com.fable.insightview.platform.provider.service.IProviderService;
import com.fable.insightview.platform.service.IDepartmentService;
import com.fable.insightview.platform.service.IEmploymentGradeService;
import com.fable.insightview.platform.service.IOrganizationService;
import com.fable.insightview.platform.service.ISysEmploymentService;
import com.fable.insightview.platform.service.ISysMailServerService;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.service.PostManageService;

/**
 * 用户管理
 * 
 * @author caoj
 * 
 */
@Controller
@RequestMapping("/permissionSysUser")
public class SysUserController {
	@Autowired
	PostManageService postManageService;
	@Autowired
	ISysUserService sysUserService;
	@Autowired
	IOrganizationService organizationService;
	@Autowired
	IDepartmentService departmentService;
	@Autowired
	IEmploymentGradeService employmentGradeService;
	@Autowired
	ISysEmploymentService sysEmploymentService;
	@Autowired
	IProviderService providerService;
	@Autowired
	ISysMailServerService sysMailServerService;
	@Autowired
	ISysUserDao sysUserDao;
	@Autowired
	private HttpServletRequest request;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final Logger logger = LoggerFactory
			.getLogger(SysUserController.class);
	private Timestamp tempTime;

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping("/toSysUserList")
	public ModelAndView toSysUserList(String navigationBar) {
		ModelAndView mv = new ModelAndView("permission/sysuser_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	/**
	 * 根据用户名检查用户是否存在
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkSysUser")
	@ResponseBody
	public boolean checkSysUser(SysUserInfoBean sysUserBean) {
		logger.info("检查用户名是否存在");
		try {
			List<SysUserInfoBean> userLst = sysUserService
					.getSysUserByConditions("userAccount", sysUserBean
							.getUserAccount());
			// int
			// i=sysUserService.getOrganization(sysUserBean.getUserAccount());
			if ((null == userLst || userLst.size() <= 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error("检查用户名是否存在异常！", e.getMessage());
			return false;
		}

	}

	/**
	 * 根据userId获取用户信息
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSysUser")
	@ResponseBody
	public SysUserInfoBean findSysUser(SysUserInfoBean sysUserBean)
			throws Exception {
		logger.info("获取用户ID = " + sysUserBean.getUserID() + " 的用户信息");
		List<SysUserInfoBean> userLst = sysUserService.getSysUserByConditions(
				"userID", sysUserBean.getUserID() + "");
		SysUserInfoBean sysUserBeanTemp = userLst.get(0);
		tempTime = sysUserBeanTemp.getCreateTime();
		sysUserBeanTemp.setUserPassword(CryptoUtil.Decrypt(sysUserBeanTemp
				.getUserPassword()));
		List<SysEmploymentBean> sysEmpLst = sysEmploymentService
				.getEmploymentByUserId(sysUserBean.getUserID());
		if (sysEmpLst.size() != 0) {
			SysEmploymentBean empBean = sysEmpLst.get(0);
			sysUserBeanTemp.setSysEmploymentBean(empBean);

			sysUserBeanTemp.setEmployeeCode(empBean.getEmployeeCode());
			sysUserBeanTemp.setDeptId(empBean.getDeptID()==null ? 0 : empBean.getDeptID());
			if (empBean.getGradeID() != null) {
				sysUserBeanTemp.setGradeId(empBean.getGradeID());
				List<EmploymentGradeBean> grades = employmentGradeService
						.getGradeName(sysUserBeanTemp.getSysEmploymentBean().getGradeID());
				if (!CollectionUtils.isEmpty(grades)){
					sysUserBeanTemp.setGradeName(grades.get(0).getGradeName());
				}
			}
			sysUserBeanTemp.setEmpId(empBean.getEmpId());
			if (empBean.getDeptID()!=null){
			DepartmentBean deptBean = (DepartmentBean) departmentService
					.getDepartmentBeanByConditions(
							"DeptID",
							sysUserBeanTemp.getSysEmploymentBean().getDeptID()
									+ "").get(0);
			sysUserBeanTemp.setDeptName(deptBean.getDeptName());
			}

		}
		List<SysProviderUserBean> proUserLst = providerService
				.getProUserByUserId(sysUserBean.getUserID());
		if (proUserLst.size() != 0) {
			SysProviderUserBean proUserBean = proUserLst.get(0);
			sysUserBeanTemp.setProviderId(proUserBean.getProviderId());
			String providerName = providerService
					.getProviderInfoBeanByConditions("ProviderID",
							proUserBean.getProviderId() + "").getProviderName();
			sysUserBeanTemp.setProviderName(providerName);
			sysUserBeanTemp.setProUserId(proUserBean.getId());
		}
		return sysUserBeanTemp;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSysUser")
	@ResponseBody
	public boolean updateSysUser(SysUserInfoBean sysUserBean, String strPostList) throws Exception {
		logger.info("根据用户类型和用户ID修改用户信息");
		logger.info("用户ID = " + sysUserBean.getUserID() + "用户类型："
				+ sysUserBean.getUserType());
		boolean updateEmp = false;
		boolean updatePro = false;
		sysUserBean.setCreateTime(tempTime);
		
		//UserType：0:管理员,1企业内IT部门用户,2:企业业务部门用户,3:外部供应商用户
		if (sysUserBean.getUserType() == 3) {
			
			//根据用户ID查询出供应商登录用户SysProviderUser
			List<SysProviderUserBean> proUserLst = providerService
					.getProUserByUserId(sysUserBean.getUserID());
			
			//如果存在供应商就执行插入，否则执行更行
			if (proUserLst.size() == 0) {
				updatePro = providerService.addProviderUser(sysUserBean
						.getSysProviderUserBean());
			} else {
				updatePro = providerService.updateProUserInfo(sysUserBean
						.getSysProviderUserBean());
			}
			
			//判断用户表是否存在当前的供应商用户
			List<SysEmploymentBean> empBeanLst = sysEmploymentService
					.getEmploymentByUserId(sysUserBean.getUserID());
			
			if (empBeanLst.size() > 0) {
				sysEmploymentService.delSysEmpByUserId(sysUserBean.getUserID());
			}
			
		} else {
			
			
			//查询员工表是否存在当前的用户ID
			List<SysEmploymentBean> empBeanLst = sysEmploymentService
					.getEmploymentByUserId(sysUserBean.getUserID());
			
			//如果存在就更新，获取就插入
			if (empBeanLst.size() == 0) {
				updateEmp = sysEmploymentService.addSysEmp(sysUserBean
						.getSysEmploymentBean());
			} else {
				updateEmp = sysEmploymentService.updateSysEmp(sysUserBean
						.getSysEmploymentBean());
			}
			
			//检查供应商是否存在当前的用户
			List<SysProviderUserBean> proUserLst = providerService
					.getProUserByUserId(sysUserBean.getUserID());
			
			//如果存在将其删除
			if (proUserLst.size() > 0) {
				providerService.deleteProUserByUserId(sysUserBean.getUserID());
			}
			
			updateEmp = sysEmploymentService.updateSysEmp(sysUserBean
					.getSysEmploymentBean());
			
		}
		
		sysUserBean.setLastModifyTime(new Timestamp(new Date().getTime()));
		
		//更新用户信息
		boolean updateUser = sysUserService.updateSysUser(sysUserBean);
		
		if ((updateEmp == true && updateUser == true)
				|| (updatePro == true && updateUser == true)) {
			
			//删除岗位信息STPostUserManage
			postManageService.deleteAllPost(sysUserBean.getUserID());
			
			if(!strPostList.equals("")) {
				String[] posts = strPostList.split(",");
				for(int i = 0; i < posts.length; i++) {
					postManageService.insertPostUserManage(sysUserBean.getUserID(), Integer.parseInt(posts[i]));
				}
			}
			logger.info("用户信息更新成功");
			return true;
		} else {
			logger.info("用户信息更新失败");
			return false;
		}

	}

	/**
	 * 新增用户
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addSysUser")
	@ResponseBody
	public boolean addSysUser(SysUserInfoBean sysUserBean, String strPostList) throws Exception {
		sysUserService.addSysUser(sysUserBean);
		postManageService.deleteAllPost(sysUserBean.getUserID());
		
		if(!strPostList.equals("")) {
			String[] posts = strPostList.split(",");
			for(int i = 0; i < posts.length; i++) {
				postManageService.insertPostUserManage(sysUserBean.getUserID(), Integer.parseInt(posts[i]));
			}
		}
		return true;
	}

	/**
	 * 删除用户
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSysUser")
	@ResponseBody
	public Map<String, Object> delSysUser(SysUserForm sysUserForm) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUserInfoBean sysUserInfoBean = new SysUserInfoBean();
		sysUserInfoBean.setUserID(sysUserForm.getUserId());
		
		//查询用户的信息
		SysUserInfoBean syUBean = sysUserService.findSysUserById(sysUserForm
				.getUserId());
		
		Map<String, AdapterService> map = CommonUtil
				.getOtherSysyemServiceImpl(AdapterService.class);
		
		if (map != null && !map.isEmpty()) {
			for (AdapterService adapter : map.values()) {
				
				//判断用户是否已分配任务
				if (adapter.isHasTask(syUBean.getUserAccount())) {
					result.put("result", SystemFinalValue.DELHASWORKUSER);
				} else {
					
					//删除用户和员工表
					boolean delJundgement = sysUserService
							.delSysUserById(sysUserInfoBean);
					
					if (delJundgement) {
						postManageService.deleteAllPost(sysUserForm.getUserId());
						result.put("result", SystemFinalValue.DELSUCCESSUSER);
					} else {
						result.put("result", SystemFinalValue.DELFAILUREUSER);
					}
				}
			}
		} else {
			
			//直接删除用户
			boolean delJundgement = sysUserService
					.delSysUserById(sysUserInfoBean);
			
			if (delJundgement) {
				postManageService.deleteAllPost(sysUserForm.getUserId());
				result.put("result", SystemFinalValue.DELSUCCESSUSER);
			} else {
				result.put("result", SystemFinalValue.DELFAILUREUSER);
			}
		}
		return result;
	}

	/**
	 * 用户列表
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSysUserGroup")
	@ResponseBody
	public Map<String, Object> listSysUserGroup(SysUserInfoBean sysUserBean)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		if (!"".equals(request.getParameter("organizationID"))
				&& request.getParameter("organizationID") != null) {
			int organizationID = Integer.parseInt(request
					.getParameter("organizationID"));
			List<Integer> userIdList = sysEmploymentService
					.getUserIdByOrgId(organizationID);
			StringBuffer sbuff = new StringBuffer();
			for (int i = 0; i < userIdList.size(); i++) {
				sbuff.append(userIdList.get(i)).append(",");
			}
			if (sbuff.length() > 0) {
				String userIds = sbuff.substring(0, sbuff.lastIndexOf(","))
						.toString();
				sysUserBean.setUserIds(userIds);
			} else {
				sysUserBean.setUserIds("-1");
			}
		}
		List<SysUserInfoBean> userLst = sysUserService.getSysUserByGroup(
				sysUserBean, flexiGridPageInfo);
		// 获取总记录数
		int total = sysUserService.getTotalCountByGroup(sysUserBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", userLst);

		return result;
	}

	/**
	 * 用户列表
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listSysUser")
	@ResponseBody
	public Map<String, Object> listSysUser(SysUserInfoBean sysUserBean)
			throws Exception {
		logger.info("获取用户列表数据");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		List<SysUserInfoBean> userLst = sysUserService.getSysUserByConditions(
				sysUserBean, flexiGridPageInfo);
		// 获取总记录数
		int total = sysUserService.getTotalCountByGroup(sysUserBean);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", userLst);
		logger.info("列表数据获取结束");
		return result;
	}

	/**
	 * 很据部门获取用户信息
	 * 
	 * @param departmentBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSysUserByDept")
	@ResponseBody
	public List<SysUserInfoBean> listSysUser(DepartmentBean departmentBean)
			throws Exception {
		List<SysUserInfoBean> userLst = sysUserService
				.getSysUserByDept(departmentBean);
		return userLst;
	}

	/**
	 * 根据Id获取用户
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSysUserById")
	@ResponseBody
	public SysUserInfoBean findSysUser(String id) throws Exception {
		SysUserInfoBean sysUserInfoBean = new SysUserInfoBean();
		List<SysUserInfoBean> userLst = sysUserService.getSysUserByConditions(
				"userID", id);
		if(null != userLst && !userLst.isEmpty()){
			return userLst.get(0);
		}
		
		return sysUserInfoBean;

	}

	/**
	 * 获取所有用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAllSysUsers")
	@ResponseBody
	public List<KeyValPair<String, String>> listAllSysUsers() throws Exception {
		List<KeyValPair<String, String>> pairs = new ArrayList<KeyValPair<String, String>>();

		List<SysUserInfoBean> items = sysUserService.getAllSysUsers();
		for (int i = 0; i < items.size(); i++) {
			pairs.add(new KeyValPair(items.get(i).getUserID(), items.get(i)
					.getUserName()));
		}
		return pairs;
	}

	@RequestMapping("/queryUserByAuto")
	@ResponseBody
	public Map<String, Object> queryUserByAuto(SysUserInfoBean sysUserBean) {
		List<SysUserInfoBean> userList = sysUserService
				.queryUserByAuto(sysUserBean);
		String userListJson = JsonUtil.toString(userList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("userListJson", userListJson);
		return result;
	}

	/**
	 * 加载组织部门供应商树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findOrgAndProvideTreeVal")
	@ResponseBody
	public Map<String, Object> findOrgAndProvideTreeVal() throws Exception {
		logger.info("获取组织部门供应商树信息");
		List<OrgDeptProviderTreeBean> menuLst = new ArrayList<OrgDeptProviderTreeBean>();
		OrgDeptProviderTreeBean odpBean = null;
		List<OrganizationBean> orgLst = organizationService
				.getOrganizationTreeVal();

		for (int i = 0; i < orgLst.size(); i++) {

				odpBean = new OrgDeptProviderTreeBean();
				odpBean.setId("O" + orgLst.get(i).getOrganizationID());
				odpBean.setParentId("0");
				odpBean.setName(orgLst.get(i).getOrganizationName());
				menuLst.add(odpBean);
			
		}
		List<DepartmentBean> deptLst = departmentService.getDepartmentTreeVal();
		for (int i = 0; i < deptLst.size(); i++) {
			odpBean = new OrgDeptProviderTreeBean();
			DepartmentBean deptBean = deptLst.get(i);

					if (deptBean.getParentDeptID() == 0) {
						odpBean.setId("D" + deptBean.getDeptId());
						odpBean.setParentId("O"
								+ deptBean.getOrganizationBean()
										.getOrganizationID());
						odpBean.setName(deptBean.getDeptName());
					} else {
						odpBean.setId("D" + deptBean.getDeptId());
						odpBean.setParentId("D" + deptBean.getParentDeptID());
						odpBean.setName(deptBean.getDeptName());
					}
					menuLst.add(odpBean);

		}
		List<ProviderInfoBean> providerLst = providerService
				.findProvideTreeVal();
		odpBean = new OrgDeptProviderTreeBean();
		odpBean.setId("1000");
		odpBean.setParentId("-1");
		odpBean.setName("供应商");
		menuLst.add(odpBean);
		for (int i = 0; i < providerLst.size(); i++) {
			odpBean = new OrgDeptProviderTreeBean();
			ProviderInfoBean proBean = providerLst.get(i);

				odpBean.setId("P" + proBean.getProviderId());
				odpBean.setParentId("1000");
				odpBean.setName(proBean.getProviderName());
				menuLst.add(odpBean);
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		logger.info("获取组织部门供应商树信息over");
		return result;
	}
	
	/**
	 * 初始化 组织部门供应商 树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initProviderTree")
	@ResponseBody
	public String orgAndProviderTree() throws Exception {
		logger.info("获取组织部门供应商树信息");
		List<Map<String,String>> listMaps = new ArrayList<Map<String,String>>();
		List<OrgDeptProviderTreeBean> treeMenuList = new ArrayList<OrgDeptProviderTreeBean>();
		List<OrgDeptProviderTreeBean> providerMenu = new ArrayList<OrgDeptProviderTreeBean>();
		OrgDeptProviderTreeBean odpBean = null;
		//查询 “组织单位” 所有根节点
        List<OrganizationBean> organizationLst = organizationService.queryOrgAll();
		
		for(OrganizationBean org : organizationLst){
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			
			odp.setId("O"+org.getOrganizationID());
			odp.setParentId("O"+org.getParentOrgID());
			odp.setName(org.getOrganizationName());
			odp.setCount(org.getCount());
			odp.setOrg(true);
			
			treeMenuList.add(odp);
		}
		
		if(treeMenuList.size() > 0){
			for(OrgDeptProviderTreeBean orgDeptP : treeMenuList){
				Map<String,String> node = new HashMap<String,String>();
				String deptId = orgDeptP.getId();
				node.put("id", deptId);
				node.put("text", orgDeptP.getName());
				node.put("checked", "true");
				node.put("isOrg", "true");
				if(orgDeptP.getCount() > 0){
					node.put("state", "closed");
				}else{
					node.put("state", "open");
				}
				listMaps.add(node);
			}
		}
		
		//查询所有 “供应商”
		List<ProviderInfoBean> providerLst = providerService.findProvideTreeVal();
		for (int i = 0; i < providerLst.size(); i++) {
			odpBean = new OrgDeptProviderTreeBean();
			ProviderInfoBean proBean = providerLst.get(i);

				odpBean.setId("P" + proBean.getProviderId());
				odpBean.setParentId("1000");
				odpBean.setName(proBean.getProviderName());
				providerMenu.add(odpBean);
		}
		//将  “供应商” 根节点 添加到map
		if(providerMenu.size() > 0){
			for(OrgDeptProviderTreeBean orgDeptP : providerMenu){
				Map<String,String> node3 = new HashMap<String,String>();
				node3.put("id", orgDeptP.getId());
				node3.put("text", orgDeptP.getName());
				node3.put("checked", "true");
				node3.put("isOrg", "false");
				node3.put("state", "open");
				
				listMaps.add(node3);
			}
		}
		
		logger.info("获取组织部门供应商树信息over");
		return JsonUtil.listMap2Json(listMaps);
	}

	//
	@RequestMapping("/userTreeList")
	@ResponseBody
	public String orgAndProviderList(String orgID,String deptID) throws Exception {
		logger.info("获取组织部门供应商树信息");
		List<Map<String,String>> listMaps = new ArrayList<Map<String,String>>();
		List<OrgDeptProviderTreeBean> orgMenu = new ArrayList<OrgDeptProviderTreeBean>();
		List<OrgDeptProviderTreeBean> deptMenu = new ArrayList<OrgDeptProviderTreeBean>();
		List<OrgDeptProviderTreeBean> providerMenu = new ArrayList<OrgDeptProviderTreeBean>();
		OrgDeptProviderTreeBean odpBean = null;
		logger.info("获取节点数据");
		List<OrgDeptProviderTreeBean> treeMenuList = new ArrayList<OrgDeptProviderTreeBean>();
		List<OrgDeptProviderTreeBean> treeMenuList2 = new ArrayList<OrgDeptProviderTreeBean>();
		
		Map<String,Object> hashmap = new HashMap<String,Object>();
		//查询所有部门子节点
		if(deptID != null){
			hashmap.put("deptID", deptID);
			List<DepartmentBean> deptList = departmentService.queryDepartTree(hashmap);
			if(deptList.size() >0){
				for(DepartmentBean dept : deptList){
					
					OrgDeptProviderTreeBean orgDept = new OrgDeptProviderTreeBean();
					
					orgDept.setId("D"+dept.getDeptId());
					orgDept.setName(dept.getDeptName());
					orgDept.setCount(dept.getCount());
					orgDept.setOrg(false);
					if(dept.getParentDeptID() == 0){
						orgDept.setParentId("O"+dept.getOrganizationID());
					}else{
						orgDept.setParentId("D"+dept.getParentDeptID());
					}

					treeMenuList2.add(orgDept);
				}
			}

			//部门列表添加到listMaps
			if(treeMenuList2.size() > 0){
				for(OrgDeptProviderTreeBean orgDeptP : treeMenuList2){
					Map<String,String> node2 = new HashMap<String,String>();
					String deptId = orgDeptP.getId();
					node2.put("id", deptId);
					node2.put("text", orgDeptP.getName());
					node2.put("checked", "true");
				    node2.put("isOrg", "false");
					
					if(orgDeptP.getCount() > 0){
						node2.put("state", "closed");
					}else{
						node2.put("state", "open");
					}
					listMaps.add(node2);
				}
			}
			
		}else{
			logger.info("deptID为null无需获取部门数据");
		}
		
		if(orgID != null){
			
			Map<String,Object> hashmap2 = new HashMap<String,Object>();
			hashmap2.put("orgID", orgID);
			List<DepartmentBean> deptList2 = departmentService.queryDepartByOrgID(hashmap2);

			for(DepartmentBean dept2 : deptList2){
				OrgDeptProviderTreeBean orgDeptBean = new OrgDeptProviderTreeBean();
				
				orgDeptBean.setId("D"+dept2.getDeptId());
				orgDeptBean.setName(dept2.getDeptName());
				orgDeptBean.setCount(dept2.getCount());
				orgDeptBean.setOrg(false);
				if(dept2.getParentDeptID() == 0){
					orgDeptBean.setParentId("O"+dept2.getOrganizationID());
				}else{
					orgDeptBean.setParentId("D"+dept2.getParentDeptID());
				}
				
				treeMenuList.add(orgDeptBean);
			}
			//单位列表添加到listMaps
			if(treeMenuList.size() > 0){
				for(OrgDeptProviderTreeBean orgDeptP : treeMenuList){
					Map<String,String> node = new HashMap<String,String>();
					String deptId = orgDeptP.getId();
					node.put("id", deptId);
					node.put("text", orgDeptP.getName());
					node.put("checked", "true");
					node.put("isOrg", "false");
				
					if(orgDeptP.getCount() > 0){
						node.put("state", "closed");
					}else{
						node.put("state", "open");
					}
					listMaps.add(node);
				}
			}
		}else{
			logger.info("orgID为null无需获取单位数据");
		}
		
		logger.info("获取组织部门供应商树信息over");
		return JsonUtil.listMap2Json(listMaps);
	}
	
	
	
	/**
	 * 锁定用户
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lockSysUser")
	@ResponseBody
	public boolean lockSysUser(SysUserInfoBean sysUserBean) throws Exception {
		return sysUserService.lockSysUser(sysUserBean);
	}

	/**
	 * 解锁用户
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/unLockSysUser")
	@ResponseBody
	public boolean unLockSysUser(SysUserInfoBean sysUserBean) throws Exception {
		return sysUserService.lockSysUser(sysUserBean);
	}

	/**
	 * 修改密码
	 * 
	 * @param sysUserBean
	 * @param sendEmail
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPwd")
	@ResponseBody
	public int modifyPwd(SysUserInfoBean sysUserBean, String sendEmail) {
		try {
			logger.info("修改密码");
			int result = 0;
			String email = sysUserService.getSysUserByConditions("UserID",
					sysUserBean.getUserID() + "").get(0).getEmail();
			logger.info("收件者邮箱：" + email);
			if (sendEmail == "on" || "on".equals(sendEmail)) {
				SysMailServerConfigBean mailServer = sysMailServerService
						.getMailServerConfigInfo().get(0);
				String from = mailServer.getSenderAccount();
				logger.info("发件者邮箱：" + from);
				String to = email;
				String title = "new password";
				String content = "您的新密码是：" + sysUserBean.getUserPassword();
				boolean modifyP = sysUserService.modifyPwd(sysUserBean);
				MailUtil rm = new MailUtil(from, to, title, content);
				rm.host = mailServer.getMailServer();
				rm.PORT = mailServer.getPort() + "";
				rm.username = mailServer.getUserName();
				rm.password = mailServer.getPassword();
				int isAuth = (int) mailServer.getIsAuth();
				boolean sendMail = rm.sendReport();
				if (modifyP == true && sendMail == true) {
					result = 1;
				} else {
					result = -1;
				}
			} else {
				boolean flag = sysUserService.modifyPwd(sysUserBean);
				if (flag == true) {
					result = 1;
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("数据库操作异常！", e);
			return -2;
		}

	}

	/**
	 * 加载部门树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDeptTreeVal")
	@ResponseBody
	public Map<String, Object> findDeptTreeVal() throws Exception {
		logger.info("获取部门树");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String organizationStr = request.getParameter("organizationId");
		int organizationId = -1;
		if (!"".equals(organizationStr) && organizationStr != null) {
			organizationId = Integer.parseInt(organizationStr);
		}
		List<DepartmentBean> menuLst = new ArrayList<DepartmentBean>();
		if (organizationId == -1) {
			menuLst = departmentService.getDepartmentTreeVal();
		} else {
			menuLst = departmentService.getDepartmentByOrgID(organizationId);
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		logger.info("获取部门树over");
		return result;
	}

	/**
	 * 根据部门Id获取部门树
	 * 
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDeptGradeTreeVal")
	@ResponseBody
	public Map<String, Object> findDeptGradeTreeVal(HttpServletRequest request)
			throws Exception {
		int deptId = Integer.parseInt(request.getParameter("deptId"));
		int organizationId = Integer.parseInt(request
				.getParameter("organizationId"));
		int orgId = -1;
		if (deptId != -1) {
			DepartmentBean deptBean = departmentService
					.getDepartmentById(deptId);
			orgId = deptBean.getOrganizationBean().getOrganizationID();
		} else {
			orgId = organizationId;
		}

		EmploymentGradeBean employmentGradeBean = new EmploymentGradeBean();
		employmentGradeBean.setOrganizationID(orgId);

		List<EmploymentGradeBean> menuLst = employmentGradeService
				.getEmploymentGradeByConditions(employmentGradeBean);
		// List<EmploymentGradeBean>
		// menuLst=employmentGradeService.getDepartmentGradeTreeVal(deptId);
		for (int i = 0; i < menuLst.size(); i++) {
			EmploymentGradeBean bean = menuLst.get(i);
		}
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	
	/**
	 * 根据条件查询用户
	 * 
	 * @param sysUserInfoBean
	 * @return
	 */
	@RequestMapping("/querySysUserInfo")
	@ResponseBody
	public List<SysUserInfoBean> querySysUserInfo(
			SysUserInfoBean sysUserInfoBean) {
		return sysUserService.queryUserByAuto(sysUserInfoBean);
	}
	
	/**
	 * 根据用户查找当前部门下所有人
	 * 
	 * @param sysUserInfoBean
	 * @return
	 */
	@RequestMapping("/querySysUserInfoByDept")
	@ResponseBody
	public List<SysUserInfoBean> querySysUserInfo1(
			SysUserInfoBean sysUserInfoBean,String gzUserId) {
		if(gzUserId != null && !"".equals(gzUserId)){
			sysUserInfoBean.setUserID(Integer.parseInt(gzUserId));
		}else{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
					.getSession().getAttribute("sysUserInfoBeanOfSession");
			if(sysUserInfoBeanTemp.getId().intValue() != 1){
				sysUserInfoBean.setUserID(sysUserInfoBeanTemp.getId().intValue());
			}else{
				return sysUserService.queryUserByDepartment(sysUserInfoBean);
			}
		}
		return sysUserService.queryUserByDepartment(sysUserInfoBean);
	}
	
	/**
	 * 根据部门Id查找当前部门下所有人
	 * 
	 * @param sysUserInfoBean
	 * @return
	 */
	@RequestMapping("/querySysUserInfoByDeptId")
	@ResponseBody
	public List<SysUserInfoBean> querySysUserInfoByDeptId(HttpServletRequest request) {
		DepartmentBean departmentBean = new DepartmentBean();
		String deptId = request.getParameter("DeptId");
		departmentBean.setDeptId(Integer.valueOf(deptId));
		List<SysUserInfoBean> userLst = sysUserService
				.getSysUserByDept(departmentBean);
		if (!userLst.isEmpty()) {
			return userLst;
		} else {
			return sysUserService.getAllSysUsers();
		}
	}
	
	/**
	 * 查询当前部门下包括当前部门下的子部门里的所有用户
	 * @param deptId
	 * @return
	 */
	@RequestMapping("findUsersWithinChildDept")
	@ResponseBody
	public List<SysUserInfoBean> findUsersWithinChildDept(Integer deptId) {
		DepartmentBean qryDept = new DepartmentBean();
		List<SysUserInfoBean> usrLst = null;
		qryDept.setDeptId(deptId);
		usrLst = this.sysUserService.findUsersWithinChildDept(qryDept);
		return usrLst;
	}

	/**
	 * 批量删除
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delSysUsers")
	@ResponseBody
	public Map<String, Object> delSysUsers(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		String userName = "";
		boolean isSystem = true;
		try {
			String userIds = request.getParameter("userIds");
			logger.info("批量删除用户ID = " + userIds);
			String[] splitIds = userIds.split(",");
			for (String strId : splitIds) {
				if (Integer.parseInt(strId) >= 10000) {
					SysUserInfoBean sysUserInfoBean = new SysUserInfoBean();
					sysUserInfoBean.setUserID(Integer.parseInt(strId));
					flag = sysUserService.delSysUserById(sysUserInfoBean);
					flag = true;
				} else {
					List<SysUserInfoBean> userLst = sysUserService
							.getSysUserByConditions("userID", strId);
					SysUserInfoBean sysUserBeanTemp = userLst.get(0);
					userName = userName + sysUserBeanTemp.getUserName() + ",";
					flag = true;
					isSystem = false;
				}
			}
		} catch (Exception e) {
			logger.error("批量删除失败！", e.getMessage());
			flag = false;
		}
		if (!"".equals(userName)) {
			userName = userName.substring(0, userName.lastIndexOf(","));
		}
		userName = "【 " + userName + "】";
		result.put("flag", flag);
		result.put("isSystem", isSystem);
		result.put("userName", userName);
		return result;
	}

	/**
	 * 查找节点ID
	 * 
	 * @param constantTypeCName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public SysUserInfoBean searchTreeNodes(String treeName) {
		try {
			return sysUserService.getTreeIdByTreeName(treeName);
		} catch (Exception e) {
			logger.error("查找节点异常！", e.getMessage());
			return null;
		}
	}

	/**
	 * 打开编辑页面
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowModify")
	@ResponseBody
	public ModelAndView toShowModify(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String isAdmin = request.getParameter("isAdmin");
		String isSameOrg = request.getParameter("isSameOrg");
		String currentUserOrgId = request.getParameter("currentUserOrgId");

		request.setAttribute("userId", userId);
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isSameOrg", isSameOrg);
		request.setAttribute("currentUserOrgId", currentUserOrgId);
		return new ModelAndView("permission/sysuser_modify");
	}

	/**
	 * 打开详情页面
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowDetail")
	@ResponseBody
	public ModelAndView toShowDetail(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		return new ModelAndView("permission/sysuser_detail");
	}

	/**
	 * 打开锁定页面
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowLock")
	@ResponseBody
	public ModelAndView toShowLock(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		return new ModelAndView("permission/sysuser_lock");
	}

	/**
	 * 打开修改密码页面
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowModifyPwd")
	@ResponseBody
	public ModelAndView toShowModifyPwd(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		request.setAttribute("userId", userId);
		return new ModelAndView("permission/sysuser_modifypwd");
	}

	/**
	 * 打开新增页面
	 * 
	 * @param sysUserForm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toShowAdd")
	@ResponseBody
	public ModelAndView toShowAdd(HttpServletRequest request) {
		String isAdmin = request.getParameter("isAdmin");
		String organizationId = request.getParameter("organizationId");
		String deptId = request.getParameter("deptId");
		String providerId = request.getParameter("providerId");
		String userType = request.getParameter("userType");
		ProviderInfoBean provider = providerService.getById(Integer.parseInt(providerId));
		if(null != provider){
			request.setAttribute("providerName", provider.getProviderName());
		}else{
			request.setAttribute("providerName", "");
		}
		DepartmentBean dept = departmentService.getDepartmentById(Integer.parseInt(deptId));
		if(null != dept){
			request.setAttribute("deptName", dept.getDeptName());
		}else{
			request.setAttribute("deptName", "");
		}
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("organizationId", organizationId);
		request.setAttribute("deptId", deptId);
		request.setAttribute("providerId", providerId);
		request.setAttribute("userType", userType);
		return new ModelAndView("permission/sysuser_add");
	}

	/**
	 * 修改时根据用户名检查用户是否存在
	 * 
	 * @param sysUserBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkSysUserModify")
	@ResponseBody
	public boolean checkSysUserModify(SysUserInfoBean sysUserBean) {
		logger.info("检查用户名是否存在");
		try {
			List<SysUserInfoBean> userLst = sysUserService
					.getSysUserByConditions(sysUserBean);
			if (null == userLst || userLst.size() <= 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error("检查用户名是否存在异常！", e.getMessage());
			return false;
		}

	}

	@RequestMapping("/doBeforeAdd")
	@ResponseBody
	public Map<String, Object> doBeforeAdd(Integer deptId, Integer providerId, Integer userType) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");

		// 判断当前用户是否为admin用户
		boolean isAdmin = true;
		String currentUser = sysUserInfoBeanTemp.getUserAccount();
		long currentUserId = sysUserInfoBeanTemp.getId();
		// 所属的单位id
		int organizationId = -1;
		if ("admin".equals(currentUser)) {
			isAdmin = true;
		} else {
			isAdmin = false;
		}
		boolean authority = false;
		List<Integer> deptIdList = new ArrayList<Integer>();
		if(deptId == -1){
			authority = true;
		}else{
			deptIdList = departmentService.getDepartmentByUserId(String.valueOf(sysUserInfoBeanTemp.getId()));
		}
		// 如果不是admin用户，获得该用户的所属单位
		if (isAdmin == false) {
			List<SysEmploymentBean> empLst = sysEmploymentService
					.getEmploymentByUserId((int) currentUserId);
			if (empLst.size() > 0) {
				organizationId = empLst.get(0).getOrganizationID();
			}
			for (int i = 0; i < deptIdList.size(); i++) {
				if(deptId.equals(deptIdList.get(i))){
					authority = true;
					break;
				}
			}
		}else{
			authority = true;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdmin", isAdmin);
		result.put("organizationId", organizationId);
		result.put("authority", authority);
		result.put("deptId", deptId);
		result.put("providerId", providerId);
		result.put("userType", userType);
		return result;
	}

	@RequestMapping("/doBeforeUpdate")
	@ResponseBody
	public Map<String, Object> doBeforeUpdate() {
		boolean isSameOrg = false;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		long currentUserId = sysUserInfoBeanTemp.getId();
		// 判断当前用户是否为admin用户
		boolean isAdmin = true;
		String currentUser = sysUserInfoBeanTemp.getUserAccount();
		if ("admin".equals(currentUser)) {
			isAdmin = true;
		} else {
			isAdmin = false;
		}

		// 所编辑的用户所属单位组织
		int updateUserOrgId = -1;
		String updateUserAccount = request.getParameter("userAccount");
		int userId = Integer.parseInt(request.getParameter("userId"));
		List<SysEmploymentBean> empLst2 = sysEmploymentService
				.getEmploymentByUserId(userId);
		if (empLst2.size() > 0) {
			updateUserOrgId = empLst2.get(0).getOrganizationID();
		}

		// 当前登录的用户所属的单位组织
		int currentUserOrgId = -1;
		if (isAdmin == false) {
			List<SysEmploymentBean> empLst = sysEmploymentService
					.getEmploymentByUserId((int) currentUserId);
			if (empLst.size() > 0) {
				currentUserOrgId = empLst.get(0).getOrganizationID();
			}
			if (currentUserOrgId == updateUserOrgId) {
				isSameOrg = true;
			}
		} else if ("admin".equals(updateUserAccount)) {
			isSameOrg = true;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdmin", isAdmin);
		result.put("isSameOrg", isSameOrg);
		result.put("currentUserOrgId", currentUserOrgId);
		return result;
	}
	
	@RequestMapping("/queryAllPost")
	@ResponseBody
	public Map<String, Object> queryAllPost(Integer deptId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<STPostManage> allPostList = postManageService.queryAllPost(deptId);
		result.put("allPostList", allPostList);
		return result;
	}
	
	@RequestMapping("/queryAvailAndAddedPost")
	@ResponseBody
	public Map<String, Object> queryAvailAndAddedPost(Integer userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<STPostManage> availPostList = postManageService.queryAvailPost(userId);
		List<STPostManage> addedPostList = postManageService.queryAddedPost(userId);
		result.put("availPostList", availPostList);
		result.put("addedPostList", addedPostList);
		
		return result;
	}

	/**
	 * 加载部门组织树
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findDeptAndOrgTreeVal")
	@ResponseBody
	public Map<String, Object> findDeptAndOrgTreeVal() throws Exception {
		String organizationStr = request.getParameter("organizationId");
		int organizationId = -1;
		if (!"".equals(organizationStr) && organizationStr != null) {
			organizationId = Integer.parseInt(organizationStr);
		}
		logger.info("初始化单位部门树........start");
		List<OrgDeptProviderTreeBean> TreeMenuList = new ArrayList<OrgDeptProviderTreeBean>();
		logger.info("获得单位数据");
		List<OrganizationBean> orgList = new ArrayList<OrganizationBean>();
		if (organizationId == -1) {
			orgList = organizationService.getOrganizationTreeVal();
		} else {
			orgList = organizationService.getOrganizationByConditions("organizationID", organizationStr);
		}
		for (int i = 0; i < orgList.size(); i++) {
			OrgDeptProviderTreeBean odp = new OrgDeptProviderTreeBean();
			odp.setId("O"+orgList.get(i).getOrganizationID());
			logger.info("单位组织拼接后的ID ："+odp.getId());
			odp.setParentId("0");
			odp.setName(orgList.get(i).getOrganizationName());
			TreeMenuList.add(odp);
		}
		logger.info("获得部门数据");
		List<DepartmentBean> deptList = new ArrayList<DepartmentBean>();
		if (organizationId == -1) {
			deptList = departmentService.getDepartmentTreeVal();
		} else {
			deptList = departmentService.getDepartmentByOrgID(organizationId);
		}
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
		String menuLstJson = JsonUtil.toString(TreeMenuList);
		logger.info("menuLstJson======="+menuLstJson);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	/**
	 * 根据查询条件查询用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryUserByconditions")
	@ResponseBody
	public Map<String, Object> queryUsers(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> conditions = new HashMap<String, String>();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		String userType = request.getParameter("userType");
		String orgId = request.getParameter("orgId");
		if(orgId ==null || "".equals(orgId)){
			SecurityUserInfoBean sysUser = (SecurityUserInfoBean) request.getSession().getAttribute("sysUserInfoBeanOfSession");
			Integer orgId1 = sysUserService.queryOrgIdByUserInfo(Integer.parseInt(sysUser.getId().toString()));
			orgId = String.valueOf(orgId1);
		}
		
		if (orgId == null || "".equals(orgId)) {
			result.put("rows", new ArrayList<Object>());
			result.put("total", 0);
			return result;
		}
		conditions.put("deptId", request.getParameter("deptId"));
		conditions.put("grade", request.getParameter("grade"));
		conditions.put("name", request.getParameter("name"));
		conditions.put("provider", request.getParameter("provider"));
		conditions.put("userNo", request.getParameter("userNo"));
		conditions.put("userIds", request.getParameter("userIds"));
		try {
			result.put("rows", sysUserService.queryUsers(userType, orgId, conditions, flexiGridPageInfo));
			result.put("total", sysUserService.queryUsersCount(userType, orgId, conditions));
		} catch (Exception e) {
			result.put("rows", new ArrayList<Object>());
			result.put("total", 0);
			logger.error("根据查询条件查询用户信息:"+e);
		}
		return result;
	}
}
