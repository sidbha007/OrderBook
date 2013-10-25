package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class BidMarketQuoteExecutionTask implements Runnable {
	private Order order;

	public BidMarketQuoteExecutionTask( Order order){
		this.order = order;
    }
    /** Access the task execution result */
    @Override
    public void run() {
        OrderBook.getInstance().addBidMarketQuote(order);
    }
  }