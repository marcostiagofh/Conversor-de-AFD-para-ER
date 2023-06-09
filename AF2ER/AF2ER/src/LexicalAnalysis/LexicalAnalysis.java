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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PushbackInputStream;

public class LexicalAnalysis implements AutoCloseable {
    private final boolean should_print = false;
    
    private int line;
    private PushbackInputStream input;
    private final SymbolTable st ;

    public LexicalAnalysis(String filename) throws LexicalException {
        try {
            this.input = new PushbackInputStream(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            throw new LexicalException("Unable to open file");
        }

        this.st = new SymbolTable();            
    }

    @Override
    public void close() {
        try {
            input.close();
        } catch (IOException e) {
            // ignore
        }
    }
    
    public Lexeme nextToken() throws IOException {
        Lexeme lex = new Lexeme("", TokenType.END_OF_FILE);
         
        int estado = 1;
        int c;
        while(estado!= 3) {
                c = input.read();
                switch(estado) {
                        case 1:
                        {
                            if (c == -1) {
                                return lex;
                            } else if (c == ' ' || c == '\n' || c == '\r' || c == '\t'){
                                //nao faça nada                                
                            } else if (c == '#' || Character.isDigit(c) || Character.isLetter(c) || c == '"' | c == ':' || c == ',' || c == '[' || c == ']' || c == '{' || c == '}'){
                                lex.token += (char) c;
                                estado = 3;       
                            } else {
                                lex.token += (char) c;
                                lex.type = TokenType.INVALID_TOKEN;
                                print(lex);
                                System.out.println(this.line+"Lexema inválido ["+lex.token+"]");						
                                System.exit(1);
                                return null;				
                            }
                            break;
                        }                        
                        default:
                        break;
                }
        }
   

        if (st.contains(lex.token))
            lex.type = st.get(lex.token);	
        else 
            lex.type = TokenType.NOME;
        
        print(lex);

        return lex;
    }
    
    public void print(Lexeme lex){
        if(should_print)
            System.out.println("Token: "+lex.token+" "+lex.type);
    }

}