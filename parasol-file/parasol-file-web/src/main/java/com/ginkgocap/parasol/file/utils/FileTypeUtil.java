package com.ginkgocap.parasol.file.utils;

import com.ginkgocap.parasol.file.constant.FileType;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gintong on 2017/7/18.
 */
public class FileTypeUtil {

    public static final List<String> pic = new ArrayList<String>() {
        {
            add("JPG");
            add("PNG");
            add("BMP");
            add("JPEG");
            add("GIF");
        }
    };

    public static final List<String> video = new ArrayList<String>() {
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

    public static final List<String> doc = new ArrayList<String>() {
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
    public static final List<String> audio = new ArrayList<String>() {
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

    /**
     *
     * @param fileNameWithSuffix 带后缀的文件名
     * @return FileType所对应的文件类型
     */
    public static int getFileTypeByFileSuffix(String fileNameWithSuffix) {
        int result = -1;
        if(Strings.isNullOrEmpty(fileNameWithSuffix)) {
            return result;
        }
        if(-1 == fileNameWithSuffix.indexOf(".")) {
            return FileType.OTHER.getKey();
        }
        String fileSuffix = fileNameWithSuffix.substring(fileNameWithSuffix.lastIndexOf(".") + 1, fileNameWithSuffix.length())
                .toUpperCase();
        if(pic.contains(fileSuffix)) {
            result = FileType.PIC.getKey();
        } else if (video.contains(fileSuffix)) {
            result = FileType.VIDEO.getKey();
        } else if (audio.contains(fileSuffix)) {
            result = FileType.AUDIO.getKey();
        } else {
            result = FileType.OTHER.getKey();
        }
        fileSuffix = null;
        return  result;
    }

}
