package com.ginkgocap.parasol.comment.service;

import com.ginkgocap.parasol.comment.exception.ResReviewCommObjServiceException;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;

public interface ResReviewCommObjService {
	ResReviewCommObj createResReviewCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	ResReviewCommObj updateResReviewCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	Boolean deleteResReviewCommObj(Long id) throws ResReviewCommObjServiceException;
	ResReviewCommObj findResReviewCommObjById(Long id) throws ResReviewCommObjServiceException;
}
