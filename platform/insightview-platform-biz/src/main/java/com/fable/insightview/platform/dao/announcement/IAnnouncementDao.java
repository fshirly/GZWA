package com.fable.insightview.platform.dao.announcement;

import java.util.List;

import com.fable.insightview.platform.entity.announcement.Announcement;
import com.fable.insightview.platform.itsm.core.dao.GenericDao;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
/**
 * @author  : ZhangYa
 * @version : eclipse kepler
 * @datetime: 2014年4月10日 下午2:18:08
 */
public interface IAnnouncementDao extends GenericDao<Announcement>{
	
	public List<Announcement> getItsmAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getReservedAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getCompletedAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getWeekAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getMonthAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getYearAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getWeekAnnCompletedByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getMonthAnnCompletedByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getYearAnnCompletedByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	/**
	 * 查询总记录数 
	 * @return
	 */
	public int getTotalCount(Announcement announcement);
	
	public Announcement getAnnounceById(int id);
	
	public boolean updateItsmAnn(Announcement announcement);
	
	public boolean addItsmAnn(Announcement announcement);
	
	public boolean delItsmAnn(Announcement announcement);
	
	public int getReservedTotalCount(Announcement announcement);
	
	public int getCompletedTotalCount(Announcement announcement);
	
	public int getWeekTotalCount(Announcement announcement);
	
	public int getMonthTotalCount(Announcement announcement);
	
	public int getYearTotalCount(Announcement announcement);
	
	public int getWeekCompletedTotalCount(Announcement announcement);
	
	public int getMonthCompletedTotalCount(Announcement announcement);
	
	public int getYearCompletedTotalCount(Announcement announcement);
}
