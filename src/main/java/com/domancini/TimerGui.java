package com.domancini;
import static java.time.temporal.ChronoUnit.MILLIS;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;


public class TimerGui {
    public Timer timer;
    public JFrame frame;
    JSpinner minSpinner;
    JSpinner hrsSpinner;
    JSpinner alarmSpinner;
    long totalWaitMs;
    JTextField timerMessageField;
    JTextField alarmMessageField;
    String messageTitle;

    public TimerGui() {
        frame = new JFrame("DeskTimer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        
        JTabbedPane tabPane = new JTabbedPane(JTabbedPane.TOP);

        // Creating panels from respective methods
        JPanel timerPanel = createTimerPanel();   
        JPanel alarmPanel = createAlarmPanel();  
           
        
        tabPane.addTab("Alarm", alarmPanel);
        tabPane.addTab("Timer", timerPanel);
        frame.add(tabPane);

        frame.setVisible(true);
    }
    ActionListener timerSubmitActionListener = new ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int mins = (Integer) minSpinner.getValue();
            int hrs = (Integer) hrsSpinner.getValue();
            System.out.format("Mins: %d, Hrs: %d%n", mins, hrs);
            int totalMins = mins + (hrs * 60);
            long totalMs = (totalMins * 60) * 1000;
            System.out.format("Total Ms: %,d%n", totalMs);
            int intTotalMs = (int) totalMs;

            messageTitle = timerMessageField.getText();
            System.out.println("Message: " + messageTitle);
            

            startTimer(intTotalMs);
        };
    };
    private JPanel createTimerPanel() {

        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new MigLayout("flowy", "[grow, fill]", ""));
        JPanel minPanel = new JPanel();
        JPanel hrsPanel = new JPanel();

        JLabel minLabel = new JLabel("Minutes: ");
        JLabel hrsLabel = new JLabel("Hours");

        // Creating spinner models & spinners
        SpinnerNumberModel minModel = new SpinnerNumberModel(0, 0, 60, 10);
        SpinnerNumberModel hrsModel = new SpinnerNumberModel(0, 0, 12, 1);
        minSpinner = new JSpinner(minModel);
        hrsSpinner = new JSpinner(hrsModel);

        timerMessageField = new JTextField("message");
        timerMessageField.addFocusListener(textFieldFocus);


        JButton submitButton1 = new JButton("Set Timer");
        submitButton1.addActionListener(timerSubmitActionListener);
        submitButton1.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        minPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        minPanel.add(minLabel);
        minPanel.add(minSpinner);

        hrsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        hrsPanel.add(hrsLabel);
        hrsPanel.add(hrsSpinner);

        timerPanel.add(minPanel);
        timerPanel.add(hrsPanel);
        timerPanel.add(timerMessageField);
        timerPanel.add(submitButton1);


        
        return timerPanel;
    }
    FocusAdapter textFieldFocus = new FocusAdapter() {
        /* Focus Listener to Remove placeholder 'message' text on user click */
        @Override
        public void focusGained(FocusEvent e) {
            Object src = e.getSource();
            if (src == timerMessageField) {
                timerMessageField.setText("");
                timerMessageField.removeFocusListener(textFieldFocus);
            } else {
                alarmMessageField.setText("");
                alarmMessageField.removeFocusListener(textFieldFocus);
            }

        };
    };
    private JPanel createAlarmPanel() {
        JPanel alarmPanel = new JPanel();
        alarmPanel.setLayout(new MigLayout("flowy, insets 10 15 10 15", "[grow, fill]", "[]15[]20[]"));
        
        JLabel timeLabel = new JLabel("Time: ");
        JButton submitButton = new JButton("Set Timer");
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setCalendarField(Calendar.MINUTE);
        alarmSpinner = new JSpinner();
        alarmSpinner.setModel(dateModel);
        // USE "EEE, h:mm a" pattern to INCLUDE DAY
        alarmSpinner.setEditor(new JSpinner.DateEditor(alarmSpinner, "h:mm a"));
        submitButton.addActionListener(alarmSubmitActionListener);
        

        JPanel spinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        spinPanel.add(timeLabel);
        spinPanel.add(alarmSpinner);
        alarmMessageField = new JTextField("Message");
        alarmMessageField.addFocusListener(textFieldFocus);

        alarmPanel.add(spinPanel);
        alarmPanel.add(alarmMessageField);
        alarmPanel.add(submitButton);

        return alarmPanel;
    }

    ActionListener alarmSubmitActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy. EEE, h:mm a");
            Date setDate = (Date)alarmSpinner.getValue();
            Instant instant = setDate.toInstant();
            LocalDateTime setLdt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            LocalTime setLocalTime = setLdt.toLocalTime();
            
            LocalDateTime todayDateTime = LocalDateTime.now();
            LocalDateTime futureDateTime = setLocalTime.atDate(todayDateTime.toLocalDate());
            
            // Fixing alarm times after midnight (next day)
            if (futureDateTime.isBefore(todayDateTime)) {
                LocalDateTime correctedDt = futureDateTime.plusDays(1);
                futureDateTime = correctedDt;
            }
            NumberFormat nf = NumberFormat.getInstance();
            Duration duration = Duration.between(todayDateTime, futureDateTime);
            long hrs = duration.toHoursPart();
            long mins = duration.toMinutesPart();
            // long ms = duration.toMillis(); NOTE: unused Ms
            
            long totalMs = MILLIS.between(todayDateTime, futureDateTime);
            totalWaitMs = totalMs;
            
            System.out.println("Set Alarm Date: " + futureDateTime.format(dateFormat));
            System.out.printf("Duration: %d:%d mins%n", hrs, mins);
            System.out.printf("Duration ms: %s%n", nf.format(totalMs));
            messageTitle = alarmMessageField.getText();

            int intMs = (int) totalMs;
            startTimer(intMs);


        };
    };
    public void startTimer(int totalMs) {
        // 8 seconds below for testing
        // totalMs = 8 * 1000;
        String dialogMsg;
        if (messageTitle.equals("Message") || messageTitle.isBlank()) {
            dialogMsg = "Timer Finished!";
        } else {
            dialogMsg = messageTitle;
        }
        WaitTray waitTray = new WaitTray();
        timer = new Timer(totalMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // frame.setVisible(true);
                // JOptionPane.showMessageDialog(null, "Timer Done!");
                // waitTray.trayIcon.displayMessage("Timer Complete", "Timer Finished Successfuly", MessageType.INFO);
                int option = JOptionPane.showOptionDialog(null, dialogMsg, "Timer Finished!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {"Ok"}, "Ok");
                if (option == JOptionPane.CLOSED_OPTION || option == 0) {
                    System.exit(0);
                }
            }
        }); 
        waitTray.startToolTipCountdown(totalMs);

        timer.setRepeats(false);
        waitTray.launchTimerTray(frame);
        timer.start();


    }
    
    public static void print(Object obj) {
        System.out.println(obj.toString());
    }
    public static void main( String[] args ) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
            System.out.println(UIManager.getLookAndFeel());
        }
        SwingUtilities.invokeLater(TimerGui::new);

    }

}