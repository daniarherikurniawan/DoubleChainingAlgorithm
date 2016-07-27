package algorithm;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import anotherAlgorithm.AES;
import anotherAlgorithm.TripleDESTest;
import commonOperation.analysisOperation;
import commonOperation.fileOperation;
import operationModel.modeCBC;

public class App {
	/*Path to open File*/
//	final static String mainPath = "/media/daniar/myPassport/WorkPlace/Windows/NewAlgorithmKripto/file/";
	final static String mainPath = "file/";
	
	private static analysisOperation analysisOp = new analysisOperation();
	private static fileOperation fileOp = new fileOperation();
	static String cipherText1 = "ChiperTextCBC.txt";
	static String cipherText2 = "ChiperTextCBC2.txt";
	static String plainText1 = "PlainText.txt";
	static String plainText2 = "PlainText2.txt";
	static String resultText1 = "ResultTextCBC.txt";
	static String resultText2 = "ResultTextCBC2.txt";
	
	public static void main(String args[]) { 
		System.out.println("Start the comparison process\n"+"=====================================\n");
		
		
		String key = new String("hanubrhf");
		
		/*Blowfish*/
		runBlowfish(key, plainText1,cipherText1, resultText1 );
		runBlowfish(key, plainText2,cipherText2, resultText2 );
		analysisOp.compareTwoFile(mainPath+cipherText1,mainPath+cipherText2);
		
		/*TripleDES*/
		run3DES(key, plainText1,cipherText1, resultText1 );
		run3DES(key, plainText2,cipherText2, resultText2 );
		analysisOp.compareTwoFile(mainPath+cipherText1,mainPath+cipherText2);

		/*AES*/
		runAES(key, plainText1,cipherText1, resultText1 );
		runAES(key, plainText2,cipherText2, resultText2 );
		analysisOp.compareTwoFile(mainPath+cipherText1,mainPath+cipherText2);
		
		/* DCA Key should be per 8 characters length, min 8 and max 32*/
		runCBC(key, plainText1,cipherText1, resultText1 );
		runCBC(key, plainText2, cipherText2, resultText2 );
		analysisOp.compareTwoFile(mainPath+cipherText1,mainPath+cipherText2);

		
//		fileOp.writeFrequencyToFile(analysisOp.frequencyAnalysis(mainPath+"ChiperTextCBC.txt"),mainPath+"frequencyAfter.txt");
//		fileOp.writeFrequencyToFile(analysisOp.frequencyAnalysis(mainPath+"PlainText.txt"),mainPath+"frequencyBefore.txt");
		System.out.println("====================================="
				+ "\nEverthing's done!");
	} 
	
	private static void run3DES (String key, String fileName, String cipherFile, String resultFile){
		String text = fileOp.readFileAsString(mainPath+fileName);
    	byte[] codedtext;
		try {
			codedtext = new TripleDESTest().encrypt(text, key);
			fileOp.writeFileAsString(new String(codedtext), mainPath+cipherFile);
	    	String decodedtext = new TripleDESTest().decrypt(codedtext, key);
			fileOp.writeFileAsString(decodedtext, mainPath+resultFile);
			// codedtext is a byte array, you'll just see a reference to an array
	    	System.out.println("3DES done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void runAES (String key, String fileName, String cipherFile, String resultFile){
	    String IV = "AAAAAAAAAAAAAAAA";
	    String plaintext = fileOp.readFileAsString(mainPath+fileName);
	    String encryptionKey = "0123456789abcdef";
	    AES aes = new AES();
		try {

	      byte[] cipher = aes.encrypt(IV, plaintext, encryptionKey);
	      fileOp.writeFileAsString(new String(cipher), mainPath+cipherFile);

	      String decrypted = aes.decrypt(IV, cipher, encryptionKey);
	      fileOp.writeFileAsString(decrypted, mainPath+resultFile);
	      System.out.println("AES done");
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	}
	
	private static void runBlowfish(String key, String fileName, String cipherFile, String resultFile){		String Key = "Something";
		byte[] KeyData = Key.getBytes();
		SecretKeySpec KS = new SecretKeySpec(KeyData, "Blowfish");
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, KS);
			
			// get the text to encrypt
		    String inputText = fileOp.readFileAsString(mainPath+fileName);
		    // encrypt message
		    byte[] encrypted = cipher.doFinal(inputText.getBytes());
		    fileOp.writeFileAsString(new String(encrypted), mainPath+cipherFile);

			cipher.init(Cipher.DECRYPT_MODE, KS);
		    // encrypt message
		    byte[] result = cipher.doFinal(encrypted);

		    fileOp.writeFileAsString(new String(result), mainPath+resultFile);
			System.out.println("Blowfish done");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public static void runCBC(String key, String fileName, String cipherFile, String resultFile){
		/*Create an instance of runCBC class*/
		modeCBC cbc = new modeCBC(key);
		
		/*Mode Encryption*/			
			/*Read plain text from PlainText.txt*/
			cbc.plainText = fileOp.readFile(mainPath+fileName);
			/*Start the CFB mode*/
			cbc.cipherText = cbc.startEncryptionModeCBC(cbc.plainText);
			/*Write cipher text to ChiperText.txt*/
			fileOp.writeFile(cbc.cipherText, mainPath+cipherFile);
		
		/*Mode Decryption*/
			/*Read cipher text from ChiperText.txt*/
//			cbc.cipherText = fileOp.readFile(mainPath+cipherFile);
			/*Start the CFB mode*/
			cbc.resultText = cbc.startDecryptionModeCBC(cbc.cipherText);
			/*Write result text to ResultText.txt*/
			fileOp.writeFile(cbc.resultText, mainPath+resultFile);
		System.out.println("DCA done");
	}	
}
