/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO CLIENTE NO BANCO DE DADOS
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
import bean.Cliente;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class ClienteDAO extends Sql {

    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Cliente c) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO clientes VALUES (NULL,?,?,?);");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            stmt.setString(3, c.getEmail());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro -> Email: "+c.getEmail()+" já pertence a base de dados!");
            JOptionPane.showMessageDialog(null, "O email deve ser único !");
        } finally {
            closeConnection(con, stmt);

        }
    }

    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public static ArrayList<Cliente> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Cliente> clientes = new ArrayList<>();
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM clientes");

            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE CLIENTE.
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("cliente_cod"));
                c.setNome(rs.getString("cliente_nome"));
                c.setData_nascimento(rs.getDate("dt_nasc"));
                c.setEmail(rs.getString("email"));
                clientes.add(c);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar !");
        } finally {
            closeConnection(con, stmt, rs);
        }
        return clientes;
    }



    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Cliente c) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE clientes SET cliente_nome=?,dt_nasc=?,email=? WHERE cliente_cod = ?");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            stmt.setString(3, c.getEmail());
            stmt.setInt(4, c.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com Sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar e/ou privilégios insuficientes!");
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Cliente c) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM clientes WHERE cliente_cod = ?");
            stmt.setInt(1, c.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Apagado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar e/ou privilégios insuficientes!");
            JOptionPane.showMessageDialog(null, "OBS:: Impossível apagar cliente que esteja vinculado a um histórico de vendas.");
        } finally {
            closeConnection(con, stmt);
        }
    }

}
