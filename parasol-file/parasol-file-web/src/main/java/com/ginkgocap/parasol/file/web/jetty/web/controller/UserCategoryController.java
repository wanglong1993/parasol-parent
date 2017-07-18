package com.ginkgocap.parasol.file.web.jetty.web.controller;

import com.ginkgocap.parasol.file.service.UserCategoryServer;
import com.ginkgocap.parasol.file.web.jetty.web.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xutlong on 2017/7/6.
 */
public class UserCategoryController extends BaseControl {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String parameterFields = "fields";
    private static final String parameterDebug = "debug";
    private static final String parameterId = "id";
    private static final String parameterCategoryName = "categoryName";
    private static final String parameterParentId = "parentId";

    @Autowired
    private UserCategoryServer userCategoryServer;

    @Override
    protected <T> void processBusinessException(ResponseError error, Exception ex) {
    }
}
