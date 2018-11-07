package com.fable.insightview.dashboardPageManage;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*import com.fable.insightview.itsm.commens.ShowDate;
import com.fable.insightview.itsm.entity.eventmanage.Event;
import com.fable.insightview.itsm.entity.knowledgeManage.Knowledge;
import com.fable.insightview.itsm.entity.taskmanage.Task;
import com.fable.insightview.itsm.service.eventmanage.EventManageService;
import com.fable.insightview.itsm.service.knowledgeManage.KnowledgeService;
import com.fable.insightview.itsm.service.taskmanage.TaskManageService;*/
import com.fable.insightview.platform.common.BaseController;
import com.fable.insightview.platform.common.entity.SecurityUserInfoBean;
import com.fable.insightview.platform.common.util.Constants;
import com.fable.insightview.platform.common.util.SystemFinalValue;
import com.fable.insightview.platform.sysconf.mapper.SysConfigMapper;
import com.fable.insightview.platform.sysconf.service.SysConfigService;
import com.fable.insightview.platform.sysinit.SystemParamInit;
/*import com.fable.insightview.resource.entity.ResCiBean;
import com.fable.insightview.resource.service.IResCiService;*/

@Controller
@RequestMapping("/dashboardPageManage")
public class DashboardPageManageController extends BaseController {

	/*@Autowired
	private EventManageService eventManageService;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private TaskManageService taskManageService;
	
	@Autowired
	private IResCiService resCiService;*/
	@Autowired
	private SysConfigService sysConfigServiceImpl;
		
