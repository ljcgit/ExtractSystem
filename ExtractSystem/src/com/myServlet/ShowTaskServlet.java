package com.myServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.Squad;
import com.bean.Staff;
import com.bean.Task;
import com.dao.RecordDao;
import com.dao.SquadDao;
import com.dao.StaffDao;
import com.dao.TaskDao;

/**
 * Servlet implementation class ShowTask
 */
@WebServlet("/ShowTaskServlet")
public class ShowTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowTaskServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			
			//������Ϣ
			Task task = TaskDao.getTask(id);
			//�������
			List<Squad> lists = SquadDao.getSquad(id);
			//��Ա���
			List<List<Integer>> l = new ArrayList<>();
			for(Squad s : lists) {
				l.add(RecordDao.getRecoredsBySqid(s.getId()));
			}
			
			List<List<Staff>> staffs = new ArrayList<>();
			for(List<Integer> t : l) {
				List<Staff> st = new ArrayList<>();
				for(Integer stid : t) {
					st.add(StaffDao.get(stid));
				}
				staffs.add(st);
			}
			//������Ϣ
			request.setAttribute("task",task);
			//С������
			request.setAttribute("squard",lists.get(0));
			//����
			request.setAttribute("groupCount", lists.size());
			//�������
			request.setAttribute("staffs", staffs);
			request.getRequestDispatcher("views/ShowTask.jsp").forward(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
