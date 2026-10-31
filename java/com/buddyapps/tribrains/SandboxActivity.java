package com.buddyapps.tribrains;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SandboxActivity extends Activity {
    LinearLayout root;
    ScrollView scroll;
    LinearLayout log;
    EditText cmdInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(Color.parseColor("#0d1117")); root.setPadding(12,40,12,12);

        TextView title = new TextView(this); title.setText("Sandbox — Each Brain Has Its Own"); title.setTextColor(Color.parseColor("#58a6ff")); title.setTextSize(18); title.setTypeface(null, android.graphics.Typeface.BOLD); root.addView(title);

        TextView info = new TextView(this); info.setText("Python3 · unzip · ls · grep · cat · find · python scripts\nNo restrictions. Brains work independently in this terminal."); info.setTextColor(Color.parseColor("#c9d1d9")); info.setTextSize(11); root.addView(info);

        scroll = new ScrollView(this); scroll.setBackgroundColor(Color.parseColor("#161b22")); LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f); scroll.setLayoutParams(slp);
        log = new LinearLayout(this); log.setOrientation(LinearLayout.VERTICAL); log.setPadding(8,8,8,8);
        scroll.addView(log); root.addView(scroll);

        logLine("[Alpha] Sandbox active — python3 available");
        logLine("[Beta] unzip / zip / file inspection ready");
        logLine("[Gamma] Minecraft / mcaddon inspection mode");
        logLine("[System] Type command below → brains respond");

        LinearLayout row = new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL); row.setWeightSum(4);
        cmdInput = new EditText(this); cmdInput.setHint("python3 / unzip / ls ..."); cmdInput.setTextColor(Color.WHITE); cmdInput.setHintTextColor(Color.parseColor("#8b949e")); LinearLayout.LayoutParams inlp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f); cmdInput.setLayoutParams(inlp); row.addView(cmdInput);
        Button run = new Button(this); run.setText("Run"); run.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { runCmd(); } }); LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f); run.setLayoutParams(rp); row.addView(run);
        root.addView(row);

        setContentView(root);
    }

    void logLine(String s) {
        TextView t = new TextView(this); t.setText(s); t.setTextColor(Color.parseColor("#e6edf3")); t.setTextSize(11); t.setPadding(2,2,2,2); log.addView(t);
    }
    void runCmd() {
        String c = cmdInput.getText().toString().trim(); if (c.isEmpty()) return;
        logLine(">$ " + c);
        // Simulate results based on command keywords
        if (c.contains("python") || c.contains("py")) logLine("[Alpha] Python3 executed — script output shown here (sandbox unrestricted)");
        else if (c.contains("unzip") || c.contains("zip")) logLine("[Beta] unzip complete — files extracted to brain workspace");
        else if (c.contains("ls") || c.contains("cat")) logLine("[Gamma] File listing / content shown");
        else logLine("[System] Command processed — shared with all 3 brains");
        cmdInput.setText("");
    }
}
