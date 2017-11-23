package dao;

import bean.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import bean.Endereco;
import java.awt.List;

public class EnderecoDAO extends Sql {

    public static void create(String username, String password, Endereco e) {
        PreparedStatement stmt = null;
        Connection con = null;
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

    public ArrayList<Endereco> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Endereco> enderecos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM endereco");
            rs = stmt.executeQuery();

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar !");
        } finally {
            closeConnection(con, stmt, rs);
        }
        return enderecos;
    }

    public static void update(String username, String password, Endereco e) {
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("UPDATE FROM endereco SET tipolog=?,logradouro=?,bairro=?,cidade=?,cidade=?,complemento=?,estado=? WHERE fk_cliente_cod=?");
            stmt.setString(1, e.getTipolog());
            stmt.setString(2, e.getLog());
            stmt.setString(3, e.getBairro());
            stmt.setString(4, e.getComplemento());
            stmt.setString(5, e.getMunicipio());
            stmt.setString(6, e.getEstado());
            stmt.setString(7, e.getCep());
            stmt.setInt(8, e.getClientecod());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Endereço salvo com sucesso !");
        }catch(Exception e){
            System.out.println(e);
        }finally{
            closeConnection(con,stmt);
        }
    }

    public static void delete(String username, String password, Endereco e) {
        PreparedStatement stmt = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("DELETE FROM endereco WHERE fk_cliente_cod=?");
            stmt.setInt(1, e.getClientecod());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Endereço salvo com sucesso !");
        }catch(Exception e){
            System.out.println(e);
        }finally{
            closeConnection(con,stmt);
        }
    }
}
