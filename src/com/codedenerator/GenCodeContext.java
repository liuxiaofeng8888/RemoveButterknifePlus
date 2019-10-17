package com.codedenerator;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;

public class GenCodeContext {
    private GenCodeStrategy strategy;
    private PsiClass mClass;
    private PsiElementFactory mFactory;

    public GenCodeContext(PsiClass mClass, PsiElementFactory mFactory) {
        this.mClass = mClass;
        this.mFactory = mFactory;
    }

    public void setStrategy(GenCodeStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.genCode(mClass, mFactory);
    }
}
