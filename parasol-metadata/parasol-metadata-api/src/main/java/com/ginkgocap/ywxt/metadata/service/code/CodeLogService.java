package com.ginkgocap.ywxt.metadata.service.code;

import java.util.List;

import com.ginkgocap.ywxt.metadata.model.CodeLog;
/**
 * 分词Service接口
 * @author 窦友
 */
@Deprecated
public interface CodeLogService {
    /**
     * 通过主键获得类型
     * @param id
     * @return
     */
    CodeLog selectByPrimarKey(long id);
    /**
     * 插入类型操作日志
     * @param codeLog
     * @return
     */
    CodeLog insert(CodeLog codeLog);
    /**
     * 删除操作日志
     * @param codeId 分词id
     */
    int deleteByCodeId(long codeId);
    /**统计某分词的操作日志数量
     * @param codeId 分词id
     * @return
     */
    long countByCodeId(long codeId);
    /**查询操作日志
     * @param codeId 分词id
     * @param startRow 起始行
     * @param pageSize 页大小
     * @return
     */
    List<CodeLog> selectByCodeId(long codeId,long startRow,int pageSize);
}
