package com.itheima.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    //创建一个二维数组用来管理数据
    int[][] data = new int[4][4];

    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;

    //定义一个变量，记录当前展示图片的路径
    String path = "puzzlegame/image/animal/animal3/";

    //定义一个二维数组，存储正确的数据
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //定义变量用来统计步数
    int step = 0;

    //创建选项下面的条目对象（作为成员变量，方便在事件中判断）
    JMenuItem replayItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("公众号");

    public GameJFrame() {
        //初始化界面
        initJFrame();
        //初始化菜单
        initJMenuBar();
        //初始化数据（打乱）
        initData();
        //初始化图片
        initImage();

        //让界面显示出来
        this.setVisible(true);
    }

    //初始化数据（打乱）
    private void initData() {
        //1.定义一个一维数组
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //2.打乱数组中的数据的顺序
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        //3.给二维数组添加数据
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    //初始化图片
    private void initImage() {
        //清空原本已经出现的所有图片
        this.getContentPane().removeAll();

        if (victory()) {
            //显示胜利的图标
            JLabel winJLabel = new JLabel(new ImageIcon("puzzlegame/image/win.png"));
            winJLabel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount = new JLabel("步数：" + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);

        // 外循环 --- 把内循环重复执行了4次
        for (int i = 0; i < 4; i++) {
            // 内循环 --- 表示在一行添加4张图片
            for (int j = 0; j < 4; j++) {
                int num = data[i][j];
                // 创建一个JLabel的对象（管理容器）
                JLabel jLabel = new JLabel(new ImageIcon(path + num + ".jpg"));
                // 指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                // 给图片添加边框: 0表示凸起来, 1表示凹下去
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                this.getContentPane().add(jLabel);
            }
        }

        //添加背景图片
        JLabel background = new JLabel(new ImageIcon("puzzlegame/image/background.png"));
        background.setBounds(40, 40, 508, 560);
        this.getContentPane().add(background);

        //刷新一下界面
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于我们");

        // === 新增：更换图片多级菜单 ===
        JMenu changeImage = new JMenu("更换图片");
        JMenu girlMenu = new JMenu("美女");
        JMenu animalMenu = new JMenu("动物");
        JMenu sportMenu = new JMenu("运动");

        changeImage.add(girlMenu);
        changeImage.add(animalMenu);
        changeImage.add(sportMenu);

        // 循环添加美女分类 1-13
        for (int i = 1; i <= 13; i++) {
            JMenuItem item = new JMenuItem("美女" + i);
            item.addActionListener(this);
            girlMenu.add(item);
        }
        // 循环添加动物分类 1-8
        for (int i = 1; i <= 8; i++) {
            JMenuItem item = new JMenuItem("动物" + i);
            item.addActionListener(this);
            animalMenu.add(item);
        }
        // 循环添加运动分类 1-10
        for (int i = 1; i <= 10; i++) {
            JMenuItem item = new JMenuItem("运动" + i);
            item.addActionListener(this);
            sportMenu.add(item);
        }

        //将每一个选项下面的条目添加到选项当中
        functionJMenu.add(changeImage);
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        aboutJMenu.add(accountItem);

        //给常规条目绑定事件
        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        //将菜单里面的两个选项添加到菜单当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //给整个界面设置菜单
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        this.setSize(603, 680);
        this.setTitle("拼图单机版 v1.0");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    //按下不松时会调用这个方法 (作弊键 A)
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {
            this.getContentPane().removeAll();
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);

            JLabel background = new JLabel(new ImageIcon("puzzlegame/image/background.png"));
            background.setBounds(40, 40, 508, 560);
            this.getContentPane().add(background);

            this.getContentPane().repaint();
        }
    }

    //松开按键的时候会调用这个方法
    @Override
    public void keyReleased(KeyEvent e) {
        //如果胜利，直接结束
        if (victory()) return;

        //对上，下，左，右进行判断。 左：37 上：38 右：39 下：40
        int code = e.getKeyCode();
        if (code == 37) {
            if (y == 3) return;
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            step++;
            initImage();
        } else if (code == 38) {
            if (x == 3) return;
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            step++;
            initImage();
        } else if (code == 39) {
            if (y == 0) return;
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            step++;
            initImage();
        } else if (code == 40) {
            if (x == 0) return;
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            step++;
            initImage();
        } else if (code == 65) {
            // 松开 A 键，恢复图片
            initImage();
        } else if (code == 87) {
            // 按下 W 键，直接作弊通关
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }
    }

    //判断数组中的数据是否跟正确数组中相同
    public boolean victory() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String command = e.getActionCommand(); // 获取被点击条目的文字

        if (obj == replayItem) {
            System.out.println("重新游戏");
            resetGame();
        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            this.setVisible(false);
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.out.println("关闭游戏");
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("公众号");
            JDialog jDialog = new JDialog();

            // === 增加的图片缩放逻辑 ===
            ImageIcon originalIcon = new ImageIcon("puzzlegame/image/register/截屏2026-03-19 14.40.10.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(320, 320, Image.SCALE_SMOOTH);
            JLabel jLabel = new JLabel(new ImageIcon(scaledImage));
            jLabel.setBounds(12, 12, 320, 320);

            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(364, 400); // 加大弹窗尺寸以容纳图片
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);

        }
        // === 新增：解析点击的是哪个分类的图片，并修改路径 ===
        else if (command != null && command.startsWith("美女")) {
            // 截取 "美女" 后面的数字
            int num = Integer.parseInt(command.substring(2));
            path = "puzzlegame/image/girl/girl" + num + "/";
            resetGame();
        } else if (command != null && command.startsWith("动物")) {
            int num = Integer.parseInt(command.substring(2));
            path = "puzzlegame/image/animal/animal" + num + "/";
            resetGame();
        } else if (command != null && command.startsWith("运动")) {
            int num = Integer.parseInt(command.substring(2));
            path = "puzzlegame/image/sport/sport" + num + "/";
            resetGame();
        }
    }

    // 抽取出的公共方法：用于重置游戏的步数、数据并刷新界面
    private void resetGame() {
        step = 0;
        initData();
        initImage();
    }
}