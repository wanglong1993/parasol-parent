package com.example.demo.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by gintong on 2017/7/5.
 */
public class ImagUtil {

    /**
     * 判断一个文件 是否是图片
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        boolean valid = false;
        try {
            ImageInputStream image = ImageIO.createImageInputStream(file);
            if (image == null) {
                System.out.println("The file could not be opened , it is not an image");
                return valid;
            }
            image.close();
            valid = true;
        } catch(IOException ex) {
            valid=false;
            System.out.println("The file could not be opened , an error occurred.");
        }
        return valid;
    }

    /**
     * 图片缩放
     * @param originalFile 原图片
     * @param resizedFile 缩放后的图片
     * @param newWidth 缩放后的宽度 高度会等比例缩放
     * @param quality 压缩比列 0-1
     * @throws IOException
     */
    public static void resize(File originalFile, File resizedFile,
                              int newWidth, float quality) throws IOException {

        if (quality > 1) {
            throw new IllegalArgumentException(
                    "Quality has to be between 0 and 1");
        }

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);

        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
                    / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
                    newWidth, Image.SCALE_SMOOTH);
        }

        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();

        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();

        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor,
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);

        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        JPEGEncodeParam param = encoder
                .getDefaultJPEGEncodeParam(bufferedImage);

        param.setQuality(quality, true);

        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }

/*    public static void main(String []args) throws IOException{
        //File originalImage = new File("C:\\Users\\gintong\\Pictures\\Saved Pictures\\642066_201604081219000068555822.jpg");
       // resize(originalImage, new File("C:\\Users\\gintong\\Pictures\\Saved Pictures\\642066_201604081219000068555822_0.jpg"),150, 0.7f);
        //resize(originalImage, new File("C:\\Users\\gintong\\Pictures\\Saved Pictures\\642066_201604081219000068555822_1.jpg"),150, 1f);
        boolean image = isImage(new File("C:\\Users\\gintong\\Pictures\\Saved Pictures\\642066_201604081219000068555822.jpg"));
        System.out.println(image);
    }*/
}
