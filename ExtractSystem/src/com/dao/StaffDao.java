package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.Staff;
import com.helper.IdHelper;
import com.myUtil.JdbcUtil;

public class StaffDao {

	
	public static List<Staff> getAll() throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		Connection con = JdbcUtil.getConn();
		Statement sta = con.createStatement();
		ResultSet rs = sta.executeQuery("select * from staff");
		Staff s = null;
		while(rs.next()) {
			s = new Staff();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSex(rs.getString("sex"));
			s.setPhone(rs.getString("phone"));
			s.setStatus(rs.getInt("status"));
			staffs.add(s);
		}
		JdbcUtil.close(con);
		return staffs;
	}
	
	public static List<Staff> getAllByLimit(int start,int num) throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("select * from staff limit ?,?");
		ps.setInt(1, start);
		ps.setInt(2, start+num);
		ResultSet rs = ps.executeQuery();
		Staff s = null;
		while(rs.next()) {
			s = new Staff();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSex(rs.getString("sex"));
			s.setPhone(rs.getString("phone"));
			s.setStatus(rs.getInt("status"));
			staffs.add(s);
		}
		JdbcUtil.close(con);
		return staffs;
	}
	
	public static List<Staff> getAll(int status) throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("select * from staff where status = ?");
		ps.setInt(1, status);
		ResultSet rs = ps.executeQuery();
		Staff s = null;
		while(rs.next()) {
			s = new Staff();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSex(rs.getString("sex"));
			s.setPhone(rs.getString("phone"));
			s.setStatus(rs.getInt("status"));
			staffs.add(s);
		}
		JdbcUtil.close(con);
		return staffs;
	}
	
	public static List<Staff> getAll(int status,String sex) throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		String sql = "select * from staff where status = ? and sex = ?";
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, status);
		ps.setString(2, sex);
		ResultSet rs = ps.executeQuery();
		Staff s = null;
		while(rs.next()) {
			s = new Staff();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSex(rs.getString("sex"));
			s.setPhone(rs.getString("phone"));
			s.setStatus(rs.getInt("status"));
			staffs.add(s);
		}
		JdbcUtil.close(con);
		return staffs;
	}
	
	public static Staff get(int id) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("select * from staff where id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		Staff s = null;
		while(rs.next()) {
			s = new Staff();
			s.setId(rs.getInt("id"));
			s.setName(rs.getString("name"));
			s.setSex(rs.getString("sex"));
			s.setPhone(rs.getString("phone"));
			s.setStatus(rs.getInt("status"));
		}
		JdbcUtil.close(con);
		return s;
	}
	
	
	/**
	 * 修改状态信息
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public static boolean setStatus(int id,int status) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("update staff set status = ? where id = ?");
		ps.setInt(1, status);
		ps.setInt(2, id);
		int count = ps.executeUpdate();
		if(count>0) {
			return true;
		}
		return false;
	}
	
	public static boolean setStatus(List<Staff> staffs,int status) throws SQLException {		
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("update staff set status = ? where id in ("+IdHelper.getPreString(staffs.size())+")");
		ps.setInt(1, status);
		for(int i = 0;i<staffs.size();i++) {
			ps.setInt(i+2,staffs.get(i).getId());
		}
		int count = ps.executeUpdate();
		if(count>0) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取staff总数目
	 * @return
	 * @throws SQLException
	 */
	public static int getTotal() throws SQLException {
		Connection con = JdbcUtil.getConn();
		Statement sta = con.createStatement();
		ResultSet rs = sta.executeQuery("select count(1) from staff"); 
		if(rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}
	
	/**
	 * 获取所有可参加的人数
	 * @return
	 * @throws SQLException
	 */
	public static int getTotalEnterable() throws SQLException {
		Connection con = JdbcUtil.getConn();
		Statement sta = con.createStatement();
		ResultSet rs = sta.executeQuery("select count(1) from staff where status = 0 or status = 2"); 
		if(rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}
	
	/**
	 * 添加staff
	 * @param staff
	 * @return
	 * @throws SQLException
	 */
	public static boolean addStaff(Staff staff) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("insert into staff(name,sex,phone) values(?,?,?)");
		ps.setString(1, staff.getName());
		ps.setString(2, staff.getSex());
		ps.setString(3, staff.getPhone());
		int count = ps.executeUpdate();
		if(count>0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 删除人员
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static boolean deleteStaff(int id) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement ps = con.prepareStatement("delete from staff where id = ?");
		ps.setInt(1, id);
		int count = ps.executeUpdate();
		if(count > 0) {
			return true;
		}
		return false;
		
	}
	
}
