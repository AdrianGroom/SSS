import java.util.List;

import org.junit.Test;

public class Test_PERatio {

	private SimpleStockServices SSS = new SimpleStockServices();

	@Test
	public void main() {
		testPERRatioForValidSymbol();
		Logger.outLine("");
	}
		
	// Test the PE ratio method
	private void testPERRatioForValidSymbol() {
		List<GBCEDataStruc> listDataii = SSS.gbceRec.getGBCEData();
		
		Logger.outTest("a(ii) - Calculate PE ratios");
	    try {
			for (GBCEDataStruc GBCEFound : listDataii) {
				Logger.outTest("PE Ratio for " + GBCEFound.stockSymbol + " is " + SSS.gbceRec.getPERatio(GBCEFound.stockSymbol) );
			}
		} catch(Exception exception) {
			Logger.outError(exception.getMessage());
		}
	}

}
