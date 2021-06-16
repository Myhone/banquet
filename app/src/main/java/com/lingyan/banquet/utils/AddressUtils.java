package com.lingyan.banquet.utils;

import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.App;
import com.lingyan.banquet.bean.Province;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by _hxb on 2021/2/6.
 */

public class AddressUtils {


    public static ArrayList<Province> init() {
        String json = ResourceUtils.readAssets2String("area.json");
        JsonArray jsonArray = (JsonArray) JsonParser.parseString(json);
        int size = jsonArray.size();
        ArrayList<Province> provinceList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            JsonObject provinceJo = (JsonObject) jsonArray.get(i);
            Province province = new Province();
            province.setCode(provinceJo.get("id").getAsString());
            province.setName(provinceJo.get("name").getAsString());
            provinceList.add(province);
            //市
            ArrayList<Province.City> cityArrayList = new ArrayList<>();
            province.setCities(cityArrayList);
            JsonArray cityJa = provinceJo.get("child").getAsJsonArray();
            int citySize = cityJa.size();
            for (int j = 0; j < citySize; j++) {
                JsonObject cityJo = cityJa.get(j).getAsJsonObject();
                Province.City city = new Province.City();
                cityArrayList.add(city);

                city.setCode(cityJo.get("id").getAsString());
                city.setName(cityJo.get("name").getAsString());

                //区
                JsonArray areaJA = cityJo.get("child").getAsJsonArray();
                ArrayList<Province.City.Area> areaArrayList = new ArrayList<>();
                city.setAreas(areaArrayList);
                for (int k = 0; k < areaJA.size(); k++) {
                    JsonObject areaJo = areaJA.get(k).getAsJsonObject();
                    Province.City.Area area = new Province.City.Area();
                    areaArrayList.add(area);

                    area.setCode(areaJo.get("id").getAsString());
                    area.setName(areaJo.get("name").getAsString());
                }
            }


        }


        return provinceList;
    }

    public static ArrayList<Province> parseXMLWithPull() {

        ArrayList<Province> addressList = new ArrayList<>();
        //一系列的初始化
        List<Province> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        //获得Document对象

        Document document = null;
        try {
            document = builder.parse(App.sApp.getResources().getAssets().open("province_data.xml"));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = document.getDocumentElement();
        NodeList provinceList = root.getElementsByTagName("province");
        for (int i = 0; i < provinceList.getLength(); i++) {
            Element provinceNode = (Element) provinceList.item(i);
            Province province = new Province();
            province.setCode(provinceNode.getAttribute("zipcode"));
            province.setName(provinceNode.getAttribute("name"));

            addressList.add(province);

            NodeList cityNodeList = provinceNode.getElementsByTagName("city");
            for (int j = 0; j < cityNodeList.getLength(); j++) {
                Element cityElement = (Element) cityNodeList.item(j);
                Province.City city = new Province.City();
                city.setCode(cityElement.getAttribute("zipcode"));
                city.setName(cityElement.getAttribute("name"));
                List<Province.City> cities = province.getCities();
                if (cities == null) {
                    cities = new ArrayList<>();
                    province.setCities(cities);
                }
                cities.add(city);


                NodeList areaList = cityElement.getElementsByTagName("district");
                for (int k = 0; k < areaList.getLength(); k++) {
                    Element areaElement = (Element) areaList.item(k);
                    Province.City.Area area = new Province.City.Area();
                    List<Province.City.Area> areas = city.getAreas();
                    if (areas == null) {
                        areas = new ArrayList<>();
                        city.setAreas(areas);
                    }
                    area.setName(areaElement.getAttribute("name"));
                    area.setCode(areaElement.getAttribute("zipcode"));
                    areas.add(area);
                }
            }
        }


        return addressList;
    }
}
