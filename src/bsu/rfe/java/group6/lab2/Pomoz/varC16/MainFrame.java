package bsu.rfe.java.group6.lab2.Pomoz.varC16;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")

public class MainFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 420;


    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;

    private JLabel imageLabel;

    private JTextField memoryTextField;
    private JTextField resultFieldText;

    //Группа радио-кнопок для обеспечения уникальности выделения в группе
    private ButtonGroup radioButtons = new ButtonGroup();
    private ButtonGroup radioMemoryButtons = new ButtonGroup();

    //Контейнер для отображения радио-кнопок
    private Box hBoxFormulaType = Box.createHorizontalBox();
    private Box hBoxMemoryType = Box.createHorizontalBox();

    //Переменная, указывающая, какая из формул является  активной  в  данный  момент
    private int formulaId = 1;
    private int memoryId= 1;

    private Double mem1 = new Double(0);
    private Double mem2 = new Double(0);
    private Double mem3 = new Double(0);

    //Формула №1 для рассчѐта
    public Double calculate1(Double x, Double y, Double z) {
        if (y == 0)	{
            JOptionPane.showMessageDialog(MainFrame.this,
                    "y не может равняться нулю",
                    "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }

        return (Math.sin(Math.log(y)+Math.sin(Math.PI*y*y))*Math.pow(x*x+Math.sin(z)+Math.exp(Math.cos(z)),1/4));
    }

    //Формула №2 для рассчѐта
    public Double calculate2(Double x, Double y, Double z) {
        if (x == 0)	{
            JOptionPane.showMessageDialog(MainFrame.this,
                    "x не может равняться нулю", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }
        if (y == -1)	{
            JOptionPane.showMessageDialog(MainFrame.this,
                    "y не может равняться -1", "" +
                            "Ошибка ввода", JOptionPane.WARNING_MESSAGE);
            return 0.0;
        }

        return (Math.pow(Math.cos(Math.exp(x))+Math.log(Math.pow((1+y),2))+Math.sqrt(Math.exp(Math.cos(x))+Math.pow(Math.sin(Math.PI*z),2))+Math.sqrt(1/x)+Math.cos(Math.pow(y,2)),Math.sin(z)));
    }

//Вспомогательный метод для добавления кнопок на панель
//buttonName – текст рядом с кнопкой, formulaId – идентификатор формулы
    private void addRadioButton(String buttonName, final int formulaId) {
        //Создать экземпляр радио-кнопки с заданным текстом
        JRadioButton button = new JRadioButton(buttonName);
        //Определить и зарегистрировать обработчик
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //Который будет устанавливать идентификатор выбранной
                //формулы в классе Formula равным formulaId
                MainFrame.this.formulaId = formulaId;
                if (formulaId == 1)	imageLabel.setIcon(new ImageIcon(MainFrame.class.getResource("Formula_1.png")));
                if (formulaId == 2) imageLabel.setIcon(new ImageIcon(MainFrame.class.getResource("Formula_2.png")));
            }
        });
        //Добавить радио-кнопку в группу
        radioButtons.add(button);
        //Добавить радио-кнопку в контейнер
        //Для этого ссылка на контейнер сделана полем данных класса
        hBoxFormulaType.add(button);
    }

    private void addMemoryRadioButton (String buttonName, final int memoryId)	{
        JRadioButton button = new JRadioButton(buttonName);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event)	{
                MainFrame.this.memoryId = memoryId;
                if (memoryId == 1)	memoryTextField.setText(mem1.toString());
                if (memoryId == 2)	memoryTextField.setText(mem2.toString());
                if (memoryId == 3)	memoryTextField.setText(mem3.toString());
            }
        });

        radioMemoryButtons.add(button);
        hBoxMemoryType.add(button);
    }


    //Конструктор класса
    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
// Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

//загрузка изображения и установка его в качестве иконки
        Image img = kit.getImage("icon.gif");
        setIconImage(img);

//Добавить «клей» C1-H1 с левой стороны
        hBoxFormulaType.add(Box.createHorizontalGlue());
//Создать радио-кнопку для формулы 1
        addRadioButton("Формула 1", 1);
//Создать радио-кнопку для формулы 2
        addRadioButton("Формула 2", 2);
//Установить выделенной 1-ую кнопку из группы
        radioButtons.setSelected(radioButtons.getElements().nextElement().getModel(), true);
