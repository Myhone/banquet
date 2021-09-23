package com.lingyan.banquet.global;

import com.blankj.utilcode.util.SPUtils;

/**
 * Created by _hxb on 2021/1/17.
 */

public class HttpURLs {
    public static final String BASE = "http://dev.yanbob.com/";
    public static String IMAGE_BASE = SPUtils.getInstance().getString("img_url");
    public static final String login = BASE + "api/v1/login";
    public static final String upload = BASE + "api/v1/common/upload";
    public static final String uploadAvatar = BASE + "api/v1/user/update_avatar";
    public static final String homeTab = BASE + "api/v1/index/menu_list";
    public static final String listBanquetHall = BASE + "api/v1/banquet/listBanquetHall";
    public static final String monthIndex = BASE + "api/v1/index/month_index";
    public static final String banquetHallList = BASE + "api/v1/banquet/index_list";
    public static final String listBanquetType = BASE + "api/v1/banquet/listBanquetType";
    public static final String listSinglePerson = BASE + "api/v1/user/listSinglePerson";
    public static final String customerChannel = BASE + "api/v1/customer/info";
    public static final String saveBanquetStep1 = BASE + "api/v1/banquet/saveBanquetStep1";
    public static final String saveBanquetStep2 = BASE + "api/v1/banquet/saveBanquetStep2";
    public static final String saveBanquetStep3 = BASE + "api/v1/banquet/saveBanquetStep3";
    public static final String saveBanquetStep4 = BASE + "api/v1/banquet/saveBanquetStep4";
    public static final String saveBanquetStep5 = BASE + "api/v1/banquet/saveBanquetStep5";
    public static final String saveBanquetStep6 = BASE + "api/v1/banquet/saveBanquetStep6";
    public static final String banquetOrderList = BASE + "api/v1/banquetOrder/list";
    public static final String banquetOrderNumberList = BASE + "api/v1/banquetOrder/number_list";
    public static final String banquetOrderInfo = BASE + "api/v1/banquetOrder/info";
    public static final String banquetModifyInfo = BASE + "api/v1/banquet/getmodifyinfo";//订单修改
    public static final String banquetModifySave = BASE + "api/v1/banquet/savemodifyinfo";//保存订单修改
    public static final String banquetOrderNumberInfo = BASE + "api/v1/banquetOrder/number_info";
    public static final String banquetGetInfo = BASE + "api/v1/banquet/getInfo";
    public static final String listMarketing = BASE + "api/v1/banquet/listMarketing";
    public static final String listMeal = BASE + "api/v1/meal/listMeal";
    public static final String listCelebration = BASE + "api/v1/user/listCelebration";
    public static final String depositConfirm = BASE + "api/v1/deposit/confirmed_status";
    public static final String depositReject = BASE + "api/v1/deposit/reject";
    public static final String reFundConfirm = BASE + "api/v1/deposit/refund_status";
    public static final String depositList = BASE + "api/v1/deposit/list";
    public static final String depositInfo = BASE + "api/v1/deposit/info";
    public static final String depositHeaderData = BASE + "api/v1/deposit/header_data";
    public static final String depositHeaderChartData = BASE + "api/v1/deposit/header_chart_data";
    public static final String depositStatisticsData = BASE + "api/v1/deposit/statistics_data";
    public static final String examineIndex = BASE + "api/v1/examine/index";
    public static final String userRecharge = BASE + "api/v1/user/recharge";
    public static final String userInfo = BASE + "api/v1/user/info";
    public static final String examineRevokeCheck = BASE + "api/v1/examine/revokeCheck";
    public static final String examineCheck = BASE + "api/v1/examine/check";
    public static final String examineInfo = BASE + "api/v1/examine/info";
    public static final String searchHall = BASE + "api/v1/index/search_hall";
    public static final String indexHallIndex = BASE + "api/v1/index/hall_index";
    public static final String messageQueryList = BASE + "api/v1/Message/queryList";
    public static final String messageQueryInfo = BASE + "api/v1/Message/queryInfo";
    public static final String insertFeedBack = BASE + "api/v1/user/insertFeedBack";
    public static final String feedBack = BASE + "api/v1/user/feedBack";
    public static final String feedInfo = BASE + "api/v1/user/feed_info";
    public static final String lostOrderInfo = BASE + "api/v1/banquet/lost_orer_info";
    public static final String saveLoseOrder = BASE + "api/v1/banquet/save_lose_order";
    public static final String screenData1 = BASE + "api/v1/screen/data1";//数据大屏
    public static final String screen2Data1 = BASE + "api/v1/screen2/data1";//数据大屏-七月围城专用。。
    public static final String pkItem = BASE + "api/v1/screen2/pkitem";//榜单tab-七月围城专用。。
    public static final String queryDeptUserList = BASE + "api/v1/user/queryDeptUserList/";
    public static final String achievementTabList = BASE + "api/v1/achievement/tabList";
    public static final String achievementIndex = BASE + "api/v1/achievement/index";
    public static final String queryDeptList = BASE + "api/v1/user/queryDeptList";
    public static final String achievementInfo = BASE + "api/v1/achievement/info";
    public static final String achievementUpdate = BASE + "api/v1/achievement/update";
    public static final String achievementSave = BASE + "api/v1/achievement/save";
    public static final String banquetChangeStep3 = BASE + "api/v1/banquet/changeStep3";
    public static final String screenAddList = BASE + "api/v1/screen/add_list";
    public static final String screenLostList = BASE + "api/v1/screen/lost_list";
    public static final String loginAgreement = BASE + "api/v1/login/agreement";
    public static final String userUpdatePwd = BASE + "api/v1/user/update_pwd";
    public static final String banquetReloationOrder = BASE + "api/v1/banquet/reloation_order";
    public static final String indexIntentCount = BASE + "api/v1/index/intent_count";
    public static final String followList = BASE + "api/v1/banquetFollow/list"; //跟进记录列表
    public static final String followAdd = BASE + "api/v1/banquetFollow/add"; //新增跟进记录
    public static final String modifyHall = BASE + "api/v1/banquet/modifyhall2"; //修改包间
    public static final String homeBackgroundImg = BASE + "api/v1/index/info"; //首页背景图
    public static final String todayStar = BASE + "api/v1/screen2/todaystar"; //七月围城今日明星
}
