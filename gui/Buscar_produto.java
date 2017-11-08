package gui;

import bean.Estoque;
import bean.Produto;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Buscar_produto extends JFrame {

    private final String username;
    private final String password;

    private JButton escolher;

    private JPanel painel_de_botoes;
    private JTextField busca_produto;
    private JButton busca_produto_b;
    private JButton cadastrar_produto;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JLabel unidades_l;
    private JTextField unidades;

    private Produto p;
    private Estoque e;

    private DefaultTableModel dmt;

    private JTextField vl_tot;
    private JTextField vl_liq;
    private JTextField vl_qnt;

    public Buscar_produto(String currentusername, String currentpassword,
            DefaultTableModel dmt, JTextField vl_tot, JTextField vl_liq, JTextField vl_qnt) {
        this.username = currentusername;
        this.password = currentpassword;
        this.dmt = dmt;
        this.vl_tot = vl_tot;
        this.vl_liq = vl_liq;
        this.vl_qnt = vl_qnt;
        initAll();

    }

    private void initAll() {
        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();

        escolher = new JButton("Confirmar Escolha", new ImageIcon(getClass().getResource("abas/ico_confirmar.png")));
        escolher.addActionListener((ActionEvent) -> {

            p = MiscDAO.search_produto_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 1));

            dmt.addRow(new Object[]{p.getProduto_foto_para_tabela(),
                p.getProduto_cod(), p.getProduto_nome(), p.getPreco_uni_compra(),
                p.getPreco_uni_venda(), unidades.getText()});

            float sum;
            sum = p.getPreco_uni_venda();
            sum *= Float.parseFloat(unidades.getText());
            sum += Float.parseFloat(vl_tot.getText());
            vl_tot.setText(""+sum);
            
            sum = p.getPreco_uni_venda()-p.getPreco_uni_compra();
            sum *= Float.parseFloat(unidades.getText());
            sum += Float.parseFloat(vl_liq.getText());
            vl_liq.setText(""+sum);
            
            sum = Float.parseFloat(unidades.getText());
            sum+= Float.parseFloat(vl_qnt.getText());
            vl_qnt.setText(""+sum);
            
            dispose();
        });

        add(escolher, BorderLayout.PAGE_END);

        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Selecionar Produto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        busca_produto = new JTextField();
        cadastrar_produto = new JButton("Cadastrar Novo Produto", new ImageIcon(getClass().getResource("abas/ico_mais.png")));
        busca_produto.setPreferredSize(new Dimension(300, 24));
        busca_produto_b = new JButton(new ImageIcon(getClass().getResource("abas/ico_lupa2.png")));
        painel_de_botoes.add(busca_produto);
        painel_de_botoes.add(busca_produto_b);

        unidades_l = new JLabel("Unidades que deseja vender:");
        unidades = new JTextField("0");
        unidades.setPreferredSize(new Dimension(70, 24));

        painel_de_botoes.add(unidades_l);
        painel_de_botoes.add(unidades);
        
        painel_de_botoes.add(cadastrar_produto);

        busca_produto_b.addActionListener((ActionEvent) -> {
            atualizar_tabela();
        });
        
        cadastrar_produto.addActionListener((ActionEvent)->{
            new Cadastrar_produto(username,password);
        });

        add(painel_de_botoes, BorderLayout.PAGE_START);

    }

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

        modelo_tabela.addColumn("Foto");
        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");
        modelo_tabela.addColumn("Preco Uni. Compra");
        modelo_tabela.addColumn("Preco Uni. Venda");
        modelo_tabela.addColumn("Qnt. em Estoque");

        tabela.setRowHeight(100);

        tabela.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela.getColumnModel().getColumn(0).setMinWidth(100);
        tabela.getColumnModel().getColumn(1).setMaxWidth(70);
        tabela.getColumnModel().getColumn(1).setMinWidth(70);
        tabela.getColumnModel().getColumn(3).setMaxWidth(150);
        tabela.getColumnModel().getColumn(3).setMinWidth(150);
        tabela.getColumnModel().getColumn(4).setMaxWidth(150);
        tabela.getColumnModel().getColumn(4).setMinWidth(150);
        tabela.getColumnModel().getColumn(5).setMaxWidth(70);
        tabela.getColumnModel().getColumn(5).setMinWidth(70);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);

        painel_da_tabela.add(scroll);
        add(painel_da_tabela, BorderLayout.CENTER);

    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Produto> dados_produto = MiscDAO.search_produto_por_nome(username, password, busca_produto.getText());
        for (int i = 0; i < dados_produto.size(); i++) {
            p = dados_produto.get(i);
            e = MiscDAO.search_estoque_por_id(username, password, p.getFk_estoque_cod());
            modelo_tabela.addRow(new Object[]{p.getProduto_foto_para_tabela(),
                p.getProduto_cod(), p.getProduto_nome(), p.getPreco_uni_compra(),
                p.getPreco_uni_venda(), e.getQnt_estoque()});
        }
    }
}
