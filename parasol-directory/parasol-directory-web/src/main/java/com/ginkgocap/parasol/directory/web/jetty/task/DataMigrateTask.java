package com.ginkgocap.parasol.directory.web.jetty.task;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wf on 2017/4/6.
 * 修改表 orderNo 字段 需要同步数据
 */
public class DataMigrateTask implements Runnable, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(DataMigrateTask.class);

    @Autowired
    private DirectoryService directoryService;

    @Override
    public void run() {
        //updateLevel();
    }


    private void updateLevel() {
        int total = 0;
        int page = 0;
        final int size = 30;
        List<Directory> allDirectory = directoryService.getAllDirectory(page++, size);
        while (CollectionUtils.isNotEmpty(allDirectory)) {
            for (Directory directory: allDirectory) {
                if (directory != null) {
                    long id = directory.getId();
                    String numberCode = directory.getNumberCode();
                    String split = split(numberCode);
                    int count = getCount(split);
                    directory.setOrderNo(count);
                    directory.setNumberCode(split);
                    boolean flag = directoryService.updateDirectory(directory);
                    if (!flag) {
                        logger.error("update directory fail : directoryId [ " + id + " ]");
                    }
                    logger.info("update directory success : directoryId [ " + id + " ]");
                }
            }
            total += allDirectory.size();
            allDirectory = directoryService.getAllDirectory(page++, size);
        }
        logger.info("update directory size: " + total);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("DataMigrateTask begin＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        //new Thread(this).start();
        logger.info("DataMigrateTask complete＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        //Thread.sleep(10000);
    }

    private int getCount(String numberCode) {

        char[] c = numberCode.toCharArray();
        Map<String, Integer> map = new HashMap();
        for (int i = 0; i < c.length; i++) {
            String s = String.valueOf(c[i]);
            if (null != map.get(s)) {
                int count = map.get(s);
                map.put(s, count + 1);
            } else {
                map.put(s, 1);
            }
        }

        Integer count = map.get("-");
        if (count == null) {
            return 1;
        }
        return count + 1;
    }

    private String split(String numberCode) {
        if (numberCode.startsWith("-")) {
            numberCode = numberCode.substring(1);
        }
        return numberCode;
    }
}