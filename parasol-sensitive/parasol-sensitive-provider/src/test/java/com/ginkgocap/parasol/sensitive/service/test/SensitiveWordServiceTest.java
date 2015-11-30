package com.ginkgocap.parasol.sensitive.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.sensitive.model.SensitiveWord;
import com.ginkgocap.parasol.sensitive.service.SensitiveWordService;

public class SensitiveWordServiceTest extends TestBase{
	
	@Resource
	private SensitiveWordService sensitiveWordService;
	
	@Test
	public void TestInsertSensitiveWord() {
		SensitiveWord word = new SensitiveWord();
		word.setAppid("gintong");
		word.setCreaterId(1l);
		word.setLevel(2);
		word.setType(5);
		word.setWord("毛泽东");
		word = sensitiveWordService.saveOrUpdate(word);
		System.out.println("word==="+word.toString());
	}
	
	@Test
	public void TestGetSensitiveWordById(){
		SensitiveWord word = sensitiveWordService.findOne(3914808483381253l);
		System.out.println("word==="+word.getWord());
	}
	
	@Test
	public void TestUpdateSensitiveWord() {
		SensitiveWord word = sensitiveWordService.findOne(3914808483381253l);
		word.setWord("江泽民王八蛋");
		sensitiveWordService.saveOrUpdate(word);
		System.out.println("word==="+word.getWord());
	}
	
	@Test
	public void TestCheckSensitiveWordByWord() {
		String word = "江泽民王八蛋";
		boolean flag = sensitiveWordService.checkSensitiveWordExist(word);
		System.out.println("flag ===" + flag);
	}
	
	@Test
	public void TestBatchInsertSensitiveWords(){
		String[] words = new String[]{"法轮功","打到中国共产党","打倒李克强","打倒毛泽东","打倒温家宝","打倒中国共产党政权","推翻共产党","推翻共党"};
		List<SensitiveWord> sensitiveWords = new ArrayList<SensitiveWord>();
		for (String word : words) {
			SensitiveWord sw = new SensitiveWord();
			sw.setAppid("gintong");
			sw.setCreaterId(1l);
			sw.setLevel(4);
			sw.setType(4);
			sw.setWord(word);
			sensitiveWords.add(sw);
		}
		boolean flag = sensitiveWordService.batchInsertSensitiveWords(sensitiveWords);
		System.out.println("批量保存敏感词"+flag);
	}
}
