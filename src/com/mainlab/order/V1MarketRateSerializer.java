//package message;
//
//import com.integral.finance.dealing.Quote;
//import com.integral.is.ISCommonConstants;
//import com.integral.is.message.MarketRate;
//import com.integral.is.message.*;
//import com.integral.log.Log;
//import com.integral.log.LogFactory;
//import com.integral.time.DateTimeFactory;
//import com.integral.time.IdcDate;
//
//import java.nio.ByteBuffer;
//import java.text.SimpleDateFormat;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class V1MarketRateSerializer {
//
//    private static final char PIPE = '|';
//    public static final char OPEN_CURLY_BRACE = '{';
//    public static final char CLOSE_CURLY_BRACE = '}';
//    public static final char COMMA = ',';
//    public static final char TILDE = '~';
//
//    private static final char CHARARRAY_PIPE = '|';
//    private static final char CHARARRAY_TILDE = '~';
//    private static final char CHARARRAY_COMMA = ',';
//
//    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    private Map <String, IdcDate> dateFormatMap = new ConcurrentHashMap<String, IdcDate>(20);
//    private Map <IdcDate, String> formattedStringMap = new ConcurrentHashMap<IdcDate, String>(20);
//
//    protected Log log = LogFactory.getLog("com.integral.is.message.MarketRateSerializer");
//
//    /*package*/
//
//    V1MarketRateSerializer() {
//    }
//
//    public boolean serialize(MarketRate rate, ByteBuffer out) {
//        throw new IllegalAccessError("Unsupported operation on V1 serializer.");
//    }
//
//
//
//    public boolean deserialize(ByteBuffer in, MarketRate rate) {
//        throw new IllegalAccessError("Unsupported error for V1 serializer.");
//    }
//
//    /**
//     * Convert string to MarketRate object
//     *
//     * @param str
//     * @return MarketRate
//     */
//    public MarketRate deserialize(String str) {
//        int[] mainDelimIndexPoints = getMainDelimeterIndexArray(str);
//        String providerShortName = null;
//        try {
//            providerShortName = str.substring(mainDelimIndexPoints[12] + 1, mainDelimIndexPoints[13]);
//        }
//        catch (Exception e) {
//            log.error( "Excpetion in deserialize - V1 Serializer rate", e);
//            StringBuilder sb = new StringBuilder(128);
//            for (int i = 0; i < mainDelimIndexPoints.length; i++) {
//                sb.append(mainDelimIndexPoints[i] + ", ");
//            }
//            log.warn( sb.append(".\nOrigString:-").append(str).toString() );
//        }
//        return deserialize(str, mainDelimIndexPoints, providerShortName);
//    }
//
//    public MarketRate deserialize(String str, int[] mainDelimIndexPoints, String providerShortName) {
//        /*1|1|G-2cbe1cf6a7-111eb1bf645-CITI-33|{G-2cbe1cf6a7-111eb1bf645-CITI-33,}|RPSNY|USD|CAD|null|{1.3911,}|{1.3912,}|
//        {2.0E7,}|{2.0E7,}|2004-03-04|CITI|{}|{}|false|null|{TIMESTAMP~20030506 10:30:00:000,PRC_SUPPORT_TYPE~2,TIMEZONE~GMT,}|{RateRec
//        eivedByAdapter~1176470550085,RateEffective~1052197200000,}|0|*/
//
//        int idx1 = 0;
//        int idx2 = 0;
//        int idx3 = 0;
//        int idx4 = 0;
//        int idx5 = 0;
//        int idx6 = 0;
//        int start = 0;
//        int end = 0;
//
//        //String providerName = str.substring(mainDelimIndexPoints[12] + 1, mainDelimIndexPoints[13]);
//
//
//        int bidTierSize = Integer.parseInt(str.substring(0, mainDelimIndexPoints[0]));
//        int offerTierSize = Integer.parseInt(str.substring(mainDelimIndexPoints[0] + 1, mainDelimIndexPoints[1]));
//
//
//        MarketRateC rate = null;
//        int maxTierSize = Math.max(bidTierSize, offerTierSize);
//        // handle empty rates.
//        maxTierSize = maxTierSize == 0 ? 1 : maxTierSize;
//
//
//        rate = new MarketRateC(maxTierSize, maxTierSize);
//
//
//        //This code is used to balance the tiers.
//        //not required as MarketRate supports unbalanced tiers.
//        /*if (bidTierSize < maxTierSize) {
//            for (int i = 0; i < (maxTierSize - bidTierSize); ++i) {
//                rate.setBidLimit(0, bidTierSize + i);
//                rate.setBidRate(0, bidTierSize + i);
//            }
//        } else if (offerTierSize < maxTierSize) {
//            for (int i = 0; i < (maxTierSize - offerTierSize); ++i) {
//                rate.setOfferLimit(0, offerTierSize + i);
//                rate.setOfferRate(0, offerTierSize + i);
//            }
//        }*/
//
//        //Quote ID
//        rate.setQuoteId(str.substring(mainDelimIndexPoints[1] + 1, mainDelimIndexPoints[2]));
//
//        // providerQuoteIDs  this str is between {,}
//        start = mainDelimIndexPoints[2] + 2;
//        end = mainDelimIndexPoints[3] - 1;
//        int[] providerIdPositions = new int[maxTierSize];
//        providerIdPositions = populateDelimiterPoints(str, providerIdPositions, CHARARRAY_COMMA, start, end);
//        idx1 = start;
//        for (int i = 0, tier = 0; i < providerIdPositions.length; i++, tier++) {
//            idx2 = providerIdPositions[i];
//            rate.setProviderQuoteId(str.substring(idx1, idx2), tier);
//            idx1 = idx2 + 1;
//        }
//
//        //StreamId
//        String streamID = str.substring(mainDelimIndexPoints[3] + 1, mainDelimIndexPoints[4]);
//        if (streamID.equalsIgnoreCase("null")) {
//            rate.setStreamId(null);
//        } else {
//            rate.setStreamId(streamID);
//        }
//
//        rate.setBaseCcy(str.substring(mainDelimIndexPoints[4] + 1, mainDelimIndexPoints[5]));
//        rate.setVarCcy(str.substring(mainDelimIndexPoints[5] + 1, mainDelimIndexPoints[6]));
//        rate.setLimitCcy(str.substring(mainDelimIndexPoints[6] + 1, mainDelimIndexPoints[7]));
//
//        // bid rates this str is between {,}
//        start = mainDelimIndexPoints[7] + 2;
//        end = mainDelimIndexPoints[8] - 1;
//        int[] bidRatePositions = new int[bidTierSize];
//        bidRatePositions = populateDelimiterPoints(str, bidRatePositions, CHARARRAY_COMMA, start, end);
//        idx3 = start;
//        for (int i = 0, tier = 0; i < bidRatePositions.length; i++, tier++) {
//            idx4 = bidRatePositions[i];
//            rate.setBidRate(Double.parseDouble(str.substring(idx3, idx4)), tier);
//            idx3 = idx4 + 1;
//        }
//
//        // offer rate this str is between {,}
//        start = mainDelimIndexPoints[8] + 2;
//        end = mainDelimIndexPoints[9] - 1;
//        int[] offerRatePositions = new int[offerTierSize];
//        offerRatePositions = populateDelimiterPoints(str, offerRatePositions, CHARARRAY_COMMA, start, end);
//        idx3 = start;
//        for (int i = 0, tier = 0; i < offerRatePositions.length; i++, tier++) {
//            idx4 = offerRatePositions[i];
//            rate.setOfferRate(Double.parseDouble(str.substring(idx3, idx4)), tier);
//            idx3 = idx4 + 1;
//        }
//
//        // bid limits this str is between {,}
//        start = mainDelimIndexPoints[9] + 2;
//        end = mainDelimIndexPoints[10] - 1;
//        int[] bidLimitPositions = new int[bidTierSize];
//        bidLimitPositions = populateDelimiterPoints(str, bidLimitPositions, CHARARRAY_COMMA, start, end);
//        idx3 = start;
//
//        double tierLimit = 0;
//        for (int i = 0, tier = 0; i < bidLimitPositions.length; i++, tier++) {
//            idx4 = bidLimitPositions[i];
//            tierLimit = Double.parseDouble(str.substring(idx3, idx4));
//            rate.setBidLimit((long) tierLimit, tier);
//            idx3 = idx4 + 1;
//        }
//
//        // offer limits this str is between {,}
//        start = mainDelimIndexPoints[10] + 2;
//        end = mainDelimIndexPoints[11] - 1;
//        int[] offerLimitPositions = new int[offerTierSize];
//        offerLimitPositions = populateDelimiterPoints(str, offerLimitPositions, CHARARRAY_COMMA, start, end);
//        idx3 = start;
//        for (int i = 0, tier = 0; i < offerLimitPositions.length; i++, tier++) {
//            idx4 = offerLimitPositions[i];
//            tierLimit = Double.parseDouble(str.substring(idx3, idx4));
//            rate.setOfferLimit((long) tierLimit, tier);
//            idx3 = idx4 + 1;
//        }
//
//        try {
//            String dateStr = str.substring(mainDelimIndexPoints[11] + 1, mainDelimIndexPoints[12]);
//            //rate.setFormattedValueDate(dateStr);
//            if (dateStr != null && !dateStr.trim().equals("") && !dateStr.trim().equalsIgnoreCase("null")) {
//                rate.setValueDate(getValueDateFromFormattedString(dateStr));
//            } else {
//                rate.setValueDate(null);
//            }
//        }
//        catch (Exception ex) {
////            rate.setValueDate(new Date());
//        }
//
//        rate.setProviderShortName(providerShortName);
//
//        rate.setStale(Boolean.valueOf(str.substring(mainDelimIndexPoints[15] + 1, mainDelimIndexPoints[16])).booleanValue());
//        rate.setServerId(str.substring(mainDelimIndexPoints[16] + 1, mainDelimIndexPoints[17]));
//
//        //start
//        start = mainDelimIndexPoints[17] + 2;
//        end = mainDelimIndexPoints[18] - 1;
//        //tempStr = str.substring(mainDelimIndexPoints[17] + 2, mainDelimIndexPoints[18]-1);
//        int[] paramatersPositions = new int[20];
//        paramatersPositions = populateDelimiterPoints(str, paramatersPositions, CHARARRAY_COMMA, start, end);
//        //res~1234,rtef~23445
//        idx1 = start;
//        for (int i = 0; i < paramatersPositions.length && paramatersPositions[i] != 0; i++) {
//            idx4 = paramatersPositions[i];
//            idx3 = str.indexOf(CHARARRAY_TILDE, idx1);
//            //TODO: Inder: Validate we are not setting and using properties.
////            rate.setProperty(str.substring(idx1, idx3), str.substring(idx3 + 1, idx4));
//            idx1 = idx4 + 1;
//        }
//        //end
//
//        start = mainDelimIndexPoints[18] + 2;
//        end = mainDelimIndexPoints[19] - 1;
//        //tempStr = str.substring(mainDelimIndexPoints[18] + 2, mainDelimIndexPoints[19]-1);
//        Timing timings = new TimingC();
//        //res~1234,rtef~23445
//        int[] timingsPositions = new int[20];
//        timingsPositions = populateDelimiterPoints(str, timingsPositions, CHARARRAY_COMMA, start, end);
//        idx1 = start;
//        for (int i = 0; i < timingsPositions.length && timingsPositions[i] != 0; i++) {
//            idx4 = timingsPositions[i];
//            idx3 = str.indexOf(CHARARRAY_TILDE, idx1);
//            timings.setTime(str.substring(idx1, idx3), Long.parseLong(str.substring(idx3 + 1, idx4)));
//            idx1 = idx4 + 1;
//        }
//        rate.setTiming(timings);
//
//
//        if (mainDelimIndexPoints[20] != 0 && (!"-1".equalsIgnoreCase(str.substring(mainDelimIndexPoints[19] + 1, mainDelimIndexPoints[20])))) {
//            rate.setPriceType(Integer.parseInt(str.substring(mainDelimIndexPoints[19] + 1, mainDelimIndexPoints[20])));
//        } else {
//            rate.setPriceType(Quote.PRICE_TYPE_QUOTES);
//        }
//
//        //Bid effective Rates
//        if (mainDelimIndexPoints[21] != 0 && (!"-1".equalsIgnoreCase(str.substring(mainDelimIndexPoints[20] + 1, mainDelimIndexPoints[21])))) {
//            String bidEffectiveRates = str.substring(mainDelimIndexPoints[20] + 1, mainDelimIndexPoints[21]) ;
//            //Remvoe { } s
//            String bidPartialString = bidEffectiveRates.substring( 1, bidEffectiveRates.length()-1 );
//            // Number of bid effective rates will be same as bid size
//            int[] bidEffectiveRatePositions  = new int [bidRatePositions.length];
//            int[] effectiveTimesPositions = populateDelimiterPoints(bidPartialString, bidEffectiveRatePositions , CHARARRAY_COMMA, 0, bidPartialString.length() );
//            int beginIdx = 0, endIdx=0;
//            for (int i = 0; i < effectiveTimesPositions.length; i++ ) {
//                endIdx = bidEffectiveRatePositions[i];
//                long effectiveRate = Long.parseLong(bidPartialString.substring(beginIdx, endIdx));
//                rate.getBidTier(i).setTimeEffective( effectiveRate );
//                beginIdx = endIdx + 1;
//            }
//        }
//        //Offer effective rates
//        if (mainDelimIndexPoints[22] != 0 && (!"-1".equalsIgnoreCase(str.substring(mainDelimIndexPoints[21] + 1, mainDelimIndexPoints[22])))) {
//            String offerEffectiveRates = str.substring(mainDelimIndexPoints[21] + 1, mainDelimIndexPoints[22]) ;
//            //Remvoe { } s
//            String offerPartialString = offerEffectiveRates.substring( 1, offerEffectiveRates.length()-1 );
//            // Number of offer effective rates will be same as offer size
//            int[] offerEffectiveRatePositions  = new int [offerRatePositions.length];
//            int[] effectiveTimesPositions = populateDelimiterPoints(offerPartialString, offerEffectiveRatePositions , CHARARRAY_COMMA, 0, offerPartialString.length() );
//            int beginIdx = 0, endIdx=0;
//            for (int i = 0; i < effectiveTimesPositions.length; i++ ) {
//                endIdx = offerEffectiveRatePositions[i];
//                long effectiveRate = Long.parseLong(offerPartialString.substring(beginIdx, endIdx));
//                rate.getOfferTier(i).setTimeEffective( effectiveRate );
//                beginIdx = endIdx + 1;
//            }
//        }
//        return rate;
//    }
//
//    private static int[] populateDelimiterPoints(String str, int[] delimiterCodePoints, char delimiter, int start, int end) {
//        for (int i = start, j = 0; i < end; i++) {
//            if (str.charAt(i) == delimiter) {
//                delimiterCodePoints[j] = i;
//                j++;
//            }
//        }
//        return delimiterCodePoints;
//    }
//
//    public static int[] getMainDelimeterIndexArray(String str) {
//        int[] mainDelimIndexPoints = new int[25];
//        mainDelimIndexPoints = populateDelimiterPoints(str, mainDelimIndexPoints, CHARARRAY_PIPE, 0, str.length());
//        return mainDelimIndexPoints;
//    }
//
//    public boolean serialize(MarketRate rate, int orgIndex, int streamIndex, ByteBuffer out) {
//        return false;
//    }
//
//    public int getProviderIndex(byte[] packet, int startOffset) {
//        return 0;
//    }
//
//    public int getStreamIndex(byte[] packet, int startOffset) {
//        return 0;
//    }
//
//    public int getBaseCcyIndex(byte[] packet, int startOffset) {
//        return 0;
//    }
//
//    public int getVarCcyIndex(byte[] packet, int startOffset) {
//        return 0;
//    }
//
//    public long createStreamingKey(byte[] packet, int startOffset) {
//        return 0;
//    }
//
//
//    public IdcDate getValueDateFromFormattedString(String formattedDate) {
//        if(formattedDate==null) {
//            return null;
//        }
//
//        IdcDate valueDate = dateFormatMap.get(formattedDate);
//        if ( valueDate == null ) {
//            try {
//                synchronized(sdf)
//                {
//                	valueDate = DateTimeFactory.newDate(sdf.parse(formattedDate));
//                }
//                if (log.isDebugEnabled())
//                {
//                    log.debug("Formatted DATE "+formattedDate );
//                    log.debug("Value DAte DATE "+valueDate );
//                }
//                dateFormatMap.put(formattedDate,valueDate);
//            } catch (Exception e) {
//                return null;
//            }
//        }
//        return valueDate;
//    }
//
//    public String getFormattedStringForValueDate(IdcDate valueDate) {
//        if (valueDate==null) {
//            return null;
//        }
//        String formattedString = formattedStringMap.get(valueDate);
//        if ( formattedString == null ) {
//                synchronized(sdf)
//                {
//                	formattedString = sdf.format(valueDate.asJdkDate());
//                }
//                formattedStringMap.put(valueDate,formattedString);
//        }
//        return formattedString;
//    }
//}