package com.ginkgocap.parasol.associate.web.jetty.web.controller;

import com.ginkgocap.parasol.associate.exception.AssociateServiceException;
import com.ginkgocap.parasol.associate.model.AssociateShare;
import com.ginkgocap.parasol.associate.service.AssociateShareService;
import com.gintong.frame.util.dto.CommonResultCode;
import com.gintong.frame.util.dto.InterfaceResult;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path = {"/associate/associate/createAssociateShare"},method = RequestMethod.POST)
    public InterfaceResult createAssociateShare(
            @RequestParam(name = AssociateShareController.parameterContent,defaultValue = "") String content,
            HttpServletRequest request) throws AssociateServiceException {
        try {
            new JsonParser().parse(content);
            AssociateShare associateShare = new AssociateShare();
            associateShare.setContent(content);
            long shareId = associateShareService.createAssociateShare(associateShare);
            return InterfaceResult.getSuccessInterfaceResultInstance(shareId);
        } catch (JsonParseException e) {
            logger.error("bad json: " + content);
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.PARAMS_FORMAT_EXCEPTION);
        } catch (Exception e) {
            return InterfaceResult.getInterfaceResultInstance(CommonResultCode.SYSTEM_EXCEPTION);
        }
    }
}
