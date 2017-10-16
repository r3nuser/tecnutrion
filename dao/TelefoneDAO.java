package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;
import bean.Cliente;
import bean.Telefone;

public class TelefoneDAO{


	public static void create(String username,String password,Cliente c, Telefone t){
		PreparedStatement stmt= null;
		Connection con = null;

		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("insert into telefone values ((select cliente_cod from clientes where cliente_nome=? and dt_nasc=?),?,?,?)");
            stmt.setString(1, c.getNome());
            stmt.setDate(2, c.getData_nascimento());
            stmt.setString(3, t.getDdd());
            stmt.setString(4, t.getAntesh());
            stmt.setString(5, t.getDepoish());
            stmt.executeUpdate();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e);
		}finally{
			closeConnection(con,stmt);
		}
	}

	public static ArrayList<Telefone> read(String username, String password){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		ArrayList<Telefone> telefones = new ArrayList<>();
		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("SELECT * FROM telefone");
			rs = stmt.executeQuery();
			while(rs.next()){
				Telefone t = new Telefone();
				t.setInt(rs.getInt("fk_cliente_cod"))
				t.setDdd(rs.getString("ddd"));
				t.setAntesh(rs.getString("antesh"));
				t.setDepoish(rs.getString("depoish"));
				telefones.add(t);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Erro ao consultar !");
		}finally{
			closeConnection(con,stmt,rs);
		}
		return telefones;
	}
	
	public static void update(String username, String password, Telefone t){
		Connection con = null;
		PreparedStatement stmt = null;

		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("UPDATE telefone SET ddd=?,antesh=?,depoish=? where fk_cliente_cod=?");
			stmt.setString(1, t.getDdd());
			stmt.setString(2, t.getAntesh());
			stmt.setString(3, t.getDepoish());
			stmt.getString(4, t.getId());
			JOptionPane.showMessageDialog(null,"Atualizado com sucesso !");			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Erro ao atualizar !");
		}finally{
			closeConnection(con,stmt);
		}
	}

	public static void delete(String username,String password, Telefone t){
		Connection con = null;
		PreparedStatement stmt = null;
		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("DELETE FROM telefone WHERE fk_cliente_cod=?");
			stmt.setInt(1,t.getId());
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null,"Apagado com sucesso!");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Erro ao Apagar !");
		}finally{
			closeConnection(con,stmt);
		}
	}

}

