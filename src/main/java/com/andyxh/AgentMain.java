package com.andyxh;

import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2019/10/21
 */
public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst) {
//        ClassDefinition def = new ClassDefinition(TransClass.class, Transformer
//                .getBytesFromFile(Transformer.classNumberReturns2));
//        inst.redefineClasses(new ClassDefinition[] { def });
        // 从 agentArgs 获取外部参数
        System.out.println("开始热更新代码");
        String path = agentArgs;
        try {
            RandomAccessFile f = new RandomAccessFile(path, "r");
            final byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            final String clazzName = readClassName(bytes);

            // 加载

            for (Class clazz : inst.getAllLoadedClasses()) {
                if (clazz.getName().equals(clazzName)) {
                    ClassDefinition definition = new ClassDefinition(clazz, bytes);
                    inst.redefineClasses(definition);
                }
            }

        } catch (UnmodifiableClassException | IOException | ClassNotFoundException e) {
            System.out.println("热更新数据失败");
        }


    }

    /**
     * 使用 asm 读取类名
     *
     * @param bytes
     * @return
     */
    private static String readClassName(final byte[] bytes) {
        return new ClassReader(bytes).getClassName().replace("/", ".");
    }
}
