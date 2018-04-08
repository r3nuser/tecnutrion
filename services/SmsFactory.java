package services;

import bean.Telefone;
import br.com.facilitamovel.bean.Retorno;
import br.com.facilitamovel.bean.SmsMultiplo;
import br.com.facilitamovel.service.CheckCredit;
import br.com.facilitamovel.service.SendMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sql.Sql;

/**
 *
 * @author renan
 */
//SMS Factory using FacilitaMovel
public class SmsFactory {

    String facilita_user;
    String facilita_pass;
    String db_user;
    String db_pass;

    public SmsFactory(String db_user, String db_pass) {
        facilita_user = "";
        facilita_pass = "";
        this.db_user = db_user;
        this.db_pass = db_pass;
    }

    public String checkCredits() {
        try {
            return "" + CheckCredit.checkRealCredit(facilita_user, facilita_pass);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    public void SendSMS(String mensagem) {
        SmsMultiplo sms = new SmsMultiplo();
        sms.setUser(facilita_user);
        sms.setPassword(facilita_pass);

        List<String> nmbs = get_numbers();

        sms.setDestinatarios(nmbs);
        sms.setMessage(mensagem);
        try {
            Retorno retorno = SendMessage.multipleSend(sms);
            System.out.println("Código:"+ retorno.getCodigo());
            System.out.println("Descrição:" + retorno.getMensagem());
            JOptionPane.showMessageDialog(null,"Mensagens enviada com sucesso ! Código:"+ retorno.getCodigo());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<String> get_numbers() {
        ArrayList<String> nmbs;

        try {
            nmbs = new ArrayList<String>();
            Connection con = Sql.getConnection(db_user, db_pass);
            PreparedStatement stmt = con.prepareStatement(""
                    + "select ddd,antesh,depoish from telefone;");
            ResultSet rs;
            rs = stmt.executeQuery();
            while (rs.next()) {
                Telefone cel = new Telefone();
                cel.setDdd(rs.getString("ddd"));
                cel.setAntesh(rs.getString("antesh"));
                cel.setDepoish(rs.getString("depoish"));
                if (Integer.parseInt(cel.getAntesh()) > 10000
                        && Integer.parseInt(cel.getDepoish()) > 999
                        && Integer.parseInt(cel.getDdd()) > 1) {
                    String number = cel.getDdd() + cel.getAntesh() + cel.getDepoish();
                    nmbs.add(number);
                }
            }
            return nmbs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Algo errado ocorreu :(, erro: " + e);
        }
        return null;
    }
}
