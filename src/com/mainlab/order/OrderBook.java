package com.mainlab.order;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: Siddharth
 * Date: 23/10/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderBook {

    private ConcurrentMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>> bidMarketRate = new ConcurrentHashMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>>(); //Maintain list of Market Rates offer for Sell
    private ConcurrentMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>> offerMarketRate= new ConcurrentHashMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>>(); //Maintain list of Market Rates offer for Buy
    private ConcurrentMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>> offerOrder= new ConcurrentHashMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>>(); //Maintain list of Orders offer for Sell
    private ConcurrentMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>> bidOrder= new ConcurrentHashMap<String, ConcurrentNavigableMap<Double, BlockingQueue<Order>>>(); //Maintain list of Orders offer for Buy

    private ConcurrentMap<String, Lock> bidMarketRateLock = new ConcurrentHashMap<String, Lock>(); //Maintain list of Market Rates offer for Sell
    private ConcurrentMap<String, Lock> offerMarketRateLock = new ConcurrentHashMap<String, Lock>();
    private ConcurrentMap<String, Lock> offerOrderLock = new ConcurrentHashMap<String, Lock>();
    private ConcurrentMap<String, Lock> bidOrderLock = new ConcurrentHashMap<String, Lock>();

    private Queue<OrderLog> orderDoneLog = new ConcurrentLinkedQueue<OrderLog>();

    private static OrderBook uniqueInstance = new OrderBook();

    public static OrderBook getInstance(){
        return uniqueInstance;
    }



    public void addOfferMarketQuote(Order offerMarketQuote){

        //System.out.println(offerMarketQuote);
        offerMarketRateLock.putIfAbsent(offerMarketQuote.getCurrPair(), new ReentrantLock());
        boolean orderSuccess=matchMarketRate(offerMarketQuote);
        if(orderSuccess==false){
            offerMarketRate.putIfAbsent(offerMarketQuote.getCurrPair(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>());
            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = offerMarketRate.get(offerMarketQuote.getCurrPair());
            currMap.putIfAbsent(offerMarketQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(offerMarketQuote.getQuoteRate()).add(offerMarketQuote);
        }
        //System.out.println(offerMarketQuote);
    }

    public void addBidMarketQuote(Order bidMarketQuote){
        //System.out.println(bidMarketQuote);
        bidMarketRateLock.putIfAbsent(bidMarketQuote.getCurrPair(), new ReentrantLock());
        boolean orderSuccess=matchMarketRate(bidMarketQuote);
        if(orderSuccess==false){
            bidMarketRate.putIfAbsent(bidMarketQuote.getCurrPair(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>());
            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = bidMarketRate.get(bidMarketQuote.getCurrPair());
            currMap.putIfAbsent(bidMarketQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(bidMarketQuote.getQuoteRate()).add(bidMarketQuote);
        }
        //System.out.println(bidMarketQuote);
    }


    public void addBidOrder(Order bidOrderQuote){
        //System.out.println(bidOrderQuote);
        bidOrderLock.putIfAbsent(bidOrderQuote.getCurrPair(), new ReentrantLock());
        boolean orderSuccess=matchOrder(bidOrderQuote);
        if(orderSuccess==false){
            bidOrder.putIfAbsent(bidOrderQuote.getCurrPair(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>());
            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = bidOrder.get(bidOrderQuote.getCurrPair()) ;
            currMap.putIfAbsent(bidOrderQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(bidOrderQuote.getQuoteRate()).add(bidOrderQuote);
        }
        //System.out.println(bidOrderQuote);
    }

    public void addOfferOrder(Order offerOrderQuote){
        //System.out.println(offerOrderQuote);
        offerOrderLock.putIfAbsent(offerOrderQuote.getCurrPair(), new ReentrantLock());
        boolean orderSuccess=matchOrder(offerOrderQuote);
        if(orderSuccess==false){
            offerOrder.putIfAbsent(offerOrderQuote.getCurrPair(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>());
            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = offerOrder.get(offerOrderQuote.getCurrPair()) ;
            currMap.putIfAbsent(offerOrderQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(offerOrderQuote.getQuoteRate()).add(offerOrderQuote);
        }
        //System.out.println(offerOrderQuote);
    }


    private OrderBook(){

    }




    public boolean doOrder(Order order, BlockingQueue<Order> availableLiquidity){

        while(availableLiquidity.isEmpty()==false){
            Order liqAvl = availableLiquidity.peek();
            if(order.getTotalOrder() <= liqAvl.getTotalOrder()){
                //BUY is success

                liqAvl.setTotalOrder(liqAvl.getTotalOrder() - order.getTotalOrder());
                //System.out.println("Full Match Occured between\n" + order + "\n" + liqAvl );
                OrderLog ordrLog =new OrderLog(order, liqAvl, order.getQuoteRate(), order.getTotalOrder());
                orderDoneLog.add(ordrLog) ;
                System.out.println("Full Match Occured between\n" + ordrLog );
                if(liqAvl.getTotalOrder()==0){
                    availableLiquidity.poll();
                }
                return true;
            }else{
                order.setTotalOrder(order.getTotalOrder() - liqAvl.getTotalOrder());
                //System.out.println("Partial Match Occured between\n" + order + "\n" + liqAvl );
                OrderLog ordrLog =new OrderLog(order, liqAvl, order.getQuoteRate(), liqAvl.getTotalOrder());
                orderDoneLog.add(ordrLog) ;
                System.out.println("Partial Match Occured\n" + ordrLog );
                availableLiquidity.poll();
            }

        }

        return false;
    }

    private enum OrderType {
        LP_BID, LP_OFFER, ORDER_BID, ORDER_OFFER
    }


    public boolean matchMarketRate(Order marketRate){
        boolean orderSuccess=false;
        if(marketRate.getOfferType().equals(Order.QuoteType.BID) && null!=offerOrder && null!=offerOrder.get(marketRate.getCurrPair())){

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = offerOrder.get(marketRate.getCurrPair()) ;

            while(orderSuccess==false && currPairOrders.isEmpty()==false
                    && marketRate.getQuoteRate() >= currPairOrders.firstKey()){

                BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.firstKey());

                orderSuccess =  doOrder(marketRate, availableOrders);

                if(availableOrders.isEmpty() == true){
                    currPairOrders.pollFirstEntry();
                }

            }

        }else if(null!=bidOrder && null!=bidOrder.get(marketRate.getCurrPair())){

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = bidOrder.get(marketRate.getCurrPair()) ;

            while(orderSuccess==false && currPairOrders.isEmpty()==false
                    && marketRate.getQuoteRate() <= currPairOrders.lastKey()){

                BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());
                orderSuccess =  doOrder(marketRate, availableOrders);

                if(availableOrders.isEmpty() == true){
                    currPairOrders.pollFirstEntry();
                }

            }

        }

        return orderSuccess;


    }


    private boolean useMarketRateBid(Order orderRate){
        if(null==offerMarketRate || null==offerMarketRate.get(orderRate.getCurrPair())
                || offerMarketRate.get(orderRate.getCurrPair()).isEmpty()==true){
            return false;
        }else if(null==offerOrder || null==offerOrder.get(orderRate.getCurrPair())
                || offerOrder.get(orderRate.getCurrPair()).isEmpty()==true){
            return true;
        }else{
            return ( offerMarketRate.get(orderRate.getCurrPair()).firstKey() <= offerOrder.get(orderRate.getCurrPair()).firstKey());
        }
    }

    private boolean useMarketRateOffer(Order orderRate){
        if(null==bidMarketRate || null==bidMarketRate.get(orderRate.getCurrPair())
                || bidMarketRate.get(orderRate.getCurrPair()).isEmpty()==true){
            return false;
        }else if(null==bidOrder || null==bidOrder.get(orderRate.getCurrPair())
                || bidOrder.get(orderRate.getCurrPair()).isEmpty()==true){
            return true;
        }else{
            return ( bidMarketRate.get(orderRate.getCurrPair()).firstKey() >= bidOrder.get(orderRate.getCurrPair()).firstKey());
        }

    }

    public boolean matchOrder(Order orderRate){

        boolean orderSuccess=false;
        if(orderRate.getOfferType().equals(Order.QuoteType.BID) &&
                ((null!=offerMarketRate && null!=offerMarketRate.get(orderRate.getCurrPair()))
                 || (null!=offerOrder && null!=offerOrder.get(orderRate.getCurrPair())))
                ){

            boolean useMarketRate =useMarketRateBid(orderRate);


            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = (useMarketRate==true?offerMarketRate.get(orderRate.getCurrPair()):offerOrder.get(orderRate.getCurrPair())) ;

            while(orderSuccess==false && currPairOrders!=null && currPairOrders.isEmpty()==false
                    && orderRate.getQuoteRate() >= currPairOrders.lastKey()){
                BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());
                orderSuccess =  doOrder(orderRate, availableOrders);

                if(availableOrders.isEmpty() == true){
                    currPairOrders.pollFirstEntry();
                }

                useMarketRate =useMarketRateBid(orderRate);
                currPairOrders = (useMarketRate==true?offerMarketRate.get(orderRate.getCurrPair()):offerOrder.get(orderRate.getCurrPair())) ;


            }


        }else if(
                (null!=bidMarketRate && null!=bidMarketRate.get(orderRate.getCurrPair()))
                        || (null!=bidOrder && null!=bidOrder.get(orderRate.getCurrPair()))
        ){

            boolean useMarketRate =useMarketRateOffer(orderRate);


            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = (useMarketRate==true?bidMarketRate.get(orderRate.getCurrPair()):bidOrder.get(orderRate.getCurrPair())) ;

            while(orderSuccess==false && currPairOrders!=null && currPairOrders.isEmpty()==false
                    && orderRate.getQuoteRate() <= currPairOrders.lastKey()){
                BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());
                orderSuccess =  doOrder(orderRate, availableOrders);

                if(availableOrders.isEmpty() == true){
                    currPairOrders.pollFirstEntry();
                }

                useMarketRate =useMarketRateOffer(orderRate);
                currPairOrders = (useMarketRate==true?bidMarketRate.get(orderRate.getCurrPair()):bidOrder.get(orderRate.getCurrPair())) ;


            }
        }

        return orderSuccess;





    }


    static String[] currPairs = new String[]{"EUR/USD", "EUR/JPY", "INR/USD", "INR/JPY", "USD/CHA", "CHA, JPY"};
    static volatile int i=0;
    public static void main(String params[]){

        

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(i=0; i< 500; i++){


            executor.execute(new Runnable(){

                public void run(){
                    OrderBook orderBook = OrderBook.getInstance();
                    orderBook.addBidMarketQuote(Order.createNewMarketOrder("DNB",currPairs[i%6],50000, Order.QuoteType.BID, 1.1234));
                    orderBook.addBidMarketQuote(Order.createNewMarketOrder("DNBA",currPairs[(i+1)%6],50000, Order.QuoteType.BID, 1.1234));
                    orderBook.addOfferMarketQuote(Order.createNewMarketOrder("ABC", currPairs[i%6], 10000, Order.QuoteType.OFFER, 1.1234));
                    orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 1,currPairs[i%6],30000, Order.QuoteType.OFFER, 1.1233));
                    orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 2,currPairs[i%6],10000, Order.QuoteType.OFFER, 1.1233));
                    orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 3,currPairs[(i+1)%6],30000, Order.QuoteType.OFFER, 1.1233));
                    orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 4,currPairs[i%6],8000, Order.QuoteType.OFFER, 1.1233));
                    orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 5,currPairs[i%6],10000, Order.QuoteType.OFFER, 1.1233));
                    orderBook.addBidOrder(Order.createNewOrder(((i+1)*6) + 6, currPairs[i%6], 10000, Order.QuoteType.BID, 1.1235));
                }


            });

		
		
		}
	}


}

