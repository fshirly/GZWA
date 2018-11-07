package com.fable.insightview.platform.service.announcement;

import java.util.List;

import com.fable.insightview.platform.entity.announcement.Announcement;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * @author  : ZhangYa
 * @version : eclipse kepler
 * @datetime: 2014年4月10日 下午2:14:53
 */
public interface IAnnouncementService {
	
	public List<Announcement> getItsmAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo);
	
	public List<Announcement> getAnnouncementListByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo, String workType, Integer deadLineInt);

	/**
	 * 获取总记录数
	 */
	public int getTotalCount(Announcement announcement);
	
	public boolean updateItsmAnn(Announcement announcement);
	
	public boolean addItsmAnn(Announcement announcement);
	
	public boolean delItsmAnnById(Announcement announcement);
	
	public Announcement getAnnouncementById(int id);
	
	public Announcement getAnnouncement(Announcement announcement);

	public int getAnnouncementCount(Announcement announcement, Integer deadLineInt, String workType);
}
