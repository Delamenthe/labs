package LabaPacks;

import java.util.ArrayList;

public class UserActiveList {
    private static ArrayList<String> activeUsers = new ArrayList<String>();
    public synchronized void addUserActive(String user){

        activeUsers.add(user);

    }

    public synchronized ArrayList<String> getUserActive(){

        return activeUsers;

    }
    public synchronized void removeUserActive(String user){

        activeUsers.remove(user);

    }
}
