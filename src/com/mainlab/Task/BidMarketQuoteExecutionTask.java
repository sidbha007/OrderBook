package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class BidMarketQuoteExecutionTask implements Callable<TaskExecutionResult> {
	Order order;
	BidMarketQuoteExecutionTask(){
		
	}
	public BidMarketQuoteExecutionTask( Order order){
		this.order = order;
    }
    /** Access the task execution result */
    @Override public TaskExecutionResult call() throws Exception {
    	OrderBook.getInstance().addBidMarketQuote(order);
    		return OrderBook.getInstance().ordrLogR;
    }
  }