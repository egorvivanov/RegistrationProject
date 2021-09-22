package edu.example.egorvivanov.registrationproject;

import android.support.v4.app.Fragment;

public class AuthorizationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AuthorizationFragment.newInstance();
    }
}
