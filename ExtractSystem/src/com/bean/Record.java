package com.bean;

public class Record {

	
	private int id;
	private int sqid;
	private int stid;
	
	public Record() {}

	public Record(int id, int sqid, int stid) {
		super();
		this.id = id;
		this.sqid = sqid;
		this.stid = stid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSqid() {
		return sqid;
	}

	public void setSqid(int sqid) {
		this.sqid = sqid;
	}

	public int getStid() {
		return stid;
	}

	public void setStid(int stid) {
		this.stid = stid;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", sqid=" + sqid + ", stid=" + stid + "]";
	}
	
	
}
