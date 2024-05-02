package org.aibles.java.demologin.service;


import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.aibles.java.demologin.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;// định danh duy nhất của người dùng

    private String username;// tên người dùng phục vụ cho việc đăng nhập

    private String email;// email người dùng

    @JsonIgnore// ẩn mật khẩu người dùng
    private String password;//mật khẩu người dùng, đuược ẩn đi khi ử dụng trong API

    private Collection<? extends GrantedAuthority> authorities;// Danh sách các quyền của người dùng, được đại diện bởi GrantedAuthor

    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Customer user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;// trả về true chỉ ra rằng tài khoản không hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;// tk không bị khoá
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;// thông tin đăng nhập không hết hạn
    }

    @Override
    public boolean isEnabled() {
        return true;// tài khoản được kích hoạt
    }

    @Override
    public boolean equals(Object o) {// so sánh giữa hai đối tượng
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
