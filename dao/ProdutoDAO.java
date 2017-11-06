package dao;

import bean.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sql.Sql;

public class ProdutoDAO extends Sql {

    

    public static void create(String username, String password, Produto p) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("INSERT INTO produtos VALUES (?,NULL,?,?,?,?,?,?,?,?,?)");
            stmt.setBytes(1, p.getProduto_foto());
            stmt.setString(2, p.getProduto_nome());
            stmt.setInt(3, p.getFk_fornecedor_cod());
            stmt.setFloat(4, p.getPreco_uni_compra());
            stmt.setFloat(5, p.getPreco_uni_venda());
            stmt.setString(6, p.getCategoria());
            stmt.setString(7, p.getDescricao_produto());
            stmt.setString(8, p.getUnidade_medida_peso());
            stmt.setFloat(9, p.getPeso_produto());

            stmt.setInt(10, p.getFk_estoque_cod());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Salvo com Sucesso !");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            closeConnection(con, stmt);
        }
    }

    public static ArrayList<Produto> read(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM produtos");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setProduto_foto(rs.getBytes("produto_foto"));
                p.setProduto_cod(rs.getInt("produto_cod"));
                p.setProduto_nome(rs.getString("produto_nome"));
                p.setFk_fornecedor_cod(rs.getInt("fk_cod_fornecedor"));
                p.setPreco_uni_compra(rs.getFloat("preco_uni_compra"));
                p.setPreco_uni_venda(rs.getFloat("preco_uni_venda"));
                p.setCategoria(rs.getString("categoria"));
                p.setDescricao_produto(rs.getString("descricao_produto"));
                p.setUnidade_medida_peso(rs.getString("unidade_medida_peso"));
                p.setPeso_produto(rs.getFloat("peso_produto"));
                p.setFk_estoque_cod(rs.getInt("fk_estoque_cod"));
                produtos.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar produtos !");
        } finally {
            closeConnection(con, stmt, rs);
        }

        return produtos;
    }

    public static void update(String username, String password, Produto p) {
    }

    public static void delete(String username, String password, Produto p) {
        PreparedStatement stmt = null;
        Connection con = null;

        try {
            con = getConnection(username,password);
            stmt = con.prepareStatement("DELETE FROM produtos WHERE produto_cod=?");
            stmt.setInt(1,p.getProduto_cod());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt);
        }
    }
}
