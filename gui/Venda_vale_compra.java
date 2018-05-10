/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bean.Estoque;
import bean.Produto;
import dao.EstoqueDAO;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

/**
 *
 * @author renan
 */
public class Venda_vale_compra extends JFrame {

    private final String username;
    private final String password;
    private final int id;

    private JPanel produtos_escolhidos;

    private JPanel informativo;

    private JLabel valor_vale_l;
    private JTextField valor_vale;
    private JButton adicionar_produto;
    private JButton remover_produto;

    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll_tabela;

    private JButton finalizar;

    public Venda_vale_compra(String username, String password, int id) {
        JOptionPane.showMessageDialog(null, "O cliente poderá escolher os produtos até o preço total ser 100 reais");
        this.username = username;
        this.password = password;
        this.id = id;
        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());
        initInformativo();
        initProdutosEscolhidos();

        finalizar = new JButton("Finalizar Venda", new ImageIcon(getClass().getResource("abas/ico_money.png")));

        add(finalizar, BorderLayout.PAGE_END);

        finalizar.addActionListener((ActionEvent) -> {
            if (Float.parseFloat(valor_vale.getText()) <= 100) {
                for (int i = 0; i < modelo_tabela.getRowCount(); i++) {
                    Produto pro = MiscDAO.search_produto_por_id(username, password,
                            Integer.parseInt("" + tabela.getValueAt(i, 1)));
                    Estoque est = MiscDAO.search_estoque_por_id(username, password,
                            pro.getFk_estoque_cod());
                    est.setQnt_estoque(est.getQnt_estoque() - Integer.parseInt(tabela.getValueAt(i, 3) + ""));
                    EstoqueDAO.update(username, password, est);
                }
                JOptionPane.showMessageDialog(null, "Venda realizada com Sucesso !");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "O valor total da compra não pode exceder R$ 100,00");
            }
        });

        setSize(1024, 700);
        setTitle("Vale compra do cliente" + MiscDAO.search_cliente_por_id(username, password, id) + " ID=" + id);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initInformativo() {
        informativo = new JPanel(new FlowLayout());
        informativo.setBorder(BorderFactory.createLineBorder(Color.black));
        valor_vale_l = new JLabel("Valor dos Produtos Escolhidos:");
        valor_vale = new JTextField("0");
        valor_vale.setEditable(false);
        adicionar_produto = new JButton("Adicionar Produto", new ImageIcon(getClass().getResource("abas/ico_mais.png")));
        remover_produto = new JButton("Remover Produto", new ImageIcon(getClass().getResource("abas/ico_deletar.png")));
        adicionar_produto.setBackground(Color.black);
        adicionar_produto.setForeground(Color.white);
        remover_produto.setBackground(Color.black);
        remover_produto.setForeground(Color.white);
        valor_vale.setForeground(Color.black);

        adicionar_produto.addActionListener((ActionEvent) -> {
            JTextField a = new JTextField();
            a.setText("0");
            new Buscar_produto(this.username, this.password, this.modelo_tabela, valor_vale, a, a, 3);
        });

        remover_produto.addActionListener((ActionEvent) -> {
            float subt;

            Produto p = MiscDAO.search_produto_por_id(username, password,
                    Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 1) + ""));

            subt = p.getPreco_uni_venda()
                    * Float.parseFloat(tabela.getValueAt(tabela.getSelectedRow(), 3) + "");

            valor_vale.setText("" + (Float.parseFloat(valor_vale.getText()) - subt));

            modelo_tabela.removeRow(tabela.getSelectedRow());
        });

        informativo.add(valor_vale_l);
        informativo.add(valor_vale);
        informativo.add(adicionar_produto);
        informativo.add(remover_produto);
        valor_vale.setPreferredSize(new Dimension(240, 24));
        add(informativo, BorderLayout.PAGE_START);
    }

    private void initProdutosEscolhidos() {
        produtos_escolhidos = new JPanel(new GridLayout());

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
        modelo_tabela.addColumn("Quantidade");

        tabela.setRowHeight(100);

        tabela.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela.getColumnModel().getColumn(0).setMinWidth(100);
        tabela.getColumnModel().getColumn(1).setMaxWidth(70);
        tabela.getColumnModel().getColumn(1).setMinWidth(70);
        tabela.getColumnModel().getColumn(3).setMaxWidth(90);
        tabela.getColumnModel().getColumn(3).setMinWidth(90);

        scroll_tabela = new JScrollPane(tabela);
        scroll_tabela.setSize(1024, 768);

        produtos_escolhidos.add(scroll_tabela);
        produtos_escolhidos.setBorder(BorderFactory.createLineBorder(Color.black));

        add(produtos_escolhidos, BorderLayout.CENTER);
    }
}
