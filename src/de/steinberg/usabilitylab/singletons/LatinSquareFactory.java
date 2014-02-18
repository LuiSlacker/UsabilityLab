package de.steinberg.usabilitylab.singletons;

import android.content.Context;

public class LatinSquareFactory {

	private static LatinSquareFactory instance = null;
	
	private int novice = 0;
	private int expert = 0;
	
	private int[][] latinSquare = new int[][]{
			{8,9,0,1,3,2},
			{8,9,1,2,0,3},
			{8,9,2,3,1,0},
			{8,9,3,0,2,1}
	};
	
	private LatinSquareFactory (Context context){
		int tmp_novice = DSPInterfaceOrderPreferences.getInstance(context).loadSavedPreferences("Novice5");
		int tmp_expert = DSPInterfaceOrderPreferences.getInstance(context).loadSavedPreferences("Expert5");
		if (tmp_novice != -1){
			this.novice = tmp_novice;
		}
		if (tmp_novice != -1){
			this.expert = tmp_expert;
		}
	}
	
	public static LatinSquareFactory getInstance(Context context) {
		if (instance == null){
			instance = new LatinSquareFactory(context);
		}
		return instance;
	}
	
	public int getNovice() {
		return novice;
	}
	
	public int getExpert() {
		return expert;
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
