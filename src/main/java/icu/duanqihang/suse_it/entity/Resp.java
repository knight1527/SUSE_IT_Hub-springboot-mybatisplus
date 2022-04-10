package icu.duanqihang.suse_it.entity;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public class Resp<E> {
    private String code;
    private String message;
    private E body;

    public Resp(String code, String message, E body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getBody() {
        return body;
    }

    public void setBody(E body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", body=" + body +
                '}';
    }

    public static <E> Resp<E> success(E body){
        return new Resp<E>("200","success",body);
    }

    public static <E> Resp<E> fail(String code,String message){
        return new Resp<E>(code,message,null);
    }
}
