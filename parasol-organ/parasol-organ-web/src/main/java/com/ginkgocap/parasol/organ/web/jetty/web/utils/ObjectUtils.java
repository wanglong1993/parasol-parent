package com.ginkgocap.parasol.organ.web.jetty.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
* <p>Title: ObjectUtils.java<／p> 
* <p>Description: 对象工具类<／p> 
* @author wfl
* @date 2015-1-23 
* @version 1.0
 */
public class ObjectUtils {

	  public static String  arraysToString(Object[] arrays){
		  String str="";
		  if(arrays!=null&&arrays.length>0){
			  int len=arrays.length;
			   for(int i=0;i<len;i++){
				    if(i!=len-1){
				    	str=str+arrays[i]+",";
				    }else{
				    	str=str+arrays[i];
				    }
			   }
		  }
		  return str;
	  }
	  
	  public static String  listToString(List<String> arrays){
		  String str="";
		  if(arrays!=null&&arrays.size()>0){
			  int len=arrays.size();
			   for(int i=0;i<len;i++){
				    if(i!=len-1){
				    	str=str+arrays.get(i)+",";
				    }else{
				    	str=str+arrays.get(i);
				    }
			   }
		  }
		  return str;
	  }
	  
	  public static List<Long> listStringTolong(List<String> arrays){
		   List<Long> list=new ArrayList<Long>();
		   if(arrays!=null&&arrays.size()>0){
			    for(String array:arrays){
			    	list.add(Long.parseLong(array));
			    }
		   }
		   return list;
	  }
	  
	  public static String alterImageUrl(String imageUrl){
		  String url="";
		  imageUrl=StringUtils.trimToEmpty(imageUrl);
		  if(!"".equals(imageUrl)){
			  int l=imageUrl.lastIndexOf(".com");
			  if(imageUrl.indexOf("http://")>-1&&l>-1){
				 url=imageUrl.substring(l+4);
			  }else{
				  url=imageUrl;
			  }
			  if(url.startsWith(":")){
				  int r=url.indexOf("/");
				  if(r>-1)
					  url=url.substring(r);
			  }
		  }
		  return url;
	  }
	  
	  public static Date StringToDate(String str) throws ParseException{
		     SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟  
			 Date date=sdf.parse(str);  
			 return date;
	  }
	  
	 public static void main(String[] args) {
		 String url="http://file.dev.gintong.com/http://file.dev.gintong.com:81/avtiv/defalut.jpg";
		 String url2=":81/avtiv/defalut.jpg";
		 System.out.println(alterImageUrl(url));
	}
	 
}
