/*
* CLASSE RESPONSÁVEL POR 
* COMANDOS ESPECIFICOS
* NO BANCO DE DADOS
* AUTOR @RENAN
 */
package dao;
//IMPORTS DE TODOS OS OBJETOS QUE UTILIZEI NA CLASSE

import bean.Cliente;
import bean.Endereco;
import bean.Estoque;
import bean.Fornecedor;
import bean.Pedido;
import bean.Pedido_item;
import bean.Produto;
import bean.Telefone;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import sql.Sql;

//NOME DA CLASSE + HERANÇA DE TODOS OS METODOS DA CLASSE Sql
public class MiscDAO extends Sql {

    //MÉTODO RESPONSÁVEL POR RETORNAR O NUMERO DE COMPRAS ACIMA DE 150 REAIS DO CLIENTE
    public static int n_compras_acima_cap(String username, String password, int id) {
        int ndc = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection(username,password);
            stmt = con.prepareStatement("select count(distinct fk_cod_pedido) "
                    + "from pedido,clientes,pedido_item where fk_cod_cliente=? and fk_cod_cliente=cliente_cod "
                    + "and fk_cod_pedido = cod_pedido and pedido_vl_tot >= 150;");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                ndc = rs.getInt("count(distinct fk_cod_pedido)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return ndc;
    }

    //MÉTODO RESPONSÁVEL POR PROCURAR O PRODUTO PELO FORNECEDOR
    public static ArrayList<Produto> search_produto_pelo_fornecedor(String username, String password, int id) {
        ArrayList<Produto> dados_produto = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT produto_foto,produto_nome,produto_cod "
                    + "FROM produtos WHERE fk_cod_fornecedor=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setProduto_foto(rs.getBytes("produto_foto"));
                p.setProduto_nome(rs.getString("produto_nome"));
                p.setProduto_cod(rs.getInt("produto_cod"));
                dados_produto.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return dados_produto;
    }

    //METODO RESPONSÁVEL POR PROCURAR O PEDIDO PELO ID DO CLIENTE
    public static ArrayList<Pedido> search_pedido_pelo_cliente(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<Pedido_item> ids = new ArrayList<>();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT distinct fk_cod_pedido FROM pedido_item WHERE fk_cod_cliente=?");
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido_item pi = new Pedido_item();
                pi.setFk_cod_pedido(rs.getInt("fk_cod_pedido"));
                ids.add(pi);
            }

            for (Pedido_item id1 : ids) {
                stmt = con.prepareStatement("SELECT * FROM pedido WHERE cod_pedido=?");
                stmt.setInt(1, id1.getFk_cod_pedido());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    Pedido p = new Pedido();
                    p.setCod_pedido(rs.getInt("cod_pedido"));
                    p.setDt_pedido(rs.getDate("dt_pedido"));
                    p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                    p.setPagamento(rs.getString("pagamento"));
                    p.setDesconto(rs.getInt("desconto"));
                    pedidos.add(p);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }

    //METODO RESPONSÁVEL POR PROCURAR O PRODUTO MENOS VENDIDO
    public static Produto produtos_menos_vendidos(String username, String password) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto p = new Produto();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT produto_nome,categoria FROM produtos "
                    + "WHERE produto_cod=(SELECT fk_cod_produto from pedido_item "
                    + "group by fk_cod_produto order by sum(quantidade) asc LIMIT 1)");

