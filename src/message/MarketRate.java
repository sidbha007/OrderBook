//// Copyright (c) 2005 Integral Development Corp.  All rights reserved.
//package message;
//
//import com.integral.finance.trade.Tenor;
//import com.integral.is.message.*;
//import com.integral.time.IdcDate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Represents a MarketRate that is streaming through the system.
// *
// * @author Integral Development Corp.
// */
//public interface MarketRate /*extends ISMessage*/
//{
//
//    public static final int PRICE_TYPE_MULTI_PRICE = 0;
//    public static final int PRICE_TYPE_MULTI_TIER = 1;
//    public static final int PRICE_TYPE_ORDERS = 2;
//
//
//    /**
//     * Quote set by adaptor
//     *
//     * @return
//     */
//    String getQuoteId();
//
//    /**
//     * Get the long integer representation of quoteId.
//     *
//     * @return
//     */
//    long getQuoteIdAsLong();
//
//    /**
//     * Is this rate an aggregated from different providers. This also implies that each of the side of the rates in
//     * all the tiers will have a providerIndex, providerQuoteIds populated.
//     *
//     * @return
//     */
//    boolean isAggregated();
//
//    boolean isIncremental();
//
//    /**
//     * QuoteId assigned  by the provider to this rate.
//     *
//     * @return
//     */
//    @Deprecated
//    String getProviderQuoteId();
//
//    /**
//     * Get the stream that this message is for.
//     *
//     * @return
//     */
//    String getStreamId();
//
//    int getStreamIndex();
//
//    /**
//     * returns base currency
//     *
//     * @return
//     */
//    String getBaseCcy();
//
//
//    /**
//     * returns variable currency
//     *
//     * @return
//     */
//    String getVariableCcy();
//
//    /**
//     * Limit currency is the monetary unit for the bid limit and offer limit amounts.
//     *
//     * @return limit currency
//     */
//    String getLimitCcy();
//
//    /**
//     * get bid rate
//     *
//     * @return
//     */
//    double getBidRate();
//
//    /**
//     * get Bid rate for tier
//     *
//     * @param tier
//     * @return
//     */
//    double getBidRate(int tier);
//
//    /**
//     * get offer rate
//     *
//     * @return
//     */
//    double getOfferRate();
//
//    /**
//     * Get offer rate for tier
//     *
//     * @param tier
//     * @return
//     */
//    double getOfferRate(int tier);
//
//
//	public double getBidForwardPoints();
//
//	public double getBidForwardPoints(int tier);
//
//
//	public double getOfferForwardPoints();
//
//	public double getOfferForwardPoints(int tier);
//
//
//	public double getBidSpotRate();
//
//	public double getBidSpotRate(int tier);
//
//
//	public double getOfferSpotRate();
//
//
//	public double getOfferSpotRate(int tier);
//
//
//	public IdcDate getBidFixingDate();
//
//
//    public IdcDate getBidFixingDate(int tier);
//
//
//	public IdcDate getOfferFixingDate();
//
//
//    public IdcDate getOfferFixingDate(int tier);
//
//    /**
//     * get bid limit
//     *
//     * @return
//     */
//    double getBidLimit();
//
//    /**
//     * get bidlimit for tier
//     *
//     * @param tier
//     * @return
//     */
//    double getBidLimit(int tier);
//
//    /**
//     * get max bid limit
//     *
//     * @return
//     */
//    double getMaxBidLimit();
//
//    /**
//     * get offer limit
//     *
//     * @return
//     */
//    double getOfferLimit();
//
//    /**
//     * get offer limit for tier
//     *
//     * @param tier
//     * @return
//     */
//    double getOfferLimit(int tier);
//
//    /**
//     * get max offer limit
//     *
//     * @return
//     */
//    double getMaxOfferLimit();
//
//
//    /**
//     * get Value Date
//     *
//     * @return
//     */
//    IdcDate getValueDate();
//
//    void setValueDate(IdcDate valueDate);
//
//    /**
//     * @return
//     */
//    String getProviderShortName();
//
//    int getProviderIndex();
//
//    /**
//     * Check whether rate is stale. Stale means that the MarketRate is not executable.
//     * For example, indicative rates are marked as stale.
//     * This field does NOT mean that a previous MarketRate is invalid.
//     *
//     * @return
//     */
//    boolean isStale();
//
//    void setStale(boolean value);
//
//    /**
//     * @return
//     */
//    int getBidTierSize();
//
//    /**
//     * @return
//     */
//    int getOfferTierSize();
//
//    /*
//    * use getProviderQuoteId(tier, bidOffer)
//    */
//
//    String getProviderQuoteId(int tier);
//
//    /**
//     * @param tier
//     * @param bidOffer - one of {@link com.integral.finance.dealing.DealingPrice#BID} or {@link com.integral.finance.dealing.DealingPrice#OFFER}
//     * @return
//     */
//    String getProviderQuoteId(int tier, int bidOffer);
//
//    /**
//     * returns value date in yyyy-MM-dd format.
//     *
//     * @return
//     */
//    String getFormattedValueDate();
//
//    /**
//     * <li> 0 = {@link #PRICE_TYPE_MULTI_PRICE multiPrice}
//     * <li> 1 = {@link #PRICE_TYPE_MULTI_TIER multiTier}
//     * <li> 2 = {@link #PRICE_TYPE_ORDERS orders}
//     *
//     * @return
//     */
//    int getPriceType();
//
//    void setPriceType(int priceType);
//
//    boolean isBidSortingRequired();
//
//    boolean isOfferSortingRequired();
//
//    void sort();
//
//    // Operations on Tiers.
//
//    int getNumTiers();
//
//    /**
//     * Get the tier at the specified 0-based index.
//     *
//     * @param tierIndex
//     * @return
//     */
//    MarketRateTier getTier(int tierIndex);
//
//    /**
//     * Get all tiers.
//     *
//     * @return
//     */
//    List<MarketRateTier> getAllTiers();
//
//    // Operations on Side Rates.
//
//    /**
//     * Get Base Currency Side Rates for the first (index 0) tier.
//     *
//     * @return
//     */
//    MarketRateSideRate getBaseCurrencySideRate();
//
//    /**
//     * Get the side base for the specified index.
//     *
//     * @param tier
//     * @return
//     */
//    MarketRateSideRate getBaseCurrencySideRate(int tier);
//
//    /**
//     * Get varaible currency side rates
//     *
//     * @return
//     */
//    MarketRateSideRate getVariableCurrencySideRate();
//
//    /**
//     * @param tier
//     * @return
//     */
//    MarketRateSideRate getVariableCurrencySideRate(int tier);
//
//    message.MarketPrice getBidTier(int tierIndex);
//
//    int getBaseCcyIndex();
//
//    int getVarCcyIndex();
//
//    int getLimitCcyIndex();
//
//    int getNumBidTiers();
//
//    int getNumOfferTiers();
//
//    message.MarketPrice getOfferTier(int tierIndex);
//
//    void setProviderShortName(String providerShortName);
//
//    void setQuoteId(String quoteId);
//
//    void setQuoteId(long quoteId);
//
//    void setStreamId(String streamId);
//
//    void setProviderIndex(int providerIndex);
//
//    void setStreamIndex(int streamIndex);
//
//    void setBaseCcyIndex(int baseCcyIndex);
//
//    void setVarCcyIndex(int varCcyIndex);
//
//    void setLimitCcyIndex(int limitCcyIndex);
//
//    void setBaseCcy(String baseCcy);
//
//    void setVarCcy(String varCcy);
//
//    void setLimitCcy(String limitCcy);
//
//    void setAggregated(boolean aggregated);
//
//    void setIncremental(boolean incremental);
//
//    void setTenor(Tenor tenor);
//
//    void addBidPrice(message.MarketPrice price);
//
//    void setBidPrices(ArrayList<message.MarketPrice> bidPrices);
//
//    void setOfferPrices(ArrayList<message.MarketPrice> offerPrices);
//
//    void addOfferPrice(message.MarketPrice price);
//
//    void setProviderQuoteId(String quoteId, int tierIndex);
//
//    message.MarketPriceC getOrCreateBidPrice(int tierIndex);
//
//    message.MarketPriceC getOrCreateOfferPrice(int tierIndex);
//
//    boolean setBidRate(double rate, int tierIndex);
//
//    boolean setOfferRate(double rate, int tierIndex);
//
//	public boolean setOutrightBidFields(double spotRate, double fwdPoints, IdcDate fixingDate, int tierIndex);
//
//    public boolean setOutrightOfferFields(double spotRate, double fwdPoints, IdcDate fixingDate, int tierIndex);
//
//    boolean setBidLimit(long limit, int tierIndex);
//
//    boolean setOfferLimit(long limit, int tierIndex);
//
//    long getRateReceivedByAdapter();
//
//    long getRateSentByAdapter();
//
//    long getRateEffective();
//
//    void setRateReceivedByAdapter(long rateRcvdByAdapter);
//
//    void setRateSentByAdapter(long rateSentByAdapter);
//
//    void setRateEffective(long rateEffectiveAtProvider);
//
//    void setTiming(Timing timings);
//
//    String getBookname();
//
//    void setBookname(String bookname);
//
//    void setPool(MarketRatePoolFactory pool);
//
//    boolean isPoolable();
//
//	public void incReference();
//
//	public int decReference();
//
//	public void setBidPrice(int index, message.MarketPrice price);
//
//	public void setOfferPrice(int index, message.MarketPrice price);
//
//	/**
//	 * Extracted SequenceNo from quoteId ( guid )
//	 * @return
//	 */
//	public String getSequenceNoAsString();
//
//	/**
//	 * Extracted SequenceNo from quoteId ( guid )
//	 * @return
//	 */
//	public long getSequenceNo();
//
//	/**
//	 *  Returns true if it is a Outright Resting Order
//	 * @return
//	 */
//	public boolean isOutrightDisplayOrder();
//
//	public void setIsOutrightDisplayOrder(boolean isOutrightDisplayOrder);
//
//    public Tenor getTenor();
//
//}
