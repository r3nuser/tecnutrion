package dao;

import bean.Cliente;
import bean.Endereco;
import bean.Estoque;
import bean.Fornecedor;
import bean.Pedido;
import bean.Produto;
import bean.Telefone;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import sql.Sql;

public class MiscDAO extends Sql {

    public static ArrayList<Pedido> relatorio_por_data(String username, String password, Date inicio, Date fim) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido WHERE dt_pedido BETWEEN ? and ?");
            stmt.setDate(1, inicio);
            stmt.setDate(2, fim);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;

    }

    public static Produto get_produto_por_fk_cod_estoque(String username, String password, int id) {
        Produto p = new Produto();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT produto_cod,produto_nome,produto_foto FROM produtos WHERE fk_estoque_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                p.setProduto_cod(rs.getInt("produto_cod"));
                p.setProduto_foto(rs.getBytes("produto_foto"));
                p.setProduto_nome(rs.getString("produto_nome"));
                p.setFk_estoque_cod(id);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return p;
    }

    public static ArrayList<Produto> get_produtos_contidos_pedido(String username, String password, int id) {
        ArrayList<Produto> dados_produtos = new ArrayList<>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rsPedido = null;

        int id_produto = 0;
        int quantidade = 0;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT fk_cod_produto,quantidade FROM pedido_item WHERE "
                    + "fk_cod_pedido = ?");
            stmt.setInt(1, id);
            rsPedido = stmt.executeQuery();

            while (rsPedido.next()) {
                id_produto = rsPedido.getInt("fk_cod_produto");
                quantidade = rsPedido.getInt("quantidade");
                try {
                    con = getConnection(username, password);
                    stmt = con.prepareStatement("SELECT produto_cod,produto_nome,preco_uni_venda FROM"
                            + " produtos WHERE produto_cod=?");
                    stmt.setInt(1, id_produto);
                    rs = stmt.executeQuery();
                    while (rs.next()) {
                        Produto p = new Produto();
                        p.setProduto_cod(rs.getInt("produto_cod"));
                        p.setProduto_nome(rs.getString("produto_nome"));
                        p.setPreco_uni_venda(rs.getFloat("preco_uni_venda"));
                        p.setFk_fornecedor_cod(quantidade);
                        dados_produtos.add(p);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    closeConnection(con, stmt, rs);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rsPedido);
        }

        return dados_produtos;
    }

    public static int get_quantidade_de_itens_pedido(String username, String password, int id) {
        int quantidade = 0;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT sum(quantidade) FROM pedido_item "
                    + "WHERE fk_cod_pedido=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                quantidade = rs.getInt("sum(quantidade)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return quantidade;
    }

    public static float get_lucro_liquido_pedido(String username, String password, int id) {

        float lucro = 0;

        int cod_produto;
        int quantidade = 0;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        ResultSet rsLucro = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT fk_cod_produto,quantidade FROM pedido_item WHERE"
                    + " fk_cod_pedido=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cod_produto = rs.getInt("fk_cod_produto");
                quantidade = rs.getInt("quantidade");
                stmt = con.prepareStatement("SELECT preco_uni_compra FROM produtos WHERE"
                        + " produto_cod=?");
                stmt.setInt(1, cod_produto);
                rsLucro = stmt.executeQuery();
                if (rsLucro.next()) {
                    lucro += quantidade * rsLucro.getFloat("preco_uni_compra");
                }
            }

            return lucro;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
            try {
                rsLucro.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return 0f;
    }

    public static Pedido search_pedido_por_id(String username, String password, int id) {
        Pedido p = new Pedido();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido WHERE cod_pedido = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                p.setCod_pedido(id);
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPagamento(rs.getString("pagamento"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return p;
    }

    public static int get_id_cliente_pedido_item_por_fk(String username, String password, int fk) {
        int pi = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT fk_cod_cliente FROM pedido_item WHERE fk_cod_pedido=?");
            stmt.setInt(1, fk);
            rs = stmt.executeQuery();
            while (rs.next()) {
                pi = rs.getInt("fk_cod_cliente");

                break;

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pi;
    }

    public static ArrayList<Fornecedor> search_fornecedor_por_nome(String username, String password, String nome) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM fornecedor WHERE fornecedor_nome LIKE ?");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("fornecedor_cod"));
                f.setNome(rs.getString("fornecedor_nome"));
                fornecedores.add(f);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return fornecedores;
    }

    public static ArrayList<Cliente> search_cliente_por_nome(String username, String password, String nome) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM clientes WHERE cliente_nome LIKE ?");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("cliente_cod"));
                c.setNome(rs.getString("cliente_nome"));
                c.setData_nascimento(rs.getDate("dt_nasc"));
                clientes.add(c);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return clientes;
    }

    public static ArrayList<Produto> search_produto_por_nome(String username, String password, String nome) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM produtos where produto_nome LIKE ?");
            stmt.setString(1, "%" + nome + "%");
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
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return produtos;
    }

    public static Cliente search_cliente_por_id(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Cliente c = new Cliente();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * from clientes where cliente_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt("cliente_cod"));
                c.setNome(rs.getString("cliente_nome"));
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                String data = fmt.format(rs.getDate("dt_nasc"));
                java.sql.Date dt = new java.sql.Date(fmt.parse(data).getTime());
                c.setData_nascimento(dt);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return c;
    }

    public static Telefone search_telefone_por_id(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Telefone t = new Telefone();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * from telefone where fk_cliente_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                t.setDdd(rs.getString("ddd"));
                t.setAntesh(rs.getString("antesh"));
                t.setDepoish(rs.getString("depoish"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return t;
    }

    public static Produto search_produto_por_id(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Produto p = new Produto();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM produtos WHERE produto_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
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
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return p;
    }

    public static Endereco search_endereco_por_id(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Endereco e = new Endereco();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * from endereco where fk_cliente_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                e.setTipolog(rs.getString("tipolog"));
                e.setLog(rs.getString("logradouro"));
                e.setBairro(rs.getString("bairro"));
                e.setComplemento(rs.getString("complemento"));
                e.setEstado(rs.getString("estado"));
                e.setMunicipio(rs.getString("cidade"));
                e.setCep(rs.getString("cep"));

            }
        } catch (Exception en) {
            System.out.println(en);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return e;
    }

    public static int get_ultimo_pedido_id(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        int p = 0;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT max(cod_pedido) FROM pedido");
            rs = stmt.executeQuery();

            while (rs.next()) {
                p = (rs.getInt("max(cod_pedido)"));
            }
            return p;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return 0;
    }

    public static int get_ultimo_cliente_id(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        int c = 0;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT max(cliente_cod) from clientes");

            rs = stmt.executeQuery();
            while (rs.next()) {
                c = (rs.getInt("max(cliente_cod)"));
            }

            return c;
        } catch (Exception ex) {

        } finally {
            closeConnection(con, stmt, rs);
        }
        return 0;
    }

    public static Estoque search_estoque_por_id(String username, String password, int id) {
        Estoque e = new Estoque();
        PreparedStatement stmt = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM estoque where estoque_cod= ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                e.setEstoque_cod(rs.getInt("estoque_cod"));
                e.setQnt_estoque(rs.getInt("qnt_estoque"));
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                String data = fmt.format(rs.getDate("validade"));
                java.sql.Date dt = new java.sql.Date(fmt.parse(data).getTime());
                e.setValidade(dt);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return e;
    }

    public static int get_ultimo_estoque_id(String username, String password) {
        PreparedStatement stmt = null;
        Connection con = null;
        ResultSet rs = null;
        int id = 0;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT max(estoque_cod) FROM estoque");
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("max(estoque_cod)");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    public static Fornecedor search_fornecedor_por_id(String username, String password, int id) {
        Fornecedor f = new Fornecedor();
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * from fornecedor where fornecedor_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                f.setId(rs.getInt("fornecedor_cod"));
                f.setNome(rs.getString("fornecedor_nome"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return f;
    }
}
