package gui.abas;

import bean.Estoque;
import bean.Produto;
import dao.EstoqueDAO;
import dao.MiscDAO;
import gui.Repor_estoque;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Painel_estoque extends JPanel {

    private final String username;
    private final String password;

    private JPanel painel_de_botoes;
    private JButton consultar_estoque;
    private JButton repor_estoque;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_dados;
    private JLabel estoque_cod_l;
    private JLabel produto_nome_l;
    private JLabel produto_foto_l;
    private JLabel produto_cod_l;
    private JLabel validade_l;
    private JLabel quantidade_l;

    private JLabel produto_foto;

    private JTextField estoque_cod;
    private JTextField produto_nome;
    private JTextField produto_cod;
    private JTextField validade;
    private JTextField quantidade;

    public Painel_estoque(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;

        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());
        inicializa_painel_de_dados();
        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();

        setVisible(true);
    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());

        consultar_estoque = new JButton("Consultar Estoque", new ImageIcon(getClass().getResource("ico_lupa.png")));
        repor_estoque = new JButton("Repor Estoque", new ImageIcon(getClass().getResource("ico_mais.png")));
        consultar_estoque.setBackground(new Color(30, 30, 30));
        consultar_estoque.setForeground(new Color(255, 255, 255));
        repor_estoque.setBackground(new Color(30, 30, 30));
        repor_estoque.setForeground(new Color(255, 255, 255));
        repor_estoque.addActionListener((ActionEvent) -> {
            try {
                new Repor_estoque(this.username, this.password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar repor estoque: Escolha um dado na tabela e tente novamente");
            }
        });

        painel_de_botoes.add(repor_estoque);
        painel_de_botoes.add(consultar_estoque);

        consultar_estoque.addActionListener((ActionEvent) -> {
            atualizar_tabela();
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
        modelo_tabela.addColumn("Nome do Produto");
        modelo_tabela.addColumn("Qnt. Estoque");
        modelo_tabela.addColumn("Validade");
        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(2).setMaxWidth(120);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(3).setMaxWidth(150);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);
        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void inicializa_painel_de_dados() {
        painel_de_dados = new JPanel(new FlowLayout());
        painel_de_dados.setPreferredSize(new Dimension(600, 1000));

        estoque_cod_l = new JLabel("ID Estoque:");
        produto_nome_l = new JLabel("Nome do Produto:");
        produto_foto_l = new JLabel("Foto do Produto:");
        produto_foto = new JLabel("");
        produto_cod_l = new JLabel("ID Produto:");
        validade_l = new JLabel("                     Validade:");
        quantidade_l = new JLabel("Quantidade em estoque:");

        estoque_cod = new JTextField();
        produto_nome = new JTextField();
        produto_cod = new JTextField();
        validade = new JTextField();
        quantidade = new JTextField();

        produto_foto.setPreferredSize(new Dimension(100, 100));
        produto_foto.setBorder(BorderFactory.createLineBorder(Color.black));

        estoque_cod.setPreferredSize(new Dimension(150, 18));
        produto_cod.setPreferredSize(new Dimension(150, 18));
        produto_nome.setPreferredSize(new Dimension(400, 18));
        validade.setPreferredSize(new Dimension(120, 18));
        quantidade.setPreferredSize(new Dimension(170, 18));
        
        
        painel_de_dados.add(produto_foto_l);
        painel_de_dados.add(produto_foto);
        painel_de_dados.add(produto_cod_l);
        painel_de_dados.add(produto_cod);

        painel_de_dados.add(produto_nome_l);
        painel_de_dados.add(produto_nome);
        painel_de_dados.add(estoque_cod_l);
        painel_de_dados.add(estoque_cod);
        painel_de_dados.add(validade_l);
        painel_de_dados.add(validade);
        painel_de_dados.add(quantidade_l);
        painel_de_dados.add(quantidade);

        painel_de_dados.setBorder(BorderFactory.createLineBorder(Color.black));
        add(painel_de_dados, BorderLayout.LINE_START);
    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Estoque> dados_estoque;
        dados_estoque = EstoqueDAO.read(username, password);
        for (int i = 0; i < dados_estoque.size(); i++) {
            Estoque e = dados_estoque.get(i);
            Produto p = MiscDAO.get_produto_por_fk_cod_estoque(username, password, e.getEstoque_cod());
            modelo_tabela.addRow(new Object[]{
                e.getEstoque_cod(),
                p.getProduto_nome(),
                e.getQnt_estoque(),
                e.getValidade()
            });
        }
    }

    private void atualizar_caixas_de_texto() {
        Produto p = MiscDAO.get_produto_por_fk_cod_estoque(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        Estoque e = MiscDAO.search_estoque_por_id(username, password, p.getFk_estoque_cod());
        produto_foto.setIcon(p.getProduto_foto_para_tabela());
        estoque_cod.setText("" + e.getEstoque_cod());
        produto_nome.setText(p.getProduto_nome());
        produto_cod.setText("" + p.getProduto_cod());
        validade.setText("" + e.getValidade());
        quantidade.setText("" + e.getQnt_estoque());
    }
}
