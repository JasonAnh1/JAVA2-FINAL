<template>
  <div class="container my-4">
    <h2 class="text-center">Your Cart</h2>
    <div v-if="cartItems.length > 0" class="list-group">
      <div v-for="item in cartItems" :key="item.id"
        class="list-group-item d-flex align-items-center justify-content-between">
        <img :src="item.product.media.originUrl" alt="product Image" class="img-thumbnail me-3"
          style="width: 160px; height: 180px;">
        <div class="flex-grow-1">
          <h5 class="mb-1">{{ item.product.title }}</h5>
          <small class="text-muted">Quantity: {{ item.quantity }}</small>
          <p class="mb-1">Price: {{ formatPrice(item.product.price) }} VND</p>
        </div>
        <button class="btn btn-danger" @click="removeItem(item.productId)">Remove</button>
      </div>
      <div class="mt-3 text-end">
        <h4>Total Price: {{ formatPrice(totalPrice) }} VND</h4>
        <button class="btn btn-success mt-3" @click="placeOrder">Place Order</button>
      </div>
    </div>
    <div v-else>
      <p class="text-center">Your cart is empty.</p>
    </div>
  </div>
</template>


<script>
import axios from "axios";
export default {
  data() {
    return {
      // Dữ liệu tĩnh
      cartItems: [
      ]
    };
  },
  name: 'CartPage',
  computed: {
    totalPrice() {
      return this.cartItems.reduce((total, item) => {
        return total + (item.quantity * item.product.price);
      }, 0);
    }
  },
  methods: {
    formatPrice(value) {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
    },
    async removeItem(productId) {
      let accessToken = localStorage.getItem("accessToken");
      // Xóa sản phẩm khỏi giỏ hàng
      const response = await axios.get("api/v1/delete-cart-item", {
        params: {
          productId: productId
        },
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      })
      this.cartItems = response.data;
    },
    async placeOrder() {
      try {
        const orderRequest = {
          totalAmount: this.totalPrice,
          totalItem: this.cartItems.reduce((sum, item) => sum + item.quantity, 0),
        };
        let accessToken = localStorage.getItem("accessToken");
        const response = await axios.post('api/v1/place-order', orderRequest, {
          headers: {
           Authorization: `Bearer ${accessToken}`
          }
        });
        
        if (response.status === 200) {
          this.fetchCart()
          this.$swal.fire({
            icon: 'success',
            title: 'Order placed successfully!',
            showConfirmButton: false,
            timer: 1500
          });
        }
      } catch (error) {
        this.$swal.fire({
          icon: 'error',
          title: 'Failed to place order',
          text: 'Please try again.',
        });
      }
    },
  
    async fetchCart() {
      let accessToken = localStorage.getItem("accessToken");

      if (!accessToken) {
        this.$swal.fire({
          icon: 'error',
          title: 'Authentication Error',
          text: 'Access token is missing. Please log in.',
          footer: '<a href="/login">Go to login page</a>'
        });
        return;
      }

      try {
        const response = await axios.get("api/v1/get-carts", {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        });
        this.cartItems = response.data;
      } catch (error) {
        this.$swal.fire({
          icon: 'error',
          title: 'API Error',
          text: 'Failed to fetch cart items. Please try again later.',
          footer: '<a href="">Try again</a>'
        });
      }
    }
  },
  async mounted() {
    this.fetchCart()
  },
};
</script>
<style scoped>
.container {
  max-width: 900px;
}
</style>
