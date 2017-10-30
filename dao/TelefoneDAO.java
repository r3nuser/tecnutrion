package dao;

import bean.Telefone;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;

public class TelefoneDAO extends Sql {

    public static void create(String username, String password, Telefone t) {
        PreparedStatement stmt = null;
        Connection con = null;
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

    public static ArrayList<Telefone> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Telefone> telefones = new ArrayList<>();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM telefone");
            rs = stmt.executeQuery();

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

    public static void update(String username, String password, Telefone t) {
        Connection con = null;
        PreparedStatement stmt = null;
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

    public static void delete(String username, String password, Telefone t) {
        Connection con = null;
        PreparedStatement stmt = null;
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
