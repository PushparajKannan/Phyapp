package com.phyapp.root.physiotherapy.ModelClass;

public class ImageModel
{



    public ImageModel(){

    }

   /* public String text;
   // public int drawable;
    public String color;

    public ImageModel(String t, String c )
    {
        text=t;
        //drawable=d;
        color=c;
    }*/
   // @SerializedName("name")
   // @Expose




   private String name;
    // @SerializedName("from")
    // @Expose
    private String image;
    // @SerializedName("to")
    //@Expose
    private String to;
    //  @SerializedName("date")
    // @Expose
    private String date;
    // @SerializedName("km")
    // @Expose
    private String km;
    // @SerializedName("ride_id")
    // @Expose
    private String Id;

    private String time;

    public ImageModel(String name)
    {
        this.name=name;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
