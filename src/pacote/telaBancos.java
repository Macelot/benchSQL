/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * estadoTela.java
 *
 * Created on 26/02/2010, 10:17:22
 */
package pacote;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import pacote.funcoes.*;


/**
 *
 * @author marcelojosuetelles
 */
public class telaBancos extends javax.swing.JInternalFrame  {
    public String sistemaIp, sistemaPorta, sistemaUser, sistemaSenha, ativo;
    public int versao, versaoConsultada,cont;
    int conta=0;
    public static String qual="",grava;
    public int resultado,ini,fim;
    public static Integer limite,limiteCliente,limiteProfissao,limiteProduto,limiteTipoProduto,limiteSaldoProduto,limiteProdutoVenda,limiteVenda;
    public static long tempInicial,tempInicial1,tempInicial2,tempInicial3,tempInicial4,tempInicial5,tempInicial6;
    public static long tempFinal,tempFinal1,tempFinal2,tempFinal3,tempFinal4,tempFinal5,tempFinal6;
    public static long dif;
    public static String tempo;
    public static Thread t;
    public static boolean fimCliente,fimProfissao,fimProduto,fimTipoProduto,fimSaldoProduto,fimProdutoVenda,fimVenda;
    public static DB db;
    MongoClient mongo;
    DBCollection collection;

    /** Creates new form estadoTela */
    public telaBancos () {
        initComponents();
        fimCliente=false;
        fimProfissao=false;
        fimProduto=false;
        fimTipoProduto=false;
        fimSaldoProduto=false;
        fimProdutoVenda=false;
        fimVenda=false;
    }
   
    public class threadInsertCliente implements Runnable{
        public int onde;
        private int iCliente;
        
