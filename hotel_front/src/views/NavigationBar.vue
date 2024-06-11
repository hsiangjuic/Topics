<template>
  <nav class="navbar navbar-expand-lg "
    :class="{ 'bg-body-tertiary': !isTransparent, 'navbar-transparent': isTransparent }" style="z-index: 999;">
    <div class="container-fluid">
      <img src="/city.png" alt="city" style="max-width: 60px;" />
      <RouterLink class="navbar-brand" to="/">芳山飯店</RouterLink>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
        aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation" style="max-width: 54px;">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
          <li class="nav-item">
            <RouterLink class="nav-link active" aria-current="page" to="/" style="color:black">首頁</RouterLink>
          </li>

          <li class="nav-item">
            <RouterLink class="nav-link active" to="/LatestNews">最新消息</RouterLink>
          </li>

          <li class="nav-item">
            <RouterLink class="nav-link active" to="/pages/login" style="color:black"> 會員登錄</RouterLink>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <RouterLink class="nav-link active" to="/bookingPages/rooms"> 線上訂房</RouterLink>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              查看訂單
            </a>
            <ul class="dropdown-menu">
              <li>
                <RouterLink class="dropdown-item" to="/bookingPages/cart">購物車</RouterLink>
              </li>
              <li>
                <RouterLink class="dropdown-item" to="/bookingPages/bookingCheck">訂單查詢</RouterLink>
              </li>
            </ul>
          </li>
        </ul>



        <ul class="navbar-nav">
          <li class="nav-item dropdown">
            <ul class="navbar-nav">
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                  aria-expanded="false" style="color:black">
                  房型介紹
                </a>
                <ul class="dropdown-menu">
                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">豪華客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/superiorintro1/1">豪華客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/superiorintro2/2">豪華客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/superiorintro4/3">豪華客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">尊榮客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/deluxe1/4">尊榮客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/deluxe2/5">尊榮客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/deluxe4/6">尊榮客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">景觀尊榮客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/deluxeview1/7">景觀尊榮客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/deluxeview2/8">景觀尊榮客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/deluxeview4/9">景觀尊榮客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="no-dropdown">
                    <RouterLink class="dropdown-item" to="/granddeluxefamily4/10">特選尊榮家庭客房四人房</RouterLink>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">超豪華客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/premier1/11">超豪華客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/premier2/12">超豪華客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/premier4/13">超豪華客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">豪華閣豪華客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/horizonsuperior1/14">豪華閣豪華客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/horizonsuperior2/15">豪華閣豪華客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/horizonsuperior4/16">豪華閣豪華客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">豪華閣尊榮客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/horizondeluxe1/17">豪華閣尊榮客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/horizondeluxe2/18">豪華閣尊榮客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/horizondeluxe4/19">豪華閣尊榮客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">景觀超豪華客房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/premierView1/20">景觀超豪華客房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/premierView2/21">景觀超豪華客房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/premierView4/22">景觀超豪華客房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="dropdown-submenu">
                    <a href="#" class="nav-link dropdown-toggle " data-bs-toggle="dropdown"
                      style="color: black;">特級套房</a>
                    <ul class="dropdown-menu">
                      <li>
                        <RouterLink class="dropdown-item" to="/specialtysuite1/23">特級套房單人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/specialtysuite2/24">特級套房兩人房</RouterLink>
                      </li>
                      <li>
                        <RouterLink class="dropdown-item" to="/specialtysuite4/25">特級套房四人房</RouterLink>
                      </li>
                    </ul>
                  </li>

                  <li class="no-dropdown">
                    <RouterLink class="dropdown-item" to="/plazasuite2/26">雅仕套房兩人房</RouterLink>
                  </li>

                </ul>
              </li>
            </ul>

          </li>
        </ul>


        <ul class="navbar-nav">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              餐廳相關(訂位/外帶)
            </a>
            <ul class="dropdown-menu">
              <li>
                <RouterLink class="dropdown-item" to="/restaurant/RestaurantBooking">線上訂位</RouterLink>
              </li>
              <li>
                <RouterLink class="dropdown-item" to="/restaurant/RestaurantBookingModify">查詢訂位
                </RouterLink>
              </li>
              <li>
                <RouterLink class="dropdown-item" to="/restaurant/RestaurantQuestion">訂位問題</RouterLink>
              </li>
              <li>
                <RouterLink class="dropdown-item" to="/restaurant/RestaurantMenu">外帶餐點</RouterLink>
              </li>
            </ul>
          </li>
        </ul>






















        <!-- 以下這段要在最右邊 -->
        <div class="ml-auto" style="margin-left: auto;">
          <!-- 點擊用戶圖標後觸發展開側邊攔 -->
          <i class="fa fa-user fa-2x" data-bs-toggle="offcanvas" href="#offcanvasExample" role="button"
            aria-controls="offcanvasExample"></i>
        </div>
      </div>
    </div>
  </nav>

  <!-- 展開側邊攔 -->
  <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasExample" aria-labelledby="offcanvasExampleLabel"><br>
    <div class="offcanvas-header">
      <h5 class="offcanvas-title" id="offcanvasExampleLabel">會員: {{ username }}</h5>
      <!-- <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button> -->
    </div>
    <br>
    <div class="offcanvas-body">
      <RouterLink class="btn11" aria-current="page" to="/pages/register">會員註冊</RouterLink><br><br>

      <RouterLink class="btn11" to="/pages/login">會員登錄</RouterLink><br><br>

      <RouterLink class="btn11" to="/pages/customerupdate">會員編輯</RouterLink>
    </div>


    <div class="offcanvas-body">
      <button v-if="isAuthenticated" @click="confirmLogout" class="logout">登出</button>
    </div>






  </div>
