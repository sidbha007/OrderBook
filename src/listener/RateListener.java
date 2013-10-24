package listener;

import com.integral.is.message.MarketRate;
import com.integral.is.message.MarketRateSerializer;
import com.integral.is.message.MarketRateSerializerFactory;
import com.integral.is.message.V1MarketRateSerializer;

import javax.jms.*;

/**
 * Created with IntelliJ IDEA.
 * User: bhaskars
 * Date: 10/17/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class RateListener implements MessageListener {

    private static javax.jms.TopicConnection connect = null;
    private static javax.jms.TopicSession pubSession = null;

    private MarketRateSerializer v1Serializer = new V1MarketRateSerializer();
    //private static TopicSubscriber subscriber1 = null;
    //private static TopicSubscriber subscriber2 = null;
    //private static TopicSubscriber subscriber3 = null;
    //private static javax.jms.Topic topic = null;
    //private static javax.jms.TopicPublisher publisher = null;

    public void onMessage( Message message )
    {

        TextMessage txtMsg = ( TextMessage ) message;
        try
        {
            System.out.println( "onMessage: " + txtMsg.getText() );
        }
        catch ( JMSException e )
        {
            e.printStackTrace();
        }


        MarketRate rate = null;
        try
        {
            rate = v1Serializer.deserialize( txtMsg.getText() );
            if ( rate == null )
            {
                System.out.println( "Rate is null for Adaptor ");

            }else{
                System.out.println( rate.getBaseCcy() + " " + rate.getQuoteId() + " " + rate.getBaseCcy() + " " + rate.getBidLimit() + " " + rate.getBaseCcyIndex() + " " + rate.getProviderShortName() + " " + rate.getAllTiers() + " " + rate.getQuoteId() + " " + rate.getQuoteId() + " " + rate.getQuoteId() + " " + rate.getQuoteId() + " " + rate.getQuoteId() + " " + rate.getQuoteId());
            }
        }
        catch ( Exception ex )
        {

            System.out.println(  "Rate parsing failed" );
        }

    }

    private void setConn()
    {
        if ( connect != null )
        {
            return;
        }

        try
        {
            String broker = "tcp://mvsonic:2507";
            String username = "integral";
            String password = "integral";

            progress.message.jclient.TopicConnectionFactory factory;
            factory = ( new
                    progress.message.jclient.TopicConnectionFactory() );

            factory.setBrokerURL( broker );
            factory.setDefaultUser( "integral" );
            factory.setDefaultPassword( "integral" );
            connect = factory.createTopicConnection();
            pubSession =
                    connect.createTopicSession( false, javax.jms.Session.AUTO_ACKNOWLEDGE );

            //topic =
            //         pubSession.createTopic( "GLOBAL.IS.25.MV.TOIS.RATES.GRP77" );
            //subscriber1 =
            //        pubSession.createSubscriber(  pubSession.createTopic( "GLOBAL.IS.25.MV.TOIS.RATES.GRP77" ) );
            //subscriber1.setMessageListener( this );

            pubSession.createSubscriber(  pubSession.createTopic( "GLOBAL.IS.25.MV.TOIS.RATES.GRP77" ) ).setMessageListener( this );
            pubSession.createSubscriber(  pubSession.createTopic( "GLOBAL.IS.25.MV.TOIS.RATES.GRP76" ) ).setMessageListener( this );
            pubSession.createSubscriber(  pubSession.createTopic( "GLOBAL.IS.25.MV.TOIS.RATES.GRP78" ) ).setMessageListener( this );
            //publisher = pubSession.createPublisher( topic );
            // Now that setup is complete, start the connection.
            connect.start();
        }
        catch ( Exception e )
        {
            System.out.println("Exception when creating a connection:\n" + e.getLocalizedMessage());
        }
    }

    public RateListener(){
        setConn();
    }



    public static void main(String params[]){
        new RateListener();

    }


}
