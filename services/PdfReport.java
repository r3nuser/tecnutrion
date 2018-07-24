/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Cliente;
import bean.Pedido;
import bean.Produto;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.MiscDAO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author renan
 */
public class PdfReport {

    private final String username;
    private final String password;

    public PdfReport(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Document createDocument() {
        return new Document();
    }

    public void ReportPDF(int mode) {
        GregorianCalendar calendar = new GregorianCalendar();
        String dia_f = "";
        String dia_i = "";
        if (mode == 0) {
            dia_f = (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                    + "/"
                    + (calendar.get(GregorianCalendar.MONTH) + 1)
                    + "/"
                    + (calendar.get(GregorianCalendar.YEAR));

            dia_i = (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                    + "/"
                    + (calendar.get(GregorianCalendar.MONTH) - 1)
                    + "/"
                    + (calendar.get(GregorianCalendar.YEAR));
        } else {
            dia_f = (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                    + "/"
                    + (calendar.get(GregorianCalendar.MONTH) + 1)
                    + "/"
                    + (calendar.get(GregorianCalendar.YEAR));
            dia_i = "01/01/2070";
        }
        Date dia_fim = converterData(dia_f);
        Date dia_inicio = converterData(dia_i);

        Document document = createDocument();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
            document.open();
            document.add(new Paragraph("Relatorio de " + dia_inicio + " - " + dia_fim));
            document.add(new Paragraph(getHr()));
            document.add(new Paragraph("Gerado em: "+dia_fim));
            document.add(new Paragraph(getHr()));
            Paragraph text = new Paragraph("VENDAS REALIZADAS");
            text.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(text);
            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(7);
            tabela.setWidthPercentage(100);
            //
            ArrayList<Pedido> dados_relatorio = MiscDAO.relatorio_por_data(username, password, dia_inicio, dia_fim);

            int quantidade = 0;
            float pedido_vl_tot = 0;
            float pedido_lucro_tot = 0;
            tabela.setSpacingAfter(2);
            tabela.addCell("ID Cliente:");
            tabela.addCell("NOME:");
            tabela.addCell("ID Pedido:");
            tabela.addCell("DATA:");
            tabela.addCell("QNT:");
            tabela.addCell("Vl Tot:");
            tabela.addCell("Pagamento:");
            for (int i = 0; i < dados_relatorio.size(); i++) {
                Pedido p = dados_relatorio.get(i);
                Cliente c = MiscDAO.search_cliente_por_id(username, password, MiscDAO.get_id_cliente_pedido_item_por_fk(username, password, p.getCod_pedido()));

                tabela.addCell(c.getId() + "");
                tabela.addCell(c.getNome());
                tabela.addCell(p.getCod_pedido() + "");
                tabela.addCell(p.getDt_pedido() + "");
                tabela.addCell(MiscDAO.get_quantidade_de_itens_pedido(username, password, p.getCod_pedido()) + "");
                tabela.addCell(p.getPedido_vl_tot() + "");
                tabela.addCell(p.getPagamento());

                pedido_vl_tot += p.getPedido_vl_tot();
                pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                quantidade++;
            }
            document.add(tabela);

            text = new Paragraph("TOTAIS E SOMATORIOS");
            text.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(text);
            document.add(new Paragraph(" "));
            tabela = new PdfPTable(2);
            tabela.setWidthPercentage(60);
            tabela.addCell("Valor Total dos Pedidos:");
            tabela.addCell("Qnt Total de Itens Vendidos:");
            tabela.addCell("R$: " + formatarCD(pedido_vl_tot));
            tabela.addCell("Valor: " + quantidade);
            document.add(tabela);

            document.add(new Paragraph(getHr()));
            document.add(new Paragraph(" "));
            text = new Paragraph("PRODUTOS DE CADA VENDA");
            text.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(text);

            for (int i = 0; i < dados_relatorio.size(); i++) {
                tabela = new PdfPTable(4);
                tabela.setWidthPercentage(100);

                tabela.addCell("ID:");
                tabela.addCell("Nome:");
                tabela.addCell("Preço Uni. Venda:");
                tabela.addCell("Quantidade:");

                ArrayList<Produto> dados_produto;
                Pedido p = dados_relatorio.get(i);
                Cliente c = MiscDAO.search_cliente_por_id(username, password, MiscDAO.get_id_cliente_pedido_item_por_fk(username, password, p.getCod_pedido()));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(c.getNome() + " - " + c.getEmail()));
                document.add(new Paragraph("Tipo de Pagamento: " + p.getPagamento()));
                document.add(new Paragraph("Valor Total: " + p.getPedido_vl_tot()));
                document.add(new Paragraph("Data: " + p.getDt_pedido()));
                document.add(new Paragraph("Desconto: " + p.getDesconto()));
                document.add(new Paragraph(" "));
                dados_produto = MiscDAO.get_produtos_contidos_pedido(username, password, p.getCod_pedido());
                for (int j = 0; j < dados_produto.size(); j++) {
                    Produto pro = dados_produto.get(j);

                    tabela.addCell(pro.getProduto_cod() + "");
                    tabela.addCell(pro.getProduto_nome());
                    tabela.addCell("R$ " + pro.getPreco_uni_venda());
                    tabela.addCell("" + pro.getFk_fornecedor_cod());
                }

                document.add(tabela);
                document.add(new Paragraph(getHr()));
            }

        } catch (DocumentException de) {
            JOptionPane.showMessageDialog(null, de);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, ioe);
        }

        document.close();

        EmailFactory email = new EmailFactory("root", "root");
        String periodo = mode==0?dia_i +" | " + dia_f:"Todo Periodo ate "+dia_f;
        try {
            email.SendEmailAnexo(
                    "visualnutrion.contato@gmail.com",
                    "visualnutrion.contato@gmail.com",
                    "Relatorio " + periodo,
                    "Qualquer erro favor entrar em contato no email: renan_battlenet@hotmail.com",
                    "report.pdf"
            );
            email.SendEmailAnexo(
                    "visualnutrion.contato@gmail.com",
                    "saravidros@yahoo.com.br",
                    "Relatorio " + periodo,
                    "Qualquer erro favor entrar em contato no email: renan_battlenet@hotmail.com",
                    "report.pdf"
            );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String formatarCD(float n) {

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(n) + "";
    }

    private String getHr() {
        return "______________________________________________________________________________";
    }

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
}
