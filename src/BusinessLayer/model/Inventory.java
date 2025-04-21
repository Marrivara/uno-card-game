package src.BusinessLayer.model;

import src.BusinessLayer.model.Interfaces.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Inventory class to manage components (Information Expert GRASP pattern)
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

    public Collection<Component> getAllComponents() {
        return components.values();
    }

    public void printInventory() {
        System.out.println("===== Current Inventory =====");
        for (Component component : components.values()) {
            System.out.println(component);
        }
        System.out.println("============================");
    }
}