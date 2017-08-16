package org.parasol.column.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.parasol.column.utils.HttpUtils;

/*@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class)*/
public class ColumnCustomControllerTest {
	
	/*@Resource(name="columnCustomService")
	private ColumnCustomService ccs;
	
	@Resource(name="columnSelfService")
	private ColumnSelfService css;*/
	
	@Test
	public void testNull(){
		
	}
	
//	@Test
	public void testShow(){
//		ccs.queryListByPidAndUserId(0l, 0l);
	}
	
	/*@Test
	public void testAddColumn() throws Exception{
		ColumnSelf cs=css.selectByPrimaryKey(1l);
		cs.setColumnname("user1");
		cs.setTags("abc,cde");
		String jsonStr=JsonUtils.beanToJson(cs);
		System.out.println(jsonStr);
		String url="http://localhost:81/columnself/addColumnSelf";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}*/
	
	/*@Test
	public void testUpdateColumn() throws Exception{
		ColumnSelf cs=css.selectByPrimaryKey(2688l);
		cs.setColumnname("user2");
		String jsonStr=JsonUtils.beanToJson(cs);
		System.out.println(jsonStr);
		String url="http://localhost:81/columnself/updateColumnSelf";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}*/
	
	/*@Test
	public void testDeleteColumn() throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", 2688l);
		String jsonStr=JsonUtils.beanToJson(map);
		System.out.println(jsonStr);
		String url="http://localhost:81/columnself/deleteColumnSelf";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}*/
	
//	@Test
	public void testReplaceColumn() throws Exception{
//		List<ColumnSelf> list=ccs.queryListByPidAndUserId(0l, 0l);
//		String jsonStr=JsonUtils.beanToJson(list);
		
		/*String url="http://localhost:8090/parasol-column-ui/columncustom/replaceColumn";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);*/
//		List<ColumnCustom> newList=JsonUtils.jsonToList(jsonStr);
//		this.ccs.replace(0l, newList);
	}
	
	@Test
	public void testShowColumn(){
		String jsonStr="{\"pid\":\"0\"}";
		String url="http://localhost:81/columncustom/showColumn";
		String resp=HttpUtils.sendJsonPost(url, jsonStr);
		System.out.println(resp);
	}
	
	@Test
	public void testShowAllColumn(){
		String url="http://192.168.101.41:8022/columnself/showAllColumnSelf";
		String resp=HttpUtils.sendGet(url);
		System.out.println(resp);
	}

	@Test
	public void testShowColumn1(){
		String jsonStr="{\"pid\":\"0\"}";
		String url="http://192.168.101.41:8022/columncustom/showColumn.json";
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("sessionID", "9cf6c69b-2269-431f-92b6-7e3604455d7f");
//		headers.put("s", "web");
		String resp=HttpUtils.sendPost(url, jsonStr,"application/json",headers);
		System.out.println(resp); 
	}
	
	@Test
	public void testLoadColumn() throws Exception{
		String fileName="d:/testjson/column.json";
//		InputStream fis = new BufferedInputStream(new FileInputStream("d:/testjson/column.json"));
		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(new FileReader(new File(
                fileName).getAbsoluteFile()));
		try {
            String s;
            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
         } finally {
            in.close();
         }
		System.out.println(sb.toString());
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("sessionID", "9cf6c69b-2269-431f-92b6-7e3604455d7f");
//		headers.put("s", "web");
		String url="http://localhost:8022/columncustom/replaceColumn";
		String resp=HttpUtils.sendJsonPost(url, sb.toString());
		System.out.println(resp); 
	}
	
}
