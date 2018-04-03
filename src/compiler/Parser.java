import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
	private String filename;
	private static Lexcer.Token token;
	private static Queue<Lexcer.Token> tokenList=new LinkedList<Lexcer.Token>();
	private static Queue<String> tree=new LinkedList<String>();
	
	public Parser(String s) throws FileNotFoundException {
		filename=s;
		Lexcer lexcer=new Lexcer(filename);
		lexcer.getCodes();
		lexcer.getTokens();
		lexcer.printTokens();
		tokenList=lexcer.getTokensString();
		//program();
	}
	
	public Parser(Queue<Lexcer.Token> s) {
		tokenList=s;
		//program();
	}
	
	public static void program() {
		token=tokenList.poll();
		statementList();
	}
	
	public static void statementList() {
		statement();
		System.out.println(token.tokenType);
		if (token.tokenType.equals("VAR")||token.tokenType.equals("IDENTIFIER")||token.tokenType.equals("IF")
		  ||token.tokenType.equals("WHILE")||token.tokenType.equals("PRINT")) {
			statementList();
		}
	}
	
	public static void statement() {
		if (token.tokenType.equals("VAR")) declaration();
		else if (token.tokenType.equals("IDENTIFIER")) assignment();
		else if (token.tokenType.equals("IF")) ifStatement();
		else if (token.tokenType.equals("WHILE")) whileStatement();
		else if (token.tokenType.equals("PRINT")) printFunc();
	}
	
	public static void declaration() {
		
		match("VAR");		
		identifier();
		match("SEMICOLON");
	}
	
	public static void assignment() {
		match("IDENTIFIER");
		match("ASSIGNMENT");
		lowExpression();
		match("SEMICOLON");
	}
	
	public static void ifStatement() {
		match("IF");
		match("LPAREN");
		booleanExpression();
		match("RPAREN");
		match("CORRECT");
		match("LCBRACKET");
		statementList();
		match("RCBRACKET");
		if (token.tokenType.equals("WRONG")) {
			match("WRONG");
			match("LCBRACKET");
			statementList();
			match("RCBRACKET");
		}		
	}
	
	public static void whileStatement() {
		match("WHILE");
		match("LPAREN");
		booleanExpression();
		match("RPAREN");
		match("LCBRACKET");
		statementList();
		match("RCBRACKET");		
	}
	
	public static void printFunc() {
		match("PRINT");
		lowExpression();
	}
	
	public static void booleanExpression() {
		lowExpression();
		switch(token.tokenType) {
		case "EQUAL":match("EQUAL");break;
		case "NOLARGERTHAN":match("NOLARGERTHAN");break;
		case "NOLESSTHAN":match("NOLESSTHAN");break;
		case "NOTEQUAL":match("NOTEQUAL");break;
		case "ASSIGNMENT":match("ASSIGNMENT");break;
		case "TRUE":match("TRUE");break;
		case "FALSE":match("FALSE");break;
		}
		lowExpression();
	}
	
	public static void lowExpression() {
		highExpression();
		if (token.tokenType.equals("PLUS")||token.tokenType.equals("MINUS")) {
			switch(token.tokenType) {
			case "PLUS":match("PLUS");lowExpression();break;
			case "MINUS":match("MINUS");lowExpression();break;
			}			
		}else return;
		
	}
	
	public static void highExpression() {			
		if (token.tokenType.equals("IDENTIFIER")) {
			match("IDENTIFIER");
		}else if (token.tokenType.equals("NUMBER")) match("NUMBER");	
		
		if (token.tokenType.equals("MULTIPLE")||token.tokenType.equals("DIVIDE")) {
			switch(token.tokenType) {
			case "MULTIPLE":match("MULTIPLE");highExpression();break;
			case "DIVIDE":match("DIVIDE");highExpression();break;
			}
			
			
		}
		
		
	}
	
	public static void number() {
		match("NUMBER");
	}
	
	public static void identifier() {
		match("IDENTIFIER");
	}
	
	public static void keyWord(String s) {
		String type=token.tokenType;
		if(s.equals(type)) match(s);
	}
	
	public static void operator(String s) {
		String type=token.tokenType;
		if(s.equals(type)) match(s);
	}
	
	public static void symbol(String s) {		
		String type=token.tokenType;
		if(s.equals(type)) match(s);
		
	}
	
	public static void match(String tokenType) {	
		if (token==null) throw new RuntimeException(tokenType+" expected");
		String type=token.tokenType;
		if (type.equals(tokenType)) {
			getToken();System.out.println(type+" match");
		}
		else throw new RuntimeException(tokenType+" expected");
	}
	
	public static void getToken() {
		if(!tokenList.isEmpty()) token=tokenList.poll();
		else token=null;
		//tree.add(token.value);
	}
	
	public static void printTree() {
		for (String x:tree) {
			System.out.println(x);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		String filename="C:/Users/Hongfei/Desktop/codes.txt";
		Queue<Lexcer.Token> tokenList=new LinkedList<Lexcer.Token>();		
		tokenList.add(new Lexcer.Token("VAR","var"));		
		tokenList.add(new Lexcer.Token("IDENTIFIER","x"));
		tokenList.add(new Lexcer.Token("SEMICOLON",";"));
		tokenList.add(new Lexcer.Token("IDENTIFIER","x"));
		tokenList.add(new Lexcer.Token("ASSIGNMENT",":="));
		tokenList.add(new Lexcer.Token("IDENTIFIER","x"));
		tokenList.add(new Lexcer.Token("PLUS","+"));
		tokenList.add(new Lexcer.Token("NUMBER","5"));
		tokenList.add(new Lexcer.Token("SEMICOLON",";"));
		
		Parser parser=new Parser(filename);
		parser.program();
		//parser.printTree();
		
		
		
	}

}
