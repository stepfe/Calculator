package ru.stepf.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SecondFragment extends Fragment {

    SecondFragmentListener mSecondFragmentListener;

    Button btnSin;
    Button btnCos;
    Button btnTan;
    Button btnAsin;
    Button btnAcos;
    Button btnAtan;
    Button btnLg;
    Button btnLb;
    Button btnLog;
    Button btnRadDeg;
    Button btnNext;
    Button btnBackSpace;
    Button btnPercent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_fragment_layout, null);

        btnSin = (Button)view.findViewById(R.id.btnSin);
        btnCos = (Button)view.findViewById(R.id.btnCos);
        btnTan = (Button)view.findViewById(R.id.btnTan);
        btnAsin = (Button)view.findViewById(R.id.btnAsin);
        btnAcos = (Button)view.findViewById(R.id.btnAcos);
        btnAtan = (Button)view.findViewById(R.id.btnAtan);
        btnLg = (Button)view.findViewById(R.id.btnLg);
        btnLb = (Button)view.findViewById(R.id.btnLb);
        btnLog = (Button)view.findViewById(R.id.btnLog);
        btnRadDeg = (Button)view.findViewById(R.id.btnRadDeg);
        btnNext = (Button)view.findViewById(R.id.btnNext);
        btnBackSpace = (Button)view.findViewById(R.id.btnBackspace);
        btnPercent = (Button)view.findViewById(R.id.btnPercent);

        btnSin.setOnClickListener(new ButtonsListener());
        btnCos.setOnClickListener(new ButtonsListener());
        btnTan.setOnClickListener(new ButtonsListener());
        btnAsin.setOnClickListener(new ButtonsListener());
        btnAcos.setOnClickListener(new ButtonsListener());
        btnAtan.setOnClickListener(new ButtonsListener());
        btnLg.setOnClickListener(new ButtonsListener());
        btnLb.setOnClickListener(new ButtonsListener());
        btnLog.setOnClickListener(new ButtonsListener());
        btnRadDeg.setOnClickListener(new FragmentClickListener());
        btnNext.setOnClickListener(new FragmentClickListener());
        btnBackSpace.setOnClickListener(new FragmentClickListener());
        btnPercent.setOnClickListener(new FragmentClickListener());
        setRadDeg(mSecondFragmentListener.getRadians());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        if(activity instanceof SecondFragmentListener) {
            mSecondFragmentListener = (SecondFragmentListener) activity;
        }
        super.onAttach(activity);
    }

    public void setRadDeg(boolean radians){
        if(radians){
            btnRadDeg.setText(getResources().getString(R.string.deg));
        }else{
            btnRadDeg.setText(getResources().getString(R.string.rad));
        }
    }

    public class ButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mSecondFragmentListener.OnSpecialSymbolButtonClick(v);
        }
    }

    public class FragmentClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnBackspace:
                    mSecondFragmentListener.OnBackspaceButtonClick();
                    break;
                case R.id.btnPercent:
                    mSecondFragmentListener.OnPercentButtonClick();
                    break;
                case R.id.btnRadDeg:
                    mSecondFragmentListener.OnRadDegButtonClick();
                    setRadDeg(mSecondFragmentListener.getRadians());
                    break;
                case R.id.btnNext:
                    mSecondFragmentListener.OnNextButtonClick();
                    break;
            }
        }
    }

    public interface SecondFragmentListener{
        void OnPercentButtonClick();
        void OnSpecialSymbolButtonClick(View view);
        void OnNextButtonClick();
        void OnRadDegButtonClick();
        void OnBackspaceButtonClick();
        boolean getRadians();
    }
}
