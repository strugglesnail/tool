package com.wtf.tool.util.asm;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassReader;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;
import com.wtf.tool.util.tree.RootTreeNode;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;

public class AddField {

    private Class clazz = null;
    private ClassReader cr = null;
    private ClassWriter cw = null;
    private ClassAdapter ca = null;
    private File classFile = null;

    private final static String CLASS_FILE_SUFFIX = ".class";

    public AddField(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * 添加一个 public 的类成员
     * @param fieldName     类成员名
     * @param fieldDesc     类成员类型描述
     */
    public void addPublicField(String fieldName, String fieldDesc) {
        if(cr == null) {
            try {
                cr = new ClassReader(clazz.getCanonicalName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        }
        if(ca == null) {
            ca = new AddFieldAdapter(cw, Opcodes.ACC_PUBLIC, fieldName, fieldDesc);
        } else {
            ca = new AddFieldAdapter(ca, Opcodes.ACC_PUBLIC, fieldName, fieldDesc);
        }
    }

    /**
     * 将字节码写入类的 .class 文件
     *
     */
    public void writeByteCode() {
        cr.accept(ca, ClassReader.SKIP_DEBUG);
        byte[] bys = cw.toByteArray();
        OutputStream os = null;
        try {
            os = new FileOutputStream(getFile());
            os.write(bys);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得类文件的 File 对象
     * @return
     */
    private File getFile() {
        if(classFile == null) {
            StringBuffer sb = new StringBuffer();
            sb.append(clazz.getResource("/"))
                    .append(clazz.getCanonicalName().replace(".", File.separator))
                    .append(CLASS_FILE_SUFFIX);
            System.out.println(sb.substring(6));
            classFile = new File(sb.substring(6));
        }
        return classFile;
    }

    public static void main(String[] args) {

        // 为 Student 添加字段
        AddField add = new AddField(RootTreeNode.class);

        // 添加一个名为 address，类型为 java.lang.String 的 public 字段
        add.addPublicField("address", "Ljava/lang/String;");

        // 重新生成 .class 文件
        add.writeByteCode();

        RootTreeNode treeNode = new RootTreeNode();
        // treeNode.address = "232"; // 编译报错，实际已经添加到class文件中
        Field[] fields = RootTreeNode.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getName());
        }
        // System.out.println(treeNode.address);
    }
}