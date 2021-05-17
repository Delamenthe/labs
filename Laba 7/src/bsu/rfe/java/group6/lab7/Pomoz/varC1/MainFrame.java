package bsu.rfe.java.group6.lab7.Pomoz.varC1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.BoxLayout.*;
import java.lang.String;

public class MainFrame extends JFrame {
    private static final String FRAME_TITLE = "Клиент мгновенных сообщений";
    private static final int FRAME_MINIMUM_WIDTH = 1000;
    private static final int FRAME_MINIMUM_HEIGHT = 500;
    private static final int FROM_FIELD_DEFAULT_COLUMNS = 5;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 5;
    private static final int LARGE_GAP = 15;
    private static final int SERVER_PORT = 4567;
    private final JTextField textFieldFrom;
    private final JTextArea textAreaIncoming;
    private final JTextArea textAreaOutgoing;
    private  JTextArea textAreaConversation;
    private JLabel User;
    private String ip;
    private String name;

    public MainFrame() {

        super(FRAME_TITLE);
        setMinimumSize(
                new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
        // Центрирование окна
        final Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - getWidth()) / 2,
                (kit.getScreenSize().height - getHeight()) / 2);

        Font font = new Font("TimesRoman", Font.PLAIN, 16);

        textAreaIncoming = new JTextArea(INCOMING_AREA_DEFAULT_ROWS,1);
        textAreaConversation = new JTextArea(INCOMING_AREA_DEFAULT_ROWS,1);

        textAreaIncoming.setFont(font);
        textAreaConversation.setFont(font);

        final JScrollPane scrollPaneIncoming =
                new JScrollPane(textAreaIncoming);
        final JScrollPane scrollPaneConversation =
                new JScrollPane(textAreaConversation);

        final JTabbedPane tabbedPane = new JTabbedPane();
//Считывание пользователей из файла

        ArrayList<String> users = new ArrayList<>();
        ArrayList<String> passwords = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader("DataBase.txt"))) {
            String line;
            Boolean flag = true;
            int i=0;
            while (((line = in.readLine()) != null)) {
                if(flag){
                    users.add(line);
                    textAreaIncoming.append("  " + ++i +". " + line+"\n");
                    flag=false;
                }else{
                    passwords.add(line);
                    flag=true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textAreaConversation.setText("");

        // Кнопки входа и выхода из аккаунта
        JButton enterButton = new JButton("Войти в аккаунт");
        JButton exitButton = new JButton("Выйти из аккаунта");
        User = new JLabel();
        JTextArea welcome = new JTextArea("\n\nДля того чтобы начать диалог введите имя пользователя,\n" +
                "с которым желаете пообщаться, в поле поиска слева");
        welcome.setFont(font);


        enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog("<html><h2>Введите логин");
               User.setText("");
                if (users.contains(username)){
                    int k = users.indexOf(username);
                    Boolean flag=true;
                    while(flag){
                    String password = JOptionPane.showInputDialog("<html><h2>Введите пароль");
                    if(passwords.get(k).equals(password)){
                        User.setText(username);
                        tabbedPane.removeAll();
                        tabbedPane.add("Добро пожаловать, "+ User.getText(),welcome);
                    flag=false;
                    }
                    else{
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Неверный пароль", "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    }

                } else
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Пользователя с именем "+username+" не существует", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);

            }
        });

        JTextArea enterText = new JTextArea("\nНевозможно добавить новый диалог\n\n" +
                "Пожалуста, авторизуйтесь\n\n" +
                "-----------------------------------------------------------------------------------------\n"+
                "Логин - имя пользователя \n(список существующих пользователей приведен слева)\n\n" +
                "Пароль - IP адресс пользователя \n(127.0.0.номер пользователя + 1)");
        enterText.setFont(font);


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User.setText("");
                tabbedPane.removeAll();
                tabbedPane.addTab("Диалоги",enterText);
            }
       });

        tabbedPane.addTab("Диалоги",enterText);

        final JLabel labelFrom = new JLabel("Введите имя получателя: ");
        textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);
        final JButton findButton = new JButton("Поиск");
        final JPanel findPanel = new JPanel();
        findPanel.setBorder(
                BorderFactory.createTitledBorder("Поиск"));
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (users.contains(textFieldFrom.getText()))
                    if(!User.getText().isEmpty()){
                    name=textFieldFrom.getText();
                    ip=passwords.get(users.indexOf(textFieldFrom.getText()));
                    textAreaConversation = new JTextArea();
                    final JScrollPane scrollPaneConversation =
                            new JScrollPane(textAreaConversation);
                    tabbedPane.remove(welcome);
                    tabbedPane.addTab(textFieldFrom.getText(),scrollPaneConversation);
                    textFieldFrom.setText("");
                } else {
                        textFieldFrom.setText("");
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Войдите в аккаунт", "Ошибка",
                                JOptionPane.ERROR_MESSAGE);
                    }else {
                    textFieldFrom.setText("");
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Пользователя с именем " + textFieldFrom.getText() + " не существует", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        final GroupLayout layout2 = new GroupLayout(findPanel);
        findPanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldFrom)
                                .addGap(LARGE_GAP)
                                .addComponent(findButton))
                .addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFrom)
                        .addComponent(textFieldFrom)
                        .addComponent(findButton))
                .addGap(MEDIUM_GAP)
                .addContainerGap());

        textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);
        // Контейнер, обеспечивающий прокрутку текстовой области
        final JScrollPane scrollPaneOutgoing =
                new JScrollPane(textAreaOutgoing);
        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(
                BorderFactory.createTitledBorder("Сообщение"));
