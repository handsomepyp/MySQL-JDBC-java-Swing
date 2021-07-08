import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class returnBook {
    private JPanel returnBook;
    private JTable returnBookRecord;
    private JButton returnBookQuerybutton;
    private JTextField returnBookSno;
    private JTextField returnBookSname;
    private JTextField returnBname;
    private JButton addReturnBookButton;
    private static Connection conn;

    public returnBook() {
        returnBookQuerybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                returnBookRecord.setModel(model);
                model.addColumn("学号");
                model.addColumn("姓名");
                model.addColumn("书名");
                model.addColumn("还书时间");
                try {
                    Statement statement = conn.createStatement();
                    String sql = "select Student.Sno,Student.Sname,library.Bookname,returnbook.returntime  " +
                            "from project.returnbook, project.Student, project.library " +
                            "where returnbook.Sno = Student.Sno AND returnbook.Bookno = library.Bookno";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{
                            rs.getString("Student.Sno"),
                            rs.getString("Student.Sname"),
                            rs.getString("library.Bookname"),
                            rs.getString("returnbook.returntime")});
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        addReturnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sname = returnBookSname.getText();
                String Sno = returnBookSno.getText();
                String Bname = returnBname.getText();
                String Bno = "";
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

                String returnTime = df.format(date); // Formats a Date into a date/time string.
                try {
                    Statement statement = conn.createStatement();
                    String sql = "Select Bookno " +
                            "from project.library " +
                            "where Bookname = '" + Bname + "'";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        Bno = rs.getString("Bookno");
                    }
                    String sql2 = "insert into project.returnbook(Sno, Bookno, returntime) " +
                            "value('" + Sno + "','" + Bno +"','" + returnTime + "')";
                    statement.executeUpdate(sql2);
                    String sql3 = "update project.library " +
                            "set number = number + " + 1 +
                            " where Bookname = '" + Bname + "'";
                    statement.executeUpdate(sql3);
                    String sql4 = "update project.Student " +
                            "set borrowBooknum = borrowBooknum - " + 1 +
                            " where Sno = " + Sno;
                    statement.executeUpdate(sql4);

                    JOptionPane.showMessageDialog(returnBook, "还书成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(returnBook, "还书失败");
                }

            }
        });
    }

    public static void main(Connection con) {
        JFrame frame = new JFrame("returnBook");
        frame.setContentPane(new returnBook().returnBook);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        conn = con;
    }

}
