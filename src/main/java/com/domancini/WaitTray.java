package com.domancini;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.net.URL;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class WaitTray {
    public PopupMenu popupMenu;
    public SystemTray sysTray;
    TrayIcon trayIcon;
    String imageResourcePath = "images/clock_noborder.png";
    int timerDurationMs;
    private long startTime;
    private long endTime;
    NumberFormat nf;
    public WaitTray() {
        trayIcon = new TrayIcon(loadImage(imageResourcePath));
        trayIcon.setImageAutoSize(true);
        sysTray = SystemTray.getSystemTray();
        popupMenu = createPopupMenu();
        trayIcon.setPopupMenu(popupMenu);
        nf = NumberFormat.getInstance();
        // frame.setVisible(false);

    }
    
    public void launchTimerTray(JFrame theFrame) {
        try {
            sysTray.add(trayIcon);
            System.out.println("Successfully activated Tray");
        } catch (AWTException x) {
            System.err.println("TrayIcon could not be added");
        }
        theFrame.setVisible(false);
    }
    @SuppressWarnings("unused")
    public PopupMenu createPopupMenu() {
        popupMenu = new PopupMenu();

        MenuItem exitItem = new MenuItem("Exit");
        MenuItem addNewTimerItem = new MenuItem("Add another Timer");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });
        addNewTimerItem.addActionListener(e -> startAnotherTimer());
        popupMenu.add(exitItem);
        popupMenu.add(addNewTimerItem);

        return popupMenu;
    }
    public static void startAnotherTimer() {
        App.main(null);
        
    }
    public void startToolTipCountdown(int durationMs) {
        startTime = System.currentTimeMillis();
        endTime = startTime + durationMs;
        trayIcon.addMouseMotionListener(hoverListener);
    } 
    MouseAdapter hoverListener = new MouseAdapter() {
        public void mouseMoved(java.awt.event.MouseEvent e) {
            long currentTime = System.currentTimeMillis();
            long remainingTimeMs = endTime - currentTime;
            
            if (remainingTimeMs > 0) {
                long remainingSecs = remainingTimeMs / 1000;
                long remainingMins = remainingSecs / 60;
                long remainingHours = remainingMins / 60;
                remainingMins = remainingHours % 60;
                String displayRemaining = String.format("%d:%d %s", remainingHours, remainingMins, nf.format(remainingSecs));
                trayIcon.setToolTip(displayRemaining);
            } else {
                trayIcon.setToolTip("Start Timer?");
            }   
        };      
        
        
    };
    public static Image loadImage(String imgPath) {
        URL imgUrl = WaitTray.class.getClassLoader().getResource(imgPath);
        
        if (imgPath == null) {
            System.err.println("Resource not found: " + imgPath);
            return null;
        } else {
            return (new ImageIcon(imgUrl)).getImage();
        }
    }
}
