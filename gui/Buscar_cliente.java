package gui;

import bean.Cliente;
import bean.Telefone;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Buscar_cliente extends JFrame {

    private final String username;
    private final String password;
    private JButton escolher;
    private Cliente c;
    private Telefone t;
    private JTextField id;
    private JTextField nome;

    private JPanel painel_de_botoes;
    private JButton busca_cliente_b;
    private JTextField busca_cliente;
    private JButton cadastrar_cliente;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    public Buscar_cliente(String currentusername, String currentpassword, JTextField id, JTextField nome) {
        this.username = currentusername;
        this.password = currentpassword;
        this.id = id;
        this.nome = nome;
        initAll();
    }

    private void initAll() {
        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();
        escolher = new JButton("Confirmar Escolha", new ImageIcon(getClass().getResource("abas/ico_confirmar.png")));
        escolher.addActionListener((ActionEvent) -> {
            id.setText("" + (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
            nome.setText("" + tabela.getValueAt(tabela.getSelectedRow(), 1));
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
        busca_cliente_b = new JButton(new ImageIcon(getClass().getResource("abas/ico_lupa2.png")));
        busca_cliente = new JTextField();
        busca_cliente.setPreferredSize(new Dimension(300, 24));
        cadastrar_cliente = new JButton("Cadastrar Novo Cliente", new ImageIcon(getClass().getResource("abas/ico_mais.png")));

        painel_de_botoes.add(busca_cliente);
        painel_de_botoes.add(busca_cliente_b);
        painel_de_botoes.add(cadastrar_cliente);

        busca_cliente_b.addActionListener((ActionEvent) -> {
            atualizar_tabela();
        });

        cadastrar_cliente.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(username, password);
        });

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
        modelo_tabela.addColumn("Dt. Nasc");
        modelo_tabela.addColumn("DDD");
        modelo_tabela.addColumn("Prefixo");
        modelo_tabela.addColumn("Sufixo");

        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);

        tabela.getColumnModel().getColumn(1).setPreferredWidth(400);

        tabela.getColumnModel().getColumn(2).setMaxWidth(80);
        tabela.getColumnModel().getColumn(2).setMinWidth(80);

        tabela.getColumnModel().getColumn(3).setMaxWidth(40);
        tabela.getColumnModel().getColumn(3).setMinWidth(40);

        tabela.getColumnModel().getColumn(4).setMaxWidth(60);
        tabela.getColumnModel().getColumn(4).setMinWidth(60);

        tabela.getColumnModel().getColumn(5).setMaxWidth(50);
        tabela.getColumnModel().getColumn(5).setMinWidth(50);
        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Cliente> dados_cliente = MiscDAO.search_cliente_por_nome(username, password, busca_cliente.getText());
        for (int i = 0; i < dados_cliente.size(); i++) {
            c = dados_cliente.get(i);
            try {
                try {
                    t = MiscDAO.search_telefone_individual_por_id(username, password, c.getId());
                    
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            } catch (Exception e) {
                t.setDdd("");
                t.setAntesh("");
                t.setDepoish("");
                System.out.println(e);
            }
            modelo_tabela.addRow(new Object[]{c.getId(), c.getNome(), c.getData_nascimento(),
                t.getDdd(), t.getAntesh(), t.getDepoish()});
        }
    }

}
