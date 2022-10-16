package cart;

import javax.swing.Icon;

public class ModelCart {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    public ModelCart(String name, String price, String description, Icon image) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public ModelCart() {
    }

    private String name;
    private String price;
    private String description;
    private Icon image;
}
