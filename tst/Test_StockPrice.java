import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class Test_StockPrice {

	private SimpleStockServices SSS = new SimpleStockServices();
	long now = System.currentTimeMillis() / 1000;
	Date endTime =  new Date(now * 1000);
	Date startTime =  new Date((now - (15 * 60)) * 1000);

	@Test
	public void main() {
		setupTrades();
		
		testStockPriceForValidSymbol();
		Logger.outLine("");
		
		testStockPriceForInvalidSymbol();
		Logger.outLine("");
	}
		
	// Setup the dataset of Trades
	private void setupTrades() {
		long now = System.currentTimeMillis() / 1000;
		long endTimeSecs =  now + (10 * 60);
		long startTimeSecs =  now - (20 * 60);
		Random tradeStockSymbolGenerator = new Random();

		Logger.outTrace("a(iv) - Recording Trades");
		for(long seconds=startTimeSecs; seconds<=endTimeSecs; seconds++){
			Date date = new Date(seconds * 1000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = sdf.format(date);
			int ssIndex = tradeStockSymbolGenerator.nextInt(SSS.gbceRec.getRecordCount());
			Logger.outTrace("Data generated for Trade @ " + formattedDate);
			SSS.gbceTradeRec.recordTrade(SSS.gbceRec.getStockSymbolAtIndex(ssIndex), date);
        }
	}
	
	// Test the Stock Price method
	private void testStockPriceForValidSymbol() {
		Logger.outTest("a(iv) - Calculate Stock Price (over last 15 mins)");
		for (int ssIndex=0; ssIndex<SSS.gbceRec.getRecordCount(); ssIndex++){
			try {
					Logger.outTest("Stock Symbol " + SSS.gbceRec.getStockSymbolAtIndex(ssIndex) + " has a Stock Price of " + SSS.gbceTradeRec.calcStockPrice(SSS.gbceRec.getStockSymbolAtIndex(ssIndex), startTime, endTime));
			} catch(Exception exception) {
				Logger.outError(exception.getMessage());
			}
		}
	}
	
	// Test the Stock Price with an invalid Symbol method
	private void testStockPriceForInvalidSymbol() {
		Logger.outTest("a(iv)extra - Calculate Stock Price (over last 15 mins) for an invalid Stock Symbol");
		try {
			Logger.outTest("Stock Symbol ADE has a Stock Price of " + SSS.gbceTradeRec.calcStockPrice("ADE", startTime, endTime));
		} catch(Exception exception) {
			Logger.outError(exception.getMessage());
		}
	}
}
