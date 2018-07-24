package services;

import bean.Cliente;
import bean.Pedido;
import dao.MiscDAO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class BackupFactory {

    private String database;
    private String username;
    private String password;

    public BackupFactory(String database, String username, String password) {
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void makeBackup() {
        int numerodobackup = 0;
        String dir = "c:/TecnutrionBackup";
        File diretorio = new File(dir);
        File bck = new File(dir + "/backup000000.sql");
// os zeros é para diferenciar um backup do outro
        // Cria diretório
        if (!diretorio.isDirectory()) {
            new File(dir).mkdir();
        } else {
        }
        // Cria Arquivo de Backup
        try {
            if (!bck.isFile()) {
                Runtime.getRuntime().exec("cmd /c mysqldump -u " + username + " -p" + password + " " + database + " > " + dir + "/backup000000.sql");

            } else {
                while (bck.isFile()) {
                    numerodobackup++;
                    bck = new File(dir + "/backup000000" + numerodobackup + ".sql");
                }
                System.out.println(bck);
                Runtime.getRuntime().exec("cmd /c mysqldump -u " + username + " -p" + password + " " + database + " > " + bck);
            }
        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    public void sendReport() {
        GregorianCalendar calendar = new GregorianCalendar();

        String dia_f = (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                + "/"
                + (calendar.get(GregorianCalendar.MONTH)+1)
                + "/"
                + (calendar.get(GregorianCalendar.YEAR));

        String dia_i = (calendar.get(GregorianCalendar.DAY_OF_MONTH))
                + "/"
                + (calendar.get(GregorianCalendar.MONTH)-1)
                + "/"
                + (calendar.get(GregorianCalendar.YEAR));

        Date dia_fim = converterData(dia_f);
        Date dia_inicio = converterData(dia_i);

        File html = new File("");

        ArrayList<Pedido> dados_relatorio = MiscDAO.relatorio_por_data("root", "root", dia_inicio, dia_fim);

        try {
            FileWriter arq = new FileWriter("relatorio.html");
            PrintWriter gravarArq = new PrintWriter(arq);

            String dados_tabela = "";
            int tamanho = 137;
            int quantidade = 0;
            float pedido_vl_tot = 0;
            float pedido_lucro_tot = 0;

            for (int i = 0; i < dados_relatorio.size(); i++) {
                Pedido p = dados_relatorio.get(i);
                Cliente c = MiscDAO.search_cliente_por_id(username, password, MiscDAO.get_id_cliente_pedido_item_por_fk(username, password, p.getCod_pedido()));
                dados_tabela += "<tr>"
                        + "<td>" + p.getCod_pedido()
                        + "</td>"
                        + "<td>" + p.getDt_pedido()
                        + "</td>"
                        + "<td>" + c.getId()
                        + "</td>"
                        + "<td>" + c.getNome()
                        + "</td>"
                        + "<td>" + MiscDAO.get_quantidade_de_itens_pedido(username, password, p.getCod_pedido())
                        + "</td>"
                        + "<td>" + p.getPedido_vl_tot()
                        + "</td>"
                        + "<td>" + MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido())
                        + "</td>"
                        + "</tr>\n";

                tamanho += 11;
                pedido_vl_tot += p.getPedido_vl_tot();
                pedido_lucro_tot += MiscDAO.get_lucro_liquido_pedido(username, password, p.getCod_pedido());
                quantidade++;
            }
            String texto;
            texto = "<!DOCTYPE html>\n"
                    + "<meta charset=\"utf-8\">\n"
                    + "<html>\n"
                    + "\n"
                    + "<head>\n"
                    + "	<style>\n"
                    + "\n"
                    + "	\n"
                    + "	header.cabecalho{\n"
                    + "		text-align : center;\n"
                    + "		border-bottom-style: solid;\n"
                    + "		border-width: 1px;\n"
                    + "		font-size:25px;\n"
                    + "		background-color:rgb(220,220,220);\n"
                    + "		border-style: solid;\n"
                    + "		border-width:1px;\n"
                    + "	}\n"
                    + "\n"
                    + "	h1{\n"
                    + "		font-size:15px;\n"
                    + "	}\n"
                    + "	\n"
                    + "	valores{\n"
                    + "		border-bottom-style: solid;\n"
                    + "\n"
                    + "		border-width:1px;\n"
                    + "		height:70px;\n"
                    + "		width:99.8%;\n"
                    + "		position:absolute;\n"
                    + "		background-color : rgb(255,255,255);\n"
                    + "		border-style: solid;\n"
                    + "		border-width:1px;\n"
                    + "	}\n"
                    + "	texto{\n"
                    + "		height:60px;\n"
                    + "		width:30%;\n"
                    + "		text-align : center;\n"
                    + "		border-style: solid;\n"
                    + "		border-width: 1px;\n"
                    + "		border-color:rgb(0,0,0);\n"
                    + "		font-size:22px;\n"
                    + "		position:absolute;\n"
                    + "		margin-left:3%;\n"
                    + "		margin-top:0.5%;\n"
                    + "		background-color :rgb(206, 4, 159);\n"
                    + "		color: rgb(255,255,255);\n"
                    + "		font-weight: bold;\n"
                    + "		border-style: solid;\n"
                    + "		border-width:1px;\n"
                    + "	}\n"
                    + "	texto1{\n"
                    + "		height:60px;\n"
                    + "		width:30%;\n"
                    + "		text-align : center;\n"
                    + "		border-style: solid;\n"
                    + "		border-width: 1px;\n"
                    + "		border-color:rgb(0,0,0);\n"
                    + "		font-size:22px;\n"
                    + "		position:absolute;\n"
                    + "		margin-left:34.5%;\n"
                    + "		margin-top:0.5%;\n"
                    + "		background-color :rgb(206, 4, 159);\n"
                    + "		color: rgb(255,255,255);\n"
                    + "		font-weight: bold;\n"
                    + "	}\n"
                    + "\n"
                    + "	texto2{\n"
                    + "		height:60px;\n"
                    + "		width:30%;\n"
                    + "		text-align : center;\n"
                    + "		border-style: solid;\n"
                    + "		border-width: 1px;\n"
                    + "		font-size:22px;\n"
                    + "		border-color:rgb(0,0,0);\n"
                    + "		position:absolute;\n"
                    + "		margin-left:66%;\n"
                    + "		margin-top:0.5%;\n"
                    + "		background-color :rgb(206, 4, 159);\n"
                    + "		color: rgb(255,255,255);\n"
                    + "		font-weight: bold;\n"
                    + "	}\n"
                    + "	table{\n"
                    + "		position:absolute;\n"
                    + "		margin-top:75px;\n"
                    + "		border:1px;\n"
                    + "		width:100%;\n"
                    + "		border-style:solid;\n"
                    + "		border-color:rgb(206, 4, 159);\n"
                    + "		\n"
                    + "		border-collapse: collapse;\n"
                    + "		text-align:center;\n"
                    + "	}\n"
                    + "	div.margem{\n"
                    + "		\n"
                    + "		height: " + tamanho + "px;\n"
                    + "		width: 90%;\n"
                    + "		border-style:solid;\n"
                    + "		border:1px;\n"
                    + "             border-color:rgb(0,0,0);"
                    + "		position: absolute;\n"
                    + "		margin: 0% 0% 0% 4%;\n"
                    + "		\n"
                    + "\n"
                    + "	}\n"
                    + "	\n"
                    + "	</style>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "	<div class=\"margem\">\n"
                    + "		<header class=\"cabecalho\"> RELATÓRIO DE VENDAS (" + dia_i + " - " + dia_f + ")</header>\n"
                    + "		<valores>\n"
                    + "			<texto>Lucro valor bruto: R$: " + pedido_vl_tot + "</texto>\n"
                    + "\n"
                    + "			<texto1>Lucro valor líquido: R$:" + pedido_lucro_tot + "</texto1>\n"
                    + "			<texto2>Número de vendas:  " + quantidade + "</texto2>\n"
                    + "			<table border=\"1px\" bordercolor=\"#ff26fb\">\n"
                    + "				<tr>\n"
                    + "					<th>Id Venda</th>\n"
                    + "					<th>Data Da Venda</th>\n"
                    + "					<th>ID Cliente</th>\n"
                    + "					<th>Nome Do Cliente</th>\n"
                    + "					<th>Itens Comprados</th>\n"
                    + "					<th>Lucro Bruto</th>\n"
                    + "					<th>Lucro Líquido</th>\n"
                    + "				</tr>\n"
                    + "				\n"
                    + "" + dados_tabela
                    + "				\n"
                    + "				\n"
                    + "			</table>\n"
                    + "		</valores>\n"
                    + "		\n"
                    + "\n"
                    + "	</div>\n"
                    + "\n"
                    + "</body>\n"
                    + "</html>";
            System.out.println(texto);
            gravarArq.print(texto);
            arq.close();
            html = new File("relatorio.html");

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(html.getAbsolutePath());

        EmailFactory email = new EmailFactory("root", "root");
        try {
            email.SendEmailAnexo(
                    "visualnutrion.contato@gmail.com",
                    "visualnutrion.contato@gmail.com",
                    "Relatorio " + dia_i + " | " + dia_f,
                    "Qualquer erro favor entrar em contato no email: renan_battlenet@hotmail.com",
                    html.getAbsolutePath()
            );
            email.SendEmailAnexo(
                    "visualnutrion.contato@gmail.com",
                    "saravidros@yahoo.com.br",
                    "Relatorio " + dia_i + " | " + dia_f,
                    "Qualquer erro favor entrar em contato no email: renan_battlenet@hotmail.com",
                    html.getAbsolutePath()
            );
        } catch (Exception e) {
            System.out.println(e);
        }

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
