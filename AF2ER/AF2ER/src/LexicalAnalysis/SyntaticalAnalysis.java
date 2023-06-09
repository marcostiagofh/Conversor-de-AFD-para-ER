/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalysis;

import AFNLambda.AFN;
import AFNLambda.Transicao;
import ER.Diagrama_ER;
import Outros.Simbolo;
import R.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcos
 */

public class SyntaticalAnalysis {

	private LexicalAnalysis lex;
	private Lexeme current;
                
	public SyntaticalAnalysis(LexicalAnalysis lex) throws IOException{
                this.lex = lex;
		this.current = lex.nextToken();
	}        
	
        public Lexeme getCurrent() {
            return current;
        }

        public void setCurrent(Lexeme current) {
            this.current = current;
        }

	public void matchToken(TokenType type)throws IOException {
		if (type == this.current.type){
			this.current = this.lex.nextToken();
                }else {
                    if(this.current.type == TokenType.END_OF_FILE){
                        System.out.println(" Fim de arquivo inesperado");
                        System.exit(1);                                     
                    } else {
                        System.out.println(" Lexema não esperado ["+this.current.token+"]");
                        System.exit(1); 
                    }
		}
	}              
        
        //<procStart> ::= '{' '"' <procChar> <procChar> '"' ':' '[' <procEstados> ',' <procAlfabeto> ',' <procTransicoes> ',' <procIniciais> ',' <procFinais> ']' ',' '"' <procChar> '"' ':' <procR> '}'
        public void procStart() throws IOException{
            matchToken(TokenType.BRA_OPEN);
            matchToken(TokenType.QUOTATION_MARK);
            char ch_a = procChar();
            char ch_f = procChar();
            matchToken(TokenType.QUOTATION_MARK);
            matchToken(TokenType.COLON);
            matchToken(TokenType.SQBRA_OPEN);
            List<Simbolo> estados = procEstados();
            matchToken(TokenType.COMMA);
            List<Simbolo> alfabeto = procAlfabeto();
            matchToken(TokenType.COMMA);
            List<Transicao> transicoes = procTransicoes();
            matchToken(TokenType.COMMA);
            List<Simbolo> iniciais = procIniciais();
            matchToken(TokenType.COMMA);
            List<Simbolo> finais = procFinais();
            matchToken(TokenType.SQBRA_CLOSE);
            matchToken(TokenType.COMMA);
            matchToken(TokenType.QUOTATION_MARK);
            char ch_r = procChar();
            matchToken(TokenType.QUOTATION_MARK);
            matchToken(TokenType.COLON);
            List<Simbolo> rs = procR();
            matchToken(TokenType.BRA_CLOSE); 
            
            AFN m = new AFN(estados,alfabeto,transicoes,iniciais,finais);
            R r = new R(rs);
            
            //Obtendo M1 a partir de M
            Simbolo i = new Simbolo('i');
            Simbolo f = new Simbolo('f');
            AFN m1 = new AFN(estados,alfabeto,transicoes,i,f);
            for(Simbolo s: m.getIniciais())
                m1.add_transicao(new Transicao(i,new Simbolo('#'),s));
            for(Simbolo s: m.getFinais())
                m1.add_transicao(new Transicao(s,new Simbolo('#'),f));
            m1.add_estado(i); m1.add_estado(f);
            
            //Gerar Diagrama_ER a partir de AFN e R
            Diagrama_ER d1 = new Diagrama_ER(m1,r);
        }
        
        //<nome> ::= '"' <procChar> '"'
        public char procNome() throws IOException{
            matchToken(TokenType.QUOTATION_MARK);
            char ch = procChar();
            matchToken(TokenType.QUOTATION_MARK);
            return ch;
        }
        
        //<procChar> ::= 'nome' 
        public char procChar() throws IOException{
            char ch = current.token.charAt(0);
            matchToken(TokenType.NOME);
            return ch;
        }
        
