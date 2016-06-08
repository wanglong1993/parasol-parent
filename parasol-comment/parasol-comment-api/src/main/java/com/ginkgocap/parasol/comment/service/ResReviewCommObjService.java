package com.ginkgocap.parasol.comment.service;

import java.util.List;

import com.ginkgocap.parasol.comment.exception.ResReviewCommObjServiceException;
import com.ginkgocap.parasol.comment.model.CommObjUpUser;
import com.ginkgocap.parasol.comment.model.ResReviewCommObj;
import com.ginkgocap.parasol.comment.model.Tipoff;

public interface ResReviewCommObjService {
	ResReviewCommObj createResReviewCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	ResReviewCommObj updateResReviewCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	Boolean deleteResReviewCommObj(Long id) throws ResReviewCommObjServiceException;
	ResReviewCommObj findResReviewCommObjById(Long id) throws ResReviewCommObjServiceException;
	ResReviewCommObj createRootCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	ResReviewCommObj findRootCommObj(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	List<ResReviewCommObj> listSubCommObjsForReview(Long pid) throws ResReviewCommObjServiceException;
	List<ResReviewCommObj> listSubCommObjsForRes(ResReviewCommObj obj) throws ResReviewCommObjServiceException;
	CommObjUpUser createCommObjUpUser(CommObjUpUser upUser) throws ResReviewCommObjServiceException;
	CommObjUpUser findCommObjUpUserById(Long id) throws ResReviewCommObjServiceException;
	List<CommObjUpUser> listUpUsers(Long commObjId) throws ResReviewCommObjServiceException;
	Tipoff createTipoff(Tipoff obj) throws ResReviewCommObjServiceException;
	Tipoff findTipoffById(Long id) throws ResReviewCommObjServiceException;
}