	@Autowired
	private SysConfigMapper sysConfigMapper;
	/**
	 * 服务台跳转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toReceptionDesk")
	public ModelAndView toReceptionDesk() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard/dashboard_service_list");
		return mv;
	}
	
	@RequestMapping(value = "/rapidCreateEvent")
	public ModelAndView rapidCreateEvent(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		SecurityUserInfoBean sysUserInfoBeanTemp = (SecurityUserInfoBean)request
				.getSession().getAttribute("sysUserInfoBeanOfSession");
		Long currentUserId = sysUserInfoBeanTemp.getId();
		mv.addObject("userId", currentUserId);
		mv.setViewName("dashboardPageManage/rapidCreateEvent");
		return mv;
	}
	
	/**
	 * 待办事件列表
	 * 
	 * @param eventManageVO
	 * @throws Exception
	 * @author sanyou
	 */
	@RequestMapping(value = "/toBlockEventQueryList")
	public ModelAndView queryBlockEventList(Integer pageNo) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("flag",true);
		if (null != pageNo && pageNo == 5) {
			mv.setViewName("eventmanage/todoWorkflow");
		} else {
			String rdEnable = SystemParamInit.getValueByKey("bpm.rudong.consumableApplication");
			mv.addObject("rdEnable", rdEnable);
			String cityWorkOrderFlag = this.sysConfigServiceImpl.getConfParamValue(SystemFinalValue.SYS_CONFIG_TYPE_PROCESS_WOA
					, SystemFinalValue.SCT_PROC_WOA_CITY_WORKORDER_FLAG);
			mv.addObject("cityWorkOrderFlag", cityWorkOrderFlag);
			mv.setViewName("eventmanage/eventQueryList");
		}
		return mv;
	}
	
	/**
	 * 首页待分配事件列表跳转
	 * @author : Zheng Zhen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toBeAllocatedEvent")
	public ModelAndView toBeAllocatedEvent() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/toBeAllocatedEvent");
		return mv;
	}
	
	@RequestMapping(value = "/myReportedEvents")
	public ModelAndView myReportedEvents() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/myReportedEvents");
		return mv;
	}
	
	@RequestMapping(value = "/myTaskList")
	public ModelAndView myTaskList() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/myTaskList");
		return mv;
	}
	
	@RequestMapping(value = "/myTaskManage")
	public ModelAndView myTaskManage() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/myTaskManage");
		return mv;
	}
	
	@RequestMapping(value = "/knowledgeSearch")
	public ModelAndView knowledgeSearch() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/knowledgeSearch");
		return mv;
	}
	
	@RequestMapping(value = "/rapidQuery")
	public ModelAndView rapidQuery() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/rapidQuery");
		return mv;
	}
	
	@RequestMapping(value = "/eventManageView")
	public ModelAndView eventManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("eventmanage/eventManageView");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		
		String serialNo = request.getParameter("serialNo");
		if(serialNo != null) {
			mv.addObject("serialNoParam",serialNo);
		}
		return mv;
	}
	
	@RequestMapping(value = "/problemBillManageView")
	public ModelAndView problemBillManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("problemManage/problemBillManageView");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		
		String serialNo = request.getParameter("serialNo");
		if(serialNo != null) {
			mv.addObject("serialNoParam",serialNo);
		}
		return mv;
	}
	
	@RequestMapping(value = "/changeManageView")
	public ModelAndView changeManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("changeManage/changeManageView");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		
		String serialNo = request.getParameter("serialNo");
		if(serialNo != null) {
			mv.addObject("serialNoParam",serialNo);
		}
		return mv;
	}
	
	@RequestMapping(value = "/resciList")
	public ModelAndView resciList(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("resource/resci_list");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		return mv;
	}
	
	@RequestMapping(value = "/taskManageView")
	public ModelAndView taskManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("taskmanage/taskManageView");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		return mv;
	}
	
	@RequestMapping(value = "/knowledgeManageView")
	public ModelAndView knowledgeManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		//是否是安徽升级版知识库;
		String zskFlag = sysConfigServiceImpl.getConfParamValue(213, "zsk");
		if (Constants.TRUE_OR_FALSE.TRUE.equals(zskFlag)) {
			//知识库应用里面
			mv.setViewName("knowledgeManage/knowledgeListJump");
		} else {
			mv.setViewName("knowledgeManage/knowledgeManageView");
		}
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		return mv;
	}
	
	@RequestMapping(value = "/knownErrorManageView")
	public ModelAndView knownErrorManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("problemManage/knownErrorManageView");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		return mv;
	}
	
	@RequestMapping(value = "/eventCaseManageView")
	public ModelAndView eventCaseManageView(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("knowledgeManage/eventCaseManageView");
		mv.addObject("flag",true);
		String title = request.getParameter("title");
		if(title != null) {
			mv.addObject("titleParam",URLDecoder.decode(title, "UTF-8"));
		}
		return mv;
	}
	
	/*@RequestMapping("/queryMyTaskList")
	@ResponseBody
	public Map<String, Object> queryMyTaskReplyList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		int userid = getUserIdBySession(request).getId().intValue();
		int status = Integer.parseInt(request.getParameter("status"));
		List<Task> taskList = taskManageService.getMyTaskList(userid, status);
		result.put("rows", taskList);
		result.put("total", taskList.size());
		return result;
	}*/
	
	/*@RequestMapping("/queryAllMyTaskList")
	@ResponseBody
	public Map<String, Object> queryAllMyTaskList() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		Map<String, Object> result = new HashMap<String, Object>();

		int userid = getUserIdBySession(request).getId().intValue();
		int status = Integer.parseInt(request.getParameter("status"));
		List<Task> taskList = taskManageService.getAllMyTaskList(userid, status,flexiGridPageInfo);
		int count = taskManageService.getAllMyTaskCount(userid, status);
		result.put("rows", taskList);
		result.put("total", count);
		return result;
	}*/
	
