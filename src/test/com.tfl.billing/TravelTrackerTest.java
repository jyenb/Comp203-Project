package test.com.tfl.billing;

import com.oyster.*;
import com.tfl.billing.*;
import com.tfl.underground.*;
import com.tfl.external.*;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.Date;
import java.math.BigDecimal;
//import org.jmock.Expectations;
//import org.jmock.Mockery;
//import com.tfl.billing;


public class TravelTrackerTest{

    //public void populateDatabases(){
    //  JourneyEvent testJE = new JourneyEvent();
    //}

    @Test
    //tests totalJourneysFor method for if account is charged the right amount depending on if traveling during peak or off peak hours
    public void testChargeAccount(){
        TravelTracker travel = new TravelTracker();

        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        Customer customer = customerDatabase.getCustomers().get(0);
        OysterCard card = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");

        OysterCardReader reader = new OysterCardReader();
        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader kingsCrossReader = OysterReaderLocator.atStation(Station.KINGS_CROSS);

        travel.connect(paddingtonReader, kingsCrossReader);
        paddingtonReader.touch(card);
        kingsCrossReader.touch(card);

        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);

        if(travel.peak(currentDate)){
            assertEquals(3.20, travel.totalJourneysFor(customer).doubleValue(), 0);
        }else{
            assertEquals(2.40, travel.totalJourneysFor(customer).doubleValue(), 0);
        }

    }

    @Test
    //tests if roundToNearestPenny works
    public void testRound(){
        TravelTracker travel = new TravelTracker();
        BigDecimal bigD = new BigDecimal(2.39999);
        assertEquals(2.40, travel.roundToNearestPenny(bigD).doubleValue(), 0);
    }

    @Test
    public void testPeak(){
        TravelTracker travel = new TravelTracker();
        // year month date hour minute
        Date date1 = new Date(2011,12,12,6,0);
        boolean result1 = travel.peak(date1);

        Date date2 = new Date(2011,12,12,0,0);
        boolean result2 = travel.peak(date2);

        Date date3 = new Date(2011,12,12,18,0);
        boolean result3 = travel.peak(date3);

        assertTrue(result1);
        assertFalse(result2);
        assertTrue(result3);
    }

    @Test
    public void validJourney() {


    }


}