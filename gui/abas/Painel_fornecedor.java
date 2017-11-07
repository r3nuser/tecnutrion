package gui.abas;

import bean.Fornecedor;
import dao.FornecedorDAO;
import dao.MiscDAO;
import gui.Cadastrar_fornecedor;
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

public class Painel_fornecedor extends JPanel {

    private final String username;
    private final String password;

    private JPanel painel_de_botoes;
    private JButton cadastrar_fornecedores;
    private JButton realizar_consulta;
    private JButton editar_dados;
    private JButton busca_fornecedor_b;
    private JTextField busca_fornecedor;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_dados;
    private JLabel id_l;
    private JLabel nome_fornecedor_l;

    private JTextField nome_fornecedor;
    private JTextField id;

    private JButton deletar_fornecedor;

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

    private void inicializa_painel_de_dados() {
        painel_de_dados = new JPanel(new FlowLayout());
        id_l = new JLabel("ID:");
        id = new JTextField();
        id.setEditable(false);
        id.setPreferredSize(new Dimension(70, 18));

        nome_fornecedor_l = new JLabel("Nome:");
        nome_fornecedor = new JTextField();
        nome_fornecedor.setPreferredSize(new Dimension(300, 18));
        nome_fornecedor.setEditable(false);

        deletar_fornecedor = new JButton("Deletar Fornecedor", new ImageIcon(getClass().getResource("ico_deletar.png")));

        deletar_fornecedor.addActionListener((ActionEvent) -> {
            Fornecedor f = new Fornecedor();
            f.setId(Integer.parseInt(id.getText()));
            FornecedorDAO.delete(username, password, f);
            atualizar_tabela((byte)0);
        });

        painel_de_dados.add(id_l);
        painel_de_dados.add(id);
        painel_de_dados.add(nome_fornecedor_l);
        painel_de_dados.add(nome_fornecedor);
        painel_de_dados.add(deletar_fornecedor);

        painel_de_dados.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_de_dados.setPreferredSize(new Dimension(600, 1000));

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
        modelo_tabela.addColumn("Nome");

        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }

    public void atualizar_caixas_de_texto() {
        Fornecedor f = MiscDAO.search_fornecedor_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        id.setText("" + f.getId());
        nome_fornecedor.setText(f.getNome());

    }

    private void atualizar_tabela(byte mode) {
        modelo_tabela.setNumRows(0);
        ArrayList<Fornecedor> dados_fornecedor;
        if (mode == 0) {
            dados_fornecedor = FornecedorDAO.read(this.username, this.password);
        } else {
            dados_fornecedor = MiscDAO.search_fornecedor_por_nome(username, password, busca_fornecedor.getText());
        }
        for (int i = 0; i < dados_fornecedor.size(); i++) {
            Fornecedor f = dados_fornecedor.get(i);
            modelo_tabela.addRow(new Object[]{f.getId(), f.getNome()});
        }

    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_fornecedores = new JButton("Cadastrar Novo Fornecedor", new ImageIcon(getClass().getResource("ico_mais.png")));
        realizar_consulta = new JButton("Consultar Fornecedor", new ImageIcon(getClass().getResource("ico_lupa.png")));
        editar_dados = new JButton("Editar Dados do Fornecedor", new ImageIcon(getClass().getResource("ico_editar.png")));
        
        busca_fornecedor_b = new JButton(new ImageIcon(getClass().getResource("ico_lupa2.png")));
        busca_fornecedor = new JTextField();
        busca_fornecedor.setPreferredSize(new Dimension(220,24));
        
        painel_de_botoes.add(cadastrar_fornecedores);
        painel_de_botoes.add(realizar_consulta);
        painel_de_botoes.add(editar_dados);
        painel_de_botoes.add(busca_fornecedor);
        painel_de_botoes.add(busca_fornecedor_b);

        realizar_consulta.addActionListener((ActionEvent) -> {
            atualizar_tabela((byte)0);
        });

        add(painel_de_botoes, BorderLayout.PAGE_START);

        cadastrar_fornecedores.addActionListener((ActionEvent) -> {
            new Cadastrar_fornecedor(this.username, this.password);
        });
        
        busca_fornecedor_b.addActionListener((ActionEvent)->{
           atualizar_tabela((byte)1); 
        });
    }

}
