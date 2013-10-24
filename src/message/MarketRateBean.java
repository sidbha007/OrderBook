package message;

/**
 * Created with IntelliJ IDEA.
 * User: Siddharth
 * Date: 23/10/13
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class MarketRateBean {


    public enum QuoteType {
        BID, OFFER
    }

    private String currPair;
    private long totalAvailable;
    private QuoteType offerType;
    private String liqProvider;
    private double quoteRate;

    public double getQuoteRate() {
        return quoteRate;
    }

    public void setQuoteRate(double quoteRate) {
        this.quoteRate = quoteRate;
    }

    public String getCurrPair() {
        return currPair;
    }

    public void setCurrPair(String currPair) {
        this.currPair = currPair;
    }

    public long getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(long totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public QuoteType getOfferType() {
        return offerType;
    }

    public void setOfferType(QuoteType offerType) {
        this.offerType = offerType;
    }

    public String getLiqProvider() {
        return liqProvider;
    }

    public void setLiqProvider(String liqProvider) {
        this.liqProvider = liqProvider;
    }
}
