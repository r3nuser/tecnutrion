/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO PEDIDO ITEM NO BANCO DE DADOS
* AUTOR @RENAN
*/

package dao;
//IMPORTS DE TODOS OS OBJETOS QUE UTILIZEI NA CLASSE
import bean.Pedido_item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sql.Sql;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class Pedido_itemDAO extends Sql {
    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Pedido_item pi) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO pedido_item VALUES (?,?,?,?,?,?);");
            stmt.setInt(1, pi.getFk_cod_cliente());
            stmt.setInt(2, pi.getFk_cod_pedido());
            stmt.setInt(3, pi.getFk_cod_produto());
            stmt.setInt(4, pi.getQuantidade());
            stmt.setFloat(5, pi.getPedido_item_vl_tot());
            stmt.setFloat(6, pi.getPedido_item_vl_liq());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt);
        }
    }
    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public static ArrayList<Pedido_item> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido_item> pedidos = new ArrayList<>();
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido_item");
            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE PEDIDO ITEM.
            while (rs.next()) {
                Pedido_item pi = new Pedido_item();
                pi.setFk_cod_cliente(rs.getInt("fk_cod_cliente"));
                pi.setFk_cod_pedido(rs.getInt("fk_cod_pedido"));
                pi.setFk_cod_produto(rs.getInt("fk_cod_produto"));
                pi.setQuantidade(rs.getInt("quantidade"));
                pi.setPedido_item_vl_tot(rs.getFloat("pedido_item_vl_tot"));
                pi.setPedido_item_vl_liq(rs.getFloat("pedido_item_vl_liq"));
                pedidos.add(pi);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return pedidos;
    }
    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Pedido_item pi) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE pedido_item SET fk_cod_cliente=?,fk_cod_pedido=?,fk_cod_produto=?,quantidade=?,pedido_item_vl_tot=?,pedido_item_vl_liq=? WHERE fk_cod_cliente=?,fk_cod_pedido=?,fk_cod_produto=?");
            stmt.setInt(1, pi.getFk_cod_cliente());
            stmt.setInt(2, pi.getFk_cod_pedido());
            stmt.setInt(3, pi.getFk_cod_produto());
            stmt.setInt(4, pi.getQuantidade());
            stmt.setFloat(5, pi.getPedido_item_vl_tot());
            stmt.setFloat(6, pi.getPedido_item_vl_liq());
            stmt.setInt(7, pi.getFk_cod_cliente());
            stmt.setInt(8, pi.getFk_cod_pedido());
            stmt.setInt(9, pi.getFk_cod_produto());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt);
        }
    }
    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Pedido_item pi, byte mode) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        if (mode == 1) {
            try {
                con = getConnection(username, password);
                stmt = con.prepareStatement("DELETE FROM pedido_item WHERE fk_cod_pedido=?");
                stmt.setInt(1, pi.getFk_cod_pedido());
                stmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                closeConnection(con, stmt);
            }
        } else {
            try {
                con = getConnection(username, password);
                stmt = con.prepareStatement("DELETE FROM pedido_item WHERE fk_cod_cliente=?,fk_cod_pedido=?,fk_cod_produto=?");
                stmt.setInt(1, pi.getFk_cod_cliente());
                stmt.setInt(2, pi.getFk_cod_pedido());
                stmt.setInt(3, pi.getFk_cod_produto());
                stmt.executeUpdate();
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                closeConnection(con, stmt);
            }
        }
    }

}
