package org.parasol.column.api.model;

public enum ColumnType {
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
	EKnowledgeArticle(10);

	private short type;
	private ColumnType(int t){
		this.type=(short)t;
	}
	
	public short getVal(){
		return type;
	}

}
