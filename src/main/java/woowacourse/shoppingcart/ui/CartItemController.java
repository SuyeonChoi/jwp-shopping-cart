package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
                                            @AuthenticationPrincipal LoginCustomer loginCustomer) {
        final Long cartId = cartService.addCart(product.getId(), loginCustomer);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(loginCustomer));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long cartId,
                                               @AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartService.deleteCart(loginCustomer, cartId);
        return ResponseEntity.noContent().build();
    }
}
