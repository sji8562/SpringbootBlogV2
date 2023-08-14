package shop.mtcoding.blogv2.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blogv2._core.utill.Script;

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
    public void join(UserRequest.JoinDTO joinDTO, HttpServletResponse response) throws IOException {
        userService.회원가입(joinDTO);
        response.sendRedirect("/loginForm");
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String join(UserRequest.LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        User sessionUser = userService.로그인(loginDTO);
        if(sessionUser == null){
            return Script.back("로그인실패");
        }

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



}
