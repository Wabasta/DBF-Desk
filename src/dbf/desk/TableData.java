package dbf.desk;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Nawaz Sarwar
 */
public class TableData extends javax.swing.JInternalFrame {
    
    String DBFfileNamePath = "";
    int DBFnumberOfFields = 0;
    
    ArrayList<String> DBFfieldNames = new ArrayList<String>();
    ArrayList<String> DBFfieldType = new ArrayList<String>();
    ArrayList<String> DBFfieldWidth = new ArrayList<String>();
    ArrayList<String> DBFfieldDecimal = new ArrayList<String>();
    ArrayList<String> DBFfieldIndex = new ArrayList<String>();
    ArrayList<String> DBFfieldNull = new ArrayList<String>();

    public TableData(String filename) {
        initComponents();
        this.DBFfileNamePath = filename;
        this.setTitle("Display data from "+filename);
        viewDBFByJavaDBFLibrary();
    }
    
    public void viewDBFByJavaDBFLibrary() {
        DefaultTableModel model = (DefaultTableModel) table_browse.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        table_browse.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DBFReader reader = null;
        try {
            // create a DBFReader object
            reader = new DBFReader(new FileInputStream(DBFfileNamePath));

            // get the field count if you want for some reasons like the following
            DBFnumberOfFields = reader.getFieldCount();
            System.out.println("Total Number of Fields :" + DBFnumberOfFields + ":");
            // use this count to fetch all field information
            // if required

            List<List<String>> DBFentireData = new ArrayList<List<String>>();
            List<String> DBFrowData = new ArrayList<String>();

            for (int i = 0; i < DBFnumberOfFields; i++) {

                DBFField field = reader.getField(i);
                DBFfieldNames.add(field.getName());
                DBFfieldType.add(field.getType() + "");
                DBFfieldWidth.add(field.getLength() + "");
                DBFfieldDecimal.add(field.getDecimalCount() + "");
                DBFfieldIndex.add(field.getIndexFieldFlag() + "");
                DBFfieldNull.add(field.isNullable() + "");

                model.addColumn(field.getName());

            }

            // Now, lets us start reading the rows
            Object[] rowObjects;

            while ((rowObjects = reader.nextRecord()) != null) {
                DBFrowData.removeAll(DBFrowData);
                for (int i = 0; i < rowObjects.length; i++) {
                    if (rowObjects[i] != null) {
                        DBFrowData.add(rowObjects[i].toString());
                    } else {
                        DBFrowData.add("NULL");
                    }
                }
                model.addRow(rowObjects);
                DBFentireData.add(DBFrowData);
            }
            table_browse.packAll();

            // By now, we have iterated through all of the rows
        } catch (DBFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DBFUtils.close(reader);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        table_browse = new org.jdesktop.swingx.JXTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Data from the Table");

        table_browse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(table_browse);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTable table_browse;
    // End of variables declaration//GEN-END:variables
}
