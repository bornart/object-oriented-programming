package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class represents the program Crypto that will allow the user to encrypt/decrypt given file 
 * using the AES cryptoalgorithm and the 128-bit encryption key 
 * or calculate and check the SHA-256 file digest.
 * 
 * @author borna
 *
 */
public class Crypto {
	
	/**
	 * Main method which allows the user to communicate with the program and use its functionalities.
	 * 
	 * @param args
	 * @throws IllegalBlockSizeException
	 */
	public static void main(String[] args) throws IllegalBlockSizeException{
		/*
		 * UPUTA PRILIKOM POKRETANJA:
		 * Uz napomenu profesora da na Ferka ne objavljujemo izgenerirane PDF-ove i ostale dokumente potrebne za ovaj zadatak,
		 * kako bi se program Crypto uspješno pokrenuo potrebno je napraviti sljedeće:
		 * 	1. u korijenskom direktoriju projekta stvoriti mapu "resources"
		 * 	2. u tu mapu dodati: hw05.pdf, hw05test.bin i hw05part2.bin
		 * 	3. izvesti program kako je predviđeno :)
		 */
		Scanner sc = new Scanner(System.in);
		
		String task = args[0];
		String file = args[1];
		//String task = sc.next();
		//String file = sc.next();
		
		if (task.equals("checksha")) {
			System.out.println("Please provide expected sha-256 digest " + file + ":");
			
			String expectedDigest = sc.next();
			String actualDigest = checkSHA(file);
			
			if (actualDigest.equals(expectedDigest)) {
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + file + " does not match the expected digest. Digest was: " + actualDigest);
			}
			
		} else if (task.equals("encrypt") || task.equals("decrypt")) {
			boolean encrypt = task.equals("encrypt");			
			//String newFile = sc.next();
			String newFile = args[2];
			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			String keyText = sc.next();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			String ivText = sc.next();
			
			encryption(file, newFile, keyText, ivText, encrypt);
			
			System.out.println((encrypt ? "Encryption" : "Decryption") + " completed. Generated file "+ newFile +" based on file " + file);
			
		} else {
			System.err.println("Illegal command!");
			sc.close();
			System.exit(0);
		}
		
		sc.close();
	}
	
	/**
	 * Method which is in charge for both encryption and decryption (the decision whether to encrypt or decrypt given file is based on the boolean value of parameter <code>encrypt</code>).
	 * 
	 * @param file source file
	 * @param newFile destination file
	 * @param keyText encryption key provided by the user as hex-encoded text
	 * @param ivText initialization vector provided by the user as hex-encoded text
	 * @param encrypt <code>true</code> for encryption and <code>false</code> for decryption
	 * @throws IllegalBlockSizeException
	 */
	private static void encryption(String file, String newFile, String keyText, String ivText, boolean encrypt) throws IllegalBlockSizeException {
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (GeneralSecurityException ex) {
			System.err.println("GeneralSecurityException occurred while creating/initializing the cipher object.");
			return;
		}
		
		Path p = Paths.get("resources/" + file);
		File createFile = new File("resources/" + newFile);
		if (createFile.isFile()) {
			System.out.println("Illegal filename!");
			System.exit(0);
		}
		Path newP = Paths.get("resources/" + newFile);
		
		try (InputStream is = new BufferedInputStream(Files.newInputStream(p));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(newP))) {
			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1) break;
				byte[] tmp = cipher.update(buff, 0, r);
				if (tmp != null) os.write(tmp);
			}
			byte[] result = cipher.doFinal();
			if (result != null) os.write(result);
		} catch(IOException | BadPaddingException ex) {
			if (ex.getClass() == BadPaddingException.class) {
				System.err.println("BadPaddingException occured during the finalization of the proccess.");
			} else {
				System.err.println("IOException occured during the proccess of encryption/decryption!");
			}
		}
	}

	/**
	 * Method calculates digest of the given file which is later used for comparsion with user-provided digest. 
	 * 
	 * @param file file whose digest is being calculated
	 * @return Hex-encoded String which represents the digest
	 */
	private static String checkSHA(String file) {
		Path p = Paths.get("resources/" + file);
		try (InputStream is = new BufferedInputStream(Files.newInputStream(p))) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[4096];
			while (true) {
				int r = is.read(buff);
				if (r < 1) break;
				md.update(buff, 0, r);
			}
			byte[] digest = md.digest();
			return Util.bytetohex(digest);
		} catch(IOException | NoSuchAlgorithmException ex) {
			return "Exception occured during the process of digest calculation.";
		}
	}
}
