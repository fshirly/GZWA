package com.fable.insightview.platform.entity.announcement;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fable.insightview.platform.itsm.core.format.CustomDateTimeSerializer;

/**
 * @author  : ZhangYa
 * @version : eclipse kepler
 * @datetime: 2014年4月10日 下午2:13:33
 */
@Entity
@Table(name = "ItsmAnnouncement")
public class Announcement extends com.fable.insightview.platform.itsm.core.entity.Entity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "itsmAnnouncement_gen")
	@TableGenerator(initialValue=INIT_VALUE, name = "itsmAnnouncement_gen", table = "SysKeyGenerator", pkColumnName = "GenName", 
	 valueColumnName = "GenValue", pkColumnValue = "ItsmAnnouncementPK", allocationSize = 1)
	@Column(name = "Id")
	private Integer id;
	
	@Column(name = "Title")
	private String title;
	
	@Column(name = "Summary")
	private String summary;
	
	@Column(name = "TypeId")
	private Integer typeId;
	
	@Column(name = "Creater")
	private Integer creater;
	
	@Transient
	private String creator;
	
	@Column(name = "CreateTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	private Date createTime;
	
	@Column(name = "DeadLine")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	private Date deadLine;
	
	@Transient
	private Integer deadLineInt;

	public Integer getDeadLineInt() {
		return deadLineInt;
	}

	public void setDeadLineInt(Integer deadLineInt) {
		this.deadLineInt = deadLineInt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
}
