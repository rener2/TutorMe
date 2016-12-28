package com.personalweb.website.utils;

import com.personalweb.website.form.PageUser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class Parser {

    public static PageUser parseJson(String text) {
        PageUser user = new PageUser();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
                DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            user = mapper.readValue(text, PageUser.class);
        } catch (IOException e) {
            // todo logger.error("Failed to parse JSON value of message object back to java object: " + e);
        }
        return user;
    }
//
//    public static byte[] fileToByte(File file) throws IOException{
//        ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
//        BufferedImage img = ImageIO.read(file);
//        ImageIO.write(img, "jpg", baos);
//        baos.flush();
//        String base64String=Base64.encodeBase64String(baos.toByteArray());
//        baos.close();
//        byte[] bytearray = Base64.decodeBase64(base64String);
//        return bytearray;
//    }
//
//    public static String byteToBase64 (byte[] pic) throws IOException{
//        return Base64.encodeBase64String(pic);
//    }
    public static java.sql.Date parseDate(java.util.Date birthdate) {
        String parsedDate = new SimpleDateFormat("yyyy-MM-dd").format(birthdate);
        java.sql.Date sqlDate = null;
        String strDate = parsedDate.substring(0, 4) + '-' + parsedDate.substring(5, 7) + '-' + parsedDate.substring(8, 10);
        sqlDate = java.sql.Date.valueOf(strDate);
        return sqlDate;
    }
}








