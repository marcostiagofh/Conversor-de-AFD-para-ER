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

public enum TokenType {
    END_OF_FILE,
    INVALID_TOKEN,
    UNEXPECTED_TOKEN,
    UNEXPECTED_EOF,    
    COLON,
    QUOTATION_MARK,
    BRA_OPEN,
    BRA_CLOSE,
    SQBRA_OPEN,
    SQBRA_CLOSE,
    COMMA,
    NOME;
}