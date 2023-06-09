/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ER;

import Outros.Simbolo;

/**
 *
 * @author marcos
 */
public class Transicao_ER {
    Simbolo e1;
    String s;
    Simbolo e2;

    public Transicao_ER(Simbolo e1, String s, Simbolo e2) {
        this.e1 = e1;
        this.s = s;
        this.e2 = e2;
    }

    public Transicao_ER(Simbolo e1, char ch, Simbolo e2) {
        this.e1 = e1;
        this.s = ""+ch;
        this.e2 = e2;
    }
    
    public String getS() {
        return s;
    }    
    
}
