package com.mainlab.order;

import com.mainlab.Utilities.Currencies;

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
    private Currencies baseCcy;
    private Currencies varCcy;
    private long totalOrder;
    private QuoteType offerType;
    private int orderId;
    private double quoteRate;
    private long timestamp;
    private static final String CURR_KEY_SEP = ":";

    private Order() {
    }

    public static Order createNewOrder(int orderId, Currencies baseCcy, Currencies varCcy, long totalOrder, QuoteType offerType,  double quoteRate) {
        Order order = new Order();
        order.setBaseCcy(baseCcy);
        order.setVarCcy(varCcy);
        order.setTotalOrder(totalOrder);
        order.setOfferType(offerType);
        order.setOrderId(orderId);
        order.setQuoteRate(quoteRate);
        order.setTimestamp((new Date()).getTime());
        return order;
    }

    public static Order createNewMarketOrder(String liqProvider, Currencies baseCcy, Currencies varCcy, long totalOrder, QuoteType offerType, double quoteRate) {
        Order order = new Order();
        order.setBaseCcy(baseCcy);
        order.setVarCcy(varCcy);
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

    public Currencies getBaseCcy() {
        return baseCcy;
    }

    public void setBaseCcy(Currencies baseCcy) {
        this.baseCcy = baseCcy;
    }

    public Currencies getVarCcy() {
        return varCcy;
    }


    public String getCurrPair(){
        return  this.getBaseCcy() + CURR_KEY_SEP + this.getVarCcy();
    }

    public void setVarCcy(Currencies varCcy) {
        this.varCcy = varCcy;
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
                ", baseCcy='" + baseCcy + '\'' +
                ", varCcy='" + varCcy + '\'' +
                ", totalOrder=" + totalOrder +
                ", offerType=" + offerType +
                ", orderId=" + orderId +
                ", quoteRate=" + quoteRate +
                ", timestamp=" + timestamp +
                '}';
    }
}
