package com.ginkgocap.parasol.sensitive.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.ginkgocap.parasol.sensitive.exception.SensitiveWordServiceException;
import com.ginkgocap.parasol.sensitive.model.SensitiveWord;
import com.ginkgocap.parasol.sensitive.service.SensitiveWordService;
import com.ginkgocap.parasol.util.sw.format.HTMLFormat;

public class SensitiveWordServiceTest extends TestBase{
	
	@Resource
	private SensitiveWordService sensitiveWordService;
	
	@Test
	public void TestInsertSensitiveWord() throws SensitiveWordServiceException {
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
	public void TestGetSensitiveWordById() throws SensitiveWordServiceException{
		SensitiveWord word = sensitiveWordService.findOne(3914808483381253l);
		System.out.println("word==="+word.getWord());
	}
	
	@Test
	public void TestUpdateSensitiveWord() throws SensitiveWordServiceException {
		SensitiveWord word = sensitiveWordService.findOne(3914808483381253l);
		word.setWord("江泽民王八蛋");
		sensitiveWordService.saveOrUpdate(word);
		System.out.println("word==="+word.getWord());
	}
	
	@Test
	public void TestCheckSensitiveWordByWord() throws SensitiveWordServiceException {
		String word = "江泽民王八蛋";
		boolean flag = sensitiveWordService.checkSensitiveWordExist(word);
		System.out.println("flag ===" + flag);
	}
	
	@Test
	public void TestBatchInsertSensitiveWords() throws SensitiveWordServiceException{
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
	
	@Test
	public void TestSensitiveWord() throws SensitiveWordServiceException {
		String text = "线上发布的时候，如果失败，不能在5分钟之内解决。立即选择回滚。发布之前需要执行的一切脚本和Sql都需要由开发工程师在Wiki上存放。运维只需要按操作步骤执行，发布的时候工程师必须在场,打倒李克强";
		List<String> result = sensitiveWordService.sensitiveWord(text);
		System.out.println("result===="+result);
	}
	
	@Test
	public void TestHighlight() throws SensitiveWordServiceException {
		String text = "线上发布的时候，如果失败，不能在5分钟之内解决。立即选择回滚。发布之前需要执行的一切脚本和Sql都需要由开发工程师在Wiki上存放。运维只需要按操作步骤执行，发布的时候工程师必须在场,打倒李克强";
		String result = sensitiveWordService.highlight(text,1,new HTMLFormat("<font color='red'>", "</font>"));
		System.out.println("result===="+result);
	}
}
