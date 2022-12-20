package com.gaurav.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String Country;
	private float cases;
	private String variants;
	private String cCodes;
	private float noDetVar;
	

	private String yearWeek;
	public User() {
		super();
	}
	public User(String Country, float cases, String variants, String yearWeek, String cCodes, float noDetVar) {
		super();
		this.Country = Country;
		this.cases = cases;
		this.yearWeek = yearWeek;
		this.variants = variants;
		this.cCodes = cCodes;
		this.noDetVar = noDetVar;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String Country) {
		this.Country = Country;
	}
	public float getCases() {
		return cases;
	}
	public String getCCodes() {
		return cCodes;
	}
	public void setCases(float cases) {
		this.cases = cases;
	}
	public String getVariants() {
		return variants;
	}
	public void setVariants(String variants) {
		this.variants = variants;
	}
	public String getyearWeek() {
		return yearWeek;
	}
	public void setyearWeek(String yearWeek) {
		this.yearWeek = yearWeek;
	}
	public void setCCodes(String cCodes) {
		this.cCodes = cCodes;
	}
	public float getNoDetVar() {
		return this.noDetVar;
	}
	public void setNoDetVar(float noDetVar) {
		this.noDetVar = noDetVar;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", Country=" + Country + ", cases=" + cases + ", countryCodes=" + cCodes + ", noDetVar=" + noDetVar + ", variants=" + variants + ", yearWeek=" + yearWeek + "]";
	}
}


















//private String country;
//private String yearWeek;
//private float cases;
//
//public User() {
//	super();
//}
//
//public User(String country, String yearWeek, float cases) {
//	super();
//	this.country = country;
//	this.yearWeek = yearWeek;
//	this.cases = cases;
//}
//public String getCountry() {
//	return country;
//}
//public void setCountry(String country) {
//	this.country = country;
//}
//public String getYearWeek() {
//	return yearWeek;
//}
//public void setCountry(String yearWeek) {
//	this.yearWeek = yearWeek;
//}
//public float getCases() {
//	return cases;
//}
//public void setCases(float cases) {
//	this.cases = cases;
//}
//
//@Override
//public String toString() {
//	return "User [country=" + country + ", yearWeek=" + yearWeek + ", cases=" + cases + "]";
//}
