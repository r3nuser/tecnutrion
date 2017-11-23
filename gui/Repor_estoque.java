/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bean.Estoque;
import dao.EstoqueDAO;
import dao.MiscDAO;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author renan
 */
public class Repor_estoque extends JFrame {

    private final String username;
    private final String password;

    private JLabel qnt_atual;
    private JLabel validade_atual;
    
    private JButton repor;
    private JLabel informativo;
    private JTextField qnt_reposta;
    private JTextField validade_reposta;

    private Estoque e;

    public Repor_estoque(String u, String p, int id) {
        e = MiscDAO.search_estoque_por_id(u, p, id);
        this.username = u;
        this.password = p;
        initAll();
    }

    private void initAll() {
        SimpleDateFormat formatdata = new SimpleDateFormat("dd/MM/yyyy");
        qnt_atual = new JLabel("  Quantidade Atual : " + e.getQnt_estoque());
        validade_atual = new JLabel("Validade do lote atual: "+ formatdata.format(e.getValidade()));
        informativo = new JLabel("Insira a quantidade que deseja incrementar ao estoque / validade:");
        qnt_reposta = new JTextField();
        try{
            validade_reposta = new JFormattedTextField(new MaskFormatter("##/##/####"));
            validade_reposta.setBounds(240+20,90,150,24);
            add(validade_reposta);
        }catch(Exception ex){
            System.out.println(ex);
        }
        repor = new JButton("Repor", new ImageIcon(getClass().getResource("abas/ico_mais.png")));
        informativo.setBounds(10, 10, 600, 40);
        qnt_atual.setBounds(10, 60, 200, 18);
        validade_atual.setBounds(10,90,300,18);
        qnt_reposta.setBounds(240+20, 60, 150, 24);
        repor.setBounds(200, 180, 100, 24);

        repor.addActionListener((ActionEvent) -> {
            try {
                e.setQnt_estoque(e.getQnt_estoque() + Integer.parseInt(qnt_reposta.getText()));
                DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    java.sql.Date data = new java.sql.Date(fmt.parse(this.validade_reposta.getText()).getTime());
                    e.setValidade(data);
                } catch (Exception e) {
                    System.out.println(e);
                }
                EstoqueDAO.update(username, password, e);
                JOptionPane.showMessageDialog(null, "Estoque Reposto com Sucesso !");
            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                initAll();
                dispose();
            }
        });

        add(repor);
        add(qnt_reposta);
        add(informativo);
        add(qnt_atual);
        add(validade_atual);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setSize(500, 240);
        setTitle("Repor Estoque");
        setResizable(false);
        URL url = this.getClass().getResource("abas/ico_cadastro.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        setIconImage(iconeTitulo);
        setVisible(true);
    }
}
