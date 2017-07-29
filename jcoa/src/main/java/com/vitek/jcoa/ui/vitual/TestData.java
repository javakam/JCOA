package com.vitek.jcoa.ui.vitual;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kam on 2016/6/16.
 */
public class TestData {
    private static String[] strings = new String[]{"Bei jing", "Shang hai", "Chang sha", "Chang chun", "Nan jing", "Dong jing", "Ji nan",
            "Qing dao", "Xiang tan", "Zhu zhou", "Heng yang", "Bei jing", "Shang hai", "Chang sha", "Chang chun",
            "Nan jing", "Dong jing", "Ji nan", "Qing dao", "Xiang tan", "Zhu zhou", "Heng yang", "Bei jing",
            "Shang hai", "Chang sha", "Chang chun", "Nan jing", "Dong jing", "Ji nan", "Qing dao", "Xiang tan",
            "Zhu zhou", "Heng yang", "Bei jing", "Shang hai", "Chang sha", "Chang chun", "Nan jing", "Dong jing",
            "Ji nan", "Qing dao", "Xiang tan", "Zhu zhou", "Heng yang", "Bei jing", "Shang hai", "Chang sha",
            "Chang chun", "Nan jing", "Dong jing", "Ji nan", "Qing dao", "Xiang tan", "Zhu zhou", "Heng yang",
            "Bei jing", "Shang hai", "Chang sha", "Chang chun", "Nan jing", "Dong jing", "Ji nan", "Qing dao",
            "Xiang tan", "Zhu zhou", "Heng yang"};
    private static String[] stringsMini = new String[]{"1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private static String[] two = new String[]{"a", "b"};

    /**
     * 获取一个String列表
     *
     * @return
     */
    public static List<String> getStringList() {
        return Arrays.asList(strings);
    }

    /**
     * 获取一个数据少的String列表
     *
     * @return
     */
    public static List<String> getStringMiniList() {
        return Arrays.asList(stringsMini);
    }

    /**
     * 获取一个数据少的String列表
     *
     * @return
     */
    public static List<String> getTwoString() {
        return Arrays.asList(two);
    }
}
