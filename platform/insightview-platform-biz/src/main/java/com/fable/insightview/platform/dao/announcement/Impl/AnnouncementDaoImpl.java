package com.fable.insightview.platform.dao.announcement.Impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import com.fable.insightview.platform.common.util.DateUtil;
import com.fable.insightview.platform.dao.announcement.IAnnouncementDao;
import com.fable.insightview.platform.entity.announcement.Announcement;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;

/**
 * @author : ZhangYa
 * @version : eclipse kepler
 * @datetime: 2014年4月10日 下午2:22:21
 */
@Repository("announcementDao")
public class AnnouncementDaoImpl extends GenericDaoHibernate<Announcement>
		implements IAnnouncementDao {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 模糊查询
	 * 
	 * @param hql
	 * @param announcement
	 * @return
	 */
	public String commonConditions(String hql, Announcement announcement) {
		if (null != announcement.getTitle()
				&& !"".equals(announcement.getTitle())) {
			hql += " and Title LIKE '%" + announcement.getTitle() + "%'";
		}
		return hql;
	}

	/**
	 * 查询所有通知公告列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Announcement> getItsmAnnByConditions(Announcement announcement,
			FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 ";
		hql = commonConditions(hql, announcement);
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 修改公告
	 */
	@Override
	public boolean updateItsmAnn(Announcement announcement) {
		try {
			super.update(announcement);
		} catch (Exception e) {
			logger.error("修改异常", announcement);
			return false;
		}
		return true;
	}

	/**
	 * 创建公告
	 */
	@Override
	public boolean addItsmAnn(Announcement announcement) {
		try {
			super.insert(announcement);
		} catch (Exception e) {
			logger.error("插入异常", announcement);
			return false;
		}
		return true;
	}

	/**
	 * 删除公告
	 */
	@Override
	public boolean delItsmAnn(Announcement announcement) {
		try {
			super.delete(announcement);
		} catch (Exception e) {
			logger.error("删除异常", announcement);
			return false;
		}
		return true;
	}

	/**
	 * 查询当前公告列表
	 */
	@Override
	public List<Announcement> getReservedAnnByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and ia.DeadLine >= ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		query.setParameter(0, new Date());
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 查询已过期公告列表
	 */
	@Override
	public List<Announcement> getCompletedAnnByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		Date da = new Date();
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine < ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		query.setParameter(0, da);
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 查询本周公告列表
	 */
	@Override
	public List<Announcement> getWeekAnnByConditions(Announcement announcement,
			FlexiGridPageInfo flexiGridPageInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		String monday = sdf.format(c.getTime()) + " 00:00:00";
		c.add(Calendar.DATE, 6);
		String sunday = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between NOW() and ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
//		query.setParameter(0, DateUtil.string2Date(monday));
		query.setParameter(0, DateUtil.string2Date(sunday));
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 查询本月公告列表
	 */
	@Override
	public List<Announcement> getMonthAnnByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between NOW() and ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
//		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(0, DateUtil.string2Date(lastDay));
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 查询本年度公告列表
	 */
	@Override
	public List<Announcement> getYearAnnByConditions(Announcement announcement,
			FlexiGridPageInfo flexiGridPageInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between NOW() and ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
//		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(0, DateUtil.string2Date(lastDay));
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 获得公告对象
	 */
	@Override
	public Announcement getAnnounceById(int id) {
		Announcement announcement = super.getById(id);
		Session sess = getHibernateTemplate().getSessionFactory()
				.getCurrentSession();
		String sql = "select Sys.UserName from SysUserInfo Sys where Sys.UserID="
				+ announcement.getCreater();
		Query query = sess.createSQLQuery(sql);
		List<String> listResult = query.list();
		for (String string : listResult) {
			announcement.setCreator(string);
		}
		return announcement;
	}

	/**
	 * 查询总页数
	 * 
	 * @return
	 */
	@Override
	public int getTotalCount(Announcement announcement) {
		String hql = "select count(1) from (select * from ItsmAnnouncement t where 1=1 ";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 当前通知公告统计
	 */
	@Override
	public int getReservedTotalCount(Announcement announcement) {
		String hql = "select count(1) from (SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and ia.DeadLine >= ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, new Date());
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 已过期通知公告统计
	 */
	@Override
	public int getCompletedTotalCount(Announcement announcement) {
		Date da = new Date();
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine < ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, da);
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 本周创建的通知公告统计
	 */
	@Override
	public int getWeekTotalCount(Announcement announcement) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		String monday = sdf.format(c.getTime()) + " 00:00:00";
		c.add(Calendar.DATE, 6);
		String sunday = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, DateUtil.string2Date(monday));
		query.setParameter(1, DateUtil.string2Date(sunday));
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 本月创建的通知公告统计
	 */
	@Override
	public int getMonthTotalCount(Announcement announcement) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(1, DateUtil.string2Date(lastDay));
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 本年度创建的通知公告统计
	 */
	@Override
	public int getYearTotalCount(Announcement announcement) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(1, DateUtil.string2Date(lastDay));
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 查询本周已过期公告列表
	 */
	@Override
	public List<Announcement> getWeekAnnCompletedByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		String monday = sdf.format(c.getTime()) + " 00:00:00";
		c.add(Calendar.DATE, 6);
		String sunday = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ? and DeadLine <= ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		query.setParameter(0, DateUtil.string2Date(monday));
		query.setParameter(1, DateUtil.string2Date(sunday));
		query.setParameter(2, new Date());
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 查询本月已过期公告列表
	 */
	@Override
	public List<Announcement> getMonthAnnCompletedByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ? and DeadLine <= ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(1, DateUtil.string2Date(lastDay));
		query.setParameter(2, new Date());
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 查询本年度已过期公告列表
	 */
	@Override
	public List<Announcement> getYearAnnCompletedByConditions(
			Announcement announcement, FlexiGridPageInfo flexiGridPageInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ? and DeadLine <= ?";
		hql = commonConditions(hql, announcement);
		hql += " ORDER BY ia.Id DESC";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql)
				.addScalar("id", IntegerType.INSTANCE).addScalar("title")
				.addScalar("summary").addScalar("typeId", IntegerType.INSTANCE)
				.addScalar("creater", IntegerType.INSTANCE)
				.addScalar("creator")
				.addScalar("createTime", TimestampType.INSTANCE)
				.addScalar("deadLine", TimestampType.INSTANCE);
		query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo
				.getRp()));
		query.setMaxResults(flexiGridPageInfo.getRp());
		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(1, DateUtil.string2Date(lastDay));
		query.setParameter(2, new Date());
		List<Announcement> annList = query.setResultTransformer(
				Transformers.aliasToBean(Announcement.class)).list();
		return annList;
	}

	/**
	 * 本周创建的已过期通知公告统计
	 */
	@Override
	public int getWeekCompletedTotalCount(Announcement announcement) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		String monday = sdf.format(c.getTime()) + " 00:00:00";
		c.add(Calendar.DATE, 6);
		String sunday = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ? and DeadLine <= ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, DateUtil.string2Date(monday));
		query.setParameter(1, DateUtil.string2Date(sunday));
		query.setParameter(2, new Date());
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 本月创建的已过期通知公告统计
	 */
	@Override
	public int getMonthCompletedTotalCount(Announcement announcement) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ? and DeadLine <= ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(1, DateUtil.string2Date(lastDay));
		query.setParameter(2, new Date());
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}

	/**
	 * 本年度创建的已过期通知公告统计
	 */
	@Override
	public int getYearCompletedTotalCount(Announcement announcement) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
		String firstDay = sdf.format(c.getTime()) + " 00:00:00";
		c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
		String lastDay = sdf.format(c.getTime()) + " 23:59:59";
		String hql = "select count(1) from ("
				+ "SELECT ia.Id,ia.Title,ia.Summary,ia.TypeId,ia.Creater,ia.CreateTime createTime,ia.DeadLine deadLine,si.UserName creator FROM ItsmAnnouncement ia LEFT JOIN SysUserInfo si ON ia.Creater=si.UserID where 1=1 and DeadLine between ? and ? and DeadLine <= ?";
		hql = commonConditions(hql, announcement);
		hql += ") t";
		Query query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(hql);
		query.setParameter(0, DateUtil.string2Date(firstDay));
		query.setParameter(1, DateUtil.string2Date(lastDay));
		query.setParameter(2, new Date());
		int count = ((Number) query.uniqueResult()).intValue();
		return count;
	}
}