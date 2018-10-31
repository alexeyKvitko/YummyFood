package ru.yummy.food;

import java.util.*;

public abstract class AppConstants {

    public static final int PROCEED = 1;
    public static final int PROCESSED = 0;

    public static final String SPACE_SPLIT = "&nbsp;";
    public static final String CURVE_SPLIT = "~";

    public static final String MORE_SYMBOL = "&gt;";

    public static final String MORE_SIGN= ">";
    public static final String LESS_SIGN= "<";

    public static final String START_COMMENT= "<!--";
    public static final String END_COMMENT= "-->";

    public static final String DIRECT_FORWARD = "f";
    public static final String DIRECT_BACKWARD = "b";

    public static final String LEFT_QUOT= "&#8220;";
    public static final String RIGHT_QUOT= "&#8221;";
    public static final String LEFT_QUOT_LIT= "«";
    public static final String RIGHT_QUOT_LIT= "»";
    public static final String BACK_SLASH_TRIM= " / ";
    public static final String BACK_SLASH= "/";

    public static final Map<String,String> HTML_TAGS = new LinkedHashMap<String,String>(){
        {
            put("<p>","");
            put("</p>","");
            put("<h3>","");
            put("</h3>","");
            put("<span>","");
            put("</span","");
            put("&quot;","");
            put("</","");
        }
    };


}
