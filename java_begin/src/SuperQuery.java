import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SuperQuery {
    private static Connection conn;
    private JPanel course;
    private JButton button1;
    private JTextField querySno;
    private JTextField querySname;
    private JTextField querySdept;
    private JTextField queryCname;
    private JTextField queryGradeStart;
    private JTextField queryGradeEnd;
    private JButton QueryButton;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField courseCno;
    private JTextField courseCname;
    private JTextField courseCtype;
    private JButton courseQueryButton;
    private JTextField courseQueryResult;
    private JTextField changeGradeSno;
    private JTextField textField16;
    private JTextField changeGradeCno;
    private JTextField changeGradeGrade;
    private JButton changeGradeButton;
    private JTextField textField19;
    private JTextField changeCourseCname;
    private JTextField changeCourseCtype;
    private JTextField changeCourseCcredit;
    private JButton changeCourseBotton;
    private JTextField changeCourseCno;
    private JButton changeEnsureButton;
    private JTable table2;
    private JTextField changeGradeResult;
    private JButton changeGradeEnButton;
    //private Connection conn;

    public SuperQuery() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(course, "clicked me");
            }
        });
        QueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                table2.setModel(model);
                model.addColumn("学号");
                model.addColumn("姓名");
                model.addColumn("性别");
                model.addColumn("课程");
                model.addColumn("成绩");
                String Sno = querySno.getText();
                //String Sname = querySname.getText();
                String Cname = queryCname.getText();

                if(Sno.equals("") && !Cname.equals("")){ // 根据课程找表
                    try{
                        Statement statement = conn.createStatement();
                        String sql = "Select \n" +
                                "SC.Sno, Student.Sname, Student.Ssex, Course.Cname, SC.Grade\n" +
                                "FROM\n" +
                                "project.SC, project.Course, project.Student\n" +
                                "WHERE\n" +
                                "SC.Sno = Student.Sno AND SC.Cno = Course.Cno " +
                                " AND Course.Cname = '" + Cname + "'";
                        //System.out.print(sql);
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("SC.Sno"), rs.getString("Student.Sname"), rs.getString("Student.Ssex"), rs.getString("Course.Cname"), rs.getString("SC.Grade")});
                        }
                    }catch (Exception ee){
                        ee.printStackTrace();
                    }
                } else if(!Sno.equals("") && Cname.equals("")){
                    try{
                        Statement statement = conn.createStatement();
                        String sql = "Select \n" +
                                "SC.Sno, Student.Sname, Student.Ssex, Course.Cname, SC.Grade\n" +
                                "FROM\n" +
                                "project.SC, project.Course, project.Student\n" +
                                "WHERE\n" +
                                "SC.Sno = Student.Sno AND SC.Cno = Course.Cno AND SC.Sno = " +
                                "'" + Sno + "'";
                        System.out.print(sql);
                        ResultSet rs = statement.executeQuery(sql);
                        while(rs.next()){
                            model.addRow(new Object[]{rs.getString("SC.Sno"), rs.getString("Student.Sname"), rs.getString("Student.Ssex"), rs.getString("Course.Cname"), rs.getString("SC.Grade")});
                        }
                    }catch (Exception ee){
                        ee.printStackTrace();
                    }
                }
                else try{
                    Statement statement = conn.createStatement();
                    String sql = "Select \n" +
                            "SC.Sno, Student.Sname, Student.Ssex, Course.Cname, SC.Grade\n" +
                            "FROM\n" +
                            "project.SC, project.Course, project.Student\n" +
                            "WHERE\n" +
                            "SC.Sno = Student.Sno AND SC.Cno = Course.Cno AND SC.Sno = " +
                            "'" + Sno + "' AND Course.Cname = '" + Cname + "'";
                    System.out.print(sql);
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{rs.getString("SC.Sno"), rs.getString("Student.Sname"), rs.getString("Student.Ssex"), rs.getString("Course.Cname"), rs.getString("SC.Grade")});
                    }
                } catch(Exception ee) {
                    ee.printStackTrace();
                }
            }
        });
        courseQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = courseCno.getText();
                String Cname = courseCname.getText();
                String Ctype = courseCtype.getText();
                try {
                    Statement statement = conn.createStatement();
                    String sql = "Select * from project.course where Cno = '" + Cno + "'";
                    ResultSet rs = statement.executeQuery(sql);
                    String res = "";
                    while(rs.next()){
                        res += "课程号：";
                        res += rs.getString("Cno");
                        res += " ,";
                        res += "课程名：";
                        res += rs.getString("Cname");
                        res += " ,";
                        res += "课程类型：";
                        res += rs.getString("Ctype");
                        res += " ,";
                        res += "课程学分：";
                        res += rs.getString("Ccredit");
                    }
                    courseQueryResult.setText(res);
                } catch (Exception ee){
                    JOptionPane.showMessageDialog(course, "查询失败");
                    ee.printStackTrace();
                }
            }
        });
        changeEnsureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = changeCourseCno.getText();

            }
        });
        changeCourseBotton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = changeCourseCno.getText();
                String Cname = changeCourseCname.getText();
                String Ctype = changeCourseCtype.getText();
                String Ccredit = changeCourseCcredit.getText();
                int credit = Integer.parseInt(Ccredit);
                try{
                    Statement statement = conn.createStatement();
                    String sql = "update project.course set Cname = '" + Cname + "', Ctype = '" + Ctype + "', Ccredit = " + credit + " where Cno = '" + Cno + "'";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(course, "修改成功");
                } catch (Exception ee){
                    JOptionPane.showMessageDialog(course, "修改失败");
                    ee.printStackTrace();
                }
            }
        });
        changeGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // 学生成绩修改
                String Sno = changeGradeSno.getText();
                String Cno = changeCourseCno.getText();
                String Grade = changeGradeGrade.getText();
                int grade = Integer.parseInt(Grade);
                try{
                    Statement statement = conn.createStatement();
                    String sql = "update project.SC set Grade = " + grade + " where Cno = '" + Cno + "' AND Sno = '" + Sno + "'";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(course, "修改成功");
                }catch (Exception ee){
                    JOptionPane.showMessageDialog(course, "修改失败");
                    ee.printStackTrace();
                }
            }
        });
        changeGradeEnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String Cno = changeGradeCno.getText();
                    String Sno = changeGradeSno.getText();
                    Statement statement = conn.createStatement();
                    String sql = "Select Grade from project.SC where Cno = '" + Cno + "' AND Sno = '" + Sno + "'";
                    ResultSet rs = statement.executeQuery(sql);
                    String res = "";
                    while(rs.next()){
                        res += rs.getString("Grade");
                    }
                    changeGradeResult.setText(res);
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        });
    }
    public void channel(Connection con){
        /*JFrame frame = new JFrame("SuperQuery");
        frame.setContentPane(new SuperQuery().course);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,300);
        frame.setVisible(true);
        conn = con;
        //System.out.print(conn);*/
    }
    public static void main(Connection con) {
        //conn = con;
        JFrame frame = new JFrame("SuperQuery");
        frame.setContentPane(new SuperQuery().course);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400,300);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        conn = con;
    }
}
