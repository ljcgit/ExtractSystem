package com.bean;

public class Squad {

	private int id;
	private int tid;
	private int total;
	private int man;
	private int woman;
	
	public Squad() {}

	public Squad(int id, int tid, int total, int man, int woman) {
		super();
		this.id = id;
		this.tid = tid;
		this.total = total;
		this.man = man;
		this.woman = woman;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getMan() {
		return man;
	}

	public void setMan(int man) {
		this.man = man;
	}

	public int getWoman() {
		return woman;
	}

	public void setWoman(int woman) {
		this.woman = woman;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", tid=" + tid + ", total=" + total + ", man=" + man + ", woman=" + woman + "]";
	}
	
	
}
