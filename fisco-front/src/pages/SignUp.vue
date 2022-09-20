<template>
  <el-row>
    <el-col :span="24">
      <div class="grid-content">
        <div class="text_style">
          注册
        </div>
      <table class="table-position">
        <tr>
          <td>请输入您的手机号:</td>
          <td>
            <el-input class="input-style" v-model="new_user.ID" type="text" placeholder="Input your Name..."></el-input>
          </td>
        </tr>
        <tr>
          <td>请输入您的密码:</td>
          <td><el-input class="input-style" v-model="new_user.password" type="password" placeholder="Input your Password..."></el-input></td>
        </tr>
        <tr>
          <td>重复您的密码::</td>
          <td><el-input class="input-style" v-model="new_user.password_confirmation" type="password" placeholder="Confirm your Password..."></el-input></td>
        </tr>
      </table>
        <el-divider></el-divider>
          <div class="button_position">
            <el-button type="danger" style="width:100px" v-on:click="go_back">返回</el-button>
            <el-button type="success" style="width:100px" v-on:click="register">注册</el-button>
          </div>
      </div>
    </el-col>
  </el-row>

</template>

<script>
  // import {SignUp} from "../javascript/signUp";
  import {AxiosURL, MRoute, User} from "../javascript/data";
  import axios from "axios";

  export default {
    name: 'SignUp',
    data() {
      return {
        new_user: {
          ID: '',
          password: '',
          password_confirmation: '',
        }
      }
    },
    methods: {
      go_back() {
        this.$router.replace(MRoute.initial)
      },
      register(){
        let _this = this
          if(_this.new_user.password !== _this.new_user.password_confirmation){
            _this.infoAlert('请修改密码后重试','密码错误')
          }else {
            let  data = {username:_this.new_user.ID,password:_this.new_user.password};
            //发送 POST 请求
            this.axios({
              method: "post",
              url: AxiosURL + "/sign_up",
              data,
            }).then((res) =>{
              if(res.data === true){
                console.log("register success")
                _this.infoAlert('','注册成功')
                User.ID = _this.new_user.ID
                User.password = _this.new_user.password
                User.state = true
              }
              else{
                _this.infoAlert('账户已存在','注册失败')
              }
            });
          }
      },
      infoAlert(message,title){
        let _this = this
        _this.$info.alert(message, title, {
          confirmButtonText: '确定',
        });
      },
    },
    mounted() {
      // SignUp.blockInit()
    }
  }
</script>

<style scoped>


.button_position{
  text-align:center;
}
.table-position{
  margin:0 auto;
}
.text_style{
  text-align:center;
  font-size: 25px;
  font-family: 微软雅黑;
}
.input-style{
  width: 500px;
}
.grid-content {
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 290px;
  background: #96CDCD;
}
</style>
