package gui.abas;

import bean.Cliente;
import bean.Pedido;
import bean.Pedido_item;
import bean.Produto;
import dao.MiscDAO;
import dao.PedidoDAO;
import gui.Realizar_troca;
import gui.Realizar_venda;
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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Painel_vendas extends JPanel {

    private String username;
    private String password;

    private JPanel painel_de_dados;
    private JLabel id_pedido_l;
    private JLabel dt_pedido_l;
    private JLabel pedido_vl_tot_l;
    private JLabel pedido_lucro_liquido_l;
    private JLabel cliente_nome_l;
    private JLabel cliente_id_l;
    private JLabel tipo_pagamento_l;
    private JLabel quantidade_de_itens_l;
    private DefaultTableModel modelo_tabela_itens;
    private JTable tabela_itens;
    private JScrollPane scroll_itens;

    private JTextField id_pedido;
    private JTextField dt_pedido;
    private JTextField pedido_vl_tot;
    private JTextField pedido_lucro_liquido;
    private JTextField cliente_nome;
    private JTextField cliente_id;
    private JTextField tipo_pagamento;
    private JTextField quantidade_de_itens;

    private JButton deletar_pedido;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_botoes;
    private JButton realizar_venda;
    private JButton realizar_consulta;
    private JButton realizar_troca;

    public Painel_vendas(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());
        inicializa_painel_de_dados();
        inicializa_painel_da_tabela();
        inicializa_painel_de_botoes();
        setVisible(true);
    }

    private void inicializa_painel_de_dados() {
        painel_de_dados = new JPanel(new FlowLayout());
        painel_de_dados.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_de_dados.setPreferredSize(new Dimension(600, 1000));

        id_pedido_l = new JLabel("ID Pedido:");
        dt_pedido_l = new JLabel("Data do Pedido:");
        pedido_vl_tot_l = new JLabel("Valor Tot. do Pedido:R$");
        pedido_lucro_liquido_l = new JLabel("Lucro Liquido:R$");
        cliente_nome_l = new JLabel("Nome do Cliente:");
        cliente_id_l = new JLabel("ID Cliente:");
        tipo_pagamento_l = new JLabel("Tipo de Pagamento:");
        quantidade_de_itens_l = new JLabel("Quantidade Itens:");

        modelo_tabela_itens = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        tabela_itens = new JTable(modelo_tabela_itens);
        
        modelo_tabela_itens.addColumn("ID");
        modelo_tabela_itens.addColumn("Nome do Produto");
        modelo_tabela_itens.addColumn("Preço Uni. Venda");
        modelo_tabela_itens.addColumn("Quantidade");
        
        tabela_itens.getColumnModel().getColumn(0).setMaxWidth(70);
        tabela_itens.getColumnModel().getColumn(0).setMinWidth(70);
        tabela_itens.getColumnModel().getColumn(2).setMaxWidth(120);
        tabela_itens.getColumnModel().getColumn(2).setMinWidth(120);
        tabela_itens.getColumnModel().getColumn(3).setMaxWidth(70);
        tabela_itens.getColumnModel().getColumn(3).setMinWidth(70);
        scroll_itens = new JScrollPane(tabela_itens);
        
        scroll_itens.setPreferredSize(new Dimension(550,200));

        id_pedido = new JTextField();
        dt_pedido = new JTextField();
        pedido_vl_tot = new JTextField();
        pedido_lucro_liquido = new JTextField();
        cliente_nome = new JTextField();
        cliente_id = new JTextField();
        tipo_pagamento = new JTextField();
        quantidade_de_itens = new JTextField();

        deletar_pedido = new JButton("Deletar Pedido:", new ImageIcon(getClass().getResource("ico_deletar.png")));

        id_pedido.setEditable(false);
        dt_pedido.setEditable(false);
        pedido_vl_tot.setEditable(false);
        pedido_lucro_liquido.setEditable(false);
        cliente_nome.setEditable(false);
        cliente_id.setEditable(false);
        tipo_pagamento.setEditable(false);
        quantidade_de_itens.setEditable(false);

        id_pedido.setPreferredSize(new Dimension(70, 18));
        dt_pedido.setPreferredSize(new Dimension(120, 18));
        pedido_vl_tot.setPreferredSize(new Dimension(130, 18));
        pedido_lucro_liquido.setPreferredSize(new Dimension(130, 18));
        cliente_nome.setPreferredSize(new Dimension(400, 18));
        cliente_id.setPreferredSize(new Dimension(70, 18));
        tipo_pagamento.setPreferredSize(new Dimension(170, 18));
        quantidade_de_itens.setPreferredSize(new Dimension(70, 18));

        painel_de_dados.add(id_pedido_l);
        painel_de_dados.add(id_pedido);
        painel_de_dados.add(dt_pedido_l);
        painel_de_dados.add(dt_pedido);
        painel_de_dados.add(cliente_id_l);
        painel_de_dados.add(cliente_id);
        painel_de_dados.add(cliente_nome_l);
        painel_de_dados.add(cliente_nome);
        painel_de_dados.add(pedido_vl_tot_l);
        painel_de_dados.add(pedido_vl_tot);
        painel_de_dados.add(pedido_lucro_liquido_l);
        painel_de_dados.add(pedido_lucro_liquido);
        painel_de_dados.add(tipo_pagamento_l);
        painel_de_dados.add(tipo_pagamento);
        painel_de_dados.add(quantidade_de_itens_l);
        painel_de_dados.add(quantidade_de_itens);
        painel_de_dados.add(scroll_itens);
        painel_de_dados.add(deletar_pedido);
        

        add(painel_de_dados, BorderLayout.LINE_START);
    }

    private void inicializa_painel_da_tabela() {
        painel_da_tabela = new JPanel(new GridLayout());
        modelo_tabela = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
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

        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome do Cliente");
        modelo_tabela.addColumn("Valor Tot. Pedido");
        modelo_tabela.addColumn("Tipo de Pagamento");
        modelo_tabela.addColumn("Dt. Pedido");

        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);
        tabela.getColumnModel().getColumn(2).setMaxWidth(120);
        tabela.getColumnModel().getColumn(2).setMinWidth(120);
        tabela.getColumnModel().getColumn(3).setMaxWidth(170);
        tabela.getColumnModel().getColumn(3).setMinWidth(170);
        tabela.getColumnModel().getColumn(4).setMaxWidth(80);
        tabela.getColumnModel().getColumn(4).setMinWidth(80);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);
        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        realizar_venda = new JButton("Realizar Venda", new ImageIcon(getClass().getResource("ico_money.png")));
        realizar_troca = new JButton("Realizar Troca", new ImageIcon(getClass().getResource("ico_troca.png")));
        realizar_consulta = new JButton("Consultar Vendas", new ImageIcon(getClass().getResource("ico_lupa.png")));
        painel_de_botoes.add(realizar_venda);
        painel_de_botoes.add(realizar_consulta);
        painel_de_botoes.add(realizar_troca);

        realizar_venda.addActionListener((ActionEvent) -> {
            new Realizar_venda(this.username, this.password);
        });
        realizar_troca.addActionListener((ActionEvent) -> {
            new Realizar_troca(this.username, this.password);
        });
        realizar_consulta.addActionListener((ActionEvent) -> {
            atualizar_tabela();
        });

        add(painel_de_botoes, BorderLayout.PAGE_START);

    }

    private void atualizar_caixas_de_texto() {
        Pedido p = MiscDAO.search_pedido_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        id_pedido.setText("" + p.getCod_pedido());
        dt_pedido.setText("" + p.getDt_pedido());
        pedido_vl_tot.setText("" + p.getPedido_vl_tot());
        tipo_pagamento.setText("" + p.getPagamento());
        Cliente c = MiscDAO.search_cliente_por_id(username, password, MiscDAO.get_id_pedido_item_por_fk(username, password, p.getCod_pedido()));
        cliente_nome.setText("" + c.getNome());
        cliente_id.setText("" + c.getId());
        quantidade_de_itens.setText("" + MiscDAO.get_quantidade_de_itens_pedido(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0)));
        pedido_lucro_liquido.setText("" + MiscDAO.get_lucro_liquido_pedido(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0)));
        atualizar_tabela_lucro();
    }

    private void atualizar_tabela_lucro() {
        modelo_tabela_itens.setNumRows(0);
        ArrayList<Produto> dados_produto;
        dados_produto = MiscDAO.get_produtos_contidos_pedido(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        for (int i = 0; i < dados_produto.size(); i++) {
            Produto p = dados_produto.get(i);
            modelo_tabela_itens.addRow(new Object[]{
                p.getProduto_cod(),p.getProduto_nome(),p.getPreco_uni_venda(),
                p.getFk_fornecedor_cod()
            });
        }
    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Pedido> dados_pedido;
        dados_pedido = PedidoDAO.read(username, password);
        for (int i = 0; i < dados_pedido.size(); i++) {
            Pedido p = dados_pedido.get(i);
            Pedido_item pi = new Pedido_item();
            Cliente c = new Cliente();
            try {
                pi.setFk_cod_cliente(MiscDAO.get_id_pedido_item_por_fk(username, password, p.getCod_pedido()));
                c = MiscDAO.search_cliente_por_id(username, password, pi.getFk_cod_cliente());
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                modelo_tabela.addRow(new Object[]{
                    p.getCod_pedido(),
                    c.getNome(),
                    p.getPedido_vl_tot(),
                    p.getPagamento(),
                    p.getDt_pedido()
                });
            }
        }

    }
}
