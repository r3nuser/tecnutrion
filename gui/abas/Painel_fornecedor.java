
package gui.abas;

import gui.Cadastrar_cliente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Painel_fornecedor extends JPanel{
    private final String username;
    private final String password;
    
    private JPanel painel_de_botoes;
    private JButton cadastrar_fornecedores;
    private JButton realizar_consulta;
    private JButton editar_dados;
    
    public Painel_fornecedor(String currentusername, String currentpassword){
        this.username= currentusername;
        this.password = currentpassword;
        
        initAll();
    }
    
    private void initAll(){
        setLayout(new BorderLayout());
        inicializa_painel_de_botoes();
        
        setVisible(true);
    }
    
    private void inicializa_painel_de_botoes() {
         painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_fornecedores = new JButton("Cadastrar Novo Fornecedor", new ImageIcon(getClass().getResource("ico_mais.png")));
        realizar_consulta = new JButton("Consultar Fornecedor", new ImageIcon(getClass().getResource("ico_lupa.png")));
        editar_dados = new JButton("Editar Dados do Fornecedor", new ImageIcon(getClass().getResource("ico_editar.png")));
        painel_de_botoes.add(cadastrar_fornecedores, BorderLayout.LINE_START);
        painel_de_botoes.add(realizar_consulta, BorderLayout.CENTER);
        painel_de_botoes.add(editar_dados, BorderLayout.LINE_END);

        add(painel_de_botoes, BorderLayout.PAGE_START);

        realizar_consulta.addActionListener((ActionEvent) -> {

            atualizar_tabela();
        });
        cadastrar_fornecedores.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(this.username, this.password);
        });
    }
    
    private void atualizar_tabela(){
        
    }
}
