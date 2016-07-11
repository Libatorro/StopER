package com.example.sebastian.stoper.Faragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastian.stoper.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int DEFAULT_TIME = 20;
    private static int DEFAULT_REST_TIME = 10;
    private static int DEFAULT_LAPS = 8;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button B_Add_Laps, B_Add_Time, B_Remove_laps, B_Remove_Time, StartCounting;
    private TextView time_Text, laps_Text;
    private View view;
    private boolean isON = false;
    private int TIME_Seconds;
    private boolean REST=false;
    private int Laps;
    private Vibrator vibrator;


    public TabataFragment() {
        // Required empty public constructor
    }


    public static TabataFragment newInstance(String param1, String param2) {
        TabataFragment fragment = new TabataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tabata, container, false);
        initialize();


        return view;
    }

    private void initialize() {
        B_Add_Laps = (Button) view.findViewById(R.id.button_Add);
        B_Add_Time = (Button) view.findViewById(R.id.button_Add_time);
        B_Remove_laps = (Button) view.findViewById(R.id.button_Minus);
        B_Remove_Time = (Button) view.findViewById(R.id.button_Less_time);
        time_Text = (TextView) view.findViewById(R.id.textView2);
        laps_Text = (TextView) view.findViewById(R.id.editText);
        StartCounting = (Button) view.findViewById(R.id.START_TABATA);


        B_Add_Laps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEFAULT_LAPS++;
                Update_TextView();
            }
        });
        B_Remove_laps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEFAULT_LAPS--;
                Update_TextView();
            }
        });
        B_Add_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEFAULT_TIME++;
                Update_TextView();
            }

        });
        B_Remove_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DEFAULT_TIME--;
                Update_TextView();
            }
        });

        StartCounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isON) {
                    isON = true;
                    StartCounting.setTextColor(Color.parseColor("#ee2525"));
                    B_Remove_Time.setClickable(false);
                    B_Add_Laps.setClickable(false);
                    B_Remove_Time.setClickable(false);
                    B_Add_Time.setClickable(false);
                    StartCounting.setText("STOP");

                    RunTimer(DEFAULT_TIME);
                } else {
                    StartCounting.setTextColor(Color.parseColor("#28e916"));
                    StartCounting.setText("START");
                    isON = false;
                    Update_TextView();
                    B_Remove_Time.setClickable(true);
                    B_Add_Laps.setClickable(true);
                    B_Remove_Time.setClickable(true);
                    B_Add_Time.setClickable(true);


                }


            }
        });


    }

    private void Update_TextView() {

        time_Text.setTextColor(Color.BLACK);
        String Time = String.format("%02d:%02d", DEFAULT_TIME, 00);
        time_Text.setText(Time);
        laps_Text.setText(DEFAULT_LAPS + "");

    }


    private void RunTimer(int time) {
         vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        TIME_Seconds = time;
          Laps=DEFAULT_LAPS;
        if (!isON) return;

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!isON) return;
if(Laps<=0)
{   Toast.makeText(getContext(), "KONIEC TRENINGU", Toast.LENGTH_SHORT).show();
    vibrator.vibrate(500);
    return;
}
                int sec = TIME_Seconds % 60;
                String Time = String.format("%02d:%02d", TIME_Seconds, 00);
                time_Text.setText(Time);

                if (TIME_Seconds > 0) {
                    TIME_Seconds--;


                } else {
                    if (!REST) {
                        if(getContext()!=null)
                        Toast.makeText(getContext(), "Przerwa", Toast.LENGTH_SHORT).show();
                        Laps--;


// Vibrate for 300 milliseconds
                        vibrator.vibrate(300);



                        laps_Text.setText(Laps + "");
                        TIME_Seconds = DEFAULT_REST_TIME;
                        time_Text.setTextColor(Color.parseColor("#FF1F76C8"));
                        REST = true;
                    } else {
                        REST = false;
                        time_Text.setTextColor(Color.parseColor("#FF15EA34"));
                        TIME_Seconds=DEFAULT_TIME;
                    }

                }

                handler.postDelayed(this, 1000);
            }
        });


    }

}
