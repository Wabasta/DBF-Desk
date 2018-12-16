package dbf.desk;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * @author Nawaz Sarwar
 */
public class Dashboard extends javax.swing.JFrame {

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement stmt = null;

    String MySQLdatabase = "";
    String MySQLtableName = "";

    String fieldsSQL = ""; //Used in inserting data to table

    ArrayList<ArrayList<String>> DBFfields = new ArrayList<ArrayList<String>>();

    DBFReader reader = null;
    String DBFfileNamePath = "";
    String onlyDBFfileName = "";
    int DBFnumberOfFields = 0;
    int DBFnumberOfRecords = 0;

    ArrayList<String> DBFfieldNames = new ArrayList<String>();
    ArrayList<String> DBFfieldType = new ArrayList<String>();
    ArrayList<String> DBFfieldWidth = new ArrayList<String>();
    ArrayList<String> DBFfieldDecimal = new ArrayList<String>();
    ArrayList<String> DBFfieldIndex = new ArrayList<String>();
    ArrayList<String> DBFfieldNull = new ArrayList<String>();

    public Dashboard() {
        initComponents();
    }

    public void browseFile() {
        final JFileChooser fc = new JFileChooser("/Users/nawazsarwar/Desktop");
//        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int response = fc.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            txt_custombasedir.setText(fc.getSelectedFile().toString());
            DBFfileNamePath = txt_custombasedir.getText();
            onlyDBFfileName = fc.getSelectedFile().getName();
            viewDBFByJavaDBFLibrary();

//            String t = replaceAllSymbols(onlyDBFfileName);
//            System.out.println(":" + t + ":");
            txt_newtablename.setText(replaceAllSymbols(onlyDBFfileName));
        } else {
            JOptionPane.showMessageDialog(rootPane, "File selection operation was cancelled");
        }
    }

    public void viewDBFByJavaDBFLibrary() {
        DefaultTableModel model = (DefaultTableModel) table_DBFStructure.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        table_DBFStructure.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        try {
            // create a DBFReader object
            reader = new DBFReader(new FileInputStream(DBFfileNamePath));

            // get the field count if you want for some reasons like the following
            DBFnumberOfFields = reader.getFieldCount();
            DBFnumberOfRecords = reader.getRecordCount();

//            System.out.println("Total Number of Fields in "+onlyDBFfileName+" :" + DBFnumberOfFields + ":");
//            System.out.println("Total Number of Rows in "+onlyDBFfileName+":" + DBFnumberOfRecords + ":");
            lbl_filedetails.setText("File :" + onlyDBFfileName + "\t\t\t\tTotal Number of Fields:" + DBFnumberOfFields + "\t\t\t\tTotal Number of Rows:" + DBFnumberOfRecords);
            // use this count to fetch all field information
            // if required

            for (int i = 0; i < DBFnumberOfFields; i++) {
                DBFField field = reader.getField(i);
                DBFfieldNames.add(field.getName());
                DBFfieldType.add(field.getType() + "");
                DBFfieldWidth.add(field.getLength() + "");
                DBFfieldDecimal.add(field.getDecimalCount() + "");
                DBFfieldIndex.add(field.getIndexFieldFlag() + "");
                DBFfieldNull.add(field.isNullable() + "");
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

            for (int k = 0; k < DBFnumberOfFields; k++) {
                model.addRow(new Object[]{(k + 1), DBFfieldNames.get(k), DBFfieldType.get(k), DBFfieldWidth.get(k), DBFfieldDecimal.get(k), DBFfieldIndex.get(k), DBFfieldNull.get(k)});
            }

            table_DBFStructure.packAll();

        } catch (DBFException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            DBFUtils.close(reader);
        }
    }

    public void fetchMySQLtables() throws SQLException {
        String dbname = cbo_dbname.getSelectedItem().toString();
        String temp_columnname = "Tables_in_" + dbname;
        conn = MySQLConnection.ConnectMySQL(dbname);

        Statement stmt = conn.createStatement();
        String sql = "SHOW tables";
        ResultSet rs = stmt.executeQuery(sql);

        cbo_tablename.removeAllItems();
        while (rs.next()) {
            String refferedby = rs.getString(temp_columnname);
            cbo_tablename.addItem(refferedby);
        }

        cbo_tablename.setEnabled(true);
//        btn_fetchstructure.setEnabled(true);
    }

    public void fetchMySQLtableStructure() throws SQLException {
//        fields.removeAll(fields);
//        String dbname = cbo_dbname.getSelectedItem().toString();
//        tblname = cbo_tablename.getSelectedItem().toString();
//
//        Statement stmt = conn.createStatement();
//        String sql = "DESCRIBE `" + tblname + "`";
//        ResultSet rs = stmt.executeQuery(sql);
//        tbl_fields.setModel(DbUtils.resultSetToTableModel(rs));
//        rs.beforeFirst();
//
//        ResultSetMetaData metadata = rs.getMetaData();
//        int numcols = metadata.getColumnCount();
//
//        while (rs.next()) {
//            ArrayList<String> row = new ArrayList<>(numcols); // new list per row
//            int i = 1;
//            while (i <= numcols) {  // don't skip the last column, use <=
//                row.add(rs.getString(i++));
//            }
//            fields.add(row); // add it to the result
//        }
//
//        initializeClasses();
//
//        btn_generatephpclasses.setEnabled(true);
//        btn_generateselected.setEnabled(true);
    }

    public void createMySQLtable() {

        MySQLdatabase = cbo_dbname.getSelectedItem().toString();
        MySQLtableName = replaceAllSymbols(txt_newtablename.getText().trim());

        String useDatabase = "USE " + MySQLdatabase;
        String sqlStart = "CREATE TABLE `" + MySQLtableName + "` ( `"+MySQLtableName+"_id` int(11) NOT NULL, ";
        String sqlBody = "";
        String sqlEnd = ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Table created using utility developed by Nawaz Sarwar'";
        String finalSQL = "";

        for (int i = 0; i < DBFnumberOfFields; i++) {

            String fieldName = DBFfieldNames.get(i);
            String fieldType = DBFfieldType.get(i);
            String fieldWidth = DBFfieldWidth.get(i);
            String fieldNull = DBFfieldNull.get(i);

            String s = fieldTypeConversiontoMySQL(fieldName, fieldType, fieldWidth, fieldNull);

            if ((i + 1) == DBFnumberOfFields) {
                sqlBody += s + " ";
                fieldsSQL += "`" + DBFfieldNames.get(i) + "`";
            } else {
                sqlBody += s + ", ";
                fieldsSQL += "`" + DBFfieldNames.get(i) + "`, ";
            }

        }

        finalSQL = sqlStart + sqlBody + sqlEnd;
//        System.out.println(finalSQL);

        try {
            stmt = conn.createStatement();
            ResultSet rsuse = stmt.executeQuery(useDatabase);
            int rs = stmt.executeUpdate(finalSQL);
            rs = stmt.executeUpdate("ALTER TABLE `" + MySQLtableName + "` ADD PRIMARY KEY (`" + MySQLtableName + "_id`);");
            rs = stmt.executeUpdate("ALTER TABLE `" + MySQLtableName + "` MODIFY `" + MySQLtableName + "_id` int(11) NOT NULL AUTO_INCREMENT;");
            rs = stmt.executeUpdate("COMMIT;");
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dumpToMySQLtable() throws FileNotFoundException {

        String useDatabase = "USE " + MySQLdatabase;
        String startSQL = "INSERT IGNORE INTO `" + MySQLtableName + "` (";
//        String fieldsSQL = "";
        String middleSQL = ") VALUES (";
        String dataSQL = "";
        String endSQL = ");";

        reader = new DBFReader(new FileInputStream(DBFfileNamePath));

        List<List<String>> DBFentireData = new ArrayList<List<String>>();
        List<String> DBFrowData = new ArrayList<String>();
        // Now, lets us start reading the rows and dump them to the MySQL table
        Object[] rowObjects;

        while ((rowObjects = reader.nextRecord()) != null) {
            DBFrowData.removeAll(DBFrowData);
            for (int i = 0; i < rowObjects.length; i++) {

                if (rowObjects[i] != null) {
//                    String cell = StringEscapeUtils.escapeSql(rowObjects[i].toString());
                    String cell = dataConversionToMySQL(rowObjects[i].toString(), i);
                    DBFrowData.add(rowObjects[i].toString());

                    if ((i + 1) == rowObjects.length) {
                        dataSQL += "'" + cell + "'";
                    } else {
                        dataSQL += "'" + cell + "', ";
                    }

//                    System.out.print(rowObjects[i].toString()+"\t");
                } else {
                    String cell = "NULL";
                    DBFrowData.add("NULL");

                    if ((i + 1) == rowObjects.length) {
                        dataSQL += "" + cell + "";
                    } else {
                        dataSQL += "" + cell + ", ";
                    }

//                    System.out.print("NULL\n");
                }
            }
            //Start inserting the data here
            String finalSQL = startSQL + fieldsSQL + middleSQL + dataSQL + endSQL;
            System.out.println(finalSQL);

            try {
                stmt = conn.createStatement();
                ResultSet rsuse = stmt.executeQuery(useDatabase);
                int rs = stmt.executeUpdate(finalSQL);
            } catch (SQLException ex) {
//                System.err.println(ex);
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }

//            System.out.println(finalSQL);
            dataSQL = "";
            finalSQL = "";

            DBFentireData.add(DBFrowData);
        }
    }

    public String fieldTypeConversiontoMySQL(String fieldName, String fieldType, String fieldWidth, String fieldNull) {

        fieldName = fieldName;
        fieldNull = "NULL";

        if (fieldType.equals("CHARACTER")) {
            fieldType = " VARCHAR";
            fieldWidth = " (" + fieldWidth + ") COLLATE utf8_unicode_ci ";
        } else if (fieldType.equals("NUMERIC")) {
            fieldType = " DOUBLE";
            fieldWidth = " ";
        } else if (fieldType.equals("DATE")) {
            fieldType = " DATE";
            fieldWidth = " ";
        }

//        System.out.println(fieldName+fieldType+fieldWidth+fieldNull);
        return fieldName + fieldType + fieldWidth + fieldNull;
    }

    private String dataConversionToMySQL(String cell, int index) {
        if (DBFfieldType.get(index).equals("DATE")) {
            try {
                Date date = new SimpleDateFormat("E MMM dd hh:mm:ss z yyyy").parse(cell); //This formating is meant for converting FOXPRO date
//            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                cell = sdf.format(date);
            } catch (ParseException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            cell = StringEscapeUtils.escapeSql(cell);
        }

        return cell;
    }

    private String MySQLNull(String DBFfieldNull) {
        if (DBFfieldNull.equals("false")) {
            return "NOT NULL";
        } else {
            return "NULL";
        }
    }

    private void getDatabases() throws SQLException {
        conn = MySQLConnection.ConnectMySQL("test");

        Statement stmt = conn.createStatement();
        String sql = "SHOW DATABASES";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
//            System.out.println("Database name :" + rs.getString("Database"));
            cbo_dbname.addItem(rs.getString("Database"));
        }
    }

    public void createDBF() throws FileNotFoundException {
        int rowCount = table_DBFStructure.getRowCount();
        DBFField[] fields = new DBFField[rowCount];

        for (int i = 0; i < rowCount; i++) {
            fields[i] = new DBFField();
            fields[i].setName((String) table_DBFStructure.getValueAt(i, 1));
            fields[i].setType(DBFDataType.CHARACTER);
            fields[i].setLength(Integer.parseInt(table_DBFStructure.getValueAt(i, 3).toString()));
        }

        DBFWriter writer = new DBFWriter(new FileOutputStream("onlyFileName.dbf"));
        writer.setFields(fields);

        // now populate DBFWriter
//        Object rowData[] = new Object[3];
//        rowData[0] = "1000";
//        rowData[1] = "John";
//        rowData[2] = new Double(5000.00);
//
//        writer.addRecord(rowData);
//
//        rowData = new Object[3];
//        rowData[0] = "1001";
//        rowData[1] = "Lalit";
//        rowData[2] = new Double(3400.00);
//
//        writer.addRecord(rowData);
//
//        rowData = new Object[3];
//        rowData[0] = "1002";
//        rowData[1] = "Rohit";
//        rowData[2] = new Double(7350.00);
//
//        writer.addRecord(rowData);
        // write to file
        writer.close();
    }

    private String escapeStringForMySQL(String s) {
        return s.replaceAll("\\", "\\\\")
                .replaceAll("\b", "\\b")
                .replaceAll("\n", "\\n")
                .replaceAll("\r", "\\r")
                .replaceAll("\t", "\\t")
                .replaceAll("\\x1A", "\\Z")
                .replaceAll("\\x00", "\\0")
                .replaceAll("'", "\\'")
                .replaceAll("\"", "\\\"");
    }

    private String escapeWildcardsForMySQL(String s) {
        return escapeStringForMySQL(s)
                .replaceAll("%", "\\%")
                .replaceAll("_", "\\_");
    }

    private String replaceAllSymbols(String s) {
//        return s.replaceAll("[\\-\\+\\.\\^:,]","");
        return s.replaceAll("[^\\w\\s]", "")
                .replaceAll(" ", "");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_custombasedir = new javax.swing.JTextField();
        btn_browse = new javax.swing.JButton();
        btn_load = new javax.swing.JButton();
        pane_main = new javax.swing.JDesktopPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_DBFStructure = new org.jdesktop.swingx.JXTable();
        cbo_dbname = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbo_tablename = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_newtablename = new javax.swing.JTextField();
        btn_createstruc = new javax.swing.JButton();
        btn_dumpdata = new javax.swing.JButton();
        btn_connectdb = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lbl_filedetails = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        menu_main = new javax.swing.JMenuBar();
        menuItem_file = new javax.swing.JMenu();
        menuItem_compare = new javax.swing.JMenu();
        menuitem_dbsettings = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setText("Choose File");

        btn_browse.setText("Browse");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });

        btn_load.setText("Load");
        btn_load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loadActionPerformed(evt);
            }
        });

        pane_main.setBackground(new java.awt.Color(255, 255, 255));

        table_DBFStructure.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(table_DBFStructure);

        pane_main.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout pane_mainLayout = new javax.swing.GroupLayout(pane_main);
        pane_main.setLayout(pane_mainLayout);
        pane_mainLayout.setHorizontalGroup(
            pane_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        pane_mainLayout.setVerticalGroup(
            pane_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );

        cbo_dbname.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "test" }));

        jLabel2.setText("Select Database Name");

        cbo_tablename.setEnabled(false);

        jLabel3.setText("Select Tables");

        jLabel4.setText("New Table Name");

        btn_createstruc.setText("Create Structure");
        btn_createstruc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createstrucActionPerformed(evt);
            }
        });

        btn_dumpdata.setText("Dump Data");
        btn_dumpdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dumpdataActionPerformed(evt);
            }
        });

        btn_connectdb.setText("Connect Database");
        btn_connectdb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_connectdbActionPerformed(evt);
            }
        });

        jLabel5.setText("Details");

        lbl_filedetails.setText("File Name, Fields count, No. of Records");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Developed by Nawaz Sarwar");

        menuItem_file.setText("File");
        menu_main.add(menuItem_file);

        menuItem_compare.setText("Compare DBF");
        menuItem_compare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuItem_compareMouseClicked(evt);
            }
        });
        menuItem_compare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_compareActionPerformed(evt);
            }
        });
        menu_main.add(menuItem_compare);

        menuitem_dbsettings.setText("MySQL Database Settings");
        menuitem_dbsettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuitem_dbsettingsMouseClicked(evt);
            }
        });
        menu_main.add(menuitem_dbsettings);

        setJMenuBar(menu_main);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pane_main)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_custombasedir, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_browse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_load)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbo_dbname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbo_tablename, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_connectdb))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_newtablename, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_createstruc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_dumpdata))
                            .addComponent(lbl_filedetails))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_custombasedir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_browse)
                    .addComponent(btn_load)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbo_dbname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbo_tablename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_connectdb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_newtablename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_createstruc)
                    .addComponent(btn_dumpdata))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_filedetails))
                .addGap(18, 18, 18)
                .addComponent(pane_main))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed
        browseFile();
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btn_loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loadActionPerformed
        TableData tabledata = new TableData(DBFfileNamePath);
        pane_main.add(tabledata);
        tabledata.setVisible(true);

        Dimension desktopSize = pane_main.getSize();
        Dimension jInternalFrameSize = tabledata.getSize();
