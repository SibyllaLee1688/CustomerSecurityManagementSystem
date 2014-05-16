package com.elulian.CustomerSecurityManagementSystem.vo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="threshold", uniqueConstraints=@UniqueConstraint(columnNames={"risk_type"})) 
public class Threshold extends BaseObject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false,name="risk_id")
	private Integer id ;
	@Basic(optional=false)
	@Column(nullable=false,name="risk_type",unique = true)
	private String type;
	@Basic(optional=false)
	@Column(nullable=false,name="risk_value")
	private Integer value;
	@Basic
	private String remark;
	
	public Threshold(){
		
	}
	
	public Threshold(Integer id, String type, Integer value, String remark) {
		this.id = id;
		this.type = type;
		this.value = value;
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.getClass().getCanonicalName()).append("thresholdType").append(this.type).append("thresholdValue").append(this.value).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Threshold)) {
			return false;
		}

		final Threshold th = (Threshold) o;
		
		/*boolean equal = false;

		equal = !(type != null ? !type.equals(th.type) : th.type != null);
		
		equal = !(value != null ? !value.equals(th.value) : th.value != null);

		return equal;*/
		
		return !(type != null ? !type.equals(th.type) : th.type != null);
	}

	@Override
	public int hashCode() {
		/*int h = (type != null ? type.hashCode() : 0);
		
		h += (value != null ? value.hashCode() : 0);
		
		return h;*/
		
		return (type != null ? type.hashCode() : 0);
	}
	
	
}
