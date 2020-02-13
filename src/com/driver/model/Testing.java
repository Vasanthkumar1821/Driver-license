package com.driver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.avps.model.Driver;
import com.driver.JDBCConnection.JDBCConnector;

public class Testing {

	public void checkEligibility(int driverage, String driverstate, String drivercity, int driverexperience,
			Date lastdrivingtestdate, int familyMembersCount, String revocationCheck, Date licenseRevokedDate)
			throws Exception {

		Driver d = new Driver();
		d.setAge(driverage);
		d.setState(driverstate);
		d.setCity(drivercity);
		d.setExperience(driverexperience);
		d.setLastTestDate(lastdrivingtestdate);
		d.setFamilyMembersCount(familyMembersCount);
		d.setRevocationCheck(revocationCheck);
		d.setLicenseRevokedDate(licenseRevokedDate);
		DriverREST dr = new DriverREST();
		d = dr.restServiceMethod(d);
		System.out.println("success testing");

	}

	public void checkEligibility(String emailDriver) throws Exception {
		Driver d = new Driver();
		Date da = new Date();
		Connection con = JDBCConnector.getConnection();
		PreparedStatement ps = con
				.prepareStatement("select * from driverpersonaldata where driveremail='" + emailDriver + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			da = rs.getDate("lastdrivingtestdate");

			d.setAge(rs.getInt("driverage"));
			d.setState(rs.getString("driverstate"));
			d.setCity(rs.getString("drivercity"));
			d.setExperience(rs.getInt("driverexperience"));
			d.setLastTestDate(rs.getDate("lastdrivingtestdate"));
		}
		rs.close();
		ps.close();
		con.close();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String t = "2020-02-11";

		Date date = new Date();

		date = sdf.parse(t);

		System.out.println("Date====" + da);
		d.setFamilyMembersCount(3);
		d.setRevocationCheck("yes");
		d.setLicenseRevokedDate(date);
		DriverREST dr = new DriverREST();
		d = dr.restServiceMethod(d);
		System.out.println("success testing");
	}

	public static Date convertToLocalDateViaSqlDate(Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime());
	}

	public static void main(String[] args) {
		Date dateToConvert = new Date();
		System.out.println(Testing.convertToLocalDateViaSqlDate(dateToConvert));
	}
}
