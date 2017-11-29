package gui.abas;

import bean.Pedido;
import bean.Produto;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class Painel_inicio extends JPanel {

    private final String username;
    private final String password;

    private JScrollPane scroll;

    private JPanel painel_principal;

    private JPanel painel_logistica;
    private JButton atualizar_dados;
    private JLabel atualizacao;
    // PAINEL DE VENDAS
    private JPanel painel_de_vendas;
    private JPanel venda_anual;

    private JLabel total_de_vendas_a;
    private JLabel total_de_lucro_bruto_a;
    private JLabel total_de_lucro_liquido_a;

    private JPanel venda_mensal;

    private JLabel total_de_vendas_m;
    private JLabel total_de_lucro_bruto_m;
    private JLabel total_de_lucro_liquido_m;

    private JPanel venda_diaria;

    private JLabel total_de_vendas_d;
    private JLabel total_de_lucro_bruto_d;
    private JLabel total_de_lucro_liquido_d;

    // PAINEL DE RENDIMENTO
    private JPanel painel_de_rendimento;
    private JComboBox periodo;

    private JPanel indice_de_vendas;
    private JPanel indice_de_lucro_b;
    private JPanel indice_de_lucro_l;

    private JLabel indice_de_vendas_texto;
    private JLabel indice_de_lucro_b_texto;
    private JLabel indice_de_lucro_l_texto;

    private JLabel img_indice_de_vendas;
    private JLabel img_indice_de_lucro_b;
    private JLabel img_indice_de_lucro_l;

    // PAINEL INFORMATIVO
    private JPanel painel_informativo;

    private JPanel informativo_total_cadastros;
    private JLabel produtos_cadastrados;
    private JLabel clientes_cadastrados;
    private JLabel fornecedores_cadastrados;
    private JLabel total_de_item_estoque;
    private JLabel total_de_vendas;

    private JPanel informativo_top_low_cadastros;
    private JLabel produtos_mais_vendidos;
    private JLabel categoria_de_produto_mais_vendidos;
    private JLabel produtos_menos_vendidos;
    private JLabel categoria_de_produto_menos_vendidos;

    private JPanel top_low_clientes_a;
    private JLabel cliente_que_mais_compra_a;
    private JLabel mais_compra_total_a;
    private JPanel top_low_clientes_m;
    private JLabel cliente_que_mais_compra_m;
    private JLabel mais_compra_total_m;
    private JPanel top_low_clientes_s;
    private JLabel cliente_que_mais_compra_s;
    private JLabel mais_compra_total_s;

//tabela
    private JPanel painel_tabela;

    private JTable tabela_estoque;
    private JTable tabela_validade;
    private JTable tabela_aniversario;

    private DefaultTableModel dtm_estoque;
    private DefaultTableModel dtm_validade;
    private DefaultTableModel dtm_aniversario;

    private JScrollPane scroll_estoque;
    private JScrollPane scroll_validade;
    private JScrollPane scroll_aniversario;

    private Border b;
    private Font f;

    private Date converterData(String datatexto) {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date data = null;
        try {
            data = new java.sql.Date(fmt.parse(datatexto).getTime());
        } catch (Exception e) {
            System.out.println(e);
        }
        return data;
    }

    public Painel_inicio(String currentusername, String currentpassword) {
        this.username = currentusername;
        this.password = currentpassword;

        initAll();
    }

    private void initAll() {
        b = BorderFactory.createLineBorder(Color.black);
        f = new java.awt.Font("Dialog", 1, 16);

        setLayout(new BorderLayout());
        painel_principal = new JPanel(new BorderLayout());
        painel_principal.setPreferredSize(new Dimension(1024, 760));
        painel_principal.setBorder(b);
        inicializa_painel_da_tabela();
        inicializa_itens_painel_logistica();

        scroll = new JScrollPane(painel_principal);

        add(scroll);

        setVisible(true);
    }

    private void inicializa_painel_da_tabela() {
        painel_tabela = new JPanel(new GridLayout(3, 1));
       
        painel_tabela.setBorder(b);
        inicializa_itens_painel_tabela();
        painel_principal.add(painel_tabela, BorderLayout.CENTER);
    }

    private void inicializa_itens_painel_tabela() {
        inicializa_painel_da_tabela_validade();
        inicializa_painel_da_tabela_estoque();
        inicializa_painel_da_tabela_aniversariante();
    }

    private void inicializa_painel_da_tabela_estoque() {
        dtm_estoque = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return ImageIcon.class;
                    default:
                        return Object.class;
                }
            }
        };
        tabela_estoque = new JTable(dtm_estoque);
        dtm_estoque.addColumn("Foto");
        dtm_estoque.addColumn("ID");
        dtm_estoque.addColumn("Nome");
        dtm_estoque.addColumn("Qnt. Estoque");

        tabela_estoque.setRowHeight(100);
        tabela_estoque.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela_estoque.getColumnModel().getColumn(0).setMinWidth(100);

        scroll_estoque = new JScrollPane(tabela_estoque);

        scroll_estoque.setBorder(BorderFactory.createTitledBorder(b, "PRODUTOS EM BAIXA NO ESTOQUE", 1, 1, f));

        painel_tabela.add(scroll_estoque);

    }

    private void inicializa_painel_da_tabela_validade() {
        dtm_validade = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return ImageIcon.class;
                    default:
                        return Object.class;
                }
            }
        };
        tabela_validade = new JTable(dtm_validade);
        dtm_validade.addColumn("Foto");
        dtm_validade.addColumn("ID");
        dtm_validade.addColumn("Nome");
        dtm_validade.addColumn("Validade");

        tabela_validade.setRowHeight(100);

        tabela_validade.getColumnModel().getColumn(0).setMaxWidth(100);
        tabela_validade.getColumnModel().getColumn(0).setMinWidth(100);

        scroll_validade = new JScrollPane(tabela_validade);
        scroll_validade.setBorder(BorderFactory.createTitledBorder(b, "PRODUTOS PERTO DE EXCEDER A VALIDADE", 1, 1, f));

        painel_tabela.add(scroll_validade);

    }

    private void inicializa_painel_da_tabela_aniversariante() {
        dtm_aniversario = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        tabela_aniversario = new JTable(dtm_aniversario);

        dtm_aniversario.addColumn("ID");
        dtm_aniversario.addColumn("Nome");
        dtm_aniversario.addColumn("Aniversario");

        scroll_aniversario = new JScrollPane(tabela_aniversario);
        scroll_aniversario.setBorder(BorderFactory.createTitledBorder(b, "CLIENTES ANIVERSARIANTES DO MÊS", 1, 1, f));

        painel_tabela.add(scroll_aniversario);
    }

    private void atualizar_dados_tabelas(GregorianCalendar calendar) {
        dtm_aniversario.setNumRows(0);
        dtm_validade.setNumRows(0);
        dtm_estoque.setNumRows(0);

        Date dia = converterData(
                (calendar.get(GregorianCalendar.DAY_OF_MONTH) + 7)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH) - (-1))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR))
        );
        System.out.println("validade <= " + dia);
        try {
            MiscDAO.search_produtos_perto_de_vencer(username, password, dia).stream().forEach((e) -> {
                Produto p = MiscDAO.get_produto_por_fk_cod_estoque(username, password, e.getEstoque_cod());
                dtm_validade.addRow(new Object[]{p.getProduto_foto_para_tabela(), p.getProduto_cod(), p.getProduto_nome(), e.getValidade()});
            });
            MiscDAO.search_produtos_fora_de_estoque(username, password).stream().forEach((e) -> {
                Produto p = MiscDAO.get_produto_por_fk_cod_estoque(username, password, e.getEstoque_cod());
                dtm_estoque.addRow(new Object[]{p.getProduto_foto_para_tabela(), p.getProduto_cod(), p.getProduto_nome(), e.getQnt_estoque()});
            });
            MiscDAO.search_aniversariantes_do_mes(username, password).stream().forEach((c) -> {
                dtm_aniversario.addRow(new Object[]{c.getId(), c.getNome(), c.getData_nascimento()});
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void inicializa_itens_painel_logistica() {
        painel_logistica = new JPanel(null);

        atualizar_dados = new JButton(new ImageIcon(getClass().getResource("button_update.png")));
        atualizar_dados.setBounds(10, 10, 64, 64);

        atualizacao = new JLabel("");

        atualizacao.setBounds(100, 5, 400, 24);

        atualizar_dados.addActionListener((ActionEvent) -> {
            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMMM' de 'yyyy' - 'HH':'mm'h'", locale);
            atualizacao.setText("Dados atualizados em " + formatador.format(calendar.getTime()));
            painel_logistica_anual();
            painel_logistica_mensal();
            painel_logistica_diario();
            atualizar_dados_tabelas(calendar);
            atualizar_dados_do_rendimento((byte) periodo.getSelectedIndex(), calendar);

            atualiza_painel_informativo();
        });
        painel_logistica.setPreferredSize(new Dimension(900, 2000));
        painel_logistica.setBorder(b);
        painel_logistica.add(atualizacao);
        painel_logistica.add(atualizar_dados);
        painel_principal.add(painel_logistica, BorderLayout.LINE_START);
        inicializa_painel_de_vendas();
        inicializa_painel_de_rendimento();
        inicializa_painel_informativo();
    }

    private void atualiza_painel_informativo() {
        produtos_cadastrados.setText("Total de Produtos Cadastrados : " + MiscDAO.produtos_cadastrados(username, password));
        clientes_cadastrados.setText("Total de Clientes Cadastrados : " + MiscDAO.clientes_cadastrados(username, password));
        fornecedores_cadastrados.setText("Total de Fornecedores Cadastrados : " + MiscDAO.fornecedores_cadastrados(username, password));
        total_de_item_estoque.setText("Total de Itens no Estoque : " + MiscDAO.total_item_estoque_cadastrados(username, password));
        total_de_vendas.setText("Total de Vendas Realizadas : " + MiscDAO.total_de_vendas_cadastrados(username, password));
        Produto p = MiscDAO.produtos_mais_vendidos(username, password);
        produtos_mais_vendidos.setText("Produto Mais Vendido:" + p.getProduto_nome());
        categoria_de_produto_mais_vendidos.setText("Categoria de Produtos Mais Vendidos:" + p.getCategoria());
        p = MiscDAO.produtos_menos_vendidos(username, password);
        produtos_menos_vendidos.setText("Produto Menos Vendido:" + p.getProduto_nome());
        categoria_de_produto_menos_vendidos.setText("Categoria de Produto Menos Vendido:" + p.getCategoria());
    }

    private void inicializa_painel_informativo() {
        painel_informativo = new JPanel(null);
        painel_informativo.setBorder(BorderFactory.createTitledBorder(b, "INFORMATIVO", 1, 1, f));
        painel_informativo.setBounds(10, 500, 880, 200);

        informativo_total_cadastros = new JPanel(null);
        informativo_total_cadastros.setBorder(BorderFactory.createTitledBorder(b, "TOTAL DE CADASTROS"));
        informativo_top_low_cadastros = new JPanel(null);
        informativo_top_low_cadastros.setBorder(BorderFactory.createTitledBorder(b, "INFORMAÇÕES DE PRODUTO"));

        informativo_total_cadastros.setBounds(10, 40, 430, 150);
        informativo_top_low_cadastros.setBounds(440, 40, 430, 150);

        produtos_cadastrados = new JLabel("Total de Produtos Cadastrados:");
        clientes_cadastrados = new JLabel("Total de Clientes Cadastrados:");
        fornecedores_cadastrados = new JLabel("Total de Fornecedores Cadastrados:");
        total_de_item_estoque = new JLabel("Total de Itens no Estoque:");
        total_de_vendas = new JLabel("Total de Vendas Realizadas: ");

        produtos_mais_vendidos = new JLabel("Produtos Mais Vendidos:");
        categoria_de_produto_mais_vendidos = new JLabel("Categoria de Produtos Mais Vendidos:");
        produtos_menos_vendidos = new JLabel("Produtos Menos Vendidos:");
        categoria_de_produto_menos_vendidos = new JLabel("Categoria de Produtos Menos Vendidos:");

        informativo_total_cadastros.add(produtos_cadastrados);
        informativo_total_cadastros.add(clientes_cadastrados);
        informativo_total_cadastros.add(fornecedores_cadastrados);
        informativo_total_cadastros.add(total_de_item_estoque);
        informativo_total_cadastros.add(total_de_vendas);

        informativo_top_low_cadastros.add(produtos_mais_vendidos);
        informativo_top_low_cadastros.add(categoria_de_produto_mais_vendidos);
        informativo_top_low_cadastros.add(produtos_menos_vendidos);
        informativo_top_low_cadastros.add(categoria_de_produto_menos_vendidos);

        produtos_cadastrados.setBounds(10, 30, 400, 20);
        clientes_cadastrados.setBounds(10, 50, 400, 20);
        fornecedores_cadastrados.setBounds(10, 70, 400, 20);
        total_de_item_estoque.setBounds(10, 90, 400, 20);
        total_de_vendas.setBounds(10, 110, 400, 20);

        produtos_mais_vendidos.setBounds(10, 30, 400, 20);
        categoria_de_produto_mais_vendidos.setBounds(10, 55, 400, 20);
        produtos_menos_vendidos.setBounds(10, 80, 400, 20);
        categoria_de_produto_menos_vendidos.setBounds(10, 105, 400, 20);

        painel_informativo.add(informativo_total_cadastros);
        painel_informativo.add(informativo_top_low_cadastros);

        /*top_low_clientes_a = new JPanel(null);
        top_low_clientes_m = new JPanel(null);
        top_low_clientes_s = new JPanel(null);
        
        cliente_que_mais_compra_a = new JLabel("Cliente que mais ");
         */
        painel_logistica.add(painel_informativo);

    }

    private void painel_logistica_anual() {

        int quantidade = 0;
        float pedido_vl_tot = 0;
        float pedido_lucro_tot = 0;

        for (Pedido p : MiscDAO.vendas_ano(username, password)) {
            quantidade++;
            pedido_vl_tot += p.getPedido_vl_tot();
            pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
        }

        total_de_vendas_a.setText("Total de Pedidos: " + quantidade);
        total_de_lucro_bruto_a.setText("Total de Lucro Bruto:    R$:" + pedido_vl_tot);
        total_de_lucro_liquido_a.setText("Total de Lucro Liquido: R$:" + pedido_lucro_tot);
    }

    private void painel_logistica_mensal() {

        int quantidade = 0;
        float pedido_vl_tot = 0;
        float pedido_lucro_tot = 0;

        for (Pedido p : MiscDAO.vendas_mes(username, password)) {
            quantidade++;
            pedido_vl_tot += p.getPedido_vl_tot();
            pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
        }

        total_de_vendas_m.setText("Total de Pedidos: " + quantidade);
        total_de_lucro_bruto_m.setText("Total de Lucro Bruto:    R$:" + pedido_vl_tot);
        total_de_lucro_liquido_m.setText("Total de Lucro Liquido: R$:" + pedido_lucro_tot);
    }

    private void painel_logistica_diario() {

        int quantidade = 0;
        float pedido_vl_tot = 0;
        float pedido_lucro_tot = 0;

        for (Pedido p : MiscDAO.vendas_dia(username, password)) {
            quantidade++;
            pedido_vl_tot += p.getPedido_vl_tot();
            pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
        }

        total_de_vendas_d.setText("Total de Pedidos: " + quantidade);
        total_de_lucro_bruto_d.setText("Total de Lucro Bruto:    R$:" + pedido_vl_tot);
        total_de_lucro_liquido_d.setText("Total de Lucro Liquido: R$:" + pedido_lucro_tot);
    }

    private void inicializa_painel_de_vendas() {

        painel_de_vendas = new JPanel(new BorderLayout());
        painel_de_vendas.setBounds(10, 100, 880, 170);
        painel_de_vendas.setBorder(BorderFactory.createTitledBorder(b, "VENDAS", 1, 1, f));

        venda_anual = new JPanel(null);
        venda_mensal = new JPanel(null);
        venda_diaria = new JPanel(null);
        venda_anual.setBorder(BorderFactory.createTitledBorder(b, "Ano"));
        venda_mensal.setBorder(BorderFactory.createTitledBorder(b, "Mês"));
        venda_diaria.setBorder(BorderFactory.createTitledBorder(b, "Dia"));
        venda_anual.setPreferredSize(new Dimension(290, 170));
        venda_mensal.setPreferredSize(new Dimension(290, 170));
        venda_diaria.setPreferredSize(new Dimension(290, 170));

        painel_de_vendas.add(venda_anual, BorderLayout.LINE_START);
        painel_de_vendas.add(venda_mensal, BorderLayout.CENTER);
        painel_de_vendas.add(venda_diaria, BorderLayout.LINE_END);

        total_de_vendas_a = new JLabel("Total de Pedidos: ");
        total_de_lucro_bruto_a = new JLabel("Total de Lucro Bruto:    R$:");
        total_de_lucro_liquido_a = new JLabel("Total de Lucro Liquido: R$:");

        total_de_vendas_a.setBounds(10, 30, 250, 18);
        total_de_lucro_bruto_a.setBounds(10, 60, 250, 18);
        total_de_lucro_liquido_a.setBounds(10, 90, 250, 18);

        venda_anual.add(total_de_vendas_a);
        venda_anual.add(total_de_lucro_bruto_a);
        venda_anual.add(total_de_lucro_liquido_a);

        total_de_vendas_m = new JLabel("Total de Pedidos: ");
        total_de_lucro_bruto_m = new JLabel("Total de Lucro Bruto:    R$:");
        total_de_lucro_liquido_m = new JLabel("Total de Lucro Liquido: R$:");

        total_de_vendas_m.setBounds(10, 30, 250, 18);
        total_de_lucro_bruto_m.setBounds(10, 60, 250, 18);
        total_de_lucro_liquido_m.setBounds(10, 90, 250, 18);

        venda_mensal.add(total_de_vendas_m);
        venda_mensal.add(total_de_lucro_bruto_m);
        venda_mensal.add(total_de_lucro_liquido_m);

        total_de_vendas_d = new JLabel("Total de Pedidos: ");
        total_de_lucro_bruto_d = new JLabel("Total de Lucro Bruto:    R$:");
        total_de_lucro_liquido_d = new JLabel("Total de Lucro Liquido: R$:");

        total_de_vendas_d.setBounds(10, 30, 250, 18);
        total_de_lucro_bruto_d.setBounds(10, 60, 250, 18);
        total_de_lucro_liquido_d.setBounds(10, 90, 250, 18);

        venda_diaria.add(total_de_vendas_d);
        venda_diaria.add(total_de_lucro_bruto_d);
        venda_diaria.add(total_de_lucro_liquido_d);

        painel_logistica.add(painel_de_vendas);
    }

    private void atualizar_dados_do_rendimento(byte mode, GregorianCalendar calendar) {
        int qnt_venda = 0;
        float lucro_b = 0;
        float lucro_l = 0;

        try {
            if (mode == 0) {
                Date inicio = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 7)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                Date fim = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                    qnt_venda++;
                    lucro_b += p.getPedido_vl_tot();
                    lucro_l += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                }
                inicio = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 14)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                fim = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 7)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                    qnt_venda--;
                    lucro_b -= p.getPedido_vl_tot();
                    lucro_l -= MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                }
                int temp_qnt_venda = 0;
                float temp_lucro_b = 0;
                float temp_lucro_l = 0;

                try {
                    temp_qnt_venda = qnt_venda;
                    temp_lucro_b = lucro_b;
                    temp_lucro_l = lucro_l;
                    for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                        qnt_venda--;
                        lucro_b -= p.getPedido_vl_tot();
                        lucro_l -= MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                    }

                } catch (Exception e) {
                    qnt_venda = temp_qnt_venda;
                    lucro_b = temp_lucro_b;
                    lucro_l = temp_lucro_l;
                }
            } else if (mode == 1) {
                Date inicio = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 30)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                Date fim = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                    qnt_venda++;
                    lucro_b += p.getPedido_vl_tot();
                    lucro_l += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                }
                inicio = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 60)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                fim = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 30)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                    qnt_venda--;
                    lucro_b -= p.getPedido_vl_tot();
                    lucro_l -= MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                }
                int temp_qnt_venda = 0;
                float temp_lucro_b = 0;
                float temp_lucro_l = 0;

                try {
                    temp_qnt_venda = qnt_venda;
                    temp_lucro_b = lucro_b;
                    temp_lucro_l = lucro_l;
                    for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                        qnt_venda--;
                        lucro_b -= p.getPedido_vl_tot();
                        lucro_l -= MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                    }

                } catch (Exception e) {
                    qnt_venda = temp_qnt_venda;
                    lucro_b = temp_lucro_b;
                    lucro_l = temp_lucro_l;
                }
            } else {
                Date inicio = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 365)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                Date fim = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                    qnt_venda++;
                    lucro_b += p.getPedido_vl_tot();
                    lucro_l += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                }
                inicio = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 730)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                fim = converterData(
                        (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 365)
                        + "/"
                        + (calendar.get(GregorianCalendar.MONTH) - (-1))
                        + "/"
                        + (calendar.get(GregorianCalendar.YEAR))
                );
                int temp_qnt_venda = 0;
                float temp_lucro_b = 0;
                float temp_lucro_l = 0;

                try {
                    temp_qnt_venda = qnt_venda;
                    temp_lucro_b = lucro_b;
                    temp_lucro_l = lucro_l;
                    for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
                        qnt_venda--;
                        lucro_b -= p.getPedido_vl_tot();
                        lucro_l -= MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                    }

                } catch (Exception e) {
                    qnt_venda = temp_qnt_venda;
                    lucro_b = temp_lucro_b;
                    lucro_l = temp_lucro_l;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            indice_de_vendas_texto.setText("" + qnt_venda);
            indice_de_lucro_b_texto.setText("" + lucro_b);
            indice_de_lucro_l_texto.setText("" + lucro_l);
            if (Integer.parseInt(indice_de_vendas_texto.getText()) >= 0) {
                indice_de_vendas_texto.setForeground(new Color(0, 120, 0));
                img_indice_de_vendas.setIcon(new ImageIcon(getClass().getResource("seta_positiva.png")));
            } else {
                indice_de_vendas_texto.setForeground(new Color(120, 0, 0));
                img_indice_de_vendas.setIcon(new ImageIcon(getClass().getResource("seta_negativa.png")));
            }
            if (Float.parseFloat(indice_de_lucro_b_texto.getText()) >= 0) {
                indice_de_lucro_b_texto.setForeground(new Color(0, 120, 0));
                img_indice_de_lucro_b.setIcon(new ImageIcon(getClass().getResource("seta_positiva.png")));
            } else {
                indice_de_lucro_b_texto.setForeground(new Color(120, 0, 0));
                img_indice_de_lucro_b.setIcon(new ImageIcon(getClass().getResource("seta_negativa.png")));
            }
            if (Float.parseFloat(indice_de_lucro_l_texto.getText()) >= 0) {
                indice_de_lucro_l_texto.setForeground(new Color(0, 120, 0));
                img_indice_de_lucro_l.setIcon(new ImageIcon(getClass().getResource("seta_positiva.png")));
            } else {
                indice_de_lucro_l_texto.setForeground(new Color(120, 0, 0));
                img_indice_de_lucro_l.setIcon(new ImageIcon(getClass().getResource("seta_negativa.png")));
            }
        }
    }

    private void inicializa_painel_de_rendimento() {
        painel_de_rendimento = new JPanel(null);
        painel_de_rendimento.setBounds(10, 300, 880, 170);
        painel_de_rendimento.setBorder(BorderFactory.createTitledBorder(b, "RENDIMENTO", 1, 1, f));

        periodo = new JComboBox();

        periodo.addItem("semana atual x semana passada");
        periodo.addItem("mês atual x mês passado");
        periodo.addItem("ano atual x ano passado");
        periodo.setBounds(10, 35, 300, 22);

        indice_de_vendas = new JPanel(null);
        indice_de_lucro_b = new JPanel(null);
        indice_de_lucro_l = new JPanel(null);
        indice_de_vendas.setBorder(BorderFactory.createTitledBorder(b, "Indice de Vendas"));
        indice_de_lucro_b.setBorder(BorderFactory.createTitledBorder(b, "Indice de Lucro Bruto"));
        indice_de_lucro_l.setBorder(BorderFactory.createTitledBorder(b, "Indice de Lucro Liquido"));
        indice_de_vendas.setSize(new Dimension(280, 90));
        indice_de_lucro_b.setSize(new Dimension(280, 90));
        indice_de_lucro_l.setSize(new Dimension(280, 90));
        indice_de_vendas.setLocation(10, 65);
        indice_de_lucro_b.setLocation(300, 65);
        indice_de_lucro_l.setLocation(590, 65);

        indice_de_vendas_texto = new JLabel("0");
        indice_de_lucro_b_texto = new JLabel("0");
        indice_de_lucro_l_texto = new JLabel("0");

        img_indice_de_vendas = new JLabel(new ImageIcon(getClass().getResource("seta_positiva.png")));
        img_indice_de_lucro_b = new JLabel(new ImageIcon(getClass().getResource("seta_positiva.png")));
        img_indice_de_lucro_l = new JLabel(new ImageIcon(getClass().getResource("seta_positiva.png")));

        img_indice_de_vendas.setBounds(200, 20, 64, 64);
        img_indice_de_lucro_b.setBounds(200, 20, 64, 64);
        img_indice_de_lucro_l.setBounds(200, 20, 64, 64);

        indice_de_vendas_texto.setFont(f);
        indice_de_lucro_b_texto.setFont(f);
        indice_de_lucro_l_texto.setFont(f);

        indice_de_vendas_texto.setBounds(20, 20, 270, 50);
        indice_de_lucro_b_texto.setBounds(20, 20, 270, 50);
        indice_de_lucro_l_texto.setBounds(20, 20, 270, 50);

        indice_de_vendas.add(img_indice_de_vendas);
        indice_de_lucro_b.add(img_indice_de_lucro_b);
        indice_de_lucro_l.add(img_indice_de_lucro_l);

        indice_de_vendas.add(indice_de_vendas_texto);
        indice_de_lucro_b.add(indice_de_lucro_b_texto);
        indice_de_lucro_l.add(indice_de_lucro_l_texto);

        painel_de_rendimento.add(periodo);
        painel_de_rendimento.add(indice_de_vendas);
        painel_de_rendimento.add(indice_de_lucro_b);
        painel_de_rendimento.add(indice_de_lucro_l);

        painel_logistica.add(painel_de_rendimento);
    }
}
