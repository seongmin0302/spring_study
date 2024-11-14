package seeya.insight.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import seeya.insight.entity.GameRoomTb;
import seeya.insight.entity.MbrAccountTb;
import seeya.insight.service.MBR_ACCOUNT_TB_Service;
import seeya.insight.service.GAME_ROOM_TB_Service;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    MBR_ACCOUNT_TB_Service mbrAccountTbService;

    @Autowired
    GAME_ROOM_TB_Service gameRoomTbService;

    @GetMapping(value = "/main")
    public String main(Model model,RedirectAttributes redirectAttributes, @PageableDefault(page = 0, size = 3, sort = "roomSeq", direction = Sort.Direction.DESC) Pageable pageable, HttpSession session)
    {
        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        // 로그인된 사용자가 없다면 로그인 페이지로 리다이렉트
        if (mbrAccountTb == null) {
            redirectAttributes.addFlashAttribute("goLogin", "로그인 후 이용해주세요.");
            return "redirect:/login";
        }


        Page<GameRoomTb> list = gameRoomTbService.gameRoomList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list.getContent());
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        //순위
        Pageable pageableForList2 = PageRequest.of(0, 5 , Sort.by(Sort.Direction.DESC, "mbrSeq"));
        Page<MbrAccountTb> list2 = mbrAccountTbService.viewAllMbr(pageableForList2);
        model.addAttribute("list2", list2.getContent());

        return "main";
    }

    @PostMapping(value = "/main/nickname-change")
    public ResponseEntity<String> mainNicknameChangePro(@RequestBody MbrAccountTb dto, HttpSession session) {

        String newMbrNm = dto.getMbrNm();

        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        if (mbrAccountTb == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to change password");
        }

        mbrAccountTb.setMbrNm(newMbrNm);
        //업데이트
        mbrAccountTbService.join(mbrAccountTb);

        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping(value = "/main/pwd-change")
    public ResponseEntity<String> mainPwdChangePro(@RequestBody MbrAccountTb dto, HttpSession session) {

        String newMbrPwd = dto.getMbrPwd();

        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        if (mbrAccountTb == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("패스워드 변경 실패");
        }

        mbrAccountTb.setMbrPwd(newMbrPwd);
        //업데이트
        mbrAccountTbService.join(mbrAccountTb);

        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping(value = "/main/create-room")
    public ResponseEntity<Map<String, Object>> mainCreateRoomPro(@RequestBody GameRoomTb dto, HttpSession session) {

        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        if (mbrAccountTb == null)
        {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "새로운 방을 만들수가 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        dto.setRoomPlayer1(mbrAccountTb.getMbrNm());
        dto.setRoomStatus("게임시작");
        gameRoomTbService.creat(dto);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "방을 성공적으로 만들었습니다.");
        responseData.put("roomSeq", dto.getRoomSeq());
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = "/game_room")  //localhost:8090/game_room/roomSeq?id=1
    public String game_room(HttpSession session, Model model, RedirectAttributes redirectAttributes,@RequestParam("roomSeq") Integer roomSeq)
    {

        // 세션에서 "mbrAccountTb" 객체를 가져오기
        MbrAccountTb mbrAccountTb = (MbrAccountTb) session.getAttribute("user");

        // 로그인된 사용자가 없다면 로그인 페이지로 리다이렉트
        if (mbrAccountTb == null) {
            redirectAttributes.addFlashAttribute("goLogin", "로그인 후 이용해주세요.");
            return "redirect:/login";
        }

        Optional<GameRoomTb> gameRoomTbOptional = gameRoomTbService.viewByRoomSeq(roomSeq);
        if (gameRoomTbOptional.isPresent()) {  //게임방이 존재한다면

            GameRoomTb gameRoomTb = gameRoomTbOptional.get();
            if(gameRoomTb.getRoomStatus().equals("게임시작"))
            {
                gameRoomTb.setRoomStatus("대기중");
                gameRoomTbService.creat(gameRoomTb);
                model.addAttribute("gameRoomTb",gameRoomTb);
                return "game_room";
            }
            else if (gameRoomTb.getRoomStatus().equals("대기중"))
            {
                gameRoomTb.setRoomPlayer2(mbrAccountTb.getMbrNm());
                gameRoomTb.setRoomStatus("진행중");
                gameRoomTbService.creat(gameRoomTb);
                model.addAttribute("gameRoomTb",gameRoomTb);
                return "game_room";
            }
            else if(gameRoomTb.getRoomStatus().equals("진행중"))
            {
                redirectAttributes.addFlashAttribute("error", "진행중인 게임방에 입장할 수 없습니다.");
                return "redirect:/main";
            }
            else //게임종료
            {
                redirectAttributes.addFlashAttribute("error", "게임종료된 게임방에 입장할 수 없습니다.");
                return "redirect:/main";
            }




        }


        // 게임방 입장 실패
        redirectAttributes.addFlashAttribute("error", "게임방에 입장할수 없습니다.");
        return "redirect:/main";
    }

    @PostMapping(value = "/game_room/out-gameroom")
    public ResponseEntity<String> outGameRoomPro(@RequestBody GameRoomTb dto, HttpSession session)
    {

        Integer roomSeq = dto.getRoomSeq();

        Optional<GameRoomTb> gameRoomTbOptional = gameRoomTbService.viewByRoomSeq(roomSeq);
        if (gameRoomTbOptional.isPresent()) {  //게임방이 존재한다면
            GameRoomTb gameRoomTb = gameRoomTbOptional.get();
            gameRoomTb.setRoomStatus("게임종료");
            gameRoomTbService.creat(gameRoomTb);
            return ResponseEntity.ok("Password changed successfully");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("패스워드 변경 실패");
        }
    }

}
