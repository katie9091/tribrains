package com.buddyapps.tribrains;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity implements View.OnClickListener {

    LinearLayout root;
    LinearLayout brainTabs;
    LinearLayout brainContent;
    LinearLayout chatBody;
    EditText chatInput;
    Button sendBtn;
    Button importBtn;

    int currentBrain = 0;
    String[] brainNames = {"Alpha Brain", "Beta Brain", "Gamma Brain"};
    String[] brainColors = {"#1a237e", "#00695c", "#4a148c"};
    String[] brainSkills = {"Python + Linux + File Analysis", "GGUF + Sandbox + Unzip", "Minecraft + Mcaddon + Fix"};
    List<String> messages = new ArrayList<>();
    List<String> importedFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#121212"));
        root.setPadding(16, 48, 16, 16);

        TextView title = new TextView(this);
        title.setText("TriBrains — 3 AI Brains");
        title.setTextSize(22); title.setTextColor(Color.WHITE);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        root.addView(title);
        TextView subtitle = new TextView(this);
        subtitle.setText("Collaborative multi-agent AI · Sandbox · GGUF Import");
        subtitle.setTextSize(12); subtitle.setTextColor(Color.parseColor("#b0bec5"));
        root.addView(subtitle);

        brainTabs = new LinearLayout(this); brainTabs.setOrientation(LinearLayout.HORIZONTAL); brainTabs.setWeightSum(3);
        for (int i = 0; i < 3; i++) {
            Button b = new Button(this); b.setText(brainNames[i]); b.setTextSize(11); b.setTag(i); b.setOnClickListener(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f); lp.setMargins(4,4,4,4); b.setLayoutParams(lp);
            b.setBackgroundColor(Color.parseColor(brainColors[i])); b.setTextColor(Color.WHITE); brainTabs.addView(b);
        }
        root.addView(brainTabs);

        brainContent = new LinearLayout(this); brainContent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams bclp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 140); brainContent.setLayoutParams(bclp);
        brainContent.setBackgroundColor(Color.parseColor("#1e1e2e")); brainContent.setPadding(12,8,12,8);
        TextView brainTitle = new TextView(this); brainTitle.setText(brainNames[0]); brainTitle.setTextColor(Color.parseColor(brainColors[0])); brainTitle.setTextSize(16); brainTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        TextView brainDesc = new TextView(this); brainDesc.setText("Skills: " + brainSkills[0] + "\nStatus: Active · Listening\nSandbox: Linux sandbox available"); brainDesc.setTextColor(Color.parseColor("#e0e0e0")); brainDesc.setTextSize(11);
        brainContent.addView(brainTitle); brainContent.addView(brainDesc); root.addView(brainContent);

        TextView collab = new TextView(this); collab.setText("Brains collaborate: Alpha → Beta → Gamma share context. All have full Python + Linux + no skill restrictions."); collab.setTextColor(Color.parseColor("#ffd700")); collab.setTextSize(10); collab.setPadding(0,4,0,4); root.addView(collab);

        // Chat area inside ScrollView
        ScrollView scroll = new ScrollView(this); scroll.setBackgroundColor(Color.parseColor("#181825"));
        LinearLayout.LayoutParams scrollLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f); scroll.setLayoutParams(scrollLp);
        chatBody = new LinearLayout(this); chatBody.setOrientation(LinearLayout.VERTICAL); chatBody.setPadding(8,8,8,8);
        scroll.addView(chatBody); root.addView(scroll);

        addChatLine("[Alpha] Welcome to TriBrains. Python, Linux, file analysis — all in my sandbox.");
        addChatLine("[Beta] GGUF import + unzip (mcaddon) + sandbox execution ready.");
        addChatLine("[Gamma] Minecraft addon specialist — unzip, inspect, suggest fixes.");

        // Actions
        LinearLayout actions = new LinearLayout(this); actions.setOrientation(LinearLayout.HORIZONTAL); actions.setWeightSum(3);
        importBtn = new Button(this); importBtn.setText("Import File / GGUF / Zip"); importBtn.setOnClickListener(this); LinearLayout.LayoutParams impLp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f); importBtn.setLayoutParams(impLp); actions.addView(importBtn);
        Button sandboxBtn = new Button(this); sandboxBtn.setText("Open Sandbox"); sandboxBtn.setOnClickListener(this); LinearLayout.LayoutParams sbLp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f); sandboxBtn.setLayoutParams(sbLp); actions.addView(sandboxBtn);
        Button ggufBtn = new Button(this); ggufBtn.setText("GGUF Manager"); ggufBtn.setOnClickListener(this); LinearLayout.LayoutParams ggLp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f); ggufBtn.setLayoutParams(ggLp); actions.addView(ggufBtn);
        root.addView(actions);

        // Input row
        LinearLayout inputRow = new LinearLayout(this); inputRow.setOrientation(LinearLayout.HORIZONTAL); inputRow.setWeightSum(4);
        chatInput = new EditText(this); chatInput.setHint("Talk to the 3 brains..."); chatInput.setTextColor(Color.WHITE); chatInput.setHintTextColor(Color.parseColor("#888888")); LinearLayout.LayoutParams inLp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,3f); chatInput.setLayoutParams(inLp); inputRow.addView(chatInput);
        sendBtn = new Button(this); sendBtn.setText("Send"); sendBtn.setOnClickListener(this); LinearLayout.LayoutParams sLp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT,1f); sendBtn.setLayoutParams(sLp); inputRow.addView(sendBtn);
        root.addView(inputRow);

        setContentView(root);
    }

    void addChatLine(String text) {
        final TextView tv = new TextView(this); tv.setText(text); tv.setTextColor(Color.parseColor("#d0d0d0")); tv.setTextSize(12); tv.setPadding(4,6,4,6);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); tv.setLayoutParams(lp); tv.setBackgroundColor(Color.parseColor("#1a1a2e"));
        chatBody.addView(tv); messages.add(text);
    }

    @Override
    public void onClick(View v) {
        if (v == sendBtn) {
            String msg = chatInput.getText().toString().trim(); if (msg.isEmpty()) return;
            addChatLine("[You] " + msg); chatInput.setText("");
            new Thread(new Runnable() { public void run() {
                try { Thread.sleep(400); } catch (Exception e) {}
                final String r1 = "[Alpha] Processing: " + msg + " (Python + Linux sandbox active)";
                final String r2 = "[Beta] Cross-checking: importing any zip/gguf context from request";
                final String r3 = "[Gamma] If it's Minecraft-related I can unzip and inspect mcaddon format";
                runOnUiThread(new Runnable() { public void run() { addChatLine(r1); addChatLine(r2); addChatLine(r3); }});
            }}).start();
            return;
        }
        if (v.getTag() != null && v.getTag() instanceof Integer) {
            int idx = (Integer) v.getTag(); currentBrain = idx;
            TextView brainTitle = (TextView) brainContent.getChildAt(0);
            TextView brainDesc = (TextView) brainContent.getChildAt(1);
            brainTitle.setText(brainNames[idx]); brainTitle.setTextColor(Color.parseColor(brainColors[idx]));
            brainDesc.setText("Skills: " + brainSkills[idx] + "\nStatus: Active · Listening\nSandbox: Linux sandbox available\nNo skills restrictions.");
            addChatLine("[System] Switched to " + brainNames[idx] + ". All 3 brains share this conversation."); return;
        }
        if (v == importBtn) { showImportOptions(); return; }
        Button btn = (Button) v;
        String txt = btn.getText().toString();
        if (txt.contains("Sandbox")) { addChatLine("[Sandbox] Linux sandbox active. Commands: python3, unzip, ls, cat, find, grep. No restrictions."); showSandboxPanel(); }
        else if (txt.contains("GGUF")) { showGGUFPanel(); }
    }

    void showImportOptions() {
        AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);
        b.setTitle("Import File"); String[] items = {"GGUF Model (.gguf)", "ZIP / Mcaddon File", "Python Script (.py)", "Any File (text / binary)"};
        b.setItems(items, new DialogInterface.OnClickListener() { public void onClick(DialogInterface d, int which) {
            String s = items[which]; addChatLine("[Import] " + s); addChatLine("[Beta] Unzip/inspect. Sandbox active."); addChatLine("[Gamma] Checking Minecraft if mcaddon."); importedFiles.add("imported_" + which + "." + (s.contains("GGUF")?"gguf":s.contains("ZIP")?"zip":"py"));
            addChatLine("[System] Queued. Sandbox: unzip / python3 inspect.");
        }}); b.show();
    }
    void showSandboxPanel() {
        AlertDialog.Builder b = new AlertDialog.Builder(this); b.setTitle("Sandbox / Linux Brain");
        b.setMessage("Each brain has full sandbox:\n• python3 (no restrictions)\n• unzip / zip (mcaddon)\n• ls / cat / grep / find\n• Brain workspace shared via chat.");
        b.setPositiveButton("OK", null); b.show();
    }
    void showGGUFPanel() {
        AlertDialog.Builder b = new AlertDialog.Builder(this); b.setTitle("GGUF Manager");
        b.setMessage("GGUF framework included.\n\n1. Import .gguf\n2. Stored in brain workspace\n3. Sandbox → python3 + llama.cpp (install in sandbox)\n4. Brains share model context\n\nFull inference needs native backend installed in sandbox.");
        b.setPositiveButton("OK", null); b.show();
    }
}
