package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1+N

    // ManyToOne Eager 전략 (디폴트)
    // OneToMany Lazy 전략 (디폴트)
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<Reply> replies = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

}