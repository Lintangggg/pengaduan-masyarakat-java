package com.smk.bi.views.masyarakat;

import java.io.IOException;

import com.smk.bi.model.Masyarakat;
import com.smk.bi.model.Pengaduan;
import com.smk.bi.service.MasyarakatService;
import com.smk.bi.service.PengaduanService;
import com.smk.bi.views.LoginView;
import com.smk.bi.views.MainLayout;
import com.smk.bi.views.MasyarakatLayout;
import com.smk.bi.views.MasyarakatView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "pengaduan", layout = MasyarakatLayout.class)
public class PengaduanForm extends VerticalLayout {
    
    private final MasyarakatService masyarakatService;
    private final PengaduanService pengaduanService;

    public PengaduanForm (MasyarakatService masyarakatService, PengaduanService pengaduanService) {
        this.masyarakatService = masyarakatService;
        this.pengaduanService = pengaduanService;

        TextField judulField = new TextField("Judul");
        TextArea isiLaporanField = new TextArea("Isi Laporan");

        // File Upload
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setMaxFiles(1);
        upload.setMaxFileSize(5 * 1024 * 1024); // 5MB

        Button submitButton = new Button("Submit", event -> {
            byte[] fileContent = null;
            try {
                fileContent = buffer.getInputStream().readAllBytes();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            submitPengaduan(judulField.getValue(), isiLaporanField.getValue(),fileContent);});
        setMargin(true);
        // setVerticalComponentAlignment(Alignment.END, judulField,isiLaporanField,submitButton);
        add(judulField,isiLaporanField,upload,submitButton);
    }

    private void submitPengaduan(String judul, String isiLaporan, byte[] fileContent) {
        Masyarakat loggedInMasyarakat = VaadinSession.getCurrent().getAttribute(Masyarakat.class);
        if (loggedInMasyarakat != null) {
            // Create a new Pengaduan and associate it with the logged-in Masyarakat
            Pengaduan newPengaduan = new Pengaduan();
            newPengaduan.setJudul(judul);
            newPengaduan.setIsiLaporan(isiLaporan);
            newPengaduan.setNik(loggedInMasyarakat.getNik());
            newPengaduan.setFoto(fileContent);

            // Set other necessary fields and submit the Pengaduan
            pengaduanService.savePengaduan(newPengaduan);

            // Optionally close the Pengaduan Form Dialog after submission
            // pengaduanFormDialog.close();

            // Display a success message or perform other actions as needed
            Notification.show("Pengaduan submitted successfully!");
        } else {
            getUI().ifPresent(ui -> ui.navigate(LoginView.class));
        }
    }
}
