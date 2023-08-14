package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//핵심로직 처리, 트랜잭션 관리, 예외처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO) {
        User user = User.builder().
                username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        userRepository.save(user);
    }

    public User 로그인(UserRequest.LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        //유저네임 검증
        if (user == null) {
            return null;
        }
        //패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())){
            return null;
        }

        //로그인 성공
        return user;
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User 회원수정(UserRequest.UpdateDTO updateDTO, Integer id) {
        //1. 조회(영속화)
        User user = userRepository.findById(id).get();
        //2. 변경
        user.setPassword(updateDTO.getPassword());

        return user;
        //3. Flush
    }
}
