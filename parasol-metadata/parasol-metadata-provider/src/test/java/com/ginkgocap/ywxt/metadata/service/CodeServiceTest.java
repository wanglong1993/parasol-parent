package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.Code;
import com.ginkgocap.ywxt.metadata.service.code.CodeService;
import com.ginkgocap.ywxt.util.DateFunc;
/**
 * 
 * @author liu
 * 创建时间：2012-3-5 15:56:33
 */
@Configuration
public class CodeServiceTest extends TestBase{
    @Autowired
    private CodeService codeService;
    
    private static Logger logger = LoggerFactory.getLogger(CodeServiceTest.class);
    private String codeOldName;
    private String codeNewName;
    private Code code;
    /**
     * @throws java.lang.Exception
     */
//    @Before
    public Code setUp() throws Exception {
        codeOldName = "测试代码";
        codeNewName = "测试代码2";
        code = new Code();
        code.setName(codeOldName);
        code.setType("1");
        code.setLevel(1);
        code.setCtime("2012-3-5 10:58:04");
        code.setUseType(0);
        code.setOrderNo(2);
        code.setRoot(1);
        code.setSynsetIds("1,54");
        String synsetNames=",银杏,申永华,";
        code.setSynsetNames(synsetNames);
        codeService.insert(code);
        return code;
    }
    
    @Test
    public void testCodeDao() throws Exception {
    	code=this.setUp();
        //测试CodeDao的新增方法
        Code code1 = codeService.insert(code);
        assertNotNull(code1);
        //测试CodeDao的
        Code code2 = codeService.selectByPrimarKey(code1.getId());
        assertEquals(code2.getName(),code1.getName());
        //测试CodeDao的修改方法
        code1.setName(codeNewName);
        code1.setOrderNo(1);
        code1.setType(""+code2.getId());
        codeService.update(code1);
        codeService.update(code2);
        codeService.selectByName("测试代码1");
        
        //测试CodeDao的方法
        List<Code> list = codeService.selectByGroupAndLevel("TZXQ", 1);
        assertNotNull(list);
        
        code2.setUseType(1);
        code2.setOrderNo(2);
        code2.setRoot(1);
        Code code3 = codeService.insert(code2);
        code3.setName(codeNewName);
        code3.setOrderNo(2);
        code3.setUseType(0);
        code3.setSysItemId(code2.getId());
        codeService.update(code3);
        
        codeService.selectAll();
        
        Map<String,Object> map =new HashMap<String, Object>();
        codeService.findByMap(map);
        //测试CodeDao的删除方法
        codeService.delete(code2.getId());
        
    }
    @Test
    public void testSelectByParams() {
        List<Code> lt = codeService.selectByName("艺术品",1);
        assertTrue(lt.size()>0);
        lt = codeService.selectByParams(1,"g", 1, 0, 20);
        for (Code code : lt) {
			System.out.println(code);
		}
        lt = codeService.selectByParams(5,"ys", 1, 0, 20);
        for (Code code : lt) {
			System.out.println(code);
		}
    }
    @Test
    public void testSelectByparam() throws Exception {
    	code=this.setUp();
    	code=codeService.insert(code);

    	Code c= new Code();
    	c.setName("123");
    	c.setUseType(1);
    	c.setSysItemId(code.getId());
    	c=codeService.insert(c);
    	code=codeService.selectByPrimarKey(code.getId());
    	code.setUseType(0);
    	code.setOrderNo(0);
    	codeService.update(code);
    	
    	DataGridModel dgm=new DataGridModel();
    	dgm.setPage(1);
    	dgm.setRows(100);
    	Map<String, Object> map=codeService.selectByParam("", "", "", DateFunc.getDate(), "", "", dgm);
    	Map<String, Object> map1=codeService.selectByParam(c.getName(), "", "", "", "", "", dgm);
    	long total=Long.parseLong(String.valueOf(map.get("total"))); 
    	System.out.println(total);
    	DataGridModel dgm1=new DataGridModel();
    	dgm.setPage(0);
    	dgm.setRows(100);
    	Map<String, Object> map2=codeService.selectByParam(c.getName(), "","", "", "","", dgm1);
    	Map<String, Object> map3=codeService.selectByParam("", "", "", DateFunc.getDate(), "", "", dgm1);
    }
    
    
    @Test
    public void selectByPageUtil(){
    	Map<String,Object> values=codeService.selectCodeByPageUtil(0, 1, 1,1, 10);
    	List<Code> list=(List<Code>) values.get("results");
    	if(list!=null&&list.size()>0){
    		for(Code code:list){
    			 System.out.println(code.getName()+"==="+code.getId());
    		}
    	}
    }
    
    
	@Test
    public void testFindMenuTree(){
		/**
    	List<Code> codes = codeDao.selectAllOrderByNumberAsc();
    	Map<String, Object> mapStore = new LinkedHashMap<String, Object>();
    	for (Code code :codes) {
    		String[] indexLevel = code.getNumber().split("-");
    		String nid = null;
    		String pid = null;
    		int level = indexLevel.length;
    		
    		if(level == 1){
    		   nid = indexLevel[0];
    		   pid = "0";
       		   TreeNode treeNode = new TreeNode(nid,pid);
       		   treeNode.setCode(code);
       		   List<TreeNode> menuList = new ArrayList<TreeNode>();
       		   menuList.add(treeNode);
       		   mapStore.put(treeNode.getCode().getName(), menuList);
    		}
    		else {
     		   nid = indexLevel[level-1];
     		   pid = indexLevel[level-2];
       		   TreeNode treeNode = new TreeNode(nid,pid);
       		   treeNode.setCode(code);
       		   for (Entry<String, Object> entry : mapStore.entrySet()) {
       			    @SuppressWarnings("unchecked")
					List<TreeNode> tmp_nodes = (List<TreeNode>) entry.getValue();
       			    for(TreeNode node_1 : tmp_nodes){
           			    if( node_1.getNid().equals(treeNode.getPid()) ){
           			    	node_1.getChildren().add(treeNode);
           			    }
           			    else{
           			    	findNodeList(node_1,treeNode);
           			    }
       			    }
       		   }
			}
		}
    	*/

   	    Map<String, Object> meunTreeMap = codeService.findMenuTree(0, 0);
    	JSONArray array = JSONArray.fromObject(meunTreeMap);  
    	System.out.println(array.toString());  
    }
	
	@Test
	public void testSelectChildNodeById() {
		List<Code> codes = codeService.selectChildNodeById(0);
		for (Code code : codes) {
			System.out.println(code);
			List<Code> codees = codeService.selectChildNodeById(Long.valueOf(code.getId()));
			for (Code code2 : codees) {
				System.out.println(code2);
			}

		}
	}
	
	@Test
	public void testSelectByGroupAndLevel() {
		List<Code> codes = codeService.selectByGroupAndLevel("8", 1);
		for (Code code : codes) {
			System.out.println(code);
		}
	}
}


