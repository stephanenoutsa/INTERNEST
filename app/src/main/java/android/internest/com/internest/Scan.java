package android.internest.com.internest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Scan extends Fragment {
    Context context = getContext();

    public Scan() {
        // Required empty constructor
    }

    public static Scan newInstance() {
        Scan fragment = new Scan();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button scanButton;

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scan = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scan != null) {
            String codeContents = getResources().getString(R.string.code_contents);
            codeContents += scan.getContents();
            codeContents += getResources().getString(R.string.code_format);
            codeContents += scan.getFormatName();
            Toast.makeText(context, codeContents, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, getResources().getString(R.string.empty_scan),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
        //scanText = (TextView) rootView.findViewById(R.id.scanText);
        scanButton = (Button) rootView.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setPrompt(getResources().getString(R.string.scanner_prompt_text));
                integrator.initiateScan();
            }
        });

        return rootView;
    }
}
