package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.CartItems;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartItemService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public CartItemResponse add(final LoginCustomer loginCustomer, final CartItemCreateRequest cartItemCreateRequest) {
        final Product product = productDao.findProductById(cartItemCreateRequest.getProductId());
        final CartItem cartItem = cartItemCreateRequest.toCartItem(product);
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        final CartItems cartItems = CartItems.of(cartItemDao.findCartItemsByCustomerId(customer.getId()));

        cartItems.add(cartItem);
        final Long cartItemId = cartItemDao.add(customer.getId(), cartItem);
        return CartItemResponse.of(cartItemId, cartItem);
    }

    public List<CartItemResponse> findByCustomer(final LoginCustomer loginCustomer) {
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        final CartItems cartItems = CartItems.of(cartItemDao.findCartItemsByCustomerId(customer.getId()));
        return CartItemResponse.of(cartItems);
    }

    public CartItemResponse updateQuantity(LoginCustomer loginCustomer, Long cartItemId,
                                           CartItemUpdateRequest cartItemUpdateRequest) {
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        final CartItems cartItems = CartItems.of(cartItemDao.findCartItemsByCustomerId(customer.getId()));
        final CartItem cartItem = cartItems.findById(cartItemId);

        final CartItem updatedCartItem = cartItemUpdateRequest.toCartItem(cartItem);
        cartItemDao.update(updatedCartItem);
        return CartItemResponse.of(cartItemId, updatedCartItem);
    }

    public void deleteAll(LoginCustomer loginCustomer) {
        final Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        cartItemDao.deleteAllByCustomerId(customer.getId());
    }

    public void delete(final LoginCustomer loginCustomer, final Long id) {
        customerDao.findByLoginId(loginCustomer.getLoginId());
        cartItemDao.delete(id);
    }
}
