import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ICGenerator {
	static int mLocation =0;
	static int counter=0;
	static int labelCounter=0;
	public static Queue<String> ic=new LinkedList<String>();
	public static HashMap<String,String> varList=new HashMap<String,String>();
	
	public ICGenerator(String s) {
	}
	public ICGenerator(AST t) {
		hProgram(t);
	}
	
	public static void hProgram(AST t) {
		System.out.println("processing program "+t.operator);
		hStatementList(t.sub1);		
	}
	
	public static void hStatementList(AST t) {
		System.out.println("processing hStatementList "+t.operator);
		hStatement(t.sub1);
		if (t.sub2!=null) hStatementList(t.sub2);
	}
	
	public static void hStatement(AST t) {
		System.out.println("processing hStatement "+t.operator);
		switch(t.sub1.operator){
		case "declare": hDeclare(t.sub1,ic);break;
		case "assignment": hAssignment(t.sub1,ic);break;
		case "if": hIf(t.sub1,ic);break;
		case "while": hWhile(t.sub1,ic);break;
		case "print": hPrint(t.sub1);break;		
		}
	}
	
	public static void hDeclare(AST t,Queue<String> s) {
		System.out.println("processing hDeclare "+t.operator);
		String id=hIdentifier(t.sub1,s);
		s.add(id);
		s.add(" :=");
		s.add(" 0");
		s.add("\r\n");
	}
	
	public static void hAssignment(AST t,Queue<String> s) {
		System.out.println("processing hAssignment "+t.operator);
		String var1=hIdentifier(t.sub1,s);
		String var2=hLExpression(t.sub2,s);

		s.add(var1);
		s.add(" :=");
		s.add(" "+var2);
		s.add("\r\n");
	}
	
	public static void hIf(AST t, Queue<String> s) {
		System.out.println("processing hIf "+t.operator);
		int localLC=labelCounter++;
		hBExpression(t.sub1,s);
		s.add("L"+localLC+"T");
		s.add("\r\n");
		s.add("L"+localLC+"T:  ");s.add("\r\n");
		hStatementList(t.sub2);
		s.add("goto");s.add(" "+"L"+localLC+"E");s.add("\r\n");
		s.add("L"+localLC+"F: ");s.add("\r\n");
		hStatementList(t.sub3);
		s.add("goto");s.add(" "+"L"+localLC+"E");s.add("\r\n");
		s.add("L"+localLC+"E: ");s.add("\r\n");
	}
	
	public static void hWhile(AST t, Queue<String> s) {
		System.out.println("processing hWhile "+t.operator);
		int localLC=labelCounter++;
		s.add("L"+localLC+"S: ");s.add("\r\n");
		hBExpression(t.sub1,s);
		s.add("L"+localLC+"T: ");s.add("\r\n");
		hStatementList(t.sub2);
		s.add("goto");s.add(" "+"L"+localLC+"S");s.add("\r\n");
		s.add("L"+localLC+"E: ");s.add("\r\n");
	}
	
	public static void hBExpression(AST t, Queue<String> s) {
		System.out.println("processing hBExpression"+t.operator);
		String var1=hLExpression(t.sub1,s);
		String var2=hLExpression(t.sub2,s);
		String relation="";
		if(t.operator.equals("true")||t.operator.equals("false")){
			s.add("if "+t.operator+"\r\ngoto ");			
		}else {
			switch(t.operator) {
			case "equal":relation="==";break;
			case "notEqual":relation="!=";break;
			case "largerThan":relation=">";break;
			case "noLargerThan":relation="<=";break;
			case "lessThan":relation="<";break;
			case "noLessThan":relation=">=";break;
			case "true":relation="true";break;
			case "false":relation="false";break;
			}
			s.add("if "+var1+" "+relation+" "+var2+"\r\ngoto ");
		}
		
	}
	
	
	public static String hLExpression(AST t,Queue<String> s) {		
		System.out.println("processing hLExpression "+t.operator);
		if (t.sub2!=null) {
			String var1=hHExpression(t.sub1,ic);
			String var2=hLExpression(t.sub2,ic);
			s.add(var1+" := "+var1);
			if(t.operator.equals("plus")) s.add(" +");	counter++;
			if(t.operator.equals("minus")) s.add(" -");counter++;
			if(t.operator.equals("multiple")) s.add(" *");	counter++;
			if(t.operator.equals("divide")) s.add(" /");counter++;
			s.add(" "+var2);
			s.add("\r\n");
			return var1;
		}else {
			return hHExpression(t,ic);
		}
		
	}
	
	public static String hHExpression(AST t,Queue<String> s) {
		System.out.println("processing hHExpression "+t.operator);
		if(t.operator.equals("multiple")) {
			String var1="";
			if(t.sub1.operator.equals("identifier")) var1=hIdentifier(t.sub1,ic);
			if(t.sub1.operator.equals("number")) var1=hNumber(t.sub1,ic);
			String var2=hHExpression(t.sub2,ic);
			s.add(var1+" := "+var1);
			s.add(" *");counter++;
			s.add(" "+var2);
			s.add("\r\n");
			return var1;
		}
		if(t.operator.equals("divide")) {
			String var1="";
			if(t.sub1.operator.equals("identifier")) var1=hIdentifier(t.sub1,ic);
			if(t.sub1.operator.equals("number")) var1=hNumber(t.sub1,ic);
			String var2=hHExpression(t.sub2,ic);
			s.add(var1+" := "+var1);
			s.add(" /");counter++;
			s.add(" "+var2);
			s.add("\r\n");
			return var1;
		}
		if(t.operator.equals("identifier")) {
			return hIdentifier(t,s);		
		}
		if(t.operator.equals("number")) {
			return hNumber(t,s);
		}else return null;
	}
	
	public static String hIdentifier(AST t,Queue<String> s) {
		System.out.println("processing hIdentifier "+t.operator);
		int loc=0;
		if(!varList.containsKey(t.sub1.operator)) {
			loc=mLocation;mLocation++;
			varList.put(t.sub1.operator, "M"+loc);
			return "M"+loc;
		}else {			
			return varList.get(t.sub1.operator);
		}
	}
	
	public static String hNumber(AST t, Queue<String> s) {
		System.out.println("processing hNumber "+t.operator);
		String location="M"+mLocation; mLocation++;
		s.add(location);
		s.add(" :=");counter++;
		s.add(" "+t.sub1.operator);
		s.add("\r\n");
		return location;		
	}
	
	public static void hPrint(AST t) {
		System.out.println("processing hPrint "+t.operator);
		String var=hIdentifier(t.sub1,ic);
		ic.add("OUT");counter++;
		ic.add(" "+var);
		ic.add("\r\n");
	}
	
	
	public static void main(String[] agrs) throws IOException {
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
		ICGenerator generator=new ICGenerator(parser.ast);
		File file=new File("C:/Users/Hongfei/Desktop/IC.txt");
		FileWriter writer=new FileWriter(file,false);
		for (String s:generator.ic) {
			writer.write(s);
			System.out.print(s);
		}
		writer.close();
	}
	
	

}
