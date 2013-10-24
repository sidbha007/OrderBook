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

    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>>> bidMarketRate = new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>>>(); //Maintain list of Market Rates offer for Sell
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>>> offerMarketRate= new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>>>();     //Maintain list of Market Rates offer for Buy
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>>> offerOrder= new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>>>();  //Maintain list of Orders offer for Sell
    private ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>>> bidOrder= new ConcurrentHashMap<String, ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>>>();     //Maintain list of Orders offer for Buy



    public void addSellMarketQuote(MarketRateBean sellMarketQuote){
        ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>> rateMap = bidMarketRate.putIfAbsent(sellMarketQuote.getCurrPair(), new ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>>());

        rateMap.putIfAbsent(sellMarketQuote.getQuoteRate(), new LinkedBlockingQueue<MarketRateBean>());

        rateMap.get(sellMarketQuote.getQuoteRate()).;
    }

    public void addBuyMarketQuote(MarketRateBean buyMarketQuote){


    }


    public void addbidOrder(OrderBean bidOrder){


    }

    public void addofferOrder(OrderBean offerOrder){


    }

    public boolean matchMarketRate(MarketRateBean marketRate){
        boolean orderSuccess=false;
        if(marketRate.getOfferType().equals(MarketRateBean.QuoteType.BID)){
            ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>> currPairOrders = offerOrder.get(marketRate.getCurrPair()) ;

            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate >= currPairOrders.firstKey() && orderSuccess==false){

                LinkedBlockingQueue<OrderBean> availableOrders = currPairOrders.get(currPairOrders.firstKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    OrderBean orderAvl = availableOrders.peek();
                    if(marketRate.getTotalAvailable() >= orderAvl.getTotalOrder()){
                        //BUY is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalAvailable());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalAvailable(marketRate.getTotalAvailable() - orderAvl.getTotalOrder());
                        availableOrders.poll();
                    }

                    if(availableOrders.isEmpty() == true){
                        currPairOrders.pollFirstEntry();
                    }

                }




            }




        }else{


            ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>> currPairOrders = bidOrder.get(marketRate.getCurrPair()) ;

            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate <= currPairOrders.lastKey() && orderSuccess==false){

                LinkedBlockingQueue<OrderBean> availableOrders = currPairOrders.get(currPairOrders.lastKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    OrderBean orderAvl = availableOrders.peek();
                    if(marketRate.getTotalAvailable() >= orderAvl.getTotalOrder()){
                        //SELL is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalAvailable());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalAvailable(marketRate.getTotalAvailable() - orderAvl.getTotalOrder());
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

    public boolean matchOrder(OrderBean orderRate){

        boolean orderSuccess=false;
        if(orderRate.getOfferType().equals(MarketRateBean.QuoteType.BID)){

            boolean useMarketRate =( bidMarketRate.get(orderRate.getCurrPair()).firstKey() >= offerOrder.get(orderRate.getCurrPair()).firstKey());

            if(useMarketRate) {
                //do a lock on  bidMarketRate

                ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>> currPairOrders = offerOrder.get(marketRate.getCurrPair()) ;

                double quoteRate = marketRate.getQuoteRate();

                while(quoteRate >= currPairOrders.firstKey() && orderSuccess==false){

                    LinkedBlockingQueue<OrderBean> availableOrders = currPairOrders.get(currPairOrders.firstKey());


                    while(availableOrders.isEmpty()==false
                            || orderSuccess==false){
                        OrderBean orderAvl = availableOrders.peek();
                        if(marketRate.getTotalAvailable() >= orderAvl.getTotalOrder()){
                            //BUY is success

                            orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalAvailable());
                            if(orderAvl.getTotalOrder()==0){
                                availableOrders.poll();
                            }

                            orderSuccess = true;
                        }else{
                            marketRate.setTotalAvailable(marketRate.getTotalAvailable() - orderAvl.getTotalOrder());
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



            ConcurrentSkipListMap<Double, LinkedBlockingQueue<MarketRateBean>> currPairMktOrders = bidMarketRate.get(orderRate.getCurrPair()) ;
            ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>> currPairOrdOrders = offerOrder.get(orderRate.getCurrPair()) ;
            
            
            
            
            
            while(orderSuccess==false  &&  ){





            }



            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate >= currPairOrders.firstKey() && orderSuccess==false){

                LinkedBlockingQueue<OrderBean> availableOrders = currPairOrders.get(currPairOrders.firstKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    OrderBean orderAvl = availableOrders.peek();
                    if(marketRate.getTotalAvailable() >= orderAvl.getTotalOrder()){
                        //BUY is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalAvailable());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalAvailable(marketRate.getTotalAvailable() - orderAvl.getTotalOrder());
                        availableOrders.poll();
                    }

                    if(availableOrders.isEmpty() == true){
                        currPairOrders.pollFirstEntry();
                    }

                }




            }




        }else{


            ConcurrentSkipListMap<Double, LinkedBlockingQueue<OrderBean>> currPairOrders = bidOrder.get(marketRate.getCurrPair()) ;

            double quoteRate = marketRate.getQuoteRate();

            while(quoteRate <= currPairOrders.lastKey() && orderSuccess==false){

                LinkedBlockingQueue<OrderBean> availableOrders = currPairOrders.get(currPairOrders.lastKey());


                while(availableOrders.isEmpty()==false
                        || orderSuccess==false){
                    OrderBean orderAvl = availableOrders.peek();
                    if(marketRate.getTotalAvailable() >= orderAvl.getTotalOrder()){
                        //SELL is success

                        orderAvl.setTotalOrder(orderAvl.getTotalOrder() - marketRate.getTotalAvailable());
                        if(orderAvl.getTotalOrder()==0){
                            availableOrders.poll();
                        }

                        orderSuccess = true;
                    }else{
                        marketRate.setTotalAvailable(marketRate.getTotalAvailable() - orderAvl.getTotalOrder());
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
