package algorithm;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class Decryption {
	public ArrayList<Integer> chainingOperationAsc(Integer subKey,ArrayList<Integer> blockPlainText) {
		/*Do chaining every 4   bits*/
		int xorResult = commonOperation.XOR(blockPlainText.get(0), subKey);
		ArrayList<Integer> result = new ArrayList<>();
		for (int i = 1; i < blockPlainText.size(); i++) {
			result.add(xorResult);
			xorResult = commonOperation.XOR(xorResult, blockPlainText.get(i));
		}
		result.add(xorResult);
		return result;
	}
	
	public ArrayList<Integer> chainingOperationDesc(Integer subKey,ArrayList<Integer> blockPlainText) {
		/*Do chaining every 4   bits*/
		Collections.reverse(blockPlainText);
		blockPlainText = chainingOperationAsc(subKey,blockPlainText);
		return blockPlainText;
	}

	public ArrayList<Integer> chainingOperation(Integer subKey, ArrayList<Integer> blockPlainText) {
		blockPlainText = chainingOperationAsc(subKey,blockPlainText);
		blockPlainText = chainingOperationDesc(subKey,blockPlainText);
		return blockPlainText;
	}
	
	/*S Box for Decryption*/
	public static ArrayList<Integer> sBoxDec(int [] sBox, ArrayList<Integer> blockPlainText){
		/*To save the result*/
		ArrayList<Integer> result = new ArrayList<Integer>();
		/*To process per byte*/
		for (int i = 0; i < blockPlainText.size(); i++) {
			result.add(sBox[blockPlainText.get(i)]);
		}
		return result;
	}
}
