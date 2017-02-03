package crypticthread.smsapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 2/3/2017.
 */

class UserSessionManager {
    private static final String PREF_NAME = "user_preference";
    private static final int MODE = 0x10;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private ParentsDetails details;


    UserSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, MODE);
        editor = pref.edit();
    }

    void setParentsDetails(ParentsDetails details) {
        this.details = details;
        editor.putString(StaticVariables.KEY_PARENTS_ID, details.getId());
        editor.putString(StaticVariables.KEY_FIRST_NAME, details.getFirst_name());
        editor.putString(StaticVariables.KEY_LAST_NAME, details.getLast_name());
        editor.putString(StaticVariables.KEY_USERNAME, details.getUser_name());
        editor.putString(StaticVariables.KEY_ROLE, details.getRole());
        editor.putString(StaticVariables.KEY_EMAIL, details.getEmail());
        editor.putString(StaticVariables.KEY_CHILD_IDS, details.getClildrens_ids());
        editor.putString(StaticVariables.KEY_PHOTO, details.getPhoto());
        editor.apply();

    }

    String getChildIds(){
        return pref.getString(StaticVariables.KEY_CHILD_IDS,null);
    }

}
