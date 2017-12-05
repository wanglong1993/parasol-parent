package com.ginkgocap.parasol.associate.service.impl;

import com.ginkgocap.parasol.associate.model.AssociateShare;
import com.ginkgocap.parasol.associate.service.AssociateShareService;
import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * Created by xutlong on 2017/11/14.
 */
@Service("associateShareService")
public class AssociateShareServiceImpl extends BaseService<AssociateShare> implements AssociateShareService {
    @Override
    public Long createAssociateShare(AssociateShare associateShare) {
        try {
            return  (Long)this.saveEntity(associateShare);
        } catch (BaseServiceException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    @Override
    public String getAssociateShare(long id) {
        try {
            if(id == 0)
                return "";
            return this.getEntity(id).getContent();
        } catch (BaseServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
}
