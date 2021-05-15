package bsu.rfe.java.group6.lab7.Pomoz.varC1;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import javax.swing.*;

public class MainFrame extends JFrame {
    private static final String FRAME_TITLE = "Клиент мгновенных сообщений";
    private static final int FRAME_MINIMUM_WIDTH = 1000;
    private static final int FRAME_MINIMUM_HEIGHT = 500;
    private static final int FROM_FIELD_DEFAULT_COLUMNS = 5;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;
    private static final int SERVER_PORT = 4567;
    private final JTextField textFieldFrom;
//    private final JTextField textFieldTo;
    private final JTextArea textAreaIncoming;
    private final JTextArea textAreaOutgoing;

    public MainFrame() {
        super(FRAME_TITLE);
        setMinimumSize(
                new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
        // Центрирование окна
        final Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - getWidth()) / 2,
                (kit.getScreenSize().height - getHeight()) / 2);
// Текстовая область для отображения полученных сообщений
        textAreaIncoming = new JTextArea(INCOMING_AREA_DEFAULT_ROWS,1);
        textAreaIncoming.setEditable(false);
//Считывание пользователей из файла
        textAreaIncoming.append("Зарегестрированные пользователи: \n\n");

        try (BufferedReader in = new BufferedReader(new FileReader("DataBase.txt"))) {
            String line;
            Boolean flag = true;
            while (((line = in.readLine()) != null)) {
                if(flag){
                 textAreaIncoming.append(line+"\n");
                  flag=false;
                }else{
                    flag=true;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

// Контейнер, обеспечивающий прокрутку текстовой области
        final JScrollPane scrollPaneIncoming =
                new JScrollPane(textAreaIncoming);

        final JTabbedPane tabbedPane = new JTabbedPane();

        final JLabel labelFrom = new JLabel("Введите имя получателя: ");
        textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);
        final JButton findButton = new JButton("Поиск");
        final JPanel findPanel = new JPanel();
        findPanel.setBorder(
                BorderFactory.createTitledBorder("Поиск"));
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader in = new BufferedReader(new FileReader("DataBase.txt"))) {
                    String line;
                    Boolean flag = true;
                    while (((line = in.readLine()) != null)) {
                        if(flag){
                            if(textFieldFrom.getText().equals(line)) {
                                tabbedPane.addTab(line, new JPanel());
                                System.out.println(line);
                            }
                            flag=false;
                        }else{
                            flag=true;
                        }

                    }
                } catch (FileNotFoundException x) {
                    x.printStackTrace();
                } catch (IOException x) {
                    throw new RuntimeException(x);
                }
            }
        });

        final GroupLayout layout2 = new GroupLayout(findPanel);
        findPanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout2.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldFrom)
                                .addGap(LARGE_GAP))
                        .addComponent(findButton))
                .addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFrom)
                        .addComponent(textFieldFrom))
                .addGap(MEDIUM_GAP)
                .addComponent(findButton)
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
            @Override
            public void actionPerformed(ActionEvent e) {
                StartClient();
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
// Подписи полей
      /*  final JLabel labelFrom = new JLabel("Подпись");
        final JLabel labelTo = new JLabel("Получатель");
// Поля ввода имени пользователя и адреса получателя
        textFieldFrom = new JTextField(FROM_FIELD_DEFAULT_COLUMNS);
        textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);
// Текстовая область для ввода сообщения
        textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);
// Контейнер, обеспечивающий прокрутку текстовой области
        final JScrollPane scrollPaneOutgoing =
                new JScrollPane(textAreaOutgoing);
// Панель ввода сообщения
        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(
                BorderFactory.createTitledBorder("Сообщение"));
// Кнопка отправки сообщения
        final JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartClient();
            }
        });

// Компоновка элементов панели "Сообщение"
        final GroupLayout layout2 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(layout2.createSequentialGroup()
                                .addComponent(labelFrom)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldFrom)
                                .addGap(LARGE_GAP)
                                .addComponent(labelTo)
                                .addGap(SMALL_GAP)
                                .addComponent(textFieldTo))
                        .addComponent(scrollPaneOutgoing)
                        .addComponent(sendButton))
                .addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelFrom)
                        .addComponent(textFieldFrom)
                        .addComponent(labelTo)
                        .addComponent(textFieldTo))
                .addGap(MEDIUM_GAP)
                .addComponent(scrollPaneOutgoing)
                .addGap(MEDIUM_GAP)
                .addComponent(sendButton)
                .addContainerGap());*/
// Компоновка элементов фрейма
        final GroupLayout layout1 = new GroupLayout(getContentPane());
        setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout1.createParallelGroup()
                        .addComponent(scrollPaneIncoming)
                        .addComponent(findPanel)
                        .addComponent(messagePanel)
                )
                .addContainerGap()
                .addGap(MEDIUM_GAP)
                .addComponent(tabbedPane)
                .addContainerGap()
        );
        layout1.setVerticalGroup(layout1.createParallelGroup()
                .addGroup(layout1.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tabbedPane)
                        .addContainerGap()
                )
                .addGroup(layout1.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPaneIncoming)
                    .addGap(MEDIUM_GAP)
                    .addContainerGap()
                    .addComponent(findPanel)
                    .addGap(MEDIUM_GAP)
                    .addContainerGap()
                    .addComponent(tabbedPane)
                .addComponent(messagePanel)));
