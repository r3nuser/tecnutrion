package gui;

//CLASSE RESPONSÁVEL POR EFETUAR O LOGIN.
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
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
    private boolean clicked_login;
    private JPasswordField password;
    private boolean clicked_password;
    private boolean already_digit;
    //CHAMADA DA CLASSE.

    public Login() {
        initComponents();
    }
    //METODO INICIALIZADOR DE COMPONENTES.

    private void initComponents() {
        bg_fundo = new JLabel("");
        bg_user = new JLabel("");
        bg_log = new JLabel("");
        bg_pas = new JLabel("");
        login = new JTextField("");
        password = new JPasswordField("");
        signin = new JButton("Logar");
        clicked_login = false;
        clicked_password = false;
        already_digit=false;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(350, 390);

        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        URL url = this.getClass().getResource("abas/ico_login.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);

        getContentPane().add(signin);
        signin.setBounds(126, 335, 80, 25);

        getContentPane().add(login);
        login.setBounds(80, 230, 170, 39);
        getContentPane().add(password);
        password.setBounds(80, 280, 170, 39);

        bg_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("login_images/user.png")));
        getContentPane().add(bg_user);
        bg_user.setBounds(100, 60, 130, 140);

        bg_log.setIcon(new javax.swing.ImageIcon(getClass().getResource("login_images/login.png")));
        getContentPane().add(bg_log);
        bg_log.setBounds(50, 230, 30, 30);

        bg_pas.setIcon(new javax.swing.ImageIcon(getClass().getResource("login_images/password.png")));
        getContentPane().add(bg_pas);
        bg_pas.setBounds(50, 280, 30, 30);

        getContentPane().add(bg_fundo);
        bg_fundo.setBounds(0, 0, 350, 390);

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent m) {
                if (!clicked_login) {
                    login.setText("");
                    clicked_login = true;
                }
            }
        });
      
        password.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent m) {
                if (!clicked_password) {
                    password.setText("");
                    clicked_password = true;
                }
            }
        });

        password.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (KeyEvent.VK_ENTER == ke.getKeyCode()) {
                    efetuar_login();
                }
            }
        });

        //EVENTO DO BOTÃO DE LOGAR, EFETUA UMA TENTATIVA DE CONEXÃO
        //COM O BANCO DE DADOS E CHAMA O MENU CASO HAJA SUCESSO
        signin.addActionListener((ActionEvent) -> {
            efetuar_login();
        });
        setTitle("LOGIN");
        setVisible(true);
    }

    private void efetuar_login() {
        String Username = this.login.getText();
        String Password = new String(this.password.getPassword());
        try {
            System.out.println(Username);
            System.out.println(Password);
            Connection con = Sql.getConnection(Username, Password);
            if (con != null) {
                dispose();
                new Menu(Username, Password);

            }
            closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
