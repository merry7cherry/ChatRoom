import javax.swing.*;
import java.awt.*;

public class Server_User_Panel {
    public JPanel jPanel_Server_User_Panel;
    public JPanel jPanel_Server_User_online_west;
    public JPanel jPanel_Server_User_online_east;
    public JList jList_online;
    public JTextPane jTextPane_text;
    public JTextPane jTextPane_massage;
    public JButton jButton_send;
    public JLabel jLabel_name_of_object;
    public JComboBox jComboBox_User;
    public JButton jButton_delete;
    public JPanel jPanel_choose;
    public CardLayout cardLayout;
    public JPanel jPanel_choose_1;
    public CardLayout cardLayout_1;
    public JPanel jPanel_choose_2;
    public CardLayout cardLayout_2;

    /*返回JLabel类型
    创建服务器在线用户、群聊列表 及 消息框和打字框
    将服务器用户群聊列表及数据交互界面加入标签中
    返回给服务器界面作为JTabbedPane的一部分
     */
    public JLabel get_jLabel_Server_User_Panel(){
        jPanel_Server_User_Panel=new JPanel(new BorderLayout());
        jPanel_Server_User_Panel.setSize(Chat_Server_Frame.FRAME_WIDTH-20,Chat_Server_Frame.FRAME_HEIGHT-75);
        jPanel_Server_User_Panel.add(jPanel_Server_User_online_west(),BorderLayout.WEST);
        jPanel_Server_User_Panel.add(jPanel_Server_User_online_east(),BorderLayout.CENTER);
        cardLayout.show(jPanel_choose,"tip");
        cardLayout_1.show(jPanel_choose_1,"null");
        cardLayout_2.show(jPanel_choose_2,"delete");
        JLabel jLabel_Server_User_Panel=new JLabel();
        jLabel_Server_User_Panel.add(jPanel_Server_User_Panel);
        return jLabel_Server_User_Panel;
    }

    /*
    创建聊天消息框和打字框
    返回作为east部件
    分为“tip”、“chat”两种页面
    cardLayout选择
     */
    public JPanel jPanel_Server_User_online_east(){
        cardLayout=new CardLayout();
        jPanel_choose=new JPanel(cardLayout);
        JPanel jPanel_tip=new JPanel(new GridLayout(1,1));
        JLabel jLabel_tip=new JLabel("Choose user you want to notice on the left");
        jLabel_tip.setFont(new Font("PingFang SC", Font.PLAIN, 20));
        jLabel_tip.setHorizontalAlignment(JLabel.CENTER);
        jPanel_tip.add(jLabel_tip);
        jPanel_choose.add(jPanel_tip,"tip");

        jPanel_Server_User_online_east=new JPanel(new BorderLayout());

        jPanel_Server_User_online_east.add(jPanel_Server_User_online_east_North(),BorderLayout.NORTH);

        jTextPane_text=new JTextPane();
        jTextPane_text.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        JScrollPane jScrollPane_text=new JScrollPane(jTextPane_text);
        jScrollPane_text.setPreferredSize(new Dimension(650,0));
        jTextPane_text.setEditable(false);
        jPanel_Server_User_online_east.add(jScrollPane_text,BorderLayout.CENTER);

        JPanel jPanel_massage=new JPanel(new BorderLayout());
        jTextPane_massage=new JTextPane();
        jTextPane_massage.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        JScrollPane jScrollPane_massage=new JScrollPane(jTextPane_massage);
        jScrollPane_massage.setPreferredSize(new Dimension(650,100));
        jPanel_massage.add(jScrollPane_massage,BorderLayout.CENTER);
        JLabel jLabel_massage=new JLabel("Send Massage: ");
        jLabel_massage.setForeground(Color.BLACK);
        jLabel_massage.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jLabel_massage.setHorizontalAlignment(JLabel.LEFT);
        jLabel_massage.setPreferredSize(new Dimension(150,30));
        jPanel_massage.add(jLabel_massage,BorderLayout.NORTH);
        jButton_send=new JButton("send");
        jButton_send.setForeground(Color.BLUE);
        jButton_send.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jButton_send.setPreferredSize(new Dimension(100,30));
        jPanel_massage.add(jButton_send,BorderLayout.SOUTH);
        jPanel_Server_User_online_east.add(jPanel_massage,BorderLayout.SOUTH);

        jPanel_choose.add(jPanel_Server_User_online_east,"chat");

        return jPanel_choose;
    }

