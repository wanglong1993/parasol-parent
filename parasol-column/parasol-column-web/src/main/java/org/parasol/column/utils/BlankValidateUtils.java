package org.parasol.column.utils;

import org.apache.commons.lang3.StringUtils;

public class BlankValidateUtils {
	
	public static boolean isBlank(String...str){
		if(str == null || str.length == 0)return false;
		for(String s : str){
			if(StringUtils.isBlank(s)){
				return true;
			}
		}
		return false;
	}
}
