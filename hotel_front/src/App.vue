
<template>
    <NavigationBar></NavigationBar>
    <RouterView></RouterView>
    <Footer></Footer>
</template>
    
<script setup>
    import "bootstrap/dist/css/bootstrap.min.css"
    import "bootstrap/dist/js/bootstrap.bundle.min.js"
    import NavigationBar from "./views/NavigationBar.vue"
    import 'bootstrap-icons/font/bootstrap-icons.css'
    import Footer from "./views/Footer.vue"

</script>
    
<style>
    
</style>