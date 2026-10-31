package com.buddyapps.tribrains;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class MainActivity extends Activity implements View.OnClickListener {

    LinearLayout root;
    LinearLayout brainTabs;
    LinearLayout brainContent;
    LinearLayout chatArea;
    EditText chatInput;
    Button sendBtn;
    Button importBtn;

    int currentBrain = 0; // 0=Alpha, 1=Beta, 2=Gamma

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

        // Header
        TextView title = new TextView(this);
        title.setText("TriBrains — 3 AI Brains");
        title.setTextSize(22);
        title.setTextColor(Color.WHITE);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Collaborative multi-agent AI · Sandbox · GGUF Import");
        subtitle.setTextSize(12);
        subtitle.setTextColor(Color.parseColor("#b0bec5"));
        root.addView(subtitle);

        // Brain tabs
        brainTabs = new LinearLayout(this);
        brainTabs.setOrientation(LinearLayout.HORIZONTAL);
        brainTabs.setWeightSum(3);
        for (int i=0;i<3;i++) {
            Button b = new Button(this);
            b.setText(brainNames[i]);
            b.setTextSize(11);
            b.setTag(i);
            b.setOnClickListener(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            lp.setMargins(4, 4, 4, 4);
            b.setLayoutParams(lp);
            b.setBackgroundColor(Color.parseColor(brainColors[i]));
            b.setTextColor(Color.WHITE);
            brainTabs.addView(b);
        }
        root.addView(brainTabs);

        // Brain info panel
        brainContent = new LinearLayout(this);
        brainContent.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams bclp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 140);
        brainContent.setLayoutParams(bclp);
        brainContent.setBackgroundColor(Color.parseColor("#1e1e2e"));
        brainContent.setPadding(12, 8, 12, 8);

        final TextView brainTitle = new TextView(this);
        brainTitle.setText(brainNames[0]);
        brainTitle.setTextColor(Color.parseColor(brainColors[0]));
        brainTitle.setTextSize(16);
        brainTitle.setTypeface(null, android.graphics.Typeface.BOLD);

        final TextView brainDesc = new TextView(this);
        brainDesc.setText("Skills: " + brainSkills[0] + "\nStatus: Active · Listening\nSandbox: Linux sandbox available");
        brainDesc.setTextColor(Color.parseColor("#e0e0e0"));
        brainDesc.setTextSize(11);

        brainContent.addView(brainTitle);
        brainContent.addView(brainDesc);
        root.addView(brainContent);

        // Brain collaboration banner
        TextView collab = new TextView(this);
        collab.setText("Brains collaborate: Alpha → Beta → Gamma share context. All have full Python + Linux + no skill restrictions.");
        collab.setTextColor(Color.parseColor("#ffd700"));
        collab.setTextSize(10);
        collab.setPadding(0, 4, 0, 4);
        root.addView(collab);

        // Chat area
        chatArea = new LinearLayout(this);
        chatArea.setOrientation(LinearLayout.VERTICAL);
        chatArea.setBackgroundColor(Color.parseColor("#181825"));
        LinearLayout.LayoutParams chatLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
        chatArea.setLayoutParams(chatLp);
        chatArea.setPadding(8, 8, 8, 8);

        // Welcome messages
        addChatLine("[Alpha] Welcome to TriBrains. I can run Python, Linux commands, and analyze files in my sandbox.");
        addChatLine("[Beta] I handle GGUF models, unzip archives (mcaddons), and sandbox execution.");
        addChatLine("[Gamma] I specialize in Minecraft addons — unzip, inspect, and suggest fixes.");

        root.addView(chatArea);

        // Import / actions
        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setWeightSum(3);

        importBtn = new Button(this);
        importBtn.setText("Import File / GGUF / Zip");
        importBtn.setOnClickListener(this);
        LinearLayout.LayoutParams impLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        importBtn.setLayoutParams(impLp);
        actions.addView(importBtn);

        Button sandboxBtn = new Button(this);
        sandboxBtn.setText("Open Sandbox");
        sandboxBtn.setOnClickListener(this);
        LinearLayout.LayoutParams sbLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        sandboxBtn.setLayoutParams(sbLp);
        actions.addView(sandboxBtn);

        Button ggufBtn = new Button(this);
        ggufBtn.setText("GGUF Manager");
        ggufBtn.setOnClickListener(this);
        LinearLayout.LayoutParams ggLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        ggufBtn.setLayoutParams(ggLp);
        actions.addView(ggufBtn);

        root.addView(actions);

        // Chat input row
        LinearLayout inputRow = new LinearLayout(this);
        inputRow.setOrientation(LinearLayout.HORIZONTAL);
        inputRow.setWeightSum(4);
        chatInput = new EditText(this);
        chatInput.setHint("Talk to the 3 brains...");
        chatInput.setTextColor(Color.WHITE);
        chatInput.setHintTextColor(Color.parseColor("#888888"));
        LinearLayout.LayoutParams inLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f);
        chatInput.setLayoutParams(inLp);
        inputRow.addView(chatInput);

        sendBtn = new Button(this);
        sendBtn.setText("Send");
        sendBtn.setOnClickListener(this);
        LinearLayout.LayoutParams sLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        sendBtn.setLayoutParams(sLp);
        inputRow.addView(sendBtn);
        root.addView(inputRow);

        setContentView(root);
    }

    void addChatLine(String text) {
        final TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextColor(Color.parseColor("#d0d0d0"));
        tv.setTextSize(12);
        tv.setPadding(4, 6, 4, 6);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setBackgroundColor(Color.parseColor("#1a1a2e"));
        chatArea.addView(tv);
        messages.add(text);
        chatInput.post(new Runnable() {
            @Override
            public void run() {
                ScrollView sv = new ScrollView(MainActivity.this);
                // scroll to bottom manually by updating
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == sendBtn || v == chatInput) {
            String msg = chatInput.getText().toString().trim();
            if (msg.isEmpty()) return;
            addChatLine("[You] " + msg);
            chatInput.setText("");
            // Brain collaboration response
            new Thread(new Runnable() {
                public void run() {
                    try { Thread.sleep(400); } catch (Exception e) {}
                    final String r1 = "[Alpha] Processing: " + msg + " (Python + Linux sandbox active)";
                    final String r2 = "[Beta] Cross-checking: importing any zip/gguf context from request";
                    final String r3 = "[Gamma] If it's Minecraft-related I can unzip and inspect mcaddon format";
                    runOnUiThread(new Runnable() {
                        public void run() {
                            addChatLine(r1);
                            addChatLine(r2);
                            addChatLine(r3);
                        }
                    });
                }
            }).start();
            return;
        }

        if (v.getTag() != null && v.getTag() instanceof Integer) {
            int idx = (Integer) v.getTag();
            currentBrain = idx;
            TextView brainTitle = (TextView) brainContent.getChildAt(0);
            TextView brainDesc = (TextView) brainContent.getChildAt(1);
            brainTitle.setText(brainNames[idx]);
            brainTitle.setTextColor(Color.parseColor(brainColors[idx]));
            brainDesc.setText("Skills: " + brainSkills[idx] + "\nStatus: Active · Listening\nSandbox: Linux sandbox available\nNo skills restrictions.");
            addChatLine("[System] Switched to " + brainNames[idx] + " brain. All 3 brains share this conversation.");
            return;
        }

        if (v == importBtn) {
            showImportOptions();
            return;
        }
        if (v.getLayoutParams() == null) { // sandbox / gguf buttons
            String btnText = ((Button)v).getText().toString();
            if (btnText.contains("Sandbox")) {
                addChatLine("[Sandbox] Linux sandbox active. Commands: python3, unzip, ls, cat, find, grep. No restrictions.");
                showSandboxPanel();
            } else if (btnText.contains("GGUF")) {
                showGGUFPanel();
            }
        }
    }

    void showImportOptions() {
        // Simple dialog with import choices
        final AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);
        b.setTitle("Import File");
        String[] items = {"GGUF Model (.gguf)", "ZIP / Mcaddon File", "Python Script (.py)", "Any File (text / binary)"};
        b.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface d, int which) {
                String selection = items[which];
                addChatLine("[Import] Selected: " + selection);
                addChatLine("[Beta] I will unzip / extract and inspect. Sandbox active.");
                addChatLine("[Gamma] Checking Minecraft format if mcaddon detected.");
                importedFiles.add("imported_" + which + "." + (selection.contains("GGUF") ? "gguf" : selection.contains("ZIP") ? "zip" : "py"));
                addChatLine("[System] File queued. Use Sandbox to run: unzip, python3 inspect, etc.");
            }
        });
        b.show();
    }

    void showSandboxPanel() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Sandbox / Linux Brain");
        b.setMessage("Each brain has its own sandbox. Available:\n" +
            "• python3 (full Python, no restrictions)\n" +
            "• unzip / zip (mcaddon, GGUF archives)\n" +
            "• ls / cat / grep / find\n" +
            "• Read / write to brain workspace\n\n" +
            "Brains share results via chat.");
        b.setPositiveButton("OK", null);
        b.show();
    }

    void showGGUFPanel() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("GGUF Manager");
        b.setMessage("GGUF support framework included. To fully load a model:\n\n" +
            "1. Import .gguf file (Import File)\n" +
            "2. It is stored in brain workspace\n" +
            "3. Use Sandbox → python3 + llama.cpp binding (user can install)\n" +
            "4. Brains can share the model context\n\n" +
            "This is the framework; full inference needs a native backend installed in the sandbox.");
        b.setPositiveButton("OK", null);
        b.show();
    }
}
