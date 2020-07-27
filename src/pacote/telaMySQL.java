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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.print.DocFlavor.STRING;
import javax.swing.table.*;
import javax.swing.event.*;
import pacote.funcoes.*;


/**
 *
 * @author marcelojosuetelles
 */
public class telaMySQL extends javax.swing.JInternalFrame  implements TableModelListener{
    private String versaoSistema;

    public void tableChanged(TableModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    public class ColorRenderer extends DefaultTableCellRenderer {
       

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){  
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
            
            if(isSelected)
            {
                comp.setBackground(table.getSelectionBackground());
                comp.setForeground(table.getSelectionForeground());
            }
            return comp;
        }    
    }
    
    public String sistemaIp, sistemaPorta, sistemaUser, sistemaSenha, sistemaRegistro, consulta = "", oqueBusca, altera, deleta, empresaSistemaAntigo, idColaboradorFK, sigla, descricao, dataCadastro, dataUpdate, ativo;
    public int versao, versaoConsultada,cont;
    public Boolean par = true;
    public String consultaTabela,idTabelaConsultado;
    public String[] idPrefixoSelecionado = new String[60];
    public String nomeTabela = "ab_grupo_tela";
    public String nomeColunaId = "idGrupo";
    public int idTabelaVerifica;
    public String campoPrincipal = "grupo";
    public String idTabela = "idGrupo";
    int pergunta;
    public static String qual="",grava;
    String idGrupo;
    public int resultado,ini,fim;
    public static Integer limite;
    public static long tempInicial;
    public static long tempFinal;
    public static long dif;
    public static String tempo;
    public static Thread t;

