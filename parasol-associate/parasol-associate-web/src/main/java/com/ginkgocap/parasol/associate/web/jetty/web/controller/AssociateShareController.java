package com.ginkgocap.parasol.associate.web.jetty.web.controller;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.model.AssociateShare;
import com.ginkgocap.parasol.associate.service.AssociateShareService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xutlong on 2017/11/15.
 */
@RestController()
public class AssociateShareController extends BaseControl {
    private static Logger logger = Logger.getLogger(AssociateShareController.class);

    private static final String parameterShareId = "shareId";
    private static final String parameterContent = "content";

    @Autowired
    private AssociateShareService associateShareService;

    /**
     * 保存关联权限
     * @param request
     * @return
     * @throws AssociateServiceException
     */
    @RequestMapping(path = {"/associate/associate/createAssociateShare"},method = RequestMethod.POST)
    public InterfaceResult  createAssociateShare(HttpServletRequest request) throws AssociateServiceException {
        try {
            String content = request.getParameter("content");
            if (content == null || content.equals("") )
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            AssociateShare associateShare = new AssociateShare();
            associateShare.setContent(content);
            long shareId = associateShareService.createAssociateShare(associateShare);
            return InterfaceResult.getSuccessInterfaceResultInstance(shareId);
        } catch (Exception e) {
            e.printStackTrace();
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }

    /**
     * 获取关联权限信息
     * @param shareId
     * @param request
     * @return
     */
    @RequestMapping(path = {"/associate/associate/getAssociateShare"},method = RequestMethod.GET)
    public InterfaceResult getAssociateShare(
            @RequestParam(name = AssociateShareController.parameterShareId,defaultValue = "0") long shareId,
            HttpServletRequest request
    ) {
        try {
            if (shareId == 0) {
                return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_EXCEPTION);
            }
            String content = associateShareService.getAssociateShare(shareId);
            return InterfaceResult.getSuccessInterfaceResultInstance(content);
        } catch (Exception e) {
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }

    }
}
