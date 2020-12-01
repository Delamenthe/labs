package bsu.rfe.java.group6.lab5.Pomoz.varC16;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GraphicsDisplay extends JPanel implements MouseMotionListener, MouseListener{
    // Список координат точек для построения графика
    private Double[][] graphicsData;
    // Флаговые переменные, задающие правила отображения графика
    private boolean showAxis = true;
    private boolean showMarkers = true;
    private boolean antiClockRotate = false;
    private boolean mousePressed = false;
    private boolean mouseReleased = false;
    private boolean zoom= false;
    // Границы диапазона пространства, подлежащего отображению
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private double Xmin=0, Xmax=0, Ymin=0, Ymax=0;
    // Используемый масштаб отображения
    private double scale;
    // Различные стили черчения линий
    final private BasicStroke graphicsStroke;
    final private BasicStroke axisStroke;
    final private BasicStroke markerStroke;
    final private BasicStroke rectStroke;
    // Различные шрифты отображения надписей
    final private Font axisFont;
    final private Font cursor;

    // координаты курсора мыши
    int mouseX = 0, mouseY = 0;
    double mouseXto = 0, mouseYto = 0;
    double InitialMouseX=0,  InitialMouseY=0;
    double EndMouseX=0,  EndMouseY=0;
    public GraphicsDisplay() {
// Цвет заднего фона области отображения - белый
        setBackground(Color.WHITE);
// Сконструировать необходимые объекты, используемые в рисовании
// Перо для рисования графика
        graphicsStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 10.0f, new float[]{8, 3}, 0.0f);
// Перо для рисования осей координат
        axisStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
// Перо для рисования контуров маркеров
        markerStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
// Перо для рисования контуров прямоугольников
        rectStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, new float[]{10, 5}, 0.0f);
