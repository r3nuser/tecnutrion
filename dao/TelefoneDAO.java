/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO TELEFONE NO BANCO DE DADOS
* AUTOR @RENAN
*/

package dao;
//IMPORTS DE TODOS OS OBJETOS QUE UTILIZEI NA CLASSE
import bean.Telefone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import static sql.Sql.closeConnection;
import static sql.Sql.getConnection;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class TelefoneDAO extends Sql {
    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Telefone t) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("insert into telefone values (?,?,?,?)");
            stmt.setInt(1, t.getFk_cliente_cod());
            stmt.setString(2, t.getDdd());
            stmt.setString(3, t.getAntesh());
            stmt.setString(4, t.getDepoish());
            stmt.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection(con, stmt);
        }
    }
    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public static ArrayList<Telefone> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Telefone> telefones = new ArrayList<>();
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM telefone");
            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE TELEFONE.
            while (rs.next()) {
                Telefone t = new Telefone();
                t.setFk_cliente_cod(rs.getInt("fk_cliente_cod"));
                t.setDdd(rs.getString("ddd"));
                t.setAntesh(rs.getString("antesh"));
                t.setDepoish(rs.getString("depoish"));
                telefones.add(t);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return telefones;
    }
    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Telefone t) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE telefone SET ddd=?,antesh=?,depoish=? WHERE fk_cliente_cod=?");
            stmt.setString(1, t.getDdd());
            stmt.setString(2, t.getAntesh());
            stmt.setString(3, t.getDepoish());
            stmt.setInt(4, t.getFk_cliente_cod());
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection(con, stmt);
        }
    }
    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Telefone t) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM telefone WHERE fk_cliente_cod=?");
            stmt.setInt(1, t.getFk_cliente_cod());
            stmt.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar!");
        } finally {
            closeConnection(con, stmt);
        }
    }

}
