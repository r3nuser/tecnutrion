package gui;


import java.awt.BorderLayout;
import gui.abas.*;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;



public class Menu extends JFrame {

    private final String currentusername;
    private final String currentpassword;

    JMenuBar barra_do_menu;
    JMenu cadastros;
    JMenu gerencia;
    JMenu ajuda;
    JMenu financeiro;
    JMenu relatorios;
    JMenu estoque;

    JMenuItem cadastrar_cliente;
    JMenuItem cadastrar_produto;
    JMenuItem cadastrar_fornecedor;

    JMenuItem gerenciar_cliente;
    JMenuItem gerenciar_produto;
    JMenuItem gerenciar_fornecedor;

    JMenuItem financeiro_estatisticas;
    JMenuItem financeiro_lucro;

    JTabbedPane abas;
    JPanel painel_inicio;
    JPanel painel_vendas;
    JPanel painel_cliente;
    JPanel painel_produto;
    JPanel painel_fornecedor;
    JPanel painel_estoque;

    public Menu(String username, String password) {
        this.currentusername = username;
        this.currentpassword = password;
        
        initComponents();

    }

    private void initComponents() {
        //DEFINIÇÃO E INICIALIZAÇÃO DA BARRA DO MENU E SEUS RESPECTIVOS ITENS.
        barra_do_menu = new JMenuBar();
        cadastros = new JMenu("Cadastros");
        gerencia = new JMenu("Gerência");
        ajuda = new JMenu("Ajuda");
        financeiro = new JMenu("Financeiro");
        relatorios = new JMenu("Relatórios");

        cadastrar_cliente = new JMenuItem("Cadastrar Cliente");
        cadastrar_produto = new JMenuItem("Cadastrar Produto");
        cadastrar_fornecedor = new JMenuItem("Cadastrar Fornecedor");

        cadastros.add(cadastrar_cliente);
        cadastros.add(cadastrar_produto);
        cadastros.add(cadastrar_fornecedor);

        gerenciar_cliente = new JMenuItem("Gerenciar Cliente");
        gerenciar_produto = new JMenuItem("Gerenciar Produto");
        gerenciar_fornecedor = new JMenuItem("Gerenciar Fornecedor");

        gerencia.add(gerenciar_cliente);
        gerencia.add(gerenciar_produto);
        gerencia.add(gerenciar_fornecedor);

        financeiro_estatisticas = new JMenuItem("Estatísticas");
        financeiro_lucro = new JMenuItem("Lucro e Vendas");

        financeiro.add(financeiro_estatisticas);
        financeiro.add(financeiro_lucro);
        financeiro.add(relatorios);

        barra_do_menu.add(cadastros);
        barra_do_menu.add(gerencia);
        barra_do_menu.add(financeiro);
        barra_do_menu.add(ajuda);

        //  AREA DE EVENTOS DE CADA BOTÃO DO MENU
        cadastrar_fornecedor.addActionListener((ActionEvent) -> {
            new Cadastrar_fornecedor(this.currentusername, this.currentpassword);
        });
        cadastrar_cliente.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(this.currentusername, this.currentpassword);
        });

        //DEFINIÇÃO E INICIALIZAÇÃO DAS ABAS E SEUS RESPECTIVOS ITENS.
        abas = new JTabbedPane();

        painel_inicio = new Painel_inicio();
        painel_vendas = new Painel_vendas();
        painel_cliente = new Painel_cliente(this.currentusername, this.currentpassword);
        painel_fornecedor = new Painel_fornecedor(this.currentusername, this.currentpassword);
        painel_produto = new Painel_produto(this.currentusername, this.currentpassword);
        painel_estoque = new Painel_vendas();

        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_inicio.png")),painel_inicio);
        
        abas.setMnemonicAt(0, KeyEvent.VK_1);
        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_vendas.png")),painel_vendas);
        abas.setMnemonicAt(1, KeyEvent.VK_2);
        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_clientes.png")),painel_cliente);
        abas.setMnemonicAt(2, KeyEvent.VK_3);
        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_produtos.png")),painel_produto);
        abas.setMnemonicAt(3, KeyEvent.VK_4);
        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_fornecedor.png")),painel_fornecedor);
        abas.setMnemonicAt(4, KeyEvent.VK_5);
        abas.addTab("", new ImageIcon(getClass().getResource("abas/ico_estoque.png")),painel_estoque);
        abas.setMnemonicAt(5, KeyEvent.VK_6);
        
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
        
        
        setVisible(true);
        
        
    }

    
}
//CODE OF GRAPHICS
/*
DefaultCategoryDataset dt = new DefaultCategoryDataset();
        final String series1 = "First";
        final String series2 = "Second";
        final String series3 = "Third";

        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";

        dt.addValue(1.0, series1, category1);
        dt.addValue(4.0, series1, category2);
        dt.addValue(3.0, series1, category3);
        dt.addValue(5.0, series1, category4);
        dt.addValue(5.0, series1, category5);

        dt.addValue(5.0, series2, category1);
        dt.addValue(7.0, series2, category2);
        dt.addValue(6.0, series2, category3);
        dt.addValue(8.0, series2, category4);
        dt.addValue(4.0, series2, category5);

        dt.addValue(4.0, series3, category1);
        dt.addValue(3.0, series3, category2);
        dt.addValue(2.0, series3, category3);
        dt.addValue(3.0, series3, category4);
        dt.addValue(6.0, series3, category5);
        JFreeChart grafico = ChartFactory.createBarChart("Mais Vendidos", "Valores", "Categoria", dt, PlotOrientation.VERTICAL, true, true, false);

        grafico.setBackgroundPaint(new Color(30, 30, 30));
        grafico.setBorderPaint(new Color(30, 30, 30));
        grafico.getTitle().setPaint(Color.WHITE);
        grafico.getLegend().setBackgroundPaint(new Color(30, 30, 30));
        grafico.getLegend().setItemPaint(new Color(255, 255, 255));
        grafico.getPlot().setBackgroundPaint(new Color(0, 0, 0));
        grafico.getPlot().setOutlinePaint(new Color(0, 0, 0));

        grafico.setBorderVisible(true);

        ChartPanel cp = new ChartPanel(grafico);

        cp.removeAll();

        cp.setPreferredSize(new Dimension(500, 500));
        inicio_pan.add(cp);
 */
