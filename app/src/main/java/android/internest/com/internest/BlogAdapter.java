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

import java.util.List;

/**
 * Created by stephnoutsa on 8/5/16.
 */
public class BlogAdapter extends ArrayAdapter<Post> {

    public BlogAdapter(Context context, List<Post> posts) {
        super(context, R.layout.custom_blog_row, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_blog_row, parent, false);

        Post singlePostItem = getItem(position);

        ImageView blogThumbnail = (ImageView) customView.findViewById(R.id.blogThumbnail);
        TextView blogTitle = (TextView) customView.findViewById(R.id.blogTitle);
        TextView blogExcerpt = (TextView) customView.findViewById(R.id.blogExcerpt);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");
        blogTitle.setTypeface(font, Typeface.BOLD);
        blogTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        blogExcerpt.setTypeface(font);
        blogExcerpt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBlack));

        blogThumbnail.setImageResource(R.drawable.fried_plantains);
        blogTitle.setText("The Magic of Fried Plantains");
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque auctor," +
                "diam scelerisque lacinia malesuada, quam libero convallis elit, et laoreet sapien lacus" +
                "vel dolor. Vivamus sit amet lorem porta nisi pharetra convallis id non magna. Sed volutpat" +
                "leo in enim feugiat tincidunt. Nunc venenatis erat erat, at auctor dui scelerisque a." +
                "Sed congue leo id magna consequat, eget volutpat felis vestibulum. Maecenas gravida" +
                "ultricies convallis. In hac habitasse platea dictumst. Curabitur a venenatis libero," +
                "ut dignissim libero. Donec tempor sem in leo pharetra imperdiet. Nam ac purus fermentum" +
                "augue auctor lacinia. Praesent in egestas augue. Vestibulum nibh neque, fermentum fermentum" +
                "vulputate sit amet, pulvinar eget est. In et eleifend erat, at maximus arcu. Vivamus" +
                "id nisi sit amet libero ultrices facilisis. Quisque eget tellus sit amet magna cursus" +
                "vulputate vel et ex. Morbi quis urna varius dolor mattis pellentesque eu at erat.";
        String trimmed = trimText(text);
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
