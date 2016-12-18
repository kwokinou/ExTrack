package umbf16cs443.extrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Sethiyaji on 05-11-2016.
 */
public class ShowReceiptFullImage extends AppCompatActivity {

    private ImageView full_receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        setTitle("Receipt");

        full_receipt = (ImageView)findViewById(R.id.receipt_full_image);
        PhotoViewAttacher photoAttacher = new PhotoViewAttacher(full_receipt);
        photoAttacher.update();
        Glide.with(this).load(getIntent().getStringExtra("imagePath")).asBitmap().into(full_receipt);
    }
}
