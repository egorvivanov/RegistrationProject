package edu.example.egorvivanov.registrationproject.view;


import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface RegistrationView extends MvpView {

    void close();

    void showMessage(@StringRes int message);
}
