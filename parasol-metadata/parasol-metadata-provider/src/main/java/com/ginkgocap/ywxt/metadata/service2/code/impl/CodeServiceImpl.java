package com.ginkgocap.ywxt.metadata.service2.code.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.form.TreeNode;
import com.ginkgocap.ywxt.metadata.model.Code;
import com.ginkgocap.ywxt.metadata.model.CodeRegion;
import com.ginkgocap.ywxt.metadata.model.RCodeDock;
import com.ginkgocap.ywxt.metadata.service.code.CodeService;
import com.ginkgocap.ywxt.metadata.service.code.RCodeDockService;
import com.ginkgocap.ywxt.metadata.service.region.CodeRegionService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.PageUtil;
import com.ginkgocap.ywxt.util.bean.BeanPropertyUtils;

/**
 * 
 * @author allenshen
 * @date: 2015年10月20日
 * @time: 下午1:35:31 Copyright©2015 www.gintong.com
 */
@Service("codeService")
public class CodeServiceImpl extends BaseService<Code> implements CodeService {
	private static Logger logger = Logger.getLogger(CodeServiceImpl.class);
	private static final String CODE_LIST_ID_TYPE_LEVEL_USETYPE = "Code_List_Id_Type_Level_UseType";
	private static final String CODE_LIST_ID_NAME = "Code_List_Id_Name";
	private static final String CODE_LIST_ID_ROOT_USETYPE = "Code_List_Id_Root_useType";
	private static final String selectByParam = "Code_List_Id_useType";
	private static final String CODE_LIST_ID_ALL = "Code_List_Id_All"; //选择全部
	private static final String CODE_LIST_ID_USETYPE_TYPE = "Code_List_Id_useType_Type";
	private static final String SelectByUserTypeIndustry = "SelectByUserTypeIndustry";
	private static final int 	USETYPE_SYSTEM = 0; //系统字段
	private static final int    INDUSTRY_TRUE= 1; //是行业

	private static final CodeComparator codeComparator = new CodeComparator();
	@Autowired
	private RCodeDockService rCodeDockService;

	@Autowired
	private CodeRegionService codeRegionService;

