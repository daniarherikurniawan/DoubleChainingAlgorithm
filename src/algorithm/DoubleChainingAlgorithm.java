package algorithm;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

import commonOperation.commonOperation;

public class DoubleChainingAlgorithm {
	int[] sBox = {
			254, 255, 98, 130, 194, 54, 132, 58, 36, 99, 12, 52, 
			121, 33, 22, 38, 191, 68, 61, 221, 60, 114, 208, 131, 
			189, 87, 171, 150, 201, 169, 34, 0, 46, 11, 70, 177, 
			63, 213, 214, 166, 53, 90, 158, 101, 47, 199, 122, 75, 
			137, 94, 78, 97, 110, 51, 163, 27, 115, 246, 193, 93,
			55, 219, 50, 197, 15, 102, 209, 174, 73, 154, 119, 112, 
			217, 238, 6, 29, 80, 66, 235, 19, 159, 143, 245, 212, 
			251, 10, 243, 202, 40, 83, 9, 138, 165, 224, 85, 100, 
			91, 120, 244, 16, 59, 134, 18, 65, 180, 207, 105, 113,
			247, 232, 190, 149, 64, 35, 222, 182, 108, 178, 225, 5, 
			206, 21, 161, 45, 228, 184, 164, 215, 86, 24, 62, 118, 
			129, 144, 233, 181, 107, 84, 95, 106, 79, 151, 148, 204, 
			30, 239, 136, 44, 196, 229, 152, 186, 240, 13, 168, 92, 
			218, 67, 141, 210, 242, 146, 220, 116, 200, 23, 39, 69, 
			140, 49, 135, 133, 147, 183, 227, 198, 157, 205, 128, 77, 
			234, 250, 249, 81, 72, 170, 237, 162, 89, 1, 176, 96, 
			48, 109, 43, 175, 127, 111, 3, 203, 226, 236, 103, 8,
			252, 153, 42, 32, 2, 124, 223, 248, 26, 17, 25, 56,
			57, 216, 185, 125, 71, 104, 76, 230, 20, 31, 160, 192, 
			195, 173, 37, 188, 117, 74, 88, 123, 155, 139, 231, 4, 
			14, 253, 172, 145, 142, 41, 211, 7, 126, 241, 156, 179, 
			82, 167, 28, 187};
	
	int[] sBoxInverse = {
			31, 189, 208, 198, 239, 119, 74, 247, 203, 90, 85, 33,
			10, 153, 240, 64, 99, 213, 102, 79, 224, 121, 14, 165, 
			129, 214, 212, 55, 254, 75, 144, 225, 207, 13, 30, 113, 
			8, 230, 15, 166, 88, 245, 206, 194, 147, 123, 32, 44,
			192, 169, 62, 53, 11, 40, 5, 60, 215, 216, 7, 100,
			20, 18, 130, 36, 112, 103, 77, 157, 17, 167, 34, 220, 
			184, 68, 233, 47, 222, 179, 50, 140, 76, 183, 252, 89, 
			137, 94, 128, 25, 234, 188, 41, 96, 155, 59, 49, 138,
			191, 51, 2, 9, 95, 43, 65, 202, 221, 106, 139, 136, 
			116, 193, 52, 197, 71, 107, 21, 56, 163, 232, 131, 70, 
			97, 12, 46, 235, 209, 219, 248, 196, 178, 132, 3, 23, 
			6, 171, 101, 170, 146, 48, 91, 237, 168, 158, 244, 81, 
			133, 243, 161, 172, 142, 111, 27, 141, 150, 205, 69, 236,
			250, 176, 42, 80, 226, 122, 187, 54, 126, 92, 39, 253,
			154, 29, 185, 26, 242, 229, 67, 195, 190, 35, 117, 251, 
			104, 135, 115, 173, 125, 218, 151, 255, 231, 24, 110, 16,
			227, 58, 4, 228, 148, 63, 175, 45, 164, 28, 87, 199, 
			143, 177, 120, 105, 22, 66, 159, 246, 83, 37, 38, 127,
			217, 72, 156, 61, 162, 19, 114, 210, 93, 118, 200, 174,
			124, 149, 223, 238, 109, 134, 180, 78, 201, 186, 73, 145, 
			152, 249, 160, 86, 98, 82, 57, 108, 211, 182, 181, 84,
			204, 241, 0, 1};
	Encryption encryption ;
	Decryption decryption ;
	
	public DoubleChainingAlgorithm(){
		encryption = new Encryption();
		decryption = new Decryption();
	}
	/*To encrypt*/
	public   ArrayList<Integer> blockE(RoundKey roundKey, ArrayList<Integer> blockPlainText){
//		System.out.println("key : "+roundKey.subKey);
		for (int i = 0; i < 4; i++) {
			blockPlainText = encryption.chainingOperation( roundKey.subKey.get(i),blockPlainText);
		}
		blockPlainText = encryption.sBoxEnc(sBox, blockPlainText);
		return blockPlainText;
	}

	/*To decrypt*/
	public   ArrayList<Integer> blockD(RoundKey roundKey, ArrayList<Integer> blockPlainText){		
		blockPlainText = decryption.sBoxDec(sBoxInverse, blockPlainText);
		for (int i = 0; i < 4; i++) {
			blockPlainText = decryption.chainingOperation(roundKey.subKey.get(3-i), blockPlainText);
		}
		return blockPlainText;
	}
}