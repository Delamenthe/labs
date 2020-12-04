package bsu.rfe.java.group6.lab5.Pomoz.varC16;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.DataOutputStream;


@SuppressWarnings("serial")

public class MainFrame  extends JFrame{
    // Начальные размеры окна приложения
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    // Объект диалогового окна для выбора файлов
    private JFileChooser fileChooser = null;
    final private JMenuItem saveToGraphicsMenuItem;
    // Пункты меню
    final private JCheckBoxMenuItem showAxisMenuItem;
    final private JCheckBoxMenuItem showMarkersMenuItem;
    final private JMenuItem shapeRotateAntiClockItem;
    // Компонент-отображатель графика
    final private GraphicsDisplay display = new GraphicsDisplay();

    // Флаг, указывающий на загруженность данных графика
    private boolean fileLoaded = false;

    public MainFrame() {
        super("Построение графиков функций на основе заранее подготовленных файлов");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        Action openGraphicsAction = new AbstractAction("Открыть файл с графиком") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
                    openGraphics(fileChooser.getSelectedFile());
            }
        };
        fileMenu.add(openGraphicsAction);
        JMenu graphicsMenu = new JMenu("График");
        menuBar.add(graphicsMenu);
        Action showAxisAction = new AbstractAction("Показывать оси координат") {
            public void actionPerformed(ActionEvent event) {
                display.setShowAxis(showAxisMenuItem.isSelected());
            }
        };
        showAxisMenuItem = new JCheckBoxMenuItem(showAxisAction);
        graphicsMenu.add(showAxisMenuItem);
        showAxisMenuItem.setSelected(true);
        Action showMarkersAction = new AbstractAction("Показывать маркеры точек") {
            public void actionPerformed(ActionEvent event) {
                display.setShowMarkers(showMarkersMenuItem.isSelected());
            }
        };
        showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
        graphicsMenu.add(showMarkersMenuItem);
        showMarkersMenuItem.setSelected(true);
        graphicsMenu.addMenuListener(new GraphicsMenuListener());
        getContentPane().add(display, BorderLayout.CENTER);

        Action rotatesShapeAntiClockAction = new AbstractAction("Поворот графика на 90 градусов") {
            public void actionPerformed(ActionEvent e) {
                display.setAntiClockRotate(!display.isAntiClockRotate());
            }
        };
        shapeRotateAntiClockItem = new JCheckBoxMenuItem(rotatesShapeAntiClockAction);
        graphicsMenu.add(shapeRotateAntiClockItem);
        shapeRotateAntiClockItem.setEnabled(false);
        graphicsMenu.addSeparator();

        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }
                if (MainFrame.this.fileChooser.showSaveDialog(MainFrame.this) == 0) {
                }
                MainFrame.this.saveToGraphicsFile(MainFrame.this.fileChooser.getSelectedFile());
            }
        };
        this.saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);


    }

    protected void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new
                    FileInputStream(selectedFile));
            Double[][] graphicsData = new
                    Double[in.available()/(Double.SIZE/8)/2][];
            int i = 0;
            while (in.available()>0) {
                double x = in.readDouble();
                double y = in.readDouble();
                graphicsData[i++] = new Double[] {x, y};
            }
            if ( graphicsData.length>0) {
                fileLoaded = true;
                display.showGraphics(graphicsData);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, "Указанный файл не найден",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainFrame.this, "Ошибка чтения координат точек из файла", "Ошибка загрузки данных",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    protected void saveToGraphicsFile (File selectedFile) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));

            for(int i = 0; i < display.getLength(); ++i) {
                out.writeDouble(this.display.getValueAt(i, 0));
                out.writeDouble(this.display.getValueAt(i, 1));
            }

            out.close();
        } catch (Exception var4) {
            System.out.println("Ошибка записи в файл!");
        }

    }

    public static void main(String[] args) {
// Создать и показать экземпляр главного окна приложения
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private class GraphicsMenuListener implements MenuListener {

        public void menuSelected(MenuEvent e) {
// Доступность или недоступность элементов меню "График" определяется загруженностью данных
            showAxisMenuItem.setEnabled(fileLoaded);
            showMarkersMenuItem.setEnabled(fileLoaded);
            shapeRotateAntiClockItem.setEnabled(fileLoaded);
            saveToGraphicsMenuItem.setEnabled((fileLoaded));
        }

        public void menuDeselected(MenuEvent e) {
        }
        // Обработчик, вызываемый в случае отмены выбора пункта меню (очень редкая ситуация)
        public void menuCanceled(MenuEvent e) {
        }
    }
}

