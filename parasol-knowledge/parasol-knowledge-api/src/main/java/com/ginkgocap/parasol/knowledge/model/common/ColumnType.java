package com.ginkgocap.parasol.knowledge.model.common;

/**
 * Created by Admin on 2016/3/17.
 */
public enum ColumnType {

    /**
     * 系统不知道的知识类型
     */
    EUnknowKnowledge(-1),

    /**
     * 知识 -（资讯）
     */
    EKnowledgeNews(1),

    /* 知识javaBean （投融工具） */
    EKnowledgeInvestment(2),

    /* 知识 -（行业）*/
    EKnowledgeIndustry(3),

    /**
     * 知识javaBean （经典案例）
     */
    EKnowledgeCase(4),

    /**
     * 知识javaBean （新材料）
     */
    EKnowledgeNewMaterials(5),

    /**
     * 知识javaBean （资产管理）
     */
    EKnowledgeAsset(6),

    /**
     * 知识javaBean （宏观）
     */
    EKnowledgeMacro(7),

    /**
     * 知识javaBean （观点）
     */
    EKnowledgeOpinion(8),

    /**
     * 知识javaBean （法律法规）
     */
    EKnowledgeLaw(9),

    /**
     * 知识javaBean （文章）
     */
    EKnowledgeArticle(10),

    /*互联网*/
    EKnowledgeInternet(21),

    /** 营销 **/
    EKnowledgeSales(22),

    /** 财经 **/
    EKnowledgeFinance(23),

    /** 游戏 **/
    EKnowledgeGame(24),

    /** 教育 **/
    EKnowledgeEducation(25),

    /** 医疗 **/
    EKnowledgeMedical(26),

    /** 政治 **/
    EKnowledgePolitical(27),

    /** 时事 **/
    EKnowledgeAffair(28),

    /** 科技 **/
    EKnowledgeScience(29),

    /** 文化 **/
    EKnowledgeCulture(30),

    /** 文艺 **/
    EKnowledgeLiterature(31),

    /** 体育 **/
    EKnowledgeSports(32),

    /** 社会 **/
    EKnowledgeSociety(33),

    /** 影视 **/
    EKnowledgeMovie(34);

    private short type;
    private ColumnType(int t){
        this.type=(short)t;
    }

    public short getVal(){
        return type;
    }

    public static ColumnType valueOf(short type)
    {
        switch (type){
            case 1: return EKnowledgeNews;
            case 2: return EKnowledgeInvestment;
            case 3: return EKnowledgeIndustry;
            case 4: return EKnowledgeCase;
            case 5: return EKnowledgeNewMaterials;
            case 6: return EKnowledgeAsset;
            case 7: return EKnowledgeMacro;
            case 8: return EKnowledgeOpinion;
            case 9: return EKnowledgeLaw;
            case 10: return EKnowledgeArticle;
            case 21: return EKnowledgeInternet;
            case 22: return EKnowledgeSales;
            case 23: return EKnowledgeFinance;
            case 24: return EKnowledgeGame;
            case 25: return EKnowledgeEducation;
            case 26: return EKnowledgeMedical;
            case 27: return EKnowledgePolitical;
            case 28: return EKnowledgeAffair;
            case 29: return EKnowledgeScience;
            case 30: return EKnowledgeCulture;
            case 31: return EKnowledgeLiterature;
            case 32: return EKnowledgeSports;
            case 33: return EKnowledgeSociety;
            case 34: return EKnowledgeMovie;
            default: return EUnknowKnowledge;
        }
    }
}
