package vn.edu.ntu.ngocnhan.appnewsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import vn.edu.ntu.ngocnhan.appnewsreader.model.News;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ReadHtmlUrl().execute("https://vnexpress.net");
    }

    private static class ReadHtmlUrl extends AsyncTask<String,Void,ArrayList<News>>{

        @SuppressLint("SimpleDateFormat")
        @Override
        protected ArrayList<News> doInBackground(String... strings) {

            ArrayList<News> arrayNews = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(strings[0]).get();

                Element element = doc.getElementsByClass("col-left col-small").get(0);

                for (Element node : element.children()){
                    Element eTitle = node.getElementsByClass("title-news").first();
                    Element eTitle1 = eTitle.getElementsByTag("a").first();

                    Element eImage = node.getElementsByClass("thumb-art").first();
                    Element eImage1 = eImage.getElementsByTag("img").first();

                    Element eDescription = node.getElementsByClass("description").first();

                    News news = new News();
                    news.setTitle(eTitle1.attr("title"));
                    news.setPicture(eImage1.attr("src"));
                    news.setDescription(eDescription.text());
                    arrayNews.add(news);

                    Log.d("xzczxczxczd","Title: " + eTitle1.attr("title"));
                    Log.d("xzczxczxczd","Image: " + eImage1.attr("src"));
                    Log.d("xzczxczxczd","Description: " + eDescription.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return arrayNews;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);


        }
    }
}
