package dbf.desk;

import static dbf.desk.MySQLConnection.host;
import javax.swing.JOptionPane;

/**
 * @author Nawaz Sarwar
 */
public class DBConnectionSettings extends javax.swing.JInternalFrame {

    public DBConnectionSettings() {
        initComponents();
    }

    public void testConnection() {
        boolean status = MySQLConnection.testConnectMySQL(txt_host.getText(), txt_port.getText(), txt_dbuser.getText(), txt_dbpassword.getText(), txt_dbname.getText());
        if (status) {
            JOptionPane.showMessageDialog(rootPane, "Connection Successfull");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Connection Failed, watch console for more details");
        }
    }

    public void saveSettings() {

        try {
            DBFDesk.host = txt_host.getText();
            DBFDesk.port = txt_port.getText();
            DBFDesk.dbuser = txt_dbuser.getText();
            DBFDesk.dbpassword = txt_dbpassword.getText();
            DBFDesk.dbname = txt_dbname.getText();
            
//            System.out.println(DBFDesk.host);
//            System.out.println(DBFDesk.port);
//            System.out.println(DBFDesk.dbuser);
//            System.out.println(DBFDesk.dbpassword);
//            System.out.println(DBFDesk.dbname);

            JOptionPane.showMessageDialog(rootPane, "DB Connection saved and will only last untill application is not closed");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error occurred while saving settings :" + e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_host = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_port = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_dbuser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_dbpassword = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txt_dbname = new javax.swing.JTextField();
        btn_test = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Database settings");

        jLabel1.setText("Database Host");

        txt_host.setText("localhost");

        jLabel2.setText("Database Port");

        txt_port.setText("3306");

        jLabel3.setText("Database Username");

        txt_dbuser.setText("root");

        jLabel4.setText("Database Password");

        jLabel5.setText("Database Name (Opt)");

        btn_test.setText("Test");
        btn_test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_testActionPerformed(evt);
            }
        });

        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_test)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_save))
                    .addComponent(txt_dbname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dbpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dbuser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_host, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_dbname, txt_dbpassword, txt_dbuser, txt_host, txt_port});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_dbuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_dbpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_dbname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_test)
                    .addComponent(btn_save))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txt_dbname, txt_dbpassword, txt_dbuser, txt_host, txt_port});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_testActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_testActionPerformed
        testConnection();
    }//GEN-LAST:event_btn_testActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        saveSettings();
    }//GEN-LAST:event_btn_saveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_test;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txt_dbname;
    private javax.swing.JPasswordField txt_dbpassword;
    private javax.swing.JTextField txt_dbuser;
    private javax.swing.JTextField txt_host;
    private javax.swing.JTextField txt_port;
    // End of variables declaration//GEN-END:variables
}
