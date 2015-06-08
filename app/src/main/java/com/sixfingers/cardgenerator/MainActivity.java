package com.sixfingers.cardgenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends ActionBarActivity {

    EditText txtKey;
    EditText txtValue;
    Button btnGen;
    ImageView imgGenPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtKey = (EditText) findViewById(R.id.txtKey);
        txtValue = (EditText) findViewById(R.id.txtValue);
        btnGen = (Button) findViewById(R.id.btnGen);
        imgGenPic = (ImageView) findViewById(R.id.imgGenPic);

        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap pic = genPic(txtKey.getText().toString(), txtValue.getText().toString());
//                Bitmap pic = genPic("日本語", "tiếng Nhật");
                imgGenPic.setImageBitmap(pic);
                try {
                    Calendar cal = Calendar.getInstance();
                    long currentLocalTime = System.currentTimeMillis();
                    DateFormat dateFormat = new DateFormat();
                    String now = dateFormat.format("yyyyMMdd-hh:mm:ss", currentLocalTime).toString();
                    FileOutputStream fos = new FileOutputStream(new File("/sdcard/CardGenerator/" + now + ".jpg"));
                    pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    // dest is Bitmap, if you want to preview the final image, you can display it on screen also before saving
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
