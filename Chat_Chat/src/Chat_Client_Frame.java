import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serial;
import java.net.Socket;

public class Chat_Client_Frame extends JFrame implements ActionListener, MouseListener , FocusListener ,WindowListener,WindowFocusListener{
    @Serial
    private static final long serialVersionUID = 1709267049032639256L;
    public static int FRAME_WIDTH=900;
    public static int FRAME_HEIGHT=600;
    String Username;
    Socket socket;
    ClientHandler clientHandler;
    public JPanel jPanel_Chat_Client_Panel;
    public JPanel jPanel_Chat_Client_west;
    public JPanel jPane_Chat_Client_east;
    public JList<String> jList_online;
    public JTextPane jTextPane_text;
    public JTextPane jTextPane_massage;
    public JButton jButton_send;
    public JLabel jLabel_name_of_object;
    public JTextPane jTextPane_notice;
    public JButton jButton_delete;
    public JPanel jPanel_choose;
    public CardLayout cardLayout;
    public JPanel jPanel_choose_1;
    public CardLayout cardLayout_1;
    public JPanel jPanel_choose_2;
    public CardLayout cardLayout_2;

    public Chat_Client_Frame(String Username,Socket socket,ClientHandler clientHandler){
        this.Username=Username;
        this.socket=socket;
        this.clientHandler=clientHandler;
        this.setTitle(Username+"  CHAT ROOM ");
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);//center
        this.add(jPanel_Chat_Client_Panel());
        this.setVisible(true);

