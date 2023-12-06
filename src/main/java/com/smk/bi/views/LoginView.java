package com.smk.bi.views;

import com.smk.bi.model.Masyarakat;
import com.smk.bi.model.User;
import com.smk.bi.model.UserRole;
import com.smk.bi.service.MasyarakatService;
import com.smk.bi.service.UserService;
import com.smk.bi.views.about.AboutView;
import com.smk.bi.views.masyarakat.PengaduanForm;
import com.smk.bi.views.petugas.PengaduanAdmin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;

import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class LoginView extends VerticalLayout {

    private final UserService userService;
    private final MasyarakatService masyarakatService;

    @Autowired
    public LoginView(UserService userService, MasyarakatService masyarakatService) {
        this.userService = userService;
        this.masyarakatService = masyarakatService;

        // User Login Form
        TextField userUsernameField = new TextField("Username (User)");
        PasswordField userPasswordField = new PasswordField("Password (User)");

        Button userLoginButton = new Button("Login as Petugas", event -> loginUser(userUsernameField.getValue(), userPasswordField.getValue()));

        // User Registration Form
        TextField userRegisterUsernameField = new TextField("Username");
        PasswordField userRegisterPasswordField = new PasswordField("Password");
        TextField userRegisterNamaPetugasField = new TextField("Nama Petugas");
        TextField userRegisterTelpField = new TextField("Telephone");

        Button userRegisterButton = new Button("Register as User", event -> registerUser(userRegisterUsernameField.getValue(), userRegisterPasswordField.getValue(), userRegisterNamaPetugasField.getValue(), userRegisterTelpField.getValue()));
        
        // Masyarakat Login Form
        TextField masyarakatUsernameField = new TextField("Username (Masyarakat)");
        PasswordField masyarakatPasswordField = new PasswordField("Password (Masyarakat)");

        Button masyarakatLoginButton = new Button("Login as Masyarakat", event -> loginMasyarakat(masyarakatUsernameField.getValue(), masyarakatPasswordField.getValue()));

        // Masyarakat Registration Form
        TextField masyarakatRegisterUsernameField = new TextField("Username");
        PasswordField masyarakatRegisterPasswordField = new PasswordField("Password");
        TextField masyarakatRegisterNamaField = new TextField("Nama");
        TextField masyarakatRegisterNikField = new TextField("NIK");
        TextField masyarakatRegisterTelpField = new TextField("Telephone");

        Button masyarakatRegisterButton = new Button("Register as Masyarakat", event -> registerMasyarakat(masyarakatRegisterUsernameField.getValue(), masyarakatRegisterPasswordField.getValue(), masyarakatRegisterNamaField.getValue(), masyarakatRegisterNikField.getValue(), masyarakatRegisterTelpField.getValue()));

        // Layout setup
        FormLayout userLoginForm = new FormLayout(userUsernameField, userPasswordField, userLoginButton);
        FormLayout userRegisterForm = new FormLayout(userRegisterUsernameField, userRegisterPasswordField, userRegisterNamaPetugasField, userRegisterTelpField, userRegisterButton);
        Dialog userLoginDialog = new Dialog(userLoginForm);
        Dialog userRegisterDialog = new Dialog(userRegisterForm);

        FormLayout masyarakatLoginForm = new FormLayout(masyarakatUsernameField, masyarakatPasswordField, masyarakatLoginButton);
        FormLayout masyarakatRegisterForm = new FormLayout(masyarakatRegisterUsernameField, masyarakatRegisterPasswordField, masyarakatRegisterNamaField, masyarakatRegisterNikField, masyarakatRegisterTelpField, masyarakatRegisterButton);
        Dialog masyarakatLoginDialog = new Dialog(masyarakatLoginForm);
        Dialog masyarakatRegisterDialog = new Dialog(masyarakatRegisterForm);
        Button showUserLoginButton = new Button("Login as Petugas", event -> userLoginDialog.open());
        Button showUserRegisterButton = new Button("Register as Petugas", event -> userRegisterDialog.open());
        Button showMasyarakatLoginButton = new Button("Login as Masyarakat", event -> masyarakatLoginDialog.open());
        Button showMasyarakatRegisterButton = new Button("Register as Masyarakat", event -> masyarakatRegisterDialog.open());
        

        String buttonMargin = "50px";
        String buttonWidth = "500px"; 
        String buttonHeight = "100px";
        showUserLoginButton.setWidth(buttonWidth);
        showUserRegisterButton.setWidth(buttonWidth);
        showMasyarakatLoginButton.setWidth(buttonWidth);
        showMasyarakatRegisterButton.setWidth(buttonWidth);

        showUserLoginButton.getStyle().set("margin-top", buttonMargin);
        showUserRegisterButton.getStyle().set("margin-top", buttonMargin);
        showMasyarakatLoginButton.getStyle().set("margin-top", buttonMargin);
        showMasyarakatRegisterButton.getStyle().set("margin-top", buttonMargin);

        showUserLoginButton.setHeight(buttonHeight);
        showUserRegisterButton.setHeight(buttonHeight);
        showMasyarakatLoginButton.setHeight(buttonHeight);
        showMasyarakatRegisterButton.setHeight(buttonHeight);

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(showUserLoginButton, showUserRegisterButton, showMasyarakatLoginButton, showMasyarakatRegisterButton);
    }

    private void loginUser(String username, String password) {
        User user = userService.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            showWelcomeMessage("Petugas", user.getNamaPetugas());
            VaadinSession.getCurrent().setAttribute(User.class, user);
            getUI().ifPresent(ui -> ui.navigate(AboutView.class));
        } else {
            showLoginFailedNotification();
        }
    }

    private void loginMasyarakat(String username, String password) {
        Masyarakat masyarakat = masyarakatService.getMasyarakatByUsername(username);
        if (masyarakat != null && masyarakat.getPassword().equals(password)) {
            showWelcomeMessage("Masyarakat", masyarakat.getNama());
            VaadinSession.getCurrent().setAttribute(Masyarakat.class, masyarakat);
            getUI().ifPresent(ui -> ui.navigate(PengaduanForm.class));
        } else {
            showLoginFailedNotification();
        }
    }

    private void registerUser(String username, String password, String namaPetugas, String telp) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setNamaPetugas(namaPetugas);
        newUser.setTelp(telp);

        newUser.setLevel(UserRole.petugas);

        userService.saveUser(newUser);
        Notification.show("Registration successful!");
    }

    private void registerMasyarakat(String username, String password, String nama, String nik, String telp) {
        Masyarakat newMasyarakat = new Masyarakat();
        newMasyarakat.setUsername(username);
        newMasyarakat.setPassword(password);
        newMasyarakat.setNama(nama);
        newMasyarakat.setNik(nik);
        newMasyarakat.setTelp(telp);

        masyarakatService.saveMasyarakat(newMasyarakat);
        Notification.show("Registration successful!");
    }

    private void showWelcomeMessage(String role, String name) {
        Notification.show("Welcome, " + role + ": " + name + "!");
    }

    private void showLoginFailedNotification() {
        Notification.show("Login failed. Please check your credentials.");
    }
}
