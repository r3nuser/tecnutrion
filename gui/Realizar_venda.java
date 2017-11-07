package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Realizar_venda extends JFrame {

    private final String username;
    private final String password;

    private JPanel dados_cliente;
    private JLabel cliente_nome_l;
    private JLabel cliente_id_l;
    private JLabel tipo_pagamento_l;
    private JTextField cliente_nome;
    private JTextField cliente_id;
    private JButton buscar_cliente;
    private JComboBox tipo_pagamento;

    private JPanel dados_financeiros;
    private JLabel pedido_vl_tot_l;
    private JTextField pedido_vl_tot;
    private JLabel lucro_liquido_l;
    private JTextField lucro_liquido;
    private JLabel qnt_itens_l;
    private JTextField qnt_itens;

    private JPanel itens_pedido;

    private JPanel botoes_pedido;
    private JButton adicionar_produto;
    private JButton remover_produto;

    private JPanel tabela_pedido;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    public Realizar_venda(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }

    private void initAll() {

        inicializa_dados_cliente();
        inicializa_dados_financeiros();
        inicializa_itens_pedido();

        setSize(1024, 768);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("Realizar Venda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void inicializa_itens_pedido() {
        itens_pedido = new JPanel(new BorderLayout());
        inicializa_botoes_pedido();
        inicializa_tabela_pedido();

        itens_pedido.setBorder(BorderFactory.createLineBorder(Color.black));
        itens_pedido.setSize(1004, 628);
        itens_pedido.setLocation(5, 110);
        add(itens_pedido);

    }

    private void inicializa_botoes_pedido() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        botoes_pedido = new JPanel(fl);
        adicionar_produto = new JButton("Adicionar Produto ao Pedido", new ImageIcon(getClass().getResource("abas/ico_mais.png")));
        remover_produto = new JButton("Remover Produto do Pedido", new ImageIcon(getClass().getResource("abas/ico_deletar.png")));

        botoes_pedido.add(adicionar_produto);
        botoes_pedido.add(remover_produto);
        
        adicionar_produto.addActionListener((ActionEvent)->{
            new Buscar_produto(this.username,this.password,this.modelo_tabela,
                    pedido_vl_tot,lucro_liquido,qnt_itens);
        });

        botoes_pedido.setBorder(BorderFactory.createLineBorder(Color.black));
        itens_pedido.add(botoes_pedido, BorderLayout.PAGE_START);

    }

    private void inicializa_tabela_pedido() {
        tabela_pedido = new JPanel(new GridLayout());

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

        modelo_tabela.addColumn("Foto");
        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");
        modelo_tabela.addColumn("Preco uni. C");
        modelo_tabela.addColumn("Preco uni. V");
        modelo_tabela.addColumn("Quantidade");

        tabela.setRowHeight(100);

        tabela.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela.getColumnModel().getColumn(0).setMinWidth(100);
        tabela.getColumnModel().getColumn(1).setMaxWidth(70);
        tabela.getColumnModel().getColumn(1).setMinWidth(70);
        tabela.getColumnModel().getColumn(3).setMaxWidth(150);
        tabela.getColumnModel().getColumn(3).setMinWidth(150);
        tabela.getColumnModel().getColumn(4).setMaxWidth(150);
        tabela.getColumnModel().getColumn(4).setMinWidth(150);
        tabela.getColumnModel().getColumn(5).setMaxWidth(90);
        tabela.getColumnModel().getColumn(5).setMinWidth(90);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);

        tabela_pedido.add(scroll);

        tabela_pedido.setBorder(BorderFactory.createLineBorder(Color.black));
        itens_pedido.add(tabela_pedido, BorderLayout.CENTER);
    }

    private void inicializa_dados_financeiros() {
        dados_financeiros = new JPanel(new FlowLayout());

        pedido_vl_tot_l = new JLabel("Valor Tot. Pedido:    R$");
        pedido_vl_tot = new JTextField("0");

        pedido_vl_tot.setPreferredSize(new Dimension(240, 18));
        pedido_vl_tot.setEditable(false);

        lucro_liquido_l = new JLabel("Valor Lucro Liquido: R$");
        lucro_liquido = new JTextField("0");

        lucro_liquido.setPreferredSize(new Dimension(240, 18));
        lucro_liquido.setEditable(false);

        qnt_itens_l = new JLabel("Quantidade de Itens:");
        qnt_itens = new JTextField("0");

        qnt_itens.setEditable(false);
        qnt_itens.setPreferredSize(new Dimension(130, 18));

        dados_financeiros.add(pedido_vl_tot_l);
        dados_financeiros.add(pedido_vl_tot);
        dados_financeiros.add(lucro_liquido_l);
        dados_financeiros.add(lucro_liquido);
        dados_financeiros.add(qnt_itens_l);
        dados_financeiros.add(qnt_itens);

        dados_financeiros.setBorder(BorderFactory.createLineBorder(Color.black));
        dados_financeiros.setSize(new Dimension(1024 - 580, 90));
        dados_financeiros.setLocation(570, 10);
        add(dados_financeiros);
    }

    private void inicializa_dados_pedido() {

    }

    private void inicializa_dados_cliente() {
        dados_cliente = new JPanel(new FlowLayout());
        cliente_id_l = new JLabel("ID:");
        cliente_id = new JTextField();
        cliente_id.setPreferredSize(new Dimension(70, 18));
        cliente_id.setEditable(false);

        cliente_nome_l = new JLabel("Nome do cliente:");
        cliente_nome = new JTextField();

        cliente_nome.setEditable(false);
        cliente_nome.setPreferredSize(new Dimension(300, 18));

        tipo_pagamento_l = new JLabel("Tipo de Pagamento:");
        tipo_pagamento = new JComboBox(new String[]{"Cartao de Debito", "Cartao de Credito", "Boleto", "A Vista"});

        buscar_cliente = new JButton("Buscar Cliente", new ImageIcon(getClass().getResource("abas/ico_lupa.png")));

        buscar_cliente.addActionListener((ActionEvent)->{
            new Buscar_cliente(this.username,this.password);
        });
        
        dados_cliente.add(cliente_id_l);
        dados_cliente.add(cliente_id);
        dados_cliente.add(cliente_nome_l);
        dados_cliente.add(cliente_nome);
        dados_cliente.add(tipo_pagamento_l);
        dados_cliente.add(tipo_pagamento);
        dados_cliente.add(buscar_cliente);

        dados_cliente.setBorder(BorderFactory.createLineBorder(Color.black));
        dados_cliente.setSize(new Dimension(550, 60));
        dados_cliente.setLocation(5, 10);

        add(dados_cliente);
    }
}
