package com.fable.insightview.dashboardPageManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fable.insightview.platform.common.BaseController;
import com.fable.insightview.platform.dict.util.DictionaryLoader;
import com.fable.insightview.platform.entity.SysUserInfoBean;
import com.fable.insightview.platform.entity.announcement.Announcement;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfoUtil;
import com.fable.insightview.platform.service.ISysUserService;
import com.fable.insightview.platform.service.announcement.IAnnouncementService;

/**
 * @author : ZhangYa
 * @version : eclipse kepler
 * @datetime: 2014年4月10日 下午2:16:35
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends BaseController {
	@Autowired
	private IAnnouncementService announcementService;
	
	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 跳转Dashboard前五条展示
	 * @return
	 */
	@RequestMapping("/announcementFrontFive")
	public ModelAndView announcementFrontFive(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/announcement");
		return mv;
	}
	
	/**
	 * 首页通知公告跳转
	 * @param announcement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryItsmAnnList")
	@ResponseBody
	public Map<String,Object> queryItsmAnnList(Announcement announcement)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		flexiGridPageInfo.setPage(1);
		flexiGridPageInfo.setRp(5);
		List<Announcement> annList = announcementService.getAnnouncementListByConditions(announcement, flexiGridPageInfo, "Reserved", -1);
		int size = annList == null ? 0 : annList.size();
		int total = annList.size() == 0 ? size : annList.size();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("total", total);
		result.put("rows", annList);
		return result;
	}

	/**
	 * 查询更多通知公告列表展示
	 * @param announcement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryItsmAnnouList")
	@ResponseBody
	public ModelAndView queryItsmAnnouList(Announcement announcement)
			throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		ModelAndView mv = new ModelAndView();
		mv.addObject("navigationBar", request.getParameter("navigationBar"));
		mv.addObject("createrName", this.getUserIdBySession(request).getUserName());
		mv.setViewName("dashboardPageManage/announcementDetailAdmin");
		return mv;
	}

	/**
	 * 选项卡判断并得到相应的TabList
	 * 
	 * @param workType
	 * @return
	 */
	@RequestMapping("/queryBlock")
	@ResponseBody
	public Map<String, Object> queryBlock(Announcement announcement,
			String workType) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		String deadLineStr = request.getParameter("deadLineInt");
		int deadLineInt = -1;
		if (!StringUtil.isEmpty(deadLineStr)) {
			deadLineInt = Integer.parseInt(deadLineStr);
		}
		//根据本周、本月、本年或者当前、已过期等条件查询公告列表
		List<Announcement> annList = announcementService.getAnnouncementListByConditions(announcement, flexiGridPageInfo, workType, deadLineInt);
		//获得本周、本月、本年或者当前、已过期等条件情况下的记录数
		int	total = announcementService.getAnnouncementCount(announcement,deadLineInt,workType);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", annList);
		return result;
	}

	/**
	 * 获取公告对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAnnouncementById")
	@ResponseBody
	public ModelAndView getAnnouncementById(Integer id) throws Exception {
		ModelAndView mv = new ModelAndView();
		Announcement announcement = announcementService.getAnnouncementById(id);
		if(announcement.getCreater() != null){
			SysUserInfoBean sysUserInfoBean = sysUserService.findSysUserById(announcement.getCreater());
			String createrName = sysUserInfoBean.getUserName();
			mv.addObject("createrId", announcement.getCreater());
			mv.addObject("createrName", createrName);
		}
		mv.addObject("announcement", announcement);
		mv.setViewName("dashboardPageManage/announcementDetailEdit");
		return mv;
	}

	/**
	 * 修改公告
	 * @param announcement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateItsmAnn")
	@ResponseBody
	public boolean updateItsmAnn(Announcement announcement) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		announcement.setCreater(this.getUserIdBySession(request).getId().intValue());
		return announcementService.updateItsmAnn(announcement);
	}

	/**
	 * 新增
	 * @param announcement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addItsmAnn")
	@ResponseBody
	public boolean addItsmAnn(Announcement announcement) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		announcement.setCreater(this.getUserIdBySession(request).getId().intValue());
		announcement.setCreateTime(new Date());
		announcement.setTypeId(1);
		return announcementService.addItsmAnn(announcement);
	}

	/**
	 * 删除
	 * @param announcement
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delItsmAnn")
	@ResponseBody
	public boolean delItsmAnn(Announcement announcement) throws Exception {
		return announcementService.delItsmAnnById(announcement);
	}
	
	/**
	 * 跳转至通知公告查看详情页
	 * @return
	 */
	@RequestMapping("/announcementDetailView")
	@ResponseBody
	public ModelAndView announcementDetailView(Integer id){
		ModelAndView mv = new ModelAndView();
		Announcement announcement = announcementService.getAnnouncementById(id);
		if(announcement.getTypeId() != null){
			String announcementType = DictionaryLoader.getConstantItems("3066").get(Integer.parseInt(String.valueOf(announcement.getTypeId())));
			mv.addObject("announcementType", announcementType);
		}
		if(announcement.getCreater() != null){
			SysUserInfoBean sysUserInfoBean = sysUserService.findSysUserById(announcement.getCreater());
			String createrName = sysUserInfoBean.getUserName();
			mv.addObject("createrName", createrName);
		}
		mv.addObject("announcement", announcement);
		mv.setViewName("dashboardPageManage/announcementDetailView");
		return mv;
	}
}
