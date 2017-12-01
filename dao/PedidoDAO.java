/*
*CLASSE RESPONSÁVEL PELA LEITURA,
*REMOÇÃO, ATUALIZAÇÃO E CRIAÇÃO DE
*DADOS DO PEDIDO NO BANCO DE DADOS
* AUTOR @RENAN
*/
package dao;
//IMPORTS DE TODOS OS OBJETOS QUE UTILIZEI NA CLASSE
import bean.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class PedidoDAO extends Sql {
    //METODO RESPONSÁVEL PELA INSERÇÃO DOS DADOS NOS BANCOS
    public static void create(String username, String password, Pedido p) {
        PreparedStatement stmt = null;
        Connection con = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE INSERÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO pedido VALUES (NULL,?,?,?,?);");
            stmt.setDate(1, p.getDt_pedido());
            stmt.setFloat(2, p.getPedido_vl_tot());
            stmt.setString(3, p.getPagamento());
            stmt.setInt(4,p.getDesconto());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt);
        }
    }
    //METODO RESPONSÁVEL PELA LEITURA DOS DADOS NOS BANCOS
    public static ArrayList<Pedido> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE LEITURA A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido");
            rs = stmt.executeQuery();
            //CLAUSULA WHILE QUE PASSA POR TODOS DADOS RESULTANTES DO SELECT
            //E APÓS A LEITURA, ADICIONA EM UM ARRAY DE PEDIDO.
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }
    //METODO RESPONSÁVEL PELA ATUALIZAÇÃO DOS DADOS NOS BANCOS
    public static void update(String username, String password, Pedido p) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE ATUALIZAÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE pedido SET dt_pedido=?,pedido_vl_tot=?,pagamento=?,desconto=? WHERE cod_pedido=?");
            stmt.setDate(1,p.getDt_pedido());
            stmt.setFloat(2,p.getPedido_vl_tot());
            stmt.setString(3,p.getPagamento());
            stmt.setInt(4,p.getDesconto());
            stmt.setInt(5,p.getCod_pedido());
            
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt);
        }
    }
    //METODO RESPONSÁVEL PELA REMOÇÃO DOS DADOS NOS BANCOS 
    public static void delete(String username, String password, Pedido p) {
        Connection con = null;
        PreparedStatement stmt = null;
        //ESTA CLÁUSULA É RESPONSÁVEL POR:
        // * PEGAR A CONEXÃO COM BANCO DE DADOS
        // * PREPARAR O COMANDO DE REMOÇÃO A SER EXECUTADO NO STATEMENT
        // * EXECUTAR O COMANDO.
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM pedido WHERE cod_pedido=?");
            stmt.setInt(1, p.getCod_pedido());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Apagado com Sucesso !!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar e/ou privilégios insuficientes!");
        } finally {
            closeConnection(con, stmt);
        }
    }
}
