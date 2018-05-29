package JVM.loadClass;

/**
 * @author lihe
 * @Title:
 * @Description:通过子类引用父类静态字段，不会初始化子类
 * @date 2018/5/20上午12:09
 */
class SuperClass {
    static {
        System.out.println("super class init!");
    }
    public static int value = 10;
}

class SubClass extends SuperClass {
    static {
        System.out.println("sub class init!");
    }
}
public class NotInitialization {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLOW);
    }
}

class ConstClass {
    static {
        System.out.println("Const class init!");
    }
    //常量会在编译阶段存入调用类的常量池中，本质上并没有引用到定义常量的类，因此不会触发定义常量类的初始化
    public static final String HELLOW = "hello, world";
}
