import javax.swing.*;
import java.awt.*;

public class CrossroadMove {

    private final Container contentPane;
    private int horCount;
    private int verCount;

    public CrossroadMove(Container contentPane) {
        this.contentPane = contentPane;
        horCount = 0;
        verCount = 0;
    }

    public void move(JButton button, boolean horVert, int direction, int destination) {
        int x = button.getX();
        int y = button.getY();
        while (true) {
            button.setBounds(x, y, Settings.WIDTH_SQUARE, Settings.HEIGHT_SQUARE);
            contentPane.repaint();
            try {
                Thread.sleep(Settings.DELAY_SQUARE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (horVert) {
                x += Settings.STEP_SQUARE * direction;
                if (x >= Settings.FRAME_WIDTH - Settings.WIDTH_SQUARE - 15) {
                    direction = -1;
                }
                if (x <= 0) {
                    direction = 1;
                }
                if (x == destination) {
                    return;
                }

            } else {
                y += Settings.STEP_SQUARE * direction;
                if (y >= Settings.FRAME_HEIGHT - Settings.HEIGHT_SQUARE - 38) {
                    direction = -1;
                }
                if (y <= 0) {
                    direction = 1;
                }
                if (y == destination) {
                    return;
                }
            }

        }
    }

    synchronized public void syncHorMove() {//Сюда может попасть только один квадрат движущийся горизонтально, остальные горизонтальные ждут пока этот выйдет из метода
        while (verCount != 0) {//Если на перекрестке есть квадраты движущиеся вертикально, ждем, пока они освободят перекресток
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        horCount++; //Перекресток свободен от квадратов движущихся вертикально. Отмечаемся увеличивая счетчик
//        System.out.println("На перекрестке " + horCount + " квадратов движущихся горизонтально.");
    }

    synchronized public void syncVerMove() {//Сюда может попасть только один квадрат движущийся вертикально, остальные вертикальные ждут пока этот выйдет из метода
        while (horCount != 0) {//Если на перекрестке есть квадраты движущиеся горизонтально, ждем, пока они освободят перекресток
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        verCount++; //Перекресток свободен от квадратов движущихся горизонтально. Отмечаемся увеличивая счетчик
//        System.out.println("На перекрестке " + verCount + " квадратов движущихся вертикально.");
    }

    public void getNextPoint(JButton button, boolean horVert, int direction, int destination) {
        int x = button.getX();
        int y = button.getY();
        while (true) {
            button.setBounds(x, y, Settings.WIDTH_SQUARE, Settings.HEIGHT_SQUARE);
            contentPane.repaint();
            try {
                Thread.sleep(Settings.DELAY_SQUARE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (horVert) {
                x += Settings.STEP_SQUARE * direction;
                if (x >= Settings.FRAME_WIDTH - Settings.WIDTH_SQUARE - 15) {
                    direction = -1;
                }
                if (x <= 0) {
                    direction = 1;
                }
                if (x == destination) {

                    return;
                }

            } else {
                y += Settings.STEP_SQUARE * direction;
                if (y >= Settings.FRAME_HEIGHT - Settings.HEIGHT_SQUARE - 38) {
                    direction = -1;
                }
                if (y <= 0) {
                    direction = 1;
                }
                if (y == destination) {

                    return;
                }
            }

        }
    }

    synchronized public void notifingVer() {
        verCount--; //Покидая перекресток, уменьшаем счетчик
        if (verCount == 0) {//Если все покинули перекресток, оповещаем об этом
            notifyAll();
        }
    }

    synchronized public void notifingHor() {
        horCount--; //Покидая перекресток, уменьшаем счетчик
        if (horCount == 0) {//Если все покинули перекресток, оповещаем об этом
            notifyAll();
        }
    }

    synchronized public void notifingAll() {
        notifyAll();
    }
}