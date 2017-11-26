package gui.abas;

import bean.Cliente;
import bean.Endereco;
import bean.Telefone;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import dao.MiscDAO;
import dao.TelefoneDAO;
import gui.Cadastrar_cliente;
import gui.Editar_clientes;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.util.ArrayList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/*import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.ComponentOrientation;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;*/
public class Painel_cliente extends JPanel {

    private final String username;
    private final String password;

    private JPanel painel_de_botoes;
    private JButton cadastrar_clientes;
    private JButton realizar_consulta;
    private JButton editar_dados;
    private JButton busca_cliente_b;
    private JTextField busca_cliente;

    private JPanel painel_da_tabela;
    private JTable tabela;
    private DefaultTableModel modelo_tabela;
    private JScrollPane scroll;

    private JPanel painel_de_dados;
    private JLabel nome_cliente_l;
    private JLabel email_l;
    private JLabel data_nascimento_l;
    private JLabel id_l;
    private JLabel tipolog_l;
    private JLabel log_l;
    private JLabel estado_l;
    private JLabel bairro_l;
    private JLabel municipio_l;
    private JLabel complemento_l;
    private JLabel cep_l;

    private JTextField nome_cliente;
    private JTextField email;
    private JTextField data_nascimento;
    private JTextField id;
    private JTextField tipolog;
    private JTextField log;
    private JTextField estado;
    private JTextField bairro;
    private JTextField municipio;
    private JTextField complemento;
    private JTextField cep;

    private JTable tabela_telefone;
    private DefaultTableModel modelo_telefone;
    private JScrollPane scroll_telefone;

    private JButton deletar_cliente;

    public Painel_cliente(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;

        initAll();
    }

    private void initAll() {
        setLayout(new BorderLayout());

        inicializa_painel_de_botoes();
        inicializa_painel_da_tabela();

        inicializa_painel_de_dados();

        setVisible(true);

    }

    private void inicializa_painel_de_dados() {
        painel_de_dados = new JPanel(new FlowLayout());

        id_l = new JLabel("ID");
        id = new JTextField();

        nome_cliente_l = new JLabel("Nome do cliente:");
        nome_cliente = new JTextField();

        data_nascimento_l = new JLabel("Data de Nascimento:");
        data_nascimento = new JTextField();
        painel_de_dados.add(nome_cliente_l);
        painel_de_dados.add(nome_cliente);
        nome_cliente.setEditable(false);

        nome_cliente.setPreferredSize(new Dimension(300, 18));

        painel_de_dados.add(id_l);
        painel_de_dados.add(id);
        id.setEditable(false);
        id.setPreferredSize(new Dimension(70, 18));

        painel_de_dados.add(data_nascimento_l);
        painel_de_dados.add(data_nascimento);
        data_nascimento.setEditable(false);
        data_nascimento.setPreferredSize(new Dimension(80, 18));

        tipolog_l = new JLabel("Tipo de logradouro:");
        tipolog = new JTextField();

        painel_de_dados.add(tipolog_l);
        painel_de_dados.add(tipolog);
        tipolog.setEditable(false);
        tipolog.setPreferredSize(new Dimension(135, 18));

        log_l = new JLabel("Logradouro:");
        log = new JTextField();

        painel_de_dados.add(log_l);

        painel_de_dados.add(log);
        log.setEditable(false);
        log.setPreferredSize(new Dimension(330, 18));

        estado_l = new JLabel("Estado:");
        estado = new JTextField();

        painel_de_dados.add(estado_l);
        painel_de_dados.add(estado);
        estado.setEditable(false);
        estado.setPreferredSize(new Dimension(50, 18));

        bairro_l = new JLabel("   Bairro:");
        bairro = new JTextField();

        painel_de_dados.add(bairro_l);
        painel_de_dados.add(bairro);
        bairro.setEditable(false);
        bairro.setPreferredSize(new Dimension(140, 18));

        municipio_l = new JLabel("Municipio:");
        municipio = new JTextField();

        painel_de_dados.add(municipio_l);
        painel_de_dados.add(municipio);
        municipio.setEditable(false);
        municipio.setPreferredSize(new Dimension(240, 18));

        complemento_l = new JLabel("Complemento:");
        complemento = new JTextField();

        painel_de_dados.add(complemento_l);
        painel_de_dados.add(complemento);
        complemento.setEditable(false);
        complemento.setPreferredSize(new Dimension(330, 18));

        cep_l = new JLabel("CEP:");
        cep = new JTextField();

        painel_de_dados.add(cep_l);
        painel_de_dados.add(cep);
        cep.setEditable(false);
        cep.setPreferredSize(new Dimension(80, 18));

        email_l = new JLabel("Email:");
        email = new JTextField();

        painel_de_dados.add(email_l);
        painel_de_dados.add(email);
        email.setEditable(false);
        email.setPreferredSize(new Dimension(260, 18));

        modelo_telefone = new DefaultTableModel() {
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        tabela_telefone = new JTable(modelo_telefone);

        modelo_telefone.addColumn("DDD");
        modelo_telefone.addColumn("Prefixo");
        modelo_telefone.addColumn("Sufixo");

        scroll_telefone = new JScrollPane(tabela_telefone);

        scroll_telefone.setPreferredSize(new Dimension(550, 200));
        scroll_telefone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                "Telefones",
                1,
                1,
                new java.awt.Font("Dialog", 1, 14)
        ));

