package com.ginkgocap.parasol.metadata.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.common.utils.IOUtils;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.AdressRegionService;
import com.ginkgocap.parasol.metadata.type.CodeRegionType;

public class DataImport {

	/**
	 * 构造省数据， 数据来源从淘宝送货地址上收取
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<CodeRegion> getProvs() throws IOException, ParseException {
		List<CodeRegion> codeRegions = new ArrayList<CodeRegion>();
		String fileStr = "/pri.json";
		InputStream is = Runtime.class.getResourceAsStream(fileStr);
		String[] lines = IOUtils.readLines(is);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		Object json = JSON.parse(sb.toString());
		if (json != null) {
			JSONObject jsonObject = (JSONObject) json;
			for (Iterator<String> iterator = jsonObject.keys(); iterator.hasNext();) {
				String property = iterator.next();
				Object object = jsonObject.get(property); // get 'A-Z'
				JSONArray jsonArray = (JSONArray) object;
				for (int i = 0; i < jsonArray.length(); i++) { // get pri
					JSONArray pri = jsonArray.getArray(i);
					System.out.print(pri.getString(0) + " " + pri.getArray(1).getString(0));
					CodeRegion codeRegion = new CodeRegion();
					codeRegion.setCname(pri.getArray(1).getString(0));
					codeRegion.setTbId(pri.getString(0));
					codeRegion.setType(CodeRegionType.TYPE_CHINAINLAND.getValue());
					codeRegions.add(codeRegion);
				}

			}
		}
		is.close();
		return codeRegions;
	}

	/**
	 * 构造城市数据， 数据来源从淘宝送货地址上收取
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<CodeRegion> getTowns() throws IOException, ParseException {
		List<CodeRegion> codeRegions = new ArrayList<CodeRegion>();
		String fileStr = "/town.json";
		InputStream is = Runtime.class.getResourceAsStream(fileStr);
		String[] lines = IOUtils.readLines(is);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		Object json = JSON.parse(sb.toString());
		if (json != null) {
			JSONArray jsonArray = (JSONArray) json;
			for (int i = 0; i < jsonArray.length(); i++) { // get town
				JSONArray pri = jsonArray.getArray(i);
				System.out.print(pri.getString(0) + " " + pri.getArray(1).getString(0));
				CodeRegion codeRegion = new CodeRegion();
				codeRegion.setCname(pri.getArray(1).getString(0));
				codeRegion.setTbId(pri.getString(0));
				codeRegion.setTbParentId(pri.getString(2));
				codeRegion.setType(CodeRegionType.TYPE_CHINAINLAND_CITY.getValue());
				codeRegions.add(codeRegion);
			}
		}
		is.close();
		return codeRegions;
	}
	/**
	 * 构造港澳台数据， 数据来源从淘宝送货地址上收取
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<CodeRegion> getHkAomeng() throws IOException, ParseException {
		List<CodeRegion> codeRegions = new ArrayList<CodeRegion>();
		String fileStr = "/xianggang_aomeng.json";
		InputStream is = Runtime.class.getResourceAsStream(fileStr);
		String[] lines = IOUtils.readLines(is);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		Object json = JSON.parse(sb.toString());
		if (json != null) {
			JSONArray jsonArray = (JSONArray) json;
			for (int i = 0; i < jsonArray.length(); i++) { // get town
				JSONArray pri = jsonArray.getArray(i);
				System.out.print(pri.getString(0) + " " + pri.getArray(1).getString(0));
				CodeRegion codeRegion = new CodeRegion();
				codeRegion.setCname(pri.getArray(1).getString(0));
				codeRegion.setTbId(pri.getString(0));
				codeRegion.setTbParentId(pri.getString(2));
				codeRegion.setType(CodeRegionType.TYPE_GANGAOTAI.getValue());
				codeRegions.add(codeRegion);
			}
		}
		is.close();
		return codeRegions;
	}
	/**
	 * 构造台湾数据， 数据来源从淘宝送货地址上收取
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<CodeRegion> getTaiwan() throws IOException, ParseException {
		List<CodeRegion> codeRegions = new ArrayList<CodeRegion>();
		String fileStr = "/taiwan.json";
		InputStream is = Runtime.class.getResourceAsStream(fileStr);
		String[] lines = IOUtils.readLines(is);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		Object json = JSON.parse(sb.toString());
		if (json != null) {
			JSONArray jsonArray = (JSONArray) json;
			for (int i = 0; i < jsonArray.length(); i++) { // get town
				JSONArray pri = jsonArray.getArray(i);
				System.out.print(pri.getString(0) + " " + pri.getArray(1).getString(0));
				CodeRegion codeRegion = new CodeRegion();
				codeRegion.setCname(pri.getArray(1).getString(0));
				codeRegion.setTbId(pri.getString(0));
				codeRegion.setTbParentId(pri.getString(2));
				codeRegion.setType(CodeRegionType.TYPE_CHINAINLAND_CITY.getValue());
				codeRegions.add(codeRegion);
			}
		}
		is.close();
		return codeRegions;
	}

	/**
	 * 构造马来西亚数据， 数据来源从淘宝送货地址上收取
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<CodeRegion> getManlaixiya() throws IOException, ParseException {
		List<CodeRegion> codeRegions = new ArrayList<CodeRegion>();
		String fileStr = "/manlaixiyan.json";
		InputStream is = Runtime.class.getResourceAsStream(fileStr);
		String[] lines = IOUtils.readLines(is);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		Object json = JSON.parse(sb.toString());
		if (json != null) {
			JSONArray jsonArray = (JSONArray) json;
			for (int i = 0; i < jsonArray.length(); i++) { // get town
				JSONArray pri = jsonArray.getArray(i);
				System.out.print(pri.getString(0) + " " + pri.getArray(1).getString(0));
				CodeRegion codeRegion = new CodeRegion();
				codeRegion.setCname(pri.getArray(1).getString(0));
				codeRegion.setEname(pri.getArray(1).getString(2));
				codeRegion.setTbId(pri.getString(0));
				codeRegion.setTbParentId(pri.getString(2));
				codeRegion.setType(CodeRegionType.TYPE_MALAYSIA.getValue());
				codeRegions.add(codeRegion);
			}
		}
		is.close();
		return codeRegions;
	}

	/**
	 * 构造海外数据， 数据来源从淘宝送货地址上收取
	 * 
	 * @return
	 * 
	 * @throws IOException
	 * 
	 * @throws ParseException
	 */
	public static List<CodeRegion> getHaiWai() throws IOException, ParseException {
		List<CodeRegion> codeRegions = new ArrayList<CodeRegion>();
		String fileStr = "/haiwai.json";
		InputStream is = Runtime.class.getResourceAsStream(fileStr);
		String[] lines = IOUtils.readLines(is);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			sb.append(lines[i]);
		}
		JSONObject json = (JSONObject) JSON.parse(sb.toString());
		if (json != null) {
			for (Iterator iterator = json.keys(); iterator.hasNext();) {
				String number = (String) iterator.next();
				JSONArray haiwai = json.getArray(number);
				System.out.println(haiwai.getString(0) + " " + haiwai.getString(2));
				CodeRegion codeRegion = new CodeRegion();
				codeRegion.setCname(haiwai.getString(0));
				codeRegion.setEname(haiwai.getString(2));
				codeRegion.setTbId(number);
				// codeRegion.setTbParentId();
				codeRegion.setType(CodeRegionType.TYPE_FOREIGNCOUNTRY.getValue());
				codeRegions.add(codeRegion);
			}
		}
		is.close();
		return codeRegions;
	}

	public static void main(String[] args) throws IOException, ParseException {
		List<CodeRegion> codeRegions = getHkAomeng();
		for (CodeRegion codeRegion : codeRegions) {
			System.out.println(codeRegion);
		}
	}

}
