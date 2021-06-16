package com.lingyan.banquet.ui.main.bean;

/**
 * Created by _hxb on 2021/2/16.
 */

public class HomeCalenderScheme {
    /**
     * 午餐数量
     */
    public String lunchCount = "0";
    /**
     * 晚餐数量
     */
    public String dinnerCount = "0";

    /**
     * 好日子的颜色
     */
    public String goodDayColor = "#FF0000";

    public boolean isGoodDay = false;

    @Override
    public String toString() {
        return "HomeCalenderScheme{" +
                "lunchCount='" + lunchCount + '\'' +
                ", dinnerCount='" + dinnerCount + '\'' +
                ", goodDayColor='" + goodDayColor + '\'' +
                ", isGoodDay=" + isGoodDay +
                '}';
    }
}
