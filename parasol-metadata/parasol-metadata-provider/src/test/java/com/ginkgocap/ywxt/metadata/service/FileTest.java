package com.ginkgocap.ywxt.metadata.service;

import java.util.List; 

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;

import com.ginkgocap.ywxt.metadata.base.TestBase;
import com.ginkgocap.ywxt.metadata.model.Dic;
import com.ginkgocap.ywxt.metadata.model.RCodeDock;
import com.ginkgocap.ywxt.metadata.model.Suggestion;
import com.ginkgocap.ywxt.metadata.service.code.RCodeDockService;
import com.ginkgocap.ywxt.metadata.service.suggestion.SuggestionService;

public class FileTest extends TestBase {
    @Resource
    SuggestionService suggestionService;

    @Test
    public void allTest() {
        List<Dic>l = suggestionService.selectDics();
        Suggestion suggestion = new Suggestion();
        suggestion.setUid(1);
        suggestion.setUser_name("aaa");
        suggestion.setFeedbackType("gggg");
        suggestion.setProblemContent("pp");
        suggestion.setBrowerInfo("brower");
        suggestion.setContact("cc");
        Suggestion s= suggestionService.add(suggestion);
        System.out.println(s);

    }
}
