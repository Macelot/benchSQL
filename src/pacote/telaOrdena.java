/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * telaOrdena.java
 *
 * Created on 04/12/2012, 12:08:17
 */
package pacote;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author marcelotelles
 */
public class telaOrdena extends javax.swing.JInternalFrame {
    public static long tempInicial,dif;
    public static long tempFinal;
    public static int tam=1000;

    /** Creates new form telaOrdena */
    public telaOrdena() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lblTempo = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        jButton1.setText("Gera e ordena manualmente");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Gera e ordena usando sort");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Gera e ordena manualmente melhorado");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblTempo.setText("0");

        jTextField1.setText("usuario");

        jTextField2.setText("123");

        jLabel1.setText("txtUsuario");

        jLabel2.setText("txtSenha");

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jButton3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(16, 16, 16)
                                .add(jLabel1))
                            .add(layout.createSequentialGroup()
                                .addContainerGap()
                                .add(jLabel2)))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(lblTempo)
                            .add(jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .add(jTextField1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButton4)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton2)
                .add(29, 29, 29)
                .add(jButton1)
                .add(18, 18, 18)
                .add(jButton3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblTempo)
                    .add(jButton4))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        //gerando
        tempInicial = System.currentTimeMillis();
        ArrayList numeros = new ArrayList();
        for (int i=0;i<tam;i++){
            numeros.add((int)(Math.random() * tam*10));//gera valores de 0 a 100
        }
        //imprimindo
        for (int i=0;i<tam;i++){
            System.out.println("valor ["+i+"] = "+numeros.get(i));
        }
        //ordenando manualmente
        System.out.println("--");
        int v1;
        int v2;
        int bkp,cont=0;
        for (int i=0;i<tam;i++){
            for (int j=0;j<tam-1;j++){
                //v1 = Integer.parseInt(numeros.get(i).toString());
                v1 = Integer.parseInt(numeros.get(j).toString());
                v2 = (Integer)numeros.get(j+1);
                System.out.println(v1+">"+v2);
                cont++;
                if (v1>v2){
                    System.out.println("trocou "+j+" por "+(j+1));
                    bkp = (Integer)numeros.get(j+1);
                    numeros.set(j+1, v1);
                    numeros.set(j, bkp);
                }
            }
            System.out.println("comparações "+cont);
        }
        //imprimindo
        for (int i=0;i<tam;i++){
            System.out.println("valor ["+i+"] = "+numeros.get(i));
        }
        tempFinal = System.currentTimeMillis();
        dif = (tempFinal - tempInicial);
        //lblCriarTabelas.setText(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000));
        lblTempo.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
         
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        tempInicial = System.currentTimeMillis();
        ArrayList numeros = new ArrayList();
        //gerando
        for (int i=0;i<tam;i++){
            numeros.add((int)(Math.random() * tam*10));//gera valores de 0 a 100
        }
        //imprimindo
        for (int i=0;i<tam;i++){
            System.out.println("valor ["+i+"] = "+numeros.get(i));
        }
        //ordenando
        Collections.sort(numeros);
        //imprimindo
        for (int i=0;i<tam;i++){
            System.out.println("valor ["+i+"] = "+numeros.get(i));
        }
        tempFinal = System.currentTimeMillis();
        dif = (tempFinal - tempInicial);
        //lblCriarTabelas.setText(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000));
        lblTempo.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
         
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        //gerando
        tempInicial = System.currentTimeMillis();
        ArrayList numeros = new ArrayList();
        for (int i=0;i<tam;i++){
            numeros.add((int)(Math.random() * tam*10));//gera valores de 0 a 100
        }
        //imprimindo
        for (int i=0;i<tam;i++){
            System.out.println("valor ["+i+"] = "+numeros.get(i));
        }
        //ordenando manualmente
        System.out.println("--");
        int v1;
        int v2;
        int bkp,cont=0;
        for (int i=0;i<tam;i++){
            for (int j=0;j<tam-1-i;j++){
                //v1 = Integer.parseInt(numeros.get(i).toString());
                v1 = Integer.parseInt(numeros.get(j).toString());
                v2 = (Integer)numeros.get(j+1);
                System.out.println(v1+">"+v2);
                cont++;
                if (v1>v2){
                    System.out.println("trocou "+j+" por "+(j+1));
                    bkp = (Integer)numeros.get(j+1);
                    numeros.set(j+1, v1);
                    numeros.set(j, bkp);
                }
            }
            System.out.println("comparações "+cont);
        }
        //imprimindo
        for (int i=0;i<tam;i++){
            System.out.println("valor ["+i+"] = "+numeros.get(i));
        }
        tempFinal = System.currentTimeMillis();
        dif = (tempFinal - tempInicial);
        //lblCriarTabelas.setText(String.format("%02d segundos  e %02d milisegundos", dif/1000, dif%1000));
        lblTempo.setText(String.format("%02d min : %02d seg  e %3d ms",((dif/1000)/60), dif/1000, dif%1000));
           
    }//GEN-LAST:event_jButton3ActionPerformed

private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
// TODO add your handling code here:
    int r;
    r = JOptionPane.showConfirmDialog(this,"Confirma gravação?","Sistema", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    System.out.println("r"+r);

}//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblTempo;
    // End of variables declaration//GEN-END:variables
}