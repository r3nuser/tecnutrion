package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import bean.Cliente;
import bean.Telefone;
import gui.abas.Painel_cliente;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ClienteDAO extends Sql {

    public static Cliente search_cliente_por_id(String username, String password, int id){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Cliente c = new Cliente();
        try{
            con = getConnection(username,password);
            stmt = con.prepareStatement("SELECT * from clientes where cliente_cod=?");
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while(rs.next()){
                c.setId(rs.getInt("cliente_cod"));
                c.setNome(rs.getString("cliente_nome"));
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                String data = fmt.format(rs.getDate("dt_nasc"));
                java.sql.Date dt = new java.sql.Date(fmt.parse(data).getTime());
                c.setData_nascimento(dt);
            }
        }catch(Exception e){
            System.out.println(e);
        }finally{
            closeConnection(con,stmt,rs);
        }
        return c;
    }
    
    public static int get_cliente_id(String username, String password, Cliente c) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT cliente_cod from clientes where cliente_nome=? and dt_nasc=?");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            rs = stmt.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt("cliente_cod"));
            }

            return c.getId();
        } catch (Exception ex) {

        } finally {
            closeConnection(con, stmt, rs);
        }
        return 0;
    }

    public static void create(String username, String password, Cliente c, Telefone t) {
        PreparedStatement stmt = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO clientes VALUES (NULL,?,?);");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());

            stmt.executeUpdate();

            stmt = con.prepareStatement("insert into telefone values ((select cliente_cod from clientes where cliente_nome=? and dt_nasc=?),?,?,?)");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            stmt.setString(3, t.getDdd());
            stmt.setString(4, t.getAntesh());
            stmt.setString(5, t.getDepoish());
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
                System.out.println(c.getNome() + " " + c.getId() + " " + c.getData_nascimento());
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
            stmt = con.prepareStatement("UPDATE clientes SET cliente_nome=? WHERE cliente_cod = ?");
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getId());
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
