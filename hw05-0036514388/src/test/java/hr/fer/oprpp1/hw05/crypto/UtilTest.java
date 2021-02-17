package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void hextobyteTest1() {
		byte[] expected = new byte[] {1, -82, 34};
		byte[] actual = Util.hextobyte("01aE22");
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
	}
	
	@Test
	public void hextobyteTest2() {
		byte[] expected = new byte[] {-70, -85, -86};
		byte[] actual = Util.hextobyte("baabaa");
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
	}
	
	@Test
	public void hextobyteTest3() {
		byte[] expected = new byte[] {7, 10, 34};
		byte[] actual = Util.hextobyte("070a22");
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
	}
	
	@Test
	public void hextobyteTest4() {
		byte[] expected = new byte[] {-1, -102, -69};
		byte[] actual = Util.hextobyte("Ff9aBB");
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
	}
	
	@Test
	public void hextobyteTest5() {
		byte[] expected = new byte[] {0, 0, 0, 1};
		byte[] actual = Util.hextobyte("00000001");
		
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
		assertEquals(expected[3], actual[3]);
	}
	
	@Test
	public void hextobyteTest6() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("abcd5"));
	}
	
	@Test
	public void hextobyteTest7() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("abcdefgh"));
	}
	
	@Test
	public void hextobyteTest8() {
		byte[] zeroLengthArray = Util.hextobyte("");
		assertTrue(zeroLengthArray.length == 0);
	}
	
	@Test
	public void bytetohexTest1() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
		assertEquals("deaefc", Util.bytetohex(new byte[] {-34, -82, -4}));
		assertEquals("7f", Util.bytetohex(new byte[] {127}));
	}
	
	@Test
	public void bytetohexTest2() {
		assertEquals("7f", Util.bytetohex(new byte[] {127}));
		assertEquals("0501", Util.bytetohex(new byte[] {5, 1}));
		assertEquals("00ff017f80", Util.bytetohex(new byte[] {0, -1, 1, 127, -128}));
	}
	
	@Test
	public void bytetohexTest3() {
		assertEquals("", Util.bytetohex(new byte[] {}));
	}
}
