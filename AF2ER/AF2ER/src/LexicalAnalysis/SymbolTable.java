/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/
package LexicalAnalysis;

/**
 *
 * @author Marcos
 */

import java.util.Map;
import java.util.HashMap;

class SymbolTable {

    private final Map<String, TokenType> st;

    public SymbolTable() {
        st = new HashMap<>();
        
        st.put("", TokenType.END_OF_FILE);
        st.put("",TokenType.INVALID_TOKEN);
        st.put("",TokenType.UNEXPECTED_TOKEN);
        st.put("",TokenType.UNEXPECTED_EOF);
        st.put(",",TokenType.COMMA); 
        st.put(":",TokenType.COLON);
        st.put("\"",TokenType.QUOTATION_MARK);
        st.put("{",TokenType.BRA_OPEN);
        st.put("}",TokenType.BRA_CLOSE);
        st.put("[",TokenType.SQBRA_OPEN);
        st.put("]",TokenType.SQBRA_CLOSE);       	
    }

    public boolean contains(String token) {
        return st.containsKey(token);
    }

    public TokenType get(String token) {
        return this.contains(token) ?
            st.get(token) : TokenType.INVALID_TOKEN;
    }

}