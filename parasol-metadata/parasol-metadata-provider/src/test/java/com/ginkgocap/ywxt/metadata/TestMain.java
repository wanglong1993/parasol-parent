package com.ginkgocap.ywxt.metadata;

import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;

import com.ginkgocap.ywxt.metadata.model.Code;

public class TestMain extends TestLog{
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    
    public void testLogCall() {
    	logger.info("wwwwwwwwwwwww");
    }
	public static void main(String[] args) {
		
		TestLog log = new TestMain();
		log.loggerInfo();
		String file = System.getProperty("user.home") + "/allenshen.syh.lock";
		
        try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			FileChannel fc = raf.getChannel();
			FileLock lock  = fc.lock();
			//lock.release();
			fc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
	public Map<String, Object> getBeanProperties(Object bean) {
		Map<String,Object> propertyValueMap = new HashMap<String, Object>();
		if (bean == null) {
			return null;
		}
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor propertyDescriptor : descriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			if (propertyName != null && readMethod != null) {
				if (logger.isDebugEnabled()) {
					logger.debug(propertyName + ":" + propertyDescriptor.getReadMethod().getName());
				}
				try {
					Object v = readMethod.invoke(bean, EMPTY_OBJECT_ARRAY);
					if (v == null) {
						propertyValueMap.put(propertyName, v);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace(System.err);
				} catch (InvocationTargetException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return propertyValueMap;
	}
}
