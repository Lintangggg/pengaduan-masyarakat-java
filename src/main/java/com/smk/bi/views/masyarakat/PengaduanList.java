package com.smk.bi.views.masyarakat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.smk.bi.model.Masyarakat;
import com.smk.bi.model.Pengaduan;
import com.smk.bi.model.Tanggapan;
import com.smk.bi.service.PengaduanService;
import com.smk.bi.service.TanggapanService;
import com.smk.bi.views.MasyarakatLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "pengaduan-list", layout = MasyarakatLayout.class)
public class PengaduanList extends VerticalLayout {
    private final PengaduanService pengaduanService;
    private final TanggapanService tanggapanService;

    public PengaduanList(PengaduanService pengaduanService, TanggapanService tanggapanService) {
        this.pengaduanService = pengaduanService;
        this.tanggapanService = tanggapanService;

        // Retrieve the logged-in Masyarakat
        Masyarakat loggedInMasyarakat = VaadinSession.getCurrent().getAttribute(Masyarakat.class);

        // Check if a Masyarakat is logged in
        if (loggedInMasyarakat != null) {
            // Retrieve Pengaduan associated with the logged-in Masyarakat
            List<Pengaduan> pengaduanList = pengaduanService.getPengaduanByMasyarakat(loggedInMasyarakat.getNik());

            // Create a Grid to display the Pengaduan list
            Grid<Pengaduan> grid = new Grid<>(Pengaduan.class);
            grid.setItems(pengaduanList);

            // Define columns to display in the Grid
            grid.setColumns("tglPengaduan", "judul", "isiLaporan", "status");
            grid.addItemClickListener(event -> {
                Pengaduan selectedPengaduan = event.getItem();
                if (selectedPengaduan.getStatus() == 2) {
                    showTanggapan(selectedPengaduan);
                } else {
                    Notification.show("Hanya bisa menampilkan tanggapan pengaduan yang sudah selesai (2)");
                }
            });
            // Add the Grid to the view
            add(grid);
        } else {
            // Handle the case where no Masyarakat is logged in
            add("No Masyarakat logged in");
        }
    }
    private void showTanggapan(Pengaduan pengaduan) {
        Dialog dialog = new Dialog();
        dialog.setModal(true);
        dialog.setWidth("400px"); // Set the width as needed

        List<Tanggapan> tanggapanList = tanggapanService.getTanggapanByPengaduan(pengaduan.getId());
        Tanggapan tanggapan = tanggapanList.get(0);

        // Create components to display Tanggapan details
        VerticalLayout layout = new VerticalLayout();
        layout.add(new Span("Tanggapan:"));
        layout.add(new Span(tanggapan.getTanggapan())); // Replace with appropriate fields

        // Close button to close the dialog
        Button closeButton = new Button("Close");
        closeButton.addClickListener(event -> dialog.close());
        layout.add(closeButton);

        dialog.add(layout);
        dialog.open();
    }
}
