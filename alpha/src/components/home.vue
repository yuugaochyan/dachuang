<template>
        <el-container>
            <!-- 头部 -->
            <el-header>
                <div>
                <span>工业互联网</span>
                </div>
            </el-header>
            <!-- 主体 -->
            <el-container>
                <!-- 侧边栏 -->
                <el-aside width="200px">
                    <el-menu
                    background-color="#545c64"
                    text-color="#fff"
                    active-text-color="#ffd04b">

                    <el-submenu index="1">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>导航1</span>
                    </template>
                        <el-menu-item index="1-1">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项1</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="1-2">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项2</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="1-3">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项3</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                    <el-submenu index="2">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>导航2</span>
                    </template>
                        <el-menu-item index="2-1">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项1</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="2-2">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项2</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="2-3">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项3</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                    <el-submenu index="3">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>导航3</span>
                    </template>
                        <el-menu-item index="3-1">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项1</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="3-2">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项2</span>
                            </template>
                        </el-menu-item>
                        <el-menu-item index="3-3">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>选项3</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>

                </el-menu>
                </el-aside>
                <!-- 右侧主体 -->
                <el-container>
                    <el-main>Main</el-main>
                    <el-footer>Footer</el-footer>
                </el-container>
            </el-container>
        </el-container>
</template>

<script>
export default {
    
}
</script>

<style lang="less" scoped>
.el-container {
    height: 100%;
}


.el-header {
    background-color: #353535;
    color: rgb(255, 255, 255);
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    font-size: 30px;
    >div {
        display: flex;
        align-items: center;
        span{
            margin-left: 15px;
        }
    }
}

.el-footer {
    background-color: #353535;
    color: rgb(255, 255, 255);
}

.el-aside {
    background-color: #212444;
    color: rgb(255, 255, 255);
    
}

.el-main {
    background-color: #83848542;
    color: rgb(0, 0, 0);
    text-align: center;
    line-height: 160px;
}
/*
body > .el-container {
    margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
    line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
    line-height: 320px;
}
*/

</style>