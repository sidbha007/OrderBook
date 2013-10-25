package com.mainlab.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mainlab.order.Order;
import com.sonicsw.blackbird.sys.SysObject;

public class ParseCSVFileOnLoad {

	private String orderType = null;
	private String baseCurrency = null;
	private String varCurrency = null;
	private long limitOrder = 0;
	//private int orderID = 0;
	private double quoteRate = 0;
	private List<Order> orderLists = new ArrayList <Order>();
	
	/*public static void main (String [] args){
		ParseCSVFileOnLoad p = new ParseCSVFileOnLoad();
		System.out.println(p.getOrderListsFromCSV("q"));
	}*/
	
	public List <Order> getOrderListsFromCSV(String fileToParse){

		//String fileToParse = "C:\\Users\\nigamg\\Desktop\\abc.csv";
		BufferedReader fileReader = null;

		final String DELIMITER = ",";
		try
		{
			String line = "";
			fileReader = new BufferedReader(new FileReader(fileToParse));


			//orderType,baseCUr,varCur,limitOrder,quoteRate 
			while ((line = fileReader.readLine()) != null)
			{
				String[] tokens = line.split(DELIMITER);
				try{
					setOrderType (tokens[0]);
				}catch (ArrayIndexOutOfBoundsException e){
					setOrderType (null);
				}
				try{
					setBaseCurrency (tokens[1]);
				}catch (ArrayIndexOutOfBoundsException e){
					setBaseCurrency (null);
				}
				try{    
					setVarCurrency (tokens[2]);
				}catch (ArrayIndexOutOfBoundsException e){
					setVarCurrency (null);
				}
				try{

				}catch (ArrayIndexOutOfBoundsException e){
					setLimitOrder (Long.parseLong(null));
				}
				try{
					setQuoteRate(Double.parseDouble(tokens[4]));
				}catch (ArrayIndexOutOfBoundsException e){
					setQuoteRate(Double.parseDouble(null));
				}
				orderLists.add(Order.createNewOrder(this.getOrderID(), Currencies.valueOf(this.baseCurrency), Currencies.valueOf(this.varCurrency), this.limitOrder, "BID".equals(this.orderType)?Order.QuoteType.BID:Order.QuoteType.OFFER, this.quoteRate));

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		return orderLists;
	}

	public void parseFile (){}
	private void setOrderType (String orderType){
		this.orderType = orderType;
	}

	public String getOrderType (){
		return this.orderType;
	}

	private void setBaseCurrency (String baseCurrency){
		this.baseCurrency = baseCurrency;
	}

	public String getBaseCurrency (){
		return this.baseCurrency;
	}
	private void setVarCurrency (String varCurrency){
		this.varCurrency = varCurrency;
	}

	public String getvarCurrency (){
		return this.varCurrency;
	}

	private void setLimitOrder (Long limitOrder){
		this.limitOrder = limitOrder;
	}

	public Long getLimitOrder (){
		return this.limitOrder;
	}

	public int getOrderID (){
		Random random = new Random();
		return random.nextInt();
	}

	private void setQuoteRate (double quoteRate){
		this.quoteRate = quoteRate;
	}

	public double getQuoteRate (){
		return this.quoteRate;
	}
}
