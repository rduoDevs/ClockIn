package com.example.clockitcurrent;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import classes.Plan;
import classes.Schedule;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test extends Fragment {
    public static View[] layoutList;
    public static Button[] buttonList;
    public static TextView[] textList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    */
    public Test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test.
     */
    // TODO: Rename and change types and number of parameters
    /*public static Test newInstance(String param1, String param2) {
        Test fragment = new Test();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        } */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View result = inflater.inflate(R.layout.fragment_test, container, false);





        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button[] buttonList1 = {
                (Button) getView().findViewById(R.id.ScheduleButton1),
                (Button) getView().findViewById(R.id.ScheduleButton2),
                (Button) getView().findViewById(R.id.ScheduleButton3),
                (Button) getView().findViewById(R.id.ScheduleButton4),
                (Button) getView().findViewById(R.id.ScheduleButton5),
                (Button) getView().findViewById(R.id.ScheduleButton6),
                (Button) getView().findViewById(R.id.ScheduleButton7),
                (Button) getView().findViewById(R.id.ScheduleButton8),
                (Button) getView().findViewById(R.id.ScheduleButton9),
                (Button) getView().findViewById(R.id.ScheduleButton10),
        };
        TextView[] textList1 = {
                (TextView) getView().findViewById(R.id.ScheduleText1),
                (TextView) getView().findViewById(R.id.ScheduleText2),
                (TextView) getView().findViewById(R.id.ScheduleText3),
                (TextView) getView().findViewById(R.id.ScheduleText4),
                (TextView) getView().findViewById(R.id.ScheduleText5),
                (TextView) getView().findViewById(R.id.ScheduleText6),
                (TextView) getView().findViewById(R.id.ScheduleText7),
                (TextView) getView().findViewById(R.id.ScheduleText8),
                (TextView) getView().findViewById(R.id.ScheduleText9),
                (TextView) getView().findViewById(R.id.ScheduleText10),
        };
        View[] layoutList1 = {
                (View) getView().findViewById(R.id.ScheduleLayout1),
                (View) getView().findViewById(R.id.ScheduleLayout2),
                (View) getView().findViewById(R.id.ScheduleLayout3),
                (View) getView().findViewById(R.id.ScheduleLayout4),
                (View) getView().findViewById(R.id.ScheduleLayout5),
                (View) getView().findViewById(R.id.ScheduleLayout6),
                (View) getView().findViewById(R.id.ScheduleLayout7),
                (View) getView().findViewById(R.id.ScheduleLayout8),
                (View) getView().findViewById(R.id.ScheduleLayout9),
                (View) getView().findViewById(R.id.ScheduleLayout10),
        };
        layoutList = layoutList1;
        textList = textList1;
        buttonList = buttonList1;
        /*Schedule schedule = new Schedule("Test", getActivity().getBaseContext(), getActivity());
        for (int i = 0; i < 5; i++) {
            Plan plan = new Plan(schedule);
        }
        schedule.updateFragment(buttonList, textList, layoutList);*/
    }
    
}