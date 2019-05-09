package com.myServlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.RecordDao;
import com.dao.SquadDao;
import com.dao.TaskDao;

/**
 * Servlet implementation class RecordServlet
 */
@WebServlet("/RecordServlet")
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sqid = request.getParameter("sqid");
		String stid = request.getParameter("stid");
		System.out.println(sqid + ":"+stid);
		String[] sq = sqid.split(" ");
		String[] st = stid.split("&");

		boolean flag = false;
		for(int i =0;i<sq.length;i++) {
			flag = saveRecord(Integer.parseInt(sq[i]),st[i]);
		}
		
		try {
			int tid = SquadDao.getTid(Integer.parseInt(sq[0]));
			//将任务状态修改为已抽取
			TaskDao.changeState(tid, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag) {
			response.sendRedirect("/ExtractSystem");
		}else {
			response.sendRedirect("SingleExtract");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	/**
	 * 分别保存每个组的信息
	 */
	private boolean saveRecord(int sqid,String stid) {
		System.out.println(stid);
		String[] sid = stid.trim().split(" ");
		boolean flag = false;
		for(int i = 0;i < sid.length;i++) {
			try {
				flag = RecordDao.addRecord(Integer.parseInt(sid[i]), sqid);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
}
