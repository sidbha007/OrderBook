//package message;
//
//import com.integral.finance.dealing.DealingPrice;
//import com.integral.finance.dealing.Quote;
//import com.integral.finance.trade.Tenor;
//import com.integral.is.ISCommonConstants;
//import com.integral.is.message.MarketPrice;
//import com.integral.is.message.*;
//import message.MarketRate;
//import com.integral.time.DateTimeFactory;
//import com.integral.time.IdcDate;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Implements MarketRate interface. Represents Streaming SPOT Price only.
// */
//public class MarketRateC /* extends ISMessageC */implements MarketRate
//{
//
//	String providerShortName;
//	int providerIndex;
//
//	String quoteId;
//	// QuoteId represented as a long integer, if available.
//	long longQuoteId = -1;
//
//	String streamId;
//	int streamIndex = 0;
//
//	String baseCcy;
//	int baseCcyIndex = -1;
//
//	String varCcy;
//	int varCcyIndex = -1;
//
//	String limitCcy;
//	int limitCcyIndex = -1;
//
//	IdcDate valueDate = null;
//	String formattedValueDate = null;
//
//	int priceType = 0;
//
//	// Flags
//	boolean aggregated;
//	boolean incremental;
//	boolean stale;
//
//	Tenor tenor;
//
//	long maxBidLimit = 0;
//	long maxOfferLimit = 0;
//
//	// Tiers
//	ArrayList<MarketPrice> bidPrices = new ArrayList<MarketPrice>();
//	ArrayList<MarketPrice> offerPrices = new ArrayList<MarketPrice>();
//
//	String stringRepresentation;
//	boolean isDirty = true;
//
//	boolean bidSortingRequired = false;
//	boolean offerSortingRequired = false;
//
//	long rateRcvdByAdapter = 0;
//	long rateSentByAdapter = 0;
//	long rateEffectiveAtProvider = 0;
//
//	String serverId;
//	String bookname;
//
//	// indicates whether its coming from object pool or not
//	boolean poolable;
//	boolean recycled;
//
//	// Below attributes are derived from guid. Its sequence no from adaptor.
//	String sequenceNoAsString;
//	long sequenceNo;
//
//	// Reference to the object pool. This object should be set before using the Object.
//	MarketRatePoolFactory poolFactory;
//
//	// Used if this object is poolable
//	AtomicInteger refCount = new AtomicInteger();
//
//	//Indicates whether the rate is Outright display order
//	boolean isOutrightDisplayOrder = false;
//
//
//
//	public MarketRateC()
//	{
//		this( 1, 1 );
//	}
//
//	public MarketRateC(int maxBidTiers, int maxOfferTiers)
//	{
//		bidPrices = new ArrayList<MarketPrice>();
//		ensureTierCapacity( maxBidTiers, bidPrices );
//		offerPrices = new ArrayList<MarketPrice>();
//		ensureTierCapacity( maxOfferTiers, offerPrices );
//	}
//
//	public String getQuoteId()
//	{
//		return quoteId;
//	}
//
//	public long getQuoteIdAsLong()
//	{
//		return longQuoteId;
//	}
//
//	public boolean isAggregated()
//	{
//		return aggregated;
//	}
//
//	public boolean isIncremental()
//	{
//		return incremental;
//	}
//
//	public String getStreamId()
//	{
//		return streamId;
//	}
//
//	public int getStreamIndex()
//	{
//		return streamIndex;
//	}
//
//	public int getBaseCcyIndex()
//	{
//		return baseCcyIndex;
//	}
//
//	public int getVarCcyIndex()
//	{
//		return varCcyIndex;
//	}
//
//	public int getLimitCcyIndex()
//	{
//		return limitCcyIndex;
//	}
//
//	public int getProviderIndex()
//	{
//		return providerIndex;
//	}
//
//	public String getBaseCcy()
//	{
//		return baseCcy;
//	}
//
//	public String getVariableCcy()
//	{
//		return varCcy;
//	}
//
//	public String getLimitCcy()
//	{
//		return limitCcy;
//	}
//
//	public double getBidRate()
//	{
//		return getBidRate( 0 );
//	}
//
//	public double getBidRate( int tier )
//	{
//		bidRangeCheck( tier );
//
//		MarketPrice price = bidPrices.get( tier );
//		return price.getRate();
//
//	}
//
//	public double getOfferRate()
//	{
//		return getOfferRate( 0 );
//	}
//
//	public double getOfferRate( int tier )
//	{
//		offerRangeCheck( tier );
//
//		MarketPrice price = offerPrices.get( tier );
//		return price.getRate();
//	}
//
//	public double getBidSpotRate()
//	{
//		return getBidSpotRate( 0 );
//	}
//
//	public double getBidSpotRate( int tier )
//	{
//		bidRangeCheck( tier );
//
//		MarketPrice price = bidPrices.get( tier );
//		return price.getSpotRate();
//
//	}
//
//	public double getOfferSpotRate()
//	{
//		return getOfferSpotRate( 0 );
//	}
//
//	public double getOfferSpotRate( int tier )
//	{
//		offerRangeCheck( tier );
//
//		MarketPrice price = offerPrices.get( tier );
//		return price.getSpotRate();
//	}
//
//	public double getBidForwardPoints()
//	{
//		return getBidForwardPoints( 0 );
//	}
//
//	public double getBidForwardPoints( int tier )
//	{
//		bidRangeCheck( tier );
//
//		MarketPrice price = bidPrices.get( tier );
//		return price.getForwardPoints();
//
//	}
//
//	public double getOfferForwardPoints()
//	{
//		return getOfferForwardPoints( 0 );
//	}
//
//	public double getOfferForwardPoints( int tier )
//	{
//		offerRangeCheck( tier );
//
//		MarketPrice price = offerPrices.get( tier );
//		return price.getForwardPoints();
//	}
//
//	public IdcDate getBidFixingDate()
//	{
//		return getBidFixingDate( 0 );
//	}
//
//	public IdcDate getBidFixingDate( int tier )
//	{
//		bidRangeCheck( tier );
//
//		MarketPrice price = bidPrices.get( tier );
//		return price.getFixingDate();
//
//	}
//
//	public IdcDate getOfferFixingDate()
//	{
//		return getOfferFixingDate( 0 );
//	}
//
//	public IdcDate getOfferFixingDate( int tier )
//	{
//		offerRangeCheck( tier );
//
//		MarketPrice price = offerPrices.get( tier );
//		return price.getFixingDate();
//	}
//
//	public double getBidLimit()
//	{
//		return getBidLimit( 0 );
//	}
//
//	public double getBidLimit( int tier )
//	{
//		bidRangeCheck( tier );
//
//		MarketPrice price = bidPrices.get( tier );
//		return price.getLimit();
//
//	}
//
//	public double getMaxBidLimit()
//	{
//		return maxBidLimit;
//	}
//
//	public double getOfferLimit()
//	{
//		return getOfferLimit( 0 );
//	}
//
//	public double getOfferLimit( int tier )
//	{
//		offerRangeCheck( tier );
//
//		MarketPrice price = offerPrices.get( tier );
//		return price.getLimit();
//	}
//
//	public double getMaxOfferLimit()
//	{
//		return maxOfferLimit;
//	}
//
//	public IdcDate getValueDate()
//	{
//		return valueDate;
//	}
//
//	public String getProviderShortName()
//	{
//		return providerShortName;
//	}
//
//	public boolean isStale()
//	{
//		return stale;
//	}
//
//	public int getBidTierSize()
//	{
//		return bidPrices.size();
//	}
//
//	public int getOfferTierSize()
//	{
//		return offerPrices.size();
//	}
//
//	public String getFormattedValueDate()
//	{
//		// TODO: this is inefficient - the formatter should be moved to the
//		// Serializer, and reused.
//		if ( formattedValueDate == null && valueDate != null )
//		{
//			SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
//			formattedValueDate = sdf.format( valueDate.asJdkDate() );
//		}
//
//		return formattedValueDate;
//	}
//
//	public int getPriceType()
//	{
//		return priceType;
//	}
//
//	public boolean isBidSortingRequired()
//	{
//		return bidSortingRequired;
//	}
//
//	public boolean isOfferSortingRequired()
//	{
//		return offerSortingRequired;
//	}
//
//	public void sort()
//	{
//		if ( this.getPriceType() == Quote.PRICE_TYPE_MULTI_TIER )
//		{
//			return;
//		}
//
//		if ( bidSortingRequired || offerSortingRequired )
//		{
//			isDirty = true;
//		}
//		if ( this.isBidSortingRequired() )
//		{
//			Collections.sort( bidPrices, new Desc() );
//			this.bidSortingRequired = false;
//		}
//		if ( this.isOfferSortingRequired() )
//		{
//			Collections.sort( offerPrices, new Asc() );
//			this.offerSortingRequired = false;
//		}
//	}
//
//	private static class Asc implements Comparator<MarketPrice>
//	{
//		public int compare( MarketPrice o1, MarketPrice o2 )
//		{
//			/*
//			 * broker adaptor appends zero rate tier's on offer side to match
//			 * the no. of bid tiers. Hence tier with zero rate should be the
//			 * last.
//			 */
//			if ( o1.getRate() == 0.0 )
//			{
//				return 1;
//			}
//			else if ( o2.getRate() == 0.0 )
//			{
//				return -1;
//			}
//			else
//			{
//				return Double.compare( o1.getRate(), o2.getRate() );
//			}
//		}
//	}
//
//	private static class Desc implements Comparator<MarketPrice>
//	{
//		public int compare( MarketPrice o1, MarketPrice o2 )
//		{
//			return Double.compare( o1.getRate(), o2.getRate() ) * -1;
//		}
//	}
//
//	public int getNumTiers()
//	{
//		return bidPrices.size() > offerPrices.size() ? bidPrices.size() : offerPrices.size();
//	}
//
//	public int getNumBidTiers()
//	{
//		return bidPrices.size();
//	}
//
//	public int getNumOfferTiers()
//	{
//		return offerPrices.size();
//	}
//
//	public MarketRateTier getTier( int tierIndex )
//	{
//		MarketRateTierC tier = new MarketRateTierC();
//		if ( tierIndex < bidPrices.size() )
//		{
//			tier.setBid( bidPrices.get( tierIndex ) );
//		}
//		if ( tierIndex < offerPrices.size() )
//		{
//			tier.setOffer( offerPrices.get( tierIndex ) );
//		}
//		return tier;
//	}
//
//
//
//	public List<MarketRateTier> getAllTiers()
//	{
//		return null;
//	}
//
//	public MarketRateSideRate getBaseCurrencySideRate()
//	{
//		return getBaseCurrencySideRate( 0 );
//	}
//
//	public MarketRateSideRate getBaseCurrencySideRate( int tier )
//	{
//		bidRangeCheck( tier );
//		MarketPrice price = bidPrices.get( tier );
//		return price.getSideRate();
//	}
//
//	public MarketRateSideRate getVariableCurrencySideRate()
//	{
//		return getVariableCurrencySideRate( 0 );
//	}
//
//	public MarketRateSideRate getVariableCurrencySideRate( int tier )
//	{
//		offerRangeCheck( tier );
//		MarketPrice price = offerPrices.get( tier );
//		return price.getSideRate();
//	}
//
//	private void bidRangeCheck( int index )
//	{
//		if ( index >= bidPrices.size() )
//		{
//			throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + bidPrices.size() );
//		}
//	}
//
//	private void offerRangeCheck( int index )
//	{
//		if ( index >= offerPrices.size() )
//		{
//			throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + offerPrices.size() );
//		}
//	}
//
//	// Setters
//
//	public void setProviderShortName( String providerShortName )
//	{
//		this.providerShortName = providerShortName;
//	}
//
//	public void setQuoteId( String quoteId )
//	{
//		this.quoteId = quoteId;
//		setSequenceNo( quoteId );
//	}
//
//	public void setQuoteId( long quoteId )
//	{
//		this.longQuoteId = quoteId;
//		this.quoteId = Long.toString( quoteId );
//	}
//
//	public void setStreamId( String streamId )
//	{
//		this.streamId = streamId;
//	}
//
//	public void setProviderIndex( int providerIndex )
//	{
//		this.providerIndex = providerIndex;
//	}
//
//	public void setStreamIndex( int streamIndex )
//	{
//		this.streamIndex = streamIndex;
//	}
//
//	public void setBaseCcyIndex( int baseCcyIndex )
//	{
//		this.baseCcyIndex = baseCcyIndex;
//	}
//
//	public void setVarCcyIndex( int varCcyIndex )
//	{
//		this.varCcyIndex = varCcyIndex;
//	}
//
//	public void setLimitCcyIndex( int limitCcyIndex )
//	{
//		this.limitCcyIndex = limitCcyIndex;
//	}
//
//	public void setBaseCcy( String baseCcy )
//	{
//		this.baseCcy = baseCcy;
//	}
//
//	public void setVarCcy( String varCcy )
//	{
//		this.varCcy = varCcy;
//	}
//
//	public void setLimitCcy( String limitCcy )
//	{
//		this.limitCcy = limitCcy;
//	}
//
//	public void setValueDate( IdcDate valueDate )
//	{
//		this.valueDate = valueDate;
//		this.formattedValueDate = null;
//	}
//
//	public void setPriceType( int priceType )
//	{
//		this.priceType = priceType;
//	}
//
//	public void setAggregated( boolean aggregated )
//	{
//		this.aggregated = aggregated;
//	}
//
//	public void setIncremental( boolean incremental )
//	{
//		this.incremental = incremental;
//	}
//
//	public void setStale( boolean stale )
//	{
//		this.stale = stale;
//	}
//
//	public void setTenor( Tenor tenor )
//	{
//		this.tenor = tenor;
//	}
//
//	public void addBidPrice( MarketPrice price )
//	{
//		this.bidPrices.add( price );
//		if ( price.getLimit() > maxBidLimit )
//		{
//			maxBidLimit = price.getLimit();
//		}
//		int size = bidPrices.size();
//		if ( size > 1 )
//		{
//			MarketPrice prevPrice = bidPrices.get( size - 2 );
//			if ( prevPrice != null && price.getRate() > prevPrice.getRate() )
//			{
//				bidSortingRequired = true;
//			}
//		}
//	}
//
//	public void setBidPrices( ArrayList<MarketPrice> bidPrices )
//	{
//		this.bidPrices.clear();
//		maxBidLimit = 0;
//		MarketPrice prevPrice = null;
//		for ( Iterator<MarketPrice> iterator = bidPrices.iterator(); iterator.hasNext(); )
//		{
//			MarketPrice price = iterator.next();
//			this.bidPrices.add( price );
//			if ( price.getLimit() > maxBidLimit )
//			{
//				maxBidLimit = price.getLimit();
//			}
//			if ( prevPrice != null && price.getRate() > prevPrice.getRate() )
//			{
//				bidSortingRequired = true;
//			}
//			prevPrice = price;
//		}
//	}
//
//	public void setOfferPrices( ArrayList<MarketPrice> offerPrices )
//	{
//		this.offerPrices.clear();
//		maxOfferLimit = 0;
//		MarketPrice prevPrice = null;
//		for ( Iterator<MarketPrice> iterator = offerPrices.iterator(); iterator.hasNext(); )
//		{
//			MarketPrice price = iterator.next();
//			this.offerPrices.add( price );
//			if ( price.getLimit() > maxOfferLimit )
//			{
//				maxOfferLimit = price.getLimit();
//			}
//			if ( prevPrice != null && price.getRate() < prevPrice.getRate() )
//			{
//				offerSortingRequired = true;
//			}
//			prevPrice = price;
//		}
//	}
//
//	public void addOfferPrice( MarketPrice price )
//	{
//		this.offerPrices.add( price );
//		if ( price.getLimit() > maxOfferLimit )
//		{
//			maxOfferLimit = price.getLimit();
//		}
//		int size = offerPrices.size();
//		if ( size > 1 )
//		{
//			MarketPrice prevPrice = offerPrices.get( size - 2 );
//			if ( prevPrice != null && price.getRate() < prevPrice.getRate() )
//			{
//				offerSortingRequired = true;
//			}
//		}
//	}
//
//	public void setBidPrice( int index, MarketPrice price )
//	{
//		this.bidPrices.set( index, price );
//		if ( price.getLimit() > maxBidLimit )
//		{
//			maxBidLimit = price.getLimit();
//		}
//		int size = bidPrices.size();
//		if ( size > 1 )
//		{
//			MarketPrice prevPrice = bidPrices.get( size - 2 );
//			if ( prevPrice != null && price.getRate() > prevPrice.getRate() )
//			{
//				bidSortingRequired = true;
//			}
//		}
//	}
//
//	public void setOfferPrice( int index, MarketPrice price )
//	{
//		this.offerPrices.set( index, price );
//		if ( price.getLimit() > maxOfferLimit )
//		{
//			maxOfferLimit = price.getLimit();
//		}
//		int size = offerPrices.size();
//		if ( size > 1 )
//		{
//			MarketPrice prevPrice = offerPrices.get( size - 2 );
//			if ( prevPrice != null && price.getRate() < prevPrice.getRate() )
//			{
//				offerSortingRequired = true;
//			}
//		}
//	}
//
//
//	// METHODS required for backward compatibility with first version of
//	// MarketRate deserialization.
//
//	/**
//	 * Set quoteId.
//	 *
//	 * @param quoteId
//	 * @param tierIndex
//	 */
//	public void setProviderQuoteId( String quoteId, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateBidPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setProviderQuoteId( quoteId );
//		}
//		price = getOrCreateOfferPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setProviderQuoteId( quoteId );
//		}
//	}
//
//	public String getProviderQuoteId( int tierIndex )
//	{
//		MarketPriceC price = getOrCreateBidPrice( tierIndex );
//		if ( price != null )
//		{
//			return price.getProviderQuoteId();
//		}
//		price = getOrCreateOfferPrice( tierIndex );
//		if ( price != null )
//		{
//			return price.getProviderQuoteId();
//		}
//		return null;
//	}
//
//	public String getProviderQuoteId( int tierIndex, int bidOffer )
//	{
//		MarketPriceC price = null;
//		if ( bidOffer == DealingPrice.BID )
//		{
//			price = getOrCreateBidPrice( tierIndex );
//		}
//		else if ( bidOffer == DealingPrice.OFFER )
//		{
//			price = getOrCreateOfferPrice( tierIndex );
//		}
//		if ( price != null )
//		{
//			return price.getProviderQuoteId();
//		}
//
//		return null;
//	}
//
//	/**
//	 * Return the quoteId of the first tier. If the tier
//	 * {@link com.integral.is.message.MarketRateTier#hasBid() has bid rate set}, then the quoteId from
//	 * the {@link com.integral.is.message.MarketRateTier#getBid() bid side} is return. If the tier has
//	 * offer rate set, then the quoteId from the
//	 * {@link com.integral.is.message.MarketRateTier#getOffer() offer side} is sent.
//	 *
//	 * @return
//	 */
//	public String getProviderQuoteId()
//	{
//		throw new UnsupportedOperationException( "Unsupported." );
//	}
//
//	public MarketPriceC getOrCreateBidPrice( int tierIndex )
//	{
//		if ( tierIndex < bidPrices.size() )
//		{
//			MarketPriceC price = (MarketPriceC) bidPrices.get( tierIndex );
//			if ( price == null )
//			{
//				price = new MarketPriceC();
//				bidPrices.set( tierIndex, price );
//			}
//			return price;
//		}
//		return null;
//	}
//
//	public MarketPriceC getOrCreateOfferPrice( int tierIndex )
//	{
//		if ( tierIndex < offerPrices.size() )
//		{
//			MarketPriceC price = (MarketPriceC) offerPrices.get( tierIndex );
//			if ( price == null )
//			{
//				price = new MarketPriceC();
//				offerPrices.set( tierIndex, price );
//			}
//			return price;
//		}
//		return null;
//	}
//
//	public boolean setBidRate( double rate, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateBidPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setRate( rate );
//			if ( tierIndex > 0 )
//			{
//				MarketPriceC prevPrice = getOrCreateBidPrice( tierIndex - 1 );
//				if ( prevPrice != null && rate > prevPrice.getRate() )
//				{
//					bidSortingRequired = true;
//				}
//			}
//			return true;
//		}
//		return false;
//	}
//
//	public boolean setOfferRate( double rate, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateOfferPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setRate( rate );
//			if ( tierIndex > 0 )
//			{
//				MarketPriceC prevPrice = getOrCreateOfferPrice( tierIndex - 1 );
//				if ( prevPrice != null && rate < prevPrice.getRate() )
//				{
//					offerSortingRequired = true;
//				}
//			}
//			return true;
//		}
//		return false;
//	}
//
//	public boolean setOutrightBidFields( double spotRate,double fwdPoints,IdcDate fixingDate, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateBidPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setSpotRate( spotRate );
//			price.setForwardPoints(fwdPoints);
//			price.setFixingDate(fixingDate);
//			return true;
//		}
//		return false;
//	}
//
//	public boolean setOutrightOfferFields(double spotRate,double fwdPoints,IdcDate fixingDate, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateOfferPrice(tierIndex);
//		if ( price != null )
//		{
//			price.setSpotRate(spotRate);
//			price.setForwardPoints(fwdPoints);
//			price.setFixingDate(fixingDate);
//			return true;
//		}
//		return false;
//	}
//	public boolean setBidLimit( long limit, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateBidPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setLimit( limit );
//			if ( limit > maxBidLimit )
//			{
//				maxBidLimit = limit;
//			}
//			return true;
//		}
//		return false;
//	}
//
//	public boolean setOfferLimit( long limit, int tierIndex )
//	{
//		MarketPriceC price = getOrCreateOfferPrice( tierIndex );
//		if ( price != null )
//		{
//			price.setLimit( limit );
//			if ( limit > maxOfferLimit )
//			{
//				maxOfferLimit = limit;
//			}
//			return true;
//		}
//		return false;
//	}
//
//	private void ensureTierCapacity( int maxTiers, ArrayList<MarketPrice> list )
//	{
//		for ( int i = list.size(); i < maxTiers; i++ )
//		{
//			list.add( new MarketPriceC() );
//		}
//	}
//
//	public long getRateReceivedByAdapter()
//	{
//		// if ( rateRcvdByAdapter == 0 )
//		// {
//		// Timing t = getTiming();
//		// if ( t != null )
//		// {
//		// rateRcvdByAdapter = t.getLongTime(
//		// ISCommonConstants.EVENT_TIME_DISP_ADAPTER_REC_RATE );
//		// }
//		// }
//		return rateRcvdByAdapter;
//	}
//
//	public long getRateSentByAdapter()
//	{
//		// if ( rateSentByAdapter == 0 )
//		// {
//		// Timing t = getTiming();
//		// if ( t != null )
//		// {
//		// rateSentByAdapter = t.getLongTime(
//		// ISCommonConstants.EVENT_TIME_DISP_ADAPTER_SENT_RATE );
//		// }
//		// }
//		return rateSentByAdapter;
//	}
//
//	public void setRateReceivedByAdapter( long rateRcvdByAdapter )
//	{
//		this.rateRcvdByAdapter = rateRcvdByAdapter;
//	}
//
//	public void setRateSentByAdapter( long rateSentByAdapter )
//	{
//		this.rateSentByAdapter = rateSentByAdapter;
//	}
//
//	public long getRateEffective()
//	{
//		// if ( rateEffectiveAtProvider == 0 )
//		// {
//		// Timing t = getTiming();
//		// if ( t != null )
//		// {
//		// rateEffectiveAtProvider = t.getLongTime(
//		// ISCommonConstants.EVENT_TIME_RATE_EFFECTIVE );
//		// }
//		// }
//		return rateEffectiveAtProvider;
//	}
//
//	public void setTiming( Timing timings )
//	{
//		rateRcvdByAdapter = timings.getLongTime( ISCommonConstants.EVENT_TIME_DISP_ADAPTER_REC_RATE );
//		rateSentByAdapter = timings.getLongTime( ISCommonConstants.EVENT_TIME_DISP_ADAPTER_SENT_RATE );
//		rateEffectiveAtProvider = timings.getLongTime( ISCommonConstants.EVENT_TIME_RATE_EFFECTIVE );
//	}
//
//	public void setRateEffective( long rateEffectiveAtProvider )
//	{
//		this.rateEffectiveAtProvider = rateEffectiveAtProvider;
//	}
//
//	public String getServerId()
//	{
//		return serverId;
//	}
//
//	public void setServerId( String serverId )
//	{
//		this.serverId = serverId;
//	}
//
//	public String getBookname()
//	{
//		return bookname;
//	}
//
//	public void setBookname( String bookname )
//	{
//		this.bookname = bookname;
//	}
//
//	public void setPool( MarketRatePoolFactory poolFactory )
//	{
//		this.poolFactory = poolFactory;
//		this.poolable = true;
//	}
//
//	public boolean isPoolable()
//	{
//		return this.poolable;
//	}
//
//	/**
//	 * Extracted SequenceNo from quoteId ( guid )
//	 * @return
//	 */
//	public String getSequenceNoAsString()
//	{
//		return sequenceNoAsString;
//	}
//
//	/**
//	 * Extracted SequenceNo from quoteId ( guid )
//	 * @return
//	 */
//	public long getSequenceNo()
//	{
//		return sequenceNo;
//	}
//
//	private void reset()
//	{
//		this.providerShortName = null;
//		this.providerIndex = 0;
//		this.quoteId = null;
//		this.longQuoteId = -1;
//		this.streamId = null;
//		this.streamIndex = 0;
//		this.baseCcy = null;
//		this.baseCcyIndex = -1;
//		this.varCcy = null;
//		this.varCcyIndex = -1;
//		this.limitCcy = null;
//		this.limitCcyIndex = -1;
//		this.valueDate = null;
//		this.formattedValueDate = null;
//		this.priceType = 0;
//		this.aggregated = false;
//		this.incremental = false;
//		this.stale = false;
//		this.tenor = null;
//		this.maxBidLimit = 0;
//		this.maxOfferLimit = 0;
//		// Tiers
//		for ( MarketPrice mp : bidPrices )
//			mp.reset();
//		for ( MarketPrice mp : offerPrices )
//			mp.reset();
//
//		this.stringRepresentation = null;
//		this.isDirty = true;
//		this.bidSortingRequired = false;
//		this.offerSortingRequired = false;
//		this.rateRcvdByAdapter = 0;
//		this.rateSentByAdapter = 0;
//		this.rateEffectiveAtProvider = 0;
//		this.serverId = null;
//		this.bookname = null;
//		this.recycled = false;
//		refCount.set( 0 );
//	}
//
//	public void incReference()
//	{
//    	if( recycled )
//    		throw new IllegalStateException( "Cannot increment reference to MarketRate.  It's already recycled ");
//		refCount.incrementAndGet();
//	}
//
//	public int decReference()
//	{
//		if ( !poolable )
//			return 0;
//    	if( recycled )
//    		throw new IllegalStateException( "Cannot decrement reference to MarketRate. It's already recycled " );
//		int refCnt = refCount.decrementAndGet();
//		if ( refCnt <= 0 )
//		{
//			if( !recycled )
//			{
//				recycled = true;
//				reset();
//				if( poolFactory != null )
//				{
//					poolFactory.returnMarketRate( this );
//					poolFactory = null;
//				}
//			}
//		}
//		return refCnt;
//	}
//
//    private void setSequenceNo( String guid )
//    {
//        try
//        {
//            int idx1 = guid.indexOf( "-", guid.indexOf( "-", guid.indexOf( "-", guid.indexOf( "-" ) + 1 ) + 1 ) + 1 );
//            int idx2 = guid.indexOf( "-", idx1 + 1 );
//            if ( idx2 != -1 )
//            {
//            	sequenceNoAsString = guid.substring( idx1 + 1, idx2 );
//                sequenceNo = Long.parseLong( sequenceNoAsString, 16 );
//            }
//            else
//            {
//            	sequenceNoAsString = guid.substring( idx1 + 1 );
//                sequenceNo = Long.parseLong( sequenceNoAsString, 16 );
//            }
//        }
//        catch ( Exception e )
//        {
//        	e.printStackTrace();
//        }
//    }
//
//	public boolean isOutrightDisplayOrder()
//	{
//		return isOutrightDisplayOrder;
//	}
//
//	public void setIsOutrightDisplayOrder(boolean isOutrightDisplayOrder)
//	{
//		this.isOutrightDisplayOrder = isOutrightDisplayOrder;
//	}
//
//    public Tenor getTenor()
//    {
//        return this.tenor;
//    }
//}
