package com.weather.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import com.weather.controller.WeatherController;
import com.weather.model.WeatherModel;
import com.weather.model.WeatherObservable;
import com.weather.model.WeatherObserver;

public class MainFrame extends JFrame implements WeatherObserver {
    private WeatherModel model;
    private WeatherController controller;
    
    private CityListPanel cityListPanel;
    private DateSelectorPanel dateSelectorPanel;
    private WeatherDisplayPanel weatherDisplayPanel;
    private TemperatureUnitSelector temperatureUnitSelector;
    private TrackedCitiesPanel trackedCitiesPanel;
    private StatisticsPanel statisticsPanel;
    
    public MainFrame(WeatherModel model, WeatherController controller) {
        this.model = model;
        this.controller = controller;
        
        // Initialize UI components
        initComponents();
        
        // Register as observer
        model.addObserver(this);
    }
    
    private void initComponents() {
        // Set up the main frame properties
        setTitle("Realtime Weather");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        // Initialize panels
        cityListPanel = new CityListPanel(model, controller);
        dateSelectorPanel = new DateSelectorPanel(model, controller);
        weatherDisplayPanel = new WeatherDisplayPanel(model);
        temperatureUnitSelector = new TemperatureUnitSelector(model, controller);
        trackedCitiesPanel = new TrackedCitiesPanel(model, controller);
        statisticsPanel = new StatisticsPanel(model);
        
        // Left panel (selection controls)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(cityListPanel, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel(new GridLayout(2, 1));
        controlsPanel.add(dateSelectorPanel);
        controlsPanel.add(temperatureUnitSelector);
        leftPanel.add(controlsPanel, BorderLayout.SOUTH);
        
        // Right panel (weather display)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(weatherDisplayPanel, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.add(new JScrollPane(trackedCitiesPanel), BorderLayout.CENTER);
        infoPanel.add(statisticsPanel, BorderLayout.SOUTH);
        rightPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Add to split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void update(WeatherObservable observable) {
        // Updates handled by individual panels
        revalidate();
        repaint();
    }
}