package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.bean.User;
import com.myUtil.JdbcUtil;

public class UserDao {
	/**
	 * 查询某一用户
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static  User getUser(int id) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement sta =  con.prepareStatement("select * from user where id = ?");
		sta.setInt(1, id);
		ResultSet re = sta.executeQuery();
		User user = null;
		while(re.next()) {
			user = new User();
			user.setId(re.getInt("id"));
			user.setUsername(re.getString("username"));
			user.setPassword(re.getString("password"));
		}
		JdbcUtil.close(con);
		return user;
	}


	
	public static  User getUser(String username) throws SQLException {
	    Connection con = JdbcUtil.getConn();
		PreparedStatement sta = con.prepareStatement("select * from user where username = ?");
		sta.setString(1,username);
		ResultSet re = sta.executeQuery();
		User user = null;
		while(re.next()) {
			user = new User();
			user.setId(re.getInt("id"));
			user.setUsername(re.getString("username"));
			user.setPassword(re.getString("password"));
		}
		JdbcUtil.close(con);
		return user;
	}
}
