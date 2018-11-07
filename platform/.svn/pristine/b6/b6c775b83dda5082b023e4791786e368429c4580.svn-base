package com.fable.insightview.permission.controller;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fable.insightview.platform.common.util.CryptoUtil;
import com.fable.insightview.platform.entity.MenuNode;
import com.fable.insightview.platform.entity.SysMenuModuleBean;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.entity.SystemParamBean;
import com.fable.insightview.platform.service.ISysMenuModuleService;
import com.fable.insightview.platform.service.ISysUserGroupService;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.service.ISystemParamService;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.sysinit.SystemParamInit;

/**
 * 兴趣爱好
 * 
 * @author 汪朝
 * 
 */
@Controller
@RequestMapping("/commonLogin")
public class LoginController {

	@Autowired
	ISysUserGroupService sysUserGroupService;
	@Autowired
	ISysMenuModuleService sysMenuModuleService;
	@Autowired
	ISysUserService sysUserService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	ISystemParamService systemParamService;
	@Autowired
	SysConfigService sysConfig;

	@RequestMapping("/loginOut")
	@ResponseBody
	public ModelAndView checkUserPermission() throws Exception {
		request.getSession().invalidate();
		return new ModelAndView("login");
	}

	@RequestMapping("/checkUserPermission")
	@ResponseBody
	public boolean checkUserPermission(SysUserInfoBean sysUserInfoBean)
			throws Exception {
		sysUserInfoBean.setUserPassword(CryptoUtil.Encrypt(sysUserInfoBean
				.getUserPassword()));
		List<SysUserInfoBean> userLst = sysUserService
				.checkUserInfo(sysUserInfoBean);
		if (null != userLst && userLst.size() > 0) {
			SysUserInfoBean sysUserInfoBeanTemp = userLst.get(0);
			request.getSession().setAttribute("sysUserInfoBeanOfSession",
					sysUserInfoBeanTemp);

			List<String> sysUserGroupLst = sysUserGroupService
					.getGroupByUserId(sysUserInfoBeanTemp);
			request.getSession().setAttribute("sysUserGroupLstOfSession",
					sysUserGroupLst);
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping("/refreshMenu")
	public ModelAndView refreshMenu() {
		int parentMenuId = Integer.parseInt(request.getParameter("menuid")
				.toString());
		String menuName = request.getParameter("menuName");
		SysMenuModuleBean sysMenuModuleBean = new SysMenuModuleBean();
		sysMenuModuleBean.setParentMenuID(parentMenuId);

		List<SysMenuModuleBean> sysMenuModuleLst = sysMenuModuleService
				.getUserSysMenuModule(sysMenuModuleBean, new SysUserInfoBean());
		for (SysMenuModuleBean menu:sysMenuModuleLst) {
			menu.setNavigationBar(menuName + " >> " + menu.getMenuName());
		}
		request.getSession().setAttribute("leftSysMenuModuleLst",
				sysMenuModuleLst);
	
		return new ModelAndView("left");
	}
	
	@RequestMapping("/subMenu")
	public @ResponseBody List<MenuNode> getSubMenu(int parentMenuId)
	{
		SysMenuModuleBean sysMenuModuleBean = new SysMenuModuleBean();
		sysMenuModuleBean.setParentMenuID(parentMenuId);
		String navigationBar = request.getParameter("navigationBar");
		List<MenuNode> sysMenuModuleLst = sysMenuModuleService
				.getUserSubMenu(sysMenuModuleBean, new SysUserInfoBean());
		for (MenuNode menu:sysMenuModuleLst) {
			menu.setNavigationBar(navigationBar + " >> <span>" + menu.getText() + "</span>");
		}
		return sysMenuModuleLst;
	}

	@RequestMapping("/toMain")
	public ModelAndView toOrganizationList() {
		List<SysMenuModuleBean> sysMenuModuleLst = sysMenuModuleService
				.getUserSysMenuModule(new SysMenuModuleBean(),
						new SysUserInfoBean());
		if (null == sysMenuModuleLst) {
			request.getSession().removeAttribute("username");
			return new ModelAndView("main");
		}
		for (SysMenuModuleBean menu:sysMenuModuleLst) {
			menu.setNavigationBar(menu.getMenuName());
		}
		request.getSession().setAttribute("sysMenuModuleLst", sysMenuModuleLst);
		
		SystemParamBean systemParamBeanTemp = new SystemParamBean("ProductSmallIcon");
		SystemParamBean systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("ProductSmallIcon",
				systemParamBeanTempSession.getParamValue());
		
		systemParamBeanTemp = new SystemParamBean("LogoIcon");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("LogoIcon",
				systemParamBeanTempSession.getParamValue());
		/**add by sunkai 在身份认证的情况少走一步listSystemParam,因此移到这里**/
		systemParamBeanTemp = new SystemParamBean("CopyRight");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("CopyRight",
				systemParamBeanTempSession.getParamValue());

		systemParamBeanTemp = new SystemParamBean("Title");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("Title",
				systemParamBeanTempSession.getParamValue());

		systemParamBeanTemp = new SystemParamBean("LoginIcon");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("LoginIcon",
				systemParamBeanTempSession.getParamValue());
		
		systemParamBeanTemp = new SystemParamBean("LogoIcon");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("LogoIcon",
				systemParamBeanTempSession.getParamValue());
		
		systemParamBeanTemp = new SystemParamBean("Version");
		systemParamBeanTempSession = systemParamService
				.querySystemParam(systemParamBeanTemp);
		request.getSession().setAttribute("Version",
				systemParamBeanTempSession.getParamValue());
		
		request.getSession().setAttribute("filePath",SystemParamInit.getValueByKey("fileServerURL"));
		/**add by sunkai end**/
		return new ModelAndView("main");
	}
	
	@RequestMapping("/noUser")
	public ModelAndView toNoUser()
	{
		return new ModelAndView("noUser");
	}
	
	/**
	 * 首页谷歌浏览器下载
	 * @return
	 */
	@RequestMapping(value = "/getGoogleDownload" ,produces="application/json;charset=utf-8")
	@ResponseBody
	public String getGoogleDownload(){
		JSONObject json = new JSONObject();
		String forWin7 = sysConfig.getConfParamValue(202, "googleForWin7");
		String forXp = sysConfig.getConfParamValue(202, "googleForXp");
		json.put("forWin7", forWin7);
		json.put("forXp", forXp);
		
		return json.toString();
	}
	
	
	/*
	 * 获取首页二维码和下载链接
	 * @author nielb
	 */
	@RequestMapping(value = "/loginGetApkUrl",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getTDC(){
		JSONObject json = new JSONObject();
		boolean flag = true;
		String apkDownloadUrl = sysConfig.getConfParamValue(202, "apkDownloadUrl");
		flag = apkDownloadUrl == null?false:true;
		json.put("flag", flag);
		json.put("apkDownloadUrl", apkDownloadUrl);
		return json.toString();
	}
	
	@RequestMapping(value = "/loginGetTDC" , produces="application/json;charset=utf-8")
	@ResponseBody
	public String loginGetTDC(HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject();
		json.put("qrCode", "/commonLogin/getQrCode");
		return json.toString();
	}
	
	
	/**
	 * 生成二维码
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/getQrCode")
	public void getQrCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String str = request.getParameter("type");
		String url = null;
		if("ios".equals(str)){
			url = sysConfig.getConfParamValue(202, "iosTdcDownloadUrl");
		}else{
			url = sysConfig.getConfParamValue(202, "apkTdcDownloadUrl");
		}
		
		try {
			url = new String(url.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
		}
		ByteArrayOutputStream out = QRCode.from(url).to(ImageType.PNG)
				.withSize(250, 250).stream();
		response.getOutputStream().write(out.toByteArray());
	}
	
	
}
