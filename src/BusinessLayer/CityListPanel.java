package src.BusinessLayer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class CityListPanel extends JPanel implements WeatherObserver {
    private WeatherModel model;
    private WeatherController controller;
    private JList<String> cityList;
    private DefaultListModel<String> listModel;
    
    public CityListPanel(WeatherModel model, WeatherController controller) {
        this.model = model;
        this.controller = controller;
        
        // Initialize UI components
        initComponents();
        
        // Register as observer
        model.addObserver(this);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Available Cities"));
        
        listModel = new DefaultListModel<>();
        for (City city : model.getRepository().getAllCities()) {
            listModel.addElement(city.getName());
        }
        
        cityList = new JList<>(listModel);
        cityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cityList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedCity = cityList.getSelectedValue();
                    if (selectedCity != null) {
                        controller.selectCity(selectedCity);
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(cityList);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton trackButton = new JButton("Track City");
        trackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCity = cityList.getSelectedValue();
                if (selectedCity != null) {
                    controller.addTrackedCity(selectedCity);
                }
            }
        });
        
        add(trackButton, BorderLayout.SOUTH);
    }
    
    @Override
    public void update(WeatherObservable observable) {
        // No updates needed for city list
    }
}