package com.codedenerator;

import com.filechain.ClickMethod;
import com.intellij.psi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdapterStrategy extends GenCodeStrategy {
    private PsiMethod method;
    private String viewName;

    public AdapterStrategy(List<String> code, Map<ClickMethod, List<String>> clickMap) {
        super(code, clickMap);
    }

    @Override
    void genFindView(PsiClass mClass, PsiElementFactory mFactory) {

    }

    private PsiStatement findSuperStatement(PsiMethod method,String viewName){
        PsiStatement result = null;
        for (PsiStatement statement : method.getBody().getStatements()) {
            String returnValue = statement.getText();
            if (returnValue.contains("super(" + viewName + ")")) {
                result = statement;
                break;
            }
        }
        return result;
    }
    private PsiMethod findMethod(PsiClass mClass){
        PsiMethod[] methods = mClass.getAllMethods();
        PsiMethod result = null;
        for (PsiMethod method:methods) {
            for (PsiParameter parameter : method.getParameterList().getParameters()) {
                String type = parameter.getType().toString();
                if (type != null && type.equals("PsiType:View")) {
                    result =  method;
                    break;
                }
            }
        }
        return result;
    }

    private String findViewName(PsiMethod method){
        String result = "";
        for (PsiParameter parameter:method.getParameterList().getParameters()){
            if (parameter.getType().toString().equals("PsiType:View")){
                result = parameter.getName();
            }
        }
        return  result;
    }

    private List<String> addViewName(String viewName) {
        List<String> codes = new ArrayList<>();
        for (String s : code) {
            StringBuilder stringBuilder = new StringBuilder(s);
            stringBuilder.insert(s.indexOf("find"),viewName+".");
            codes.add(stringBuilder.toString());
        }
        return codes;
    }

    @Override
    void genOnClick(PsiClass mClass, PsiElementFactory mFactory) {
        method = findMethod(mClass);
        viewName = findViewName(method);
        PsiStatement statement = findSuperStatement(method,viewName);
        for (ClickMethod method:clickMap.keySet()){
            StringBuilder methodString = getMethodInvokeString(method);
            for(String id:clickMap.get(method)){
                String code = getOnClickCode(methodString, id);
                insertOnclickCode(mClass, mFactory, statement, code);
            }
        }
    }
}
