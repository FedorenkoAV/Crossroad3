import java.awt.*;

/**
 * Created by vcnuv on 07.06.2018.
 */
public class Settings {
    public static int counter = 0;
    public static boolean numberTask = true;

    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 600;

    public static final int WIDTH_SQUARE = 20;
    public static final int HEIGHT_SQUARE = 20;
    public static final int STEP_SQUARE = 1;
    public static final int DELAY_SQUARE = 10;

    public static final Color COLOR_OF_LINE = Color.RED;
    public static final Color COLOR_TEXT_SQUARE = Color.white;

    public static final Point POINT_CROSS_ROAD = new Point(250, 250);
    public static final Dimension DIMENSION_CROSS_ROAD = new Dimension(100, 100);

    public static final String ICON_FILE_NAME = "crossroads.png";

    public static final String BUTTON_TEXT_PART1 = "<html><font size = 5><p align = center>Задача 1.<p align=center> " +
            "На перекрестке <p align = center>" +
            "в одно время может <p align = center>оказаться только один квадрат";
    public static final String BUTTON_TEXT_PART2 = "<html><font size = 5><p align = center>Задача 2.<p align=center> " +
            "На перекрестке <p align =center>" +
            "в одно время могут оказаться <p align = center>квадраты одинаковых цветов";
    public static final String FRAME_TEXT_TASK1 = "Перекресток с нитями. Задача 1";
    public static final String FRAME_TEXT_TASK2 = "Перекресток с нитями. Задача 2";
    public static Container contentPane;
    public static CrossroadMove crossroadMove;
}