public class AST {
	String operator;
	AST sub1;
	AST sub2;
	AST sub3;
	
	public AST(String p){
		operator=p;
	}
	public AST(String p, AST s1){
		operator=p;
		sub1=s1;
	}
	public AST(String p, AST s1, AST s2){
		operator=p;
		sub1=s1;
		sub2=s2;
	}
	public AST(String p, AST s1, AST s2,AST s3){
		operator=p;
		sub1=s1;
		sub2=s2;
		sub3=s3;
	}
	

}
