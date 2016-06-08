package com.ginkgocap.parasol.organ.web.jetty.web.service.organ.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.UserTag;
import com.ginkgocap.ywxt.metadata.service.userTag.UserTagService;
import com.ginkgocap.ywxt.organ.model.RCustomerTag;
import com.ginkgocap.ywxt.organ.service.tag.RCustomerTagService;
import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.OrganTagService;
@Service("OrganTagService")
public class OrganTagServiceImpl implements OrganTagService {
	@Resource
    private UserTagService userTagService;
	@Resource
	private RCustomerTagService rCustomerTagService;
     public UserTag save(String tagName,long userId) {
    	 UserTag tag = new UserTag();
    	 tag.setTagName(tagName);
    	 tag.setUserId(userId);
    	 UserTag newTag = userTagService.insert(tag);
    	 RCustomerTag rTag = new  RCustomerTag();
    	 rTag.setTagId(newTag.getTagId());
    	 rTag.setTagName(newTag.getTagName());
    	 rTag.setCustomerId(0); //表示没有被引用
    	 rTag.setCreatorId(userId);
    	 rCustomerTagService.save(rTag);
		return newTag;
     }
}

