/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO ENDERECO NO BANCO DE DADOS
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
import bean.Endereco;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class EnderecoDAO extends Sql {

    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Endereco e) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO endereco VALUES(?,?,?,?,?,?,?,?)");
            stmt.setInt(1, e.getClientecod());
            stmt.setString(2, e.getTipolog());
            stmt.setString(3, e.getLog());
            stmt.setString(4, e.getBairro());
            stmt.setString(5, e.getComplemento());
            stmt.setString(6, e.getMunicipio());
            stmt.setString(7, e.getEstado());
            stmt.setString(8, e.getCep());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Endereço salvo com sucesso !");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public ArrayList<Endereco> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Endereco> enderecos = new ArrayList<>();
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM endereco");
            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE ENDEREÇO.
            while (rs.next()) {
                Endereco e = new Endereco();
                e.setClientecod(rs.getInt("fk_cliente_cod"));
                e.setTipolog(rs.getString("tipolog"));
                e.setLog(rs.getString("logradouro"));
                e.setBairro(rs.getString("bairro"));
                e.setMunicipio(rs.getString("cidade"));
                e.setCep(rs.getString("cep"));
                e.setComplemento(rs.getString("complemento"));
                e.setEstado(rs.getString("estado"));
                enderecos.add(e);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar !" + ex);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return enderecos;
    }

    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Endereco e) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE endereco SET tipolog=?,logradouro=?,bairro=?,complemento=?,cidade=?,estado=?,cep=? WHERE fk_cliente_cod=?");
            stmt.setString(1, e.getTipolog());
            stmt.setString(2, e.getLog());
            stmt.setString(3, e.getBairro());
            stmt.setString(4, e.getComplemento());
            stmt.setString(5, e.getMunicipio());
            stmt.setString(6, e.getEstado());
            stmt.setString(7, e.getCep());
            stmt.setInt(8, e.getClientecod());
            stmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Endereco e) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM endereco WHERE fk_cliente_cod=?");
            stmt.setInt(1, e.getClientecod());
            stmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt);
        }
    }
}
