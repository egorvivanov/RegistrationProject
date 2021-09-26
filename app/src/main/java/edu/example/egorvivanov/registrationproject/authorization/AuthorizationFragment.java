package edu.example.egorvivanov.registrationproject.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.example.egorvivanov.registrationproject.ProfileActivity;
import edu.example.egorvivanov.registrationproject.R;
import edu.example.egorvivanov.registrationproject.RegistrationFragment;
import edu.example.egorvivanov.registrationproject.api.ApiUtils;
import edu.example.egorvivanov.registrationproject.model.ResponseData;
import edu.example.egorvivanov.registrationproject.model.User;

import retrofit2.Call;
import retrofit2.Callback;

public class AuthorizationFragment extends Fragment {

    private AutoCompleteTextView mEmail;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;

    public static AuthorizationFragment newInstance() {
        Bundle args = new Bundle();

        AuthorizationFragment fragment = new AuthorizationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View v = inflater.inflate(R.layout.fragment_authorization, container, false);

        mEmail = v.findViewById(R.id.auth_email);
        mPassword = v.findViewById(R.id.auth_password);
        mEnter = v.findViewById(R.id.btn_enter);
        mRegister = v.findViewById(R.id.btn_register);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        mEmail.setOnFocusChangeListener(mOnEmailFocusChangeListener);

        return v;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {
                ApiUtils.getApi().authorization().enqueue(
                        new Callback<ResponseData<User>>() {

                            //используем Handler, чтобы показывать ошибки в Main потоке,
                            // т.к. наши коллбеки возвращаются в рабочем потоке
                            final Handler mainHandler = new Handler(getActivity().getMainLooper());

                            @Override
                            public void onResponse(final Call<ResponseData<User>> call, final retrofit2.Response<ResponseData<User>> response) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!response.isSuccessful()) {
                                            showMessage(R.string.authorization_error);
                                        } else {
                                            User user = response.body().getData();

                                            Intent startProfileIntent = new Intent(getActivity(), ProfileActivity.class);
                                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
                                            startActivity(startProfileIntent);
                                            getActivity().finish();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<ResponseData<User>> call, Throwable t) {
                                mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showMessage(R.string.request_error);
                                    }
                                });
                            }
                        });
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };

    private View.OnFocusChangeListener mOnEmailFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus) {
                mEmail.showDropDown();
            }
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mEmail.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }
}