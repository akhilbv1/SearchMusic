package android.searchmusic.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.searchmusic.MainApplication;
import android.searchmusic.R;
import android.searchmusic.dto.ArtistDetailsResponse;
import android.searchmusic.dto.DetailsMainResponse;
import android.searchmusic.helpers.InternetConnection;
import android.searchmusic.helpers.ProgressDialogBar;
import android.searchmusic.helpers.ResizableCustomView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import ooo.oxo.library.widget.TouchImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistDetailsActivity extends AppCompatActivity {

    private ImageView ivProfile, ivStream;

    private TextView tvArtistName, tvSummary, tvBioLink, tvAboutUrl;

    private ArtistDetailsResponse detailsResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_details);

        String artistName = getIntent().getStringExtra("ArtistName");

        viewsInitialization();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(artistName + " Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Check network connection and get artist details
        if (InternetConnection.isNetworkAvailable(this)) {
            getArtistDetailsActivity(artistName);
        } else {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    /*get Artists details list service*/
    public void getArtistDetailsActivity(final String artistName) {

        final ProgressDialogBar dialogProgress = new ProgressDialogBar(ArtistDetailsActivity.this);
        dialogProgress.showProgressDialog(getString(R.string.loading));

        final Call<DetailsMainResponse> artistsResponse = MainApplication.getRestClient().getRestServices().getArtistDetails("artist.getinfo", artistName, "fa2e62987b8c372e16daa60331164d12", "json");
        artistsResponse.enqueue(new Callback<DetailsMainResponse>() {
            @Override
            public void onResponse(Call<DetailsMainResponse> responseCall, Response<DetailsMainResponse> response) {

                dialogProgress.dismissProgressDialog();

                detailsResponses = response.body().getDetailsResponse();

                if (response.code() == 200) {

                    if (detailsResponses != null) {

                        setDataToUI(detailsResponses);

                    }
                } else {
                    Toast.makeText(ArtistDetailsActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailsMainResponse> call, Throwable t) {

                dialogProgress.dismissProgressDialog();
                Toast.makeText(ArtistDetailsActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //set data to ui
    private void setDataToUI(ArtistDetailsResponse detailsResponses) {

        final String profileName = detailsResponses.getArtistName();

        final String aboutUrl = detailsResponses.getArtistAboutUrl();

        final String profileSummary = detailsResponses.getBoiData().getSummary();

        final String bioUrl = detailsResponses.getBoiData().getLink().getLinkDetailObject().getHref();

        final String profileImageUrl = detailsResponses.getImageArray().get(2).getImageUrl();

        final String isStreaming = detailsResponses.getStreamable();

        // Set is Streaming
        if (!TextUtils.isEmpty(isStreaming)) {
            if (isStreaming.equals("0")) {
                ivStream.setImageResource(R.drawable.ic_gray_circle);
            } else {
                ivStream.setImageResource(R.drawable.ic_green_circle);
            }
        }

        // set profile name
        if (!TextUtils.isEmpty(profileName)) {
            tvArtistName.setText(profileName);
        }

        // Set profile image
        if (!TextUtils.isEmpty(profileImageUrl)) {
            Glide.with(ArtistDetailsActivity.this)
                    .load(profileImageUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(ivProfile);

            // On click listener for image view
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showFullImageDialog(profileImageUrl);
                }
            });

        } else {

            ivProfile.setImageResource(R.drawable.ic_artist_dummy);
        }

        // set profile Summary
        if (!TextUtils.isEmpty(profileSummary)) {
            tvSummary.setText(profileSummary);

            ResizableCustomView.doResizeTextView(tvSummary, 3, "More", true);

        } else {
            tvSummary.setText("N/A");
        }

        // set profile about Link
        if (!TextUtils.isEmpty(aboutUrl)) {

            final SpannableString spanString = new SpannableString(aboutUrl);
            spanString.setSpan(new UnderlineSpan(), 0, aboutUrl.length(), 0);
            tvAboutUrl.setText(spanString);

            //On click listener for web site
            tvAboutUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ArtistDetailsActivity.this, WebViewActivity.class);
                    intent.putExtra("WebUrl", aboutUrl);
                    startActivity(intent);

                }
            });

        } else {
            tvAboutUrl.setText("N/A");
        }


        // set profile Bio Link
        if (!TextUtils.isEmpty(bioUrl)) {

            final SpannableString spanString = new SpannableString(bioUrl);
            spanString.setSpan(new UnderlineSpan(), 0, bioUrl.length(), 0);
            tvBioLink.setText(spanString);

            //On click listener for web site
            tvBioLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ArtistDetailsActivity.this, WebViewActivity.class);
                    intent.putExtra("WebUrl", bioUrl);
                    startActivity(intent);

                }
            });

        } else {
            tvBioLink.setText("N/A");
        }

    }

    /*Show full image on clicking status image view*/
    protected void showFullImageDialog(String imageUrl) {

        final View dialogView = View.inflate(ArtistDetailsActivity.this, R.layout.dialog_image_zoom, null);

        final Dialog dialog = new Dialog(ArtistDetailsActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        TouchImageView fullImgVw = dialog.findViewById(R.id.ivZoom);

        if (!TextUtils.isEmpty(imageUrl)) {

            Glide.with(this).load(imageUrl)
                    .into(fullImgVw);
        } else {

            Toast.makeText(ArtistDetailsActivity.this, R.string.image_not_found, Toast.LENGTH_SHORT).show();
        }


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        dialog.show();
    }

    /*Dialog animation using reveal show*/
    private void revealShow(View rootView, boolean reveal, final Dialog dialog) {
        final View view = rootView.findViewById(R.id.reveal_view);
        int w = view.getWidth();
        int h = view.getHeight();
        float maxRadius = (float) Math.sqrt(w * w / 4 + h * h / 4);

        if (reveal) {
            Animator revealAnimator = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                revealAnimator = ViewAnimationUtils.createCircularReveal(view,
                        w / 2, h / 2, 0, maxRadius);
            }
            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();
        } else {
            Animator anim = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                anim = ViewAnimationUtils.createCircularReveal(view, w / 2, h / 2, maxRadius, 0);
            }

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });

            anim.setDuration(700);
            anim.start();
        }
}

    //Views Initialization
    private void viewsInitialization() {

        ivProfile = findViewById(R.id.ivProfile);
        ivStream = findViewById(R.id.ivStream);
        tvArtistName = findViewById(R.id.tvArtistName);
        tvSummary = findViewById(R.id.tvSummary);
        tvBioLink = findViewById(R.id.tvBioLink);
        tvAboutUrl = findViewById(R.id.tvAboutUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
            default:

                return super.onOptionsItemSelected(item);

        }

    }
}
