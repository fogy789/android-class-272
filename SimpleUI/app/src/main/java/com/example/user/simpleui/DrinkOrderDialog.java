package com.example.user.simpleui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;//把support.V4(舊手機也能使用的Fragment)改成現在這樣
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkOrderDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkOrderDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkOrderDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Drink drink;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    NumberPicker mnumberPicker;
    NumberPicker lnumberPicker;

    RadioGroup iceRadioGroup;
    RadioGroup sugarRadioGroup;
    EditText noteEdiText;

    private OnFragmentInteractionListener mListener;

    public DrinkOrderDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DrinkOrderDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkOrderDialog newInstance(Drink drink) {
        DrinkOrderDialog fragment = new DrinkOrderDialog();
        Bundle args = new Bundle();

        args.putParcelable(ARG_PARAM1,drink);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if(getArguments() != null)
        {
            drink = getArguments().getParcelable(ARG_PARAM1);
        }
        View contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_drink_order_dialog,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(contentView).setTitle(drink.name).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DrinkOrder drinkOrder = new DrinkOrder(drink);
                drinkOrder.mNumber = mnumberPicker.getValue();
                drinkOrder.LNumber = lnumberPicker.getValue();
                drinkOrder.ice = getSeletedTextFromRadioGroup(iceRadioGroup);
                drinkOrder.sugar = getSeletedTextFromRadioGroup(sugarRadioGroup);
                drinkOrder.note = noteEdiText.getText().toString();

                if(mListener != null)
                {
                    mListener.onDrinkOrderResult(drinkOrder);
                }
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        mnumberPicker = (NumberPicker)contentView.findViewById(R.id.mnumberPicker);
        lnumberPicker = (NumberPicker)contentView.findViewById(R.id.lnumberPicker);
        iceRadioGroup = (RadioGroup)contentView.findViewById(R.id.iceradioGroup);
        sugarRadioGroup = (RadioGroup)contentView.findViewById(R.id.sugarradioGroup);
        noteEdiText = (EditText)contentView.findViewById(R.id.noteeditText);

        mnumberPicker.setMaxValue(100);
        mnumberPicker.setMinValue(0);

        lnumberPicker.setMaxValue(100);
        lnumberPicker.setMinValue(0);

        return builder.create();
    }

    private String getSeletedTextFromRadioGroup(RadioGroup radioGroup)
    {
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(id);
        return radioButton.getText().toString();
    }


    @Override
    public void onAttach(Context context) {//把activity傳進來，建立溝通的橋梁，若溝通機制無效就要注意是否使用API22的手機，並將成是改成public void onAttach(Activity activity)
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {//若不會實做此介面，則丟出一個ERROR
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {//取消這兩個介面的溝通關係
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onDrinkOrderResult(DrinkOrder drinkOrder);
    }
}
