//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

// $ANTLR 3.1.1 ./resources/Query.g 2016-05-01 19:37:50


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;


import org.antlr.runtime.tree.*;

public class QueryParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "WHERE", "ORDER", "BY", "AND", "LT", "LE", "GT", "GE", "EQ", "NE", "ID", "PLACEHOLDER", "COMMA", "ASC", "DESC", "LIMIT", "CONSTANT_INTEGER", "CONSTANT_STRING", "TRUE", "FALSE", "WS"
    };
    public static final int CONSTANT_INTEGER=20;
    public static final int BY=6;
    public static final int WHERE=4;
    public static final int GE=11;
    public static final int LT=8;
    public static final int ASC=17;
    public static final int ORDER=5;
    public static final int LIMIT=19;
    public static final int AND=7;
    public static final int ID=14;
    public static final int EOF=-1;
    public static final int TRUE=22;
    public static final int WS=24;
    public static final int COMMA=16;
    public static final int CONSTANT_STRING=21;
    public static final int GT=10;
    public static final int EQ=12;
    public static final int DESC=18;
    public static final int FALSE=23;
    public static final int LE=9;
    public static final int PLACEHOLDER=15;
    public static final int NE=13;

    // delegates
    // delegators


        public QueryParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public QueryParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return QueryParser.tokenNames; }
    public String getGrammarFileName() { return "./resources/Query.g"; }


        public static Tree parse(final RelationModel m, final String str)
          throws QueryParseException {
          try {
            final QueryParser p = new QueryParser(
              new TokenRewriteStream(
                new QueryLexer(
                  new ANTLRStringStream(str)
                )
              )
            );
            p.relationModel = m;
            return (Tree)p.query().getTree();
          } catch (QueryParseInternalException e) {
            throw new QueryParseException(e.getMessage());
          } catch (RecognitionException e) {
            throw new QueryParseException(e.getMessage());
          }
        }

        public static class Column extends CommonTree {
          private final ColumnModel field;

          public Column(int ttype, Token t, final RelationModel relationModel) {
            token = t;
            field = relationModel.getField(t.getText());
            if (field == null) {
              throw new QueryParseInternalException("No field " + t.getText());
            }
          }

          public Column(final Column o, final ColumnModel f) {
            token = o.token;
            field = f;
          }

          public ColumnModel getField() {
            return field;
          }

          public Tree dupNode() {
            return new Column(this, field);
          }
        }

        private RelationModel relationModel;

        public void displayRecognitionError(String[] tokenNames,
                                            RecognitionException e) {
            String hdr = getErrorHeader(e);
            String msg = getErrorMessage(e, tokenNames);
            throw new QueryParseInternalException(hdr + " " + msg);
        }


    public static class query_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "query"
    // ./resources/Query.g:115:1: query : ( where )? ( orderBy )? ( limit )? ;
    public final QueryParser.query_return query() throws RecognitionException {
        QueryParser.query_return retval = new QueryParser.query_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        QueryParser.where_return where1 = null;

        QueryParser.orderBy_return orderBy2 = null;

        QueryParser.limit_return limit3 = null;



        try {
            // ./resources/Query.g:116:3: ( ( where )? ( orderBy )? ( limit )? )
            // ./resources/Query.g:116:5: ( where )? ( orderBy )? ( limit )?
            {
            root_0 = (Object)adaptor.nil();

            // ./resources/Query.g:116:5: ( where )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==WHERE) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // ./resources/Query.g:116:5: where
                    {
                    pushFollow(FOLLOW_where_in_query182);
                    where1=where();

                    state._fsp--;

                    adaptor.addChild(root_0, where1.getTree());

                    }
                    break;

            }

            // ./resources/Query.g:116:12: ( orderBy )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==ORDER) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ./resources/Query.g:116:12: orderBy
                    {
                    pushFollow(FOLLOW_orderBy_in_query185);
                    orderBy2=orderBy();

                    state._fsp--;

                    adaptor.addChild(root_0, orderBy2.getTree());

                    }
                    break;

            }

            // ./resources/Query.g:116:21: ( limit )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==LIMIT) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ./resources/Query.g:116:21: limit
                    {
                    pushFollow(FOLLOW_limit_in_query188);
                    limit3=limit();

                    state._fsp--;

                    adaptor.addChild(root_0, limit3.getTree());

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "query"

    public static class where_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "where"
    // ./resources/Query.g:119:1: where : WHERE conditions ;
    public final QueryParser.where_return where() throws RecognitionException {
        QueryParser.where_return retval = new QueryParser.where_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token WHERE4=null;
        QueryParser.conditions_return conditions5 = null;


        Object WHERE4_tree=null;

        try {
            // ./resources/Query.g:120:3: ( WHERE conditions )
            // ./resources/Query.g:120:5: WHERE conditions
            {
            root_0 = (Object)adaptor.nil();

            WHERE4=(Token)match(input,WHERE,FOLLOW_WHERE_in_where202); 
            WHERE4_tree = (Object)adaptor.create(WHERE4);
            root_0 = (Object)adaptor.becomeRoot(WHERE4_tree, root_0);

            pushFollow(FOLLOW_conditions_in_where205);
            conditions5=conditions();

            state._fsp--;

            adaptor.addChild(root_0, conditions5.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "where"

    public static class orderBy_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "orderBy"
    // ./resources/Query.g:123:1: orderBy : ORDER BY fieldSort ( COMMA fieldSort )* ;
    public final QueryParser.orderBy_return orderBy() throws RecognitionException {
        QueryParser.orderBy_return retval = new QueryParser.orderBy_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ORDER6=null;
        Token BY7=null;
        Token COMMA9=null;
        QueryParser.fieldSort_return fieldSort8 = null;

        QueryParser.fieldSort_return fieldSort10 = null;


        Object ORDER6_tree=null;
        Object BY7_tree=null;
        Object COMMA9_tree=null;

        try {
            // ./resources/Query.g:124:3: ( ORDER BY fieldSort ( COMMA fieldSort )* )
            // ./resources/Query.g:124:5: ORDER BY fieldSort ( COMMA fieldSort )*
            {
            root_0 = (Object)adaptor.nil();

            ORDER6=(Token)match(input,ORDER,FOLLOW_ORDER_in_orderBy218); 
            ORDER6_tree = (Object)adaptor.create(ORDER6);
            root_0 = (Object)adaptor.becomeRoot(ORDER6_tree, root_0);

            BY7=(Token)match(input,BY,FOLLOW_BY_in_orderBy221); 
            pushFollow(FOLLOW_fieldSort_in_orderBy224);
            fieldSort8=fieldSort();

            state._fsp--;

            adaptor.addChild(root_0, fieldSort8.getTree());
            // ./resources/Query.g:124:26: ( COMMA fieldSort )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ./resources/Query.g:124:27: COMMA fieldSort
            	    {
            	    COMMA9=(Token)match(input,COMMA,FOLLOW_COMMA_in_orderBy227); 
            	    pushFollow(FOLLOW_fieldSort_in_orderBy230);
            	    fieldSort10=fieldSort();

            	    state._fsp--;

            	    adaptor.addChild(root_0, fieldSort10.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "orderBy"

    public static class fieldSort_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fieldSort"
    // ./resources/Query.g:127:1: fieldSort : ( field sortDirection | field -> ^( ASC field ) );
    public final QueryParser.fieldSort_return fieldSort() throws RecognitionException {
        QueryParser.fieldSort_return retval = new QueryParser.fieldSort_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        QueryParser.field_return field11 = null;

        QueryParser.sortDirection_return sortDirection12 = null;

        QueryParser.field_return field13 = null;


        RewriteRuleSubtreeStream stream_field=new RewriteRuleSubtreeStream(adaptor,"rule field");
        try {
            // ./resources/Query.g:128:3: ( field sortDirection | field -> ^( ASC field ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ID) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==EOF||LA5_1==COMMA||LA5_1==LIMIT) ) {
                    alt5=2;
                }
                else if ( ((LA5_1>=ASC && LA5_1<=DESC)) ) {
                    alt5=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // ./resources/Query.g:128:5: field sortDirection
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_field_in_fieldSort245);
                    field11=field();

                    state._fsp--;

                    adaptor.addChild(root_0, field11.getTree());
                    pushFollow(FOLLOW_sortDirection_in_fieldSort247);
                    sortDirection12=sortDirection();

                    state._fsp--;

                    root_0 = (Object)adaptor.becomeRoot(sortDirection12.getTree(), root_0);

                    }
                    break;
                case 2 :
                    // ./resources/Query.g:129:5: field
                    {
                    pushFollow(FOLLOW_field_in_fieldSort254);
                    field13=field();

                    state._fsp--;

                    stream_field.add(field13.getTree());


                    // AST REWRITE
                    // elements: field
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

                    root_0 = (Object)adaptor.nil();
                    // 129:11: -> ^( ASC field )
                    {
                        // ./resources/Query.g:129:14: ^( ASC field )
                        {
                        Object root_1 = (Object)adaptor.nil();
                        root_1 = (Object)adaptor.becomeRoot((Object)adaptor.create(ASC, "ASC"), root_1);

                        adaptor.addChild(root_1, stream_field.nextTree());

                        adaptor.addChild(root_0, root_1);
                        }

                    }

                    retval.tree = root_0;
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fieldSort"

    public static class sortDirection_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sortDirection"
    // ./resources/Query.g:132:1: sortDirection : ( ASC | DESC );
    public final QueryParser.sortDirection_return sortDirection() throws RecognitionException {
        QueryParser.sortDirection_return retval = new QueryParser.sortDirection_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set14=null;

        Object set14_tree=null;

        try {
            // ./resources/Query.g:133:3: ( ASC | DESC )
            // ./resources/Query.g:
            {
            root_0 = (Object)adaptor.nil();

            set14=(Token)input.LT(1);
            if ( (input.LA(1)>=ASC && input.LA(1)<=DESC) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set14));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sortDirection"

    public static class limit_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limit"
    // ./resources/Query.g:137:1: limit : LIMIT limitArg ;
    public final QueryParser.limit_return limit() throws RecognitionException {
        QueryParser.limit_return retval = new QueryParser.limit_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token LIMIT15=null;
        QueryParser.limitArg_return limitArg16 = null;


        Object LIMIT15_tree=null;

        try {
            // ./resources/Query.g:138:3: ( LIMIT limitArg )
            // ./resources/Query.g:138:5: LIMIT limitArg
            {
            root_0 = (Object)adaptor.nil();

            LIMIT15=(Token)match(input,LIMIT,FOLLOW_LIMIT_in_limit294); 
            LIMIT15_tree = (Object)adaptor.create(LIMIT15);
            root_0 = (Object)adaptor.becomeRoot(LIMIT15_tree, root_0);

            pushFollow(FOLLOW_limitArg_in_limit297);
            limitArg16=limitArg();

            state._fsp--;

            adaptor.addChild(root_0, limitArg16.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "limit"

    public static class limitArg_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "limitArg"
    // ./resources/Query.g:141:1: limitArg : ( PLACEHOLDER | CONSTANT_INTEGER );
    public final QueryParser.limitArg_return limitArg() throws RecognitionException {
        QueryParser.limitArg_return retval = new QueryParser.limitArg_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set17=null;

        Object set17_tree=null;

        try {
            // ./resources/Query.g:142:3: ( PLACEHOLDER | CONSTANT_INTEGER )
            // ./resources/Query.g:
            {
            root_0 = (Object)adaptor.nil();

            set17=(Token)input.LT(1);
            if ( input.LA(1)==PLACEHOLDER||input.LA(1)==CONSTANT_INTEGER ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set17));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "limitArg"

    public static class conditions_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditions"
    // ./resources/Query.g:146:1: conditions : ( condition AND condition ( AND condition )* | condition );
    public final QueryParser.conditions_return conditions() throws RecognitionException {
        QueryParser.conditions_return retval = new QueryParser.conditions_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token AND19=null;
        Token AND21=null;
        QueryParser.condition_return condition18 = null;

        QueryParser.condition_return condition20 = null;

        QueryParser.condition_return condition22 = null;

        QueryParser.condition_return condition23 = null;


        Object AND19_tree=null;
        Object AND21_tree=null;

        try {
            // ./resources/Query.g:147:3: ( condition AND condition ( AND condition )* | condition )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ID) ) {
                int LA7_1 = input.LA(2);

                if ( ((LA7_1>=LT && LA7_1<=NE)) ) {
                    switch ( input.LA(3) ) {
                    case PLACEHOLDER:
                        {
                        int LA7_3 = input.LA(4);

                        if ( (LA7_3==EOF||LA7_3==ORDER||LA7_3==LIMIT) ) {
                            alt7=2;
                        }
                        else if ( (LA7_3==AND) ) {
                            alt7=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 3, input);

                            throw nvae;
                        }
                        }
                        break;
                    case CONSTANT_INTEGER:
                        {
                        int LA7_4 = input.LA(4);

                        if ( (LA7_4==EOF||LA7_4==ORDER||LA7_4==LIMIT) ) {
                            alt7=2;
                        }
                        else if ( (LA7_4==AND) ) {
                            alt7=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 4, input);

                            throw nvae;
                        }
                        }
                        break;
                    case TRUE:
                    case FALSE:
                        {
                        int LA7_5 = input.LA(4);

                        if ( (LA7_5==EOF||LA7_5==ORDER||LA7_5==LIMIT) ) {
                            alt7=2;
                        }
                        else if ( (LA7_5==AND) ) {
                            alt7=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 5, input);

                            throw nvae;
                        }
                        }
                        break;
                    case CONSTANT_STRING:
                        {
                        int LA7_6 = input.LA(4);

                        if ( (LA7_6==EOF||LA7_6==ORDER||LA7_6==LIMIT) ) {
                            alt7=2;
                        }
                        else if ( (LA7_6==AND) ) {
                            alt7=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 7, 6, input);

                            throw nvae;
                        }
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 2, input);

                        throw nvae;
                    }

                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // ./resources/Query.g:147:5: condition AND condition ( AND condition )*
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_condition_in_conditions329);
                    condition18=condition();

                    state._fsp--;

                    adaptor.addChild(root_0, condition18.getTree());
                    AND19=(Token)match(input,AND,FOLLOW_AND_in_conditions331); 
                    AND19_tree = (Object)adaptor.create(AND19);
                    root_0 = (Object)adaptor.becomeRoot(AND19_tree, root_0);

                    pushFollow(FOLLOW_condition_in_conditions334);
                    condition20=condition();

                    state._fsp--;

                    adaptor.addChild(root_0, condition20.getTree());
                    // ./resources/Query.g:147:30: ( AND condition )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==AND) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // ./resources/Query.g:147:31: AND condition
                    	    {
                    	    AND21=(Token)match(input,AND,FOLLOW_AND_in_conditions337); 
                    	    pushFollow(FOLLOW_condition_in_conditions340);
                    	    condition22=condition();

                    	    state._fsp--;

                    	    adaptor.addChild(root_0, condition22.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // ./resources/Query.g:148:5: condition
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_condition_in_conditions348);
                    condition23=condition();

                    state._fsp--;

                    adaptor.addChild(root_0, condition23.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "conditions"

    public static class condition_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "condition"
    // ./resources/Query.g:151:1: condition : field compare_op conditionValue ;
    public final QueryParser.condition_return condition() throws RecognitionException {
        QueryParser.condition_return retval = new QueryParser.condition_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        QueryParser.field_return field24 = null;

        QueryParser.compare_op_return compare_op25 = null;

        QueryParser.conditionValue_return conditionValue26 = null;



        try {
            // ./resources/Query.g:152:3: ( field compare_op conditionValue )
            // ./resources/Query.g:152:5: field compare_op conditionValue
            {
            root_0 = (Object)adaptor.nil();

            pushFollow(FOLLOW_field_in_condition361);
            field24=field();

            state._fsp--;

            adaptor.addChild(root_0, field24.getTree());
            pushFollow(FOLLOW_compare_op_in_condition363);
            compare_op25=compare_op();

            state._fsp--;

            root_0 = (Object)adaptor.becomeRoot(compare_op25.getTree(), root_0);
            pushFollow(FOLLOW_conditionValue_in_condition366);
            conditionValue26=conditionValue();

            state._fsp--;

            adaptor.addChild(root_0, conditionValue26.getTree());

            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "condition"

    public static class compare_op_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compare_op"
    // ./resources/Query.g:155:1: compare_op : ( LT | LE | GT | GE | EQ | NE );
    public final QueryParser.compare_op_return compare_op() throws RecognitionException {
        QueryParser.compare_op_return retval = new QueryParser.compare_op_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set27=null;

        Object set27_tree=null;

        try {
            // ./resources/Query.g:156:2: ( LT | LE | GT | GE | EQ | NE )
            // ./resources/Query.g:
            {
            root_0 = (Object)adaptor.nil();

            set27=(Token)input.LT(1);
            if ( (input.LA(1)>=LT && input.LA(1)<=NE) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set27));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "compare_op"

    public static class field_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "field"
    // ./resources/Query.g:164:1: field : ID -> ID[$ID, relationModel] ;
    public final QueryParser.field_return field() throws RecognitionException {
        QueryParser.field_return retval = new QueryParser.field_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token ID28=null;

        Object ID28_tree=null;
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");

        try {
            // ./resources/Query.g:165:3: ( ID -> ID[$ID, relationModel] )
            // ./resources/Query.g:165:5: ID
            {
            ID28=(Token)match(input,ID,FOLLOW_ID_in_field415);  
            stream_ID.add(ID28);



            // AST REWRITE
            // elements: ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (Object)adaptor.nil();
            // 165:8: -> ID[$ID, relationModel]
            {
                adaptor.addChild(root_0, new Column(ID, ID28, relationModel));

            }

            retval.tree = root_0;
            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "field"

    public static class conditionValue_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conditionValue"
    // ./resources/Query.g:168:1: conditionValue : ( PLACEHOLDER | CONSTANT_INTEGER | constantBoolean | CONSTANT_STRING );
    public final QueryParser.conditionValue_return conditionValue() throws RecognitionException {
        QueryParser.conditionValue_return retval = new QueryParser.conditionValue_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token PLACEHOLDER29=null;
        Token CONSTANT_INTEGER30=null;
        Token CONSTANT_STRING32=null;
        QueryParser.constantBoolean_return constantBoolean31 = null;


        Object PLACEHOLDER29_tree=null;
        Object CONSTANT_INTEGER30_tree=null;
        Object CONSTANT_STRING32_tree=null;

        try {
            // ./resources/Query.g:169:3: ( PLACEHOLDER | CONSTANT_INTEGER | constantBoolean | CONSTANT_STRING )
            int alt8=4;
            switch ( input.LA(1) ) {
            case PLACEHOLDER:
                {
                alt8=1;
                }
                break;
            case CONSTANT_INTEGER:
                {
                alt8=2;
                }
                break;
            case TRUE:
            case FALSE:
                {
                alt8=3;
                }
                break;
            case CONSTANT_STRING:
                {
                alt8=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // ./resources/Query.g:169:5: PLACEHOLDER
                    {
                    root_0 = (Object)adaptor.nil();

                    PLACEHOLDER29=(Token)match(input,PLACEHOLDER,FOLLOW_PLACEHOLDER_in_conditionValue436); 
                    PLACEHOLDER29_tree = (Object)adaptor.create(PLACEHOLDER29);
                    adaptor.addChild(root_0, PLACEHOLDER29_tree);


                    }
                    break;
                case 2 :
                    // ./resources/Query.g:170:5: CONSTANT_INTEGER
                    {
                    root_0 = (Object)adaptor.nil();

                    CONSTANT_INTEGER30=(Token)match(input,CONSTANT_INTEGER,FOLLOW_CONSTANT_INTEGER_in_conditionValue442); 
                    CONSTANT_INTEGER30_tree = (Object)adaptor.create(CONSTANT_INTEGER30);
                    adaptor.addChild(root_0, CONSTANT_INTEGER30_tree);


                    }
                    break;
                case 3 :
                    // ./resources/Query.g:171:5: constantBoolean
                    {
                    root_0 = (Object)adaptor.nil();

                    pushFollow(FOLLOW_constantBoolean_in_conditionValue448);
                    constantBoolean31=constantBoolean();

                    state._fsp--;

                    adaptor.addChild(root_0, constantBoolean31.getTree());

                    }
                    break;
                case 4 :
                    // ./resources/Query.g:172:5: CONSTANT_STRING
                    {
                    root_0 = (Object)adaptor.nil();

                    CONSTANT_STRING32=(Token)match(input,CONSTANT_STRING,FOLLOW_CONSTANT_STRING_in_conditionValue454); 
                    CONSTANT_STRING32_tree = (Object)adaptor.create(CONSTANT_STRING32);
                    adaptor.addChild(root_0, CONSTANT_STRING32_tree);


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "conditionValue"

    public static class constantBoolean_return extends ParserRuleReturnScope {
        Object tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "constantBoolean"
    // ./resources/Query.g:175:1: constantBoolean : ( TRUE | FALSE );
    public final QueryParser.constantBoolean_return constantBoolean() throws RecognitionException {
        QueryParser.constantBoolean_return retval = new QueryParser.constantBoolean_return();
        retval.start = input.LT(1);

        Object root_0 = null;

        Token set33=null;

        Object set33_tree=null;

        try {
            // ./resources/Query.g:176:3: ( TRUE | FALSE )
            // ./resources/Query.g:
            {
            root_0 = (Object)adaptor.nil();

            set33=(Token)input.LT(1);
            if ( (input.LA(1)>=TRUE && input.LA(1)<=FALSE) ) {
                input.consume();
                adaptor.addChild(root_0, (Object)adaptor.create(set33));
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            retval.tree = (Object)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (Object)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "constantBoolean"

    // Delegated rules


 

    public static final BitSet FOLLOW_where_in_query182 = new BitSet(new long[]{0x0000000000080022L});
    public static final BitSet FOLLOW_orderBy_in_query185 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_limit_in_query188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHERE_in_where202 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_conditions_in_where205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ORDER_in_orderBy218 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_BY_in_orderBy221 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_fieldSort_in_orderBy224 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_COMMA_in_orderBy227 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_fieldSort_in_orderBy230 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_field_in_fieldSort245 = new BitSet(new long[]{0x0000000000060000L});
    public static final BitSet FOLLOW_sortDirection_in_fieldSort247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_field_in_fieldSort254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_sortDirection0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LIMIT_in_limit294 = new BitSet(new long[]{0x0000000000108000L});
    public static final BitSet FOLLOW_limitArg_in_limit297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_limitArg0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condition_in_conditions329 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_AND_in_conditions331 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_condition_in_conditions334 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_AND_in_conditions337 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_condition_in_conditions340 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_condition_in_conditions348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_field_in_condition361 = new BitSet(new long[]{0x0000000000003F00L});
    public static final BitSet FOLLOW_compare_op_in_condition363 = new BitSet(new long[]{0x0000000000F08000L});
    public static final BitSet FOLLOW_conditionValue_in_condition366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_compare_op0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ID_in_field415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLACEHOLDER_in_conditionValue436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_INTEGER_in_conditionValue442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constantBoolean_in_conditionValue448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONSTANT_STRING_in_conditionValue454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_constantBoolean0 = new BitSet(new long[]{0x0000000000000002L});

}