//        tabledata.setLocation((desktopSize.width - jInternalFrameSize.width)/2, (desktopSize.height- jInternalFrameSize.height)/2);
        tabledata.setLocation(((desktopSize.width - jInternalFrameSize.width) / 2), ((desktopSize.height - jInternalFrameSize.height) / 2));
    }//GEN-LAST:event_btn_loadActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }//GEN-LAST:event_formWindowOpened

    private void menuItem_compareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_compareActionPerformed
        DBFCompare dbfCompare = new DBFCompare();
        pane_main.add(dbfCompare);
        dbfCompare.setVisible(true);
    }//GEN-LAST:event_menuItem_compareActionPerformed

    private void menuItem_compareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItem_compareMouseClicked
        DBFCompare dbfCompare = new DBFCompare();
        pane_main.add(dbfCompare);
        dbfCompare.setVisible(true);

        Dimension desktopSize = pane_main.getSize();
        Dimension jInternalFrameSize = dbfCompare.getSize();
        dbfCompare.setLocation(((desktopSize.width - jInternalFrameSize.width) / 2), ((desktopSize.height - jInternalFrameSize.height) / 2));
    }//GEN-LAST:event_menuItem_compareMouseClicked

    private void menuitem_dbsettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuitem_dbsettingsMouseClicked
        DBConnectionSettings dbc = new DBConnectionSettings();
        pane_main.add(dbc);
        dbc.setVisible(true);

        Dimension desktopSize = pane_main.getSize();
        Dimension jInternalFrameSize = dbc.getSize();
        dbc.setLocation(((desktopSize.width - jInternalFrameSize.width) / 2), ((desktopSize.height - jInternalFrameSize.height) / 2));
    }//GEN-LAST:event_menuitem_dbsettingsMouseClicked

    private void btn_connectdbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_connectdbActionPerformed
        try {
            getDatabases();
            fetchMySQLtables();
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_connectdbActionPerformed

    private void btn_createstrucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createstrucActionPerformed

        createMySQLtable();
        try {
            dumpToMySQLtable();
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_createstrucActionPerformed

    private void btn_dumpdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dumpdataActionPerformed
        try {
            dumpToMySQLtable();
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(rootPane, "Error : " + ex);
        }

//        try {
//            createDBF();
//            dumpToMySQLtable();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btn_dumpdataActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_connectdb;
    private javax.swing.JButton btn_createstruc;
    private javax.swing.JButton btn_dumpdata;
    private javax.swing.JButton btn_load;
    private javax.swing.JComboBox<String> cbo_dbname;
    private javax.swing.JComboBox<String> cbo_tablename;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_filedetails;
    private javax.swing.JMenu menuItem_compare;
    private javax.swing.JMenu menuItem_file;
    private javax.swing.JMenuBar menu_main;
    private javax.swing.JMenu menuitem_dbsettings;
    private javax.swing.JDesktopPane pane_main;
    private org.jdesktop.swingx.JXTable table_DBFStructure;
    private javax.swing.JTextField txt_custombasedir;
    private javax.swing.JTextField txt_newtablename;
    // End of variables declaration//GEN-END:variables
}
