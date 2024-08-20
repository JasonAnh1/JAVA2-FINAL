<template>
  <div class="row">
    <nav>
      <ul class="nav mb-2">
        <!-- Category List -->
        <li class="nav-item">
              <input type="text" class="form-control" placeholder="Search for books..." v-model="searchString"
                @input="fetchProducts(searchString)" />
            </li>
        <li class="nav-item">
          <a class="nav-link active" href="#" v-on:click="fetchProducts()">All</a>
        </li>
        <li class="nav-item" v-for="category in categories" :key="category.id">
          <a class="nav-link active" href="#" v-on:click="fetchProducts(searchString, category.id)">{{ category.title
            }}</a>
        </li>

      </ul>
    </nav>
    <!-- Product Grid -->
    <div class="col-md-4 mb-4" v-for="item in products" :key="item.id">
      <div class="card">
        <img :src="item.media.thumbUrl" style="height: 300px;" class="card-img-top" :alt="item.name">
        <div class="card-body">
          <h5 class="card-title">{{ item.name }}</h5>
          <p class="card-text"><strong>${{ item.price.toFixed(2) }}</strong></p>
          <button class="btn btn-primary" @click="addToCart(item.id)">Add to Cart</button>
        </div>

      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      searchString: "",
      categories: [

      ],
      products: [

      ]
    }
  },
  name: 'UserPage',
  props: {
    msg: String
  },
  async mounted() {
    this.fetchProducts()
    this.fetchCategory()

  },
  methods: {
    async fetchProducts(title, categoryId) {
      const response = await axios.get("api/v1/publish/get-products", {
        params: { name: title, categoryId: categoryId }

      })
      this.products = response.data
    },
    async fetchCategory() {
      const response = await axios.get("api/v1/publish/list-category")
      this.categories = response.data
    },
    async addToCart(productId) {
      if (localStorage.getItem("accessToken") !== null) {
        let payload = {
          productId: productId,
          quantity: 1
        };
        try {
          let accessToken = localStorage.getItem("accessToken");
          await axios.post("api/v1/add-to-cart", payload,
            {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            });
          this.$swal.fire({
            icon: 'success',
            title: 'Yes',
            text: 'Add to cart success!',
            footer: '<a href="">Go to cart?</a>'
          });
        } catch (error) {
          this.$swal.fire({
            icon: 'error',
            title: 'Oh no',
            text: 'Failed to add to cart!',
            footer: '<a href="">Try again later.</a>'
          });
        }
      } else {
        this.$swal.fire({
          icon: 'error',
          title: 'Oh no',
          text: 'You are not logged in yet!',
          footer: '<a href="">Go to login?</a>'
        });
      }
    }

  }
}

</script>