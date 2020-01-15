package io.github.idilantha.pos.controller;

import io.github.idilantha.pos.AppInitializer;
import io.github.idilantha.pos.business.custom.CustomerBO;
import io.github.idilantha.pos.business.custom.OrderBO;
import io.github.idilantha.pos.dto.OrderDTO2;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import io.github.idilantha.pos.util.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SearchOrdersFormController {

    public TextField txtSearch;
    public TableView<OrderTM> tblOrders;
    public AnchorPane root;
    OrderBO orderBO = AppInitializer.ctx.getBean(OrderBO.class);

    public void initialize() throws Exception {

        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        loadTable();
        ObservableList<OrderTM> olOrders = tblOrders.getItems();

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    loadTable();
                    loadTables();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.INFORMATION, "Something went wrong.. contact DEPPO").show();
                }
            }
        });
    }

    private void loadTables() {

        String searchText = txtSearch.getText();
        ObservableList<OrderTM> tempOrders = FXCollections.observableArrayList();
        ObservableList<OrderTM> olOrders = tblOrders.getItems();

        for (OrderTM tempSearch : olOrders) {
            if (tempSearch.getOrderId().contains(searchText) || tempSearch.getOrderDate().contains(searchText) || tempSearch.getCustomerId().contains(searchText) || tempSearch.getCustomerName().contains(searchText)) {
                tempOrders.add(tempSearch);
            }
        }
        tblOrders.setItems(tempOrders);
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.txtSearch.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void tblOrders_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            URL resource = this.getClass().getResource("/view/PlaceOrderForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();
            Scene placeOrderScene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(placeOrderScene);
            secondaryStage.centerOnScreen();
            secondaryStage.setTitle("View Order");
            secondaryStage.setResizable(false);

            PlaceOrderFormController ctrl = fxmlLoader.getController();
//            OrderTM selectedOrder = tblOrders.getSelectionModel().getSelectedItem();
//            ctrl.initializeForSearchOrderForm(selectedOrder.getOrderId());

            secondaryStage.show();
        }
    }

    public void loadTable() throws Exception {

        List<OrderDTO2> orderInfo = orderBO.getOrderInfo();
        //System.out.println(orderBO.getOrderInfo('%' + txtSearch.getText() + '%'));
        ObservableList<OrderTM> items = tblOrders.getItems();
        items.clear();

        for (OrderDTO2 order : orderInfo) {
            items.add(new OrderTM(order.getOrderId() + "", order.getOrderDate() + "", order.getCustomerId(), order.getCustomerName(), order.getTotal()));
        }
        tblOrders.setItems(items);
    }

}
