package hcmute.nhom13.myapplication.BottomSheet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import hcmute.nhom13.myapplication.MainActivity;
import hcmute.nhom13.myapplication.R;

public class BottomSheetNote extends BottomSheetDialogFragment {

    EditText edtNote;
    ImageView imgNote;
    public BottomSheetNote(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.bottom_sheet_note,container,false);
        edtNote = view.findViewById(R.id.editTextNotes);
        imgNote = view.findViewById(R.id.imageViewNotes);
        edtNote.setText(MainActivity.note);

        imgNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNote.getText().toString().equals("")) {
                    MainActivity.note = edtNote.getText().toString();
                    dismiss();
                }
            }
        });

        return view;
    }
}
