package shop.mtcoding.blogv2.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // localhost:8080?page=1&keyword=바나나
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber()-1);
        request.setAttribute("nextPage", boardPG.getNumber()+1);
        return "index";
    }
    @GetMapping("/test")
    public @ResponseBody Page<Board> test(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        return boardPG; // ViewResolver (X), MessageConverter (O) -> json 직렬화
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    // 1. 데이터 받기 (V)
    // 2. 인증체크 (:TODO)
    // 3. 유효성 검사 (:TODO)
    // 4. 핵심로직 호출 (V)
    // 5. view or data 응답} (V)
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model){

        Board board = boardService.상세보기(id);
        model.addAttribute("board",board);
        return "board/detail";
    }

    @GetMapping("/board/{id}/delete")
    public void delete(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        boardService.삭제(id);
        response.sendRedirect("/");
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id,HttpServletRequest request){
        Board board = boardService.업데이트보기(id);
        request.setAttribute("board", board);
        return "/board/updateForm";
    }


    @PostMapping("/board/{id}/update")
    public void update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO,HttpServletResponse response) throws IOException {
        boardService.업데이트(id, updateDTO);

        response.sendRedirect("/board/"+id);
    }
}