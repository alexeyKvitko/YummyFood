package ru.yummy.food.service.impl;

import org.springframework.stereotype.Service;

@Service
public class ParseRoomService extends ParseServiceImpl {

    @Override
    public synchronized void parsePage() {
//        SearchResponse searchResponse = new SearchResponse( );
//        initProcess();
//        if ( getProxies().size() < 5  ){
//            setLog( AppConstants.AVITO_TYPE_ROOM, "Закончились прокси адреса: [" + LocalTime.now( ).toString( ) + "]" );
//            return searchResponse;
//        }
//        parseList = AvitoValues.getInstance( ).getParseList( );
//        String sessionId = AppUtils.getRandomBetweenRange( 10000, 20000 ) + "";
//        boolean timeOver = false;
//        Long startTime = (new Date()).getTime();
//        LocalTime startTimeProcess = LocalTime.now();
//        setLog( AppConstants.AVITO_TYPE_ROOM, "СТАРТ СЕАНСА ИЗВЛЕЧЕНИЯ КОМНАТ: " + sessionId + "[" + LocalTime.now( ).toString( ) + "]" );
//        Map< Integer,String> htmlResponses = getParsingPages( AppConstants.AVITO_TYPE_ROOM, AppConstants.PAGE_DOWNLOAD_ROOM );
//        List< RoomEstate > rooms = new LinkedList<>( );
//        for ( int page = 1; page <= AppConstants.PAGE_DOWNLOAD_ROOM; page++ ) {
//            String htmlResponse = htmlResponses.get( page );
//            System.out.println("СЕАНС: " + sessionId + "[ Страница: " + page+"/"+ LocalTime.now( ).toString( ) + "]" );
//            for ( int rc = 1; rc < 101; rc++ ) {
////                    ПАРСИМ ЗАПИСЬ
//                String roomPrice = AppUtils.getValueFromHtml(htmlResponse, parseList.getPriceStart(), parseList.getPriceEnd());
//                String roomInRoom = AppUtils.getValueFromHtml(htmlResponse, parseList.getRoomStart(), parseList.getRoomEnd())
//                        .replace( AppConstants.TYPE_ROOM , "").trim();
//                String roomPeriod = AppUtils.getValueFromHtml(htmlResponse, parseList.getPeriodStart(), parseList.getPeriodEnd());
//                String roomId = AppUtils.getValueFromHtml(htmlResponse, parseList.getLandIdStart(), parseList.getLandIdEnd());
//                String roomHref = AppUtils.getValueFromHtml(htmlResponse, parseList.getHfefStart(), parseList.getHfefEnd());
//                String roomArea = AppUtils.getValueFromHtml(htmlResponse, parseList.getLandAreaStart(), parseList.getLandAreaEnd());
//                String roomFloor = AppUtils.getValueFromHtml(htmlResponse, parseList.getFloorStart(), parseList.getFloorEnd());
//                String floor = null;
//                String houseHeight = null;
//                if (roomFloor != null && roomFloor.indexOf("/") > 0) {
//                    floor = roomFloor.split("/")[0] != null ? roomFloor.split("/")[0].trim() : "";
//                    houseHeight = roomFloor.split("/")[1] != null ? roomFloor.split("/")[1].trim() : "";
//                }
//                Double area = 0.00;
//                String areaDesc = "";
//                if (roomArea != null && roomArea.length() > 0) {
//                    area = Double.valueOf(roomArea.split(" ")[0]);
//                    areaDesc = roomArea.split(" ")[1];
//                }
//                String roomTitle = AppUtils.getValueFromHtml(htmlResponse, parseList.getTitleStart(), parseList.getTitleEnd());
//                String roomDate = AppUtils.getValueFromHtml(htmlResponse, parseList.getDateStart(), parseList.getDateEnd());
////                    ПРОВЕРЯЕМ ВРЕМЯ
//            if ( rc > AppConstants.PRIVILAGE_COUNT && !AppUtils.checkAdForDate( startTimeProcess, roomDate ) ) {
//                timeOver = true;
//                break;
//            }
////                    УМЕНЬШАЕМ HTML
//                htmlResponse = htmlResponse.substring(htmlResponse.indexOf(AvitoValues.getInstance().getParseList().getStartCut()) +
//                        AvitoValues.getInstance().getParseList().getStartCut().length() );
//                RoomEstate roomEstate = new RoomEstate();
//                roomEstate.setId(Integer.valueOf(roomId));
//                roomEstate.setCategory("");
//                roomEstate.setTitle(roomTitle);
//                roomEstate.setPrice(Integer.valueOf(roomPrice));
//                roomEstate.setPeriod(roomPeriod);
//                roomEstate.setArea(area);
//                roomEstate.setAreaDesc(areaDesc);
//                roomEstate.setDate(AppUtils.createDateFromAvito(roomDate));
//                roomEstate.setAvitoURL(roomHref);
//                roomEstate.setProcessed(AppConstants.NOT_PROCESSED);
//                roomEstate.setRoomCount( roomInRoom );
//                roomEstate.setFloor(floor);
//                roomEstate.setHouseHeight(houseHeight);
//                rooms.add( roomEstate );
//            }
//            if ( timeOver ) {
//                break;
//            }
//        }
//        Set<Integer> existRoomIds = ((HashSet) roomEstateRepo.getAllId());
//        Map<Integer,List<RoomEstate>> candidates =  new HashMap<>();
//        List<RoomEstate> existRooms =  new LinkedList<>();
//        Integer key = 0;
//        candidates.put( key, new LinkedList<>() );
//        int roomIdx = 0;
//        for (RoomEstate room: rooms){
//            String cityUri = AppUtils.getCityPathFromAvito( room.getAvitoURL(), AvitoValues.getInstance( ).getAvitoValues( ).get( AppConstants.AVITO_TYPE_ROOM ) );
//            City city = cityMap.get( cityUri );
//            if ( city == null ){
//                city = new City();
//                city.setUrl( cityUri );
//                cityUri = cityUri.substring( 1 ).toUpperCase();
//                city.setNameEn( cityUri );
//                city.setRegionId( AppConstants.FAKE_ID );
//                city.setProcessed( 0 );
//                cityRepo.save( city );
//            }
//            room.setRegionId( city.getRegionId() );
//            room.setCityId( city.getId() );
//            if ( !existRoomIds.contains( room.getId()) ){
//                candidates.get(key).add( room );
//                if ( roomIdx == AppConstants.ESTATE_BACKETS ){
//                    roomIdx = 0;
//                    key++;
//                    candidates.put( key, new LinkedList<>() );
//                }
//                roomIdx++;
//                existRoomIds.add( room.getId() );
//            } else {
//                existRooms.add( room );
//            }
//        }
//        saveRoomsThreadPool( candidates, sessionId );
//        roomEstateRepo.updateNullLatitude();
//        uploadProcess( sessionId );
//        setLog( AppConstants.AVITO_TYPE_ROOM,"СЕАНС: " + sessionId + ", ОСТАЛОСЬ ПРОКСИ: ["+ getProxies().size()+ "]");
//        updateProxies();
//        Long endTime = ( new Date() ).getTime();
//        Double duaration = (endTime.doubleValue()-startTime.doubleValue())/60000;
//        setLog( AppConstants.AVITO_TYPE_ROOM, "СИНХРОНИЗАЦИЯ КОМНАТ ЗАВЕРШЕНА: " + sessionId + "[" + LocalTime.now( ).toString( ) + "/d: "+ duaration.toString()+"]" );
//        searchResponse.setResponseCode( HttpStatus.OK.value( ) );
//        return searchResponse;
        return;
    }


//    private void uploadProcess( String sessionId ) {
//        List< RoomEstate > rooms = roomEstateRepo.findAllByProcessed( AppConstants.NOT_PROCESSED );
//        if ( rooms.isEmpty( ) ) {
//            return;
//        }
//        int poolCount = AppConstants.PULL_COUNT < rooms.size( ) ? AppConstants.PULL_COUNT : rooms.size( );
//        ExecutorService tasksPool = Executors.newFixedThreadPool( poolCount );
//        int finishedTasks = 0;
//        setLog( AppConstants.AVITO_TYPE_ROOM, "СЕАНС: " + sessionId + "[" + LocalTime.now( ).toString( ) + "], орбработка данных : ["+rooms.size()+"] комнат" );
//        try {
//            List< Future< Integer > > downloads = new LinkedList<>( );
//            for ( int i = 0; i < rooms.size( ); i++ ) {
//                ParseRoomService.RoomDownloader downloader =
//                        new ParseRoomService.RoomDownloader( rooms.get( i ), sessionId );
//                Future< Integer > result = tasksPool.submit( downloader );
//                downloads.add( result );
//            }
//            while ( finishedTasks < rooms.size( ) ) {
//                finishedTasks = 0;
//                for ( Future< Integer > result : downloads ) {
//                    try {
//                        finishedTasks += result.get( );
//                    } catch ( InterruptedException | ExecutionException ex ) {
//                        finishedTasks++;
//                    }
//                }
//            }
//            setLog( AppConstants.AVITO_TYPE_ROOM, "СЕАНС: " + sessionId + "[" + LocalTime.now( ).toString( ) + "], Обработано [" + rooms.size( ) + " комнат]" );
//        } catch ( Exception e ) {
//            setLog( AppConstants.AVITO_TYPE_ROOM, "СЕАНС: " + sessionId + "[" + LocalTime.now( ).toString( ) + "], Ошибка обработки комнат: " + e.getMessage( ) );
//        } finally {
//            tasksPool.shutdown( );
//        }
//    }
//
//    class RoomDownloader implements Callable<Integer> {
//
//        private RoomEstate room;
//        private String sessionId;
//
//        public RoomDownloader(RoomEstate roomEstate,String sessionId) {
//            this.room = roomEstate;
//            this.sessionId = sessionId;
//        }
//
//        @Override
//        public Integer call() throws Exception {
//            String path = AvitoValues.getInstance( ).getAvitoValues( ).get( AppConstants.AVITO_URL_KEY ) + room.getAvitoURL( );
//            HttpTransport httpTransport = null;
//            FreeProxyAddress freeProxyAddress = null;
//            String htmlResponse = null;
//            try {
//                do {
//                    try {
//                        freeProxyAddress = freeProxyAddress == null ? getFreeProxy( ) : freeProxyAddress;
//                        Proxy proxy = new Proxy( Proxy.Type.HTTP,
//                                new InetSocketAddress( InetAddress.getByName( freeProxyAddress.getHost( ) ),
//                                        freeProxyAddress.getPort( ) ) );
//                        httpTransport = new NetHttpTransport.Builder( ).setProxy( proxy ).build( );
//                        HttpRequest request = httpTransport.createRequestFactory( ).buildGetRequest( new GenericUrl( path ) );
//                        ////                ПОЛУЧАЕМ HTML
//                        htmlResponse = request.execute( ).parseAsString( ).replace( "\n", "" );
//                        if ( htmlResponse.contains( AppConstants.ACCESS_DENIED ) ){
//                            throw new IOException( AppConstants.ACCESS_DENIED );
//                        }
//                    } catch ( Exception e ) {
//                        if ( httpTransport != null ) {
//                            httpTransport.shutdown( );
//                        }
//                        if ( freeProxyAddress != null ){
//                            freeProxyAddress.setError( e.getMessage().length() > 1000 ? e.getMessage().substring( 0,999 ) : e.getMessage() );
//                            freeProxyAddress.setModifyDate( new Timestamp( (new Date()).getTime()) );
//                            getBrokenProxies().add( freeProxyAddress );
//                            getProxies().remove( freeProxyAddress );
//                        }
//                        freeProxyAddress = null;
//                        htmlResponse = null;
//                    }
//                } while ( htmlResponse == null );
//                String roomAddress = AppUtils.getValueFromHtml(htmlResponse, parseList.getAddressStart(), parseList.getAddressEnd());
//                String roomLatitude = AppUtils.getValueFromHtml(htmlResponse, parseList.getLatitudeStart(), parseList.getLatitudeEnd());
//                String roomLongitude = AppUtils.getValueFromHtml(htmlResponse, parseList.getLongitudeStart(), parseList.getLongitudeEnd());
//                String roomImageUrl = AppUtils.getValueFromHtml( htmlResponse, parseList.getImageStart(), parseList.getImageEnd() );
//                roomImageUrl = roomImageUrl != null ? roomImageUrl.replaceAll( "'", "" ) : null;
//
//                room.setAdType(getAdType(htmlResponse));
//                room.setAddress(roomAddress == null ? "" : roomAddress);
//                room.setLatitude(roomLatitude != null && roomLatitude.trim().length() > 0 ? Double.valueOf(roomLatitude) : null);
//                room.setLongitude(roomLongitude != null && roomLatitude.trim().length() > 0 ? Double.valueOf(roomLongitude) : null);
//                room.setImageUrl( roomImageUrl );
//                room.setProcessed(AppConstants.PROCESSED);
//            } catch ( Exception e ) {
//                String ex = "Ошибка чтения объекта: " + room.getTitle( ) + ", причина: " + e.getStackTrace( )[0].toString( ) + ", " + e.getMessage( );
//                threadLog( ex );
//                throw new InterruptedException( ex );
//            } finally {
//                try {
//                    roomEstateRepo.save( room );
//                } catch ( Exception e ) {
//                    String ex = "Ошибка записи объекта: " + room.getTitle( ) + ", причина: " + e.getStackTrace( )[0].toString( ) + ", " + e.getMessage( );
//                    threadLog( ex );
//                    throw new InterruptedException( ex );
//                }
//                if ( httpTransport != null ) {
//                    try {
//                        httpTransport.shutdown( );
//                    } catch ( IOException e ) {
//                        e.printStackTrace( );
//                    }
//                }
//            }
//            return 1;
//        }
//
//        private void threadLog( String ex ){
//            System.out.println( ex );
//            Logs log = new Logs( );
//            log.setMessage( ex );
//            log.setType( AvitoValues.getInstance( ).getAvitoValues( ).get( AppConstants.AVITO_TYPE_ROOM ) );
//            log.setModifyDate( new Date( ) );
//            logRepo.save( log );
//        }
//
//    }
//
//    // СОХРАНЯЕМ НЕДВИЖИМОСТЬ
//    private void saveRoomsThreadPool(Map<Integer,List<RoomEstate>> roomEstateMap, String sessionId ){
//        ExecutorService tasksPool = Executors.newFixedThreadPool( roomEstateMap.size() );
//        int finishedTasks = 0;
//        try {
//            List< Future< Integer > > savers = new LinkedList<>( );
//            for ( int i = 0; i < roomEstateMap.size( ); i++ ) {
//                ParseRoomService.RoomSaver saver =
//                        new ParseRoomService.RoomSaver( roomEstateMap.get(i));
//                Future< Integer > result = tasksPool.submit( saver );
//                savers.add( result );
//            }
//            while ( finishedTasks < roomEstateMap.size( ) ) {
//                finishedTasks = 0;
//                for ( Future< Integer > result : savers ) {
//                    try {
//                        finishedTasks += result.get( );
//                    } catch ( InterruptedException | ExecutionException ex ) {
//                        finishedTasks++;
//                    }
//                }
//            }
//        } catch ( Exception e ) {
//            setLog( AppConstants.AVITO_TYPE_ROOM, "СЕАНС: " + sessionId + "[" + LocalTime.now( ).toString( ) + "], Ошибка записи комнат: " + e.getMessage( ) );
//        } finally {
//            tasksPool.shutdown( );
//        }
//    }
//
//
//    class RoomSaver implements Callable< Integer > {
//
//        private List<RoomEstate> rooms;
//
//        public RoomSaver( List<RoomEstate> rooms) {
//            this.rooms = rooms;
//        }
//
//        @Override
//        public Integer call() throws Exception {
//            roomEstateRepo.saveAll( rooms );
//            return 1;
//        }
//
//    }

}
