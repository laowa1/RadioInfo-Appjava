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
public class ChannelInfo {
    private Integer id;
    //private Image img;
    private final String name;
    private String tagLine;
    private URL imageURL;
    private URL siteURL;
    private URL scheduleURL;
    private List<ProgramInfo> pI;

    public ChannelInfo(String name) {
        this.name = name;
    }

    public void setProgramList(List<ProgramInfo> l) {
        pI = new ArrayList<>();
        pI = l;
    }

    public List<ProgramInfo> getProgramList() {
        return this.pI;
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

    public URL getSiteURL() {
        return siteURL;
    }

    public URL getScheduleURL() {
        return scheduleURL;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setImageURL(URL imageURL) {
        this.imageURL = imageURL;
    }

    public void setSiteURL(URL siteURL) {
        this.siteURL = siteURL;
    }

    public void setScheduleURL(URL scheduleURL) {
        this.scheduleURL = scheduleURL;
    }
}
