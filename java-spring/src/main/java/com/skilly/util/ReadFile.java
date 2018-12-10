package com.skilly.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.*;

/**
 * Created by ${1254109699@qq.com} on 2018/1/4.
 */
public class ReadFile {
    private static ArrayList<String> result;

    public static ArrayList read(File file, String search, boolean isSort, boolean uniq) {
        result = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                if (s.indexOf(search) != -1) {
                    result.add(System.lineSeparator() + s);
                }
            }
            br.close();

            if (isSort) {
                Collections.sort(result);
            }

            if (uniq) {

                HashSet<String> hashSet = new HashSet<String>();
                hashSet.addAll(result); //set无序
                ArrayList<String> res = new ArrayList<String>();
                res.addAll(hashSet);


//                ArrayList<String> res = new ArrayList<String>();
//                ListIterator<String> iter = result.listIterator();
//                while (iter.hasNext()) {
//                    String str = (String) iter.next();
//                    if (!res.contains(str)) {
//                        res.add(str);
//                    }
//                }
                System.out.println(res.get(0));
                return res;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        File file = new File("H:\\11.txt");
        read(file, "abc", true, true);

//        HashSet<String> hashSet=new HashSet<String>();
//        hashSet.addAll(list);
//        List<String> listWithoutDup =new ArrayList<String>();
//        list1.addAll(hashSet);
//        Collections.sort(listWithoutDup );
//        for(String str:listWithoutDup ){
//            System.out.println(str);
//        }
    }
}
