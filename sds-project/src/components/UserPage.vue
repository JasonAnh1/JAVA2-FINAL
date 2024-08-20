<template>
  <div>
    <a href="#" class="text-dark text-decoration-none">
      <h1 class="m-0">BookStore</h1>
    </a>
    <!-- Header -->
    <header class="bg-light py-3">
      <div class="container d-flex justify-content-between align-items-center">
        <!-- Logo -->
        <!-- Navbar -->
        <nav>
          <ul class="nav">
            <!-- Cart -->
            <li class="nav-item">
              <a class="nav-link" v-on:click="goHome">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" v-on:click="goToCart">Cart</a>
            </li>
            <li class="nav-item" v-if="logined == false">
              <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#loginModal">Login</a>
            </li>
            <li class="nav-item" v-if="logined == false">
              <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#registerModal">Register</a>
            </li>
            <li class="nav-item" v-else>
              <a class="nav-link" href="#" v-on:click="logOut">Logout</a>
            </li>
            <!-- Modal -->
            <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel"
              aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="loginModal">Login Modal </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form @submit.prevent="login">
                      <div class="mb-3">
                        <label for="phone" class="form-label">Phone</label>
                        <input type="text" class="form-control" id="phone" v-model="phone" required>
                      </div>
                      <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" v-model="password" required>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" v-on:click="login" data-bs-dismiss="modal">Save
                      changes</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="exampleModalLabel"
              aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="registerModal">Register Modal </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <form @submit.prevent="login">
                      <div class="mb-3">
                        <label for="phone" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" v-model="rname" required>
                      </div>
                      <div class="mb-3">
                        <label for="phone" class="form-label">Full name</label>
                        <input type="text" class="form-control" id="phone" v-model="rfullname" required>
                      </div>
                      <div class="mb-3">
                        <label for="phone" class="form-label">Phone</label>
                        <input type="text" class="form-control" id="phone" v-model="rphone" required>
                      </div>
                      <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="phone" v-model="remail" required>
                      </div>
                      <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" v-model="rpassword" required>
                      </div>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" v-on:click="signup" data-bs-dismiss="modal">Save
                      changes</button>
                  </div>
                </div>
              </div>
            </div>
          </ul>
        </nav>

      </div>
    </header>
    <div class="container d-flex justify-content-between align-items-center">
      <!-- Logo -->

    </div>
    <!-- Main Content -->

    <main class="container mt-4">
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      rname: "",
      rfullname: "",
      rpassword: "",
      remail: "",
      rphone: "",
      logined: false,
      phone: "",
      password: ""
    }
  },
  name: 'UserPage',
  props: {
    msg: String
  },
  async mounted() {
    if (localStorage.getItem("accessToken") !== null) {
      this.logined = true;
    }

  },
  methods: {
    async signup() {
      let payload = {
        phone: this.rphone,
        password: this.rpassword,
        email: this.remail,
        fullname: this.rfullname,
        name: this.rname
      };

      try {
        const response = await axios.post("api/v1/auth/signup", payload);

        if (response.data.accessToken) {
          this.logined = true;
          const accessToken = response.data.accessToken;
          localStorage.setItem("accessToken", accessToken);
          localStorage.setItem("role", response.data.roles[0].name);
          localStorage.setItem("username", response.data.name);
          localStorage.setItem("ownerId", response.data.id);

          this.$swal.fire({
            title: 'Success!',
            text: 'You have signed up successfully!',
            icon: 'success',
            confirmButtonText: 'OK'
          });
        } else {
          throw new Error("Sign-up failed.");
        }
      } catch (error) {
        this.$swal.fire({
          title: 'Error!',
          text: 'Sign-up failed. Please check your details and try again.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    }
    ,
    async login() {
      let payload = {
        phone: this.phone,
        password: this.password
      }
      const response = await axios.post(
        "api/v1/auth/signin",
        payload
      );
      if (response.data.accessToken !== null) {
        this.logined = true;
      }
      const accessToken = response.data.accessToken;
      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("role", response.data.roles[0].name);
      localStorage.setItem("username", response.data.name);
      localStorage.setItem("ownerId", response.data.id);


    },
    logOut() {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("username");
      localStorage.removeItem("ownerId");
      localStorage.removeItem("role");
      this.logined = false;
    },
    goToCart() {
      if (this.$route.path !== '/CartPage') {
        this.$router.push({ path: '/CartPage' });
      }
    },
    goHome() {
      if (this.$route.path !== '/') {
        this.$router.push({ path: "/" })
      }
    }
  }
}
</script>


<style scoped>
/* Custom styles to ensure the navbar items are displayed horizontally */
.nav {
  display: flex;
  flex-direction: row;
}

.nav-item {
  margin-right: 15px;
}

.nav-link {
  padding: 8px 16px;
}

.nav-link.active {
  font-weight: bold;
}
</style>
