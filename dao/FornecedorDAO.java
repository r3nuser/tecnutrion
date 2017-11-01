package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import bean.Fornecedor;


public class FornecedorDAO extends Sql{
    public static void create(String username, String password, Fornecedor f){
        PreparedStatement stmt = null;
                Connection con = null;
        try{
            con = getConnection(username,password);
            stmt = con.prepareStatement("INSERT INTO fornecedor(fornecedor_cod,fornecedor_nome) VALUES (NULL,?)");
            stmt.setString(1,f.getNome());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Salvo com sucesso!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao salvar :(");
        }finally{
            closeConnection(con,stmt);
        }
    }

    public static ArrayList<Fornecedor> read(String username,String password){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
        Connection con = null;
        try{
            con = getConnection(username,password);
            stmt = con.prepareStatement("SELECT * FROM fornecedor");
            rs = stmt.executeQuery();

            while(rs.next()){
                Fornecedor f= new Fornecedor();
                f.setId(rs.getInt("fornecedor_cod"));
                f.setNome(rs.getString("fornecedor_nome"));
                fornecedores.add(f);
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            closeConnection(con,stmt,rs);
        }
        return fornecedores;
    }

    public static void update(String username,String password, Fornecedor f){
        PreparedStatement stmt = null;
                Connection con = null;
        try{
            con = getConnection(username,password);
            stmt = con.prepareStatement("UPDATE fornecedor SET fornecedor_nome=? WHERE fornecedor_id=?");
            stmt.setString(1,f.getNome());
            stmt.setInt(2,f.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Atualizado com sucesso!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao atualizar :(");
        }finally{
            closeConnection(con,stmt);
        }
    }
    public static void delete(String username,String password, Fornecedor f){
        PreparedStatement stmt = null;
                Connection con = null;
        try{
            con = getConnection(username,password);
            stmt = con.prepareStatement("DELETE FROM fornecedor WHERE fornecedor_cod=?");
            stmt.setInt(1,f.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Apagado com sucesso!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao Apagar :(");
        }finally{
            closeConnection(con,stmt);
        }
    }
    
}
