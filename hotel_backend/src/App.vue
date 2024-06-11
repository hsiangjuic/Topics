<template>
  <div class="container-fluid custom-container-width">
    <NavigationBar></NavigationBar>
    <RouterView></RouterView>
  </div>
    
  
</template>

<script setup>
  import "bootstrap/dist/css/bootstrap.min.css"
  import "bootstrap/dist/js/bootstrap.bundle.min.js"
  import NavigationBar from "./views/NavigationBar.vue"
  import 'bootstrap-icons/font/bootstrap-icons.css'



</script>

<style scoped>
  .custom-container-width{
        padding: 0 100px 0 100px;
        width: 1700px;
    }
  
</style>