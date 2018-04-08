/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.abas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import services.EmailFactory;
import services.SmsFactory;

/**
 *
 * @author renan
 */
public class Painel_mensagem extends JPanel {

    private String currentusername;
    private String currentpassword;

    private JPanel topnav;
    private JPanel bottomnav;
    private JPanel leftnav;
    private JPanel rightnav;
    private JPanel center;

    //top nav items
    private JButton clear;
    private JButton config;
    private JButton att_data;
    //bottom nav items
    private JButton send_email;
    private JButton send_sms;
    //left nav items

    //right nav items
    //center items
    private boolean assunto_click;

    private JPanel informativo;
    private JLabel qnt_sms;
    private JLabel qnt_cad_cel;
    private JLabel qnt_email_cad;

    private JTextArea corpo;
    private JTextField assunto;

    public Painel_mensagem(String u, String p) {
        this.currentusername = u;
        this.currentpassword = p;
        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());

        initTopnav();
        initBottomnav();
        initLeftnav();
        initRightnav();
        initCenter();

        center.setBorder(BorderFactory.createLineBorder(Color.black));

        aparencia();

        add(topnav, BorderLayout.PAGE_START);
        add(bottomnav, BorderLayout.PAGE_END);
        add(leftnav, BorderLayout.LINE_START);
        add(rightnav, BorderLayout.LINE_END);
        add(center, BorderLayout.CENTER);

        setBackground(Color.black);
    }

    private void aparencia() {
        send_email.setBackground(Color.white);
        send_sms.setBackground(Color.white);
        clear.setBackground(Color.white);
        config.setBackground(Color.white);
        att_data.setBackground(Color.white);

        assunto.setForeground(Color.gray);
        topnav.setBackground(Color.black);
        bottomnav.setBackground(Color.black);
        //leftnav.setBackground(Color.black);
        //rightnav.setBackground(Color.black);
    }

    private void initTopnav() {
        topnav = new JPanel(new FlowLayout());
        clear = new JButton("Limpar Campos");
        config = new JButton("Configurações");
        att_data = new JButton("Atualizar Dados");

        clear.addActionListener((ActionEvent) -> {
            limpar_tudo();
        });

        att_data.addActionListener((ActionEvent) -> {
            atualizar_dados();
        });

        topnav.add(clear);
        topnav.add(config);
        topnav.add(att_data);
    }

    private void initBottomnav() {
        bottomnav = new JPanel(new FlowLayout());
        send_email = new JButton("Enviar Email");
        send_sms = new JButton("Enviar SMS");

        send_sms.addActionListener((ActionEvent) -> {
            SmsFactory sms = new SmsFactory(this.currentusername, this.currentpassword);
            if ("".equals(corpo.getText())) {
                JOptionPane.showMessageDialog(null, "Corpo da mensagem vazio, por favor escreva uma mensagem no campo de texto acima do botão.");
            } else {
                sms.SendSMS(corpo.getText());
            }
        });

        send_email.addActionListener((ActionEvent) -> {
            EmailFactory email = new EmailFactory(this.currentusername, this.currentpassword);
            if ("".equals(corpo.getText())) {
                JOptionPane.showMessageDialog(null, "Corpo da mensagem vazio, por favor escreva uma mensagem no campo de texto acima do botão.");
            } else if ("".equals(assunto.getText()) || "Assunto: ".equals(assunto.getText())) {
                JOptionPane.showMessageDialog(null, "Assunto da mensagem vazio, por favor escreva uma mensagem no campo de texto acima do botão.");
            } else {
                email.SendEmail(assunto.getText(),corpo.getText());
            }

        });

        bottomnav.add(send_email);
        bottomnav.add(send_sms);
    }

    private void initLeftnav() {
        leftnav = new JPanel(new FlowLayout());

    }

    private void initRightnav() {
        rightnav = new JPanel(new FlowLayout());

    }

    private void limpar_tudo() {
        corpo.setText("");
        assunto.setText("");
    }

    private void atualizar_dados() {
        SmsFactory smsfac = new SmsFactory(this.currentusername, this.currentpassword);
        qnt_sms.setText("Quantidade de SMS restante: " + smsfac.checkCredits());
    }

    private void initCenter() {
        center = new JPanel(new BorderLayout());
        //center.add(new JButton(new ImageIcon(getClass().getResource("construcao.png"))));
        qnt_sms = new JLabel("Quantidade de SMS restante:");
        qnt_cad_cel = new JLabel("Quantidade de Nº de Celulares cadastrados:");
        qnt_email_cad = new JLabel("Quantidade de E-mails cadastrados:");

        corpo = new JTextArea();
        assunto = new JTextField("Assunto: ");

        corpo.setLineWrap(true);

        assunto.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent m) {
                if (!assunto_click) {
                    assunto.setText("");
                    assunto_click = true;
                    assunto.setForeground(Color.black);
                }
            }
        });

        assunto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt) {
                if ("".equals(assunto.getText())) {
                    assunto.setText("Assunto: ");
                    assunto_click = false;
                    assunto.setForeground(Color.gray);
                }
            }

        });

        assunto.setBorder(BorderFactory.createLineBorder(Color.black));

        assunto.setFont(new Font("Dialog", 1, 14));

        informativo = new JPanel(new BorderLayout());
        informativo.setPreferredSize(new Dimension(300, 50));
        informativo.add(qnt_sms, BorderLayout.PAGE_START);
        informativo.add(qnt_cad_cel, BorderLayout.CENTER);
        informativo.add(qnt_email_cad, BorderLayout.PAGE_END);
        informativo.setBorder(BorderFactory.createLineBorder(Color.black));

        center.add(assunto, BorderLayout.PAGE_START);
        center.add(corpo, BorderLayout.CENTER);
        center.add(informativo, BorderLayout.PAGE_END);

    }

}
