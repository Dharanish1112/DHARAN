import javax.swing.*; import java.awt.*;
import java.awt.event.ActionEvent; import java.awt.event.ActionListener; import java.util.ArrayList;
import java.util.HashMap; import java.util.List; import java.util.Map;

public class QuizApplication extends JFrame { private JPanel questionPanel;
private JButton submitButton; private List<Question> questions;
private Map<Integer, Integer> userAnswers; private String userName;
private String rollNo; private String email;

public QuizApplication() { setTitle("Quiz Application");
setSize(600, 600); // Adjusted size to accommodate more questions setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null);

// Start with the name, roll number, and email entry screen showUserDetailsDialog();
}


// Show a dialog to enter the user's details (name, roll number, and email) private void showUserDetailsDialog() {
JPanel panel = new JPanel(new GridLayout(4, 2));


JTextField nameField = new JTextField(); JTextField rollNoField = new JTextField(); JTextField emailField = new JTextField();

panel.add(new JLabel("Enter your name:")); panel.add(nameField);
panel.add(new JLabel("Enter your roll number:")); panel.add(rollNoField);
panel.add(new JLabel("Enter your email:")); panel.add(emailField);

int option = JOptionPane.showConfirmDialog(this, panel, "Enter your details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

if (option == JOptionPane.OK_OPTION) { userName = nameField.getText(); rollNo = rollNoField.getText();
email = emailField.getText();


if (userName.isEmpty() || rollNo.isEmpty() || email.isEmpty()) {

JOptionPane.showMessageDialog(this, "All fields are required! Please enter valid information.", "Error", JOptionPane.ERROR_MESSAGE);
showUserDetailsDialog(); // Reopen the dialog if any field is empty
} else {
initializeQuiz();
}
} else {
System.exit(0); // Exit if user cancels
}
}


// Initialize the quiz questions private void initializeQuiz() {
questionPanel = new JPanel(); questionPanel.setLayout(new BoxLayout(questionPanel,
BoxLayout.Y_AXIS));
JScrollPane scrollPane = new JScrollPane(questionPanel);


submitButton = new JButton("Submit Quiz"); submitButton.addActionListener(new SubmitQuizListener());

add(scrollPane, BorderLayout.CENTER); add(submitButton, BorderLayout.SOUTH);

initializeQuestions(); // Populate quiz questions after the details are entered
}


// Initialize a local list of questions private void initializeQuestions() {

questions = new ArrayList<>(); userAnswers = new HashMap<>();

// Add example questions
questions.add(new Question(1, "What is the capital of France?", List.of("Paris", "Berlin", "Madrid", "Rome")));
questions.add(new Question(2, "Which programming language is used for Android development?", List.of("Java", "Python", "C++", "Ruby")));
questions.add(new Question(3, "What is 5 + 3?", List.of("5", "8", "10",
"15")));


// Additional questions
questions.add(new Question(4, "What is the largest planet in our solar system?", List.of("Earth", "Jupiter", "Mars", "Saturn")));
questions.add(new Question(5, "Who wrote 'To Kill a Mockingbird'?", List.of("Harper Lee", "J.K. Rowling", "George Orwell", "Jane Austen")));
questions.add(new Question(6, "Which element has the chemical symbol 'O'?", List.of("Oxygen", "Gold", "Iron", "Osmium")));
questions.add(new Question(7, "What is the square root of 64?", List.of("6", "8", "10", "16")));
questions.add(new Question(8, "In which year did World War II end?", List.of("1942", "1945", "1950", "1960")));
questions.add(new Question(9, "Who painted the Mona Lisa?", List.of("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet")));
questions.add(new Question(10, "What is the longest river in the world?", List.of("Amazon", "Nile", "Yangtze", "Mississippi")));

displayQuestions();

}


// Display questions and options private void displayQuestions() {
for (int i = 0; i < questions.size(); i++) { Question question = questions.get(i);

JLabel questionLabel = new JLabel((i + 1) + ". " + question.getQuestion());
questionPanel.add(questionLabel);


ButtonGroup buttonGroup = new ButtonGroup();
JPanel optionsPanel = new JPanel(new GridLayout(0, 1)); for (int j = 0; j < question.getOptions().size(); j++) {
JRadioButton optionButton = new JRadioButton(question.getOptions().get(j));
int questionId = question.getId(); int optionIndex = j;
optionButton.addActionListener(e -> userAnswers.put(questionId, optionIndex));
buttonGroup.add(optionButton); optionsPanel.add(optionButton);
}
questionPanel.add(optionsPanel);
}


revalidate(); repaint();
}



// Handle quiz submission
private class SubmitQuizListener implements ActionListener { @Override
public void actionPerformed(ActionEvent e) { int score = calculateScore();
JOptionPane.showMessageDialog(QuizApplication.this, "Hello, " + userName + " (Roll No: " + rollNo + ", Email: " + email + ")! Your score: " + score + "/" + questions.size());
}
}


// Calculate the score
private int calculateScore() { int score = 0;

// Correct answers for the questions
Map<Integer, Integer> correctAnswers = new HashMap<>(); correctAnswers.put(1, 0); // Paris
correctAnswers.put(2, 0); // Java correctAnswers.put(3, 1); // 8 correctAnswers.put(4, 1); // Jupiter correctAnswers.put(5, 0); // Harper Lee correctAnswers.put(6, 0); // Oxygen correctAnswers.put(7, 1); // 8
correctAnswers.put(8, 1); // 1945 correctAnswers.put(9, 2); // Leonardo da Vinci correctAnswers.put(10, 0); // Amazon

for (Map.Entry<Integer, Integer> entry : userAnswers.entrySet()) { int questionId = entry.getKey();
int selectedAnswer = entry.getValue();
if (correctAnswers.get(questionId) == selectedAnswer) { score++;
}
}


return score;
}


public static void main(String[] args) { SwingUtilities.invokeLater(() -> {
new QuizApplication().setVisible(true);
});
}
}


// Question class class Question { private int id;
private String question; private List<String> options;

public Question(int id, String question, List<String> options) { this.id = id;
this.question = question; this.options = options;
}



public int getId() { return id;
}


public String getQuestion() { return question;
}


public List<String> getOptions() { return options;
}
}
