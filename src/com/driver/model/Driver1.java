package com.driver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.avps.model.Driver;
import com.driver.JDBCConnection.JDBCConnector;

// this is a pojo class

@ManagedBean(name = "driverData")
@SessionScoped
public class Driver1 {

	private String driverName;

	private String emailDriver;

	private String driverGender;

	private int age;

	private String state;

	private int experience;

	private String city;

	private boolean eligibility;

	private String licenseCategory;

	private Date lastTestDate;

	private boolean driverTestEligibility;

	private boolean driverLocationEligibility;

	// Total family Members Count
	private int familyMembersCount;

	// license revoked check true or false
	private boolean familyBackgroundCheckEligibility;

	// license revoked date of a family member
	private Date licenseRevokedDate;

	private String revocationCheck;

	public String getRevocationCheck() {
		return revocationCheck;
	}

	public void setRevocationCheck(String revocationCheck) {
		this.revocationCheck = revocationCheck;
	}

	public int getFamilyMembersCount() {
		return familyMembersCount;
	}

	public void setFamilyMembersCount(int familyMembersCount) {
		this.familyMembersCount = familyMembersCount;
	}

	public boolean isFamilyBackgroundCheckEligibility() {
		return familyBackgroundCheckEligibility;
	}

	public void setFamilyBackgroundCheckEligibility(boolean familyBackgroundCheckEligibility) {
		this.familyBackgroundCheckEligibility = familyBackgroundCheckEligibility;
	}

	/*
	 * public Date getLicenseRevokedDate() { return licenseRevokedDate; }
	 * 
	 * public void setLicenseRevokedDate(Date licenseRevokedDate) {
	 * this.licenseRevokedDate = licenseRevokedDate; }
	 */

	public Date getLastTestDate() {
		return lastTestDate;
	}

	public void setLastTestDate(Date lastTestDate) {
		this.lastTestDate = lastTestDate;
	}

	public Date getLicenseRevokedDate() {
		return licenseRevokedDate;
	}

	public void setLicenseRevokedDate(Date licenseRevokedDate) {
		this.licenseRevokedDate = licenseRevokedDate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getCity() {
		return city;
	}

	public String getDriverGender() {
		return driverGender;
	}

	public void setDriverGender(String driverGender) {
		this.driverGender = driverGender;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmailDriver() {
		return emailDriver;
	}

	public void setEmailDriver(String emailDriver) {
		this.emailDriver = emailDriver;
	}

	public boolean isEligibility() {
		return eligibility;
	}

	public void setEligibility(boolean eligibility) {
		this.eligibility = eligibility;
	}

	public String getLicenseCategory() {
		return licenseCategory;
	}

	public void setLicenseCategory(String licenseCategory) {
		this.licenseCategory = licenseCategory;
	}

	/*
	 * public Date getLastTestDate() { return lastTestDate; }
	 * 
	 * public void setLastTestDate(Date lastTestDate) { this.lastTestDate =
	 * lastTestDate; }
	 */

	public boolean isDriverTestEligibility() {
		return driverTestEligibility;
	}

	public void setDriverTestEligibility(boolean driverTestEligibility) {
		this.driverTestEligibility = driverTestEligibility;
	}

	public boolean isDriverLocationEligibility() {
		return driverLocationEligibility;
	}

	public void setDriverLocationEligibility(boolean driverLocationEligibility) {
		this.driverLocationEligibility = driverLocationEligibility;
	}

	public String add() throws ClassNotFoundException, SQLException {

		Connection con = JDBCConnector.getConnection();
		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO driverpersonaldata(drivername, driveremail, drivergender, driverage, driverstate,drivercity, driverexperience) VALUES(?,?,?,?,?,?,?)");
		ps.setString(1, driverName);
		ps.setString(2, emailDriver);
		ps.setString(3, driverGender);
		ps.setInt(4, age);
		ps.setString(5, state);
		ps.setString(6, city);
		ps.setInt(7, experience);
		// ps.setDate(8, lastTestDate);
		ps.executeUpdate();
		ps.close();
		con.close();
//		System.out.println(driverName+"email=="+emailDriver);
		System.out.println("success");
		return "save";
	}

	// private Driver d = new Driver();

	public String checkEligibility() throws Exception {
		Driver d = new Driver();
		String ee = null;
		Connection con = JDBCConnector.getConnection();
		PreparedStatement ps = con
				.prepareStatement("select * from driverpersonaldata where driveremail='" + emailDriver + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ee = rs.getString("driveremail");
		//	da = rs.getDate("lastdrivingtestdate");

			d.setAge(rs.getInt("driverage"));
			d.setState(rs.getString("driverstate"));
			d.setCity(rs.getString("drivercity"));
			d.setExperience(rs.getInt("driverexperience"));
			d.setLastTestDate(rs.getDate("lastdrivingtestdate"));
		}
		rs.close();
		ps.close();
		con.close();
		if (emailDriver.equals(ee)) {
			System.out.println("Success==" + ee);

			System.out.println("revoke date==" + licenseRevokedDate);

			d.setFamilyMembersCount(familyMembersCount);
			d.setRevocationCheck(revocationCheck);
			if (revocationCheck.equals("yes")) {
				d.setLicenseRevokedDate(Driver1.convertToDateViaSqlDate(licenseRevokedDate));
			} else {
				d.setLicenseRevokedDate(null);
			}

			DriverREST dr = new DriverREST();
			d = dr.restServiceMethod(d);

			eligibility = d.isEligibility();
			driverTestEligibility = d.isDriverTestEligibility();
			driverLocationEligibility = d.isDriverLocationEligibility();
			licenseCategory = d.getLicenseCategory();
			familyBackgroundCheckEligibility = d.isFamilyBackgroundCheckEligibility();

			return "CheckEligibility";
		} else {

			System.out.println("failure");
			return "EmailError";
		}
	}

	public static Date convertToDateViaSqlDate(Date dateToConvert) {
		dateToConvert = new java.sql.Date(dateToConvert.getTime());
		System.out.println("sql date convertor===" + dateToConvert);
		return dateToConvert;
	}

	public void clearData() {
		setAge(0);
		setCity(null);
		setDriverGender(null);
		setDriverName(null);
		setEmailDriver(null);
		setExperience(0);
		setFamilyMembersCount(0);
		setState(null);
	}

}