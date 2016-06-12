package com.ginkgocap.parasol.organ.web.jetty.web.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** @Description:  工具类
 * @Author:       qinguochao  
 * @CreateDate:  2014-4-18   
 * @Version:      [v1.0]
 */
 
public class Utils {
	 /**
     * 判断对象是否为null或空
     * @param obj
     * return IOException
     */
	public static boolean isNullOrEmpty(Object obj){
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    }
	/**
     * 判断所有对象对象是否为不等null和不为空
     * @param obj
     * return IOException
     */
	public static boolean isAllNotNullOrEmpty(Object... obj){
        
		for(Object ob:obj){
			if(isNullOrEmpty(ob)){
				return false;
			}
		}
		return true;
    }
	

	
	/**
	 * douyou
	 * <p>获取ip地址，由于经过nginx跳转，所以不能单纯的request.getRemoteAddr</p> 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for"); 
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		ip = request.getHeader("Proxy-Client-IP"); 
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		ip = request.getHeader("WL-Proxy-Client-IP"); 
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) 
		ip = request.getRemoteAddr(); 
		return ip; 
	}
	
	/**
	 * txt转html  UTF-8编码
	 * @param content txt文本内容字符串，行结尾必须以\r\n结束
	 * @param urls 图片url
	 * @return html内容字符串
	 */
	public static String txt2Html(String content, List<String> urls,String[] listImageUrl,String neturl) {
		StringBuffer htmlsb = new StringBuffer("<!DOCTYPE html><html><head><meta charset='utf-8' /><style>.gtrelated img{margin-top:10px;max-width:96%;margin-left:2%;height:auto;}.gtrelated{word-break: break-all;word-wrap: break-word; overflow-x: hidden; overflow-y:auto; } body { letter-spacing: 0.1em; line-height: 1.5em;} table{ width:100%; border-top: #bbb solid 1px;border-left: #bbb solid 1px; text-align: center;}table td{ border-right: #bbb solid 1px; border-bottom: #bbb solid 1px;} </style></head><body><div class='gtrelated'>");
		
		String[] lines = content.split("\n");
		for(String line : lines){
			htmlsb.append("<p>" + line.replace("\r", "") + "</p>");
		}
		for(String url : urls){
			htmlsb.append("<img src='" + url + "'/> <br/> <br/>");
		}
		if (null != listImageUrl) {
			for (String img : listImageUrl) {
				htmlsb.append("<img src='" + img + "'/> <br/> <br/>");
			}
		}
		if(!neturl.equals("")) {
			htmlsb.append("<a href='"+ neturl + "'>原网址</a>");	
		}
		htmlsb.append("</div></body></html>");
		return htmlsb.toString();
	}

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
        imageUrl= StringUtils.trimToEmpty(imageUrl);
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

    public static Date StringToDate(String str) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date date=sdf.parse(str);
        return date;
    }

    public static void main(String[] args) {
        String url="http://file.dev.gintong.com/http://file.dev.gintong.com:81/avtiv/defalut.jpg";
        String url2=":81/avtiv/defalut.jpg";
        System.out.println(alterImageUrl(url));
    }

    /**
     * 获取json字符串
     * @param request
     * @return
     * @throws java.io.IOException
     */
    public static String getJsonIn(HttpServletRequest request) throws IOException {
        String requestJson=(String)request.getAttribute("requestJson");
        if(requestJson==null){
            return "";
        }
        return requestJson;
    }

    public static Integer valInt(JSONObject j, String key) throws IOException {
        Object o = j.get(key);
        if (o != null) {
            return Integer.parseInt(o + "");
        }

        return null;
    }

    public static Long valLong(JSONObject j, String key) {
        Object o = j.get(key);
        if (o != null) {
            try {
                String val = o + "";
                Long l;
                l = Long.parseLong(val);
                return l;
            } catch (Exception e) {
            }
        }
        return null;
    }
}
