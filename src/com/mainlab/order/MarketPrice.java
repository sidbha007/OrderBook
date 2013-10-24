package com.mainlab.order;

import com.integral.is.message.MarketRateSideRate;
import com.integral.time.IdcDate;

/**
 * Represents one side of a MarketRate = bid/offer.
 *
 * @author inder
 */
public interface MarketPrice {

    long getMinLimit();

    long getLimit();

    String getProviderShortName();

    int getProviderIndex();

    double getRate();
    
    double getSpotRate();
    
    double getForwardPoints();
    
    IdcDate getFixingDate();
    
    String getProviderQuoteId();

    long getLongProviderQuoteId();

    MarketRateSideRate getSideRate();

    void setMinLimit(long limit);

    void setLimit(long limit);

    void setProviderShortName(String s);

    void setProviderIndex(int index);

    void setRate(double rate);

    void setSideRate(MarketRateSideRate sideRate);

    void setProviderQuoteId(String providerQuoteId);

    void setProviderQuoteId(long longProviderQuoteId);

    public long getTimeEffective();

    public void setTimeEffective(long timeEffective);
    
    public void  setSpotRate(double spotRate);
    
    public void  setForwardPoints(double forwardPoints);
    
    public void setFixingDate(IdcDate fixingDate);
    
    public void reset();

    public int getTTL();

    public void setTTL(int timeToLive);

}
