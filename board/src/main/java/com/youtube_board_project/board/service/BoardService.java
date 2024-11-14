package com.youtube_board_project.board.service;

import com.youtube_board_project.board.entity.Board;
import com.youtube_board_project.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성 처리
    public void write(Board board, MultipartFile file) throws Exception
    {                         //현재 우리의 프로젝트 경로 반환
        String projectPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files"; //매개변수로 들어온 파일을 저장할 경로 projectPath에 저장
        //반환된 프로젝트 경로에 src/main/resources/static/files를 덧붙여서 파일을 저장할 경로를 설정합니다. 이 경로는 프로젝트 내에서 정적 자원들이 저장될 위치입니다

        //매개변수로 들어온 파일이 저장될 이름을 랜덤으로 만들어준다.
        UUID uuid = UUID.randomUUID();  //랜덤 식별자 생성
        String fileName = uuid + "_" + file.getOriginalFilename(); //고유한 식별자(UUID)와 원본 파일명을 결합하여 저장할 파일 이름을 만듭니다.

        File saveFile = new File(projectPath, fileName);  //파라미터로 들어돈 파일 저장할 빈 껍데기 생성, 이떄 저장될 파일의 경로와 이름을 지정해준다.
        // 파일을 저장할 빈 파일 객체를 생성합니다. 이때 경로와 파일 이름을 지정하여 실제로 저장될 위치와 이름을 설정합니다.

        file.transferTo(saveFile); // 업로드된 파일을 지정한 경로에 지정한 이름으로 저장

        // 데이터베이스에도 저장하기 위해서!
        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName); // 서버에서 접근할때 static밑에 있는것은 그 아래 경로 만으로도 접근이 가능!
        boardRepository.save(board);
    }

//    게시글 리스트 처리
//    public List<Board> boardList()
//    {
//        return boardRepository.findAll();
//    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable)
    {
        return boardRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id)
    {
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id)
    {
        boardRepository.deleteById(id);
    }


}
