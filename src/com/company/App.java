package com.company;

import javax.swing.*;
import java.awt.*;

public class App {

    private final String info = "Криптосистема на основе метода Полига-Хелмана by Max Trunnikov (@maxonfjvipon) \n" +
        "N - простое 512-ти битное число (должно оставаться секретным)\n" +
        "E - ключ для шифрования (можно открыто передавать)\n" +
        "D - ключ для дешифрования (должен оставаться секретным)\n" +
        "Е подбирается исходя из следующего условия: E(mod(N-1)) != 0\n" +
        "D подбирается исходя из следующего условия: D*E(mod(N-1)) == 1\n" +
        "Сообщение шифруется блоками по 16 байт. C - зашифрованный блок, M - расшифрованный блок\n" +
        "C = (M^E)(mod N), M = (C^D)(mod N)";

    private final int NKEYLABELROW = 3;

    private final int NKEYLABELCOL = 45;

    private final int TAMARGIN = 5;

    private final int WIDTH = 1200, HEIGHT = 605;

    private final int MSGTAROW = 6;

    private PHEncoder encoder;

    public App() {
        // INFO TEXT AREA
        JTextArea infoTA = new JTextArea();
        infoTA.setLineWrap(true);
        infoTA.setWrapStyleWord(true);
        infoTA.setBackground(new Color(240, 240, 240));
        infoTA.setEditable(false);
        infoTA.setMargin(new Insets(TAMARGIN, TAMARGIN, TAMARGIN, TAMARGIN));
        infoTA.setText(info);

        // INFO PANEL
        JPanel infoPnl = new JPanel(new BorderLayout());
        infoPnl.add(infoTA, BorderLayout.CENTER);

        // SENDER LABEL
        JLabel snLbl = new JLabel("Отправитель");
        snLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // SENDER N LABEL
        JLabel snNLabel = new JLabel("N: ");

        // SENDER N TEXT AREA
        JTextArea snNTA = new JTextArea(NKEYLABELROW, NKEYLABELCOL);
        snNTA.setLineWrap(true);
        snNTA.setWrapStyleWord(true);

        // SENDER N PANEL
        JPanel snNPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        snNPnl.add(snNLabel);
        snNPnl.add(new JScrollPane(snNTA));

        // SENDER KEY LABEL
        JLabel snKeyLbl = new JLabel("E: ");

        // SENDER KEY TEXT AREA
        JTextArea snKeyTA = new JTextArea(NKEYLABELROW, NKEYLABELCOL);
        snKeyTA.setLineWrap(true);
        snKeyTA.setWrapStyleWord(true);

        // SENDER KEY PANEL
        JPanel snKeyPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        snKeyPnl.add(snKeyLbl);
        snKeyPnl.add(new JScrollPane(snKeyTA));

        // SENDER INFO PANEL
        JPanel snInfoPnl = new JPanel(new BorderLayout());
        snInfoPnl.add(snLbl, BorderLayout.NORTH);
        snInfoPnl.add(snNPnl, BorderLayout.CENTER);
        snInfoPnl.add(snKeyPnl, BorderLayout.SOUTH);

        // SENDER ORIGIN MESSAGE TEXT AREA
        JTextArea snOriginMessageTA = new JTextArea();
        snOriginMessageTA.setRows(MSGTAROW);
        snOriginMessageTA.setLineWrap(true);
//        snOriginMessageTA.setWrapStyleWord(true);
        snOriginMessageTA.setMargin(new Insets(TAMARGIN, TAMARGIN, TAMARGIN, TAMARGIN));
        snOriginMessageTA.setText("Hello world!");

        // ENCODE BUTTON
        JButton encodeButton = new JButton("Зашифровать");

        // SEND BUTTON
        JButton sendButton = new JButton("Отправить");

        // GENERATE BUTTON
        JButton generateButton = new JButton("Сгенерировать");

        // SENDER BUTTONS PANEL
        JPanel snButtonsPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        snButtonsPnl.add(generateButton);
        snButtonsPnl.add(encodeButton);
        snButtonsPnl.add(sendButton);

        // SENDER ENCODED MESSAGE TEXT AREA
        JTextArea snEncodedMessageTA = new JTextArea();
        snEncodedMessageTA.setRows(MSGTAROW);
        snEncodedMessageTA.setLineWrap(true);
        snEncodedMessageTA.setWrapStyleWord(true);
        snEncodedMessageTA.setMargin(new Insets(TAMARGIN, TAMARGIN, TAMARGIN, TAMARGIN));

        // SENDER MESSAGES PANEL
        JPanel snMessagesPnl = new JPanel(new BorderLayout());
        snMessagesPnl.add(new JScrollPane(snOriginMessageTA), BorderLayout.NORTH);
        snMessagesPnl.add(snButtonsPnl, BorderLayout.CENTER);
        snMessagesPnl.add(new JScrollPane(snEncodedMessageTA), BorderLayout.SOUTH);

        // SENDER PANEL
        JPanel snPnl = new JPanel(new BorderLayout());
        snPnl.add(snInfoPnl, BorderLayout.NORTH);
        snPnl.add(snMessagesPnl, BorderLayout.CENTER);

        // RECEIVER LABEL
        JLabel rcLbl = new JLabel("Получатель");
        rcLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // RECEIVER N LABEL
        JLabel rcNLabel = new JLabel("N: ");

        // RECEIVER N TEXT AREA
        JTextArea rcNTA = new JTextArea(NKEYLABELROW, NKEYLABELCOL);
        rcNTA.setLineWrap(true);
        rcNTA.setWrapStyleWord(true);

        // RECEIVER N PANEL
        JPanel rcNPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rcNPnl.add(rcNLabel);
        rcNPnl.add(new JScrollPane(rcNTA));

        // RECEIVER KEY LABEL
        JLabel rcKeyLbl = new JLabel("D: ");

        // RECEIVER KEY TEXT AREA
        JTextArea rcKeyTA = new JTextArea(NKEYLABELROW, NKEYLABELCOL);
        rcKeyTA.setLineWrap(true);
        rcKeyTA.setWrapStyleWord(true);

        // RECEIVER KEY PANEL
        JPanel rcKeyPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rcKeyPnl.add(rcKeyLbl);
        rcKeyPnl.add(new JScrollPane(rcKeyTA));

        // RECEIVER INFO PANEL
        JPanel rcInfoPnl = new JPanel(new BorderLayout());
        rcInfoPnl.add(rcLbl, BorderLayout.NORTH);
        rcInfoPnl.add(rcNPnl, BorderLayout.CENTER);
        rcInfoPnl.add(rcKeyPnl, BorderLayout.SOUTH);

        // RECEIVER ENCODED MESSAGE TEXT AREA
        JTextArea rcEncodedMessageTA = new JTextArea();
        rcEncodedMessageTA.setRows(MSGTAROW);
        rcEncodedMessageTA.setLineWrap(true);
        rcEncodedMessageTA.setWrapStyleWord(true);
        rcEncodedMessageTA.setMargin(new Insets(TAMARGIN, TAMARGIN, TAMARGIN, TAMARGIN));

        // DECODE BUTTON
        JButton decodeButton = new JButton("Расшифровать");

        // RECEIVER BUTTONS PANEL
        JPanel rcButtonsPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rcButtonsPnl.add(decodeButton);

        // RECEIVER DECODED MESSAGE TEXT AREA
        JTextArea rcDecodedMessageTA = new JTextArea();
        rcDecodedMessageTA.setRows(MSGTAROW);
        rcDecodedMessageTA.setLineWrap(true);
        rcDecodedMessageTA.setWrapStyleWord(true);
        rcDecodedMessageTA.setMargin(new Insets(TAMARGIN, TAMARGIN, TAMARGIN, TAMARGIN));

        // RECEIVER MESSAGES PANEL
        JPanel rcMessagesPnl = new JPanel(new BorderLayout());
        rcMessagesPnl.add(new JScrollPane(rcEncodedMessageTA), BorderLayout.NORTH);
        rcMessagesPnl.add(rcButtonsPnl, BorderLayout.CENTER);
        rcMessagesPnl.add(new JScrollPane(rcDecodedMessageTA), BorderLayout.SOUTH);

        // RECEIVER PANEL
        JPanel receiverPnl = new JPanel(new BorderLayout());
        receiverPnl.add(rcInfoPnl, BorderLayout.NORTH);
        receiverPnl.add(rcMessagesPnl, BorderLayout.CENTER);

        // SPLIT PANE
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, snPnl, receiverPnl);
        splitPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // MAIN PANEL
        JPanel mainPnl = new JPanel(new BorderLayout());
        mainPnl.add(infoPnl, BorderLayout.NORTH);
        mainPnl.add(splitPane, BorderLayout.CENTER);

