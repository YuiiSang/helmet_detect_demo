<script setup>
import { onMounted, ref } from 'vue';
import instance from '../../axios.js';
import { ElMessage } from 'element-plus';

const videoSource = ref('');
const confidence = ref(0.5);
const isFullScreen = ref(false);  // 控制全屏显示
const fullScreenVideoUrl = ref('');  // 存储全屏视频的URL
const areaList = ref([]);  // 存储区域列表
const images = ref([]);  // 存储图片列表

// 获取保存的图片
const getImages = async () => {
    try {
        const response = await instance.get('/detect/getImages');
        if (response.data.code === 200) {
            images.value = response.data.data;
        } else {
            console.error('Failed to fetch images:', response.data.message);
        }
    } catch (error) {
        console.error('Error fetching images:', error);
    }
};

// 获取区域列表及任务数据
const getAreaListBySiteId = () => {
    instance.get('/area/getAreaListBySiteId', {
        params: {
            siteId: localStorage.getItem('siteId')
        }
    }).then((res) => {
        if (res.data.code === 200) {
            areaList.value = res.data.data;
            console.log('区域列表:', areaList.value);
        } else {
            ElMessage.error(res.data.msg);
        }
    }).catch((err) => {
        console.log(err);
    });
};

// 启动检测
const startDetection = async () => {
    const taskId = `task_${Date.now()}`;
    const videoUrl = `http://127.0.0.1:5000/video_feed?source=${encodeURIComponent(videoSource.value)}&confidence=${confidence.value}&task_id=${taskId}`;

    // 在 areaList 中找到对应区域并添加任务（如果该区域已有任务，则替换旧任务）
    const area = areaList.value.find(area => area.cameraIp === videoSource.value);
    if (area) {
        // 如果该区域已有任务，则不做操作
        if (area.tasks && area.tasks.length > 0) {
            //area.tasks[0] = { task_id: taskId, status: 'Running', videoUrl: videoUrl }; // 替换现有任务
            ElMessage.warning(area.areaName + '已有任务，无法再次启动任务');
        } else {
            area.tasks = [{ task_id: taskId, status: 'Running', videoUrl: videoUrl }]; // 新增任务
        }
    }

    console.log('更新后的区域列表:', areaList.value);
};

// 停止任务
const stopDetection = async (taskId, area, event) => {
    event.stopPropagation();  // 阻止事件冒泡，避免触发 showFullScreen
    try {
        const response = await instance.post('/detect/stop', { taskId });
        if (response.data.code === 200) {
            // 删除任务
            area.tasks = [];  // 清空任务列表
            console.log('任务已停止:', area.tasks);
        } else {
            console.error('Failed to stop task:', response.data.message);
        }
    } catch (error) {
        console.error('Error stopping task:', error);
    }
};

// 点击视频时显示全屏
const showFullScreen = (videoUrl) => {
    fullScreenVideoUrl.value = videoUrl;
    isFullScreen.value = true;
    console.log("Full screen activated:", fullScreenVideoUrl.value, isFullScreen.value); // 调试输出
};

// 关闭全屏显示
const closeFullScreen = () => {
    isFullScreen.value = false;
    fullScreenVideoUrl.value = '';
};

const showAreaInfo = (areaDescription, event) => {
    event.stopPropagation();  // 阻止事件冒泡，避免触发全屏
    ElMessage.info(areaDescription);
};
onMounted(() => {
    getAreaListBySiteId()
});
</script>

<template>

    <el-main class="detectDiv">
        <!-- 获取图片 -->
        <!--         <button @click="getImages">获取图片</button>
        <div v-if="images.length">
            <h2>图片列表</h2>
            <div v-for="(image, index) in images" :key="index">
                <img :src="'data:image/jpeg;base64,' + image.image" alt="Image" />
                <p>时间戳: {{ new Date(image.timestamp * 1000).toLocaleString() }}</p>
                <p>视频源: {{ image.source }}</p>
            </div>
        </div>  -->
        <el-select v-model="videoSource" placeholder="请选择你要添加的区域" style="width: 240px;margin-right: 5px;">
            <el-option v-for="item in areaList" :key="item.aid" :label="item.areaName" :value="item.cameraIp" />
        </el-select>
        <el-input-number v-model="confidence" :step="0.1" :max="0.9" :min="0.1" placeholder="输入置信度阈值" style="margin-right: 5px;" />
        <el-button type="primary" @click="startDetection" style="width: 240px;">开始检测</el-button>
        <!-- 显示图片 -->
        <h2>监控画面</h2>

        <!-- 显示任务列表 -->
        <el-row :gutter="20">
            <el-col v-for="area in areaList" :key="area.aid" :span="8" style="margin-bottom:20px">
                <div v-if="area.tasks && area.tasks.length > 0">
                    <div v-for="task in area.tasks" :key="task.task_id" class="task-card"
                        @click="showFullScreen(task.videoUrl)">
                        <h3>区域名称:{{ area.areaName }}</h3>
                        <img v-if="task.videoUrl" :src="task.videoUrl" alt="Video Stream" class="task-video" />
                        <button @click="stopDetection(task.task_id, area, $event)">停止任务</button>
                        <button @click="showAreaInfo(area.areaDescription, $event)">区域详情</button>
                    </div>
                </div>
                <div v-else class="task-card">
                    <h3>{{ area.areaName }}暂无监控任务</h3>
                </div>
            </el-col>
        </el-row>

        <!-- 全屏显示 -->
        <div v-if="isFullScreen" class="full-screen-container" @click="closeFullScreen">
            <div class="full-screen-video-wrapper">
                <img :src="fullScreenVideoUrl" alt="Full Screen Video" class="full-screen-video" />
                <button class="close-button" @click="closeFullScreen">关闭全屏</button>
            </div>
        </div>

    </el-main>
</template>

<style scoped>
.task-card {
    background-color: #f5f5f5;
    padding: 10px;
    border-radius: 5px;
    text-align: center;
    cursor: pointer;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.task-video {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
}

.full-screen-container {
    height: auto;
    width: auto;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 9999;
    display: flex;
    justify-content: center;
    align-items: center;
}

.full-screen-video-wrapper {
    position: relative;
    width: 80%;
    height: 80%;
}

.full-screen-video {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.close-button {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: rgba(0, 0, 0, 0.5);
    color: white;
    border: none;
    padding: 10px;
    border-radius: 5px;
    cursor: pointer;
}

.detectDiv {
    max-height: 85vh;
    overflow-y: auto;
}
</style>
