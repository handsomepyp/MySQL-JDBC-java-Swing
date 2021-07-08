import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Student {
    private JPanel Student;
    private JTextField AllText;
    private JButton QueryButton;
    private JButton superQueryButton;
    private JButton ConnectMysqlButton;
    private JTextField QueryResult;
    private JButton AddButton;
    private JTextField addSno;
    private JTextField addSname;
    private JTextField addSdept;
    private JTextField addStel;
    private JTextField addSage;
    private JTextField changeSno;
    private JTextField changeResult;
    private JButton ensureButton;
    private JTextField changeNewSno;
    private JTextField changeSname;
    private JTextField changeSdept;
    private JTextField changeStel;
    private JTextField changeSage;
    private JButton changeButton;
    private JButton deleteButton;
    private JButton closeConnectButton;
    private JComboBox studentQueryBox;
    private JTable table1;
    private JComboBox addSsexBox;
    private JComboBox changeSsexBox;
    private JButton changeClearButton;
    private JButton superFunctionButton;

    private String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private String user = "root";
    private String passwd = "100318";
    private String jdbcName="com.mysql.jdbc.Driver"; // 驱动名称
    private static Connection conn;

    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(url, user, passwd);
        return con;
    }
    public Student() {
        /*ConnectMysqlButton.addActionListener(new ActionListener() { // 连接数据库按钮
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Student, "数据库连接成功");
                try {
                    conn = getCon();
                } catch (Exception ee){
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(Student, "数据库连接失败");
                }
            }
        });*/

        QueryButton.addActionListener(new ActionListener() { // 通过学号查询个人信息
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DefaultTableModel model=new DefaultTableModel();
                    table1.setModel(model);
                    model.addColumn("学号");
                    model.addColumn("姓名");
                    model.addColumn("性别");
                    model.addColumn("年龄");
                    model.addColumn("专业");
                    model.addColumn("电话");
                    int type = studentQueryBox.getSelectedIndex();
                    if(type == 0) { // 按照学号查询
                        String Sno = AllText.getText();
                        Statement statement = conn.createStatement();
                        String sql = "select * from project.student where Sno='" + Sno + "'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()) {
                            model.addRow(new Object[]{rs.getString("Sno"),
                                    rs.getString("Sname"),
                                    rs.getString("Ssex"),
                                    rs.getString("Sage"),
                                    rs.getString("Sdept"),
                                    rs.getString("Stel")});
                        }
                    } else if(type == 1){ // 按照姓名
                        String Sname = AllText.getText();
                        Statement statement = conn.createStatement();
                        String sql = "select * from project.student where Sname='" + Sname + "'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()) {
                            //Object res = new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")};
                            model.addRow(new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")});
                        }
                    } else if(type == 2){ // 性别
                        String Ssex = AllText.getText();
                        Statement statement = conn.createStatement();
                        String sql = "select * from project.student where Ssex='" + Ssex + "'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()) {
                            //Object res = new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")};
                            model.addRow(new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")});
                        }
                    } else if(type == 3){ // 年龄
                        String Sage = AllText.getText();
                        Statement statement = conn.createStatement();
                        int age = Integer.parseInt(Sage);
                        String sql = "select * from project.student where Sage='" + Sage + "'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()) {
                      model.addRow(new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")});
                        }
                    } else if                   //Object res = new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")};
                    (type == 4){ // 专业
                        String Sdept = AllText.getText();
                        Statement statement = conn.createStatement();
                        String sql = "select * from project.student where Sdept='" + Sdept + "'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()) {
                            //Object res = new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")};
                            model.addRow(new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")});
                        }
                    } else if(type == 5){ // 电话
                        String Stel = AllText.getText();
                        Statement statement = conn.createStatement();
                        String sql = "select * from project.student where Stel='" + Stel + "'";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()) {
                            //Object res = new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")};
                            model.addRow(new Object[]{rs.getString("Sno"), rs.getString("Sname"), rs.getString("Ssex"), rs.getString("Sage"), rs.getString("Sdept"), rs.getString("Stel")});
                        }
                    }
                }
                catch(Exception ee) {
                    System.out.print("获取信息错误");
                    ee.printStackTrace();
                }
            }
        });
        AddButton.addActionListener(new ActionListener() { // 添加功能
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Sno = addSno.getText();
                    String Sname = addSname.getText();
                    String Sdept = addSdept.getText();
                    //String Ssex = addSsex.getText();
                    String SSsex = (String)addSsexBox.getSelectedItem();
                    String Stel = addStel.getText();
                    String Sage = addSage.getText();
                    int age = Integer.parseInt(Sage);
                    Statement statement = conn.createStatement();
                    String sql = "insert into project.student(Sno, Sname, Sdept, Ssex, Stel, Sage) value('" + Sno + "','" + Sname + "','" + Sdept + "','" + SSsex + "','" + Stel + "',"+ age + ")";
                    //System.out.print(sql);//prepareStatement
                    statement.executeUpdate(sql); // 添加完就更新
                    JOptionPane.showMessageDialog(Student, "添加成功");
                } catch(Exception ee){
                    JOptionPane.showMessageDialog(Student, "添加失败");
                    ee.printStackTrace();
                }
            }
        });

        ensureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Sno = changeSno.getText();
                    Statement statement = conn.createStatement();
                    String sql = "select * from project.student where Sno='" + Sno + "'";
                    //System.out.print(sql);
                    ResultSet rs = statement.executeQuery(sql);
                    String res = "";
                    while(rs.next()) {
                        res += "学号：" + rs.getString("Sno");
                        res += ",";
                        res += "姓名：" + rs.getString("Sname");
                        res += ",";
                        res += "性别：" + rs.getString("Ssex");
                        res += ",";
                        res += "年龄：" + rs.getString("Sage");
                        res += ",";
                        res += "专业：" + rs.getString("Sdept");
                        res += ",";
                        res += "电话：" + rs.getString("Stel");
                    }
                    changeResult.setText(res);
                }
                catch(Exception ee) {
                    System.out.print("获取信息错误");
                    ee.printStackTrace();
                }
            }
        });
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sno = changeSno.getText();
                String newSno = changeNewSno.getText();
                String Sname = changeSname.getText();
                //String Ssex = changeSsex.getText();
                String Ssex = (String)changeSsexBox.getSelectedItem();
                String age = changeSage.getText();
                String Sdept = changeSdept.getText();
                String Stel = changeStel.getText();
                try{
                    // 先查询，再修改，如果框框里为空，则这一项没必要修改
                    // 查询
                    String oldSno = Sno;
                    Statement quertstat = conn.createStatement();
                    String querysql = "select * from project.student where Sno='" + oldSno + "'";
                    ResultSet rs1 = quertstat.executeQuery(querysql);
                    //System.out.print(rs1);
                    String oldSname = "", oldSage = "", oldSdept = "", oldStel = "";
                    while(rs1.next()) {
                        oldSno = rs1.getString("Sno");
                        oldSname = rs1.getString("Sname");
                        oldSage = rs1.getString("Sage");
                        oldSdept = rs1.getString("Sdept");
                        oldStel = rs1.getString("Stel");
                    }
                    // 修改
                    Statement statement = conn.createStatement();
                    if(newSno.equals("")) newSno = oldSno;
                    if(Sname.equals("")) Sname = oldSname;
                    if(Sdept.equals("")) Sdept = oldSdept;
                    if(Stel.equals("")) Stel = oldStel;
                    if(age.equals("")) age = oldSage;
                    //Sage = Integer.parseInt(age);
                    int Sage = Integer.parseInt(age);
                    String sql = "update project.student set Sno = '" + newSno + "', Sname = '" + Sname + "', Sdept = '" + Sdept + "', Ssex = '" + Ssex + "', Stel = '" + Stel + "', Sage = "+ Sage + " where Sno = '" + Sno + "'";
                    statement.executeUpdate(sql); // 添加完就更新
                    JOptionPane.showMessageDialog(Student, "修改成功");
                } catch (Exception ee){
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(Student, "修改失败");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Sno = changeSno.getText();
                    Statement statement = conn.createStatement();
                    String sql = "delete from project.student where Sno='" + Sno + "'";
                    //System.out.print(sql);
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(Student, "删除成功");
                } catch(Exception ee){
                    System.out.print("获取信息错误");
                    ee.printStackTrace();
                }
            }
        });
        /*closeConnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(conn.isClosed())
                        JOptionPane.showMessageDialog(Student, "连接已关闭");
                    else{
                        conn.close();
                        JOptionPane.showMessageDialog(Student, "关闭成功");
                    }
                } catch (Exception ee){
                    JOptionPane.showMessageDialog(Student, "关闭失败");
                    ee.printStackTrace();
                }
            }
        });*/
        /*superQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperQuery sub = new SuperQuery();
                //System.out.print(conn);
                //sub.channel(conn);
                sub.main(conn);
                //SuperQuery.main();
            }
        });*/
        studentQueryBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //String name = studentQueryBox.getName();
            }
        });
        changeClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeSno.setText("");
                changeResult.setText("");
                changeNewSno.setText("");
                changeSname.setText("");
                changeStel.setText("");
                changeSdept.setText("");
                changeStel.setText("");
                changeSage.setText("");
            }
        });
        superFunctionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                superFunction superfunction = new superFunction();
                //System.out.print(conn);
                //sub.channel(conn);
                superfunction.main(conn);
            }
        });
    }

    public static void main(Connection con) { // 主界面
        JFrame frame = new JFrame("mainTable");
        frame.setContentPane(new Student().Student);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setSize(1200,1200);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        conn = con;
    }
}
