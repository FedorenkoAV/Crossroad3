import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vcnuv on 07.06.2018.
 */
public class CrossRoad {
    private JFrame frame;
    private JButton addHorSquareButton, addVertSquareButton, changeTaskButton, exitButton;
    private MyDrawPanel drawPanel;

    public static class MyDrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0, 0, Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);

            g.setColor(Settings.COLOR_OF_LINE);
            g.drawLine(Settings.POINT_CROSS_ROAD.x, 0, Settings.POINT_CROSS_ROAD.x, Settings.FRAME_HEIGHT);

            g.setColor(Settings.COLOR_OF_LINE);
            g.drawLine(Settings.POINT_CROSS_ROAD.x + Settings.DIMENSION_CROSS_ROAD.width, 0,
                    Settings.POINT_CROSS_ROAD.x + Settings.DIMENSION_CROSS_ROAD.width, Settings.FRAME_HEIGHT);

            g.setColor(Settings.COLOR_OF_LINE);
            g.drawLine(0, Settings.POINT_CROSS_ROAD.y, Settings.FRAME_WIDTH, Settings.POINT_CROSS_ROAD.y);

            g.setColor(Settings.COLOR_OF_LINE);
            g.drawLine(0, Settings.POINT_CROSS_ROAD.y + Settings.DIMENSION_CROSS_ROAD.height,
                    Settings.FRAME_WIDTH, Settings.POINT_CROSS_ROAD.y + Settings.DIMENSION_CROSS_ROAD.height);
        }
    }

    public void go() {
        addHorSquareButton = new JButton("<html><font size=5><p align = center>Добавить горизонтальный квадрат");
        addHorSquareButton.setLocation(10, 10);
        addHorSquareButton.setSize(230, 230);
        addHorSquareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread threadCrossRoad = new Thread(new ThreadSquare(true));
                threadCrossRoad.start();
                changeTaskButton.setEnabled(false);
            }
        });

        addVertSquareButton = new JButton("<html><font size=5><p align = center>Добавить вертикальный квадрат");
        addVertSquareButton.setLocation(360, 10);
        addVertSquareButton.setSize(225, 230);
        addVertSquareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread threadCrossRoad = new Thread(new ThreadSquare(false));
                threadCrossRoad.start();
                changeTaskButton.setEnabled(false);
            }
        });

        changeTaskButton = new JButton(Settings.BUTTON_TEXT_PART2);
        changeTaskButton.setLocation(10, 360);
        changeTaskButton.setSize(230, 205);
        changeTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Settings.numberTask) {
                    changeTaskButton.setText(Settings.BUTTON_TEXT_PART1);
                    frame.setTitle(Settings.FRAME_TEXT_TASK2);
                    Settings.numberTask = false;
                } else {
                    changeTaskButton.setText(Settings.BUTTON_TEXT_PART2);
                    frame.setTitle(Settings.FRAME_TEXT_TASK1);
                    Settings.numberTask = true;
                }
//                Settings.crossroadMove.notifingAll();
            }
        });

        exitButton = new JButton("<html><font size=5>Выход");
        exitButton.setLocation(360, 360);
        exitButton.setSize(225, 205);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        initDrawPanel();
        initFrame();
        Settings.crossroadMove = new CrossroadMove(Settings.contentPane);
    }

    private void initDrawPanel() {
        drawPanel = new MyDrawPanel();
        drawPanel.setBackground(Color.white);
        drawPanel.setLayout(null);
        drawPanel.add(addHorSquareButton);
        drawPanel.add(addVertSquareButton);
        drawPanel.add(changeTaskButton);
        drawPanel.add(exitButton);
        Settings.contentPane = drawPanel;
    }

    private void initFrame() {
        frame = new JFrame(Settings.FRAME_TEXT_TASK1);
        frame.getContentPane().add(drawPanel, BorderLayout.CENTER);
        frame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(Settings.ICON_FILE_NAME).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        CrossRoad cr = new CrossRoad();
        cr.go();
    }
}

