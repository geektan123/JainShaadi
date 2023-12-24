package com.example.jainshaadi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityEdit extends DialogFragment {

    private LinearLayout Layout1;
    private LinearLayout Layout2;
    private LinearLayout Layout3;
//    private LinearLayout Layout4;
    private LinearLayout Layout5;
    private LinearLayout Layout6;
    private TextView nexttext;
    private LinearLayout nextlay;
    private TextView Digamber;
    private TextView Svetamber;
    private String category = "None";
    private String subCategory;
    private boolean isNextLayoutChanged = false;
    private EditText editText;
    private String spinners;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_cast, container, false);

        Digamber = view.findViewById(R.id.Digamber);
        Layout1 = view.findViewById(R.id.Layout1);
        Layout2 = view.findViewById(R.id.Layout2);
        Layout3 = view.findViewById(R.id.layout3);
//        Layout4 = view.findViewById(R.id.layout4);
        Layout5 = view.findViewById(R.id.layout5);
        Layout6 = view.findViewById(R.id.layout6);
        Svetamber = view.findViewById(R.id.Swetamber);
        nextlay = view.findViewById(R.id.Nextlay);
        nexttext = view.findViewById(R.id.Next);
        Layout3.setVisibility(View.INVISIBLE);
//        Layout4.setVisibility(View.INVISIBLE);
        Layout5.setVisibility(View.INVISIBLE);
        Spinner spin = view.findViewById(R.id.spinner1);
        editText = view.findViewById(R.id.others);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
//                nexttext.setTextColor(Color.parseColor("#756568"));
                isNextLayoutChanged = false;
                Layout6.setVisibility(view.INVISIBLE);
                if (category.equals("Shwetambar")) {
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    Svetamber.setTextColor(Color.parseColor("#756568"));
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Digamber.setTextColor(Color.parseColor("#FFFFFF"));

                } else if (category.equals("Digambar")) {
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Digamber.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Digamber.setTextColor(Color.parseColor("#FFFFFF"));
                }
                Layout3.setVisibility(View.VISIBLE);
//                Layout4.setVisibility(View.VISIBLE);
                Layout5.setVisibility(View.VISIBLE);
                category = "Digambar";

                ArrayList<String> arrayAdapter1 = new ArrayList<>();
                arrayAdapter1.add("-Select-");
                arrayAdapter1.add("Digambar-" + "Navnat");
                arrayAdapter1.add("Digambar-" + "Saitwal");
                arrayAdapter1.add("Digambar-" + "Agrawal");
                arrayAdapter1.add("Digambar-" + "Bagherwal");
                arrayAdapter1.add("Digambar-" + "Bhabra");
                arrayAdapter1.add("Digambar-" + "Bunt");
                arrayAdapter1.add("Digambar-" + "Chaturtha");
                arrayAdapter1.add("Digambar-" + "Dugar");
                arrayAdapter1.add("Digambar-" + "Godha");
                arrayAdapter1.add("Digambar-" + "Golalare");
                arrayAdapter1.add("Digambar-" + "Golapurva");
                arrayAdapter1.add("Digambar-" + "Humad");
                arrayAdapter1.add("Digambar-" + "Jaiswal");
                arrayAdapter1.add("Digambar-" + "Kasliwal");
                arrayAdapter1.add("Digambar-" + "Narsingpura");
                arrayAdapter1.add("Digambar-" + "Oswal");
                arrayAdapter1.add("Digambar-" + "Parwar");
                arrayAdapter1.add("Digambar-" + "Porwal");
                arrayAdapter1.add("Digambar-" + "Sarak");
                arrayAdapter1.add("Digambar-" + "Sarawagi");
                arrayAdapter1.add("Digambar-" + "Shrimal");
                arrayAdapter1.add("Digambar-" + "Tamil");
                arrayAdapter1.add("Digambar-" + "Veerwal");
                arrayAdapter1.add("Digambar-" + "Vijayvargiya");
                arrayAdapter1.add("Other");
                ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayAdapter1);
                arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                spin.setAdapter(arrayAdapter6);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (spin.getSelectedItemPosition() > -1) {
                            spinners = arrayAdapter1.get(position);
                            subCategory = spinners;
                            if (spinners.equals("Other")) {
                                Layout6.setVisibility(view.VISIBLE);
                                editText = view.findViewById(R.id.others);
                                subCategory = "";

                                editText.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String enteredText = editable.toString();
                                        subCategory = enteredText;
                                        if (subCategory != null) {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                            nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                            isNextLayoutChanged = true;
                                        } else {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
//                                            nexttext.setTextColor(Color.parseColor("#756568"));
                                            isNextLayoutChanged = false;
                                        }
                                    }
                                });
                            } else {
                                Layout6.setVisibility(view.INVISIBLE);
                                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                isNextLayoutChanged = true;
                            }

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        Layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
//                nexttext.setTextColor(Color.parseColor("#756568"));
                isNextLayoutChanged = false;
                Layout6.setVisibility(view.INVISIBLE);

                if (category.equals("Digambar")) {
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    Digamber.setTextColor(Color.parseColor("#756568"));
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Svetamber.setTextColor(Color.parseColor("#FFFFFF"));

                } else if (category.equals("Shwetambar")) {
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Svetamber.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Svetamber.setTextColor(Color.parseColor("#FFFFFF"));
                }
                Layout3.setVisibility(View.VISIBLE);
