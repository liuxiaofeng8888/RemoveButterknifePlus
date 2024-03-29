package com.filechain;

public class DetectAPIUseChain extends BaseChain{
    public static final String BUTTER_KNIFE = "ButterKnife";

    @Override
    public void process() {
        for (int i = 0; i < currentDoc.length; i++) {
            if (currentDoc[i] != null&&currentDoc[i].trim().indexOf(BUTTER_KNIFE) == 0) {
                deleteLineNumbers.add(i);
            }
        }
    }
}
