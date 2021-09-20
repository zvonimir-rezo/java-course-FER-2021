package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used to encrypt and decrypt messages and files.
 * @author Zvonimir Petar Rezo
 *
 */
public class Crypto {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println(args[0]);
		byte[] buffer = new byte[1024];
		if (args[0].equals("checksha")) {
			System.out.println("Please provide expected sha-256 digest for hw05test.bin:");
			System.out.print(">");
			String expectedDigest = sc.nextLine();
			Path toFile = Path.of(args[1]);
			InputStream is = Files.newInputStream(toFile);
			StringBuilder sb = new StringBuilder();
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;
				md.update(buffer, 0, r);
			}
			String digestCalc = Util.bytetohex(md.digest());
			if (digestCalc.equals(expectedDigest)) {
				System.out.println("Same.");
			} else {
				System.out.println("Not same.");
			}
		} else {
			Path toFile = Path.of(args[1]);
			Path newFile = Path.of(args[2]);
			InputStream is = Files.newInputStream(toFile);
			OutputStream os = Files.newOutputStream(newFile);

			System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
			System.out.print(">");
			String keyText = sc.nextLine();
			System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
			System.out.print(">");
			String ivText = sc.nextLine();
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
			
			boolean encrypt = args[0].equals("encrypt");

			try {
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			while (true) {
				int r = is.read(buffer);
				if (r < 1)
					break;

				os.write(cipher.update(buffer, 0, r));
			}

			try {
				os.write(cipher.doFinal());
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
		sc.close();

	}

	/**
	 * Digests a message and returns the SHA-256 hash.
	 * @param message - string to hash
	 * @return Hash of the given string.
	 * @throws NoSuchAlgorithmException if a non-existing algorithm is given to the getInstance function.
	 */
	public static String digest(String message) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(message.getBytes());
		byte[] b = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte by : b) {
			sb.append(Integer.toHexString(by & 0xff).toString());
		}
		return sb.toString();
	}

}
