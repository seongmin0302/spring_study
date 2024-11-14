package com.youtube_board_project.board.controller;

import com.youtube_board_project.board.entity.Board;
import com.youtube_board_project.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller     // 스프링에게 여기가 컨트롤러 라고 알려준다!
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/")   //http://localhost:8090/
    @ResponseBody
    public String main()
    {
        return "Hello Wolrd";
    }

    @GetMapping("/board/write")  // http://localhost:8090/board/write
    public String boardWirteForm()
    {
        return "boardwrite";
    }

    @PostMapping("board/writepro")
    public String boardWritePro(Board board, Model model, @RequestParam(name="file", required = false) MultipartFile file) throws Exception
    {
        boardService.write(board,file);
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }

    //Model은 데이터를 담아서 우리가 보는 페이지로 넘겨주는 역할!
    //jpa의 findAll()매서드에 pageable을 넘김으로 페이징 처리를 할수 있다. 이때  pageable는 페이지의 번호와 크기가 필요하다.
    //페이지의 번호와 크기는 url을 통해서 pageable에 전달 된다.  이때 @PageableDefault 사용해서 디폴트값 넣어둘수 있다.
    @GetMapping("/board/list")
    public String boardList(Model model,@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable)
    {                                                   //page: default 페이지, size: 한페이지 게시글수, sort: 정렬기준 컬럼, direction: 정렬순서
        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;  //페이지는 0부터 시작함으로 우리가 보는것 보다 1적다
        int startPage = Math.max(nowPage - 4,1);   // Math.max()는 매개변수로 들어온 두 값중 더 높은값 반환. 1보다 작아지면 1을 반환!
        int endPage = Math.min(nowPage + 5, list.getTotalPages());  //토탈페이지를 넘어버리면 토탈페이지를 반환

        model.addAttribute("list", list );
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "boardlist";
    }

    @GetMapping("/board/view") //localhost:8090/board/view?id=1
    public String boardView(Model model,@RequestParam("id") Integer id)
    {
        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("board/delete")
    public String boardDelete(@RequestParam("id") Integer id)
    {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id , Model model )  // //{id}가 id로 들어온다.
    {
        model.addAttribute("board",boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id , Board board ,@RequestParam(name="file", required = false)MultipartFile file) throws Exception // //{id}가 id로 들어온다.
    {
        Board boardTemp = boardService.boardView(id);  //기존 글 검색
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        boardService.write(boardTemp, file );

        return "redirect:/board/list";
    }





}