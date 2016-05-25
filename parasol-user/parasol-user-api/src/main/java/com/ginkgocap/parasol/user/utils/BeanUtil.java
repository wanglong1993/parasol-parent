/**
 * JavaBean和Map互相转换工具类
 * @author gintong
 * create by 2015/11/03
 */
package com.ginkgocap.parasol.user.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {
  /**
   * javabean转换成map  
   * @param o
   * @return
   * @throws Exception
   */
  public static Map<String, Object> bean2map(Object o) throws Exception{  
          Map<String, Object> map = new HashMap<String, Object>();  
          Field[] fields = null;  
          for(Class<?> clazz = o.getClass();clazz!=Object.class;clazz=clazz.getSuperclass()){
            fields = clazz.getDeclaredFields(); 
            for (Field field : fields) {  
              field.setAccessible(true);  
              String proName = field.getName();  
              Object proValue = field.get(o);
              map.put(proName, proValue); 
               
          }  
          }
          return map;  
      }  
        
  /**
   * map转换成javabean  
   * @param o
   * @return
   * @throws Exception
   */    
  public static Object map2bean(Map<String,Object> map,Class clz) throws Exception{ 
          Object o = clz.newInstance();
          if (!map.isEmpty()) {  
              for (String k : map.keySet()) {  
                  Object v = "";  
                  if (!k.isEmpty()) {  
                      v = map.get(k);  
                  }  
                  Field[] fields = null;  
                  fields = o.getClass().getDeclaredFields();  
                  String clzName = o.getClass().getSimpleName();  
                  for (Field field : fields) {  
                      int mod = field.getModifiers();  
                      if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){  
                          continue;  
                      }  
                      if (field.getName().equals(k)) {  
                          field.setAccessible(true);
//                          Class clazz=field.getType();
                          field.set(o,v);  
                      }  
    
                  }  
              }  
          }  
          return o;  
      }
  /**
   * List<Object> to List<Map>
   * @param objcets
   * @return
   * @throws Exception
   */
  public static List<Map> getMaps(List objects) throws Exception{
	  return getMaps(objects,null);
  }
  
  public static List<Map> getMaps(List objects,List<String> fields) throws Exception{
	  List<Map> pList =null;
	  if(objects!=null){
		  for(Object o:objects){
			Map p = bean2map(o);
			if(fields!=null){
				for(String field:fields){
					p.remove(field);
				}
			}
			pList.add(p);
		  }
	  }
	  return pList;
  }
  
}
