package com.ginkgocap.parasol.sensitive.model;

import java.io.Serializable;

/**
 * 
* <p>Title: Word.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public class Word implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6564575056461236652L;

	private int wLength;
	
	private String word;

	public int getwLength() {
		return wLength;
	}

	public void setwLength(int wLength) {
		this.wLength = wLength;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	
	
}
