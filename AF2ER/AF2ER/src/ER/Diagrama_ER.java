/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ER;

import AFNLambda.AFN;
import AFNLambda.Transicao;
import Outros.Simbolo;
import R.R;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class Diagrama_ER {
    List<Simbolo> estados;   
    List<Transicao_ER> transicoes;
    String p;
        
    public Diagrama_ER(AFN afn1, R r1) {
        for(Simbolo s: afn1.getEstados())
            if(!lista_contem(r1.getRs(),s) && s.ch != 'i' && s.ch != 'f'){
                System.out.println("Erro. A lista de remoção não contém todos os estados. Falta ("+s.ch+")");
                System.exit(1);
            }
        for(Simbolo r: r1.getRs())
            if(!lista_contem(afn1.getEstados(),r)){
                System.out.println("Erro. A lista de remoção contém estado inexistente ("+r.ch+")");
                System.exit(1);
            }
        transicoes = new ArrayList<>();
        estados = afn1.getEstados();
        for(Transicao t_afn: afn1.getTransicoes()){
            if(existe_transicao_de_e1_para_e2(t_afn.getE1(),t_afn.getE2())){
                for(Transicao_ER t_er: transicoes)
                    if(t_er.e1.ch == t_afn.getE1().ch && t_er.e2.ch == t_afn.getE2().ch)
                        t_er.s += "+" + t_afn.getS().ch;                        
            } else {
                transicoes.add(new Transicao_ER(t_afn.getE1(),t_afn.getS().ch,t_afn.getE2()));
            }
        }
        List<Simbolo> iniciais = new ArrayList<>();
        List<Simbolo> finais = new ArrayList<>();       
        List<Transicao_ER> tr_entrada = new ArrayList<>();      
        List<Transicao_ER> tr_loop = new ArrayList<>(); 
        List<Transicao_ER> tr_saida = new ArrayList<>();      
        String s1,s2,s3;
        
        for(Simbolo r: r1.getRs()){
            for(Simbolo e: estados){
                if(existe_transicao_de_e1_para_e2(e,r) && e.ch != r.ch){
                    iniciais.add(e); tr_entrada.add(transicao_de_e1_para_e2(e,r));
                }
                if(existe_transicao_de_e1_para_e2(r,r)){
                    tr_loop.add(transicao_de_e1_para_e2(r,r));
                }
                if(existe_transicao_de_e1_para_e2(r,e) && r.ch != e.ch){
                    finais.add(e); tr_saida.add(transicao_de_e1_para_e2(r,e));
                }
            }
            for(Simbolo i1: iniciais){
                for(Simbolo f1: finais){
                    s1="";
                    s2="";
                    s3="";
                    for(Transicao_ER t1: tr_entrada){
                        if(t1.e1.ch == i1.ch){
                            if(!t1.s.equals("#"))
                                s1 = t1.s;
                        }                        
                    }
                    for(Transicao_ER t2: tr_saida){
                        if(t2.e2.ch == f1.ch){
                            if(!t2.s.equals("#"))
                                s3 = t2.s;
                        }                        
                    }
                    if(s1.contains("+") && s1.charAt(0)!='(')
                        s1 = '('+s1+')';
                    
                    if(!tr_loop.isEmpty()){
                        s2 = tr_loop.get(0).s;
                        if(s2.length()>1 && s2.charAt(0)!='(')
                            s2 = "("+s2+")*";
                        else
                            s2 += "*";
                    }                    
                    if(s3.contains("+") && s3.charAt(0)!='(')
                        s3 = '('+s3+')';
                    if(existe_transicao_de_e1_para_e2(i1,f1)){
                        for(Transicao_ER t_er: transicoes){
                            if(t_er.e1.ch == i1.ch && t_er.e2.ch == f1.ch){
                                if(i1.ch == f1.ch){
                                    t_er.s = ""+t_er.s+'+'+s1+s2+s3+"";
                                }else{
                                    t_er.s = t_er.s+"+"+s1+s2+s3;
                                }break;                                
                            }
                        }
                    }else{
                        transicoes.add(new Transicao_ER(i1,s1+s2+s3,f1));   
                    }
                }
                remover_transicao_de_e1_para_e2(i1,r);
            }
            iniciais.clear(); 
            finais.clear(); 
            tr_entrada.clear();
            tr_loop.clear();
            tr_saida.clear();
            remover_estado(r);
        }
        String r="",t="";
        if(existe_transicao_de_e1_para_e2(new Simbolo('i'),new Simbolo('f')))
            r = transicao_de_e1_para_e2(new Simbolo('i'),new Simbolo('f')).s;
        if(existe_transicao_de_e1_para_e2(new Simbolo('f'),new Simbolo('i')))
            t = transicao_de_e1_para_e2(new Simbolo('f'),new Simbolo('i')).s;
       
        if(r.isEmpty() && t.isEmpty())
            p = "";
        if(r.isEmpty() && !t.isEmpty())
            p = t;
        if(!r.isEmpty() && t.isEmpty()){
            if(     r.contains("+") && r.contains("(") && r.indexOf('+') < r.indexOf('(') ||
                    r.contains("+") && r.contains(")") && r.indexOf(')') < r.indexOf('+'))
                r = '('+r+')';
            p = r;
        }
        if(!r.isEmpty() && !t.isEmpty()){
            if(r.contains("+") && r.charAt(0)!='(')
                r = '('+r+')';
            if(t.contains("+") && t.charAt(0)!='(')
                t = '('+t+')';
            p = r+"("+t+r+")*";
        }System.out.println(p);
    }

    private boolean existe_transicao_de_e1_para_e2(Simbolo e1, Simbolo e2){
        for(Transicao_ER t_er: transicoes)
            if(t_er.e1.ch == e1.ch && t_er.e2.ch == e2.ch)
                return true;
        return false;
    }
    private Transicao_ER transicao_de_e1_para_e2(Simbolo e1, Simbolo e2){
        for(Transicao_ER t_er: transicoes)
            if(t_er.e1.ch == e1.ch && t_er.e2.ch == e2.ch)
                return t_er;
        return null;
    }
    private void remover_transicao_de_e1_para_e2(Simbolo e1, Simbolo e2){
        for(Transicao_ER t_er: transicoes)
            if(t_er.e1.ch == e1.ch && t_er.e2.ch == e2.ch){
                transicoes.remove(t_er);
                return;
            }               
    }
    private void remover_estado(Simbolo s1){
        for(Simbolo s: estados)
            if(s.ch == s1.ch){
                estados.remove(s);
                return;
            }
    }
    
    public boolean lista_contem(List<Simbolo> lista, Simbolo s){
        for(Simbolo l: lista)
            if(l.ch == s.ch)
                return true;
        return false;           
    }
    
}
