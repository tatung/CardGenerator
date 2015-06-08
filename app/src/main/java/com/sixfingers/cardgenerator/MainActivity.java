package com.sixfingers.cardgenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    EditText txtKey;
    EditText txtValue;
    Button btnGen;
    TextView lblStatus;
    ImageView imgGenPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtKey = (EditText) findViewById(R.id.txtKey);
        txtValue = (EditText) findViewById(R.id.txtValue);
        btnGen = (Button) findViewById(R.id.btnGen);
        lblStatus = (TextView) findViewById(R.id.lblStatus);
        imgGenPic = (ImageView) findViewById(R.id.imgGenPic);

        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genPicFromFile("KanjiListPipeDelimiter");

                txtKey.setText("");
                txtValue.setText("");
            }
        });
    }

    private Bitmap genPic(String key, String val) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.white_rect); // the original file yourimage.jpg i added in resources
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);


        Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(300);
        tPaint.setColor(Color.BLACK);
        tPaint.setStyle(Paint.Style.FILL);
        cs.drawBitmap(src, 0f, 0f, null);

        float height = tPaint.measureText("yY");
        float width = tPaint.measureText(key);
        float x_coord = (src.getWidth() - width) / 2;
        float y_coord = (src.getHeight() / 3 + height / 3);
        cs.drawText(key, x_coord, y_coord, tPaint); // 15f is to put space between top edge and the text, if you want to change it, you can

        tPaint.setTextSize(200);
        width = tPaint.measureText(val);
        x_coord = (src.getWidth() - width) / 2;
        y_coord = src.getHeight() * 2 / 3 + height / 3;
        cs.drawText(val, x_coord, y_coord, tPaint);

        return dest;

    }

    private Bitmap genPicFromFile(String fileName) {
//        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.white_rect); // the original file yourimage.jpg i added in resources
        Bitmap dest;// = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        try {
            InputStream in = this.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            int i = 0;

            do {
                line = reader.readLine();
                String[] strs = line.split("\\|");
                Word word = new Word(strs[0], strs[1], strs[2], strs[3], strs[4], strs[5]);
                Log.d("Progress", i++ + ": " + word.get_word());
                dest = genPicFromWord(word);
                Calendar cal = Calendar.getInstance();
                long currentLocalTime = System.currentTimeMillis();
                DateFormat dateFormat = new DateFormat();
                String now = dateFormat.format("yyyyMMdd-hh:mm:ss", currentLocalTime).toString();
                FileOutputStream fos = new FileOutputStream(new File("/sdcard/CardGenerator/" + now + ".jpg"));
                dest.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();


            } while (line != null);

            //line = reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return null;
    }

    private Bitmap genPicFromWord(Word word) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.white_rect); // the original file yourimage.jpg i added in resources
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas cs = new Canvas(dest);
        Paint tPaint = new Paint();
        tPaint.setTextSize(450);
        tPaint.setColor(Color.BLACK);
        tPaint.setStyle(Paint.Style.FILL);
        cs.drawBitmap(src, 0f, 0f, null);

        float hWord = tPaint.measureText("yY");
        float wWord = tPaint.measureText(word.get_word());
        float xWord = wWord / 3;
        float yWord = xWord + hWord / 1.3f;
        cs.drawText(word.get_word(), xWord, yWord, tPaint);

        tPaint.setTextSize(300);
        tPaint.setColor(Color.RED);
        float wHanViet = tPaint.measureText(word.get_meaningViet1());
        float xHanViet = xWord + wWord + (src.getWidth() - xWord - wWord - wHanViet)/2;
        float yHanViet = yWord;
        cs.drawText(word.get_meaningViet1(), xHanViet, yHanViet, tPaint);

        tPaint.setTextSize(130);
        tPaint.setColor(Color.BLACK);
        float wSpace = tPaint.measureText(" ");
        float hOnYomi = tPaint.measureText("yY");
        float wOnYomi = tPaint.measureText(word.get_onYomi());
        float xOnYomi = xWord;
        float yOnYomi = yWord + 1.5f * hOnYomi;
        cs.drawText(word.get_onYomi(), xOnYomi, yOnYomi, tPaint);

        tPaint.setColor(Color.MAGENTA);
        float hKunYomi = tPaint.measureText("yY");
        float wKunYomi = tPaint.measureText(word.get_kunYomi());
        float xKunYomi;
        float yKunYomi;
        if (wOnYomi + wKunYomi < src.getWidth() - xOnYomi) {
            xKunYomi = src.getWidth() - xOnYomi - wKunYomi;
            yKunYomi = yOnYomi;
        } else {
            xKunYomi = src.getWidth() - xOnYomi - wKunYomi;
            yKunYomi = yOnYomi + 1.2f * hKunYomi;
        }

        cs.drawText(word.get_kunYomi(), xKunYomi, yKunYomi, tPaint);

        tPaint.setColor(Color.BLACK);
        float hMean = tPaint.measureText("yY");
        float wMean = tPaint.measureText(word.get_meaningViet2());
        float xMean = (src.getWidth() - wMean) / 2;
        float yMean = yOnYomi + 1.2f * hMean;
        cs.drawText(word.get_meaningViet2(), xMean, yMean, tPaint);

        return dest;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
