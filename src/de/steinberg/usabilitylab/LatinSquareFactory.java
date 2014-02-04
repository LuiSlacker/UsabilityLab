package de.steinberg.usabilitylab;

public class LatinSquareFactory {

	private static LatinSquareFactory instance = null;
	
	private int novice = 2;
	private int expert = 0;
	
	private int[][] latinSquare = new int[][]{
			{0,1,2,3},
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
		
		return (group.equals("novice")) ? latinSquare[novice]:latinSquare[expert];
	}
}
