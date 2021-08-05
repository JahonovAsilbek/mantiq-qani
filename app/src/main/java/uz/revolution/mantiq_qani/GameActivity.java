package uz.revolution.mantiq_qani;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import uz.revolution.mantiq_qani.dataBase.Database;
import uz.revolution.mantiq_qani.dataBase.QuestionsData;
import uz.revolution.mantiq_qani.dialogs.StartDialog;
import uz.revolution.mantiq_qani.dialogs.WinDialog;
import uz.revolution.mantiq_qani.fragment.InfoFragment;
import uz.revolution.mantiq_qani.fragment.MenuFragment;
import uz.revolution.mantiq_qani.manager.MyManager;
import uz.revolution.mantiq_qani.model.MyData;
import uz.revolution.mantiq_qani.model.MyModel;

public class GameActivity extends AppCompatActivity {

    private MyManager myManager;
    private QuestionsData questionsData = QuestionsData.getDatabase();
    private ArrayList<MyModel> arrayList;
    private String startName;
    private Timer timer;
    private TextView timeShow, questionShow;
    private int timercount;
    private String format_time = null;
    private ImageButton hint, deleteLetter;
    private TextView currentScore, totalScore, level;
    private ViewGroup variantGroup, answerGroup;
    private int totalCount;
    private int d = 1;
    private int h = 1;
    private Database database = Database.getDatabase();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        startDialog();
        timeShow();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        myManager = new MyManager();
        loadData(myManager.getLevel());
        loadView();
        loadDataToView();


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startDialog() {
        StartDialog dialog = new StartDialog(GameActivity.this);

        dialog
                .setOnStartClickListener(new StartDialog.OnStartClickListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onClick(StartDialog dialog, String name) {

                        int a = 0;
                        boolean b = false;
                        ArrayList<MyData> data = new ArrayList<>();
                        data = database.getUsersData();
                        for (int i = 0; i < data.size(); i++) {
                            if (name.equalsIgnoreCase(data.get(i).getName())) {
                                startName = name;
                                a = i;
                                b = true;
                                break;
                            }

                        }
                            if (b) {
                                myManager.setTotalScore(Integer.parseInt(data.get(a).getScore()));
                                myManager.setLevel(Integer.parseInt(data.get(a).getStatusLevel()));
                                Log.d("AAAA", "onClick: " + data.get(a).getName());
                                level.setText(String.valueOf(myManager.getLevel()));
                                totalScore.setText(String.valueOf(myManager.getTotalScore()));
                                loadData(myManager.getLevel());
                                loadDataToView();
                                dialog.cancel();
                            } else {
                                if (name.trim().length() > 0) {
                                    startName = name;
                                    dialog.cancel();
                                    Toast.makeText(GameActivity.this, name + " добро пожаловать в игру!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(GameActivity.this, name + " Введите имя!", Toast.LENGTH_SHORT).show();
                                }
                            }


                    }
                })
                .build();
    }

    public void timeShow() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timercount++;
                int hour;
                int minute;
                int second;

                hour = timercount / 3600;
                minute = (timercount - hour * 3600) / 60;
                if (minute > 60) {
                    hour += minute / 60;
                }

                second = (timercount - 3600 * hour - 60 * minute);
                if (second > 60) {
                    minute += second / 60;
                }

                format_time = String.format("%02d:%02d:%02d", hour, minute, second);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeShow.setText(format_time);
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    private void loadView() {
        answerGroup = findViewById(R.id.answer_group);
        variantGroup = findViewById(R.id.variant_group);
        hint = findViewById(R.id.hint);
        deleteLetter = findViewById(R.id.delete_letter);
        currentScore = findViewById(R.id.current_score);
        totalScore = findViewById(R.id.total_score);
        level = findViewById(R.id.level);
        timeShow = findViewById(R.id.time_show);
        questionShow = findViewById(R.id.question_show);
    }

    private void loadDataToView() {


        totalCount = 0;

        questionShow.setText(myManager.getQuestion());

        for (int i = 0; i < answerGroup.getChildCount(); i++) {
            Button button = (Button) answerGroup.getChildAt(i);
            button.setText("");
            if (i < myManager.getAnswerLength()) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button button = (Button) variantGroup.getChildAt(i);
            button.setVisibility(View.VISIBLE);
            button.setText(String.valueOf(myManager.getCurrentQuestion().getVariant().charAt(i)));
            button.setTag(String.valueOf(i));
        }
    }

