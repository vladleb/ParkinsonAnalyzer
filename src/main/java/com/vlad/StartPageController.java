package com.vlad;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class StartPageController {

    private InputJSON inp;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnFileSelect;

    @FXML
    private Button btnGraphA_t;

    @FXML
    private Button btnGraphPosition;

    @FXML
    private Button btnGraphCoefficientError;

    @FXML
    private Button btnGraphV_t;

    @FXML
    private Button btnExit;

    @FXML
    private Label labelInfo;

    @FXML
    private TextField tfFilePath;

    @FXML
    void initialize() {

        btnGraphA_t.setDisable(true);
        btnGraphPosition.setDisable(true);
        btnGraphCoefficientError.setDisable(true);
        btnGraphV_t.setDisable(true);

        btnExit.setOnAction(event -> {
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        });

        btnFileSelect.setOnAction(event -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialDirectory(new File("C:\\Users"));
                    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
                    List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
                    if (selectedFiles != null) {
                        for (int i = 0; i < selectedFiles.size(); i++) {
                            tfFilePath.setText(selectedFiles.get(i).getAbsolutePath());
                        }
                    } else {
                        tfFilePath.setText("???????? ???? ????????????");
                    }
                    Gson gson = new Gson();
                    try {
                        System.out.println(String.valueOf(selectedFiles.get(0)));
                        inp = gson.fromJson(new FileReader(String.valueOf(selectedFiles.get(0))), InputJSON.class);
                        labelInfo.setText("???????? ?? ?????????? ?????????????????????? ??????????????????: " + inp.getDateNowSending() + "\n" +
                                "?????????? ?????????? ?????????????????????? ??????????????????: " + inp.getGameTime() + " ????????????" + "\n" +
                                "???????????????????? ??????????????????????????: " + inp.getCoeffRezist() + "\n");
                        btnGraphA_t.setDisable(false);
                        btnGraphPosition.setDisable(false);
                        btnGraphCoefficientError.setDisable(false);
                        btnGraphV_t.setDisable(false);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                });
        btnGraphA_t.setOnAction(event -> {

            Stage stage = new Stage();
            stage.setTitle("A(t)");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("??????????");
            yAxis.setLabel("??????????????????");
            final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("A(t)");

            XYChart.Series accelerationX = new XYChart.Series();
            accelerationX.setName("?????????????????? ???? ?????? X");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                accelerationX.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        inp.getMeasurement().get(i).getaX()));

            XYChart.Series accelerationY = new XYChart.Series();
            accelerationY.setName("?????????????????? ???? ?????? Y");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                accelerationY.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        inp.getMeasurement().get(i).getaY()));

            XYChart.Series accelerationRezult = new XYChart.Series();
            accelerationRezult.setName("???????????????????????????? ??????????????????");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                accelerationRezult.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        Math.hypot(inp.getMeasurement().get(i).getaX(), inp.getMeasurement().get(i).getaY())));

            Scene scene = new Scene(lineChart, 800, 600);

            lineChart.getData().add(accelerationX);
            lineChart.getData().add(accelerationY);
            lineChart.getData().add(accelerationRezult);

            ObservableList<XYChart.Data> dataListAccelerationX = ((XYChart.Series) lineChart.getData().get(0)).getData();
            for (XYChart.Data value : dataListAccelerationX) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("?????????????????? ???? ?????? X" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "?????????????????? aX: " + value.getYValue().toString() + " ??/??^2");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            ObservableList<XYChart.Data> dataListAccelerationY = ((XYChart.Series) lineChart.getData().get(1)).getData();
            for (XYChart.Data value : dataListAccelerationY) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("?????????????????? ???? ?????? Y" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "?????????????????? aY: " + value.getYValue().toString() + " ??/??^2");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            ObservableList<XYChart.Data> dataListAccelerationRezult = ((XYChart.Series) lineChart.getData().get(2)).getData();
            for (XYChart.Data value : dataListAccelerationRezult) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("???????????????????????????? ??????????????????" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "??????????????????: " + value.getYValue().toString() + " ??/??^2");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            stage.setScene(scene);
            stage.show();


        });

        btnGraphPosition.setOnAction(event -> {

            Stage stage = new Stage();
            stage.setTitle("Coordinate(t)");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("??????????");
            yAxis.setLabel("?????????????????? ???????? ???????????????????????? ????????????");
            final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Coordinate(T)");

            XYChart.Series positionX = new XYChart.Series();
            positionX.setName("?????????????????? ???????? ???? ?????? X");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                positionX.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        calcXValue(inp.getMeasurement().get(i).getCoordinateBallX(),
                                inp.getBallLeftDefault(), inp.getCoordinateBallXMax())));

            XYChart.Series positionY = new XYChart.Series();
            positionY.setName("?????????????????? ???????? ???? ?????? Y");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                positionY.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        calcYValue(inp.getMeasurement().get(i).getCoordinateBallY(),
                                inp.getBallTopDefault(), inp.getCoordinateBallYMax())));

            XYChart.Series positionRezult = new XYChart.Series();
            positionRezult.setName("???????????????????????????? ?????????????????? ????????");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                positionRezult.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        (calcXValue(inp.getMeasurement().get(i).getCoordinateBallX(),
                                inp.getBallLeftDefault(), inp.getCoordinateBallXMax()) +
                                calcYValue(inp.getMeasurement().get(i).getCoordinateBallY(),
                                        inp.getBallTopDefault(), inp.getCoordinateBallYMax())) / 2));

            Scene scene = new Scene(lineChart, 800, 600);

            lineChart.getData().add(positionX);
            lineChart.getData().add(positionY);
            lineChart.getData().add(positionRezult);

            ObservableList<XYChart.Data> dataListPositionX = ((XYChart.Series) lineChart.getData().get(0)).getData();
            for (XYChart.Data value : dataListPositionX) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("?????????????????? ???????? ???? ?????? X" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "?????????????????? ???????? ???? X: " + value.getYValue().toString() + " %");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            ObservableList<XYChart.Data> dataListPositionY = ((XYChart.Series) lineChart.getData().get(1)).getData();
            for (XYChart.Data value : dataListPositionY) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("?????????????????? ???????? ???? ?????? Y" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "?????????????????? ???????? ???? Y: " + value.getYValue().toString() + " %");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            ObservableList<XYChart.Data> dataListPositionRezult = ((XYChart.Series) lineChart.getData().get(2)).getData();
            for (XYChart.Data value : dataListPositionRezult) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("???????????????????????????? ?????????????????? ????????" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "?????????????????? ????????: " + value.getYValue().toString() + " %");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            stage.setScene(scene);
            stage.show();

        });

        btnGraphV_t.setOnAction(event ->
        {

            Stage stage = new Stage();
            stage.setTitle("V(t)");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("??????????");
            yAxis.setLabel("V ????????");
            final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("V(t)");

            XYChart.Series vX = new XYChart.Series();
            vX.setName("V ???????? ???? ?????? X");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                vX.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        inp.getMeasurement().get(i).getvX()));

            XYChart.Series vY = new XYChart.Series();
            vY.setName("V ???????? ???? ?????? Y");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                vY.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        inp.getMeasurement().get(i).getvY()));

            XYChart.Series vRezult = new XYChart.Series();
            vRezult.setName("???????????????????????????? V ????????");
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                vRezult.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        Math.hypot(inp.getMeasurement().get(i).getvX(), inp.getMeasurement().get(i).getvY())));

            Scene scene = new Scene(lineChart, 800, 600);

            lineChart.getData().add(vX);
            lineChart.getData().add(vY);
            lineChart.getData().add(vRezult);

            ObservableList<XYChart.Data> dataListPositionX = ((XYChart.Series) lineChart.getData().get(0)).getData();
            for (XYChart.Data value : dataListPositionX) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("V ???????? ???? ?????? X" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "V ???????? ???? X: " + value.getYValue().toString() + " ??/??");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            ObservableList<XYChart.Data> dataListPositionY = ((XYChart.Series) lineChart.getData().get(1)).getData();
            for (XYChart.Data value : dataListPositionY) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("V ???????? ???? ?????? Y" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "V ???????? ???? Y: " + value.getYValue().toString() + " ??/??");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            ObservableList<XYChart.Data> dataListPositionRezult = ((XYChart.Series) lineChart.getData().get(2)).getData();
            for (XYChart.Data value : dataListPositionRezult) {

                Node node = value.getNode();
                Tooltip tooltip = new Tooltip("???????????????????????????? V ????????" + "\n" +
                        "??????????: " + value.getXValue().toString() + " c????.\n" +
                        "V ????????: " + value.getYValue().toString() + " ??/??");
                tooltip.setFont(Font.font("Verdana", 16));
                Tooltip.install(node, tooltip);
            }

            stage.setScene(scene);
            stage.show();

        });

        btnGraphCoefficientError.setOnAction(event -> {

            Stage stage = new Stage();
            stage.setTitle("????????????(t)");
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("??????????");
            yAxis.setLabel("????????????");
            final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
            lineChart.setTitle("????????????(t)");

            List<Integer> resultListX = new ArrayList<>();
            List<Integer> resultListY = new ArrayList<>();

            List<Integer> positionNecessaryX = new ArrayList<>();
            for (int i = 0; i <= inp.getPointSize() - inp.getBallSize(); i++)
                positionNecessaryX.add(inp.getPointLeftDefault() + i);

            List<Integer> positionNecessaryY = new ArrayList<>();
            for (int i = 0; i <= inp.getPointSize() - inp.getBallSize(); i++)
                positionNecessaryY.add(inp.getPointTopDefault() + i);

            XYChart.Series errorX = new XYChart.Series();
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++) {

                int result = calcErrorOnAxis(
                        inp.getMeasurement().get(i).getCoordinateBallX(),
                        inp.getMeasurement().get(i + 1).getCoordinateBallX(),
                        positionNecessaryX);

                errorX.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(), result));

                resultListX.add(result);
            }

            errorX.setName("???????????? ???? ?????? X\n" +
                    Collections.frequency(resultListX, 0) + "/" + resultListX.size() + "\n" +
                    String.format("%.3f", (double)Collections.frequency(resultListX, 0) / resultListX.size()) + "%");

            XYChart.Series errorY = new XYChart.Series();
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++) {

                int result = calcErrorOnAxis(
                        inp.getMeasurement().get(i).getCoordinateBallY(),
                        inp.getMeasurement().get(i + 1).getCoordinateBallY(),
                        positionNecessaryY);

                errorY.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(), result));

                resultListY.add(result);
            }
            errorY.setName("???????????? ???? ?????? Y\n" +
                    Collections.frequency(resultListY, 0) + "/" + resultListY.size() + "\n" +
                    String.format("%.3f", (double)Collections.frequency(resultListY, 0) / resultListY.size()) + "%");

            XYChart.Series errorResult = new XYChart.Series();
            for (int i = 0; inp.getMeasurement().get(i).getTime() < inp.getGameTime(); i++)
                errorResult.getData().add(new XYChart.Data(inp.getMeasurement().get(i).getTime(),
                        (double)(resultListX.get(i) + resultListY.get(i)) / 2
                        ));

            errorResult.setName("???????????????????????????? ???? ???????? ????????\n" +
                    (Collections.frequency(resultListX, 0) + Collections.frequency(resultListY, 0)) + "/" +
                    (resultListX.size() + resultListY.size()) + "\n" +
                    String.format("%.3f", (double)(Collections.frequency(resultListX, 0) +
                            Collections.frequency(resultListY, 0)) / (resultListX.size() + resultListY.size())) + "%"
                    );
            Scene scene = new Scene(lineChart, 800, 600);

            lineChart.getData().add(errorX);
            lineChart.getData().add(errorY);
            lineChart.getData().add(errorResult);

