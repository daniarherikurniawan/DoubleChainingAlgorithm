package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class Encryption {
	public ArrayList<Integer> chainingOperationAsc(Integer subKey, ArrayList<Integer> blockPlainText) {
		/*Do chaining every 4   bit*/
		Collections.reverse(blockPlainText);
		ArrayList<Integer> result = new ArrayList<>();
		int xorResult =blockPlainText.get(0);
		for (int i = 0; i < blockPlainText.size()-1; i++) {
			xorResult = commonOperation.XOR(blockPlainText.get(i), blockPlainText.get(i+1));
			result.add(xorResult);
		}
		result.add( commonOperation.XOR(subKey,blockPlainText.get(15)));
		Collections.reverse(result);
		return result;
	}

	public ArrayList<Integer> chainingOperationDesc(Integer subKey, ArrayList<Integer> blockPlainText) {
		/*Do chaining every 4   bits*/
		blockPlainText = chainingOperationAsc(subKey, blockPlainText);
		Collections.reverse(blockPlainText);
		return blockPlainText;
	}

	public ArrayList<Integer> chainingOperation(Integer subKey, ArrayList<Integer> blockPlainText) {
		blockPlainText = chainingOperationDesc(subKey, blockPlainText);
		blockPlainText = chainingOperationAsc(subKey,blockPlainText);
		return blockPlainText;
	}
	
	/*S Box for Encryption*/
	public static ArrayList<Integer> sBoxEnc( int[] sBox, ArrayList<Integer> blockPlainText){
		/*To save the result*/
		ArrayList<Integer> result = new ArrayList<Integer>();
		/*To process per byte*/
		for (int i = 0; i < blockPlainText.size(); i++) {
			result.add(sBox[blockPlainText.get(i)]);
		}
		return result;
	}
}
