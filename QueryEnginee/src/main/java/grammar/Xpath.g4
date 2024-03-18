grammar Xpath;

//absolute path
ap
    : doc '/' rp    #childrenAp
    | doc '//' rp   #allAp
    ;


//relative path
rp
   : tagName        #tagRp
   | '*'            #childRp
   | '.'            #currentRp
   | '..'           #parentRp
   | 'text()'       #textRp
   | '@' attName    #attributeRp
   | '(' rp ')'     #haveRp
   | rp '/' rp      #childrenRP
   | rp '//' rp     #allRP
   | rp '[' f ']'   #filterRP
   | rp ',' rp      #unionRp
   ;


//path filter
f
  : rp              #rpFilter
  | rp '=' rp       #eqFilter
  | rp 'eq' rp      #eqFilter
  | rp '==' rp      #isFilter
  | rp 'is' rp      #isFilter
  | rp '=' str      #strFilter
  | '(' f ')'       #haveFilter
  | f 'and' f       #andFilter
  | f 'or' f        #orFilter
  | 'not' f         #notFilter
  ;

doc: 'doc' '('fileName ')';
tagName: ID;
attName: ID;
ID: [a-zA-Z_][a-zA-Z_0-9-]*;
str: STRING;
fileName: str;
STRING: '"' (~'"')* '"';


WS: (' ' | '\t' | '\r' | '\n') -> skip;


