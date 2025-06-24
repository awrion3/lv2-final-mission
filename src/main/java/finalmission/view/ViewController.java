package finalmission.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String displayIndexPage() {
        return "index";
    }

    @GetMapping("/signup")
    public String displaySignupPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String displayLoginPage() {
        return "login";
    }

    @GetMapping("/reservation")
    public String displayReservationPage() {
        return "reservation";
    }

    @GetMapping("/reservation-mine")
    public String displayMyReservationPage() {
        return "reservation-mine";
    }

    @GetMapping("/admin/reservation")
    public String displayAdminReservationPage() {
        return "admin/reservation-admin";
    }
}
