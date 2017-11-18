package gui.abas;

/*CLASSE RESPONSÁVEL POR:
*APRESENTAR TODOS OS PRODUTOS
*POSSIBILITAR A EDIÇÃO, CRIAÇÃO, LEITURA E REMOÇÃO DE PRODUTOS.
*EXIBIR DADOS RELEVANTES.
*/
import bean.Estoque;
import bean.Fornecedor;
import bean.Produto;
import dao.EstoqueDAO;
import dao.MiscDAO;
import dao.ProdutoDAO;
import gui.Cadastrar_produto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Painel_produto extends JPanel {
    //TODOS OBJETOS UTILIZADOS
    private final String username;
    private final String password;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_botoes;
    private JButton cadastrar_produtos;
    private JButton realizar_consulta;
    private JButton editar_dados;
    private JButton busca_produto_b;
    private JTextField busca_produto;

    private JPanel painel_de_dados;

    private JLabel produto_foto;
    private JTextField produto_cod;
    private JTextField produto_nome;
    private JTextField preco_uni_compra;
    private JTextField preco_uni_venda;
    private JTextField categoria;
    private JTextArea descricao_produto;
    private JTextField unidade_medida_peso;
    private JTextField peso_produto;
    private JTextField quantidade_estoque;
    private JTextField nome_fornecedor;
    private JTextField validade;

    private JButton deletar_produto;

    private JLabel produto_foto_l;
    private JLabel produto_cod_l;
    private JLabel produto_nome_l;
    private JLabel preco_uni_compra_l;
    private JLabel preco_uni_venda_l;
    private JLabel categoria_l;
    private JLabel descricao_produto_l;
    private JLabel unidade_medida_peso_l;
    private JLabel peso_produto_l;
    private JLabel quantidade_estoque_l;
    private JLabel nome_fornecedor_l;
    private JLabel validade_l;
    //METODO CONSTRUTOR QUE RECEBE USUARIO, SENHA E CHAMA O METODO
    // QUE INICIALIZA TODOS OS COMPONENTES
    public Painel_produto(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;

        initAll();
    }
    //METODO QUE INICIALIZA TABELA, BOTOES E DADOS.
    private void initAll() {
        setLayout(new BorderLayout());
        inicializa_painel_da_tabela();
        inicializa_painel_de_botoes();
        inicializa_painel_de_dados();
        setVisible(true);
    }
    //METODO RESPONSAVEL POR INICIALIZAR
    //TODOS COMPONENTES LOCALIZADOS NA PARTE ESQUERDA DO PAINEL
    private void inicializa_painel_de_dados() {
        painel_de_dados = new JPanel(new FlowLayout());

        produto_foto = new JLabel();
        produto_foto.setBorder(BorderFactory.createLineBorder(Color.black));
        produto_foto.setPreferredSize(new Dimension(100, 100));

        produto_cod = new JTextField();
        produto_nome = new JTextField();
        preco_uni_compra = new JTextField();
        preco_uni_venda = new JTextField();
        categoria = new JTextField();
        descricao_produto = new JTextArea();
        unidade_medida_peso = new JTextField();
        peso_produto = new JTextField();
        quantidade_estoque = new JTextField();
        nome_fornecedor = new JTextField();
        validade = new JTextField();

        deletar_produto = new JButton("Deletar Produto", new ImageIcon(getClass().getResource("ico_deletar.png")));

        descricao_produto.setLineWrap(true);

        produto_cod.setPreferredSize(new Dimension(220, 18));
        produto_nome.setPreferredSize(new Dimension(340, 18));
        preco_uni_compra.setPreferredSize(new Dimension(120, 18));
        preco_uni_venda.setPreferredSize(new Dimension(120, 18));
        categoria.setPreferredSize(new Dimension(200, 18));
        unidade_medida_peso.setPreferredSize(new Dimension(130, 18));
        peso_produto.setPreferredSize(new Dimension(200, 18));
        quantidade_estoque.setPreferredSize(new Dimension(120, 18));
        nome_fornecedor.setPreferredSize(new Dimension(370, 18));
        descricao_produto.setPreferredSize(new Dimension(330, 48));
        validade.setPreferredSize(new Dimension(80, 18));

        produto_cod.setEditable(false);
        produto_nome.setEditable(false);
        preco_uni_compra.setEditable(false);
        preco_uni_venda.setEditable(false);
        categoria.setEditable(false);
        descricao_produto.setEditable(false);
        unidade_medida_peso.setEditable(false);
        peso_produto.setEditable(false);
        quantidade_estoque.setEditable(false);
        nome_fornecedor.setEditable(false);
        validade.setEditable(false);

        produto_foto_l = new JLabel("Foto do Produto:");
        produto_cod_l = new JLabel("ID:");
        produto_nome_l = new JLabel("Nome do Produto:");
        preco_uni_compra_l = new JLabel("Preco Uni. Compra:");
        preco_uni_venda_l = new JLabel("Preco Uni. Venda:");
        categoria_l = new JLabel("Categoria:");
        descricao_produto_l = new JLabel("Descrição:");
        unidade_medida_peso_l = new JLabel("Unidade Medida:");
        peso_produto_l = new JLabel("Peso:");
        quantidade_estoque_l = new JLabel("Quantidade em Estoque:");
        nome_fornecedor_l = new JLabel("Nome do Fornecedor:");
        validade_l = new JLabel("Validade:");

        painel_de_dados.add(produto_foto_l);
        painel_de_dados.add(produto_foto);
        painel_de_dados.add(produto_cod_l);
        painel_de_dados.add(produto_cod);
        painel_de_dados.add(produto_nome_l);
        painel_de_dados.add(produto_nome);
        painel_de_dados.add(preco_uni_compra_l);
        painel_de_dados.add(preco_uni_compra);
        painel_de_dados.add(preco_uni_venda_l);
        painel_de_dados.add(preco_uni_venda);
        painel_de_dados.add(categoria_l);
        painel_de_dados.add(categoria);
        painel_de_dados.add(quantidade_estoque_l);
        painel_de_dados.add(quantidade_estoque);
        painel_de_dados.add(unidade_medida_peso_l);
        painel_de_dados.add(unidade_medida_peso);
        painel_de_dados.add(peso_produto_l);
        painel_de_dados.add(peso_produto);
        painel_de_dados.add(nome_fornecedor_l);
        painel_de_dados.add(nome_fornecedor);
        painel_de_dados.add(validade_l);
        painel_de_dados.add(validade);
        painel_de_dados.add(descricao_produto_l);
        painel_de_dados.add(descricao_produto);
        painel_de_dados.add(deletar_produto);
        //EVENTO NO BOTÃO DE DELETAR PRODUTO.
        deletar_produto.addActionListener((ActionEvent) -> {
            Produto p = MiscDAO.search_produto_por_id(username, password, Integer.parseInt(produto_cod.getText()));
            Estoque e = new Estoque();
            e.setEstoque_cod(p.getFk_estoque_cod());
            Fornecedor f = new Fornecedor();
            f.setId(p.getFk_fornecedor_cod());
            ProdutoDAO.delete(username, password, p);
            EstoqueDAO.delete(username, password, e);
            atualizar_tabela((byte)0);
        });

        painel_de_dados.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_de_dados.setPreferredSize(new Dimension(600, 1000));
        add(painel_de_dados, BorderLayout.LINE_START);
    }
    //METODO RESPONSAVEL POR INICIALIZAR
    //TODOS COMPONENTES LOCALIZADOS NA PARTE SUPERIOR DO PAINEL
    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_produtos = new JButton("Cadastrar Novo Produto", new ImageIcon(getClass().getResource("ico_mais.png")));
        realizar_consulta = new JButton("Consultar Produtos", new ImageIcon(getClass().getResource("ico_lupa.png")));
        editar_dados = new JButton("Editar Dados do Produto", new ImageIcon(getClass().getResource("ico_editar.png")));

        busca_produto_b = new JButton(new ImageIcon(getClass().getResource("ico_lupa2.png")));
        busca_produto = new JTextField();
        busca_produto.setPreferredSize(new Dimension(270, 24));

        painel_de_botoes.add(cadastrar_produtos);
        painel_de_botoes.add(realizar_consulta);
        painel_de_botoes.add(editar_dados);
        painel_de_botoes.add(busca_produto);
        painel_de_botoes.add(busca_produto_b);
        add(painel_de_botoes, BorderLayout.PAGE_START);

        realizar_consulta.addActionListener((ActionEvent) -> {
            atualizar_tabela((byte)0);
        });
        cadastrar_produtos.addActionListener((ActionEvent) -> {
            new Cadastrar_produto(this.username, this.password);
        });
        busca_produto_b.addActionListener((ActionEvent)->{
            atualizar_tabela((byte)1);
        });
    }
    //METODO RESPONSAVEL POR INICIALIZAR
    //TODOS COMPONENTES LOCALIZADOS NA PARTE SUPERIOR DO PAINEL
    private void inicializa_painel_da_tabela() {
        painel_da_tabela = new JPanel(new GridLayout());
        modelo_tabela = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return ImageIcon.class;
                    default:
                        return Object.class;
                }
            }
        };
        tabela = new JTable(modelo_tabela);
        tabela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ((ke.getKeyCode() == KeyEvent.VK_UP) || (ke.getKeyCode() == KeyEvent.VK_DOWN)) {
                    atualizar_caixas_de_texto();
                }
            }
        });
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                atualizar_caixas_de_texto();
            }
        });

        modelo_tabela.addColumn("Foto");
        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");
        modelo_tabela.addColumn("Preco uni. C");
        modelo_tabela.addColumn("Preco uni. V");
        modelo_tabela.addColumn("Categoria");

        tabela.setRowHeight(100);

        tabela.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela.getColumnModel().getColumn(0).setMinWidth(100);

        tabela.getColumnModel().getColumn(1).setMaxWidth(70);
        tabela.getColumnModel().getColumn(1).setMinWidth(70);

        tabela.getColumnModel().getColumn(3).setMaxWidth(150);
        tabela.getColumnModel().getColumn(3).setMinWidth(150);

        tabela.getColumnModel().getColumn(4).setMaxWidth(150);
        tabela.getColumnModel().getColumn(4).setMinWidth(150);

        tabela.getColumnModel().getColumn(5).setMaxWidth(180);
        tabela.getColumnModel().getColumn(5).setMinWidth(180);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }
    //METODO QUE MOSTRA OS ATRIBUTOS DO PRODUTO NO PAINEL DA ESQUERDA
    private void atualizar_caixas_de_texto() {
        Produto p = MiscDAO.search_produto_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 1));
        produto_foto.setIcon(p.getProduto_foto_para_tabela());
        produto_cod.setText("" + p.getProduto_cod());
        produto_nome.setText(p.getProduto_nome());
        preco_uni_compra.setText("" + p.getPreco_uni_compra());
        preco_uni_venda.setText("" + p.getPreco_uni_venda());
        categoria.setText(p.getCategoria());
        descricao_produto.setText(p.getDescricao_produto());
        unidade_medida_peso.setText(p.getUnidade_medida_peso());
        peso_produto.setText("" + p.getPeso_produto());
        if ("Kg".equals(unidade_medida_peso.getText()) || "g".equals(unidade_medida_peso.getText())) {
            peso_produto_l.setText("Peso    :");
        } else if ("L".equals(unidade_medida_peso.getText()) || "ml".equals(unidade_medida_peso.getText())) {
            peso_produto_l.setText("Volume  :");
        } else {
            peso_produto_l.setText("Capsulas:");
        }
        try {
            Fornecedor f = MiscDAO.search_fornecedor_por_id(username, password, p.getFk_fornecedor_cod());
            nome_fornecedor.setText(f.getNome());

        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Estoque e = MiscDAO.search_estoque_por_id(username, password, p.getFk_estoque_cod());
            quantidade_estoque.setText("" + e.getQnt_estoque());
            validade.setText("" + e.getValidade());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void atualizar_tabela(byte mode) {
        modelo_tabela.setNumRows(0);
        ArrayList<Produto> dados_produtos;
        if (mode == 0) {
            dados_produtos = ProdutoDAO.read(username, password);
        } else {
            dados_produtos = MiscDAO.search_produto_por_nome(this.username, this.password, busca_produto.getText());
        }

        for (int i = 0; i < dados_produtos.size(); i++) {
            Produto p = dados_produtos.get(i);
            modelo_tabela.addRow(
                    new Object[]{p.getProduto_foto_para_tabela(),
                        p.getProduto_cod(),
                        p.getProduto_nome(),
                        p.getPreco_uni_compra(),
                        p.getPreco_uni_venda(),
                        p.getCategoria()}
            );
        }

    }
}
