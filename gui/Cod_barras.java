package gui;

import static java.awt.event.KeyEvent.VK_ENTER;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Cod_barras extends JFrame {

    private JLabel cod_barras_l;
    private JTextField cod_ghost;
    private final JTextField cad_cod_barras;

    public Cod_barras(JTextField cad_cod_barras) {
        this.cad_cod_barras = cad_cod_barras;
        initAll();
    }

    private void initAll() {
        cod_barras_l = new JLabel("Por favor, realize a leitura do código de barras...");
        cod_ghost = new JTextField();
        add(cod_ghost);
        add(cod_barras_l);
        
        cod_barras_l.setFont(new java.awt.Font("Arial", 1, 20));
        cod_barras_l.setBounds(10, 80, 500, 18);

        cod_ghost.requestFocus();

        setSize(500, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Leitura de Cod. Barras");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cod_ghost.addKeyListener(new java.awt.event.KeyListener() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                if(e.getKeyCode() == VK_ENTER){
                    cad_cod_barras.setText(cod_ghost.getText());
                    dispose();
                }
            }

            public void keyReleased(java.awt.event.KeyEvent e) {
            }

            public void keyTyped(java.awt.event.KeyEvent e) {
            }
        });

        setVisible(true);
    }
}