        @Override
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            Statement stmt;
            try {
                lblOque.setText("Inserindo Clientes");
                lblClientes.setText("aguardando...");
                tempInicial1 = System.currentTimeMillis();

                fimCliente=false;             
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limiteCliente);
                lblSaldoProduto.setText("aguardando...");
                int profissao;
                stmt=null;
                if(!cmbBanco.getSelectedItem().toString().equals("Mongo")){
                    stmt = conexao.conectaBancos2(cmbBanco.getSelectedItem().toString()).createStatement();
                }
                for (iCliente=1;iCliente<=limiteCliente;iCliente++){
                    jProgressBar1.setValue(iCliente);
                    profissao = 1 + (int)(Math.random() * limiteProfissao);

                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench002`.`clientes` (`id`, `nome`, `endereco`, `profissaoId`,`versao`,`ativo`) VALUES ('"+iCliente+"', 'Nome Completo "+iCliente+"', 'rua "+iCliente+"','"+profissao+"','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO clientes VALUES ("+iCliente+", 'Nome Completo"+iCliente+"',  'rua "+iCliente+"', '"+profissao+"','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, PROFISSAOID, VERSAO, ATIVO, CIDADE) VALUES ("+iCliente+", 'Nome Completo"+iCliente+"', 'rua "+iCliente+"', '"+profissao+"','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, profissao, versao, ativo) VALUES ("+iCliente+", 'Nome Completo"+iCliente+"', 'rua "+iCliente+"', '"+profissao+"','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+iCliente+", 'Nome Completo"+iCliente+"', 'rua "+iCliente+"', '"+profissao+"','1','1', CURRENT_TIMESTAMP);";
                            txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, profissao, versao, ativo) VALUES ("+iCliente+", 'Nome Completo"+iCliente+"', 'rua "+iCliente+"', '"+profissao+"','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                        case 6://Mongo
                            
                            

                            // 1. BasicDBObject example
                            //System.out.println("BasicDBObject example...");
//                            BasicDBObject document = new BasicDBObject();
//                            document.put("database", "mkyongDB");
//                            document.put("table", "hosting");
//
//                            BasicDBObject documentDetail = new BasicDBObject();
//                            documentDetail.put("records", 99);
//                            documentDetail.put("index", "vps_index1");
//                            documentDetail.put("active", "true");
//                            document.put("detail", documentDetail);
                            
                            BasicDBObject document = new BasicDBObject();
                            document.put("Id", iCliente);
                            document.put("Nome", "Nome Completo"+iCliente);
                            document.put("Endereco", "rua "+iCliente);
                            document.put("profisaoid", profissao);
                            document.put("versao", 1);
                            document.put("ativo", 1);
                            document.put("createdDate", new Date());

                            collection.insert(document);

                            //grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iCliente+", 'Nome Completo"+iCliente+"', 'rua "+iCliente+"', '1','1','1');";
                            txtConsole.append(document+"\n");
                            break;    
                            
                    }
                    if(!cmbBanco.getSelectedItem().toString().equals("Mongo")){
                        stmt.executeUpdate(grava);   
                    }
                                
                }
                if(iCliente>=limiteCliente){
                    tempInicial2 = System.currentTimeMillis();
                    Runnable r2 = new threadInsertProfissao();
                    new Thread(r2).start();
                }
                if(!cmbBanco.getSelectedItem().toString().equals("Mongo")){
                    Runnable r22 = new t2();
                    new Thread(r22).start();
                }else{
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial1);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblClientes.setText(tempo + " para Clientes");
                    lblClientes.setForeground(Color.blue);
                }
            } catch (Exception ex) {
                Logger.getLogger(telaBancos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
 
    public class threadInsertProfissao implements Runnable{
        public int onde;
        private int iProfissao;
        @Override
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            Statement stmt;
            try {
                lblOque.setText("Inserindo Profissões");
                lblProfissoes.setText("aguardando...");

                fimProfissao=false;
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limiteProfissao);
                lblSaldoProduto.setText("aguardando...");
                int profissao;
                stmt = conexao.conectaBancos2(cmbBanco.getSelectedItem().toString()).createStatement();
                for (iProfissao=1;iProfissao<=limiteProfissao;iProfissao++){
                    jProgressBar1.setValue(iProfissao);
                    profissao = 1 + (int)(Math.random() * limiteProfissao);

                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench002`.`profissoes` (`id`, `descricao`,`versao`,`ativo`) VALUES ('"+iProfissao+"', 'descricao Profissao "+iProfissao+"','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO profissoes VALUES ("+iProfissao+", 'descricao Profissao"+iProfissao+"','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE) VALUES ("+iProfissao+", 'Nome Completo"+iProfissao+"', 'rua "+iProfissao+"', '1','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iProfissao+", 'Nome Completo"+iProfissao+"', 'rua "+iProfissao+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            //return;
                            //grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+iProfissao+", 'Nome Completo"+iProfissao+"', 'rua "+iProfissao+"', '1','1','1', CURRENT_TIMESTAMP);";
                            //txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iProfissao+", 'Nome Completo"+iProfissao+"', 'rua "+iProfissao+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                            
                    }
                    stmt.executeUpdate(grava);               
                }
                if(iProfissao>=limiteProfissao){
                    tempInicial3 = System.currentTimeMillis();
                    Runnable r3 = new threadInsertProduto();
                    new Thread(r3).start();
                }
                Runnable r23 = new t2();
                new Thread(r23).start();
            } catch (Exception ex) {
                Logger.getLogger(telaBancos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
    
    public class threadInsertProduto implements Runnable{
        public int onde,tipo;
        private int iProduto;
        @Override
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            Statement stmt;
            try {
                lblOque.setText("Inserindo Produtos");
                lblProdutos.setText("aguardando...");
                fimProduto=false;
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limiteProduto);
                double preco;
                lblSaldoProduto.setText("aguardando...");
                stmt = conexao.conectaBancos2(cmbBanco.getSelectedItem().toString()).createStatement();
                for (iProduto=1;iProduto<=limiteProduto;iProduto++){
                    preco = Math.random() * 1000;
                    tipo = 1 + (int)(Math.random() * limiteTipoProduto);
                    jProgressBar1.setValue(iProduto);
                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench002`.`produtos` (`id`, `descricao`,`preco`,`obs`,`tipoProdutoId`,`versao`,`ativo`) VALUES ('"+iProduto+"', 'descricao produto "+iProduto+"','"+preco+"','obs,obs,obs,obs','"+tipo+"','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO produtos VALUES ('"+iProduto+"', 'descricao produto "+iProduto+"','"+preco+"','obs, obs,obs,obs','"+tipo+"','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE) VALUES ("+iProduto+", 'Nome Completo"+iProduto+"', 'rua "+iProduto+"', '1','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iProduto+", 'Nome Completo"+iProduto+"', 'rua "+iProduto+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+iProduto+", 'Nome Completo"+iProduto+"', 'rua "+iProduto+"', '1','1','1', CURRENT_TIMESTAMP);";
                            txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iProduto+", 'Nome Completo"+iProduto+"', 'rua "+iProduto+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                            
                    }
                    stmt.executeUpdate(grava);               
                }
                if(iProduto>=limiteProduto){
                    tempInicial4 = System.currentTimeMillis();
                    Runnable r4 = new threadInsertTipoProduto();
                    new Thread(r4).start();
                }
                Runnable r24 = new t2();
                new Thread(r24).start();
            } catch (Exception ex) {
                Logger.getLogger(telaBancos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
    
    public class threadInsertTipoProduto implements Runnable{
        public int onde;
        private int iTipoProduto;
        @Override
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            Statement stmt;
            try {
                lblOque.setText("Inserindo Tipos de Produto");
                lblTiposProduto.setText("aguardando...");
                fimTipoProduto=false;
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limiteTipoProduto);
                lblSaldoProduto.setText("aguardando...");
                stmt = conexao.conectaBancos2(cmbBanco.getSelectedItem().toString()).createStatement();
                for (iTipoProduto=1;iTipoProduto<=limiteTipoProduto;iTipoProduto++){
                    jProgressBar1.setValue(iTipoProduto);
                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench002`.`tiposProduto` (`id`, `descricao`,`versao`,`ativo`) VALUES ('"+iTipoProduto+"', 'descricao Profissao "+iTipoProduto+"','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO tiposProduto VALUES ("+iTipoProduto+", 'descricao Profissao"+iTipoProduto+"','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE) VALUES ("+iTipoProduto+", 'Nome Completo"+iTipoProduto+"', 'rua "+iTipoProduto+"', '1','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iTipoProduto+", 'Nome Completo"+iTipoProduto+"', 'rua "+iTipoProduto+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+iTipoProduto+", 'Nome Completo"+iTipoProduto+"', 'rua "+iTipoProduto+"', '1','1','1', CURRENT_TIMESTAMP);";
                            txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iTipoProduto+", 'Nome Completo"+iTipoProduto+"', 'rua "+iTipoProduto+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                            
                    }
                    stmt.executeUpdate(grava);               
                }
                if(iTipoProduto>=limiteTipoProduto){
                    tempInicial5 = System.currentTimeMillis();
                    Runnable r5 = new threadInsertSaldoProduto();
                    new Thread(r5).start();
                }
                Runnable r25 = new t2();
                new Thread(r25).start();
            } catch (Exception ex) {
                Logger.getLogger(telaBancos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
    
    public class threadInsertSaldoProduto implements Runnable{
        public int onde;
        private int iSaldoProduto;
        @Override
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            Statement stmt;
            try {
                lblOque.setText("Inserindo Saldo de Produto");
                lblSaldoProduto.setText("aguardando...");
                fimSaldoProduto=false;
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limiteSaldoProduto);
                int idProduto;
                lblSaldoProduto.setText("aguardando...");
                stmt = conexao.conectaBancos2(cmbBanco.getSelectedItem().toString()).createStatement();
                for (iSaldoProduto=1;iSaldoProduto<=limiteSaldoProduto;iSaldoProduto++){
                    
                    jProgressBar1.setValue(iSaldoProduto);
                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench002`.`saldoProduto` (`id`, `idProduto`,`saldo`,`versao`,`ativo`) VALUES ('"+iSaldoProduto+"', '"+iSaldoProduto+"','50','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO saldoProduto VALUES ('"+iSaldoProduto+"', '"+iSaldoProduto+"','50','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE) VALUES ("+iSaldoProduto+", 'Nome Completo"+iSaldoProduto+"', 'rua "+iSaldoProduto+"', '1','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iSaldoProduto+", 'Nome Completo"+iSaldoProduto+"', 'rua "+iSaldoProduto+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+iSaldoProduto+", 'Nome Completo"+iSaldoProduto+"', 'rua "+iSaldoProduto+"', '1','1','1', CURRENT_TIMESTAMP);";
                            txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iSaldoProduto+", 'Nome Completo"+iSaldoProduto+"', 'rua "+iSaldoProduto+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                            
                    }
                    stmt.executeUpdate(grava);               
                }
                conta++;
                if(iSaldoProduto>=limiteSaldoProduto){
                    //btnInserir.setEnabled(true);

                    //Runnable r6 = new threadInsertProfissao();
                    //new Thread(r6).start();
                }
                Runnable r26 = new t2();
                new Thread(r26).start();
                
            } catch (Exception ex) {
                Logger.getLogger(telaBancos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
   
    public class threadInsertVenda implements Runnable{
        public int onde;
        private int iVenda,idCliente,quantasVendas,qualProduto;
        private double preco,x;
        
        @Override
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            int v=1,valorFinal;
            Statement stmt;
            try {
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limiteVenda);
                lblBench.setText("aguardando...");
                
                stmt = conexao.conectaBancos2(cmbBanco.getSelectedItem().toString()).createStatement();
                for (iVenda=1;iVenda<=limiteVenda;iVenda++){
                    jProgressBar1.setValue(iVenda);
                    idCliente = 1 + (int)(Math.random() * limiteCliente);
                    preco = Math.random() * 1000;
                    x=preco*10/100;
                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench002`.`vendas` (`id`, `idCliente`,`valorTotal`,`percentualDesconto`,`valorDesconto`,`versao`,`ativo`) VALUES ('"+iVenda+"','"+idCliente+"','"+preco+"', '10','"+x+"','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO vendas VALUES ('"+iVenda+"','"+idCliente+"','"+preco+"', '10','0','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE) VALUES ("+iVenda+", 'Nome Completo"+iVenda+"', 'rua "+iVenda+"', '1','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iVenda+", 'Nome Completo"+iVenda+"', 'rua "+iVenda+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+iVenda+", 'Nome Completo"+iVenda+"', 'rua "+iVenda+"', '1','1','1', CURRENT_TIMESTAMP);";
                            txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+iVenda+", 'Nome Completo"+iVenda+"', 'rua "+iVenda+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                            
                    }
                    stmt.executeUpdate(grava); 
                    
                    switch (onde){
                        case 0://MySQL
                            for (quantasVendas=1;quantasVendas<=limiteProdutoVenda;quantasVendas++){
                                qualProduto = 1 + (int)(Math.random() * limiteProduto);
                                grava = "INSERT INTO `bench002`.`produtosVenda` (id, idVenda, idProduto, quantidade, valorUnitario, versao, ativo) VALUES ("+v+","+iVenda+", '"+qualProduto+"', '2', '1.50','1','1');";
                                txtConsole.append(grava+"\n");
                                stmt.executeUpdate(grava);
                                grava = "SELECT saldo FROM `bench002`.`saldoProduto` WHERE idProduto="+qualProduto+";";
                                txtConsole.append(grava+"\n");
                                String q="";
                                ResultSet resultSet = stmt.executeQuery(grava);
                                while (resultSet.next()) {
                                    q = resultSet.getString("saldo");
                                }
                                valorFinal = Integer.parseInt(q)-2;
                                grava = "UPDATE `bench002`.`saldoProduto` SET saldo='"+valorFinal+"' WHERE idProduto="+qualProduto+";";
                                txtConsole.append(grava+"\n"); 
                                stmt.executeUpdate(grava);
                                v++;
                            } 
                            break;
                        case 1://PostgreSQL
                            for (quantasVendas=1;quantasVendas<=limiteProdutoVenda;quantasVendas++){
                                qualProduto = 1 + (int)(Math.random() * limiteProduto);
                                grava = "INSERT INTO produtosVenda (id, idVenda, idProduto, quantidade, valorUnitario, versao, ativo) VALUES ("+v+","+iVenda+", '"+qualProduto+"', '2', '1.50','1','1');";
                                txtConsole.append(grava+"\n");
                                stmt.executeUpdate(grava);
                                grava = "SELECT saldo FROM saldoProduto WHERE idProduto="+qualProduto+";";
                                txtConsole.append(grava+"\n");
                                String q="";
                                ResultSet resultSet = stmt.executeQuery(grava);
                                while (resultSet.next()) {
                                    q = resultSet.getString("saldo");
                                }
                                valorFinal = Integer.parseInt(q)-2;
                                grava = "UPDATE saldoProduto SET saldo='"+valorFinal+"' WHERE idProduto="+qualProduto+";";
                                txtConsole.append(grava+"\n"); 
                                stmt.executeUpdate(grava);
                                v++;
                            }
                            break;
                        case 2://ORACLE
                                                      
                            break;
                        case 3://SQLSERVER
                            
                            break;                           
                        case 4://FireBird
                              
                            break;
                        case 5://SQLSERVERntegrated
                            
                            break;
                            
                    }
                    
                                      
                }
                Runnable r27 = new t2();
                new Thread(r27).start();
            } catch (Exception ex) {
                Logger.getLogger(telaBancos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
    
    public class t2 implements Runnable{
        @Override
        public void run(){
            try{
                //tempFinal = System.currentTimeMillis();
                //dif = (tempFinal - tempInicial);
                //tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                if(lblOque.getText().equals("Inserindo Profissões")){
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial1);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblClientes.setText(tempo + " para Clientes");
                    lblClientes.setForeground(Color.blue);
                }
                
                
                if(lblOque.getText().equals("Inserindo Produtos")){
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial2);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblProfissoes.setText(tempo + " para Profissões");
                    lblProfissoes.setForeground(Color.blue);
                }
                if(lblOque.getText().equals("Inserindo Tipos de Produto")){
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial3);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblProdutos.setText(tempo + " para Produtos");
                    lblProdutos.setForeground(Color.blue);
                }

                if((lblOque.getText().equals("Inserindo Saldo de Produto"))&&(conta==0)){
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial4);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblTiposProduto.setText(tempo + " para Tipos de Produto");
                    lblTiposProduto.setForeground(Color.blue);
                }
                if((lblOque.getText().equals("Inserindo Saldo de Produto"))&&(conta==1)){
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial5);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblSaldoProduto.setText(tempo + " para Saldo de Produto");
                    lblSaldoProduto.setForeground(Color.blue);
                }
                
                if(lblOque.getText().equals("Vendas de produtos")){
                    tempFinal = System.currentTimeMillis();
                    dif = (tempFinal - tempInicial);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblBench.setText(tempo + " para Vendas de produtos");
                    lblBench.setForeground(Color.blue);
                }
               
                if((lblOque.getText().equals("Criando tabelas"))){
                    dif = (tempFinal - tempInicial);
                    tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                
                    lblCriarTabelas.setText(tempo + " para criar tabelas");
                    lblCriarTabelas.setForeground(Color.blue);
                }
                
                
                

                if (cmbBanco.getSelectedIndex()==0){
                    //btnBench.setEnabled(true);
                }
                /*
                 * 
                btnInserir.setEnabled(false);
                btnLimpar.setEnabled(true);
                ini = 4181;
                fim = 4461;
                resultado = funcoes.fazProgresso (dif, ini,fim);
                System.out.println(resultado);
                ini = 6312;
                fim = 6859;
                resultado = funcoes.fazProgresso (dif, ini,fim);
                ini = 3045;
                fim = 3839;
                resultado = funcoes.fazProgresso (dif, ini,fim);
                 * 
                 */
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erro na thread. " + e);
            }
        }
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCriarTabelas = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnConectar = new javax.swing.JButton();
        btnCriarBanco = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIp = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtSenha = new javax.swing.JTextField();
        txtPorta = new javax.swing.JTextField();
        lblCriarBanco = new javax.swing.JLabel();
        lblCriarTabelas = new javax.swing.JLabel();
        btnInserir = new javax.swing.JButton();
        btnBench = new javax.swing.JButton();
        btnEliminarBanco = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        txtProfissoes = new javax.swing.JTextField();
        lbl01 = new javax.swing.JLabel();
        lbl02 = new javax.swing.JLabel();
        lbl03 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        cmbBanco = new javax.swing.JComboBox();
        lblPostgre = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsole = new javax.swing.JTextArea();
        jLabel52 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTiposProduto = new javax.swing.JTextField();
        txtSaldosProduto = new javax.swing.JTextField();
        txtVendas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblBench = new javax.swing.JLabel();
        lblSaldoProduto = new javax.swing.JLabel();
        txtClientes = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtProdutos = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtProdutosVenda = new javax.swing.JTextField();
        lblClientes = new javax.swing.JLabel();
        lblProfissoes = new javax.swing.JLabel();
        lblProdutos = new javax.swing.JLabel();
        lblTiposProduto = new javax.swing.JLabel();
        lblOque = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnLimpar1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tela de Testes");
        setMaximumSize(new java.awt.Dimension(1000, 700));
        setPreferredSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(null);

        btnCriarTabelas.setText("Criar 7 Tabelas");
        btnCriarTabelas.setEnabled(false);
        btnCriarTabelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarTabelasActionPerformed(evt);
            }
        });
        getContentPane().add(btnCriarTabelas);
        btnCriarTabelas.setBounds(40, 210, 180, 25);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("SGBD");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 10, 70, 25);

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConectar);
        btnConectar.setBounds(290, 10, 210, 25);

        btnCriarBanco.setText("Criar Banco");
        btnCriarBanco.setEnabled(false);
        btnCriarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarBancoActionPerformed(evt);
            }
        });
        getContentPane().add(btnCriarBanco);
        btnCriarBanco.setBounds(40, 180, 180, 25);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Usuário");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 70, 70, 25);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Senha");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 100, 70, 25);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Porta");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 130, 70, 25);

        txtIp.setText("192.168.1.2");
        getContentPane().add(txtIp);
        txtIp.setBounds(100, 40, 180, 25);

        txtUsuario.setText("root");
        getContentPane().add(txtUsuario);
        txtUsuario.setBounds(100, 70, 180, 25);

        txtSenha.setText("123");
        getContentPane().add(txtSenha);
        txtSenha.setBounds(100, 100, 180, 25);

        txtPorta.setText("3306");
        getContentPane().add(txtPorta);
        txtPorta.setBounds(100, 130, 180, 25);

        lblCriarBanco.setText("aguardando...");
        getContentPane().add(lblCriarBanco);
        lblCriarBanco.setBounds(230, 180, 340, 25);

        lblCriarTabelas.setText("aquardando...");
        getContentPane().add(lblCriarTabelas);
        lblCriarTabelas.setBounds(230, 210, 340, 25);

        btnInserir.setText("Inserir Dados");
        btnInserir.setEnabled(false);
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });
        getContentPane().add(btnInserir);
        btnInserir.setBounds(40, 240, 180, 25);

        btnBench.setText("Benchmark");
        btnBench.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBenchActionPerformed(evt);
            }
        });
        getContentPane().add(btnBench);
        btnBench.setBounds(580, 160, 180, 70);

        btnEliminarBanco.setText("Eliminar Banco");
        btnEliminarBanco.setEnabled(false);
        btnEliminarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarBancoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarBanco);
        btnEliminarBanco.setBounds(510, 10, 150, 25);

        btnLimpar.setText("Limpar 1 à 5");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpar);
        btnLimpar.setBounds(570, 360, 180, 29);

        txtProfissoes.setText("100");
        txtProfissoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProfissoesActionPerformed(evt);
            }
        });
        getContentPane().add(txtProfissoes);
        txtProfissoes.setBounds(150, 300, 60, 25);

        lbl01.setText("...");
        getContentPane().add(lbl01);
        lbl01.setBounds(290, 40, 340, 25);
        lbl01.getAccessibleContext().setAccessibleName("");

        lbl02.setText("...");
        getContentPane().add(lbl02);
        lbl02.setBounds(290, 70, 340, 25);

        lbl03.setText("...");
        getContentPane().add(lbl03);
        lbl03.setBounds(290, 100, 340, 25);
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(230, 420, 520, 25);

        cmbBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "PostgreSQL", "Oracle11g", "SQLServer", "FireBird", "SQLServerIntegrated", "Mongo" }));
        cmbBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBancoActionPerformed(evt);
            }
        });
        getContentPane().add(cmbBanco);
        cmbBanco.setBounds(100, 10, 180, 25);

        lblPostgre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lblPostgre);
        lblPostgre.setBounds(30, 190, 200, 25);

        txtConsole.setColumns(20);
        txtConsole.setRows(5);
        jScrollPane1.setViewportView(txtConsole);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(110, 450, 640, 120);

        jLabel52.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel52.setText("Console");
        getContentPane().add(jLabel52);
        jLabel52.setBounds(10, 490, 80, 20);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Ip");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 40, 70, 25);

        txtTiposProduto.setText("500");
        getContentPane().add(txtTiposProduto);
        txtTiposProduto.setBounds(150, 360, 60, 25);

        txtSaldosProduto.setText("5000");
        txtSaldosProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldosProdutoActionPerformed(evt);
            }
        });
        getContentPane().add(txtSaldosProduto);
        txtSaldosProduto.setBounds(150, 390, 60, 25);

        txtVendas.setText("10");
        getContentPane().add(txtVendas);
        txtVendas.setBounds(690, 270, 60, 25);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("1.clientes");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 270, 140, 25);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("2.profissões");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(10, 300, 140, 25);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("5.saldo produto");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(10, 390, 140, 25);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("7.vendas");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(560, 270, 120, 25);

        lblBench.setText("aguardando...");
        getContentPane().add(lblBench);
        lblBench.setBounds(570, 310, 410, 25);

        lblSaldoProduto.setText("aguardando...");
        getContentPane().add(lblSaldoProduto);
        lblSaldoProduto.setBounds(230, 390, 330, 25);

        txtClientes.setText("500");
        getContentPane().add(txtClientes);
        txtClientes.setBounds(150, 270, 60, 25);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("4.tipos de produto");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(10, 360, 140, 25);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("3.produtos");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(10, 330, 140, 25);

        txtProdutos.setText("5000");
        getContentPane().add(txtProdutos);
        txtProdutos.setBounds(150, 330, 60, 25);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("6.produtos por venda");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(540, 240, 140, 25);

        txtProdutosVenda.setText("10");
        getContentPane().add(txtProdutosVenda);
        txtProdutosVenda.setBounds(690, 240, 60, 25);

        lblClientes.setText("aguardando...");
        getContentPane().add(lblClientes);
        lblClientes.setBounds(230, 270, 330, 25);

        lblProfissoes.setText("aguardando...");
        getContentPane().add(lblProfissoes);
        lblProfissoes.setBounds(230, 300, 330, 25);

        lblProdutos.setText("aguardando...");
        getContentPane().add(lblProdutos);
        lblProdutos.setBounds(230, 330, 330, 25);

        lblTiposProduto.setText("aguardando...");
        getContentPane().add(lblTiposProduto);
        lblTiposProduto.setBounds(230, 360, 330, 25);

        lblOque.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        lblOque.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOque.setText("..");
        getContentPane().add(lblOque);
        lblOque.setBounds(20, 420, 200, 25);

        jButton1.setText("L");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(0, 10, 40, 25);

        btnLimpar1.setText("Limpar 6 e 7");
        btnLimpar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpar1);
        btnLimpar1.setBounds(570, 390, 180, 29);

        jButton2.setText("SP");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(840, 160, 75, 29);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        qual = cmbBanco.getSelectedItem().toString();
        if (qual.equals("Mongo")){
            try {
                mongo = new MongoClient(txtIp.getText(), Integer.parseInt(txtPorta.getText()));
                db = mongo.getDB("test");
                btnCriarBanco.setEnabled(true);
                btnCriarTabelas.setEnabled(false);
                btnEliminarBanco.setEnabled(false);
                btnInserir.setEnabled(true);
                btnConectar.setText("Conectado "+qual);
                btnConectar.setEnabled(false);
            } catch (Exception e) {
            
            }
            
        }else{
        
            try{ 

                conexao.conectaBancos2(qual).createStatement();
                switch (cmbBanco.getSelectedIndex()){
                    case 0://MySQL
                        btnCriarBanco.setEnabled(true);
                        btnCriarTabelas.setEnabled(true);
                        btnEliminarBanco.setEnabled(true);
                        break;
                    case 1: //PostgreSQL   
                        btnCriarBanco.setEnabled(false);
                        btnCriarTabelas.setEnabled(true);
                        btnEliminarBanco.setEnabled(false);
                        break;
                    case 2://Oracle    
                        btnCriarBanco.setEnabled(false);
                        btnCriarTabelas.setEnabled(true);
                        btnEliminarBanco.setEnabled(false);
                        break;
                    case 3://SQLServer    
                        btnCriarBanco.setEnabled(false);
                        btnCriarTabelas.setEnabled(true);
                        btnEliminarBanco.setEnabled(false);
                        break;
                    case 4://Firebird    
                        btnCriarBanco.setEnabled(true);
                        btnCriarTabelas.setEnabled(true);
                        btnEliminarBanco.setEnabled(false);
                        break;
                    case 5://SQLServerIntegrated    
                        btnCriarBanco.setEnabled(false);
                        btnCriarTabelas.setEnabled(true);
                        btnEliminarBanco.setEnabled(false);
                        break;
                     

               }
               btnInserir.setEnabled(true);
               btnConectar.setText("Conectado "+qual);
               btnConectar.setEnabled(false);
           }catch (Exception e){
               JOptionPane.showMessageDialog(null, "Erro na conexão. " + e.getMessage());
           }
        }
    }//GEN-LAST:event_btnConectarActionPerformed

    private void btnCriarTabelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarTabelasActionPerformed
        // TODO add your handling code here:
        qual = cmbBanco.getSelectedItem().toString();
        lblOque.setText("Criando tabelas");

        try {
            tempInicial = System.currentTimeMillis();
            Statement stmt = conexao.conectaBancos2(qual).createStatement();
            switch (cmbBanco.getSelectedIndex()){
                case 0://MySQL
                    grava = "USE bench002;";
                    stmt.executeUpdate(grava);
                    txtConsole.setText("");
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `clientes` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`nome` varchar(50) NOT NULL," +
                            "`endereco` varchar(50) NOT NULL," +
                            "`profissaoId` int NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `profissoes` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`descricao` varchar(50) NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `produtos` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`descricao` varchar(50) NOT NULL," +
                            "`preco` decimal(10,2) NOT NULL," +
                            "`obs` varchar(150) NOT NULL," +
                            "`tipoProdutoId` bigint NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `tiposProduto` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`descricao` varchar(150) NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `saldoProduto` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`idProduto` bigint NOT NULL," +
                            "`saldo` int NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `vendas` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`idCliente` bigint NOT NULL," +
                            "`valorTotal` decimal(10,2) NOT NULL," +
                            "`percentualDesconto` decimal(10,2) NOT NULL," +
                            "`valorDesconto` decimal(10,2) NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "CREATE TABLE IF NOT EXISTS `produtosVenda` (" +
                            "`id` bigint NOT NULL AUTO_INCREMENT," +
                            "`idVenda` bigint NOT NULL," +
                            "`idProduto` bigint NOT NULL," +
                            "`quantidade` mediumint NOT NULL," +
                            "`valorUnitario` decimal(10,2) NOT NULL," +
                            "`versao` int," +
                            "`ativo` tinyint NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");  
                    lbl02.setText("Tabelas são do tipo InnoDB.");                   
                    btnCriarTabelas.setEnabled(false);
                    break;
                case 1://PostgreSQL 
                    txtConsole.setText("");
                    grava ="CREATE or REPLACE FUNCTION CriaTabela() RETURNS integer AS $$\n"
                            +"DECLARE \n"
                            +"foiCriada INTEGER :=0;\n"
                            +"tabela RECORD;\n"
                            +"BEGIN \n"
                            +" SELECT INTO tabela tablename FROM pg_tables where tablename='clientes' and schemaname = ANY (current_schemas(true));\n"
                            +" IF tabela.tablename IS NULL THEN \n"
                            +" CREATE TABLE clientes("//clientes
                            + "id bigint NOT NULL,"
                            + "nome text,"
                            + "endereco text,"
                            + "profissaoId bigint,"                            
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT clientes_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim clientes
                            +" CREATE TABLE profissoes("//profissoes
                            + "id bigint NOT NULL,"
                            + "descricao text,"
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT profissoes_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim profissoes
                            +" CREATE TABLE produtos("//produtos
                            + "id bigint NOT NULL,"
                            + "descricao text,"
                            + "preco float,"
                            + "obs text,"
                            + "idTipoProduto bigint,"
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT produtos_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim produtos
                            +" CREATE TABLE tiposProduto("//tiposProduto
                            + "id bigint NOT NULL,"
                            + "descricao text,"
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT tiposProduto_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim tiposProduto
                            +" CREATE TABLE saldoProduto("//saldoProduto
                            + "id bigint NOT NULL,"
                            + "idProduto bigint,"
                            + "saldo integer,"
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT saldoProduto_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim saldoProduto
                            +" CREATE TABLE vendas("//vendas
                            + "id bigint NOT NULL,"
                            + "idCliente bigint,"
                            + "valorTotal text,"
                            + "percentualDesconto text,"
                            + "valorDesconto text,"
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT vendas_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim vendas
                            +" CREATE TABLE produtosVenda("//produtosVenda
                            + "id bigint NOT NULL,"
                            + "idVenda bigint,"
                            + "idProduto bigint,"
                            + "quantidade integer,"
                            + "valorUnitario float,"
                            + "versao integer,"
                            + "ativo smallint,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT produtosVenda_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            //fim produtosVenda
                            +" foiCriada = 1;\n"
                            +" END IF;\n"
                            +" RETURN foiCriada;\n"
                            +" END;\n"
                            +" $$ LANGUAGE plpgsql;";
                    int criou = stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "select criaTabela();";
                    stmt.executeQuery(grava);
                    txtConsole.append(grava+"\n");
                    grava = "ALTER TABLE clientes OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    grava = "ALTER TABLE profissoes OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    grava = "ALTER TABLE produtos OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    grava = "ALTER TABLE tiposProduto OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    grava = "ALTER TABLE saldoProduto OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    grava = "ALTER TABLE vendas OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    grava = "ALTER TABLE produtosVenda OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    
                    txtConsole.append(grava+"\n");
                    if (criou==0){
                        JOptionPane.showMessageDialog(this,"As tabelas já existem");
                    }
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    break;
                case 2://Oracle11g
                    txtConsole.setText("");
                    grava = "CREATE TABLE hr.clientes(" +
                            "id number NOT NULL constraint id_pk primary key," +
                            "nome varchar2(50) NOT NULL," +
                            "endereco varchar2(49) NOT NULL," +
                            "versao decimal(10)," +
                            "ativo decimal(2) NOT NULL," +
                            "cidade decimal(5) NOT NULL," +
                            "dataCadastro timestamp NOT NULL);"; 
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    stmt.executeUpdate(grava);
                    
                    break;
                case 3://SQLServer
                    grava = "USE [tempdb] " +
                            "CREATE TABLE [dbo].[clientes]( " +
                            "        [id] [int] NOT NULL, " +
                            "        [nome] [varchar](50) NULL, " +
                            "        [endereco] [varchar](49) NULL, " +
                            "        [versao] [int] NULL, " +
                            "        [ativo] [int] NULL, " +
                            "        [cidade] [int] NULL, " +
                            "        [dataCadastro] [timestamp] NULL " +
                            ") ON [PRIMARY] ";
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    //btnBench.setEnabled(false);
                    break;
                case 4://FireBird
                    grava = "CREATE TABLE CLIENTES (" +
                            "ID            INTEGER NOT NULL," +
                            "NOME          VARCHAR(50)," +
                            "ENDERECO      VARCHAR(49)," +
                            "VERSAO        INTEGER," +
                            "ATIVO         INTEGER," +
                            "CIDADE        INTEGER," +
                            "DATACADASTRO  TIMESTAMP);";
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");  
                    btnCriarTabelas.setEnabled(false);
                    break;
                 case 5://SQLIntegrated
                    grava = "USE [tempdb] " +
                            "CREATE TABLE [dbo].[clientes]( " +
                            "        [id] [int] NOT NULL, " +
                            "        [nome] [varchar](50) NULL, " +
                            "        [endereco] [varchar](49) NULL, " +
                            "        [versao] [int] NULL, " +
                            "        [ativo] [int] NULL, " +
                            "        [cidade] [int] NULL, " +
                            "        [dataCadastro] [timestamp] NULL " +
                            ") ON [PRIMARY] ";
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    //btnBench.setEnabled(false);
                    break;
            
            }
            
            //stmt.executeUpdate(grava);
            tempFinal = System.currentTimeMillis();
            dif = (tempFinal - tempInicial);
            //lblCriarTabelas.setText(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000));
            lblCriarTabelas.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
            lblCriarTabelas.setForeground(Color.blue);
            btnInserir.setEnabled(true);
            Runnable r30 = new t2();
            new Thread(r30).start();           
                     
            
        }
        catch( Exception e ) {
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
       
    }//GEN-LAST:event_btnCriarTabelasActionPerformed


    private void btnCriarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarBancoActionPerformed
        qual = cmbBanco.getSelectedItem().toString();
        if (qual.equals("Mongo")){
            tempInicial = System.currentTimeMillis();
            
            db = mongo.getDB("bench002"); 
            collection = db.getCollection("Clientes");
            
            
            tempFinal = System.currentTimeMillis();
            dif = (tempFinal - tempInicial);
            lblCriarBanco.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
            btnCriarBanco.setEnabled(false);
            lblCriarBanco.setForeground(Color.blue);
            btnCriarTabelas.setEnabled(false);
            lbl01.setText("bench002 criado no "+cmbBanco.getSelectedItem().toString()+".");
        }else{
            try {
                qual = cmbBanco.getSelectedItem().toString();    
                btnBench.setEnabled(true);
                Statement stmt = conexao.conectaBancos2(qual).createStatement();  
                tempInicial = System.currentTimeMillis();
                switch (cmbBanco.getSelectedIndex()){
                    case 0://MySQL
                        grava = "CREATE DATABASE IF NOT EXISTS bench002;";
                        break;
                    case 1://PostgreSQL
                        String lc = "";
                            //lc = "C";
                            lc = "Portuguese_Brazil.1252";                  
                        grava ="CREATE DATABASE \"bench002\""
                                + "  WITH OWNER = postgres"
                                + "  ENCODING = \'UTF8\'"
                                + "  TABLESPACE = pg_default"
                                + "  LC_COLLATE = \'"+lc+"\'"
                                + "  LC_CTYPE = \'"+lc+"\'"
                                + "  CONNECTION LIMIT = -1;";                   
                        break;
                    case 2://Oracle11g

                        break;
                    case 3://SQLServer


                        break;
                    case 4://FireBird
                        grava="SET SQL DIALECT 3;\n"
                        +"CREATE DATABASE \""+txtIp.getText()+":"+txtPorta.getText()+"/c:\\bench002.fdb\" USER \""+txtUsuario.getText()+"\" PASSWORD \""+txtSenha.getText()+"\" PAGE_SIZE 4096";        
                        break;

                }
                try{
                    stmt.executeUpdate(grava);            
                }catch (Exception e){
                    JOptionPane.showMessageDialog(this,"O banco já existe");
                    txtConsole.append("Erro "+e.getMessage());
                }
                tempFinal = System.currentTimeMillis();
                dif = (tempFinal - tempInicial);
                lblCriarBanco.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
                btnCriarBanco.setEnabled(false);
                lblCriarBanco.setForeground(Color.blue);
                btnCriarTabelas.setEnabled(true);
                lbl01.setText("bench002 criado no "+cmbBanco.getSelectedItem().toString()+".");
            }
            catch( Exception e ) {
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
            }
        }
    }//GEN-LAST:event_btnCriarBancoActionPerformed

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        // TODO add your handling code here:
        conta=0;
        btnInserir.setEnabled(false);
        limiteCliente = Integer.parseInt(txtClientes.getText());
        limiteProfissao = Integer.parseInt(txtProfissoes.getText());  
        limiteProduto = Integer.parseInt(txtProdutos.getText());
        limiteTipoProduto = Integer.parseInt(txtTiposProduto.getText());
        limiteSaldoProduto = Integer.parseInt(txtSaldosProduto.getText());
        limiteProdutoVenda = Integer.parseInt(txtProdutosVenda.getText());
        limiteVenda = Integer.parseInt(txtVendas.getText());
        btnLimpar.setEnabled(true);
        tempInicial = System.currentTimeMillis();
        Runnable r1 = new threadInsertCliente();
        new Thread(r1).start();
       
        
    }//GEN-LAST:event_btnInserirActionPerformed

    private void btnBenchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBenchActionPerformed
        // TODO add your handling code here:
        btnBench.setEnabled(false);
        limiteVenda =  Integer.parseInt(txtVendas.getText());
        limiteProdutoVenda =Integer.parseInt(txtProdutosVenda.getText());
        limiteCliente = Integer.parseInt(txtClientes.getText());
        limiteProduto = Integer.parseInt(txtProdutos.getText());
        txtConsole.setText("");
        txtConsole.setText("Início das vendas"+"\n");
        lblOque.setText("Vendas de produtos");
        tempInicial = System.currentTimeMillis();
        Runnable r7 = new threadInsertVenda();
        new Thread(r7).start();
    }//GEN-LAST:event_btnBenchActionPerformed

    private void btnEliminarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarBancoActionPerformed
        // TODO add your handling code here:
        tempInicial = System.currentTimeMillis();
        try {
            qual = cmbBanco.getSelectedItem().toString();     
            Statement stmt = conexao.conectaBancos2(qual).createStatement();
            grava = "DROP DATABASE if EXISTS bench002;";
            stmt.executeUpdate(grava);
            tempFinal = System.currentTimeMillis();
            dif = (tempFinal - tempInicial);
            btnCriarBanco.setEnabled(true);
            btnCriarTabelas.setEnabled(false);
            btnInserir.setEnabled(false);
            btnLimpar.setEnabled(false);
            btnBench.setEnabled(false);
            lbl01.setText("Banco bench002 removido.");
            lbl02.setText("");
            lbl03.setText("");         
        }
        catch( Exception e ) {
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarBancoActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        qual = cmbBanco.getSelectedItem().toString();    
        try{    
            btnInserir.setEnabled(true);
            Statement stmt = conexao.conectaBancos2(qual).createStatement();  
            tempInicial = System.currentTimeMillis();
            switch (cmbBanco.getSelectedIndex()){
                case 0://MySQL
                    grava = "USE bench002;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE clientes;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE produtos;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE produtosvenda;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE profissoes;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE saldoproduto;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE tiposproduto;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");               
                    btnInserir.setEnabled(true);
                    break;
                case 1://PostgreSQL
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    grava = "TRUNCATE TABLE produtos;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE produtosvenda;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE profissoes;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE saldoproduto;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE tiposproduto;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");               
                    btnInserir.setEnabled(true);
                    break;
                case 2://Oracle11g
                    grava = "TRUNCATE TABLE HR.clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 3://SQLServer
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 4://FireBird
                    grava = "DELETE from clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 5://SQLServerIntegrated
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;                        
            }
           stmt.executeUpdate(grava);            
        }
        catch( Exception e ) {
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnLimparActionPerformed

    private void cmbBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBancoActionPerformed
        // TODO add your handling code here:
        btnConectar.setText("Conectar");
        lbl01.setText("...");
        lbl02.setText("...");
        lbl03.setText("...");
        btnConectar.setEnabled(true);
        
        switch (cmbBanco.getSelectedIndex()){
            case 0: //MySQL
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("root");
                txtSenha.setText("123");
                txtPorta.setText("3306");
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(false);                
                break;
            case 1://PostgreSQL
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("postgres");
                txtSenha.setText("123");
                txtPorta.setText("5432");
                btnCriarBanco.setEnabled(false);  
                btnCriarTabelas.setEnabled(false);
                break;
            case 2: //Oracle11g
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("SYSTEM");
                txtSenha.setText("123");
                txtPorta.setText("1521");           
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(false);
                txtConsole.append("\nPara testes no Oracle11g, será utilizada apenas a tabela CLIENTES:"+"\n");
                break;
            case 3:// SQLServer
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("root");
                txtSenha.setText("123");
                txtPorta.setText("1433");
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(false);
                txtConsole.append("\nPara testes no SQLServer, será utilizada apenas a tabela CLIENTES:"+"\n");
                break;
            case 4://FireBird
                txtIp.setText("192.168.1.5");
                txtUsuario.setText("sysdba");
                txtSenha.setText("masterkey");
                txtPorta.setText("3050");
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(false);
//                txtConsole.append("Banco:"+"\n");
//                txtConsole.append("CREATE TABLE CLIENTES (\n" +
//                                "    ID INTEGER NOT NULL,\n" +
//                                "    NOME VARCHAR(50),\n" +
//                                "    ENDERECO VARCHAR(50),\n" +
//                                "    VERSAO VARCHAR(50),\n" +
//                                "    ATIVO VARCHAR(50),\n" +
//                                "    CIDADE VARCHAR(50),\n" +
//                                "    DATACADASTRO TIMESTAMP);\n" +
//                                "\n" +
//                                "ALTER TABLE NEW_TABLE\n" +
//                                "ADD CONSTRAINT PK_NEW_TABLE\n" +
//                                "PRIMARY KEY (ID);\n" +
//                                "\n" +
//                                "CREATE SEQUENCE GEN_NEW_TABLE_ID;");
                txtConsole.append("\nPara testes no Firebird, será utilizada apenas a tabela CLIENTES:"+"\n");
                break;
            case 5://SQLServerIntegrated
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("");
                txtSenha.setText("");
                txtPorta.setText("");
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(false);
                txtConsole.append("\nPara testes no SQLServerIntegrated, será utilizada apenas a tabela CLIENTES:"+"\n");
                break;   
            case 6://Mongo
                txtIp.setText("localhost");
                txtUsuario.setText("");
                txtSenha.setText("");
                txtPorta.setText("27017");
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(false);
                break;
        }
    }//GEN-LAST:event_cmbBancoActionPerformed

    private void txtSaldosProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldosProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldosProdutoActionPerformed

    private void txtProfissoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProfissoesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProfissoesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txtIp.setText("127.0.0.1");
        txtUsuario.setText("alunoMarcelo");
        txtSenha.setText("123");
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnLimpar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpar1ActionPerformed
        // TODO add your handling code here:
        qual = cmbBanco.getSelectedItem().toString();    
        try{    
            btnInserir.setEnabled(true);
            Statement stmt = conexao.conectaBancos2(qual).createStatement();  
            tempInicial = System.currentTimeMillis();
            switch (cmbBanco.getSelectedIndex()){
                case 0://MySQL
                    grava = "USE bench002;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    grava = "TRUNCATE TABLE vendas;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");  
                    grava = "TRUNCATE TABLE produtosVenda;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    btnBench.setEnabled(true);
                    break;
                case 1://PostgreSQL
                    grava = "TRUNCATE TABLE vendas;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");  
                    grava = "TRUNCATE TABLE produtosVenda;";
                    stmt.executeUpdate(grava);
                    txtConsole.append(grava+"\n");
                    btnBench.setEnabled(true);                 
                    break;
                case 2://Oracle11g
                    grava = "TRUNCATE TABLE HR.clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 3://SQLServer
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 4://FireBird
                    grava = "DELETE from clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 5://SQLServerIntegrated
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;                        
            }
            //btnBench.setEnabled(true);
           stmt.executeUpdate(grava);            
        }
        catch( Exception e ) {
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnLimpar1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
    qual = cmbBanco.getSelectedItem().toString();    
        try{    
            btnInserir.setEnabled(true);
            Statement stmt = conexao.conectaBancos2(qual).createStatement();  
            grava = "USE bench002;";
            stmt.executeUpdate(grava);
            grava = "call teste(1,10.00,0,0,1,1);";
            stmt.executeUpdate(grava);        
        }
        catch( Exception e ) {
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
}//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnBench;
    public static javax.swing.JButton btnConectar;
    public static javax.swing.JButton btnCriarBanco;
    public static javax.swing.JButton btnCriarTabelas;
    public static javax.swing.JButton btnEliminarBanco;
    public static javax.swing.JButton btnInserir;
    public static javax.swing.JButton btnLimpar;
    public static javax.swing.JButton btnLimpar1;
    public static javax.swing.JComboBox cmbBanco;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lbl01;
    public static javax.swing.JLabel lbl02;
    public static javax.swing.JLabel lbl03;
    public static javax.swing.JLabel lblBench;
    public static javax.swing.JLabel lblClientes;
    public static javax.swing.JLabel lblCriarBanco;
    public static javax.swing.JLabel lblCriarTabelas;
    private javax.swing.JLabel lblOque;
    private javax.swing.JLabel lblPostgre;
    public static javax.swing.JLabel lblProdutos;
    public static javax.swing.JLabel lblProfissoes;
    public static javax.swing.JLabel lblSaldoProduto;
    public static javax.swing.JLabel lblTiposProduto;
    public static javax.swing.JTextField txtClientes;
    private javax.swing.JTextArea txtConsole;
    public static javax.swing.JTextField txtIp;
    public static javax.swing.JTextField txtPorta;
    private javax.swing.JTextField txtProdutos;
    private javax.swing.JTextField txtProdutosVenda;
    public static javax.swing.JTextField txtProfissoes;
    private javax.swing.JTextField txtSaldosProduto;
    public static javax.swing.JTextField txtSenha;
    private javax.swing.JTextField txtTiposProduto;
    public static javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtVendas;
    // End of variables declaration//GEN-END:variables
}