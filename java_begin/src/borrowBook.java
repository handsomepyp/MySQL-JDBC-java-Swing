import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class borrowBook {
    private JButton QueryBorrowButton;
    private JTable BorrowRecord;
    private JPanel borrowRecord;
    private JTextField addBookSno;
    private JTextField addBorrowNum;
    private JTextField addBorrowSname;
    private JTextField addBorrowBookname;
    private JButton addBookRecordButton;
    private static Connection conn;

    public static void main(Connection con) {
        JFrame frame = new JFrame("borrowBook");
        frame.setContentPane(new borrowBook().borrowRecord);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        conn = con;
    }

    public borrowBook() {
        QueryBorrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                BorrowRecord.setModel(model);
                model.addColumn("学号");
                model.addColumn("姓名");
                model.addColumn("书名");
                model.addColumn("借书时间");
                model.addColumn("应还时间");
                model.addColumn("书籍库存");
                try {
                    Statement statement = conn.createStatement();
                    String sql = "select bookrecord.Sno, Student.Sname, library.Bookname,bookrecord.borrowtime, bookrecord.returntime, library.number " +
                            "from project.library, project.bookrecord, project.student " +
                            "where bookrecord.Sno = Student.Sno AND library.Bookno = bookrecord.Bookno";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{
                        rs.getString("bookrecord.Sno"),
                        rs.getString("Student.Sname"),
                        rs.getString("library.Bookname"),
                        rs.getString("bookrecord.borrowtime"),
                        rs.getString("bookrecord.returntime"),
                        rs.getString("library.number")});
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        addBookRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Sno = addBookSno.getText();
                String Sname = addBorrowSname.getText();
                String Bname = addBorrowBookname.getText();
                String Bnumber = addBorrowNum.getText();
                Date date = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

                String dateTime = df.format(date); // Formats a Date into a date/time string.

                LocalDate today = LocalDate.parse(dateTime);
                //当前月份+（-1）
                today = today.minusMonths(-1);

                String Bno = "";
                String returnTime = today.toString();

                try {
                    Statement statement = conn.createStatement();
                    String sql = "select Bookno from project.library where Bookname = '"
                            + Bname + "'";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        Bno = rs.getString("Bookno");
                    }
                    String sql1 = "insert into project.bookrecord" +
                            "(Sno, Bookno, borrowtime, returntime) value('"
                            + Sno + "', '" + Bno + "','" + dateTime + "','" + returnTime + "')";
                    //System.out.print(sql1);

                    statement.executeUpdate(sql1);
                    String sql2 = "update project.student " +
                            "set borrowBooknum = borrowBooknum + "
                            + Bnumber +
                            " where Sno = '" + Sno + "'";
                    statement.executeUpdate(sql2);
                    String sql3 = "update project.library " +
                            "set number = number - " + Bnumber +
                            " where Bookno = '" + Bno + "'";
                    statement.executeUpdate(sql3);
                    JOptionPane.showMessageDialog(borrowRecord, "添加成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(borrowRecord, "添加失败");
                }

            }
        });
    }
}
