package com.example.ishmeetkaur.sportify_version1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private Button buttonLogin;
        private FirebaseAuth mAuth;
        private EditText emailInput;
        private EditText passInput;

        private FirebaseAuth.AuthStateListener mAuthListener;

    public Login_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login_, container, false);
        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);
        emailInput = (EditText) rootView.findViewById(R.id.emailInput);
        passInput = (EditText) rootView.findViewById(R.id.passInput);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    Intent intent = new Intent(getActivity(),Home.class);
                    startActivity(intent);
                }

            }
        };


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });

        return rootView;
    }

    public void startLogin()
    {
        final String email = emailInput.getText().toString().trim();
        String pass = passInput.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
        {
            Toast.makeText(getActivity(),"Field empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Signing In");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Signed In",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getActivity(),Home.class);
                        startActivity(i);
                       /* boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                        if (isNew)
                        {
                            Intent i = new Intent(getActivity(),ChooseSports.class);
                            startActivity(i);
                        }
                        else
                        {
                            Intent i = new Intent(getActivity(),Home.class);
                            startActivity(i);
                        }*/

                    }
                }
            });

        }
    }


}