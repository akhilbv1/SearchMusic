package android.searchmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.searchmusic.MainApplication;
import android.searchmusic.R;
import android.searchmusic.adapter.ArtistSearchListAdapter;
import android.searchmusic.dto.ArtistSearchResponse;
import android.searchmusic.dto.MainSearchResponse;
import android.searchmusic.helpers.InternetConnection;
import android.searchmusic.helpers.ProgressDialogBar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistSearchActivity extends AppCompatActivity implements ArtistSearchListAdapter.OnArtistItemClickListener {

    private EditText etSearchArtist;

    private RecyclerView rvArtists;

    private TextView tvNoData;

    private LinearLayout llSearch;

    private ArtistSearchListAdapter artistSearchAdapter;

    private List<ArtistSearchResponse> artistSearchList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_search);

        viewsInitialization();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.artists);
        setSupportActionBar(toolbar);

        // On search edit text change listener
        etSearchArtist.setOnEditorActionListener(new SearchOnEditorActionListener());
        etSearchArtist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString().trim())) {

                    if (InternetConnection.isNetworkAvailable(ArtistSearchActivity.this)) {

                        rvArtists.setVisibility(View.VISIBLE);
                        llSearch.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);

                        getSearchArtistList(etSearchArtist.getText().toString().trim());

                    } else {

                        rvArtists.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(getResources().getString(R.string.no_internet_connection));

                    }
                } else {

                    rvArtists.setVisibility(View.GONE);
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText("");
                }
            }
        });

        //Tap to retry internet connection
        tvNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tvNoData.getText().toString().trim().contains("Retry")) {

                    if (InternetConnection.isNetworkAvailable(ArtistSearchActivity.this)) {

                        rvArtists.setVisibility(View.VISIBLE);
                        llSearch.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);

                        getSearchArtistList(etSearchArtist.getText().toString().trim());

                    } else {

                        rvArtists.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(getResources().getString(R.string.no_internet_connection));

                    }
                }

            }
        });

    }

    @Override
    public void onClickArtistListItem(ArtistSearchResponse artistSearchResponse) {

        Intent intent = new Intent(ArtistSearchActivity.this, ArtistDetailsActivity.class);
        intent.putExtra("ArtistName", artistSearchResponse.getArtistName());
        startActivity(intent);

    }

    /*search by using keyboard search button*/
    private class SearchOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String searchText = etSearchArtist.getText().toString().trim();

                if (!TextUtils.isEmpty(searchText)) {
                    // Check if Internet connection is available or not
                    if (InternetConnection.isNetworkAvailable(ArtistSearchActivity.this)) {

                        rvArtists.setVisibility(View.VISIBLE);
                        etSearchArtist.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);

                        getSearchArtistList(searchText);

                    } else {

                        rvArtists.setVisibility(View.GONE);
                        etSearchArtist.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(getResources().getString(R.string.no_internet_connection));

                    }

                }

                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }

    /*get Artists list service*/
    public void getSearchArtistList(final String searchStr) {

        final ProgressDialogBar dialogProgress = new ProgressDialogBar(ArtistSearchActivity.this);
        dialogProgress.showProgressDialog(getString(R.string.loading));

        final Call<MainSearchResponse> artistsResponse = MainApplication.getRestClient().getRestServices().getArtistSearchList("artist.search", searchStr, "fa2e62987b8c372e16daa60331164d12", "json");

        artistsResponse.enqueue(new Callback<MainSearchResponse>() {
            @Override
            public void onResponse(Call<MainSearchResponse> responseCall, Response<MainSearchResponse> response) {

                dialogProgress.dismissProgressDialog();

                artistSearchList = new ArrayList<>();
                artistSearchList = response.body().getMatchResponse().getArtistListResponse().getSearchResponse();

                if (response.code() == 200) {

                    if (artistSearchList != null && artistSearchList.size() > 0) {

                        artistSearchAdapter = new ArtistSearchListAdapter(ArtistSearchActivity.this, artistSearchList, ArtistSearchActivity.this);
                        rvArtists.setAdapter(artistSearchAdapter);

                        rvArtists.setVisibility(View.VISIBLE);
                        //etSearchArtist.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);


                    } else {

                        rvArtists.setVisibility(View.GONE);
                        //etSearchArtist.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(getResources().getString(R.string.no_results_found));
                    }

                } else {
                    Toast.makeText(ArtistSearchActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MainSearchResponse> call, @NonNull Throwable t) {

                dialogProgress.dismissProgressDialog();
                Toast.makeText(ArtistSearchActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Initializing views
    private void viewsInitialization() {

        etSearchArtist = findViewById(R.id.etSearchArtist);
        tvNoData = findViewById(R.id.tvNoData);
        ImageView ivSearch = findViewById(R.id.ivSearch);
        llSearch = findViewById(R.id.llSearch);
        // initializing  the RecyclerView
        rvArtists = findViewById(R.id.rvArtists);
        rvArtists.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvArtists.setLayoutManager(mLayoutManager);

    }
}
