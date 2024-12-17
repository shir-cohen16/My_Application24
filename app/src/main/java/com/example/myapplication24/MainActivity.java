package com.example.myapplication24;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int score = 0;
    private int currentQuestion = 0;
    private List<Question> questions = new ArrayList<>();

    static class Question {
        String questionText;
        List<String> options;
        String correctAnswer;

        Question(String questionText, List<String> options, String correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // יצירת שאלות דינמיות
        generateQuestions();

        // הצגת השאלה הראשונה
        displayQuestion();

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnswer();
            }
        });
    }

    // יצירת שאלות דינמיות
    private void generateQuestions() {
        Random random = new Random();

        // יצירת 10 שאלות מתמטיות
        for (int i = 0; i < 10; i++) {
            int num1 = random.nextInt(10) + 1;
            int num2 = random.nextInt(10) + 1;

            // בוחרים סוג פעולה אקראי (חיבור, חיסור, כפל, חילוק, חזקות, שורש)
            int operation = random.nextInt(6); // בין 0 ל-5

            Question question = null;
            List<String> options = new ArrayList<>();
            String correctAnswer = "";

            switch (operation) {
                case 0: // חיבור
                    question = createAdditionQuestion(num1, num2);
                    break;
                case 1: // חיסור
                    question = createSubtractionQuestion(num1, num2);
                    break;
                case 2: // כפל
                    question = createMultiplicationQuestion(num1, num2);
                    break;
                case 3: // חילוק
                    question = createDivisionQuestion(num1, num2);
                    break;
                case 4: // חזקות
                    question = createExponentiationQuestion(num1, num2);
                    break;
                case 5: // שורש
                    question = createSquareRootQuestion(num1);
                    break;
            }

            // הוספת השאלה לרשימה
            questions.add(question);
        }
    }

    // יצירת שאלה לחיבור
    private Question createAdditionQuestion(int num1, int num2) {
        int correctAnswer = num1 + num2;
        List<String> options = generateOptions(correctAnswer);
        String questionText = num1 + " + " + num2;
        return new Question(questionText, options, String.valueOf(correctAnswer));
    }

    // יצירת שאלה לחיסור
    private Question createSubtractionQuestion(int num1, int num2) {
        int correctAnswer = num1 - num2;
        List<String> options = generateOptions(correctAnswer);
        String questionText = num1 + " - " + num2;
        return new Question(questionText, options, String.valueOf(correctAnswer));
    }

    // יצירת שאלה לכפל
    private Question createMultiplicationQuestion(int num1, int num2) {
        int correctAnswer = num1 * num2;
        List<String> options = generateOptions(correctAnswer);
        String questionText = num1 + " * " + num2;
        return new Question(questionText, options, String.valueOf(correctAnswer));
    }

    // יצירת שאלה לחילוק
    private Question createDivisionQuestion(int num1, int num2) {
        if (num2 == 0) {
            num2 = 1; // כדי לא לחלק ב-0
        }
        float correctAnswer = (float) num1 / num2;
        List<String> options = generateOptionsForDivision(correctAnswer);
        String questionText = num1 + " / " + num2;
        return new Question(questionText, options, String.valueOf(correctAnswer));
    }

    // יצירת שאלה לחזקות
    private Question createExponentiationQuestion(int base, int exponent) {
        int correctAnswer = (int) Math.pow(base, exponent);
        List<String> options = generateOptions(correctAnswer);
        String questionText = base + " ^ " + exponent;
        return new Question(questionText, options, String.valueOf(correctAnswer));
    }

    // יצירת שאלה לשורש ריבועי
    private Question createSquareRootQuestion(int num) {
        double correctAnswer = Math.sqrt(num);
        List<String> options = generateOptionsForDivision(correctAnswer);
        String questionText = "√" + num;
        return new Question(questionText, options, String.format("%.2f", correctAnswer));
    }

    // פונקציה ליצירת אפשרויות תשובה
    private List<String> generateOptions(int correctAnswer) {
        List<String> options = new ArrayList<>();
        options.add(String.valueOf(correctAnswer));  // תשובה נכונה
        options.add(String.valueOf(correctAnswer + 1)); // תשובה שגויה
        options.add(String.valueOf(correctAnswer - 1)); // תשובה שגויה
        options.add(String.valueOf(correctAnswer + 2)); // תשובה שגויה

        // ערבוב תשובות
        return shuffleOptions(options);
    }

    // פונקציה ליצירת אפשרויות תשובה עבור חילוק
    private List<String> generateOptionsForDivision(float correctAnswer) {
        List<String> options = new ArrayList<>();
        options.add(String.format("%.2f", correctAnswer));  // תשובה נכונה
        options.add(String.format("%.2f", correctAnswer + 0.1)); // תשובה שגויה
        options.add(String.format("%.2f", correctAnswer - 0.1)); // תשובה שגויה
        options.add(String.format("%.2f", correctAnswer + 0.2)); // תשובה שגויה

        // ערבוב תשובות
        return shuffleOptions(options);
    }

    // ערבוב אפשרויות תשובה
    private List<String> shuffleOptions(List<String> options) {
        Random random = new Random();
        for (int i = 0; i < options.size(); i++) {
            int randomIndex = random.nextInt(options.size());
            String temp = options.get(i);
            options.set(i, options.get(randomIndex));
            options.set(randomIndex, temp);
        }
        return options;
    }

    private void displayQuestion() {
        if (currentQuestion < questions.size()) {
            Question currentQ = questions.get(currentQuestion);

            TextView questionText = findViewById(R.id.question_text);
            questionText.setText(currentQ.questionText);

            RadioButton optionA = findViewById(R.id.option_a);
            RadioButton optionB = findViewById(R.id.option_b);
            RadioButton optionC = findViewById(R.id.option_c);
            RadioButton optionD = findViewById(R.id.option_d);

            optionA.setText(currentQ.options.get(0));
            optionB.setText(currentQ.options.get(1));
            optionC.setText(currentQ.options.get(2));
            optionD.setText(currentQ.options.get(3));

            RadioGroup optionsGroup = findViewById(R.id.options_group);
            optionsGroup.clearCheck();
        } else {
            TextView questionText = findViewById(R.id.question_text);
            questionText.setText("סיימת את כל השאלות! הציון הסופי שלך: " + score + "/10");

            Button submitButton = findViewById(R.id.submit_button);
            submitButton.setEnabled(false);
        }
    }

    private void handleAnswer() {
        RadioGroup optionsGroup = findViewById(R.id.options_group);
        int selectedId = optionsGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            return; // לא נבחרה תשובה
        }

        RadioButton selectedOption = findViewById(selectedId);
        String selectedAnswer = selectedOption.getText().toString();

        Question currentQ = questions.get(currentQuestion);
        if (selectedAnswer.equals(currentQ.correctAnswer)) {
            score++;
        }

        currentQuestion++;
        displayQuestion();
    }
}