    /*
    创建聊天框Panel的用户选择器和对象展示器
    返回east作为North部件
    标签分为“user”、“null”两种模式
    用户列表（delete键）分为“box”、“delete”两种模式
    服务器选择用户时，标签->“null” 用户列表（delete键）->“delete”
    服务器选择群聊时，标签->“user” 用户列表（delete键）->“box”
     */
    public JPanel jPanel_Server_User_online_east_North(){
        JPanel jPanel=new JPanel(new BorderLayout());

        jLabel_name_of_object=new JLabel();
        jLabel_name_of_object.setText("");
        jLabel_name_of_object.setForeground(Color.BLUE);
        jLabel_name_of_object.setHorizontalAlignment(JLabel.LEFT);
        jLabel_name_of_object.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_name_of_object.setPreferredSize(new Dimension(200,40));
        jPanel.add(jLabel_name_of_object,BorderLayout.WEST);

        JLabel jLabel_user_name=new JLabel("User in this group: ");
        jLabel_user_name.setForeground(Color.BLACK);
        jLabel_user_name.setHorizontalAlignment(JLabel.CENTER);
        jLabel_user_name.setFont(new Font("PingFang SC", Font.BOLD, 15));
        jLabel_user_name.setPreferredSize(new Dimension(150,40));
        JPanel jPanel_user_name=new JPanel(new BorderLayout());
        JLabel jLabel_n=new JLabel();
        jLabel_n.setPreferredSize(new Dimension(0,5));
        JLabel jLabel_s=new JLabel();
        jLabel_s.setPreferredSize(new Dimension(0,5));
        JLabel jLabel_w=new JLabel();
        jLabel_w.setPreferredSize(new Dimension(120,0));
        jPanel_user_name.add(jLabel_n,BorderLayout.NORTH);
        jPanel_user_name.add(jLabel_s,BorderLayout.SOUTH);
        jPanel_user_name.add(jLabel_w,BorderLayout.WEST);
        jPanel_user_name.add(jLabel_user_name,BorderLayout.CENTER);
        cardLayout_1=new CardLayout();
        jPanel_choose_1=new JPanel(cardLayout_1);
        jPanel_choose_1.add(jPanel_user_name,"user");
        jPanel_choose_1.add(new JLabel(),"null");
        jPanel.add(jPanel_choose_1,BorderLayout.CENTER);
        cardLayout_1.show(jPanel_choose_1,"user");

        cardLayout_2=new CardLayout();
        jPanel_choose_2=new JPanel(cardLayout_2);
        JPanel jPanel_jComboBox_User=new JPanel(new BorderLayout());
        jComboBox_User =new JComboBox<>();
        jComboBox_User.setPreferredSize(new Dimension(200,30));
        JLabel jLabel1=new JLabel();
        jLabel1.setPreferredSize(new Dimension(0,5));
        JLabel jLabel2=new JLabel();
        jLabel2.setPreferredSize(new Dimension(0,5));
        jPanel_jComboBox_User.add(jLabel1,BorderLayout.NORTH);
        jPanel_jComboBox_User.add(jComboBox_User,BorderLayout.CENTER);
        jPanel_jComboBox_User.add(jLabel2,BorderLayout.SOUTH);
        jPanel_choose_2.add(jPanel_jComboBox_User,"box");

        jButton_delete=new JButton("Delete This User");
        jButton_delete.setForeground(Color.BLUE);
        jButton_delete.setFont(new Font("PingFang SC", Font.BOLD, 15));
        JPanel jPanel_jButton_delete=new JPanel(new BorderLayout());
        jButton_delete.setPreferredSize(new Dimension(200,30));
        JLabel jLabel11=new JLabel();
        jLabel11.setPreferredSize(new Dimension(0,5));
        JLabel jLabel22=new JLabel();
        jLabel22.setPreferredSize(new Dimension(0,5));
        jPanel_jButton_delete.add(jLabel11,BorderLayout.NORTH);
        jPanel_jButton_delete.add(jButton_delete,BorderLayout.CENTER);
        jPanel_jButton_delete.add(jLabel22,BorderLayout.SOUTH);
        jPanel_choose_2.add(jPanel_jButton_delete,"delete");
        cardLayout_2.show(jPanel_choose_2,"box");

        jPanel.add(jPanel_choose_2,BorderLayout.EAST);

        jPanel.setPreferredSize(new Dimension(0,40));
        return jPanel;
    }

    /*
    创建用户及群聊列表
    返回作为west部件
     */
    public JPanel jPanel_Server_User_online_west(){
        jPanel_Server_User_online_west=new JPanel(new BorderLayout());

        JLabel jLabel_User_and_group=new JLabel("Online User List :");
        jLabel_User_and_group.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_User_and_group.setForeground(Color.BLACK);
        jLabel_User_and_group.setHorizontalAlignment(JLabel.CENTER);
        jPanel_Server_User_online_west.add(jLabel_User_and_group,BorderLayout.NORTH);

        jPanel_Server_User_online_west.add(jPanel_Server_User_online_west_UserList(),BorderLayout.CENTER);
        return jPanel_Server_User_online_west;
    }

    /*
    创建用户与群聊列表的列表部件
    返回作为west部件
     */
    public JPanel jPanel_Server_User_online_west_UserList(){
        JPanel jPanel=new JPanel();
        jList_online=new JList<>();
        jList_online.setFont(new Font("PingFang SC",Font.BOLD, 14));
        jList_online.setVisibleRowCount(17);
        jList_online.setFixedCellWidth(180);
        jList_online.setFixedCellHeight(60);
        JScrollPane jScrollPane=new JScrollPane(jList_online);
        jScrollPane.setPreferredSize(new Dimension(200,490));
        jPanel.add(jScrollPane);
        return jPanel;
    }

    /*
    测试
     */
    public JPanel jPanel_test(){
        jPanel_Server_User_Panel=new JPanel(new BorderLayout());
        jPanel_Server_User_Panel.add(jPanel_Server_User_online_west(),BorderLayout.WEST);
        jPanel_Server_User_Panel.add(jPanel_Server_User_online_east(),BorderLayout.CENTER);
        return jPanel_Server_User_Panel;
    }

    public static void main(String[] args){
        Server_User_Panel server_user_panel=new Server_User_Panel();
        JFrame jFrame=new JFrame();
        jFrame.add(server_user_panel.get_jLabel_Server_User_Panel());
//        jFrame.add(server_user_panel.jPanel_Server_User_online_west());
//        jFrame.add(server_user_panel.jPanel_Server_User_online_east());
        jFrame.setVisible(true);
        jFrame.setSize(900,600);
    }
}
