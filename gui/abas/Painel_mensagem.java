/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.abas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

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

    public Painel_mensagem(String u, String p) {
        this.currentusername = u;
        this.currentpassword = p;
        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());

        topnav = new JPanel();
        bottomnav = new JPanel();
        leftnav = new JPanel();
        rightnav = new JPanel();
        center = new JPanel();
        center.setBorder(BorderFactory.createLineBorder(Color.black));

        add(topnav, BorderLayout.PAGE_START);
        add(bottomnav, BorderLayout.PAGE_END);
        add(leftnav, BorderLayout.LINE_START);
        add(rightnav, BorderLayout.LINE_END);
        add(center, BorderLayout.CENTER);

        initTopnav();
        initBottomnav();
        initLeftnav();
        initRightnav();
        initCenter();
    }

    private void initTopnav() {
        
    }

    private void initBottomnav() {
    }

    private void initLeftnav() {
    }

    private void initRightnav() {
    }

    private void initCenter() {
        center.setLayout(new BorderLayout());
        center.add(new JButton(new ImageIcon(getClass().getResource("construcao.png"))));
    }
}
