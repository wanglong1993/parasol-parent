package com.ginkgocap.parasol.knowledge.model.common;

import java.io.Serializable;

/**
 * <p>DESCRIPTION:  给客户端提供错误编码的枚举对象
 * <p>CALLED BY:	钱明金
 * <p>CREATE DATE: 2015/12/9
 *
 * @version 1.0
 * @since java 1.7.0
 */
public enum CommonResultCode implements Serializable{

    SUCCESS("0", "success"),
    SYSTEM_EXCEPTION("10000", "系统发生未知异常"),
    PERMISSION_EXCEPTION("10001", "用户长时间未操作或者未登录，权限失效！"),
    PARAMS_EXCEPTION("10002", "参数错误"),
    PARAMS_NULL_EXCEPTION("10003", "参数不能为空"),
    PARAMS_FORMAT_EXCEPTION("10004", "参数类型错误"),
    PARAMS_METHODID_EXCEPTION("10005", "methodId参数不能为空"),
    PARAMS_DB_OPERATION_EXCEPTION("10006", "数据库操作失败"),


    PARAMS_DEMAND_EXCEPTION_60001("60001", "需求创建失败,数据转换失败,请检查数据格式正确性!"),
    PARAMS_DEMAND_EXCEPTION_60002("60002", "需求创建失败,需求标题为空!"),
    PARAMS_DEMAND_EXCEPTION_60003("60003", "需求添加失败!"),
    PARAMS_DEMAND_EXCEPTION_60004("60004", "要更新的需求不 存在"),
    PARAMS_DEMAND_EXCEPTION_60005("60005", "需求ID不合法!"),
    PARAMS_DEMAND_EXCEPTION_60006("60006", "需求类型不合法!"),
    PARAMS_DEMAND_EXCEPTION_60007("60007", "权限信息为空或转换失败!"),
    PARAMS_DEMAND_EXCEPTION_60008("60008", "关联信息为空或转换失败!"),
    PARAMS_DEMAND_EXCEPTION_60011("60011", "该需求已被收藏"),
    PARAMS_DEMAND_COMMENT_EXCEPTION_60051("60051", "需求评论不存在或没有权限!");

    private final String code;
    private final String msg;

    CommonResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }
}
