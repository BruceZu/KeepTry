lexer grammar Query;
options {
  language=Java;

}

@members {
    public void displayRecognitionError(String[] tokenNames,
                                        RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        throw new QueryParseInternalException(hdr + " " + msg);
    }
}
@header {
package com.google.gwtorm.schema;
}

// $ANTLR src "./resources/Query.g" 180
WHERE: 'WHERE' ;
// $ANTLR src "./resources/Query.g" 181
ORDER: 'ORDER' ;
// $ANTLR src "./resources/Query.g" 182
BY:    'BY'    ;
// $ANTLR src "./resources/Query.g" 183
AND:   'AND'   ;
// $ANTLR src "./resources/Query.g" 184
ASC:   'ASC'   ;
// $ANTLR src "./resources/Query.g" 185
DESC:  'DESC'  ;
// $ANTLR src "./resources/Query.g" 186
LIMIT: 'LIMIT' ;
// $ANTLR src "./resources/Query.g" 187
TRUE:  'true'  ;
// $ANTLR src "./resources/Query.g" 188
FALSE: 'false' ;

// $ANTLR src "./resources/Query.g" 190
LT : '<'  ;
// $ANTLR src "./resources/Query.g" 191
LE : '<=' ;
// $ANTLR src "./resources/Query.g" 192
GT : '>'  ;
// $ANTLR src "./resources/Query.g" 193
GE : '>=' ;
// $ANTLR src "./resources/Query.g" 194
EQ : '='  ;
// $ANTLR src "./resources/Query.g" 195
NE : '!=' ;

// $ANTLR src "./resources/Query.g" 197
PLACEHOLDER: '?' ;
// $ANTLR src "./resources/Query.g" 198
COMMA: ',' ;

// $ANTLR src "./resources/Query.g" 200
CONSTANT_INTEGER
  : '0'
  | '1'..'9' ('0'..'9')*
  ;

// $ANTLR src "./resources/Query.g" 205
CONSTANT_STRING
  : '\'' ( ~('\'') )* '\''
  ;

// $ANTLR src "./resources/Query.g" 209
ID
  : ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
  ;

// $ANTLR src "./resources/Query.g" 213
WS
  :  ( ' ' | '\r' | '\t' | '\n' ) { $channel=HIDDEN; }
  ;
