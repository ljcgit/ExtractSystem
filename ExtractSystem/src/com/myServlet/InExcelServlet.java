package com.myServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bean.Staff;
import com.dao.StaffDao;
import com.excelHelper.PoiRead;

/**
 * Servlet implementation class InExcelServlet
 */
@WebServlet("/InExcelServlet")
public class InExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InExcelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
        //�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
        String savePath = "E:/upload";
        System.out.println(savePath);
        File file = new File(savePath);
        if(!file.exists()&&!file.isDirectory()){
            System.out.println("Ŀ¼���ļ������ڣ�");
            file.mkdir();
        }
        //��Ϣ��ʾ
        String message = "";
        try {
            //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
            //1������һ��DiskFileItemFactory����
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            //2������һ���ļ��ϴ�������
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            //����ϴ��ļ�������������
            fileUpload.setHeaderEncoding("UTF-8");
            //3���ж��ύ�����������Ƿ����ϴ���������
            if(!fileUpload.isMultipartContent(request)){
                //���մ�ͳ��ʽ��ȡ����
                return;
            }
            //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
            List<FileItem> list = fileUpload.parseRequest(request);
            for (FileItem item : list) {
                //���fileitem�з�װ������ͨ�����������
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //�����ͨ����������ݵ�������������
                    String value = item.getString("UTF-8");
                    String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name+"  "+value);
                    System.out.println(name+"  "+value1);
                }else{
                    //���fileitem�з�װ�����ϴ��ļ����õ��ϴ����ļ����ƣ�
                    String fileName = item.getName();
                    System.out.println(fileName);
                    if(fileName==null||fileName.trim().equals("")){
                        continue;
                    }
                    //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
                    //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
                    //��ȡitem�е��ϴ��ļ���������
                    InputStream is = item.getInputStream();
                    //����һ���ļ������
                    FileOutputStream fos = new FileOutputStream(savePath+File.separator+fileName);
                    //����һ��������
                    byte buffer[] = new byte[1024];
                    //�ж��������е������Ƿ��Ѿ�����ı�ʶ
                    int length = 0;
                    //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
                    while((length = is.read(buffer))>0){
                        //ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
                        fos.write(buffer, 0, length);
                    }
                    //�ر�������
                    is.close();
                    //�ر������
                    fos.close();
                    //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
                    item.delete();
                    message = "�ļ��ϴ��ɹ�";
                    
                    
                    
            		List<Staff> staffs = PoiRead.readExcel(savePath+File.separator+fileName);
            		for(Staff s : staffs) {
            			StaffDao.addStaff(s);
            		}
            		
            		new File(savePath+File.separator+fileName).delete();
            		
                }
            }
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            message = "�ļ��ϴ�ʧ��";
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
        
		response.sendRedirect("StaffServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
