<script setup>
import SavingTipz from './components/SavingTipz.vue'
import Navbar from './components/layout/Navbar.vue'
</script>

<template>
  <br />
  <Navbar />
  <SavingTipz msg="Saving Tipz" />
</template>

<style scoped>

.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}

</style>
