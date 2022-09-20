<template>
  <el-row>
    <el-col :span="24">
      <div class="button_position init_style">
        <div class="text_style">
          请选择操作
        </div>
        <el-divider></el-divider>
        <el-button type="danger" style="width:150px" v-on:click="register">注册</el-button>
        <el-button type="success" style="width:150px" v-on:click="doLogin">登录</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script>
// import { Init } from "../javascript/init";
import {User} from "../javascript/data";
import {MRoute} from "../javascript/data";

export default {
  name: "InitPage",
  methods:{
    register(){
      this.$router.push(MRoute.signUp)
    },
    doLogin(){
      if(User.state === false){
        this.$router.push(MRoute.user.initial)
      }else {
        this.$router.push(MRoute.signIn)
      }
    },
  },
   mounted(){
     // this.$router.push(MRoute.initial)
   }
}
</script>

<style scoped>
.text_style{
  text-align:center;
  font-size: 25px;
  font-family: 微软雅黑;
}
.button_position{
  text-align:center;
}
.init_style{
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 150px;
  background: #96CDCD;
}
</style>