//Создание и запуск потока-обработчика запросов
  /*  new Thread(new Runnable() {
            public void run() {
                try {
                    final ServerSocket serverSocket =
                            new ServerSocket(SERVER_PORT);
                    while (!Thread.interrupted()) {
                        final Socket socket = serverSocket.accept();
                        final DataInputStream in = new DataInputStream(
                                socket.getInputStream());
// Читаем имя отправителя
                        final String senderName = in.readUTF();
// Читаем сообщение
                        final String message = in.readUTF();
// Закрываем соединение
                        socket.close();
// Выделяем IP-адрес
                        final String address =
                                ((InetSocketAddress) socket
                                        .getRemoteSocketAddress())
                                        .getAddress()
                                        .getHostAddress();
// Выводим сообщение в текстовую область
                        textAreaIncoming.append(senderName +
                                " (" + address + "): " +
                                message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в работе сервера", "Ошибка",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();*/

        new Thread(() -> {
            try(ServerSocket server = new ServerSocket(SERVER_PORT))
            {
                System.out.println("Server started");
                while(!Thread.interrupted()) {
                    Phone phone = new Phone(server);
                    final String request = phone.readLine();
                    System.out.println("Request: " + request);
                    textAreaIncoming.append(request + "\n");
                    try {phone.close(); } catch (IOException e) {  }
                }
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }).start();
    }
   /* private void sendMessage() {
        try {
// Получаем необходимые параметры
            final String senderName = textFieldFrom.getText();
            final String destinationAddress = textFieldTo.getText();
            final String message = textAreaOutgoing.getText();
// Убеждаемся, что поля не пустые
            if (senderName.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите имя отправителя", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (destinationAddress.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите адрес узла-получателя", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Введите текст сообщения", "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

    }*/

private void StartClient(){
    // Получаем необходимые параметры
/*    final String senderName = textFieldFrom.getText();
    final String destinationAddress = textFieldTo.getText();
    final String message = textAreaOutgoing.getText();
// Убеждаемся, что поля не пустые
    if (senderName.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Введите имя отправителя", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (destinationAddress.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Введите адрес узла-получателя", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (message.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Введите текст сообщения", "Ошибка",
                JOptionPane.ERROR_MESSAGE);
        return;
    }*/
    try(Phone phone = new Phone("127.0.0.1",SERVER_PORT))
    {
        System.out.println("Connected to server");
        phone.writeLine("Hello");

// Помещаем сообщения в текстовую область вывода
 /*       textAreaIncoming.append("Я -> " + destinationAddress + ": "
                + message + "\n");
// Очищаем текстовую область ввода сообщения
        textAreaOutgoing.setText("");*/
    } catch (UnknownHostException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(MainFrame.this,
                "Не удалось отправить сообщение: узел-адресат не найден",
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