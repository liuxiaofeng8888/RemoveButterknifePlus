package com.codedenerator;

import com.filechain.ClickMethod;
import com.intellij.psi.*;

import java.util.List;
import java.util.Map;

public class ActivityStrategy extends GenCodeStrategy {
    public ActivityStrategy(List<String> code, Map<ClickMethod, List<String>> clickMap) {
        super(code, clickMap);
    }

    @Override
    void genFindView(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement statement = findSetContentView(mClass);
        insertFindViewCode(mClass, mFactory, statement, code);
    }

    private PsiStatement findSetContentView(PsiClass mClass) {
        PsiStatement result = null;
        PsiMethod onCreate = mClass.findMethodsByName("onCreate", false)[0];
        for (PsiStatement statement : onCreate.getBody().getStatements()) {
            if (statement.getFirstChild() instanceof PsiMethodCallExpression) {
                PsiReferenceExpression methodExpression
                        = ((PsiMethodCallExpression) statement.getFirstChild())
                        .getMethodExpression();
                if (methodExpression.getText().equals("setContentView")) {
                    result = statement;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    void genOnClick(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement statement = findSetContentView(mClass);
        for (ClickMethod method : clickMap.keySet()) {
            StringBuilder methodString = getMethodInvokeString(method);
            for (String id : clickMap.get(method)) {
                String code = getOnClickCode(methodString, id);
                insertOnclickCode(mClass, mFactory, statement, code);
            }
        }
    }
}
