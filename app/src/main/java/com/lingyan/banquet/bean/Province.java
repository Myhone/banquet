package com.lingyan.banquet.bean;

import java.util.List;

/**
 * Created by _hxb on 2019/9/27.
 */
public class Province {

    private String code;
    private String name;
    private List<City> mCities;


    @Override
    public String toString() {
        return  name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return mCities;
    }

    public void setCities(List<City> cities) {
        mCities = cities;
    }

    public static class City{
        private String code;
        private String name;
        private List<Area> mAreas;

        @Override
        public String toString() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Area> getAreas() {
            return mAreas;
        }

        public void setAreas(List<Area> areas) {
            mAreas = areas;
        }

        public static class Area{
            private String code;
            private String name;

            @Override
            public String toString() {
                return name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
