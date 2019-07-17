package anoop.myprojects.redbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerBlood,spinnerDistrict;
    String BloodGrp="";
    String District="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Search Nearest Hospital", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        // Spinner element
        spinnerBlood = (Spinner) findViewById(R.id.spinnerBlood);
        // Spinner element
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);

        // Spinner click listener
        spinnerBlood.setOnItemSelectedListener(this);
        // Spinner click listener
        spinnerDistrict.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("A +ve");
        categories.add("B +ve");
        categories.add("AB +ve");
        categories.add("O +ve");
        categories.add("A -ve");
        categories.add("B -ve");
        categories.add("AB -ve");
        categories.add("O -ve");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerBlood.setAdapter(dataAdapter);

        // Spinner Drop down elements
        List<String> categoriesDistrict = new ArrayList<String>();
        categoriesDistrict.add("Alappuzha");
        categoriesDistrict.add("Ernakulam");
        categoriesDistrict.add("Iduki");
        categoriesDistrict.add("Kannur");
        categoriesDistrict.add("Kasaragod");
        categoriesDistrict.add("Kollam");
        categoriesDistrict.add("Kottayam");
        categoriesDistrict.add("Kozhikode");

        categoriesDistrict.add("Malappuram");
        categoriesDistrict.add("Palakkad");
        categoriesDistrict.add("Pathanamthitta");
        categoriesDistrict.add("Thiruvananthapuram");
        categoriesDistrict.add("Thrissur");
        categoriesDistrict.add("Wayanad");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterDistrict = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesDistrict);

        // Drop down layout style - list view with radio button
        dataAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDistrict.setAdapter(dataAdapterDistrict);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int x=(int)id;
        System.out.println("Id="+x);
        System.out.println("Pos="+position);

        System.out.println("view id="+view.getId());

        String item = "";//parent.getItemAtPosition(position).toString();

        Spinner sp=(Spinner) parent;

        if(sp.getId()==R.id.spinnerBlood){

            System.out.println("hello");

            switch (x){

                case 0:item="ap";
                        break;
                case 1:item="bp";
                    break;
                case 2:item="abp";
                    break;
                case 3:item="op";
                    break;
                case 4:item="an";
                    break;
                case 5:item="bn";
                    break;
                case 6:item="abn";
                    break;
                case 7:item="on";
                    break;



            }

           BloodGrp=item;

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + BloodGrp, Toast.LENGTH_LONG).show();


        }
        if(sp.getId()==R.id.spinnerDistrict){

            System.out.println("hi");

            String itemd = parent.getItemAtPosition(position).toString().toLowerCase();

            District=itemd;

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + District, Toast.LENGTH_LONG).show();


        }






    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void yourDetails(View view){

       // Intent intent=new Intent(MainActivity.this,YourDetails.class);
        //startActivity(intent);


    }
    public void search(View view){

        if(!BloodGrp.isEmpty() && !District.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, ShowDonors.class);
            intent.putExtra("bd", BloodGrp);
            intent.putExtra("dt", District);
            startActivity(intent);

        }
        else {
            Toast.makeText(MainActivity.this, "Please Select a Blood Group and District", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent =new Intent(MainActivity.this,Registration.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
