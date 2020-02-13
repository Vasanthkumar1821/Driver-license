package com.driver.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.driver.JDBCConnection.JDBCConnector;

@ManagedBean(name = "stateListReterival")
@SessionScoped
public class StateListReterival {

	private ArrayList<String> stateList = new ArrayList<String>();

	public ArrayList<String> getStateList() {
		return stateList;
	}

	public void setStateList(ArrayList<String> stateList) {
		this.stateList = stateList;
	}

	public ArrayList<String> getStateListDB() {

		try {
			Connection con = JDBCConnector.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from statelist");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				stateList.add(rs.getString("statename"));

			}
			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stateList;
	}
}
