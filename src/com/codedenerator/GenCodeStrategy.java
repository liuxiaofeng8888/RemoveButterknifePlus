package com.codedenerator;

import com.filechain.ClickMethod;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiStatement;

import java.util.List;
import java.util.Map;

/**
 * @author liuxiaofeng
 * @description 生成代码的策略
 * @since 2019-10-17
 */
public abstract class GenCodeStrategy {
    protected List<String> code;
    protected Map<ClickMethod,List<String>> clickMap;

    public GenCodeStrategy(List<String> code, Map<ClickMethod,List<String>> clickMap){
        this.code = code;
        this.clickMap = clickMap;
    }

    protected void genCode(PsiClass mClass, PsiElementFactory mFactory){
        try {
            genOnClick(mClass, mFactory);
            genFindView(mClass, mFactory);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void insertFindViewCode(PsiClass mClass, PsiElementFactory mFactory, PsiStatement statement, List<String> code) {
        for (int i = code.size() - 1; i >= 0; i--) {
            StringBuffer buffer = new StringBuffer(code.get(i));
            try {
                statement.addAfter(mFactory.createStatementFromText(
                        buffer.toString(), mClass), statement);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    protected void insertOnclickCode(PsiClass mClass, PsiElementFactory mFactory, PsiStatement statement, String code) {
        try {
            statement.addAfter(mFactory.createStatementFromText(
                    code, mClass), statement);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    protected String getOnClickCode(StringBuilder methodString, String id) {
        return "findViewById("+id+").setOnClickListener(new View.OnClickListener() {\n" +
                " @Override\n" +
                " public void onClick(View v){\n"+
                methodString.toString()+
                "}"+
                "});";
    }

    protected StringBuilder getMethodInvokeString(ClickMethod method) {
        StringBuilder methodString = new StringBuilder();
        if (method.isHaveArg()){
            methodString.append(method.getName()+"(("+method.getArgType()+")"+"v);");
        }else{
            methodString.append(method.getName()+"();");
        }
        return methodString;
    }

    abstract void genFindView(PsiClass mClass, PsiElementFactory mFactory);
    abstract void genOnClick(PsiClass mClass, PsiElementFactory mFactory);
}
