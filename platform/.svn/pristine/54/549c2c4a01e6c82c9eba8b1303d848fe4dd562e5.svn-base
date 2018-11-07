package com.fable.insightview.platform.service.announcement.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fable.insightview.platform.dao.announcement.IAnnouncementDao;
import com.fable.insightview.platform.entity.announcement.Announcement;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.service.announcement.IAnnouncementService;

/**
 * @author  : ZhangYa
 * @version : eclipse kepler
 * @datetime: 2014年4月10日 下午2:15:13
 */
@Service("announcementService")
public class AnnouncementServiceImpl implements IAnnouncementService{
	@Autowired
	private IAnnouncementDao announcementDao;

	@Override
	public List<Announcement> getItsmAnnByConditions(Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		List<Announcement> annList = announcementDao.getItsmAnnByConditions(announcement, flexiGridPageInfo);
		return annList;
	}
	
	@Override
	public boolean updateItsmAnn(Announcement announcement) {
		return announcementDao.updateItsmAnn(announcement);
	}

	@Override
	public boolean addItsmAnn(Announcement announcement) {
		return announcementDao.addItsmAnn(announcement);
	}
	
	public boolean delItsmAnnById(Announcement announcement){
		announcement.setId(announcement.getId());
		return announcementDao.delItsmAnn(announcement);
	}

	@Override
	public List<Announcement> getAnnouncementListByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo, String workType, Integer deadLineInt) {
		List<Announcement> annList = null;
		if (workType.equals("Reserved")) {
			annList = announcementDao.getReservedAnnByConditions(announcement, flexiGridPageInfo);
			if (deadLineInt == 1) {
				annList = announcementDao.getWeekAnnByConditions(announcement, flexiGridPageInfo);
				deadLineInt = -1;
			}
			if (deadLineInt == 2) {
				annList = announcementDao.getMonthAnnByConditions(announcement, flexiGridPageInfo);
				deadLineInt = -1;
			}
			if (deadLineInt == 3) {
				annList = announcementDao.getYearAnnByConditions(announcement, flexiGridPageInfo);
				deadLineInt = -1;
			}
		} else {
			annList = announcementDao.getCompletedAnnByConditions(announcement, flexiGridPageInfo);
			if (deadLineInt == 1) {
				annList = announcementDao.getWeekAnnCompletedByConditions(announcement, flexiGridPageInfo);
				deadLineInt = -1;
			}
			if (deadLineInt == 2) {
				annList = announcementDao.getMonthAnnCompletedByConditions(announcement, flexiGridPageInfo);
				deadLineInt = -1;
			}
			if (deadLineInt == 3) {
				annList = announcementDao.getYearAnnCompletedByConditions(announcement, flexiGridPageInfo);
				deadLineInt = -1;
			}
		}
		return annList;
	}
	
	@Override
	public Announcement getAnnouncementById(int id) {
		Announcement announce = announcementDao.getAnnounceById(id);
		return announce;
	}

	@Override
	public Announcement getAnnouncement(Announcement announcement) {
		return announcementDao.getById(announcement.getId());
	}

	/**
	 * 查询总记录数
	 */
	@Override
	public int getTotalCount(Announcement announcement) {
		return announcementDao.getTotalCount(announcement);
	}

	/**
	 * 查询不同情况总记录数
	 */
	@Override
	public int getAnnouncementCount(Announcement announcement, Integer deadLineInt, String workType) {
		// TODO Auto-generated method stub
		int total = 0;
		if (workType.equals("Reserved")) {
			total = announcementDao.getReservedTotalCount(announcement);
			if (deadLineInt == 1) {
				total = announcementDao.getWeekTotalCount(announcement);
				deadLineInt = -1;
			}
			if (deadLineInt == 2) {
				total = announcementDao.getMonthTotalCount(announcement);
				deadLineInt = -1;
			}
			if (deadLineInt == 3) {
				total = announcementDao.getYearTotalCount(announcement);
				deadLineInt = -1;
			}
		} else {
			total = announcementDao.getCompletedTotalCount(announcement);
			if (deadLineInt == 1) {
				total = announcementDao.getWeekCompletedTotalCount(announcement);
				deadLineInt = -1;
			}
			if (deadLineInt == 2) {
				total = announcementDao.getMonthCompletedTotalCount(announcement);
				deadLineInt = -1;
			}
			if (deadLineInt == 3) {
				total = announcementDao.getYearCompletedTotalCount(announcement);
				deadLineInt = -1;
			}
		}
		return total;
	}
}
