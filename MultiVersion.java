import java.awt.EventQueue;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

import java.io.*;
import java.io.IOException;
import java.lang.Math;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.List;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MultiVersion {

    private ButtonGroup bG;
    public LinkedList<Operation> OperList;
    public LinkedList<Version> listData;
    public LinkedList<Transaction> TransList;
    public LinkedList<Transaction> abortTrans;
    public LinkedList<Operation> Serial;
    private JFrame frmMultiVersionBasedProtocol;
    private JTextField textField;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MultiVersion window = new MultiVersion();
                    window.frmMultiVersionBasedProtocol.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public MultiVersion() {
        initialize();
    }


    private void initialize() {
        frmMultiVersionBasedProtocol = new JFrame();
        frmMultiVersionBasedProtocol.setTitle("Timestamp Based Protocol");
        frmMultiVersionBasedProtocol.setBounds(95, 95, 390, 394);
        frmMultiVersionBasedProtocol.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frmMultiVersionBasedProtocol.setBackground(java.awt.Color.BLUE);
        

        

        JPanel panel = new JPanel();
        frmMultiVersionBasedProtocol.getContentPane().add(panel, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane();

        JPanel panel_1 = new JPanel();
       // panel_1.setBorder(new TitledBorder(null, "Techniques", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        textField = new JTextField();
        bG = new ButtonGroup();
        JPanel panel_3 = new JPanel();

        Font font = new Font("Calibri", Font.ITALIC, 14);
        Border lineBorder = new LineBorder(Color.WHITE, 0);
        TitledBorder titledBorder = new TitledBorder(lineBorder,"Serial Schedule:", TitledBorder.CENTER,
        TitledBorder.TOP, font, Color.BLACK);
        panel_3.setBorder(titledBorder);
        
        panel_3.setPreferredSize(new Dimension(90, 100));
        panel_3.setMaximumSize(new Dimension(100,200));
        
        
        JTextArea textArea = new JTextArea();
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textArea.setText("");
            }
        });

        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        
        gl_panel_3.setHorizontalGroup(
                gl_panel_3.createParallelGroup(Alignment.CENTER)
                .addGroup(gl_panel_3.createSequentialGroup()
                        .addGap(21)
                        .addComponent(textArea, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(21, Short.MAX_VALUE))
        );
        gl_panel_3.setVerticalGroup(
                gl_panel_3.createParallelGroup(Alignment.CENTER)
                .addGroup(gl_panel_3.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textArea, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_3.setLayout(gl_panel_3);

        JLabel lblEnterYourText = new JLabel("Enter Your Text File Path");
        lblEnterYourText.setFont(new Font("Calibri", Font.PLAIN, 14));


        textField.setColumns(9);

        JButton btnBasic = new JButton("Multi-version Timestamp Ordering");
        btnBasic.setFont(new Font("Calibri", Font.PLAIN, 14));
        btnBasic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");

                try {
                    OperList = new LinkedList<Operation>();
                    listData = new LinkedList<Version>();
                    TransList = new LinkedList<Transaction>();
                    abortTrans = new LinkedList<Transaction>();
                    Serial = new LinkedList<Operation>();
                    String Fname = textField.getText() + ".txt";
                    File file = new File(Fname);
                    BufferedReader bfr = new BufferedReader(new FileReader(Fname));
                    ArrayList<String> tnsNum = new ArrayList<String>();

                    char data = ' ';
                    String line = "";
                    String Opname = "";
                    int numLine = 1;
                    String Opfull = "";

                    while ((line = bfr.readLine()) != null) {

                        Opfull = line;
                        int underscore = line.indexOf('_');
                        String transName = line.substring(underscore + 1);
                        Opname = line.substring(0, underscore);
                        int Tnum = Integer.parseInt(transName);
                        String op = line.substring(0, underscore);// read
                        int bracket = line.indexOf('(');
                      
                      
                       if (bracket != -1) { // make sure the data does not exits before

                            data = op.charAt(bracket + 1); // get the Data name
                            op = op.substring(0, bracket); // read write
                        }
                        
                        Version d = new Version(data);
                        if (data != ' ' && findDlist(listData, data) == null) {
                            listData.findFirst();
                            listData.insert(d);
                        }

                        switch (op) {
                            case "begin":
                                OperList.insert(new Operation(Opname, Opfull, numLine, ' ', Tnum));
                                TransList.insert(new Transaction(Tnum, numLine));
                                break;
                            case "read":
                                readMVTO(data, Tnum, Opname, numLine, Opfull);
                                break;

                            case "write":
                                writeMVTO(data, Tnum, Opname, numLine, Opfull);
                                break;
                            case "commit":
                                OperList.insert(new Operation(Opname, Opfull, numLine, ' ', Tnum));
                                break;
                        }// end switch
                        numLine++;
                    } // while	
                    if (abortTrans.empty()) {

                        Serial = sortSerial(TransList, OperList);
                        textArea.setText("");
                        Serial.findFirst();
                        for (int i = 0; i < Serial.size(); i++) {
                            String name = Serial.retrieve().fname;
                            int underscore = name.indexOf('_');
                            String transName = name.substring(underscore + 1);
                            Serial.findNext();
                            tnsNum.add(transName);
                        }
                        
                        ArrayList<String> tnsNumDistinict = removeDuplicates(tnsNum);
                        for (int j = 0; j < tnsNumDistinict.size(); j++) {
                            textArea.append("T" + tnsNumDistinict.get(j) + " , ");
                        }
                    } else {
                        String msg = "";
                        OperList.findFirst();
                        for (int i = 0; i < OperList.size(); i++) {
                            if (OperList.retrieve().reject != 0) {
                               msg = msg + "T" + OperList.retrieve().TransList + " is Aborted and " + OperList.retrieve().OperName + " is Rejected at time " + OperList.retrieve().timestamp + "\n";
                            }
                            OperList.findNext();
                        }
                        JOptionPane.showMessageDialog(null, msg, "WARNING - Transaction is aborted", JOptionPane.WARNING_MESSAGE);
                    } 

                } catch (IOException ee) {
                    System.out.println("File does Not Exist");
                    JOptionPane.showMessageDialog(null, ee, "File does Not Exist", JOptionPane.ERROR_MESSAGE);
                    ee.printStackTrace();
                } // catch
                catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, ee, "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
                        .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                .addGroup(gl_panel_1.createSequentialGroup()
                                        .addGap(27)
                                        .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                                .addComponent(btnBasic, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblEnterYourText, Alignment.LEADING)
                                                .addComponent(textField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE))))
                        .addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblEnterYourText)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                        .addGap(35)
                        .addComponent(btnBasic, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                        .addGap(18)
                        .addContainerGap(34, Short.MAX_VALUE))
        );
        panel_1.setLayout(gl_panel_1);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE))
                        .addGap(23))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                        .addGap(31)
                        .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
                        .addGap(24))
        );
        panel.setLayout(gl_panel);
    }

    public static Version findDlist(LinkedList<Version> listData, char data) {

        listData.findFirst();
        for (int i = 0; i < listData.size(); i++) {
            if (listData.retrieve().Symbol == data) {
                return listData.retrieve();
            }
            listData.findNext();
        } // for
        return null;
    }// end findDlist

    public static Transaction FindTlist(LinkedList<Transaction> TransList, int Tnum) {
        TransList.findFirst();
        for (int i = 0; i < TransList.size(); i++) {
            if (TransList.retrieve().TransName == Tnum) {
                return TransList.retrieve();
            }
            TransList.findNext();
        } // for
        return null;
    }// end FindTlist

    public static LinkedList<Operation> sortSerial(LinkedList<Transaction> TransList, LinkedList<Operation> OperList) {

        LinkedList<Operation> Serial = new LinkedList<Operation>();
        TransList.findFirst();
        Transaction[] time = new Transaction[TransList.size()];
        for (int i = 0; i < TransList.size(); i++) {
            time[i] = TransList.retrieve();
            TransList.findNext();
        }//for

        int n = time.length;
        Transaction temp = null;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (time[j - 1].timeStamp > time[j].timeStamp) {
                    temp = time[j - 1]; 
                    time[j - 1] = time[j];
                    time[j] = temp;
                }//if                 
            }
        } //for 1

        for (int i = 0; i < n; i++) {
            OperList.findFirst();
            for (int j = 0; j < OperList.size(); j++) {
                if (OperList.retrieve().TransList == time[i].TransName) {
                    Serial.insert(OperList.retrieve());
                }
                OperList.findNext();
            }//for  
        }//for
        return Serial;
    }//End 

    public void readMVTO(char data, int Tnum, String Opname, int numLine, String Opfull) {

        Transaction indexT = FindTlist(TransList, Tnum);
        OperList.insert(new Operation(Opname, Opfull, numLine, data, Tnum));
        if (indexT != null) {
            int lastVersion = findlast(OperList, data, Opname, TransList);
            if (lastVersion != -1) {  
                Transaction preT = FindTlist(TransList, lastVersion);
                if (TransList.exists(preT)) {
                    listData.retrieve().setRead(Math.max(preT.timeStamp, listData.retrieve().getRead()));
                }
            }

        }
    } // End of readMVTO

    public void writeMVTO(char data, int Tnum, String Opname, int numLine, String Opfull) {

        Transaction indexT = FindTlist(TransList, Tnum);
        Version indexD = findDlist(listData, data);
        OperList.insert(new Operation(Opname, Opfull, numLine, data, Tnum));
        if (indexT != null) {
            if (indexD.getRead()> indexT.timeStamp) {
                OperList.retrieve().reject = numLine;
                TransList.exists(indexT);
                TransList.retrieve().abortTrans = numLine;
                abortTrans.insert(TransList.retrieve());
                reset(listData, TransList, indexT, data);
                TransList.remove();
            } else { 
                int lastVersion = findlast(OperList, data, Opname, TransList);
                if (lastVersion != -1) {
                    Transaction preT = FindTlist(TransList, lastVersion);
                    if (TransList.exists(preT)) {
                        listData.retrieve().setWrite(preT.timeStamp);

                    }
                }

            }
        }
    }
    // End of WriteMVTO

    public int findlast(LinkedList<Operation> OperList, char data, String op, LinkedList<Transaction> TransList) { // Find last number of transaction that was modified 
        Operation c = OperList.retrieve();
        Operation temp = null;
        OperList.findFirst();
        for (int i = 0; i < OperList.size(); i++) {
            if (OperList.retrieve().dataitem == data && OperList.retrieve().OperName.equals(op) && OperList.retrieve().reject == 0) {
                temp = OperList.retrieve();
            }
            OperList.findNext();
        } // for
        OperList.exists(c);
        if (temp == null) {
            return -1;
        } else {
            return temp.TransList;
        }
    } // end find

    public void reset(LinkedList<Version> listData, LinkedList<Transaction> TransList, Transaction tr, char data) {
        TransList.exists(tr);
        listData.findFirst();
        for (int i = 0; i < listData.size(); i++) {
            listData.retrieve().resetRTS(TransList.retrieve().timeStamp);
            listData.retrieve().resetWTS(TransList.retrieve().timeStamp);
            listData.findNext();
        }
    }//End  res

    public static <TransList> ArrayList<TransList> removeDuplicates(ArrayList<TransList> list) {

        // Create a new ArrayList
        ArrayList<TransList> newList = new ArrayList<TransList>();

        // Traverse through the first list
        for (TransList element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}// End class
