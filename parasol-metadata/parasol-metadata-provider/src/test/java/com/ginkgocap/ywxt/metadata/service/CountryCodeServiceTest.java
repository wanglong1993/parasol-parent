package com.ginkgocap.ywxt.metadata.service;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ginkgocap.ywxt.metadata.base.TestBase;

public class CountryCodeServiceTest  extends TestBase{
	@Resource
	CountryCodeService countryCodeService;

	@Test
	public void testFindAll() {
		Map<String,Object> map = countryCodeService.findAll();
		System.out.println(JSONObject.fromObject(map));
	}

}
