package demo.login;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InterfaceDetect {
    public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    static class MyClassLoader extends ClassLoader{
        @Override
        protected Class<?> findClass(String className)
                throws ClassNotFoundException {
            byte[] cLassBytes = null;
            //Java 7有下列ＡＰＩ
            Path path;
            try {
                path = Paths.get(new URI("file:///E:/ideaProject/MS-examples/spring-cloud-event-sourcing-example/user-service/target/classes/demo/login/LoginController.class"));
                cLassBytes = Files.readAllBytes(path);
                System.out.println(cLassBytes.length);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            Class cLass = defineClass("demo.login.LoginController",cLassBytes, 0, cLassBytes.length);
            return cLass;
        }
    }

    public static void main(String[] args) {
        ClassLoader classLoader = new MyClassLoader();
        try {
            Class o = Class.forName("demo.login.LoginController",true,classLoader);
            Method[] methods = o.getDeclaredMethods();
            for(Method method : methods){
                Annotation[] annotations = method.getDeclaredAnnotations();
                for(Annotation annotation: annotations){
                    System.out.println(annotation.toString());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
