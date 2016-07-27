package algorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class SubKey {
	private ArrayList<Integer> hashOfKey;
	public ArrayList<RoundKey> arrayRoundKey;
	String mainKey;
	private int roundNumber;
	
	public SubKey(int roundNumber,String mainKey){
		this.roundNumber = roundNumber;
		this.mainKey = mainKey;
		hashOfKey = new ArrayList<Integer>();
		arrayRoundKey = new ArrayList<>();
	}
	
	public void generateSubKey(){		
		hashOfKey = commonOperation.getHash(mainKey.toString());
		for (int i = 0; i < roundNumber; i++) {
			RoundKey roundKey = new RoundKey();		
			roundKey.generateRoundKey(getBlockHash(i));
			arrayRoundKey.add(roundKey);
		}
	}

	private ArrayList<Integer> getBlockHash(int i) {
		int firstIdx = i*8;
		return new ArrayList<Integer>(hashOfKey.subList(firstIdx, firstIdx+8 ));
	}

	public void print() {
		System.out.println("GENERATED SUBKEY");
		System.out.println("hashOfKey : "+hashOfKey.subList(0, 8).toString());
		System.out.println("hashOfKey : "+hashOfKey.subList(8, 16).toString());
		System.out.println("hashOfKey : "+hashOfKey.subList(16, 24).toString());
		System.out.println("hashOfKey : "+hashOfKey.subList(24, 32).toString());
		for (int i = 0; i < roundNumber; i++) {
			System.out.println("round "+i);
			System.out.println("	subKey : "+arrayRoundKey.get(i).subKey.toString());
			System.out.println();
		}
	}
	
}