        // GENERATE BUTTON PRESSED
        generateButton.addActionListener((event) -> {
            encoder = new PHEncoder();
            snNTA.setText(encoder.nAsString());
            snKeyTA.setText(encoder.eAsString());
            rcKeyTA.setText(encoder.dAsString());
            System.out.println("GENERATE");
            System.out.println("N: " + encoder.nAsString());
            System.out.println("E: " + encoder.eAsString());
            System.out.println("D: " + encoder.dAsString());
            System.out.println("-----------");
        });

        // ENCODE BUTTON PRESSED
        encodeButton.addActionListener((event) -> {
//            encoder = new PHEncoder();
            encoder.setN(snNTA.getText());
            encoder.setE(snKeyTA.getText());
            snEncodedMessageTA.setText(encoder.encoded(snOriginMessageTA.getText()));
//            snNTA.setText(encoder.nAsString());
//            snKeyTA.setText(encoder.eAsString());
            System.out.println("ENCODE");
            System.out.println("N: " + encoder.nAsString());
            System.out.println("E: " + encoder.eAsString());
            System.out.println("D: " + encoder.dAsString());
            System.out.println("Orig: " + snOriginMessageTA.getText());
            System.out.println("Enc: " + snEncodedMessageTA.getText());
            System.out.println("-----------");
        });

