package com.guojin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import sun.net.www.protocol.http.HttpURLConnection;



public class FileFromUrl {

	/**  
	 * @从制定URL下载文件并保存到指定目录
	 * @param filePath 文件将要保存的目录  
	 * @param method 请求方法，包括POST和GET  
	 * @param url 请求的路径  
	 * @return  
	 */  
	public static File saveUrlAs(String url,String filePath,String method){  
	     //System.out.println("fileName---->"+filePath);  
	     //创建不同的文件夹目录  
	     File file=new File(filePath);  
	     //判断文件夹是否存在  
	     if (!file.exists())  
	    {  
	        //如果文件夹不存在，则创建新的的文件夹  
	         file.mkdirs();  
	    }  
	     FileOutputStream fileOut = null;  
	     HttpURLConnection conn = null;  
	     InputStream inputStream = null;  
	     try  
	    {  
	         // 建立链接  
	         URL httpUrl=new URL(url);  
	         conn=(HttpURLConnection) httpUrl.openConnection();  
	         //以Post方式提交表单，默认get方式  
	         conn.setRequestMethod(method);  
	         conn.setDoInput(true);    
	         conn.setDoOutput(true);  
	         // post方式不能使用缓存   
	         conn.setUseCaches(false);  
	         //连接指定的资源   
	         conn.connect();  
	         //获取网络输入流  
	         inputStream=conn.getInputStream();  
	         BufferedInputStream bis = new BufferedInputStream(inputStream);  
	         //判断文件的保存路径后面是否以/结尾  
	         if (!filePath.endsWith("/")) {  
	  
	             filePath += "/";  
	  
	             }  
	         //写入到文件（注意文件保存路径的后面一定要加上文件的名称）  
	         fileOut = new FileOutputStream(filePath+"123.png");  
	         BufferedOutputStream bos = new BufferedOutputStream(fileOut);  
	           
	         byte[] buf = new byte[4096];  
	         int length = bis.read(buf);  
	         //保存文件  
	         while(length != -1)  
	         {  
	             bos.write(buf, 0, length);  
	             length = bis.read(buf);  
	         }  
	         bos.close();  
	         bis.close();  
	         conn.disconnect();  
	    } catch (Exception e)  
	    {  
	         e.printStackTrace();  
	         System.out.println("抛出异常！！");  
	    }  
	       
	     return file;  
	       
	 }  
	
	
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024*4];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
		}
	
	/**
	 *  从给出的url读取文件返回字节数组
	 * @param url
	 * @param method
	 * @return
	 */
	public static byte[] readFileFromUrl(String url,String method){  
		HttpURLConnection conn = null;  
		InputStream inputStream = null;  
		byte[] result = null;
		try  
		{  
			// 建立链接  
			URL httpUrl=new URL(url);  
			conn=(HttpURLConnection) httpUrl.openConnection();  
			//以Post方式提交表单，默认get方式  
			conn.setRequestMethod(method);  
			conn.setDoInput(true);    
			conn.setDoOutput(true);  
			// post方式不能使用缓存   
			conn.setUseCaches(false);  
			//连接指定的资源   
			conn.connect();  
			//获取网络输入流  
			inputStream=conn.getInputStream();  
			result = toByteArray(inputStream);
			conn.disconnect();  
		} catch (Exception e)  
		{  
			e.printStackTrace();  
			System.out.println("抛出异常！！");  
		}  
		
		return result;  
		
	}  
	/**  
	 * @param args  
	 */  
	public static void main(String[] args)  
	{  
	    String photoUrl = "http://pdf.dfcfw.com/pdf/H2_AN201202240003284483_1.txt";   //文件URL地址                                     
	    String fileName = photoUrl.substring(photoUrl.lastIndexOf("/"));     //为下载的文件命名
	    String filePath = "d:";      //保存目录
	    File file = saveUrlAs(photoUrl, filePath + fileName,"GET");    
	}  

}
