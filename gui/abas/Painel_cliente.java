package gui.abas;

import bean.Cliente;
import dao.ClienteDAO;
import gui.Cadastrar_cliente;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Painel_cliente extends JPanel {

    private String username;
    private String password;

    private JPanel painel_de_botoes;
    private JButton cadastrar_clientes;
    private JButton realizar_consulta;
    private JButton editar_dados;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_graficos;
    private JFreeChart grafico_mais_rentaveis_mes;
    private JFreeChart grafico_mais_rentaveis_semana;
    private ChartPanel cp_mais_rentaveis_mes;
    private ChartPanel cp_mais_rentaveis_semana;
    private DefaultCategoryDataset dt;

    private JPanel painel_de_dados;
    private JLabel nome_cliente_l;
    private JLabel data_nascimento_l;
    private JLabel id_l;
    private JTextField nome_cliente;
    private JTextField data_nascimento;
    private JTextField id;

    public Painel_cliente(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;
        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());

        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();
        inicializa_painel_de_graficos();
        inicializa_painel_de_dados();

        setVisible(true);

    }

    private void inicializa_painel_de_dados() {

        painel_de_dados = new JPanel();
        

        id_l = new JLabel("ID");
        id = new JTextField();

        nome_cliente_l = new JLabel("Nome do cliente:");
        nome_cliente = new JTextField();

        data_nascimento_l = new JLabel("Data de Nascimento:");
        data_nascimento = new JTextField();

        painel_de_dados.add(nome_cliente_l);
        painel_de_dados.add(nome_cliente);
        painel_de_dados.add(id_l);
        painel_de_dados.add(id);
        painel_de_dados.add(data_nascimento_l);
        painel_de_dados.add(data_nascimento);

        

        painel_de_dados.setPreferredSize(new Dimension(400, 1000));

        add(painel_de_dados, BorderLayout.LINE_START);
    }

    private void inicializa_painel_de_graficos() {
        painel_de_graficos = new JPanel(new FlowLayout());
        dt = new DefaultCategoryDataset();
        grafico_mais_rentaveis_mes = ChartFactory.createBarChart("Clientes mais lucrativos do mês", "Valores", null, dt, PlotOrientation.VERTICAL, true, true, false);
        cp_mais_rentaveis_mes = new ChartPanel(grafico_mais_rentaveis_mes);
        cp_mais_rentaveis_mes.removeAll();
        cp_mais_rentaveis_mes.setPreferredSize(new Dimension(300, 300));
        painel_de_graficos.add(cp_mais_rentaveis_mes);

        grafico_mais_rentaveis_mes = ChartFactory.createBarChart("Clientes mais lucrativos da semana", "Valores", null, dt, PlotOrientation.VERTICAL, true, true, false);
        cp_mais_rentaveis_semana = new ChartPanel(grafico_mais_rentaveis_mes);
        cp_mais_rentaveis_semana.removeAll();
        cp_mais_rentaveis_semana.setPreferredSize(new Dimension(300, 300));

        painel_de_graficos.add(cp_mais_rentaveis_semana);
        painel_de_graficos.setPreferredSize(new Dimension(300, 600));
        add(painel_de_graficos, BorderLayout.LINE_END);

    }

    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_clientes = new JButton("Cadastrar Novo Cliente");
        realizar_consulta = new JButton("Consultar Clientes");
        editar_dados = new JButton("Editar Dados do Cliente");
        painel_de_botoes.add(cadastrar_clientes, BorderLayout.LINE_START);
        painel_de_botoes.add(realizar_consulta, BorderLayout.CENTER);
        painel_de_botoes.add(editar_dados, BorderLayout.LINE_END);

        add(painel_de_botoes, BorderLayout.PAGE_START);

        realizar_consulta.addActionListener((ActionEvent) -> {

            atualizar_tabela();
        });
        cadastrar_clientes.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(this.username, this.password);
        });
    }

    private void inicializa_painel_da_tabela() {
        painel_da_tabela = new JPanel(new GridLayout());
        modelo_tabela = new DefaultTableModel();
        tabela = new JTable(modelo_tabela);

        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");
        modelo_tabela.addColumn("Data Nascimento");
        modelo_tabela.addColumn("DDD");
        modelo_tabela.addColumn("Prefixo");
        modelo_tabela.addColumn("Sufixo");

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void atualizar_tabela() {
        modelo_tabela.setNumRows(0);
        ArrayList<Cliente> dados_cliente = ClienteDAO.read(this.username, this.password);
        for (int i = 0; i < dados_cliente.size(); i++) {
            Cliente c = dados_cliente.get(i);
            modelo_tabela.addRow(new Object[]{c.getId(), c.getNome(), c.getData_nascimento()});
        }
        tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(400);

        tabela.getColumnModel().getColumn(2).setMaxWidth(130);
        tabela.getColumnModel().getColumn(2).setMinWidth(130);

        tabela.getColumnModel().getColumn(3).setMaxWidth(40);
        tabela.getColumnModel().getColumn(3).setMinWidth(40);

        tabela.getColumnModel().getColumn(4).setMaxWidth(60);
        tabela.getColumnModel().getColumn(4).setMinWidth(60);

        tabela.getColumnModel().getColumn(5).setMaxWidth(50);
        tabela.getColumnModel().getColumn(5).setMinWidth(50);

    }
}
