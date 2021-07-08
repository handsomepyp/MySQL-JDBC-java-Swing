import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

public class Index {
    private JButton conToMySQLButton;
    private JButton StudentInfoButton;
    private JButton CourseInfoButton;
    private JButton GradeInfoButton;
    private JButton CloseMySQLButton;
    private JPanel index;
    private JButton superFuncButton;
    private JButton BigDataButton;
    private JButton bookInfoButton;
    private JButton BorrowBookInfoButton;
    private JButton returnBookButton;
    private String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private String user = "root";
    private String passwd = "100318";
    private String jdbcName="com.mysql.jdbc.Driver"; // 驱动名称
    private Connection conn;

    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(url, user, passwd);
        return con;
    }
    public Index() {
        conToMySQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(index, "数据库连接成功");
                try {
                    conn = getCon();
                } catch (Exception ee){
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(index, "数据库连接失败");
                }
            }
        });
        StudentInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = new Student();
                student.main(conn);
            }
        });
        CourseInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Course course = new Course();
                course.main(conn);
            }
        });
        GradeInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Grade grade = new Grade();
                grade.main(conn);
            }
        });
        CloseMySQLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(conn.isClosed())
                        JOptionPane.showMessageDialog(index, "连接已处于关闭状态");
                    else{
                        conn.close();
                        JOptionPane.showMessageDialog(index, "关闭成功");
                    }
                } catch (Exception ee){
                    JOptionPane.showMessageDialog(index, "关闭失败");
                    ee.printStackTrace();
                }
            }
        });
        superFuncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                superFunction sup = new superFunction();
                sup.main(conn);
            }
        });
        BigDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigData bigdata = new BigData();
                bigdata.main(conn);
            }
        });
        bookInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library lib = new library();
                lib.main(conn);
            }
        });
        BorrowBookInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook borrow = new borrowBook();
                borrow.main(conn);
            }
        });
        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook returnbook = new returnBook();
                returnbook.main(conn);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Index");
        frame.setContentPane(new Index().index);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);//窗口在屏幕中间显示
        frame.setSize(300,400);
    }
}
