package com.mainlab.Utilities;

import java.util.EnumMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mainlab.order.Order;

public class ConcurrentTwoDimensionalArrayList {

	/*	public static void main (String args[]){
		ConcurrentTwoDimensionalArrayList c = new ConcurrentTwoDimensionalArrayList();
		c.addOrderPairQuote(0, 1, null);
		System.out.println(c.getConcurrentSkipListCell(0,1));

	    ConcurrentSkipListMap<Integer,Order> currencyRow = new ConcurrentSkipListMap<Integer,Order>();
		System.out.println(currencyRow.hashCode());

		c.addOrderPairQuote(1, 2, currencyRow);
		System.out.println(c.getConcurrentSkipListCell(1,2).hashCode());

	}*/

	private final static ConcurrentSkipListMap<Double,Order> emptyValue = null;

	CopyOnWriteArrayList <CopyOnWriteArrayList<ConcurrentSkipListMap<Double,Order>>> currencyPairCells;
	CopyOnWriteArrayList <ConcurrentSkipListMap<Double,Order>> currencyRow = null; // new CopyOnWriteArrayList <ConcurrentSkipListMap<Integer,Order>>();




	public ConcurrentTwoDimensionalArrayList() {
		currencyPairCells= new CopyOnWriteArrayList <CopyOnWriteArrayList<ConcurrentSkipListMap<Double,Order>>>() ;
		for (int i = 0 ; i < 150 ; i ++){
			currencyRow =  new CopyOnWriteArrayList <ConcurrentSkipListMap<Double,Order>>();
			for (int j=0;j<150;j++){
				currencyRow.add(j,emptyValue);
			}
			currencyPairCells.add(i,currencyRow);
		}
	}
	
