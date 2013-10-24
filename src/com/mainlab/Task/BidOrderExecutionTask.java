package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class BidOrderExecutionTask implements Callable<TaskExecutionResult> {
	Order order;
	BidOrderExecutionTask(){
		
	}
	public BidOrderExecutionTask( Order order){
		this.order = order;
    }
    /** Access the task execution result */
    @Override public TaskExecutionResult call() throws Exception {
    	OrderBook.getInstance().addBidMarketQuote(order);
    		return OrderBook.getInstance().ordrLogR;
    }
  }