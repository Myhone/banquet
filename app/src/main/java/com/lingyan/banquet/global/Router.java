package com.lingyan.banquet.global;

import android.net.Uri;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.ui.apply.ApplyRecordListActivity;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.banquet.ReserveHomeActivity;
import com.lingyan.banquet.ui.celebration.CelStepManagerActivity;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.finance.FinanceManageActivity;
import com.lingyan.banquet.ui.finance.FinanceOrderDetailActivity;
import com.lingyan.banquet.ui.message.MessageDetailActivity;
import com.lingyan.banquet.ui.order.OrderDetailActivity;
import com.lingyan.banquet.ui.order.OrderListActivity;
import com.lingyan.banquet.ui.target.TargetHomeActivity;

/**
 * Created by _hxb on 2021/2/16.
 */

public class Router {

    //ybb://banquetReservation
    public static boolean navigation(String strURI) {
        if (StringUtils.isTrimEmpty(strURI)) {
            return false;
        }

        if (!strURI.startsWith("ybb://www.ybb.com/")) {
            strURI = strURI.replaceFirst("ybb://", "ybb://www.ybb.com/");
        }
        Uri uri = Uri.parse(strURI);
        String path = uri.getPath();
        if (ObjectUtils.isEmpty(path)) {
            return false;
        }
        path = path.trim();
        switch (path) {
            case "/partyList": {
                //宴会首页
                ReserveHomeActivity.start();
                break;
            }
            case "/banquetReservation": {
                //宴会预定
                BanquetStepManagerActivity.start(null, 0);
                return true;
            }
            case "/partyOrders": {
                //宴会订单
                OrderListActivity.start(BanquetCelebrationType.TYPE_BANQUET);
                return true;
            }
            case "/celebrationScheduled": {
                //庆典预定
                CelStepManagerActivity.start(null, 0);

                return true;
            }
            case "/celebrationOfOrders": {
                //庆典订单
                OrderListActivity.start(BanquetCelebrationType.TYPE_CELEBRATION);

                return true;
            }
            case "/financialManagement": {
                //财务管理
                FinanceManageActivity.start();
                return true;
            }
            case "/ManagementByObjectives": {
                //目标管理
                TargetHomeActivity.start();
                return true;
            }
            case "/dataScreen": {
                //数据大屏
                DataHomeActivity.start();
                return true;
            }
            case "/examinationAndApprovalRecords": {
                //审批记录
                ApplyRecordListActivity.start();
                return true;
            }

            //消息详情   ybb://messageDetails?id=xxx  （消息id）
            //
            //财务详情    ybb://caiWuDetails?id=xxx  （详情页id   定金/尾款同界面同接口）
            //
            //订单详情  ybb://orderDetails?type=xx&identity=xxx&id=xxx
            //  (type 1.宴会 2.庆典)
            //  (identity 1.正常订单 2.场次订单)
            //  (id  订单的id)

            case "/messageDetails": {
                //消息详情
                String id = uri.getQueryParameter("id");
                MessageDetailActivity.start(id);
                return true;
            }
            case "/caiWuDetails": {
                //财务详情
                String id = uri.getQueryParameter("id");
                FinanceOrderDetailActivity.start(id);
                return true;
            }
            case "/orderDetails": {
                //订单详情
                String type = uri.getQueryParameter("type");
                String identity = uri.getQueryParameter("identity");
                String id = uri.getQueryParameter("id");
                //(identity 1.正常订单 2.场次订单)
                String url = HttpURLs.banquetOrderInfo;
                if(StringUtils.equals("1",identity)){
                    url = HttpURLs.banquetOrderInfo;
                }else {
                    url = HttpURLs.banquetOrderNumberInfo;
                }
                OrderDetailActivity.start(url,id,Integer.parseInt(type));

                return true;
            }


        }


        return false;
    }
}
