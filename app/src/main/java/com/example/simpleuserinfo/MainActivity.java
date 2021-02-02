package com.example.simpleuserinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextName, mEditTextSurname;
    RadioButton mRadioSingle, mRadioMarried;
    RadioGroup mRadioGroupMedeni;
    Switch mSwitchHobby;
    CheckBox mCheckSport, mCheckDance, mCheckMusic, mCheckCinema;
    Button mButtonShow;
    TextView mTextShow, mTextAsteriks1, mTextAsteriks2, mTextAsteriks3, mTextAsteriks4;
    GridLayout mGridHobby;

    StringBuffer hobbies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myInitComponents();


        mSwitchHobby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideKeybaord(buttonView); //basılınca klavyenin gizlenmesi için
                if(!isChecked){
                    disableCheckbox(mGridHobby,false);

                }else{
                    disableCheckbox(mGridHobby,true);

                }
            }
        });

        //radiio buttonlara tıklandığında klavyenin gizlenmesi için
        mRadioGroupMedeni.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideKeybaord(group); //klavyenin gizlendiği metodun çağırımı
            }
        });

        mGridHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybaord(v);
            }
        });

        mButtonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] errorBool = new boolean[]{true, true, true, true};
                String errors = "";

                hobbies.delete(0,hobbies.length());
                if(mSwitchHobby.isChecked()){
                    if(mCheckCinema.isChecked())
                        hobbies.append(mCheckCinema.getText().toString() + ", ");
                    if(mCheckDance.isChecked())
                        hobbies.append(mCheckDance.getText().toString() + ", ");
                    if(mCheckMusic.isChecked())
                        hobbies.append(mCheckMusic.getText().toString() + ", ");
                    if(mCheckSport.isChecked())
                        hobbies.append(mCheckSport.getText().toString() + ", ");
                    if(mCheckCinema.isChecked() || mCheckDance.isChecked() || mCheckMusic.isChecked() || mCheckSport.isChecked()){
                        hobbies.delete(hobbies.length()-2,hobbies.length());
                        mTextAsteriks4.setVisibility(View.GONE);
                        errorBool[3] = false;
                    }
                    else {
                        mTextAsteriks4.setVisibility(View.VISIBLE);
                        mTextAsteriks4.setTextColor(getResources().getColor(R.color.red));
                        errorBool[3] = true;
                        errors += "\n* Hobi seçimi yapmanız gerekmektedir";
                    }
                }else{
                    mTextAsteriks4.setVisibility(View.GONE);
                    hobbies.append("yok"); //hobi yok seçeceği seçildi ise
                    errorBool[3] = false;
                }


                String medeniDurum;
                if(mRadioGroupMedeni.getCheckedRadioButtonId() == R.id.radioEvli){
                    medeniDurum = mRadioMarried.getText().toString();
                    errorBool[2] = false;
                    mTextAsteriks3.setVisibility(View.GONE);
                }else if(mRadioGroupMedeni.getCheckedRadioButtonId() == R.id.radioBekar){
                    medeniDurum= mRadioSingle.getText().toString();
                    errorBool[2] = false;
                    mTextAsteriks3.setVisibility(View.GONE);
                }
                else{
                    medeniDurum = "belirtilmedi";
                    mTextAsteriks3.setVisibility(View.VISIBLE);
                    mTextAsteriks3.setTextColor(getResources().getColor(R.color.red));
                    errorBool[2] = true;
                    errors += "\n* Medeni durum seçimi yapmanız gerekmektedir";
                }

                if(mEditTextSurname.getText().toString().isEmpty() || mEditTextName.getText().toString().isEmpty()){
                    if(mEditTextName.getText().toString().isEmpty()){
                        mTextAsteriks1.setVisibility(View.VISIBLE);
                        mTextAsteriks1.setTextColor(getResources().getColor(R.color.red));
                        errorBool[0] = true;
                        errors += "\n* Adınızı girmeniz gerekmektedir";
                    }
                    else{
                        mTextAsteriks1.setVisibility(View.INVISIBLE);
                        errorBool[0] = false;
                    }
                    if(mEditTextSurname.getText().toString().isEmpty()){
                        mTextAsteriks2.setVisibility(View.VISIBLE);
                        mTextAsteriks2.setTextColor(getResources().getColor(R.color.red));
                        errorBool[1] = true;
                        errors += "\n* Soyadınızı girmeniz gerekmektedir";
                    }
                    else{
                        mTextAsteriks2.setVisibility(View.INVISIBLE);
                        errorBool[1] = false;
                    }
                }
                else{
                    mTextAsteriks2.setVisibility(View.INVISIBLE);
                    errorBool[1] = false;

                    mTextAsteriks1.setVisibility(View.INVISIBLE);
                    errorBool[0] = false;
                }

                int errorCounter = 0;
                for(int i = 0; i < errorBool.length; i++){
                    if(errorBool[i] == true)
                        errorCounter += 1;
                }
                if(errorCounter > 0){
                    mTextShow.setText(errors);
                }else{
                    mTextShow.setText("İsim: " + mEditTextName.getText().toString() +
                            "\nSoyisim: " + mEditTextSurname.getText().toString() + "\nMedeni Durum: " + medeniDurum +
                            "\nHobiler: " + hobbies );
                }



                hideKeybaord(v); // butona tıklandıktan sonra klayveyeyi saklamak için metot çağırımı
            }
        });
    }

    public void myInitComponents(){
        mEditTextName = (EditText) findViewById(R.id.editTextAd);
        mEditTextSurname = (EditText) findViewById(R.id.editTextSoyad);
        mRadioSingle = (RadioButton) findViewById(R.id.radioBekar);
        mRadioMarried = (RadioButton) findViewById(R.id.radioEvli);
        mRadioGroupMedeni = (RadioGroup) findViewById(R.id.radioGrupMedeni);
        mGridHobby = (GridLayout) findViewById(R.id.gridHobi);
        mSwitchHobby = (Switch) findViewById(R.id.switchHobi);
        mCheckSport = (CheckBox) findViewById(R.id.checkboxSpor);
        mCheckDance = (CheckBox) findViewById(R.id.checkboxDans);
        mCheckMusic = (CheckBox) findViewById(R.id.checkboxMusic);
        mCheckCinema = (CheckBox) findViewById(R.id.checkboxSinema);
        mButtonShow = (Button) findViewById(R.id.buttonYazdir);
        mTextShow = (TextView) findViewById(R.id.textGöster);
        mTextAsteriks1 = (TextView) findViewById(R.id.textAsteriks1);
        mTextAsteriks2 = (TextView) findViewById(R.id.textAsteriks2);
        mTextAsteriks3 = (TextView) findViewById(R.id.textAsteriks3);
        mTextAsteriks4 = (TextView) findViewById(R.id.textAsteriks4);

        hobbies = new StringBuffer();
    }

    //klavyeyi saklamak için metot
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    public void disableCheckbox(ViewGroup viewTree, boolean condition){
        View child;
        for(int i = 0; i < viewTree.getChildCount(); i++){
            child = viewTree.getChildAt(i);
            if(child instanceof ViewGroup){
                disableCheckbox((ViewGroup)child,condition);
            }
            else if(child instanceof CheckBox){
                ((CheckBox)child).setEnabled(condition);
            }
        }
    }
}