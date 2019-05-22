package th.ac.kku.cis.lab.wongnai;

public class Food {
    private String ImageURL;
    private String name;
    private String component;
    private String howmenu;
    public Food(){}

    public Food(String imageURL,String name,String component,String howmenu){
        this.ImageURL = imageURL;
        this.name = name;
        this.component = component;
        this.howmenu = howmenu;
    }

    public String getComponent() {
        return component;
    }

    public String getHowmenu() {
        return howmenu;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getName() {
        return name;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public void setHowmenu(String howmenu) {
        this.howmenu = howmenu;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }
}
