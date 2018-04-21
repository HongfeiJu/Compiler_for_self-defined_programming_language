# SER502-Spring2018-Team<24>

**Members**

Hongfei Ju

Ruihao Zhou

Zachary Wang

**Development Plan**

Lexical Analysis: Hongfei Ju, Ruihao Zhou

Syntax Analysis: Hongfei ju, Zachary Wang

Intermediate Code Generating: Hongfei Ju

Interpreter: Zachary Wang


## System Requirement

**Platform**

    MacOS
    Windows: java runtime environment 1.8.0


## Install Directions







## Build and Run Directions







## YouTube Video Link

The presentation video about this project is [here]().

## Language Name

**L0** will be our language name. 


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
item  :  ID | NUMBER  ;
ID  :  [a-z|A-Z]+  ;
NUMBER  :  [0-9]+  ;
WS :  [ \t\r\n]+ -> skip ;
```

## Design Details
* Paradigm of the Language
	

* Parsing Process(Technique)



* Example Program in High Level Language

	
  



