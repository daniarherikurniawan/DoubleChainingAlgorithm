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
	
	public static int count1bit(String str){
		byte[] byteOfKey = str.getBytes(StandardCharsets.UTF_8);
		int sum = 0;
		for (int i = 0; i < byteOfKey.length; i++) {
			BitSet bitsetKey = BitSet.valueOf(new byte[] { byteOfKey[i] });
			sum += bitsetKey.length();
		}
		return sum;
	}
	
	
	public static ArrayList<Integer> shiftLeftPerByte(ArrayList<Integer> input){
		String padding = "00000000";

		ArrayList<Integer>  result = new ArrayList<Integer>() ;
		for (int j = 0; j < input.size(); j++) {
			/*ECB 8-bit -> this loop will encrypt per character*/
			String originalCipher = Integer.toBinaryString(input.get(j));
			if(originalCipher.length() < 8)
				originalCipher = padding.substring(originalCipher.length()) + originalCipher;

			/*Shift the bit to the left for one bit*/
			String shiftedCipher = originalCipher.substring(1);
			shiftedCipher += originalCipher.charAt(0);
			
			/*insert the final cipher to the result*/
			int value = Integer.parseInt(shiftedCipher, 2);		

			result.add(value);	
		}	
		
		return result;
	}
	
	public static ArrayList<Integer> shiftRightPerByte(ArrayList<Integer> input){
		String padding = "00000000";

		ArrayList<Integer>  result = new ArrayList<Integer>() ;
		for (int j = 0; j < input.size(); j++) {
			/*shift right*/
			String originalCipher = Integer.toBinaryString(input.get(j));
			if(originalCipher.length() < 8)
				originalCipher = padding.substring(originalCipher.length()) + originalCipher;

			/*Shift the bit to the right for one bit*/
			String shiftedCipher = originalCipher.substring(0,7);
			shiftedCipher = originalCipher.charAt(7)+ shiftedCipher;
			
			/*insert the final cipher to the result*/
			int value = Integer.parseInt(shiftedCipher, 2);		

			result.add(value);	
		}	
		
		return result;
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

	public static String convertToString (ArrayList<Integer> array, int blockSize){
		String str = "";
		for (int i = 0; i < blockSize; i++) {
			int val = Integer.valueOf(array.get(i));
			str += Character.toString((char)val);
		}
		return str;
	}
	
	public static String getSubKey(String key, int i){
		int numOf1Bit = commonOperation.count1bit(key);
		/*Initiate with 255 char*/
		ArrayList<Integer> subKeyInt = commonOperation.initiateSBox();
		String subKey = "";
		
		byte[] byteOfKey = key.getBytes(StandardCharsets.UTF_8);
				
		/*i max = 3*/
		for (int j = 0; j <= i; j++) {
			Collections.shuffle(subKeyInt, new Random(Long.valueOf(numOf1Bit)));
			
			/*Shuffled subkey will be xor with original key*/
			subKeyInt = commonOperation.byteXOR(subKeyInt, byteOfKey);
			
			/*Convert to string*/
			subKey = convertToString(subKeyInt, key.length());
			numOf1Bit = commonOperation.count1bit(subKey);
//			System.out.println((subKey));
		}		
//		System.out.println((" "));
		return subKey;
	}

	
	public static <K, V extends Comparable<? super V>> Map<K, V> 
    sortByValueAsc( Map<K, V> map ){
	     Map<K,V> result = new LinkedHashMap<>();
	     Stream <Entry<K,V>> st = map.entrySet().stream();
	
	     st.sorted(Comparator.comparing(e -> e.getValue()))
	          .forEachOrdered(e ->result.put(e.getKey(),e.getValue()));
	
	     return result;
	}
	
	static <K,V extends Comparable<? super V>> 
    List<Entry<K, V>> sortByValueDesc(Map<K,V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());
		
		Collections.sort(sortedEntries, 
		    new Comparator<Entry<K,V>>() {
		        @Override
		        public int compare(Entry<K,V> e1, Entry<K,V> e2) {
		            return e2.getValue().compareTo(e1.getValue());
		        }
		    }
		);
		
		return sortedEntries;
	}
	

	public static  Map<Integer, Integer> countFrequency(ArrayList<Integer> cipher){
		Map<Integer, Integer> frequency = new HashMap<Integer, Integer>();
		for (int i = 0; i < 256; i++) {
			frequency.put(i,Collections.frequency(cipher, i));
		}
		System.out.println(commonOperation.sortByValueDesc(frequency));
		return frequency;
	}
	
	
	public static ArrayList<Integer> byteXOR(ArrayList<Integer> arrayInput, byte[] byteOfKey){
		/*To save the cipher text*/
		ArrayList<Integer> cipher = new ArrayList<Integer>();

		for (int i = 0 ; i < byteOfKey.length; i ++){
			/*Operation happens per Byte*/
			BitSet bits = new BitSet(); 
			bits = intToBitSet(arrayInput.get(i));
			BitSet bitsConstant = BitSet.valueOf(new byte[] { byteOfKey[i] });
			
			/*The real algorithm begins here*/
			bits.xor(bitsConstant);
			
			/*After binary operation, it will be converted to integer*/
			cipher.add(bitSetToInt(bits));
		}
		return cipher;
	}
	
	/*This function will convert String to Hexadecimal */
	public static String stringToHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}
	
	/*This function will give space between two consequence characters as Hexadecimal format*/
	public static String formatHex(String strHex){
		StringBuilder str = new StringBuilder(strHex);
		int idx = str.length() - 2;

		while (idx > 0)
		{
		    str.insert(idx, " ");
		    idx = idx - 2;
		}
		return str.toString();
	}
	
	/*This function will insert space every 8 characters to represent one Byte*/
	public static String formatByte(String strByte){
		StringBuilder str = new StringBuilder(strByte);
		int idx = str.length() - 8;

		while (idx > 0)
		{
		    str.insert(idx, " ");
		    idx = idx - 8;
		}
		return str.toString();
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

}
