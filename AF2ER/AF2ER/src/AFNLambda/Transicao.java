/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFNLambda;

import Outros.Simbolo;

/**
 *
 * @author marcos
 */
public class Transicao {
	//transicao de e1 para e2 sob 's'
	Simbolo e1;
	Simbolo s;
	Simbolo e2;    

    public Transicao(Simbolo e1, Simbolo s, Simbolo e2) {
        this.e1 = e1;
        this.s = s;
        this.e2 = e2;
    }

    public Simbolo getE1() {
        return e1;
    }

    public Simbolo getS() {
        return s;
    }

    public Simbolo getE2() {
        return e2;
    }
        
    
}