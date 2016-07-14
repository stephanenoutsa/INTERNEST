package android.internest.com.internest;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class CustomAdapter extends ArrayAdapter<Scanned> {

    public CustomAdapter(Context context, List<Scanned> scans) {
        super(context, R.layout.custom_row, scans);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        Scanned singleScannedItem = getItem(position);

        ImageView scanIcon = (ImageView) customView.findViewById(R.id.scanIcon);
        TextView scanType = (TextView) customView.findViewById(R.id.scanType);
        TextView scanDetails = (TextView) customView.findViewById(R.id.scanDetails);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Magnificent.ttf");
        scanType.setTypeface(font, Typeface.BOLD);
        scanType.setTextColor(customView.getResources().getColor(R.color.colorBlack));
        scanDetails.setTypeface(font);
        scanDetails.setTextColor(customView.getResources().getColor(R.color.colorBlack));

        if(singleScannedItem.getStype().equals(getContext().getString(R.string.scanned_type_url))) {
            scanIcon.setImageResource(R.drawable.link);
            scanType.setText(R.string.url_simple);

            String text = singleScannedItem.getSdetails();
            String trimmed = trimText(text);
            scanDetails.setText(trimmed);
        }
        else if(singleScannedItem.getStype().equals(getContext().getString(R.string.scanned_type_text))) {
            scanIcon.setImageResource(R.drawable.text);
            scanType.setText(singleScannedItem.getStype());

            String text = singleScannedItem.getSdetails();
            String trimmed = trimText(text);
            scanDetails.setText(trimmed);
        }

        return customView;
    }

    public static String trimText(String text) {

        String trimmed = "";
        int requiredNum = 20;
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