        jList_online.addMouseListener(this);
        jButton_send.addMouseListener(this);
        jList_online.addFocusListener(this);
        this.addWindowFocusListener(this);
        this.addWindowListener(this);
    }

    /*返回JPanel类型
    创建客户端在线用户、群聊列表 及 消息框和打字框
    将客户端用户群聊列表及数据交互界面加入面板中
     */
    public JPanel jPanel_Chat_Client_Panel(){
        jPanel_Chat_Client_Panel=new JPanel(new BorderLayout());
        jPanel_Chat_Client_Panel.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        jPanel_Chat_Client_Panel.add(jPanel_Chat_Client_west(),BorderLayout.WEST);
        jPanel_Chat_Client_Panel.add(jPanel_Chat_Client_east(),BorderLayout.CENTER);
        cardLayout.show(jPanel_choose,"tip");
        return jPanel_Chat_Client_Panel;
    }

    /*
    创建聊天消息框和打字框
    返回作为east部件
    分为“tip”、“chat”两种页面
     */
    public JPanel jPanel_Chat_Client_east(){
        cardLayout=new CardLayout();
        jPanel_choose=new JPanel(cardLayout);
        JPanel jPanel_tip=new JPanel(new GridLayout(1,1));
        JLabel jLabel_tip=new JLabel("Choose user/group you want to chat on the left");
        jLabel_tip.setFont(new Font("PingFang SC", Font.PLAIN, 20));
        jLabel_tip.setHorizontalAlignment(JLabel.CENTER);
        jPanel_tip.add(jLabel_tip);
        jPanel_choose.add(jPanel_tip,"tip");

        jPane_Chat_Client_east=new JPanel(new BorderLayout());

        jPane_Chat_Client_east.add(jPanel_Chat_Client_east_North(),BorderLayout.NORTH);

        jTextPane_text=new JTextPane();
        jTextPane_text.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        JScrollPane jScrollPane_text=new JScrollPane(jTextPane_text);
        jScrollPane_text.setPreferredSize(new Dimension(600,0));
        jTextPane_text.setEditable(false);
        jPane_Chat_Client_east.add(jScrollPane_text,BorderLayout.CENTER);

        JPanel jPanel_massage=new JPanel(new BorderLayout());
        jTextPane_massage=new JTextPane();
        jTextPane_massage.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        JScrollPane jScrollPane_massage=new JScrollPane(jTextPane_massage);
        jScrollPane_massage.setPreferredSize(new Dimension(600,100));
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
        jPane_Chat_Client_east.add(jPanel_massage,BorderLayout.SOUTH);

        jPanel_choose.add(jPane_Chat_Client_east,"chat");

        return jPanel_choose;
    }

    /*
    创建聊天框Panel的用户选择器和对象展示器
    返回east作为North部件
    标签分为“user”、“null”两种模式
    用户列表（delete键）分为“box”、“delete”两种模式
    客户端选择用户时，标签->“null” 用户列表（delete键）->“delete”
    客户端选择群聊时，标签->“user” 用户列表（delete键）->“box”
     */
    public JPanel jPanel_Chat_Client_east_North(){
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

        cardLayout_1.show(jPanel_choose_1,"null");

        cardLayout_2=new CardLayout();
        jPanel_choose_2=new JPanel(cardLayout_2);

        JLabel jLabel_card2_null=new JLabel();
        jPanel_choose_2.add(jLabel_card2_null,"null");

        jButton_delete=new JButton("Delete This Friend");
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

        cardLayout_2.show(jPanel_choose_2,"null");

        jPanel.add(jPanel_choose_2,BorderLayout.EAST);

        jPanel.setPreferredSize(new Dimension(0,40));
        return jPanel;
    }

    /*
    创建用户及群聊列表
    返回作为west部件
     */
    public JPanel jPanel_Chat_Client_west(){
        jPanel_Chat_Client_west=new JPanel(new BorderLayout());

        JLabel jLabel_User_and_group=new JLabel("User/Group List :");
        jLabel_User_and_group.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_User_and_group.setForeground(Color.BLACK);
        jLabel_User_and_group.setHorizontalAlignment(JLabel.CENTER);
        jPanel_Chat_Client_west.add(jLabel_User_and_group,BorderLayout.NORTH);

        jPanel_Chat_Client_west.add(jPanel_Chat_Client_west_UserList(),BorderLayout.CENTER);

        JPanel jPanel_notice=new JPanel(new BorderLayout());
        jTextPane_notice=new JTextPane();
        jTextPane_notice.setEditable(false);
        jTextPane_notice.setFont(new Font("PingFang SC", Font.PLAIN, 15));
        JScrollPane jScrollPane_notice=new JScrollPane(jTextPane_notice);
        jScrollPane_notice.setPreferredSize(new Dimension(200,130));
        jPanel_notice.add(jScrollPane_notice,BorderLayout.CENTER);
        JLabel jLabel_notice=new JLabel("System Message:");
        jLabel_notice.setFont(new Font("PingFang SC", Font.BOLD, 20));
        jLabel_notice.setHorizontalAlignment(JLabel.CENTER);
        jPanel_notice.add(jLabel_notice,BorderLayout.NORTH);
        jPanel_Chat_Client_west.add(jPanel_notice,BorderLayout.SOUTH);
        return jPanel_Chat_Client_west;
    }

    /*
    创建用户与群聊列表的列表部件
    返回作为west部件
     */
    public JPanel jPanel_Chat_Client_west_UserList(){
        JPanel jPanel=new JPanel();
        jList_online=new JList<>();
        jList_online.setFont(new Font("PingFang SC",Font.BOLD, 15));
        jList_online.setVisibleRowCount(17);
        jList_online.setFixedCellWidth(180);
        jList_online.setFixedCellHeight(60);
        JScrollPane jScrollPane=new JScrollPane(jList_online);
        jScrollPane.setPreferredSize(new Dimension(200,370));
        jPanel.add(jScrollPane);
        return jPanel;
    }

    /*
    测试
     */
    public JPanel jPanel_test(){
        jPanel_Chat_Client_Panel=new JPanel(new BorderLayout());
        jPanel_Chat_Client_Panel.add(jPanel_Chat_Client_west(),BorderLayout.WEST);
        jPanel_Chat_Client_Panel.add(jPanel_Chat_Client_east(),BorderLayout.CENTER);
        return jPanel_Chat_Client_Panel;
    }

    public static void main(String[] args){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==jButton_send){
            String current=jList_online.getSelectedValue();
            if(jList_online.getSelectedIndex()<ClientHandler.petition){
                TransferInfo transferInfo=new TransferInfo();
                transferInfo.setStatusEnum(InfoType.CHAT_WITH_GROUP);
                transferInfo.setContent(jTextPane_massage.getText());
                transferInfo.setSender(Username);
                transferInfo.setReceiver_group(current);
                jTextPane_massage.setText("");
                IOStream.writeMessage(socket,transferInfo);
            } else {
                TransferInfo transferInfo=new TransferInfo();
                transferInfo.setStatusEnum(InfoType.CHAT_WITH_USER);
                transferInfo.setContent(jTextPane_massage.getText());
                transferInfo.setSender(Username);
                transferInfo.setReceiver_personnel(current);
                jTextPane_massage.setText("");
                IOStream.writeMessage(socket,transferInfo);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource()==jList_online){
            if(jList_online.getSelectedIndex()==-1){
                cardLayout.show(jPanel_choose,"tip");
                jLabel_name_of_object.setText("");
            } else if(jList_online.getSelectedIndex()<ClientHandler.petition){
                cardLayout.show(jPanel_choose,"chat");
                jTextPane_massage.setText("");
                String current=jList_online.getSelectedValue();
                jLabel_name_of_object.setText("Group: "+current);
                jTextPane_text.setText(ClientHandler.GroupMessageHashMap.get(current));
            } else {
                cardLayout.show(jPanel_choose,"chat");
                jTextPane_massage.setText("");
                String current=jList_online.getSelectedValue();
                jLabel_name_of_object.setText("User: "+current);
                jTextPane_text.setText(ClientHandler.UserMessageHashMap.get(current));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        if(e.getSource()==jList_online&&jList_online.getSelectedIndex()==-1){
            System.out.println("Client exit");
            jList_online.clearSelection();
            cardLayout.show(jPanel_choose,"tip");
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        clientHandler.setIs_exist(false);
        System.out.println(clientHandler.isIs_exist());

        TransferInfo transferInfo=new TransferInfo();
        transferInfo.setStatusEnum(InfoType.EXIT);
        transferInfo.setUsername(Username);
        transferInfo.setIs_exile(false);
        IOStream.writeMessage(socket,transferInfo);

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        if(e.getSource()==this&&jList_online.getSelectedIndex()==-1){
            jList_online.clearSelection();
            cardLayout.show(jPanel_choose,"tip");
        }
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        if(e.getSource()==this&&jList_online.getSelectedIndex()==-1){
            jList_online.clearSelection();
            cardLayout.show(jPanel_choose,"tip");
        }
    }
}
