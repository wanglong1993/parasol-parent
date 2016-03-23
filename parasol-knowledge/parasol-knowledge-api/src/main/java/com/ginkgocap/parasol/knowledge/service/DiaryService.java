package com.ginkgocap.parasol.knowledge.service;

import java.util.List;

import com.ginkgocap.parasol.knowledge.model.ReceiversInfo;
import com.ginkgocap.parasol.knowledge.model.Diary;

/**
 * 心情日记
 *
 * @author yan
 * @version 2011-12-30
 */
public interface DiaryService {

    Diary insert(Diary diary);

    Diary insertNew(Diary diary);

    void delete(long id);

    /**
     * 更新心情记录
     * @param diary
     * @return long
     */
    long update(Diary diary);

    /**
     * 获取所有心情显示列表
     * @param fname 发布人信息
     * @param dlm 登录名
     * @param uid uid
     * @param mobile 手机号
     * @param status 屏蔽状态0.屏蔽1.正常
     * @param beginDate 起始时间
     * @param endDate 截至时间
     * @param creator 操作人
     * @param content 内容
     * @param shareLevel 公开状态
     * @param start 起始条数
     * @param size 页大小
     * @return List<Diary>
     */
    List<Diary> selectDiary(String fname, String dlm,Long uid, String mobile,Integer status,
                            String beginDate,String endDate, String creator,String content,String shareLevel,Long start,Long size);
    /**
     * 获取屏蔽心情显示列表
     * @param id id
     * @param fname 发布人信息
     * @param dlm 登录名
     * @param uid uid
     * @param mobile 手机号
     * @param status 屏蔽状态0.屏蔽1.正常
     * @param beginDate 起始时间
     * @param endDate 截至时间
     * @param creator 操作人
     * @param content 内容
     * @param start 起始条数
     * @param size 页大小
     * @return List<Diary>
     */
    List<Diary> selectPbDiary(String id,String fname, String dlm,Long uid, String mobile,Integer status,
                              String beginDate,String endDate, String creator,String content,Long start,Long size);

    /**
     * 根据id查询心情
     * @param id
     * @return Diary
     */
    Diary selectDiaryById(Long id);
    /**
     * 获取接受人的信息
     * @author liuyang 2013-11-22 14:26:15
     * @param content 观点
     * @param infoVisible 获取可见级别信息可见； 0公开   -1好友可见   -2自己可见     >0好友组id可见状态
     * @param userId 当前用户Id
     * @return List<ReceiversInfo>
     */
    List<ReceiversInfo> getReceiversInfo(String content,int infoVisible,long userId);
    /**
     * 获取24之内观点信息
     * @author liuyang 2013-11-22 15:26:23
     * @param fbrId 发布人id
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @return List<Diary>
     */
    List<Diary> get24HourDiaryInfo(long fbrId,String startTime,String endTime);

    /**查询所有的接收人，如果是全公开的，返回""
     * @param id 观点id
     * @return
     */
    String selectReceivers(long id);

    /**查询所有的观点，用于全文索引
     * @param startRow 起始行
     * @param ppageSize 大小
     * @return
     */
    List<Diary> selectAllDiaryForIndex(long startRow,int ppageSize);

    /**检验是否有权限查看该观点
     * @param id
     * @return
     */
    boolean checkRole(long id,long userId);
}