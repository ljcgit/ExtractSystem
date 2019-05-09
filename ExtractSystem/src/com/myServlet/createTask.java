package com.myServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Task;
import com.dao.TaskDao;

/**
 * Servlet implementation class createTask
 */
@WebServlet("/createTask")
public class createTask extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createTask() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); //1
		response.setContentType("text/html;charset=utf-8"); //2
		response.setCharacterEncoding("utf-8"); //3
		Task task = new Task();
		task.setTitle(request.getParameter("title"));
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			task.setStartDate(dateFormat1.parse(request.getParameter("startDate")));
			task.setEndDate(dateFormat1.parse(request.getParameter("endDate")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.setIntro(request.getParameter("intro"));
		boolean flag = false;
		try {
			flag = TaskDao.addTask(task);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(flag) {
			response.sendRedirect("index.jsp");
		}else {
			response.sendRedirect("views/NewTask.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
