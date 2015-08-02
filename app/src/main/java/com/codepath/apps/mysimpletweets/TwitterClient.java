package com.codepath.apps.mysimpletweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "EQao6v6ttp6O9KDOSnt9Yr07w";       // Change this
	public static final String REST_CONSUMER_SECRET = "nEfIW4vo2W476YIQolNw4LHN1GFxrt7iLXrLlebnAJuJgT8PX4"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://MyTSTweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// Method == Endpoint

	//HomeTimeLine == Gets us the Home timeline
	//Getstatuses/home_timeline.json
	//		count=25
	//since_id= 1
	public void getHomeTimeline(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// specify the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		// Execute the request
		getClient().get(apiUrl, params, handler);
	}

    public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        // specify the params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        // Execute the request
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        // specify the params
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("screen_name", screenName);
        // Execute the request
        getClient().get(apiUrl, params, handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, null, handler);
    }
    //compose tweet


	//

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    /**
     * Created by Ing Djason(Admin) on 21-Jul-15.
     */

    // taking in tweet objects and turning them into views display in the list
    public static class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
        public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
            super(context,0, tweets);
        }

        //override and custome template
        //view older pattern
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //1.Get the tweet
            Tweet tweet = getItem(position);
            //2.find or inflate the template
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            }
            //3.find the subviews to fill with data in the template
            ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            //4.populate data into subviews
            tvUserName.setText(tweet.getUser().getScreenname());
            tvBody.setText(tweet.getBody());
            ivProfileImage.setImageResource(android.R.color.transparent); // clear out the old image for a ...
            Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

            //5.return the view to be inserted into the list
            return convertView;
        }

    }
}