	public static EnumMap<Currencies, Integer> getCurrenciesMap(){
		EnumMap<Currencies,Integer> currenciesMap = new EnumMap<Currencies,Integer>(Currencies.class);
		currenciesMap.put(Currencies.AED, 0);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.AON, 2);
		currenciesMap.put(Currencies.ARS, 3);
		currenciesMap.put(Currencies.AMD, 4);
		currenciesMap.put(Currencies.AUD, 5);
		currenciesMap.put(Currencies.DZD, 6);
		currenciesMap.put(Currencies.AZM, 7);
		currenciesMap.put(Currencies.BSD, 8);
		currenciesMap.put(Currencies.BHD, 9);
		currenciesMap.put(Currencies.BDT, 10);
		currenciesMap.put(Currencies.BYR, 11);
		currenciesMap.put(Currencies.BZD, 12);
		currenciesMap.put(Currencies.BMD, 13);
		currenciesMap.put(Currencies.BTN, 14);
		currenciesMap.put(Currencies.BOB, 15);
		currenciesMap.put(Currencies.BAD, 16);
		currenciesMap.put(Currencies.BWP, 17);
		currenciesMap.put(Currencies.BRL, 18);
		currenciesMap.put(Currencies.BND, 19);
		currenciesMap.put(Currencies.BGL, 20);
		currenciesMap.put(Currencies.BIF, 21);
		currenciesMap.put(Currencies.KHR, 22);
		currenciesMap.put(Currencies.CAD, 23);
		currenciesMap.put(Currencies.CLP, 24);
		currenciesMap.put(Currencies.CNY, 25);
		currenciesMap.put(Currencies.COP, 26);
		currenciesMap.put(Currencies.CRC, 27);
		currenciesMap.put(Currencies.HRK, 28);
		currenciesMap.put(Currencies.CUP, 29);
		currenciesMap.put(Currencies.CYP, 30);
		currenciesMap.put(Currencies.CZK, 32);
		currenciesMap.put(Currencies.DKK, 33);
		currenciesMap.put(Currencies.DJF, 34);
		currenciesMap.put(Currencies.DOP, 35);
		currenciesMap.put(Currencies.ECS, 36);
		currenciesMap.put(Currencies.EGP, 37);
		currenciesMap.put(Currencies.SVC, 38);
		currenciesMap.put(Currencies.ERN, 39);
		currenciesMap.put(Currencies.EEK, 40);
		currenciesMap.put(Currencies.ETB, 41);
		currenciesMap.put(Currencies.FJD, 42);
		currenciesMap.put(Currencies.EUR, 43);
		currenciesMap.put(Currencies.GMD, 44);
		currenciesMap.put(Currencies.GEL, 45);
		currenciesMap.put(Currencies.GTQ, 46);
		currenciesMap.put(Currencies.GNS, 47);
		currenciesMap.put(Currencies.GYD, 48);
		currenciesMap.put(Currencies.HTG, 49);
		currenciesMap.put(Currencies.HNL, 50);
		currenciesMap.put(Currencies.HKD, 51);
		currenciesMap.put(Currencies.HUF, 52);
		currenciesMap.put(Currencies.ISK, 53);
		currenciesMap.put(Currencies.INR, 54);
		currenciesMap.put(Currencies.IDR, 55);
		currenciesMap.put(Currencies.IRR, 56);
		currenciesMap.put(Currencies.IQD, 57);
		currenciesMap.put(Currencies.ILS, 58);
		currenciesMap.put(Currencies.JMD, 59);
		currenciesMap.put(Currencies.JPY, 60);
		currenciesMap.put(Currencies.JOD, 61);
		currenciesMap.put(Currencies.KZT, 62);
		currenciesMap.put(Currencies.KES, 63);
		currenciesMap.put(Currencies.KPW, 64);
		currenciesMap.put(Currencies.KRW, 65);
		currenciesMap.put(Currencies.KWD, 66);
		currenciesMap.put(Currencies.KGS, 67);
		currenciesMap.put(Currencies.LAK, 68);
		currenciesMap.put(Currencies.LVL, 69);
		currenciesMap.put(Currencies.LTL, 70);
		currenciesMap.put(Currencies.LBP, 71);
		currenciesMap.put(Currencies.LSL, 72);
		currenciesMap.put(Currencies.LRD, 73);
		currenciesMap.put(Currencies.LYD, 74);
		currenciesMap.put(Currencies.EUR, 75);
		currenciesMap.put(Currencies.MOP, 76);
		currenciesMap.put(Currencies.MKD, 77);
		currenciesMap.put(Currencies.MWK, 78);
		currenciesMap.put(Currencies.MYR, 79);
		currenciesMap.put(Currencies.MVR, 80);
		currenciesMap.put(Currencies.MTL, 81);
		currenciesMap.put(Currencies.MRO, 82);
		currenciesMap.put(Currencies.MUR, 83);
		currenciesMap.put(Currencies.MXN, 84);
		currenciesMap.put(Currencies.MDL, 85);
		currenciesMap.put(Currencies.MNT, 86);
		currenciesMap.put(Currencies.MAD, 87);
		currenciesMap.put(Currencies.MZM, 88);
		currenciesMap.put(Currencies.MMK, 89);
		currenciesMap.put(Currencies.NAD, 90);
		currenciesMap.put(Currencies.NPR, 91);
		currenciesMap.put(Currencies.NZD, 92);
		currenciesMap.put(Currencies.NIO, 93);
		currenciesMap.put(Currencies.NGN, 94);
		currenciesMap.put(Currencies.NOK, 95);
		currenciesMap.put(Currencies.OMR, 96);
		currenciesMap.put(Currencies.PKR, 97);
		currenciesMap.put(Currencies.PAB, 98);
		currenciesMap.put(Currencies.PYG, 99);
		currenciesMap.put(Currencies.PEN, 100);
		currenciesMap.put(Currencies.PHP, 101);
		currenciesMap.put(Currencies.PLN, 102);
		currenciesMap.put(Currencies.QAR, 103);
		currenciesMap.put(Currencies.RON, 104);
		currenciesMap.put(Currencies.RUB, 105);
		currenciesMap.put(Currencies.RWF, 106);
		currenciesMap.put(Currencies.XCD, 107);
		currenciesMap.put(Currencies.SAR, 108);
		currenciesMap.put(Currencies.SCR, 109);
		currenciesMap.put(Currencies.SLL, 110);
		currenciesMap.put(Currencies.SGD, 111);
		currenciesMap.put(Currencies.SIT, 112);
		currenciesMap.put(Currencies.SOS, 113);
		currenciesMap.put(Currencies.ZAR, 114);
		currenciesMap.put(Currencies.LKR, 115);
		currenciesMap.put(Currencies.SDD, 116);
		currenciesMap.put(Currencies.SRG, 117);
		currenciesMap.put(Currencies.SZL, 118);
		currenciesMap.put(Currencies.SEK, 119);
		currenciesMap.put(Currencies.CHF, 118);
		currenciesMap.put(Currencies.SYP, 119);
		currenciesMap.put(Currencies.TWD, 120);
		currenciesMap.put(Currencies.TJR, 121);
		currenciesMap.put(Currencies.TZS, 122);
		currenciesMap.put(Currencies.THB, 123);
		currenciesMap.put(Currencies.TOP, 124);
		currenciesMap.put(Currencies.TTD, 125);
		currenciesMap.put(Currencies.TND, 126);
		currenciesMap.put(Currencies.TRY, 127);
		currenciesMap.put(Currencies.TMM, 128);
		currenciesMap.put(Currencies.AED, 129);
		currenciesMap.put(Currencies.UGX, 130);
		currenciesMap.put(Currencies.UAH, 131);
		currenciesMap.put(Currencies.GBP, 132);
		currenciesMap.put(Currencies.USD, 133);
		currenciesMap.put(Currencies.UYU, 134);
		currenciesMap.put(Currencies.UZS, 135);
		currenciesMap.put(Currencies.VEB, 136);
		currenciesMap.put(Currencies.VND, 137);
		currenciesMap.put(Currencies.YER, 138);
		currenciesMap.put(Currencies.YUD, 139);
		currenciesMap.put(Currencies.ZMK, 140);
		currenciesMap.put(Currencies.ZWD, 141);
		
		return currenciesMap;
	}



	public ConcurrentSkipListMap<Double,Order> getConcurrentSkipListCell (int row, int col) {
		try{
			return currencyPairCells.get(row).get(col);
		}catch (IndexOutOfBoundsException e){
			if (row > 150 || col > 150){
				System.out.println ("Invalid currency pair is requested.");
				return null;
			}else{
				System.out.println ("New currency pair is requested, adding the new currency pair in new cell.");
				return new ConcurrentSkipListMap<Double,Order>(); 
			}
		}
	}

	public void deleteOrderPair (int row, int col){
		try{
			currencyRow.set(col,emptyValue);
			currencyPairCells.set(row, currencyRow);
		}catch (IndexOutOfBoundsException e){
			if (row > 150 || col > 150){
				System.out.println ("Invalid currency pair is requested.");
			}else{
				System.out.println ("New currency pair is requested, adding the new currency pair in new cell.");
			}
		}
	}

	public void addOrderPairQuote (int row, int col, ConcurrentSkipListMap<Double,Order> price){
		try{
			currencyRow.set(col, price);
			currencyPairCells.set(row,currencyRow);
		}catch (IndexOutOfBoundsException e){
			if (row > 150 || col > 150){
				System.out.println ("Invalid currency pair is requested.");
			}else{
				System.out.println ("New currency pair is requested, adding the new currency pair in new cell.");
			}
		}
	}



}
