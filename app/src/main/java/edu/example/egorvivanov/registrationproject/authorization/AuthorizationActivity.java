package edu.example.egorvivanov.registrationproject.authorization;

import android.support.v4.app.Fragment;

import edu.example.egorvivanov.registrationproject.SingleFragmentActivity;

public class AuthorizationActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return AuthorizationFragment.newInstance();
    }
}
