package com.mainlab.order;

/**
 * Created with IntelliJ IDEA.
 * User: bhaskars
 * Date: 10/21/13
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class Order {
    public enum QuoteType {
        BID, OFFER
    }
    private String liqProvider;
    private String currPair;
    private long totalOrder;
    private QuoteType offerType;
    private int orderId;
    private double quoteRate;
    private long timestamp;

    public String getLiqProvider() {
        return liqProvider;
    }

    public void setLiqProvider(String liqProvider) {
        this.liqProvider = liqProvider;
    }

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

    public QuoteType getOfferType() {
        return offerType;
    }

    public void setOfferType(QuoteType offerType) {
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
