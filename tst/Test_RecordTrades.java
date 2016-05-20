import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Test_RecordTrades {

	private SimpleStockServices SSS = new SimpleStockServices();

	@Test
	public void main() {
		testRecordTrades();
		Logger.outLine("");
	}
		
	// Test the Recording of Trades method
	private void testRecordTrades() {
		long now = System.currentTimeMillis() / 1000;
		long endTimeSecs =  now + (10 * 60);
		long startTimeSecs =  now - (20 * 60);
		Random tradeStockSymbolGenerator = new Random();

		Logger.outTest("a(iii) - Recording Trades");
		for(long seconds=startTimeSecs; seconds<=endTimeSecs; seconds++){
			Date date = new Date(seconds * 1000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = sdf.format(date);
			int ssIndex = tradeStockSymbolGenerator.nextInt(SSS.gbceRec.getRecordCount());
			Logger.outTrace("Data generated for Trade @ " + formattedDate);
			SSS.gbceTradeRec.recordTrade(SSS.gbceRec.getStockSymbolAtIndex(ssIndex), date);
        }
		Logger.outTest("Total Number of Trades recorded = " + SSS.gbceTradeRec.getTradeRecordCount());
		//Check that a condition is true
	    assertEquals(SSS.gbceTradeRec.getTradeRecordCount(), endTimeSecs - startTimeSecs + 1);
	}
}
