package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonAttachment;
import com.ginkgocap.parasol.person.service.PersonAttachmentService;
@Service("personAttachmentService")
public class PersonAttachmentServiceImpl extends BaseService<PersonAttachment> implements PersonAttachmentService {
	
	private PersonAttachment checkValidity(PersonAttachment personAttachment,int type)throws Exception {
		if(personAttachment==null) throw new Exception("PersonAttachment can not be null.");
		if(personAttachment.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
//		if(StringUtils.isEmpty(personBasic.getName()))throw new PersonBasicServiceException("The value of  name is null or empty.");
		if(personAttachment.getCtime()==null) personAttachment.setCtime(System.currentTimeMillis());
		if(personAttachment.getUtime()==null) personAttachment.setUtime(System.currentTimeMillis());
		if(type==1)personAttachment.setUtime(System.currentTimeMillis());
		return personAttachment;
	}
	@Override
	public Long createObject(PersonAttachment object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户基本信息失败！ ");
	}

	@Override
	public Boolean updateObject(PersonAttachment objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public PersonAttachment getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		PersonAttachment personAttachment =getEntity(id);
		return personAttachment;
	}

	@Override
	public List<PersonAttachment> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<PersonAttachment> personAttachments =this.getEntityByIds(ids);
		return personAttachments;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}

/*	@Override
	public List<PersonAttachment> createObjects(List<PersonAttachment> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(PersonAttachment personAttachment : objects){
			this.checkValidity(personAttachment, 0);
		}
		return this.saveEntitys(objects);
	}*/
/*	@Override
	public List<PersonAttachment> getObjectsByPersonId(Long personId)
			throws Exception {
		if(personId==null || personId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("PersonAttachment_List_PersonId", personId);
		return this.getEntityByIds(ids);
	}*/
	/*@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}*/
	/*@Override
	public Boolean updateObjects(List<PersonAttachment> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(PersonAttachment personAttachment : objects){
			this.checkValidity(personAttachment, 1);
		}
		return this.updateEntitys(objects);
	}*/
	
	/*@Override
	public Boolean deleteObjectsByPersonId(Long personId) throws Exception {
		if(personId==null) throw new Exception("personId is null or empty");
		this.deleteList("PersonAttachment_List_PersonId", personId);
		return true;
	}*/

}
