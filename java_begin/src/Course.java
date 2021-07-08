import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Course {
    private JTextField addCno;
    private JTextField addCname;
    private JTextField addCtype;
    private JTextField addCcredit;
    private JButton addCourseButton;
    private JPanel Course;
    private JButton deleteCourseButton;
    private JTextField deleteCno;
    private JTextField deleteCname;
    private JTextField changeCoursepre;
    private JTextField changeCourseCno;
    private JTextField changeCourseCname;
    private JTextField changeCourseCcredit;
    private JTextField changeCourseCtype;
    private JButton changeCourseButton;
    private JButton QueryCourseButton;
    private JTable courseTable;
    private static Connection conn;

    public static void main(Connection con) {
        JFrame frame = new JFrame("Course");
        frame.setContentPane(new Course().Course);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);//窗口在屏幕中间显示
        frame.setSize(400,800);
        conn = con;
    }

    public Course() {
        addCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // 添加课程信息
                String Cno = addCno.getText();
                String Cname = addCname.getText();
                String Ctype = addCtype.getText();
                String Ccredit = addCcredit.getText();
                int credit = Integer.parseInt(Ccredit);
                try{
                    Statement statement = conn.createStatement();
                    String sql = "insert into project.course(Cno, Cname, Ctype, Ccredit) value('" + Cno + "','" +
                            Cname + "','" + Ctype + "'," + credit + ")";
                    //System.out.print(sql);
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(Course, "添加课程成功");
                } catch(Exception ee){
                    System.out.print("添加课程失败");
                    ee.printStackTrace();
                }
            }
        });
        deleteCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cno = deleteCno.getText();
                String Cname = deleteCname.getText();
                if(!Cno.isBlank()){
                    try{
                        Statement statement = conn.createStatement();
                        String sql = "delete from project.course where Cno = " + Cno;
                        //System.out.print(sql);
                        statement.executeUpdate(sql);
                        JOptionPane.showMessageDialog(Course, "删除课程成功");
                    } catch(Exception ee){
                        System.out.print("删除课程失败");
                        ee.printStackTrace();
                    }
                }
                else if(!Cname.isBlank()){
                    try{
                        Statement statement = conn.createStatement();
                        String sql = "delete from project.course where Cname = " + Cname;
                        //System.out.print(sql);
                        statement.executeUpdate(sql);
                        JOptionPane.showMessageDialog(Course, "删除课程成功");
                    } catch(Exception ee){
                        System.out.print("删除课程失败");
                        ee.printStackTrace();
                    }
                }
            }
        });
        changeCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Cnamepre = changeCoursepre.getText();
                String Cno = changeCourseCno.getText();
                String Cname = changeCourseCname.getText();
                String Ccredit = changeCourseCcredit.getText();
                String Ctype = changeCourseCtype.getText();
                try{
                    Statement statement = conn.createStatement();
                    String sql = "select * from project.course where Cname = '" + Cnamepre + "'";
                    //System.out.print(sql);
                    ResultSet rs = statement.executeQuery(sql);
                    String oldCno = "", oldCcredit = "", oldCtype = "", oldCname = Cnamepre;
                    while(rs.next()){
                        oldCno = rs.getString("Cno");
                        oldCcredit = rs.getString("Ccredit");
                        oldCtype = rs.getString("Ctype");
                    } // 先查找老的

                    if(Cno.equals("")) Cno = oldCno;
                    if(Ccredit.equals("")) Ccredit = oldCcredit;
                    if(Ctype.equals("")) Ctype = oldCtype;
                    if(Cname.equals("")) Cname = oldCname;

                    String sql1 = "update project.course " +
                            "set Cno = '" + Cno + "', Cname = '" + Cname +
                            "', Ctype = '" + Ctype + "', Ccredit = " + Ccredit +
                            " where Cname = '" + Cnamepre + "'";
                    statement.executeUpdate(sql1); // 添加完就更新
                    JOptionPane.showMessageDialog(Course, "修改课程成功");
                } catch(Exception ee){
                    JOptionPane.showMessageDialog(Course, "修改课程失败");
                    ee.printStackTrace();
                }
            }
        });
        QueryCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                courseTable.setModel(model);
                model.addColumn("课程号");
                model.addColumn("课程名");
                model.addColumn("学分");
                model.addColumn("类型");
                try{
                    Statement statement = conn.createStatement();
                    String sql = "select * from project.course";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{rs.getString("Cno"),
                                rs.getString("Cname"),
                                rs.getString("Ccredit"),
                                rs.getString("Ctype")});
                    }
                } catch(Exception ee){
                    //System.out.print("删除课程失败");
                    ee.printStackTrace();
                }
            }
        });
    }
}
