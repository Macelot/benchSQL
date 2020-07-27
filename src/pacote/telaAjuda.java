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

import javax.swing.*;
import java.awt.*;
import java.sql.Statement;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 *
 * @author marcelojosuetelles
 */
public class telaAjuda extends javax.swing.JInternalFrame  implements TableModelListener{
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

    public String sistemaIp, sistemaPorta, sistemaUser, sistemaSenha, sistemaRegistro, consulta = "", oqueBusca, grava, altera, deleta, empresaSistemaAntigo, idColaboradorFK, sigla, descricao, dataCadastro, dataUpdate, ativo;
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
    public static String qual="";
    String idGrupo;


    /** Creates new form estadoTela */
    public telaAjuda () {
        initComponents();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Tela de Ajuda");
        setMaximumSize(new java.awt.Dimension(1000, 600));
        setPreferredSize(new java.awt.Dimension(1000, 575));

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setText("Os valores de referência apresentados foram obtidos realizando os testes 10 vezes,\nem cada sistema operacional (Windows Seven, Windows XP e Linux Ubuntu).\nO hardware utilizado está descrito na aba \"hardware\".\nOs testes foram cronometrados utilizando o valor de 50000 linhas.\nO tipo de tabela adotado foi o MyISAM.");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("Versão 2");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(56, 56, 56)
                        .add(jLabel1))
                    .add(layout.createSequentialGroup()
                        .add(47, 47, 47)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 603, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(16, 16, 16)
                .add(jLabel1)
                .add(18, 18, 18)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(331, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

   
}