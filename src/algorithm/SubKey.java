package algorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class SubKey {
	private ArrayList<Integer> hashOfKey;
	public ArrayList<RoundKey> arrayRoundKey;
	private int roundNumber;
	
	public SubKey(int roundNumber){
		this.roundNumber = roundNumber;
		hashOfKey = new ArrayList<Integer>();
		arrayRoundKey = new ArrayList<>();
	}
	
	public void generateSubKey(ArrayList<Integer> mainKey){
		int firstSeed = getFirstSeed(mainKey);
		int secondSeed = getSecondSeed(mainKey);
		
		hashOfKey = commonOperation.getHash(mainKey.toString());
		hashOfKey = shuffleArrayList(firstSeed, secondSeed, hashOfKey);
		
		for (int i = 0; i < roundNumber; i++) {
			RoundKey roundKey = new RoundKey();		
			roundKey.generateRoundKey(getBlockHash(i), mainKey);
			arrayRoundKey.add(roundKey);
		}
	}

	private ArrayList<Integer> getBlockHash(int i) {
		int firstIdx = i*8;
		return new ArrayList<Integer>(hashOfKey.subList(firstIdx, firstIdx+8 ));
	}

	private ArrayList<Integer> shuffleArrayList(int firstSeed, int secondSeed, ArrayList<Integer> hashOfMsg2) {
		Collections.shuffle(hashOfMsg2, new Random(Long.valueOf(firstSeed)));
		Collections.shuffle(hashOfMsg2, new Random(Long.valueOf(secondSeed)));
		return hashOfMsg2;
	}

	private int getFirstSeed(ArrayList<Integer> mainKey) {
		ArrayList<Integer> arraySeed = commonOperation.XOR(
				new ArrayList<Integer>(mainKey.subList(0, 3)),
				new ArrayList<Integer>(mainKey.subList(3, 6))); 
		int result= Integer.valueOf(arraySeed.get(0)+""+arraySeed.get(1)+
				""+arraySeed.get(2));
		return result;
	}
	
	private int getSecondSeed(ArrayList<Integer> mainKey) {
		Integer seed = commonOperation.XOR(
				(mainKey.get(5)), (mainKey.get(6))); 
		int result= Integer.valueOf(seed+""+mainKey.get(7));
		return result;
	}

	public void print() {
		System.out.println("GENERATED SUBKEY");
		System.out.println("hashOfKey : "+hashOfKey.subList(0, 8).toString());
		System.out.println("hashOfKey : "+hashOfKey.subList(8, 16).toString());
		System.out.println("hashOfKey : "+hashOfKey.subList(16, 24).toString());
		System.out.println("hashOfKey : "+hashOfKey.subList(24, 32).toString());
		for (int i = 0; i < roundNumber; i++) {
			System.out.println("round "+i);
			System.out.println("	subKey4Byte : "+arrayRoundKey.get(i).key4Bytes.toString());
			System.out.println("	subKey5Byte1 : "+arrayRoundKey.get(i).key5Bytes1.toString());
			System.out.println("	subKey5Byte2 : "+arrayRoundKey.get(i).key5Bytes2.toString());
			System.out.println();
		}
	}
	
}
