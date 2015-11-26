package com.ginkgocap.parasol.sensitive.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.sensitive.model.SensitiveWord;
import com.ginkgocap.parasol.sensitive.service.SensitiveWordService;
import com.ginkgocap.parasol.sensitive.sw.SWSeeker;
import com.ginkgocap.parasol.sensitive.sw.format.AbstractFormat;
import com.ginkgocap.parasol.sensitive.sw.format.HTMLFormat;

/**
 * 
* <p>Title: SensitiveWordServiceImpl.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
@Service("sensitiveWordService")
public class SensitiveWordServiceImpl extends BaseService<SensitiveWord> implements SensitiveWordService {
	
	SWSeeker sw = new SWSeeker();

	@Override
	public SensitiveWord findOne(long id) {
		SensitiveWord word = null;
		try {
			word = getEntity(Long.valueOf(id));
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return word;
	}

	@Override
	public SensitiveWord saveOrUpdate(SensitiveWord sensitiveWord) {
		if(sensitiveWord!=null ){
			if(sensitiveWord.getId()>0){
				try {
					saveEntity(sensitiveWord);
				} catch (BaseServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return sensitiveWord;
			}else{
				try {
					saveEntity(sensitiveWord);
				} catch (BaseServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return sensitiveWord;
			}
		}else{
			return null;
		}
	}

	@Override
	public boolean deleteByIds(List<Serializable> ids) {
		boolean flag = false;
		try {
			flag = deleteEntityByIds(ids);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public int updateWord(){
		SWSeeker sw2 = new SWSeeker();
		try {
			if(!sw2.isReady()){
				Map<String,Object> param = new HashMap<String, Object>();
				long start=0;
				param.put("start", start);
				param.put("step", 100);
//				List<SensitiveWord> list = sensitiveWordDao.findByParmas(param);
				List<SensitiveWord> list = new ArrayList<SensitiveWord>();
				while(list.size()>0){
					List<String> words = new ArrayList<String>();
					for(SensitiveWord s:list){
						words.add(s.getWord());
					}
					sw2.initData(words);
					start+=100;
					param.put("start", start);
//					list = sensitiveWordDao.findByParmas(param);
				}
			}
			if(sw2!=null){
				sw=sw2;
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 高亮文本中的关键词
	 * @param text 文本
	 * @param matchType 匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	 * @return
	 */
	public String highlight(String text, int matchType){
		return highlight(text, matchType, new HTMLFormat("<font color='red'>", "</font>"));
	}
	
	/**
	 * 使用指定字符串高了文本中关键词
	 * @param text 文本
	 * @param matchType 匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	 * @param formate  例如：new HTMLFormat("<font color='red'>", "</font>")
	 * @return
	 */
	public String highlight(String text, int matchType, AbstractFormat formate) {
		init();
		return sw.highlight(text, matchType, formate);
	}
	public List<String> sensitiveWord(String text){
		init();
		return sw.sensitiveWord(text);
	}
	
	private void init(){
		if(!sw.isReady()){
			Map<String,Object> param = new HashMap<String, Object>();
			long start=0;
			param.put("start", start);
			param.put("step", 100);
//			List<SensitiveWord> list = sensitiveWordDao.findByParmas(param);
			List<SensitiveWord> list = new ArrayList<SensitiveWord>();
			while(list.size()>0){
				List<String> words = new ArrayList<String>();
				for(SensitiveWord s:list){
					words.add(s.getWord());
				}
				sw.initData(words);
				start+=100;
				param.put("start", start);
//				list = sensitiveWordDao.findByParmas(param);
			}
		}
	}
	public String replaceSensitive(String text){
		init();
		return sw.replaceSensitive(text);
	}
	
	@Override
	public boolean batchInsertSelective(List<SensitiveWord> sensitiveWords) {
		return false; 
//				sensitiveWordDao.batchInsertSelective(sensitiveWords);
	}

	@Override
	public String highlight(String text, int matchType,
			com.ginkgocap.parasol.util.sw.format.AbstractFormat formate) {
		// TODO Auto-generated method stub
		return null;
	}
}
