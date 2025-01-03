import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.iut.exceptions.*;
import fr.iut.exceptions.InvalidDiscountCodeException;
import fr.iut.exceptions.OutOfStockException;
import fr.iut.Invoice;
import fr.iut.Order;
import fr.iut.Product;
import fr.iut.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

public class OrderManagementTest {
    private Product laptop;
    private Product phone;
    private ShoppingCart cart;
    private Order order;

    @BeforeEach
    void setUp() {
        laptop = new Product("Laptop", 999.99, 5);
        phone = new Product("Phone", 499.99, 3);
        cart = new ShoppingCart();
    }

    // test adding to the cart a product out of stock
       @Test
    void testAddProductOutOfStock() {
        Product outOfStockProduct = new Product("Camera", 249.99, 0);
        assertThrows(OutOfStockException.class, () -> cart.addProduct(outOfStockProduct), "Adding an out-of-stock product should throw an exception");
    }

    //apply an invalid promotionnal code
    @Test
    void testApplyInvalidPromo() throws OutOfStockException {
        cart.addProduct(laptop);
        order = new Order(cart);
        assertThrows(InvalidDiscountCodeException.class, () -> order.applyDiscount("INVALID"), "Applying an invalid code should throw an exception");
    }


    // Test checking product stock
    @Test
    void testStockCountPositiveOrZero() {
        assertEquals(5, laptop.getStockQuantity(), "Stock should be 5");
    }


    // Test removing an item from the cart
    @Test
    void testRemoveProductFromCart() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.removeProduct(laptop);
        assertEquals(0, cart.getItemCount(), "Cart should be empty after removing the only product");
    }
    // removing a non existent product

    @Test
    void testRemoveNonExistentProduct() {
        cart.removeProduct(laptop);
        assertEquals(0, cart.getItemCount(), "Removing a non-existent product should not alter the cart");
    }

    // Test adding an out-of-stock product to the cart
    @Test
    void testAddOutOfStockProduct() {
        Product outOfStockProduct = new Product("Headphones", 99.99, 0);
        assertThrows(OutOfStockException.class, () -> cart.addProduct(outOfStockProduct), "Adding an out-of-stock product should trigger an exception");
    }

    // test invoice without products
    @Test
    void testInvoiceWithOneProduct() throws OutOfStockException {
        cart.addProduct(laptop);
        order = new Order(cart);
        Invoice invoice = new Invoice(order);
        String invoiceContent = invoice.generateInvoice();
        assertTrue(invoiceContent.contains("Laptop"), "Invoice should include product details for 'Laptop'");
    }
    // test invoice with plenty of products
    @Test
    void testInvoiceWithMultipleProducts() throws OutOfStockException {
        cart.addProduct(laptop);
        cart.addProduct(phone);
        order = new Order(cart);
        Invoice invoice = new Invoice(order);
        String invoiceContent = invoice.generateInvoice();
        assertTrue(invoiceContent.contains("Laptop") && invoiceContent.contains("Phone"), "Invoice should include details for multiple products");
    }
    // test applying a discount
    @Test
    void testOrderReturnsDiscount() throws OutOfStockException, InvalidDiscountCodeException {
        cart.addProduct(laptop);
        order = new Order(cart);
        order.applyDiscount("PROMO10");
        assertEquals(0.10, order.getDiscount(), 0.01, "Order should correctly return the applied discount");
    }
    // test order methods that returns the cart
    @Test
    void testOrderReturnsCart() throws OutOfStockException {
        cart.addProduct(laptop);
        order = new Order(cart);
        assertNotNull(order.getShoppingCart(), "Order should return the cart it contains");
    }
    // test invoice with a 10% discount applied
    @Test
    void testInvoiceIncludesDiscount10() throws OutOfStockException, InvalidDiscountCodeException {
        cart.addProduct(laptop);
        order = new Order(cart);
        order.applyDiscount("PROMO10");

        Invoice invoice = new Invoice(order);
        String invoiceContent = invoice.generateInvoice();
        assertTrue(invoiceContent.contains("Remise: 10,00%"), "Invoice should display the discount percentage when a discount is applied.");
    }
    // test invoice with a 20% discount applied
    @Test
    void testInvoiceIncludesDiscount20() throws OutOfStockException, InvalidDiscountCodeException {
        cart.addProduct(laptop);
        order = new Order(cart);
        order.applyDiscount("PROMO20");

        Invoice invoice = new Invoice(order);
        String invoiceContent = invoice.generateInvoice();
        assertTrue(invoiceContent.contains("Remise: 20,00%"), "Invoice should display the discount percentage when a discount is applied.");
    }
    // reducing by 1 the quantity of a product
    @Test
    void testDecrementStock() {
        int initialStock = laptop.getStockQuantity();
        laptop.decrementStock();
        assertEquals(initialStock - 1, laptop.getStockQuantity(), "Stock quantity should decrease by 1 after decrementing.");
    }
    //reducing the quantity of a product to 0
    @Test
    void testDecrementStockToZero() {
        laptop = new Product("Laptop", 999.99, 1); // stock = 1
        laptop.decrementStock();
        assertEquals(0, laptop.getStockQuantity(), "Stock quantity should be 0 after decrementing from 1.");
    }
    // reducing the quantity of a product lower to 0
    @Test
    void testDecrementStockWhenEmpty() {
        laptop = new Product("Laptop", 999.99, 0); // stock = 0
        laptop.decrementStock();
        assertEquals(0, laptop.getStockQuantity(), "Stock quantity should remain 0 when attempting to decrement stock from 0.");
    }
}

