package seeya.insight.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import seeya.insight.entity.MbrAccountTb;
import seeya.insight.service.MBR_ACCOUNT_TB_Service;

import java.util.Optional;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    MBR_ACCOUNT_TB_Service mbrAccountTbService;


    @GetMapping(value = "/login")
    public String login(HttpSession session) {
        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        //세션에 저장돼 있는 사용자가 있다면
        if (mbrAccountTb != null) {
            session.invalidate();
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPro(MbrAccountTb dto, HttpSession session, Model model) {

        // 로그인 화면에서 입력받은 아이디와 패스워드
        String dtoMbrId = dto.getMbrId();
        String dtoMbrPwd = dto.getMbrPwd();

        Optional<MbrAccountTb> mbrAccountTbOptional = mbrAccountTbService.viewById(dtoMbrId);
        if (mbrAccountTbOptional.isPresent()) {
            MbrAccountTb mbrAccountTb = mbrAccountTbOptional.get();
            if (mbrAccountTb.getMbrPwd().equals(dtoMbrPwd)) {
                session.setAttribute("user", mbrAccountTb);
                return "redirect:/main";
            }
        }

        // 로그인 실패 시
        model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
        return "login";
    }

    @GetMapping(value = "/find_id")
    public String findId() {
        return "find_id";
    }

    @PostMapping("/find_id")
    public String findIdPro(MbrAccountTb dto, Model model) {
        // 아이디 찾기 화면에서 입력받은 이메일
        String mbrEm = dto.getMbrEm();

        Optional<MbrAccountTb> mbrAccountTbOptional = mbrAccountTbService.viewByEm(mbrEm);
        if (mbrAccountTbOptional.isPresent()) {
            MbrAccountTb mbrAccountTb = mbrAccountTbOptional.get();

            Random random = new Random();
            int randomNumber = 1000000 + random.nextInt(9000000);

            //mbrAccountTb에 인증번호 저장
            mbrAccountTb.setMbrCertificationNumber(randomNumber + "");

            //업데이트
            mbrAccountTbService.join(mbrAccountTb);

            model.addAttribute("viewCertificationNumber", "인증번호는" + randomNumber + "입니다. 인증번호를 잘 기억하시고 확인버튼을 누르세요. ");
            return "find_id";

        }

        model.addAttribute("error", "일치하는 이메일이 존재하지 않습니다.");
        return "find_id";

    }

    @GetMapping(value = "/find_pw")
    public String find_pw() {
        return "find_pw";
    }

    @PostMapping("/find_pw")
    public String findPwPro(MbrAccountTb dto, Model model) {
        //비밀번호 찾기 화면에서 입력받은 아이디와 이메일
        String dtoMbrId = dto.getMbrId();
        String dtoMbrEm = dto.getMbrEm();

        Optional<MbrAccountTb> mbrAccountTbOptional = mbrAccountTbService.viewById(dtoMbrId);
        if (mbrAccountTbOptional.isPresent()) {
            MbrAccountTb mbrAccountTb = mbrAccountTbOptional.get();
            if (mbrAccountTb.getMbrEm().equals(dtoMbrEm)) {

                //이 안에서 mbrAccountTb은 아이디와 이메일 모두 일치하는 회원이다.

                Random random = new Random();
                int randomNumber = 1000000 + random.nextInt(9000000);

                //mbrAccountTb에 인증번호 저장
                mbrAccountTb.setMbrCertificationNumber(randomNumber + "");

                //업데이트
                mbrAccountTbService.join(mbrAccountTb);

                model.addAttribute("viewCertificationNumber", "인증번호는" + randomNumber + "입니다. 인증번호를 잘 기억하시고 확인버튼을 누르세요. ");
                return "find_pw";
            }
        }

        //아이디또는 이메일 둘중하나 또는 모두 잘못된경우
        model.addAttribute("error", "일치하는 아이디 또는 이메일이 존재하지 않습니다.");
        return "find_pw";
    }

    @GetMapping(value = "/signin")
    public String signin() {
        return "signin";
    }

    //회원가입 처리
    @PostMapping("/signin")
    public String SignUpPro(MbrAccountTb dto,Model model)
    {
        //회원가입화면에서 입력받은것들
        String dtoMbrId = dto.getMbrId();
        String dtoMbrPwd = dto.getMbrPwd();
        String dtoMbrPwdCheck = dto.getMbrPwdCheck();
        String dtoMbrNm = dto.getMbrNm();
        String dtoMbrEm = dto.getMbrEm();

        Optional<MbrAccountTb> mbrAccountTbOptionalViewById = mbrAccountTbService.viewById(dtoMbrId);
        Optional<MbrAccountTb> mbrAccountTbOptionalViewByNm = mbrAccountTbService.viewByNm(dtoMbrNm);
        Optional<MbrAccountTb> mbrAccountTbOptionalViewByEm = mbrAccountTbService.viewByEm(dtoMbrEm);

        //중복회원조회
        if (mbrAccountTbOptionalViewById.isPresent() || mbrAccountTbOptionalViewByNm.isPresent() || mbrAccountTbOptionalViewByEm.isPresent())
        {
            model.addAttribute("error1","아이디 또는 닉네임 또는 이메일이 이미 존재합니다.");
            return "signin";
        }
        else if (!dtoMbrPwdCheck.equals(dtoMbrPwd))  //비밀번호 일치한지 확인
        {
            model.addAttribute("error2","비밀번호가 일치하지 않습니다.");
            return "signin";
        }
        else if (dtoMbrId.length()==0 || dtoMbrPwd.length()==0 || dtoMbrNm.length()==0 || dtoMbrEm.length()==0)
        {
            model.addAttribute("error3","아이디 또는 닉네임 또는 이메일은 공백 일 수 없습니다.");
            return "signin";
        }

        mbrAccountTbService.join(dto);
        model.addAttribute("success","회원가입을 성공적으로 마쳤습니다. 확인버튼을 누르면 로그인페이지로 이동합니다.");
        return "signin";

    }

    @GetMapping(value = "/code_id")
    public String codeId()
    {
        return "code_id";
    }

    @PostMapping(value = "/code_id")
    public String codeIdPro(MbrAccountTb dto, Model model)
    {
        String mbrCertificationNumber=dto.getMbrCertificationNumber();

        Optional<MbrAccountTb> mbrAccountTbOptional = mbrAccountTbService.viewByCertificationNumber(mbrCertificationNumber);
        if (mbrAccountTbOptional.isPresent()) {
        MbrAccountTb mbrAccountTb = mbrAccountTbOptional.get();
        String mbrId = mbrAccountTb.getMbrId();

        model.addAttribute("viewMbrId", "아이디는"+mbrId+"입니다. 확인버튼을 누르면 로그인페이지로 이동합니다.");
        return "code_id";
        }

        model.addAttribute("error", "인증번호를 다시 확인해주세요.");
        return "code_id";

    }

    @GetMapping(value = "/code_pw")
    public String codePw()
    {
        return "code_pw";
    }

    @PostMapping(value = "/code_pw")
    public String codePwPro(MbrAccountTb dto, Model model)
    {
        String mbrCertificationNumber=dto.getMbrCertificationNumber();

        Optional<MbrAccountTb> mbrAccountTbOptional = mbrAccountTbService.viewByCertificationNumber(mbrCertificationNumber);
        if (mbrAccountTbOptional.isPresent()) {
            MbrAccountTb mbrAccountTb = mbrAccountTbOptional.get();
            String mbrPwd = mbrAccountTb.getMbrPwd();

            model.addAttribute("viewMbrId", "비밀번호는"+mbrPwd+"입니다. 확인버튼을 누르시면 로그인페이지로 이동합니다.");
            return "code_pw";
        }

        model.addAttribute("error", "인증번호를 다시 확인해주세요.");
        return "code_pw";
    }



    @GetMapping(value = "/check_id")
    public String checkId()
    {
        return "check_id";
    }

}