    public void OnAnswerClick(View view) {
        Button button = (Button) view;
        undoLetter(button.getText().toString());
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button variant = (Button) variantGroup.getChildAt(i);
            if (variant.getTag().toString().equals(button.getTag().toString())) {
                button.setTag("");
                button.setText("");
                variant.setVisibility(View.VISIBLE);
                totalCount--;
            }
        }
    }

    public void hint(View view) {
        for (Integer.parseInt(String.valueOf(h)); h <= 2; ) {
            for (int position = 0; position < answerGroup.getChildCount(); position++) {
                Button answer = (Button) answerGroup.getChildAt(position);
                if (answer.getText().toString().equals("") && answer.getVisibility() == View.VISIBLE) {
                    answer.setText(String.valueOf(arrayList.get(myManager.getLevel()).getAnswer().charAt(position)));
                    variantGroup.getChildAt(findLetter(position)).setVisibility(View.INVISIBLE);
                    myManager.setCurrentScore(myManager.getCurrentScore() - 2);
                    currentScore.setText(String.valueOf(myManager.getCurrentScore()));
                    answer.setTag(variantGroup.getChildAt(myManager.getLevel()).getTag().toString());
                    totalCount++;
                    break;
                }
            }
            if (totalCount == myManager.getAnswerLength()) {
                checkWin();
            }
            totalScore.setText(String.valueOf(myManager.getTotalScore()));
            h++;
            break;
        }
    }

    public void deleteLetter(View view) {
        for (Integer.parseInt(String.valueOf(d)); d <= 2; ) {
            for (int position = 0; position < variantGroup.getChildCount(); position++) {
                Button btn = (Button) variantGroup.getChildAt(position);
                if (!btn.getText().toString().equalsIgnoreCase("")) {
                    variantGroup.getChildAt(findWrongLetter(position)).setVisibility(View.INVISIBLE);
                    myManager.setCurrentScore(myManager.getCurrentScore() - 2);
                    currentScore.setText(String.valueOf(myManager.getCurrentScore()));
                    break;
                }
            }
            d++;
            break;
        }
    }

    public int findWrongLetter(int position) {
        int a = 0;
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button btn = (Button) variantGroup.getChildAt(i);
            if (btn.getVisibility() == View.VISIBLE && !arrayList.get(myManager.getLevel()).getAnswer().contains(btn.getText().toString())) {
                a = i;
                break;
            }
        }
        return a;
    }

    public int findLetter(int position) {
        int a = 0;
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button btn = (Button) variantGroup.getChildAt(i);
            if (btn.getVisibility() == View.VISIBLE &&
                    btn.getText().toString().equals(arrayList.get(myManager.getLevel()).getAnswer().charAt(position) + "")) {
                a = i;
            }
        }
        return a;
    }

    public void OnVariantClick(View view) {
        Button button = (Button) view;
        for (int i = 0; i < answerGroup.getChildCount(); i++) {
            Button answer = (Button) answerGroup.getChildAt(i);
            if (answer.getText().toString().equals("") && answer.getVisibility() == View.VISIBLE) {
                answer.setText(button.getText().toString());
                button.setVisibility(View.INVISIBLE);
                answer.setTag(button.getTag().toString());
                totalCount++;
                break;
            }
        }

        Log.i("AAAA", "OnVariantClick: " + totalCount);

        if (totalCount == myManager.getAnswerLength()) {
            checkWin();
        }

    }

    private void undoLetter(String letter) {
        for (int i = 0; i < variantGroup.getChildCount(); i++) {
            Button btn = (Button) variantGroup.getChildAt(i);
            if (btn.getVisibility() == View.INVISIBLE && btn.getText().toString().equals(letter)) {
                btn.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void checkWin() {

        String s = "";
        for (int i = 0; i < answerGroup.getChildCount(); i++) {

            Button button = (Button) answerGroup.getChildAt(i);

            s = s.concat(button.getText().toString());
        }

        if (myManager.checkAnswer(s)) {

            level.setText(String.valueOf(myManager.getLevel() + 1));
            currentScore.setText(String.valueOf(myManager.getCurrentScore()));
            totalScore.setText(String.valueOf(myManager.getTotalScore()));
//            loadData();
            if (!myManager.isEnd()) {
                loadDataToView();
                d = 1;
                h = 1;
            } else {
                level.setText(String.valueOf(myManager.getLevel()));
                Toast.makeText(this, "Уровен пройден", Toast.LENGTH_SHORT).show();

                final WinDialog dialog = new WinDialog(this);

                dialog.setText(myManager.getTotalScore(), timeShow.getText().toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.setOnPositiveClickListener(new WinDialog.OnPositiveClickListener() {
                        @Override
                        public void onClick(WinDialog winDialog) {
                            database.insertData(
                                    startName,
                                    String.valueOf(myManager.getTotalScore()),
                                    timeShow.getText().toString(),
                                    String.valueOf(myManager.getLevel())
                            );
                            loadData(myManager.getLevel()+1);
                            level.setText(String.valueOf(myManager.getLevel() + 1));
                            currentScore.setText(String.valueOf(myManager.getCurrentScore()));
                            totalScore.setText(String.valueOf(myManager.getTotalScore()));
                            loadDataToView();
                            d = 1;
                            h = 1;
                            dialog.cancel();
                        }
                    })
                            .setOnPlayAgainClickListener(new WinDialog.OnPlayAgainClickListener() {
                                @Override
                                public void onClick(WinDialog winDialog) {
                                    myManager.setLevel(myManager.getLevel() - 4);
                                    myManager.setTotalScore(database.getScore("score_"));
                                    totalScore.setText(String.valueOf(myManager.getTotalScore()));
                                    loadData(myManager.getLevel() );
                                    level.setText(String.valueOf(myManager.getLevel()));
                                    loadDataToView();
                                    timeShow();
                                    d = 1;
                                    h = 1;
                                    dialog.cancel();
                                }
                            })
                            .build();
                }
            }
        } else {
            Toast.makeText(this, "Lose", Toast.LENGTH_SHORT).show();
        }

    }

    public void onInfoClick(View view) {

        InfoFragment fragment = new InfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString("comment", myManager.getCurrentQuestion().getSource() + "\n\n" + myManager.getCurrentQuestion().getAuthor());

        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("fragment_1")
                .commit();

    }

    public void onMenuClick(View view) {
        MenuFragment fragment = new MenuFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("fragment_1")
                .commit();

    }

    private void loadData(int level) {
        level = myManager.getLevel();
        arrayList = new ArrayList<>();
        Random random = new Random();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<MyModel> data = new ArrayList<>();
        data = questionsData.getData(level);

        String s = "ёйфячыцувсмакепитрнгоьблшщдюжэзхъ";

        for (int i = 0; i < data.size(); i++) {
            strings.add(data.get(i).getAnswer());
        }

        for (int i = 0; i < strings.size(); i++) {
            String array = strings.get(i);

            while (array.length() < 20) {
                array = array.concat(String.valueOf(s.charAt(random.nextInt(s.length()))));
            }

            List<Character> characters = new ArrayList<>();
            for (char c : array.toCharArray()) {
                characters.add(c);
            }
            StringBuilder output = new StringBuilder(array.length());
            while (characters.size() != 0) {
                int randPicker = (int) (Math.random() * characters.size());
                output.append(characters.remove(randPicker));
            }


            strings.set(i, output.toString());
        }

        for (int i = 0; i < data.size(); i++) {
            data.get(i).setVariant(strings.get(i));
        }

        for (int i = 0; i < questionsData.getData(level).size(); i++) {
            arrayList.add(new MyModel(
                    data.get(i).getVariant().trim(),
                    questionsData.getData(level).get(i).getQuestion().trim(),
                    questionsData.getData(level).get(i).getAnswer().trim(),
                    questionsData.getData(level).get(i).getComment(),
                    questionsData.getData(level).get(i).getSource(),
                    questionsData.getData(level).get(i).getAuthor()));
        }
        myManager.setQuestions(arrayList);
    }

}
