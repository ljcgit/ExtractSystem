package com.myFilter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.Permission;
import com.bean.User;
import com.dao.PermissionDao;
import com.dao.UserDao;
import com.dao.UserPermissionDao;

public class RoleFilter implements Filter{

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;//获取request对象
		HttpServletResponse response=(HttpServletResponse) arg1;//获取response对象
		HttpSession session=request.getSession();//获取session对象
		
		String urlPath = request.getRequestURI();
		boolean flag = urlPath.indexOf("/NewTask") > -1 || urlPath.indexOf("/NewTask") > -1 || urlPath.indexOf("/SingleExtract") > -1 || urlPath.indexOf("/MultipleExtract") > -1 || urlPath.indexOf("/OutExcelServlet") > -1 || urlPath.indexOf("/views/NewTask.jsp") > -1 || urlPath.indexOf("/HistoricalTask") > -1 || urlPath.indexOf("/InExcelServlet") > -1;
		if(!flag) {
			arg2.doFilter(request, response);
			return ;
		}
		
		String username = (String) session.getAttribute("username");
		if(username != null) {
	
			try {

				User user  = UserDao.getUser(username);
				List<Integer> plist = UserPermissionDao.getAllByUid(user.getId());
				Permission p = null;
				for(Integer t : plist) {
					p = PermissionDao.selectById(t);
					String s = "/ExtractSystem"+p.getUrl();
					if(urlPath.startsWith(s)) {
						arg2.doFilter(request, response);
						return ;
					}
				}
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.sendRedirect("/ExtractSystem/views/404.jsp");

	}

}
