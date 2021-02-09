<template>
    <div id="app" v-if="reset">
        <div class="maindb">
            <div class="dbcol1"></div>
            <div class="dbcol2">
                <div class="banner"></div>
                {{tbID}}
            </div>
            
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import mtqqtest from '@/components/factory/mqttfac/mtqqtest'
export default {
    name:"edittb",
    data() {
        return {
            tbData:[],
            tbID:'',
            reset:false
        }
    },
    methods: {
        getTbData() {
            let that = this;
            this.tbID = this.$route.params.tbID
            let postData=this.$qs.stringify({
                tbID:that.tbID,
            })
            const result = axios({
                method: 'post',
                url:'/getTBList',
                data:postData
            }).then(function(resp){
                if(resp.data.status==200) {
                that.tbData=resp.data.data
            }
            })
        },
    
    },
    mounted() {
        this.getTbData();
    },
    watch: {
        tbData: function(){
            this.$nextTick(function(){
                this.reset = true;
            })
        }
    }
}
</script>

<style lang="less" scope>
.maindb {
    width: 100%;
    height: calc(93.6vh);
    background-color: #fff;
    display: flex;
}
.dbcol1 {
    flex:2;
    margin-right: 40PX;
    background-color:  #ddbb80;
    height: 100%;
}
.dbcol2 {
    flex: 7;
    height: 100%;
    background-color: #fff;
    padding-right: 40px;
}
.banner {
    height: 5%;
}

.iconfont {
    // margin-right: 5px;
    font-size: 20px;
}