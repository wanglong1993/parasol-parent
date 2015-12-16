package com.ginkgocap.parasol.file.web.jetty.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImageProcessUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageProcessUtil.class);
	
	/**
	 * 根据坐标，宽度、高度切图
	 * @param x
	 * @param width
	 * @param y
	 * @param height
	 * @param image
	 * @return	截图后的字节数组
	 * @throws IOException
	 */
    public static byte[] scissorImage(int x, int width, int y, int height, byte[] image, String fileExtName)
            throws IOException {
    	if(image==null || image.length<=0 || width<= 0 || height<=0) {
			logger.error("根据坐标及长度宽度截图失败，参数错误,width:{}, height:{}", width, height);
			return null;
		}
    	// 输入流
    	ByteArrayInputStream in = new ByteArrayInputStream(image);
    	// 输出流
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	try {
	    	// 读取源图字节流
	    	BufferedImage mImage = ImageIO.read(in);
	    	// 内存中切图
	    	BufferedImage temImage = mImage.getSubimage(x, y, width, height);
	    	// 写入输出流
	    	ImageIO.write(temImage, fileExtName, out);
	    	// 转换成字节数组
	    	byte[] sImage = out.toByteArray();
	    	return sImage;
    	} catch (IOException e) {
    		
    	} finally {
    		if (in != null) in.close();
    		if (out != null) out.close();
    	}
    	return null;
    }

    /**
     * 根据源图片的字节数组，缩略长度，宽度获取缩略图字节数组
     * @param mImage
     * @param width
     * @param height
     * @return	缩略图字节数据
     * @throws IOException
     */
	public static byte[] scaleImage(byte[] mImage, int width,int height, String fileExtName) throws IOException {
		if(mImage==null || mImage.length<=0 || width<= 0 || height<=0) {
			logger.error("缩略图参数错误,width:{}, height:{}", width, height);
			return null;
		}
		// 将字节数组转换成字节流
		ByteArrayInputStream in = new ByteArrayInputStream(mImage);
        // 输出字节流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			// 读取图片字节流
			BufferedImage image = ImageIO.read(in);
			// 获取源图width
			int mWidth = image.getWidth();
			// 获取源图height
			int mHeight = image.getHeight();
			logger.info("源图片长度宽度,width:{}, height:{}", width, height);
			// 缩放比例
	        double ratio = 0;
			// 缩略图
	        Image itemp = image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
			//计算比例     
	        if (mWidth > width || mHeight > height) {
	        	// 计算缩略比例
	        	ratio = Math.min((new Integer(height)).doubleValue() / mHeight, (new Integer(width)).doubleValue() / mWidth);
	            // 使用仿射转换来执行从源图像到目标图像
	        	AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);  
	        	itemp = op.filter(image, null);
	        	// 写入输出字节流
	            ImageIO.write((BufferedImage) itemp,fileExtName, out);  
	        }  
			byte[] sImage = out.toByteArray();
			return sImage;
		} catch (Exception e) {
			logger.error("生成缩略图失败："+e.getMessage());
			e.printStackTrace();
		} finally {
			if (in != null)		in.close();
			if (out != null)	out.close();
		}
		return null;
	}

}
