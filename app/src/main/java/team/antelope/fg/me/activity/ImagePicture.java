package team.antelope.fg.me.activity;

/**
 * Created by Carlos on 2017/12/30.
 */

public class ImagePicture {
    private int imageId;
    private  String name;
    private String personName;
    private String personLetterName;


    public ImagePicture(String name,int imageId) {
        this.imageId = imageId;
        this.name =name;

    }

    public int getImageId() {
        return imageId;
    }


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonLetterName() {
        return personLetterName;
    }

    public void setPersonLetterName(String personLetterName) {
        this.personLetterName = personLetterName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
