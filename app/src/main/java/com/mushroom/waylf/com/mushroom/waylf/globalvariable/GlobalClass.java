package com.mushroom.waylf.com.mushroom.waylf.globalvariable;

import android.app.Application;
/**
 * Created by antoinerose on 03/02/2015.
 */
public class GlobalClass extends Application {

    private String user_id;

    public String getUserId() {

        return user_id;
    }

    public void setUserId(String uId) {

        user_id = uId;

    }

}
