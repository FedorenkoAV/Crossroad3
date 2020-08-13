import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vcnuv on 07.06.2018.
 */
public class ThreadSquare implements Runnable {
    private final boolean horVert;
    private int x, y;
    private int crossroadStart;
    private int crossroadEnd;
    JButton button;
    private final Container contentPane;
    private final CrossroadMove crossroadMove;

    public ThreadSquare(boolean horVert) {
        this.horVert = horVert;
        if (horVert) {
            x = 0;
            y = Settings.POINT_CROSS_ROAD.y + (int) (Math.random() * (Settings.DIMENSION_CROSS_ROAD.height - Settings.HEIGHT_SQUARE));
            crossroadStart = Settings.POINT_CROSS_ROAD.x - Settings.WIDTH_SQUARE + 2;
            crossroadEnd = crossroadStart + Settings.DIMENSION_CROSS_ROAD.width + Settings.WIDTH_SQUARE;
        } else {
            x = Settings.POINT_CROSS_ROAD.x + (int) (Math.random() * (Settings.DIMENSION_CROSS_ROAD.width - Settings.WIDTH_SQUARE));
            y = 0;
            crossroadStart = Settings.POINT_CROSS_ROAD.y - Settings.HEIGHT_SQUARE + 2;
            crossroadEnd = crossroadStart + Settings.DIMENSION_CROSS_ROAD.height + Settings.HEIGHT_SQUARE;
        }
        this.contentPane = Settings.contentPane;
        this.crossroadMove = Settings.crossroadMove;
        initButton();
    }

    @Override
    public void run() {
        while (true) {
            /*
            Режим 1 -   по дороге перемещаемся обычным вызовом метода crossroadMove.move()
                        при заезде на перекресток запускаем этот же метод crossroadMove.move в синхронном режиме,
                        таким образом на перекресток сможет попасть только один квадрат.
                        После покидания перекрестка снова запускаем метод crossroadMove.move в обычном режиме.
            Режим 2 -   сначала запускаем метод crossroadMove.syncHorMove() или crossroadMove.syncVerMove() чтобы
                        дождаться освобождения перекрестка оппонентами,
                        когда дождались освобождения перекрестка, блокируем к нему доступ оппонентов тем, что
                        увеличиваем на еденицу число "своих" квадратов на перекрестке.
                        Затем запускаем метод crossroadMove.getNextPoint() НЕ в синхронном режиме, чтобы свои могли
                        заезжать на перекресток свободно.
                        После того, как квадрат покинул перекресток, в синхронном режиме уменьшаем счетчик квадратов.
                        Если все квадраты покинули перекресток, сообщаем (notifyAll()), что перекресток свободен.
             */
//            System.out.println("Поехали по первой дороге прямо");
            crossroadMove.move(button, horVert, 1, crossroadStart);

            x = button.getX();
            y = button.getY();
//            System.out.println("Поехали по перекрестку прямо");
            if (Settings.numberTask) {//Режим 1
                synchronized (crossroadMove) {
                    crossroadMove.move(button, horVert, 1, crossroadEnd);
                }

            } else {//Режим 2
                if (horVert) {
                    crossroadMove.syncHorMove();
                } else {
                    crossroadMove.syncVerMove();
                }
                crossroadMove.getNextPoint(button, horVert, 1, crossroadEnd);
                if (horVert) {
                    crossroadMove.notifingHor();
                } else {
                    crossroadMove.notifingVer();
                }
            }


            x = button.getX();
            y = button.getY();
//            System.out.println("Поехали по второй дороге туда и обратно");
            crossroadMove.move(button, horVert, 1, crossroadEnd - 2);

            x = button.getX();
            y = button.getY();
//            System.out.println("Поехали по перекрестку обратно");
            if (Settings.numberTask) {
                synchronized (crossroadMove) {
                    crossroadMove.move(button, horVert, -1, crossroadStart - 2);
                }

            } else {
                if (horVert) {
                    crossroadMove.syncHorMove();
                } else {
                    crossroadMove.syncVerMove();
                }
                crossroadMove.getNextPoint(button, horVert, -1, crossroadStart);
                if (horVert) {
                    crossroadMove.notifingHor();
                } else {
                    crossroadMove.notifingVer();
                }
            }


//            System.out.println("Поехали по первой дороге обратно");
            crossroadMove.move(button, horVert, -1, 0);
        }
    }

    private void initButton() {
        button = new JButton("" + (++Settings.counter));
        button.setLocation(x, y);
        button.setSize(Settings.WIDTH_SQUARE, Settings.HEIGHT_SQUARE);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setForeground(Settings.COLOR_TEXT_SQUARE);
        button.setBackground(new Color(51, 102, 255));
        if (horVert) {
            button.setBackground(new Color(255, 153, 0));
        }
        contentPane.add(button);
        contentPane.repaint();
    }
}