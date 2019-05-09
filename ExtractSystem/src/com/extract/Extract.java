package com.extract;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bean.Staff;
import com.dao.StaffDao;


/**
 * ��ȡ��
 * @author LJC
 *
 */
/***
 * �����Ȼ�ȡ�������û���id����
 * ���ݸ�������Ŀ
 * @author LJC
 *
 */
public class Extract {

	private static List<Staff> getSquard(int num,List<Staff> staffs){
		//��������ʱ��ֱ�ӷ���
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
	 * ��ȡ��ȡ���
	 * @param total
	 * @param man
	 * @param woman
	 * @return
	 * @throws SQLException
	 */
	public static List<Staff> getStaffs(int total,int man,int woman) throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		staffs.addAll(getStaffsBySex(man,"��"));
		staffs.addAll(getStaffsBySex(woman,"Ů"));
		return staffs;
	}
	
	private static List<Staff> getStaffsBySex(int total,String sex) throws SQLException{
		List<Staff> staffs = StaffDao.getAll(0,sex);
		if(staffs.size()<total) {
			//���¿�ʼ
			List<Staff> extra = StaffDao.getAll(2,sex);
			StaffDao.setStatus(extra, 0);
			//��ȡ
			extra = getSquard(total-staffs.size(), extra);
			StaffDao.setStatus(extra,2);
			staffs.addAll(extra);
		}else {
			//��ȡ
			staffs = getSquard(total, staffs);
			StaffDao.setStatus(staffs, 2);
		}
		return staffs;
	}
}
