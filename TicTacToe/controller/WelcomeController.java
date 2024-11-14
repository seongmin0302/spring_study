package seeya.insight.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import seeya.insight.entity.MbrAccountTb;

@Controller
public class WelcomeController {

    @GetMapping(value = "/index")
    public String index(HttpSession session)
    {

        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        //세션에 저장돼 있는 사용자가 있다면
        if (mbrAccountTb != null) {
            session.invalidate();
        }
        return "index";
    }



}
