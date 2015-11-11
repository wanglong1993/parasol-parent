package com.ginkgocap.ywxt.metadata.service2.sensitiveword.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.SensitiveWord;
import com.ginkgocap.ywxt.metadata.service.SensitiveWordService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;
import com.ginkgocap.ywxt.util.sw.SWSeeker;
import com.ginkgocap.ywxt.util.sw.format.AbstractFormat;
import com.ginkgocap.ywxt.util.sw.format.HTMLFormat;

@Service("sensitiveWordService")
public class SensitiveWordServiceImpl extends BaseService<SensitiveWord> implements SensitiveWordService {
	private static Logger logger = Logger.getLogger(SensitiveWordServiceImpl.class);
	private static final String SENSITIVEWORD_LIST_ID_ALL = "SensitiveWord_List_Id_All";
	SWSeeker sw = new SWSeeker();

	protected List<SensitiveWord> selectSensitiveWord(int start, int size) throws BaseServiceException {
		return getSubEntitys(SENSITIVEWORD_LIST_ID_ALL, start, size, 1);
	}

	@Override
	public SensitiveWord findOne(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public SensitiveWord saveOrUpdate(SensitiveWord sensitiveWord) {
		try {
			if (sensitiveWord != null) {
				SensitiveWord saveSensitiveWord = getEntity(sensitiveWord.getId());
				if (saveSensitiveWord != null) {
					updateEntity(sensitiveWord);
				} else {
					Long id = (Long) saveEntity(sensitiveWord);
					sensitiveWord.setId(id);
				}
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		return sensitiveWord;
	}

	@Override
	public Map<String, Object> findByParams(Map<String, Object> param, DataGridModel dgm) {
		String strType = ObjectUtils.toString(param.get("type"), null);
		String strWord = ObjectUtils.toString(param.get("word"), null);
		String strStartTime = ObjectUtils.toString(param.get("startTime"), null);
		String strEndTime = ObjectUtils.toString(param.get("endTime"), null);
		String strLevel = ObjectUtils.toString(param.get("level"), null);

		boolean bType = StringUtils.isNotBlank(strType);
		boolean bWord = StringUtils.isNotBlank(strWord);
		boolean bStartTime = StringUtils.isNotBlank(strStartTime);
		boolean bEndTime = StringUtils.isNotBlank(strEndTime);
		boolean bLevel = StringUtils.isNotBlank(strLevel);

		List<SensitiveWord> lResult = new ArrayList<SensitiveWord>();
		Integer start = (dgm.getPage() - 1) * dgm.getRows();
		Integer size = dgm.getRows();
		try {
			int take_number = 0;
			while (true) {
				List<SensitiveWord> sensitiveWords = selectSensitiveWord(take_number * TAKE_SIZE, TAKE_SIZE);

				if (CollectionUtils.isNotEmpty(sensitiveWords)) {

					for (SensitiveWord sensitiveWord : sensitiveWords) {
						if (sensitiveWord != null) {
							boolean pass = true;
							if (bType && !ObjectUtils.equals(String.valueOf(sensitiveWord.getType()), strType)) {
								pass = false;
							}
							if (pass && bWord && !StringUtils.contains(sensitiveWord.getWord(), strWord)) {
								pass = false;
							}
							if (pass && bStartTime && ObjectUtils.compare(sensitiveWord.getWord(), strStartTime) < 0) {
								pass = false;
							}
							if (pass && bEndTime && ObjectUtils.compare(sensitiveWord.getWord(), strEndTime) > 0) {
								pass = false;
							}

							if (pass && bLevel && !ObjectUtils.equals(ObjectUtils.toString(sensitiveWord.getLevel(),null), strLevel)) {
								pass = false;
							}

							if (pass) {
								lResult.add(sensitiveWord);
							}

						}
					}

				}

				// 查不到数据数据时退出，或者查的次数超过1000次太多
				if (CollectionUtils.isEmpty(sensitiveWords) || sensitiveWords.size() < TAKE_SIZE || take_number > 1000) {
					if (logger.isDebugEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb.append(CollectionUtils.isEmpty(sensitiveWords) ? "SensitiveWord list is empty break" : "");
						sb.append(sensitiveWords.size() < TAKE_SIZE ? "Query to complete." : "");
						sb.append(take_number > 1000 ? "Query too many times, infinite loop" : "");
						logger.debug(sb.toString());
					}
					break;
				}
				take_number++;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		int total = lResult.size();
		result.put("total", total);
		result.put("rows", lResult.subList(Math.min(start, total), Math.min(start + size, total)));
		return result;
	}

	@Override
	public int deleteByIds(Long[] ids) {
		int iResult = 0;
		for (int i = 0; i < ids.length; i++) {
			try {
				this.deleteEntity(ids[i]);
				iResult++;
			} catch (BaseServiceException e) {
				e.printStackTrace(System.err);
			}
		}
		return iResult;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SensitiveWord> check(String word, long id) {
		if (StringUtils.isBlank(word)) {
			return ListUtils.EMPTY_LIST;
		}
		List<SensitiveWord> lResult = new ArrayList<SensitiveWord>();
		try {
			int take_number = 0;
			while (true) {
				List<SensitiveWord> sensitiveWords = selectSensitiveWord(take_number * TAKE_SIZE, TAKE_SIZE);
				if (CollectionUtils.isNotEmpty(sensitiveWords)) {
					for (SensitiveWord sensitiveWord : sensitiveWords) {
						if (sensitiveWord != null && ObjectUtils.equals(sensitiveWord.getWord(), word) && !ObjectUtils.equals(sensitiveWord.getId(), id)) {
							lResult.add(sensitiveWord);
						}
					}

				}

				// 查不到数据数据时退出，或者查的次数超过1000次太多
				if (CollectionUtils.isEmpty(sensitiveWords) || sensitiveWords.size() < TAKE_SIZE || take_number > 1000) {
					if (logger.isDebugEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb.append(CollectionUtils.isEmpty(sensitiveWords) ? "SensitiveWord list is empty break" : "");
						sb.append(sensitiveWords.size() < TAKE_SIZE ? "Query to complete." : "");
						sb.append(take_number > 1000 ? "Query too many times, infinite loop" : "");
						logger.debug(sb.toString());
					}
					break;
				}
				take_number++;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		return lResult;
	}

	@Override
	public int updateWord() {
		SWSeeker sw2 = new SWSeeker();
		try {
			if (!sw2.isReady()) {
				int take_number = 0;
				while (true) {
					List<SensitiveWord> sensitiveWords = selectSensitiveWord(take_number * TAKE_SIZE, TAKE_SIZE);
					List<String> words = new ArrayList<String>();
					if (CollectionUtils.isNotEmpty(sensitiveWords)) {
						for (SensitiveWord sensitiveWord : sensitiveWords) {
							if (sensitiveWord != null && StringUtils.isNotBlank(sensitiveWord.getWord())) {
								words.add(sensitiveWord.getWord());
							}
						}
						sw2.initData(words);
					}

					// 查不到数据数据时退出，或者查的次数超过1000次太多
					if (CollectionUtils.isEmpty(sensitiveWords) || sensitiveWords.size() < TAKE_SIZE || take_number > 1000) {
						if (logger.isDebugEnabled()) {
							StringBuffer sb = new StringBuffer();
							sb.append(CollectionUtils.isEmpty(sensitiveWords) ? "SensitiveWord list is empty break" : "");
							sb.append(sensitiveWords.size() < TAKE_SIZE ? "Query to complete." : "");
							sb.append(take_number > 1000 ? "Query too many times, infinite loop" : "");
							logger.debug(sb.toString());
						}
						break;
					}
					take_number++;
				}
			}
			if (sw2 != null) {
				sw = sw2;
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return 0;
		}
	}

	@Override
	@Deprecated
	public String highlight(String text, int matchType) {
		return highlight(text, matchType, new HTMLFormat("<font color='red'>", "</font>"));
	}

	@Override
	@Deprecated
	public String highlight(String text, int matchType, AbstractFormat formate) {
		init();
		return sw.highlight(text, matchType, formate);
	}

	@Override
	public List<String> sensitiveWord(String text) {
		init();
		return sw.sensitiveWord(text);
	}

	@Override
	@Deprecated
	public String replaceSensitive(String text) {
		init();
		return sw.replaceSensitive(text);
	}

	@Override
	@Deprecated
	public boolean batchInsertSelective(List<SensitiveWord> sensitiveWords) {
		return false;
	}

	@Override
	public Class<SensitiveWord> getEntityClass() {
		return SensitiveWord.class;
	}

	private void init() {
		if (!sw.isReady()) {
			try {
				int take_number = 0;
				while (true) {
					List<SensitiveWord> sensitiveWords = null;
					sensitiveWords = selectSensitiveWord(take_number * TAKE_SIZE, TAKE_SIZE);
					List<String> words = new ArrayList<String>();
					if (CollectionUtils.isNotEmpty(sensitiveWords)) {
						for (SensitiveWord sensitiveWord : sensitiveWords) {
							if (sensitiveWord != null && StringUtils.isNotBlank(sensitiveWord.getWord())) {
								words.add(sensitiveWord.getWord());
							}
						}
						sw.initData(words);
					}

					// 查不到数据数据时退出，或者查的次数超过1000次太多
					if (CollectionUtils.isEmpty(sensitiveWords) || sensitiveWords.size() < TAKE_SIZE || take_number > 1000) {
						if (logger.isDebugEnabled()) {
							StringBuffer sb = new StringBuffer();
							sb.append(CollectionUtils.isEmpty(sensitiveWords) ? "SensitiveWord list is empty break" : "");
							sb.append(sensitiveWords.size() < TAKE_SIZE ? "Query to complete." : "");
							sb.append(take_number > 1000 ? "Query too many times, infinite loop" : "");
							logger.debug(sb.toString());
						}
						break;
					}
					take_number++;
				}
			} catch (BaseServiceException e) {
				e.printStackTrace();
			}
		}
	}
}
