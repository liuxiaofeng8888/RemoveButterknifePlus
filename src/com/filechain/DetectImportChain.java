package com.filechain;

/**
 * @author liuxiaofeng
 * @description 检测import导包语句
 * @since 2019-10-17
 */
public class DetectImportChain extends BaseChain{
    public static final String IMPORT_BUTTERKNIFE_BIND = "import butterknife.Bind;";
    public static final String IMPORT_BUTTERKNIFE_INJECT_VIEW = "import butterknife.InjectView;";
    public static final String IMPORT_BUTTERKNIFE_BUTTER_KNIFE = "import butterknife.ButterKnife;";
    public static final String IMPORT_BUTTERKNIFE_BIND_VIEW = "import butterknife.BindView;";
    public static final String IMPORT_BUTTERKNIFE_BIND_ONCLICK = "import butterknife.OnClick;";

    @Override
    public void process() {
        for (int i = 0;i < currentDoc.length;i++){
            if (currentDoc[i].equals(IMPORT_BUTTERKNIFE_BIND)||currentDoc[i].equals(IMPORT_BUTTERKNIFE_BIND_VIEW)||
                    currentDoc[i].equals(IMPORT_BUTTERKNIFE_BUTTER_KNIFE)||currentDoc[i].equals(IMPORT_BUTTERKNIFE_INJECT_VIEW)){
                deleteLineNumbers.add(i);
            }
        }
    }
}
