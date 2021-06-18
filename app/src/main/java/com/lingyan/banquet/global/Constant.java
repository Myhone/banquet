package com.lingyan.banquet.global;

/**
 * Created by wyq on 2021/6/18.
 */
public class Constant {

    /**
     * 参数
     */
    public interface Parameter {
        String JSON = "json";
        String ORDER = "order";
        String PAGE = "page";
        String LIMIT = "limit";
        String TIME_TYPE = "time_type";
        String RANGE = "range";
    }

    /**
     * 筛选器相关
     */
    public interface Filter {
        String TODAY = "today";
        String MONTH = "month";
        String WEEK = "week";
        String YESTERDAY = "yesterday";
        String PERIOD_ZERO = "period0";
        String PERIOD_ONE = "period1";
        String PERIOD_TWO = "period2";
        String PERIOD_THREE = "period3";
    }

    public interface RankingTab {

    }

}
