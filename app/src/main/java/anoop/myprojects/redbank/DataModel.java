package anoop.myprojects.redbank;


public class DataModel {


    String name;
    String email;
    String version;
    int id_;
    int image;
    String bg;

    public DataModel(String name, String version, int id_, int image, String email,String bg) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image=image;
        this.email=email;
        this.bg=bg;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }

    public String getBg() {
        return bg ;
    }
}