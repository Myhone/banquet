package com.lingyan.banquet.ui.main.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/4.
 */

public class NetHomeTab {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":207,"title":"宴会预定","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/c98ed09e2c743d8bc929d38221cc46c8.png"},{"id":208,"title":"宴会订单","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/458870ad2022fcaab215b2e6f3657173.png"},{"id":209,"title":"庆典预定","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/a3593c16324af7909b14934ab0acdc58.png"},{"id":210,"title":"庆典订单","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/5592291958d5cdc801e77e4433c97d9b.png"},{"id":211,"title":"财务管理","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/98c33f11893e34b91dd219ed33729fa9.png"},{"id":212,"title":"目标管理","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/d569fd14b74d6b5ad777ba5ced27e637.png"},{"id":214,"title":"数据大屏","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/0e281181640c14710b2708a4e2d6e188.png"},{"id":215,"title":"审批记录","component":"","app_icon":"http://api.forvery.top/uploads/attach/2021/01/20210115/65666785dda4327e6226c3c48dd5aa30.png"}]
     */

    private int code;
    private String msg;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * id : 207
         * title : 宴会预定
         * component :
         * app_icon : http://api.forvery.top/uploads/attach/2021/01/20210115/c98ed09e2c743d8bc929d38221cc46c8.png
         */

        private int id;
        private String title;
        private String component;
        private String app_icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getComponent() {
            return component;
        }

        public void setComponent(String component) {
            this.component = component;
        }

        public String getApp_icon() {
            return app_icon;
        }

        public void setApp_icon(String app_icon) {
            this.app_icon = app_icon;
        }
    }
}
