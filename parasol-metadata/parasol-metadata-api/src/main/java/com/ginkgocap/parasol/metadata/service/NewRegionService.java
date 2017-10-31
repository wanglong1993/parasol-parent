package com.ginkgocap.parasol.metadata.service;

import com.ginkgocap.parasol.metadata.exception.NewRegionServiceException;
import com.ginkgocap.parasol.metadata.model.NewRegion;

import java.util.List;

/**
 * Created by xutlong on 2017/10/30.
 */
public interface NewRegionService {

    public List<NewRegion> getNewRegionListById(String cid, String id) throws NewRegionServiceException;

}
