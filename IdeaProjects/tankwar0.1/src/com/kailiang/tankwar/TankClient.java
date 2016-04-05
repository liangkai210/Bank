package com.kailiang.tankwar;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

    int x = 50, y = 50;
    Image offScreenImage = null;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
        g.setColor(c);
    }

    @Override
    public void update(Graphics g) {
        super.update(g);
        if (offScreenImage == null) {
            offScreenImage = this.createImage(800, 600);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.GREEN);
        gOffScreen.fillRect(0, 0, 800, 600);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public void launchFrame() {
        this.setLocation(200, 100);
        this.setSize(800, 600);
        this.setTitle("Tank War");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        this.setBackground(Color.GREEN);
        this.setResizable(false);
        this.addKeyListener(new KeyMonitor());
        setVisible(true);

        new Thread(new paintThread()).start();
    }

    private class KeyMonitor extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    x -= 5;
                    break;
                case KeyEvent.VK_UP:
                    y -= 5;
                    break;
                case KeyEvent.VK_RIGHT:
                    x += 5;
                    break;
                case KeyEvent.VK_DOWN:
                    y += 5;
                    break;
            }
        }
    }

    private class paintThread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TankClient tankClient = new TankClient();
        tankClient.launchFrame();
    }
}
