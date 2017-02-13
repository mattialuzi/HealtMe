package Helpers;

/**
 * La classe Item è utilizzata nei ComboBox
 */

public class Item {
    private String value;
    private String label;

    public Item(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString(){
        return label;
    }
}
