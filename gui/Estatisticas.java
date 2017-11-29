package gui;

import bean.Cliente;
import bean.Pedido;
import bean.Pedido_item;
import bean.Produto;
import dao.MiscDAO;
import dao.PedidoDAO;
import dao.Pedido_itemDAO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Estatisticas extends JFrame {

    private final String username;
    private final String password;

    private JPanel dados_cliente;
    private JLabel cliente_nome_l;
    private JLabel cliente_id_l;
    private JTextField cliente_nome;
    private JTextField cliente_id;
    private JButton buscar_cliente;
    private JButton atualizar_dados;
    private JButton deletar_historico;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_da_tabela2;
    private DefaultTableModel modelo_tabela_itens;
    private JTable tabela_itens;
    private JScrollPane scroll_itens;

    private JPanel tabela_pedidos_itens;

    public Estatisticas(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }

    private void initAll() {
        inicializa_dados_cliente();
        inicializa_pedidos();
        inicializa_itens_do_pedido();

        setResizable(false);
        setSize(830 + 160, 650);
        setLocationRelativeTo(null);
        setTitle("Histórico de Vendas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        URL url = this.getClass().getResource("abas/ico_money.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        setVisible(true);
    }

    /*
    .setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Itens da Venda",
                1,
                1,
                new java.awt.Font("Dialog", 1, 14)
        ));
     */
    private void inicializa_pedidos() {
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
                    atualizar_tabela_pedido();
                }
            }
        });
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                atualizar_tabela_pedido();
            }
        });

        modelo_tabela.addColumn("Valor Tot. Pedido");
        modelo_tabela.addColumn("Tipo de Pagamento");
        modelo_tabela.addColumn("Dt. Pedido");

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);

        painel_da_tabela.add(scroll);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Pedidos",
                1,
                1,
                new java.awt.Font("Dialog", 1, 14)
        ));
        painel_da_tabela.setBounds(5, 70, 450, 550);
        add(painel_da_tabela);
    }

    private void inicializa_itens_do_pedido() {
        painel_da_tabela2 = new JPanel(new GridLayout());
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
        tabela_itens.getColumnModel().getColumn(3).setMaxWidth(85);
        tabela_itens.getColumnModel().getColumn(3).setMinWidth(85);
        scroll_itens = new JScrollPane(tabela_itens);

        scroll_itens.setPreferredSize(new Dimension(550, 200));

        scroll_itens.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Itens do Pedido",
                1,
                1,
                new java.awt.Font("Dialog", 1, 14)
        ));

        painel_da_tabela2.add(scroll_itens);
        painel_da_tabela2.setBounds(460, 70, 520, 550);
        add(painel_da_tabela2);
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

        buscar_cliente = new JButton("Buscar Cliente", new ImageIcon(getClass().getResource("abas/ico_lupa.png")));
        buscar_cliente.setBackground(new Color(30, 30, 30));
        buscar_cliente.setForeground(new Color(255, 255, 255));
        atualizar_dados = new JButton("Consultar Dados", new ImageIcon(getClass().getResource("abas/ico_lupa2.png")));
        atualizar_dados.setBackground(new Color(30, 30, 30));
        atualizar_dados.setForeground(new Color(255, 255, 255));
        deletar_historico = new JButton("Deletar Histórico de Pedidos", new ImageIcon(getClass().getResource("abas/ico_deletar.png")));
        deletar_historico.setBackground(new Color(30, 30, 30));
        deletar_historico.setForeground(new Color(255, 255, 255));

        deletar_historico.addActionListener((ActionEvent) -> {
            try {
                for (int i = 0; i < tabela.getRowCount(); i++) {
                    Pedido_item pi = new Pedido_item();
                    pi.setFk_cod_pedido((int) tabela.getValueAt(i, 0));
                    Pedido_itemDAO.delete(username, password, pi, (byte) 1);
                    Pedido p = MiscDAO.search_pedido_por_id(username, password, pi.getFk_cod_pedido());
                    PedidoDAO.delete(username, password, p);
                    JOptionPane.showMessageDialog(null, "Apagado com Sucesso !");
                    modelo_tabela.setNumRows(0);
                    modelo_tabela_itens.setNumRows(0);
                    scroll_itens.setBorder(BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Color.black),
                            "Itens do Pedido",
                            1,
                            1,
                            new java.awt.Font("Dialog", 1, 14)
                    ));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao apagar os dados.");
            }
        });

        buscar_cliente.addActionListener((ActionEvent) -> {
            new Buscar_cliente(this.username, this.password, cliente_id, cliente_nome);
        });

        dados_cliente.add(cliente_id_l);
        dados_cliente.add(cliente_id);
        dados_cliente.add(cliente_nome_l);
        dados_cliente.add(cliente_nome);

        dados_cliente.add(buscar_cliente);
        dados_cliente.add(atualizar_dados);
        dados_cliente.add(deletar_historico);

        atualizar_dados.addActionListener((ActionEvent) -> {
            atualizar_tabela();
            modelo_tabela_itens.setNumRows(0);
            scroll_itens.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.black),
                    "Itens do Pedido",
                    1,
                    1,
                    new java.awt.Font("Dialog", 1, 14)
            ));
        });

        dados_cliente.setBorder(BorderFactory.createLineBorder(Color.black));
        dados_cliente.setSize(new Dimension(760, 65));
        dados_cliente.setLocation(120, 5);

        add(dados_cliente);
    }

    private void atualizar_tabela() {
        try {
            Integer.parseInt(cliente_id.getText());
            modelo_tabela.setNumRows(0);
            ArrayList<Pedido> dados_pedido = MiscDAO.search_pedido_pelo_cliente(username, password, Integer.parseInt(cliente_id.getText()));
            Cliente c = new Cliente();
            for (int i = 0; i < dados_pedido.size(); i++) {
                Pedido p = dados_pedido.get(i);
                try {
                    c = MiscDAO.search_cliente_por_id(username, password, Integer.parseInt(cliente_id.getText()));
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, selecione um cliente e tente novamente.");

        }

    }

    private void atualizar_tabela_pedido() {
        modelo_tabela_itens.setNumRows(0);
        ArrayList<Produto> dados_produto;
        Pedido ped = MiscDAO.search_pedido_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        dados_produto = MiscDAO.get_produtos_contidos_pedido(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        for (int i = 0; i < dados_produto.size(); i++) {
            Produto p = dados_produto.get(i);
            modelo_tabela_itens.addRow(new Object[]{
                p.getProduto_cod(), p.getProduto_nome(),
                "R$ " + (p.getPreco_uni_venda()
                - (p.getPreco_uni_venda()
                * ped.getDesconto()
                / 100)),
                p.getFk_fornecedor_cod()
            });
        }

        scroll_itens.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Itens do Pedido ID = " + (int) tabela.getValueAt(tabela.getSelectedRow(), 0),
                1,
                1,
                new java.awt.Font("Dialog", 1, 14)
        ));
    }

}
