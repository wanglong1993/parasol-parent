package com.ginkgocap.ywxt.metadata.service.code;

import java.util.List;
import java.util.Map;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.Code;
import com.ginkgocap.ywxt.metadata.model.RCodeDock;

/**
 * 类型对接关系接口
 * @author liubang
 * 创建时间：
 */
@Deprecated
public interface RCodeDockService {
	/**
	 * 按主键检索
	 * @param id
	 * @return
	 */
	RCodeDock selectByPrimarKey(long id);
	/**
	 * 保存类型对接关系
	 * @param rCodeDock
	 * @return
	 */
	RCodeDock save(RCodeDock rCodeDock);
	/**
	 * 更新对接关系
	 * @param rCodeDock
	 */
	int update(RCodeDock rCodeDock);
	/**
	 * 删除对接关系
	 * @param id
	 * @return
	 */
	int delete(long id);
	/**
	 * 投资类型相对接的融资类型
	 * @param tz 投资类型
	 * @return
	 */
	String dockTz(String tz);
	/**
	 * 投资类型相对接的融资类型
	 * @param tz 投资类型
	 * @return
	 */
	List<RCodeDock> dockTzList(String tz);
	/**
	 * 融资类型对接的投资类型
	 * @param rz 融投资类型
	 * @return
	 */
	String dockRz(String rz);
	/**
	 * 融资类型对接的投资类型
	 * @param rz 融投资类型
	 * @return
	 */
	List<RCodeDock> dockRzList(String rz);
	/**
	 * 检索对接数据
	 * @param rzName
	 * @param tzName
	 * @param dgm
	 * @return
	 */
	Map<String,Object> selectByParam(String rzName,String tzName, DataGridModel dgm);
}
