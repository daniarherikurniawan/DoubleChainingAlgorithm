package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import commonOperation.commonOperation;

public class Encryption {
	public  ArrayList<Integer> thirdSubtitutionEnc(ArrayList<Integer> arrayList,
			ArrayList<Integer> blockPlainText) {
		// TODO Auto-generated method stub
		return null;
	}

	public  ArrayList<Integer> thirdTransformationEnc(ArrayList<Integer> blockPlainText) {
		// TODO Auto-generated method stub
		return null;
	}

	public  ArrayList<Integer> secondTransformationEnc(ArrayList<Integer> blockPlainText) {
		ArrayList<Integer> result = (ArrayList<Integer>) blockPlainText.clone();
		result.set(0,blockPlainText.get(9));
		result.set(1,blockPlainText.get(10));
		result.set(2,blockPlainText.get(11));
		result.set(3,blockPlainText.get(12));
		result.set(4,blockPlainText.get(13));
		result.set(5,blockPlainText.get(14));
		result.set(6,blockPlainText.get(15));
		result.set(7,blockPlainText.get(4));
		result.set(8,blockPlainText.get(5));
		result.set(9,blockPlainText.get(6));
		result.set(10,blockPlainText.get(7));
		result.set(11,blockPlainText.get(8));
		result.set(12,blockPlainText.get(1));
		result.set(13,blockPlainText.get(2));
		result.set(14,blockPlainText.get(3));
		result.set(15,blockPlainText.get(0));
		
		return result;
	}

	public  ArrayList<Integer> secondSubtitutionEnc(ArrayList<Integer> key5Bytes,
			ArrayList<Integer> blockPlainText) {
		int idx4b = blockPlainText.get(1);
		int idx4c = blockPlainText.get(2);
		int idx4d = blockPlainText.get(3);
		int idx4e = blockPlainText.get(4);
		int idx4f = blockPlainText.get(5);
		
		int idx3a = blockPlainText.get(7);
		int idx3b = blockPlainText.get(8);
		int idx3c = blockPlainText.get(9);
		int idx3d = blockPlainText.get(10);
		int idx3e = blockPlainText.get(11);
		
		idx4b = commonOperation.XOR(idx4b, key5Bytes.get(0));
		idx4c = commonOperation.XOR(idx4c, key5Bytes.get(1));
		idx4d = commonOperation.XOR(idx4d, key5Bytes.get(2));
		idx4e = commonOperation.XOR(idx4e, key5Bytes.get(3));
		idx4f = commonOperation.XOR(idx4f, key5Bytes.get(4));

		blockPlainText.set(1, idx4b);
		blockPlainText.set(2, idx4c);
		blockPlainText.set(3, idx4d);
		blockPlainText.set(4, idx4e);
		blockPlainText.set(5, idx4f);

		idx3a = commonOperation.XOR(idx3a,idx4b);
		idx3b = commonOperation.XOR(idx3b,idx4c);
		idx3c = commonOperation.XOR(idx3c,idx4d);
		idx3d = commonOperation.XOR(idx3d,idx4e);
		idx3e = commonOperation.XOR(idx3e,idx4f);

		blockPlainText.set(7, idx3a);
		blockPlainText.set(8, idx3b);
		blockPlainText.set(9, idx3c);
		blockPlainText.set(10, idx3d);
		blockPlainText.set(11, idx3e);
		
		return blockPlainText;
	}

	public  ArrayList<Integer> firstTransformationEnc(ArrayList<Integer> blockPlainText) {
		ArrayList<Integer> result = (ArrayList<Integer>) blockPlainText.clone();
		result.set(0,blockPlainText.get(9));
		result.set(1,blockPlainText.get(10));
		result.set(2,blockPlainText.get(11));
		result.set(3,blockPlainText.get(12));
		result.set(4,blockPlainText.get(13));
		result.set(5,blockPlainText.get(14));
		result.set(6,blockPlainText.get(15));
		result.set(7,blockPlainText.get(4));
		result.set(8,blockPlainText.get(5));
		result.set(9,blockPlainText.get(6));
		result.set(10,blockPlainText.get(7));
		result.set(11,blockPlainText.get(8));
		result.set(12,blockPlainText.get(1));
		result.set(13,blockPlainText.get(2));
		result.set(14,blockPlainText.get(3));
		result.set(15,blockPlainText.get(0));
		
		return result;
	}

	public  ArrayList<Integer> firstSubtitutionEnc(ArrayList<Integer> key4Bytes,
			ArrayList<Integer> blockPlainText) {
		int idx3a = blockPlainText.get(4);
		int idx3e = blockPlainText.get(8);
		int idx4b = blockPlainText.get(10);
		int idx4f = blockPlainText.get(14);
		int idx1a = blockPlainText.get(0);
		int idx2b = blockPlainText.get(2);
		int idx3c = blockPlainText.get(6);
		int idx4d = blockPlainText.get(12);
		
		/*F function*/
		idx3a = commonOperation.XOR(idx3a, key4Bytes.get(0));
		idx3e = commonOperation.XOR(idx3e, key4Bytes.get(1));
		idx4b = commonOperation.XOR(idx4b, key4Bytes.get(2));
		idx4f = commonOperation.XOR(idx4f, key4Bytes.get(3));
		
		blockPlainText.set(0,commonOperation.XOR(idx1a, idx3a));
		blockPlainText.set(2,commonOperation.XOR(idx2b, idx3e));
		blockPlainText.set(6,commonOperation.XOR(idx3c, idx4b));
		blockPlainText.set(12,commonOperation.XOR(idx4d, idx4f));

		blockPlainText.set(4,idx3a);
		blockPlainText.set(8,idx3e);
		blockPlainText.set(10,idx4b);
		blockPlainText.set(14,idx4f);
		return blockPlainText;
	}
	
	public ArrayList<Integer> chainingOperationAsc(Integer subKey, ArrayList<Integer> blockPlainText) {
		/*Do chaining every 4   bits*/
//		System.out.println(" + "+blockPlainText);
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
	public static ArrayList<Integer> sBoxEnc(ArrayList<Integer> sBox, ArrayList<Integer> blockPlainText){
		/*To save the result*/
		ArrayList<Integer> result = new ArrayList<Integer>();
		/*To process per byte*/
		for (int i = 0; i < blockPlainText.size(); i++) {
			result.add(sBox.get(blockPlainText.get(i)));
		}
		return result;
	}
}
