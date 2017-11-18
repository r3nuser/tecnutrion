
package gui;

import javax.swing.JFrame;

public class Editar_clientes extends JFrame{

	private String username;
	private String password;

	private JLabel cliente_cod_l;
	private JLabel cliente_nome_l;
	private JLabel dt_nasc_l;
	private JLabel ddd_l;
	private JLabel antesh_l;
	private JLabel depoish_l;
	private JLabel tipolog_l;
	private JLabel logradouro_l;
	private JLabel bairro_l;
	private JLabel complemento_l;
	private JLabel cidade_l;
	private JLabel estado_l;
	private JLabel cep_l;

	private JTextField cliente_cod;
	private JTextfield cliente_nome;
	private JTextField dt_nasc;
	private JTextfield ddd;
	private JTextField antesh;
	private JTextField depoish;
	private JTextField tipolog;
	private JTextField logradouro;
	private JTextField bairro;
	private JTextField complemento;
	private JTextField cidade;
	private JTextField estado;
	private JTextField cep;

	public Editar_clientes(String currentusername, String currentpassword){
		this.username = currentusername;
		this.password = currentpassword;

		initAll();
	}

	private void initAll(){
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