	@Override
	public Code selectByPrimarKey(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<Code> selectByGroupAndLevel(String group, int level) {
		try {
			String[] strs = group.split(",");
			List<Code> lResult = new ArrayList<Code>();

			for (String type : strs) {
				if (StringUtils.isNotBlank(type)) {
					List<Code> codes = this.getEntitys(CODE_LIST_ID_TYPE_LEVEL_USETYPE, type, level, USETYPE_SYSTEM);
					if (CollectionUtils.isNotEmpty(codes)) {
						lResult.addAll(codes);
					}
				}
			}
			return lResult;

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Code insert(Code code) {
		try {
			Long id = (Long) saveEntity(code);
			if (id != null) {
				code.setId(id);
				return code;
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void update(Code code) {
		try {
			updateEntity(code);
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
	public List<Code> selectByParams(int root, String keywords, int useType, long startRow, int pageSize) {
		try {
			String name = keywords;
			boolean checkName = StringUtils.isNotBlank(name) ? true : false;
			boolean checkIndustry = root >=4 ? true:false;
			
			int iStart = (int)startRow;
			int iSize = pageSize;			
			int iRoot = root;
			int iUseType = useType;
			
			List<Code> lResult = new ArrayList<Code>();
			int takePageNumber = 0;
			int recordCount = 0;
			while (true) {
				List<Code> codes = getSubEntitys(CODE_LIST_ID_ROOT_USETYPE, takePageNumber * TAKE_SIZE, TAKE_SIZE, iRoot,iUseType);
				for (Code code : codes) {
					if (code != null) {
						boolean pass = true;
						if (checkName && !(StringUtils.contains(code.getName(), name) || StringUtils.contains(code.getNameIndex(), name)
								|| StringUtils.contains(code.getNameIndexAll(), name))) {
							pass = false;
						}
						
						if (pass && checkIndustry && !ObjectUtils.equals(code.getIndustry(), INDUSTRY_TRUE)){
							pass =false;
						}
						
						if (pass) {
							if (recordCount >= iStart) {
								lResult.add(code);
							}
							recordCount++;
						}
					}
				}
				
				//检查是否退出循环
				if (CollectionUtils.isEmpty(codes) || recordCount >= iStart + iSize) {
					if (logger.isDebugEnabled()) {
						StringBuffer sb = new StringBuffer();
						sb.append(CollectionUtils.isEmpty(codes) ? "codes is empty or null break loop":"");
						sb.append(recordCount >= iStart + iSize ? "find full number codes; break loop":"");
						logger.debug(sb.toString());
					}
					break;
				}
				takePageNumber++;
				
			}
			
			return lResult.subList(Math.min(0, lResult.size()), Math.min(iSize, lResult.size()));
		}catch(BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Code> selectByName(String name) {
		try {
			return getEntitys(CODE_LIST_ID_NAME, name);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Code> selectByName(String name, int root) {
		List<Code> codes = selectByName(name);
		if (CollectionUtils.isNotEmpty(codes)) {
			List<Code> resultCodes = new ArrayList<Code>();
			for (Code code : codes) {
				if (code != null && ObjectUtils.equals(code.getRoot(), root)) {
					resultCodes.add(code);
				}
			}
			return resultCodes;
		}
		return null;
	}

	@Override
	@Deprecated
	public List<Code> selectAll() {
		logger.error("Deprecated method call");
		return null;
	}

	@Override
	public List<Map> findByMap(Map<String, Object> map) {
		try {
			int take_number = 0;
			List<Map> lResult = new ArrayList<Map>();
			while (true) {
				List<Code> codes = getSubEntitys(CODE_LIST_ID_ALL, take_number * TAKE_SIZE, TAKE_SIZE, 1);
				List<Code> resultCodes = null;
				if (MapUtils.isEmpty(map)) {
					resultCodes = codes;
				} else {
					resultCodes = filterListByMap(codes, map);
				}
				
				if (CollectionUtils.isNotEmpty(resultCodes)) {
					if (logger.isDebugEnabled()) {
						logger.debug("find code size is " + resultCodes.size());
					}

					for (Code code : resultCodes) {
						if (code != null) {
							Map<String, Object> m = BeanPropertyUtils.getBeanProperties(code);
							if (code.getOrderNo() == 999) {
								m.put("orderNo", 0);
							}
							String number = code.getNumber();
							if (code.getRoot() == 1 && StringUtils.isNotBlank(code.getNumber())) { // 投资
								if (logger.isDebugEnabled()) {
									logger.debug("begin find code tzNumber:" + code.getNumber());
								}
								String tz = "";
								List<RCodeDock> tzCds = rCodeDockService.dockTzList(number);
								if (CollectionUtils.isNotEmpty(tzCds)) {
									for (RCodeDock rCodeDock : tzCds) {
										tz += rCodeDock.getRzName() + ",";
									}
									tz = StringUtils.removeEnd(tz, ",");
								}
								m.put("dockStr", tz);
								m.put("dockList", tzCds == null ? ListUtils.EMPTY_LIST : tzCds);
							}

							if (code.getRoot() == 2 && StringUtils.isNotBlank(code.getNumber())) { // 融资
								if (logger.isDebugEnabled()) {
									logger.debug("begin find code rzNumber:" + code.getNumber());
								}
								String rz = "";
								List<RCodeDock> rzCds = rCodeDockService.dockRzList(number);
								if (CollectionUtils.isNotEmpty(rzCds)) {
									for (RCodeDock rCodeDock : rzCds) {
										rz += rCodeDock.getTzName() + ",";
									}
									rz = StringUtils.removeEnd(rz, ",");
								}
								m.put("dockStr", rz);
								m.put("dockList", rzCds == null ? ListUtils.EMPTY_LIST : rzCds);
							}
							lResult.add(m);
						}
					}

				}			
				
				//退出循环（break loop）
				if (CollectionUtils.isEmpty(codes) || take_number >= 100) {
					StringBuffer sb = new StringBuffer();
					sb.append(CollectionUtils.isEmpty(codes) ? "code is empty or null; break loop" : "");
					sb.append(take_number >= 100 ? "Query too many times； break loop" : "");
					logger.info(sb.toString());
					break;
				}
				take_number++;
			}
			return lResult;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}		
		
		
	}

	@Override
	public Map<String, Object> selectByParam(String name, String createBy, String sysItem, String startTimer, String endTimer, String type, DataGridModel dgm) {
		// 创建开始时间不为空的时候 创建结束时间为空的时候，默认当前时间
		endTimer = StringUtils.isNotBlank(startTimer) && !StringUtils.isNotBlank(endTimer) ? DateFunc.getDate() : endTimer;
		Integer start = (dgm.getPage() - 1) * dgm.getRows();
		Integer size = dgm.getRows();
		List<Code> lt = null;
		long total = 0l;
		try {

			boolean bName = StringUtils.isBlank(name) ? false : true;
			boolean bCreateBy = StringUtils.isBlank(createBy) ? false : true;
			boolean bSysItem = StringUtils.isBlank(sysItem) ? false : true;
			boolean bStartTimer = StringUtils.isBlank(startTimer) ? false : true;
			boolean bEndTimer = StringUtils.isBlank(endTimer) ? false : true;
			boolean bType = StringUtils.isBlank(type) ? false : true;

			List<Code> allCode = getEntitys(selectByParam, 1);
			List<Code> lResult = new ArrayList<Code>();

			if (CollectionUtils.isNotEmpty(allCode) & (bName || bCreateBy || bSysItem || bStartTimer || bEndTimer || bType)) {
				for (Code code : allCode) {
					boolean pass = true;
					if (code != null) {
						if (bName && !ObjectUtils.equals(code.getName(), name)) {
							pass = false;
						}

						if (pass && bCreateBy && !ObjectUtils.equals(code.getCreateBy(), name)) {
							pass = false;
						}

						if (pass && bSysItem && (ObjectUtils.toString(code.getSysItem(), "").indexOf(sysItem) < 0)) {
							pass = false;
						}

						if (pass && bStartTimer && (ObjectUtils.compare(code.getCtime(), startTimer) < 1)) {
							pass = false;
						}

						if (pass && bEndTimer && (ObjectUtils.compare(code.getCtime(), endTimer) > 1)) {
							pass = false;
						}

						if (pass && bType && !(ObjectUtils.equals(code.getType(), type))) {
							pass = false;
						}

						if (pass) {
							lResult.add(code);
						}

					}
				}

			} else {
				lResult = allCode;
			}

			int iCountResult = lResult.size();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("total", iCountResult);
			resultMap.put("rows", lResult.subList(Math.min(start, iCountResult), Math.min(start + size, iCountResult)));
			return resultMap;

		} catch (Exception e) {
			logger.error("selectByParam is error");
			e.printStackTrace(System.err);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", lt);
		return result;
	}

	@Override
	public List<Code> selectChildNodeById(long id) {
		try {
			List<Code> codes = getEntitys(CODE_LIST_ID_USETYPE_TYPE, USETYPE_SYSTEM, String.valueOf(id));
			return codes;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@Override
	public List<Code> selectBrotherNodeById(long id) {
		// 旧版的DAO和 selectChildNodeById 是一样的SQL
		logger.error("Deprecated method call");
		throw new RuntimeException("Please implement this method");
	}

	@Override
	@Deprecated
	public Map<String, Object> selectCodeByPageUtil(int useType, int industry, int level, int pageIndex, int pageSize) {
		try {
			logger.error("Deprecated method call");
			List<Code> lResult = new ArrayList<Code>();
			int count = countEntitys(SelectByUserTypeIndustry, useType, industry, level);
			if (count > 0 && count > pageIndex) {
				lResult = getSubEntitys(SelectByUserTypeIndustry, pageIndex, pageSize, useType, industry, level);
			}
			PageUtil pu = new PageUtil(count, pageIndex, pageSize);
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("results", lResult);
			values.put("page", pu);
			return values;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	@Deprecated
	public List<CodeRegion> selectAreaList(Long pid) {
		logger.error("Deprecated method call");
		return codeRegionService.selectByParentId(pid);
	}

	@Override
	public Map<String, Object> findMenuTree(int page, int pagesize) {
		List<Code> codes = null;
		try {
			codes = getEntitys(CODE_LIST_ID_ALL, 1);
			if (CollectionUtils.isNotEmpty(codes)) {
				Collections.sort(codes, codeComparator);
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		} // 取全部记录
		Map<String, Object> menuTree = new LinkedHashMap<String, Object>();
		Map<String,Boolean> numberMap = new HashMap<String,Boolean>();
		for (Code code : codes) {
			if (code == null || StringUtils.isBlank(code.getNumber()) || numberMap.containsKey(code.getNumber())) {
				logger.error("code is null or number of code is blank or number of code duplicate");
				continue;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Number: " + code.getNumber());
			}
			numberMap.put(code.getNumber(), Boolean.TRUE);
			String[] indexLevel = code.getNumber().split("-");
			String nid = null;
			String pid = null;
			int level = indexLevel.length;
			// String rootKey = String.valueOf(code.getRoot());
			if (level == 1) {
				nid = indexLevel[0];
				pid = "0";
				TreeNode treeNode = new TreeNode(nid, pid);
				treeNode.setCode(code);
				List<TreeNode> menuList = new ArrayList<TreeNode>();
				menuList.add(treeNode);
				menuTree.put(treeNode.getCode().getName(), menuList);
				// menuTree.put(rootKey, menuList);
			} else {
				nid = indexLevel[level - 1];
				pid = indexLevel[level - 2];
				TreeNode treeNode = new TreeNode(nid, pid);
				treeNode.setCode(code);
				for (Entry<String, Object> entry : menuTree.entrySet()) {
					@SuppressWarnings("unchecked")
					List<TreeNode> tmp_nodes = (List<TreeNode>) entry.getValue();
					if (logger.isDebugEnabled()) {
						logger.debug(tmp_nodes.size() + "");
					}
					for (TreeNode node_1 : tmp_nodes) {
						logger.debug(node_1.getNid() + "," + treeNode.getPid());
						;
						if (node_1.getNid().equals(treeNode.getPid())) {
							node_1.getChildren().add(treeNode);
						} else {
							findNodeList(node_1, treeNode);
						}
					}
				}
			}
		}
		menuTree.put("Totals", codes.size());
		return menuTree;
	}

	@Override
	@Deprecated
	public Long getCataLogMaxId() {
		// selectCataLogMaxId
		throw new RuntimeException("Please implements this method");
	}

	@Override
	public Class<Code> getEntityClass() {
		return Code.class;
	}

	private List<Code> filterListByMap(List<Code> codes, Map<String, Object> conditionMap) {
		List<Code> lResult = null;
		String strUseType = ObjectUtils.toString(conditionMap.get("useType"), null);
		String strRoot = ObjectUtils.toString(conditionMap.get("root"), null);
		String strIndustry = ObjectUtils.toString(conditionMap);
		String strParentId = ObjectUtils.toString(conditionMap.get("parentId),null);"), null);
		String strCtime1 = ObjectUtils.toString(conditionMap.get("ctime1"), null);
		String strCtime2 = ObjectUtils.toString(conditionMap.get("ctime2"), null);
		String strName = ObjectUtils.toString(conditionMap.get("name"), null);
		String strSysItemId = ObjectUtils.toString(conditionMap.get("sysItemId"), null);
		String strCreateBy = ObjectUtils.toString(conditionMap.get("createBy"), null);
		String strOrderNo = ObjectUtils.toString(conditionMap.get("orderNo"), null);

		boolean bUseType = StringUtils.isBlank(strUseType) ? false : true;
		boolean bRoot = StringUtils.isBlank(strRoot) ? false : true;
		boolean bIndustry = StringUtils.isBlank(strIndustry) ? false : true;
		boolean bParentId = StringUtils.isBlank(strParentId) ? false : true;
		boolean bCtime1 = StringUtils.isBlank(strCtime1) ? false : true;
		boolean bCtime2 = StringUtils.isBlank(strCtime2) ? false : true;
		boolean bName = StringUtils.isBlank(strName) ? false : true;
		boolean bSysItemId = StringUtils.isBlank(strSysItemId) ? false : true;
		boolean bCreateBy = StringUtils.isBlank(strCreateBy) ? false : true;
		boolean bOrderNo = StringUtils.isBlank(strOrderNo) ? false : true;

		if (bUseType || bRoot || bIndustry || bParentId || bCtime1 || bCtime2 || bName || bSysItemId || bCreateBy || bOrderNo) {
			lResult = new ArrayList<Code>();
			for (Iterator<Code> iterator = codes.iterator(); iterator.hasNext();) {
				boolean pass = true;
				Code code = iterator.next();
				if (code != null) {
                    if (bUseType && (!ObjectUtils.equals(ObjectUtils.toString(code.getUseType(), null), strUseType))) {
                        pass = false;
                    }
                    if (pass && bRoot && (!ObjectUtils.equals(ObjectUtils.toString(code.getRoot(), null), strRoot))) {
                        pass = false;
                    }
                    if (pass && bIndustry && (!ObjectUtils.equals(ObjectUtils.toString(code.getIndustry(), null), strIndustry))) {
                        pass = false;
                    }
                    if (pass && bParentId && (!ObjectUtils.equals(ObjectUtils.toString(code.getType(), null), strParentId))) {
                        pass = false;
                    }
                    if (pass && bCtime1 && (ObjectUtils.compare(ObjectUtils.toString(code.getCtime(), null), strCtime1) <= 1)) {
                        pass = false;
                    }
                    if (pass && bCtime2 && (ObjectUtils.compare(ObjectUtils.toString(code.getCtime(), null), strCtime2) >= 1)) {
                        pass = false;
                    }
                    if (pass && bName && (ObjectUtils.toString(code.getName(), "").indexOf(strName) < 0)) {
                        pass = false;
                    }
                    if (pass && bSysItemId && (!ObjectUtils.equals(ObjectUtils.toString(code.getSysItemId(), null), strSysItemId))) {
                        pass = false;
                    }
                    if (pass && bCreateBy && (ObjectUtils.toString(code.getName(), "").indexOf(strName) < 0)) {
                        pass = false;
                    }
                    if (pass && bOrderNo && (!ObjectUtils.equals(ObjectUtils.toString(code.getOrderNo(), null), strOrderNo))) {
                        pass = false;
                    }

					if (pass) {
						lResult.add(code);
					}
				}

			}
		}
		return lResult;
	}

	/**
	 * 功能描述：递归查找
	 * 
	 * @param treeNode
	 * @param storeNode
	 * @return
	 */
	private boolean findNodeList(TreeNode treeNode, TreeNode storeNode) {
		boolean isCreate = false;
		if (treeNode.getChildren().size() > 0) {
			List<TreeNode> nodes = treeNode.getChildren();
			for (TreeNode node : nodes) {
				if (node.getNid().equals(storeNode.getPid())) {
					node.getChildren().add(storeNode);
					isCreate = true;
				} else {
					findNodeList(node, storeNode);
				}
			}
		}
		return isCreate;
	}
	
	private static class CodeComparator implements Comparator<Code> {
		@Override
		public int compare(Code code1, Code code2) {
			String number1 = code1 == null ? null : code1.getNumber();
			String number2 = code2 == null ? null : code2.getNumber();
			return ObjectUtils.compare(number1, number2);
		}

		
	}
}
