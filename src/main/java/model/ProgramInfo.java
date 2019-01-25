package model;

import java.net.URL;

/**
 * Info about programs.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */

public class ProgramInfo {

    private Integer id;
    //private Image img;
    private String startTimeUTC;
    private String endTimeUTC;
    private final String name;
    private String description;
    private URL imageURL;


    public ProgramInfo(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTagLine(String tagLine) {
        this.description = tagLine;
    }

    public String getTagLine() {
        return description;
    }

    public void setImageURL(URL imageURL) { this.imageURL = imageURL; }

    public String getStartTimeUTC() {
        return startTimeUTC;
    }

    public void setStartTimeUTC(String startTimeUTC) {
        this.startTimeUTC = startTimeUTC;
    }

    public String getEndTimeUTC() {
        return endTimeUTC;
    }

    public void setEndTimeUTC(String endTimeUTC) {
        this.endTimeUTC = endTimeUTC;
    }
}
