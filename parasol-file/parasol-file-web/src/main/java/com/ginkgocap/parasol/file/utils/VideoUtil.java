package com.ginkgocap.parasol.file.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gintong on 2017/7/5.
 */
public class VideoUtil {

    /**
     * 视频转码并截图
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @param mediaPicPath    截图保存路径
     * @return
     * @throws Exception
     */
    public static boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
                                 String mediaPicPath) throws Exception {
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>(16);
        convert.add(ffmpegPath); // 添加转换工具路径
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
        convert.add("-qscale");     //指定转换的质量
        convert.add("6");
        convert.add("-ab");	//设置音频码率
        convert.add("64");
        convert.add("-ac");	//设置声道数
        convert.add("2");
        convert.add("-ar");	//设置声音的采样频率
        convert.add("22050");
        convert.add("-r");	//设置帧频
        convert.add("24");
        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>(16);
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("17"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*280"); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();

            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }

    /**
     * 视频截图
     * @param ffmpegPath    截图工具的存放路径
     * @param upFilePath    要截图的视频源文件
     * @param mediaPicPath    截图保存路径
     * @param second    添加截图起始时间为第17秒
     * @param format    添加截取的图片大小为352*240
     * @return
     * @throws Exception
     */
    public static boolean executePrintScreenCodecs(
            String ffmpegPath,
            String upFilePath,
            String mediaPicPath,
            String second,
            String format
            ) throws Exception {
        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>(16);
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add(second); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(format); // 添加截取的图片大小为352*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径
        //System.out.println(cutpic.toString());
        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }

    /**
     * 视频截图
     * @param upFilePath    要截图的视频源文件
     * @param second    添加截图起始时间为第17秒
     * @param format    添加截取的图片大小为352*240
     * @return
     * @throws Exception
     */
    public static byte[] executePrintScreenCodecs(
            String upFilePath,
            String second,
            String format
    ) throws Exception {
        File dir = new File("temp");
        if(!dir.exists()) {
            dir.mkdir();
        }
        String mediaPicPath = dir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
        byte[] file_buff = new byte[0];
        String ffmpegPath = "ffmpeg";
        //String ffmpegPath = "E:\\ffmpeg\\ffmpeg.exe";


        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>(16);
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add(second); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(format); // 添加截取的图片大小为352*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径
        //System.out.println(cutpic.toString());
        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {

            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start().waitFor();
            file_buff = getBytes(mediaPicPath);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        } finally {
            try {
                new File(mediaPicPath).delete();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return file_buff;
    }

    /**
     * 获取 视频/音频 总时长
     * @param videoPath    视频路径
     * @param ffmpegPath   ffmpeg路径
     * @return "00:35:51"
     */
    public static String getVideoTime(String ffmpegPath, String videoPath) {
        List<String> commands = new java.util.ArrayList<String>(4);
        commands.add(ffmpegPath);
        commands.add("-i");
        commands.add(videoPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                //float time = getTimelen(m.group(1));
                String time = m.group(1).substring(0,8);//把后面的毫秒数截掉 只要时分秒
                System.out.println(videoPath+",视频时长："+time+", 开始时间："+m.group(2)+",比特率："+m.group(3)+"kb/s");
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return 0;
        return "00:00:00";
    }

    /**
     * 时间格式化为秒
     * @param timelen 格式:"00:00:10.68"
     * @return 返回时长单位为秒
     */
    private static float getTimelen(String timelen){
        float min=0;
        String strs[] = timelen.split(":");
        if (strs[0].compareTo("0") > 0) {
            min+=Integer.valueOf(strs[0])*60*60;//秒
        }
        if(strs[1].compareTo("0")>0){
            min+=Integer.valueOf(strs[1])*60;
        }
        if(strs[2].compareTo("0")>0){
            //min+=Math.round(Float.valueOf(strs[2]));
            min+=Float.valueOf(strs[2]);
        }
        return min;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static File getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }


/*    public static void main(String [] args) throws Exception {
        //当前时间毫秒数
        long presentTime = System.currentTimeMillis();
        //String ffmpegPath = "E:\\ffmpeg\\ffmpeg.exe";
        String upFilePath = "/usr/local/demo/星月神话_官方版--音悦台.mp4";
        String mediaPicPath = "/usr/local/demo/999_352_240.jpg";
        String second = "96";
        String format = "352*240";
        //executePrintScreenCodecs(ffmpegPath, upFilePath, mediaPicPath,second, format);
        byte[] bytes = executePrintScreenCodecs(upFilePath, second, format);
        getFile(bytes, "/usr/local/demo/", "99999_352_240.jpg");
        String videoTime = getVideoTime("ffmpeg", upFilePath);
        System.out.println("视频时长:" + videoTime);
        long presentTime_back = System.currentTimeMillis();
        System.out.println("花费的时间:" + (presentTime_back - presentTime));
    }*/
}
