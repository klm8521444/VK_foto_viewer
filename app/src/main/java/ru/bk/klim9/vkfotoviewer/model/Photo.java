package ru.bk.klim9.vkfotoviewer.model;

public class Photo {
    public long id;
    public String photoSrc_130;
    public String photoSrc_604;

    public Photo(long id, String photoSrc_130, String photoSrc_604) {
        this.id = id;
        this.photoSrc_130 = photoSrc_130;
        this.photoSrc_604 = photoSrc_604;
    }
}
