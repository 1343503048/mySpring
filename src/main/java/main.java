import ioc.context.FileSystemXmlApplicationContext;

public class main {
    public static void main(String...args) {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("/home/zq/mySpring/src/main/resources/spring-config.xml");
        System.out.println(context.getBean("stu"));
    }
}
