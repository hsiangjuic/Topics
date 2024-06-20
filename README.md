# 寄信功能
寫上自己的**GMAIL**， **二階驗證碼**
**EmailService** 裡的email名稱也要修改 ，機器人認證金鑰
以上改完後端應該能正常運作
## 總結
程式碼功能為，註冊後寄信，忘記密碼提交寄信驗證碼，會員規範增刪改查，圖片增刪改查，會員成員增刪改查等

# hotel 後端
## application.properties
spring.jpa.hibernate.ddl-auto: **Hibernate** 在啟動時會根據實體類別自動建立、更新資料庫表結構的策略，這裡設定為update，表示自動更新
<br>郵件配置:
spring.mail.host=smtp.gmail.com  只支援Gmail<br>
SMTP 伺服器，連接埠為587<br>
## pom.xml
**XML**        版本1.0        編碼UTF-8<br>
**Maven POM**  版本4.0.0<br>
版本號:開發中版本0.0.1-SNAPSHOT   將Maven項目打包成**WAR**文件<br>
屬性配置:
**java**版本17<br>
**log4j-core**版本2.17.1最新版2.17應該不會受log4j漏洞影響<br>
**Servlet API**版本3.0.1在3.0提高對CSRF防護<br>
spring-boot-starter-thymeleaf<br>
spring-boot-starter-web<br>
spring-boot-starter-mail寄信<br>
spring-security-crypto輸入的密碼加密<br>
spring-boot-starter-tomcat 10版本<br>
spring-boot-devtools開發時用:自動重新啟動<br>
lombok自動生成程式碼:例如:getter,setter<br>
mssql-jdbc連接SQL<br>
json版本20240303<br>

## JavaBean
**Customer.java**:<br> 
多對一MemberRank 的mr_id 使用@JsonIgnore  避免因為雙向關聯循環引用無限遞歸<br>
verificationToken:<br>
寄信時輸出專屬驗證碼到資料庫與驗證信的連結。<br>
verificationTokenExpiration:<br>
驗證信有效時間:LocalDateTime<br>
lastModifiedDat:<br>
物件持久化到資料庫之前先執行@PrePersist，如果為空值，使用現在時間，@PreUpdate在onUpdate()中，每次更新時，一併更新時間，顯示

**MemberRank.java**:<br>
@Lob儲存大型物件
byte[]二進制儲存圖片


**技術和框架**<br>
Java Persistence API (JPA)<br>
Jakarta Persistence (formerly Java EE Persistence)<br>
Spring Framework<br>
Java 8 Date-Time API<br>
byte[] photoFile儲存會員卡圖片用<br>

## AppConfig
使用Spring Security提供的**BCryptPasswordEncoder**密碼加密器，提高安全性。調用@Bean方法會員註冊輸入密碼後會進行加密在放到資料庫裡。<br>

## EmailService
使用JavaMailSender介面。創建多用途互聯網郵件擴展發送郵件。MimeMessageHelper簡化發送郵件過程，設置寄件人名稱，主題。(允許HTML格式)郵件的樣式是在後端寫的。<br>
**密碼重置**:功能<br>
驗證碼:verificationCode隨著每封發送的郵件而不同<br>




# hotel_front

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```
# hotel_backend

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```
