import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GBCETrade {

    // Create dataset for Stock Trades
	private List<GBCETradeDataStruc> GBCETradeData = new ArrayList<>();
	
	Random tradePriceGenerator = new Random();
	Random tradeTrendGenerator = new Random();
	Random tradeQuantityGenerator = new Random();
	Random tradeBoSGenerator = new Random();

	// Count the number of records in the Stock Trade dataset
	public int getTradeRecordCount() {
		return GBCETradeData.size();			
	}
	
	// Calculate the Trade Quantity between a high and low limit
	private int getTradeQuantity(String ss) throws Exception {
		int quantity;
		int maxQuantity = 10000;
		int minQuantity = 1000;
		
		quantity = tradeQuantityGenerator.nextInt(maxQuantity - minQuantity) + minQuantity + 1;
		Logger.outTrace("Quantity is " + quantity + " for Stock Symbol  " + ss);
		
		return quantity;
	}
	
	// Calculate a trade price based on last trade price
	// This method allows a gradual fluctuation in the trade price
	private int getNewTradePrice(String ss) throws Exception {
		int price;
		int trend;
		
		try {
			GBCETradeDataStruc GBCELastTradeDetails = findLastTradeData(ss);
			trend = tradeTrendGenerator.nextInt(10);
			// Trend is:
			// 0 - No change
			// 1 - 4 inc - Stock has reduced in price
			// otherwise - Stock has increased in price
            switch (trend) {
	        	case 0:
	                Logger.outTrace("Trend is static for Stock Symbol  " + ss);
	                price = GBCELastTradeDetails.price;
	        		break;
	        	case 1:
	        	case 2:
	        	case 3:
	        	case 4:
	                if (GBCELastTradeDetails.price == 1) {
	                	Logger.outTrace("Trend is down however the current price for Stock Symbol " + ss + " is 1 therefore no change will occur");
	                	price = GBCELastTradeDetails.price;
	                } else {
	                	Logger.outTrace("Trend is down (-1) for Stock Type  " + ss);
	                	price = GBCELastTradeDetails.price - 1;
	                }
	        		break;
	        	default:
	                Logger.outTrace("Trend is up (+1) for Stock Symbol  " + ss);
	                price = GBCELastTradeDetails.price + 1;
	        		break;
        	}
		} catch (Exception exception) {
			// No last trade detected
			price = getTradePrice();
		}
		Logger.outTrace("Price for Stock Symbol " + ss + " is set to " + price);
		return price;
	}
	
	// Identifies the last trade price based in the Trade dataset - If there is not one available it will generated one
	public int getLastTradePrice(String ss) {
		int price;
		
		try {
			GBCETradeDataStruc GBCELastTradeDetails = findLastTradeData(ss);
			price = GBCELastTradeDetails.price;
		} catch (Exception exception) {
			// No last trade detected
			price = getTradePrice();
		}
		Logger.outTrace("Current Price for Stock Symbol " + ss + " is " + price);
		return price;
	}
	
	// Generates a BUY or SELL indicator
	private String getNewTradeBoS(String ss) throws Exception {
		int bosInd;
		String bos;
		
		bosInd = tradeBoSGenerator.nextInt(2);
        switch (bosInd) {
        	case 1:
                Logger.outTrace("BoS is SELL for Stock Symbol  " + ss);
                bos = "SELL";
                break;
        	case 0:
                Logger.outTrace("BoS is BUY for Stock Symbol  " + ss);
                bos = "BUY";
        		break;
        	default:
        		throw new Exception("Invalid bos indicator " + bosInd + " has not been located in the Super Simple Stocks system.");
    	}
		return bos;
	}
	
	// Identifies the last record for a Stock Symbol in the Stock Trade dataset
	private GBCETradeDataStruc findLastTradeData(String ss) throws Exception {
		GBCETradeDataStruc GBCETradeFound;
		
		ListIterator<GBCETradeDataStruc> listIterator = GBCETradeData.listIterator(GBCETradeData.size());
		
		while(listIterator.hasPrevious()){
			GBCETradeFound = listIterator.previous();
			if (GBCETradeFound.stockSymbol == ss){
				return GBCETradeFound;
			}
		}
		// Trade not located for Stock Symbol
		throw new Exception("The stock symbol '" + ss + "' has not been located in the Super Simple Stocks Trading system.");
	}
	
	// Identifies the last Date in the Stock Trade dataset
	public Date getLastDateTime() throws Exception {
		GBCETradeDataStruc GBCETradeFound;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ListIterator<GBCETradeDataStruc> listIterator = GBCETradeData.listIterator(GBCETradeData.size());
		if (listIterator.hasPrevious() ) {
			GBCETradeFound = listIterator.previous();
			Logger.outTrace("Last Date set to " + dateFormat.format(GBCETradeFound.dateTime));
			return GBCETradeFound.dateTime;
		} else {
			throw new Exception("No data in the Trading system");
		}
	}
	
	// Identifies the first Date in the Stock Trade dataset
	public Date getFirstDateTime() throws Exception {
		GBCETradeDataStruc GBCETradeFound;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		ListIterator<GBCETradeDataStruc> listIterator = GBCETradeData.listIterator();
		if (listIterator.hasNext() ) {
			GBCETradeFound = listIterator.next();
			Logger.outTrace("First Date set to " + dateFormat.format(GBCETradeFound.dateTime));
			return GBCETradeFound.dateTime;	

		} else {
			throw new Exception("No data in the Trading system");
		}
	}
	
	// Calculate a Trade price between a high and low value
	private int getTradePrice() {
		int tradeLow = 90;
		int tradeHigh = 110;
		int rndTradePrice = tradePriceGenerator.nextInt(tradeHigh - tradeLow) + tradeLow + 1;
		Logger.outTrace("Trade price generated is " + rndTradePrice);
		return rndTradePrice;
	}
	
	// Calculate the Stock Price for a Stock Symbol between a start and end time
	public double calcStockPrice(String ss, Date startTime, Date endTime) throws Exception {
		int tradeQuantity = 0;
		int tradePriceProduct = 0;
		
		for (GBCETradeDataStruc GBCETradeFound : GBCETradeData) {
			if (GBCETradeFound.stockSymbol == ss){
				tradeQuantity = tradeQuantity + GBCETradeFound.quantity;
				tradePriceProduct = tradePriceProduct + (GBCETradeFound.price * GBCETradeFound.quantity);
			}
		}
        if (tradeQuantity == 0) {
        	throw new Exception("Trade quantity for Stock Symbol " + ss + " is 0.  No Stock Price can be calculated");
        } else {
        
        	return tradePriceProduct / tradeQuantity;
        }
	}
	
	// Record a Trace action to the Stock Trade dataset
	public void recordTrade (String ss, Date inDate) {
		try {
			int price = getNewTradePrice(ss);
			Logger.outTrace("New Stock price: " + price + ".");
			GBCETradeData.add(new GBCETradeDataStruc(inDate, ss, getTradeQuantity(ss), price, getNewTradeBoS(ss)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
