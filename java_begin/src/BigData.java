import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BigData {
    private JPanel bigdata;
    private JButton begin;
    private JTextField datatime;
    private JTextField Sno;
    private JTextField Cno;
    private JTable result;
    private JButton QueryButton;
    private static Connection con;

    public static void main(Connection cont) {
        JFrame frame = new JFrame("BigData");
        frame.setContentPane(new BigData().bigdata);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        con = cont;
    }

    public BigData() {
        begin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long res = computeTime();
                    String s = String.valueOf(res);
                    datatime.setText(s);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        QueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model=new DefaultTableModel();
                result.setModel(model);
                model.addColumn("学号");
                model.addColumn("课程号");
                model.addColumn("成绩");
                String sno = Sno.getText();
                String cno = Cno.getText();
                int ssno = Integer.parseInt(sno);
                try {
                    Statement statement = con.createStatement();
                    String sql = "";
                    if(!cno.equals("")) sql = "select * from project.sc_test where Sno=" + ssno + " AND Cno_test = '" +
                            cno + "'";
                    else sql = "select * from project.sc_test where Sno=" + ssno;
                    ResultSet rs = statement.executeQuery(sql);
                    System.out.print(rs);
                    while(rs.next()){
                        model.addRow(new Object[]{rs.getString("Sno"),
                                rs.getString("Cno_test"),
                                rs.getString("Grade")});
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
    }

    public static long computeTime() throws Exception {

        long start = System.currentTimeMillis();
        conn();
        long end = System.currentTimeMillis();
        return (end - start)/1000;
    }
    public static void conn(){
        //1.导入驱动jar包
        //2.注册驱动(mysql5之后的驱动jar包可以省略注册驱动的步骤)
        //Class.forName("com.mysql.jdbc.Driver");
        //3.获取数据库连接对象
        Connection conn = null;
        PreparedStatement pstmt = null;
        {
            try {
                //"&rewriteBatchedStatements=true",一次插入多条数据，只插入一次
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root"
                        + "&rewriteBatchedStatements=true","root","100318");
                //4.定义sql语句
                String sql = "insert into project.sc_test values(default,?,?)";
                //5.获取执行sql的对象PreparedStatement
                pstmt = conn.prepareStatement(sql);
                //6.不断产生sql
                for (int i = 0; i < 1000000; i++) {
                    pstmt.setString(1,(int)(Math.random()*1000000)+"");
                    pstmt.setString(2,(int)(Math.random()*100)+"");
                    pstmt.addBatch();
                }
                //7.往数据库插入一次数据
                pstmt.executeBatch();
                System.out.println("添加1000000条信息成功！");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //8.释放资源
                //避免空指针异常
                if(pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if(conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
