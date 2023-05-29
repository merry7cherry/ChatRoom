import javax.swing.*;
import java.awt.*;

public class Server_Info_Panel {
    public JTextField jTextField_online_user_num;//当前在线人数
    public JTextField jTextField_ip;//服务器IP
    public JTextField jTextField_port;//服务器端口
    public JTextPane jTextPane_log;//服务器日志
    public JButton jButton_emit;//发送按钮
    public JTextPane jTextPane_massage;//

    /*返回JLabel类型
    创建服务器状态日志界面
    将服务器状态日志界面加入标签中
    返回给服务器界面作为JTabbedPane的一部分
    */
    public JLabel get_jLabel_Server_Info_Panel() {
        JPanel jPanel_Server_Info_Panel = new JPanel(new BorderLayout(5, 5));
        jPanel_Server_Info_Panel.setOpaque(true);
        jPanel_Server_Info_Panel.setSize(Chat_Server_Frame.FRAME_WIDTH - 20, Chat_Server_Frame.FRAME_HEIGHT - 75);

        JPanel jPanel_Server_Info_Panel_center = new JPanel();
        jPanel_Server_Info_Panel_center.setLayout(new BorderLayout(5, 5));
        jPanel_Server_Info_Panel_center.setOpaque(false);

        JLabel jLabel_log = new JLabel("Server Log");
        jLabel_log.setForeground(Color.RED);
        jLabel_log.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_log.setPreferredSize(new Dimension(100, 30));
        jPanel_Server_Info_Panel_center.add(jLabel_log, BorderLayout.NORTH);

        jTextPane_log = new JTextPane();
        jTextPane_log.setEditable(false);
        jTextPane_log.setBackground(Color.BLACK);
        jTextPane_log.setForeground(Color.WHITE);
        jTextPane_log.setOpaque(true);
        jTextPane_log.setFont(new Font("PingFang SC", Font.PLAIN, 18));
        JScrollPane jScrollPane_log = new JScrollPane(jTextPane_log);
        jPanel_Server_Info_Panel_center.add(jScrollPane_log, BorderLayout.CENTER);

        jPanel_Server_Info_Panel.add(jPanel_Server_Info_Panel_west(), BorderLayout.WEST);
        jPanel_Server_Info_Panel.add(jPanel_Server_Info_Panel_center, BorderLayout.CENTER);
        jPanel_Server_Info_Panel.add(jPanel_Server_Info_Panel_south(), BorderLayout.SOUTH);

        JLabel jLabel_Server_Info_Panel = new JLabel();
        jLabel_Server_Info_Panel.add(jPanel_Server_Info_Panel);
        return jLabel_Server_Info_Panel;
    }

    /*
    创建服务器状态界面
    返回给服务器状态日志界面作为
    west部件
    */
    public JPanel jPanel_Server_Info_Panel_west() {
        JPanel jPanel_Server_Info_Panel_west = new JPanel(new GridLayout(12, 1));
        jPanel_Server_Info_Panel_west.setOpaque(false);
        jPanel_Server_Info_Panel_west.setPreferredSize(new Dimension(200, 30));
        jPanel_Server_Info_Panel_west.setFont(new Font("PingFang SC", Font.BOLD, 15));

        JLabel jLabel_Server_status = new JLabel("Server status");
        jLabel_Server_status.setHorizontalAlignment(JLabel.CENTER);
        jLabel_Server_status.setForeground(Color.RED);
        jLabel_Server_status.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_Server_status.setPreferredSize(new Dimension(100, 30));
        jPanel_Server_Info_Panel_west.add(jLabel_Server_status, BorderLayout.NORTH);

        JLabel jLabel_Online_num = new JLabel("Current online population :");
        jLabel_Online_num.setForeground(Color.BLACK);
        jLabel_Online_num.setHorizontalAlignment(JTextField.CENTER);
        jLabel_Online_num.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jPanel_Server_Info_Panel_west.add(jLabel_Online_num);

        jTextField_online_user_num = new JTextField();
        jTextField_online_user_num.setText("0");
        jTextField_online_user_num.setForeground(Color.BLUE);
        jTextField_online_user_num.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jTextField_online_user_num.setEditable(false);
        jTextField_online_user_num.setHorizontalAlignment(JTextField.CENTER);
        jPanel_Server_Info_Panel_west.add(jTextField_online_user_num);

        JLabel jLabel_ip = new JLabel("Server IP :");
        jLabel_ip.setForeground(Color.BLACK);
        jLabel_ip.setHorizontalAlignment(JTextField.CENTER);
        jLabel_ip.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jPanel_Server_Info_Panel_west.add(jLabel_ip);

        jTextField_ip = new JTextField();
        jTextField_ip.setText("127.0.0.1");
        jTextField_ip.setForeground(Color.GRAY);
        jTextField_ip.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jTextField_ip.setEditable(false);
        jTextField_ip.setHorizontalAlignment(JTextField.CENTER);
        jPanel_Server_Info_Panel_west.add(jTextField_ip);

        JLabel jLabel_port = new JLabel("Server Port :");
        jLabel_port.setForeground(Color.BLACK);
        jLabel_port.setHorizontalAlignment(JTextField.CENTER);
        jLabel_port.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jPanel_Server_Info_Panel_west.add(jLabel_port);

        jTextField_port = new JTextField();
        jTextField_port.setText("8888");
        jTextField_port.setForeground(Color.GRAY);
        jTextField_port.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jTextField_port.setEditable(false);
        jTextField_port.setHorizontalAlignment(JTextField.CENTER);
        jPanel_Server_Info_Panel_west.add(jTextField_port);

        return jPanel_Server_Info_Panel_west;
    }

    /*
    创建服务器发送系统信息界面
    返回给服务器状态日志界面作为
    south部件
     */
    public JPanel jPanel_Server_Info_Panel_south() {
        JPanel jPanel_Server_Info_Panel_south = new JPanel();
        jTextPane_massage = new JTextPane();
        jTextPane_massage.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        JScrollPane jScrollPane = new JScrollPane(jTextPane_massage);
        jScrollPane.setPreferredSize(new Dimension(600, 40));
        jButton_emit = new JButton("Emit Massage To ALL User");
        jButton_emit.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jButton_emit.setForeground(Color.BLUE);
        jButton_emit.setPreferredSize(new Dimension(250, 30));
        jPanel_Server_Info_Panel_south.add(jScrollPane);
        jPanel_Server_Info_Panel_south.add(jButton_emit);
        return jPanel_Server_Info_Panel_south;
    }

}
