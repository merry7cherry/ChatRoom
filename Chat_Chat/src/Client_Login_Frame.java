import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client_Login_Frame extends JFrame {
    // 设置窗体宽度常量
    private static final Integer FRAME_WIDTH = 600;
    // 设置窗体高度常量
    private static final Integer FRAME_HEIGHT = 400;

    //构造方法
    public Client_Login_Frame() {
        //下面对LoginFrame的设置加不加this.都行
        setTitle("*CHAT ROOM*");    //设置标题
        setSize(FRAME_WIDTH, FRAME_HEIGHT);    //设置尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //设置关闭模式
        setResizable(false);    //窗口是否可缩放（不行）

        // 获取屏幕像素并确定计算窗口位置居中，两种方法
        setLocationRelativeTo(null);
        //	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //	int screenWidth = screenSize.width;
        //	int screenheight = screenSize.height;
        //	setLocation((screenWidth - FRAME_WIDTH) / 2, (screenheight - FRAME_HEIGHT) / 2);

        JLabel jlbBg = new JLabel();

        // 图片的位置和大小【不设置也不影响】
        jlbBg.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        jlbBg.setLayout(null);    // 布局为空布局
        add(jlbBg);    //LoginFrame加入标签组件

        // 标题
        JLabel lblTitle = new JLabel("* CHAT   ROOM *");
        lblTitle.setBounds(150, 35, 350, 55);    // x，y，宽，高
        lblTitle.setFont(new Font("PingFang SC", Font.BOLD, 36));    //苹方字体，加粗，36尺寸
        lblTitle.setForeground(Color.RED);    //字体红色
        jlbBg.add(lblTitle);    //大背景图加入标题组件

        // 账户
        JLabel lblUid = new JLabel("ACCOUNT:");
        lblUid.setBounds(150, 120, 110, 30);    //x,y,宽，高
        lblUid.setFont(new Font("PingFang SC", Font.BOLD, 16));
        lblUid.setForeground(Color.BLACK);
        jlbBg.add(lblUid);
        //账户文本框
        JTextField textUid = new JTextField();
        textUid.setBounds(260, 120, 160, 30);
        jlbBg.add(textUid);

        // 登录按钮
        JButton login = new JButton("LOGIN");
        login.setBounds(175, 250, 103, 30);
        login.setBackground(Color.BLUE);    //背景粉色
        login.setForeground(Color.WHITE);    //字体白色
        login.setFont(new Font("PingFang SC", Font.BOLD, 16));

        //登录按钮监听
        login.addActionListener(new ActionListener() {    //匿名内部类，只在登录时使用一次
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textUid.getText();    //获取用户名
                if(username.isEmpty()||username.length()>10){
                    JOptionPane.showMessageDialog(null, "该用户名不合法，请重新输入");
                    return;
                }
                TransferInfo transferInfo = new TransferInfo();    //实例化传输信息对象
                transferInfo.setUsername(username);    //设置用户名
                transferInfo.setStatusEnum(InfoType.LOGIN);    //设置信息为登录
                connectionServer(transferInfo);     //与服务器连接
            }
        });
        jlbBg.add(login);

        // 取消按钮
        JButton cancel = new JButton("CANCEL");
        cancel.setBounds(355, 250, 103, 30);
        cancel.setBackground(Color.BLUE);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("PingFang SC", Font.BOLD, 16));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jlbBg.add(cancel);

        setVisible(true);    //设置可视化

    }

    //连接服务器的方法
    public void connectionServer(TransferInfo transferInfo) {
        try {
            Socket socket = new Socket("127.0.0.1", Port.SERVER_PORT);    //创建socket（主机，端口）
            //写一个消息
            IOStream.writeMessage(socket, transferInfo);
            //开启客户端子线程，接收消息
            ClientHandler clientHandler = new ClientHandler(socket, this);
            clientHandler.start();

        } catch (UnknownHostException e1) {    //捕获一下异常
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "服务器未上线");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("点击了login按钮");
    }

    //主函数：启动登录界面
    public static void main(String[] args) {
        new Client_Login_Frame();
    }

}

