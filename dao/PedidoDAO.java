package dao;

import bean.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sql.Sql;

public class PedidoDAO extends Sql {

    public static void create(String username, String password, Pedido p) {
        PreparedStatement stmt = null;
        Connection con = null;

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

    public static ArrayList<Pedido> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido");
            rs = stmt.executeQuery();
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

    public static void update(String username, String password, Pedido p) {
        Connection con = null;
        PreparedStatement stmt = null;
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

    public static void delete(String username, String password, Pedido p) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM pedido WHERE cod_pedido=?");
            stmt.setInt(1, p.getCod_pedido());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt);
        }
    }
}
