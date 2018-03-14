package team.antelope.fg.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @Author：hwc
 * @Date：2017/12/6 14:39
 * @Desc: sqlite所要用到的实体user，这个指android的客户端个人用户
 */

public class User {
    private Long id; //id,后台系统设置
    private String name;    //用户名
    private String password;//密码
    private String email;   //邮箱

    public User(Long id, String name, String password,
                String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    /**
     * @Description 无参公有构造方法
     * @date 2017/12/6
     */
    public User() {
    }
    /**
     * @Description 构造方法
     * @date 2017/12/6
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;
        if (!password.equals(user.password)) return false;
        return email != null ? email.equals(user.email) : user.email == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