</template>

<script>
import Swal from 'sweetalert2';
import { mapGetters, mapMutations } from 'vuex';
import router from '@/router/router.js';
import '@fortawesome/fontawesome-free/css/all.css';

export default {
  data() {
    return {
      isTransparent: true
    };
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
      // 監聽頁面滾動事件，根據滾動位置決定是否使導覽列透明
      this.isTransparent = window.pageYOffset === 0;
    },
    ...mapMutations(['logout']),
    confirmLogout() {
      Swal.fire({
        title: '確定登出?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '確定',
        cancelButtonText: '取消'
      }).then((result) => {
        if (result.isConfirmed) {
          this.performLogout();
        }
      });
    },
    performLogout() {
      this.logout(); // 用 Vuex 的登出方法
      sessionStorage.removeItem("username");
      router.push({ name: 'home-link' }); // 連到登入頁面
    }
  },
  computed: {
    ...mapGetters(['getUsername', 'isAuthenticated']),
    username() {
      return this.getUsername;
    }
  }
};
//滑鼠點擊特效
window.onload = () => {
  document.addEventListener("click", (event) => {
    const ripple = document.createElement("span");
    const diameter = Math.min(20, Math.max(event.target.clientWidth, event.target.clientHeight));
    const radius = diameter / 2;
    ripple.style.width = ripple.style.height = diameter + "px";
    ripple.style.left = event.clientX - (diameter / 2) + "px";
    ripple.style.top = event.clientY - (diameter / 2) + "px";
    ripple.classList.add("ripple");
    const existingRipple = document.querySelector(".ripple");
    if (existingRipple) {
      existingRipple.remove();
    }
    document.body.appendChild(ripple);
  });
};

</script>

<style>
@import url('./style/btn.css');

.navbar-nav li:hover>ul.dropdown-menu {

  display: block;

}

.dropdown-submenu>.dropdown-menu {

  /* 調整元素位置 */
  top: 0;
  left: 100%;
  margin-top: -5px;

}

.dropdown-submenu {

  /* 讓展開來的選單不要疊在一起 */
  position: relative;

}

body {
  /* background-color: #5fe90f17 !important; */
  background-image: url('/lobby.jpg');
  opacity: 90;
  background-attachment: fixed;
  /* 固定背景圖片 */
  background-size: cover;
  /* 根據容器大小調整圖片大小 */
}

/* 水波紋效果參考https://blog.tarswork.com/post/a-selection-of-cool-css-button-effects#google_vignette */
.ripple {
  position: absolute;
  border-radius: 50%;
  background-color: rgba(43, 42, 41, 0.178);
  transform: scale(0);
  animation: ripple-effect 0.5s linear;

}

/* 多選項的 li */
.navbar-nav li:hover {

  background-color: rgba(201, 199, 199);


}

/* 單選項的 li */
.navbar-nav .no-dropdown:hover>.dropdown-item {

  background-color: rgba(201, 199, 199);


}

.navbar-nav .no-dropdown>.dropdown-item {

  padding-left: 8px;
  font-size: 16px;
  color: black
}

.navbar-nav li {

  font-size: 16px;
  color: black;
  opacity: 1;

}

@keyframes ripple-effect {
  to {
    transform: scale(4);
    opacity: 0;
  }
}

/* 上面是水波紋效果 */



.offcanvas-end {
  background-image: linear-gradient(to right, gray, rgba(0, 0, 255, 0.027));
  border-radius: 20px 0px 0px 20px;
  max-width: 150px;
}

.bg-body-tertiary {
  background-image: linear-gradient(to left, rgba(0, 255, 98, 0.11), rgba(0, 108, 248, 0.205));
  /* border-radius: 0px 0px 20px 20px; */

  position: sticky;
  top: 0;
  min-width: 100%;
  max-width: 68.1%;
  z-index: 999;
  opacity: 0.8;
}

.offcanvas-body button {
  border-radius: 20px;
  background-color: rgba(0, 255, 0, 0.377);
  width: 100%;
}




.navbar-transparent {
  /* background-color: transparent !important; */
  background-image: url('/background-625659_1280.jpg');
  /* border-radius: 0px 0px 20px 20px; */
  width: 100%;
  /* Set width to 100% */
  max-width: 100%;
  /* Limit width to 100% of viewport width */

}



.navbar-brand {
  color: #0c0c0c;
}


.offcanvas-header {
  background-image: url('/label-153093_1280.png');
  background-position: center;
  background-size: 200px;
}
</style>