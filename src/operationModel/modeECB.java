package operationModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import algorithm.BurgAlgorithm;
import commonOperation.commonOperation;

public class modeECB {
	final int  blockSize = 16; /*Bytes*/
	final int  iterate = 1;
		
	/*Key with minimum 8 Byte length or 8 characters*
	 * 1 character = 8 bit = 1 Byte*/	
	String key;
	
	/*Plain text that will be encrypted */
	public ArrayList<Integer> plainText;
	/*Chiper text that will be decrypted */
	public ArrayList<Integer> cipherText;
	/*Result text is the result of decrypted cipher text */
	public ArrayList<Integer> resultText;
	
	/*Constructor of modeCFB*/
	public modeECB(String key){
		/*initialization*/
		this.key = key;
//		this.blockSize = key.length();
		this.plainText = new ArrayList<Integer>();
		this.cipherText = new ArrayList<Integer>();
		this.resultText = new ArrayList<Integer>();
	}
	

	
	public ArrayList<Integer> encrypt(String subKey, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		int i = 0;
		for (i = 0 ; i < plainText.size(); i += blockSize){
			ArrayList<Integer> singleBlock = new ArrayList<Integer>();
			
			/*one single block = 8 Bytes*/
			for (int j = 0; j < blockSize; j++) {
				singleBlock.add(plainText.get(i+j));
			}
			
//			singleBlock = newAlgorithm.blockE(subKey, singleBlock);
			/*shift left*/
			result.addAll(singleBlock);	
		}
		return result;
	}

	public ArrayList<Integer> decrypt(String subKey, ArrayList<Integer>  cipherText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		int i = 0;
		System.out.println("dcsi  "+cipherText.size());
		for (i = 0 ; i < cipherText.size() ; i += blockSize){
			ArrayList<Integer> singleBlock = new ArrayList<Integer>();
			/*one single block = 8 Bytes*/
			System.out.println("i  "+cipherText.size());
			for (int j = 0; j < blockSize; j++) {
				singleBlock.add(cipherText.get(i+j));
			}
			
//			singleBlock = newAlgorithm.blockD(subKey, singleBlock);

			result.addAll(singleBlock);			
		}
		
		return result;
	}
	
	
	/*Start the encryption mode ECB*/
	public ArrayList<Integer> startEncryptionModeECB(ArrayList<Integer> plainText){
		plainText = commonOperation.adjustSizeOfPlaintext(plainText, blockSize);
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = (ArrayList<Integer>) plainText.clone();
		String subKey = "";
		for (int i = 0; i < iterate; i++) {
			System.out.println("\niteration "+(i+1)+" for encryption ");
			subKey = commonOperation.getSubKey(key, i);
			result = encrypt(subKey, result);
			System.out.println("success "+result.size());
		}
		
		Map<Integer, Integer> frequency = commonOperation.countFrequency(result);
		
		return result;
	}
	
	/*Start the decryption mode CFB*/
	public ArrayList<Integer> startDecryptionModeECB(ArrayList<Integer> cipherText){

		ArrayList<Integer> result = new ArrayList<Integer>();
		result = (ArrayList<Integer>) cipherText.clone();
		/*Iterate 3 times and generate 3 subKey*/
		String subKey = "";
		for (int i = 0; i < iterate; i++) {
			System.out.println("\niteration "+(i+1)+" for decryption ");
			System.out.println("result  "+result.size());
			subKey = commonOperation.getSubKey(key, iterate-i-1);
			System.out.println("success "+result.size());
			result = decrypt(subKey, result);
			System.out.println("success "+result.size());
		}
		result = commonOperation.removePadding(result, blockSize);
		return result;
	}	
}
