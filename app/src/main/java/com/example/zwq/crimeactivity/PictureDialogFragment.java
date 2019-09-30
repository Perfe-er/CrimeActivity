package com.example.zwq.crimeactivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class PictureDialogFragment extends DialogFragment {
    public static PictureDialogFragment newInstance(String path){
        Bundle args = new Bundle();
        args.putString("path",path);
        PictureDialogFragment pictureDialogFragment = new PictureDialogFragment();
        pictureDialogFragment.setArguments(args);
        return pictureDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        String path=getArguments().getString("path");
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.big_view);
        ImageView imageView=(ImageView)dialog.findViewById(R.id.iv);
        imageView.setImageBitmap(PictureUtils.getScaledBitmap(path,getActivity()));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
