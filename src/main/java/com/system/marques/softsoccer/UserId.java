package com.system.marques.softsoccer;

public class UserId
{
    protected String userId;

    public <T extends UserId> T withId(final String id)
    {
        this.userId = id;

        return (T) this;
    }
}
