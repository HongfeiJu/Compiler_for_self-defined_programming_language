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
		program();
	}
	
	public Parser(Queue<Lexcer.Token> s) {
		tokenList=s;
		program();
	}
	
	public static void program() {
		token=tokenList.poll();
		tree.add("program");
		tree.add("(");
		statementList();
		tree.add(")");
	}
	
	public static void statementList() {
		tree.add("stmtList");
		tree.add("(");
		statement();
		//System.out.println("debug:"+token);
		if (token==null) return;
		else if (token.tokenType.equals("VAR")||token.tokenType.equals("IDENTIFIER")||token.tokenType.equals("IF")
		  ||token.tokenType.equals("WHILE")||token.tokenType.equals("PRINT")) {
			statementList();
		}
		tree.add(")");
	}
	
	public static void statement() {
		tree.add("stmt");
		tree.add("(");
		if (token.tokenType.equals("VAR")) declaration();
		else if (token.tokenType.equals("IDENTIFIER")) assignment();
		else if (token.tokenType.equals("IF")) ifStatement();
		else if (token.tokenType.equals("WHILE")) whileStatement();
		else if (token.tokenType.equals("PRINT")) printFunc();
		tree.add(")");
	}
	
	public static void declaration() {		
		tree.add("dcl");
		tree.add("(");
		match("VAR");		
		tree.add(identifier());
		match("SEMICOLON");
		tree.add(")");
	}
	
	public static void assignment() {
		tree.add("asgm");
		tree.add("(");
		tree.add(identifier());
		match("ASSIGNMENT");
		for(String x:lowExpression()) {
			tree.add(x);
		};
		match("SEMICOLON");
		tree.add(")");
	}
	
	public static void ifStatement() {
		tree.add("if");
		tree.add("(");
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
		tree.add(")");
	}
	
	public static void whileStatement() {
		tree.add("while");
		tree.add("(");
		match("WHILE");
		match("LPAREN");
		booleanExpression();
		match("RPAREN");
		match("LCBRACKET");
		statementList();
		match("RCBRACKET");
		tree.add(")");
	}
	
	public static void printFunc() {
		tree.add("print");
		tree.add("(");
		match("PRINT");
		tree.add(identifier());
		match("SEMICOLON");
		tree.add(")");
	}
	
	public static void booleanExpression() {
		if (token.tokenType.equals("TRUE")) {
			tree.add("true");
			match("TRUE");
		}else if (token.tokenType.equals("FALSE")) {
			tree.add("false");
			match("FALSE");
		}else {
			Queue<String> templow1=lowExpression();
			switch(token.tokenType) {
			case "EQUAL":{
				match("EQUAL");tree.add("equal");tree.add("(");
				for(String x:templow1) {
					tree.add(x);
				};
				break;
			}
			case "NOLARGERTHAN":{
				match("NOLARGERTHAN");tree.add("NoLargerThan");tree.add("(");
				for(String x:templow1) {
					tree.add(x);
				};
				break;
			}
			case "NOLESSTHAN":{
				match("NOLESSTHAN");tree.add("NoLessThan");tree.add("(");
				for(String x:templow1) {
					tree.add(x);
				};
				break;
			}
			case "NOTEQUAL":{
				match("NOTEQUAL");tree.add("NotEqual");tree.add("(");
				for(String x:templow1) {
					tree.add(x);
				};
				break;
			}
			case "ASSIGNMENT":{
				match("ASSIGNMENT");tree.add("assignment");tree.add("(");
				for(String x:templow1) {
					tree.add(x);
				};
				break;
			}			
			}
			Queue<String> templow2=lowExpression();
			for(String x:templow2) {
				tree.add(x);
			};
			tree.add(")");
		}
	}
	
	public static Queue<String> lowExpression() {
		Queue<String> tempHigh=highExpression();
		Queue<String> temp=new LinkedList<String>();
		if (token.tokenType.equals("PLUS")||token.tokenType.equals("MINUS")) {
			switch(token.tokenType) {
			case "PLUS":{
				match("PLUS");temp.add("plus");temp.add("(");
				for(String x:tempHigh) {
					temp.add(x);
				};
				Queue<String> tempLow=lowExpression();
				for(String x:tempLow) {
					temp.add(x);
				};
				temp.add(")");
				break;
			}
			case "MINUS":{
				match("MINUS");temp.add("minus");temp.add("(");
				for(String x:tempHigh) {
					temp.add(x);
				};
				Queue<String> tempLow=lowExpression();
				for(String x:tempLow) {
					temp.add(x);
				};
				temp.add(")");
				break;
				}
			}			
		}else {
			for(String x:tempHigh) {
				temp.add(x);
			};
		}		
		return temp;
	}
	
	public static Queue<String> highExpression() {
		Queue<String> temp=new LinkedList<String>();
		String s="";
		if (token.tokenType.equals("IDENTIFIER")) {
			s=identifier();
		}else if (token.tokenType.equals("NUMBER")) {
			s=number();	
		}
		
		if (token.tokenType.equals("MULTIPLE")||token.tokenType.equals("DIVIDE")) {
			switch(token.tokenType) {
			case "MULTIPLE":{
				match("MULTIPLE");temp.add("multiple");temp.add("(");temp.add(s);
				for(String x:highExpression()) {
					temp.add(x);
				};
				temp.add(")");
				break;
			}
			case "DIVIDE":{
				match("DIVIDE");temp.add("divide");temp.add("(");temp.add(s);
				for(String x:highExpression()) {
					temp.add(x);
				};
				temp.add(")");break;
			}
			}			
		}else {
			temp.add(s);
		}
		return temp;
	}
	
	public static String number() {		
		String value=token.value;		
		match("NUMBER");
		return value;
	}
	
	public static String identifier() {
		String value=token.value;
		match("IDENTIFIER");
		return value;
	}
	
	public static void match(String tokenType) {	
		if (token==null) return;//throw new RuntimeException("No tokens");
		String type=token.tokenType;
		if (type.equals(tokenType)) {
			getToken();System.out.println(type+" match");
		}
		else throw new RuntimeException(tokenType+" expected");
	}
	
	public static void getToken() {
		if(!tokenList.isEmpty()) token=tokenList.poll();
		else token=null;
	}
	
	public static void printTree() {
		for (String x:tree) {
			System.out.print(x+" ");
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
		//parser.printTree();
		parser.printTree();
		
		
	}

}