        // SEND BUTTON PRESSED
        sendButton.addActionListener((event) -> {
            encoder.setN(snNTA.getText());
            encoder.setE(snKeyTA.getText());
            rcEncodedMessageTA.setText(snEncodedMessageTA.getText());
            rcNTA.setText(encoder.nAsString());
            rcKeyTA.setText(encoder.dAsString());
//            rcKeyTA.setText("");
            System.out.println("SEND");
            System.out.println("N: " + encoder.nAsString());
            System.out.println("E: " + encoder.eAsString());
            System.out.println("D: " + encoder.dAsString());
            System.out.println("Orig: " + snOriginMessageTA.getText());
            System.out.println("Enc: " + snEncodedMessageTA.getText());
            System.out.println("-----------");


        });

        // DECODE BUTTON PRESSED
        decodeButton.addActionListener((event) -> {
            encoder.setN(rcNTA.getText());
            encoder.setD(rcKeyTA.getText());
            rcDecodedMessageTA.setText(encoder.decoded(rcEncodedMessageTA.getText()).trim());
            rcKeyTA.setText(encoder.dAsString());
            System.out.println("DECODE");
            System.out.println("N: " + encoder.nAsString());
            System.out.println("E: " + encoder.eAsString());
            System.out.println("D: " + encoder.dAsString());
            System.out.println("Orig: " + snOriginMessageTA.getText());
            System.out.println("Enc: " + snEncodedMessageTA.getText());
            System.out.println("Dec; " + rcDecodedMessageTA.getText());
            System.out.println("-----------");
        });

        // FRAME
        JFrame frame = new JFrame();
        frame.setTitle("Poligh-Hellman encoder by Max Trunnikov (@maxonfjvipon)");
        frame.setLayout(new BorderLayout());
        frame.add(mainPnl, BorderLayout.CENTER);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        splitPane.setDividerLocation(frame.getWidth() / 2);
    }

}
