package my.edu.tarc.inventoryapp;

public class Product {

    String Name ;
    String Price ;
    String Desc ;
    String ExpDate;
    String Qty ;
    String Image;
    String Barcode;
    String Category;
    String date;
    String time;

    public Product() {
    }

    public Product(String name, String price, String desc, String expDate, String qty, String image, String barcode, String category, String date, String time) {
        this.Name = name;
        this.Price = price;
        this.Desc = desc;
        this.ExpDate = expDate;
        this.Qty = qty;
        this.Image = image;
        this.Barcode = barcode;
        this.Category = category;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public void setExpDate(String expDate) {
        ExpDate = expDate;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
