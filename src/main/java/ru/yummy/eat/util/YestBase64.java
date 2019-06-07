package ru.yummy.eat.util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class YestBase64 {

    public static void main(String[] args) {

//        FileOutputStream fos = null;
//        try {
//            String base64String = IOUtils.toString(new FileInputStream(new
//                    File("c:/work-temp/pd-f.txt")), "UTF-8");
//            fos = new FileOutputStream("c:/work-temp/1.pdf");
//            fos.write(Base64.getDecoder().decode(base64String));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    String rate = "45";
    Double val = Double.valueOf( rate.substring(0,1)+"."+rate.substring(1,2) );
        System.out.println(val);
    }
}
