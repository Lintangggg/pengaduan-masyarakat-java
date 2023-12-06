package com.smk.bi.views;

import com.smk.bi.model.Masyarakat;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("main-masyarakat")
public class MasyarakatView extends MasyarakatLayout{
    Masyarakat masyarakat = VaadinSession.getCurrent().getAttribute(Masyarakat.class);
}
