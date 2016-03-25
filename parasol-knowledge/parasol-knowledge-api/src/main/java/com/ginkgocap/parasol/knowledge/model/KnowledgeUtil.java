package com.ginkgocap.parasol.knowledge.model;

import java.util.Date;

/**
 * Created by Admin on 2016/3/24.
 */
public final class KnowledgeUtil {

    public static Class<? extends Knowledge> getClassByColumnId(short columnId)
    {
        switch (columnId){
            case 1: return KnowledgeNews.class;
            case 2: return KnowledgeInvestment.class;
            case 3: return KnowledgeIndustry.class;
            case 4: return KnowledgeCase.class;
            case 5: return KnowledgeNewMaterials.class;
            case 6: return KnowledgeAsset.class;
            case 7: return KnowledgeMacro.class;
            case 8: return KnowledgeOpinion.class;
            case 9: return KnowledgeLaw.class;
            case 10: return KnowledgeArticle.class;
            /* For new requirement
            case 21: return KnowledgeInternet.class;
            case 22: return EKnowledgeSales.class;
            case 23: return EKnowledgeFinance.class;
            case 24: return EKnowledgeGame.class;
            case 25: return EKnowledgeEducation.class;
            case 26: return EKnowledgeMedical.class;
            case 27: return EKnowledgePolitical.class;
            case 28: return EKnowledgeAffair.class;
            case 29: return EKnowledgeScience.class;
            case 30: return EKnowledgeCulture.class;
            case 31: return EKnowledgeLiterature.class;
            case 32: return KnowledgeSports.class;
            case 33: return KnowledgeSociety.class;
            case 34: return KnowledgeMovie.class;
            */
            default: return null;
        }
    }

    public static KnowledgeBase convertOldKnowledge(Knowledge knowledge)
    {
        if (knowledge == null) {
            throw new IllegalArgumentException("Knowledge is null");
        }
        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.setId(knowledge.getId());
        knowledgeBase.setColumnId(knowledge.getColumnId());
        knowledgeBase.setTitle(knowledge.getTitle());
        knowledgeBase.setContentDesc(knowledge.getDesc());
        knowledgeBase.setContent(knowledge.getContent());
        knowledgeBase.setCreateUserId(knowledge.getCId());
        knowledgeBase.setCreateUserName(knowledge.getCname());
        knowledgeBase.setCreateDate(new Date(knowledge.getCreatetime()).getTime());
        //TODO: check if picture Id is not a number
        knowledgeBase.setPictureId(Long.parseLong(knowledge.getPic()));
        knowledgeBase.setModifyDate(new Date(knowledge.getModifytime()).getTime());
        //knowledgeBase.setType();
        knowledgeBase.setReportStatus((short)knowledge.getReport_status());
        knowledgeBase.setStatus((short)knowledge.getStatus());
        knowledgeBase.setAuditStatus((short)knowledge.getStatus());

        return knowledgeBase;
    }
}