            rs = stmt.executeQuery();
            while (rs.next()) {
                p.setProduto_nome(rs.getString("produto_nome"));
                p.setCategoria(rs.getString("categoria"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return p;
    }

    //METODO RESPONSÁVEL POR PROCURAR O PRODUTO MAIS VENDIDO
    public static Produto produtos_mais_vendidos(String username, String password) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Produto p = new Produto();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT produto_nome,categoria FROM produtos "
                    + "WHERE produto_cod=(SELECT fk_cod_produto from pedido_item "
                    + "group by fk_cod_produto order by sum(quantidade) desc LIMIT 1)");

            rs = stmt.executeQuery();
            while (rs.next()) {
                p.setProduto_nome(rs.getString("produto_nome"));
                System.out.println(p.getProduto_nome());
                p.setCategoria(rs.getString("categoria"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return p;
    }

    //METODO RESPONSÁVEL POR PROCURAR O NUMERO DE CLIENTES CADASTRADOS
    public static int clientes_cadastrados(String username, String password) {
        int total = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT count(cliente_cod) FROM clientes");

            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("count(cliente_cod)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return total;
    }

    //METODO RESPONSÁVEL POR BUSCAR O NUMERO DE PRODUTOS CADASTRADOS
    public static int produtos_cadastrados(String username, String password) {
        int total = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT count(produto_cod) FROM produtos");
            rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt("count(produto_cod)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return total;
    }

    //METODO RESPONSÁVEL POR BUSCAR O NUMERO DE FORNECEDORES CADASTRADOS
    public static int fornecedores_cadastrados(String username, String password) {
        int total = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT count(fornecedor_cod) FROM fornecedor");

            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("count(fornecedor_cod)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return total;
    }

    //METODO RESPONSÁVEL POR BUSCAR O NUMERO TOTAL DE ITENS NO ESTOQUE
    public static int total_item_estoque_cadastrados(String username, String password) {
        int total = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT sum(qnt_estoque) FROM estoque");

            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("sum(qnt_estoque)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return total;
    }

    //METODO QUE BUSCA O TOTAL DE VENDAS
    public static int total_de_vendas_cadastrados(String username, String password) {
        int total = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT count(cod_pedido) FROM pedido");

            rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt("count(cod_pedido)");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return total;
    }

    //METODO QUE BUSCA AS VENDAS DA SEMANA PASSADA
    public static ArrayList<Pedido> vendas_semana_passada(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("select * from pedido WHERE YEARWEEK(dt_pedido) = YEARWEEK(NOW())-1");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }

    //METODO QUE BUSCA A VENDAS NA SEMANA ATUAL
    public static ArrayList<Pedido> vendas_semana(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido WHERE week(dt_pedido)= week(now()) and year(data) = year(now())");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }

    //METODO QUE BUSCA VENDAS NO ANO
    public static ArrayList<Pedido> vendas_ano(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido WHERE year(dt_pedido)= year(now())");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }

    //METODO QUE BUSCA VENDAS NO MES
    public static ArrayList<Pedido> vendas_mes(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido WHERE month(dt_pedido)= month(now())");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }

    //METODO QUE BUSCA VENDAS NO DIA
    public static ArrayList<Pedido> vendas_dia(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM pedido WHERE day(dt_pedido)= day(now())");

            rs = stmt.executeQuery();
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setCod_pedido(rs.getInt("cod_pedido"));
                p.setDt_pedido(rs.getDate("dt_pedido"));
                p.setPedido_vl_tot(rs.getFloat("pedido_vl_tot"));
                p.setPagamento(rs.getString("pagamento"));
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;
    }

    //METODO QUE SELECIONA OS ANIVERSARIANTES DO MES
    public static ArrayList<Cliente> search_aniversariantes_do_mes(String username, String password) {
        ArrayList<Cliente> dados_cliente = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM clientes Where Month(dt_nasc) = Month(Now());");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setData_nascimento(rs.getDate("dt_nasc"));
                c.setId(rs.getInt("cliente_cod"));
                c.setNome(rs.getString("cliente_nome"));
                dados_cliente.add(c);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return dados_cliente;
    }

    //METODO QUE PROCURA PRODUTOS FORA DE ESTOQUE
    public static ArrayList<Estoque> search_produtos_fora_de_estoque(String username, String password) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<Estoque> dados_estoque = new ArrayList<>();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT qnt_estoque,estoque_cod FROM estoque WHERE qnt_estoque<5 ORDER BY qnt_estoque");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Estoque e = new Estoque();
                e.setQnt_estoque(rs.getInt("qnt_estoque"));
                e.setEstoque_cod(rs.getInt("estoque_cod"));
                dados_estoque.add(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return dados_estoque;
    }

    //METODO QUE PROCURA PRODUTOS PERTO DE VENCER
    public static ArrayList<Estoque> search_produtos_perto_de_vencer(String username, String password, Date dia) {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<Estoque> dados_estoque = new ArrayList<>();

        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT validade,estoque_cod from estoque where validade<=? ORDER BY validade");
            stmt.setDate(1, dia);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Estoque e = new Estoque();
                e.setValidade(rs.getDate("validade"));
                e.setEstoque_cod(rs.getInt("estoque_cod"));
                dados_estoque.add(e);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return dados_estoque;

    }

    //METODO QUE RETORNA PEDIDO POR DATAS
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
                p.setDesconto(rs.getInt("desconto"));
                pedidos.add(p);

            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return pedidos;

    }

    //METODO QUE PEGA O PRODUTO PELO COD DE ESTOQUE
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

    //METODO QUE BUSCA OS PRODUTOS CONTIDOS EM UM PEDIDO
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

    //METODO QUE BUSCA QUANTIDADE DE ITENS EM UM PEDIDO
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

    //METODO QUE BUSCA O LUCRO LIQUIDO DO PEDIDO
    public static float get_lucro_liquido_pedido(String username, String password, int id) {

        float lucro = 0;

        PreparedStatement stmt = null;
        ResultSet rs = null;

        Connection con = null;
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT fk_cod_produto,quantidade,pedido_item_vl_liq FROM pedido_item WHERE"
                    + " fk_cod_pedido=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                lucro += rs.getFloat("pedido_item_vl_liq");

            }

            return lucro;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return 0f;
    }

    //METODO QUE BUSCA O PEDIDO POR ID
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
                p.setDesconto(rs.getInt("desconto"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return p;
    }

    //METODO QUE BUSCA O ID DO CLIENTE PELO COD DO PEDIDO
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

    //METODO QUE BUSCA O FORNECEDOR POR NOME
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

    //METODO QUE BUSCA O CLIENTE PELO NOME
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
                c.setEmail(rs.getString("email"));
                clientes.add(c);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return clientes;
    }

    //METODO QUE BUSCA O PRODUTO PELO NOME
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
                p.setCod_barra(rs.getString("cod_barra"));
                produtos.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }

        return produtos;
    }

    //METODO QUE BUSCA O CLIENTE PELO ID
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
                c.setEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return c;
    }

    //METODO QUE BUSCA O TELEFONE PELO ID
    public static ArrayList<Telefone> search_telefone_por_id(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Telefone> telefones = new ArrayList<>();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * from telefone where fk_cliente_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Telefone t = new Telefone();
                t.setDdd(rs.getString("ddd"));
                t.setAntesh(rs.getString("antesh"));
                t.setDepoish(rs.getString("depoish"));
                telefones.add(t);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return telefones;
    }

    //METODO QUE BUSCA O TELEFONE POR ID
    public static Telefone search_telefone_individual_por_id(String username, String password, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Telefone t = new Telefone();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * from telefone where fk_cliente_cod=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {

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

    //METODO QUE BUSCA PRODUTO PELO ID
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
                p.setCod_barra(rs.getString("cod_barra"));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return p;
    }

    //METODO QUE BUSCA O PRODUTO PELO CÓDIGO DE BARRAS
    public static Produto search_produto_por_cdb(String username, String password, String cdb) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        Produto p = new Produto();
        try {
            con = getConnection(username, password);
            stmt = con.prepareStatement("SELECT * FROM produtos WHERE cod_barra=?");
            stmt.setString(1, cdb);
            rs = stmt.executeQuery();
            if (rs.next()) {
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
                p.setCod_barra(rs.getString("cod_barra"));
                return p;
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return null;
    }

    //METODO QUE BUSCA O ENDERECO PELO ID DO CLIENTE
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
            if (rs.next()) {
                e.setTipolog(rs.getString("tipolog"));
                e.setLog(rs.getString("logradouro"));
                e.setBairro(rs.getString("bairro"));
                e.setComplemento(rs.getString("complemento"));
                e.setEstado(rs.getString("estado"));
                e.setMunicipio(rs.getString("cidade"));
                e.setCep(rs.getString("cep"));
                return e;
            }

        } catch (Exception en) {
            System.out.println(en);
        } finally {
            closeConnection(con, stmt, rs);
        }
        return null;
    }

    //METODO QUE BUSCA O ULTIMO PEDIDO E RETORNA O ID
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

    //METODO QUE BUSCA O ULTIMO CLIENTE E RETORNA O ID
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

    //METODO QUE BUSCA O ESTOQUE PELO ID
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
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String data = fmt.format(rs.getDate("validade"));
                    java.sql.Date dt = new java.sql.Date(fmt.parse(data).getTime());
                    e.setValidade(dt);
                } catch (Exception ex) {
                    e.setValidade(null);
                }

                e.setQnt_estoque(rs.getInt("qnt_estoque"));
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return e;
    }

    //METODO QUE BUSCA O ULTIMO ESTOQUE E RETORNA O ID
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

    //METODO QUE BUSCA O FORNECEDOR PELO ID
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
