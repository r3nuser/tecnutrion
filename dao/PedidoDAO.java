package dao;

//cod_pedido int not null,
//dt_pedido date not null,
//pedido_vl_tot decimal(8,2) not null

public class PedidoDAO extends Sql {

	public static void create(String username, String password, Pedido p){
		PreparedStatement stmt = null;
		Connection con = null;

		try{
			con = getConnection(username, password);
			stmt = con.prepareStatement("INSERT INTO pedido VALUES (NULL,?,?);");
			stmt.setInt(1,p.getDt_pedido());
			stmt.setFloat(2,p.getPedido_vl_tot());
			stmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			closeConnection(con,stmt);
		}
	}

	public static ArrayList<Pedido> read(String username, String password){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		ArrayList<Pedido> pedidos = new ArrayList<>();

		try{
			con = getConnection(username,password);
			stmt = con.PrepareStatement("SELECT * FROM pedido");
			rs = stmt.executeQuery();
			while(rs.next()){
				Pedido p = new Pedido();
				p.setCod_pedido(rs.getInt("cod_pedido"));
				p.setDt_pedido(rs.getDate("dt_pedido"));
				p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
				pedidos.add(p);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		finally{
			closeConnection(con,stmt,rs);
		}
		return pedidos;
	}
	public static void update(String username, String password, Pedido p){
		Connection con = null;
		PreparedStatement stmt = null;
		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("UPDATE pedido SET dt_pedido=?,pedido_vl_tot=? WHERE cod_pedido=?");
			stmt.setDate(p.getDt_pedido());
			stmt.setFloat(p.getPedido_vl_tot());
			stmt.setInt(p.getCod_pedido());
			stmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			closeConnection(con,stmt);
		}
	}
	public static void delete(String username, String password, Pedido p){
		Connection con = null;
		PreparedStatement stmt = null;

		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("DELETE FROM pedido WHERE cod_pedido=?");
			stmt.setInt(1,p.getCod_pedido());
			stmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			closeConnection(con,stmt);
		}
	}
}