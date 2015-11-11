package com.ginkgocap.ywxt.metadata.service2.suggestion.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.Dic;
import com.ginkgocap.ywxt.metadata.model.Suggestion;
import com.ginkgocap.ywxt.metadata.service.dic.DicService;
import com.ginkgocap.ywxt.metadata.service.suggestion.SuggestionService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;

/**
 * 
 * @author allenshen
 * @date: 2015年10月21日
 * @time: 下午3:19:43 Copyright©2015 www.gintong.com
 */
@Service("suggestionService")
public class SuggestionServiceImpl extends BaseService<Suggestion> implements SuggestionService {
	private static final String SUGGESTION_LIST_ID_ALL = "Suggestion_List_Id_All";
	private static Logger logger = Logger.getLogger(SuggestionServiceImpl.class);

	@Autowired
	private DicService dicService;
	
	@Override
	public Suggestion selectByPrimarKey(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Suggestion insert(Suggestion suggestion) {
		try {
			Long id = (Long) saveEntity(suggestion);
			if (id != null) {
				suggestion.setId(id);
				return suggestion;
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Suggestion add(Suggestion suggestion) {
		return insert(suggestion);
	}

	@Override
	public void update(Suggestion suggestion) {
		try {
			updateEntity(suggestion);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void delete(long id) {
		try {
			deleteEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	@Deprecated
	public List<Suggestion> selectByParams(long id, String user_name, String problemTitle, String feedbackType, long startRow, int pageSize) {
		return null;
	}

	@Override
	@Deprecated
	public Map<String, Object> selectByParam(long id, String user_name, String problemTitle, String cstart, String cend, String fstart, String fend, String feedbackType,
			DataGridModel dgm) {

		Long tempId = id == 0l ? null : id;
		String strId = ObjectUtils.toString(tempId, null);
		String strProblemTitle = problemTitle;
		String strUser_name = user_name;
		String strCend = cend;
		String strCstart = cstart;
		String strFeedbackType = feedbackType;
		String strFend = fend;
		String strFstart = fstart;

		boolean bId = StringUtils.isNotBlank(strId) ? true : false;
		boolean bProblemTitle = StringUtils.isNotBlank(strProblemTitle) ? true : false;
		boolean bUser_name = StringUtils.isNotBlank(strUser_name) ? true : false;
		boolean bCend = StringUtils.isNotBlank(strCend) ? true : false;
		boolean bCstart = StringUtils.isNotBlank(strCstart) ? true : false;
		boolean bFeedbackType = StringUtils.isNotBlank(strFeedbackType) ? true : false;
		boolean bFend = StringUtils.isNotBlank(strFend) ? true : false;
		boolean bFstart = StringUtils.isNotBlank(strFstart) ? true : false;

		List<Suggestion> lt = new ArrayList<Suggestion>();
		Integer start = (dgm.getPage() - 1) * dgm.getRows();
		Integer size = dgm.getRows();
		int take_count = 0;
		int take_number = 0;
		try {

			while (true) {
				List<Suggestion> suggestions = getSubEntitys(SUGGESTION_LIST_ID_ALL, take_number * TAKE_SIZE, TAKE_SIZE, 1);

				if (CollectionUtils.isNotEmpty(suggestions)) {
					for (Suggestion suggestion : suggestions) {
						if (suggestion != null) {
							boolean passCheck = true;
							if (bId && !ObjectUtils.equals(String.valueOf(suggestion.getId()), strId)) {
								passCheck = false;
							}

							if (passCheck && bProblemTitle && !StringUtils.contains(suggestion.getProblemTitle(), StringUtils.trim(strProblemTitle))) {
								passCheck = false;
							}

							if (passCheck && bUser_name && !StringUtils.equalsIgnoreCase(suggestion.getUser_name(), StringUtils.trim(strUser_name))) {
								passCheck = false;
							}

							if (passCheck && bCstart && ObjectUtils.compare(suggestion.getCtime(), StringUtils.trim(strCstart)) < 0) {
								passCheck = false;
							}
							if (passCheck && bCend && ObjectUtils.compare(suggestion.getCtime(), StringUtils.trim(strCend)) > 0) {
								passCheck = false;
							}
							if (passCheck && bFeedbackType && !ObjectUtils.equals(suggestion.getFeedbackType(), StringUtils.trim(strFeedbackType))) {
								passCheck = false;
							}

							if (passCheck && bFstart && ObjectUtils.compare(suggestion.getFtime(), StringUtils.trim(strFstart)) < 0) {
								passCheck = false;
							}
							if (passCheck && bFend && ObjectUtils.compare(suggestion.getFtime(), StringUtils.trim(strFend)) > 0) {
								passCheck = false;
							}
							
							if (passCheck) {
								if (take_count >= start) {
									lt.add(suggestion);
								}
								take_count ++;
							}

						}
					}
				}

				// skip loop condition
				if (CollectionUtils.isEmpty(suggestions) || suggestions.size() < TAKE_SIZE || take_count >= start+size || take_number > 1000) {
					if (logger.isDebugEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb.append(CollectionUtils.isEmpty(suggestions) ? "Suggestion list is empty break" : "");
						sb.append(suggestions.size() < TAKE_SIZE ? "Query to complete." : "");
						sb.append(take_count >= start+size ? "find data number is ok" : "");
						sb.append(take_number > 1000 ? "Query too many times, infinite loop" : "");
						logger.debug(sb.toString());
					}
					break;
				}
				take_number++;
			}
		} catch (Exception e) {
			logger.error("selectByParam is error");
			e.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", take_count < size ? take_count : take_count + size);
		result.put("rows", lt);
		return result;
	}

	@Override
	public List<Dic> selectDics() {
		return dicService.getDicsByType(0);
	}

	@Override
	public Class<Suggestion> getEntityClass() {
		return Suggestion.class;
	}

}
