package com.example.tool;

/**
 * @time 2020/5/9 18:10
 * @desc
 */
public class jvm {

    //https://blog.csdn.net/kingzone_2008/article/details/9083327


    public static class JavaBean {

        private int age; //基本数据类型占用4个字节

        private long high; //基本数据类型占用8个字节

        private String name;//指针占用4个字节，指向实际的引用对象

    }


    public void init() {

        final JavaBean javaBean = new JavaBean();

        /*

        1、jvm堆中存放 实例对象，也就是具体new 出来的对象；
           jvm虚拟机栈中存放 局部变量(方法中的变量)、对象的引用；


        2、方法中的变量  javaBean 存放在虚拟机栈中；成员变量就是一个指针，
          这个指针，指向它实际的引用对象；

        3、对象的指针是int型的，16位占用4个字节；
           对象中基本数据类型直接占用对象的内存，存放在堆内存中





         */


    }


}
