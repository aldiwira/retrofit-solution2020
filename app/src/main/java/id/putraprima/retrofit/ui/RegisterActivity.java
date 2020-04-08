package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.net.Inet4Address;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.helper.NotifMaker;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText eName, eEmail, ePassword, eConfirmPassword;
    String nama, email, password, c_password;
    RegisterRequest registerRequest;
    Intent intent;
    Boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bind_Layout();
        setup_data();
    }

    private void bind_Layout(){
        eName = findViewById(R.id.eName);
        eEmail = findViewById(R.id.eEmail);
        ePassword = findViewById(R.id.ePassword);
        eConfirmPassword = findViewById(R.id.eConfirm);
    }
    private void setup_data(){
        nama = eName.getText().toString();
        email = eEmail.getText().toString();
        password = ePassword.getText().toString();
        c_password = eConfirmPassword.getText().toString();
    }

    private void doRegister(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        registerRequest = new RegisterRequest(nama, email, password, c_password);
        Call<Envelope<RegisterResponse>> call = apiInterface.doRegister(registerRequest);
        call.enqueue(new Callback<Envelope<RegisterResponse>>() {
            @Override
            public void onResponse(Call<Envelope<RegisterResponse>> call, Response<Envelope<RegisterResponse>> response) {
                if (response.isSuccessful()){
                    status = true;
                    snackbarmaker("Register Sukses");
                    Toast.makeText(RegisterActivity.this, response.body().toString(), Toast.LENGTH_LONG);
                }else {
                    status = false;
                    ApiError error = ErrorUtils.parseError(response);
                    snackbarmaker(error.getError().getName().get(0));

                }
            }

            @Override
            public void onFailure(Call<Envelope<RegisterResponse>> call, Throwable t) {
                status = false;
                snackbarmaker("Gagal Koneksi");
            }
        });
    }
    private void snackbarmaker(String arg){
        Snackbar.make(getWindow().getDecorView().getRootView(), arg, Snackbar.LENGTH_SHORT).show();
    }
    public void handleRegisterProcess(View view) {
        doRegister();
        if(status) {
            intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
