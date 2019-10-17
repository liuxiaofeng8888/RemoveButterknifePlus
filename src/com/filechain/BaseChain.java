package com.filechain;

import java.util.List;
import java.util.Map;

/**
 * @author liuxiaofeng
 * @description 代码寻找/处理部分 责任链模式
 * @since 2019-10-17
 */
public abstract class BaseChain {
    protected BaseChain next;
    protected String[] currentDoc;
    protected List<Integer> deleteLineNumbers;
    protected Map<String, String> nameAndIdMap;

    /**
     * 设置下一步
     * @param next 下一个操作
     */
    public void setNext(BaseChain next) {
        this.next = next;
    }

    /**
     * 内部处理逻辑，无法被子类修改
     */
    final public void handle(String[] currentDoc,List deleteLineNumbers,Map nameAndIdMap){
        this.currentDoc = currentDoc;
        this.deleteLineNumbers = deleteLineNumbers;
        this.nameAndIdMap = nameAndIdMap;
        process();
        dispatcher();
    }

    /**
     * 子类需要实现的处理部分
     */
    abstract public void process();

    /**
     * 转发逻辑
     */
    private void dispatcher(){
        if (next!=null){
            next.handle(currentDoc,deleteLineNumbers,nameAndIdMap);
        }
    }
}