        public void showError(){
            if(current.type == TokenType.UNEXPECTED_EOF){
                    //imprimir erro 1
            } else {
                    //imprimir erro 2
            }
            System.exit(1);
        }

        //<procEstados> ::= '[' <nome> { ',' <nome> } ']' 
        public List<Simbolo> procEstados() throws IOException {
            List<Simbolo> estados = new ArrayList<>();
            matchToken(TokenType.SQBRA_OPEN);
            char ch = procNome();
            estados.add(new Simbolo(ch));
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                ch = procNome();
                estados.add(new Simbolo(ch));            
            }            
            matchToken(TokenType.SQBRA_CLOSE);
            return estados;
        }
        
        //<procAlfabeto> ::= '[' <nome> { ',' <nome> } ']' 
        public List<Simbolo> procAlfabeto() throws IOException {
            List<Simbolo> alfabeto = new ArrayList<>();
            matchToken(TokenType.SQBRA_OPEN);
            char ch = procNome();
            alfabeto.add(new Simbolo(ch));
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                ch = procNome();
                alfabeto.add(new Simbolo(ch));            
            }            
            matchToken(TokenType.SQBRA_CLOSE);
            return alfabeto;
        }
    
        //<procTransicoes> ::= '[' '[' <nome> ',' <nome> ',' <nome> ']' { ',' '[' <nome> ',' <nome> ',' <nome> ']' } ']'
        public List<Transicao> procTransicoes() throws IOException {
            List<Transicao> transicoes = new ArrayList<>();
            matchToken(TokenType.SQBRA_OPEN);
            matchToken(TokenType.SQBRA_OPEN);
            char e1 = procNome();
            matchToken(TokenType.COMMA);
            char s = procNome();
            matchToken(TokenType.COMMA);
            char e2 = procNome();
            transicoes.add(new Transicao(new Simbolo(e1),new Simbolo(s),new Simbolo(e2)));            
            matchToken(TokenType.SQBRA_CLOSE);
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                matchToken(TokenType.SQBRA_OPEN);
                e1 = procNome();
                matchToken(TokenType.COMMA);
                s = procNome();
                matchToken(TokenType.COMMA);
                e2 = procNome();
                transicoes.add(new Transicao(new Simbolo(e1),new Simbolo(s),new Simbolo(e2)));            
                matchToken(TokenType.SQBRA_CLOSE);            
            }
            matchToken(TokenType.SQBRA_CLOSE);
            return transicoes;
        }

        //<procIniciais> ::= '[' <nome> { ',' <nome> } ']' 
        public List<Simbolo> procIniciais() throws IOException {
            List<Simbolo> iniciais = new ArrayList<>();
            matchToken(TokenType.SQBRA_OPEN);
            char ch = procNome();
            iniciais.add(new Simbolo(ch));
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                ch = procNome();
                iniciais.add(new Simbolo(ch));            
            }            
            matchToken(TokenType.SQBRA_CLOSE);
            return iniciais;
        }

        //<procFinais> ::= '[' <nome> { ',' <nome> } ']' 
        public List<Simbolo> procFinais() throws IOException {
            List<Simbolo> finais = new ArrayList<>();
            matchToken(TokenType.SQBRA_OPEN);
            char ch = procNome();
            finais.add(new Simbolo(ch));
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                ch = procNome();
                finais.add(new Simbolo(ch));            
            }            
            matchToken(TokenType.SQBRA_CLOSE);
            return finais;
        }

        //<procR> ::= '[' <nome> { ',' <nome> } ']' 
        public List<Simbolo> procR() throws IOException {
            List<Simbolo> r = new ArrayList<>();
            matchToken(TokenType.SQBRA_OPEN);
            char ch = procNome();
            r.add(new Simbolo(ch));
            while(current.type == TokenType.COMMA){
                matchToken(TokenType.COMMA);
                ch = procNome();
                r.add(new Simbolo(ch));            
            }            
            matchToken(TokenType.SQBRA_CLOSE);
            return r;
        }
}
