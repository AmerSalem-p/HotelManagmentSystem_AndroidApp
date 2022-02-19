package model;

import java.io.Serializable;

public class item implements Serializable {
    private int itemNumber;
    private String itemName;
    private String itemType;
    private int price;
    private String image;

    @Override
    public String toString() {
        return "item{" +
                "itemNumber=" + itemNumber +
                ", itemName='" + itemName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }

    public item(){}

    public item(int itemNumber, String itemName, String itemType, int price, String image) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemType = itemType;
        this.price = price;
        this.image = image;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
}
