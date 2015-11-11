package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.Dic;
import com.ginkgocap.ywxt.metadata.model.Suggestion;
import com.ginkgocap.ywxt.metadata.service.suggestion.SuggestionService;
/**
 * 
 * @author lk
 * 创建时间：2012-3-5 15:56:33
 */
@Configuration
public class SuggestionServiceTest extends TestBase{
    @Autowired
    private SuggestionService suggestionService;
    private Suggestion suggestion;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        suggestion = new Suggestion();
        suggestion.setBrowerInfo("asdfas");
        suggestion.setDataSource("asdf");
        suggestion.setErrUrl("http://localhost");
        suggestion.setFeedbackType("技术错误");
        suggestion.setProblemContent("错误内容");
        suggestion.setProblemTitle("大家好");
        suggestion.setUid(10);
        suggestion.setUser_name("likun");
        suggestion.setCtime("2011-11-24 10:31:57");
    }
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSuggestionDao() {
        Suggestion suggestion1 = suggestionService.insert(suggestion);
        assertNotNull(suggestion1);
        System.out.println(suggestion1.getUid());
        System.out.println(suggestion1.getFeedbackType());
        ////////////////////////////////
        Suggestion suggestion2 = suggestionService.selectByPrimarKey(suggestion1.getId());
        assertEquals(suggestion1.getProblemTitle(),suggestion2.getProblemTitle());
        
        
        ///////////////////////////////
    	DataGridModel dgm=new DataGridModel();
    	dgm.setPage(1);
    	dgm.setRows(100);
    	Map<String, Object> map=suggestionService.selectByParam(suggestion1.getId(), "", "大家", "", "","","","", dgm);
    	long total=Long.parseLong(String.valueOf(map.get("total")));
    	System.out.println(total);

    	suggestionService.update(suggestion2);
    	suggestionService.selectByParams(0, "", "", "", 0,100);
    	suggestionService.delete(suggestion2.getId());
    	
    	DataGridModel dgm1=new DataGridModel();
    	dgm.setPage(0);
    	dgm.setRows(100);
    	Map<String, Object> map1=suggestionService.selectByParam(suggestion1.getId(), "", "大家", "", "","","","", dgm);
    }
    
    @Test
    public void testDic()  {
    	List<Dic> dics = suggestionService.selectDics();
    	assertEquals(CollectionUtils.isNotEmpty(dics), true);
    	for (Dic dic : dics) {
			System.out.println(dic);
		}
    	
    }

}
