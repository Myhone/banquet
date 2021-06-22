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
        String RATE = "rate";
        String USER_NUMBER = "user_number";
        String TABLE_TITLE = "table_title";
        String TABLE_LIST = "table_list";
        String TARGET_TYPE = "target_type";
        String TITLE = "title";
        String DATA = "data";
    }

    /**
     * 字符串
     */
    public interface Str {
        String PK_COUNTRY_QG = "全国酒店PK榜";
        String PK_PERSONAL_QG = "全国个人PK榜";
        String PK_KING_SIGNED_QG = "全国连单王";
        String PK_DEPARTMENT_GS = "酒店团队PK榜";
        String PK_PERSONAL_GS = "酒店个人PK榜";
        String PK_KING_SIGNED_GS = "酒店连单王";
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
        String DATA1 = "data1";
        String DATA2 = "data2";
        String DATA3 = "data3";
        String DATA4 = "data4";
        String DATA1_RATE = "data1_rate";
        String DATA2_RATE = "data2_rate";
        String DATA3_RATE = "data3_rate";
        String DATA4_RATE = "data4_rate";
        String INCOME = "income";
        String INCOME_RATE = "income_rate";
        String CONTINUATION = "continuation";
    }

}
