package com.ginkgocap.ywxt.metadata.service.dic;

import java.util.List;

import com.ginkgocap.ywxt.metadata.model.Dic;
/**
 * 
 * @author allenshen
 * @date: 2015年10月22日
 * @time: 上午10:33:18
 * Copyright©2015 www.gintong.com
 */
public interface DicService {
	public List<Dic> getDicsByType(Integer type);
}
