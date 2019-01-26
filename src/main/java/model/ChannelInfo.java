package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Info about channels.
 * @UserID - tfy17jfo
 * @date - 2018-12-10
 * @version 1.0
 * @author Jakob Fridesjö
 */
@SuppressWarnings("unused")
public class ChannelInfo {
    private Integer id;
    private final String name;
    private String tagLine;
    private URL imageURL;
    private URL siteURL;
    private URL scheduleURL;
    private List<ProgramInfo> pI;

    /**
     * Constructs channel.
     * @param name name of channel.
     */
    ChannelInfo(String name) {
        this.name = name;
    }

    /**
     * Sets the list
     * @param l list to set
     */
    void setProgramList(List<ProgramInfo> l) {
        pI = new ArrayList<>();
        pI = l;
    }

    /**
     * gets the list
     * @return list
     */
    public List<ProgramInfo> getProgramList() {
        return this.pI;
    }

    /**
     * Gets ID
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets URL for image
     * @return image url
     */
    public URL getImageURL() {
        return imageURL;
    }

    /**
     * Gets site URL
     * @return site url
     */
    public URL getSiteURL() {
        return siteURL;
    }

    /**
     * Gets schedule url.
     * @return
     */
    URL getScheduleURL() {
        return scheduleURL;
    }

    /**
     * Sets ID
     * @param id of program
     */
    void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets description/tagline
     * @param tagLine to set
     */
    void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    /**
     * Gets tagline
     * @return tagLine
     */
    public String getTagLine() {
        return tagLine;
    }

    /**
     * Sets image URL
     * @param imageURL url to image
     */
    void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Sets site URL
     * @param siteURL url to site.
     */
    void setSiteURL(URL siteURL) {
        this.siteURL = siteURL;
    }

    /**
     * Sets URL to schedule.
     * @param scheduleURL to set
     */
    void setScheduleURL(URL scheduleURL) {
        this.scheduleURL = scheduleURL;
    }
}
