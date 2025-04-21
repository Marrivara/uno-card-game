package src.BusinessLayer.model;

import src.BusinessLayer.model.Interfaces.Component;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Component> components;

    public Inventory() {
        this.components = new HashMap<>();
    }

    public void addComponent(Component component) {
        components.put(component.getId(), component);
    }

    public Component getComponent(String id) {
        return components.get(id);
    }

    public void printInventory() {
        System.out.println("****** Current Inventory ******");
        for (Component component : components.values()) {
            System.out.println(component);
        }
        System.out.println("****************************");
    }
}