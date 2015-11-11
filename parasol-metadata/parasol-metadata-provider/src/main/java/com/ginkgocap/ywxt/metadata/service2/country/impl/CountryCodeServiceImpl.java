package com.ginkgocap.ywxt.metadata.service2.country.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.CountryCode;
import com.ginkgocap.ywxt.metadata.service.CountryCodeService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;

@Service("countryCodeService")
public class CountryCodeServiceImpl extends BaseService<CountryCode> implements CountryCodeService {
	private static final String selectAll = "CountryCode_List_Id_All";

	@Override
	@Deprecated
	public CountryCode findOne(long id) {
		try {
			return getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> findAll() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<CountryCode> cuntryCodes;
		try {
			cuntryCodes = getEntitys(selectAll, 1);
			Set<String> nameFirstSet = new TreeSet<String>();
			for (CountryCode countryCode : cuntryCodes) {
				if (countryCode != null) {
					String nameFirst = countryCode.getNameFirst();
					if (StringUtils.isNotBlank(nameFirst)) {
						nameFirstSet.add(nameFirst);
					}
				}
			}

			List<Object> nameFirstlist = Arrays.asList(nameFirstSet.toArray());

			resultMap.put("listNameFirst", nameFirstlist);
			resultMap.put("listCountry", cuntryCodes == null ? ListUtils.EMPTY_LIST : cuntryCodes);

			return resultMap;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Deprecated
	@Override
	public CountryCode saveOrUpdate(CountryCode countryCode) {
		try {
			if (countryCode != null) {
				CountryCode saveCountryCode = getEntity(countryCode.getId());
				if (saveCountryCode != null) {
					updateEntity(saveCountryCode);
				} else {
					Long id = (Long) saveEntity(countryCode);
					countryCode.setId(id);
				}
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		return countryCode;
	}

	@Deprecated
	@Override
	public void delete(long id) {
		try {
			deleteEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class<CountryCode> getEntityClass() {
		return CountryCode.class;
	}

}
