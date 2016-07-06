package com.ginkgocap.parasol.file.web.jetty.web.utils;

import java.text.DecimalFormat;



public class MakePrimaryKey {
	   private static final int MAX_GENERATE_COUNT = 99999;
	    private static int generateCount = 0;
	    private static DecimalFormat df = new DecimalFormat("00000");
	    private MakePrimaryKey() {
	    }

	    /**创建唯一字符串
	     * @return 18位唯一字符串  13位时间+5位流水号
	     */
	    public static synchronized String getPrimaryKey() {
	        generateCount++;
	        if (generateCount > MAX_GENERATE_COUNT)
	            generateCount = 1;
	        String uniqueNumber = Long.toString(System.currentTimeMillis()) + df.format(generateCount);
	        return uniqueNumber;
	    }
	    public static void main(String args[]){
	        for (int i = 0; i < 500; i++) {
	            String id = getPrimaryKey();
	            System.err.println(id);
	            System.err.println(id.length());
	        }
	    }
}
