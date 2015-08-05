package ru.bk.klim9.vkfotoviewer.model;

public class Album {
    public long id;
    public String title;
    public String thumbSrc;

    public Album(long id, String title, String thumbSrc) {
        this.id = id;
        this.title = title;
        this.thumbSrc = thumbSrc;
    }
}
