package ds.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringEncoderDecoder {
    public static String encode(String str){
        try {
            String output = URLEncoder.encode(str, Constants.ENCODE_CLASS);
            return output;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return str;
        }
    }

    public static String decode(String str){
        try {
            String output = URLDecoder.decode(str, Constants.ENCODE_CLASS);
            return output;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return str;
        }
    }
}
