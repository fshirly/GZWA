package com.fable.insightview.platform.snmpdevice.dao.impl;

import java.util.List;

import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.fable.insightview.platform.itsm.core.dao.hibernate.GenericDaoHibernate;
import com.fable.insightview.platform.itsm.core.util.FlexiGridPageInfo;
import com.fable.insightview.platform.snmpdevice.dao.ISnmpDeviceDao;
import com.fable.insightview.platform.snmpdevice.entity.SnmpDeviceSysOIDInfo;

/**
 * @author 郑小辉
 * @ClassName: SnmpDeviceSysOIDMapper.java
 * @Description: 设备OID维护
 * @Date 2014-6-16 下午01:46:35
 */
@Repository("snmpDeviceDao")
public class SnmpDeviceDaoImpl extends GenericDaoHibernate<SnmpDeviceSysOIDInfo> implements ISnmpDeviceDao {


    @Override
    public void addInfo(SnmpDeviceSysOIDInfo vo) {
        super.insert(vo);
    }

    @Override
    public void deleteInfo(SnmpDeviceSysOIDInfo vo) {
        super.delete(vo);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SnmpDeviceSysOIDInfo getInfoById(String paramName, String paramVal) {
        //创建session对象
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        String sbfSql = "select t.id,t.pen,t.deviceOID,t.deviceModelName,t.deviceType,t.deviceNameAbbr," +
                "t.os,t.deviceIcon,t.resCategoryID,t.moClassID,o.descr,m.resCategoryName " +
                "from SNMPDeviceSysObjectID t left join ResCategoryDefine m " +
                "on t.resCategoryID = m.resCategoryID " +
                "LEFT JOIN mobjectdef o ON o.ClassID = t.moClassID where " + paramName + "=" + paramVal;
        Query query = session.createSQLQuery(sbfSql).setResultTransformer(Transformers.aliasToBean(SnmpDeviceSysOIDInfo.class));
        List<SnmpDeviceSysOIDInfo> list = query.list();
        if (null != list && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateInfo(SnmpDeviceSysOIDInfo vo) {
        Session session = this.getHibernateTemplate().getSessionFactory().openSession();
        session.beginTransaction();
        SnmpDeviceSysOIDInfo SnmpDeviceSys = (SnmpDeviceSysOIDInfo) session.get(SnmpDeviceSysOIDInfo.class, vo.getId());
        SnmpDeviceSys.setPen(vo.getPen());
        SnmpDeviceSys.setDeviceOID(vo.getDeviceOID());
        SnmpDeviceSys.setDeviceModelName(vo.getDeviceModelName());
        SnmpDeviceSys.setDeviceType(vo.getDeviceType());
        SnmpDeviceSys.setDeviceNameAbbr(vo.getDeviceNameAbbr());
        SnmpDeviceSys.setOs(vo.getOs());
        SnmpDeviceSys.setDeviceIcon(vo.getDeviceIcon());
        SnmpDeviceSys.setResCategoryID(vo.getResCategoryID());
        if (null != vo.getMoClassID()) {
            SnmpDeviceSys.setMoClassID(vo.getMoClassID());
        }
        session.getTransaction().commit();
    }

    @Override
    public void deleteBathInfo(String id) {
        String sql = "delete from SNMPDeviceSysObjectID where id in(" + id + ")";
        this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public FlexiGridPageInfo queryPage(SnmpDeviceSysOIDInfo vo, FlexiGridPageInfo flexiGridPageInfo) {
        StringBuffer sbfSql = new StringBuffer();
        sbfSql.append(" select t.id,t.pen,t.deviceOID,t.deviceModelName,t.deviceType,t.deviceNameAbbr,");
        sbfSql.append(" 	   t.os,t.deviceIcon,t.resCategoryID,t.moClassID,o.descr,m.resCategoryName");
        sbfSql.append(" from   SNMPDeviceSysObjectID t left join ResCategoryDefine m on t.resCategoryID = m.resCategoryID");
        sbfSql.append(" LEFT JOIN mobjectdef o ON o.ClassID = t.moClassID where 1=1");
        //条件查询使用占位符
        if (null != vo.getDeviceOID() && !"".equals(vo.getDeviceOID())) {
            sbfSql.append(" and DeviceOID LIKE :deviceOID");
        }
        //排序
        if (null != flexiGridPageInfo.getSort() && flexiGridPageInfo.getSort() != ""
                && null != flexiGridPageInfo.getOrder() && flexiGridPageInfo.getOrder() != "") {
            sbfSql.append(" order by " + flexiGridPageInfo.getSort() + " " + flexiGridPageInfo.getOrder());
        }
        //创建session对象
        Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();

		/*分页查询 */
        SQLQuery query = session.createSQLQuery(sbfSql.toString()).addScalar("id", Hibernate.INTEGER)
                .addScalar("pen", Hibernate.INTEGER).addScalar("deviceOID", Hibernate.STRING).addScalar("deviceModelName", Hibernate.STRING)
                .addScalar("deviceType", Hibernate.STRING).addScalar("deviceNameAbbr", Hibernate.STRING).addScalar("os", Hibernate.STRING)
                .addScalar("deviceIcon", Hibernate.STRING).addScalar("resCategoryID", Hibernate.INTEGER).addScalar("moClassID", Hibernate.BIG_DECIMAL)
                .addScalar("descr", Hibernate.STRING).addScalar("resCategoryName", Hibernate.STRING);
        //填充占位符
        query = setQueryParam(query, vo);
        //设置分页参数
        query.setFirstResult((int) ((flexiGridPageInfo.getPage() - 1) * flexiGridPageInfo.getRp()));
        query.setMaxResults(flexiGridPageInfo.getRp());
        //获取分页数据
        List<SnmpDeviceSysOIDInfo> list = query.setResultTransformer(Transformers.aliasToBean(SnmpDeviceSysOIDInfo.class)).list();

		/*总记录数查询 */
        String countHql = "select count(1) from (" + sbfSql.toString() + ") p";
        SQLQuery countQuery = session.createSQLQuery(countHql);
        //填充占位符
        countQuery = setQueryParam(countQuery, vo);
        //获取总记录数
        int count = ((Number) countQuery.uniqueResult()).intValue();

        //结果放入分页对象中
        flexiGridPageInfo.setRows(list);
        flexiGridPageInfo.setTotal(count);
        return flexiGridPageInfo;
    }

    /**
     * 填充占位符数据
     *
     * @param query
     * @param vo
     * @param flexiGridPageInfo
     * @return
     */
    public SQLQuery setQueryParam(SQLQuery query, SnmpDeviceSysOIDInfo vo) {
        //查询条件
        if (null != vo.getDeviceOID() && !"".equals(vo.getDeviceOID())) {
            query.setParameter("deviceOID", "%" + vo.getDeviceOID() + "%");
        }
        return query;
    }
}