//                Layout4.setVisibility(View.VISIBLE);
                Layout5.setVisibility(View.VISIBLE);
                category = "Shvwetambar";

                ArrayList<String> arrayAdapter1 = new ArrayList<>();
                arrayAdapter1.add("-Select-");
                arrayAdapter1.add("Shwetambar-" + "Navnat");
                arrayAdapter1.add("Shwetambar-" + "Saitwal");
                arrayAdapter1.add("Shwetambar-" + "Agrawal");
                arrayAdapter1.add("Shwetambar-" + "Bagherwal");
                arrayAdapter1.add("Shwetambar-" + "Bhabra");
                arrayAdapter1.add("Shwetambar-" + "Bunt");
                arrayAdapter1.add("Shwetambar-" + "Chaturtha");
                arrayAdapter1.add("Shwetambar-" + "Dugar");
                arrayAdapter1.add("Shwetambar-" + "Godha");
                arrayAdapter1.add("Shwetambar-" + "Golalare");
                arrayAdapter1.add("Shwetambar-" + "Golapurva");
                arrayAdapter1.add("Shwetambar-" + "Humad");
                arrayAdapter1.add("Shwetambar-" + "Jaiswal");
                arrayAdapter1.add("Shwetambar-" + "Kasliwal");
                arrayAdapter1.add("Shwetambar-" + "Narsingpura");
                arrayAdapter1.add("Shwetambar-" + "Oswal");
                arrayAdapter1.add("Shwetambar-" + "Parwar");
                arrayAdapter1.add("Shwetambar-" + "Porwal");
                arrayAdapter1.add("Shwetambar-" + "Sarak");
                arrayAdapter1.add("Shwetambar-" + "Sarawagi");
                arrayAdapter1.add("Shwetambar-" + "Shrimal");
                arrayAdapter1.add("Shwetambar-" + "Tamil");
                arrayAdapter1.add("Shwetambar-" + "Veerwal");
                arrayAdapter1.add("Shwetambar-" + "Vijayvargiya");

                arrayAdapter1.add("Other");
                ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayAdapter1);
                arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                spin.setAdapter(arrayAdapter6);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (spin.getSelectedItemPosition() > -1) {
                            spinners = arrayAdapter1.get(position);
                            subCategory = spinners;
                            if (spinners.equals("Other")) {
                                Layout6.setVisibility(view.VISIBLE);

                                subCategory = "";
                                editText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});

                                editText.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        String enteredText = editable.toString().trim();
                                        subCategory = enteredText;
                                        if (subCategory != null) {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                            nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                            isNextLayoutChanged = true;
                                        } else {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
//                                            nexttext.setTextColor(Color.parseColor("#756568"));
                                            isNextLayoutChanged = false;
                                        }
                                    }
                                });
                            } else {
                                Layout6.setVisibility(view.INVISIBLE);
                                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                isNextLayoutChanged = true;
                            }

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subCategory.isEmpty() || subCategory.equals("-Select-"))
                {
                    isNextLayoutChanged = false;
                }
                if (isNextLayoutChanged) {
                    String userKey = FirebaseAuth.getInstance().getUid();
                    DatabaseReference userRef = databaseReference.child(userKey);
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Category", category);
                    updateData.put("Subcategory", subCategory);
                    userRef.updateChildren(updateData);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Please enter inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // Set the size and margins of the dialog
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(params);

        return dialog;
    }
}
