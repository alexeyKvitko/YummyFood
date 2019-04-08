package ru.yummy.eat;

import java.util.*;

public abstract class AppConstants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "admin";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String UPDATE_DATE_FORMAT = "dd-MM-yyyy HH:mm";

    public static final String UUID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
    public static final String CONFIRM_CODE_PATTERN = "^\\d{4}$";

    public static final int CRIMEA_REGION = 621550;
    public static final int SIMFEROPOL_ID = 621565;
    public static final double SIMFEROPOL_LAT = 44.9521;
    public static final double SIMFEROPOL_LON = 34.1024;
    public static final String SEND_PHONE_CODE = "1111";

    public static final String WSP_TYPE_ONE = "ONE";
    public static final String WSP_TYPE_TWO = "TWO";
    public static final String WSP_TYPE_THREE = "THREE";
    public static final String WSP_TYPE_FOUR = "FOUR";


    public static final String EMAIL_NOT_FOUND = "Пользователь с указанным E-MAIL не найден";
    public static final String PHONE_NOT_FOUND = "Пользователь с указанным телефонным номером не найден";
    public static final String WRONG_PASSWORD = "Введен неверный пароль";
    public static final String WRONG_EMAIL = "Код подтверждения не отправлен, попробуйте позже ...";
    public static final String USER_EXIST = "Пользователь %s зарегистрирован";
    public static final String USER_NOT_EXIST = "Пользователь %s не зарегистрирован";

    public static final String DEFAULT_LOGO = "default.png";
    public static final String SECTION = "SECTION [";

    public static final String SUCCESS = "SUCCESS";

    public static final String TEST_SUCCESS = "TEST SUCCESS";
    public static final String PRODUCTION_SUCCESS = "PRODUCTION SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String ERROR_PRODUCTION = "ERROR PRODUCTION";

    public static final Integer FAKE_ID = -1;
    public static final String FAKE_STR_ID ="-1";

    public static final int PROCEED = 1;
    public static final int PROCESSED = 0;

    public static final String SPACE_SPLIT = "&nbsp;";
    public static final String CURVE_SPLIT = "~";

    public static final String MORE_SYMBOL = "&gt;";

    public static final String MORE_SIGN= ">";
    public static final String LESS_SIGN= "<";


    public static final String STATIC_URL= "http://194.58.122.145:8080/static/images/";



    public static final String START_COMMENT= "<!--";
    public static final String END_COMMENT= "-->";

    public static final String DIRECT_FORWARD = "f";
    public static final String INLINE_VALUE = "i";
    public static final String DIRECT_BACKWARD = "b";
    public static final String INLINE_TAG_VALUE = "~i~";

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
            put("/>","");
            put("</","");
            put(">","");
            put("<","");
            put("=","");
            put("br","");
            put("&#1088;","");
            put("&#171;","«");
            put("&#187;","»");
        }
    };

    public static final String FORGOT_PASSWORD = "FORGOT_PASSWORD";

}
