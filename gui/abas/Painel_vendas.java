package gui.abas;

import gui.Realizar_troca;
import gui.Realizar_venda;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Painel_vendas extends JPanel{
    private String username;
    private String password;
    
    private JPanel painel_de_dados;
    
    private JPanel painel_da_tabela;
    
    private JPanel painel_de_botoes;
    private JButton realizar_venda;
    private JButton realizar_consulta;
    private JButton realizar_troca;
    
    public Painel_vendas(String currentusername, String currentpassword){
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }
    
    private void initAll(){
        inicializa_painel_de_dados();
        inicializa_painel_da_tabela();
        inicializa_painel_de_botoes();
    }
    
    private void inicializa_painel_de_dados(){
    }
    private void inicializa_painel_da_tabela(){
    }
    private void inicializa_painel_de_botoes(){
        painel_de_botoes = new JPanel(new FlowLayout());
        realizar_venda = new JButton("Realizar Venda", new ImageIcon(getClass().getResource("ico_money.png")));
        realizar_troca = new JButton("Realizar Troca", new ImageIcon(getClass().getResource("ico_troca.png")));
        realizar_consulta = new JButton("Consultar Vendas", new ImageIcon(getClass().getResource("ico_lupa.png")));
        painel_de_botoes.add(realizar_venda);
        painel_de_botoes.add(realizar_consulta);
        painel_de_botoes.add(realizar_troca);
        
        realizar_venda.addActionListener((ActionEvent)->{
            new Realizar_venda(this.username,this.password);
        });
        realizar_troca.addActionListener((ActionEvent)->{
            new Realizar_troca(this.username,this.password);
        });
        realizar_consulta.addActionListener((ActionEvent)->{
            atualizar_tabela();
        });
        
        add(painel_de_botoes,BorderLayout.PAGE_START);
        
    }
    
    private void atualizar_caixas_de_texto(){}
    private void atualizar_tabela(){}
}
