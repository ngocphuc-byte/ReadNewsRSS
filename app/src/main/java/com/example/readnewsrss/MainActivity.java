package com.example.readnewsrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    ListView lv_title;
    ArrayList<String> arrayTitle;
    ArrayList<String> arrayLink;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_title = (ListView) findViewById(R.id.listView);
        arrayTitle = new ArrayList<>();
        arrayLink = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayTitle);
        lv_title.setAdapter(adapter);
        new ReadNewsRSS().execute("https://vnexpress.net/rss/khoa-hoc.rss");
        lv_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("linkNews",arrayLink.get(position));
                startActivity(intent);
            }
        });

    }
    private class ReadNewsRSS extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line ="";
                while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            try {
                Document document = parser.getDocument(s);
                NodeList nodeList = document.getElementsByTagName("item");
                String title  = "";
                for(int i = 0; i< nodeList.getLength();i++){
                    Element element = (Element) nodeList.item(i);
                    title = parser.getValue(element, "title");
                    arrayTitle.add(title);
                    arrayLink.add(parser.getValue(element, "link"));
                }

                adapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }

        }
    }
}