// Кнопка отправки сообщения
        final JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMsg(ip,name);
            }
        });

        final GroupLayout layout3 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout3);
        layout3.setHorizontalGroup(layout3.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout3.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(scrollPaneOutgoing)
                        .addComponent(sendButton))
                .addContainerGap());
        layout3.setVerticalGroup(layout3.createSequentialGroup()
                .addContainerGap()
                .addGap(MEDIUM_GAP)
                .addComponent(scrollPaneOutgoing)
                .addGap(MEDIUM_GAP)
                .addComponent(sendButton)
                .addContainerGap());

        JPanel enterPanel = new JPanel();
        enterPanel.setAlignmentX(30);
        enterPanel.add(enterButton);
        enterPanel.add(exitButton);
        enterPanel.add(User);

// Компоновка элементов фрейма
        JPanel pane = new JPanel();

        GridLayout gridLayout = new GridLayout(1,2);
        gridLayout.setHgap(20);

        Container functional = new Container();
        functional.setLayout(new BoxLayout(functional, PAGE_AXIS));
        functional.add(enterPanel);
        functional.add(scrollPaneIncoming);
        functional.add(findPanel);
        functional.add(messagePanel);

        pane.add(functional,gridLayout);
        pane.add(tabbedPane,gridLayout);

        pane.setLayout(gridLayout);
        setContentPane(pane);

        new Thread(() -> {
            try {
                final ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                while (true) {
                    final Socket socket = serverSocket.accept();
                    BufferedReader reader =
                            new BufferedReader(
                                    new InputStreamReader(
                                            socket.getInputStream()));
                    String senderName =reader.readLine();
                    String message =reader.readLine();

                    textAreaConversation.append(senderName +" -> " + name +" : "+ message + "\n");

                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в работе сервера", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public void sendMsg(String ip, String name) {
        try {
            String SenderName = User.getText();
            String message = textAreaOutgoing.getText();
            if(User.getText().isEmpty()){
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Войдите в аккаунт", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (SenderName.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Выберите собеседника", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Введите текст сообщения", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Socket socket = new Socket(ip, SERVER_PORT)) {

                BufferedWriter writer =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        socket.getOutputStream()));
                writer.write(SenderName);
                writer.newLine();
                writer.write(message);
                writer.flush();
                socket.close();
                textAreaOutgoing.setText("");
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Не удалось отправить сообщение: узел адресат не найден",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Не удалось отправить сообщение", "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}