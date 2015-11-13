package com.ginkgocap.parasol.common;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.sms.common.Ids;

/**
 * 
* <p>Title: CommonService.java<／p> 
* <p>Description: common service used by ShortMessage <／p> 

* @author fuliwen 
* @date 2015-11-13
* @version 1.0
 */
@Service("commonService")
public class CommonService {
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	/**
	 * 获取行为日志id，自增
	 * @return id
	 */
	public Long getShortMessageIncreaseId() {
		Criteria c = Criteria.where("name").is("kid");

		Update update = new Update();
		Query query = new Query(c);
		Ids hasIds = mongoTemplate.findOne(query, Ids.class);
		Ids ids = new Ids();
		update.inc("cid", 1);
		if (hasIds != null && hasIds.getCid() > 0) {
			ids = mongoTemplate.findAndModify(query, update, Ids.class);
		} else {
			ids.setCid(1l);
			ids.setName("kid");
			mongoTemplate.insert(ids);
			ids = mongoTemplate.findAndModify(query, update, Ids.class);
		}

		return ids.getCid();
	}
}
