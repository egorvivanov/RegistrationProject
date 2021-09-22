package edu.example.egorvivanov.registrationproject.presenter;

import android.text.TextUtils;
import android.util.Patterns;

import com.arellomobile.mvp.MvpPresenter;

import edu.example.egorvivanov.registrationproject.R;
import edu.example.egorvivanov.registrationproject.api.AcademyApi;
import edu.example.egorvivanov.registrationproject.model.User;
import edu.example.egorvivanov.registrationproject.view.RegistrationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPresenter extends MvpPresenter<RegistrationView> {

    private final AcademyApi mAcademyApi;

    public RegistrationPresenter(AcademyApi academyApi) {
        mAcademyApi = academyApi;
    }

    public void registration(String email, String name, String password, String passwordAgain) {

        if (isInformationValid(email, name) && isPasswordsValid(password, passwordAgain)) {
            User user = new User(email, name, password);

            mAcademyApi.registration(user).enqueue(
                    new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, final Response<Void> response) {
                            if (!response.isSuccessful()) {
                                getViewState().showMessage(R.string.registration_error);
                            } else {
                                getViewState().showMessage(R.string.registration_success);
                                getViewState().close();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            getViewState().showMessage(R.string.request_error);
                        }
                    });
        } else {
            getViewState().showMessage(R.string.input_error);
        }
    }

    private boolean isInformationValid(String email, String name) {
        return isEmailValid(email) && !TextUtils.isEmpty(name);
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordsValid(String password, String passwordAgain) {

        return password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }
}
