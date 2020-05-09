package com.example.tool;

import android.os.Build;
import android.os.Trace;

/**
 * @time 2020/5/8 13:39
 * @desc 系统调用跟踪提供器
 */
public class SystraceUtil {


    public static void beginSection(String sectionName) {
     /*
      三种方式抓取systrace
      1、systrace.py工具
      2、Device Monitor（DDMS）
      3、自定义systrance


      参考：http://maoao530.github.io/2017/02/06/systrace/
      */

        if (Build.VERSION.SDK_INT > 17) {
            Trace.beginSection(sectionName);
        }
    }

    public static void endSection() {
     /*
      三种方式抓取systrace
      1、systrace.py工具
      2、Device Monitor（DDMS）
      3、自定义systrance


      参考：http://maoao530.github.io/2017/02/06/systrace/
      */

        if (Build.VERSION.SDK_INT > 17) {
            Trace.endSection();
        }
    }

}
