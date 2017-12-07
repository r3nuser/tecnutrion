package gui;

import bean.Estoque;
import bean.Pedido;
import bean.Pedido_item;
import bean.Produto;
import dao.EstoqueDAO;
import dao.MiscDAO;
import dao.PedidoDAO;
import dao.Pedido_itemDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import sql.Sql;

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
    private JCheckBox dar_desconto;
    private JTextField desconto;
    private JLabel porcentagem;

    private JPanel dados_financeiros;
    private JLabel pedido_vl_tot_l;
    private JTextField pedido_vl_tot;
    private JLabel lucro_liquido_l;
    private JTextField lucro_liquido;
    private JLabel qnt_itens_l;
    private JTextField qnt_itens;
    private JLabel preco_c_desconto_l;
    private JTextField preco_c_desconto;
    private JLabel lucro_c_desconto_l;
    private JTextField lucro_c_desconto;

    private JPanel itens_pedido;

    private JPanel botoes_pedido;
    private JButton adicionar_produto;
    private JButton remover_produto;

    private JPanel tabela_pedido;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JButton realizar_venda;

    public Realizar_venda(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }

    private void initAll() {

        inicializa_dados_cliente();
        inicializa_dados_financeiros();
        inicializa_itens_pedido();

        realizar_venda = new JButton("Finalizar Venda", new ImageIcon(getClass().getResource("abas/ico_money.png")));
        realizar_venda.setLocation(402, 750);
        realizar_venda.setSize(220, 30);
        add(realizar_venda);
        realizar_venda.setBackground(new Color(30, 30, 30));
        realizar_venda.setForeground(new Color(255, 255, 255));
        realizar_venda.addActionListener((ActionEvent) -> {
            if (validacao()) {
                Pedido p = new Pedido();
                atualizar_valor_desconto();
                p.setDt_pedido(new Date(System.currentTimeMillis()));
                if (dar_desconto.isSelected()) {
                    p.setPedido_vl_tot(Float.parseFloat(preco_c_desconto.getText()));
                    p.setDesconto(Integer.parseInt(desconto.getText()));
                } else {
                    p.setPedido_vl_tot(Float.parseFloat(pedido_vl_tot.getText()));
                    p.setDesconto(0);
                }
                p.setPagamento("" + tipo_pagamento.getSelectedItem());

                PedidoDAO.create(username, password, p);
                p.setCod_pedido(MiscDAO.get_ultimo_pedido_id(username, password));

                Pedido_item pi = new Pedido_item();

                pi.setFk_cod_cliente(Integer.parseInt(cliente_id.getText()));
                pi.setFk_cod_pedido(p.getCod_pedido());

                for (int i = 0; i < modelo_tabela.getRowCount(); i++) {
                    pi.setFk_cod_produto(Integer.parseInt("" + tabela.getValueAt(i, 1)));
                    pi.setQuantidade(Integer.parseInt("" + tabela.getValueAt(i, 5)));
                    if (dar_desconto.isSelected()) {
                        pi.setPedido_item_vl_tot(
                                (pi.getQuantidade()
                                * Float.parseFloat(""
                                        + tabela.getValueAt(i, 4)))
                                - ((pi.getQuantidade() * Float.parseFloat(""
                                        + tabela.getValueAt(i, 4))
                                * (Float.parseFloat(desconto.getText())
                                / 100))));
                        pi.setPedido_item_vl_liq(pi.getPedido_item_vl_tot() - pi.getQuantidade() * Float.parseFloat("" + tabela.getValueAt(i, 3)));
                    } else {
                        pi.setPedido_item_vl_tot(pi.getQuantidade() * Float.parseFloat("" + tabela.getValueAt(i, 4)));
                        pi.setPedido_item_vl_liq(pi.getQuantidade() * Float.parseFloat("" + tabela.getValueAt(i, 3)));
                    }
                    Produto pro = MiscDAO.search_produto_por_id(username, password, (int) tabela.getValueAt(i, 1));
                    Estoque e = MiscDAO.search_estoque_por_id(username, password, pro.getFk_estoque_cod());
                    e.setQnt_estoque(e.getQnt_estoque() - pi.getQuantidade());
                    EstoqueDAO.update(username, password, e);
                    Pedido_itemDAO.create(username, password, pi);
                }
                JOptionPane.showMessageDialog(null, "Venda Realizada com Sucesso !");
                dispose();
            }
        });

        setSize(1024, 820);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("Realizar Venda");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        URL url = this.getClass().getResource("abas/ico_money.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        setVisible(true);
    }

    private void inicializa_itens_pedido() {
        itens_pedido = new JPanel(new BorderLayout());
        inicializa_botoes_pedido();
        inicializa_tabela_pedido();

        itens_pedido.setBorder(BorderFactory.createLineBorder(Color.black));
        itens_pedido.setSize(1004, 590);
        itens_pedido.setLocation(5, 140);
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

        adicionar_produto.setBackground(new Color(30, 30, 30));
        adicionar_produto.setForeground(new Color(255, 255, 255));
        remover_produto.setBackground(new Color(30, 30, 30));
        remover_produto.setForeground(new Color(255, 255, 255));

        adicionar_produto.addActionListener((ActionEvent) -> {
            new Buscar_produto(this.username, this.password, this.modelo_tabela,
                    pedido_vl_tot, lucro_liquido, qnt_itens);

        });

        remover_produto.addActionListener((ActionEvent) -> {
            float subt;
            subt = Float.parseFloat("" + tabela.getValueAt(tabela.getSelectedRow(), 4));
            subt *= -1 * Float.parseFloat("" + tabela.getValueAt(tabela.getSelectedRow(), 5));
            subt += Float.parseFloat(pedido_vl_tot.getText());
            pedido_vl_tot.setText(subt + "");

            subt = Float.parseFloat("" + tabela.getValueAt(tabela.getSelectedRow(), 4))
                    - Float.parseFloat("" + tabela.getValueAt(tabela.getSelectedRow(), 3));
            subt *= -1 * Float.parseFloat("" + tabela.getValueAt(tabela.getSelectedRow(), 5));
            subt += Float.parseFloat(lucro_liquido.getText());
            lucro_liquido.setText(subt + "");

            subt = Float.parseFloat("" + tabela.getValueAt(tabela.getSelectedRow(), 5));
            subt *= -1;
            subt += Float.parseFloat(qnt_itens.getText());
            qnt_itens.setText(subt + "");
            tabela.getValueAt(tabela.getSelectedRow(), 3);
            tabela.getValueAt(tabela.getSelectedRow(), 4);
            tabela.getValueAt(tabela.getSelectedRow(), 5);
            modelo_tabela.removeRow(tabela.getSelectedRow());
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
        qnt_itens.setPreferredSize(new Dimension(240, 18));

        preco_c_desconto_l = new JLabel("Preco c/ Desconto:   R$");
        preco_c_desconto = new JTextField("0");

        preco_c_desconto.setEditable(false);
        preco_c_desconto.setPreferredSize(new Dimension(240, 18));

        lucro_c_desconto_l = new JLabel("Lucro c/ Desconto:   R$");
        lucro_c_desconto = new JTextField("0");

        lucro_c_desconto.setEditable(false);
        lucro_c_desconto.setPreferredSize(new Dimension(240, 18));

        dados_financeiros.add(pedido_vl_tot_l);
        dados_financeiros.add(pedido_vl_tot);
        dados_financeiros.add(lucro_liquido_l);
        dados_financeiros.add(lucro_liquido);
        dados_financeiros.add(qnt_itens_l);
        dados_financeiros.add(qnt_itens);
        dados_financeiros.add(preco_c_desconto_l);
        dados_financeiros.add(preco_c_desconto);
        dados_financeiros.add(lucro_c_desconto_l);
        dados_financeiros.add(lucro_c_desconto);

        dados_financeiros.setBorder(BorderFactory.createLineBorder(Color.black));
        dados_financeiros.setSize(new Dimension(1024 - 580, 120));
        dados_financeiros.setLocation(570, 10);
        add(dados_financeiros);
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

        buscar_cliente = new JButton("Procurar Cliente", new ImageIcon(getClass().getResource("abas/ico_lupa.png")));
        buscar_cliente.setBackground(new Color(30, 30, 30));
        buscar_cliente.setForeground(new Color(255, 255, 255));
        buscar_cliente.addActionListener((ActionEvent) -> {
            new Buscar_cliente(this.username, this.password, cliente_id, cliente_nome);
        });

        dar_desconto = new JCheckBox("Venda com Desconto ?  ");
        dar_desconto.addActionListener((ActionEvent) -> {
            if (dar_desconto.isSelected()) {
                desconto.setEditable(true);
                desconto.setText("0");
                atualizar_valor_desconto();
            } else {
                desconto.setEditable(false);
                desconto.setText("");
                preco_c_desconto.setText("");
            }
        });
        desconto = new JTextField();
        desconto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                atualizar_valor_desconto();
            }
        });
        desconto.setEditable(false);
        desconto.setPreferredSize(new Dimension(25, 18));
        porcentagem = new JLabel("%");

        dados_cliente.add(cliente_id_l);
        dados_cliente.add(cliente_id);
        dados_cliente.add(cliente_nome_l);
        dados_cliente.add(cliente_nome);
        dados_cliente.add(tipo_pagamento_l);
        dados_cliente.add(tipo_pagamento);
        dados_cliente.add(buscar_cliente);

        dados_cliente.add(dar_desconto);
        dados_cliente.add(desconto);
        dados_cliente.add(porcentagem);

        dados_cliente.setBorder(BorderFactory.createLineBorder(Color.black));
        dados_cliente.setSize(new Dimension(550, 120));
        dados_cliente.setLocation(5, 10);

        add(dados_cliente);
    }

    private void atualizar_valor_desconto() {
        try {
            preco_c_desconto.setText(
                    ""
                    + (Float.parseFloat(pedido_vl_tot.getText())
                    - (Float.parseFloat(pedido_vl_tot.getText())
                    * (Float.parseFloat(desconto.getText()) / 100)))
            );
        } catch (Exception e) {
            preco_c_desconto.setText(pedido_vl_tot.getText());
        } finally {
            try {
                if (dar_desconto.isSelected()) {
                    lucro_c_desconto.setText(
                            ""
                            + (Float.parseFloat(preco_c_desconto.getText())
                            - Float.parseFloat(pedido_vl_tot.getText())
                            + Float.parseFloat(lucro_liquido.getText()))
                    );

                } else {
                    lucro_c_desconto.setText("");
                }
            } catch (Exception e) {
                lucro_c_desconto.setText(lucro_liquido.getText());
            }
        }
    }

    private boolean validacao() {
        boolean valido = true;

        if (modelo_tabela.getRowCount() == 0) {
            valido = false;
            JOptionPane.showMessageDialog(null, "Impossivel realizar venda sem itens."
                    + "Por favor insira itens na tabela de pedido.");
        } else if ("".equals(cliente_nome.getText())) {
            valido = false;
            JOptionPane.showMessageDialog(null, "Impossivel realizar venda sem clientes."
                    + "Por favor insira um cliente para realizar a venda.");
            buscar_cliente.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (dar_desconto.isSelected()) {
            try {
                Float.parseFloat(desconto.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Desconto invalido, insira apenas numeros.");
                valido = false;
                desconto.setText("0");
                desconto.setBorder(BorderFactory.createLineBorder(Color.red));
            }

            if (Float.parseFloat(desconto.getText()) > 20) {
                JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(null, "Senha de Gerente Necessária Para Realizar um Desconto maior que 20%");
                JLabel label = new JLabel("Entre com a senha de GERENTE:");
                JPasswordField pass = new JPasswordField(10);
                panel.add(label);
                panel.add(pass);
                String[] options = new String[]{"Cancelar", "Confirmar"};
                int option = JOptionPane.showOptionDialog(null, panel, "Confirmação de Senha",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
                if (option == 1) {
                    char[] password = pass.getPassword();
                    String senha = "";
                    for (int i = 0; i < password.length; i++) {
                        senha += password[i];
                    }
                    if (Sql.getConnection("gerente", senha) == null) {
                        JOptionPane.showMessageDialog(null, "Senha Invalida !");
                        valido = false;
                    }
                } else {
                    valido = false;
                }
            }

        }

        return valido;

    }
}
