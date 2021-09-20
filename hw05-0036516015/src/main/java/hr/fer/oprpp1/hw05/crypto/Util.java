package hr.fer.oprpp1.hw05.crypto;

/**
 * Class used to convert hexadecimal values to byte array and vice versa.
 * @author Zvonimir Petar Rezo
 *
 */
public class Util {
	
	public static void main(String[] args) {
		byte[] bytes = hextobyte("01aE22");
		for (byte b : bytes) {
			System.out.println(b);
		}
		System.out.println(bytetohex(bytes));
	}
	
	/**
	 * Converts hexadecimal string value to a byte array.
	 * @param keyText - hexadecimal string value
	 * @return Array of bytes converted from the given hexadecimal value.
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < keyText.length(); i++) {
			if (!Character.isDigit(keyText.charAt(i)) && !Character.isLetter(keyText.charAt(i))) {
				throw new IllegalArgumentException();
			}
		}
		if (keyText.length() == 0) {
			return new byte[0];
		}

		int len = keyText.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4) + Character.digit(keyText.charAt(i + 1), 16));
		}
		return data;
	}

	/**
	 * Converts a byte array to hexadecimal string value.
	 * @param bytearray - given byte array which needs to be converted
	 * @return Hexadecimal representation of the given byte array.
	 */
	public static String bytetohex(byte[] bytearray) {
		if (bytearray.length == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
	    for (byte b : bytearray) {
	        sb.append(String.format("%02X", b).toLowerCase());
	    }
	    return sb.toString();
	}

}
