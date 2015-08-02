package com.codepath.apps.mysimpletweets.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ing Djason(Admin) on 31-Jul-15.
 */
public class TweetsListFragment extends Fragment{
    private ArrayList<Tweet> tweets;
    private TwitterClient.TweetsArrayAdapter aTweets;
    private ListView lvTweets;


    //inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        //find the list view
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //connect adapter to list view
        lvTweets.setAdapter(aTweets);
        return v;
    }

    //creation lifecycle event
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //create the arraylist (data source)
        tweets = new ArrayList<>();
        //construct the adapter from data source
        aTweets = new TwitterClient.TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }

}
