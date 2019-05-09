package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.Squad;
import com.myUtil.JdbcUtil;

public class SquadDao {

	
	public static boolean addSquad(Squad s) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement sta = con.prepareStatement("insert into squad(tid,total,man,woman) values(?,?,?,?)" );
		sta.setInt(1, s.getTid());
		sta.setInt(2, s.getTotal());
		sta.setInt(3, s.getMan());
		sta.setInt(4,s.getWoman());
		int count = sta.executeUpdate();
		JdbcUtil.close(con);
		if(count>0)
			return true;
		return false;
	}
	
	public static List<Squad> getSquad(int tid) throws SQLException{
		Connection con = JdbcUtil.getConn();
		PreparedStatement sta = con.prepareStatement("select * from squad where tid=?");
		sta.setInt(1, tid);
		ResultSet rs = sta.executeQuery();
		List<Squad> groups = new ArrayList<>();
		Squad group = null;
		while(rs.next()) {
			group = new Squad();
			group.setId(rs.getInt("id"));
			group.setTid(rs.getInt("tid"));
			group.setTotal(rs.getInt("total"));
			group.setMan(rs.getInt("man"));
			group.setWoman(rs.getInt("woman"));
			groups.add(group);
		}
		JdbcUtil.close(con);
		return groups;
	}
	
	public static int getId(int tid) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement sta = con.prepareStatement("select id from squad where tid=?");
		sta.setInt(1, tid);
		ResultSet rs = sta.executeQuery();
		int re = -1;
		while(rs.next()) {
			re = rs.getInt(1);
		}
		JdbcUtil.close(con);
		return re;
	}
	
	public static int getTid(int id) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement sta = con.prepareStatement("select tid from squad where id=?");
		sta.setInt(1, id);
		ResultSet rs = sta.executeQuery();
		int re = -1;
		while(rs.next()) {
			re = rs.getInt(1);
		}
		JdbcUtil.close(con);
		return re;
	}
	
	/**
	 * 获取某个任务的所有id集合
	 * @param tid
	 * @return
	 * @throws SQLException
	 */
	public static List<Integer> getIdList(int tid) throws SQLException{
		List<Integer> lists = new ArrayList<>();
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("select id from squad where tid = ?");
		p.setInt(1,tid);
		ResultSet rs = p.executeQuery();
		while(rs.next()) {
			lists.add(rs.getInt(1));
		}
		JdbcUtil.close(con);
		return lists;
	}
}
