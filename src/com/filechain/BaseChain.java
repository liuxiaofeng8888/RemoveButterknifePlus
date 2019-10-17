package com.filechain;

import java.util.List;
import java.util.Map;

public abstract class BaseChain {
    protected BaseChain next;
    protected String[] currentDoc;
    protected List<Integer> deleteLineNumbers;
    protected Map<String, String> nameAndIdMap;

    public void setNext(BaseChain next) {
        this.next = next;
    }

    final public void handle(String[] currentDoc,List deleteLineNumbers,Map nameAndIdMap){
        this.currentDoc = currentDoc;
        this.deleteLineNumbers = deleteLineNumbers;
        this.nameAndIdMap = nameAndIdMap;
        process();
        dispatcher();
    }

    abstract public void process();
    private void dispatcher(){
        if (next!=null){
            next.handle(currentDoc,deleteLineNumbers,nameAndIdMap);
        }
    }
}
