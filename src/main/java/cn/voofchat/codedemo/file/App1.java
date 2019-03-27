package cn.voofchat.codedemo.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2019/2/25
 * @time: 11:17 AM
 * Description:
 */
public class App1 {


    public static void main(String[] args) throws IOException {
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            ids.add(new String("100"));
        }
        System.out.println(ids.size());
    }
}
