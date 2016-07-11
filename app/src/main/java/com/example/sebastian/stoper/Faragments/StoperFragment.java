package com.example.sebastian.stoper.Faragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastian.stoper.R;


public class StoperFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View VIEW;
    private TextView Stoper_time;
    private Button start, stopp, reset;
    private int TIME_Seconds = 0;
    private int TIME_HOURS = 0;
    private int TIME_Minutes = 0;
    private boolean ON = false;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private boolean mParam2=false;
    private boolean RESET = false;
    private String Time;

    private static boolean isThreadRunning=true;
    private Handler handler;
    private Runnable mRunnable;
    private Intent iService;

    public StoperFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StoperFragment newInstance(int param1, Boolean param2) {
        StoperFragment fragment = new StoperFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putBoolean(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDetach() {


         iService = new Intent(getContext(),StoperService.class);

        iService.putExtra(StoperService.key,Time);
        iService.putExtra("abc",TIME_Seconds);
        getActivity().startService(iService);
        handler.removeCallbacks(mRunnable);


  // Notyfikacja();
        super.onDetach();








    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VIEW = inflater.inflate(R.layout.fragment_blank, container, false);


        Stoper_time = (TextView) VIEW.findViewById(R.id.textView);
        start = (Button) VIEW.findViewById(R.id.START);
        stopp = (Button) VIEW.findViewById(R.id.Stop);
        reset = (Button) VIEW.findViewById(R.id.RESET);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ON = true;
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESET = true;

                TIME_Seconds = 0;
            }
        });

        stopp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ON = false;
                if(iService!=null)
                getActivity().stopService(iService);

            }
        });

        if(mParam2)
        {

            TIME_Seconds=mParam1;
            ON=mParam2;
        }


        RunTimer();


        return VIEW;
    }

    private void RunTimer() {







            mRunnable=  new Runnable() {
                @Override
                public void run() {

                    TIME_HOURS = TIME_Seconds / 3600;
                    TIME_Minutes = (TIME_Seconds % 3600) / 60;
                    int sec = TIME_Seconds % 60;
                    Time = String.format("%d:%02d:%02d", TIME_HOURS, TIME_Minutes, sec);
                    Stoper_time.setText(Time);
                    if (ON) {
                        TIME_Seconds++;
                        // Notyfikacja();
                        System.out.println("time");
                    }
                    handler.postDelayed(this, 1000);
                }
            };


            handler = new Handler();
            handler.post(mRunnable);



    }

}
