package com.lingyan.banquet;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by _hxb on 2021/3/26.
 */


public class HelloTest {


    @Test
    public  void main() {
        String txt = "你好";
        if(txt.matches(".*\\d+.*") ){
            System.out.println("match");
        }else {
            System.out.println("not match");
        }
    }
}
