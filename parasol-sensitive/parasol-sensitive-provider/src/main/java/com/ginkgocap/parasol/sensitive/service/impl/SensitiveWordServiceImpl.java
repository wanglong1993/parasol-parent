package com.ginkgocap.parasol.sensitive.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.sensitive.exception.SensitiveWordServiceException;
import com.ginkgocap.parasol.sensitive.model.SensitiveWord;
import com.ginkgocap.parasol.sensitive.service.SensitiveWordService;
import com.ginkgocap.parasol.sensitive.sw.SWSeeker;
import com.ginkgocap.parasol.util.sw.format.AbstractFormat;
import com.ginkgocap.parasol.util.sw.format.HTMLFormat;

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
	
	private static int error_sensitiveid_blank = 100; // id为0
	private static int error_sensitiveword_blank = 101; // 敏感词对象为空
	private static int error_checkword_blank = 102; // 检测字符串为空。	
	private static int error_sensitivewordids_null = 103;	//	id列表为空
	private static int error_userid_null = 104; //	用户id为空
	private static int error_entity_null = 105; // entity为空	
	
	SWSeeker sw = new SWSeeker();

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public SensitiveWord findOne(long id) throws SensitiveWordServiceException {
		if(id == 0) {
			throw new SensitiveWordServiceException(error_sensitiveid_blank,"sensitive worid id is null");
		}
		logger.info("进入通过id获取敏感词，参数id:{}", id);
		SensitiveWord word = null;
		try {
			word = getEntity(Long.valueOf(id));
		} catch (BaseServiceException e) {
			logger.error("通过id获取敏感词失败，参数id:{}", id);
			throw new SensitiveWordServiceException(e);
		}
		return word;
	}

	@Override
	public SensitiveWord saveOrUpdate(SensitiveWord sensitiveWord) throws SensitiveWordServiceException {
		if(sensitiveWord == null) {
			throw new SensitiveWordServiceException(error_sensitiveword_blank,"sensitive worid id is null");
		}
		logger.info("进入保存或修改敏感词，参数word:{}", sensitiveWord.getWord());
		if(sensitiveWord.getId()>0){
			try {
				updateEntity(sensitiveWord);
			} catch (BaseServiceException e) {
				logger.error("修改敏感词失败，参数word:{}", sensitiveWord.getWord());
				throw new SensitiveWordServiceException(e);
			}
			return sensitiveWord;
		}else{
			try {
				Long id = (Long)saveEntity(sensitiveWord);
				sensitiveWord.setId(id);
			} catch (BaseServiceException e) {
				logger.error("保存敏感词失败，参数word:{}", sensitiveWord.getWord());
				throw new SensitiveWordServiceException(e);
			}
			return sensitiveWord;
		}
	}

	@Override
	public boolean checkSensitiveWordExist(String word) throws SensitiveWordServiceException{
		if( StringUtils.isBlank(word)) {
			throw new SensitiveWordServiceException(error_checkword_blank,"worid is null");
		}
		logger.info("进入检查敏感词是否已存在，参数word:{}", word);
		int count = 0;
		try {
			count = countEntitys("SensitiveWord_Id_Name",word);
		} catch (BaseServiceException e) {
			logger.error("检查敏感词是否已存在失败，参数word:{}", word);
			throw new SensitiveWordServiceException(e);
		}
		return count>0? true : false;
	}	


	@Override
	public boolean deleteById(Long id) throws SensitiveWordServiceException{
		if(id == 0) {
			throw new SensitiveWordServiceException(error_sensitiveid_blank,"delete sensitive worid id is null");
		}
		logger.info("进入根据id删除敏感词，参数id:{}", id);
		boolean flag = false;
		try {
			flag = deleteEntity(id);
		} catch (BaseServiceException e) {
			logger.error("根据id删除敏感词失败，参数id:{}", id);
			throw new SensitiveWordServiceException(e);
		}
		return flag;
	}	
	
	@Override
	public boolean deleteByIds(List<Long> ids) throws SensitiveWordServiceException {
		if(ids == null || ids.size() == 0) {
			throw new SensitiveWordServiceException(error_sensitivewordids_null,"delete sensitive worid ids is null");
		}
		logger.info("进入根据id列表删除敏感词，参数id:{}", ids);
		boolean flag = false;
		try {
			flag = deleteEntityByIds(ids);
		} catch (BaseServiceException e) {
			logger.error("根据id列表删除敏感词失败，参数id:{}", ids);
			throw new SensitiveWordServiceException(e);
		}
		return flag;
	}
	
	public int updateWord(){
		logger.info("进入更新敏感词！");
		SWSeeker sw2 = new SWSeeker();
		try {
			if(!sw2.isReady()){
				int start=0;
				int step=100;
				List<SensitiveWord> list = getSubEntitys("SensitiveWord_Id_Level", start ,step, -1);
				while(list.size()>0){
					List<String> words = new ArrayList<String>();
					for(SensitiveWord s:list){
						words.add(s.getWord());
					}
					sw2.initData(words);
					start+=100;
					list = getEntitys("SensitiveWord_Id_Level", start ,step, -1);
				}
			}
			if(sw2!=null){
				sw=sw2;
			}
			return 1;
		} catch (Exception e) {
			logger.error("更新敏感词失败！错误信息"+e.getMessage());
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
		logger.info("进入初始化敏感词！");
		if(!sw.isReady()){
			try {
				int start=0;
				int step=100;
				List<SensitiveWord> list = getSubEntitys("SensitiveWord_Id_Level", start ,step, -1);
				while(list.size()>0){
					List<String> words = new ArrayList<String>();
					for(SensitiveWord s:list){
						words.add(s.getWord());
					}
					sw.initData(words);
					start+=100;
					list = getSubEntitys("SensitiveWord_Id_Level", start ,step, -1);
				}
			}catch(Exception e) {
				logger.error("初始化敏感词失败！错误信息"+e.getMessage());
				e.printStackTrace();
			}
		}
	}
	public String replaceSensitive(String text){
		init();
		return sw.replaceSensitive(text);
	}
	
	@Override
	public boolean batchInsertSensitiveWords(List<SensitiveWord> sensitiveWords) {
		logger.info("进入批量保存敏感词方法！");
		try {
			saveEntitys(sensitiveWords);
		} catch (BaseServiceException e) {
			logger.info("批量保存敏感词方法失败！"+e.getMessage());
			e.printStackTrace();
		}
		return false; 
	}

}
