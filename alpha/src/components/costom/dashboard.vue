<template>
    <div id="app">
        {{dbID}}
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
export default {
    name:"dashboard",
    data() {
        return {
            dbID:'',
        }
    },
    methods: {
        dbAjax() {
            this.dbID=this.$route.params.userID
        }
    },
}
</script>

<style lang="less" scoped>


</style>