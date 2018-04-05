# SER502-Spring2018-Team<24>

**Members**

Hongfei Ju

Ruihao Zhou

Zachary Wang

**Development Plan**

Hongfei Ju, Ruihao Zhou(Lexical Analysis)

Hongfei ju, Zachary Wang(Syntax Analysis)

All members(Intermediate Code Generating) 

All member(Executing)


## System Requirement

**Platform**

    MacOS(native)
    Windows(Cygwin required)


## Install Directions







## Build and Run Directions







## YouTube Video Link

The presentation video about this project is [here]().

## Language Name

**L24** will be our language name since we are team **24**. 


## Grammar Rule

```
program  :  statement_list ;
statement_list  :  statement  statement_list  |  statement  ;
statement  :  declaration  |  assignment  |  if_statement  |  while_statement  |  print  ;
declaration  :  ‘var’  identifier  ‘;’  ;
assignment  :  identifier  ‘=’  number  ‘;’  ;
if_statement  :  ‘if’  ‘(‘  Boolean_expression  ‘)’  ‘correct’  ‘{‘  statement_list  ‘}’  |  ‘if’  ‘(‘  Boolean_expression  ‘)’  ‘correct’  ‘{‘  statement_list  ‘}’  ‘wrong’  ‘{‘  statement_list  ‘}’  ;
while_statement  :  ‘while’  ‘(‘  Boolean_expression  ‘)’  ‘{‘  statement_list  ‘}’  ;
print  :  ‘print’  low_expression  ;	
boolean_expression  :  expression  ‘==’  expression  |  expression  ‘>’  expression  |  expression  ‘>=’   expression  |  expression ‘<’ expression  |  expression  ‘<=’  expression  |  expression  ‘!=’  expression  |  boolean_val  ;
boolean_val  :  ‘true’  |  ‘false’  ;
low_expression  :  high_expression  ‘+’  low_expression  |  high_expression  ‘-‘  low_expression  |  high_expression  ;
high_expression  :  item  ‘*’  high_expression  |  item  ‘/’  high_expression  |  item  ;
item  :  identifier | number  ;
identifier  :  [a-z|A-Z]+  ;
number  :  [-]?[0-9]+  ;
white_space :  [ \t\r\n]+ -> skip ;
```

## Design Details
* Paradigm of the Language
	

* Parsing Process(Technique)



* Example Program in High Level Language

	
  



