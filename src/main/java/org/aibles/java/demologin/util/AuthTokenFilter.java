package org.aibles.java.demologin.util;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.aibles.java.demologin.service.JWTService;
import org.aibles.java.demologin.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


public class AuthTokenFilter extends OncePerRequestFilter {//dùng để thao tác với JWT tạo xác thực token
    @Autowired
    private JWTService jwtUtils;  //Cai nay nen dat package filter
    @Autowired
    private UserDetailsServiceImpl userDetailsService;// tải thông tin người dùng từ cơ sở dữ liệu

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);// ghi log các hoạt động trong filter

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);// lấy JWT
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) { // xác thực token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);// nếu JWT hợp lệ thì tải về thông tin người dùng

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);// load thông tin người udng thông qua tên gnuoiwf dùng

                UsernamePasswordAuthenticationToken authentication =// đối tượng được sử dụng bởi spring security ể đại diện cho thông tin xác thực người dùng
                        new UsernamePasswordAuthenticationToken(
                                userDetails// đối tượng chứa thoong tin chi tiết của người dùng
                                ,null// tham số mật khẩu, Trong trường hợp sử dụng JWT, mật khẩu không cần thiết vì người dùng đã được xác thực qua JWT, không qua mật khẩu truyền thống.
                                ,userDetails.getAuthorities());// Cung cấp danh sách các quyền (roles/authorities) của người dùng, cho phép Spring Security quản lý truy cập dựa trên vai trò.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// Tạo chi tiết xác thực bổ sung từ yêu cầu HTTP, như địa chỉ IP và session ID.

                SecurityContextHolder.getContext().setAuthentication(authentication);//Là một lớp trung tâm trong Spring Security,
                // nó lưu trữ chi tiết xác thực và cung cấp chúng cho toàn bộ ứng dụng.
                // setAuthentication(authentication) lưu đối tượng xác thực vào SecurityContext,
                // cho phép ứng dụng nhận biết người dùng hiện tại và quản lý truy cập.
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        return request.getParameter("jwtToken");
    }

}
