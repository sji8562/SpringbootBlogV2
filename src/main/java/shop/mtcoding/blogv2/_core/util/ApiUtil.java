package shop.mtcoding.blogv2._core.util;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiUtil<T>{

    private boolean sucuess; //true ,false
    private T data;   //댓글쓰기 성공 ,실패

    public ApiUtil(boolean sucess, T data){
        this.sucuess = sucess;
        this.data = data;
    }

    }
