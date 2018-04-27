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

    Windows(Java runtime environment 1.8.0,cygwin)
    MacOS(Java runtime environment 1.8.0)

## tools
**GNU Make**

[GNU Make](https://www.gnu.org/software/make/) is a tool which controls the generation of executables and other non-source files of a program from the program's source files.

**Cygwin**

[Cygwin](https://www.cygwin.com/) a large collection of GNU and Open Source tools which provide functionality similar to a Linux distribution on Windows.

## Install Directions

**For intact program**

Download the folder "SER502-Spring2018-Team-24" from [here](https://github.com/HongfeiJu?tab=repositories)

**For components**

Compiler: down load soucre codes from [here](https://github.com/HongfeiJu/SER502-Spring2018-Team-24/tree/master/src/compiler) or download executable file "Compiler.jar" from [here](https://github.com/HongfeiJu/SER502-Spring2018-Team-24/tree/master/bin).

Interpreter: download java class "Interpreter" from [here](https://github.com/HongfeiJu/SER502-Spring2018-Team-24/tree/master/src/runtime).


## Build and Run Directions


**option 1: use bash script**

run the compiler, source codes of the compiler will be compiled. The built compiler will process the first five samples in the data folder and generate the corresponding intermediate codes.

`~$ ./compile.sh`

run the interpreter, source codes of the interpreter will be compiled. The built interpreter will execute the first five intermediate codes files.

`~$ ./interpret.sh`


**option 2: compile and run the compiler and interpreter by yourself**


For the compiler, please see the following instruction. 

step 1: On cmd.exe or terminal.app, go to "compiler" folder and use following command to compile the source codes:

`javac ICGenerator.java`

step 2: Run following command to process the L0 file in "data" folder and generate intermediate codes:

`java ICGenerator ../../data/filename.L0`


PS: for your convenience, the executable jar file can be created. you can put L0 file in the same folder and run the compiler directly withous compiling by using the following command, :

`java -jar Compiler.jar filename.L0`


For the interpreter, please see the following instruction.

Step 1: On cmd.exe or terminal.app, go to "runtime" folder and run the following command to compile the source codes.

    javac Interpreter.java

Step 2: Run the following command to execute intermediate codes in "data" folder.

    java Interpreter ../../data/filename.txt


## YouTube Video Link

The presentation video about this project is [here](https://www.youtube.com/watch?v=vZbybIA0izA).

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
	
		imperative paradigm
	

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



