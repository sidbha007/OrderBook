package com.mainlab.Utilities;

import java.util.EnumMap;

public enum Currencies {
	AFA, ALL, AON, ARS, AMD, AUD, DZD, AZM, BSD, BHD, BDT, BYR, BZD, BMD, BTN, BOB, BAD, BWP, BRL, BND, BGL, BIF, KHR, CAD, CLP, CNY, COP, CRC, HRK, CUP, CYP, CZK, DKK, DJF, DOP, ECS, EGP, SVC, ERN, EEK, ETB, FJD, EUR, GMD, GEL, GTQ, GNS, GYD, HTG, HNL, HKD, HUF, ISK, INR, IDR, IRR, IQD, ILS, JMD, JPY, JOD, KZT, KES, KPW, KRW, KWD, KGS, LAK, LVL, LTL, LBP, LSL, LRD, LYD, MOP, MKD, MWK, MYR, MVR, MTL, MRO, MUR, MXN, MDL, MNT, MAD, MZM, MMK, NAD, NPR, NZD, NIO, NGN, NOK, OMR, PKR, PAB, PYG, PEN, PHP, PLN, QAR, RON, RUB, RWF, XCD, SAR, SCR, SLL, SGD, SIT, SOS, ZAR, LKR, SDD, SRG, SZL, SEK, CHF, SYP, TWD, TJR, TZS, THB, TOP,TTD, TND, TRY, TMM, AED, UGX, UAH, GBP, USD, UYU, UZS, VEB, VND, YER, YUD, ZMK, ZWD;
	
	public EnumMap<Currencies, Integer> getCurrenciesMap(){
		EnumMap<Currencies,Integer> currenciesMap = new EnumMap<Currencies,Integer>(Currencies.class);
		currenciesMap.put(Currencies.AED, 0);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.AON, 1);
		currenciesMap.put(Currencies.ARS, 1);
		currenciesMap.put(Currencies.AMD, 1);
		currenciesMap.put(Currencies.AUD, 1);
		currenciesMap.put(Currencies.DZD, 1);
		currenciesMap.put(Currencies.AZM, 1);
		currenciesMap.put(Currencies.BSD, 1);
		currenciesMap.put(Currencies.BHD, 1);
		currenciesMap.put(Currencies.BDT, 1);
		currenciesMap.put(Currencies.BYR, 1);
		currenciesMap.put(Currencies.BZD, 1);
		currenciesMap.put(Currencies.BMD, 1);
		currenciesMap.put(Currencies.BTN, 1);
		currenciesMap.put(Currencies.BOB, 1);
		currenciesMap.put(Currencies.BAD, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		currenciesMap.put(Currencies.ALL, 1);
		return currenciesMap;
	}
}