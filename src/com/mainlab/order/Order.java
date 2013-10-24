package com.mainlab.order;

import java.util.Date;

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

    private Order() {
    }

    public static Order createNewOrder(int orderId, String currPair, long totalOrder, QuoteType offerType,  double quoteRate) {
        Order order = new Order();
        order.setCurrPair(currPair);
        order.setTotalOrder(totalOrder);
        order.setOfferType(offerType);
        order.setOrderId(orderId);
        order.setQuoteRate(quoteRate);
        order.setTimestamp((new Date()).getTime());
        return order;
    }

    public static Order createNewMarketOrder(String liqProvider, String currPair, long totalOrder, QuoteType offerType, double quoteRate) {
        Order order = new Order();
        order.setCurrPair(currPair);
        order.setTotalOrder(totalOrder);
        order.setOfferType(offerType);
        order.setLiqProvider(liqProvider);
        order.setQuoteRate(quoteRate);
        order.setTimestamp((new Date()).getTime());
        return order;
    }

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

    @Override
    public String toString() {
        return "Order{" +
                "liqProvider='" + liqProvider + '\'' +
                ", currPair='" + currPair + '\'' +
                ", totalOrder=" + totalOrder +
                ", offerType=" + offerType +
                ", orderId=" + orderId +
                ", quoteRate=" + quoteRate +
                ", timestamp=" + timestamp +
                '}';
    }
}
