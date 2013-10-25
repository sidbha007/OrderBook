package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class BidOrderExecutionTask implements Runnable {
	private Order order;

	public BidOrderExecutionTask( Order order){
		this.order = order;
    }

    @Override
    public void run() {
        OrderBook.getInstance().addBidOrder(order);
    }
}