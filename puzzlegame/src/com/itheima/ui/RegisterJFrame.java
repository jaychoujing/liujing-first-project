package com.itheima.ui;

import com.itheima.domain.User;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RegisterJFrame extends JFrame implements MouseListener {
    // 定义组件
    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    JPasswordField rePassword = new JPasswordField();

    JButton register = new JButton();
    JButton reset = new JButton();

    public RegisterJFrame() {
        initJFrame();
        initView();
        this.setVisible(true);
    }

    private void initView() {
        // 1. 用户名文字和输入框
        JLabel usernameText = new JLabel(new ImageIcon("puzzlegame/image/register/注册用户名.png"));
        usernameText.setBounds(96, 135, 79, 17);
        this.getContentPane().add(usernameText);

        username.setBounds(195, 134, 200, 30);
        this.getContentPane().add(username);

        // 2. 密码文字和输入框
        JLabel passwordText = new JLabel(new ImageIcon("puzzlegame/image/register/注册密码.png"));
        passwordText.setBounds(96, 195, 64, 16);
        this.getContentPane().add(passwordText);

        password.setBounds(195, 195, 200, 30);
        this.getContentPane().add(password);

        // 3. 确认密码文字和输入框 (已替换为你截图中的 再次输入密码.png)
        JLabel rePasswordText = new JLabel(new ImageIcon("puzzlegame/image/register/再次输入密码.png"));
        rePasswordText.setBounds(96, 255, 96, 16); // 宽度可能比原来长一点，我稍微调大到了96
        this.getContentPane().add(rePasswordText);

        rePassword.setBounds(195, 255, 200, 30);
        this.getContentPane().add(rePassword);

        // 4. 注册按钮
        register.setBounds(123, 310, 128, 47);
        register.setIcon(new ImageIcon("puzzlegame/image/register/注册按钮.png"));
        // 去除按钮的边框和背景
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.addMouseListener(this);
        this.getContentPane().add(register);

        // 5. 重置按钮
        reset.setBounds(256, 310, 128, 47);
        reset.setIcon(new ImageIcon("puzzlegame/image/register/重置按钮.png"));
        // 去除按钮的边框和背景
        reset.setBorderPainted(false);
        reset.setContentAreaFilled(false);
        reset.addMouseListener(this);
        this.getContentPane().add(reset);

        // 6. 添加背景图片 (必须放在最后添加，才能在最底层)
        JLabel background = new JLabel(new ImageIcon("puzzlegame/image/register/background.png"));
        background.setBounds(0, 0, 470, 390);
        this.getContentPane().add(background);
    }

    private void initJFrame() {
        this.setSize(488, 430);
        this.setTitle("拼图游戏 V1.0 注册");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        // 注意：这里用 DISPOSE_ON_CLOSE 或只隐藏窗口，不要用 EXIT_ON_CLOSE，否则关掉注册界面整个程序就退出了
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == register) {
            String name = username.getText();
            String pwd = new String(password.getPassword());
            String rePwd = new String(rePassword.getPassword());

            // 逻辑检查
            if (name.length() == 0 || pwd.length() == 0 || rePwd.length() == 0) {
                showJDialog("信息不能为空");
                return;
            }
            if (!pwd.equals(rePwd)) {
                showJDialog("两次密码不一致");
                return;
            }
            if (containsName(name)) {
                showJDialog("用户名已存在");
                return;
            }

            // 注册成功：添加用户并跳转
            LoginJFrame.allUsers.add(new User(name, pwd));
            showJDialog("注册成功！");

            // 隐藏当前注册界面，打开登录界面
            this.setVisible(false);
            new LoginJFrame();

        } else if (e.getSource() == reset) {
            // 清空三个输入框的内容
            username.setText("");
            password.setText("");
            rePassword.setText("");
        }
    }

    // 检查用户名是否重复
    private boolean containsName(String name) {
        for (User u : LoginJFrame.allUsers) {
            if (u.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void showJDialog(String content) {
        JDialog jDialog = new JDialog();
        jDialog.setSize(200, 150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);

        JLabel warning = new JLabel(content, JLabel.CENTER);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        jDialog.setVisible(true);
    }

    // --- 鼠标视觉反馈逻辑 ---

    // 鼠标按下：更换为按下的图片
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == register) {
            register.setIcon(new ImageIcon("puzzlegame/image/register/注册按下.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("puzzlegame/image/register/重置按下.png"));
        }
    }

    // 鼠标松开：恢复原本的图片
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == register) {
            register.setIcon(new ImageIcon("puzzlegame/image/register/注册按钮.png"));
        } else if (e.getSource() == reset) {
            reset.setIcon(new ImageIcon("puzzlegame/image/register/重置按钮.png"));
        }
    }

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}