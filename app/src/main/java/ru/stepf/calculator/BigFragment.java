package ru.stepf.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BigFragment extends Fragment {

    BigFragmentListener mBigFragmentListener;

    Button btnZero;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnVor;
    Button btnFife;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnBackspace;
    Button btnClear;
    Button btnEquals;
    Button btnIntEquals;
    Button btnOpenBrace;
    Button btnCloseBrace;
    Button btnDot;
    Button btnPlus;
    Button btnMinus;
    Button btnDivide;
    Button btnMultiplie;
    Button btnRoote;
    Button btnInvolute;
    Button btnPi;

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
    Button btnPercent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.big_fragment, null);

        btnZero = (Button)view.findViewById(R.id.btnZero);
        btnOne = (Button)view.findViewById(R.id.btnOne);
        btnTwo = (Button)view.findViewById(R.id.btnTwo);
        btnThree = (Button)view.findViewById(R.id.btnThree);
        btnVor = (Button)view.findViewById(R.id.btnVor);
        btnFife = (Button)view.findViewById(R.id.btnFife);
        btnSix = (Button)view.findViewById(R.id.btnSix);
        btnSeven = (Button)view.findViewById(R.id.btnSeven);
        btnEight = (Button)view.findViewById(R.id.btnEight);
        btnNine = (Button)view.findViewById(R.id.btnNine);

        btnBackspace = (Button)view.findViewById(R.id.btnBackspace);
        btnClear = (Button)view.findViewById(R.id.btnClear);
        btnEquals = (Button)view.findViewById(R.id.btnEquals);
        btnIntEquals = (Button)view.findViewById(R.id.btnIntEquals);
        btnOpenBrace = (Button)view.findViewById(R.id.btnOpenBrace);
        btnCloseBrace = (Button)view.findViewById(R.id.btnCloseBrace);
        btnDot = (Button)view.findViewById(R.id.btnDot);
        btnPlus  = (Button)view.findViewById(R.id.btnPlus);
        btnMinus = (Button)view.findViewById(R.id.btnMinus);
        btnDivide = (Button)view.findViewById(R.id.btnDivide);
        btnMultiplie = (Button)view.findViewById(R.id.btnMultiplie);
        btnRoote = (Button)view.findViewById(R.id.btnRoote);
        btnInvolute = (Button)view.findViewById(R.id.btnInvolute);
        btnPi = (Button)view.findViewById(R.id.btnPi);

        btnOne.setOnClickListener(new NumberButtonsListener());
        btnTwo.setOnClickListener(new NumberButtonsListener());
        btnThree.setOnClickListener(new NumberButtonsListener());
        btnVor.setOnClickListener(new NumberButtonsListener());
        btnFife.setOnClickListener(new NumberButtonsListener());
        btnSix.setOnClickListener(new NumberButtonsListener());
        btnSeven.setOnClickListener(new NumberButtonsListener());
        btnEight.setOnClickListener(new NumberButtonsListener());
        btnNine.setOnClickListener(new NumberButtonsListener());
        btnZero.setOnClickListener(new NumberButtonsListener());

        btnBackspace.setOnClickListener(new FragmentClickListener());
        btnClear.setOnClickListener(new FragmentClickListener());
        btnEquals.setOnClickListener(new EqualsButtonListener());
        btnIntEquals.setOnClickListener(new EqualsButtonListener());
        btnOpenBrace.setOnClickListener(new BracesButtonsListener());
        btnCloseBrace.setOnClickListener(new BracesButtonsListener());
        btnDot.setOnClickListener(new FragmentClickListener());
        btnPlus.setOnClickListener(new OperationsButtonsListener());
        btnMinus.setOnClickListener(new OperationsButtonsListener());
        btnDivide.setOnClickListener(new OperationsButtonsListener());
        btnMultiplie.setOnClickListener(new OperationsButtonsListener());
        btnRoote.setOnClickListener(new SpecialButtonsListener());
        btnInvolute.setOnClickListener(new SpecialButtonsListener());
        btnPi.setOnClickListener(new NumberButtonsListener());

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
        btnBackspace = (Button)view.findViewById(R.id.btnBackspace);
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
        btnBackspace.setOnClickListener(new FragmentClickListener());
        btnPercent.setOnClickListener(new FragmentClickListener());
        setRadDeg(mBigFragmentListener.getRadians());

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        if(activity instanceof BigFragmentListener) {
            mBigFragmentListener = (BigFragmentListener) activity;
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
            mBigFragmentListener.OnSpecialSymbolButtonClick(v);
        }
    }

    public class FragmentClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnBackspace:
                    mBigFragmentListener.OnBackspaceButtonClick();
                    break;
                case R.id.btnPercent:
                    mBigFragmentListener.OnPercentButtonClick();
                    break;
                case R.id.btnRadDeg:
                    mBigFragmentListener.OnRadDegButtonClick();
                    setRadDeg(mBigFragmentListener.getRadians());
                    break;
                case R.id.btnNext:
                    mBigFragmentListener.OnNextButtonClick();
                    break;
                case R.id.btnClear:
                    mBigFragmentListener.OnClearButtonClick();
                    break;
                case R.id.btnDot:
                    mBigFragmentListener.OnDotButtonClick();
                    break;
            }
        }
    }

    public class NumberButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mBigFragmentListener.OnNumberButtonClick(v);
        }
    }

    public class OperationsButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mBigFragmentListener.OnSymbolButtonClick(v);
        }
    }

    public class SpecialButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mBigFragmentListener.OnSpecialSymbolButtonClick(v);
        }
    }

    public class EqualsButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mBigFragmentListener.OnEqualsButtonClick(v);
        }
    }

    public class BracesButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mBigFragmentListener.OnBraceButtonClick(v);
        }
    }

    public interface BigFragmentListener{
        void OnSymbolButtonClick(View view);
        void OnSpecialSymbolButtonClick(View view);
        void OnDotButtonClick();
        void OnNumberButtonClick(View view);
        void OnBraceButtonClick(View view);
        void OnClearButtonClick();
        void OnBackspaceButtonClick();
        void OnEqualsButtonClick(View view);
        void OnNextButtonClick();
        void OnPercentButtonClick();
        void OnRadDegButtonClick();
        boolean getRadians();
    }
}
