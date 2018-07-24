/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO FORNECEDOR NO BANCO DE DADOS
* AUTOR @RENAN
 */
package dao;
//IMPORTS DE TODOS OS OBJETOS QUE UTILIZEI NA CLASSE

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import bean.Fornecedor;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class FornecedorDAO extends Sql {

    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Fornecedor f) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO fornecedor(fornecedor_cod,fornecedor_nome) VALUES (NULL,?)");
            stmt.setString(1, f.getNome());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar :(");
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public static ArrayList<Fornecedor> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM fornecedor");
            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE FORNECEDOR.
            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("fornecedor_cod"));
                f.setNome(rs.getString("fornecedor_nome"));
                fornecedores.add(f);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return fornecedores;
    }

    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Fornecedor f) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE fornecedor SET fornecedor_nome=? WHERE fornecedor_cod=?");
            stmt.setString(1, f.getNome());
            stmt.setInt(2, f.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar e/ou privilégios insuficientes!");
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Fornecedor f) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM fornecedor WHERE fornecedor_cod=?");
            stmt.setInt(1, f.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Apagado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar e/ou privilégios insuficientes!");
            JOptionPane.showMessageDialog(null, "OBS:: Impossível apagar fornecedor que esteja vinculado a um produto.");
        } finally {
            closeConnection(con, stmt);
        }
    }

}
