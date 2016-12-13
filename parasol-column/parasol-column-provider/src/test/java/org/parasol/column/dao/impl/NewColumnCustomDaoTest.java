package org.parasol.column.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parasol.column.dao.NewColumnCustomDao;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.NewColumnCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;

/**
 * Created by gintong on 2016/12/6.
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NewColumnCustomDaoTest implements Test{


    @Autowired
    private NewColumnCustomDao newColumnCustomDao;

    @Test
    public void testInsert(){
        NewColumnCustom custom = new NewColumnCustom();
        custom.setSid(5l);
        custom.setUserId(102544l);
        custom.setUserOrSystem((short)0);
        custom.setCreatetime(new Date());
        custom.setUpdateTime(new Date());
        System.out.print(newColumnCustomDao);
        int n =newColumnCustomDao.insert(custom);
        System.out.println(n);
    }

    @Test
    public void testSelect(){
        NewColumnCustom newColumnCustom = newColumnCustomDao.queryBySid(2l);
        System.out.println(newColumnCustom);
    }

    @Test
    public void testQueryListByUid(){
        List<ColumnSelf> selfList = newColumnCustomDao.queryListByUid(102544l);
        System.out.println(selfList.size());
    }
    @Test
    public void testUpdate(){
        NewColumnCustom newColumnCustom = newColumnCustomDao.queryBySid(2l);
        int i = newColumnCustomDao.updateByUid(newColumnCustom, 4l);

        System.out.println(i);
    }
    @Test
    public void testDelete(){
        int i = newColumnCustomDao.deleteByUserId(100544l);
        System.out.println("*****************"+ i + "*****************");
    }

    @Override
    public Class<? extends Throwable> expected() {
        return null;
    }

    @Override
    public long timeout() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

   /* @Test
    public void testUpdate(){

        newColumnCustomDao.updateByUid();

    }*/
}
