/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.abas;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author renan
 */
public class Painel_mensagem extends JPanel{
    
    private String currentusername;
    private String currentpassword;
    
    
    
    public Painel_mensagem(String u, String p){
        this.currentusername = u;
        this.currentpassword = p;
        
        setLayout(new BorderLayout());
    }
}
