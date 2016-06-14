package operationModel;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import algorithm.BurgAlgorithm;
import commonOperation.commonOperation;

public class modeCFB {
	/*Initialization Vector*/
	ArrayList<Integer> IV;

	int  blockSize = 16;/*8 Bytes*/
	final int  iterate = 3;
	
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
	public modeCFB(String key, ArrayList<Integer>  plainText){
		/*initialization*/
		this.key = key;
		this.blockSize = key.length();
		this.plainText = new ArrayList<Integer>();
		this.cipherText = new ArrayList<Integer>();
		this.resultText = new ArrayList<Integer>();
		this.IV = new ArrayList<Integer>();
		this.plainText = plainText;
		
		/*set Initialization Vector from sha-256 of the key*/
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(plainText.toString().getBytes("UTF-8"));
			byte[] bytesIV = md.digest();
			for (int i = 0; i < bytesIV.length; i++) {
				BitSet bitsetKey = BitSet.valueOf(new byte[] { bytesIV[i] });
				/*32 Bytes*/
				this.IV.add(commonOperation.bitSetToInt(bitsetKey));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public ArrayList<Integer> encrypt(String key, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> constant = new ArrayList<Integer>();
		constant.addAll(IV.subList(0, blockSize));
		int i = 0;
		for (i = 0 ; i < plainText.size() ; i ++){
			
			/*adjust the size of block to be sent for encryption
			 * in this case we assume that one block is one Byte*/
			ArrayList<Integer> singleBlock = new ArrayList<Integer>();

			/*one single block = 4 Bytes*/
//			singleBlock = newAlgorithm.blockE(key, constant);
			
			/*CFB 8-bit -> this loop will encrypt per character*/
			/*XOR singleblock with plaintext(8 bit)*/
			ArrayList<Integer> LMB= new ArrayList<Integer>();
			LMB.add(plainText.get(i));
			LMB = commonOperation.XOR(LMB,singleBlock);
			result.addAll(LMB);
			
			/*Update the constant as CFB rules*/
			constant.set(blockSize -1 - ( i % blockSize), LMB.get(0));
		}
		
		return result;
	}

	public ArrayList<Integer> decrypt(String key, ArrayList<Integer>  plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Integer> constant = new ArrayList<Integer>();
		constant.addAll(IV.subList(0, blockSize));
		int i = 0;
		for (i = 0 ; i < plainText.size() ; i ++){
			
			/*adjust the size of block to be sent for encryption
			 * in this case we assume that one block is one Byte*/
			ArrayList<Integer> singleBlock = new ArrayList<Integer>();

			/*one single block */
//			singleBlock = newAlgorithm.blockE(key, constant);
			
			/*CFB 8-bit -> this loop will encrypt per character*/
			/*XOR singleblock with plaintext(8 bit)*/
			ArrayList<Integer> LMB= new ArrayList<Integer>();
			LMB.add(plainText.get(i));
			result.addAll(commonOperation.XOR(LMB,singleBlock));
			
			/*Update the constant as CFB rules*/
			/*LMB in this section is not the result from XOR*/
			constant.set(blockSize -1 - ( i % blockSize), LMB.get(0));
		}
		
		return result;
	}
	
	
	/*Start the encryption mode CFB*/
	public ArrayList<Integer> startEncryptionModeCFB(ArrayList<Integer> plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = (ArrayList<Integer>) plainText.clone();
		String subKey = "";
		for (int i = 0; i < iterate; i++) {
			subKey = commonOperation.getSubKey(key, i);
			result = encrypt(subKey, result);
		}
		
		Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
		frequency = commonOperation.countFrequency(result);
		return result;
	}
	
	/*Start the encryption mode CFB*/
	public ArrayList<Integer> startDecryptionModeCFB(ArrayList<Integer> plainText){
		ArrayList<Integer> result = new ArrayList<Integer>();
		result = (ArrayList<Integer>) plainText.clone();
		String subKey = "";
		for (int i = 0; i < iterate; i++) {
			subKey = commonOperation.getSubKey(key, iterate-i-1);
			result = decrypt(subKey, result);
		}
		return result;
	}
	
}
