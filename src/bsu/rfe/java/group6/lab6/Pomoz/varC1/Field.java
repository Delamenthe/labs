package bsu.rfe.java.group6.lab6.Pomoz.varC1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
@SuppressWarnings("serial")
public class Field extends JPanel implements MouseMotionListener, MouseListener {

    private double MouseFromX=0, MouseFromY=0;
    private double MouseToX=0,MouseToY=0;
    private boolean mousePressed=false, mouseDragged=false;
    private double x,y;
    private double X,Y;
    private int k = 0;
    public int radius;
    // Флаг приостановленности движения
    private boolean paused;
    private boolean inBall;
    // Динамический список скачущих мячей
    private ArrayList<BouncingBall> balls = new ArrayList<BouncingBall>(10);
    // Класс таймер отвечает за регулярную генерацию событий ActionEvent
// При создании его экземпляра используется анонимный класс,
// реализующий интерфейс ActionListener
    private Timer repaintTimer = new Timer(10, new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
// Задача обработчика события ActionEvent - перерисовка окна
            repaint();
        }
    });
    // Конструктор класса BouncingBall
    public Field() {
// Установить цвет заднего фона белым
        setBackground(Color.WHITE);
// Запустить таймер
        repaintTimer.start();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    // Унаследованный от JPanel метод перерисовки компонента
    public void paintComponent(Graphics g) {
// Вызвать версию метода, унаследованную от предка
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;
        ChangeDirection(canvas);
// Последовательно запросить прорисовку от всех мячей из списка
        for (BouncingBall ball: balls) {
            ball.paint(canvas);
        }
    }
    // Метод добавления нового мяча в список
    public void addBall() {
//Заключается в добавлении в список нового экземпляра BouncingBall
// Всю инициализацию положения, скорости, размера, цвета
// BouncingBall выполняет сам в конструкторе
        balls.add(new BouncingBall(this));
    }
    // Метод синхронизированный, т.е. только один поток может
// одновременно быть внутри
    public synchronized void pause() {
// Включить режим паузы
        paused = true;
    }
    // Метод синхронизированный, т.е. только один поток может
// одновременно быть внутри
    public synchronized void resume() {
// Выключить режим паузы
        paused = false;
// Будим все ожидающие продолжения потоки
        notifyAll();
    }
    // Синхронизированный метод проверки, может ли мяч двигаться
// (не включен ли режим паузы?)
    public synchronized void canMove(BouncingBall ball) throws
            InterruptedException {
        if (paused) {
// Если режим паузы включен, то поток, зашедший
// внутрь данного метода, засыпает
            wait();
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
            mousePressed = true;
            MouseFromX = e.getX();
            MouseFromY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        mouseDragged = false;
        mousePressed=false;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        mouseDragged=true;
        MouseToX = e.getX();
        MouseToY = e.getY();
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void ChangeDirection(Graphics2D canvas) {

        if (mousePressed) {
            for (BouncingBall ball : balls) {
                x = ball.getX();
                y = ball.getY();
                radius = ball.getRadius();
                if (MouseFromX <= x + radius && MouseFromX >= x - radius && MouseFromY <= y + radius && MouseFromY >= y - radius) {
                    canvas.drawString("Тык", (int) MouseFromX, (int) MouseFromY);
                    k = balls.indexOf(ball);
                    inBall = true;
                    break;
                }
            }
            if (mouseDragged && inBall) {
                X = balls.get(k).getX();
                Y = balls.get(k).getY();
                canvas.draw(new Line2D.Double(X, Y, MouseToX, MouseToY));
                canvas.fillOval((int)MouseToX, (int)MouseToY, 10,10);
            }else
                inBall=false;
        }
    }

}

