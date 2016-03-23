package com.ginkgocap.ywxt.knowledge.testData;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ginkgocap.parasol.knowledge.model.Knowledge;
import com.ginkgocap.parasol.knowledge.model.KnowledgeBase;

public class TestData {

    public static String jsonPath = null;
    private static ObjectMapper objectMapper = null;
    static {
        objectMapper = new ObjectMapper();
    }

	public static KnowledgeBase getKnowledgeBase() {
		KnowledgeBase knowledgeBase = new KnowledgeBase();

		return knowledgeBase;
	}

    public static void main(String[] args) throws Exception {
        Knowledge knowledge = DataUtil.getKnowledgeNews();
        String jsonData = objectMapper.writeValueAsString(knowledge);
        System.err.println( jsonData );
    }
}