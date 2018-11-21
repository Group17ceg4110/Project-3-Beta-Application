package ceg4110_f18_g19.isitfood.client;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button imagePicker = findViewById(R.id.selectImageButton);
        imagePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        if (requestCode == 42 && resultCode == RESULT_OK)
        {
            Uri uri = null;
            if (resultData != null)
            {
                uri = resultData.getData();
            }
            if (uri != null)
            {
                File file = new File(uri.getPath());
                try
                {
                    FileInputStream stream = new FileInputStream(file);
                    Intent intent = new Intent(this, ImageViewActivity.class);
                    intent.putExtra("ceg4110_f18_g19.isitfood.BITMAP", BitmapFactory.decodeFileDescriptor(stream.getFD()));
                    intent.putExtra("ceg4110_f18_g19.isitfood.RESULT", CommunicationModule.request(file));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "Could not open " + file.getPath() + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
