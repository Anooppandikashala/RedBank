package anoop.myprojects.redbank;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ShowDonors extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;

    private String BloodGrp="";
    private String District="";
    String JSON_STRING, json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_donors);

        Intent intent = getIntent();
        BloodGrp= intent.getStringExtra("bd").toLowerCase();
        District= intent.getStringExtra("dt").toLowerCase();


        myOnClickListener = new MyOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        String msg="ggg";

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(msg);



    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            callItem(v);
        }

        private void callItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewVersion);
            String selectedName = (String) textViewName.getText();
            /*int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }*/

            Toast.makeText(context, "Selected: " + selectedName, Toast.LENGTH_LONG).show();


        }







    }

    class BackgroundTask extends AsyncTask<String,Void,String>
    {


        String addInfoUrl;




        @Override
        protected void onPreExecute() {

            if(!BloodGrp.isEmpty() && !District.isEmpty()){

                addInfoUrl ="http://redbank.ml/api.php?bg="+BloodGrp+"&district="+District;
            }


        }

        @Override
        protected String doInBackground(String... args) {

            String msg;
            msg=args[0];

            try {
                URL url = new URL(addInfoUrl);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                //httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setDoOutput(true);


                InputStream inputStream =httpURLConnection.getInputStream();
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(inputStream));


                StringBuilder stringBuilder =new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine())!=null){

                    stringBuilder.append(JSON_STRING+"\n");



                }




                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                // AlertDialogManager alertDialogManager=new AlertDialogManager();
                //alertDialogManager.showAlertDialog(MainActivity.this,"Server Error","Sorry Server Connection terminated!",false);

            } catch (IOException e) {
                e.printStackTrace();
                //AlertDialogManager alertDialogManager=new AlertDialogManager();
                //alertDialogManager.showAlertDialog(MainActivity.this,"Network Error","Network Problem Detected",false);

            }

            return null;


        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            //textView.setText(result);
            json_string =result;
            Object dataTransfer[] = new Object[2];
            String url="";
            data = new ArrayList<DataModel>();

            try {
                jsonObject=new JSONObject(json_string);

                jsonArray=jsonObject.getJSONArray("server_response");


                int count=0;

                String sname,bg,phno,place;
                data = new ArrayList<DataModel>();

                while (count<jsonArray.length()){

                    JSONObject JO= jsonArray.getJSONObject(count);

                    sname=JO.getString("name").toUpperCase();
                    bg=JO.getString("bg").toUpperCase();
                    phno=JO.getString("pn");
                    place=JO.getString("place");

                    //{"name":"Reckson","place":"Nilambur","district":"Malappuram","bg":"o+","pn":"8714242173"}






                        data.add(new DataModel(
                                sname,
                                phno,
                                count,
                                MyData.drawableArray[0],
                                place,
                                bg
                        ));


                    //removedItems = new ArrayList<Integer>();

                    count++;


                }
                int l=data.size();
                System.out.println("length of adapter :"+l);
                if(!data.isEmpty()){

                    //progressDialog.dismiss();

                    adapter = new CustomAdapter(data);
                    recyclerView.setAdapter(adapter);


                }
                else {

                   // progressDialog.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ShowDonors.this);
                    builder.setMessage("Donor Details Not Available Now!\n Or Please Try again").setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.setIcon(R.drawable.fail);

                    builder.show();

                }





            } catch (JSONException e) {
                e.printStackTrace();

                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDonors.this);
                builder.setMessage("Server Error!").setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                builder.setIcon(R.drawable.fail);

                builder.show();
                //AlertDialogManager alertDialogManager=new AlertDialogManager();
                //alertDialogManager.showAlertDialog(MainActivity.this,"Server Error","Sorry Server Connection terminated!",false);


            }
            catch (Exception e) {
                e.printStackTrace();
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowDonors.this);
                builder.setMessage("Network or System Problem!").setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //finish();
                    }
                });
                builder.setIcon(R.drawable.fail);

                builder.show();
                //AlertDialogManager alertDialogManager=new AlertDialogManager();
                //alertDialogManager.showAlertDialog(MainActivity.this,"Network or System Problem","Please Try again later",false);
            }




        }
    }
}
