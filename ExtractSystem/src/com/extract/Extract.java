package com.extract;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bean.Staff;
import com.dao.StaffDao;


/**
 * 抽取类
 * @author LJC
 *
 */
/***
 * 首先先获取到所有用户的id数组
 * 根据给定的数目
 * @author LJC
 *
 */
public class Extract {

	private static List<Staff> getSquard(int num,List<Staff> staffs){
		//人数不足时，直接返回
		if(staffs.size()<num) {
			return staffs;
		}
		int size = staffs.size();
		for(int i = 0;i<size-num;i++) {
			staffs.remove(getRandomIndex(staffs.size()));
		}
		return staffs;
	}
	
	
	private static int getRandomIndex(int len) {
		Random random = new Random();
		return random.nextInt(len);
	}
	
	/**
	 * 获取抽取结果
	 * @param total
	 * @param man
	 * @param woman
	 * @return
	 * @throws SQLException
	 */
	public static List<Staff> getStaffs(int total,int man,int woman) throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		staffs.addAll(getStaffsBySex(man,"男"));
		staffs.addAll(getStaffsBySex(woman,"女"));
		return staffs;
	}
	
	private static List<Staff> getStaffsBySex(int total,String sex) throws SQLException{
		List<Staff> staffs = StaffDao.getAll(0,sex);
		if(staffs.size()<total) {
			//从新开始
			List<Staff> extra = StaffDao.getAll(2,sex);
			StaffDao.setStatus(extra, 0);
			//抽取
			extra = getSquard(total-staffs.size(), extra);
			StaffDao.setStatus(extra,2);
			staffs.addAll(extra);
		}else {
			//抽取
			staffs = getSquard(total, staffs);
			StaffDao.setStatus(staffs, 2);
		}
		return staffs;
	}
}
