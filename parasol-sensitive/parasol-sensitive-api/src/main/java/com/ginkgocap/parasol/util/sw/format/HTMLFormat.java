package com.ginkgocap.parasol.util.sw.format;

import org.apache.commons.lang3.StringUtils;


/**
 * 
* <p>Title: HTMLFormat.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public class HTMLFormat extends AbstractFormat {
	
	private String prefix;
	
	private String suffix;
	
	public HTMLFormat(String prefix, String suffix){
		this.prefix = StringUtils.trimToEmpty(prefix);
		this.suffix = StringUtils.trimToEmpty(suffix);;
	}

	//加高亮
	public String format(String swword) {
		return prefix + swword + suffix;
	}
	
	//敏感词替换
	public String format(String swword, String replaceStr) {
		if(StringUtils.isNotEmpty(replaceStr)){
			StringBuilder r = new StringBuilder();
			for(int i = 0; i < swword.length(); i++){
				r.append(replaceStr);
			}
			return r.toString();
		}else{
			return "";
		}
		
	}

}
