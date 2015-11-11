package com.ginkgocap.ywxt.metadata.service2.emailsuffix.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.TreeBag;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.CountryCode;
import com.ginkgocap.ywxt.metadata.model.EmailSuffix;
import com.ginkgocap.ywxt.metadata.service.EmailSuffixService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;

@Service("emailSuffixService")
public class EmailSuffixServiceImpl extends BaseService<EmailSuffix> implements EmailSuffixService {
	private static final String EmailSuffix_List_Id_Status = "EmailSuffix_List_Id_Status";
	
	@Override
	public EmailSuffix findOne(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<String> findAll() {
		//all status =1
		List<String> lResult = new ArrayList<String>();
		try {
			Set<String> suffixSet = new HashSet<String>();
			List<EmailSuffix> emailSuffixs =  getEntitys(EmailSuffix_List_Id_Status, 1);
			if (CollectionUtils.isNotEmpty(emailSuffixs)) {
				for (EmailSuffix emailSuffix : emailSuffixs) {
					if (emailSuffix != null) {
						String suffix = emailSuffix.getSuffix();
						if (StringUtils.isNotBlank(suffix)) {
							suffixSet.add(suffix);
						}
					}
				}
				for (String suffix : suffixSet) {
					lResult.add(suffix);
				}
			}else {
				return ListUtils.EMPTY_LIST;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		return lResult;
	}

	@Override
	public EmailSuffix saveOrUpdate(EmailSuffix emailSuffix) {
		try {
			if (emailSuffix != null) {
				EmailSuffix saveEmailSuffix = getEntity(emailSuffix.getId());
				if (saveEmailSuffix != null) {
					updateEntity(emailSuffix);
				} else {
					Long id = (Long) saveEntity(emailSuffix);
					emailSuffix.setId(id);
				}
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		return emailSuffix;
	}

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
	public Class<EmailSuffix> getEntityClass() {
		return EmailSuffix.class;
	}

}
