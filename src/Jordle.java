import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Puxuan Wang.
 * @version 1.0.
 *          It is the Jordle GUI program.
 */
public class Jordle extends Application {
    int counter = 0;
    int instructionsCounter = 0;
    int radInt = 0;
    ComboBox<Object> languagePane = new ComboBox<Object>();
    Label labelForCurrentStatisticsValue = new Label();

    /**
     * This method is used to generate the error window.
     */
    public void invalidInput() {
        Stage windowForError = new Stage();
        windowForError.initModality(Modality.APPLICATION_MODAL);
        windowForError.setTitle("Error");
        windowForError.setMinWidth(300);
        windowForError.setMinHeight(300);
        Label labelForError = new Label();
        labelForError.setText("Please enter a 5-letter word.");
        if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                || languagePane.getValue().equals("英語")) {
            labelForError.setText("Please enter a 5-letter word.");

        } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                || languagePane.getValue().equals("中国語")) {
            labelForError.setText("请输入5个字母的单词。");
        } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                || languagePane.getValue().equals("スペイン語")) {
            labelForError.setText("Por favor, introduzca una palabra de 5 letras.");
        } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                || languagePane.getValue().equals("スペイン語")) {
            labelForError.setText("Please enter a 5-letter word.");
        } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                || languagePane.getValue().equals("ロシア語")) {
            labelForError.setText("Пожалуйста, введите 5-буквенное слово.");
        } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                || languagePane.getValue().equals("日本語")) {
            labelForError.setText("5文字の言葉を入力してください。");
        }
        labelForError.setFont(new Font("Arial", 20));
        HBox hBoxButtonForError = new HBox(10);
        Button closeButtonForError = new Button("OK");
        closeButtonForError.setFont(new Font("Arial", 20));
        hBoxButtonForError.getChildren().addAll(closeButtonForError);
        hBoxButtonForError.setAlignment(Pos.CENTER_RIGHT);
        closeButtonForError.setOnAction(e -> windowForError.close());
        VBox layoutForError = new VBox(10);
        layoutForError.getChildren().addAll(labelForError, hBoxButtonForError);
        layoutForError.setAlignment(Pos.CENTER);
        Scene sceneForError = new Scene(layoutForError);
        windowForError.setScene(sceneForError);
        windowForError.showAndWait();
    }

    /**
     * This method is used to evaluator the input word and generate the result.
     * @param input         the input word.
     * @param searchedValue the value we want to search.
     * @return the result.
     */
    public int[] evaluator(String[] input, String searchedValue) {
//        System.out.println(searchedValue);
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i].toLowerCase();
        }
        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            if (input[i].charAt(0) == (searchedValue.charAt(i))) {
                result[i] = 1;
            }
        }
        for (int i = 0; i < input.length; i++) {
            if (result[i] == 1) {
                continue;
            } else if (result[i] != 1 && searchedValue.contains(input[i])) {
                result[i] = 2;
            }
        }
        return result;
    }

    /**
     * This is the main method to launch the program.
     * @param args the input arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is used to generate the main window.
     * @param primaryStage the main window.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        InnerShadow is = new InnerShadow();
        is.setOffsetX(2.0f);
        is.setOffsetY(2.0f);

        Label title = new Label();
        title.setText("Jordle");
        title.setEffect(is);
        title.setTextFill(Color.GREENYELLOW);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 80));

        Label labelForLightAndDark = new Label("Light/Dark Mode");
        labelForLightAndDark.setStyle("-fx-font: 12px \"Arial\";");
        ToggleGroup groupforTB = new ToggleGroup();
        ToggleButton tbForLight = new ToggleButton("Light");
        tbForLight.setStyle("-fx-font: 12px \"Arial\";");
        tbForLight.setToggleGroup(groupforTB);
        tbForLight.setSelected(true);
        ToggleButton tbForDark = new ToggleButton("Dark");
        tbForDark.setStyle("-fx-font: 12px \"Arial\";");
        tbForDark.setToggleGroup(groupforTB);

        HBox hBoxForLightAndDark = new HBox(2);
        hBoxForLightAndDark.getChildren().addAll(labelForLightAndDark, tbForLight, tbForDark);

        // FileReader fr = new FileReader("statisticsFile.txt");

        Label labelForTimer = new Label("Timer: ");
        labelForTimer.setStyle("-fx-font: 12px \"Arial\";");
        Label labelForStatistics = new Label();
        labelForStatistics.setStyle("-fx-font: 12px \"Arial\";");
        DoubleProperty timeForStatistics = new SimpleDoubleProperty();
        labelForStatistics.textProperty().bind(timeForStatistics.asString("%.2f seconds"));
        BooleanProperty running = new SimpleBooleanProperty();
        AnimationTimer timerStartAndStop = new AnimationTimer() {
            private long startTime = 0;

            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                running.set(true);
                super.start();
            }

            @Override
            public void stop() {
                running.set(false);
                super.stop();
            }

            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                timeForStatistics.set((now - startTime) / 1000.0);
            }
        };
        timerStartAndStop.start();

        Button statistics = new Button("Show statistics");
        statistics.setStyle("-fx-font: 12px \"Arial\";");
        labelForCurrentStatisticsValue.setText("0.000 seconds");
        statistics.setOnAction(e -> {
            try {
                File in = new File("statisticsFile.txt");
                Scanner sc = new Scanner(in);
                ArrayList<String> statisticsList = new ArrayList<>();
                int i = 0;
                while (sc.hasNextLine()) {
                    statisticsList.add(sc.nextLine());
                    i++;
                }
                sc.close();
                ArrayList<Double> resultList = new ArrayList<Double>(statisticsList.size());
                for (String value : statisticsList) {
                    resultList.add(Double.valueOf(value));
                }
                Collections.sort(resultList);
                Label labelForStatisticsTitle = new Label("Statistics");
                labelForStatisticsTitle.setStyle("-fx-font: 20px \"Arial\";");
                Label labelForCurrentStatistics = new Label("Current statistics: ");
                labelForCurrentStatistics.setStyle("-fx-font: 12px \"Arial\";");
                labelForCurrentStatisticsValue
                        .setStyle("-fx-font: 12px \"Arial\"; -fx-font-style: italic; -fx-font-weight: bold;");
                // labelForCurrentStatisticsValue.setText(String.valueOf(resultList.get(resultList.size()
                // - 1)));
                Label labelForStatisticsText1 = new Label();
                labelForStatisticsText1.setStyle("-fx-font: 12px \"Arial\";");
                labelForStatisticsText1.setText("1: " + resultList.get(0));
                Label labelForStatisticsText2 = new Label();
                labelForStatisticsText2.setStyle("-fx-font: 12px \"Arial\";");
                labelForStatisticsText2.setText("2: " + resultList.get(1));
                Label labelForStatisticsText3 = new Label();
                labelForStatisticsText3.setStyle("-fx-font: 12px \"Arial\";");
                labelForStatisticsText3.setText("3: " + resultList.get(2));
                Label labelForStatisticsText4 = new Label();
                labelForStatisticsText4.setStyle("-fx-font: 12px \"Arial\";");
                labelForStatisticsText4.setText("4: " + resultList.get(3));
                Label labelForStatisticsText5 = new Label();
                labelForStatisticsText5.setStyle("-fx-font: 12px \"Arial\";");
                labelForStatisticsText5.setText("5: " + resultList.get(4));
                Button closeButtonForStatistics = new Button("OK");
                closeButtonForStatistics.setStyle("-fx-font: 12px \"Arial\";");
                HBox hBoxForStatistics = new HBox(2);
                hBoxForStatistics.getChildren().addAll(labelForCurrentStatistics, labelForCurrentStatisticsValue);
                hBoxForStatistics.setAlignment(Pos.CENTER);
                VBox layoutForStatistics = new VBox(10);
                layoutForStatistics.getChildren().addAll(labelForStatisticsTitle, hBoxForStatistics,
                        labelForCurrentStatisticsValue, labelForStatisticsText1, labelForStatisticsText2,
                        labelForStatisticsText3, labelForStatisticsText4, labelForStatisticsText5,
                        closeButtonForStatistics);
                layoutForStatistics.setAlignment(Pos.CENTER);
                Scene sceneForStatistics = new Scene(layoutForStatistics, 300, 300);
                Stage windowForStatistics = new Stage();
                closeButtonForStatistics.setOnAction(e1 -> windowForStatistics.close());
                windowForStatistics.setTitle("Statistics");
                windowForStatistics.setMinWidth(350);
                windowForStatistics.setMinHeight(350);
                windowForStatistics.setScene(sceneForStatistics);
                windowForStatistics.initModality(Modality.APPLICATION_MODAL);
                windowForStatistics.initOwner(primaryStage);
                windowForStatistics.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        windowForStatistics.hide();
                    }
                });
                windowForStatistics.show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox hBoxForTimer = new HBox(2);
        hBoxForTimer.getChildren().addAll(labelForTimer, labelForStatistics);

        VBox vBoxForFunction = new VBox(2);
        vBoxForFunction.getChildren().addAll(languagePane, hBoxForLightAndDark, statistics, hBoxForTimer);

        Image guessImage = new Image("guess.jpg");
        Image failImage = new Image("Fail.png");
        Image successImage = new Image("success.jpg");

        ImageView stateView = new ImageView(guessImage);
        stateView.setFitHeight(100);
        stateView.setFitWidth(100);

        Rectangle rec11 = new Rectangle(0, 0, 80, 80);
        rec11.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text11 = new Text("");

        Rectangle rec12 = new Rectangle(0, 0, 80, 80);
        rec12.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text12 = new Text("");

        Rectangle rec13 = new Rectangle(0, 0, 80, 80);
        rec13.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text13 = new Text("");

        Rectangle rec14 = new Rectangle(0, 0, 80, 80);
        rec14.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text14 = new Text("");

        Rectangle rec15 = new Rectangle(0, 0, 80, 80);
        rec15.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text15 = new Text("");

        Rectangle rec21 = new Rectangle(0, 0, 80, 80);
        rec21.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text21 = new Text("");

        Rectangle rec22 = new Rectangle(0, 0, 80, 80);
        rec22.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text22 = new Text("");

        Rectangle rec23 = new Rectangle(0, 0, 80, 80);
        rec23.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text23 = new Text("");

        Rectangle rec24 = new Rectangle(0, 0, 80, 80);
        rec24.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text24 = new Text("");

        Rectangle rec25 = new Rectangle(0, 0, 80, 80);
        rec25.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text25 = new Text("");

        Rectangle rec31 = new Rectangle(0, 0, 80, 80);
        rec31.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text31 = new Text("");

        Rectangle rec32 = new Rectangle(0, 0, 80, 80);
        rec32.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text32 = new Text("");

        Rectangle rec33 = new Rectangle(0, 0, 80, 80);
        rec33.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text33 = new Text("");

        Rectangle rec34 = new Rectangle(0, 0, 80, 80);
        rec34.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text34 = new Text("");

        Rectangle rec35 = new Rectangle(0, 0, 80, 80);
        rec35.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text35 = new Text("");

        Rectangle rec41 = new Rectangle(0, 0, 80, 80);
        rec41.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text41 = new Text("");

        Rectangle rec42 = new Rectangle(0, 0, 80, 80);
        rec42.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text42 = new Text("");

        Rectangle rec43 = new Rectangle(0, 0, 80, 80);
        rec43.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text43 = new Text("");

        Rectangle rec44 = new Rectangle(0, 0, 80, 80);
        rec44.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text44 = new Text("");

        Rectangle rec45 = new Rectangle(0, 0, 80, 80);
        rec45.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text45 = new Text("");

        Rectangle rec51 = new Rectangle(0, 0, 80, 80);
        rec51.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text51 = new Text("");

        Rectangle rec52 = new Rectangle(0, 0, 80, 80);
        rec52.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text52 = new Text("");

        Rectangle rec53 = new Rectangle(0, 0, 80, 80);
        rec53.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text53 = new Text("");

        Rectangle rec54 = new Rectangle(0, 0, 80, 80);
        rec54.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text54 = new Text("");

        Rectangle rec55 = new Rectangle(0, 0, 80, 80);
        rec55.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text55 = new Text("");

        Rectangle rec61 = new Rectangle(0, 0, 80, 80);
        rec61.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text61 = new Text("");

        Rectangle rec62 = new Rectangle(0, 0, 80, 80);
        rec62.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text62 = new Text("");

        Rectangle rec63 = new Rectangle(0, 0, 80, 80);
        rec63.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text63 = new Text("");

        Rectangle rec64 = new Rectangle(0, 0, 80, 80);
        rec64.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text64 = new Text("");

        Rectangle rec65 = new Rectangle(0, 0, 80, 80);
        rec65.setStyle("-fx-fill: white; -fx-stroke: gray; -fx-stroke-width: 2;");
        Text text65 = new Text("");

        StackPane stackPaneFor11 = new StackPane();
        stackPaneFor11.getChildren().addAll(rec11, text11);
        StackPane stackPaneFor12 = new StackPane();
        stackPaneFor12.getChildren().addAll(rec12, text12);
        StackPane stackPaneFor13 = new StackPane();
        stackPaneFor13.getChildren().addAll(rec13, text13);
        StackPane stackPaneFor14 = new StackPane();
        stackPaneFor14.getChildren().addAll(rec14, text14);
        StackPane stackPaneFor15 = new StackPane();
        stackPaneFor15.getChildren().addAll(rec15, text15);

        StackPane stackPaneFor21 = new StackPane();
        stackPaneFor21.getChildren().addAll(rec21, text21);
        StackPane stackPaneFor22 = new StackPane();
        stackPaneFor22.getChildren().addAll(rec22, text22);
        StackPane stackPaneFor23 = new StackPane();
        stackPaneFor23.getChildren().addAll(rec23, text23);
        StackPane stackPaneFor24 = new StackPane();
        stackPaneFor24.getChildren().addAll(rec24, text24);
        StackPane stackPaneFor25 = new StackPane();
        stackPaneFor25.getChildren().addAll(rec25, text25);

        StackPane stackPaneFor31 = new StackPane();
        stackPaneFor31.getChildren().addAll(rec31, text31);
        StackPane stackPaneFor32 = new StackPane();
        stackPaneFor32.getChildren().addAll(rec32, text32);
        StackPane stackPaneFor33 = new StackPane();
        stackPaneFor33.getChildren().addAll(rec33, text33);
        StackPane stackPaneFor34 = new StackPane();
        stackPaneFor34.getChildren().addAll(rec34, text34);
        StackPane stackPaneFor35 = new StackPane();
        stackPaneFor35.getChildren().addAll(rec35, text35);

        StackPane stackPaneFor41 = new StackPane();
        stackPaneFor41.getChildren().addAll(rec41, text41);
        StackPane stackPaneFor42 = new StackPane();
        stackPaneFor42.getChildren().addAll(rec42, text42);
        StackPane stackPaneFor43 = new StackPane();
        stackPaneFor43.getChildren().addAll(rec43, text43);
        StackPane stackPaneFor44 = new StackPane();
        stackPaneFor44.getChildren().addAll(rec44, text44);
        StackPane stackPaneFor45 = new StackPane();
        stackPaneFor45.getChildren().addAll(rec45, text45);

        StackPane stackPaneFor51 = new StackPane();
        stackPaneFor51.getChildren().addAll(rec51, text51);
        StackPane stackPaneFor52 = new StackPane();
        stackPaneFor52.getChildren().addAll(rec52, text52);
        StackPane stackPaneFor53 = new StackPane();
        stackPaneFor53.getChildren().addAll(rec53, text53);
        StackPane stackPaneFor54 = new StackPane();
        stackPaneFor54.getChildren().addAll(rec54, text54);
        StackPane stackPaneFor55 = new StackPane();
        stackPaneFor55.getChildren().addAll(rec55, text55);

        StackPane stackPaneFor61 = new StackPane();
        stackPaneFor61.getChildren().addAll(rec61, text61);
        StackPane stackPaneFor62 = new StackPane();
        stackPaneFor62.getChildren().addAll(rec62, text62);
        StackPane stackPaneFor63 = new StackPane();
        stackPaneFor63.getChildren().addAll(rec63, text63);
        StackPane stackPaneFor64 = new StackPane();
        stackPaneFor64.getChildren().addAll(rec64, text64);
        StackPane stackPaneFor65 = new StackPane();
        stackPaneFor65.getChildren().addAll(rec65, text65);

        HBox hBoxForLineOne = new HBox(2);
        hBoxForLineOne.getChildren().addAll(stackPaneFor11, stackPaneFor12,
                stackPaneFor13, stackPaneFor14, stackPaneFor15);
        HBox hBoxForLineSecond = new HBox(2);
        hBoxForLineSecond.getChildren().addAll(stackPaneFor21,
                stackPaneFor22, stackPaneFor23, stackPaneFor24,
                stackPaneFor25);
        HBox hBoxForLineThird = new HBox(2);
        hBoxForLineThird.getChildren().addAll(stackPaneFor31,
                stackPaneFor32, stackPaneFor33, stackPaneFor34,
                stackPaneFor35);
        HBox hBoxForLineFourth = new HBox(2);
        hBoxForLineFourth.getChildren().addAll(stackPaneFor41,
                stackPaneFor42, stackPaneFor43, stackPaneFor44,
                stackPaneFor45);
        HBox hBoxForLineFifth = new HBox(2);
        hBoxForLineFifth.getChildren().addAll(stackPaneFor51,
                stackPaneFor52, stackPaneFor53, stackPaneFor54,
                stackPaneFor55);
        HBox hBoxForLineSixth = new HBox(2);
        hBoxForLineSixth.getChildren().addAll(stackPaneFor61,
                stackPaneFor62, stackPaneFor63, stackPaneFor64,
                stackPaneFor65);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(hBoxForLineOne, 0, 1);
        gridPane.add(hBoxForLineSecond, 0, 2);
        gridPane.add(hBoxForLineThird, 0, 3);
        gridPane.add(hBoxForLineFourth, 0, 4);
        gridPane.add(hBoxForLineFifth, 0, 5);
        gridPane.add(hBoxForLineSixth, 0, 6);
        gridPane.setVgap(2);

        Line line = new Line(0, 450, 500, 450);
        line.setStrokeWidth(2);
        line.setStroke(Color.GRAY);
        line.setOpacity(0.8);

        // Image image = new
        // Image("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png");
        // ImageView imageView = new ImageView(image);
        // imageView.setFitHeight(100);

        Random rand = new Random();
        radInt = rand.nextInt(Words.list.size());

        Text promptMessage = new Text();
        promptMessage.setFont(new Font("Verdana", 10));
        promptMessage.setText("Try guessing a word! ");

        Button buttonOfInstructions = new Button("Instructions");
        buttonOfInstructions.setFont(new Font("Verdana", 10));
        buttonOfInstructions.setText("Instructions");
        buttonOfInstructions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                instructionsCounter++;
                Label label1 = new Label(
                        "Guess the WORDLE in six tries. Each guess must be a valid five-letter word. "
                                + "Hit the enter button to submit. After each guess, "
                                + "the color of the tiles will change to show how close your guess was to the word.");
                label1.setFont(new Font("Verdana", 10));
                if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                        || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                        || languagePane.getValue().equals("英語")) {
                    label1.setText(
                            "Guess the WORDLE in six tries. Each guess must be a valid five-letter word. "
                                    + "Hit the enter button to submit. After each guess, "
                                    + "the color of the tiles will change "
                                    + "to show how close your guess was to the word.");
                } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                        || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                        || languagePane.getValue().equals("中国語")) {
                    label1.setText(
                            "在六次机会中猜出WORDLE。每次猜测必须是五个字母的有效单词。点击提交按钮提交。"
                                    + "每次猜测后，砖块的颜色将改变以显示您的猜测与单词的相似程度。");
                } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                        || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                        || languagePane.getValue().equals("スペイン語")) {
                    label1.setText(
                            "En seis intentos adivina WORDLE. "
                                    + "Cada intento debe ser una palabra válida de cinco letras. "
                                    + "Haz clic en el botón de envío para enviar. Después de cada intento, "
                                    + "el color de las baldosas cambiará para mostrar cómo cerca de la palabra.");
                } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                        || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                        || languagePane.getValue().equals("スペイン語")) {
                    label1.setText(
                            "En seis intentos adivina WORDLE. "
                                    + "Cada intento debe ser una palabra válida de cinco letras. "
                                    + "Haz clic en el botón de envío para enviar. Después de cada intento, "
                                    + "el color de las baldosas cambiará para mostrar cómo cerca de la palabra.");
                } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                        || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                        || languagePane.getValue().equals("ロシア語")) {
                    label1.setText(
                            "В шести попытках угадать WORDLE. Каждая попытка "
                                    + "должна быть действительным словом пяти букв. "
                                    + "Нажмите кнопку отправки, чтобы отправить. После каждой попытки, "
                                    + "цвет кирпичей будет изменен, чтобы указать, как близко к слову.");
                } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                        || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                        || languagePane.getValue().equals("日本語")) {
                    label1.setText(
                            "六回試行でWORDLEを答えます。それぞれの試行は有効な五文字の単語である必要があります。"
                                    + "提出ボタンをクリックして送信します。それぞれの試行後、砖石の色が変更されて、"
                                    + "あなたの答えと単語の近くを示すようになります。");
                }
                label1.setTextAlignment(TextAlignment.CENTER);
                label1.setWrapText(true);
                label1.setMaxWidth(200);
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(label1);
                Scene sceneForInstructionPage = new Scene(stackPane, 250, 250);
                Stage buttonOfInstructionsStage = new Stage();
                buttonOfInstructionsStage.setScene(sceneForInstructionPage);
                buttonOfInstructionsStage.initModality(Modality.APPLICATION_MODAL);
                buttonOfInstructionsStage.initOwner(primaryStage);
                buttonOfInstructionsStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        buttonOfInstructionsStage.hide();
                    }
                });
                buttonOfInstructionsStage.show();
            }
        });

        // buttonOfInstructions.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // Label text = new Label("Guess the WORDLE in six tries. Each guess must be a
        // valid five-letter word. Hit the enter button to submit. After each guess, the
        // color of the tiles will change to show how close your guess was to the
        // word.");
        // text.setFont(new Font("Verdana", 12));
        // text.setWrapText(true);
        // text.setTextAlignment(TextAlignment.CENTER);
        // text.setMaxWidth(200);
        // // text.setTranslateX(25);
        // // text.setTranslateY(25);
        // StackPane stackPane = new StackPane();
        // stackPane.getChildren().add(text);
        // // stackPane.setAlignment(VPos.TOP);
        // Scene scene = new Scene(stackPane, 250, 250);
        // Stage stage = new Stage();
        // stage.setScene(scene);
        // stage.show();
        // }
        // });

        Button buttonOfRestart = new Button("Restart");
        buttonOfRestart.setFont(new Font("Verdana", 10));
        buttonOfRestart.setText("Restart");
        buttonOfRestart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                counter = 0;
                instructionsCounter = 0;
                timerStartAndStop.start();
                labelForCurrentStatisticsValue.setText("0.000 seconds");
                Random rand = new Random();
                radInt = rand.nextInt(Words.list.size());
                if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                        || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                        || languagePane.getValue().equals("英語")) {
                    promptMessage.setText("Try guessing a word! ");
                } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                        || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                        || languagePane.getValue().equals("スペイン語")) {
                    promptMessage.setText("¡Intenta adivinar una palabra!");
                } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                        || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                        || languagePane.getValue().equals("中国語")) {
                    promptMessage.setText("试着猜一个单词！");
                } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                        || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                        || languagePane.getValue().equals("ロシア語")) {
                    promptMessage.setText("Попробуйте предположить слово!");
                } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                        || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                        || languagePane.getValue().equals("日本語")) {
                    promptMessage.setText("あなたは単語を猜つべきです！");
                }

                text11.setText("");
                text12.setText("");
                text13.setText("");
                text14.setText("");
                text15.setText("");
                text21.setText("");
                text22.setText("");
                text23.setText("");
                text24.setText("");
                text25.setText("");
                text31.setText("");
                text32.setText("");
                text33.setText("");
                text34.setText("");
                text35.setText("");
                text41.setText("");
                text42.setText("");
                text43.setText("");
                text44.setText("");
                text45.setText("");
                text51.setText("");
                text52.setText("");
                text53.setText("");
                text54.setText("");
                text55.setText("");
                text61.setText("");
                text62.setText("");
                text63.setText("");
                text64.setText("");
                text65.setText("");
                rec11.setFill(Color.WHITE);
                rec12.setFill(Color.WHITE);
                rec13.setFill(Color.WHITE);
                rec14.setFill(Color.WHITE);
                rec15.setFill(Color.WHITE);
                rec21.setFill(Color.WHITE);
                rec22.setFill(Color.WHITE);
                rec23.setFill(Color.WHITE);
                rec24.setFill(Color.WHITE);
                rec25.setFill(Color.WHITE);
                rec31.setFill(Color.WHITE);
                rec32.setFill(Color.WHITE);
                rec33.setFill(Color.WHITE);
                rec34.setFill(Color.WHITE);
                rec35.setFill(Color.WHITE);
                rec41.setFill(Color.WHITE);
                rec42.setFill(Color.WHITE);
                rec43.setFill(Color.WHITE);
                rec44.setFill(Color.WHITE);
                rec45.setFill(Color.WHITE);
                rec51.setFill(Color.WHITE);
                rec52.setFill(Color.WHITE);
                rec53.setFill(Color.WHITE);
                rec54.setFill(Color.WHITE);
                rec55.setFill(Color.WHITE);
                rec61.setFill(Color.WHITE);
                rec62.setFill(Color.WHITE);
                rec63.setFill(Color.WHITE);
                rec64.setFill(Color.WHITE);
                rec65.setFill(Color.WHITE);
            }
        });

        HBox hBoxForButton = new HBox(10);
        hBoxForButton.setAlignment(Pos.CENTER_RIGHT);
        hBoxForButton.getChildren().addAll(promptMessage, buttonOfRestart, buttonOfInstructions);

        Image icon = new Image("gt.png");

        // ComboBox<Object> languagePane = new ComboBox<Object>();
        String[] languagePaneResult = new String[] {"Jordle", "若德尔", "Jordle", "Джордл" };
        languagePane
                .setItems(FXCollections.observableArrayList("English", "Chinese", "Spanish", "Russian", "Japanese"));
        languagePane.setValue("English");
        languagePane.setTooltip(new Tooltip("Select the language"));
        languagePane.setStyle("-fx-font: 12px \"Arial\";");
        languagePane.setOnAction(e -> {
            if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                    || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                    || languagePane.getValue().equals("英語")) {
                title.setText("Jordle");
                languagePane.setValue("English");
                promptMessage.setText("Try guessing a word! ");
                buttonOfInstructions.setText("Instructions");
                buttonOfRestart.setText("Restart");
                languagePane.setTooltip(new Tooltip("Select the language"));
                languagePane
                        .setItems(FXCollections.observableArrayList("English", "Chinese", "Spanish", "Russian",
                                "Japanese"));
            } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                    || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                    || languagePane.getValue().equals("中国語")) {
                title.setText("若德尔");
                languagePane.setValue("中文");
                promptMessage.setText("试着猜一个单词！");
                buttonOfInstructions.setText("指令");
                buttonOfRestart.setText("重新开始");
                languagePane.setTooltip(new Tooltip("選擇語言"));
                languagePane.setItems(FXCollections.observableArrayList("英语", "中文", "西班牙语", "俄语", "日语"));
                // title.setText("若德尔");
                // languagePane.setValue("Language");
            } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                    || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                    || languagePane.getValue().equals("スペイン語")) {
                title.setText("Jordle");
                languagePane.setValue("Español");
                promptMessage.setText("¡Intenta adivinar una palabra!");
                buttonOfInstructions.setText("Instrucciones");
                buttonOfRestart.setText("Reiniciar");
                languagePane.setTooltip(new Tooltip("Seleccione el idioma"));
                languagePane
                        .setItems(FXCollections.observableArrayList("Inglés", "Chino", "Español", "Ruso", "Japonés"));
                // title.setText("Jordle");
                // languagePane.setValue("Language");
            } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                    || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                    || languagePane.getValue().equals("ロシア語")) {
                title.setText("Джордл");
                languagePane.setValue("Русский");
                promptMessage.setText("Попробуйте отгадать слово!");
                buttonOfInstructions.setText("Инструкции");
                buttonOfRestart.setText("Перезапуск");
                languagePane.setTooltip(new Tooltip("Выберите язык"));
                languagePane
                        .setItems(FXCollections.observableArrayList("Английский", "Китайский", "Испанский", "Русский",
                                "Японский"));
                // title.setText("Джордл");
                // languagePane.setValue("Language");
            } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                    || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                    || languagePane.getValue().equals("日本語")) {
                title.setText("ジョードル");
                languagePane.setValue("日本語");
                promptMessage.setText("あなたの単語を猜ろう！");
                buttonOfInstructions.setText("使い方");
                buttonOfRestart.setText("リスタート");
                languagePane.setTooltip(new Tooltip("言語を選択してください"));
                languagePane.setItems(FXCollections.observableArrayList("英語", "中国語", "スペイン語", "ロシア語", "日本語"));
            }
        });

        BorderPane gridForTop = new BorderPane();
        gridForTop.setCenter(title);
        gridForTop.setRight(vBoxForFunction);

        BorderPane pane = new BorderPane();
        pane.setCenter(gridPane);
        pane.setTop(gridForTop);
        pane.setRight(stateView);
        pane.setBottom(hBoxForButton);

        Scene scene = new Scene(pane, 650, 650);
        tbForLight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tbForLight.isSelected()) {
                    pane.setBackground(
                            new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    labelForLightAndDark.setTextFill(Color.BLACK);
                    labelForStatistics.setTextFill(Color.BLACK);
                    labelForTimer.setTextFill(Color.BLACK);
                    promptMessage.setFill(Color.BLACK);
                }
            }
        });
        tbForDark.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tbForDark.isSelected()) {
                    pane.setBackground(
                            new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
                    labelForLightAndDark.setTextFill(Color.WHITE);
                    labelForStatistics.setTextFill(Color.WHITE);
                    labelForTimer.setTextFill(Color.WHITE);
                    promptMessage.setFill(Color.WHITE);
                }
            }
        });

        scene.setOnKeyPressed((KeyEvent e1) -> {
            if (counter >= 0 && counter < 5 && e1.getCode() != KeyCode.BACK_SPACE
                    && e1.getCode() != KeyCode.ENTER
                    && e1.getCode() != KeyCode.ESCAPE
                    && e1.getCode() != KeyCode.SHIFT
                    && e1.getText().toUpperCase().charAt(0) >= 'A'
                    && e1.getText().toUpperCase().charAt(0) <= 'Z') {
                if (counter == 0) {
                    text11.setFont(new Font("Verdana", 20));
                    text11.setText(e1.getText().toUpperCase());
                } else if (counter == 1) {
                    text12.setFont(new Font("Verdana", 20));
                    text12.setText(e1.getText().toUpperCase());
                } else if (counter == 2) {
                    text13.setFont(new Font("Verdana", 20));
                    text13.setText(e1.getText().toUpperCase());
                } else if (counter == 3) {
                    text14.setFont(new Font("Verdana", 20));
                    text14.setText(e1.getText().toUpperCase());
                } else if (counter == 4) {
                    text15.setFont(new Font("Verdana", 20));
                    text15.setText(e1.getText().toUpperCase());
                }
                counter++;
            } else if (counter >= 6 && counter < 11 && e1.getCode() != KeyCode.BACK_SPACE
                    && e1.getCode() != KeyCode.ENTER
                    && e1.getCode() != KeyCode.ESCAPE
                    && e1.getCode() != KeyCode.SHIFT
                    && e1.getText().toUpperCase().charAt(0) >= 'A'
                    && e1.getText().toUpperCase().charAt(0) <= 'Z') {
                if (counter == 6) {
                    text21.setFont(new Font("Verdana", 20));
                    text21.setText(e1.getText().toUpperCase());
                } else if (counter == 7) {
                    text22.setFont(new Font("Verdana", 20));
                    text22.setText(e1.getText().toUpperCase());
                } else if (counter == 8) {
                    text23.setFont(new Font("Verdana", 20));
                    text23.setText(e1.getText().toUpperCase());
                } else if (counter == 9) {
                    text24.setFont(new Font("Verdana", 20));
                    text24.setText(e1.getText().toUpperCase());
                } else if (counter == 10) {
                    text25.setFont(new Font("Verdana", 20));
                    text25.setText(e1.getText().toUpperCase());
                }
                counter++;
            } else if (counter >= 12 && counter < 17 && e1.getCode() != KeyCode.BACK_SPACE
                    && e1.getCode() != KeyCode.ENTER
                    && e1.getCode() != KeyCode.ESCAPE
                    && e1.getCode() != KeyCode.SHIFT
                    && e1.getText().toUpperCase().charAt(0) >= 'A'
                    && e1.getText().toUpperCase().charAt(0) <= 'Z') {
                if (counter == 12) {
                    text31.setFont(new Font("Verdana", 20));
                    text31.setText(e1.getText().toUpperCase());
                } else if (counter == 13) {
                    text32.setFont(new Font("Verdana", 20));
                    text32.setText(e1.getText().toUpperCase());
                } else if (counter == 14) {
                    text33.setFont(new Font("Verdana", 20));
                    text33.setText(e1.getText().toUpperCase());
                } else if (counter == 15) {
                    text34.setFont(new Font("Verdana", 20));
                    text34.setText(e1.getText().toUpperCase());
                } else if (counter == 16) {
                    text35.setFont(new Font("Verdana", 20));
                    text35.setText(e1.getText().toUpperCase());
                }
                counter++;
            } else if (counter >= 18 && counter < 23 && e1.getCode() != KeyCode.BACK_SPACE
                    && e1.getCode() != KeyCode.ENTER
                    && e1.getCode() != KeyCode.ESCAPE
                    && e1.getCode() != KeyCode.SHIFT
                    && e1.getText().toUpperCase().charAt(0) >= 'A'
                    && e1.getText().toUpperCase().charAt(0) <= 'Z') {
                if (counter == 18) {
                    text41.setFont(new Font("Verdana", 20));
                    text41.setText(e1.getText().toUpperCase());
                } else if (counter == 19) {
                    text42.setFont(new Font("Verdana", 20));
                    text42.setText(e1.getText().toUpperCase());
                } else if (counter == 20) {
                    text43.setFont(new Font("Verdana", 20));
                    text43.setText(e1.getText().toUpperCase());
                } else if (counter == 21) {
                    text44.setFont(new Font("Verdana", 20));
                    text44.setText(e1.getText().toUpperCase());
                } else if (counter == 22) {
                    text45.setFont(new Font("Verdana", 20));
                    text45.setText(e1.getText().toUpperCase());
                }
                counter++;
            } else if (counter >= 24 && counter < 29 && e1.getCode() != KeyCode.BACK_SPACE
                    && e1.getCode() != KeyCode.ENTER
                    && e1.getCode() != KeyCode.ESCAPE
                    && e1.getCode() != KeyCode.SHIFT
                    && e1.getText().toUpperCase().charAt(0) >= 'A'
                    && e1.getText().toUpperCase().charAt(0) <= 'Z') {
                if (counter == 24) {
                    text51.setFont(new Font("Verdana", 20));
                    text51.setText(e1.getText().toUpperCase());
                } else if (counter == 25) {
                    text52.setFont(new Font("Verdana", 20));
                    text52.setText(e1.getText().toUpperCase());
                } else if (counter == 26) {
                    text53.setFont(new Font("Verdana", 20));
                    text53.setText(e1.getText().toUpperCase());
                } else if (counter == 27) {
                    text54.setFont(new Font("Verdana", 20));
                    text54.setText(e1.getText().toUpperCase());
                } else if (counter == 28) {
                    text55.setFont(new Font("Verdana", 20));
                    text55.setText(e1.getText().toUpperCase());
                }
                counter++;
            } else if (counter >= 30 && counter < 35 && e1.getCode() != KeyCode.BACK_SPACE
                    && e1.getCode() != KeyCode.ENTER
                    && e1.getCode() != KeyCode.ESCAPE
                    && e1.getCode() != KeyCode.SHIFT
                    && e1.getText().toUpperCase().charAt(0) >= 'A'
                    && e1.getText().toUpperCase().charAt(0) <= 'Z') {
                if (counter == 30) {
                    text61.setFont(new Font("Verdana", 20));
                    text61.setText(e1.getText().toUpperCase());
                } else if (counter == 31) {
                    text62.setFont(new Font("Verdana", 20));
                    text62.setText(e1.getText().toUpperCase());
                } else if (counter == 32) {
                    text63.setFont(new Font("Verdana", 20));
                    text63.setText(e1.getText().toUpperCase());
                } else if (counter == 33) {
                    text64.setFont(new Font("Verdana", 20));
                    text64.setText(e1.getText().toUpperCase());
                } else if (counter == 34) {
                    text65.setFont(new Font("Verdana", 20));
                    text65.setText(e1.getText().toUpperCase());
                }
                counter++;
            } else if (e1.getCode() == KeyCode.ENTER && counter == 5) {
                counter++;
                String[] input = {text11.getText(), text12.getText(), text13.getText(), text14.getText(),
                        text15.getText() };
                int[] result = evaluator(input, Words.list.get(radInt));
                for (int i = 0; i < 5; i++) {
                    if (result[i] == 1) {
                        if (i == 0) {
                            rec11.setFill(Color.GREEN);
                        } else if (i == 1) {
                            rec12.setFill(Color.GREEN);
                        } else if (i == 2) {
                            rec13.setFill(Color.GREEN);
                        } else if (i == 3) {
                            rec14.setFill(Color.GREEN);
                        } else if (i == 4) {
                            rec15.setFill(Color.GREEN);
                        }
                    } else if (result[i] == 2) {
                        if (i == 0) {
                            rec11.setFill(Color.YELLOW);
                        } else if (i == 1) {
                            rec12.setFill(Color.YELLOW);
                        } else if (i == 2) {
                            rec13.setFill(Color.YELLOW);
                        } else if (i == 3) {
                            rec14.setFill(Color.YELLOW);
                        } else if (i == 4) {
                            rec15.setFill(Color.YELLOW);
                        }
                    } else {
                        if (i == 0) {
                            rec11.setFill(Color.GRAY);
                        } else if (i == 1) {
                            rec12.setFill(Color.GRAY);
                        } else if (i == 2) {
                            rec13.setFill(Color.GRAY);
                        } else if (i == 3) {
                            rec14.setFill(Color.GRAY);
                        } else if (i == 4) {
                            rec15.setFill(Color.GRAY);
                        }
                    }
                }
                if (rec11.getFill() == Color.GREEN && rec12.getFill() == Color.GREEN
                        && rec13.getFill() == Color.GREEN && rec14.getFill() == Color.GREEN
                        && rec15.getFill() == Color.GREEN) {
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Congratulations! You’ve guessed the word!");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("恭喜！你猜对了这个词！");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("¡Felicidades! ¡Acertaste la palabra!");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Поздравляем! Вы угадали слово!");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("おめでとうございます！あなたはこの言葉を当てました！");
                    }
                    stateView.setImage(successImage);
                    timerStartAndStop.stop();
                    labelForCurrentStatisticsValue.setText(timeForStatistics.get() + " seconds");
                    double timerOutput = timeForStatistics.get();
                    File fw = new File("statisticsFile.txt");
                    try {
                        Scanner sc = new Scanner(fw);
                        String lineFromTimerFile = "";
                        while (sc.hasNextLine()) {
                            lineFromTimerFile = lineFromTimerFile + sc.nextLine() + "\n";
                        }
                        PrintWriter pw = new PrintWriter(fw);
                        pw.append(lineFromTimerFile + timerOutput + "\n");
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    counter = 36;
                }
            } else if (e1.getCode() == KeyCode.ENTER && counter == 11) {
                counter++;
                String[] input = {text21.getText(), text22.getText(), text23.getText(), text24.getText(),
                        text25.getText() };
                int[] result = evaluator(input, Words.list.get(radInt));
                for (int i = 0; i < 5; i++) {
                    if (result[i] == 1) {
                        if (i == 0) {
                            rec21.setFill(Color.GREEN);
                        } else if (i == 1) {
                            rec22.setFill(Color.GREEN);
                        } else if (i == 2) {
                            rec23.setFill(Color.GREEN);
                        } else if (i == 3) {
                            rec24.setFill(Color.GREEN);
                        } else if (i == 4) {
                            rec25.setFill(Color.GREEN);
                        }
                    } else if (result[i] == 2) {
                        if (i == 0) {
                            rec21.setFill(Color.YELLOW);
                        } else if (i == 1) {
                            rec22.setFill(Color.YELLOW);
                        } else if (i == 2) {
                            rec23.setFill(Color.YELLOW);
                        } else if (i == 3) {
                            rec24.setFill(Color.YELLOW);
                        } else if (i == 4) {
                            rec25.setFill(Color.YELLOW);
                        }
                    } else {
                        if (i == 0) {
                            rec21.setFill(Color.GRAY);
                        } else if (i == 1) {
                            rec22.setFill(Color.GRAY);
                        } else if (i == 2) {
                            rec23.setFill(Color.GRAY);
                        } else if (i == 3) {
                            rec24.setFill(Color.GRAY);
                        } else if (i == 4) {
                            rec25.setFill(Color.GRAY);
                        }
                    }
                }
                if (rec21.getFill() == Color.GREEN && rec22.getFill() == Color.GREEN
                        && rec23.getFill() == Color.GREEN && rec24.getFill() == Color.GREEN
                        && rec25.getFill() == Color.GREEN) {
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Congratulations! You’ve guessed the word!");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("恭喜！你猜对了这个词！");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("¡Felicidades! ¡Acertaste la palabra!");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Поздравляем! Вы угадали слово!");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("おめでとうございます！あなたはこの言葉を当てました！");
                    }
                    stateView.setImage(successImage);
                    timerStartAndStop.stop();
                    labelForCurrentStatisticsValue.setText(timeForStatistics.get() + " seconds");
                    double timerOutput = timeForStatistics.get();
                    File fw = new File("statisticsFile.txt");
                    try {
                        Scanner sc = new Scanner(fw);
                        String lineFromTimerFile = "";
                        while (sc.hasNextLine()) {
                            lineFromTimerFile = lineFromTimerFile + sc.nextLine() + "\n";
                        }
                        PrintWriter pw = new PrintWriter(fw);
                        pw.append(lineFromTimerFile + timerOutput + "\n");
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    counter = 36;
                }
            } else if (e1.getCode() == KeyCode.ENTER && counter == 17) {
                counter++;
                String[] input = {text31.getText(), text32.getText(), text33.getText(), text34.getText(),
                        text35.getText() };
                int[] result = evaluator(input, Words.list.get(radInt));
                for (int i = 0; i < 5; i++) {
                    if (result[i] == 1) {
                        if (i == 0) {
                            rec31.setFill(Color.GREEN);
                        } else if (i == 1) {
                            rec32.setFill(Color.GREEN);
                        } else if (i == 2) {
                            rec33.setFill(Color.GREEN);
                        } else if (i == 3) {
                            rec34.setFill(Color.GREEN);
                        } else if (i == 4) {
                            rec35.setFill(Color.GREEN);
                        }
                    } else if (result[i] == 2) {
                        if (i == 0) {
                            rec31.setFill(Color.YELLOW);
                        } else if (i == 1) {
                            rec32.setFill(Color.YELLOW);
                        } else if (i == 2) {
                            rec33.setFill(Color.YELLOW);
                        } else if (i == 3) {
                            rec34.setFill(Color.YELLOW);
                        } else if (i == 4) {
                            rec35.setFill(Color.YELLOW);
                        }
                    } else {
                        if (i == 0) {
                            rec31.setFill(Color.GRAY);
                        } else if (i == 1) {
                            rec32.setFill(Color.GRAY);
                        } else if (i == 2) {
                            rec33.setFill(Color.GRAY);
                        } else if (i == 3) {
                            rec34.setFill(Color.GRAY);
                        } else if (i == 4) {
                            rec35.setFill(Color.GRAY);
                        }
                    }
                }
                if (rec31.getFill() == Color.GREEN && rec32.getFill() == Color.GREEN
                        && rec33.getFill() == Color.GREEN && rec34.getFill() == Color.GREEN
                        && rec35.getFill() == Color.GREEN) {
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Congratulations! You’ve guessed the word!");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("恭喜！你猜对了这个词！");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("¡Felicidades! ¡Acertaste la palabra!");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Поздравляем! Вы угадали слово!");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("おめでとうございます！あなたはこの言葉を当てました！");
                    }
                    stateView.setImage(successImage);
                    timerStartAndStop.stop();
                    labelForCurrentStatisticsValue.setText(timeForStatistics.get() + " seconds");
                    double timerOutput = timeForStatistics.get();
                    File fw = new File("statisticsFile.txt");
                    try {
                        Scanner sc = new Scanner(fw);
                        String lineFromTimerFile = "";
                        while (sc.hasNextLine()) {
                            lineFromTimerFile = lineFromTimerFile + sc.nextLine() + "\n";
                        }
                        PrintWriter pw = new PrintWriter(fw);
                        pw.append(lineFromTimerFile + timerOutput + "\n");
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    counter = 36;
                }
            } else if (e1.getCode() == KeyCode.ENTER && counter == 23) {
                counter++;
                String[] input = {text41.getText(), text42.getText(), text43.getText(), text44.getText(),
                        text45.getText() };
                int[] result = evaluator(input, Words.list.get(radInt));
                for (int i = 0; i < 5; i++) {
                    if (result[i] == 1) {
                        if (i == 0) {
                            rec41.setFill(Color.GREEN);
                        } else if (i == 1) {
                            rec42.setFill(Color.GREEN);
                        } else if (i == 2) {
                            rec43.setFill(Color.GREEN);
                        } else if (i == 3) {
                            rec44.setFill(Color.GREEN);
                        } else if (i == 4) {
                            rec45.setFill(Color.GREEN);
                        }
                    } else if (result[i] == 2) {
                        if (i == 0) {
                            rec41.setFill(Color.YELLOW);
                        } else if (i == 1) {
                            rec42.setFill(Color.YELLOW);
                        } else if (i == 2) {
                            rec43.setFill(Color.YELLOW);
                        } else if (i == 3) {
                            rec44.setFill(Color.YELLOW);
                        } else if (i == 4) {
                            rec45.setFill(Color.YELLOW);
                        }
                    } else {
                        if (i == 0) {
                            rec41.setFill(Color.GRAY);
                        } else if (i == 1) {
                            rec42.setFill(Color.GRAY);
                        } else if (i == 2) {
                            rec43.setFill(Color.GRAY);
                        } else if (i == 3) {
                            rec44.setFill(Color.GRAY);
                        } else if (i == 4) {
                            rec45.setFill(Color.GRAY);
                        }
                    }
                }
                if (rec41.getFill() == Color.GREEN && rec42.getFill() == Color.GREEN
                        && rec43.getFill() == Color.GREEN && rec44.getFill() == Color.GREEN
                        && rec45.getFill() == Color.GREEN) {
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Congratulations! You’ve guessed the word!");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("恭喜！你猜对了这个词！");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("¡Felicidades! ¡Acertaste la palabra!");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Поздравляем! Вы угадали слово!");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("おめでとうございます！あなたはこの言葉を当てました！");
                    }
                    stateView.setImage(successImage);
                    timerStartAndStop.stop();
                    labelForCurrentStatisticsValue.setText(timeForStatistics.get() + " seconds");
                    double timerOutput = timeForStatistics.get();
                    File fw = new File("statisticsFile.txt");
                    try {
                        Scanner sc = new Scanner(fw);
                        String lineFromTimerFile = "";
                        while (sc.hasNextLine()) {
                            lineFromTimerFile = lineFromTimerFile + sc.nextLine() + "\n";
                        }
                        PrintWriter pw = new PrintWriter(fw);
                        pw.append(lineFromTimerFile + timerOutput + "\n");
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    counter = 36;
                }
            } else if (e1.getCode() == KeyCode.ENTER && counter == 29) {
                counter++;
                String[] input = {text51.getText(), text52.getText(), text53.getText(), text54.getText(),
                        text55.getText() };
                int[] result = evaluator(input, Words.list.get(radInt));
                for (int i = 0; i < 5; i++) {
                    if (result[i] == 1) {
                        if (i == 0) {
                            rec51.setFill(Color.GREEN);
                        } else if (i == 1) {
                            rec52.setFill(Color.GREEN);
                        } else if (i == 2) {
                            rec53.setFill(Color.GREEN);
                        } else if (i == 3) {
                            rec54.setFill(Color.GREEN);
                        } else if (i == 4) {
                            rec55.setFill(Color.GREEN);
                        }
                    } else if (result[i] == 2) {
                        if (i == 0) {
                            rec51.setFill(Color.YELLOW);
                        } else if (i == 1) {
                            rec52.setFill(Color.YELLOW);
                        } else if (i == 2) {
                            rec53.setFill(Color.YELLOW);
                        } else if (i == 3) {
                            rec54.setFill(Color.YELLOW);
                        } else if (i == 4) {
                            rec55.setFill(Color.YELLOW);
                        }
                    } else {
                        if (i == 0) {
                            rec51.setFill(Color.GRAY);
                        } else if (i == 1) {
                            rec52.setFill(Color.GRAY);
                        } else if (i == 2) {
                            rec53.setFill(Color.GRAY);
                        } else if (i == 3) {
                            rec54.setFill(Color.GRAY);
                        } else if (i == 4) {
                            rec55.setFill(Color.GRAY);
                        }
                    }
                }
                if (rec51.getFill() == Color.GREEN && rec52.getFill() == Color.GREEN
                        && rec53.getFill() == Color.GREEN && rec54.getFill() == Color.GREEN
                        && rec55.getFill() == Color.GREEN) {
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Congratulations! You’ve guessed the word!");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("恭喜！你猜对了这个词！");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("¡Felicidades! ¡Acertaste la palabra!");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Поздравляем! Вы угадали слово!");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("おめでとうございます！あなたはこの言葉を当てました！");
                    }
                    stateView.setImage(successImage);
                    timerStartAndStop.stop();
                    labelForCurrentStatisticsValue.setText(timeForStatistics.get() + " seconds");
                    double timerOutput = timeForStatistics.get();
                    File fw = new File("statisticsFile.txt");
                    try {
                        Scanner sc = new Scanner(fw);
                        String lineFromTimerFile = "";
                        while (sc.hasNextLine()) {
                            lineFromTimerFile = lineFromTimerFile + sc.nextLine() + "\n";
                        }
                        PrintWriter pw = new PrintWriter(fw);
                        pw.append(lineFromTimerFile + timerOutput + "\n");
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    counter = 36;
                }
            } else if (e1.getCode() == KeyCode.ENTER && counter == 35) {
                counter++;
                String[] input = {text61.getText(), text62.getText(), text63.getText(), text64.getText(),
                        text65.getText() };
                int[] result = evaluator(input, Words.list.get(radInt));
                for (int i = 0; i < 5; i++) {
                    if (result[i] == 1) {
                        if (i == 0) {
                            rec61.setFill(Color.GREEN);
                        } else if (i == 1) {
                            rec62.setFill(Color.GREEN);
                        } else if (i == 2) {
                            rec63.setFill(Color.GREEN);
                        } else if (i == 3) {
                            rec64.setFill(Color.GREEN);
                        } else if (i == 4) {
                            rec65.setFill(Color.GREEN);
                        }
                    } else if (result[i] == 2) {
                        if (i == 0) {
                            rec61.setFill(Color.YELLOW);
                        } else if (i == 1) {
                            rec62.setFill(Color.YELLOW);
                        } else if (i == 2) {
                            rec63.setFill(Color.YELLOW);
                        } else if (i == 3) {
                            rec64.setFill(Color.YELLOW);
                        } else if (i == 4) {
                            rec65.setFill(Color.YELLOW);
                        }
                    } else {
                        if (i == 0) {
                            rec61.setFill(Color.GRAY);
                        } else if (i == 1) {
                            rec62.setFill(Color.GRAY);
                        } else if (i == 2) {
                            rec63.setFill(Color.GRAY);
                        } else if (i == 3) {
                            rec64.setFill(Color.GRAY);
                        } else if (i == 4) {
                            rec65.setFill(Color.GRAY);
                        }
                    }
                }
                if (rec61.getFill() == Color.GREEN && rec62.getFill() == Color.GREEN
                        && rec63.getFill() == Color.GREEN && rec64.getFill() == Color.GREEN
                        && rec65.getFill() == Color.GREEN) {
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Congratulations! You’ve guessed the word!");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("恭喜！你猜对了这个词！");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("¡Felicidades! ¡Acertaste la palabra!");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Поздравляем! Вы угадали слово!");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("おめでとうございます！あなたはこの言葉を当てました！");
                    }
                    stateView.setImage(successImage);
                    timerStartAndStop.stop();
                    labelForCurrentStatisticsValue.setText(timeForStatistics.get() + " seconds");
                    double timerOutput = timeForStatistics.get();
                    File fw = new File("statisticsFile.txt");
                    try {
                        Scanner sc = new Scanner(fw);
                        String lineFromTimerFile = "";
                        while (sc.hasNextLine()) {
                            lineFromTimerFile = lineFromTimerFile + sc.nextLine() + "\n";
                        }
                        PrintWriter pw = new PrintWriter(fw);
                        pw.append(lineFromTimerFile + timerOutput + "\n");
                        pw.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found");
                    }
                    counter = 36;
                } else {
                    String answer = Words.list.get(radInt);
                    stateView.setImage(failImage);
                    timerStartAndStop.stop();
                    if (languagePane.getValue().equals("English") || languagePane.getValue().equals("英语")
                            || languagePane.getValue().equals("English") || languagePane.getValue().equals("Английский")
                            || languagePane.getValue().equals("英語")) {
                        promptMessage.setText("Game over. The word was " + answer + ".");
                    } else if (languagePane.getValue().equals("Chinese") || languagePane.getValue().equals("中文")
                            || languagePane.getValue().equals("Chino") || languagePane.getValue().equals("Китайский")
                            || languagePane.getValue().equals("中国語")) {
                        promptMessage.setText("游戏结束。这个词是" + answer + "。");
                    } else if (languagePane.getValue().equals("Spanish") || languagePane.getValue().equals("西班牙语")
                            || languagePane.getValue().equals("Español") || languagePane.getValue().equals("Испанский")
                            || languagePane.getValue().equals("スペイン語")) {
                        promptMessage.setText("Fin del juego. La palabra era " + answer + ".");
                    } else if (languagePane.getValue().equals("Russian") || languagePane.getValue().equals("俄语")
                            || languagePane.getValue().equals("Ruso") || languagePane.getValue().equals("Русский")
                            || languagePane.getValue().equals("ロシア語")) {
                        promptMessage.setText("Конец игры. Это слово " + answer + ".");
                    } else if (languagePane.getValue().equals("Japanese") || languagePane.getValue().equals("日语")
                            || languagePane.getValue().equals("Japonés") || languagePane.getValue().equals("Японский")
                            || languagePane.getValue().equals("日本語")) {
                        promptMessage.setText("ゲームオーバー。この言葉は" + answer + "です。");
                    }
                }
            } else if (e1.getCode() == KeyCode.ENTER && counter >= 0 && counter < 5) {
                invalidInput();
            } else if (e1.getCode() == KeyCode.ENTER && counter >= 7 && counter < 11) {
                invalidInput();
            } else if (e1.getCode() == KeyCode.ENTER && counter >= 13 && counter < 17) {
                invalidInput();
            } else if (e1.getCode() == KeyCode.ENTER && counter >= 19 && counter < 23) {
                invalidInput();
            } else if (e1.getCode() == KeyCode.ENTER && counter >= 25 && counter < 29) {
                invalidInput();
            } else if (e1.getCode() == KeyCode.ENTER && counter >= 31 && counter < 35) {
                invalidInput();
            } else if (e1.getCode() == KeyCode.BACK_SPACE) {
                if (counter == 1) {
                    text11.setText("");
                    counter--;
                } else if (counter == 2) {
                    text12.setText("");
                    counter--;
                } else if (counter == 3) {
                    text13.setText("");
                    counter--;
                } else if (counter == 4) {
                    text14.setText("");
                    counter--;
                } else if (counter == 5) {
                    text15.setText("");
                    counter--;
                } else if (counter == 7) {
                    text21.setText("");
                    counter--;
                } else if (counter == 8) {
                    text22.setText("");
                    counter--;
                } else if (counter == 9) {
                    text23.setText("");
                    counter--;
                } else if (counter == 10) {
                    text24.setText("");
                    counter--;
                } else if (counter == 11) {
                    text25.setText("");
                    counter--;
                } else if (counter == 13) {
                    text31.setText("");
                    counter--;
                } else if (counter == 14) {
                    text32.setText("");
                    counter--;
                } else if (counter == 15) {
                    text33.setText("");
                    counter--;
                } else if (counter == 16) {
                    text34.setText("");
                    counter--;
                } else if (counter == 17) {
                    text35.setText("");
                    counter--;
                } else if (counter == 19) {
                    text41.setText("");
                    counter--;
                } else if (counter == 20) {
                    text42.setText("");
                    counter--;
                } else if (counter == 21) {
                    text43.setText("");
                    counter--;
                } else if (counter == 22) {
                    text44.setText("");
                    counter--;
                } else if (counter == 23) {
                    text45.setText("");
                    counter--;
                } else if (counter == 25) {
                    text51.setText("");
                    counter--;
                } else if (counter == 26) {
                    text52.setText("");
                    counter--;
                } else if (counter == 27) {
                    text53.setText("");
                    counter--;
                } else if (counter == 28) {
                    text54.setText("");
                    counter--;
                } else if (counter == 29) {
                    text55.setText("");
                    counter--;
                } else if (counter == 31) {
                    text61.setText("");
                    counter--;
                } else if (counter == 32) {
                    text62.setText("");
                    counter--;
                } else if (counter == 33) {
                    text63.setText("");
                    counter--;
                } else if (counter == 34) {
                    text64.setText("");
                    counter--;
                } else if (counter == 35) {
                    text65.setText("");
                    counter--;
                }
            }
//            System.out.println(counter);
        });

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Jordle");
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(700);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("YOU HAVE TO EXIT BY HINT ESC");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("esc"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}