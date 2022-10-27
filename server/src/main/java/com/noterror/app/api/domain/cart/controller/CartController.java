package com.noterror.app.api.domain.cart.controller;

import com.noterror.app.api.domain.cart.dto.CartDetailResponseDto;
import com.noterror.app.api.domain.cart.dto.CartResponseDto;
import com.noterror.app.api.domain.cart.dto.PurchaseQuantityDto;
import com.noterror.app.api.domain.cart.service.CartService;
import com.noterror.app.api.domain.member.service.MemberService;
import com.noterror.app.api.domain.product.service.ProductService;
import com.noterror.app.api.entity.Product;
import com.noterror.app.api.entity.cart.Cart;
import com.noterror.app.api.entity.cart.CartDetail;
import com.noterror.app.api.entity.member.Member;
import com.noterror.app.api.global.response.MultiCartsResponse;
import com.noterror.app.api.global.response.SingleCartDetailResponse;
import com.noterror.app.api.global.response.SingleCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class CartController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    /**
     * 장바구니 제품 추가
     */
    @PostMapping("{product-id}")
    public ResponseEntity addProductInCart(@PathVariable("product-id") Long productId,
                                           @RequestBody @Valid PurchaseQuantityDto purchaseQuantityDto) {
        CartDetail cartDetail = toCartDetail(productId, purchaseQuantityDto);
        Cart cart = cartService.addProductInCart(cartDetail);
        CartDetailResponseDto response = new CartDetailResponseDto(cart);

        return new ResponseEntity(
                new SingleCartDetailResponse(response), HttpStatus.OK);
    }

    /**
     * 장바구니 제품 전체 조회
     */
    @GetMapping
    public ResponseEntity getCartDetailsInCart() {
        Cart cartOfMember = cartService.getCartOfMember(getMemberByEmail());
        CartResponseDto response = new CartResponseDto(cartOfMember);
        return new ResponseEntity(
                new SingleCartResponse(response), HttpStatus.OK);
    }

    /**
     * 장바구니 제품 수량 변경
     */
    @PutMapping
    public ResponseEntity updateCartProduct(@RequestBody @Valid UpdatePurchaseQuantityDto updatePurchaseQuantityDto) {
        UpdatePurchaseQuantityDto cartDetail = cartService.updateCart(updatePurchaseQuantityDto);
        return new ResponseEntity(new SingleCartDetailResponse(cartDetail), HttpStatus.OK);
    }

    /**
     * 장바구니 제품 삭제
     */
    @DeleteMapping("/{cart-detail-id}")
    public ResponseEntity deleteCartProduct(@PathVariable("cart-detail-id") Long cartDetailId) {

        cartService.deleteCart(cartDetailId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private Member getMemberByEmail() {
        return memberService.findMemberByEmail(getCurrentUserEmail());
    }


    private CartDetail toCartDetail(Long productId, PurchaseQuantityDto purchaseQuantityDto) {
        return purchaseQuantityDto
                .toCartDetailWithMemberAndProduct
                        (getMemberByEmail(), getProduct(productId));
    }

    private Product getProduct(Long productId) {
        Product product = productService.findProduct(productId);
        return product;
    }
}