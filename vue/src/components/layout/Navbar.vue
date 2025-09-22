<template>
    <nav class="navbar">
        <div class="navbar-brand">
            <router-link to="/"></router-link>
        </div>
        <button class="navbar-toggle" @click="toggleMenu">
            ☰
        </button>
        <ul :class="['navbar-menu', menuOpen ? 'open' : '']">
            <li><router-link to="/">SAVINGTIPZ</router-link></li>
            <li><router-link to="/add-tipz">ADD SAVING TIPZ</router-link></li>
            <li><router-link to="/contact">CONTACT</router-link></li>
        </ul>
    </nav>
</template>

<script setup>
import { ref } from 'vue'
const menuOpen = ref(false)
const toggleMenu = () => {
    menuOpen.value = !menuOpen.value
}
</script>

<style scoped>
.navbar {
    display: flex;
    align-content: center;
    background-color: rgb(11, 129, 188);
    color: white;
    font-family: Arial, Helvetica, sans-serif;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1000;
    align-items: center;
    box-sizing: border-box; /*ensures padding doesn't overflow */
    justify-content: center;
}

.navbar-brand a {
    font-size: 1.5rem;
    font-weight: bold;
    color: #fff;
    text-decoration: none;
}

.navbar-toggle {
    display: none;
    font-size: 1.5rem;
    background: none;
    border: none;
    color: #fff;
    cursor: pointer;
}

.navbar-menu {
    display: flex;
    gap: 1.5rem;
    list-style: none;
}

.navbar-menu li a {
    color: #fff;
    text-decoration: none;
    font-weight: 600;
    transition: color 0.3s ease;
}

.navbar-menu li a:hover {
    color: #3b82f6;
    /* blue accent */
}

/* Responsive */
@media (max-width: 768px) {
    .navbar-toggle {
        display: block;
    }

    .navbar-menu {
        display: none;
        flex-direction: column;
        justify-items: flex-start;
        background-color: #3b82f6;
        position: relative;
        top: 0px;
        right: 0px;
        border-radius: 8px;
    }

    .navbar-menu.open {
        display: flex;
    }
}
</style>