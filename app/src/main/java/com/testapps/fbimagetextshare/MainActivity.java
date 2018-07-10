package com.testapps.fbimagetextshare;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

public class MainActivity extends AppCompatActivity implements FacebookCallback<Sharer.Result> {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    TextView tvShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShare = (TextView) findViewById(R.id.tvShare);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        tvShare.setOnClickListener(shareClickListener);

    }

    View.OnClickListener shareClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Share url with quote
            //shareURL();

            // Share photo with hash tags
            sharePhoto();
        }
    };

    private void sharePhoto() {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.image))
                .build();

        ShareContent shareContent = new ShareMediaContent.Builder()
                .addMedium(photo)
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#YourHasTagHere")
                        .setHashtag("#YourHasTagHere2")
                        .setHashtag("#YourHasTagHere3")
                        .build())
                .build();
        shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);
    }

    private void shareURL() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setQuote("Subway Surfers. here is the text you may want to add in the post.")
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.kiloo.subwaysurf")) // Post url here
                .build();
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(Sharer.Result result) {
        Log.d("Success", "" + result.getPostId());
    }

    @Override
    public void onCancel() {
        Log.d("cancel", "Cancel");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("error", "" + error.getLocalizedMessage());
    }
}
