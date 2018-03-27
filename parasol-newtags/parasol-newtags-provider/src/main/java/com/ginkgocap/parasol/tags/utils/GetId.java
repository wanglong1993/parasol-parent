package com.ginkgocap.parasol.tags.utils;

import com.ginkgocap.ywxt.framework.dao.id.TimeIdHelper;

import java.util.concurrent.atomic.AtomicLong;

public class GetId {
	public static long getId() {
        long currTime = System.currentTimeMillis();
        AtomicLong sid = new AtomicLong();
        long id = TimeIdHelper.getIdByDate(currTime, sid, 0);
        return id;
    }
}
