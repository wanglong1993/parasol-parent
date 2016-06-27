package com.ginkgocap.parasol.directory.web.jetty.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class GenPic {
	/**
	 * 
	 * @param source
	 * @param fileType
	 * @param nfsHome
	 * @param imageWidth1
	 *            // 设置图片像素为30*30,60*60,90*90, the value is 30 or 60 or 90
	 * @param crop_x
	 * @param crop_y
	 * @param crop_width
	 * @param crop_height
	 * @param image_width
	 * @param image_height
	 * @throws Exception
	 */
	public static String genPic(String target, String targetFileId,
			String source, String fileType, String nfsHome, int imageWidth1,
			float crop_x, float crop_y, float crop_width, float crop_height,
			float image_width, float image_height) throws Exception {

		// 截取图片的起点坐标
		Integer x1 = Math.round(crop_x);
		Integer y1 = Math.round(crop_y);
		// 图片截取的长和宽
		Integer width = Math.round(crop_width);
		Integer height = Math.round(crop_height);
		Integer imgWidthOnPage = Math.round(image_width);
		Integer imgHeightOnPage = Math.round(image_height);

		// 获取原始图片文件对象
		String sourcePath = nfsHome + source;
		sourcePath = sourcePath.replace("/", File.separator);
		File sourceFile = new File(sourcePath);
		// 建立本地临时文件对象
		File tempDir = FileUtils.getTempDirectory();
		String tempStr = String.valueOf(System.currentTimeMillis())
				+ String.valueOf(Math.random()) + fileType;
		File temp = new File(tempDir, tempStr);
		// 把原始文件copy到本地
		FileUtils.copyFile(sourceFile, temp);
		// 临时文件对象路径
		String tempPath = temp.getAbsolutePath();
		// 切图
		if (imgWidthOnPage > 0 && imgHeightOnPage > 0) {
			ImageUtils.scaleImage(imgWidthOnPage, imgHeightOnPage, tempPath,
					tempPath);
		}
		if (height > 0 && width > 0) {
			ImageUtils.scissor(x1, width, y1, height, tempPath, tempPath);// 新路径
		}
		ImageUtils.scaleImage(imageWidth1, imageWidth1, tempPath, tempPath);
		// 切图之后的文件对象
		File copyFile = new File(tempPath);
		// 目标文件的绝对地址
		String nginxOriginPath = nfsHome + target;
		nginxOriginPath = nginxOriginPath.replace("/", File.separator);
		String dir = nginxOriginPath.substring(0, source.lastIndexOf("/") + 1);
		File dirFile = new File(dir);
		// 把切图之后的文件copy到目标地址
		FileUtils.copyFile(copyFile, new File(nginxOriginPath));
		copyFile.delete();
		return nginxOriginPath;
	}

	public static String get90PicPath(String source, String targetFileId,
			String fileType) {
		String tmp = source.substring(0, source.lastIndexOf("/"));
		String target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "90/"
				+ targetFileId + fileType;
		return target;
	}

	public static String get140PicPath(String source, String targetFileId,
									  String fileType) {
		String tmp = source.substring(0, source.lastIndexOf("/"));
		String target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "140/"
				+ targetFileId + fileType;
		return target;
	}

	public static String get60PicPath(String source, String targetFileId,
			String fileType) {
		String tmp = source.substring(0, source.lastIndexOf("/"));
		String target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "60/"
				+ targetFileId + fileType;
		return target;
	}

	public static String get30PicPath(String source, String targetFileId,
			String fileType) {
		String tmp = source.substring(0, source.lastIndexOf("/"));
		String target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "30/"
				+ targetFileId + fileType;
		return target;
	}

	public static String get60PicPathBy90PicPath(String path) {
		return path.replace("/original/", "/90/").replace("/90/", "/60/");
	}

	public static String get30PicPathBy90PicPath(String path) {
		return path.replace("/original/", "/90/").replace("/90/", "/30/");
	}
	/**
	 * 
	 * @param target 目标相对目录
	 * @param targetFileId 目标文件名
	 * @param source 源文件相对路径
	 * @param fileType 文件后缀
	 * @param nfsHome 文件根目录
	 * @return
	 * @throws Exception
	 * liubang
	 */
	public static String genPic(String target, String targetFileId,
			String source, String fileType, String nfsHome) throws Exception {

		// 获取原始图片文件对象
		String sourcePath = nfsHome + source;
		sourcePath = sourcePath.replace("/", File.separator);
		File sourceFile = new File(sourcePath);
		// 目标文件的绝对地址
		String nginxOriginPath = nfsHome + target;
		nginxOriginPath = nginxOriginPath.replace("/", File.separator);
		File targetFile = new File(nginxOriginPath);
		if(targetFile.exists() || !targetFile.isDirectory()){
			targetFile.mkdir();
		}
		// 把切图之后的文件copy到目标地址
		FileUtils.copyFile(sourceFile, new File(nginxOriginPath+File.separator+targetFileId+fileType));
		return target+"/"+targetFileId+fileType;
	}
	/**
	 * 
	 * @param source 相对路径
	 * @param type //1营业执照图片、2身份证 、3名片图片 、4头像
	 * @return
	 * liubang
	 */
	public static String getIdCardPicPath(String source,String type) {
		String tmp = StringUtils.substringBeforeLast(source, "/");
		String target ="";
		if("1".equals(type)){//1营业执照图片
			 target = tmp + "/organ";
        }else if("3".equals(type)){//3名片图片
        	 target = tmp + "/card";
        }else if("4".equals(type)){//4头像
       	 	target = tmp + "/avatar";
        }else{//默认身份证
        	 target = tmp + "/idCard";
		}
		
		return target;
	}
	
	public static String getIdCardPicPath(String source, String targetFileId,
			String fileType,String type) {
		String tmp = source.substring(0, source.lastIndexOf("/"));
		String target ="";
		if("1".equals(type)){
			 target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "organization/"
					+ targetFileId + fileType;
        }else if("2".equals(type)){
        	 target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "license/"
					+ targetFileId + fileType;
        }else if("4".equals(type)){//4
       	 target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "linkIdCard/"
					+ targetFileId + fileType;
       }
        else{
        	target = tmp.substring(0, tmp.lastIndexOf("/") + 1) + "idCard/"
    				+ targetFileId + fileType;
		}
		
		return target;
	}
	
	public static void main(String[] args) {
		String source="/web1/1111";
		String type="1";
		String targetFileId=MakePrimaryKey.getPrimaryKey();
		String fileType=".jpg";
		String nfsHome="D://";
		String target=getIdCardPicPath(source, type);
		System.out.println(target);
		try {
			String nginxOriginPath = genPic(target, targetFileId, source, fileType, nfsHome);
			System.out.println(nginxOriginPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
