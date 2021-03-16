package com.wtf.tool.bytecode;

import com.wtf.tool.bytecode.annotation.RpcGatewayClazz;
import com.wtf.tool.bytecode.annotation.RpcGatewayMethod;
import com.wtf.tool.bytecode.interceptor.UserRepositoryInterceptor;
import com.wtf.tool.bytecode.model.Repository;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;

/**
 * @author strugglesnail
 * @date 2021/2/2
 * @desc
 */
public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        // 生成含有注解的泛型实现字类
        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
                .subclass(TypeDescription.Generic.Builder.parameterizedType(Repository.class, String.class).build()) // 创建复杂类型的泛型注解
                // 添加类信息包括地址
                .name(Repository.class.getPackage().getName().concat(".").concat("UserRepository"))
                // 匹配处理的方法
                .method(ElementMatchers.named("queryData"))
                // 交给委托函数
                .intercept(MethodDelegation.to(UserRepositoryInterceptor.class))
                // 添加指定方法注解
                .annotateMethod(AnnotationDescription.Builder.ofType(RpcGatewayMethod.class).define("methodName", "queryData").define("methodDesc", "查询数据").build())
                // 添加指定类注解
                .annotateType(AnnotationDescription.Builder.ofType(RpcGatewayClazz.class).define("alias", "dataApi").define("clazzDesc", "查询数据信息").define("timeOut", 350L).build())
                .make();

        // 输出类信息到目标文件夹下
        System.out.println(new File(Test.class.getResource("/").getPath()));
//        dynamicType.saveIn(new File(Test.class.getResource("/").getPath()));


        // 从目标文件夹下加载类信息
        Class<Repository<String>> repositoryClass = (Class<Repository<String>>) Class.forName("com.wtf.tool.bytecode.model.UserRepository");
        // 获取类注解
        RpcGatewayClazz rpcGatewayClazz = repositoryClass.getAnnotation(RpcGatewayClazz.class);
        System.out.println("RpcGatewayClazz.clazzDesc：" + rpcGatewayClazz.clazzDesc());
        System.out.println("RpcGatewayClazz.alias：" + rpcGatewayClazz.alias());
        System.out.println("RpcGatewayClazz.timeOut：" + rpcGatewayClazz.timeOut());

        // 获取方法注解
        RpcGatewayMethod rpcGatewayMethod = repositoryClass.getMethod("queryData", int.class).getAnnotation(RpcGatewayMethod.class);
        System.out.println("RpcGatewayMethod.methodName：" + rpcGatewayMethod.methodName());
        System.out.println("RpcGatewayMethod.methodDesc：" + rpcGatewayMethod.methodDesc());

        Repository<String> repository = repositoryClass.newInstance();
        System.out.println(repository.queryData(1));

    }
}