//            ObservableList<XYChart.Data> dataListAccelerationX = ((XYChart.Series) lineChart.getData().get(0)).getData();
//            for (XYChart.Data value : dataListAccelerationX) {
//
//                Node node = value.getNode();
//                Tooltip tooltip = new Tooltip("?????????????????? ???? ?????? X" + "\n" +
//                        "??????????: " + value.getXValue().toString() + " c????.\n" +
//                        "?????????????????? aX: " + value.getYValue().toString() + " ??/??^2");
//                tooltip.setFont(Font.font("Verdana", 16));
//                Tooltip.install(node, tooltip);
//            }

//            ObservableList<XYChart.Data> dataListAccelerationY = ((XYChart.Series) lineChart.getData().get(1)).getData();
//            for (XYChart.Data value : dataListAccelerationY) {
//
//                Node node = value.getNode();
//                Tooltip tooltip = new Tooltip("?????????????????? ???? ?????? Y" + "\n" +
//                        "??????????: " + value.getXValue().toString() + " c????.\n" +
//                        "?????????????????? aY: " + value.getYValue().toString() + " ??/??^2");
//                tooltip.setFont(Font.font("Verdana", 16));
//                Tooltip.install(node, tooltip);
//            }

//            ObservableList<XYChart.Data> dataListAccelerationRezult = ((XYChart.Series) lineChart.getData().get(2)).getData();
//            for (XYChart.Data value : dataListAccelerationRezult) {
//
//                Node node = value.getNode();
//                Tooltip tooltip = new Tooltip("???????????????????????????? ??????????????????" + "\n" +
//                        "??????????: " + value.getXValue().toString() + " c????.\n" +
//                        "??????????????????: " + value.getYValue().toString() + " ??/??^2");
//                tooltip.setFont(Font.font("Verdana", 16));
//                Tooltip.install(node, tooltip);
//            }

            stage.setScene(scene);
            stage.show();

        });

    }

    public int calcErrorOnAxis(int positionCurrent, int positionNext, List<Integer> positionNecessary){
        // 1 - ???????????? ??????????????
        // 0 - ?????????????????? ??????????????
        if(positionNecessary.contains(positionNext)) { //  100% ???????????????????? ??????????????. ?????? ?????????? ?? ????????????
            // func() ???????????????? ?????????????????? ?????????????????? ?????????????? = 100%
            return 1; //
        }

        if (positionCurrent == positionNext)
            return 0;

        if(positionNecessary.contains(positionCurrent)){ // ???????? ?????? ???????????? ?? ????????????
            if (!positionNecessary.contains(positionNext))
                return 0;
            return 1;
        }

        if(positionCurrent > positionNecessary.get(positionNecessary.size() - 1)){ // ?????? ?????????????????? ????????????/???????? ???????????? ????????
            if (positionNext < positionCurrent){ // ???????????????????? ??????????????
                // func() ?????????????? ???????????????? ?????????????????? ?????????????????? ??????????????
                return 1;
            }
            if (positionNext > positionCurrent) // ?????????????????? ??????????????
                return 0;
        }

        if(positionCurrent < positionNecessary.get(0)){ // ?????? ?????????????????? ??????????/???????? ???????????? ????????
            if (positionNext > positionCurrent){ // ???????????????????? ??????????????
                // func() ?????????????? ???????????????? ?????????????????? ?????????????????? ??????????????
                return 1;
            }
            if (positionNext < positionCurrent) // ?????????????????? ??????????????
                return 0;
        }
        return 0;
    }

    public double calcYValue(int yValue, int middleValue, int coordinateBallYMax) {

        if (yValue < middleValue) {
            double i = 100 - (((double) yValue / (double) middleValue) * 100);
            return i;
        } else if (yValue > middleValue) {
            double i = 100 - (((double)(yValue - middleValue) / (double)(coordinateBallYMax - middleValue)) * 100);
            return i;
        } else if (yValue == middleValue) {
            return 0;
        }
        return 0;

    }

    public double calcXValue(int xValue, int middleValue, int coordinateBallXMax) {

        if (xValue < middleValue) {
            return 100 - (((double) xValue / (double) middleValue) * 100);
        } else if (xValue > middleValue) {
            return 100 - (((xValue - middleValue) / (double)(coordinateBallXMax - middleValue)) * 100);
        } else if (xValue == middleValue) {
            return 0;
        }
        return 0;

    }

}
