package gui;

import bean.Produto;
import dao.MiscDAO;
import static java.awt.event.KeyEvent.VK_ENTER;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Buscar_produto_cdb extends JFrame {

    private final String username;
    private final String password;

    private final DefaultTableModel dmt;

    private final JTextField vl_tot;
    private final JTextField vl_liq;
    private final JTextField vl_qnt;

    private JLabel cod_barras_l;
    private JTextField cod_ghost;

    public Buscar_produto_cdb(String currentusername,
            String currentpassword,
            DefaultTableModel dmt,
            JTextField vl_tot,
            JTextField vl_liq,
            JTextField vl_qnt) {
        this.username = currentusername;
        this.password = currentpassword;
        this.dmt = dmt;
        this.vl_tot = vl_tot;
        this.vl_liq = vl_liq;
        this.vl_qnt = vl_qnt;
        initAll();
    }

    private void initAll() {
        cod_barras_l = new JLabel("Por favor, realize a leitura do código de barras...");
        cod_ghost = new JTextField();
        add(cod_barras_l);
        add(cod_ghost);

        cod_barras_l.setFont(new java.awt.Font("Arial", 1, 20));
        cod_barras_l.setBounds(10, 80, 500, 18);

        cod_ghost.requestFocus();

        setSize(500, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Leitura de Cod. Barras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cod_ghost.addKeyListener(new java.awt.event.KeyListener() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == VK_ENTER) {
                    leitura(cod_ghost.getText());
                    dispose();
                }
            }

            public void keyReleased(java.awt.event.KeyEvent e) {
            }

            public void keyTyped(java.awt.event.KeyEvent e) {
            }
        });

        setVisible(true);
    }

    private void leitura(String cdb) {
        Produto p = MiscDAO.search_produto_por_cdb(username, password, cdb);
        if (p != null) {
            boolean already = false;
            int j;
            for (j = 0; j < dmt.getRowCount(); j++) {
                if (p.getProduto_cod() == (int) dmt.getValueAt(j, 1)) {
                    already = true;
                    break;
                }
            }
            if (already) {
                dmt.setValueAt(Integer.parseInt(dmt.getValueAt(j, 5) + "") + 1, j, 5);
            } else {
                dmt.addRow(new Object[]{p.getProduto_foto_para_tabela(),
                    p.getProduto_cod(), p.getProduto_nome(), p.getPreco_uni_compra(),
                    p.getPreco_uni_venda(), 1});
            }
            float sum;
            sum = p.getPreco_uni_venda();
            sum *= 1;
            sum += Float.parseFloat(vl_tot.getText());
            vl_tot.setText("" + sum);

            sum = p.getPreco_uni_venda() - p.getPreco_uni_compra();
            sum *= 1;
            sum += Float.parseFloat(vl_liq.getText());
            vl_liq.setText("" + sum);

            sum = 1;
            sum += Float.parseFloat(vl_qnt.getText());
            vl_qnt.setText("" + sum);

            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado na base de dados");
        }
    }
}
