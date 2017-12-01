/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO ESTOQUE NO BANCO DE DADOS
* AUTOR @RENAN
 */
package dao;
//IMPORTS DE TODOS OS OBJETOS QUE UTILIZEI NA CLASSE

import bean.Estoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import sql.Sql;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class EstoqueDAO extends Sql {

    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO estoque VALUES (NULL,?,?);");
            stmt.setInt(1, e.getQnt_estoque());
            stmt.setDate(2, e.getValidade());
            stmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public static ArrayList<Estoque> read(String username, String password) {
        ArrayList<Estoque> dados_estoque = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM estoque");
            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE ESTOQUE.
            while (rs.next()) {
                Estoque e = new Estoque();
                e.setEstoque_cod(rs.getInt("estoque_cod"));
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                String data = fmt.format(rs.getDate("validade"));
                java.sql.Date dt = new java.sql.Date(fmt.parse(data).getTime());
                e.setValidade(dt);
                e.setQnt_estoque(rs.getInt("qnt_estoque"));
                dados_estoque.add(e);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return dados_estoque;
    }

    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE estoque SET qnt_estoque=?,validade=? WHERE estoque_cod=?");
            stmt.setInt(1, e.getQnt_estoque());
            stmt.setDate(2, e.getValidade());
            stmt.setInt(3, e.getEstoque_cod());
            stmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt);
        }
    }

    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM estoque WHERE estoque_cod=?");
            stmt.setInt(1, e.getEstoque_cod());
            stmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt);
        }
    }
}
