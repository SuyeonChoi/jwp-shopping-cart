package woowacourse;

import woowacourse.shoppingcart.domain.Customer;

public class Fixture {
    public static final String 페퍼_아이디 = "pepper@woowacourse.com";
    public static final String 페퍼_이름 = "pepper";
    public static final String 페퍼_비밀번호 = "Qwe1234!";
    public static final Customer 페퍼 = new Customer(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);
}
