package gui;

import java.awt.BorderLayout;
import gui.abas.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;


public class Menu extends JFrame {

    private final String currentusername;
    private final String currentpassword;

    JMenuBar barra_do_menu;
    JMenu cadastros;
    JMenu gerencia;
    JMenu financeiro;
    JMenu relatorios;
    JMenu estoque;
    JMenu configuracoes;

    JMenuItem funcoes_administrativas;

    JMenuItem cadastrar_cliente;
    JMenuItem cadastrar_produto;

    JMenuItem cadastrar_fornecedor;

    JMenuItem relatorio_de_vendas;

    JMenuItem financeiro_lucro;

    JTabbedPane abas;
    JPanel painel_inicio;
    JPanel painel_vendas;
    JPanel painel_cliente;
    JPanel painel_produto;
    JPanel painel_fornecedor;
    JPanel painel_estoque;
    JPanel painel_mensagem;

    public Menu(String username, String password) {
        this.currentusername = username;
        this.currentpassword = password;

        initComponents();

    }

    private void initComponents() {
        //DEFINIÇÃO E INICIALIZAÇÃO DA BARRA DO MENU E SEUS RESPECTIVOS ITENS.
        barra_do_menu = new JMenuBar();
        barra_do_menu.setBackground(new Color(40, 40, 40));

        cadastros = new JMenu("Cadastros");
        cadastros.setIcon(new ImageIcon(getClass().getResource("abas/ico_cadastro.png")));
        cadastros.setForeground(new Color(255, 255, 255));
        financeiro = new JMenu("Financeiro");
        financeiro.setIcon(new ImageIcon(getClass().getResource("abas/ico_financeiro.png")));
        financeiro.setForeground(new Color(255, 255, 255));
        relatorios = new JMenu("Relatórios");
        relatorios.setIcon(new ImageIcon(getClass().getResource("abas/ico_relatorios.png")));

        configuracoes = new JMenu("Configurações");
        configuracoes.setIcon(new ImageIcon(getClass().getResource("abas/ico_config.png")));
        configuracoes.setForeground(new Color(255, 255, 255));
        funcoes_administrativas = new JMenuItem("Funções Administrativas");
        funcoes_administrativas.setIcon(new ImageIcon(getClass().getResource("abas/ico_permissao.png")));
        configuracoes.add(funcoes_administrativas);

        cadastrar_cliente = new JMenuItem("Cadastrar Cliente");
        cadastrar_cliente.setIcon(new ImageIcon(getClass().getResource("abas/ico_clientes_peq.png")));
        cadastrar_produto = new JMenuItem("Cadastrar Produto");
        cadastrar_produto.setIcon(new ImageIcon(getClass().getResource("abas/ico_produtos_peq.png")));
        cadastrar_fornecedor = new JMenuItem("Cadastrar Fornecedor");
        cadastrar_fornecedor.setIcon(new ImageIcon(getClass().getResource("abas/ico_fornecedor_peq.png")));

        cadastros.add(cadastrar_cliente);
        cadastros.add(cadastrar_produto);
        cadastros.add(cadastrar_fornecedor);

        financeiro_lucro = new JMenuItem("Histórico de Vendas");
        financeiro_lucro.setIcon(new ImageIcon(getClass().getResource("abas/ico_money.png")));
        financeiro.add(financeiro_lucro);
        financeiro.add(relatorios);

        relatorio_de_vendas = new JMenuItem("Gerar Relatório de Vendas");

        relatorios.add(relatorio_de_vendas);

        barra_do_menu.add(cadastros);
        barra_do_menu.add(financeiro);
        barra_do_menu.add(configuracoes);
        
  

        //  AREA DE EVENTOS DE CADA BOTÃO DO MENU
        cadastrar_fornecedor.addActionListener((ActionEvent) -> {
            new Cadastrar_fornecedor(this.currentusername, this.currentpassword);
        });
        cadastrar_cliente.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(this.currentusername, this.currentpassword);
        });
        cadastrar_produto.addActionListener((ActionEvent) -> {
            new Cadastrar_produto(this.currentusername, this.currentpassword);
        });
        relatorio_de_vendas.addActionListener((ActionEvent) -> {
            new Relatorio_de_vendas(this.currentusername, this.currentpassword);
        });
        financeiro_lucro.addActionListener((ActionEvent) -> {
            new Estatisticas(this.currentusername, this.currentpassword);
        });
        funcoes_administrativas.addActionListener((ActionEvent) -> {
            JPanel panel = new JPanel();
            JLabel label = new JLabel("Entre com a senha de ADMIN:");
            JPasswordField pass = new JPasswordField(10);
            panel.add(label);
            panel.add(pass);
            String[] options = new String[]{"Cancelar", "Confirmar"};
            int option = JOptionPane.showOptionDialog(null, panel, "Confirmação de Senha",
                    JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[1]);
            if (option == 1) // pressing OK button
            {
                char[] password = pass.getPassword();
                String senha = "";
                for (int i = 0; i < password.length; i++) {
                    senha += password[i];
                }
                new Funcoes_administrativas(senha);
                setExtendedState(ICONIFIED);
            }

        });
        //DEFINIÇÃO E INICIALIZAÇÃO DAS ABAS E SEUS RESPECTIVOS ITENS.
        abas = new JTabbedPane(JTabbedPane.LEFT);

        painel_inicio = new Painel_inicio(this.currentusername, this.currentpassword);
        painel_vendas = new Painel_vendas(this.currentusername, this.currentpassword);
        painel_cliente = new Painel_cliente(this.currentusername, this.currentpassword);
        painel_fornecedor = new Painel_fornecedor(this.currentusername, this.currentpassword);
        painel_produto = new Painel_produto(this.currentusername, this.currentpassword);
        painel_estoque = new Painel_estoque(this.currentusername, this.currentpassword);
        painel_mensagem = new Painel_mensagem(this.currentusername, this.currentpassword);

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_inicio.png")), painel_inicio);

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_vendas.png")), painel_vendas);

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_clientes.png")), painel_cliente);

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_produtos.png")), painel_produto);

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_fornecedor.png")), painel_fornecedor);

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_estoque.png")), painel_estoque);
        
        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_message.png")), painel_mensagem);

        abas.setMnemonicAt(0, KeyEvent.VK_1);
        abas.setMnemonicAt(1, KeyEvent.VK_2);
        abas.setMnemonicAt(2, KeyEvent.VK_3);
        abas.setMnemonicAt(3, KeyEvent.VK_4);
        abas.setMnemonicAt(4, KeyEvent.VK_5);
        abas.setMnemonicAt(5, KeyEvent.VK_6);
        
              abas.setBackground(new Color(200,200,200));
              getContentPane().setBackground(new Color(180,180,180));
              
        //ADICIONANDO TODOS COMPONENTES NA JFRAME
        setJMenuBar(barra_do_menu);
        add(abas, BorderLayout.CENTER);
        //PROPRIEDADES DA JFRAME
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        //setUndecorated(true);
        setTitle("Tecnutrion");

        //getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        URL url = this.getClass().getResource("abas/ico_blacksheep.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(iconeTitulo);

        setVisible(true);

    }

}
