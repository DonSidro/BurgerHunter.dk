package dk.burgerhunter.burgerhunter.Helper;

/**
 * Created by SidonKK on 01/06/2017.
 */

public class Burger {

    private String name;
    private float price;
    private String imageString;
    private float latitude;
    private float longitude;
    private String info;
    private int rating;
    private String fileUri;

    public Burger(String name, float price, String imageString, float latitude, float longitude, String info, int rating, String fileUri) {
        this.name = name;
        this.price = price;
        this.imageString = imageString;
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
        this.rating = rating;
        this.fileUri = fileUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}
