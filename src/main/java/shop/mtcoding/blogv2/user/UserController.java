package shop.mtcoding.blogv2.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired //DI
    private UserService userService;

    @Autowired
    private HttpSession session;
    //V - C
    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    //M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println(joinDTO.getPic().getOriginalFilename());
        // System.out.println(joinDTO.getPic().getSize());
        // System.out.println(joinDTO.getPic().getContentType());

        userService.회원가입(joinDTO);
        return "user/loginForm"; // persist 초기화
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String join(UserRequest.LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser",sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request){
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user",user);
        return "user/updateForm";
    }

    @PostMapping("update")
    public String update (UserRequest.UpdateDTO updateDTO){
        //회원수정 (서비스)
        //세션 동기화
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser",user);
        return "redirect:/";
    }

    //브라우저 GET /logout 요청을 함(request)
    //서버는 / 주소를 응답의 헤더에 담음 (Location), 상태코드 302
    //브라우전
    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/api/check")
    public @ResponseBody ApiUtil<String> check(String username) {
        User user = userService.중복체크(username);
        if (user == null) {
            return new ApiUtil<>(true, "사용가능 아이디");
        } else {
            return new ApiUtil<>(false, "이미 사용중인 아이디입니다");
        }
    }
}
