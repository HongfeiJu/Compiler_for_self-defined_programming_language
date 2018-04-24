# SER502-Spring2018-Team<24>

**Members**

Hongfei Ju

Ruihao Zhou

Zachary Wang

**Development Plan**

Lexical Analysis: Hongfei Ju, Ruihao Zhou

Parser & Intermediate Code Generator: Hongfei Ju

Interpreter: Zachary Wang


## System Requirement

**Platform**

    MacOS(native)
    Windows(java runtime environment 1.8.0)


## Install Directions

Compiler: download executable file "Compiler.jar" from [here](https://github.com/HongfeiJu/SER502-Spring2018-Team-24/tree/master/src/compiler).





## Build and Run Directions







## YouTube Video Link

The presentation video about this project is [here](https://www.youtube.com/).

## Language Name

**L0** will be our language name.
Zero is the first natural number, we see L0 as a good start of our learning in compiling techniques.

## Language design

Primitive Type: 

This programming language includes two primitive types: boolean values and int numeric value. 

Operation:

For boolean type data, the corresponding operators include “equal”, “larger than”, “no less than”, “less than”, no larger than”, “not equal”. For int numeric type data, the corresponding operators include “plus”, “minus”, “multiply”, “divide”.

Statements: 

assignment to associate a value with a variable, if-then-else statement to make decisions, and while statement for iterative execution.



## Grammar Rule

```
program  :  statement_list ;
statement_list  :  statement  statement_list  |  statement  ;
statement  :  declaration  |  assignment  |  if_statement  |  while_statement  |  print  ;
declaration  :  'var'  ID  ';'  ;
assignment  :  ID  ':='  low_expression ';'  ;
if_statement  :  'if'  '(' boolean_expression  ')'  'correct'  '{'  statement_list  '}'  |  'if'  '('  boolean_expression  ')'  'correct'  '{'  statement_list  '}'  'wrong'  '{'  statement_list  '}'  ;
while_statement  :  'while'  '('  boolean_expression  ')'  '{'  statement_list  '}'  ;
print  :  'print'  low_expression  ;
boolean_expression  :  low_expression  '=='  low_expression  |  low_expression  '>'  low_expression  |  low_expression  '>='   low_expression  |  low_expression '<' low_expression  |  low_expression  '<='  low_expression  |  low_expression  '!='  low_expression  |  boolean_val  ;
boolean_val  :  'true'  |  'false'  ;
low_expression  :  high_expression  '+'  low_expression  |  high_expression  '-'  low_expression  |  high_expression  ;
high_expression  :  item  '*'  high_expression  |  item  '/'  high_expression  |  item  ;
item  :  '(' expr ')' | ID | NUMBER  ;
ID  :  [a-z|A-Z]+  ;
NUMBER  :  [0-9]+  ;
WS :  [ \t\r\n]+ -> skip ;
```

## Design Details
* Paradigm of the Language
	

* Parsing Process(Technique)

   ![Parsing Process](https://github.com/HongfeiJu/SER502-Spring2018-Team-24/blob/master/doc/Parsing%20process.png)
		


* Example Program in High Level Language

     For more example, please see [here](https://github.com/HongfeiJu/SER502-Spring2018-Team-24/tree/master/data)
	
     Example: counting the sum of all the numbers between 0 and 10 using our language L0.

```
var sum;
sum:=0;
var count;
count:=0;
while(count<=10){
sum:=sum+count;
count:=count+1;
}
print sum;
```
  



