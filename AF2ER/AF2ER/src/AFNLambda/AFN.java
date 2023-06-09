/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFNLambda;

import Outros.Simbolo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class AFN {
	List<Simbolo> estados;
	List<Simbolo> alfabeto;
        List<Transicao> transicoes;
	List<Simbolo> iniciais;
	List<Simbolo> finais;	

    public AFN(List<Simbolo> estados, List<Simbolo> alfabeto, List<Transicao> transicoes, List<Simbolo> iniciais, List<Simbolo> finais) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.iniciais = iniciais;
        this.finais = finais;
        
        verificar_validade_afn(); //verificar se propriedades de AFN estao sendo respeitadas
    }
    
    public AFN(List<Simbolo> estados, List<Simbolo> alfabeto, List<Transicao> transicoes, Simbolo i, Simbolo f) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.iniciais = new ArrayList<>(); iniciais.add(i);
        this.finais = new ArrayList<>(); finais.add(f);
        
        verificar_validade_afn(); //verificar se propriedades de AFN estao sendo respeitadas
    }
    
    public void verificar_validade_afn(){
        if(estados.isEmpty()){
            System.out.println("Erro. Não foi fornecido nenhum estado");
            System.exit(1);
        }
        if(alfabeto.isEmpty()){
            System.out.println("Erro. Foi fornecido um alfabeto vazio");
            System.exit(1);     
        }
        if(transicoes.isEmpty()){
            System.out.println("Erro. Não foram fornecidas transições");
            System.exit(1);   
        }
        if(iniciais.isEmpty()){
            System.out.println("Erro. Não foi fornecido nenhum estado inicial");
            System.exit(1);     
        }
        if(finais.isEmpty()){
            System.out.println("Erro. Não foi fornecido nenhum estado final");
            System.exit(1);    
        }
        for(Simbolo e: estados)
            for(Simbolo a: alfabeto)
                if(e.ch == a.ch){
                    System.out.println("Erro. Alfabeto e estados deveriam ser conjuntos disjuntos, mas possuem elementos em comum, por exemplo ("+e.ch+")");
                    System.exit(1);    
                }
        
        for(Transicao t: transicoes){
            if(!lista_contem(estados,t.e1)){
                System.out.println("Erro. Uma das transicoes sai de um estado inexistente ("+t.e1.ch+")");
                System.exit(1);   
            }
            if(!lista_contem(estados,t.e2)){
                System.out.println("Erro. Uma das transicoes chega em um estado inexistente ("+t.e2.ch+")");
                System.exit(1);   
            }
            if(!lista_contem(alfabeto,t.s) && t.s.ch != '#'){
                System.out.println("Erro. Uma das transicoes está sob simbolo de transicao inexistente no alfabeto ("+t.s.ch+")");
                System.exit(1);   
            }
        }
        for(Simbolo i: iniciais)
            if(!lista_contem(estados,i) && i.ch != 'i'){
                System.out.println("Erro. Um dos estados iniciais é inexistente ("+i.ch+")");
                System.exit(1);
            }
        for(Simbolo f: finais)
            if(!lista_contem(estados,f) && f.ch != 'f'){
                System.out.println("Erro. Um dos estados finais é inexistente ("+f.ch+")");
                System.exit(1);
            }
        
    }
    
    public boolean lista_contem(List<Simbolo> lista, Simbolo s){
        for(Simbolo l: lista)
            if(l.ch == s.ch)
                return true;
        return false;           
    }
    
    public void add_estado(Simbolo estado){
        estados.add(estado);
    }
        
    public void add_transicao(Transicao transicao){
        transicoes.add(transicao);
    }

    public List<Simbolo> getEstados() {
        return estados;
    }

    public List<Simbolo> getAlfabeto() {
        return alfabeto;
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    public List<Simbolo> getIniciais() {
        return iniciais;
    }

    public List<Simbolo> getFinais() {
        return finais;
    }    
    
    
}
