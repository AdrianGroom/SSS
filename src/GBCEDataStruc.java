// Super Simple Stock data record structure

public class GBCEDataStruc {
	String stockSymbol;   // Stock Symbol
	String type;          // Type - normally common or deferred
	int lastDividend;     // Last Dividend (in pence)
	double fixedDividend; // Fixed Dividend (percentage value eg 2.3 = 2.3%)
	int parValue;         // Face Value of Share (from http://www.investopedia.com/terms/p/parvalue.asp)

	public GBCEDataStruc( String ss, String t, int ld, String fd, int pv ) {
		// Embed the incoming parameters into the record structure
        this.stockSymbol = ss;
        this.type = t;
        this.lastDividend = ld;
        if (fd != "") {
        	this.fixedDividend = Float.parseFloat(fd);
        }
        this.parValue = pv;
	}
}
