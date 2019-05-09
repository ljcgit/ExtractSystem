package com.helper;

import java.util.ArrayList;
import java.util.List;

import com.bean.Staff;

public class IdHelper {

	/**
	 * 将数组的id拼接成字符串
	 * @param staffs
	 * @return
	 */
	public static String toIdString(List<Staff> staffs) {
		if(staffs == null || staffs.size()<1) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		s.append(Integer.toString(staffs.get(0).getId()));
		for(int i = 1;i<staffs.size();i++) {
			s.append(" ").append(Integer.toString(staffs.get(i).getId()));
		}
		return s.toString();
	}
	
	public static String getPreString(int num) {
		if(num<1)
			return "";
		StringBuilder s = new StringBuilder();
		s.append("?");
		for(int i = 1;i < num;i++) {
			s.append(",?");
		}
		return s.toString();
		
	}
	
	public static String getIdString(List<Integer> lists) {
		if(lists == null || lists.size()<1) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		s.append(Integer.toString(lists.get(0)));
		for(int i = 1;i<lists.size();i++) {
			s.append(" ").append(Integer.toString(lists.get(i)));
		}
		return s.toString();
	}
	
	public static List<Integer> stringToList(String s){
		List<Integer> lists = new ArrayList<>();
		for(String t : s.split(" ")) {
			lists.add(Integer.parseInt(t));
		}
		return lists;
	}
}
