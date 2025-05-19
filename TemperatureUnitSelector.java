package com.weather.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.weather.controller.WeatherController;
import com.weather.model.TemperatureUnit;
import com.weather.model.WeatherModel;
import com.weather.model.WeatherObservable;
import com.weather.model.WeatherObserver;

public class TemperatureUnitSelector extends JPanel implements WeatherObserver {
    private WeatherModel model;
    private WeatherController controller;
    private JRadioButton celsiusButton;
    private JRadioButton fahrenheitButton;
    
    public TemperatureUnitSelector(WeatherModel model, WeatherController controller) {
        this.model = model;
        this.controller = controller;
        
        // Initialize UI components
        initComponents();
        
        // Register as observer
        model.addObserver(this);
    }
    
    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Temperature Unit"));
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        celsiusButton = new JRadioButton("Celsius (°C)");
        fahrenheitButton = new JRadioButton("Fahrenheit (°F)");
        
        // Set initial selection based on user preferences
        if (model.getUserPreferences().getPreferredUnit() == TemperatureUnit.CELSIUS) {
            celsiusButton.setSelected(true);
        } else {
            fahrenheitButton.setSelected(true);
        }
        
        // Add action listeners
        celsiusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setTemperatureUnit(TemperatureUnit.CELSIUS);
            }
        });
        
        fahrenheitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setTemperatureUnit(TemperatureUnit.FAHRENHEIT);
            }
        });
        
        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(celsiusButton);
        group.add(fahrenheitButton);
        
        add(celsiusButton);
        add(fahrenheitButton);
    }
    
    @Override
    public void update(WeatherObservable observable) {
        // Update the selection if it changed elsewhere
        TemperatureUnit unit = model.getUserPreferences().getPreferredUnit();
        if (unit == TemperatureUnit.CELSIUS) {
            celsiusButton.setSelected(true);
        } else {
            fahrenheitButton.setSelected(true);
        }
    }
}