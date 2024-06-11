<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary" style="background-color: #FF0080;">
        <div class="container-fluid">
            <RouterLink class="navbar-brand" to="/">HOTEL</RouterLink>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <RouterLink class="nav-link active" aria-current="page" to="/">首頁</RouterLink>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            會員中心
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <RouterLink class="dropdown-item" to="/pages/memberRank">會員等級</RouterLink>
                            </li>
                            
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            客戶中心
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <RouterLink class="dropdown-item" to="/pages/customer">訂房客戶編輯</RouterLink>
                            </li>
                        </ul>
                    </li>

                    <li class="nav-item">
                        <RouterLink class="nav-link active" to="/news">公告管理</RouterLink>
                    </li>
                    <li class="nav-item">
                        <RouterLink class="nav-link active" to="/discount">優惠管理</RouterLink>
                    </li>
                </ul>

                <ul class="navbar-nav">
                    <li>
                        <RouterLink class="dropdown-item" to="/bookingBackPages/bookings">訂單管理</RouterLink>
                    </li>
                </ul>

                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: black;">
                            房間相關
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <RouterLink class="dropdown-item" to="/room/rooms" style="color: black;">房型總覽 | 房型照片</RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="/room/roomlist" style="color: black;">房間總攬</RouterLink>
                            </li>
                            
                        </ul>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="color: black;">
                            餐廳相關
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <RouterLink class="dropdown-item" to="/restaurant/restaurantBackend" style="color: black;">訂位資訊</RouterLink>
                            </li>
                            <li>
                                <RouterLink class="dropdown-item" to="/restaurant/RestaurantMenuBackend" style="color: black;">外帶資訊</RouterLink>
                            </li>
                            
                        </ul>
                    </li>
                </ul>
                
            </div>
        </div>
    </nav>
</template>

<script setup>

</script>

<style></style>