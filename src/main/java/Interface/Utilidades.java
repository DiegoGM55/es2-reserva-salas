/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import java.util.regex.Pattern;

/**
 *
 * @author freit
 */
public class Utilidades {
    
    /**
     * Valida se o CPF informado é válido.
     * 
     * @param cpf CPF em formato de String. Pode ser no formato "111.222.333-44" ou "11122233344".
     * @return true se o CPF for válido, false caso contrário.
     */
    
    public boolean validarCPF(String cpf) {
        if (cpf == null) {
            return false;
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se o CPF tem 11 dígitos e não é uma sequência repetida
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Valida os dígitos verificadores
            int soma = 0, peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }

            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) {
                primeiroDigito = 0;
            }

            if (cpf.charAt(9) - '0' != primeiroDigito) {
                return false;
            }

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }

            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) {
                segundoDigito = 0;
            }

            return cpf.charAt(10) - '0' == segundoDigito;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valida se o e-mail informado tem o formato válido.
     * 
     * @param email E-mail em formato de String.
     * @return true se o e-mail for válido, false caso contrário.
     */
    
    public boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Expressão regular para validar e-mail
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }
    
    /**
     * Valida se a string fornecida pode ser convertida para um número inteiro.
     * 
     * @param valor
     * @return true se a string for um número inteiro válido, false caso contrário.
     */
    public boolean validarInt(String valor) {
        try {
            Integer.valueOf(valor); // Tenta converter para inteiro
            return true; // Conversão bem-sucedida
        } catch (NumberFormatException e) {
            return false; // Falha na conversão, não é um número inteiro
        }
    }
}

