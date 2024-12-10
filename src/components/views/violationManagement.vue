<script setup>
import { onMounted, ref } from 'vue';
import instance from '../../axios.js';
import { ElMessage, ElMessageBox } from 'element-plus';
let images = ref([]);
const inputTime = ref();
const siteId = localStorage.getItem('siteId');
const isFullScreen = ref(false);  // 控制全屏显示
const fullScreenImageUrl = ref('');  // 存储全屏图片的URL
const cascaderData = ref([]); // 创建一个空对象来存储转换后的数据
const operateArea = ref(null);
// 通过java后台调用保存图片的方法
const savePictures = async () => {
    try {
        const response = await instance.get('/detect/getImages');
        if (response.data.code === 200) {
        } else {
            console.error('Failed to save images:', response.data.message);
        }
    } catch (error) {
        console.error('Error save images:', error);
    }
};
const getImages = async () => {
    try {
        const siteId = localStorage.getItem('siteId');
        const response = await instance.get(
            `/violationRecord/getImages?siteId=${siteId}`);
        if (response.data.code === 200) {
            images.value = response.data.data;
            cascaderData.value = processData(response.data.data);
            if (images.value == null) {
                ElMessage.error('没有找到符合条件的图片');
            }
        } else {
            console.error('Failed to fetch images:', response.data.message);
        }
    } catch (error) {
        console.error('Error fetching images:', error);
    }
};
// 通过时间查询图片
const getImagesByTime = async () => {
    if (!inputTime.value) {
        ElMessage.error('请选择时间');
        return;
    }

    // 获取用户选择的时间，使用 toLocaleString 转换为本地时间
    const selectedTime = inputTime.value.toLocaleString('en-GB', {
        timeZone: 'Asia/Shanghai',
        hour12: false
    });

    // 解析选择的时间字符串并格式化为 ISO 8601 格式（yyyy-MM-dd'T'HH:mm:ss）
    const [datePart, timePart] = selectedTime.split(', ');
    const [day, month, year] = datePart.split('/');
    const formattedDate = `${year}-${month}-${day}T${timePart}`;

    // 打印格式化后的时间，检查输出格式
    console.log('Selected Time (ISO):', formattedDate); // 输出 ISO 格式的时间

    try {
        const siteId = localStorage.getItem('siteId');
        // 将 formattedDate 作为查询参数传给后端
        const response = await instance.get(
            `/violationRecord/getImagesByTime?siteId=${siteId}&time=${formattedDate}`);
        if (response.data.code === 200) {
            images.value = response.data.data;
            cascaderData.value = processData(response.data.data);
            if (images.value == null) {
                ElMessage.error('没有找到符合条件的图片');
            }
        } else {
            console.error('Failed to fetch images:', response.data.message);
        }
    } catch (error) {
        console.error('Error fetching images:', error);
    }
};



// 点击图片时显示全屏
const showFullScreen = (imageUrl) => {
    fullScreenImageUrl.value = imageUrl;
    isFullScreen.value = true;
};

// 关闭全屏显示
const closeFullScreen = () => {
    isFullScreen.value = false;
    fullScreenImageUrl.value = '';
};

