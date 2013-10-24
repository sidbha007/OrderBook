package message;

/**
 * Created with IntelliJ IDEA.
 * User: Siddharth
 * Date: 23/10/13
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderBean {

    private String currPair;
    private long totalOrder;
    private MarketRateBean.QuoteType offerType;
    private int orderId;
    private double quoteRate;

    public String getCurrPair() {
        return currPair;
    }

    public void setCurrPair(String currPair) {
        this.currPair = currPair;
    }

    public long getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(long totalOrder) {
        this.totalOrder = totalOrder;
    }

    public MarketRateBean.QuoteType getOfferType() {
        return offerType;
    }

    public void setOfferType(MarketRateBean.QuoteType offerType) {
        this.offerType = offerType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getQuoteRate() {
        return quoteRate;
    }

    public void setQuoteRate(double quoteRate) {
        this.quoteRate = quoteRate;
    }
}
