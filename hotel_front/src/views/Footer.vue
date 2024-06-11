<template>
    <div style="position: relative; ">
        <!-- 下方導覽列(參考台北香格里拉飯店) -->
        <footer class="footer">
            <div class="container">
                <div class="column contact">
                    <h4>台北芳山</h4>
                    <p>地址: 台灣台北市大安區仁愛路四段123號 郵政編碼 106</p>
                    <p>電話: (02)2835-8477</p>
                    <p>電子郵件: <a href="mailto:Taipei@gmail.com">Taipei@gmail.com</a></p>
                </div>
                <div class="column">
                    <h4>查找或預訂</h4>
                    <ul>
                        <li>我們的目的地</li>
                        <li>查找預訂</li>
                        <li>會員與賬戶</li>
                    </ul>
                </div>
                <div class="column">
                    <h4>芳山會</h4>
                    <ul>
                        <li>會員計劃概述</li>
                        <li>加入芳山會</li>
                        <li>我的賬戶</li>
                        <li>獎勵多點</li>
                        <li>常見問題</li>
                    </ul>
                </div>
                <div class="column">
                    <h4>關於芳山</h4>
                    <ul>
                        <li>關於我們</li>
                        <li>我們的酒店品牌</li>
                        <li>芳山中心</li>
                        <li>芳山集團</li>
                        <li>聯絡我們</li>
                    </ul>
                </div>
                <div class="column payment">
                    <h4>支付方式</h4>
                    <p>我們在指定平台上接受的線上付款方式:</p>
                    <i class="fab fa-cc-visa"></i>
                    <i class="fab fa-cc-mastercard"></i>
                    <i class="fab fa-cc-amex"></i>
                    <i class="fab fa-cc-jcb"></i>
                </div>
                <div class="column social">
                    <h4>開啟無限精彩</h4>
                    <p>體驗非凡，盡在掌中</p>
                    <i class="fab fa-apple"></i>
                    <i class="fab fa-google-play"></i>
                    <div>
                        <i class="fab fa-facebook-f"></i>
                        <i class="fab fa-instagram"></i>
                        <i class="fab fa-youtube"></i>
                    </div>
                </div>
            </div>
            <div class="bottom">
                <p> 9999 reviews</p>
                <p>隱私政策 | 條款與細則 | 供應商行為守則</p>
                <p>© 2024 芳山旅館 保留所有權利 00000號</p>
            </div>
        </footer>
    </div>
</template>

<script setup>

</script>

<style scoped>
body {
    font-family: Arial, sans-serif;

}

.footer {
    background-color: #f2f2f2;
    padding: 20px 0;
    color: #333;
    font-size: 14px;
    position: absolute;
    min-width: 100%;

}

.footer .container {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    max-width: 2000px;

    margin: 0 auto;
    padding: 0 15px;
}

.footer .column {
    flex: 1;
    padding: 0 15px;
}

.footer .column h4 {
    font-size: 16px;
    margin-bottom: 15px;
}

.footer .column ul {
    list-style: none;
    padding: 0;
}

.footer .column ul li {
    margin-bottom: 10px;
}

.footer .contact {
    text-align: left;
}

.footer .payment {
    text-align: right;
}

.footer .payment i {
    margin-left: 10px;
    font-size: 1.5em;
}

.footer .social {
    text-align: center;
}

.footer .social i {
    margin: 0 10px;
    font-size: 1.5em;
}

.footer .bottom {
    text-align: center;
    padding: 10px 0;
    background-color: #333;
    color: #fff;
}

.footer .bottom p {
    margin: 0;
}
</style>