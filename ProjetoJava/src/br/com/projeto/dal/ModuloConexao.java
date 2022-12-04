/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projeto.dal;

import java.sql.*;

/**
 *
 * @author Karol
 */
public class ModuloConexao {

    // Metodo para conexao com o BD
    public static Connection conector() {
        java.sql.Connection conexao = null;

        // armazena informações referente ao BD
        String url = "jdbc:mysql://localhost:3306/dbloja?useTimezone=true&serverTimezone=UTC&useSSL=false";
        String user = "root";
        String password = "Y&k121018";
        
        // Estabelecendo a conexao com o banco
        try {
            conexao = DriverManager.getConnection(url, user, password);
            System.out.println("Conexao feita com sucesso: "+ conexao);
            return conexao;
        } catch (Exception e) {
            System.out.println("Erro de conexao:" + e.getMessage());
            return null;
        }

    }

}
