package operationModel;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import algorithm.RoundKey;
import algorithm.SubKey;
import algorithm.DoubleChainingAlgorithm;
import commonOperation.commonOperation;

public class modeCBC {
	final int  blockSize = 16; /*Bytes*/
	/*SHA256 of the text*/
	ArrayList<Integer> hashOfMainKey;
	final int  roundNumber = 4;
	
	/*Key with minimum 8 Byte length or 8 characters*
	 * 1 character = 8 bit = 1 Byte*/	
	String mainKey;
	/*Plain text that will be encrypted */
	public ArrayList<Integer> plainText;
	/*Chiper text that will be decrypted */
	public ArrayList<Integer> cipherText;
	/*Result text is the result of decrypted cipher text */
	public ArrayList<Integer> resultText;
	
	/*Constructor of modeCBC*/
	public modeCBC(String key){
		this.mainKey = key;
		this.plainText = new ArrayList<Integer>();
		this.cipherText = new ArrayList<Integer>();
		this.resultText = new ArrayList<Integer>();
		this.hashOfMainKey = commonOperation.getHash(key);
	}
	
	public ArrayList<Integer> encrypt(RoundKey roundKey, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> constant = hashOfMainKey;
		DoubleChainingAlgorithm DC = new DoubleChainingAlgorithm();
		/*Proceed per block*/
		for (int i = 0 ; i < plainText.size() ; i += blockSize){
			/*one single block = 8 Bytes*/
			ArrayList<Integer> singleBlock = new ArrayList<Integer>();
			singleBlock.addAll(plainText.subList(i, i+blockSize));

			/*XOR before doing encription*/
			singleBlock = commonOperation.XOR( singleBlock, constant);
			/*Encription per block*/
			singleBlock = DC.blockE(roundKey, singleBlock);

			/*Collect the result (cipher)*/
			result.addAll(singleBlock);	
			
			/*Update the constant as CBC rules*/
			constant = singleBlock;
		}
		return result;
	}

	public ArrayList<Integer> decrypt(RoundKey roundKey, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();		
		ArrayList<Integer> constant = new ArrayList<Integer>();
		DoubleChainingAlgorithm DC = new DoubleChainingAlgorithm();
		for (int i = plainText.size() ; i >= blockSize ; i -= blockSize){	
			ArrayList<Integer> currentBlock= new ArrayList<Integer>(plainText.subList(i-16, i));

			ArrayList<Integer> prevBlock;
			if(i == blockSize){
				prevBlock= new ArrayList<>( hashOfMainKey.subList(0, blockSize));
			}else{
				prevBlock= new ArrayList<Integer>(plainText.subList(i-32, i-16));
			}
			/*one single block = 4 Bytes*/

			/*clone is needed to prevent memory level modification*/
			currentBlock =  DC.blockD(roundKey, currentBlock);	
			
			constant = (ArrayList<Integer>) prevBlock.clone();
			
			/*XOR after doing encription*/
			currentBlock = commonOperation.XOR( currentBlock, constant);

			/*Save the result (cipher)*/
			Collections.reverse(currentBlock);
			result.addAll(currentBlock);	
		}
		Collections.reverse(result);
		return result;
	}
	
	/*Start the encryption mode CBC*/
	public ArrayList<Integer> startEncryptionModeCBC(ArrayList<Integer> plainText){
		plainText = commonOperation.adjustSizeOfPlaintext(plainText, blockSize);
		ArrayList<Integer> result = (ArrayList<Integer>) plainText.clone();

		SubKey subKey = new SubKey(roundNumber, mainKey);
		subKey.generateSubKey();
//		subKey.print();
		/*algorithm started*/
		for (int j = 0; j < 2; j++) {
			// 4 times iteration Ek === important
			for (int i = 0; i < roundNumber; i++) {
//				System.out.println("no : "+i+"  "+subKey.arrayRoundKey.get(i).subKey);
				result = encrypt(subKey.arrayRoundKey.get(i), result);
			}
			Collections.reverse(result);
		}
		return result;
	}
	

	/*Start the decryption mode CBC*/
	public ArrayList<Integer> startDecryptionModeCBC(ArrayList<Integer> plainText){
		ArrayList<Integer> result = (ArrayList<Integer>) plainText.clone();

		SubKey subKey = new SubKey(roundNumber, mainKey);
		subKey.generateSubKey();
		/*algorithm started*/
		for (int j = 0; j < 2; j++) {
			Collections.reverse(result);
			for (int i = 0; i < roundNumber; i++) {
				result = decrypt(subKey.arrayRoundKey.get(roundNumber-i-1), result);
			}
			result = commonOperation.removePadding(result, blockSize);
		}
		return result;
	}
	
}
