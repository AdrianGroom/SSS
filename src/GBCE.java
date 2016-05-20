import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GBCE {
	
    // Create dataset for Stock
	private List<GBCEDataStruc> GBCEData = new ArrayList<>();
	
	Random ticketPriceGenerator = new Random();
	
	// Add a record to the Stock data
	public void addRecord( String ss, String t, int ld, Object fd, int pv ) {
		if (fd == null) {
			GBCEData.add(new GBCEDataStruc(ss, t, ld, "", pv));
		} else {
			GBCEData.add(new GBCEDataStruc(ss, t, ld, (String) fd, pv));
		}			
	}
	
	// Get a List of all the Stock datset
	public List<GBCEDataStruc> getGBCEData () {
		return GBCEData;
	}
	
	// Count the number of records in the Stock dataset
	public int getRecordCount() {
		return GBCEData.size();			
	}
	
	// Get the record relating to a particular index in the dataset
	public String getStockSymbolAtIndex(int index) {
		return GBCEData.get(index).stockSymbol;			
	}
	
	// Get the record relating to a particular Stock Symbol
	private GBCEDataStruc findData(String ss) throws Exception {
		for (GBCEDataStruc GBCEFound : GBCEData) {
			Logger.outTrace("Checking " + ss + " against " + GBCEFound.stockSymbol);
            
			if (GBCEFound.stockSymbol == ss){
				return GBCEFound;
			}
		}
		throw new Exception("The stock symbol '" + ss + "' has not been located in the Super Simple Stocks system.");
	}
	
	// Generate a Ticker Price between 1 and 100
	private int getTickerPrice(String ss) {
		int rndTicketPrice = ticketPriceGenerator.nextInt(100)+1;
		//  ss is not needed here - Ticker Price created using a Random Number for testing
		Logger.outTrace("Ticker Price generated is " + rndTicketPrice);
		return rndTicketPrice;
	}
	
	// Calculate the Dividend Yield
	public double getDividendYield(String ss) {
		GBCEDataStruc GBCEFound;
		double dv = (double) 0.0;
		try {
			GBCEFound = findData(ss);
			Logger.outTrace("Searching for the Dividend Yield for Stock Symbol " + ss);
            int tickerPrice = getTickerPrice(ss);  //tickerPrice is in pence
            Logger.outTrace("Ticket Price is " + tickerPrice);
            Logger.outTrace("Found " + ss + " - Type is " + GBCEFound.type);
            // Different calculation required for Common and Preferred Stock
            switch (GBCEFound.type) {
	        	case "Common":
	                Logger.outTrace("calulating " + GBCEFound.lastDividend + " divided by " + tickerPrice);
	        		dv = (double) GBCEFound.lastDividend / tickerPrice;
	        		break;
	        	case "Preferred":
	        		dv = (double) ( GBCEFound.fixedDividend * GBCEFound.parValue ) / tickerPrice;
	        		break;
	        	default:
	        		throw new Exception("The stock type '" + GBCEFound.type + "' has not been located in the Super Simple Stocks system.");
			} 
		} catch (Exception exception) {
			Logger.outError(exception.getMessage());
			Logger.outError("Error calculating Dividend Yield for the Stock Symbol: " + ss + ".");
		} 
		return dv;
	}


	// Calculate the PE Ratio for a Stock Symbol
	public double getPERatio(String ss) {
		GBCEDataStruc GBCEFound;
		double per = (double) 0.0;
		
		try {
			GBCEFound = findData(ss);
			Logger.outTrace("Searching for the PE Ratio for Stock Symbol " + ss);
	        int tickerPrice = getTickerPrice(ss);  //tickerPrice is in pence
	        Logger.outTrace("Ticket Price is " + tickerPrice);
	        Logger.outTrace("calulating " + tickerPrice + " divided by " + GBCEFound.lastDividend);
	        if (GBCEFound.lastDividend != 0 ) {
	        	per = (double) tickerPrice / GBCEFound.lastDividend;
	        } else {
	        	throw new Exception("PE Ratio for Stock Symbol " + ss + " cannot be calculated as the last dividend is 0");
	        }
		} catch(Exception exception) {
			Logger.outError(exception.getMessage());
			Logger.outError("Error calculating PE Ratio for the stock symbol: " + ss + ".");
		} 
		return per;
	}
	
	// Calculate the Geometric Mean for the Stock dataset
	public double calcGeometricMean(int[] inArray) {
		// from http://stackoverflow.com/questions/16450368/how-to-find-the-geometric-mean-of-an-array-of-ints-using-math-pow
		// This version uses logarithms to avoid over- and underflows
		double GM_log = 0.0;
        for (int inArrayInt : inArray) {
        	GM_log += Math.log(inArrayInt);
        }
        return Math.exp(GM_log / inArray.length);
	}
	
}

