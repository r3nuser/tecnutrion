package gui;

import bean.Fornecedor;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Buscar_fornecedor extends JFrame {

    private final String username;
    private final String password;

    private JPanel painel_de_botoes;
    private JTextField busca_fornecedor;
    private JButton busca_fornecedor_b;
    private JButton cadastrar_fornecedor;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JButton escolher;

    private Fornecedor tf;
    private JTextField caixa;

    public Buscar_fornecedor(String currentusername, String currentpassword, Fornecedor tf, JTextField caixa) {
        this.username = currentusername;
        this.password = currentpassword;
        this.tf = tf;
        this.caixa = caixa;
        initAll();

    }

    public void initAll() {
        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();
        escolher = new JButton("Confirmar Escolha", new ImageIcon(getClass().getResource("abas/ico_confirmar.png")));
        add(escolher, BorderLayout.PAGE_END);
        escolher.addActionListener((ActionEvent) -> {
            tf.setId((int) tabela.getValueAt(tabela.getSelectedRow(), 0));
            tf.setNome("" + tabela.getValueAt(tabela.getSelectedRow(), 1));
            caixa.setText(tf.getNome());
            dispose();
        });
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Selecionar Fornecedor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void inicializa_painel_de_botoes() {
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEFT);
        painel_de_botoes = new JPanel(fl);

        busca_fornecedor = new JTextField();
        busca_fornecedor.setPreferredSize(new Dimension(280, 24));

        busca_fornecedor_b = new JButton(new ImageIcon(getClass().getResource("abas/ico_lupa2.png")));
        busca_fornecedor_b.addActionListener((ActionEvent)->{
            atualizar_tabela();
        });
        
        cadastrar_fornecedor = new JButton("Cadastrar Novo Fornecedor",new ImageIcon(getClass().getResource("abas/ico_mais.png")));
        cadastrar_fornecedor.addActionListener((ActionEvent)->{
            new Cadastrar_fornecedor(this.username,this.password);
        });
        
        

        painel_de_botoes.add(busca_fornecedor);
        painel_de_botoes.add(busca_fornecedor_b);
        painel_de_botoes.add(cadastrar_fornecedor);
        
        painel_de_botoes.setBorder(BorderFactory.createLineBorder(Color.black));

        add(painel_de_botoes, BorderLayout.PAGE_START);
    }

    private void inicializa_painel_da_tabela() {
        painel_da_tabela = new JPanel(new GridLayout());

        modelo_tabela = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        tabela = new JTable(modelo_tabela);

        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");

        tabela.getColumnModel().getColumn(0).setMaxWidth(70);
        tabela.getColumnModel().getColumn(0).setMinWidth(70);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);

        painel_da_tabela.add(scroll);
        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Fornecedor> dados_fornecedor = MiscDAO.search_fornecedor_por_nome(username, password, busca_fornecedor.getText());

        for (int i = 0; i < dados_fornecedor.size(); i++) {
            Fornecedor f = dados_fornecedor.get(i);
            modelo_tabela.addRow(new Object[]{f.getId(), f.getNome()});
        }

    }
}
