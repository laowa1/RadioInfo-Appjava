package model;

import java.net.URL;

/**
 * Info about programs.
 * @UserID - tfy17jfo
 * @date - 2018-01-25
 * @version 2.0
 * @author Jakob Fridesjö
 */
public class ProgramInfo {

    private String startTimeUTC;
    private String endTimeUTC;
    private final String name;
    private String description;
    private URL imageURL;

    /**
     * Constructor for ProgramInfo
     * @param name name of program
     */
    ProgramInfo(String name) {
        this.name = name;
    }

    /**
     * Gets name of program
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets URL for image
     * @return url
     */
    public URL getImageURL() {
        return imageURL;
    }

    /**
     * Sets tagLine
     * @param tagLine description for program
     */
    void setTagLine(String tagLine) {
        this.description = tagLine;
    }

    /**
     * Gets tagLine
     * @return tagLine
     */
    public String getTagLine() {
        return description;
    }

    /**
     * Sets URL for image
     * @param imageURL url to set
     */
    void setImageURL(URL imageURL) { this.imageURL = imageURL; }

    /**
     * Gets start time in utc.
     * @ time
     */
    public String getStartTimeUTC() {
        return startTimeUTC;
    }

    /**
     * Sets start time in utc.
     * @param time time
     */
    void setStartTimeUTC(String time) {
        this.startTimeUTC = time;
    }

    /**
     * Gets end time in utc
     * @return time
     */
    public String getEndTimeUTC() {
        return endTimeUTC;
    }

    /**
     * Sets end time in utc
     * @param endTimeUTC time
     */
    void setEndTimeUTC(String endTimeUTC) {
        this.endTimeUTC = endTimeUTC;
    }
}
