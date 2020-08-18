package com.wtf.tool.util;

import com.wtf.tool.util.tree.RootTreeNode;

import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Test<T> {

    public static void main(String[] args) {

        // 字符串
        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("This is one");
        consumer.accept("This is two");

        //  整数
        Consumer<Integer> intConsumer = str -> System.out.println(str);
        intConsumer.accept(1);
        intConsumer.accept(2);

        // 对象
        RootTreeNode user = new RootTreeNode(1L, 2L, "1");
        RootTreeNode userTwo = new RootTreeNode(2L, 3L, "2");

        Consumer<RootTreeNode> userConsumer = userCon -> System.out.println(userCon);
        userConsumer.accept(user);
        userConsumer.accept(userTwo);


    }


}
