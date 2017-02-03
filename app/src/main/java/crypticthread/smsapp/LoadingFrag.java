package crypticthread.smsapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by User on 2/2/2017.
 */

public class LoadingFrag extends DialogFragment {
    private static final String TAG = "LoadingFrag";

    private String loading_msg;

    public String getLoading_msg() {
        return loading_msg;
    }

    public void setLoading_msg(String loading_msg) {
        this.loading_msg = loading_msg;
        loading_view.setText(loading_msg);
    }

    private TextView loading_view;

    public LoadingFrag() {
    }

    public static LoadingFrag newInstance(String loading_msg){
        Bundle args = new Bundle();
        args.putString(StaticVariables.KEY_LOADING_MSG,loading_msg);
        LoadingFrag frag = new LoadingFrag();
        frag.setStyle(DialogFragment.STYLE_NO_FRAME,R.style.dialogFragmentThemeLoading);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogFragmentThemeLoading;
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.loading_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loading_msg = getArguments().getString(StaticVariables.KEY_LOADING_MSG);
        loading_view = (TextView) view.findViewById(R.id.loading_msg);

        setLoading_msg(loading_msg);
    }
}
