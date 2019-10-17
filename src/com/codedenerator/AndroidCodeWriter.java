package com.codedenerator;

import com.filechain.ClickMethod;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;

import java.util.List;
import java.util.Map;

public class AndroidCodeWriter extends WriteCommandAction.Simple {
    private PsiClass mClass;
    private PsiElementFactory mFactory;
    private List<String> code;
    private Map<ClickMethod, List<String>> clickMap;
    private Project mProject;

    public AndroidCodeWriter(Project project, PsiFile file) {
        super(project, file);
        mProject = project;
    }

    public void setData(List<String> code, Map<ClickMethod, List<String>> clickMap) {
        this.code = code;
        this.clickMap = clickMap;
    }

    public void setEnvData(PsiClass psiClass, PsiElementFactory mFactory) {
        mClass = psiClass;
        this.mFactory = mFactory;
    }

    @Override
    protected void run() {
        GenCodeContext codeContext = new GenCodeContext(mClass, mFactory);
        String type = mClass.getSuperClassType().toString();
        if (type.contains("Activity")){
            codeContext.setStrategy(new ActivityStrategy(code,clickMap));
        }else if (type.contains("Fragment")) {
            codeContext.setStrategy(new FragmentStrategy(code,clickMap));
        }else if (type.contains("ViewHolder")||type.contains("Adapter<ViewHolder>")) {
            codeContext.setStrategy(new AdapterStrategy(code,clickMap));
        }else {
            codeContext.setStrategy(new CustomViewStrategy(code,clickMap));
        }
        codeContext.executeStrategy();
    }
}