        painel_de_dados.add(scroll_telefone);

        deletar_cliente = new JButton("Deletar Cliente", new ImageIcon(getClass().getResource("ico_deletar.png")));
        painel_de_dados.add(deletar_cliente);

        deletar_cliente.addActionListener((ActionEvent) -> {
            Cliente c = new Cliente();
            c.setId(Integer.parseInt(id.getText()));
            Telefone t = new Telefone();
            t.setFk_cliente_cod(c.getId());
            Endereco e = new Endereco();
            e.setClientecod(c.getId());
            System.out.println(c.getId());
            try {
                TelefoneDAO.delete(username, password, t);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            try {
                EnderecoDAO.delete(username, password, e);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            ClienteDAO.delete(username, password, c);
            atualizar_tabela((byte) 0);
        });

        this.nome_cliente_l.setPreferredSize(new Dimension(119, 25));
        this.data_nascimento_l.setPreferredSize(new Dimension(148, 25));
        this.log_l.setPreferredSize(new Dimension(88, 25));
        this.bairro_l.setPreferredSize(new Dimension(60, 25));
        this.complemento_l.setPreferredSize(new Dimension(102, 25));

        painel_de_dados.setBorder(BorderFactory.createLineBorder(Color.black));
        painel_de_dados.setPreferredSize(new Dimension(600, 1000));

        add(painel_de_dados, BorderLayout.LINE_START);
    }

    /*private void inicializa_painel_de_graficos() {
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

    }*/
    private void inicializa_painel_de_botoes() {
        painel_de_botoes = new JPanel(new FlowLayout());
        cadastrar_clientes = new JButton("Cadastrar Novo Cliente", new ImageIcon(getClass().getResource("ico_mais.png")));
        realizar_consulta = new JButton("Consultar Clientes", new ImageIcon(getClass().getResource("ico_lupa.png")));
        editar_dados = new JButton("Editar Dados do Cliente", new ImageIcon(getClass().getResource("ico_editar.png")));

        busca_cliente_b = new JButton(new ImageIcon(getClass().getResource("ico_lupa2.png")));;
        busca_cliente = new JTextField();
        busca_cliente.setPreferredSize(new Dimension(270, 24));

        painel_de_botoes.add(cadastrar_clientes);
        painel_de_botoes.add(realizar_consulta);
        painel_de_botoes.add(editar_dados);
        painel_de_botoes.add(busca_cliente);
        painel_de_botoes.add(busca_cliente_b);

        add(painel_de_botoes, BorderLayout.PAGE_START);

        realizar_consulta.addActionListener((ActionEvent) -> {

            atualizar_tabela((byte) 0);
        });
        cadastrar_clientes.addActionListener((ActionEvent) -> {
            new Cadastrar_cliente(this.username, this.password);
        });

        busca_cliente_b.addActionListener((ActionEvent) -> {
            atualizar_tabela((byte) 1);
        });
        editar_dados.addActionListener((ActionEvent) -> {
            try {
                new Editar_clientes(this.username, this.password, Integer.parseInt(id.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao editar : Escolha um cliente na tabela e tente novamente !");
                atualizar_tabela((byte) 0);
            }
        });
    }

    private void inicializa_painel_da_tabela() {
        painel_da_tabela = new JPanel(new GridLayout());
        modelo_tabela = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        tabela = new JTable(modelo_tabela);
        tabela.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if ((ke.getKeyCode() == KeyEvent.VK_UP) || (ke.getKeyCode() == KeyEvent.VK_DOWN)) {
                    atualizar_caixas_de_texto();
                }
            }
        });
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                atualizar_caixas_de_texto();
            }
        });

        modelo_tabela.addColumn("ID");
        modelo_tabela.addColumn("Nome");
        modelo_tabela.addColumn("Data Nascimento");
        modelo_tabela.addColumn("Email");

        tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(0).setMaxWidth(70);

        tabela.getColumnModel().getColumn(1).setPreferredWidth(400);

        tabela.getColumnModel().getColumn(2).setMaxWidth(80);
        tabela.getColumnModel().getColumn(2).setMinWidth(80);

        tabela.getColumnModel().getColumn(3).setMaxWidth(220);
        tabela.getColumnModel().getColumn(3).setMinWidth(220);

        scroll = new JScrollPane(tabela);
        scroll.setSize(1024, 768);
        painel_da_tabela.add(scroll);

        add(painel_da_tabela, BorderLayout.CENTER);
    }

    private void atualizar_caixas_de_texto() {

        Cliente c = MiscDAO.search_cliente_por_id(username, password, (int) tabela.getValueAt(tabela.getSelectedRow(), 0));
        nome_cliente.setText(c.getNome());
        id.setText("" + c.getId());
        email.setText(c.getEmail());
        data_nascimento.setText("" + c.getData_nascimento());
        try {
            modelo_telefone.setNumRows(0);
            for (Telefone t : MiscDAO.search_telefone_por_id(username, password, c.getId())) {
                modelo_telefone.addRow(new Object[]{t.getDdd(), t.getAntesh(),
                    t.getDepoish()});

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            Endereco e = MiscDAO.search_endereco_por_id(username, password, c.getId());
            tipolog.setText(e.getTipolog());
            log.setText(e.getLog());
            bairro.setText(e.getBairro());
            complemento.setText(e.getComplemento());
            estado.setText(e.getEstado());
            municipio.setText(e.getMunicipio());
            cep.setText(e.getCep());

        } catch (Exception e) {
            tipolog.setText("");
            log.setText("");
            bairro.setText("");
            complemento.setText("");
            estado.setText("");
            municipio.setText("");
            cep.setText("");
            System.out.println(e);
        }
    }

    private void atualizar_tabela(byte mode) {
        modelo_tabela.setNumRows(0);
        ArrayList<Cliente> dados_cliente;

        if (mode == 0) {
            dados_cliente = ClienteDAO.read(this.username, this.password);
        } else {
            dados_cliente = MiscDAO.search_cliente_por_nome(username, password, busca_cliente.getText());
        }
        for (int i = 0; i < dados_cliente.size(); i++) {
            Cliente c = dados_cliente.get(i);

            modelo_tabela.addRow(new Object[]{
                c.getId(),
                c.getNome(),
                c.getData_nascimento(),
                c.getEmail()
            });
        }

    }

}
