package com.geek;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**https://u.geekbang.org/lesson/81?article=331955
 * 2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，
 * 执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 *
 * 执行结果：
 * D:\SoftWare\jdk1.8\bin\java.exe "-javaagent:D:\SoftWare\IDEA2019.1\IntelliJ IDEA 2019.1.4\lib\idea_rt.jar=64737:D:\SoftWare\IDEA2019.1\IntelliJ IDEA 2019.1.4\bin" -Dfile.encoding=UTF-8 -classpath D:\SoftWare\jdk1.8\jre\lib\charsets.jar;D:\SoftWare\jdk1.8\jre\lib\deploy.jar;D:\SoftWare\jdk1.8\jre\lib\ext\access-bridge-64.jar;D:\SoftWare\jdk1.8\jre\lib\ext\cldrdata.jar;D:\SoftWare\jdk1.8\jre\lib\ext\dnsns.jar;D:\SoftWare\jdk1.8\jre\lib\ext\jaccess.jar;D:\SoftWare\jdk1.8\jre\lib\ext\jfxrt.jar;D:\SoftWare\jdk1.8\jre\lib\ext\localedata.jar;D:\SoftWare\jdk1.8\jre\lib\ext\nashorn.jar;D:\SoftWare\jdk1.8\jre\lib\ext\sunec.jar;D:\SoftWare\jdk1.8\jre\lib\ext\sunjce_provider.jar;D:\SoftWare\jdk1.8\jre\lib\ext\sunmscapi.jar;D:\SoftWare\jdk1.8\jre\lib\ext\sunpkcs11.jar;D:\SoftWare\jdk1.8\jre\lib\ext\zipfs.jar;D:\SoftWare\jdk1.8\jre\lib\javaws.jar;D:\SoftWare\jdk1.8\jre\lib\jce.jar;D:\SoftWare\jdk1.8\jre\lib\jfr.jar;D:\SoftWare\jdk1.8\jre\lib\jfxswt.jar;D:\SoftWare\jdk1.8\jre\lib\jsse.jar;D:\SoftWare\jdk1.8\jre\lib\management-agent.jar;D:\SoftWare\jdk1.8\jre\lib\plugin.jar;D:\SoftWare\jdk1.8\jre\lib\resources.jar;D:\SoftWare\jdk1.8\jre\lib\rt.jar;D:\work_file\geek\target\classes com.geek.HelloClassLoader
 *
 * Hello, classLoader!
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object o = new HelloClassLoader().findClass("Hello").newInstance();
            Method hello = o.getClass().getMethod("hello");
            hello.invoke(o);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = "D:\\work_file\\geek\\src\\main\\resources\\Hello.xlass";
        File file = new File(path);

        int length = (int) file.length();
        byte[] xlassBytes = new byte[length];
        byte[] classByte = new byte[length];

        try {
            FileInputStream fs = new FileInputStream(file);
            int read = fs.read(xlassBytes);
            fs.close();

            for (int i = 0; i < read; i++) {
                byte xlassByte = (byte) (255 - xlassBytes[i]);
                classByte[i] = xlassByte;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, classByte, 0, classByte.length);
    }
}
