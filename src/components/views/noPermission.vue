<!-- NoPermission.vue -->
<template>
    <div class="no-permission">
        <h1>无权限访问</h1>
        <img src="https://imgs.nmplus.hk/wp-content/uploads/2021/11/006892xely1gw0xa6uf0aj30c80bt40r_347238195618215e648c39.jpeg"
            sytle="height:400px;width:200px" /><br>
        <el-text type="danger" style="font-size: larger;">您没有权限访问该页面。{{ countdownMessage }}</el-text>

    </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const countdown = ref(5); // 倒计时时间

const countdownMessage = computed(() => {
    return countdown.value > 0 ? `${countdown.value} 秒后返回上一页` : '';
});

onMounted(() => {
    const interval = setInterval(() => {
        if (countdown.value > 0) {
            countdown.value -= 1;
        } else {
            clearInterval(interval);
            router.back(); // 倒计时结束，返回上一页
        }
    }, 1000);
});
</script>

<style scoped>
.no-permission {
    text-align: center;
    margin-top: 50px;
}
</style>