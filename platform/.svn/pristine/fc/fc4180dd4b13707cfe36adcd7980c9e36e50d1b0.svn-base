package com.fable.insightview.platform.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import org.hibernate.annotations.Entity;


@Entity
public class OrgDeptProviderTreeBean extends  com.fable.insightview.platform.itsm.core.entity.Entity
		implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String parentId;
	private String name;
	private String type;
	@Transient
	private int count;
	
	@Transient
	private boolean  isOrg;
	
	
	public boolean isOrg() {
		return isOrg;
	}
	public void setOrg(boolean isOrg) {
		this.isOrg = isOrg;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public OrgDeptProviderTreeBean() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
