package com.system.marques.softsoccer;

public class Users
    extends
        UserId
{
    private String name;

    public Users()
    {
    }

    public Users(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
