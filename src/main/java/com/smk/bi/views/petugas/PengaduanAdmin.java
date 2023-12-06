package com.smk.bi.views.petugas;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

import com.smk.bi.model.Pengaduan;
import com.smk.bi.model.Tanggapan;
import com.smk.bi.model.User;
import com.smk.bi.service.PengaduanService;
import com.smk.bi.service.TanggapanService;
import com.smk.bi.service.UserService;
import com.smk.bi.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "pengaduan-admin", layout = MainLayout.class)
public class PengaduanAdmin extends VerticalLayout {

    private final PengaduanService pengaduanService;
    private final TanggapanService tanggapanService;
    private final UserService userService;
    public PengaduanAdmin(PengaduanService pengaduanService,
    TanggapanService tanggapanService,
    UserService userService) {
        this.pengaduanService = pengaduanService;
        this.tanggapanService = tanggapanService;
        this.userService = userService;

        // Retrieve all Pengaduan
        List<Pengaduan> pengaduanList = pengaduanService.getAllPengaduan();

        // Create a Grid to display the Pengaduan list
        Grid<Pengaduan> grid = new Grid<>(Pengaduan.class);
        grid.setItems(pengaduanList);

        // Define columns to display in the Grid
        grid.removeColumnByKey("foto");
        grid.getColumnByKey("tglPengaduan").setHeader("Tanggal Pengaduan");
        grid.getColumnByKey("judul").setHeader("Judul");
        grid.getColumnByKey("isiLaporan").setHeader("Isi Laporan");

        // Add a custom column to display the photo
        grid.addComponentColumn(this::createImageComponent)
            .setHeader("Foto");

        grid.getColumnByKey("status").setHeader("Status");

        // Add a click listener to navigate to a detailed view or show more information
        grid.addItemClickListener(event -> {
            Pengaduan selectedPengaduan = event.getItem();
            // You can navigate to a detailed view or show more information here
            // For example, navigate to a view using the selected Pengaduan's id
            if (selectedPengaduan.getStatus() == 0) {
                selectedPengaduan.setStatus(1);
                pengaduanService.savePengaduan(selectedPengaduan);
                // Show a notification or perform other actions as needed
                Notification.show("Pengaduan status updated to 1");
                // You can also refresh the grid to reflect the updated status
                UI.getCurrent().getPage().reload();
            } else if (selectedPengaduan.getStatus() == 1) {
                showTanggapanForm(selectedPengaduan);
            } else {
                Notification.show("Cannot create Tanggapan for Pengaduan with status other than 1");
            }
        });

        // Add the Grid to the view
        add(grid);
    }
    private void showTanggapanForm(Pengaduan pengaduan) {
        Dialog tanggapanFormDialog = new Dialog();
        FormLayout formLayout = new FormLayout();

        TextArea tanggapanTextArea = new TextArea("Tanggapan");

        Button submitButton = new Button("Submit", event -> {
            User loggedInUser = VaadinSession.getCurrent().getAttribute(User.class);
            Tanggapan tanggapan = new Tanggapan();
            tanggapan.setPengaduan(pengaduan);
            tanggapan.setTanggapan(tanggapanTextArea.getValue());

            // Set petugas ID as the logged-in user's ID (you need to implement this logic in UserService)
            // User loggedInUser = userService.getLoggedInUser(); // Implement this method in UserService
            tanggapan.setPetugas(loggedInUser);

            tanggapanService.saveTanggapan(tanggapan);

            // Update the status of Pengaduan to 2
            pengaduan.setStatus(2);
            pengaduanService.savePengaduan(pengaduan);

            Notification.show("Tanggapan submitted successfully!");

            tanggapanFormDialog.close();
            // Refresh the grid or page as needed
            UI.getCurrent().getPage().reload();
        });

        formLayout.add(tanggapanTextArea, submitButton);
        tanggapanFormDialog.add(formLayout);
        tanggapanFormDialog.open();
    }
    private Image createImageComponent(Pengaduan pengaduan) {
        byte[] imageBytes = pengaduan.getFoto();
        Image image = new Image();
        if (imageBytes != null && imageBytes.length > 0) {
            image.getElement().setAttribute("src", "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(imageBytes));
            // Atur lebar dan tinggi gambar
            image.setWidth("100px");
            image.setHeight("100px");
        }
        return image;

    }
}