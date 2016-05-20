public class SimpleStockServices {

	public GBCE gbceRec = new GBCE();
	public GBCETrade gbceTradeRec = new GBCETrade();
	
	// Constructor - used when the Class is used
	SimpleStockServices() {
		gbceRec.addRecord("TEA", "Common", 0, "", 100);
		gbceRec.addRecord("POP", "Common", 8, null, 100);
		gbceRec.addRecord("ALE", "Common", 23, null, 60);
		gbceRec.addRecord("GIN", "Preferred", 8, "2", 100);
		gbceRec.addRecord("JOE", "Common", 13, null, 250);
		
	}
	
	public static void main(String[] args) {
	}
	
	

}
