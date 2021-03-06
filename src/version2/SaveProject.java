/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package version2;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author marcos
 */
public class SaveProject extends javax.swing.JFrame {
    private MainWindow mainWindow;
    private Timer t;
    private TimerTask task;
    private Random rand;
    
    /**
     * Creates new form SaveProject
     */
    public SaveProject() {
        initComponents();
        
        jLabel1.setVisible(false);
        
        rand = new Random();
        
        t = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                java.awt.EventQueue.invokeLater(() -> {
                    load();
                });
            }
        };
    }
    
    public SaveProject(MainWindow mw) {
        this();
        
        mainWindow = mw;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooser = new javax.swing.JFileChooser();
        Button_Save = new javax.swing.JButton();
        Button_Cancel = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setResizable(false);

        chooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        chooser.setControlButtonsAreShown(false);
        chooser.setDialogTitle("");
        chooser.setFileFilter(new FileNameExtensionFilter("Paint 2.0 project", "p2"));
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);

        Button_Save.setText("Save");
        Button_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SaveActionPerformed(evt);
            }
        });

        Button_Cancel.setText("Cancel/Stop");
        Button_Cancel.setEnabled(false);
        Button_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_CancelActionPerformed(evt);
            }
        });

        jProgressBar1.setEnabled(false);

        jLabel1.setBackground(new java.awt.Color(34, 34, 34));
        jLabel1.setText("Loaded!");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(chooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(Button_Save)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(108, 108, 108)
                        .addComponent(Button_Cancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Button_Save)
                        .addComponent(Button_Cancel))
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Button_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_CancelActionPerformed
        t.cancel();
        
        JOptionPane.showMessageDialog(this, "Saving process canceled", "Canceled!",JOptionPane.OK_OPTION);
        
        loaded();
    }//GEN-LAST:event_Button_CancelActionPerformed

    private void Button_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SaveActionPerformed
        t.schedule(task, 0, 500);
        jProgressBar1.setEnabled(true);
        Button_Cancel.setEnabled(true);
    }//GEN-LAST:event_Button_SaveActionPerformed

    public void saveProject(){
        try{
            File oFile = new File(chooser.getSelectedFile().getAbsolutePath().replace(".p2", "") + ".p2");

            System.out.println(chooser.getSelectedFile().getAbsolutePath() + ".p2");
            if(oFile.exists()){
                int option = JOptionPane.showConfirmDialog(this, "The file already exists! Overwrite the file?", "File exists", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

                if(option != JOptionPane.OK_OPTION)
                    throw new IOException();
            }

            try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(oFile))) {
                output.writeObject(mainWindow.getPainting());
                output.close();
            }


            System.out.println("Image saved!");

            mainWindow.getRecentWindow().addFile(oFile);
        }catch(IOException e){}
    }
    
    public void load(){
        if(rand.nextFloat()<0.9)
            jProgressBar1.setValue(jProgressBar1.getValue() + rand.nextInt(10));
        
        if(jProgressBar1.getValue()>=100){
            t.cancel();
            saveProject();
            loaded();
        }
    }
    
    public void changeColor(Color c){
        jLabel1.setBackground(c);
    }
    
    public void loaded(){
        t = new Timer();
        
        jLabel1.setVisible(true);
        
        t.schedule(new TimerTask(){
            @Override
            public void run(){
                reset();
                stopTimer();
            }
        }, 1000);
        
        mainWindow.changeSaved();
    }
    
    public void reset(){
        jProgressBar1.setValue(0);
        jProgressBar1.setEnabled(false);
        
        Button_Cancel.setEnabled(false);
        
        this.setVisible(false);
    }
    
    public void stopTimer(){
        t.cancel();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SaveProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaveProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaveProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaveProject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SaveProject().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Cancel;
    private javax.swing.JButton Button_Save;
    private javax.swing.JFileChooser chooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
