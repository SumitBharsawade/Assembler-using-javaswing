
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.*;
import java.io.IOException;
import javax.swing.border.TitledBorder;

public class Assembler extends JFrame {

    private JPanel contentPane;
    JButton sytbtn;
    JButton intbtn;
    JButton littbtn;
    JButton assfilebtn;
    static  Assembler frame;
    private JTextField textField;
    /**
     * Launch the application.
     */

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new Assembler();
                    frame.setVisible(true);
                    frame.setSize(600, 500);
                    frame.setTitle("Pass2 Assambler");
                    frame.getContentPane().setBackground(new Color(229, 255, 204));//new java.awt.Color(229, 255, 204));
                            frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void setbtnfield(JButton btn) {
        btn.setOpaque(true);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBackground(new java.awt.Color(255, 204, 153));
    }

    public Assembler() {
        ImageIcon icon=new ImageIcon("icon.png");
        setIconImage(icon.getImage().getScaledInstance(20,20,java.awt.Image.SCALE_SMOOTH));
        setBackground(new Color(152, 251, 152));
        setForeground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel lblNewLabel_1 = new JLabel("Machine Code Generator");
        lblNewLabel_1.setForeground(new Color(153, 0, 0));
        lblNewLabel_1.setBackground(Color.ORANGE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnMachineCode = new JButton("Generate Machine Code");
        btnMachineCode.setFont(UIManager.getFont("Button.font"));
        btnMachineCode.setFont(new Font("Tahoma", Font.BOLD, 12));

        setbtnfield(btnMachineCode);
        btnMachineCode.setBackground(new java.awt.Color(189, 183, 107));

        btnMachineCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    Pass1 obj1=new Pass1(textField.getText());
                    int a=obj1.compute();
                    if(a!=-1)
                    {
                        showWorninng("Error Occured at line "+a);
                    }
                    try {
                        Pass2 obj = new Pass2("symbol.txt", "intermediate.txt", "literal.txt");
                        obj.compute();
                    }catch(IOException i)
                    {
                        showWorninng("File opening error\n\n"+i);
                    }
                if (Desktop.isDesktopSupported()) {
                    try {
                        //File myFile = new File(System.getProperty("user.dir")+"\\Pass2.txt");
                        //Desktop.getDesktop().open(myFile);
                        ProcessBuilder pb=new ProcessBuilder("write",System.getProperty("user.dir")+"\\Pass2.txt");
                        pb.start();
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 220));
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " Pass1", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 99, 71)));

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(245, 245, 220));
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Pass2", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 99, 71)));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, 557, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(203)
                                                .addComponent(btnMachineCode, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(30)
                                .addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(35)
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
                                .addGap(32)
                                .addComponent(btnMachineCode, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addGap(21))
        );

        JLabel lblNewLabel = new JLabel("Intermediate File");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));


        intbtn = new JButton("Browse");
        intbtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        setbtnfield(intbtn);
        intbtn.addActionListener(new ClassBrowse());

        JLabel label = new JLabel("Symbol Table File");
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));

        sytbtn = new JButton("Browse");
        sytbtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        setbtnfield(sytbtn);
        sytbtn.addActionListener(new ClassBrowse());

        JLabel label_1 = new JLabel("Literal Table File");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));


        littbtn = new JButton("Browse");
        littbtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        setbtnfield(littbtn);
        littbtn.addActionListener(new ClassBrowse());
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap(128, Short.MAX_VALUE)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(label)
                                        .addComponent(lblNewLabel)
                                        .addComponent(label_1))
                                .addGap(48)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(sytbtn)
                                        .addComponent(intbtn)
                                        .addComponent(littbtn))
                                .addContainerGap(187, Short.MAX_VALUE))
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(intbtn)
                                        .addComponent(lblNewLabel))
                                .addGap(29)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(sytbtn)
                                        .addComponent(label))
                                .addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(littbtn)
                                        .addComponent(label_1))
                                .addContainerGap())
        );
        panel_1.setLayout(gl_panel_1);

        textField = new JTextField();
        textField.setColumns(10);

         assfilebtn = new JButton("Browse");
        assfilebtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        setbtnfield(assfilebtn);
        assfilebtn.addActionListener(new ClassBrowse());
        JLabel lblAssembleyFile = new JLabel("Assembly File");
        lblAssembleyFile.setBackground(new Color(255, 255, 240));
        lblAssembleyFile.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(26)
                                .addComponent(lblAssembleyFile, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                .addComponent(assfilebtn)
                                .addGap(38))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                                .addComponent(assfilebtn)
                                .addComponent(lblAssembleyFile, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
        );
        panel.setLayout(gl_panel);
        contentPane.setLayout(gl_contentPane);
    }

    class ClassBrowse implements ActionListener {
        private JFileChooser fileChooser = new JFileChooser();
        private String fileName;

        ClassBrowse() {
            String userhome = System.getProperty("user.home");
            fileChooser.setCurrentDirectory(new File(userhome + "\\Desktop"));
        }

        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            File file=null;
                try {
                    //fileName = file.toString();

                    if(src==assfilebtn)
                    {
                        int returnVal = fileChooser.showSaveDialog((Component) e.getSource());
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                             file = fileChooser.getSelectedFile();
                            fileName = file.toString();
                            textField.setText(fileName);
                        }
                    }
                    else if (src == sytbtn) {
                       //sytextField.setText(fileName);
                        //File myFile = new File(System.getProperty("user.dir")+"\\symbol.txt");
                        //Desktop.getDesktop().open(myFile);
                        ProcessBuilder pb=new ProcessBuilder("write",System.getProperty("user.dir")+"\\symbol.txt");
                        pb.start();
                    }
                   else if (src == intbtn) {
                        //File myFile = new File(System.getProperty("user.dir")+"\\intermediate.txt");
                        //Desktop.getDesktop().open(myFile);
                        ProcessBuilder pb=new ProcessBuilder("write",System.getProperty("user.dir")+"\\intermediate.txt");
                        pb.start();
                   }
                    else if(src == littbtn) {
                        //File myFile = new File(System.getProperty("user.dir")+"\\literal.txt");
                        //Desktop.getDesktop().open(myFile);
                        ProcessBuilder pb=new ProcessBuilder("write",System.getProperty("user.dir")+"\\literal.txt");
                        pb.start();
                    }
                    //    littextField.setText(fileName);
                } catch (Exception x) {
                    showWorninng("problem accessing file" + file.getAbsolutePath());
                }
            } //else {
                //showWorninng("File access cancelled by user.");
            //}
        }

    void showWorninng(String msg)
    {
        JOptionPane.showMessageDialog(frame,msg,"Alert",JOptionPane.WARNING_MESSAGE);
//"Please insert correct Files"
    }
}