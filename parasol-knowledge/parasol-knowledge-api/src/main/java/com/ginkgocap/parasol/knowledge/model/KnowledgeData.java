package com.ginkgocap.parasol.knowledge.model;

import com.ginkgocap.ywxt.asso.model.Asso;

/**
 * Created by Admin on 2016/3/24.
 */
public class KnowledgeData {
    private static final long serialVersionUID = -424912985959502809L;

    /**旧知识信息*/
    private Knowledge knowledge;

    public KnowledgeBase getKnowledgeBase() {
        return knowledgeBase;
    }

    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    /**知识信息*/
    private KnowledgeBase knowledgeBase;

    /**知识来源*/
    private KnowledgeReference reference;

    /**栏目*/
    private ColumnCollection column;

    /**关联*/
    private Asso asso;

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public KnowledgeReference getReference() {
        return reference;
    }

    public void setReference(KnowledgeReference reference) {
        this.reference = reference;
    }

    public ColumnCollection getColumn() {
        return column;
    }

    public void setColumn(ColumnCollection column) {
        this.column = column;
    }

    public Asso getAsso() {
        return asso;
    }

    public void setAsso(Asso asso) {
        this.asso = asso;
    }
}
