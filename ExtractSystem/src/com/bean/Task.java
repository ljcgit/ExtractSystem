package com.bean;

import java.util.Date;

public class Task {

	
	private int id;
	private String title;
	private Date startDate;
	private Date endDate;
	private String intro;
	private int status;
	
	public Task() {}

	
	
	public Task(int id, String title, Date startDate, Date endDate, String intro) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.intro = intro;
	}





	public Task(int id, String title, Date startDate, Date endDate, String intro, int status) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.intro = intro;
		this.status = status;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}



	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate + ", intro="
				+ intro + ", status=" + status + "]";
	}



	

	
	
	
}
