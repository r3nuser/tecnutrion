
package gui;

import javax.swing.JFrame;

public class Editar_produto extends JFrame{

	private String username;
	private String password;

	private JLabel produto_foto_l;
	private JLabel produto_cod_l;
	private JLabel produto_nome;
	private JLabel nome_fornecedor_l;
	private JLabel preco_uni_compra_l;
	private JLabel preco_uni_venda_l;
	private JLabel categoria_l;
	private JLabel descricao_produto_l;
	private JLabel unidade_medida_peso_l;
	private JLabel peso_produto_l;
	private JLabel qnt_estoque_l;
	private JLabel validade_l;

	private Jlabel produto_foto;
	private JTextField produto_cod;
	private JTextField produto_nome;
	private JTextField nome_fornecedor;
	private JTextField preco_uni_compra;
	private JTextField preco_uni_venda;
	private JTextField categoria;
	private JTextField descricao_produto;
	private JTextField unidade_medida_peso;
	private JTextField peso_produto;
	private JTextfield qnt_estoque;
	private JTextField validade;

	public Editar_produto(String currentusername, String currentpassword){
		this.username=currentusername;
		this.password=currentpassword;

		initAll();
	}

	private void initAll(){

	}
        private void initAll(){
                setLayout(null);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setVisible(true);
        }


}
