package gui.abas;

import bean.Pedido;
import dao.MiscDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class Painel_inicio extends JPanel {

    private String username;
    private String password;

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
        f = new java.awt.Font("Dialog", 1, 20);

        setLayout(new BorderLayout());
        painel_principal = new JPanel(new BorderLayout());
        painel_principal.setPreferredSize(new Dimension(1024, 3000));
        painel_principal.setBorder(b);

        inicializa_itens_painel_logistica();
        scroll = new JScrollPane(painel_principal);
        add(scroll);

        setVisible(true);
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

            painel_logistica_anual(calendar);
            painel_logistica_mensal(calendar);
            painel_logistica_diario(calendar);
        });
        painel_logistica.setPreferredSize(new Dimension(900, 3000));
        painel_logistica.setBorder(b);
        painel_logistica.add(atualizacao);
        painel_logistica.add(atualizar_dados);
        painel_principal.add(painel_logistica, BorderLayout.LINE_START);
        inicializa_painel_de_vendas();
        inicializa_painel_de_rendimento();
    }

    private void painel_logistica_anual(GregorianCalendar calendar) {
        Date inicio = converterData(
                calendar.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH) - (-1))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR) - 1)
        );
        Date fim = converterData(
                calendar.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH) - (-1))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR))
        );

        System.out.println(inicio + " - " + fim);

        int quantidade = 0;
        float pedido_vl_tot = 0;
        float pedido_lucro_tot = 0;

        for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
            quantidade++;
            pedido_vl_tot += p.getPedido_vl_tot();
            pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
        }

        total_de_vendas_a.setText("Total de Pedidos: " + quantidade);
        total_de_lucro_bruto_a.setText("Total de Lucro Bruto:    R$:" + pedido_vl_tot);
        total_de_lucro_liquido_a.setText("Total de Lucro Liquido: R$:" + pedido_lucro_tot);
    }

    private void painel_logistica_mensal(GregorianCalendar calendar) {
        Date inicio = converterData(
                calendar.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR))
        );
        Date fim = converterData(
                calendar.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH) - (-1))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR))
        );

        System.out.println(inicio + " - " + fim);

        int quantidade = 0;
        float pedido_vl_tot = 0;
        float pedido_lucro_tot = 0;

        for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
            quantidade++;
            pedido_vl_tot += p.getPedido_vl_tot();
            pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
        }

        total_de_vendas_m.setText("Total de Pedidos: " + quantidade);
        total_de_lucro_bruto_m.setText("Total de Lucro Bruto:    R$:" + pedido_vl_tot);
        total_de_lucro_liquido_m.setText("Total de Lucro Liquido: R$:" + pedido_lucro_tot);
    }

    private void painel_logistica_diario(GregorianCalendar calendar) {
        Date inicio = converterData(
                (calendar.get(GregorianCalendar.DAY_OF_MONTH) - 1)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH) - (-1))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR))
        );
        Date fim = converterData(
                calendar.get(GregorianCalendar.DAY_OF_MONTH)
                + "/"
                + (calendar.get(GregorianCalendar.MONTH) - (-1))
                + "/"
                + (calendar.get(GregorianCalendar.YEAR))
        );

        System.out.println(inicio + " - " + fim);

        int quantidade = 0;
        float pedido_vl_tot = 0;
        float pedido_lucro_tot = 0;

        for (Pedido p : MiscDAO.relatorio_por_data(username, password, inicio, fim)) {
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

        indice_de_vendas_texto = new JLabel("-1000000");
        indice_de_lucro_b_texto = new JLabel("500000000");
        indice_de_lucro_l_texto = new JLabel("-500000000");

        img_indice_de_vendas = new JLabel(new ImageIcon(getClass().getResource("seta_positiva.png")));
        img_indice_de_lucro_b = new JLabel(new ImageIcon(getClass().getResource("seta_positiva.png")));
        img_indice_de_lucro_l = new JLabel(new ImageIcon(getClass().getResource("seta_positiva.png")));

        img_indice_de_vendas.setBounds(200, 20, 64, 64);
        img_indice_de_lucro_b.setBounds(200, 20, 64, 64);
        img_indice_de_lucro_l.setBounds(200, 20, 64, 64);

        if (Integer.parseInt(indice_de_vendas_texto.getText()) >= 0) {
            indice_de_vendas_texto.setForeground(new Color(0, 120, 0));
            img_indice_de_vendas.setIcon(new ImageIcon(getClass().getResource("seta_positiva.png")));
        } else {
            indice_de_vendas_texto.setForeground(new Color(120, 0, 0));
            img_indice_de_vendas.setIcon(new ImageIcon(getClass().getResource("seta_negativa.png")));
        }
        if (Integer.parseInt(indice_de_lucro_b_texto.getText()) >= 0) {
            indice_de_lucro_b_texto.setForeground(new Color(0, 120, 0));
            img_indice_de_lucro_b.setIcon(new ImageIcon(getClass().getResource("seta_positiva.png")));
        } else {
            indice_de_lucro_b_texto.setForeground(new Color(120, 0, 0));
            img_indice_de_lucro_b.setIcon(new ImageIcon(getClass().getResource("seta_negativa.png")));
        }
        if (Integer.parseInt(indice_de_lucro_l_texto.getText()) >= 0) {
            indice_de_lucro_l_texto.setForeground(new Color(0, 120, 0));
            img_indice_de_lucro_l.setIcon(new ImageIcon(getClass().getResource("seta_positiva.png")));
        } else {
            indice_de_lucro_l_texto.setForeground(new Color(120, 0, 0));
            img_indice_de_lucro_l.setIcon(new ImageIcon(getClass().getResource("seta_negativa.png")));
        }

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
