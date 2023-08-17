package shop.mtcoding.blogv2.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.User;


/*
1. 비즈니스 로직 처리(핵심로직)
2. 트랜잭션 관리
3. 예외처리
4. DTO 변환(나중에 할꺼)
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();

        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }


    public Board 상세보기(Integer id) {
        return boardRepository.findById(id).get();
    }

    public void 삭제(Integer id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 업데이트(Integer id,BoardRequest.UpdateDTO updateDTO) {
        Board board = boardRepository.findById(id).get();
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());
    }

    public Board 업데이트보기(Integer id) {
        return boardRepository.findById(id).get();
    }
}