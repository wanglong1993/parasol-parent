package com.ginkgocap.ywxt.metadata.service2.code.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.RCodeDock;
import com.ginkgocap.ywxt.metadata.service.code.RCodeDockService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;
@Deprecated
@Service("rCodeDockService")
public class RCodeDockServiceImpl extends BaseService<RCodeDock> implements RCodeDockService {
	private static Logger logger = Logger.getLogger(RCodeDockServiceImpl.class);
	private static final String selectDockTz = "selectDockTz";
	

	@Override
	public RCodeDock selectByPrimarKey(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public RCodeDock save(RCodeDock rCodeDock) {
		try {
			Long id = (Long) saveEntity(rCodeDock);
			if (id != null) {
				rCodeDock.setId(id);
				return rCodeDock;
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int update(RCodeDock rCodeDock) {
		try {
			if (rCodeDock.getId() != 0) {
				updateEntity(rCodeDock);
				return 1;
			} else {
				return 0;
			}
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public int delete(long id) {
		try {
			deleteEntity(id);
			return 1;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public String dockTz(String tz) {
		logger.error("Deprecated method call");
		return null;	}

	@Override
	public List<RCodeDock> dockTzList(String tz) {
		logger.error("Deprecated method call");
		return null;
	}

	@Override
	public String dockRz(String rz) {
		logger.error("Deprecated method call");
		return null;	}

	@Override
	public List<RCodeDock> dockRzList(String rz) {
		logger.error("Deprecated method call");
		return null;
	}

	@Override
	public Map<String, Object> selectByParam(String rzName, String tzName, DataGridModel dgm) {
		throw new RuntimeException("Please implements the method");
	}

	@Override
	public Class<RCodeDock> getEntityClass() {
		return RCodeDock.class;
	}

}
