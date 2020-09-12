package com.wtf.tool.util.generator;


import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenMain {
    public static void main(String[] args) {
        //读取文件
        File configFile = new File(GenMain.class.getResource("/generatorConfig.xml").getFile());

        List<String> warnings = new ArrayList();
        ConfigurationParser cp = new ConfigurationParser(warnings);

        //true:覆盖生成
        DefaultShellCallback callback = new DefaultShellCallback(true);

        try {
            Configuration config = cp.parseConfiguration(configFile);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.err.println("代码成功生成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
