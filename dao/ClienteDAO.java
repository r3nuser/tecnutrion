package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import bean.Cliente;

public class ClienteDAO extends Sql {

    public static void create(String username, String password, Cliente c) {
        PreparedStatement stmt = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO clientes VALUES (NULL,?,?,?);");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            stmt.setString(3, c.getEmail());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection(con, stmt);

        }
    }

    public static ArrayList<Cliente> read(String username, String password) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM clientes");

            rs = stmt.executeQuery();

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

    public static void update(String username, String password, Cliente c) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE clientes SET cliente_nome=?,dt_nasc=?,email=? WHERE cliente_cod = ?");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            stmt.setString(3, c.getEmail());
            stmt.setInt(4, c.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar :(");
        } finally {
            closeConnection(con, stmt);
        }
    }

    public static void delete(String username, String password, Cliente c) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM clientes WHERE cliente_cod = ?");
            stmt.setInt(1, c.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Apagado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar!");
        } finally {
            closeConnection(con, stmt);
        }
    }

}
