import java.util.Date;

//Super Simple Stock trading data record structure

public class GBCETradeDataStruc {

	Date dateTime;        // Date and Time
	String stockSymbol;   // Stock Symbol
	int quantity;         // How many in the Trade
	int price;            // In pence
	String buyOrSell;     // Buy or sell indicator

	public GBCETradeDataStruc( Date dt, String ss, int q, int p, String bos ) {
		// Embed the incoming parameters into the record structure
        this.dateTime = dt;
        this.stockSymbol = ss;
        this.quantity= q;
        this.price = p;
        this.buyOrSell = bos;
	}
}
