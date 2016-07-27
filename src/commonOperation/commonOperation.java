package commonOperation;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class commonOperation {
	final static int  blockSize = 16;/*Byte or 128 bit*/
	
	public static ArrayList<Integer> initiateSBox (){
		ArrayList<Integer> box = new ArrayList<Integer>();
		for (int i = 0; i < 256; i++) {
			box.add(i);
		}
		return box;
	}
	
	public static ArrayList<Integer> XOR(ArrayList<Integer> arrayInput, ArrayList<Integer> constant){
		/*To save the cipher text*/
		ArrayList<Integer> cipher = new ArrayList<Integer>();
//		System.out.println(arrayInput.size());
		for (int i = 0 ; i < arrayInput.size() ; i ++){
			/*Operation happens per Byte*/
			BitSet bits = new BitSet(); 
			BitSet bitsConstant = new BitSet(); 
			bits = intToBitSet(arrayInput.get(i));
			bitsConstant = intToBitSet(constant.get(i));
			
			/*The real algorithm begins here*/
			bits.xor(bitsConstant);
			
			/*After binary operation, it will be converted to integer*/
			cipher.add(bitSetToInt(bits));
		}
		return cipher;
	}
	

	public static Integer XOR(Integer integer, Integer integer2) {
		BitSet bits = new BitSet(); 
		BitSet bitsConstant = new BitSet(); 
		bits = intToBitSet(integer);
		bitsConstant = intToBitSet(integer2);
		
		/*The real algorithm begins here*/
		bits.xor(bitsConstant);
		return bitSetToInt(bits);
	}
	
	/*This function convert BitSet to Integer*/
	public static int bitSetToInt(BitSet bitSet){
	    int bitInteger = 0;
	    for(int i = 0 ; i < 32; i++)
	        if(bitSet.get(i))
	            bitInteger |= (1 << i);
	    return bitInteger;
	}
	
	/*This function convert BitSet to Binary*/
	public static String bitSetToBinary(BitSet bitSet){
		int in = bitSetToInt(bitSet);
		return (Integer.toBinaryString(in));
	}

	/*This function convert Long to BitSet*/
	public static BitSet intToBitSet(long value) {
	    BitSet bits = new BitSet();
	    int index = 0;
	    while (value != 0L) {
	      if (value % 2L != 0) {
	        bits.set(index);
	      }
	      ++index;
	      value = value >>> 1;
	    }
	    return bits;
	}
	
	public static ArrayList<Integer> getHash(String txt){
		ArrayList<Integer> result = new ArrayList<>();
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(txt.getBytes("UTF-8"));
			byte[] bytesIV = md.digest();
			for (int i = 0; i < bytesIV.length; i++) {
				BitSet bitsetKey = BitSet.valueOf(new byte[] { bytesIV[i] });
				result.add(commonOperation.bitSetToInt(bitsetKey));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<Integer> convertStringToArrayInt(String txt){
		ArrayList<Integer> result = new ArrayList<>();
		for (int i = 0; i < txt.length(); i++) {
			result.add((int)txt.charAt(i));
		}
		return result;
	}
	
	public static ArrayList<Integer> adjustSizeOfPlaintext(ArrayList<Integer> plainText, int blockSize){
		/*The remaining byte + padding NUL*/
		int numOfPad = blockSize - plainText.size()%blockSize;
		if (numOfPad != blockSize){
			for (int j = 0; j < numOfPad ; j++) {
				plainText.add(0);
			}
		}
		/*Should be times of 16 as the block's size is 16*/
		return plainText;
	}
	
	public static ArrayList<Integer> removePadding(ArrayList<Integer> plainText, int blockSize){
		/*remove Padding*/
		boolean findEndOfPadding = false;
		for (int j = plainText.size() - 1 ; j >= plainText.size() - blockSize -1 && !findEndOfPadding ; j--) {
			if(!findEndOfPadding && plainText.get(j) == 0){
				plainText.remove(j);
			}else{
				findEndOfPadding = true;
			}
		}
		return plainText;
	}

	public static ArrayList<Integer> XOR(List<Integer> subList, List<Integer> subList2) {
		/*To save the cipher text*/
		ArrayList<Integer> cipher = new ArrayList<Integer>();
//		System.out.println(arrayInput.size());
		for (int i = 0 ; i < subList.size() ; i ++){
			/*Operation happens per Byte*/
			BitSet bits = new BitSet(); 
			BitSet bitsConstant = new BitSet(); 
			bits = intToBitSet(subList.get(i));
			bitsConstant = intToBitSet(subList2.get(i));
			
			/*The real algorithm begins here*/
			bits.xor(bitsConstant);
			
			/*After binary operation, it will be converted to integer*/
			cipher.add(bitSetToInt(bits));
		}
		return cipher;
	}

}
