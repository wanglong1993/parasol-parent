package com.ginkgocap.parasol.metadata.service.impl;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.metadata.exception.NewRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.model.NewRegion;
import com.ginkgocap.parasol.metadata.service.NewRegionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xutlong on 2017/10/30.
 */
@Service("newRegionService")
public class NewRegionServiceImpl extends BaseService<NewRegion> implements NewRegionService {

    private static final String NEWREGION_LIST_BY_ID = "NewRegion_list_Id";

    @Override
    public List<NewRegion> getNewRegionListById(String cid, String id) throws NewRegionServiceException {

        try {
            return getEntitys(NEWREGION_LIST_BY_ID,cid,id);
        } catch (BaseServiceException e) {
            e.printStackTrace();
            throw new NewRegionServiceException(e);
        }
    }
}
