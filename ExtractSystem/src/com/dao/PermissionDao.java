package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.Permission;
import com.myUtil.JdbcUtil;

public class PermissionDao {

	
	public static Permission selectById(int id) throws SQLException {
		Connection  con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("select * from permission where id = ?");
		p.setInt(1, id);
		ResultSet rs = p.executeQuery();
		Permission permission = new Permission();
		while(rs.next()) {
			permission.setId(id);
			permission.setName(rs.getString("name"));
			permission.setUrl(rs.getString("url"));
		}
		return permission;
	}
}
