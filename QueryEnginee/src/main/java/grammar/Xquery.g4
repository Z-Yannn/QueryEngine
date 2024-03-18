grammar Xquery;
import Xpath;

//XQ
xq
    : var                                               #varXQ
    | str                                               #strXQ
    | ap                                                #apXQ
    | '(' xq ')'                                        #haveXQ
    | xq ',' xq                                         #unionXQ
    | xq '/' rp                                         #childXQ
    | xq '//' rp                                        #allXQ
    | '<' tagName '>' '{' xq '}' '</' tagName '>'       #tagXQ
    | forClause letClause? whereClause? returnClause    #flwrXQ
    | letClause xq                                      #letXQ
    | joinClause                                        #joinXQ
    ;

// for clause
forClause: 'for' var 'in' xq (',' var 'in' xq)*;

// let clause
letClause: 'let' var ':=' xq (',' var ':=' xq)*;

// where clause
whereClause: 'where' cond;

// return clause
returnClause: 'return' xq;

// join clause
joinClause: 'join' '(' xq ',' xq ',' tags ',' tags ')';
tags: '[' (ID (',' ID)*)? ']' ;

// condition
cond
    : xq '=' xq                                                                 #eqCond
    | xq 'eq' xq                                                                #eqCond
    | xq '==' xq                                                                #isCond
    | xq 'is' xq                                                                #isCond
    | 'empty(' xq ')'                                                           #empCond
    | 'some' var 'in' xq (',' var 'in' xq)* 'satisfies' cond                    #satCond
    | '(' cond ')'                                                              #haveCond
    | cond 'and' cond                                                          # andCond
    | cond 'or' cond                                                           # orCond
    | 'not' cond                                                               # notCond
    ;

var: '$' ID;