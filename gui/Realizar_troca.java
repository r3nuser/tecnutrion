package gui;

import bean.Estoque;
import bean.Pedido;
import bean.Produto;
import dao.EstoqueDAO;
import dao.MiscDAO;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Realizar_troca extends JFrame {

    private final String username;
    private final String password;

    private JButton adicionar_troca;
    private JButton remover_troca;
    private JButton realizar_troca;

    private JTable tabela_atual;
    private DefaultTableModel dtm_atual;
    private JScrollPane scroll_atual;

    private JTable tabela_troca;
    private DefaultTableModel dtm_troca;
    private JScrollPane scroll_troca;

    private JLabel informativo;

    private Pedido p;

    public Realizar_troca(String currentusername, String currentpassword, int id) {
        this.p = MiscDAO.search_pedido_por_id(currentusername, currentpassword, id);

        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }

    private void initAll() {
        inicializa_tabela_atual();
        inicializa_tabela_troca();

        informativo = new JLabel("OBS:: Ao passar produtos para tabela da direita, o produto em questão terá sua quantidade no estoque decrementada de acordo com a quantidade.");
        adicionar_troca = new JButton(">>>");
        adicionar_troca.addActionListener((ActionEvent) -> {
            try {
                Boolean already = false;
                int i;
                for (i = 0; i < dtm_troca.getRowCount(); i++) {
                    if ((int) tabela_troca.getValueAt(i, 0) == (int) tabela_atual.getValueAt(tabela_atual.getSelectedRow(), 0)) {
                        already = true;
                        break;
                    }
                }
                if (already) {
                    tabela_troca.setValueAt(
                            (int) tabela_troca.getValueAt(i, 3) + 1,
                            i,
                            3
                    );
                } else {
                    Produto pro = MiscDAO.search_produto_por_id(username, password, (int) tabela_atual.getValueAt(tabela_atual.getSelectedRow(), 0));
                    dtm_troca.addRow(new Object[]{pro.getProduto_cod(), pro.getProduto_nome(), pro.getPreco_uni_venda(), 1});
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor, escolha um produto na tabela à esquerda e tente novamente");
            }
        });

        remover_troca = new JButton("<<<");
        remover_troca.addActionListener((ActionEvent) -> {
            try {
                if ((int) tabela_troca.getValueAt(tabela_troca.getSelectedRow(), 3) > 1) {
                    tabela_troca.setValueAt(
                            (int) tabela_troca.getValueAt(tabela_troca.getSelectedRow(), 3) - 1,
                            tabela_troca.getSelectedRow(),
                            3);
                } else {
                    dtm_troca.removeRow(tabela_troca.getSelectedRow());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor, escolha um produto na tabela à direita e tente novamente");
            }
        });

        realizar_troca = new JButton("Trocar", new ImageIcon(getClass().getResource("abas/ico_troca.png")));

        realizar_troca.addActionListener((ActionEvent) -> {
            for (int i = 0; i < tabela_troca.getRowCount(); i++) {
                Produto pro = MiscDAO.search_produto_por_id(username, password, (int) tabela_troca.getValueAt(i, 0));
                Estoque est = MiscDAO.search_estoque_por_id(username, password, pro.getFk_estoque_cod());
                est.setQnt_estoque(est.getQnt_estoque() - (int) tabela_troca.getValueAt(i, 3));
                EstoqueDAO.update(username, password, est);
            }
            JOptionPane.showMessageDialog(null, "Troca Realizada com Sucesso !");
            dispose();
        });

        informativo.setBounds(10, 420, 1100, 50);
        informativo.setForeground(new Color(120, 0, 0));

        adicionar_troca.setBounds(500, 100, 100, 24);
        remover_troca.setBounds(500, 140, 100, 24);
        realizar_troca.setBounds(475, 300, 150, 24);

        add(informativo);
        add(adicionar_troca);
        add(remover_troca);
        add(realizar_troca);

        setLayout(null);
        setSize(1100, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void inicializa_tabela_atual() {
        dtm_atual = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        dtm_atual.addColumn("ID");
        dtm_atual.addColumn("Nome do Produto");
        dtm_atual.addColumn("Preco uni. Venda");
        dtm_atual.addColumn("Qnt");
        tabela_atual = new JTable(dtm_atual);

        scroll_atual = new JScrollPane(tabela_atual);
        scroll_atual.setBounds(10, 10, 400, 400);
        scroll_atual.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Produtos vendidos", 1, 1, new java.awt.Font("Dialog", 1, 14)));

        for (Produto p : MiscDAO.get_produtos_contidos_pedido(username, password, p.getCod_pedido())) {
            dtm_atual.addRow(new Object[]{
                p.getProduto_cod(),
                p.getProduto_nome(),
                p.getPreco_uni_venda(),
                p.getFk_fornecedor_cod()
            });
        }

        setTitle("Realizar Troca do Pedido ID:" + p.getCod_pedido());
        add(scroll_atual);
    }

    private void inicializa_tabela_troca() {
        dtm_troca = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        dtm_troca.addColumn("ID");
        dtm_troca.addColumn("Nome do Produto");
        dtm_troca.addColumn("Preco uni. Venda");
        dtm_troca.addColumn("Qnt");
        tabela_troca = new JTable(dtm_troca);

        scroll_troca = new JScrollPane(tabela_troca);
        scroll_troca.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Produtos que serão trocados", 1, 1, new java.awt.Font("Dialog", 1, 14)));
        scroll_troca.setBounds(740 - 55, 10, 400, 400);
        add(scroll_troca);
    }
}
