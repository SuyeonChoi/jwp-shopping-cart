package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;

public class Customer {
    private final Long id;
    private final String loginId;
    private final String name;
    private final String password;

    public Customer(String loginId, String name, String password) {
        this(null, loginId, name, password);
    }

    public Customer(Long id, Customer customer) {
        this(id, customer.getLoginId(), customer.getName(), customer.getPassword());
    }

    public Customer(Long id, String loginId, String name, String password) {
        validateLoginId(loginId);
        validatePassword(password);
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    private void validateLoginId(String loginId) {
        String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        if (!Pattern.matches(emailRegex, loginId)) {
            throw new IllegalArgumentException("아이디 형식이 잘못되었습니다.");
        }
    }

    private void validatePassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$";
        if (!Pattern.matches(passwordRegex, password)) {
            throw new IllegalArgumentException("비밀번호 형식이 잘못되었습니다.");
        }
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
