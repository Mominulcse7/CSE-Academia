package com.cseacademia.mominulcse1213.cseacademia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mominulcse1213 on 9/27/2018.
 */

public class PostList  extends ArrayAdapter<PostConstructor> {
    private Activity context;
    List<PostConstructor> posts;

    public PostList(Activity context, List<PostConstructor> posts) {
        super(context, R.layout.post_list_show, posts);
        this.context = context;
        this.posts = posts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.post_list_show, null, true);

        TextView textViewSession = (TextView) listViewItem.findViewById(R.id.postsessionlayout);
        TextView textViewPosIn = (TextView) listViewItem.findViewById(R.id.postingtimelayout);
        TextView textViewPostOut = (TextView) listViewItem.findViewById(R.id.postouttimelayout);
        TextView textViewPostDes = (TextView) listViewItem.findViewById(R.id.postdeslayout);

        PostConstructor post = posts.get(position);
        textViewSession.setText(post.getPostSession());
        textViewPosIn.setText(post.getPostIngTime());
        textViewPostOut.setText(post.getPostTimeOut());
        textViewPostDes.setText(post.getPostDes());

        return listViewItem;
    }
}
