package ps.school.numbersgamefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class register_Activity extends AppCompatActivity {
    ImageView edit_img;
    EditText full_name, email_address, user_name, _password, password_re;
    RadioGroup radio_group;
    RadioButton male, female;
    Button save;
    String date;
    Spinner spinner_country;
    DBHelper DB;
    DatePickerDialog datePickerDialog;
    TextView dateBaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit_img = findViewById(R.id.edit_img);
        full_name = findViewById(R.id.full_name);
        email_address = findViewById(R.id.email_address);
        user_name = findViewById(R.id.user_name);
        _password = findViewById(R.id.password);
        password_re = findViewById(R.id.password_re);
        radio_group = findViewById(R.id.radio_group);
        spinner_country = findViewById(R.id.spinner_country);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        save = findViewById(R.id.save);
        dateBaker = findViewById(R.id.date_baker_register);
        DB = new DBHelper(this);

        dateBaker.setText(getTodaysDate());
        initDatePicker();
        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 111);

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Save", Context.MODE_PRIVATE);
        boolean s1 = sharedPreferences.getBoolean("login", false);
        if (s1) {
            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
            finish();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userName = user_name.getText().toString().trim();
                String emailAddress = email_address.getText().toString().trim();
                String fullName = full_name.getText().toString().trim();
                String password_now = _password.getText().toString();
                String rePassword = password_re.getText().toString();


                if (validateUserName(userName) && validateFullName(fullName) && validateEmail(emailAddress) && validatePassword(password_now, rePassword)) {


                    if (validateGender().equals("null")) {
                        Toast.makeText(getApplicationContext(), R.string.gender, Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean insert = false;
                        Boolean checkuser = DB.checkusername(userName);
                        if (!checkuser) {
                            insert = DB.insertData(userName, password_now);
                        } else {
                            Toast.makeText(getApplicationContext(), "the user name is already Exists!!", Toast.LENGTH_SHORT).show();
                        }
                        Boolean insertDetails = DB.insertDetails(userName, fullName, emailAddress, date, "", validateGender());
                        if (insert && insertDetails) {
                            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putBoolean("rem", radio_group.isClickable());
                            myEdit.putString("user", userName);
                            myEdit.putString("password", password_now);
                            myEdit.apply();
                            startActivity(intent);
                            finish();
                        }
                    }

                }
            }

        });

        dateBaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateBaker.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.BUTTON_POSITIVE;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {

        return getMonthFormat(month) + " " + day + " " + year;

    }

//هين حطينا لما يضغط على الشهر يظهر في Textview اسم الشهر


    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

//default شهر JAN
        return "JAN";

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode == register_Activity.RESULT_OK) {
                final Uri uri = data.getData();
                InputStream in;
                try {
                    in = getContentResolver().openInputStream(uri);
                    final Bitmap selected_img = BitmapFactory.decodeStream(in);
                    edit_img.setImageBitmap(selected_img);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "An error occured!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "You didn't pick an image!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean validateUserName(String userName) {

        if (userName.isEmpty()) {
            user_name.setError(getString(R.string.enterName));
            return false;
        }
        user_name.setError(null);
        return true;
    }

    private Boolean validateFullName(String fullName) {

        if (fullName.isEmpty()) {
            full_name.setError(getString(R.string.enterfullname));
            return false;
        }
        full_name.setError(null);
        return true;
    }

    private Boolean validateEmail(String email) {

        if (email.isEmpty()) {
            email_address.setError(getString(R.string.emailAddress));
            return false;
        }
        email_address.setError(null);
        return true;
    }

    private String validateGender() {

        String gender = String.valueOf(radio_group.getCheckedRadioButtonId());
        if (gender.equals("2131362056")) {

            return "male";
        } else if (gender.equals("2131361987")) {
            return "female";
        } else if (gender.equals("-1")) {
            return "null";
        }
        return "null";
    }


    private Boolean validatePassword(String password_now, String rePassword) {
        if (password_now.isEmpty()) {
            _password.setError(getString(R.string.userpasseord));
            return false;
        }
        if (rePassword.isEmpty()) {
            password_re.setError(getString(R.string.userpasseordre));
            return false;
        }
        if (!(password_now.equals(rePassword))) {
            password_re.setError(getString(R.string.comformPassword));
            _password.setError(getString(R.string.comformPassword));
            return false;
        }
        if (password_now.length() < 8) {
            _password.setError("Password is too short (Min. 8 Characters)");
            return false;
        }
        _password.setError(null);
        password_re.setError(null);
        return true;
    }
    //


}
