package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ContatoDao {
    public void salvar(Contato contato)throws SQLException{
        String command = """
                INSERT INTO contato
                (nome, numero )
                VALUES
                (?,?);
                """;
        try(Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getNumero());
            stmt.executeUpdate();
        }
    }
    public void alterarNomePorId(int id, String nome)throws SQLException{
        String command = """
                UPDATE contato
                SET nome = ?
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, nome);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    public void alterarNumeroPorId(int id, String numero)throws SQLException{
        String command = """
                UPDATE contato
                SET numero = ?
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, numero);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void alterarNomeENumeroPorId (int id,String nome, String numero) throws SQLException{
        String command = """
                UPDATE contato
                SET nome = ? , numero = ?
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, nome);
            stmt.setString(2, numero);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    public void deletarContatoPorId(int id) throws SQLException{
        String command = """
                DELETE FROM contato
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Contato buscarContatoPorId (int id) throws SQLException{
        String command = """
            SELECT id, nome, numero
            FROM contato
            WHERE id = ?
            """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Contato(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("numero")
                );
            }
        }
        return null;
    }

    public Contato buscarContatoPorNome (String nome) throws SQLException{
        String command = """
                SELECT id, nome, numero
                FROM contato
                WHERE nome = ?
                """;
        try(Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Contato(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("numero")
                );
            }
        }
        return null;
    }

    public Contato buscarContatoPorNumero (String numero ) throws SQLException{
        String command = """
                SELECT id, nome, numero
                FROM contato
                WHERE numero = ?
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, numero);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return new Contato(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("numero")
                );
            }
        }
        return null;
    }

    public ArrayList<Contato> buscarTodos ()throws SQLException{
        ArrayList <Contato> contatos = new ArrayList<>();
        String command = """
                SElECT id, nome, numero
                FROM contato
                """;
        try(Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                contatos.add(new Contato(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("numero")
                ));
            }
            return contatos;
        }
    }

    public int quantidadeTotal() throws SQLException{
        String command = """
                SELECT COUNT(*)
                FROM contato
                """;
        try (Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                int qtd = rs.getInt(1);
                return qtd;
            }
        }
        return 0;
    }

    public String verificarNome(String nome) throws SQLException{
        String command = """
                SELECT id, nome, numero
                FROM contato
                WHERE nome = ?
                """;
        try(Connection conn = Conexao.conectar()){
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return nome;
            }
        }
        return null;
    }

}