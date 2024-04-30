package org.aibles.java.demologin.util;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
//Phương thức commence được ghi đè để xử lý các trường hợp khi người dùng truy cập vào tài nguyên yêu cầu xác
// thực mà không được cung cấp hoặc cung cấp sai thông tin xác thực.
//Trong phương thức commence, nếu xác thực không thành công,
// phương thức sendError của đối tượng HttpServletResponse sẽ được gọi để gửi một mã lỗi 401 Unauthorized
// kèm theo thông điệp "Error: Unauthorized". Điều này thông báo cho người dùng rằng họ không có quyền truy
// cập tài nguyên và cần cung cấp các thông tin xác thực hợp lệ.
////Phan nay em hieu no co tac dung gi chua