package android.internest.com.internest;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by stephnoutsa on 8/5/16.
 */
public class BlogAdapter extends ArrayAdapter<Post> {

    Context context;

    public BlogAdapter(Context context, List<Post> posts) {
        super(context, R.layout.custom_blog_row, posts);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_blog_row, parent, false);

        Post singlePostItem = getItem(position);
        /*int id = singlePostItem.getId();
        String author = singlePostItem.getAuthor();
        String created = singlePostItem.getCreated();
        String url = singlePostItem.getUrl();*/
        String thumbnail = singlePostItem.getThumbnail();
        String title = singlePostItem.getTitle();
        String body = singlePostItem.getBody();

        ImageView blogThumbnail = (ImageView) customView.findViewById(R.id.blogThumbnail);
        TextView blogTitle = (TextView) customView.findViewById(R.id.blogTitle);
        TextView blogExcerpt = (TextView) customView.findViewById(R.id.blogExcerpt);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");
        blogTitle.setTypeface(font, Typeface.BOLD);
        blogTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        blogExcerpt.setTypeface(font);
        blogExcerpt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

        Picasso.with(context)
                .load(thumbnail)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(blogThumbnail);
        blogTitle.setText(title);
        String trimmed = trimText(body);
        blogExcerpt.setText(trimmed);

        return customView;
    }

    public static String trimText(String text) {

        String trimmed = "";
        int requiredNum = 100;
        int currentNum = text.length();

        if(requiredNum >= currentNum) {
            trimmed = text;
        }
        else {
            for(int i = 0; i < requiredNum; i++) {
                trimmed += text.charAt(i);
            }
            trimmed += "...";
        }

        return trimmed;
    }

}
