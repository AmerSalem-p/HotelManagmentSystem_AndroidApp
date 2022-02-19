package model;

public class room {

    private int roomNumber;
    private String floor;
    private String numberOfBeds;
    private String typeOfBeds;
    private int price;
    private String size;
    private String image1;
    public room(){}

    @Override
    public String toString() {
        return "room{" +
                "roomNumber=" + roomNumber +
                ", floor='" + floor + '\'' +
                ", numberOfBeds='" + numberOfBeds + '\'' +
                ", typeOfBeds='" + typeOfBeds + '\'' +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", image1='" + image1 + '\'' +
                '}';
    }





    public room(int roomNumber, String floor, String numberOfBeds, String typeOfBed, int price, String size, String image1){
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.numberOfBeds = numberOfBeds;
        this.typeOfBeds = typeOfBed;
        this.price = price;
        this.size = size;
        this.image1 = image1;
    }



    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTypeOfBeds() {
        return typeOfBeds;
    }

    public void setTypeOfBeds(String typeOfBeds) {
        this.typeOfBeds = typeOfBeds;
    }

    public String getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(String numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
