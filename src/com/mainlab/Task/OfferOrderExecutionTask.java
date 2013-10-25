package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class OfferOrderExecutionTask implements Runnable {
	private Order order;

	public OfferOrderExecutionTask( Order order){
		this.order = order;
    }
    /** Access the task execution result */
    @Override
    public void run() {
        OrderBook.getInstance().addOfferOrder(order);
    }
}