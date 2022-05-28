package ps.school.numbersgamefinalproject.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ps.school.numbersgamefinalproject.R;
import ps.school.numbersgamefinalproject.constant.Constant;
import ps.school.numbersgamefinalproject.database.DBHelper;

public class register_Activity extends AppCompatActivity {
    ImageView edit_img;
    RadioGroup radio_group;
    TextInputLayout input_full_name, input_email_address, input_user_name, input_password_register, input_password_re;
    RadioButton male, female;
    Button save;
    String date;
    Spinner spinner_country;
    DBHelper DB;
    TextView dateBaker;

    public static final int requests_code = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit_img = findViewById(R.id.edit_img);
        input_full_name = findViewById(R.id.full_name);
        input_email_address = findViewById(R.id.email_address);
        input_user_name = findViewById(R.id.user_name);
        input_password_register = findViewById(R.id.password);
        input_password_re = findViewById(R.id.password_re);

        radio_group = findViewById(R.id.radio_group);
        spinner_country = findViewById(R.id.spinner_country);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        save = findViewById(R.id.save);
        dateBaker = findViewById(R.id.date_baker_register);
        DB = new DBHelper(this);


        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animeion2);
        edit_img.startAnimation(animation1);
        input_full_name.startAnimation(animation1);
        input_email_address.startAnimation(animation1);
        input_user_name.startAnimation(animation1);
        input_password_register.startAnimation(animation1);
        input_password_re.startAnimation(animation1);
        spinner_country.startAnimation(animation1);
        radio_group.startAnimation(animation1);
        male.startAnimation(animation1);
        female.startAnimation(animation1);
        dateBaker.startAnimation(animation1);
        save.startAnimation(animation1);

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        edit_img.setImageURI(result);

                    }
                }
        );
        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcher.launch("image/*");

            }
        });


//
//                edit_img.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        i.setType("image/*");
//                        startActivityForResult(i, requests_code);
//
//
//                    }
//                });

        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SAVE, Context.MODE_PRIVATE);
        boolean s1 = sharedPreferences.getBoolean(Constant.LOGIN, false);
        if (s1) {
            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
            finish();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String userName = input_user_name.getEditText().getText().toString().trim();
                String emailAddress = input_email_address.getEditText().getText().toString().trim();
                String fullName = input_full_name.getEditText().getText().toString().trim();
                String password_now = input_password_register.getEditText().getText().toString();
                String rePassword = input_password_re.getEditText().getText().toString();


//
                if (validateFullName(fullName) && validateEmail(emailAddress) && validateUserName(userName) && validatePassword(password_now, rePassword)) {


                    if (validateGender().equals("null")) {
                        Toast.makeText(getApplicationContext(), R.string.gender, Toast.LENGTH_SHORT).show();
                        String gender = String.valueOf(radio_group.getCheckedRadioButtonId());
                        Toast.makeText(getApplicationContext(), gender, Toast.LENGTH_SHORT).show();

                    } else {
                        Boolean insert = false;
                        Boolean checkuser = DB.checkusername(userName);
                        if (!checkuser) {
                            insert = DB.insertData(userName, password_now);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.the_user_name_is_already_Exists, Toast.LENGTH_SHORT).show();
                        }
                        Boolean insertDetails = DB.insertDetails(userName, fullName, emailAddress, date, "", validateGender(), "0");
                        if (insert && insertDetails) {
                            Toast.makeText(getApplicationContext(), R.string.registered_successfully, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putBoolean(Constant.REMEMBER, radio_group.isClickable());
                            myEdit.putString(Constant.USER, userName);
                            myEdit.putString(Constant.PASSWORD, password_now);
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
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                DatePickerDialog datePickerDialog = new DatePickerDialog(register_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int
                                                          dayOfMonth) {
                                Toast.makeText(register_Activity.this, "" + dayOfMonth + "/"
                                                + (monthOfYear + 1) + "/" + year,
                                        Toast.LENGTH_SHORT).show();
                                String dateString = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                Date d1 = null;
                                try {
                                    d1 = df.parse(dateString);
                                    dateBaker.setText(d1.toString());
                                    Log.e("Test", d1.toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date currentTime = Calendar.getInstance().getTime();
                                printDifference(d1, currentTime);
                                date = String.valueOf(printDifference(d1, currentTime));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


    }


    public long printDifference(Date startDate, Date endDate) {
//milliseconds
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long yearsInMilli = daysInMilli * 365;
        long elapsedYears = different / yearsInMilli;
        Log.e("elapsedYears", elapsedYears + "");
        return elapsedYears;
    }

//هين حطينا لما يضغط على الشهر يظهر في Textview اسم الشهر


    private Boolean validateUserName(String userName) {

        if (userName.isEmpty()) {
            input_user_name.setError(getString(R.string.enterName));
            return false;
        }
        input_user_name.setError(null);
        return true;
    }

    private Boolean validateFullName(String fullName) {

        if (fullName.isEmpty()) {
            input_full_name.setError(getString(R.string.enterfullname));
            return false;
        }
        input_full_name.setError(null);
        return true;
    }

    private Boolean validateEmail(String email) {

        if (email.isEmpty()) {
            input_email_address.setError(getString(R.string.emailAddress));
            return false;
        }
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            input_email_address.setError(getString(R.string.emailAddress));
            return false;
        }
        input_email_address.setError(null);
        return true;
    }

    private String validateGender() {

        String gender = String.valueOf(radio_group.getCheckedRadioButtonId());
        if (gender.equals("2131362061")) {

            return "male";
        } else if (gender.equals("2131361992")) {
            return "female";
        } else if (gender.equals("-1")) {
            return "null";
        }
        return "null";
    }


    private Boolean validatePassword(String password_now, String rePassword) {
        if (password_now.isEmpty()) {
            input_password_register.setError(getString(R.string.userpasseord));
            return false;
        }
        if (rePassword.isEmpty()) {
            input_password_re.setError(getString(R.string.userpasseordre));
            return false;
        }
        if (!(password_now.equals(rePassword))) {
            input_password_re.setError(getString(R.string.comformPassword));
            input_password_register.setError(getString(R.string.comformPassword));
            return false;
        }
        if (password_now.length() < 8) {
//            في مشكلة انجليزي
            input_password_register.setError(getString(R.string.min_eight_characters));
            return false;
        }
        input_password_register.setError(null);
        input_password_re.setError(null);
        return true;
    }
}
