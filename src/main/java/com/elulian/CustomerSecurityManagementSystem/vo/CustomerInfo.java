package com.elulian.CustomerSecurityManagementSystem.vo;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="khxx",uniqueConstraints=@UniqueConstraint(columnNames={"customerId"}))
public class CustomerInfo extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1587017290777888159L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Basic(optional=false)
	private String customerId;
	@Basic
	private String customerName;
	@Basic
	private String nationality;
	@Basic
	private String certificateType;
	@Basic
	private String certificateId;
	@Basic
	private Date certificateBeginDate;
	@Basic
	private Date certificateEndDate;
	@Basic
	private String mobileNumber;
	@Basic
	private String phoneNumber;
	@Basic
	private Boolean foreignFlag;
	@Basic
	private String sex;
	@Basic
	private String birthday;
	@Basic
	private String zipcode;
	@Basic
	private String certificateAddress;
	@Basic
	private String address;
	@Basic
	private String relationcertificateId;
	@Basic
	private String relationcertificateType;
	@Basic
	private String instrperName;
	@Basic
	private String customerType;
	@Basic
	private String professionCode;
	@Basic
	private String branch;
	@Basic
	private Integer riskValue;
	@Basic
	private String riskType;
	@Basic
	private String remark;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateId() {
		return certificateId;
	}
	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}
	public Date getCertificateBeginDate() {
		return certificateBeginDate;
	}
	public void setCertificateBeginDate(Date certificateBeginDate) {
		this.certificateBeginDate = certificateBeginDate;
	}
	public Date getCertificateEndDate() {
		return certificateEndDate;
	}
	public void setCertificateEndDate(Date certificateEndDate) {
		this.certificateEndDate = certificateEndDate;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Boolean isForeignFlag() {
		return foreignFlag;
	}
	public void setForeignFlag(Boolean foreignFlag) {
		this.foreignFlag = foreignFlag;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCertificateAddress() {
		return certificateAddress;
	}
	public void setCertificateAddress(String certificateAddress) {
		this.certificateAddress = certificateAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getRelationcertificateId() {
		return relationcertificateId;
	}
	public void setRelationcertificateId(String relationcertificateId) {
		this.relationcertificateId = relationcertificateId;
	}
	public String getRelationcertificateType() {
		return relationcertificateType;
	}
	public void setRelationcertificateType(String relationcertificateType) {
		this.relationcertificateType = relationcertificateType;
	}
	public String getInstrperName() {
		return instrperName;
	}
	public void setInstrperName(String instrperName) {
		this.instrperName = instrperName;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getProfessionCode() {
		return professionCode;
	}
	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}
	public Integer getRiskValue() {
		return riskValue;
	}
	public void setRiskValue(Integer riskValue) {
		this.riskValue = riskValue;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getForeignFlag() {
		return foreignFlag;
	}
	   /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
    	
    	/*if(null == o)
    		return false;*/
    	
        if (this == o) 
            return true;
            		
        if (!(o instanceof CustomerInfo)) {
            return false;
        }

        final CustomerInfo ci = (CustomerInfo) o;

        return !(customerId != null ? !customerId.equals(ci.customerId) : ci.customerId != null);

    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return (customerId != null ? customerId.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new StringBuilder().append(this.getClass().getCanonicalName()).append("customerId").append(this.customerId)
                .toString();
    }	
	
}
