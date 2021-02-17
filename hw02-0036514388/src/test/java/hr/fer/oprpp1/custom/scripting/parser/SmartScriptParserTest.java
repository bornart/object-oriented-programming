package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	
	private String readExample(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
	}

	@Test
	public void ParserTest1() {
		String text = readExample(1);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		Node node = scp.getDocumentNode();
		assertEquals(node.getChild(0).getClass(), TextNode.class);
	}

	@Test
	public void ParserTest2() {
		String text = readExample(2);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(1, scp.getDocumentNode().numberOfChildren());
	}

	@Test
	public void ParserTest3() {
		String text = readExample(3);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(1, scp.getDocumentNode().numberOfChildren());
	}

	@Test
	public void ParserTest4() {
		String text = readExample(4);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(1, scp.getDocumentNode().numberOfChildren());
	}

	@Test
	public void ParserTest5() {
		String text = readExample(5);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(1, scp.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void ParserTest6() {
		String text = readExample(6);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		Node node = scp.getDocumentNode();
		assertEquals(node.getChild(1).getClass(), EchoNode.class);
	}

	@Test
	public void ParserTest7() {
		String text = readExample(7);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		Node node = scp.getDocumentNode();
		assertEquals(node.getChild(1).getClass(), EchoNode.class);
	}

	@Test
	public void ParserTest8() {
		String text = readExample(8);
	
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(2, scp.getDocumentNode().numberOfChildren());
	}

	@Test
	public void ParserTest9() {
		String text = readExample(9);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(2, scp.getDocumentNode().numberOfChildren());
	}

	@Test
	public void ParserTest10() {
		String text = readExample(10);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		Node node = scp.getDocumentNode();
		assertEquals(node.getChild(1).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void ParserTest11() {
		String text = readExample(11);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		Node node = scp.getDocumentNode();
		assertEquals(node.getChild(1).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void ParserTest12() {
		String text = readExample(12);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		Node node = scp.getDocumentNode();
		assertEquals(node.getChild(1).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void ParserTest13() {
		String text = readExample(13);
		
		SmartScriptParser scp = new SmartScriptParser(text);
		assertEquals(2, scp.getDocumentNode().numberOfChildren());
	}
}
