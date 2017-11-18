
package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Editar_fornecedor extends JFrame{

	private String username;
	private String password;

	private JLabel fornecedor_nome_l;

	private JTextField fornecedor_nome;

	public Editar_fornecedor(String currentusername, String currentpassword){
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
