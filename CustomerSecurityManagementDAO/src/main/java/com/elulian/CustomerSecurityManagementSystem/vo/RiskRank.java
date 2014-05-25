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
@Table(name="riskrank", uniqueConstraints=@UniqueConstraint(columnNames={"rankType"}))   
public class RiskRank extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1425736767627206841L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Basic(optional=false)
	@Column(unique=true)
	private String rankType;
	@Basic(optional=false)
	@Column(name="minVal")
	private Integer minValue;
	@Basic(optional=false)
	@Column(name="maxVal")
	private Integer maxValue;
	
	public RiskRank(){
		
	}
	
	public RiskRank(Integer id, String rankType, Integer minValue, Integer maxValue) {
		this.id = id;
		this.rankType = rankType;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRankType() {
		return rankType;
	}
	public void setRankType(String rankType) {
		this.rankType = rankType;
	}
	public Integer getMinValue() {
		return minValue;
	}
	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}
	public Integer getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	
	 /**
     * {@inheritDoc}
     */
	@Override
    public boolean equals(Object o) {
    	
    	/*if(null == o)
    		return false;*/
    	
        if (this == o) 
            return true;
            		
        if (!(o instanceof RiskRank)) {
            return false;
        }

        final RiskRank rr = (RiskRank) o;

        return !(rankType != null ? !rankType.equals(rr.rankType) : rr.rankType != null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (rankType != null ? rankType.hashCode() : 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new StringBuilder().append(this.getClass().getCanonicalName()).append(" rankType: ").append(this.rankType).append(" riskMinValue: ").append(this.minValue).append(" rankMaxValue: ").append(this.maxValue)
                .toString();
    }
}
