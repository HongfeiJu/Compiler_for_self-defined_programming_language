import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
	private String filename;
	private static Lexcer.Token token;
	private static Queue<Lexcer.Token> tokenList=new LinkedList<Lexcer.Token>();
	private static Queue<String> tree=new LinkedList<String>();
	public static AST ast=new AST("program");
	
	public Parser(String s) throws FileNotFoundException {
		filename=s;
		Lexcer lexcer=new Lexcer(filename);
		lexcer.getCodes();
		lexcer.getTokens();
		lexcer.printTokens();
		tokenList=lexcer.getTokensString();
		program(ast);
	}
	
	public Parser(Queue<Lexcer.Token> s) {
		tokenList=s;
		program(ast);
	}
	
	public static void program(AST t) {
		token=tokenList.poll();
		//tree.add("program");
		//tree.add("(");
		t.sub1=new AST("stmtList");
		statementList(t.sub1);
		//tree.add(")");
	}
	
	public static void statementList(AST t) {
		//tree.add("stmtList");
		//tree.add("(");
		t.sub1=new AST("stmt");
		statement(t.sub1);
		//System.out.println("debug:"+token);
		if (token==null) return;
		else if (token.tokenType.equals("VAR")||token.tokenType.equals("IDENTIFIER")||token.tokenType.equals("IF")
		  ||token.tokenType.equals("WHILE")||token.tokenType.equals("PRINT")) {
			t.sub2=new AST("stmtList");
			statementList(t.sub2);
		}
		//tree.add(")");
	}
	
	public static void statement(AST t) {
		//tree.add("stmt");
		//tree.add("(");
		if (token.tokenType.equals("VAR")) {
			t.sub1=new AST("dcl");
			declaration(t.sub1);
		}
		else if (token.tokenType.equals("IDENTIFIER")) {
			t.sub1=new AST("asgm");
			assignment(t.sub1);
		}
		else if (token.tokenType.equals("IF")) {
			t.sub1=new AST("if");
			ifStatement(t.sub1);
		}
		else if (token.tokenType.equals("WHILE")) {
			t.sub1=new AST("while");
			whileStatement(t.sub1);
		}
		else if (token.tokenType.equals("PRINT")) {
			t.sub1=new AST("print");
			printFunc(t.sub1);
		}		
		//tree.add(")");
	}
	
	public static void declaration(AST t) {		
		//tree.add("dcl");
		//tree.add("(");
		match("VAR");
		t.sub1=identifier();
		//tree.add(identifier(t));
		match("SEMICOLON");
		//tree.add(")");
	}
	
	public static void assignment(AST t) {
		//tree.add("asgm");
		//tree.add("(");
		//tree.add(identifier(t));
		t.sub1=identifier();
		match("ASSIGNMENT");
		t.sub2=lowExpression();
		/*for(String x:lowExpression(t)) {
			tree.add(x);
		};*/
		match("SEMICOLON");
		//tree.add(")");
	}
	
	public static void ifStatement(AST t) {
		//tree.add("if");
		//tree.add("(");
		match("IF");
		match("LPAREN");
		t.sub1=booleanExpression();
		match("RPAREN");
		match("CORRECT");
		match("LCBRACKET");
		t.sub2=new AST("stmtList");
		statementList(t.sub2);
		match("RCBRACKET");
		if (token.tokenType.equals("WRONG")) {
			
			match("WRONG");
			match("LCBRACKET");
			t.sub3=new AST("stmtList");
			statementList(t.sub3);
			match("RCBRACKET");
		}
		//tree.add(")");
	}
	
	public static void whileStatement(AST t) {
		//tree.add("while");
		//tree.add("(");
		match("WHILE");
		match("LPAREN");
		t.sub1=booleanExpression();
		match("RPAREN");
		match("LCBRACKET");
		t.sub2=new AST("stmtList");
		statementList(t.sub2);
		match("RCBRACKET");
		//tree.add(")");
	}
	
	public static void printFunc(AST t) {
		
		//tree.add("print");
		//tree.add("(");
		match("PRINT");
		t.sub1=identifier();
		//tree.add(identifier(t));
		match("SEMICOLON");
		//tree.add(")");
	}
	
	public static AST booleanExpression() {
		AST t=new AST("");
		if (token.tokenType.equals("TRUE")) {
			//tree.add("true");
			match("TRUE");
			return new AST("true");
		}else if (token.tokenType.equals("FALSE")) {
			//tree.add("false");
			match("FALSE");
			return new AST("false");
		}else {
			t.sub1=lowExpression();
			switch(token.tokenType) {
			case "EQUAL":{
				match("EQUAL");
				t.operator="EQUAL";
				/*tree.add("equal");tree.add("(");
				for(String x:templow1) {
					tree.add(x);
				};*/
				break;
				}
			case "NOLARGERTHAN":{
				match("NOLARGERTHAN");
				t.operator="NOLARGERTHAN";
				break;
				}
			case "NOLESSTHAN":{
				match("NOLESSTHAN");
				t.operator="NOLESSTHAN";
				break;
				}
			case "NOTEQUAL":{
				match("NOTEQUAL");
				t.operator="NOTEQUAL";
				break;
				}
			case "LARGERTHAN":{
				match("LARGERTHAN");
				t.operator="LARGERTHAN";
				break;
				}	
			case "LESSTHAN":{
				match("LESSTHAN");
				t.operator="LESSTHAN";
				break;
				}
			}
			t.sub2=lowExpression();			
			}
			//tree.add(")");
		return t;
		}
	
	public static AST lowExpression() {
		AST t=new AST("");
		//Queue<String> tempHigh=highExpression();
		//Queue<String> temp=new LinkedList<String>();
		t.sub1=highExpression();
		if (token.tokenType.equals("PLUS")||token.tokenType.equals("MINUS")) {
			switch(token.tokenType) {
			case "PLUS":{
				match("PLUS");
				t.operator="PLUS";
				t.sub2=lowExpression();
				/*temp.add("plus");temp.add("(");
				for(String x:tempHigh) {
					temp.add(x);
				};
				Queue<String> tempLow=lowExpression();
				for(String x:tempLow) {
					temp.add(x);
				};
				temp.add(")");*/
				break;
			}
			case "MINUS":{
				match("MINUS");
				t.operator="MINUS";
				t.sub2=lowExpression();
				/*temp.add("minus");temp.add("(");
				for(String x:tempHigh) {
					temp.add(x);
				};
				Queue<String> tempLow=lowExpression();
				for(String x:tempLow) {
					temp.add(x);
				};
				temp.add(")");*/
				break;
				}
			}			
		}else {
			return t.sub1;
		}		
		return t;
	}
	
	public static AST highExpression() {
		AST t=new AST("");
		if (token.tokenType.equals("IDENTIFIER")) {
			t.sub1=identifier();
		}else if (token.tokenType.equals("NUMBER")) {
			t.sub1=number();	
		}
		
		if (token.tokenType.equals("MULTIPLE")||token.tokenType.equals("DIVIDE")) {
			switch(token.tokenType) {
			case "MULTIPLE":{
				t.operator="MULTIPLE";
				match("MULTIPLE");
				t.sub2=highExpression();
				/*temp.add("multiple");temp.add("(");temp.add(s);
				for(String x:highExpression()) {
					temp.add(x);
				};
				temp.add(")");*/
				break;
			}
			case "DIVIDE":{
				t.operator="DIVIDE";
				match("DIVIDE");
				t.sub2=highExpression();
				/*temp.add("divide");temp.add("(");temp.add(s);
				for(String x:highExpression()) {
					temp.add(x);
				};
				temp.add(")");break;*/
			}
			}			
		}else {
			return t.sub1;
		}
		return t;
	}
	
	public static AST number() {		
		String value=token.value;	
		AST t=new AST(value);
		match("NUMBER");
		return t;
	}
	
	public static AST identifier() {
		String value=token.value;
		AST t=new AST(value);
		match("IDENTIFIER");
		return t;
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
			if(true) {
				System.out.print(x+" ");
			}
			if(false) {
				if(!x.equals("(")&&!x.equals(")")) {
					System.out.println(x+" ");
				}
			}
			
		}
	}
	public static void printAST() {
		printAST(ast);
	}
	public static void printAST(AST t) {
		
		if (t==null) return;
		
		boolean hasSub=hasSub(t);
		System.out.print(t.operator);
		if (hasSub) System.out.print("(");
		if (t.sub1!=null) printAST(t.sub1);
		if (t.sub2==null) {
			if (hasSub) System.out.print(")");
		}else {
			System.out.print(", ");
			printAST(t.sub2);
		}
		if (t.sub3==null) {
			if (hasSub) System.out.print(")");
		}else {
			System.out.print(", ");
			printAST(t.sub3);
			if (hasSub) System.out.print(")");
		}
		
	}
	
	public static boolean hasSub(AST t) {
		return t.sub1!=null||t.sub2!=null||t.sub3!=null;
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
		parser.printAST();
		
		
	}

}
