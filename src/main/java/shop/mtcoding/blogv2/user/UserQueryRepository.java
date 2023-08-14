package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserQueryRepository {
    //persist() , clear(), flush() , removed()   removed는 잘 안씀
    //트랜잭셕 종료시 flush 자동 실행
    @Autowired
    private EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findById(Integer id){
        return em.find(User.class,id);
    }
}
