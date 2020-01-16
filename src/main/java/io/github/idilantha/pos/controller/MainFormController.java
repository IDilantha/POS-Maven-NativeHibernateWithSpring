package io.github.idilantha.pos.controller;

import com.jfoenix.controls.JFXProgressBar;
import io.github.idilantha.pos.AppInitializer;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep.crypto.DEPCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public JFXProgressBar pgb;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgCustomer;
    @FXML
    private ImageView imgItem;
    @FXML
    private ImageView imgOrder;
    @FXML
    private ImageView imgViewOrders;
    @FXML
    private Label lblMenu;
    @FXML
    private Label lblDescription;

    private Environment env;

    private String getUsername(){
        return DEPCrypt.decode(env.getRequiredProperty("hibernate.connection.username"),"123");
    }

    private String getPassword(){
        return DEPCrypt.decode(env.getRequiredProperty("hibernate.connection.password"),"123");
    }

    private String getIp(){
        return env.getRequiredProperty("pos.ip");
    }

    private String getPort(){
        return env.getRequiredProperty("pos.port");
    }

    private String getDb(){
        return env.getRequiredProperty("pos.db");
    }


    public void initialize(URL url, ResourceBundle rb) {
        env = AppInitializer.ctx.getBean(Environment.class);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(2000), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        pgb.setVisible(false);


    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgCustomer":
                    lblMenu.setText("Manage Customers");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "imgItem":
                    lblMenu.setText("Manage Items");
                    lblDescription.setText("Click to add, edit, delete, search or view items");
                    break;
                case "imgOrder":
                    lblMenu.setText("Place Orders");
                    lblDescription.setText("Click here if you want to place a new order");
                    break;
                case "imgViewOrders":
                    lblMenu.setText("Search Orders");
                    lblDescription.setText("Click if you want to search orders");
                    break;
            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    @FXML
    private void navigate(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;

            FXMLLoader fxmlLoader = null;
            switch (icon.getId()) {
                case "imgCustomer":
                    root = FXMLLoader.load(this.getClass().getResource("/view/ManageCustomerForm.fxml"));
                    break;
                case "imgItem":
                    root = FXMLLoader.load(this.getClass().getResource("/view/ManageItemForm.fxml"));
                    break;
                case "imgOrder":
                    root = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));
                    break;
                case "imgViewOrders":
                    root = FXMLLoader.load(this.getClass().getResource("/view/SearchOrdersForm.fxml"));
                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.root.getScene().getWindow();

                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();

            }
        }
    }

    public void restoreBtn_onAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Let's restore the backup");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showOpenDialog(this.root.getScene().getWindow());

        if (file != null) {
            String[] commands;
            if (getPassword().length() > 0) {
                commands = new String[]{"mysql", "-h", getIp(), "--port",getPort(), "-u", getUsername(), "-p" + getPassword(), getDb(), "-e", "source " + file.getAbsolutePath()};
            } else {
                commands = new String[]{"mysql", "-h", getIp(), "--port", getPort(), "-u", getUsername(), getDb(), "-e", "source " + file.getAbsolutePath()};
            }
            this.root.getScene().setCursor(Cursor.WAIT);
            pgb.setVisible(true);

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    Process process = Runtime.getRuntime().exec(commands);
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        br.lines().forEach(System.out::println);
                        br.close();
                        throw new RuntimeException("wade kachal");
                    } else {
                        return null;
                    }
                }
            };


            task.setOnSucceeded(event -> {
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.INFORMATION, "Database Restored Successfully").show();
                pgb.setVisible(false);
            });
            task.setOnFailed(event -> {
                this.root.getScene().setCursor(Cursor.DEFAULT);
                new Alert(Alert.AlertType.ERROR, "Database Restored Error").show();
                pgb.setVisible(false);
            });
            new Thread(task).start();
        }
    }

    public void backupBtn_onAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save the DB Backup");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SQL File", "*.sql"));
        File file = fileChooser.showSaveDialog(this.root.getScene().getWindow());
        if (file != null) {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("mysqldump -h" + getIp() + " -u" + getUsername() + " -p"
                        + getPassword() + " " + getDb() + " --result-file "
                        + file.getAbsolutePath() + (file.getAbsolutePath().endsWith(".sql") ? "" : ".sql"));
                int exitCode = process.waitFor();
                pgb.setVisible(true);

                if (exitCode != 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    br.lines().forEach(System.out::println);
                    br.close();
                    throw new RuntimeException("wade kachal");
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Database Backedup Successfully").show();
                    pgb.setVisible(false);
                }
                System.out.println(exitCode);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
