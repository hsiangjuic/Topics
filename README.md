# 寄信功能
寫上自己的**GMAIL**， **二階驗證碼**
**EmailService** 裡的email名稱也要修改 ，機器人認證金鑰
以上改完後端應該能正常運作
## 總結
程式碼功能為，註冊後寄信，忘記密碼提交寄信驗證碼，會員規範增刪改查，圖片增刪改查，會員成員增刪改查等

# hotel 後端
## application.properties
spring.jpa.hibernate.ddl-auto: **Hibernate** 在啟動時會根據實體類別自動建立、更新資料庫表結構的策略，這裡設定為update，表示自動更新。
郵件配置:
spring.mail.host=smtp.gmail.com  只支援Gmail.
SMTP 伺服器，連接埠為587.
## pom.xml
**XML**        版本1.0        編碼UTF-8.
**Maven POM**  版本4.0.0.
版本號:開發中版本0.0.1-SNAPSHOT   將Maven項目打包成**WAR**文件.
屬性配置:
**java**版本17.
**log4j-core**版本2.17.1最新版2.17應該不會受log4j漏洞影響.
**Servlet API**版本3.0.1在3.0提高對CSRF防護.
spring-boot-starter-thymeleaf.
spring-boot-starter-web.
spring-boot-starter-mail寄信.
spring-security-crypto輸入的密碼加密.
spring-boot-starter-tomcat 10版本.
spring-boot-devtools開發時用:自動重新啟動.
lombok自動生成程式碼:例如:getter,setter.
mssql-jdbc連接SQL.
json版本20240303.

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
