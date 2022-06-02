package woowacourse.shoppingcart.ui;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.LoginCustomer;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody @Valid CustomerRequest request) {
        CustomerResponse response = customerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getMe(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok(customerService.findByLoginId(loginCustomer));
    }

    @PutMapping("/me")
    public ResponseEntity<CustomerResponse> updateMe(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                     @RequestBody @Valid CustomerRequest request) {
        CustomerResponse response = customerService.update(loginCustomer, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
    public ResponseEntity<CustomerResponse> deleteMe(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                     @RequestBody @Valid CustomerDeleteRequest request) {
        customerService.delete(loginCustomer, request);
        return ResponseEntity.noContent().build();
    }
}
