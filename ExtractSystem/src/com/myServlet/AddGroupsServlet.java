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
import com.dao.SquadDao;
import com.dao.StaffDao;
import com.dao.TaskDao;
import com.extract.Extract;
import com.helper.IdHelper;

/**
 * Servlet implementation class AddGroupsServlet
 */
@WebServlet("/AddGroupsServlet")
public class AddGroupsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddGroupsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Squad group = new Squad();
		int tid = Integer.parseInt(request.getParameter("tid"));
		int groupCount = Integer.parseInt(request.getParameter("groupCount"));
		System.out.println("groupCount"+groupCount);
		int total = Integer.parseInt(request.getParameter("total"));
		int man = Integer.parseInt(request.getParameter("man"));
		int woman = Integer.parseInt(request.getParameter("woman"));
		group.setTid(tid);
		group.setTotal(total);
		group.setMan(man);	
		group.setWoman(woman);
		//�����Ҫ���������ܿ��Բμ���������ֱ�ӷ���
		try {
			if(total!=man+woman || total*groupCount>StaffDao.getTotalEnterable()) {
				request.setAttribute("msg", "�����Ƿ���");
			}else {
				boolean flag = false;
				List<Integer> id = new ArrayList<>();
				if(request.getParameter("id")==null) {
					//������ͬʱ����
					for(int i=0;i<groupCount;i++) {
						flag = SquadDao.addSquad(group);
					}
					//��ȡ����id
					id = SquadDao.getIdList(tid);
				}else {
					id = IdHelper.stringToList(request.getParameter("id"));
					flag = true;
				}
				if(flag && id.size()>=1) {
					request.setAttribute("squard", group);
					//��������id
					request.setAttribute("id", IdHelper.getIdString(id));
					request.setAttribute("groupCount", groupCount);
					List<List<Staff>> staffs = new ArrayList<>();
					StringBuilder  sd = new StringBuilder();
					for(int i = 0;i < groupCount ;i++) {
						//���ó�ȡ�ɹ�����Ա����
						List<Staff> l = Extract.getStaffs(total,man,woman);
						staffs.add(l);
						sd.append(IdHelper.toIdString(l)).append(" & ");
					}
					//�������
					request.setAttribute("staffs", staffs);
					//��Ա�������
					request.setAttribute("stid", sd.toString().substring(0,sd.toString().length()-3));
				}else {
					request.setAttribute("msg", "��ȡʧ�ܣ�");
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Task> tasks = null;
		try {
			tasks = TaskDao.getAllAfterTitle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("tasks", tasks);
		request.getRequestDispatcher("views/MultipleExtract.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
