package com.ginkgocap.parasol.organ.web.jetty.web.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static List<String> pic = new ArrayList<String>() {
		{
			add("JPG");
			add("PNG");
			add("BMP");
			add("JPEG");
			add("GIF");
		}
	};
	public static List<String> video = new ArrayList<String>() {
		{
			add("AVI");
			add("MPEG");
			add("MPG");
			add("QT");
			add("RAM");
			add("VIV");
			add("MP4");
			add("3GP");
			add("WMV");
			add("RMVB");
			add("MKV");
			add("VOB");
			add("SWF");
		}
	};
	public static List<String> doc = new ArrayList<String>() {
		{
			add("DOC");
			add("DOCX");
			add("WPS");
			add("WORD");
			add("RTF");
			add("TXT");
			add("PDF");
			add("PPT");
			add("PPTX");
			add("XLS");
			add("ZIP");
			add("RAR");
			add("XLSX");
		}
	};
	public static List<String> audio = new ArrayList<String>() {
		{
			add("AMR");
			add("AIF");
			add("SVX");
			add("SND");
			add("MID");
			add("VOC");
			add("WAV");
			add("MP3");
		}
	};
	
	public static long gtnid = 0l;

	public final static String status = "result";

	public final static String msg = "msg";

	public final static String permIds = "permIds";

	public final static String categoryIds = "categoryIds";

	public final static String TAG = "tag";

	public final static String CONN = "conn";

	public final static String ALL = "-9";

	public final static String asso = "asso";

	/** 缩略图地址 **/
	public final static String thumbnailsPath = "/web1/thumbnails/";
	//public final static String thumbnailsPath = "/thumbnails/";

	/** 缩略图宽度 **/
	public final static Integer imgWidth = 153;

	/** 缩略图高度 **/
	public final static Integer imgHeight = 153;

	/** 全平台 **/
	public final static String platform = "\"id\":-1";

	/** 金桐脑 **/
	public final static String gintong = "\"id\":0";

	/** 独乐 **/
	public final static String dule = "dule";
	/** 大乐 **/
	public final static String dales = "dales";
	/** 中乐 **/
	public final static String zhongles = "zhongles";
	/** 小乐 **/
	public final static String xiaoles = "xiaoles";

	public enum ResultType {
		fail(0), success(1);

		private int v;

		private ResultType(int v) {
			this.v = v;
		}

		public int v() {
			return v;
		}
	}

	public static enum PermissionType {
		dule(1, "dule"), dales(2, "dales"), zhongles(3, "zhongles"), xiaoles(4,
				"xiaoles");
		private int v;

		private String c;

		private PermissionType(int v, String c) {
			this.v = v;
			this.c = c;
		}

		public int v() {
			return v;
		}

		public String c() {
			return c;
		}
	}

	public static enum Ids {
		jinTN(0), platform(-1);

		private int v;

		private Ids(int v) {
			this.v = v;
		}

		public int v() {
			return v;
		}
	}

	public static enum ConnectType {
		// 关联格式（p:人脉,r:事件,o:组织,k:知识）
		event(1, "r"), people(2, "p"), organization(5, "o"), knowledge(6, "k");
		private int v;

		private String c;

		private ConnectType(int v, String c) {
			this.v = v;
			this.c = c;
		}

		public int v() {
			return v;
		}

		public String c() {
			return c;
		}
	}

	public static enum FileType {
		pic(1), video(2), file(3);
		private int v;

		private FileType(int v) {
			this.v = v;
		}

		public int v() {
			return v;
		}

	}
	public static enum LoginType {
		//web直接登录       //第三方登录
		webLogin(1), thirdLogin(2);
		private int v;

		private LoginType(int v) {
			this.v = v;
		}

		public int v() {
			return v;
		}

	}

	public static int success = 0;
	public static int fail = 1;

	public enum ErrorMessage {
		levelPathTooLong("目录级别不能超过5级!"), hasName("名称重复!"), tagExist("标签已存在"), categoryExist("该目录已存在"),sensitiveWord("您的文章存在敏感词"), artNotExsit(
				"亲爱的用户你好：你所查看的文章不存在或被删除!"), demandNotExsit( "亲爱的用户你好：你所查看的需求不存在或被删除!"), addKnowledgeFail("添加知识失败!"), addCollFail("文章收藏失败!"), addColumnFail(
				"添加栏目失败!"), alreadyCollection("您已经收藏过该文章!"), addCommentFail("评论失败!"), artUserNotExsit("文章作者不存在!"), addReportFail(
				"添加举报失败!"), columnNotFound("未找到知识所属栏目"), addFriendsFail("添加好友失败!"), addFriendsWaiting(
				"您已申请过添加好友,请耐心等待!"), checkColumnFail("栏目名已存在!"), IsFriends("您与该用户已是好友关系!"), UserNotExisitInSession(
				"请确认是否登陆!"), contentIsBlank("评论内容不能为空!"), commentNotExsit("评论不存在!"), delCommentNotPermission("无权删除该评论!"), delCommentFail(
				"删除评论失败!"), notFindColumn("栏目不存在，请刷新页面后重试!"), delColumnNotPermission("无权删除该栏目!"), delFail("删除失败!"), paramNotValid(
				"用户权限参数不合法!"), updateFail("更新失败!"), paramNotBlank("参数不能为空"), nameNotBlank("名称不能为空"), contentTooLong("内容过长"), artPermissionNotFound(
				"对不起,您没有查看该文章的权限!"), demandPermissionNotFound("对不起,您没有查看该需求的权限!") ,parseError("解析错误!"), userNotLogin("您未登陆,请先登陆!"), addasso("添加关联失败!");
		String v;

		ErrorMessage(String v) {
			this.v = v;
		}

		public String v() {
			return v;
		}
	}

	public static final String USER_DEFAULT_PIC_PATH_FAMALE = "/web/pic/user/default.jpeg";//个人默认头像：女
	public static final String USER_DEFAULT_PIC_PATH_MALE = "/web/pic/people/original/default.jpeg";//个人默认头像：男
	public static final String ORGAN_DEFAULT_PIC_PATH = "/web1/organ/avatar/default.jpeg";//组织、客户默认头像
    public static final String WEB = "web";
    /**
     * 维护人类型标识
     */
    public static final int _MAIN_STRING = 0;
    /**
     * 浏览人类型标识
     */
    public static final int _VIEW_STRING = 1;

    public static final String TB_CUSTOMER_GROUP = "tb_business_customer_group"; //客户分组表
    public static final String TB_PEOPLE_GROUP = "tb_business_people_group";	 //人脉分组表



    /**
     * 消息业务类型：1需求中心，2业务需求，3项目，4人脉，5客户，6人员管理，7知识库,9人员管理
     */

    public static final String BM_REQUIRE = "1";	 //需求中心
    public static final String BM_BUSINESS_REQUIRE = "2";	 //业务需求
    public static final String BM_PROJECT = "3";	 //项目
    public static final String BM_PEOPLE = "4";	 //人脉
    public static final String BM_CUSTOMER = "5";	 //客户
    public static final String BM_EMPLOYEE = "6";	 //人员管理
    public static final String BM_KNOWLEDGE = "7";	 //知识库
    public static final String BM_CONTRACT = "9";	 //人员管理


    /**
     * 消息子任务类型：0通知消息 1告知消息
     */
    public static final int BM_MSGTYPE_NOTICE = 0;	 //通知消息
    public static final int BM_MSGTYPE_INFORM = 1;	 //告知消息

    public static final String BM_STATUS_UNREAD= "0";	 //
    public static final String BM_STATUS_READ = "1";	 //

    /**
     * 消息权息类型：0表示其他权限，1转出权限,2转入权限，3无需权限
     */
    public static final int BM_NETTYPE_OTHER = 0;	 //其他权限
    public static final int BM_NETTYPE_EXTERNAL = 1; //转出权限
    public static final int BM_NETTYPE_INTERNAL = 2; //转入权限
    public static final int BM_NETTYPE_NOTHING = 3;	 //无需权限


    /**
     * 分享接受方 0 互联网个人好友 1 业务系统事业部 2机构好友 3互联网
     */
    public static final String SHARE_FRIEND = "0";
    public static final String SHARE_INC = "1";
    public static final String SHARE_ORG = "2";
    public static final String SHARE_NET = "3";


    /**
     * 分享业务状态
     */
    public static final String SHARE_STATUS_NEW = "6";		//初始状态
    public static final String SHARE_STATUS_FAIL = "5";	//分享失败（拒绝转出或者拒绝转入）
    public static final String SHARE_STATUS_PASS = "4";	//转出成功（转出成功或者转入成功）

    /**
     * 分享业务状态
     */
    public static final int SHARE_OUT = 1;		//业务转出
    public static final int SHARE_IN = 2;	//业务转入

    /**
     * 1：人脉,2:客户，3:需求业务,4:项目,5:知识,
     */
    public static final String SHARE_PEOPLE = "1";	 //人脉
    public static final String SHARE_CUSTOMER = "2";	 //客户
    public static final String SHARE_REQUIRE = "3";	 //需求中心
    public static final String SHARE_PROJECT = "4";	 //项目
    public static final String SHARE_KNOWLEDGE = "5";	 //人员管理
    public static final int REMIND_STATUS_NEW = 0;
    public static final int REMIND_STATUS_FINISH = 1;

    //缓存中用到的 String  key
    //将和登录用户相关的部门、员工、公司部门员工关系、所有子事业部等信息放到session中
    public final static String SELECT_DEPT_KEYWORD = "select.dept";
    public final static String SELECT_ALL_DEPT_KEYWORD = "select.all.dept";
    public final static String SELECT_ALL_DEPT_STR_KEYWORD = "select.all.dept.str";
    public final static String SELECT_EMPLOYEE_KEYWORD = "select.employee";
    public final static String SELECT_DEPT_EMPLOYEE_KEYWORD = "select.dept.employee";

    public final static String SELECT_ALL_USER = "select.all.user";

    public final static String SOCIAL_TYPE_BUSS = "事务沟通";
    public final static String SOCIAL_TYPE_SHARE = "发分享";
    public final static String SOCIAL_TYPE_TASK = "发任务";

    public final static String SOCIAL_TASK_PRO = "项目";
    public final static String SOCIAL_TASK_REQ= "业务需求";
    public final static String SOCIAL_TASK_OTH = "其他任务";


    public final static int SOCIAL_SENDSMS_YES = 1; //是否发送短信
    public final static int SOCIAL_SENDSMS_NO = 0; //是否发送短信

    public static final int PROGRAM_DAY_NUM = 1;


    /***match bigdata redis key Beging ***/
    /***机构匹配投需求***/
    public static final String REDIS_APP_PEOPLE_RECOMMENDS_REQS = "APP_people_recommends_reqs";
    /***机构匹配投关系 ***/
    public static final String REDIS_APP_PEOPLE = "APP_people";
    /***需求匹配知识 ***/
    public static final String REDIS_APP_REQ_KNOWLEDGE = "APP_req_knowledge";
    /***需求匹配投融资 ***/
    public static final String REDIS_APP_REQ_MATCH = "APP_req_match";
    /***需求匹配投关系 ***/
    public static final String REDIS_APP_REQ_PEOPLE = "APP_req_people";
    /***业务需求匹配投知识 ***/
    public static final String REDIS_APP_BUSINESS_REQ_KNOWLEDGE = "APP_business_req_knowledge";
    /***业务需求匹配投需求 ***/
    public static final String REDIS_APP_BUSINESS_REQ_MATCH = "APP_business_req_match";
    /***业务需求匹配投关系 ***/
    public static final String REDIS_APP_BUSINESS_REQ_PEOPLE = "APP_business_req_people";
    /***项目匹配投知识 ***/
    public static final String REDIS_APP_PRJ_REQ_KNOWLEDGE = "APP_prj_req_knowledge";
    /***项目匹配投需求 ***/
    public static final String REDIS_APP_PRJ_REQ_MATCH = "APP_prj_req_match";
    /***项目匹配投关系 ***/
    public static final String REDIS_APP_PRJ_REQ_PEOPLE = "APP_prj_req_people";
    /***match bigdata redis key End ***/

    /**
     * 静态资源对象常量key值
     */
    public static final String CONST_KEY = "CONST_KEY";

    /**
     * 需要强制更新的版本<br/>
     * 说明：传null、""、VERSIONS中包含的值都会提示更新
     */
    private static final String[]  VERSIONS = {"0.045"};
    public static boolean checkUpdateVersions(String version){
        if(StringUtils.isEmpty(version)){
            return true;
        }
        boolean result = false;
        for(String obj : VERSIONS){
            if(obj.equals(version)){
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * 当前登录用户所属公司、部门信息
     * @return
     */
	/* public static Map<String, Object> getDeptMap() {
	        return (Map<String, Object>)SessionManager.getCurrentSession().getAttribute(Constants.SELECTDEPT_SNAPSHOT_KEYWORD);
	    }*/
}
