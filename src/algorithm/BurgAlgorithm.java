package algorithm;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

import commonOperation.commonOperation;


public class BurgAlgorithm {
	ArrayList<Integer> sBox;
	Encryption encryption ;
	Decryption decryption ;
	
	public BurgAlgorithm(){
		sBox = commonOperation.initiateSBox();
		encryption = new Encryption();
		decryption = new Decryption();
		Collections.shuffle(sBox, new Random(Long.valueOf(commonOperation.count1bit(""))));
	}
	/*To encrypt*/
	public   ArrayList<Integer> blockE(RoundKey roundKey, ArrayList<Integer> blockPlainText){
		for (int i = 0; i < 5; i++) {
			blockPlainText = encryption.chainingOperation( roundKey.key5Bytes2.get(i),blockPlainText);
		}
		blockPlainText = encryption.sBoxEnc(sBox, blockPlainText);
		return blockPlainText;
	}

	/*To decrypt*/
	public   ArrayList<Integer> blockD(RoundKey roundKey, ArrayList<Integer> blockPlainText){
		blockPlainText = decryption.sBoxDec(sBox, blockPlainText);
		for (int i = 0; i < 5; i++) {
			blockPlainText = decryption.chainingOperation(roundKey.key5Bytes2.get(4-i), blockPlainText);
		}
		return blockPlainText;
	}
}