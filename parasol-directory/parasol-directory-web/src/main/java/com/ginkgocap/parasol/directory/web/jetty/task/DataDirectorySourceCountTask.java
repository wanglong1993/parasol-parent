package com.ginkgocap.parasol.directory.web.jetty.task;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.ywxt.knowledge.model.KnowledgeBase;
import com.ginkgocap.ywxt.knowledge.service.KnowledgeService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedList;
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

    @Autowired
    private KnowledgeService knowledgeService;

    @Override
    public void run() {
        try {
            deleteUselessSource();
            updateSourceCount();
        } catch (Throwable ex) {
            ex.printStackTrace();
            logger.error("............");
        }
    }

    private void updateSourceCount() {
        logger.info("updateSourceCount........");
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
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void deleteUselessSource() {
        logger.info("deleteUselessSource begin........");
        int total = 0;
        int page = 0;
        final int size = 30;
        // 知识类型
        final byte type = 8;
        List<DirectorySource> allDirectorySource = null;
        List<Long> deleteList = new LinkedList<Long>();
        try {
            logger.info("deleteUselessSource running.......");
            allDirectorySource = directorySourceService.getSourcesBySourceType(page++, size, type);
            while (CollectionUtils.isNotEmpty(allDirectorySource)) {
                for (DirectorySource directorySource : allDirectorySource) {
                    long sourceId = directorySource.getSourceId();
                    long id = directorySource.getId();
                    KnowledgeBase base = knowledgeService.getBaseById(sourceId);
                    if (base == null) {
                        logger.info("----------------KnowledgeBase is empty, id is {}" + sourceId);
                        deleteList.add(id);
                    }
                }
                total += allDirectorySource.size();
                allDirectorySource = directorySourceService.getSourcesBySourceType(page++, size, type);
            }
            logger.info("delete source size is: " + total);
            directorySourceService.removeDirectorySourceByIds(deleteList);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        logger.info("deleteUselessSource complete.......");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("synchronized data begin ====================================");
        new Thread(this).start();
        logger.info("synchronized data complete ====================================");
    }
}
