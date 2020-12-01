package com.phyapp.root.physiotherapy.ModelClass;

public class DynamicTabMode {


    public DynamicTabMode()
    {

    }





    private  String name;
    private String dis;
    private String img;


    public  DynamicTabMode(String name,String dis){

        this.name=name;
        this.dis=dis;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getDis()
    {
        return dis;
    }

    public void setDis(String dis)
    {
        this.dis = dis;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

}

