package dao;

import bean.Estoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;

public class EstoqueDAO extends Sql {

    

    public static void create(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;

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

    public static ArrayList<Estoque> read(String username, String password) {
        ArrayList<Estoque> dados_estoque = new ArrayList<>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM estoque");
            rs = stmt.executeQuery();
            
            while(rs.next()){
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

    public static void update(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;
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

    public static void delete(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;
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
