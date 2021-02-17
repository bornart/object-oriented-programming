package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Klasa koja predstavlja parser. Parser obavlja sintaksnu analizu.
 * 
 * @author borna
 *
 */
public class SmartScriptParser {
	
	private Lexer lexer;
	private ObjectStack stack;
	private DocumentNode dn;
	
	/**
	 * Konstruktor koji prima string koji sadrzi citavo tijelo dokumenta. U konstruktoru se stvara instanca lexera koji se inicijalizira sa Stringom <code>docBody</code>.
	 * Dodatno, konstruktor delegira postupak parsiranja metodi <code>parse</code>.
	 * 
	 * @param docBody string koji sadrzi citavo tijelo dokumenta
	 */
	public SmartScriptParser(String docBody) {
		lexer = new Lexer(docBody);
		stack = new ObjectStack();
		
		dn = new DocumentNode();
		stack.push(dn);
		
		parse();
	}
	
	/**
	 * Metoda obavlja parsiranje, tj. sintaksnu analizu.
	 * 
	 * @throws SmartScriptParserException ukoliko se pojavi bilokakva pogreska u procesu parsiranja
	 */
	private void parse() {
		try {
			while (lexer.nextToken().getType() != TokenType.EOF) {
				Token token = lexer.getToken();
				//System.out.println(token.getType() + " " + token.getValue());
				if (token.getType() == TokenType.TEXT) {
					lexer.setState(LexerState.EXTENDED);
					TextNode tn = new TextNode((String)token.getValue());
					Node peek = (Node) stack.peek();
					peek.addChildNode(tn);
				} else if (token.getType() == TokenType.TAG) {
					if (token.getValue().equals("FOR")) {
						ForLoopNode fn = forLoopElements();
						Node peek = (Node) stack.peek();
						peek.addChildNode(fn);
						stack.push(fn);
					} else if (token.getValue().equals("=")) {
						Node peek = (Node) stack.peek();
						peek.addChildNode(echoElements());
					} else {
						throw new SmartScriptParserException();
					}
				} else if (token.getType() == TokenType.TAGEND) {
					lexer.setState(LexerState.BASIC);
				} else if (token.getType() == TokenType.END) {
					stack.pop();
					if (stack.isEmpty()) throw new SmartScriptParserException("Dokument sadrzi vise END tagova nego otvorenih FOR tagova!");
					lexer.setState(LexerState.BASIC);
				} else {
					throw new SmartScriptParserException();
				}
			}
		} catch(Exception e) {
			throw new SmartScriptParserException();
		}
	}
	
	/**
	 * Metoda pronalazi koji se elementi sastavni dio "for petlje" i iz definiranih elemenata stvara primjerak klase <code>ForLoopNode</code>.
	 * 
	 * @return primjerak klase <code>ForLoopNode</code>
	 * @throws SmartScriptParserException
	 */
	private ForLoopNode forLoopElements() {
		Element[] elems = new Element[4];
		
		int i = 0;
		while (true) {
			if (lexer.nextToken().getType() == TokenType.TAGEND) {
				lexer.setState(LexerState.BASIC);
				break;
			}
			Token t = lexer.getToken();
			if (t.getType() != TokenType.DECIMAL && t.getType() != TokenType.INTEGER && t.getType() != TokenType.STRING) throw new SmartScriptParserException();
			elems[i] = findElement(t.getType(), t.getValue());
			i++;
		}
		if (i < 3 || i > 4) throw new SmartScriptParserException();
		ElementVariable e = null;
		
		if (!elems[0].asText().matches("^([\\w\\s*\\_])+$")){
			throw new SmartScriptParserException();
		} else {
			 e = new ElementVariable(elems[0].asText());
		}
		
		ForLoopNode node = new ForLoopNode(e, elems[1], elems[2], elems[3]);
		return node;
	}
	
	/**
	 * Metoda na temelju primjenih parametara stvara instancu klase <code>Element</code> ili neke od njoj izvedenih klasa.
	 * 
	 * @param type <code>TokenType</code>, tip tokena
	 * @param value vrijednost tokena
	 * @return instanca klase <code>Element</code> ili neka od izvedenih klasa
	 * @throws SmartScriptParserException
	 */
	private Element findElement(TokenType type, Object value) {
		switch (type) {
		case STRING: return new ElementString((String)value);
		case INTEGER: return new ElementConstantInteger((Integer)value);
		case DECIMAL: return new ElementConstantDouble((Double)value);
		case OPERATOR: {
			return new ElementOperator(value.toString());
		}
		case FUNCTION: return new ElementFunction((String)value);
		default: throw new SmartScriptParserException();
		}
	}
	
	/**
	 * Metoda pronalazi koji se elementi sastavni dio "echo" izraza i iz definiranih elemenata stvara primjerak klase <code>EchoNode</code>.
	 * 
	 * @return primjerak klase <code>EchoNode</code>
	 */
	private EchoNode echoElements() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		while (true) {
			if (lexer.nextToken().getType() == TokenType.TAGEND) {
				lexer.setState(LexerState.BASIC);
				break;
			}
			Token t = lexer.getToken();
			col.add(findElement(t.getType(), t.getValue()));
		}

		Element[] elems = new Element[col.size()];
		for (int i = 0; i < col.size(); i++) {
			elems[i] = (Element) col.get(i);
		}
		EchoNode node = new EchoNode(elems);
		return node;
	}
	
	/**
	 * Metoda vraca korijenski element stabla <code>DocumentNode</code>.
	 * 
	 * @return korijenski element stabla
	 */
	public DocumentNode getDocumentNode() {
		return dn;
	}
}
