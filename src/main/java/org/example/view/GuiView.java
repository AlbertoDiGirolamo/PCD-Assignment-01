package org.example.view;

import org.example.controller.Controller;
import org.example.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GuiView implements View{
    private Controller controller;
    private final JList<Pair<File, Integer>> rankingList = new JList<>();
    private final JList<String> distributionList = new JList<>();
    private final JFrame frame = new JFrame();

    public GuiView() {
        JFrame frame = new JFrame("Assignment 1");

        frame.setSize(800, 800);
        frame.setLocation(100, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultLookAndFeelDecorated(true);

        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());


        JLabel lblPath = new JLabel("Path: ");
        JTextField txtPath = new JTextField();

        JLabel lblNI = new JLabel("Numero intervalli: ");
        JTextField txtNI = new JTextField();

        JLabel lblMaxL = new JLabel("Numero massimo di linee: ");
        JTextField txtMaxL = new JTextField();

        JLabel lblN = new JLabel("Top n: ");
        JTextField txtN = new JTextField();



        JButton btnStart = new JButton("Start");
        JButton btnStop = new JButton("Stop");

        JLabel lblN3 = new JLabel("");

        lblPath.setBounds(50, 50, 200,30);
        txtPath.setBounds(50, 100, 200,30);
        lblNI.setBounds(50, 150, 200,30);
        txtNI.setBounds(50, 200, 200,30);
        lblMaxL.setBounds(50, 250, 200,30);
        txtMaxL.setBounds(50, 300, 200,30);
        lblN.setBounds(50, 350, 200,30);
        txtN.setBounds(50, 400, 200,30);


        btnStart.setBounds(50, 500, 200,30);
        btnStop.setBounds(50, 550, 200,30);

        lblN3.setBounds(50, 600, 200,30);


        frame.add(lblPath);
        frame.add(txtPath);
        frame.add(lblNI);
        frame.add(txtNI);
        frame.add(lblMaxL);
        frame.add(txtMaxL);
        frame.add(lblN);
        frame.add(txtN);
        frame.add(lblN3);
        frame.add(btnStart);
        frame.add(btnStop);


        frame.setVisible(true);
        frame.setLayout(null);

    }
}
