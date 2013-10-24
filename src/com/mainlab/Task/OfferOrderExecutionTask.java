package com.mainlab.Task;

import java.util.concurrent.Callable;

import com.mainlab.order.Order;
import com.mainlab.order.OrderBook;

public  class OfferOrderExecutionTask implements Callable<TaskExecutionResult> {
	Order order;
	OfferOrderExecutionTask(){
		
	}
	public OfferOrderExecutionTask( Order order){
		this.order = order;
    }
    /** Access the task execution result */
    @Override public TaskExecutionResult call() throws Exception {
    	OrderBook.getInstance().addOfferOrder(order);
    		return OrderBook.getInstance().ordrLogR;
    }
  }