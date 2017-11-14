package gui;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Estatisticas extends JFrame{
    
    private String username;
    private String password;
    
    private JPanel painel_de_lucros;
    
    private JPanel painel_de_vendas;
    
    private JPanel painel_misc;
    
    public Estatisticas(String currentusername, String currentpassword){
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }
    private void initAll(){
        inicializa_painel_de_lucros();
        inicializa_painel_de_vendas();
        inicializa_painel_misc();
        
        setResizable(false);
        setSize(830,650);
        setLocationRelativeTo(null);
        setTitle("Estatísticas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
    }
    
    private void inicializa_painel_de_lucros(){
        painel_de_lucros = new JPanel(null);
        
        painel_de_lucros.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_de_lucros.setBounds(10,10,800,200);
        add(painel_de_lucros);
    }
    
    private void inicializa_painel_de_vendas(){
        painel_de_vendas = new JPanel(null);
        
       painel_de_vendas.setBorder(BorderFactory.createLineBorder(Color.black));
       painel_de_vendas.setBounds(10,210,800,200);
       add(painel_de_vendas);
    }
    
    private void inicializa_painel_misc(){
        painel_misc = new JPanel(null);
        
        painel_misc.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_misc.setBounds(10,410,800,200);
        add(painel_misc);
    }
}
