package com.mainlab.order;

import com.mainlab.Task.*;
import com.mainlab.Utilities.ConcurrentTwoDimensionalArrayList;
import com.mainlab.Utilities.Currencies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    private ConcurrentTwoDimensionalArrayList bidMarketRate = new ConcurrentTwoDimensionalArrayList(); //Maintain list of Market Rates offer for Sell
    private ConcurrentTwoDimensionalArrayList offerMarketRate=  new ConcurrentTwoDimensionalArrayList(); //Maintain list of Market Rates offer for Buy
    private ConcurrentTwoDimensionalArrayList offerOrder= new ConcurrentTwoDimensionalArrayList(); //Maintain list of Orders offer for Sell
    private ConcurrentTwoDimensionalArrayList bidOrder=  new ConcurrentTwoDimensionalArrayList(); //Maintain list of Orders offer for Buy

     private ConcurrentMap<String, Lock> currPairLock = new ConcurrentHashMap<String, Lock>();

    private Queue<OrderLog> orderDoneLog = new ConcurrentLinkedQueue<OrderLog>();

    private static OrderBook uniqueInstance = new OrderBook();


    private void initializeAll(){
        bidMarketRate = new ConcurrentTwoDimensionalArrayList();//Maintain list of Market Rates offer for Sell
        offerMarketRate=new ConcurrentTwoDimensionalArrayList(); //Maintain list of Market Rates offer for Buy
        offerOrder= new ConcurrentTwoDimensionalArrayList(); //Maintain list of Orders offer for Sell
        bidOrder= new ConcurrentTwoDimensionalArrayList();//Maintain list of Orders offer for Buy
        currPairLock = new ConcurrentHashMap<String, Lock>();
        orderDoneLog = new ConcurrentLinkedQueue<OrderLog>();
    }


    public void clearAll(){
        initializeAll();
    }


    public static OrderBook getInstance(){
        return uniqueInstance;
    }



    public void addOfferMarketQuote(Order offerMarketQuote){

        //System.out.println(offerMarketQuote);
        currPairLock.putIfAbsent(offerMarketQuote.getCurrPair(), new ReentrantLock());

        currPairLock.get(offerMarketQuote.getCurrPair()).lock();


        try{
           boolean orderSuccess = matchMarketRate(offerMarketQuote);

        if(orderSuccess==false){

            if (null==offerMarketRate.getConcurrentSkipListCell(offerMarketQuote.getBaseCcy(),offerMarketQuote.getVarCcy())) {
                offerMarketRate.addOrderPairQuote(offerMarketQuote.getBaseCcy(),offerMarketQuote.getVarCcy(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>() );
            }

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = offerMarketRate.getConcurrentSkipListCell(offerMarketQuote.getBaseCcy(), offerMarketQuote.getVarCcy());
            currMap.putIfAbsent(offerMarketQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(offerMarketQuote.getQuoteRate()).add(offerMarketQuote);

        }
        }finally{
            currPairLock.get(offerMarketQuote.getCurrPair()).unlock();
        }
        //System.out.println(offerMarketQuote);
    }

    public void addBidMarketQuote(Order bidMarketQuote){
        //System.out.println(bidMarketQuote);
        currPairLock.putIfAbsent(bidMarketQuote.getCurrPair(), new ReentrantLock());

        currPairLock.get(bidMarketQuote.getCurrPair()).lock();

        try{
            boolean orderSuccess=matchMarketRate(bidMarketQuote);

        if(orderSuccess==false){

            if (null==bidMarketRate.getConcurrentSkipListCell(bidMarketQuote.getBaseCcy(),bidMarketQuote.getVarCcy())) {
                bidMarketRate.addOrderPairQuote(bidMarketQuote.getBaseCcy(),bidMarketQuote.getVarCcy(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>() );
            }

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = bidMarketRate.getConcurrentSkipListCell(bidMarketQuote.getBaseCcy(), bidMarketQuote.getVarCcy());
            currMap.putIfAbsent(bidMarketQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(bidMarketQuote.getQuoteRate()).add(bidMarketQuote);

        }
        }finally{
            currPairLock.get(bidMarketQuote.getCurrPair()).unlock();

        }
        //System.out.println(bidMarketQuote);
    }


    public void addBidOrder(Order bidOrderQuote){
        //System.out.println(bidOrderQuote);
        currPairLock.putIfAbsent(bidOrderQuote.getCurrPair(), new ReentrantLock());

        currPairLock.get(bidOrderQuote.getCurrPair()).lock();
        boolean orderSuccess = false;
        try{
            orderSuccess=matchOrder(bidOrderQuote);


        if(orderSuccess==false){


            if (null==bidOrder.getConcurrentSkipListCell(bidOrderQuote.getBaseCcy(),bidOrderQuote.getVarCcy())) {
                bidOrder.addOrderPairQuote(bidOrderQuote.getBaseCcy(),bidOrderQuote.getVarCcy(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>() );
            }

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = bidOrder.getConcurrentSkipListCell(bidOrderQuote.getBaseCcy(), bidOrderQuote.getVarCcy());
            currMap.putIfAbsent(bidOrderQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(bidOrderQuote.getQuoteRate()).add(bidOrderQuote);

        }
        }finally{
            currPairLock.get(bidOrderQuote.getCurrPair()).unlock();

        }
        //System.out.println(bidOrderQuote);
    }

    public void addOfferOrder(Order offerOrderQuote){
        //System.out.println(offerOrderQuote);
        currPairLock.putIfAbsent(offerOrderQuote.getCurrPair(), new ReentrantLock());

        currPairLock.get(offerOrderQuote.getCurrPair()).lock();

        try{
            boolean orderSuccess =matchOrder(offerOrderQuote);



        if(orderSuccess==false){

            if (null==offerOrder.getConcurrentSkipListCell(offerOrderQuote.getBaseCcy(),offerOrderQuote.getVarCcy())) {
                offerOrder.addOrderPairQuote(offerOrderQuote.getBaseCcy(),offerOrderQuote.getVarCcy(), new ConcurrentSkipListMap<Double, BlockingQueue<Order>>() );
            }

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currMap = offerOrder.getConcurrentSkipListCell(offerOrderQuote.getBaseCcy(), offerOrderQuote.getVarCcy());
            currMap.putIfAbsent(offerOrderQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());
            currMap.get(offerOrderQuote.getQuoteRate()).add(offerOrderQuote);

        }
        }finally{
            currPairLock.get(offerOrderQuote.getCurrPair()).unlock();

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

        if(marketRate.getOfferType().equals(Order.QuoteType.BID) && null!=offerOrder){


                ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = offerOrder.getConcurrentSkipListCell(marketRate.getBaseCcy(), marketRate.getVarCcy()) ;
                if(null!=currPairOrders){
                    while(orderSuccess==false && currPairOrders.isEmpty()==false
                            && marketRate.getQuoteRate() >= currPairOrders.firstKey()){

                        BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.firstKey());

                        orderSuccess =  doOrder(marketRate, availableOrders);

                        if(availableOrders.isEmpty() == true){
                            currPairOrders.pollFirstEntry();
                        }

                    }
                }


        }else if(null!=bidOrder){

                ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = bidOrder.getConcurrentSkipListCell(marketRate.getBaseCcy(), marketRate.getVarCcy()) ;
                if(null!=currPairOrders){
                    while(orderSuccess==false && currPairOrders.isEmpty()==false
                            && marketRate.getQuoteRate() <= currPairOrders.lastKey()){

                        BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());
                        orderSuccess =  doOrder(marketRate, availableOrders);

                        if(availableOrders.isEmpty() == true){
                            currPairOrders.pollFirstEntry();
                        }

                    }
                }

        }

        return orderSuccess;


    }


    private boolean useMarketRateBid(Order orderRate){

        if(null==offerMarketRate || null==offerMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())
                || offerMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).isEmpty()==true){
            return false;
        }else if(null==offerOrder || null==offerOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())
                || offerOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).isEmpty()==true){
            return true;
        }else{
            return ( offerMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).firstKey() <= offerOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).firstKey());
        }
    }

    private boolean useMarketRateOffer(Order orderRate){

        if(null==bidMarketRate || null==bidMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())
                || bidMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).isEmpty()==true){
            return false;
        }else if(null==bidOrder || null==bidOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())
                || bidOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).isEmpty()==true){
            return true;
        }else{
            return ( bidMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).firstKey() >= bidOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()).firstKey());
        }

    }

    public boolean matchOrder(Order orderRate){

        boolean orderSuccess=false;
        if(orderRate.getOfferType().equals(Order.QuoteType.BID) &&
                ((null!=offerMarketRate )
                        || (null!=offerOrder ))
                ){


            boolean useMarketRate =useMarketRateBid(orderRate);

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = (useMarketRate==true?offerMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()):offerOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())) ;

            while(orderSuccess==false && currPairOrders!=null && currPairOrders.isEmpty()==false
                    && orderRate.getQuoteRate() >= currPairOrders.lastKey()){
                BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());
                orderSuccess =  doOrder(orderRate, availableOrders);

                if(availableOrders.isEmpty() == true){
                    currPairOrders.pollFirstEntry();
                }

                useMarketRate =useMarketRateBid(orderRate);
                currPairOrders =(useMarketRate==true?offerMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()):offerOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())) ;


            }


        }else if(
                (null!=bidMarketRate)
                        || (null!=bidOrder )
                ){

            boolean useMarketRate =useMarketRateOffer(orderRate);

            ConcurrentNavigableMap<Double, BlockingQueue<Order>> currPairOrders = (useMarketRate==true?bidMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()):bidOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())) ;

            while(orderSuccess==false && currPairOrders!=null && currPairOrders.isEmpty()==false
                    && orderRate.getQuoteRate() <= currPairOrders.lastKey()){
                BlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());
                orderSuccess =  doOrder(orderRate, availableOrders);

                if(availableOrders.isEmpty() == true){
                    currPairOrders.pollFirstEntry();
                }

                useMarketRate =useMarketRateOffer(orderRate);
                currPairOrders = (useMarketRate==true?bidMarketRate.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy()):bidOrder.getConcurrentSkipListCell(orderRate.getBaseCcy(), orderRate.getVarCcy())) ;


            }

        }

        return orderSuccess;





    }


    static String[] currPairs = new String[]{"EUR/USD", "EUR/JPY", "INR/USD", "INR/JPY", "USD/CNY", "CNY/JPY"};
    static volatile int i=0;
    public static void main(String params[]){


        OrderBook orderBook = OrderBook.getInstance();

        ExecutionManagementService  execMgmtSrv = ExecutorManagementServiceImpl.getInstance();
//        execMgmtSrv.execute(new BidMarketQuoteExecutionTask(Order.createNewMarketOrder("DBNB",Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234)));
//        execMgmtSrv.execute(new BidMarketQuoteExecutionTask(Order.createNewMarketOrder("DNBA",Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234)));
//        execMgmtSrv.execute(new OfferMarketQuoteExecutionTask(Order.createNewMarketOrder("ABC", Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.OFFER, 1.1234)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 1,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 2,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 3,Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 4,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),8000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 5,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new BidOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 6, Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.BID, 1.1235)));

//        execMgmtSrv.execute(new BidMarketQuoteExecutionTask(Order.createNewMarketOrder("DBNB",Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234)));
//       execMgmtSrv.execute(new BidMarketQuoteExecutionTask(Order.createNewMarketOrder("DNBA",Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234)));
//        execMgmtSrv.execute(new OfferMarketQuoteExecutionTask(Order.createNewMarketOrder("ABC", Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.OFFER, 1.1234)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 1,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 2,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 3,Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 4,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),8000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 5,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233)));
//        execMgmtSrv.execute(new BidOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 6, Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.BID, 1.1235)));
//


        // ExecutorService executor = Executors.newFixedThreadPool(5);
        for(i=0; i< 25000; i++){


           /* executor.execute(new Runnable(){

                public void run(){   */
            //OrderBook orderBook = OrderBook.getInstance();
            execMgmtSrv.execute(new BidMarketQuoteExecutionTask(Order.createNewMarketOrder("DBNB",Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234)));
            execMgmtSrv.execute(new BidMarketQuoteExecutionTask(Order.createNewMarketOrder("DNBA",Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234)));
            execMgmtSrv.execute(new OfferMarketQuoteExecutionTask(Order.createNewMarketOrder("ABC", Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.OFFER, 1.1234)));
            execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 1,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233)));
            execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 2,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233)));
            execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 3,Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233)));
            execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 4,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),8000, Order.QuoteType.OFFER, 1.1233)));
            execMgmtSrv.execute(new OfferOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 5,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233)));
            execMgmtSrv.execute(new BidOrderExecutionTask(Order.createNewOrder(((i+1)*6) + 6, Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.BID, 1.1235)));



            //orderBook.addBidMarketQuote(Order.createNewMarketOrder("DBNB",Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234));
            //orderBook.addBidMarketQuote(Order.createNewMarketOrder("DNBA",Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),50000, Order.QuoteType.BID, 1.1234));
            //orderBook.addOfferMarketQuote(Order.createNewMarketOrder("ABC", Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.OFFER, 1.1234));
            //orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 1,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233));
            //orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 2,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233));
           // orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 3,Currencies.valueOf(currPairs[(i+1)%6].split("/")[0]),Currencies.valueOf(currPairs[(i+1)%6].split("/")[1]),30000, Order.QuoteType.OFFER, 1.1233));
           // orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 4,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),8000, Order.QuoteType.OFFER, 1.1233));
           // orderBook.addOfferOrder(Order.createNewOrder(((i+1)*6) + 5,Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]),10000, Order.QuoteType.OFFER, 1.1233));
           // orderBook.addBidOrder(Order.createNewOrder(((i+1)*6) + 6, Currencies.valueOf(currPairs[i%6].split("/")[0]),Currencies.valueOf(currPairs[i%6].split("/")[1]), 10000, Order.QuoteType.BID, 1.1235));
            // }


            // });



        }
    }


}

