package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Buscar_produto_cdb extends JFrame{
    
    private final String username;
    private final String password;
    
    private final DefaultTableModel dmt;
    
    private final JTextField vl_tot;
    private final JTextField vl_liq;
    private final JTextField vl_qnt;
    
    private JTextField cod_ghost;
    private JLabel cdb_msg;

    public Buscar_produto_cdb(String currentusername, 
            String currentpassword,
            DefaultTableModel dmt,
            JTextField vl_tot,
            JTextField vl_liq,
            JTextField vl_qnt){
        this.username = currentusername;
        this.password = currentpassword;
        this.dmt = dmt;
        this.vl_tot = vl_tot;
        this.vl_liq = vl_liq;
        this.vl_qnt = vl_qnt;
        initAll();
    }
    
    private void initAll(){
	cdb_msg = new JLabel("Por favor, realize a leitura do código de barras");
        cod_ghost = new JTextField();

	add(cod_ghost);
	add(cdb_msg);

	setTitle("Leitura de Cod. Barras");
	setSize(400,400);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setVisible(true);
    }
}
