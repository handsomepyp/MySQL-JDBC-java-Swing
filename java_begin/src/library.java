import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class library {
    private JTextField addBookName;
    private JTextField addBookNo;
    private JTextField addBookAuthor;
    private JTextField addBookNum;
    private JButton addBookButton;
    private JTextField deleteBookName;
    private JTextField deleteBookNo;
    private JButton deleteBookButton;
    private JTable BookInfo;
    private JPanel bookInfo;
    private JButton QueryBookButton;
    private static Connection conn;

    public static void main(Connection con) {
        JFrame frame = new JFrame("library");
        frame.setContentPane(new library().bookInfo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        conn = con;
    }

    public library() {
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = addBookName.getText();
                String no = addBookNo.getText();
                String author = addBookAuthor.getText();
                String number = addBookNum.getText();
                Statement statement;
                try {
                    statement = conn.createStatement();
                    String sql = "insert into project.library(Bookno, Bookname, author, number) value " +
                            "('" + no + "','" + name + "', '" + author + "', " + number + ")";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(bookInfo, "添加成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(bookInfo, "添加失败");
                }

            }
        });
        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Bookname = deleteBookName.getText();
                String BookNo = deleteBookNo.getText();
                Statement statement;
                try {
                    statement = conn.createStatement();
                    String sql = "delete from project.library where Bookname =  '" +
                            Bookname + "' AND Bookno = '" + BookNo + "'";
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(bookInfo, "删除成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(bookInfo, "删除失败");
                }
            }
        });
        QueryBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                BookInfo.setModel(model);
                model.addColumn("书名");
                model.addColumn("书号");
                model.addColumn("作者");
                model.addColumn("库存");
                Statement statement;
                try {
                    statement = conn.createStatement();
                    String sql = "select * from project.library";
                    ResultSet rs = statement.executeQuery(sql);
                    while(rs.next()){
                        model.addRow(new Object[]{
                                rs.getString("Bookno"),
                                rs.getString("Bookname"),
                                rs.getString("author"),
                                rs.getString("number")});
                    }
                    //JOptionPane.showMessageDialog(bookInfo, "删除成功");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(bookInfo, "查询失败");
                }
            }
        });
    }
}
