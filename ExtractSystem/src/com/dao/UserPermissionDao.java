package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myUtil.JdbcUtil;

public class UserPermissionDao {

	/**
	 * 获取所有的权限id
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public static List<Integer> getAllByUid(int uid) throws SQLException{
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("select pid from user_permission where uid = ?");
		p.setInt(1, uid);
		ResultSet rs = p.executeQuery();
		List<Integer> re = new ArrayList<>();
		while(rs.next()) {
			re.add(rs.getInt("pid"));
		}
		return re;
	}
}
