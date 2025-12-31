package ir.maktabsharif;

import ir.maktabsharif.config.ApplicationContext;
import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.view.UniversityConsoleApp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try {
            UniversityConsoleApp app = ApplicationContext.getInstance().createApp();

            app.run();

        } finally {
            JpaUtil.close();
        }
    }
}