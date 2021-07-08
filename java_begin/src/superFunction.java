import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class superFunction {
    private JComboBox choiceBox;
    private JTable table3;
    private JPanel superFunc;
    private JButton queryStudentButton;
    private JTextField CnoCnameText;
    private JButton NotchoiceButton;
    private JTextField impoveCname;
    private JTextField impoveRate;
    private JButton improveGradeButton;
    private static Connection conn;

    public superFunction() {
        queryStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DefaultTableModel model=new DefaultTableModel();
                    table3.setModel(model);
                    model.addColumn("学号");
                    model.addColumn("姓名");
                    int type = choiceBox.getSelectedIndex();
                    if(type == 0){
                        String Cno = CnoCnameText.getText();
                        Statement statement = conn.createStatement();
                        String sql =
                                "SELECT \n" +
                                "    SC.Sno, Sname\n" +
                                "FROM\n" +
                                "    project.Student,\n" +
                                "    project.SC\n" +
                                "WHERE\n" +
                                "    SC.Cno = '" + Cno + "' AND SC.Sno = Student.Sno";
                        ResultSet rs = statement.executeQuery(sql);
                        //System.out.print(rs);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("SC.Sno"),rs.getString("Sname")});
                        }
                    }
                    else if(type == 1){
                        String Cname = CnoCnameText.getText();
                        Statement statement = conn.createStatement();
                        String sql =
                                "SELECT \n" +
                                "    SC.Sno, Sname\n" +
                                "FROM\n" +
                                "    project.Student,\n" +
                                "    project.SC,\n" +
                                "    project.Course\n"+
                                "WHERE\n" +
                                "    Course.Cname =  '" + Cname + "' AND Course.Cno = SC.Cno AND SC.Sno = Student.Sno";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("SC.Sno"),rs.getString("Sname")});
                        }
                    }
                    else if(type == 2){
                        Statement statement = conn.createStatement();
                        String sql = "select Sno,Sname\n" +
                                " from project.student\n" +
                                "where not exists(\n" +
                                "select *\n" +
                                "from project.course\n" +
                                "where not exists(\n" +
                                " select *\n" +
                                "from project.sc\n" +
                                "where sc.Sno = Student.Sno AND sc.Cno = course.Cno))";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("Sno"),
                                    rs.getString("Sname")});
                        }
                    }
            } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        NotchoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    DefaultTableModel model=new DefaultTableModel();
                    table3.setModel(model);
                    model.addColumn("学号");
                    model.addColumn("姓名");
                    int type = choiceBox.getSelectedIndex();
                    if(type == 0){
                        String Cno = CnoCnameText.getText();
                        Statement statement = conn.createStatement();
                        String sql =
                                "SELECT DISTINCT \n" +
                                    "Sno, Sname \n" +
                                "FROM \n" +
                                        "project.course,\n" +
                                        "project.student\n" +
                                "WHERE\n" +
                                        "NOT EXISTS( SELECT * \n" +
                                        "FROM \n" +
                                            "project.SC\n" +
                                        "WHERE\n" +
                                            "SC.Sno = Student.Sno AND SC.Cno = '" + Cno + "')";
                        //System.out.print(sql);
                        ResultSet rs = statement.executeQuery(sql);
                        System.out.print(rs);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("Sno"),rs.getString("Sname")});
                        }
                    }
                    else if(type == 1){
                        String Cname = CnoCnameText.getText();
                        Statement statement = conn.createStatement();
                        String sql =
                                "SELECT \n" +
                                        "    SC.Sno, Sname\n" +
                                        "FROM\n" +
                                        "    project.Student,\n" +
                                        "    project.SC,\n" +
                                        "    project.Course\n"+
                                        "WHERE\n" +
                                        "    Course.Cname =  '" + Cname + "' AND Course.Cno = SC.Cno AND SC.Sno = Student.Sno";
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("SC.Sno"),rs.getString("Sname")});
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        improveGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Cno = impoveCname.getText();
                    String Rate = impoveRate.getText();
                    double rate = Double.parseDouble(Rate);
                    Statement statement = conn.createStatement();
                    String sql = "UPDATE project.SC \n" +
                            "SET \n" +
                            "    Grade = Grade * " + rate + "\n" +
                            "WHERE\n" +
                            "    SC.Cno = '" + Cno + "' AND Sno >= '0'";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(superFunc, "成绩增加成功");
                }catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            }
        });
    }

    public static void main(Connection con) {
        JFrame frame = new JFrame("superFunction");
        frame.setContentPane(new superFunction().superFunc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        conn = con;
    }
}
