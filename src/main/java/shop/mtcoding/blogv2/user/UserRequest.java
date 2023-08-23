package shop.mtcoding.blogv2.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    @Getter @Setter
    public static class JoinDTO{
        private String username;
        private String password;
        private String email;
        private MultipartFile pic;
    }

    @Getter @Setter
    public static class LoginDTO{


        private String username;
        private String password;
    }
    @Getter @Setter
    public static class UpdateDTO{

        private String password;
    }
}
