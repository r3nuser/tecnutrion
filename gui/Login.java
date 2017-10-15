package gui;


//CLASSE RESPONSÁVEL POR EFETUAR O LOGIN.

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;

import java.sql.Connection;
import javax.swing.JPasswordField;
import sql.Sql;
import static sql.Sql.closeConnection;


public class Login extends JFrame {
    //TODOS COMPONENTES DA CLASSE.

    private JButton signin;
    private JLabel bg_fundo;
    private JLabel bg_user;
    private JLabel bg_log;
    private JLabel bg_pas;
    private JTextField login;
    private JPasswordField password;
    //CHAMADA DA CLASSE.

    public Login() {
        initComponents();
    }
    //METODO INICIALIZADOR DE COMPONENTES.

    private void initComponents() {
        signin = new JButton("Logar");
        bg_fundo = new JLabel("");
        bg_user = new JLabel("");
        bg_log = new JLabel("");
        bg_pas = new JLabel("");
        login = new JTextField("Login");
        password = new JPasswordField("Password");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 390);
        setVisible(true);
        
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().add(signin);
        signin.setBounds(126, 335, 80, 25);

        getContentPane().add(login);
        login.setBounds(80, 230, 170, 39);
        getContentPane().add(password);
        password.setBounds(80, 280, 170, 39);

        bg_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("user.png")));
        getContentPane().add(bg_user);
        bg_user.setBounds(100, 60, 130, 140);

        bg_log.setIcon(new javax.swing.ImageIcon(getClass().getResource("login.png")));
        getContentPane().add(bg_log);
        bg_log.setBounds(50, 230, 30, 30);

        bg_pas.setIcon(new javax.swing.ImageIcon(getClass().getResource("password.png")));
        getContentPane().add(bg_pas);
        bg_pas.setBounds(50, 280, 30, 30);

        bg_fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("background.jpg")));
        getContentPane().add(bg_fundo);
        bg_fundo.setBounds(0, 0, 350, 390);

        //EVENTO DO BOTÃO DE LOGAR, EFETUA UMA TENTATIVA DE CONEXÃO
        //COM O BANCO DE DADOS E CHAMA O MENU CASO HAJA SUCESSO
        signin.addActionListener((ActionEvent evento) -> {
            try {
                String Username = this.login.getText();
                String Password = this.password.getText();
                Connection con = Sql.getConnection(Username, Password);
                if (con != null) {
                    dispose();
                    new Menu(Username, Password);
                    
                }
                closeConnection(con);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }
}
