package dbf.desk;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Nawaz Sarwar
 */
public class DBFCompare extends javax.swing.JInternalFrame {

    String filenameFirst = "";
    String filenameSecond = "";
    String filenameOnlyFirst = "";
    String filenameOnlySecond = "";
    
    public DBFCompare() {
        initComponents();
    }
    
    public void viewDBFByJavaDBFLibrary(String filename, int tableIndex) {
        DefaultTableModel model = (DefaultTableModel) table_compareFirst.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        table_compareFirst.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DBFReader reader = null;
        try {
            // create a DBFReader object
            reader = new DBFReader(new FileInputStream(filename));

            // get the field count if you want for some reasons like the following
            int numberOfFields = reader.getFieldCount();
            System.out.println("Total Number of Fields :" + numberOfFields + ":");
            // use this count to fetch all field information
            // if required

            ArrayList<String> alFieldNames = new ArrayList<String>();
            ArrayList<String> alFieldType = new ArrayList<String>();
            ArrayList<String> alFieldWidth = new ArrayList<String>();
            ArrayList<String> alFieldDecimal = new ArrayList<String>();
            ArrayList<String> alFieldIndex = new ArrayList<String>();
            ArrayList<String> alFieldNull = new ArrayList<String>();

            for (int i = 0; i < numberOfFields; i++) {
                DBFField field = reader.getField(i);
                alFieldNames.add(field.getName());
                alFieldType.add(field.getType() + "");
                alFieldWidth.add(field.getLength() + "");
                alFieldDecimal.add(field.getDecimalCount() + "");
                alFieldIndex.add(field.getIndexFieldFlag() + "");
                alFieldNull.add(field.isNullable() + "");
//                model.addColumn(field.getName());
            }

            if (model.getColumnCount() == 0) {
                model.addColumn("S. No.");
                model.addColumn("Name");
                model.addColumn("Type");
                model.addColumn("Width");
                model.addColumn("Decimal");
                model.addColumn("Index");
                model.addColumn("Null");
            }

            for (int k = 0; k < numberOfFields; k++) {
                model.addRow(new Object[]{(k + 1), alFieldNames.get(k), alFieldType.get(k), alFieldWidth.get(k), alFieldDecimal.get(k), alFieldIndex.get(k), alFieldNull.get(k)});
            }

            table_compareFirst.packAll();

        } catch (DBFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DBFUtils.close(reader);
        }
    }
    
    public void browseFileFirst() {
        final JFileChooser fc = new JFileChooser("/Users/nawazsarwar/Desktop/NAD/Data/20112017");
        int response = fc.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            txt_custombasedirFirst.setText(fc.getSelectedFile().toString());
            filenameFirst = txt_custombasedirFirst.getText();
            filenameOnlyFirst = fc.getSelectedFile().getName();
            this.setTitle(filenameOnlyFirst+" vs "+filenameOnlySecond);
            
            viewDBFByJavaDBFLibrary(filenameFirst, 1);
        } else {
            JOptionPane.showMessageDialog(rootPane, "File selection operation was cancelled");
        }
    }
    
    public void browseFileSecond() {
        final JFileChooser fc = new JFileChooser("/Users/nawazsarwar/Desktop/NAD/Data/20112017");
        int response = fc.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            txt_custombasedirSecond.setText(fc.getSelectedFile().toString());
            filenameSecond = txt_custombasedirSecond.getText();
            filenameOnlySecond = fc.getSelectedFile().getName();
            this.setTitle(filenameOnlyFirst+" vs "+filenameOnlySecond);
            
            viewDBFByJavaDBFLibrary(filenameSecond, 2);
        } else {
            JOptionPane.showMessageDialog(rootPane, "File selection operation was cancelled");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_firstFile = new javax.swing.JLabel();
        txt_custombasedirFirst = new javax.swing.JTextField();
        btn_browseFirst = new javax.swing.JButton();
        btn_compare = new javax.swing.JButton();
        lbl_secondFile = new javax.swing.JLabel();
        txt_custombasedirSecond = new javax.swing.JTextField();
        btn_browseSecond = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_compareFirst = new org.jdesktop.swingx.JXTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_compareSecond = new org.jdesktop.swingx.JXTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("DBF Comparator");

        lbl_firstFile.setText("Choose first File");

        btn_browseFirst.setText("Browse");
        btn_browseFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseFirstActionPerformed(evt);
            }
        });

        btn_compare.setText("Compare");
        btn_compare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_compareActionPerformed(evt);
            }
        });

        lbl_secondFile.setText("Choose second File");

        btn_browseSecond.setText("Browse");
        btn_browseSecond.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseSecondActionPerformed(evt);
            }
        });

        table_compareFirst.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_compareFirst);

        table_compareSecond.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(table_compareSecond);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_firstFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_custombasedirFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_browseFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_secondFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_custombasedirSecond, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_browseSecond)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_compare))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_firstFile)
                    .addComponent(txt_custombasedirFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_browseFirst)
                    .addComponent(btn_compare)
                    .addComponent(lbl_secondFile)
                    .addComponent(txt_custombasedirSecond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_browseSecond))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_browseFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseFirstActionPerformed
        browseFileFirst();
    }//GEN-LAST:event_btn_browseFirstActionPerformed

    private void btn_compareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_compareActionPerformed
        //        viewDBFByJavaDBFLibrary();
        //        btn_browse.setEnabled(false);
        //        btn_load.setEnabled(false);
        //        txt_custombasedir.setEnabled(false);

//        TableData tabledata = new TableData(filename);
//        pane_main.add(tabledata);
//        tabledata.setVisible(true);
//
//        Dimension desktopSize = pane_main.getSize();
//        Dimension jInternalFrameSize = tabledata.getSize();
//        //        tabledata.setLocation((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
//        tabledata.setLocation(((desktopSize.width - jInternalFrameSize.width)/2), ((desktopSize.height- jInternalFrameSize.height)/2));
    }//GEN-LAST:event_btn_compareActionPerformed

    private void btn_browseSecondActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseSecondActionPerformed
        browseFileSecond();
    }//GEN-LAST:event_btn_browseSecondActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_browseFirst;
    private javax.swing.JButton btn_browseSecond;
    private javax.swing.JButton btn_compare;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_firstFile;
    private javax.swing.JLabel lbl_secondFile;
    private org.jdesktop.swingx.JXTable table_compareFirst;
    private org.jdesktop.swingx.JXTable table_compareSecond;
    private javax.swing.JTextField txt_custombasedirFirst;
    private javax.swing.JTextField txt_custombasedirSecond;
    // End of variables declaration//GEN-END:variables

}