package com.smk.bi.views.about;

import com.smk.bi.model.User;
import com.smk.bi.views.LoginView;
import com.smk.bi.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("Home - Lintang Anggowoyuono")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {
User user = VaadinSession.getCurrent().getAttribute(User.class);
    public AboutView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H2 header = new H2("Welcome petugas");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        try {
        add(new Paragraph("Welcome "+user.getNamaPetugas()+" 🤗"));
        } catch (Exception e) {}

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
