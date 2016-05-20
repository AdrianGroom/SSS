import java.util.List;

import org.junit.Test;

public class Test_DividendYield {

	private SimpleStockServices SSS = new SimpleStockServices();

	@Test
	public void main() {
		// Testing all the methods required for this demo
		testDividendYieldForValidSymbol();
		Logger.outLine("");
		
	}
		
	// Test the Dividend Yield method
	private void testDividendYieldForValidSymbol() {
		List<GBCEDataStruc> listDatai = SSS.gbceRec.getGBCEData();
		
		Logger.outTest("a(i) - Calculate Divident Yields");
	    try {
			for (GBCEDataStruc GBCEFound : listDatai) {
				Logger.outTest("Dividend Yield for " + GBCEFound.stockSymbol + " is " + SSS.gbceRec.getDividendYield(GBCEFound.stockSymbol) );
			}
		} catch(Exception exception) {
			Logger.outError(exception.getMessage());
		}
	}
	
}
