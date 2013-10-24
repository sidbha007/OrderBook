package message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.*;
/**
 * Created with IntelliJ IDEA.
 * User: Siddharth
 * Date: 23/10/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderBook {

    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>> bidMarketRate = new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>>(); //Maintain list of Market Rates offer for Sell
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>> offerMarketRate= new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>>();     //Maintain list of Market Rates offer for Buy
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>> offerOrder= new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>>();  //Maintain list of Orders offer for Sell
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>> bidOrder= new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>>();     //Maintain list of Orders offer for Buy



    public void addSellMarketQuote(Order sellMarketQuote){
        ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> rateMap = bidMarketRate.putIfAbsent(sellMarketQuote.getCurrPair(), new ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>>());

        rateMap.putIfAbsent(sellMarketQuote.getQuoteRate(), new LinkedBlockingQueue<Order>());

        rateMap.get(sellMarketQuote.getQuoteRate()).;
    }

    public void addBuyMarketQuote(Order buyMarketQuote){


    }


    public void addbidOrder(Order bidOrder){


    }

    public void addofferOrder(Order offerOrder){


    }

    public boolean matchMarketRate(Order marketRate){
        boolean orderSuccess=false;
        if(marketRate.getOfferType().equals(Order.QuoteType.BID)){
            ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> currPairOrders = offerOrder.get(marketRate.getCurrPair()) ;

            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate >= currPairOrders.firstKey() && orderSuccess==false){

                LinkedBlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.firstKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    Order orderAvl = availableOrders.peek();
                    if(marketRate.getTotalOrder() >= orderAvl.getTotalOrder()){
                        //BUY is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalOrder());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalOrder(marketRate.getTotalOrder() - orderAvl.getTotalOrder());
                        availableOrders.poll();
                    }

                    if(availableOrders.isEmpty() == true){
                        currPairOrders.pollFirstEntry();
                    }

                }




            }




        }else{


            ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> currPairOrders = bidOrder.get(marketRate.getCurrPair()) ;

            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate <= currPairOrders.lastKey() && orderSuccess==false){

                LinkedBlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    Order orderAvl = availableOrders.peek();
                    if(marketRate.getTotalOrder() >= orderAvl.getTotalOrder()){
                        //SELL is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalOrder());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalOrder(marketRate.getTotalOrder() - orderAvl.getTotalOrder());
                        availableOrders.poll();
                    }

                    if(availableOrders.isEmpty() == true){
                        currPairOrders.pollLastEntry();
                    }

                }




            }




        }

        return   orderSuccess;


    }

    public boolean matchOrder(Order orderRate){

        boolean orderSuccess=false;
        if(orderRate.getOfferType().equals(Order.QuoteType.BID)){

            boolean useMarketRate =( bidMarketRate.get(orderRate.getCurrPair()).firstKey() >= offerOrder.get(orderRate.getCurrPair()).firstKey());

            if(useMarketRate) {
                //do a lock on  bidMarketRate

                ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> currPairOrders = offerOrder.get(marketRate.getCurrPair()) ;

                double quoteRate = marketRate.getQuoteRate();

                while(quoteRate >= currPairOrders.firstKey() && orderSuccess==false){

                    LinkedBlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.firstKey());


                    while(availableOrders.isEmpty()==false
                            || orderSuccess==false){
                        Order orderAvl = availableOrders.peek();
                        if(marketRate.getTotalOrder() >= orderAvl.getTotalOrder()){
                            //BUY is success

                            orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalOrder());
                            if(orderAvl.getTotalOrder()==0){
                                availableOrders.poll();
                            }

                            orderSuccess = true;
                        }else{
                            marketRate.setTotalOrder(marketRate.getTotalOrder() - orderAvl.getTotalOrder());
                            availableOrders.poll();
                        }

                        if(availableOrders.isEmpty() == true){
                            currPairOrders.pollFirstEntry();
                        }

                    }




                }



            }else{
                //do a lock on  offerOrder



            }



            ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> currPairMktOrders = bidMarketRate.get(orderRate.getCurrPair()) ;
            ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> currPairOrdOrders = offerOrder.get(orderRate.getCurrPair()) ;
            
            
            
            
            
            while(orderSuccess==false  &&  ){





            }



            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate >= currPairOrders.firstKey() && orderSuccess==false){

                LinkedBlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.firstKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    Order orderAvl = availableOrders.peek();
                    if(marketRate.getTotalOrder() >= orderAvl.getTotalOrder()){
                        //BUY is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalOrder());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalOrder(marketRate.getTotalOrder() - orderAvl.getTotalOrder());
                        availableOrders.poll();
                    }

                    if(availableOrders.isEmpty() == true){
                        currPairOrders.pollFirstEntry();
                    }

                }




            }




        }else{


            ConcurrentSkipListMap<Double, LinkedBlockingQueue<Order>> currPairOrders = bidOrder.get(marketRate.getCurrPair()) ;

            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate <= currPairOrders.lastKey() && orderSuccess==false){

                LinkedBlockingQueue<Order> availableOrders = currPairOrders.get(currPairOrders.lastKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    Order orderAvl = availableOrders.peek();
                    if(marketRate.getTotalOrder() >= orderAvl.getTotalOrder()){
                        //SELL is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalOrder());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalOrder(marketRate.getTotalOrder() - orderAvl.getTotalOrder());
                        availableOrders.poll();
                    }

                    if(availableOrders.isEmpty() == true){
                        currPairOrders.pollLastEntry();
                    }

                }




            }




        }

        return   orderSuccess;





    }







}
