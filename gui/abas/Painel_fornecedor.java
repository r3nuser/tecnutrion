package gui.abas;

import bean.Fornecedor;
import dao.FornecedorDAO;
import gui.Cadastrar_cliente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Painel_fornecedor extends JPanel {

    private final String username;
    private final String password;

    private JPanel painel_de_botoes;
    private JButton cadastrar_fornecedores;
    private JButton realizar_consulta;
    private JButton editar_dados;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;
    
    private JPanel painel_de_dados;

    public Painel_fornecedor(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;

        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());

        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();
        inicializa_painel_de_dados();

        setVisible(true);
    }

    private void inicializa_painel_de_dados(){
        
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
        modelo_tabela.addColumn("Nome");

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }

    public void atualizar_caixas_de_texto() {

    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Fornecedor> dados_fornecedor = FornecedorDAO.read(this.username, this.password);
        for (int i = 0; i < dados_fornecedor.size(); i++) {
            Fornecedor f = dados_fornecedor.get(i);
            modelo_tabela.addRow(new Object[]{f.getId(), f.getNome()});
        }

        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);
    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_fornecedores = new JButton("Cadastrar Novo Fornecedor", new ImageIcon(getClass().getResource("ico_mais.png")));
        realizar_consulta = new JButton("Consultar Fornecedor", new ImageIcon(getClass().getResource("ico_lupa.png")));
        editar_dados = new JButton("Editar Dados do Fornecedor", new ImageIcon(getClass().getResource("ico_editar.png")));
        painel_de_botoes.add(cadastrar_fornecedores, BorderLayout.LINE_START);
        painel_de_botoes.add(realizar_consulta, BorderLayout.CENTER);
        painel_de_botoes.add(editar_dados, BorderLayout.LINE_END);

        realizar_consulta.addActionListener((ActionEvent)->{
            atualizar_tabela();
        });
        
        add(painel_de_botoes, BorderLayout.PAGE_START);

        realizar_consulta.addActionListener((ActionEvent) -> {

            atualizar_tabela();
        });
        cadastrar_fornecedores.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(this.username, this.password);
        });
    }

}
