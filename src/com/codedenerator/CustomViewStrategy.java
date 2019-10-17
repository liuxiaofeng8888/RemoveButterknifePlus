package com.codedenerator;

import com.filechain.ClickMethod;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;

import java.util.List;
import java.util.Map;

public class CustomViewStrategy extends GenCodeStrategy {
    public CustomViewStrategy(List<String> code, Map<ClickMethod, List<String>> clickMap) {
        super(code, clickMap);
    }

    @Override
    void genFindView(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement statement = findInflateStatement(mClass);
        insertFindViewCode(mClass, mFactory, statement, code);
    }

    private PsiStatement findInflateStatement(PsiClass mClass) {
        PsiStatement result = null;
        PsiMethod[] methods = mClass.getAllMethods();
        for (PsiMethod method : methods) {
            for (PsiStatement statement : method.getBody().getStatements()) {
                String returnValue = statement.getText();
                if (returnValue.contains("R.layout") || returnValue.contains("LayoutInflater.from(context).inflate")) {
                    result = statement;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    void genOnClick(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement statement = findInflateStatement(mClass);
        for (ClickMethod method : clickMap.keySet()) {
            StringBuilder methodString = getMethodInvokeString(method);
            for (String id : clickMap.get(method)) {
                String code = getOnClickCode(methodString, id);
                insertOnclickCode(mClass, mFactory, statement, code);
            }
        }
    }
}
