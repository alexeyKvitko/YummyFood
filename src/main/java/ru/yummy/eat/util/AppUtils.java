package ru.yummy.eat.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yummy.eat.AppConstants;
import ru.yummy.eat.entity.City;
import ru.yummy.eat.model.SearchParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AppUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AppUtils.class);

    static final double EQUATOR_IAL_EARTH_RADIUS = 6378.1370D;
    static final double D2R = ( Math.PI / 180D );

    public static int getAreaDelta( Integer area ) {
        Integer result = 0;
        if ( area <= 10 ) {
            result = area;
        } else if ( area > 10 && area < 40 ) {
            result = 8 + area / 5;
        } else if ( area > 30 && area < 200 ) {
            result = 11 + area / 10;
        } else if ( area > 100 ) {
            result = 20 + area / 100;
        }
        return result;
    }

    public static synchronized String getValueFromHtml( String html, String start, String end,int entry ) {
        if ( start == null || end == null || start.trim().length() == 0 || end.trim().length() == 0 ){
            return null;
        }
        StringBuilder sb = new StringBuilder( );
        int entryIndex = StringUtils.ordinalIndexOf( html,start,entry);
        int startIndex = entryIndex > -1 ? entryIndex + start.length( ) : -1;
        if ( startIndex == -1 ) {
            return null;
        }
        for ( int i = startIndex; i < html.length( ); i++ ) {
            if ( html.charAt( i ) == end.charAt( 0 ) ) {
                break;
            }
            sb.append( html.charAt( i ) );
        }
        return sb.toString( ).trim( );
    }

    public static synchronized String getBackValueFromHtml( String html, String end, String start ) {
        if ( start == null || end == null || start.trim().length() == 0 || end.trim().length() == 0 ){
            return null;
        }
        StringBuilder sb = new StringBuilder( );
        int endIndex = html.indexOf( end ) - 1;
        if ( endIndex == -1 ) {
            return null;
        }
        int startIndex = endIndex;
        for ( int i = endIndex; i >= 0; i-- ) {
            if ( html.charAt( i ) == start.charAt( 0 ) ) {
                startIndex = i;
                break;
            }
        }
        for ( int i = startIndex + 1; i <= endIndex; i++ ) {
            sb.append( html.charAt( i ) );
        }
        return sb.toString( ).trim( ).length() > 0 ? sb.toString( ).trim( ) : null;
    }

    public static synchronized String polish( String html ) {
        boolean processed = true;
        while ( processed ) {
            int startIdx = html.indexOf( AppConstants.START_COMMENT );
            processed = startIdx > -1;
            if ( processed ) {
                int endIdx = html.indexOf( AppConstants.END_COMMENT ) + 3;
                String remove = html.substring( startIdx, endIdx );
                html = html.replace( remove, "" );
            }

        }
        html = html.replaceAll( AppConstants.LEFT_QUOT, AppConstants.LEFT_QUOT_LIT );
        html = html.replaceAll( AppConstants.RIGHT_QUOT, AppConstants.RIGHT_QUOT_LIT );
        html = html.replaceAll( AppConstants.BACK_SLASH_TRIM, AppConstants.BACK_SLASH );
        //уберем множественные пробелы
        StringBuilder sb = new StringBuilder( );
        char prev = 65;
        for ( int i = 0; i < html.length( ); i++ ) {
            if ( html.charAt( i ) != 32 || ( html.charAt( i ) == 32 && prev != 32 ) ) {
                sb.append( html.charAt( i ) );
            }
            prev = html.charAt( i );
        }
        return sb.toString( );
    }

    public static synchronized String getFieldValue( String html, String tag ) {
        SearchParam param = convertToSearch( tag );
        if ( AppConstants.SPACE_SPLIT.equals( param.getEndTag( ) ) ) {
            param.setEndTag( " " );
        }
        String value = null;
        switch( param.getDirection() ){
            case AppConstants.DIRECT_FORWARD:
                value = getValueFromHtml( html, param.getStartTag(), param.getEndTag(), param.getEntry() );
                break;
            case AppConstants.DIRECT_BACKWARD:
                value = getBackValueFromHtml( html, param.getStartTag(), param.getEndTag() );
                break;
            case AppConstants.INLINE_VALUE:
                value = param.getStartTag();
                break;
                default: break;
        }
        if ( value != null ) {
            for ( String htmlTag : AppConstants.HTML_TAGS.keySet() ) {
                value = value.replace( htmlTag, AppConstants.HTML_TAGS.get( htmlTag ) );
            }
            value = value.trim( );
        }
        return value;
    }

    public static String getSection( String html, String sectionTag ) {
        int endIdx = html.indexOf( sectionTag );
        String section = html.substring( endIdx + sectionTag.length( ) );
        endIdx = section.indexOf( sectionTag );
        if ( endIdx > -1 ) {
            section = section.substring( 0, endIdx );
        }
        return section;
    }

    public static int getHaversineInM( double lat1, double long1, double lat2, double long2 ) {
        return ( int ) ( 1000D * HaversineInKM( lat1, long1, lat2, long2 ) );
    }

    public static double HaversineInKM( double lat1, double long1, double lat2, double long2 ) {
        double dlong = ( long2 - long1 ) * D2R;
        double dlat = ( lat2 - lat1 ) * D2R;
        double a = Math.pow( Math.sin( dlat / 2D ), 2D ) + Math.cos( lat1 * D2R ) * Math.cos( lat2 * D2R )
                * Math.pow( Math.sin( dlong / 2D ), 2D );
        double c = 2D * Math.atan2( Math.sqrt( a ), Math.sqrt( 1D - a ) );
        double d = EQUATOR_IAL_EARTH_RADIUS * c;
        return d;
    }

    public static City getNearestCity( double lat1, double long1, List<City> cities){
        City nearest = null;
        int minDistance = 100000;
        for(City city: cities){
            int distance = getHaversineInM( lat1, long1, city.getLatitude(), city.getLongitude() );
            if ( distance < minDistance ){
                 minDistance = distance;
                 nearest = city;
            }
        }
        return nearest;
    }

    public static int getRandomBetweenRange( int min, int max ) {
        Double x = ( Math.random( ) * ( ( max - min ) + 1 ) ) + min;
        return x.intValue( );
    }

    public static SearchParam convertToSearch( String tag ) {
        SearchParam param = new SearchParam( );
        String[] tags = tag.split( AppConstants.CURVE_SPLIT );
        param.setStartTag( tags[0] );
        param.setEndTag( tags[1] );
        param.setDirection( tags[2] );
        param.setEntry( Integer.valueOf( tags[3] ) );
        return param;
    }

    public static String formatDate( String format, Date date ) {
        SimpleDateFormat sdf = new SimpleDateFormat( format );
        String result = null;
        try {
            result = sdf.format(date);
        } catch ( Exception ex) {
            LOG.error( "Ошибка преобразования: "+ex.getMessage() );
        }
        return result;
    }

    public static boolean nullOrEmpty( String value ) {
        return value == null || ( value != null && value.trim().length() == 0 );
    }

    public static boolean nullOrEmpty( List value ) {
        return value == null || ( value != null && value.size() == 0 );
    }

}
