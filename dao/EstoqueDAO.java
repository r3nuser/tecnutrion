package dao;

import bean.Estoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;

public class EstoqueDAO extends Sql {

    public static int pega_ultimo_id(String username, String password){
        PreparedStatement stmt = null;
        Connection con = null;
        ResultSet rs = null;
        int id = 0;
        try{
            con = getConnection(username,password);
            stmt = con.prepareStatement("SELECT max(estoque_cod) FROM estoque");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("max(estoque_cod)");
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        return id;
    }
    
    public static void create(String username, String password, Estoque e) {
        PreparedStatement stmt = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO estoque VALUES (NULL,?,?);");
            stmt.setInt(1,e.getQnt_estoque());
            stmt.setDate(2, e.getValidade());
            stmt.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            closeConnection(con, stmt);
        }
    }

    public static ArrayList<Estoque> read(String username, String password) {
        return null;
    }

    public static void update(String username, String password, Estoque e) {

    }

    public static void delete(String username, String password, Estoque f) {

    }
}
