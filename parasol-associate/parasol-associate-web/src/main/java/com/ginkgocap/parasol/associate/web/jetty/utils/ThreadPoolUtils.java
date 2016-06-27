package com.ginkgocap.parasol.associate.web.jetty.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolUtils {

	private static int CPU_CORE_SIZE = Runtime.getRuntime().availableProcessors();
	private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(CPU_CORE_SIZE * 2, CPU_CORE_SIZE * 10, 2, TimeUnit.MINUTES,
			new ArrayBlockingQueue<Runnable>(100), new ThreadPoolExecutor.CallerRunsPolicy());

	public static ExecutorService getExecutorService() {
		return EXECUTOR_SERVICE;
	}

	private ThreadPoolUtils() {
	}

}
