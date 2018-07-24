package gui;

import bean.Estoque;
import bean.Produto;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

    private int v_row;

    public Buscar_produto(String currentusername, String currentpassword,
            DefaultTableModel dmt, JTextField vl_tot, JTextField vl_liq, JTextField vl_qnt, int v_row) {
        this.username = currentusername;
        this.password = currentpassword;
        this.dmt = dmt;
        this.vl_tot = vl_tot;
        this.vl_liq = vl_liq;
        this.vl_qnt = vl_qnt;
        this.v_row = v_row;
        initAll();

    }

    private void initAll() {
        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();

        escolher = new JButton("Confirmar Escolha", new ImageIcon(getClass().getResource("abas/ico_confirmar.png")));
        escolher.setBackground(new Color(30, 30, 30));
        escolher.setForeground(new Color(255, 255, 255));
        escolher.addActionListener((ActionEvent) -> {
            try {
                tabela.getValueAt(tabela.getSelectedRow(), 1);
                try {
                    Integer.parseInt(unidades.getText());
                    System.out.println("flag 0");
                    if (((int) tabela.getValueAt(tabela.getSelectedRow(), 5) >= Integer.parseInt(unidades.getText()))
                            && (Integer.parseInt(unidades.getText()) != 0)) {
                        p = MiscDAO.search_produto_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 1));

                        boolean already = false;
                        int j;
                        for (j = 0; j < dmt.getRowCount(); j++) {
                            if (p.getProduto_cod() == (int) dmt.getValueAt(j, 1)) {
                                already = true;
                                break;
                            }
                        }
                        System.out.println("flag 1");
                        if (already) {
                            dmt.setValueAt(Integer.parseInt(dmt.getValueAt(j, v_row) + "") + Integer.parseInt(unidades.getText()), j, v_row);
                        } else {
                            if (v_row == 5) {
                                dmt.addRow(new Object[]{p.getProduto_foto_para_tabela(),
                                    p.getProduto_cod(), p.getProduto_nome(), p.getPreco_uni_compra(),
                                    p.getPreco_uni_venda(), unidades.getText()});
                            } else {
                                dmt.addRow(new Object[]{p.getProduto_foto_para_tabela(),
                                    p.getProduto_cod(), p.getProduto_nome(), unidades.getText()});
                            }
                        }

                        float sum;
                        sum = p.getPreco_uni_venda();
                        sum *= Float.parseFloat(unidades.getText());
                        sum += Float.parseFloat(vl_tot.getText());
                        vl_tot.setText("" + sum);
                        if (v_row == 5) {
                            sum = p.getPreco_uni_venda() - p.getPreco_uni_compra();
                            sum *= Float.parseFloat(unidades.getText());
                            sum += Float.parseFloat(vl_liq.getText());
                            vl_liq.setText("" + sum);

                            sum = Float.parseFloat(unidades.getText());
                            sum += Float.parseFloat(vl_qnt.getText());
                            vl_qnt.setText("" + sum);
                        }
                        dispose();

                    } else {
                        JOptionPane.showMessageDialog(null, "Quantidade invalida: Quantidade maior que a disponível"
                                + " no estoque ou quantidade igual a 0.");
                        unidades.setBorder(BorderFactory.createLineBorder(Color.red));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira apenas numeros inteiros nas unidades.");
                    System.out.println(e);
                    System.out.println("Unidades: " + unidades.getText());
                    unidades.setBorder(BorderFactory.createLineBorder(Color.red));
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor, selecione um produto na tabela e tente novamente !");
                atualizar_tabela();
            }
        });

        add(escolher, BorderLayout.PAGE_END);

        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Selecionar Produto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        URL url = this.getClass().getResource("abas/ico_lupa2.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
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
        cadastrar_produto.setBackground(new Color(30, 30, 30));
        cadastrar_produto.setForeground(new Color(255, 255, 255));
        busca_produto_b.setBackground(new Color(30, 30, 30));
        busca_produto_b.setForeground(new Color(255, 255, 255));
        unidades_l = new JLabel("Unidades que deseja vender:");
        unidades = new JTextField("0");
        unidades.setPreferredSize(new Dimension(70, 24));

        painel_de_botoes.add(unidades_l);
        painel_de_botoes.add(unidades);

        painel_de_botoes.add(cadastrar_produto);

        busca_produto_b.addActionListener((ActionEvent) -> {
            atualizar_tabela();
        });

        cadastrar_produto.addActionListener((ActionEvent) -> {
            new Cadastrar_produto(username, password);
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
        tabela.getColumnModel().getColumn(5).setMaxWidth(150);
        tabela.getColumnModel().getColumn(5).setMinWidth(150);

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

            boolean already = false;
            int j;
            for (j = 0; j < dmt.getRowCount(); j++) {
                if (p.getProduto_cod() == (int) dmt.getValueAt(j, 1)) {
                    already = true;
                    break;
                }
            }
            if (already) {
                modelo_tabela.addRow(new Object[]{
                    p.getProduto_foto_para_tabela(),
                    p.getProduto_cod(),
                    p.getProduto_nome(),
                    p.getPreco_uni_compra(),
                    p.getPreco_uni_venda(),
                    (e.getQnt_estoque() - Integer.parseInt("" + dmt.getValueAt(j, 5)))
                });
            } else {
                modelo_tabela.addRow(new Object[]{p.getProduto_foto_para_tabela(),
                    p.getProduto_cod(), p.getProduto_nome(), p.getPreco_uni_compra(),
                    p.getPreco_uni_venda(), e.getQnt_estoque()});
            }
        }
    }
}