// Шрифт для подписей осей координат
        axisFont = new Font("Serif", Font.BOLD, 36);
        cursor = new Font("Serif",Font.PLAIN,14);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void showGraphics(Double[][] graphicsData) {
// Сохранить массив точек во внутреннем поле класса
        this.graphicsData = graphicsData;
// Запросить перерисовку компонента, т.е. неявно вызвать paintComponent()
        repaint();
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
        repaint();
    }

    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graphicsData == null || graphicsData.length == 0) return;
        if (!zoom){
            minX = graphicsData[0][0];
            maxX = graphicsData[graphicsData.length - 1][0];
            minY = graphicsData[0][1];
            maxY = minY;
            for (int i = 1; i < graphicsData.length; i++) {
                if (graphicsData[i][1] < minY) {
                    minY = graphicsData[i][1];
                }
                if (graphicsData[i][1] > maxY) {
                    maxY = graphicsData[i][1];
                }
            }
        }else{
            minX=Xmin;
            maxX=Xmax;
            minY=Ymin;
            maxY=Ymax;}


        double scaleX = getSize().getWidth() / (maxX - minX);
        double scaleY = getSize().getHeight() / (maxY - minY);

        scale = Math.min(scaleX, scaleY);
        if (scale == scaleX) {
            double yIncrement = (getSize().getHeight() / scale - (maxY - minY)) / 2;
            maxY += yIncrement;
            minY -= yIncrement;
        }
        if (scale == scaleY) {
            double xIncrement = (getSize().getWidth() / scale - (maxX - minX)) / 2;
            maxX += xIncrement;
            minX -= xIncrement;
        }
        Graphics2D canvas = (Graphics2D) g;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();
        if (antiClockRotate) {
            AffineTransform at = AffineTransform.getRotateInstance(-Math.PI / 2, getSize().getWidth() / 2, getSize().getHeight() / 2);
            at.concatenate(new AffineTransform(getSize().getHeight() / getSize().getWidth(), 0.0, 0.0, getSize().getWidth() / getSize().getHeight(),
                    (getSize().getWidth() - getSize().getHeight()) / 2, (getSize().getHeight() - getSize().getWidth()) / 2));
            canvas.setTransform(at);
        }
        if (showAxis) paintAxis(canvas);
        paintGraphics(canvas);
        if (showMarkers) paintMarkers(canvas);
        findCloseAreas(canvas);
        ShowPointsCoordinates(canvas);
        Scaling(canvas);

        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }

    protected void paintGraphics(Graphics2D canvas) {
// Выбрать линию для рисования графика
        canvas.setStroke(graphicsStroke);
// Выбрать цвет линии
        canvas.setColor(Color.RED);

        /* Будем рисовать линию графика как путь, состоящий из множества cегментов (GeneralPath)
         * Начало пути устанавливается в первую точку графика, после чего прямой соединяется со
         * следующими точками
         */
        GeneralPath graphics = new GeneralPath();

        for (int i = 0; i < graphicsData.length; i++) {
// Преобразовать значения (x,y) в точку на экране point
            Point2D.Double point = xyToPoint(graphicsData[i][0], graphicsData[i][1]);
            if (i > 0) {
// Не первая итерация цикла - вести линию в точку point
                graphics.lineTo(point.getX(), point.getY());
            } else {
// Первая итерация цикла - установить начало пути в точку point
                graphics.moveTo(point.getX(), point.getY());
            }
        }
// Отобразить график
        canvas.draw(graphics);
    }

    protected void findCloseAreas(Graphics2D canvas) {
        int k = 0;
        double s = 0, minX, maxX, maxY, minY = 0;
        Double[] point = new Double[2];
        Double[] nextPoint = new Double[2];
        for (int i = 0; i < graphicsData.length - 1; i++) {
            point[0] = graphicsData[i][0];
            point[1] = graphicsData[i][1];
            nextPoint[0] = graphicsData[i + 1][0];
            nextPoint[1] = graphicsData[i + 1][1];
            if (point[1] * nextPoint[1] <= 0)
                k++;
        }
        if (k > 1) {
            Double[] x  = new Double[graphicsData.length];
            Double[] y = new Double[graphicsData.length];
            for (int i = 0; i < graphicsData.length; i++) {
                x[i] = graphicsData[i][0];
                y[i] = graphicsData[i][1];
            }
            for (int i = 0; i < graphicsData.length - 2; i++) {
                if (y[i] * y[i + 1] <= 0) {
                    GeneralPath path = new GeneralPath();
                    Point2D p;
                    if (x[i] == 0 && y[i] == 0)
                        p = xyToPoint(0, 0);
                    else
                        p = xyToPoint((x[i + 1] + x[i]) / 2, 0);
                    Point2D np = xyToPoint(x[i + 1], y[i + 1]);
                    path.moveTo(p.getX(), p.getY());
                    path.lineTo(np.getX(), np.getY());
                    s += Math.abs(x[i + 1] - x[i]) * Math.abs(y[1 + i]) / 2;
                    minX = (np.getX() + p.getX()) / 2;
                    maxY = (p.getY());
                    do {
                        i++;
                        if (y[i] * y[i + 1] <= 0)
                            break;
                        Point2D P = xyToPoint(x[i], y[i]);
                        np = xyToPoint(x[i + 1], y[i + 1]);
                        if (np.getY() < P.getY())
                            minY = np.getY();
                        path.lineTo(np.getX(), np.getY());
                        s += Math.abs(y[i] + y[i + 1]) * Math.abs(x[i + 1] - x[i]) / 2;
                    } while (y[i] * y[i + 1] > 0 && i < graphicsData.length - 2);
                    if (y[i] * y[i + 1] > 0)
                        continue;
                    s += Math.abs(y[i]) * Math.abs((x[i + 1] + x[i]) / 2 - x[i]) / 2;
                    np = xyToPoint((x[i + 1] + x[i]) / 2, 0);
                    maxX = np.getX();
                    path.lineTo(np.getX(), p.getY());
                    path.closePath();
                    canvas.setColor(Color.GREEN);
                    canvas.fill(path);
                    canvas.setColor(Color.BLACK);
                    Font S = new Font("Serif", Font.PLAIN, 12);
                    canvas.setFont(S);
                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
                    formatter.setMaximumFractionDigits(3);
                    canvas.drawString(formatter.format(s), (float) (minX + (maxX - minX) / 2 - 10), (float) (minY + Math.abs(maxY - minY) / 2));

                    if (y[i] * y[i + 1] < 0)
                        i--;

                }
            }
        }
    }

    protected void paintMarkers(Graphics2D canvas) {
        canvas.setStroke(markerStroke);
        canvas.setColor(Color.RED);
        for (Double[] point : graphicsData) {
            double y = point[1];
            while (Math.abs(y - Math.round(y)) > 0)
                y *= 10;
            boolean flag = true;
            double c1 = y % 10, c2;
            y /= 10;
            while (Math.abs(y) > 0) {
                c2 = y % 10;
                y /= 10;
                if (c1 < c2) {
                    flag = false;
                    break;
                }
                c1 = c2;
            }
            if (flag)
                canvas.setColor(Color.BLUE);
            else
                canvas.setColor(Color.RED);

            Point2D.Double center = xyToPoint(point[0], point[1]);
            canvas.draw(new Line2D.Double(shiftPoint(center, -5, 5), shiftPoint(center, 5, -5)));
            canvas.draw(new Line2D.Double(shiftPoint(center, 5, 5), shiftPoint(center, -5, -5)));
            canvas.draw(new Line2D.Double(shiftPoint(center, 0, 5), shiftPoint(center, 0, -5)));
            canvas.draw(new Line2D.Double(shiftPoint(center, -5, 0), shiftPoint(center, 5, 0)));
        }
    }

    protected void paintAxis(Graphics2D canvas) {
// Установить особое начертание для осей
        canvas.setStroke(axisStroke);
// Оси рисуются чѐрным цветом
        canvas.setColor(Color.BLACK);
// Стрелки заливаются чѐрным цветом
        canvas.setPaint(Color.BLACK);
// Подписи к координатным осям делаются специальным шрифтом
        canvas.setFont(axisFont);
// Создать объект контекста отображения текста - для получения характеристик устройства (экрана)
        FontRenderContext context = canvas.getFontRenderContext();
// Определить, должна ли быть видна ось Y на графике
        if (minX <= 0.0 && maxX >= 0.0) {
// Она должна быть видна, если левая граница показываемой области (minX) <= 0.0,
// а правая (maxX) >= 0.0
// Сама ось - это линия между точками (0, maxY) и (0, minY)

            canvas.draw(new Line2D.Double(xyToPoint(0, maxY), xyToPoint(0, minY)));
// Стрелка оси Y
            GeneralPath arrow = new GeneralPath();
// Установить начальную точку ломаной точно на верхний конец оси Y
            Point2D.Double lineEnd = xyToPoint(0, maxY);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
// Вести левый "скат" стрелки в точку с относительными координатами (5,20)
            arrow.lineTo(arrow.getCurrentPoint().getX() + 5,
                    arrow.getCurrentPoint().getY() + 20);
// Вести нижнюю часть стрелки в точку с относительными координатами (-10, 0)
            arrow.lineTo(arrow.getCurrentPoint().getX() - 10,
                    arrow.getCurrentPoint().getY());
// Замкнуть треугольник стрелки
            arrow.closePath();
            canvas.draw(arrow); // Нарисовать стрелку
            canvas.fill(arrow); // Закрасить стрелку
// Нарисовать подпись к оси Y
// Определить, сколько места понадобится для надписи "y"
            Rectangle2D bounds = axisFont.getStringBounds("y", context);
            Point2D.Double labelPos = xyToPoint(0, maxY);
// Вывести надпись в точке с вычисленными координатами
            canvas.drawString("y", (float) labelPos.getX() + 10,
                    (float) (labelPos.getY() - bounds.getY()));
        }
// Определить, должна ли быть видна ось X на графике
        if (minY <= 0.0 && maxY >= 0.0) {
// Она должна быть видна, если верхняя граница показываемой области (maxX) >= 0.0,
// а нижняя (minY) <= 0.0
            canvas.draw(new Line2D.Double(xyToPoint(minX, 0),
                    xyToPoint(maxX, 0)));
// Стрелка оси X
            GeneralPath arrow = new GeneralPath();
// Установить начальную точку ломаной точно на правый конец оси X
            Point2D.Double lineEnd = xyToPoint(maxX, 0);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
// Вести верхний "скат" стрелки в точку с относительными координатами (-20,-5)
            arrow.lineTo(arrow.getCurrentPoint().getX() - 20,
                    arrow.getCurrentPoint().getY() - 5);
// Вести левую часть стрелки в точку с относительными координатами (0, 10)
            arrow.lineTo(arrow.getCurrentPoint().getX(),
                    arrow.getCurrentPoint().getY() + 10);
// Замкнуть треугольник стрелки
            arrow.closePath();
            canvas.draw(arrow); // Нарисовать стрелку
            canvas.fill(arrow); // Закрасить стрелку
// Нарисовать подпись к оси X
// Определить, сколько места понадобится для надписи "x"
            Rectangle2D bounds = axisFont.getStringBounds("x", context);
            Point2D.Double labelPos = xyToPoint(maxX, 0);
// Вывести надпись в точке с вычисленными координатами
            canvas.drawString("x", (float) (labelPos.getX() -
                    bounds.getWidth() - 10), (float) (labelPos.getY() + bounds.getY()));
        }
    }

    protected Point2D.Double xyToPoint(double x, double y) {
// Вычисляем смещение X от самой левой точки (minX)
        double deltaX = x - minX;
// Вычисляем смещение Y от точки верхней точки (maxY)
        double deltaY = maxY - y;
        return new Point2D.Double(deltaX * scale, deltaY * scale);
    }

    protected Point2D.Double shiftPoint(Point2D.Double src, double deltaX, double deltaY) {
        Point2D.Double dest = new Point2D.Double();
        dest.setLocation(src.getX() + deltaX, src.getY() + deltaY);
        return dest;

    }

    public void setAntiClockRotate(boolean antiClockRotate) {
        this.antiClockRotate = antiClockRotate;
        repaint();
    }

    public boolean isAntiClockRotate() {
        return antiClockRotate;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        InitialMouseX=e.getX();
        InitialMouseY=e.getY();
        mousePressed=true;
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        EndMouseX=e.getX();
        EndMouseY=e.getY();
        mouseReleased=true;
        repaint();
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        mouseXto=e.getX();
        mouseYto=e.getY();
    }

    public void mouseMoved(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
        repaint();
    }
    public void ShowPointsCoordinates(Graphics2D canvas) {
        canvas.setPaint(Color.BLACK);
        canvas.setFont(cursor);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(3);
       for (Double[] point: graphicsData) {
            Point2D p = xyToPoint(point[0], point[1]);
            if ((mouseY<=(int)p.getY()+5)&&(mouseY>=(int)p.getY()-5)&&((mouseX<=(int)p.getX()+5)&&(mouseX>=(int)p.getX()-5))) {
                canvas.drawString("X=" + formatter.format(point[0]) + "; Y=" + formatter.format(point[1]), (float) mouseX, (float) mouseY);
                repaint();
            }
        }
    }
    public void Scaling(Graphics2D canvas) {
        canvas.setStroke(rectStroke);
        canvas.setColor(Color.BLUE);
        if (mousePressed) {
            repaint();
            canvas.drawRect((int) InitialMouseX, (int) InitialMouseY, (int) (mouseXto - InitialMouseX), (int) (mouseYto - InitialMouseY));
        }
        if (mouseReleased) {
            zoom = true;
            boolean flag = false;
            for (Double[] graphicsDatum : graphicsData) {
                Point2D.Double pos = xyToPoint(graphicsDatum[0], graphicsDatum[1]);
                if (pos.getX() >= InitialMouseX && pos.getY() >= InitialMouseY && !flag && pos.getX() <= EndMouseX && pos.getY() <= EndMouseY) {
                    Xmin = graphicsDatum[0];
                    Xmax = graphicsDatum[0];
                    Ymin = graphicsDatum[1];
                    Ymax = graphicsDatum[1];
                    flag = true;
                    continue;
                }
                if (pos.getX() >= InitialMouseX && pos.getY() >= InitialMouseY && flag && pos.getX() <= EndMouseX && pos.getY() <= EndMouseY) {
                    Xmax = graphicsDatum[0];
                    if (Ymin > graphicsDatum[1])
                        Ymin = graphicsDatum[1];
                    if (Ymax < graphicsDatum[1])
                        Ymax = graphicsDatum[1];
                }
            }
            mouseReleased=false;
            mousePressed=false;
        }
    }
}