    /** Creates new form estadoTela */
    public telaMySQL () {
        initComponents();
        chkC.setVisible(false);
        chkPortuguese.setVisible(false);
        
    }
    public class threadInsert implements Runnable{
        public int onde;
        private int i;
        private float total;
        private double ac;
        public void run() {
            onde = cmbBanco.getSelectedIndex();
            Statement stmt;
            PreparedStatement stmt2;
            try {
                jProgressBar1.setMinimum(0);
                jProgressBar1.setValue(0);
                jProgressBar1.setMaximum(limite);
                lblInserir.setForeground(Color.black);
                lblInserir.setText("aguardando...");
                stmt = conexao.conectaBancos(cmbBanco.getSelectedItem().toString()).createStatement();
                for (i=1;i<=limite;i++){
                    jProgressBar1.setValue(i);
                    lbl03.setText("Com "+limite+" linhas.");
                    switch (onde){
                        case 0://MySQL
                            grava = "INSERT INTO `bench001`.`clientes` (`id`, `nome`, `endereco`, `cidade`,`versao`,`ativo`) VALUES ('"+i+"', 'Nome Completo "+i+"', 'rua "+i+"', '1','1','1');";                           
                            txtConsole.append(grava+"\n");
                            break;
                        case 1://PostgreSQL
                            grava = "INSERT INTO clientes VALUES ("+i+", 'Nome Completo"+i+"',  'rua "+i+"', '1','1','1');";  
                            txtConsole.append(grava+"\n");
                            break;
                        case 2://ORACLE
                            grava = "INSERT INTO HR.CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE) VALUES ("+i+", 'Nome Completo"+i+"', 'rua "+i+"', '1','1','1')";  
                            System.out.println("grava Oracle "+grava);
                            txtConsole.append(grava+"\n");                            
                            break;
                        case 3://SQLSERVER
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+i+", 'Nome Completo"+i+"', 'rua "+i+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;                           
                        case 4://FireBird
                            grava = "INSERT INTO CLIENTES (ID, NOME, ENDERECO, VERSAO, ATIVO, CIDADE, DATACADASTRO) VALUES ("+i+", 'Nome Completo"+i+"', 'rua "+i+"', '1','1','1', CURRENT_TIMESTAMP);";
                            txtConsole.append(grava+"\n");    
                            break;
                        case 5://SQLSERVERntegrated
                            grava = "INSERT INTO dbo.clientes (id, nome, endereco, versao, ativo, cidade) VALUES ("+i+", 'Nome Completo"+i+"', 'rua "+i+"', '1','1','1');";
                            txtConsole.append(grava+"\n");
                            break;
                            
                    }
                    //PreparedStatement ps = stmt2.prepareStatement(grava);  
                    stmt.executeUpdate(grava);
                    
                }
                Runnable r2 = new t2();
                new Thread(r2).start();
            } catch (Exception ex) {
                Logger.getLogger(telaMySQL.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+ex.getMessage());
            }

        }
       
    }
    
    
    public class t2 implements Runnable{
        public void run(){
            try{
                tempFinal = System.currentTimeMillis();
                dif = (tempFinal - tempInicial);
                tempo = String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000);
                lblInserir.setText(tempo);
                if (cmbBanco.getSelectedIndex()==0){
                    btnSelecionar.setEnabled(true);
                    btnBenchMark.setEnabled(true);
                }
                btnInserir.setEnabled(false);
                btnLimpar.setEnabled(true);
                lblInserir.setForeground(Color.blue);
                ini = 4181;
                fim = 4461;
                resultado = funcoes.fazProgresso (dif, ini,fim);
                pgbBenchMark1.setValue(resultado);
                System.out.println(resultado);
                ini = 6312;
                fim = 6859;
                resultado = funcoes.fazProgresso (dif, ini,fim);
                pgbBenchMark2.setValue(resultado);
                ini = 3045;
                fim = 3839;
                resultado = funcoes.fazProgresso (dif, ini,fim);
                pgbBenchMark3.setValue(resultado);
            }catch (Exception e){
                e.printStackTrace();
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
        lblInserir = new javax.swing.JLabel();
        btnSelecionar = new javax.swing.JButton();
        lblSelecionar = new javax.swing.JLabel();
        btnEliminarBanco = new javax.swing.JButton();
        btnDefinirFormato = new javax.swing.JButton();
        cmbFormato = new javax.swing.JComboBox();
        btnLimpar = new javax.swing.JButton();
        txtLimite = new javax.swing.JTextField();
        btnBenchMark = new javax.swing.JButton();
        lblBenchMark = new javax.swing.JLabel();
        txtBenchMark = new javax.swing.JTextField();
        lbl01 = new javax.swing.JLabel();
        lbl02 = new javax.swing.JLabel();
        lbl03 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pgbInserir1 = new javax.swing.JProgressBar();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        pgbCriarBanco1 = new javax.swing.JProgressBar();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        pgbBenchMark1 = new javax.swing.JProgressBar();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        pgbCriarTabelas1 = new javax.swing.JProgressBar();
        pgbSelecionar1 = new javax.swing.JProgressBar();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        pgbInserir2 = new javax.swing.JProgressBar();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        pgbCriarBanco2 = new javax.swing.JProgressBar();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        pgbBenchMark2 = new javax.swing.JProgressBar();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        pgbSelecionar2 = new javax.swing.JProgressBar();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        pgbCriarTabelas2 = new javax.swing.JProgressBar();
        jLabel34 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pgbInserir3 = new javax.swing.JProgressBar();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        pgbCriarBanco3 = new javax.swing.JProgressBar();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        pgbBenchMark3 = new javax.swing.JProgressBar();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        pgbCriarTabelas3 = new javax.swing.JProgressBar();
        pgbSelecionar3 = new javax.swing.JProgressBar();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        cmbBanco = new javax.swing.JComboBox();
        chkC = new javax.swing.JCheckBox();
        chkPortuguese = new javax.swing.JCheckBox();
        lblPostgre = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsole = new javax.swing.JTextArea();
        jLabel52 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tela de Testes");
        setMaximumSize(new java.awt.Dimension(1000, 700));
        setPreferredSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(null);

        btnCriarTabelas.setText("Criar Tabela");
        btnCriarTabelas.setEnabled(false);
        btnCriarTabelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarTabelasActionPerformed(evt);
            }
        });
        getContentPane().add(btnCriarTabelas);
        btnCriarTabelas.setBounds(20, 260, 211, 25);

        jLabel4.setText("Ip");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 10, 80, 25);

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });
        getContentPane().add(btnConectar);
        btnConectar.setBounds(586, 10, 230, 29);

        btnCriarBanco.setText("Criar Banco");
        btnCriarBanco.setEnabled(false);
        btnCriarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarBancoActionPerformed(evt);
            }
        });
        getContentPane().add(btnCriarBanco);
        btnCriarBanco.setBounds(20, 220, 211, 25);

        jLabel1.setText("Usuário");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 50, 80, 25);

        jLabel2.setText("Senha");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 90, 80, 25);

        jLabel3.setText("Porta");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 130, 80, 25);

        txtIp.setText("127.0.0.1");
        getContentPane().add(txtIp);
        txtIp.setBounds(110, 10, 210, 25);

        txtUsuario.setText("root");
        getContentPane().add(txtUsuario);
        txtUsuario.setBounds(110, 50, 210, 25);

        txtSenha.setText("123");
        getContentPane().add(txtSenha);
        txtSenha.setBounds(110, 90, 210, 25);

        txtPorta.setText("3306");
        getContentPane().add(txtPorta);
        txtPorta.setBounds(110, 130, 210, 25);

        lblCriarBanco.setText("aguardando...");
        getContentPane().add(lblCriarBanco);
        lblCriarBanco.setBounds(248, 220, 269, 25);

        lblCriarTabelas.setText("aquardando...");
        getContentPane().add(lblCriarTabelas);
        lblCriarTabelas.setBounds(248, 260, 281, 25);

        btnInserir.setText("Inserir ");
        btnInserir.setEnabled(false);
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });
        getContentPane().add(btnInserir);
        btnInserir.setBounds(20, 330, 102, 25);

        lblInserir.setText("aguardando...");
        getContentPane().add(lblInserir);
        lblInserir.setBounds(251, 330, 245, 25);

        btnSelecionar.setText("Selecionar");
        btnSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnSelecionar);
        btnSelecionar.setBounds(20, 400, 213, 25);

        lblSelecionar.setText("aguardando...");
        getContentPane().add(lblSelecionar);
        lblSelecionar.setBounds(248, 400, 278, 25);

        btnEliminarBanco.setText("Eliminar Banco");
        btnEliminarBanco.setEnabled(false);
        btnEliminarBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarBancoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminarBanco);
        btnEliminarBanco.setBounds(820, 10, 150, 29);

        btnDefinirFormato.setText("Tipo da Tabela");
        btnDefinirFormato.setEnabled(false);
        btnDefinirFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefinirFormatoActionPerformed(evt);
            }
        });
        getContentPane().add(btnDefinirFormato);
        btnDefinirFormato.setBounds(20, 290, 211, 29);

        cmbFormato.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "InnoDB", "ARCHIVE", "MRG_MYISAM", "MEMORY", "MyISAM" }));
        cmbFormato.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFormatoItemStateChanged(evt);
            }
        });
        cmbFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFormatoActionPerformed(evt);
            }
        });
        getContentPane().add(cmbFormato);
        cmbFormato.setBounds(233, 290, 141, 27);

        btnLimpar.setText("Limpar Tabela");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpar);
        btnLimpar.setBounds(20, 365, 213, 29);

        txtLimite.setText("50000");
        getContentPane().add(txtLimite);
        txtLimite.setBounds(133, 330, 100, 25);

        btnBenchMark.setText("BenchMark");
        btnBenchMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBenchMarkActionPerformed(evt);
            }
        });
        getContentPane().add(btnBenchMark);
        btnBenchMark.setBounds(20, 440, 112, 25);

        lblBenchMark.setText("aguardando...");
        getContentPane().add(lblBenchMark);
        lblBenchMark.setBounds(248, 440, 268, 25);

        txtBenchMark.setText("50000");
        getContentPane().add(txtBenchMark);
        txtBenchMark.setBounds(141, 440, 88, 25);

        lbl01.setText("...");
        getContentPane().add(lbl01);
        lbl01.setBounds(380, 50, 340, 25);
        lbl01.getAccessibleContext().setAccessibleName("");

        lbl02.setText("...");
        getContentPane().add(lbl02);
        lbl02.setBounds(380, 90, 340, 25);

        lbl03.setText("...");
        getContentPane().add(lbl03);
        lbl03.setBounds(380, 130, 340, 25);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Valores de referência para 50000"));

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setLayout(null);

        jLabel15.setForeground(new java.awt.Color(0, 102, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("15ms");
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel15);
        jLabel15.setBounds(0, 2, 80, 25);
        jPanel1.add(pgbInserir1);
        pgbInserir1.setBounds(80, 2, 160, 25);

        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("16ms");
        jPanel1.add(jLabel16);
        jLabel16.setBounds(249, 2, 80, 25);

        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("47ms");
        jPanel1.add(jLabel17);
        jLabel17.setBounds(249, 42, 80, 25);
        jPanel1.add(pgbCriarBanco1);
        pgbCriarBanco1.setBounds(80, 42, 160, 25);

        jLabel18.setForeground(new java.awt.Color(0, 102, 51));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("15ms");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel18);
        jLabel18.setBounds(0, 42, 80, 25);

        jLabel19.setForeground(new java.awt.Color(0, 102, 51));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("4s 181ms");
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel19);
        jLabel19.setBounds(0, 110, 80, 25);

        pgbBenchMark1.setBackground(new java.awt.Color(255, 204, 153));
        jPanel1.add(pgbBenchMark1);
        pgbBenchMark1.setBounds(80, 110, 160, 25);

        jLabel20.setForeground(new java.awt.Color(255, 51, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("4s 461ms");
        jPanel1.add(jLabel20);
        jLabel20.setBounds(249, 110, 80, 25);

        jLabel21.setForeground(new java.awt.Color(255, 51, 51));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("32ms");
        jPanel1.add(jLabel21);
        jLabel21.setBounds(249, 185, 80, 25);

        jLabel22.setForeground(new java.awt.Color(255, 51, 51));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("12ms");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(249, 222, 80, 25);
        jPanel1.add(pgbCriarTabelas1);
        pgbCriarTabelas1.setBounds(80, 222, 160, 25);
        jPanel1.add(pgbSelecionar1);
        pgbSelecionar1.setBounds(80, 185, 160, 25);

        jLabel23.setForeground(new java.awt.Color(0, 102, 51));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("8ms");
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel23);
        jLabel23.setBounds(0, 222, 80, 25);

        jLabel24.setForeground(new java.awt.Color(0, 102, 51));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("15ms");
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel24);
        jLabel24.setBounds(0, 185, 80, 25);

        jTabbedPane1.addTab("Seven", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setLayout(null);

        jLabel25.setForeground(new java.awt.Color(0, 102, 51));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("8ms");
        jPanel2.add(jLabel25);
        jLabel25.setBounds(0, 2, 80, 25);
        jPanel2.add(pgbInserir2);
        pgbInserir2.setBounds(80, 2, 160, 25);

        jLabel26.setForeground(new java.awt.Color(255, 51, 51));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("15ms");
        jPanel2.add(jLabel26);
        jLabel26.setBounds(249, 2, 80, 25);

        jLabel27.setForeground(new java.awt.Color(255, 51, 51));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("47ms");
        jPanel2.add(jLabel27);
        jLabel27.setBounds(249, 42, 80, 25);
        jPanel2.add(pgbCriarBanco2);
        pgbCriarBanco2.setBounds(80, 42, 160, 25);

        jLabel28.setForeground(new java.awt.Color(0, 102, 51));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("31ms");
        jPanel2.add(jLabel28);
        jLabel28.setBounds(0, 42, 80, 25);

        jLabel29.setForeground(new java.awt.Color(0, 102, 51));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("6s 312ms");
        jPanel2.add(jLabel29);
        jLabel29.setBounds(0, 110, 80, 25);
        jPanel2.add(pgbBenchMark2);
        pgbBenchMark2.setBounds(80, 110, 160, 25);

        jLabel30.setForeground(new java.awt.Color(255, 51, 51));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("6s 859ms");
        jPanel2.add(jLabel30);
        jLabel30.setBounds(249, 110, 80, 25);

        jLabel31.setForeground(new java.awt.Color(255, 51, 51));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("16ms");
        jPanel2.add(jLabel31);
        jLabel31.setBounds(249, 185, 80, 25);
        jPanel2.add(pgbSelecionar2);
        pgbSelecionar2.setBounds(80, 185, 160, 25);

        jLabel32.setForeground(new java.awt.Color(0, 102, 51));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("15ms");
        jPanel2.add(jLabel32);
        jLabel32.setBounds(0, 185, 80, 25);

        jLabel33.setForeground(new java.awt.Color(0, 102, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("8ms");
        jPanel2.add(jLabel33);
        jLabel33.setBounds(0, 222, 80, 25);
        jPanel2.add(pgbCriarTabelas2);
        pgbCriarTabelas2.setBounds(80, 222, 160, 25);

        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("15ms");
        jPanel2.add(jLabel34);
        jLabel34.setBounds(249, 222, 80, 25);

        jTabbedPane1.addTab("WinXP", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setLayout(null);
        jPanel3.add(pgbInserir3);
        pgbInserir3.setBounds(80, 2, 160, 25);

        jLabel35.setForeground(new java.awt.Color(0, 102, 51));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("6ms");
        jPanel3.add(jLabel35);
        jLabel35.setBounds(0, 2, 80, 25);

        jLabel36.setForeground(new java.awt.Color(255, 51, 51));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("14ms");
        jPanel3.add(jLabel36);
        jLabel36.setBounds(249, 2, 80, 25);

        jLabel37.setForeground(new java.awt.Color(255, 51, 51));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("47ms");
        jPanel3.add(jLabel37);
        jLabel37.setBounds(249, 42, 80, 25);
        jPanel3.add(pgbCriarBanco3);
        pgbCriarBanco3.setBounds(80, 42, 160, 25);

        jLabel38.setForeground(new java.awt.Color(0, 102, 51));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("15ms");
        jPanel3.add(jLabel38);
        jLabel38.setBounds(0, 42, 80, 25);

        jLabel39.setForeground(new java.awt.Color(0, 102, 51));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("3s 045ms");
        jPanel3.add(jLabel39);
        jLabel39.setBounds(0, 110, 80, 25);
        jPanel3.add(pgbBenchMark3);
        pgbBenchMark3.setBounds(80, 110, 160, 25);

        jLabel40.setForeground(new java.awt.Color(255, 51, 51));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("3s 839ms");
        jPanel3.add(jLabel40);
        jLabel40.setBounds(249, 110, 80, 25);

        jLabel41.setForeground(new java.awt.Color(255, 51, 51));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("31ms");
        jPanel3.add(jLabel41);
        jLabel41.setBounds(249, 185, 80, 25);

        jLabel42.setForeground(new java.awt.Color(255, 51, 51));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("8ms");
        jPanel3.add(jLabel42);
        jLabel42.setBounds(249, 222, 80, 25);
        jPanel3.add(pgbCriarTabelas3);
        pgbCriarTabelas3.setBounds(80, 222, 160, 25);
        jPanel3.add(pgbSelecionar3);
        pgbSelecionar3.setBounds(80, 185, 160, 25);

        jLabel43.setForeground(new java.awt.Color(0, 102, 51));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("4ms");
        jPanel3.add(jLabel43);
        jLabel43.setBounds(0, 185, 80, 25);

        jLabel44.setForeground(new java.awt.Color(0, 102, 51));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel44.setText("2ms");
        jPanel3.add(jLabel44);
        jLabel44.setBounds(0, 222, 80, 25);

        jTabbedPane1.addTab("Linux", jPanel3);

        jPanel5.setLayout(null);

        jLabel5.setText("Intel Core Quad Q6600 2.4GHz");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(20, 28, 194, 16);

        jLabel6.setText("Mother Board Asus Maximus Formula");
        jPanel5.add(jLabel6);
        jLabel6.setBounds(20, 58, 234, 16);

        jLabel7.setText("Mem Corsair 3GB, FSB 800MHz DDR2");
        jPanel5.add(jLabel7);
        jLabel7.setBounds(20, 92, 232, 16);

        jLabel8.setText("HD 160GB, SATAII, buffer 8MB.  ");
        jPanel5.add(jLabel8);
        jLabel8.setBounds(20, 126, 201, 16);

        jTabbedPane1.addTab("Hardware", jPanel5);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(576, 162, 362, 354);

        jLabel10.setText("1");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(10, 220, 20, 25);

        jLabel11.setText("2");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 260, 20, 25);

        jLabel12.setText("3");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 330, 20, 25);

        jLabel13.setText("4");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(10, 400, 20, 25);

        jLabel14.setText("5");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(10, 440, 20, 25);

        jLabel46.setText("1");
        getContentPane().add(jLabel46);
        jLabel46.setBounds(560, 220, 20, 25);

        jLabel47.setText("2");
        getContentPane().add(jLabel47);
        jLabel47.setBounds(560, 260, 20, 25);

        jLabel48.setText("3");
        getContentPane().add(jLabel48);
        jLabel48.setBounds(560, 330, 20, 25);

        jLabel50.setText("4");
        getContentPane().add(jLabel50);
        jLabel50.setBounds(560, 400, 20, 25);

        jLabel45.setText("5");
        getContentPane().add(jLabel45);
        jLabel45.setBounds(560, 440, 20, 25);

        jLabel9.setForeground(new java.awt.Color(51, 51, 255));
        jLabel9.setText("seu tempo");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(720, 520, 90, 25);

        jLabel49.setForeground(new java.awt.Color(0, 102, 51));
        jLabel49.setText("menor tempo");
        getContentPane().add(jLabel49);
        jLabel49.setBounds(580, 520, 100, 25);

        jLabel51.setForeground(new java.awt.Color(255, 51, 51));
        jLabel51.setText("maior tempo");
        getContentPane().add(jLabel51);
        jLabel51.setBounds(840, 520, 90, 25);
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(20, 490, 510, 20);

        cmbBanco.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "PostgreSQL", "Oracle11g", "SQLServer", "FireBird", "SQLServerIntegrated" }));
        cmbBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBancoActionPerformed(evt);
            }
        });
        getContentPane().add(cmbBanco);
        cmbBanco.setBounds(350, 10, 180, 27);

        chkC.setText("C");
        chkC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCActionPerformed(evt);
            }
        });
        getContentPane().add(chkC);
        chkC.setBounds(240, 190, 41, 25);

        chkPortuguese.setText("Portuguese");
        chkPortuguese.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPortugueseActionPerformed(evt);
            }
        });
        getContentPane().add(chkPortuguese);
        chkPortuguese.setBounds(380, 190, 102, 25);

        lblPostgre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lblPostgre);
        lblPostgre.setBounds(30, 190, 200, 25);

        btnOk.setText("ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        getContentPane().add(btnOk);
        btnOk.setBounds(380, 290, 70, 29);

        txtConsole.setColumns(20);
        txtConsole.setRows(5);
        jScrollPane1.setViewportView(txtConsole);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 560, 930, 84);

        jLabel52.setFont(new java.awt.Font("Lucida Grande", 2, 13));
        jLabel52.setText("Console");
        getContentPane().add(jLabel52);
        jLabel52.setBounds(20, 536, 130, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
       try{ 
           String qual = cmbBanco.getSelectedItem().toString();
           conexao.conectaBancos(qual).createStatement();
           if((cmbBanco.getSelectedIndex()==1) || (cmbBanco.getSelectedIndex()==2) || (cmbBanco.getSelectedIndex()==3) || (cmbBanco.getSelectedIndex()==4) || (cmbBanco.getSelectedIndex()==5) ) {
               btnCriarBanco.setEnabled(false);
               btnEliminarBanco.setEnabled(false);

           }else{
               btnCriarBanco.setEnabled(true);
               btnEliminarBanco.setEnabled(true);
           }
           if(cmbBanco.getSelectedIndex()>=1){
               btnInserir.setEnabled(true);
           }
           btnCriarTabelas.setEnabled(true);
           //lblCriarBanco.setText("");
           //lblCriarTabelas.setText("");
            
           
           
           btnConectar.setText("Conectado "+qual);
       }catch (Exception e){
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, "Erro na conexão. " + e);
       }
    }//GEN-LAST:event_btnConectarActionPerformed

    private void btnCriarTabelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarTabelasActionPerformed
        // TODO add your handling code here:
        qual = cmbBanco.getSelectedItem().toString();

        try {
            Statement stmt = conexao.conectaBancos(qual).createStatement();
            switch (cmbBanco.getSelectedIndex()){
                case 0://MySQL
                    grava = "USE bench001;";
           
                    stmt.executeUpdate(grava);

                    grava = "CREATE TABLE IF NOT EXISTS `clientes` (" +
                            "`id` int(3) NOT NULL AUTO_INCREMENT," +
                            "`nome` varchar(50) NOT NULL," +
                            "`endereco` varchar(49) NOT NULL," +
                            "`versao` int(10)," +
                            "`ativo` int(2) NOT NULL," +
                            "`cidade` int(5) NOT NULL," +
                            "`dataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                            "PRIMARY KEY (`id`)" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;";
                    cmbFormato.setEnabled(true);
                    lbl02.setText("Tabela é do tipo InnoDB.");
                    btnDefinirFormato.setEnabled(true);
                    btnCriarTabelas.setEnabled(false);
                    break;
                case 1://PostgreSQL  
                    grava ="CREATE or REPLACE FUNCTION CriaTabela() RETURNS integer AS $$\n"
                            +"DECLARE \n"
                            +"foiCriada INTEGER :=0;\n"
                            +"tabela RECORD;\n"
                            +"BEGIN \n"
                            +" SELECT INTO tabela tablename FROM pg_tables where tablename='clientes' and schemaname = ANY (current_schemas(true));\n"
                            +" IF tabela.tablename IS NULL THEN \n"
                            +" CREATE TABLE clientes("
                            + "id integer NOT NULL,"
                            + "nome text,"
                            + "endereco text,"
                            + "versao integer,"
                            + "ativo integer,"
                            + "cidade integer,"
                            + "dataCadastro timestamp without time zone[],"
                            + " CONSTRAINT clientes_pkey PRIMARY KEY (id)"
                            + ")\n"
                            + "WITH (\n"
                            + "  OIDS=FALSE\n"
                            + ");\n"
                            +" foiCriada = 1;\n"
                            +" END IF;\n"
                            +" RETURN foiCriada;\n"
                            +" END;\n"
                            +" $$ LANGUAGE plpgsql;";
                    System.out.println("g"+grava);
                    int criou = stmt.executeUpdate(grava);
                    grava = "select criaTabela();";
                    stmt.executeQuery(grava);
                    grava = "ALTER TABLE clientes OWNER TO postgres;";
                    stmt.executeUpdate(grava);
                    if (criou==0){
                        JOptionPane.showMessageDialog(this,"A tabela já existe");
                    }
                    btnDefinirFormato.setEnabled(false);
                    cmbFormato.setEnabled(false);
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    btnSelecionar.setEnabled(false);
                    btnBenchMark.setEnabled(false);        
                    break;
                case 2://Oracle11g
                    //grava = "BEGIN TRANSACTION;";
                    //stmt.executeUpdate(grava);
                    grava = "CREATE TABLE hr.clientes(" +
                            "id decimal(4) NOT NULL constraint id_pk primary key," +
                            "nome varchar2(50) NOT NULL," +
                            "endereco varchar2(49) NOT NULL," +
                            "versao decimal(10)," +
                            "ativo decimal(2) NOT NULL," +
                            "cidade decimal(5) NOT NULL," +
                            "dataCadastro timestamp NOT NULL);"; 
                    System.out.println("Grava:\n"+grava+"\n\nFIM");
                    btnDefinirFormato.setEnabled(false);
                    cmbFormato.setEnabled(false);
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    btnSelecionar.setEnabled(false);
                    btnBenchMark.setEnabled(false);
                    int criou2 = stmt.executeUpdate(grava);
                    if (criou2==0){
                        JOptionPane.showMessageDialog(this,"A tabela já existe");
                        //grava = "ROLLBACK;";
                        //stmt.executeUpdate(grava);
                    }else{
                        //grava = "COMMIT;";  
                    }
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
                    btnDefinirFormato.setEnabled(false);
                    cmbFormato.setEnabled(false);
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    btnSelecionar.setEnabled(false);
                    btnBenchMark.setEnabled(false);
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
                    btnDefinirFormato.setEnabled(false);
                    cmbFormato.setEnabled(false);
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    btnSelecionar.setEnabled(false);
                    btnBenchMark.setEnabled(false);
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
                    btnDefinirFormato.setEnabled(false);
                    cmbFormato.setEnabled(false);
                    lbl02.setText("...");
                    btnCriarTabelas.setEnabled(false);
                    btnSelecionar.setEnabled(false);
                    btnBenchMark.setEnabled(false);
                    break;
            
            }
            
            long tempInicial = System.currentTimeMillis();
            stmt.executeUpdate(grava);
            tempFinal = System.currentTimeMillis();
            long dif = (tempFinal - tempInicial);
            //lblCriarTabelas.setText(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000));
            lblCriarTabelas.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
            lblCriarTabelas.setForeground(Color.blue);
            btnInserir.setEnabled(true);
                       
            ini = 15;
            fim = 47;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbCriarBanco1.setValue(resultado);
            
            ini = 31;
            fim = 47;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbCriarBanco2.setValue(resultado);
            
            
            ini = 15;
            fim = 47;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbCriarBanco3.setValue(resultado);
            
            
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
       
    }//GEN-LAST:event_btnCriarTabelasActionPerformed


    private void btnCriarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarBancoActionPerformed
        try {
            qual = cmbBanco.getSelectedItem().toString();    
            
            Statement stmt = conexao.conectaBancos(qual).createStatement();  
            tempInicial = System.currentTimeMillis();
            switch (cmbBanco.getSelectedIndex()){
                case 0://MySQL
                    grava = "CREATE DATABASE IF NOT EXISTS bench001;";
                    break;
                case 1://PostgreSQL
                    String lc = "";
                    if (chkC.isSelected()){
                        lc = "C";
                    }
                    if (chkPortuguese.isSelected()){
                        lc = "Portuguese_Brazil.1252";
                    }
                    grava ="CREATE DATABASE \"bench001\""
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
                    +"CREATE DATABASE \""+txtIp.getText()+"/"+txtPorta.getText()+":c:\\bench001.fdb\" USER \""+txtUsuario.getText()+"\" PASSWORD \""+txtSenha.getText()+"\" PAGE_SIZE 4096";        
                    break;
                            
            }
            try{
                stmt.executeUpdate(grava);            
            }catch (Exception e){
                JOptionPane.showMessageDialog(this,"O banco já existe");
            }
            tempFinal = System.currentTimeMillis();
            long dif = (tempFinal - tempInicial);
            lblCriarBanco.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
            btnCriarBanco.setEnabled(false);
            lblCriarBanco.setForeground(Color.blue);
            btnCriarTabelas.setEnabled(true);
            lbl01.setText("bench001 criado no "+cmbBanco.getSelectedItem().toString()+".");
            
            ini = 15;
            fim = 16;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbInserir1.setValue(resultado);
            System.out.println(resultado);
            
            ini = 8;
            fim = 15;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbInserir2.setValue(resultado);
            
            ini = 6;
            fim = 14;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbInserir3.setValue(resultado);
            
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnCriarBancoActionPerformed

    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        // TODO add your handling code here:
        tempInicial = System.currentTimeMillis();
        int i;
        limite = Integer.parseInt(txtLimite.getText());
        Runnable r1 = new threadInsert();
        new Thread(r1).start();
    }//GEN-LAST:event_btnInserirActionPerformed

    private void btnSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarActionPerformed
        // TODO add your handling code here:
       
       
        int i;

        try {
            Statement stmt = conexao.conectaBanco().createStatement();
           
            grava = "USE bench001;";
            stmt.executeUpdate(grava);

            grava = "SELECT * FROM clientes WHERE nome ='Nome Completo 76591';";
            tempInicial = System.currentTimeMillis();
            stmt.executeQuery(grava);


            tempFinal = System.currentTimeMillis();
            long dif = (tempFinal - tempInicial);
            
            
            ini = 15;
            fim = 32;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbSelecionar1.setValue(resultado);
            System.out.println(resultado);
            
            
            ini = 15;
            fim = 16;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbSelecionar2.setValue(resultado);
            
            
            ini = 4;
            fim = 31;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbSelecionar3.setValue(resultado);
            lblSelecionar.setForeground(Color.blue);
           
            lblSelecionar.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnSelecionarActionPerformed

    private void btnEliminarBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarBancoActionPerformed
        // TODO add your handling code here:
         tempInicial = System.currentTimeMillis();
         

        try {
            Statement stmt = conexao.conectaBanco().createStatement();
            grava = "DROP DATABASE if EXISTS bench001;";
            stmt.executeUpdate(grava);
            tempFinal = System.currentTimeMillis();
            long dif = (tempFinal - tempInicial);
            //lblCriarBanco.setText(String.format("%02d segundos  e %02d milisegundos", dif/60, dif%60));
            btnCriarBanco.setEnabled(true);
            btnCriarTabelas.setEnabled(false);
            btnDefinirFormato.setEnabled(false);
            btnInserir.setEnabled(false);
            cmbFormato.setEnabled(false);
            btnLimpar.setEnabled(false);
            btnSelecionar.setEnabled(false);
            btnBenchMark.setEnabled(false);
            lbl01.setText("Banco bench001 removido.");
            lbl02.setText("");
            lbl03.setText("");
           
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnEliminarBancoActionPerformed

    private void btnDefinirFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefinirFormatoActionPerformed
        // TODO add your handling code here:
       
         try {
            Statement stmt = conexao.conectaBanco().createStatement();
            grava = " ALTER TABLE bench001.clientes ENGINE = "+cmbFormato.getSelectedItem().toString()+
                    " ROW_FORMAT = DYNAMIC;";
            stmt.executeUpdate(grava);
            lbl02.setText("Tabela é do tipo "+cmbFormato.getSelectedItem().toString()+".");
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }





    }//GEN-LAST:event_btnDefinirFormatoActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
        qual = cmbBanco.getSelectedItem().toString();    
        try{    
            Statement stmt = conexao.conectaBancos(qual).createStatement();  
            tempInicial = System.currentTimeMillis();
            switch (cmbBanco.getSelectedIndex()){
                case 0://MySQL
                    grava = "USE bench001;";
                    stmt.executeUpdate(grava);
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");
                    break;
                case 1://PostgreSQL
                    grava = "TRUNCATE TABLE clientes";
                    stmt.executeUpdate(grava);
                    btnInserir.setEnabled(true);
                    lbl03.setText("Com 0 linhas.");                 
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
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnBenchMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBenchMarkActionPerformed
        // TODO add your handling code here:
        
         
        int i;
        Integer quantos;
        quantos = Integer.parseInt(txtBenchMark.getText());

        try {
            Statement stmt = conexao.conectaBanco().createStatement();

            grava = "USE bench001;";
            stmt.executeUpdate(grava);

            grava = "SELECT Benchmark("+quantos+", (SELECT id FROM bench001.clientes " +
                    "WHERE clientes.nome = 'Nome Completo 500 'LIMIT 1) ) ";
            tempInicial = System.currentTimeMillis();
            stmt.executeQuery(grava);
             tempFinal = System.currentTimeMillis();
            long dif = (tempFinal - tempInicial);
            ini = 8;
            fim = 12;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbCriarTabelas1.setValue(resultado);
            System.out.println(resultado);
            ini = 8;
            fim = 15;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbCriarTabelas2.setValue(resultado);
            ini = 2;
            fim = 8;
            resultado = funcoes.fazProgresso (dif, ini,fim);
            pgbCriarTabelas3.setValue(resultado);
            lblBenchMark.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
            lblBenchMark.setForeground(Color.blue); 
            
        }
        catch( Exception e ) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
        }
    }//GEN-LAST:event_btnBenchMarkActionPerformed

    private void cmbFormatoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFormatoItemStateChanged
        // TODO add your handling code here:
       
    }//GEN-LAST:event_cmbFormatoItemStateChanged

    private void cmbFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFormatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFormatoActionPerformed

    private void cmbBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBancoActionPerformed
        // TODO add your handling code here:
        btnConectar.setText("Conectar");
        lbl01.setText("...");
        lbl02.setText("...");
        lbl03.setText("...");
        
        switch (cmbBanco.getSelectedIndex()){
            case 0: //MySQL
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("root");
                txtSenha.setText("123");
                txtPorta.setText("3306");
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                btnDefinirFormato.setEnabled(true);
                btnCriarBanco.setText("Criar Banco");
                btnCriarBanco.setEnabled(true);
                btnCriarTabelas.setText("Criar Tabelas");
                btnCriarTabelas.setEnabled(true);                
                cmbFormato.removeAllItems();
                cmbFormato.addItem("InnoDB");
                cmbFormato.addItem("ARCHIVE");
                cmbFormato.addItem("MRG_MYISAM");
                cmbFormato.addItem("MEMORY");
                cmbFormato.addItem("MyISAM");
                cmbFormato.setEnabled(true);
                btnOk.setEnabled(true);
                //btnCriarBanco.setEnabled(true);
                btnCriarBanco.setText("Criar Banco");
                break;
            case 1://PostgreSQL
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("postgres");
                txtSenha.setText("123");
                txtPorta.setText("5432");
                //chkC.setVisible(true);
                //chkPortuguese.setVisible(true);
                cmbFormato.removeAllItems();
                btnDefinirFormato.setEnabled(false);
                //btnCriarBanco.setText("indisponível");
                btnCriarBanco.setEnabled(false);
                btnBenchMark.setEnabled(false);
                //lblPostgre.setText("LC_COLLATE/LC_CTYPE:");
                cmbFormato.setEnabled(false);
                btnOk.setEnabled(false);

                
                break;
            case 2: //Oracle11g
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("SYSTEM");
                txtSenha.setText("123");
                txtPorta.setText("1521");
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                cmbFormato.removeAllItems();
                btnDefinirFormato.setEnabled(false);
                //btnCriarBanco.setText("indisponível");
                btnCriarBanco.setEnabled(false);
                //btnCriarTabelas.setText("indisponível");
                btnCriarTabelas.setEnabled(false);
                btnBenchMark.setEnabled(false);
                btnOk.setEnabled(false);
                break;
            case 3:// SQLServer
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("root");
                txtSenha.setText("123");
                txtPorta.setText("1433");
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                cmbFormato.removeAllItems();
                btnDefinirFormato.setEnabled(false);
                btnCriarBanco.setEnabled(false);
                btnCriarTabelas.setEnabled(true);
                btnBenchMark.setEnabled(false);
                btnOk.setEnabled(false);
                btnInserir.setEnabled(true);
                break;
            case 4://FireBird
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("sysdba");
                txtSenha.setText("masterkey");
                txtPorta.setText("3050");
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                btnOk.setEnabled(false);
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                cmbFormato.removeAllItems();
                btnDefinirFormato.setEnabled(false);
                //btnCriarBanco.setText("indisponível");
                btnCriarBanco.setEnabled(false);
                //btnCriarTabelas.setText("indisponível");
                btnCriarTabelas.setEnabled(false);
                btnBenchMark.setEnabled(false);
                btnOk.setEnabled(false);
                btnInserir.setEnabled(true);
                break;
            case 5://SQLServerIntegrated
                txtIp.setText("192.168.1.2");
                txtUsuario.setText("");
                txtSenha.setText("");
                txtPorta.setText("");
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                btnOk.setEnabled(false);
                chkC.setVisible(false);
                chkPortuguese.setVisible(false);
                cmbFormato.removeAllItems();
                btnDefinirFormato.setEnabled(false);
                //btnCriarBanco.setText("indisponível");
                btnCriarBanco.setEnabled(false);
                //btnCriarTabelas.setText("indisponível");
                btnCriarTabelas.setEnabled(false);
                btnBenchMark.setEnabled(false);
                btnOk.setEnabled(false);
                btnInserir.setEnabled(true);
                break;              
        }

    }//GEN-LAST:event_cmbBancoActionPerformed

    private void chkPortugueseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPortugueseActionPerformed
        // TODO add your handling code here:
        chkC.setSelected(false);
    }//GEN-LAST:event_chkPortugueseActionPerformed

    private void chkCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCActionPerformed
        // TODO add your handling code here:
        chkPortuguese.setSelected(false);
    }//GEN-LAST:event_chkCActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
         switch (cmbBanco.getSelectedIndex()){
            case 0: //MySQL
                try {
                    Statement stmt = conexao.conectaBanco().createStatement();
                    grava = " ALTER TABLE bench001.clientes ENGINE = "+cmbFormato.getSelectedItem().toString()+
                            " ROW_FORMAT = DYNAMIC;";
                    stmt.executeUpdate(grava);
                    System.out.println("SQL\n"+grava);
                    lbl02.setText("Tabela é do tipo "+cmbFormato.getSelectedItem().toString()+".");
                }
                catch( Exception e ) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro no SQL ["+grava+"] "+e.getMessage());
                }
                break;
        }
    }//GEN-LAST:event_btnOkActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnBenchMark;
    public static javax.swing.JButton btnConectar;
    public static javax.swing.JButton btnCriarBanco;
    public static javax.swing.JButton btnCriarTabelas;
    public static javax.swing.JButton btnDefinirFormato;
    public static javax.swing.JButton btnEliminarBanco;
    public static javax.swing.JButton btnInserir;
    public static javax.swing.JButton btnLimpar;
    public static javax.swing.JButton btnOk;
    public static javax.swing.JButton btnSelecionar;
    private javax.swing.JCheckBox chkC;
    private javax.swing.JCheckBox chkPortuguese;
    public static javax.swing.JComboBox cmbBanco;
    public static javax.swing.JComboBox cmbFormato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JLabel lbl01;
    public static javax.swing.JLabel lbl02;
    public static javax.swing.JLabel lbl03;
    public static javax.swing.JLabel lblBenchMark;
    public static javax.swing.JLabel lblCriarBanco;
    public static javax.swing.JLabel lblCriarTabelas;
    public static javax.swing.JLabel lblInserir;
    private javax.swing.JLabel lblPostgre;
    public static javax.swing.JLabel lblSelecionar;
    public static javax.swing.JProgressBar pgbBenchMark1;
    public static javax.swing.JProgressBar pgbBenchMark2;
    public static javax.swing.JProgressBar pgbBenchMark3;
    public static javax.swing.JProgressBar pgbCriarBanco1;
    public static javax.swing.JProgressBar pgbCriarBanco2;
    public static javax.swing.JProgressBar pgbCriarBanco3;
    public static javax.swing.JProgressBar pgbCriarTabelas1;
    public static javax.swing.JProgressBar pgbCriarTabelas2;
    public static javax.swing.JProgressBar pgbCriarTabelas3;
    public static javax.swing.JProgressBar pgbInserir1;
    public static javax.swing.JProgressBar pgbInserir2;
    public static javax.swing.JProgressBar pgbInserir3;
    public static javax.swing.JProgressBar pgbSelecionar1;
    public static javax.swing.JProgressBar pgbSelecionar2;
    public static javax.swing.JProgressBar pgbSelecionar3;
    public static javax.swing.JTextField txtBenchMark;
    private javax.swing.JTextArea txtConsole;
    public static javax.swing.JTextField txtIp;
    public static javax.swing.JTextField txtLimite;
    public static javax.swing.JTextField txtPorta;
    public static javax.swing.JTextField txtSenha;
    public static javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}