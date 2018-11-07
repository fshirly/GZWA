package com.fable.insightview.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.permission.form.OrganizationForm;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.ComboTreeModel;
import com.fable.insightview.platform.common.util.JsonUtil;
import com.fable.insightview.platform.entity.OrganizationBean;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.IOrganizationService;
import com.fable.insightview.platform.service.ISysEmploymentService;

/**
 * 单位组织
 * 
 * @author 汪朝
 * 
 */
@Controller
@RequestMapping("/permissionOrganization")
public class OrganizationController {

	@Autowired
	IOrganizationService organizationService;
	
	@Autowired
	ISysEmploymentService sysEmploymentService;
	
	@Autowired
	private HttpServletRequest request;

	private final Logger logger = LoggerFactory
			.getLogger(OrganizationController.class);

	/**
	 * 编辑修改单位
	 * 
	 * @return
	 */
	@RequestMapping("/updateOrganization")
	@ResponseBody
	public boolean updateOrganization(OrganizationBean organizationBean) {
		logger.info("修改更新单位组织.....start");
		try {
			return organizationService.updateOrganizationBean(organizationBean);
		} catch (Exception e) {
			logger.error("修改更新异常：" + e.getMessage(), organizationBean
					.getOrganizationID());
			return false;
		}
	}

	/**
	 * 初始化编辑数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findOrganization")
	@ResponseBody
	public OrganizationBean findOrganization(OrganizationBean organizationBean)
			throws Exception {
		logger.info("获取更新数据");
		logger.info("被更新数据Id = "+organizationBean.getOrganizationID());
		List<OrganizationBean> organizationLst = organizationService
				.getOrganizationBeanByConditions("organizationID",
						organizationBean.getOrganizationID() + "");

		return organizationLst.get(0);

	}

	@RequestMapping("/findOrganizationLst")
	@ResponseBody
	public Map<String, Object> findOrganizationLst(
			OrganizationBean organizationBean) throws Exception {
		List<OrganizationBean> organizationLst = organizationService.getOrganizationBeanByConditions("", "");
		String organizationLstJson = JsonUtil.toString(organizationLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("organizationLstJson", organizationLstJson);
		return result;

	}
	
	@RequestMapping("/orgTreeList")
	@ResponseBody
	public String findOrgTree(String orgID) throws Exception {
		logger.info("获取节点数据");
		List<OrganizationBean> organizationLst = new ArrayList<OrganizationBean>();
		if(orgID!=null){
			organizationLst = organizationService.getOrgByParentId(Integer.parseInt(orgID));
		}else{
			orgID = "0";
			organizationLst = organizationService.getOrgByParentId(Integer.parseInt(orgID));
		}
		List<Map<String,String>> listMaps= new ArrayList<Map<String,String>>();
		for (OrganizationBean org : organizationLst) {
			Map<String,String> node = new HashMap<String,String>();
			
			int orginId = org.getOrganizationID();
			node.put("id", orginId+"");
			node.put("text", org.getOrganizationName());
			node.put("checked", "true");
			if(org.getCount() > 0){
				node.put("state", "closed");
				//node.put("iconCls", "icon-folder");
				node.put("isChild", "no");
			}else{
				node.put("state", "null");
				//node.put("iconCls", "icon-file");
				node.put("isChild", "yes");
			}
			listMaps.add(node);
		}
		
		return JsonUtil.listMap2Json(listMaps);

	}
	
		
	@RequestMapping("/orgList")	
	@ResponseBody
    public Map<String,Object> organizationList(String orgID){
    	
    	logger.info("准备获取单位列表数据。。。");
    	List<OrganizationBean> s = new ArrayList<OrganizationBean>();
    	// 获取分页数据
    	if(orgID!=null){
        	s = organizationService.queryOrgConditionList(Integer.parseInt(orgID));
    	}
    	int total = s.size();//总记录数
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("total", total);
    	result.put("rows", s);
    	logger.info("数据获取结束。。。");
    	return result;
    }
    


	private void orgList() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 初始化单位组织树
	 * @param organizationBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findOrganizationList")
	@ResponseBody
	public List<OrganizationBean> findOrganizationJsonLst(OrganizationBean organizationBean) throws Exception {
		List<OrganizationBean> organizationLst = organizationService.getOrganizationBeanByConditions("", "");
		return organizationLst;

	}

	/**
	 * 单位信息页面弹出
	 * @return
	 */
	@RequestMapping("/toOrganizationList")
	public ModelAndView toOrganizationList(String navigationBar) {
		logger.info("strat..........进入单位信息页面");
		ModelAndView mv = new ModelAndView("permission/organization_list");
		mv.addObject("navigationBar", navigationBar);
		return mv ;
	}

	@RequestMapping("/getJsonVal")
	@ResponseBody
	public String getJsonVal() {
		List<ComboTreeModel> list = new ArrayList<ComboTreeModel>();
		for (int i = 1; i < 10; i++) {
			ComboTreeModel ctm = new ComboTreeModel();
			ctm.setId(i);
			ctm.setText("树节点" + i);
			if (i == 2) {
				List<ComboTreeModel> children = new ArrayList<ComboTreeModel>();
				for (int j = 1; j < 6; j++) {
					ComboTreeModel comboTreeModel = new ComboTreeModel();
					comboTreeModel.setId(j);
					comboTreeModel.setText("子节点" + i + j);
					children.add(comboTreeModel);
				}
				ctm.setChildren(children);
			}
			list.add(ctm);
		}
		String json = JsonUtil.toString(list);
		logger.info("Json========"+json);
		return json;
	}

