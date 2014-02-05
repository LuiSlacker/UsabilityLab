package de.steinberg.usabilitylab;

public class LatinSquareFactory {

	private static LatinSquareFactory instance = null;
	
	private int novice = 0;
	private int expert = 0;
	
	private int[][] latinSquare = new int[][]{
			{0,1,3,2},
			{1,2,0,3},
			{2,3,1,0},
			{3,0,2,1}
	};
	
	private LatinSquareFactory (){}
	
	public static LatinSquareFactory getInstance() {
		if (instance == null){
			instance = new LatinSquareFactory();
		}
		return instance;
	}
	
	public int[] getInterfaceOrder(String group) {
		
		int[] tmp;
		if(group.equals("novice")) {
			tmp = latinSquare[novice];
			novice++;
		} else {
			tmp = latinSquare[expert];
			expert++;
		}
		if(novice == 4) {
			novice = 0;
		}
		if (expert == 4){
			expert = 0;
		}
		return tmp;
	}
}
