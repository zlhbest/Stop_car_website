package com.stopcar.web.impl.a1;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.stopcar.services.support.JDBCSupport;

@WebServlet("/A1004Servlet")
public class A1004Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		 String message = "";
		 String fieldValue ="";
		 Map<String,String> sqlconnect = new HashMap<>();
		 JDBCSupport jdbcsupport = new JDBCSupport();
		 StringBuilder sql = new StringBuilder()
				 .append("UPDATE aa01")
				 .append(" SET   aa010 = ?")
				 .append(" WHERE aa001 = ?");
		 StringBuilder sql2 = new StringBuilder()
				 .append("SELECT a.aa010 ")
				 .append("  FROM aa01 a")
				 .append(" WHERE a.aa001=?");
		    try{
		        DiskFileItemFactory dff = new DiskFileItemFactory();
		        ServletFileUpload sfu = new ServletFileUpload(dff);
		        List<FileItem> items = sfu.parseRequest(request);
		     
		        for(FileItem item:items){
		            if(item.isFormField()){
		                //普通表单
		                String fieldName = item.getFieldName();
		                fieldValue= item.getString();
		                
		                Object[] vals2 = {
		                		fieldValue
		        		};
		                sqlconnect = jdbcsupport.queryForMapOpen(sql2.toString(),vals2);
		            } else {// 获取上传字段
		                // 更改文件名为唯一的
		                String filename = item.getName();
		                if (filename != null) {
		                    filename = A1004Servlet.generateGUID() + "." + FilenameUtils.getExtension(filename);
		                }
		                // 生成存储路径
		                String storeDirectory = "C:/Tomcat 8.5/webapps/files/images/";
		                //getServletContext().getRealPath("/files/images");
		                File file = new File(storeDirectory);
		                if (!file.exists()) {
		                    file.mkdir();
		                }
		                String path = genericPath(filename, storeDirectory);
		                // 处理文件的上传
		                try {
		                    item.write(new File(storeDirectory + path, filename));
		                    String PathName = "C:/Tomcat 8.5/webapps/" + sqlconnect.get("aa010");
		                   
		                    deleteFile(PathName);
		                    String filePath = "files/images" + path +"/"+ filename;
		                    message = filePath;
		                	Object[] vals = {
		            				filePath,
		            				fieldValue
		            		};
		                	jdbcsupport.executeUpdateOpen(sql.toString(), vals);
		                	
		                } catch (Exception e) {
		                    message = "上传图片失败";
		                }
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        message = "上传图片失败";
		    } finally {
		        response.getWriter().write(message);
		    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	/**
	 * 生成UUID
	 * 
	 * @return UUID
	 */
	public static String generateGUID() {
	    return new BigInteger(165, new Random()).toString(36).toUpperCase();
	}
	// 计算文件的存放目录
	private String genericPath(String filename, String storeDirectory) {
	    int hashCode = filename.hashCode();
	    int dir1 = hashCode&0xf;
	    int dir2 = (hashCode&0xf0)>>4;

	    String dir = "/"+dir1+"/"+dir2;

	    File file = new File(storeDirectory,dir);
	        if(!file.exists()){
	            file.mkdirs();
	        }
	    return dir;
	}
	 public boolean deleteFile(String fileName){
	        File file = new File(fileName);
	        if(file.isFile() && file.exists()){
	            Boolean succeedDelete = file.delete();
	            if(succeedDelete){
	                System.out.println("删除单个文件"+fileName+"成功！");
	                return true;
	            }
	            else{
	                System.out.println("删除单个文件"+fileName+"失败！");
	                return true;
	            }
	        }else{
	            System.out.println("删除单个文件"+fileName+"失败！");
	            return false;
	        }
	    }
}
