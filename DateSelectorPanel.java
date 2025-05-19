package com.weather.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.weather.controller.WeatherController;
import com.weather.model.WeatherModel;

public class DateSelectorPanel extends JPanel {
    private WeatherModel model;
    private WeatherController controller;
    private JComboBox<String> yearCombo;
    private JComboBox<String> monthCombo;
    private JComboBox<String> dayCombo;
    
    private static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    
    public DateSelectorPanel(WeatherModel model, WeatherController controller) {
        this.model = model;
        this.controller = controller;
        
        // Initialize UI components
        initComponents();
    }
    
    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Select Date"));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Year selection (2025 only for this app)
        add(new JLabel("Year:"));
        yearCombo = new JComboBox<>(new String[]{"2025"});
        yearCombo.setSelectedIndex(0);
        add(yearCombo);
        
        // Month selection (January to May)
        add(new JLabel("Month:"));
        monthCombo = new JComboBox<>();
        for (int i = 0; i < 5; i++) {
            monthCombo.addItem(MONTHS[i]);
        }
        monthCombo.setSelectedIndex(0);
        add(monthCombo);
        
        // Day selection (1-31, will be adjusted based on month)
        add(new JLabel("Day:"));
        dayCombo = new JComboBox<>();
        updateDaysCombo();
        add(dayCombo);
        
        // Add action listeners
        monthCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDaysCombo();
            }
        });
        
        ActionListener dateChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedDate();
            }
        };
        
        yearCombo.addActionListener(dateChangeListener);
        monthCombo.addActionListener(dateChangeListener);
        dayCombo.addActionListener(dateChangeListener);
        
        // Set initial date
        updateSelectedDate();
    }
    
    private void updateDaysCombo() {
        int selectedDay = dayCombo.getSelectedIndex() >= 0 ? dayCombo.getSelectedIndex() + 1 : 1;
        int month = monthCombo.getSelectedIndex() + 1;
        
        dayCombo.removeAllItems();
        
        int daysInMonth;
        switch (month) {
            case 2: // February
                daysInMonth = 28;
                break;
            case 4: case 6: case 9: case 11: // 30 days
                daysInMonth = 30;
                break;
            default: // 31 days
                daysInMonth = 31;
                break;
        }
        
        for (int i = 1; i <= daysInMonth; i++) {
            dayCombo.addItem(String.valueOf(i));
        }
        
        // Try to keep the same day selected
        if (selectedDay <= daysInMonth) {
            dayCombo.setSelectedIndex(selectedDay - 1);
        } else {
            dayCombo.setSelectedIndex(daysInMonth - 1);
        }
    }
    
    private void updateSelectedDate() {
        int year = 2025; // Fixed to 2025 for this app
        int month = monthCombo.getSelectedIndex() + 1;
        int day = dayCombo.getSelectedIndex() + 1;
        
        LocalDate date = LocalDate.of(year, month, day);
        controller.selectDate(date);
    }
}