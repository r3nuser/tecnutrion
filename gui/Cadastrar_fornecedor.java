package gui;

import bean.Fornecedor;
import dao.FornecedorDAO;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Cadastrar_fornecedor extends JFrame {

    private final String currentusername;
    private final String currentpassword;
    private javax.swing.JLabel nome_l;
    private javax.swing.JTextField nome;
    private javax.swing.JButton cadastro;

    public Cadastrar_fornecedor(String user, String pass) {
        initComponents();
        this.currentusername = user;
        this.currentpassword = pass;
    }

    private void initComponents() {

        nome = new JTextField();
        nome_l = new JLabel("Nome do fornecedor:");
        cadastro = new JButton("Cadastrar !");
        nome.setFont(new java.awt.Font("Dialog", 0, 14));
        nome_l.setFont(new java.awt.Font("Dialog", 1, 16));
        add(nome);
        add(nome_l);

        add(cadastro);

        nome.setBounds(12, 66, 371, 21);
        nome_l.setBounds(12, 32, 250, 28);
        cadastro.setBounds(140, 90, 120, 25);
        cadastro.setBackground(new Color(30,30,30));
        cadastro.setForeground(new Color(255,255,255));
        cadastro.addActionListener((ActionEvent) -> {

            if (validacao()) {
                try {
                    Fornecedor f = new Fornecedor();
                    f.setNome(nome.getText());
                    FornecedorDAO.create(this.currentusername, this.currentpassword, f);

                } catch (Exception e) {
                    System.out.println(e);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Campo obrigatório não preenchido: Nome do fornecedor.");
            }

        });

        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de fornecedor !");
        setSize(395, 150);
        URL url = this.getClass().getResource("abas/ico_cadastro.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);
        setVisible(true);
    }

    private boolean validacao() {
        boolean validado = true;
        if ("".equals(nome.getText())) {
            nome.setBorder(BorderFactory.createLineBorder(Color.red));
            validado = false;
        } else {
            nome.setBorder(BorderFactory.createLineBorder(Color.green));
        }
        return validado;
    }

}
