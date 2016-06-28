package com.ginkgocap.parasol.comment.service;

import com.ginkgocap.parasol.comment.exception.CommObjServiceException;
import com.ginkgocap.parasol.comment.model.CommObj;

public interface CommObjService {
	CommObj createCommObj(CommObj obj) throws CommObjServiceException;
	CommObj updateCommObj(CommObj obj) throws CommObjServiceException;
	Boolean deleteCommObj(Long obj) throws CommObjServiceException;
	CommObj findCommObjById(Long id) throws CommObjServiceException;
}
