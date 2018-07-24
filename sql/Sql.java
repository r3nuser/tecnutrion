package sql;
/*
*CLASSE RESPONSÁVEL POR REALIZAR A CONEXÃO DO BANCO E 
*POR FECHAR A CONEXÃO DO MESMO.
* AUTOR: RENAN

*/
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Sql {
    //METODO QUE FAZ A CHAMADA DA CONEXÃO COM BANCO
    //NECESSÁRIO O NOME DE USUÁRIO E SENHA SER PASSADOS COMO ARGUMENTOS
    //PARA A CHAMADA DO METODO
    public static Connection getConnection(String username, String password) {
        try {
            //LOCALIZAÇÃO DO DRIVER
            String driver = "com.mysql.jdbc.Driver";
            //LOCALIZAÇÃO DO BANCO
            String url = "jdbc:mysql://127.0.0.1:3306/visualnutrion";
            //CARREGANDO DRIVER
            Class.forName(driver);
            //PEGANDO CONEXÃO DO GERENCIADOR DE DRIVERS
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println(username + " Connected !");
            return con;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    //SOBRECARGA DE METODOS RESPONSÁVEIS POR FECHAR A CONEXÃO
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