//Добавить «клей» C1-H2 с правой стороны
        hBoxFormulaType.add(Box.createHorizontalGlue());


        Box hBoxFormulaImage = Box.createHorizontalBox();
        hBoxFormulaImage.add(Box.createHorizontalGlue());
        imageLabel = new JLabel(new ImageIcon(MainFrame.class.getResource("Formula_1.png")));
        hBoxFormulaImage.add(imageLabel);
        hBoxFormulaImage.add(Box.createHorizontalGlue());



        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

//Создать контейнер «коробка с горизонтальной укладкой»
        Box hBoxVariables = Box.createHorizontalBox();

//Добавить в контейнер ряд объектов:
//Добавить «клей» C2-H1 – для максимального удаления от левого края
        hBoxVariables.add(Box.createHorizontalGlue());
//Добавить подпись для переменной Х
        hBoxVariables.add(labelForX);
//Добавить «распорку» C2-H2 шириной 10 пикселов для отступа между
//надписью и текстовым полем для ввода значения X
        hBoxVariables.add(Box.createHorizontalStrut(10));
//Добавить само текстовое поле для ввода Х
        hBoxVariables.add(textFieldX);
//Добавить «распорку» C2-H3 шириной 50 пикселов для отступа между
//текстовым полем для ввода X и подписью для Y
        hBoxVariables.add(Box.createHorizontalStrut(50));
//Добавить подпись для переменной Y
        hBoxVariables.add(labelForY);
//Добавить «распорку» C2-H4 шириной 10 пикселов для отступа между
//надписью и текстовым полем для ввода значения Y
        hBoxVariables.add(Box.createHorizontalStrut(10));
//Добавить само текстовое поле для ввода Y
        hBoxVariables.add(textFieldY);
//Добавить «распорку» C2-H5 шириной 50 пикселов для отступа между
//текстовым полем для ввода Y и подписью для Z
        hBoxVariables.add(Box.createHorizontalStrut(50));
//Добавить подпись для переменной Z
        hBoxVariables.add(labelForZ);
//Добавить «распорку» C2-H6 шириной 10 пикселов для отступа между
//надписью и текстовым полем для ввода значения Z
        hBoxVariables.add(Box.createHorizontalStrut(10));
//Добавить текстовое поле для ввода Z
        hBoxVariables.add(textFieldZ);
//Добавить «клей» C2-H5 для максимального удаления от правого края
        hBoxVariables.add(Box.createHorizontalGlue());



//Создать подпись для поля с результатом
        JLabel labelForResult = new JLabel("Результат:");
// Создать текстовое поле для вывода результата, начальное значение - 0
        resultFieldText = new JTextField("", 15);
//Установить макс размер = желаемому для предотвращения масштабирования
        resultFieldText.setMaximumSize( resultFieldText.getPreferredSize());


// Создать контейнер «коробка с горизонтальной укладкой»
        Box hBoxResult = Box.createHorizontalBox();
// Добавить в контейнер ряд объектов
// Добавить «клей» C3-H1 для отступа от левого края
        hBoxResult.add(Box.createHorizontalGlue());
//Добавить подпись для результата
        hBoxResult.add(labelForResult);
//Добавить «распорку» C3-H2 в 10 пикселов между подписью и полем результата
        hBoxResult.add(Box.createHorizontalStrut(10));
//Добавить текстовое поле для вывода результата
        hBoxResult.add(resultFieldText);
//Добавить «клей» C3-H3 справа
        hBoxResult.add(Box.createHorizontalGlue());
//Задать рамку для контейнера
        hBoxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));


// Создать область для кнопок
// Создать кнопку «Вычислить»
        JButton buttonCalc = new JButton("Вычислить");
