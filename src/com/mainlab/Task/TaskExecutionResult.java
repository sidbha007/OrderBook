package com.mainlab.Task;

import java.util.Date;

import com.mainlab.order.Order;
import com.mainlab.order.OrderLog.ORDER_TYPE;

public class TaskExecutionResult {
    public enum ORDER_TYPE{LP, ORDER};

    private Order orderParty1;
    private ORDER_TYPE orderParty1Type;
    private Order orderParty2;
    private ORDER_TYPE orderParty2Type;
    private double orderExecutionRate;
    private long orderAmount;
    private long orderTime;

    public TaskExecutionResult() {
    }

    public TaskExecutionResult(Order orderParty1,  Order orderParty2,  double orderExecutionRate, long orderAmount) {
        this.orderParty1 = orderParty1;
        this.orderParty1Type = orderParty1.getLiqProvider()==null?ORDER_TYPE.ORDER:ORDER_TYPE.LP;
        this.orderParty2 = orderParty2;
        this.orderParty2Type = orderParty2.getLiqProvider()==null?ORDER_TYPE.ORDER:ORDER_TYPE.LP;;
        this.orderExecutionRate = orderExecutionRate;
        this.orderAmount = orderAmount;
        this.orderTime = (new Date()).getTime();
    }

    public Order getOrderParty1() {
        return orderParty1;
    }

    public void setOrderParty1(Order orderParty1) {
        this.orderParty1 = orderParty1;
    }

    public ORDER_TYPE getOrderParty1Type() {
        return orderParty1Type;
    }

    public void setOrderParty1Type(ORDER_TYPE orderParty1Type) {
        this.orderParty1Type = orderParty1Type;
    }

    public Order getOrderParty2() {
        return orderParty2;
    }

    public void setOrderParty2(Order orderParty2) {
        this.orderParty2 = orderParty2;
    }

    public ORDER_TYPE getOrderParty2Type() {
        return orderParty2Type;
    }

    public void setOrderParty2Type(ORDER_TYPE orderParty2Type) {
        this.orderParty2Type = orderParty2Type;
    }

    public double getOrderExecutionRate() {
        return orderExecutionRate;
    }

    public void setOrderExecutionRate(double orderExecutionRate) {
        this.orderExecutionRate = orderExecutionRate;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "OrderLog{" +
                "orderParty1=" + orderParty1 +
                ", orderParty1Type=" + orderParty1Type +
                ", orderParty2=" + orderParty2 +
                ", orderParty2Type=" + orderParty2Type +
                ", orderExecutionRate=" + orderExecutionRate +
                ", orderAmount=" + orderAmount +
                ", orderTime=" + orderTime +
                '}';
    }
}
