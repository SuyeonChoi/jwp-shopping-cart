package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemUpdateRequest {

    @Positive
    private int quantity;

    private CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem toCartItem() {
        return null;
    }
}
