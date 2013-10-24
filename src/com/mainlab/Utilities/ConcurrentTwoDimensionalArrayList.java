package com.mainlab.Utilities;

import java.util.EnumMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mainlab.order.Order;

public class ConcurrentTwoDimensionalArrayList {
	
	public static void main (String args[]){
		ConcurrentTwoDimensionalArrayList c = new ConcurrentTwoDimensionalArrayList();
		c.addOrderPairQuote(0, 1, null);
		System.out.println(c.getConcurrentSkipListCell(0,1));
		
	    ConcurrentSkipListMap<Integer,Order> currencyRow = new ConcurrentSkipListMap<Integer,Order>();
		System.out.println(currencyRow.hashCode());
		
		c.addOrderPairQuote(1, 2, currencyRow);
		System.out.println(c.getConcurrentSkipListCell(1,2).hashCode());
		
	}
	
	private final static ConcurrentSkipListMap<Integer,Order> emptyValue = null;
	
	CopyOnWriteArrayList <CopyOnWriteArrayList<ConcurrentSkipListMap<Integer,Order>>> currencyPairCells;
	CopyOnWriteArrayList <ConcurrentSkipListMap<Integer,Order>> currencyRow = null; // new CopyOnWriteArrayList <ConcurrentSkipListMap<Integer,Order>>();
	
	
	
	
	public ConcurrentTwoDimensionalArrayList() {
		currencyPairCells= new CopyOnWriteArrayList <CopyOnWriteArrayList<ConcurrentSkipListMap<Integer,Order>>>() ;
		for (int i = 0 ; i < 150 ; i ++){
			currencyRow =  new CopyOnWriteArrayList <ConcurrentSkipListMap<Integer,Order>>();
			for (int j=0;j<150;j++){
				currencyRow.add(j,emptyValue);
			}
			currencyPairCells.add(i,currencyRow);
		}
	}
	
	
	
	public ConcurrentSkipListMap<Integer,Order> getConcurrentSkipListCell (int row, int col) {
		return currencyPairCells.get(row).get(col);
	}
	
	public void deleteOrderPair (int row, int col){
		currencyRow.set(col,emptyValue);
		currencyPairCells.set(row, currencyRow);
	}
	
	public void addOrderPairQuote (int row, int col, ConcurrentSkipListMap<Integer,Order> price){
		
		currencyRow.set(col, price);
		currencyPairCells.set(row,currencyRow);
	}
	

	
}
