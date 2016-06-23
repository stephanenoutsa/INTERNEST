package android.internest.com.internest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by stephnoutsa on 6/1/16.
 */
public class MakeCode extends Fragment implements AdapterView.OnItemSelectedListener {

    public MakeCode() {
        // Required empty constructor
    }

    String placeholder;
    Button makeCodeButton;
    EditText contents;

    public static MakeCode newInstance() {
        MakeCode fragment = new MakeCode();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_make_code, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.content_types_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        contents = (EditText) rootView.findViewById(R.id.contents);

        makeCodeButton = (Button) rootView.findViewById(R.id.makeCodeButton);
        makeCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = contents.getText().toString();
                if(value.isEmpty() || value.equals(getString(R.string.url_placeholder_text))) {
                    Toast.makeText(getContext(), getString(R.string.submit_error), Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Intent intent = new Intent(getContext(), YourQRCode.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        contents.setText("");

        /*switch (parent.getItemAtPosition(pos).toString()) {
            case "Text":
                placeholder = getString(R.string.text_placeholder_text);
                contents.setHint(placeholder);
                contents.setHintTextColor(getResources().getColor(R.color.colorPrimaryLight));
                break;
            default:
                placeholder = getString(R.string.url_placeholder_text);
                contents.setText(placeholder);
                break;
        }*/

        if (parent.getItemAtPosition(pos).toString().equals(getString(R.string.text_content_type))) {
            placeholder = getString(R.string.text_placeholder_text);
            contents.setHint(placeholder);
            contents.setHintTextColor(getResources().getColor(R.color.colorPrimaryLight));
        }
        else if (parent.getItemAtPosition(pos).toString().equals(getString(R.string.url_content_type))) {
            placeholder = getString(R.string.url_placeholder_text);
            contents.setText(placeholder);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
