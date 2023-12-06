package com.smk.bi.views.petugas;

import com.smk.bi.model.Tanggapan;
import com.smk.bi.service.TanggapanService;
import com.smk.bi.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "tanggapan-list", layout = MainLayout.class)
public class Laporan extends VerticalLayout {

    private final TanggapanService tanggapanService;

    public Laporan(TanggapanService tanggapanService) {
        this.tanggapanService = tanggapanService;

        // Retrieve all Tanggapan
        List<Tanggapan> tanggapanList = tanggapanService.getAllTanggapan();

        // Create a Grid to display the Tanggapan list
        Grid<Tanggapan> grid = new Grid<>(Tanggapan.class);
        grid.setItems(tanggapanList);

        // Define columns to display in the Grid
        // grid.addColumn("tglTanggapan").setHeader("Tanggal Tanggapan");
        // grid.addColumn("tanggapan").setHeader("Tanggapan");
        // grid.addColumn("petugas").setHeader("Petugas"); // Assuming you have a "petugas" property in Tanggapan

        // Add the Grid to the view
        add(grid);
    }
}
