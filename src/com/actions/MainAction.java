package com.actions;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;

/**
 * @author liuxiaofeng
 * @description 主action
 * @since 2019-10-16
 */
public class MainAction extends BaseGenerateAction {
    private PsiClass mClass;
    private String[] s1;
    private Project project;
    private PsiFile file;
    private Editor editor;
    private PsiElementFactory mFactory;

    public MainAction() {
        super(null);
    }

    public MainAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {

        try {
            project = event.getData(PlatformDataKeys.PROJECT);
            editor = event.getData(PlatformDataKeys.EDITOR);
            file = PsiUtilBase.getPsiFileInEditor(editor, project);
            mFactory = JavaPsiFacade.getElementFactory(project);
            mClass = getTargetClass(editor,file);
            Document document = editor.getDocument();//以上都是从上下文中获取的辅助对象，具体可以查阅idea plugin文档
            new DeleteAction(project,file,document,mClass).execute();////执行删除操作
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
