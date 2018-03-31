import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Lexcer {
	private static String filename;
	private static String codes="";
	private static Queue<Token> tokenList=new LinkedList<Token>(); 
	
	public Lexcer(String s) {
		filename=s;
	}
	
	public static class Token{
		private String tokenType;
		private String value;
		public Token(String type, String val) {
			tokenType=type;
			value=val;
		}
	}
	public static void getTokens(){
		char currChar;
		for (int i=0;i<codes.length();i++) {
			currChar=codes.charAt(i);
			if (currChar==' '||currChar=='\t'||currChar=='\r'||currChar=='\n') {
				continue;
			}else if (currChar=='('||currChar==')'||currChar=='{'||currChar=='}'||currChar==';') {
				switch(currChar) {
				case '(':tokenList.add(new Token("LPAREN", "("));break;
				case ')':tokenList.add(new Token("RPAREN", ")"));break;
				case '{':tokenList.add(new Token("LCBRACKET", "{"));break;
				case '}':tokenList.add(new Token("RCBRACKET", "}"));break;
				case ';':tokenList.add(new Token("SEMICOLON", ";"));break;
				}
			}else if (currChar=='+'||currChar=='-'||currChar=='*'||currChar=='/') {
				switch(currChar) {
				case '+':tokenList.add(new Token("PLUS", "+"));break;
				case '-':tokenList.add(new Token("MINUS", "-"));break;
				case '*':tokenList.add(new Token("MULTIPLE", "*"));break;
				case '/':tokenList.add(new Token("DIVIDE", "/"));break;
				}
			}else if ((currChar=='='||currChar=='<'||currChar=='>'||currChar=='!'||currChar==':')) {
				if (codes.charAt(i+1)=='=') {
					i++;
					switch(currChar) {
					case '=':tokenList.add(new Token("EQUAL", "=="));
					case '<':tokenList.add(new Token("NOLARGERTHAN", "<="));break;
					case '>':tokenList.add(new Token("NOLESSTHAN", ">="));break;
					case '!':tokenList.add(new Token("NOTEQUAL", "!="));break;
					case ':':tokenList.add(new Token("ASSIGNMENT", ":="));break;
					}
				}else {
					switch(currChar) {					
					case '<':tokenList.add(new Token("LESSTHAN", "<"));break;
					case '>':tokenList.add(new Token("LARGERTHAN", ">"));break;
					}
				}
				
				
			}else if (Character.isDigit(currChar)) {
				String num=Character.toString(currChar);
				while(Character.isDigit(currChar=codes.charAt(++i))) {
					num+=Character.toString(currChar);
					}
				i--;
				tokenList.add(new Token("NUMBER",num));
			}else if(Character.isAlphabetic(currChar)) {
				String word=Character.toString(currChar);
				while(Character.isAlphabetic(currChar=codes.charAt(++i))) {
					word+=Character.toString(currChar);
					}
				i--;
				if (word.equals("var")||word.equals("if")||word.equals("correct")||word.equals("wrong")||
					word.equals("while")||word.equals("print")||word.equals("true")||word.equals("false")) {
					switch(word) {
					case "var":tokenList.add(new Token("VAR", "var"));break;
					case "if":tokenList.add(new Token("IF", "if"));break;
					case "correct":tokenList.add(new Token("CORRECT", "correct"));break;
					case "wrong":tokenList.add(new Token("WRONG", "wrong"));break;
					case "while":tokenList.add(new Token("WHILE", "while"));break;
					case "print":tokenList.add(new Token("PRINT", "print"));break;
					case "true":tokenList.add(new Token("TRUE", "true"));break;
					case "false":tokenList.add(new Token("FALSE", "false"));break;
					}
				}else {
					tokenList.add(new Token("IDENTIFIER",word));
					}
				}		
			}
			
		}
		
	public static void printTokens(){
		for (Token x:tokenList) {
			System.out.println(x.tokenType+" "+x.value);
		}
	}
	
	public static void getCodes() throws FileNotFoundException {
		File codeFile=new File(filename);
		Scanner in=new Scanner(new FileReader(filename));		
		while(in.hasNextLine()) {
			codes+=in.nextLine();
		}		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		String filename="C:/Users/Hongfei/Desktop/codes.txt";
		Lexcer lexcer=new Lexcer(filename);
		lexcer.getCodes();
		lexcer.getTokens();
		lexcer.printTokens();
		System.out.println("Done");
	}

}
