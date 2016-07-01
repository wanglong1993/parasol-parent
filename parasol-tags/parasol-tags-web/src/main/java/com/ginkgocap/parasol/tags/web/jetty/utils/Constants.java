package com.ginkgocap.parasol.tags.web.jetty.utils;

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

}
