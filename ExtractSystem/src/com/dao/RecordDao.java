package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bean.Record;
import com.myUtil.JdbcUtil;

public class RecordDao {

	public static boolean addRecord(Record record) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("insert into record(stid,sqid) values (?,?)");
		p.setInt(1, record.getStid());
		p.setInt(2, record.getSqid());
		int count = p.executeUpdate();
		if(count>0) {
			return true;
		}
		return false;
	}
	
	public static boolean addRecord(int stid,int sqid) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("insert into record(stid,sqid) values (?,?)");
		p.setInt(1, stid);
		p.setInt(2, sqid);
		int count = p.executeUpdate();
		if(count>0) {
			return true;
		}
		return false;
	}
	
	public static List<Integer> getRecoredsBySqid(int sqid) throws SQLException{
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("select stid from record where sqid = ?");
		p.setInt(1, sqid);
		ResultSet rs = p.executeQuery();
		List<Integer> re = new ArrayList<>();
		while(rs.next()) {
			re.add(rs.getInt("stid"));
		}
		return re;
	}
}
