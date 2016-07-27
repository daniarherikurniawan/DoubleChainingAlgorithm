package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class RoundKey {
	public ArrayList<Integer> subKey;
	
	public RoundKey(){
		subKey = new ArrayList<Integer>();
	}
	
	public void generateRoundKey(ArrayList<Integer> hashBlock){
		subKey = (ArrayList<Integer>) commonOperation.XOR(hashBlock.subList(0, 4),hashBlock.subList(4, 8));
	}
}
