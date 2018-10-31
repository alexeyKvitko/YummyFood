package ru.yummy.food.util;

import ru.yummy.food.AppConstants;
import ru.yummy.food.model.SearchParam;

public abstract class AppUtils {

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

    public static synchronized String getValueFromHtml( String html, String start, String end ) {
        StringBuilder sb = new StringBuilder( );
        int startIndex = html.indexOf( start ) > -1 ? html.indexOf( start ) + start.length( ) : -1;
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
        StringBuilder sb = new StringBuilder( );
        int endIndex = html.indexOf( end ) - 1;
        if ( endIndex == -1 ) {
            return null;
        }
        int startIndex = endIndex;
        for ( int i = endIndex; i >= 0; i-- ) {
            if ( html.charAt( i ) == end.charAt( 0 ) ) {
                startIndex = i;
                break;
            }
        }
        for ( int i = startIndex + 1; i <= endIndex; i++ ) {
            sb.append( html.charAt( i ) );
        }
        return sb.toString( ).trim( );
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
        String value = AppConstants.DIRECT_FORWARD.equals( param.getDirection( ) ) ?
                getValueFromHtml( html, param.getStartTag( ), param.getEndTag( ) ) :
                getBackValueFromHtml( html, param.getStartTag( ), param.getEndTag( ) );
        if ( value != null ) {
            value = value.trim( );
            for ( String htmlTag : AppConstants.HTML_TAGS.keySet() ) {
                value = value.replace( htmlTag, AppConstants.HTML_TAGS.get( htmlTag ) );
            }
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

    public static int HaversineInM( double lat1, double long1, double lat2, double long2 ) {
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
        return param;
    }

}
