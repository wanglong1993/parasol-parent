package com.ginkgocap.ywxt.metadata.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;

import com.ginkgocap.parasol.metadata.service.userTag.UserTagService;
import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.UserTag;


/**
 * 用户标签的测试用例
 * @author hdy
 * @date 2014-10-21
 */
@Configuration
public class UserTagDyServiceTest extends TestBase {
    @Autowired
    private UserTagService userTagService;

    @Test
//    @Rollback(false)
    public void testUserTagDyService() {
        UserTag ut  = new UserTag();
        //测试userTagService的insert方法
        ut.setTagName("小说");
        ut.setUserId(0L);
        UserTag ut2 = userTagService.insert(ut);
        System.out.println("abc:"+ut2.getTagId());
        assertNotNull(ut2);
      
        //测试查询
        UserTag ut3 = userTagService.selectById(ut2.getTagId());
        //  System.out.println(ut2.getTagName());
        System.out.println(ut3.getTagName());
          assertNotNull(ut3);
          //测试
          long count4 = userTagService.countBySys();
          List tag4 = userTagService.selectPageBySys(0, 10);
          System.out.println("cout4:"+count4+"list4:"+tag4.size());

    }
    
/*  @Test
  @Rollback(false)
  //测试删除
    public void testDelTagService() {
    	userTagService.delete(7L);
    	userTagService.deleteByTagNameAndUserId(1L, "经济2");
    }*/
  @Test
  @Rollback(false)
  public void testTagQryService() {
	  //测试用户id的标签数量
        List<UserTag> list = userTagService.selectPageByUserId(1, 1, 10);
        System.out.println("abc:"+list.size());
        long count2 = userTagService.countByUserId(1);
        System.out.println("count2:"+count2);
        assertNotNull(list);
      
  	  //测试用户id标签数量a
        List<UserTag> list2 = userTagService.selectPageByUserId(3, 1, 10);
        System.out.println("abc:"+list2.size());
        

         System.out.println("start ...");
        //测试用户id和系统的标签数量
        List<UserTag> list3= userTagService.selectPageByUserIdAndSys(1, 0, 10);
        System.out.println("abc:"+list3.size());
        for (int i = 0; i < list3.size(); i++)  {
        	System.out.println(i+":"+list3.get(i).getTagName()+","+list3.get(i).getUserId());
        }
        long count3 = userTagService.countByUserIdAndSys(1);
        System.out.println("count3:"+count3);
        assertNotNull(list);
        
        //测试查询系统和用户签名
        List<UserTag> list4= userTagService.selectTagNameInUserIdAndSys(1, "星期");
        System.out.println(list4.size());
        
        //测试前缀查询
        String[] keyword={"北","房地产"};
        List<UserTag> list5= userTagService.searchPrefixPageByUserIdAndSys(1, keyword, 0, 2);
       for(UserTag tag : list5) {
    	  System.out.println(tag.getTagName()); 
       }
        System.out.println(list5.size());
        
        //测试包含查询
        String[] keyword2={"北","房地产"};
        List<UserTag> list6= userTagService.searchContainPageByUserIdAndSys(1, keyword2, 0, 3);
       for(UserTag tag : list6) {
    	  System.out.println(tag.getTagName()); 
       }
        System.out.println(list6.size());
        
        
    }
    
}
