package hr.fer.oprpp1.hw05.crypto;

/**
 * Class which allows conversions from text to byte array and vice versa.
 * 
 * @author borna
 *
 */
public class Util {
	
	/**
	 * Method takes hex-encoded String and returns appropriate byte array.
	 * Hextobyte method supports both uppercase and lowercase letters and returns zero-length byte array if size of <code>keyText</code> is 0.
	 *  
	 * @param keyText hex-encoded String
	 * @return byte array created from <code>keyText</code>
	 * @throws IllegalArgumentException if string <code>keyText</code> has invalid characters or is odd-sized
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.isEmpty()) return new byte[0];
		
		if (keyText.length() % 2 != 0) throw new IllegalArgumentException("String is odd-sized!");
		if (!keyText.matches("[0-9a-fA-F]+")) throw new IllegalArgumentException("Invalid character(s) occured!");
		
		int size = keyText.length();
		String upperKeyText = keyText.toUpperCase();
		
		byte[] byteArr = new byte[size/2];
		for (int i = 0; i < size; i+=2) {
			byteArr[i/2] = (byte) (convert(upperKeyText.charAt(i)) * 16 + convert(upperKeyText.charAt(i+1)));
		}
		
		return byteArr;
	}
	
	/**
	 * Method converts given character in byte.
	 * 
	 * @param c one character from <code>keyText</code>
	 * @return one element of byte array
	 */
	private static byte convert(char c) {
		if (c >= 'A' && c <= 'F') {
			return (byte) (10 + c - 'A');
		}
		return (byte) (c - '0');
	}
	
	/**
	 * Method takes a byte array and creates its hex-encoding: 
	 * for each byte of given array, two characters are returned in string, while using big-endian notation.ž
	 * 
	 * @param bytearray
	 * @return String created from byte array. For zero-length array method must return an empty string 
	 */
	public static String bytetohex(byte[] bytearray) {
		if (bytearray.length == 0) return "";
		
		String hexEncoding = "";
		
		for (int i = 0; i < bytearray.length; i++) {
			int byteElem = bytearray[i];
		
			if (byteElem < 0) {
				byteElem = 256 + byteElem;
			}
			
			if (byteElem == 0) hexEncoding += "0";
			String num = "";
			while (byteElem > 0) {
				int digit = byteElem % 16;
				num = digit < 10 ? digit + num : (char)('a' - 10 + digit) + num;
				byteElem /= 16;
			}
			hexEncoding += num.length() < 2 ? "0" + num : num;
		}
		return hexEncoding;
	}

}
 