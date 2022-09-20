package org.voting.fisco.tool;

import org.fisco.bcos.sdk.utils.Numeric;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class Tool {
    /**
     * description:set a data and time for system
     * method:setData
     * create time: 3:45 PM 2022/3/18
     * create by: gabriel
     */
    public static Date setDate(String year, String month,String day,String hours) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
        String data_str =year+"-"+month+"-"+day+"-"+hours;
        return sdf.parse(data_str);
    }
    /**
     * description:get difference value of two times
     * method:calLastedTime
     * create time: 4:10 PM 2022/3/18
     * create by: gabriel
     */
    public static long getTime(Date timeDate) {
        return timeDate.getTime();
    }
//    /**
//     * description: the api of get difference value
//     * method:getDifferenceTime
//     * create time: 4:17 PM 2022/3/18
//     * create by: gabriel
//     */
//    public static long getDifferenceTime(Date endDate) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
//        String data_str ="1970-1-1-0";
//        Date startDate = sdf.parse(data_str);
//        return calLastedTime(startDate,endDate);
//    }
    /**
     * description:change the bytes to Ascii String
     * method:byteToAscii
     * create time: 2:58 PM 2022/3/17
     * create by: gabriel
     */
    public static String byteToAscii(byte[] byteArray) {
        int temp;
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        //String str = new String(byteArray, StandardCharsets.US_ASCII);
        final StringBuilder hexString = new StringBuilder();

        for (byte b : byteArray) {
            if (b != 0) {
                hexString.append((char)b);
            }
        }
        return hexString.toString().toLowerCase();
    }
    /**
     * description:change the bytes to String
     * method:byteToString
     * create time: 2:59 PM 2022/3/17
     * create by: gabriel
     */
    public static String byteToString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            if ((b & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & b));
        }
        return hexString.toString().toLowerCase();
    }

    /**
     * description:change the Ascii String to Ascii Hex String
     * method:asciiToHex
     * create time: 3:00 PM 2022/3/17
     * create by: gabriel
     */
    public static String asciiToHex(String asciiValue)
    {
        char[] chars = asciiValue.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char aChar : chars) {
            hex.append(Integer.toHexString((int) aChar));
        }
        return hex.toString() + String.join("", Collections.nCopies(32 - (hex.length()/2), "00"));
    }
    /**
     * description:change the String to Ascii byte[]
     * method:stringToByte
     * create time: 3:00 PM 2022/3/17
     * create by: gabriel
     */
    public static byte[] stringToByte(String str){
        return Numeric.hexStringToByteArray(asciiToHex(str));
    }
}