	/**
	 * 新增单位
	 * @param organizationBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrganization")
	@ResponseBody
	public boolean addOrganization(OrganizationBean organizationBean) {
		logger.info("新增单位.........start");
		try {
			return organizationService.addOrganization(organizationBean);
		} catch (Exception e) {
			logger.error("插入异常："+e.getMessage(),organizationBean);
			return false;
		}
	}

	/**
	 * 删除单位
	 */
	@RequestMapping("/delOrganization")
	@ResponseBody
	public Map<String, Object> delOrganization(OrganizationForm organizationForm){
		Map<String, Object> result = new HashMap<String, Object>();
		OrganizationBean organizationBean = new OrganizationBean();
		organizationBean.setOrganizationID(organizationForm.getOrganizationId());
		result.put("result", organizationService.delOrganizationById(organizationBean));
		return result;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delOrganizations")
	@ResponseBody
	public Map<String, Object> delOrganizations(HttpServletRequest request) {
		logger.info("批量删除........start");
		boolean flag = false;
		boolean rs = false;
		boolean checkRs = false;
		String organizationIDs = request.getParameter("organizationIDs");
		String[] splitIds = organizationIDs.split(",");
		String orgName = "";
		/* 能够被删的ID数组 */
		List<Integer> delOrgId = new ArrayList<Integer>();
		/* 不能被删保留的ID数组 */
		List<Integer> reserveId = new ArrayList<Integer>();
		for (String strId : splitIds) {
			Integer organizationID = Integer.parseInt(strId);
			OrganizationBean organizationBean = new OrganizationBean();
			organizationBean.setOrganizationID(organizationID);
			logger.info("删除前的验证");
			checkRs = organizationService.checkBeforeDel(organizationBean);
			if (checkRs) {
				delOrgId.add(organizationID);
				logger.info("能够被删的Id：" + organizationID);
			} else {
				reserveId.add(organizationID);
				logger.info("不能够被删而保留的Id：" + organizationID);
			}
		}
		logger.info("删除能够删除的单位组织.....start");
		for (int i = 0; i < delOrgId.size(); i++) {
			OrganizationBean organizationBean = new OrganizationBean();
			organizationBean.setOrganizationID(delOrgId.get(i));
			try {
				organizationService.delOrganizationById(organizationBean);
			} catch (Exception e) {
				logger.error("删除异常："+e.getMessage());
			}
		}

		if (reserveId.size() > 0) {
			flag = false;
			String oName = "";
			for (int i = 0; i < reserveId.size(); i++) {
				oName = organizationService.getOrganizationByConditions(
						"organizationID", reserveId.get(i) + "").get(0)
						.getOrganizationName();
				orgName = orgName + oName + ",";
			}
			orgName = orgName.substring(0, orgName.lastIndexOf(","));
			logger.info("不能删除的单位名称："+orgName);
		} else {
			flag = true;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		orgName = " 【 " + orgName + " 】 ";
		result.put("flag", flag);
		result.put("orgName", orgName);
		return result;
	}

	/**
	 * 加载单位组织的列表
	 * @param organizationBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listOrganization")
	@ResponseBody
	public Map<String, Object> listOrganization(
			OrganizationBean organizationBean) throws Exception {
		logger.info("准备获取单位列表数据。。。");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		// 获取分页数据
		List<OrganizationBean> s = organizationService.getOrganizationByConditions(organizationBean,flexiGridPageInfo);
		// 获取总记录数
		int total = organizationService.getTotalCount(organizationBean);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", s);
		logger.info("数据获取结束。。。");
		return result;
	}

	/**
	 * 验证单位名称的唯一性
	 * @param organizationBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkOrganizationName")
	@ResponseBody
	public boolean checkSysUser(OrganizationBean organizationBean){
		boolean flag = false;
		try {
			flag = organizationService.checkOrganizationName(organizationBean);
		} catch (Exception e) {
			logger.error("单位名称验证异常"+e.getMessage());
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 验证单位编码的唯一性
	 * @param organizationBean
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkOrganizationCode")
	@ResponseBody
	public boolean checkOrganizationCode(OrganizationBean organizationBean){
		boolean flag = false;
		try {
			flag = organizationService.checkOrganizationCode(organizationBean);
		} catch (Exception e) {
			logger.error("单位编码验证异常"+e.getMessage());
			flag = false;
		}
		return flag;
	}

	/**
	 * 初始化树
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findOrganizationTreeVal")
	@ResponseBody
	public Map<String, Object> getOrganizationTreeVal() throws Exception {
		List<OrganizationBean> menuLst = organizationService.getOrganizationTreeVal();
		String menuLstJson = JsonUtil.toString(menuLst);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("menuLstJson", menuLstJson);
		return result;
	}

	/**
	 * 树定位
	 * @param organizationName
	 * @return
	 */
	@RequestMapping("/searchTreeNodes")
	@ResponseBody
	public OrganizationBean searchTreeNodes(String organizationName) {
		return organizationService.getOrganizationBeanByOrgName(organizationName);

	}
	
	@RequestMapping("/queryOrganization")
	@ResponseBody
	public List<OrganizationBean> queryOrganization() {
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean) request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		long currentUserId = sysUserInfoBeanTemp.getId();
		return organizationService.listOrganizationByUserId((int)currentUserId);
	}
}
