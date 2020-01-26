package com.example.riya.maps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class DialogCreate extends AppCompatDialogFragment {
    public EditText marname;
//  public EditText category;
    String Category;
    String Sample[];
    public DialogCreateListener listener;
    public Spinner mySpinner;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);
        builder.setView(view).setTitle("Add Marker").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            String marmane = marname.getText().toString();
            //String Category = category.getText().toString();

            listener.applyTexts(marmane,Category);

            ((MapsActivity)getActivity()).markerAdd(); //Calls markerAdd function in MapsActivity
            }
        });
        marname = view.findViewById(R.id.marname);
        //category = view.findViewById(R.id.categoryName);
        mySpinner = view.findViewById(R.id.spinner);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.dialogDropdownItem));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //use position value

                switch (position)

                {

                    case 0:
//                        Category = "Hostel";
                        Sample = getResources().getStringArray(R.array.dialogDropdownItem);
                        Category = Sample[position];
                        Toast.makeText(getActivity(), Category, Toast.LENGTH_SHORT).show();

                        break;

                    case 1:

//                      Category ="food & Beverages";
                        Sample = getResources().getStringArray(R.array.dialogDropdownItem);
                        Category = Sample[position];
//                        Toast.makeText(getActivity(), Category, Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
//                        Category = "Parking";
                        Sample = getResources().getStringArray(R.array.dialogDropdownItem);
                        Category = Sample[position];
//                        Toast.makeText(getActivity(), Category, Toast.LENGTH_SHORT).show();

                        break;

                    case 3:
//                        Category = "Office";
                        Sample = getResources().getStringArray(R.array.dialogDropdownItem);
                        Category = Sample[position];
//                        Toast.makeText(getActivity(), Category, Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
//                        Category = "Washroom";
                        Sample = getResources().getStringArray(R.array.dialogDropdownItem);
                        Category = Sample[position];
//                        Toast.makeText(getActivity(), Category, Toast.LENGTH_SHORT).show();
                        break;

                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{listener = (DialogCreateListener) context;}
        catch (ClassCastException e){throw new ClassCastException(context.toString()+"must implement DialogCreateListener");}
    }

    public interface DialogCreateListener{
        void applyTexts(String marname, String Category);

    }
}
