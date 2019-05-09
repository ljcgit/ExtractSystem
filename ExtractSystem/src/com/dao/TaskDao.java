package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.Task;
import com.myUtil.JdbcUtil;

public class TaskDao {

	public static boolean addTask(Task task) throws SQLException {
		System.out.println(task);
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("insert into task(title,startDate,endDate,intro) values (?,?,?,?)");
		p.setString(1, task.getTitle());
		p.setDate(2, new Date(task.getStartDate().getTime()));
		p.setDate(3, new Date(task.getEndDate().getTime()));
		p.setString(4, task.getIntro());
		int count = p.executeUpdate();
		JdbcUtil.close(con);
		if(count>0)
			return true;
		return false;
	}
	
	public static Task getTask(int id) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("select * from task where id = ?");
		p.setInt(1, id);
		ResultSet rs = p.executeQuery();
		Task task = null;
		while(rs.next()) {
			task = new Task();
			task.setId(id);
			task.setTitle(rs.getString("title"));
			task.setStartDate(rs.getDate("startDate"));
			task.setEndDate(rs.getDate("endDate"));
			task.setIntro(rs.getString("intro"));
			task.setStatus(rs.getInt("status"));
		}
		JdbcUtil.close(con);
		return task;
	}
	
	public static List<Task> getAll() throws SQLException{
		List<Task> tasks = new ArrayList<>();
		Connection con = JdbcUtil.getConn();
		Statement sta = con.createStatement();
		ResultSet rs = sta.executeQuery("select * from task");
		Task task = null;
		while(rs.next()) {
			task = new Task();
			task.setId(rs.getInt("id"));
			task.setTitle(rs.getString("title"));
			task.setStartDate(rs.getDate("startDate"));
			task.setEndDate(rs.getDate("endDate"));
			task.setIntro(rs.getString("intro"));
			task.setStatus(rs.getInt("status"));
			tasks.add(task);
		}
		JdbcUtil.close(con);
		return tasks;
	}
	
	/**
	 * 获取到所有还未开始的同时未抽取的任务列表
	 * @return
	 * @throws SQLException
	 */
	public static List<Task> getAllAfterTitle() throws SQLException{
		List<Task> tasks = new ArrayList<>();
		Connection con = JdbcUtil.getConn();
		Statement sta = con.createStatement();
		ResultSet rs = sta.executeQuery("SELECT id,title FROM task WHERE TO_DAYS(startDate) - TO_DAYS(NOW()) > 0 and status = 0");
		Task task = null;
		while(rs.next()) {
			task = new Task();
			task.setId(rs.getInt("id"));
			task.setTitle(rs.getString("title"));
			tasks.add(task);
		}
		JdbcUtil.close(con);
		return tasks;
	}
	
	/**
	 * 修改任务状态
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public static boolean changeState(int id,int status) throws SQLException {
		Connection con = JdbcUtil.getConn();
		PreparedStatement p = con.prepareStatement("update task set status = ? where id = ?");
		p.setInt(1, status);
		p.setInt(2, id);
		int count = p.executeUpdate();
		if(count > 0) {
			return true;
		}
		return false;
	}
}
