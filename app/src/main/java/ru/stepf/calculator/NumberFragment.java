package ru.stepf.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NumberFragment extends Fragment {

    NumberFragmentListener mNumberFragmentListener;

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
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.number_fragment_layout, null);

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
        btnNext = (Button)view.findViewById(R.id.btnNext);

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
        btnNext.setOnClickListener(new FragmentClickListener());

        return view;
    }

    @Override
    public void onAttach(Activity activity){
        if(activity instanceof NumberFragmentListener)
            mNumberFragmentListener = (NumberFragmentListener)activity;
        super.onAttach(activity);
    }

    public class NumberButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mNumberFragmentListener.OnNumberButtonClick(v);
        }
    }

    public class OperationsButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mNumberFragmentListener.OnSymbolButtonClick(v);
        }
    }

    public class SpecialButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mNumberFragmentListener.OnSpecialSymbolButtonClick(v);
        }
    }

    public class EqualsButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mNumberFragmentListener.OnEqualsButtonClick(v);
        }
    }

    public class BracesButtonsListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            mNumberFragmentListener.OnBraceButtonClick(v);
        }
    }

    public class FragmentClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnBackspace:
                    mNumberFragmentListener.OnBackspaceButtonClick();
                    break;
                case R.id.btnClear:
                    mNumberFragmentListener.OnClearButtonClick();
                    break;
                case R.id.btnDot:
                    mNumberFragmentListener.OnDotButtonClick();
                    break;
                case R.id.btnNext:
                    mNumberFragmentListener.OnNextButtonClick();
                    break;
            }
        }
    }

    public interface NumberFragmentListener{
        void OnSymbolButtonClick(View view);
        void OnSpecialSymbolButtonClick(View view);
        void OnDotButtonClick();
        void OnNumberButtonClick(View view);
        void OnBraceButtonClick(View view);
        void OnClearButtonClick();
        void OnBackspaceButtonClick();
        void OnEqualsButtonClick(View view);
        void OnNextButtonClick();
    }

}