// 删除图片
const deleteImageByVid = async (vid) => {
    ElMessageBox.confirm('确定要删除这条记录吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        try {
            const response = await instance.get('/violationRecord/deleteImageByVid', {
                params: { vid: vid }
            });
            if (response.data.code === 200) {
                ElMessage.success(response.data.msg);
                getImages(); // 假设 getImages 是一个函数，用于重新加载图片
            } else {
                ElMessage.error(response.data.msg);
                console.error('Failed to delete image:', response.data.message);
            }
        } catch (error) {
            console.error('Error deleting image:', error);
            ElMessage.error('删除图片失败');
        }
    }).catch(() => {
        // 用户点击取消按钮时的处理逻辑
        ElMessage.info('已取消删除');
    });
};
const processData = (data) => {
    const tempData = {}; // 临时对象，用于存储按 siteId 分组的数据

    data.forEach(item => {
        // 确保每个 siteId 对应一个对象
        if (!tempData[item.siteId]) {
            tempData[item.siteId] = {
                value: item.siteId, // 使用 siteId 作为 value
                label: item.siteName, // 使用 siteName 作为 label
                children: [] // 初始化 children 数组
            };
        }

        // 检查 children 中是否已经有相同的 areaId，避免重复
        const existingArea = tempData[item.siteId].children.find(child => child.value === item.aid);
        if (!existingArea) {
            // 如果没有重复，则添加新的区域
            tempData[item.siteId].children.push({
                value: item.aid, // 使用 aid 作为 value
                label: item.areaName // 使用 areaName 作为 label
            });
        }
    });

    // 将临时对象转换为数组，并返回
    return Object.values(tempData);
};
const deleteImageByAid = async () => {
    ElMessageBox.confirm('确定要删除这个区域的所有记录吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        try {
            const response = await instance.get('/violationRecord/deleteImageByAid', {
                params: { aid: operateArea.value[1] }
            });
            if (response.data.code === 200) {
                ElMessage.success(response.data.msg);
                getImages();
            } else {
                ElMessage.error(response.data.msg);
                console.error('Failed to delete image:', response.data.message);
            }
        } catch (error) {
            console.error('Error deleting image:', error);
            ElMessage.error('删除失败');
        }
    }).catch(() => {
        // 用户点击取消按钮时的处理逻辑
        ElMessage.info('已取消删除');
    });
};
const cascaderProps = {
  expandTrigger: 'hover', // 鼠标悬停展开
  checkOnClick: true, // 点击选项时触发选择

};
const handleChange = (value, selectedData) => {
  // 处理级联选择器值变化时的逻辑
  console.log(value, selectedData);
};
onMounted(() => {
    savePictures();
    getImages();
    setInterval(savePictures, 10000);
});
</script>

<template>
    <div class="violation-div">
        <div style="display:flex;justify-content: space-between;">
            <div style="display:flex;">
                <el-date-picker v-model="inputTime" type="datetime" placeholder="选择一个最早时间" />
                <el-button type="primary" @click="getImagesByTime" style="margin-left: 3%;">搜索</el-button>
            </div>
            <div style="display:flex;">
                <el-cascader v-model="operateArea" :options="cascaderData" :props="cascaderProps" placeholder="请选择"
                    @change="handleChange" />
                <el-button type="danger" @click="deleteImageByAid" style="margin-left: 2%;">删除全部</el-button>
            </div>
        </div>
        <div style="margin-top: 10px;">
            <el-row :gutter="10">
                <el-col v-for="item in images" :key="item.vid" :span="8" style="margin-bottom:20px">
                    <el-card class="picture-card">
                        <div style="display: flex; justify-content: space-between; width: 100%;">
                            <el-text type="primary">时间: {{ item.createTime }}</el-text>
                            <el-text type="primary">地点: {{ item.areaName }}</el-text>
                        </div>

                        <el-image :src="'data:image/jpeg;base64,' + item.picture"
                            style="width: 100%; object-fit: cover;"
                            @click="showFullScreen('data:image/jpeg;base64,' + item.picture)" />
                        <div style="display: flex; justify-content: space-between;">
                            <el-text type="primary" v-if="siteId == 0">所属工地Id:{{ item.siteId }}</el-text>&nbsp;
                            <el-button type="danger" size="small" @click="deleteImageByVid(item.vid)">删除</el-button>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <!-- 全屏显示 -->
        <div v-if="isFullScreen" class="full-screen-container" @click="closeFullScreen">
            <div class="full-screen-image-wrapper">
                <img :src="fullScreenImageUrl" alt="Full Screen Image" class="full-screen-image" />
                <button class="close-button" @click="closeFullScreen">关闭全屏</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.picture-card {
    background-color: #f5f5f5;
    padding: 10px;
    border-radius: 5px;
    text-align: center;
    cursor: pointer;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.violation-div {
    max-height: 85vh;
    overflow-y: auto;
    overflow-x: hidden;
}

/* 全屏显示样式 */
.full-screen-container {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.7);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
}

.full-screen-image-wrapper {
    position: relative;
    width: 80%;
    height: 80%;
}

.full-screen-image {
    width: 100%;
    height: 100%;
    object-fit: contain;
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
</style>
