import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Grade {
    private JPanel Grade;
    private JTextField QueryStuSno;
    private JTextField QueryStuSname;
    private JButton QueryStuButton;
    private JTable StuGrade;
    private JTextField QueryTeacherCname;
    private JTable TeaTable;
    private JTextField addGradeSno;
    private JTextField addGradeSname;
    private JTextField addGradeCname;
    private JTextField addGradegrade;
    private JButton addGradeButton;
    private JButton GradeTeaButton;
    private JButton deleteGradeButton;
    private JButton changeGradeButton;
    private JTextField deleteGradeSno;
    private JTextField deleteGradeSname;
    private JTextField deleteGradeCname;
    private JTextField changeSno;
    private JTextField changeSname;
    private JTextField changeCname;
    private JTextField changeGrade;
    private static Connection conn;

    public Grade() {
        QueryStuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                StuGrade.setModel(model);
                model.addColumn("学号");
                model.addColumn("姓名");
                model.addColumn("性别");
                model.addColumn("课程");
                model.addColumn("成绩");
                String Sno = QueryStuSno.getText();
                String Sname = QueryStuSname.getText();
                try{
                    Statement statement = conn.createStatement();
                    String sql = "";
                    if(Sname.equals("")){
                        sql = "select SC.grade,SC.Sno,student.Sname,Student.Ssex,Course.Cname  " +
                                "from project.student,project.sc,project.course " +
                                "where student.Sno='" + Sno + "' AND student.Sno = SC.Sno AND SC.Cno = Course.Cno";
                    }
                    else if(Sno.equals("")){
                        sql = "select SC.grade,SC.Sno,student.Sname,Student.Ssex,Course.Cname  " +
                                "from project.student,project.sc,project.course " +
                                "where student.Sname='" + Sname + "' AND student.Sno = SC.Sno AND SC.Cno = Course.Cno";
                    }
                    if(!Sname.equals("") && !Sno.equals("")){
                        sql = "select SC.grade,SC.Sno,student.Sname,Student.Ssex,Course.Cname  " +
                                "from project.student,project.sc,project.course " +
                                "where student.Sname='" + Sname + "' AND student.Sno = SC.Sno AND SC.Cno = Course.Cno AND SC.Sno = '" + Sno + "'";
                    }
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{
                        rs.getString("SC.Sno"),
                        rs.getString("student.Sname"),
                        rs.getString("Student.Ssex"),
                        rs.getString("Course.Cname"),
                        rs.getString("SC.grade")});
                    }
                    //JOptionPane.showMessageDialog(Grade, "查询成功");
                } catch(Exception ee){
                    JOptionPane.showMessageDialog(Grade, "查询失败");
                    ee.printStackTrace();
                }
            }
        });
        GradeTeaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                TeaTable.setModel(model);
                String Cname = QueryTeacherCname.getText();
                model.addColumn("学号");
                model.addColumn("姓名");
                model.addColumn("性别");
                model.addColumn("课程");
                model.addColumn("成绩");
                try{
                    Statement statement = conn.createStatement();
                    String sql =  "select SC.grade,SC.Sno,student.Sname,Student.Ssex,Course.Cname  " +
                                "from project.student,project.sc,project.course " +
                                "where course.Cname='" + Cname +
                                "' AND student.Sno = SC.Sno AND SC.Cno = Course.Cno";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{
                                rs.getString("SC.Sno"),
                                rs.getString("student.Sname"),
                                rs.getString("Student.Ssex"),
                                rs.getString("course.Cname"),
                                rs.getString("SC.grade")});
                    }
                    //JOptionPane.showMessageDialog(Grade, "查询成功");
                } catch(Exception ee){
                    JOptionPane.showMessageDialog(Grade, "查询失败");
                    ee.printStackTrace();
                }
            }
        });
        deleteGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sno = deleteGradeSno.getText();
                String Cname = deleteGradeCname.getText();
                String Sname = deleteGradeSname.getText();
                try{
                    Statement statement = conn.createStatement();
                    String sql = "delete from project.SC where Sno = '"
                            + Sno + "' AND " + "SC.Cno = (" +
                            "select course.Cno " +
                            "from project.course " +
                            "where Cname = '" + Cname + "')";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(Grade, "删除成功");
                }catch (Exception ee){
                    JOptionPane.showMessageDialog(Grade, "删除失败");
                }
            }
        });
        addGradeButton.addActionListener(new ActionListener() { // 添加学生成绩
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sno = addGradeSno.getText();
                String Sname = addGradeSname.getText();
                String Cname = addGradeCname.getText();
                String grade = addGradegrade.getText();
                String Cno = "";
                try {
                    Statement statement1 = conn.createStatement();
                    String sql1 = "select Cno from project.course where Cname = '" + Cname + "'";
                    ResultSet rs = statement1.executeQuery(sql1);
                    while(rs.next()){
                        Cno = rs.getString("Cno");
                    }
                    Statement statement = conn.createStatement();
                    String sql = "insert into project.SC(Sno, Cno, Grade) " +
                            "value('" + Sno + "', '" + Cno + "', " + grade + ")";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(Grade, "添加成功");
                }catch (Exception ee){
                    JOptionPane.showMessageDialog(Grade, "添加失败");
                }
            }
        });
        changeGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sno = changeSno.getText();
                String Sname = changeSname.getText();
                String Cname = changeCname.getText();
                String changeGraded = changeGrade.getText();
                String Cno = "";
                try {
                    Statement statement1 = conn.createStatement();
                    String sql1 = "select Cno from project.course where Cname = '" + Cname + "'";
                    ResultSet rs = statement1.executeQuery(sql1);
                    while(rs.next()){
                        Cno = rs.getString("Cno");
                    }
                    Statement statement = conn.createStatement();
                    String sql = "update project.SC " +
                            "set Grade = " + changeGraded +
                            " where Sno = '" + Sno + "' AND Cno = '" + Cno + "'";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(Grade, "修改成功");
                }catch (Exception ee){
                    JOptionPane.showMessageDialog(Grade, "修改失败");
                }
            }
        });
    }

    public static void main(Connection con) {
        JFrame frame = new JFrame("Grade");
        frame.setContentPane(new Grade().Grade);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);//窗口在屏幕中间显示
        conn = con;
    }
}
