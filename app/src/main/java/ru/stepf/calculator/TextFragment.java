package ru.stepf.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TextFragment extends Fragment {

    private EditText mExampleView;
    private EditText mAnswerText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_fragment_layout, null);
        mExampleView = (EditText) view.findViewById(R.id.exampleTextView);
        mAnswerText = (EditText)view.findViewById(R.id.answerTextView);

        if(savedInstanceState != null){
            mAnswerText.setText(savedInstanceState.getString("Answer"));
            mExampleView.setText(savedInstanceState.getString("Example"));
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("Example", mExampleView.getText().toString());
        outState.putString("Answer", mAnswerText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void addText(String text){
        mExampleView.append(text);
        mAnswerText.setText("");
    }

    public boolean printAnswer(String answer){
        if(mAnswerText.getText().toString().equals(answer)) {
            mAnswerText.setText("");
            mExampleView.setText(answer);
            return true;
        }
        mAnswerText.setText(answer);
        return false;
    }

    public void clear(){
        mExampleView.setText("");
        mAnswerText.setText("");
    }

    public void deleteSymbols(int numOfSymbols){
        mExampleView.setText(mExampleView.getText().toString().substring(0, mExampleView.length() - numOfSymbols));
        mAnswerText.setText("");
    }
}
