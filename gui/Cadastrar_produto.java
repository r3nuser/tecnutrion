package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Cadastrar_produto extends JFrame {

    private final String currentusername;
    private final String currentpassword;

    private JLabel titulo_l;

    private JButton cadastrar;
    
    private JLabel produto_nome_l;
    private JLabel fornecedor_l;
    private JLabel preco_uni_l;
    private JLabel peso_l;
    private JLabel descricao_l;
    private JLabel uni_compradas_l;
    private JLabel categoria_l;
    private JLabel unidade_medida_l;

    private JTextField produto_nome;
    private JTextField fornecedor;
    private JTextField preco_uni;
    private JTextField peso;
    private JTextField descricao;
    private JTextField uni_compradas;

    private JComboBox categoria;
    private JComboBox unidade_medida;

    public Cadastrar_produto(String u, String p) {
        this.currentusername = u;
        this.currentpassword = p;
        initComponents();
    }

    private void initComponents() {
        titulo_l = new JLabel("CADASTRO DE PRODUTO");
        unidade_medida_l = new JLabel("Unidade de Medida:");
        produto_nome_l = new JLabel("Nome do Produto:");
        fornecedor_l = new JLabel("Fornecedor:");
        preco_uni_l = new JLabel("Preço Unitário:");
        peso_l = new JLabel("Peso:");
        descricao_l = new JLabel("Descrição:");
        uni_compradas_l = new JLabel("Unidades Compradas:");
        categoria_l = new JLabel("Categoria:");
        produto_nome = new JTextField();
        fornecedor = new JTextField();
        preco_uni = new JTextField();
        peso = new JTextField();
        descricao = new JTextField();
        uni_compradas = new JTextField();
        categoria = new JComboBox();
        unidade_medida = new JComboBox();
        cadastrar = new JButton("Cadastrar !");

        setSize(555, 600);
        setLocationRelativeTo(null);

        add(titulo_l);
        add(produto_nome_l);
        add(fornecedor_l);
        add(preco_uni_l);
        add(peso_l);
        add(descricao_l);
        add(uni_compradas_l);
        add(categoria_l);
        add(unidade_medida_l);
        add(produto_nome);
        add(fornecedor);
        add(preco_uni);
        add(peso);
        add(descricao);
        add(uni_compradas);
        add(categoria);
        add(unidade_medida);
        add(cadastrar);
        

        titulo_l.setBounds(148, 40, 400, 30);
        titulo_l.setFont(new java.awt.Font("Arial", 1, 19));

        produto_nome_l.setBounds(30, 100, 200, 30);
        produto_nome_l.setFont(new java.awt.Font("Arial", 1, 13));

        produto_nome.setBounds(30, 130, 200, 30);

        fornecedor_l.setBounds(30, 160, 200, 30);
        fornecedor_l.setFont(new java.awt.Font("Arial", 1, 13));

        fornecedor.setBounds(30, 190, 200, 30);

        preco_uni_l.setBounds(30, 220, 200, 30);
        preco_uni_l.setFont(new java.awt.Font("Arial", 1, 13));

        preco_uni.setBounds(30, 250, 200, 30);

        categoria_l.setBounds(30, 280, 200, 30);
        categoria_l.setFont(new java.awt.Font("Arial", 1, 13));

        categoria.setBounds(30, 310, 200, 30);

        categoria.addItem("Proteina");
        categoria.addItem("Multivitaminico");
        categoria.addItem("Acessórios");
        categoria.addItem("Pro-Hormonal");
        categoria.addItem("Hipercalorico");
        categoria.addItem("Termogenico");
        categoria.addItem("Anti-oxidante");
        categoria.addItem("Repositor energético");
        categoria.addItem("Repositor eletrolítico");
        categoria.addItem("Aminoacidos");
        categoria.addItem("Outros");
        
        uni_compradas_l.setBounds(30,340,200,30);
        uni_compradas_l.setFont(new java.awt.Font("Arial", 1, 13));

        uni_compradas.setBounds(30,370,200,30);
        
        peso_l.setBounds(300,220,200,30);
        peso_l.setFont(new java.awt.Font("Arial", 1, 13));
        
        peso.setBounds(300,250,120,30);
        
        unidade_medida_l.setBounds(300,160,200,30);
        unidade_medida_l.setFont(new java.awt.Font("Arial", 1, 13));
        unidade_medida.setBounds(300,190,130,30);
        
        unidade_medida.addItem("Kg");
        unidade_medida.addItem("g");
        unidade_medida.addItem("ml");
        unidade_medida.addItem("L");
        
        unidade_medida.addActionListener((ActionEvent)->{
            if(unidade_medida.getSelectedItem()=="Kg" || unidade_medida.getSelectedItem()=="g"){
                peso_l.setText("Peso:");
            }else{
                peso_l.setText("Volume:");
            }
        });
        
        descricao_l.setBounds(300,280,150,30);
        descricao_l.setFont(new java.awt.Font("Arial", 1, 13));
        
        descricao.setBounds(300,310,200,30);
        
        cadastrar.setBounds(215,450,100,30);
        
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
