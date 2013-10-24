package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class OfferMarketQuoteExecutionTask implements Callable<TaskExecutionResult> {
	Order order;
	OfferMarketQuoteExecutionTask(){
		
	}
	public OfferMarketQuoteExecutionTask( Order order){
		this.order = order;
    }
    /** Access the task execution result */
    @Override public TaskExecutionResult call() throws Exception {
    	OrderBook.getInstance().addOfferMarketQuote(order);
    		return OrderBook.getInstance().ordrLogR;
    }
  }