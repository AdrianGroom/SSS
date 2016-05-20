import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Test_AllShareIndex {

	private SimpleStockServices SSS = new SimpleStockServices();

	@Test
	public void main() {
		setupTrades();
		testAllShareIndex();
		Logger.outLine("");
	}
		
	// Setup the dataset of Trades
	private void setupTrades() {
		long now = System.currentTimeMillis() / 1000;
		long endTimeSecs =  now + (10 * 60);
		long startTimeSecs =  now - (20 * 60);
		Random tradeStockSymbolGenerator = new Random();

		Logger.outTrace("b) - Recording Trades");
		for(long seconds=startTimeSecs; seconds<=endTimeSecs; seconds++){
			Date date = new Date(seconds * 1000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = sdf.format(date);
			int ssIndex = tradeStockSymbolGenerator.nextInt(SSS.gbceRec.getRecordCount());
			Logger.outTrace("Data generated for Trade @ " + formattedDate);
			SSS.gbceTradeRec.recordTrade(SSS.gbceRec.getStockSymbolAtIndex(ssIndex), date);
        }
	}
	
	// Test the All share Index method
	private void testAllShareIndex() {
		int[] stockPrice = new int[SSS.gbceRec.getRecordCount()];
		
		Logger.outTest("b - Calculate GBCE All Share Index (Using the Stock recordset)");
		for (int ssIndex=0; ssIndex<SSS.gbceRec.getRecordCount(); ssIndex++){
			stockPrice[ssIndex] = SSS.gbceTradeRec.getLastTradePrice(SSS.gbceRec.getStockSymbolAtIndex(ssIndex));
			Logger.outTest("Stock Symbol " + SSS.gbceRec.getStockSymbolAtIndex(ssIndex) + " has a current Stock Price of " + stockPrice[ssIndex]);
		}
	    int[] testArray = {2, 4, 8};
	    Logger.outTrace("GBCE All Share Index (for 2, 4, 8 should equal 4) = " +  SSS.gbceRec.calcGeometricMean(testArray));
	    assertEquals(SSS.gbceRec.calcGeometricMean(testArray), 4.0, 0.001);

		Logger.outTest("GBCE All Share Index (for all StocK Symbols) = " +  SSS.gbceRec.calcGeometricMean(stockPrice));
	}
}
