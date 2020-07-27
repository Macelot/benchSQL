/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacote;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import java.awt.HeadlessException;
import java.io.File;
import java.net.UnknownHostException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;  
import java.sql.Statement;

/**
 *
 * @author marcelojosuetelles
 */
public class conexao {
    public static String sistemaIp, sistemaPorta, sistemaUser, sistemaSenha, sistemaRegistro;
    public static Connection con;
    
    //atualmente usada
    public static String encM(String recebido){
        String resultado2 = "";
        int i;
        for (i=0; i<recebido.length(); i++){
            resultado2 = resultado2 + (String.valueOf((int)recebido.charAt(i)));
        }
        return resultado2;
    }
        //atualmente usada
    public static String decM(String recebido){
        String resultado2 = "";
        int tamanho = recebido.length();
        int tamanho2;
        int i;
        int dois;
        if (tamanho%2==0){
            tamanho2=(tamanho/2);
        }else{
            tamanho2=(tamanho/2)+1;
        }
        //JOptionPane.showMessageDialog(null, recebido);
        for (i=0; i<tamanho2; i++){
                dois = Integer.parseInt(recebido.substring((i*2),((i*2)+2)));
                resultado2 = resultado2 + (char)dois;
        }
        return resultado2;
     }
     public static Connection conectaBancos(String qual)throws Exception{
         String url,driver;
         try {
            sistemaIp = telaMySQL.txtIp.getText();
            sistemaPorta =  telaMySQL.txtPorta.getText();
            sistemaUser =  telaMySQL.txtUsuario.getText();
            sistemaSenha =  telaMySQL.txtSenha.getText();
            if (qual.equals("MySQL")){
                driver = "com.mysql.jdbc.Driver"; //loga no banco mysql mas será feito os testes em um novo banco bench001
                url = "jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/mysql";
                Class.forName(driver);
                con = DriverManager.getConnection(url, sistemaUser, sistemaSenha);
            }
            if (qual.equals("PostgreSQL")){
                driver = "org.postgresql.Driver"; //tudo sera feito no banco postgres pois não consegui criar banco condicionalmente
                url      = "jdbc:postgresql://"+sistemaIp+":"+sistemaPorta+"/postgres";
                Class.forName(driver);
                con = DriverManager.getConnection(url, sistemaUser, sistemaSenha);
            }
            if (qual.equals("SQLServer")){
                driver = "net.sourceforge.jtds.jdbc.Driver"; //Classe do driver JDBC
                url      = "jdbc:jtds:sqlserver://"+sistemaIp+":"+sistemaPorta+"/tempdb";
                Class.forName(driver);
                con = DriverManager.getConnection(url,sistemaUser,sistemaSenha);
            }
            if (qual.equals("Oracle11g")){
                driver = "oracle.jdbc.driver.OracleDriver"; //xe é a instancia que será acessada
                url      = "jdbc:oracle:thin:@"+sistemaIp+":"+sistemaPorta+":xe";
                Class.forName(driver);            
                con = DriverManager.getConnection(url,sistemaUser,sistemaSenha);
            }
            if (qual.equals("FireBird")){
                driver = "org.firebirdsql.jdbc.FBDriver";
                Class.forName(driver);  
                url = "jdbc:firebirdsql:"+sistemaIp+"/"+sistemaPorta+":C:/fb.gdb";
                con =  DriverManager.getConnection(url,sistemaUser,sistemaSenha);  
            }
            if (qual.equals("SQLServerIntegrated")){
                //driver = "org.firebirdsql.jdbc.FBDriver";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                SQLServerDataSource dataSource = new SQLServerDataSource();
                dataSource.setIntegratedSecurity(true);//<<<< Será utilizado para que a conexão seja feita sem login e senha
                dataSource.setServerName(sistemaIp+"\\SQLEXPRESS");
                dataSource.setDatabaseName("tempdb");
                con = dataSource.getConnection();
            }
            
            }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro na comunicação "+sistemaPorta +" "+sistemaUser+" "+sistemaSenha+ e.getMessage() );
            
            }
        return con;
    } 
     public static Connection conectaBancos2(String qual)throws Exception{
         String url,driver;
         try {
            sistemaIp = telaBancos.txtIp.getText();
            sistemaPorta =  telaBancos.txtPorta.getText();
            sistemaUser =  telaBancos.txtUsuario.getText();
            sistemaSenha =  telaBancos.txtSenha.getText();
            if (qual.equals("MySQL")){
                driver = "com.mysql.jdbc.Driver"; //loga no banco mysql mas será feito os testes em um novo banco bench001
                url = "jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/mysql";
                Class.forName(driver);
                con = DriverManager.getConnection(url, sistemaUser, sistemaSenha);
            }
            if (qual.equals("PostgreSQL")){
                driver = "org.postgresql.Driver"; //tudo sera feito no banco postgres pois não consegui criar banco condicionalmente
                url      = "jdbc:postgresql://"+sistemaIp+":"+sistemaPorta+"/postgres";
                Class.forName(driver);
                con = DriverManager.getConnection(url, sistemaUser, sistemaSenha);
            }
            if (qual.equals("SQLServer")){
                driver = "net.sourceforge.jtds.jdbc.Driver"; //Classe do driver JDBC
                url      = "jdbc:jtds:sqlserver://"+sistemaIp+":"+sistemaPorta+"/tempdb";
                Class.forName(driver);
                con = DriverManager.getConnection(url,sistemaUser,sistemaSenha);
            }
            if (qual.equals("Oracle11g")){
                driver = "oracle.jdbc.driver.OracleDriver"; //xe é a instancia que será acessada
                url      = "jdbc:oracle:thin:@"+sistemaIp+":"+sistemaPorta+":xe";
                Class.forName(driver);            
                con = DriverManager.getConnection(url,sistemaUser,sistemaSenha);
            }
            if (qual.equals("FireBird")){
                driver = "org.firebirdsql.jdbc.FBDriver";
                Class.forName(driver);  
                url = "jdbc:firebirdsql://"+sistemaIp+":"+sistemaPorta+"/C:\\fb.fdb";
                con =  DriverManager.getConnection(url,sistemaUser,sistemaSenha);  
            }
            if (qual.equals("SQLServerIntegrated")){
                //driver = "org.firebirdsql.jdbc.FBDriver";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                SQLServerDataSource dataSource = new SQLServerDataSource();
                dataSource.setIntegratedSecurity(true);//<<<< Será utilizado para que a conexão seja feita sem login e senha
                dataSource.setServerName(sistemaIp+"\\SQLEXPRESS");
                dataSource.setDatabaseName("tempdb");
                con = dataSource.getConnection();
            }
            
            
            }catch (ClassNotFoundException | SQLException | NumberFormatException | HeadlessException e){
                e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro na comunicação "+sistemaPorta +" "+sistemaUser+" "+sistemaSenha+ e.getMessage());
        }
        return con;
    } 


    public static Connection conectaBanco()throws Exception{
        try {
            sistemaIp = telaMySQL.txtIp.getText();
            sistemaPorta =  telaMySQL.txtPorta.getText();
            sistemaUser =  telaMySQL.txtUsuario.getText();
            sistemaSenha =  telaMySQL.txtSenha.getText();
            String driver = "com.mysql.jdbc.Driver"; //Classe do driver JDBC
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:mysql://"+sistemaIp+":"+sistemaPorta+"/mysql", sistemaUser, sistemaSenha);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro na comunicação "+sistemaPorta +" "+sistemaUser+" "+sistemaSenha+ e.getMessage() );
        }
            return con;
    }
    

    public static Connection conectaBancoPostgreSQL()throws Exception{ 
        try {
            sistemaIp = telaPostgreSQL.txtIp.getText();
            sistemaPorta =  telaPostgreSQL.txtPorta.getText();
            sistemaUser =  telaPostgreSQL.txtUsuario.getText();
            sistemaSenha =  telaPostgreSQL.txtSenha.getText();
            String driver = "org.postgresql.Driver"; //Classe do driver JDBC
            String url      = "jdbc:postgresql://"+sistemaIp+":"+sistemaPorta+"/sqlMagHerancaPostgreSQL2";
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, sistemaUser, sistemaSenha);

            //con = DriverManager.getConnection("jdbc:postgrsql://"+sistemaIp+":"+sistemaPorta+"/postgres", sistemaUser, sistemaSenha);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro na comunicação "+sistemaPorta +" "+sistemaUser+" "+sistemaSenha+ e.getMessage() );
        }
        return con;
    }
    
    
}

