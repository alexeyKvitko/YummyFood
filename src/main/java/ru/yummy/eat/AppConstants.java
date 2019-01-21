package ru.yummy.eat;

import java.util.*;
//https://www.devglan.com/angular/angular-7-crud-example
public abstract class AppConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "admin";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final int CRIMEA_REGION = 621550;
    public static final int SIMFEROPOL_ID = 621565;
    public static final String SIMFEROPOL_NAME = "Симферополь";
    public static final String DEFAULT_LOGO = "default.png";
    public static final String SECTION = "SECTION [";

    public static final Integer FAKE_ID = -1;

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
    public static final String INLINE_VALUE = "i";
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
            put("</div","");
            put("div","");
            put("class","");
            put("&quot;","");
            put("&nbsp;"," ");
            put("руб.","");
            put("руб","");
            put("</","");
            put(">","");
            put("<","");
            put("=","");
            put("&#1088;","");
        }
    };


}