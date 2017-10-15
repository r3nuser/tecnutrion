package gui;

import bean.Fornecedor;
import dao.FornecedorDAO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Cadastrar_fornecedor extends JFrame {
    private final String currentusername;
    private final String currentpassword;
    private javax.swing.JLabel nome_l;
    private javax.swing.JTextField nome;
    private javax.swing.JButton cadastro;
    Cadastrar_fornecedor(String user,String pass){
        initComponents();
        this.currentusername=user;
        this.currentpassword=pass;
    }
    private void initComponents(){
        
        
        nome=new JTextField();
        nome_l=new JLabel("Nome do fornecedor:");
        cadastro=new JButton("Cadastrar !");
        nome.setFont(new java.awt.Font("Dialog", 0, 14));
        nome_l.setFont(new java.awt.Font("Dialog", 1, 16));
        add(nome);
        add(nome_l);
        
        add(cadastro);
        
        nome.setBounds(12, 66, 371, 21);
        nome_l.setBounds(12, 32, 250, 28);
        cadastro.setBounds(140, 90, 120, 25);
        cadastro.addActionListener((ActionEvent)->{
            try{
                Fornecedor f = new Fornecedor();
                f.setNome(nome.getText());
                FornecedorDAO.create(this.currentusername, this.currentpassword, f);
                dispose();
            }catch(Exception e){
                System.out.println(e);
            }
        });
        
        
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(395,150);
        setVisible(true);
    }
    
}
