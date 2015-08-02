package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ing Djason(Admin) on 31-Jul-15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the client
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    //Send an api request to get the timeline json
    // fill the listview by creating the tweets objects from json
    private void populateTimeline() {

        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            //success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                // deserialize json
                //create models and add them to the adapter
                //load the model data into listview
                addAll(Tweet.fromJSONArray(json));

            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
