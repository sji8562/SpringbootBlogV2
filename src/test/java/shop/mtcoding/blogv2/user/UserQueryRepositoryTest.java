package shop.mtcoding.blogv2.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;

@Import(UserQueryRepository.class)
@DataJpaTest
public class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test(){
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userQueryRepository.save(user);
    }


    @Test
    public void findById_test(){
        //pc는 비어있다
        userQueryRepository.findById(1);
        em.clear();
        userQueryRepository.findById(1);
        // pc는 user 1의 객체가 영속화 되어있다.
    }

    @Test
    public void update_test(){
        //jpa update 알고리즘
        //1. 업데이트 할 객체를 영속화
        //2. 객체 상태변경
        //3. em.flush() or @Transactional 종료
        User user = userQueryRepository.findById(1);
        user.setEmail("ssarmango@nate.com");
        em.flush();
    }
}
