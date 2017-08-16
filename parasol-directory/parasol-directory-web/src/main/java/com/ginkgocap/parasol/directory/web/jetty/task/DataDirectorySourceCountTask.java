package com.ginkgocap.parasol.directory.web.jetty.task;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by wf on 2017/5/15.
 * 添加表 sourceCount 字段 需要同步数据
 */
public class DataDirectorySourceCountTask implements Runnable, InitializingBean{

    private final Logger logger = LoggerFactory.getLogger(DataDirectorySourceCountTask.class);

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private DirectorySourceService directorySourceService;

    @Override
    public void run() {
        updateSourceCount();
    }

    private void updateSourceCount() {
        int total = 0;
        int page = 0;
        final int size = 30;
        List<Directory> allDirectory = null;
        try {
            allDirectory = directoryService.getAllDirectory(page++, size);
            while (CollectionUtils.isNotEmpty(allDirectory)) {
                for (Directory directory : allDirectory) {
                    int count = directorySourceService.countDirectorySourcesByDirectoryId(0l, 0l, directory.getId());
                    directory.setSourceCount(count);
                    boolean b = directoryService.updateDirectory(directory);
                    if (!b) {
                        logger.error("update directory failed:[ directoryId = " + directory.getId() + "]");
                    }
                    logger.info("update directory success:[ directoryId = " + directory.getId() + "]");
                }
                total += allDirectory.size();
                allDirectory = directoryService.getAllDirectory(page++, size);
            }
            logger.info("update directory size: " + total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("synchronized data begin ====================================");
        //new Thread(this).start();
        logger.info("synchronized data complete ====================================");
    }
}
