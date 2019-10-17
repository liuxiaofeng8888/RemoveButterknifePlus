package com.codedenerator;

import com.filechain.ClickMethod;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;

import java.util.List;
import java.util.Map;

public class FragmentStrategy extends GenCodeStrategy {
    public FragmentStrategy(List<String> code, Map<ClickMethod, List<String>> clickMap) {
        super(code, clickMap);
    }

    @Override
    void genFindView(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement statement = findOnCreateStatement(mClass, mFactory);
        insertFindViewCode(mClass, mFactory, statement, code);
    }

    private PsiStatement findOnCreateStatement(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement result = null;
        PsiMethod onCreateView = mClass.findMethodsByName("onCreateView", false)[0];
        for (PsiStatement statement : onCreateView.getBody().getStatements()) {
            String returnValue = statement.getText();
            if (returnValue.contains("R.layout") || returnValue.contains("LayoutInflater.from")) {
                result = statement;
                break;
            }
        }
        return result;
    }

    @Override
    void genOnClick(PsiClass mClass, PsiElementFactory mFactory) {
        PsiStatement statement = findOnCreateStatement(mClass, mFactory);
        for (ClickMethod method : clickMap.keySet()) {
            StringBuilder methodString = getMethodInvokeString(method);
            for (String id : clickMap.get(method)) {
                String code = getOnClickCode(methodString, id);
                insertOnclickCode(mClass, mFactory, statement, code);
            }
        }
    }
}