//	@RequestMapping(value = "/saveEvent")
//	@ResponseBody
//	public String saveEventManageVO(Event event) throws Exception {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		event.setSerialNO(SystemFinalValue.EVENT_NO + sdf.format(new Date()));
//		event.setBookTime(new ShowDate());
//		//event.setRequestMode(2);/*Web自助*/
//		event.setStatus((byte)5);/*待分配状态*/
//		event.setmFlag((byte)0);
//		eventManageService.updateEvent(event);
//		return "OK";
//	}
	
	/*@RequestMapping("/queryMyEventList")
	@ResponseBody
	public Map<String, Object> queryMyEventList(Event event) {
		Map<String, Object> result = new HashMap<String, Object>();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);

		int requester = getUserIdBySession(request).getId().intValue();
		event.setRequester(requester);
		List<Event> eventList = this.eventManageService.getMyEventList(event, flexiGridPageInfo);
		result.put("rows", eventList);
		result.put("total", eventList.size());
		return result;
	}*/
	
	
	/**
	 * @author Zhang Ya
	 * @param keywords
	 * @param category
	 * @return
	 */
	/*@RequestMapping("/retrievalResults")
	@ResponseBody
	public ModelAndView retrievalResults(String keywords, Integer category) {
		int count = this.knowledgeService.searchRetrievalKnowledgeCount(keywords, category);
		ModelAndView mv = new ModelAndView();
		mv.addObject("count", count);
		mv.addObject("keywords", keywords);
		mv.addObject("category", category);
		mv.setViewName("dashboardPageManage/retrievalResults");
		return mv;
	}*/
	
	/**
	 * @author Zhang Ya
	 * 查询检索结果列表页
	 * @param keywords
	 * @param category
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/queryRetrievalResults")
	@ResponseBody
	public Map<String, Object> queryRetrievalResults(String keywords, Integer category) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		flexiGridPageInfo.setPage(1);
		flexiGridPageInfo.setRp(10);
		List<Knowledge> knoList = knowledgeService.searchRetrievalKnowledge(keywords, category, flexiGridPageInfo);
		int total = knowledgeService.searchRetrievalKnowledgeCount(keywords, category);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", knoList);
		return result;
	}*/
	
	/**
	 * @author Zhang Ya
	 * 检索结果查看详情页
	 * @param id
	 * @return
	 */
	/*@RequestMapping("/retrievalResultsView")
	@ResponseBody
	public ModelAndView retrievalResultsView(Integer id){
		ModelAndView mv = new ModelAndView();
		Knowledge knowledge = knowledgeService.getById(id);
		mv.addObject("knowledge", knowledge);
		mv.setViewName("dashboardPageManage/retrievalResultsView");
		return mv;
	}*/
	
	//KnownError,Knowledge,EventCase
	/*@RequestMapping("/searchKnowledge")
	public ModelAndView searchKnowledge(String keywords, Integer category) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil.getFlexiGridPageInfo(request);
		List<Knowledge> listKnowledge = this.knowledgeService.searchKnowledge(keywords, category, flexiGridPageInfo);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/results");
		mv.addObject("rows", listKnowledge);
		mv.addObject("category", category);
		return mv;
	}*/
	
	/*@RequestMapping("/firstTeamResolvingRate")
	@ResponseBody
	public Point firstTeamResolvingRate() {
		Point point = new Point();
		
		double rate = this.eventManageService.firstTeamResolvingRate() * 100;
		point.setValue(String.format("%.2f", rate));
		return point;
	}*/
	
	@RequestMapping("/eventResolveStat")
	public String eventResolveStat()
	{
		return "dashboardPageManage/eventResovleStat";
	}
	
	/**
	 * 首页我的关注跳转
	 * @author : Zheng Zhen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myConcern")
	public ModelAndView myConcern() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/myConcern");
		return mv;
	}
	
	/**
	 * 首页显示我的关注的资源的最新变更信息Top5
	 * @author : Zheng Zhen
	 * @param requset
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/myConcern/latest")
	@ResponseBody
	public Map<String, Object> queryMyLatestConcernRes(HttpServletRequest request) throws Exception {
		int userId = this.getUserIdBySession(request).getId().intValue();
		List<ResCiBean> resList = resCiService.getMyLatestConcernRes(userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", resList);
		return result;
	}*/
	
	/**
	 * 我关注的资源的变更详情
	 * @author : Zheng Zhen
	 * @param id
	 * @return
	 */
	@RequestMapping("/myConcern/detail")
	public ModelAndView showConcernResciChangeDtl(HttpServletRequest request) {
		String resciId=request.getParameter("id");
		ModelAndView mv = new ModelAndView();
		mv.addObject("resciId",resciId);
		mv.setViewName("dashboardPageManage/myConcern_detail");
		return mv;
	}
	
	/**
	 * 我的关注查看更多页面跳转
	 * @author : Zheng Zhen
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/myConcernList")
	public ModelAndView myConcernList() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboardPageManage/myConcernList");
		return mv;
	}
	
	/**
	 * 首页显示我的关注的资源的信息列表
	 * @author : Zheng Zhen
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping("/myConcernList/latest")
	@ResponseBody
	public Map<String, Object> queryMyLatestConcernResList(HttpServletRequest request) throws Exception {
		FlexiGridPageInfo flexiGridPageInfo = FlexiGridPageInfoUtil
				.getFlexiGridPageInfo(request);
		int userId = this.getUserIdBySession(request).getId().intValue();
		List<ResCiBean> resList = resCiService.getMyLatestConcernResList(userId,flexiGridPageInfo);
		int count = resCiService.getMyLatestConcernResCount(userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("rows", resList);
		result.put("total", count);
		return result;
	}*/
	
}
