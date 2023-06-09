/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import LexicalAnalysis.LexicalAnalysis;
import LexicalAnalysis.SyntaticalAnalysis;
import LexicalAnalysis.TokenType;

/**
 *
 * @author Marcos
 */

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usar: verificador [AFN]");
            return;
        }
        try (LexicalAnalysis la = new LexicalAnalysis(args[0])) {            
            SyntaticalAnalysis sa = new SyntaticalAnalysis(la);
            
            sa.procStart();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        System.out.printf("Acabou o programa\n");
    }

    private static boolean checkType(TokenType type) {
        return !(type == TokenType.END_OF_FILE ||
                 type == TokenType.INVALID_TOKEN ||
                 type == TokenType.UNEXPECTED_EOF);
    }
}