/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacote;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author marcelojosuetelles
 *
 * 
 */
public class funcoes {
    boolean teste = true;
    boolean teste2 = true;
    public static String logado;
    public static String idColaboradorFKlogado;
    public static String nomeLogado,emailLogado,nomeEmpresa;

    //função usada para obter a data e hora do servidor do MySQL
    //deve ser usada em todos os insert`s
    //para usar ela basta chamar: dataCadastro = funcoes.buscaDataHora();
    public static String buscaDataHora()throws Exception{
        String dataHora = "";
        try{
            Statement stmt = conexao.conectaBanco().createStatement();
            String selecDataHora = "select now();";
            ResultSet rs = stmt.executeQuery(selecDataHora);
            while(rs.next()){
                //System.out.println("campo: " + rs.getString("now()"));
                dataHora = rs.getString("now()");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi possivel obter a data e hora do banco. ["+dataHora+"] . "+e.getMessage());
        }finally{
            return dataHora;
        }

    }
    public static String buscaData()throws Exception{
        String dataHora = "";
        try{
            Statement stmt = conexao.conectaBanco().createStatement();
            String selecDataHora = "SELECT DATE_FORMAT( current_date( ) , '%d/%m/%Y' ) AS minhaData;";
            ResultSet rs = stmt.executeQuery(selecDataHora);
            while(rs.next()){
                //System.out.println("campo: " + rs.getString("now()"));
                dataHora = rs.getString("minhaData");

            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Não foi possivel obter a data e hora do banco. ["+dataHora+"] . "+e.getMessage());
        }finally{
            return dataHora;
        }

    }

    //funcao limpaTela usada para limpar todos o campos do formulário
    //para usar ela basta chamar: new funcoes().limpaTela(this);
    public void limpaTela(JInternalFrame Frame) {
        for  (int i=0; i < Frame.getContentPane().getComponentCount(); i++) {
            //varre todos os componentes
            Component c = Frame.getContentPane().getComponent(i);
            if (c instanceof JTextField) {
                JTextField field = (JTextField) c;
                field.setText("");
            }
        }
    }

    public static String trocaVirgulaPorPonto(String recebe){
        String resposta = "";
        if  (recebe!= null){
            resposta =  recebe.replace(",", ".");
        }else{
             resposta = "";
        }

        return(resposta);

    }

    public static String trocaPontoPorVirgula(String recebe){
        String resposta ="";
        if (recebe!= null){
              resposta = recebe.replace(".", ",");
        }else{
            resposta = "";
        }
        return(resposta);

    }
    public static String pegaNumeros(String recebe){
        int tam,i,pos=0;
        String respFinal="";
        char[] resposta = new char[100];

        if (recebe != null){
            tam = recebe.length();
            for(i=0; i<tam; i++){
                if ((recebe.charAt(i)=='1') || (recebe.charAt(i)=='2') || (recebe.charAt(i)=='3') || (recebe.charAt(i)=='4') || (recebe.charAt(i)=='5') || (recebe.charAt(i)=='6') || (recebe.charAt(i)=='7') || (recebe.charAt(i)=='8') || (recebe.charAt(i)=='9') || (recebe.charAt(i)=='0') ){
                    resposta[pos] = recebe.charAt(i);
                    pos++;
                }   
            }
            for (i=0; i<pos; i++){
                    respFinal=respFinal+Character.toString(resposta[i]);
                }

        }
        return(respFinal);

    }
    public static String limpaApostrofo(String recebe){
        String resposta ="";
        if (recebe!= null){
            resposta = recebe.replaceAll("'", "\\\\'");
        }else{
            resposta = "";
        }
        return(resposta);

    }

    public static int converteInteiro(String recebe){
        String temp="";
        int resposta=0;
        if (recebe!= null){
            temp = recebe.replaceAll("'", "\\\\'");
            int num;
            try {
                resposta=Integer.parseInt(temp);

            }catch (Exception e){
            e.printStackTrace();

            }
        }else{
            resposta = 0;
        }

        return(resposta);

    }


        public static String dataBrasilToUs(String recebe){
        String resposta ="",temp="";
        if (recebe!= null){
            temp = recebe.replaceAll("'", "\\\\'");
        }else{
            temp = "";
            return (temp);
        }

        //resposta = temp.substring(6,10)+"-"+temp.substring(3,5)+"-"+temp.substring(0,2);

        resposta = temp;
        
        return (resposta);

    }
        
        public static int fazProgresso (long q,int i, int f){
            int resposta=0;
            resposta = (int) (q*100/f);
            
            return (resposta);
    }


}