// Определить и зарегистрировать обработчик нажатия на кнопку
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                //Преобразование введенных строк в числа с плавающей точкой может
                //спровоцировать исключительную ситуацию при неправильном формате чисел,
                //поэтому необходим блок try-catch
                try {
                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;

                    if (formulaId==1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    resultFieldText.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой"
                            , "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        hBoxMemoryType.add(Box.createHorizontalGlue());
        addMemoryRadioButton("Память 1", 1);
        addMemoryRadioButton("Память 2", 2);
        addMemoryRadioButton("Память 3", 3);
        // по умолчанию на первую память
        radioMemoryButtons.setSelected(radioMemoryButtons.getElements().nextElement().getModel(), true);
        //Добавить «клей» C1-H2 с правой стороны
        hBoxMemoryType.add(Box.createHorizontalGlue());
        //Задать рамку для коробки с помощью класса BorderFactory
        hBoxMemoryType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

//Создать кнопку «Очистить поля»
        JButton buttonReset = new JButton("Очистить поля");
// Определить и зарегистрировать обработчик нажатия на кнопку
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("");
                textFieldY.setText("");
                textFieldZ.setText("");
                resultFieldText.setText("");
                JOptionPane.showMessageDialog(MainFrame.this, "Поля отчищены!","Очистка", JOptionPane.PLAIN_MESSAGE);
            }
        });

        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(new ActionListener()	{
            public void actionPerformed(ActionEvent event) {

                if (memoryId == 1)	mem1 = (double) 0;
                if (memoryId == 2)	mem2 = (double) 0;
                if (memoryId == 3)	mem3 = (double) 0;
                memoryTextField.setText("0.0");
            }
        });

        memoryTextField = new JTextField("0.0", 15);
        memoryTextField.setMaximumSize(memoryTextField.getPreferredSize());

        Box hBoxMemoryField = Box.createHorizontalBox();
        hBoxMemoryField.add(Box.createHorizontalGlue());
        hBoxMemoryField.add(memoryTextField);
        hBoxMemoryField.add(Box.createHorizontalGlue());


        JButton buttonMp = new JButton("M+");
        buttonMp.addActionListener(new ActionListener(){


            public void actionPerformed(ActionEvent arg0) {
                try{
                    Double result = Double.parseDouble(resultFieldText.getText());

                    if (memoryId == 1) 	{mem1 += result;memoryTextField.setText(mem1.toString());}
                    if (memoryId == 2)	{mem2 += result;memoryTextField.setText(mem2.toString());}
                    if (memoryId == 3)	{mem3 += result;memoryTextField.setText(mem3.toString());}

                }catch (NumberFormatException ex)
                { JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой", "" +
                                "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        Box hBoxControlButtons = Box.createHorizontalBox();
        hBoxControlButtons.add(Box.createHorizontalGlue());
        hBoxControlButtons.add(buttonMC);
        hBoxControlButtons.add(Box.createHorizontalStrut(30));
        hBoxControlButtons.add(buttonMp);
        hBoxControlButtons.add(Box.createHorizontalGlue());

//Создать коробку с горизонтальной укладкой
        Box hBoxButtons = Box.createHorizontalBox();
//Добавить «клей» C4-H1 с левой стороны
        hBoxButtons.add(Box.createHorizontalGlue());
//Добавить кнопку «Вычислить» в компоновку
        hBoxButtons.add(buttonCalc);
//Добавить распорку в 30 пикселов C4-H2 между кнопками
        hBoxButtons.add(Box.createHorizontalStrut(30));
//Добавить кнопку «Очистить поля» в компоновку
        hBoxButtons.add(buttonReset);
//Добавить «клей» C4-H3 с правой стороны
        hBoxButtons.add(Box.createHorizontalGlue());
//Задать рамку для контейнера
        hBoxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));


// Связать области воедино в компоновке BoxLayout
//Создать контейнер «коробка с вертикальной укладкой»
        Box contentBox = Box.createVerticalBox();
//Добавить «клей» V1 сверху
        contentBox.add(Box.createVerticalGlue());
// Добавить контейнер с выбором формулы
        contentBox.add(hBoxFormulaType);
// картинка формулы
        contentBox.add(hBoxFormulaImage);
//Добавить контейнер с переменными
        contentBox.add(hBoxVariables);
//Добавить контейнер с результатом вычислений
        contentBox.add(hBoxResult);
//Добавить контейнер с кнопками
        contentBox.add(hBoxButtons);
// Добавить контейнер с выбором памяти
        contentBox.add(hBoxMemoryType);
//Добавить результат
        contentBox.add(hBoxControlButtons);
        contentBox.add(hBoxMemoryField);
//Добавить «клей» V2 снизу
        contentBox.add(Box.createVerticalGlue());
//Установить «вертикальную коробку» в область содержания главного окна
        getContentPane().add(contentBox, BorderLayout.CENTER);

    }

    //Главный метод класса
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}