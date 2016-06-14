package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class RoundKey {
	public ArrayList<Integer> key5Bytes1;
	public ArrayList<Integer> key5Bytes2;
	public ArrayList<Integer> key4Bytes;
	
	public RoundKey(){
		key5Bytes1 = new ArrayList<Integer>();
		key5Bytes2 = new ArrayList<Integer>();
		key4Bytes = new ArrayList<Integer>();
	}
	
	public void generateRoundKey(ArrayList<Integer> hashBlock, ArrayList<Integer> mainKey){
		ArrayList<Integer> xorResult = commonOperation.XOR(hashBlock, mainKey);
		ArrayList<Integer> xorResult2 = commonOperation.XOR(xorResult, mainKey);
		
		xorResult.addAll(xorResult2);
		xorResult = shuffleXorResult(xorResult);
		
		key5Bytes1 = new ArrayList<Integer>(xorResult.subList(0, 5));
		key5Bytes2 = new ArrayList<Integer>(xorResult.subList(5, 10));
		key4Bytes = new ArrayList<Integer>(xorResult.subList(10, 14));
	}

	private ArrayList<Integer> shuffleXorResult(ArrayList<Integer> xorResult) {
		int seed = Integer.valueOf(xorResult.get(0)+""+ xorResult.get(15));
		Collections.shuffle(xorResult, new Random(Long.valueOf(seed)));
		return xorResult;
	}
}
