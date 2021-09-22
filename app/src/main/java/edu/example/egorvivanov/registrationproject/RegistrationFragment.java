package edu.example.egorvivanov.registrationproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import edu.example.egorvivanov.registrationproject.api.ApiUtils;
import edu.example.egorvivanov.registrationproject.presenter.RegistrationPresenter;
import edu.example.egorvivanov.registrationproject.view.RegistrationView;

public class RegistrationFragment
        extends MvpAppCompatFragment
        implements RegistrationView {

    private EditText mEmail;
    private EditText mName;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mBtnRegistration;

    @InjectPresenter
    RegistrationPresenter mRegistrationPresenter;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @ProvidePresenter
    public RegistrationPresenter provideRegistrationPresenter() {
        return new RegistrationPresenter(ApiUtils.getApi());
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        mEmail = view.findViewById(R.id.registration_email);
        mName = view.findViewById(R.id.registration_name);
        mPassword = view.findViewById(R.id.registration_password);
        mPasswordAgain = view.findViewById(R.id.registration_password_again);
        mBtnRegistration = view.findViewById(R.id.btn_registration);

        mBtnRegistration.setOnClickListener(mOnRegistrationClickListener);

        return view;
    }

    private View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mRegistrationPresenter.registration(
                    mEmail.getText().toString(),
                    mName.getText().toString(),
                    mPassword.getText().toString(),
                    mPasswordAgain.getText().toString()
            );
        }
    };

    @Override
    public void close() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }
}
